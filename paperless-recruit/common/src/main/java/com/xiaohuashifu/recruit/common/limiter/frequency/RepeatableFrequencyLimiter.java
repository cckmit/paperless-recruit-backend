package com.xiaohuashifu.recruit.common.limiter.frequency;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.List;

/**
 * 可获取多个 key 的限频器
 *
 * @author xhsf
 * @create 2020/12/18 15:41
 */
public class RepeatableFrequencyLimiter implements FrequencyLimiter {

    /**
     * 固定时间点刷新的限频 Redis Key 前缀
     */
    private static final String FIXED_POINT_REFRESH_FREQUENCY_LIMIT_REDIS_KEY_PREFIX =
            "frequency-limit:fixed-point-refresh:";

    /**
     * 范围刷新的限频 Redis Key 前缀
     */
    private static final String RANGE_REFRESH_FREQUENCY_LIMIT_REDIS_KEY_PREFIX = "frequency-limit:range-fresh:";

    /**
     * 可同时获取多个 key 的限频 Lua 脚本
     */
    private static final String REPEATABLE_FREQUENCY_LIMIT_LUA =
            "--[[\n" +
            "KEYS[i] 需要限频的 key\n" +
            "ARGV[i] 频率\n" +
            "ARGV[#KEYS + i] 限频时间\n" +
            "ARGV[#KEYS * 2 + i] 限频类型\n" +
            "--]]\n" +
            "\n" +
            "-- 记录已经获取成功的 key: token 键值对\n" +
            "local tokenMap = {}\n" +
            "\n" +
            "-- 循环获取每个 token\n" +
            "for i = 1, #KEYS do\n" +
            "    if ARGV[#KEYS * 2 + i] == 'FIXED_POINT_REFRESH' then\n" +
            "        -- 若频率为0直接拒绝请求\n" +
            "        if tonumber(ARGV[i]) == 0 then\n" +
            "            break\n" +
            "        end\n" +
            "\n" +
            "        -- 获取 key 对应的 token 数量\n" +
            "        KEYS[i] = '" + FIXED_POINT_REFRESH_FREQUENCY_LIMIT_REDIS_KEY_PREFIX + "' .. KEYS[i]\n" +
            "        local tokenNumbers = redis.call('GET', KEYS[i])\n" +
            "\n" +
            "        -- 如果对应 key 存在，且数量大于等于 frequency，直接 break\n" +
            "        if tokenNumbers and tonumber(tokenNumbers) >= tonumber(ARGV[i]) then\n" +
            "            break\n" +
            "        end\n" +
            "\n" +
            "        -- 增加token数量，设置过期时间\n" +
            "        redis.call('INCR', KEYS[i])\n" +
            "        redis.call('PEXPIRE', KEYS[i], ARGV[#KEYS + i])\n" +
            "        tokenMap[KEYS[i]] = ''\n" +
            "    else\n" +
            "        -- 获取 key 对应的 tokens\n" +
            "        KEYS[i] = '" + RANGE_REFRESH_FREQUENCY_LIMIT_REDIS_KEY_PREFIX + "' .. KEYS[i]\n" +
            "        local tokens = redis.call('SMEMBERS', KEYS[i])\n" +
            "\n" +
            "        -- 删除 tokens 里面所有过期的 token\n" +
            "        local expiredTokensCount = 0\n" +
            "        for j = 1, #tokens do\n" +
            "            if not redis.call('GET', tokens[j]) then\n" +
            "                redis.call('SREM', KEYS[i], tokens[j])\n" +
            "                expiredTokensCount = expiredTokensCount + 1\n" +
            "            end\n" +
            "        end\n" +
            "\n" +
            "        -- 如果未过期的 token 数量大于等于 frequency，直接 break\n" +
            "        local unexpiredTokensCount = #tokens - expiredTokensCount\n" +
            "        if unexpiredTokensCount >= tonumber(ARGV[i]) then\n" +
            "            break\n" +
            "        end\n" +
            "\n" +
            "        -- 生成唯一的 token，添加 token 到 redis 里和 key 对应的 set 里，并设置过期时间\n" +
            "        local token = '" + RANGE_REFRESH_FREQUENCY_LIMIT_REDIS_KEY_PREFIX + "' .. redis.call('INCR', 'frequency-limit:token:increment-id')\n" +
            "        redis.call('SET', token, '', 'PX', ARGV[#KEYS + i])\n" +
            "        redis.call('SADD', KEYS[i], token)\n" +
            "        redis.call('PEXPIRE', KEYS[i], ARGV[#KEYS + i])\n" +
            "        tokenMap[KEYS[i]] = token\n" +
            "    end\n" +
            "end\n" +
            "\n" +
            "-- 获取 tokenMap 的大小\n" +
            "local tokenMapSize = 0\n" +
            "for key, token in pairs(tokenMap) do\n" +
            "\ttokenMapSize = tokenMapSize + 1\n" +
            "end\n" +
            "\n" +
            "-- 判断是否获取所有 token 都成功，若失败需要释放已经获取的 token\n" +
            "if tokenMapSize < #KEYS then\n" +
            "    for key, token in pairs(tokenMap) do\n" +
            "        if token == '' then\n" +
            "            redis.call('INCRBY', key, -1)\n" +
            "        else\n" +
            "            redis.call('SREM', key, token)\n" +
            "            redis.call('DEL', token)\n" +
            "        end\n" +
            "    end\n" +
            "    return false\n" +
            "end\n" +
            "\n" +
            "return true";

    /**
     * StringRedisTemplate
     */
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 可获取多个 key 的限频脚本
     */
    private final RedisScript<Boolean> repeatableFrequencyLimitRedisScript;

    public RepeatableFrequencyLimiter(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        // 可获取多个 key 的限频脚本
        DefaultRedisScript<Boolean> repeatableFrequencyLimitRedisScript = new DefaultRedisScript<>();
        repeatableFrequencyLimitRedisScript.setResultType(Boolean.class);
        repeatableFrequencyLimitRedisScript.setScriptText(REPEATABLE_FREQUENCY_LIMIT_LUA);
        this.repeatableFrequencyLimitRedisScript = repeatableFrequencyLimitRedisScript;
    }

    /**
     * 查询多个键是否被允许操作
     *
     * 只要其中一个不被允许，就会失败，并释放已经获取的 tokens
     *
     * @param frequencyLimiterTypes 限频类型
     * @param keys 需要限频的键
     * @param frequencies 频率
     * @param timeouts 过期时间
     * @return 是否允许
     */
    public boolean isAllowed(FrequencyLimiterType[] frequencyLimiterTypes, List<String> keys, long[] frequencies,
                             long[] timeouts) {
        String[] args = new String[frequencyLimiterTypes.length * 3];
        for (int i = 0; i < frequencies.length; i++) {
            args[i] = String.valueOf(frequencies[i]);
        }
        for (int i = 0; i < timeouts.length; i++) {
            args[frequencyLimiterTypes.length + i] = String.valueOf(timeouts[i]);
        }
        for (int i = 0; i < frequencyLimiterTypes.length; i++) {
            args[frequencyLimiterTypes.length * 2 + i] = frequencyLimiterTypes[i].name();
        }
        return stringRedisTemplate.execute(repeatableFrequencyLimitRedisScript, keys, args);
    }
    
}
