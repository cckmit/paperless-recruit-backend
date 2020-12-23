package com.xiaohuashifu.recruit.common.limiter.frequency;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.List;

/**
 * 默认的限频器
 *
 * @author xhsf
 * @create 2020/12/18 15:41
 */
public class DefaultFrequencyLimiter implements FrequencyLimiter {

    /**
     * 固定时间点刷新的限频 Redis Key 前缀
     */
    private static final String FIXED_POINT_REFRESH_FREQUENCY_LIMIT_REDIS_KEY_PREFIX =
            "frequency-limit:fixed-point-refresh:";

    /**
     * 固定延迟刷新的限频 Redis Key 前缀
     */
    private static final String FIXED_DELAY_REFRESH_FREQUENCY_LIMIT_REDIS_KEY_PREFIX =
            "frequency-limit:fixed-delay-refresh:";

    /**
     * 范围刷新的限频 Redis Key 前缀
     */
    private static final String RANGE_REFRESH_FREQUENCY_LIMIT_REDIS_KEY_PREFIX =
            "frequency-limit:range-fresh:";

    /**
     * 被允许时的 Lua 脚本返回值
     */
    private static final int LUA_RETURN_VALUE_WHEN_ALLOWED = -1;

    /**
     * 可同时获取多个 key 的限频 Lua 脚本
     */
    private static final String REPEATABLE_FREQUENCY_LIMIT_LUA =
        "--[[\n" +
                "KEYS[i] 需要限频的 key\n" +
                "ARGV[(i - 1) * 3 + 1] 频率\n" +
                "ARGV[(i - 1) * 3 + 2] expireAt or refreshTime or delayTime\n" +
                "ARGV[(i - 1) * 3 + 3] 限频类型\n" +
                "--]]\n" +
                "\n" +
                "-- 由于调用了时间函数，因此需要调用此函数，让 Redis 只复制写命令，避免主从不一致\n" +
                "redis.replicate_commands()\n" +
                "\n" +
                "-- 记录已经获取成功的 key，值是失败时需要进行的操作\n" +
                "local tokenMap = {}\n" +
                "\n" +
                "-- 获取当前时间\n" +
                "local now = redis.call('TIME')\n" +
                "local currentTime = tonumber(now[1]) * 1000 + math.ceil(tonumber(now[2]) / 1000)\n" +
                "\n" +
                "-- 最后一个获取成功的 key 下标\n" +
                "local lastIndex = 0\n" +
                "\n" +
                "-- 循环处理每个限频请求\n" +
                "for i = 1, #KEYS do\n" +
                "    -- 若频率为0直接拒绝请求\n" +
                "    local frequency = tonumber(ARGV[(i - 1) * 3 + 1])\n" +
                "    if frequency == 0 then\n" +
                "        break\n" +
                "    end\n" +
                "\n" +
                "    -- 范围刷新\n" +
                "    if ARGV[(i - 1) * 3 + 3] == 'RANGE_REFRESH' then\n" +
                "        -- 删除 tokens 里面第一个过期的 token\n" +
                "        local expireTime = redis.call('LINDEX', KEYS[i], 0)\n" +
                "        if expireTime and (tonumber(expireTime) <= currentTime) then\n" +
                "            redis.call('LPOP', KEYS[i])\n" +
                "        end\n" +
                "\n" +
                "        -- 如果 token 的数量大于等于 frequency，直接 break\n" +
                "        if redis.call('LLEN', KEYS[i]) >= frequency then\n" +
                "            break\n" +
                "        end\n" +
                "\n" +
                "        -- 把 expireAt 作为 value 添加到对于 key 的 list 里，并设置 list 的过期时间\n" +
                "        local expireAt = currentTime + tonumber(ARGV[(i - 1) * 3 + 2])\n" +
                "        redis.call('RPUSH', KEYS[i], expireAt)\n" +
                "        redis.call('PEXPIREAT', KEYS[i], expireAt)\n" +
                "        tokenMap[KEYS[i]] = 'RPOP'\n" +
                "\n" +
                "    -- 固定延迟刷新\n" +
                "    elseif ARGV[(i - 1) * 3 + 3] == 'FIXED_DELAY_REFRESH' then\n" +
                "        -- 获取 key 对应的 token 数量\n" +
                "        local tokenNumbers = redis.call('GET', KEYS[i])\n" +
                "\n" +
                "        -- 如果对应 key 存在，且数量大于等于 frequency，直接 break\n" +
                "        if tokenNumbers and tonumber(tokenNumbers) >= frequency then\n" +
                "            break\n" +
                "        end\n" +
                "\n" +
                "        -- 增加 token 数量，设置过期时间\n" +
                "        redis.call('INCR', KEYS[i])\n" +
                "        -- 如果原来的并不存在对应 key，需要设置过期时间\n" +
                "        if not tokenNumbers then\n" +
                "            redis.call('PEXPIRE', KEYS[i], ARGV[(i - 1) * 3 + 2])\n" +
                "        end\n" +
                "        tokenMap[KEYS[i]] = 'INCRBY'\n" +
                "\n" +
                "    -- 固定时间点刷新\n" +
                "    else\n" +
                "        -- expireAt 小于当前时间直接拒绝\n" +
                "        local expireAt = tonumber(ARGV[(i - 1) * 3 + 2])\n" +
                "        if expireAt <= currentTime then\n" +
                "            break\n" +
                "        end\n" +
                "\n" +
                "        -- 获取 key 对应的 token 数量\n" +
                "        local tokenNumbers = redis.call('GET', KEYS[i])\n" +
                "\n" +
                "        -- 如果对应 key 存在，且数量大于等于 frequency，直接 break\n" +
                "        if tokenNumbers and tonumber(tokenNumbers) >= frequency then\n" +
                "            break\n" +
                "        end\n" +
                "\n" +
                "        -- 增加 token 数量，设置过期时间\n" +
                "        redis.call('INCR', KEYS[i])\n" +
                "        -- 如果原来的并不存在对应 key，需要设置过期时间\n" +
                "        if not tokenNumbers then\n" +
                "            redis.call('PEXPIREAT', KEYS[i], ARGV[(i - 1) * 3 + 2])\n" +
                "        end\n" +
                "        tokenMap[KEYS[i]] = 'INCRBY'\n" +
                "    end\n" +
                "    lastIndex = i\n" +
                "end\n" +
                "\n" +
                "-- 判断是否所有限频都成功，若失败需要释放已经成功的 token\n" +
                "if lastIndex < #KEYS then\n" +
                "    for key, token in pairs(tokenMap) do\n" +
                "        if token == 'RPOP' then\n" +
                "            redis.call('RPOP', key)\n" +
                "        else\n" +
                "            redis.call('INCRBY', key, -1)\n" +
                "        end\n" +
                "    end\n" +
                "    return lastIndex\n" +
                "end\n" +
                "\n" +
                "return -1\n";

    /**
     * StringRedisTemplate
     */
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 可获取多个 key 的限频脚本
     */
    private final RedisScript<Long> repeatableFrequencyLimitRedisScript;

    public DefaultFrequencyLimiter(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        // 可获取多个 key 的限频脚本
        DefaultRedisScript<Long> repeatableFrequencyLimitRedisScript = new DefaultRedisScript<>();
        repeatableFrequencyLimitRedisScript.setResultType(Long.class);
        repeatableFrequencyLimitRedisScript.setScriptText(REPEATABLE_FREQUENCY_LIMIT_LUA);
        this.repeatableFrequencyLimitRedisScript = repeatableFrequencyLimitRedisScript;
    }

    /**
     * 查询一个键是否被允许操作
     *
     * @param frequencyLimitType 限频类型
     * @param key 需要限频的键，key的格式最好是 {服务名}:{具体业务名}:{唯一标识符}，如 sms:auth-code:15333333333
     * @param frequency 频率
     * @param time 过期时间戳 or 刷新时间 or 延迟时间
     * @return 是否允许
     */
    @Override
    public boolean isAllowed(FrequencyLimitType frequencyLimitType, String key, long frequency, long time) {
        String[] args = {String.valueOf(frequency), String.valueOf(time), frequencyLimitType.name()};
        return isAllowed(Collections.singletonList(key), args) == LUA_RETURN_VALUE_WHEN_ALLOWED;
    }

    /**
     * 查询多个键是否被允许操作
     *
     * 只要其中一个不被允许，就会失败，并释放已经获取的 tokens
     *
     * @param keys 需要限频的键
     * @param args 参数列表，格式为 [frequency1, time1, frequencyLimitType1,
     *             frequency2, time2, frequencyLimitType2, ..., frequencyN, timeN, frequencyLimitTypeN]
     * @return 是否允许，-1表示允许，其他表示获取失败时的下标
     */
    public int isAllowed(List<String> keys, String[] args) {
        for (int i = 0; i < keys.size(); i++) {
            String frequencyLimitType = args[i * 3 + 2];
            String time = args[i * 3 + 1];
            if (frequencyLimitType.equals(FrequencyLimitType.FIXED_POINT_REFRESH.name())) {
                keys.set(i, FIXED_POINT_REFRESH_FREQUENCY_LIMIT_REDIS_KEY_PREFIX + time + ":" + keys.get(i));
            } else if (frequencyLimitType.equals(FrequencyLimitType.RANGE_REFRESH.name())){
                keys.set(i, RANGE_REFRESH_FREQUENCY_LIMIT_REDIS_KEY_PREFIX + time + ":" + keys.get(i));
            } else {
                keys.set(i, FIXED_DELAY_REFRESH_FREQUENCY_LIMIT_REDIS_KEY_PREFIX + keys.get(i));
            }
        }
        return stringRedisTemplate.execute(repeatableFrequencyLimitRedisScript, keys, args).intValue();
    }

}
