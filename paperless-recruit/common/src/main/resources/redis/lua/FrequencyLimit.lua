--[[
KEYS[i] 需要限频的 key
ARGV[i] 频率
ARGV[#KEYS + i] 限频时间
ARGV[#KEYS * 2 + i] 限频类型
--]]

-- 记录已经获取成功的 key: token 键值对
local tokenMap = {}

-- 循环获取每个 token
for i = 1, #KEYS do
    if ARGV[#KEYS * 2 + i] == 'FIXED_POINT_REFRESH' then
        -- 若频率为0直接拒绝请求
        if tonumber(ARGV[i]) == 0 then
            break
        end

        -- 获取 key 对应的 token 数量
        local tokenNumbers = redis.call('GET', KEYS[i])

        -- 如果对应 key 存在，且数量大于等于 frequency，直接 break
        if tokenNumbers and tonumber(tokenNumbers) >= tonumber(ARGV[i]) then
            break
        end

        -- 增加token数量，设置过期时间
        redis.call('INCR', KEYS[i])
        redis.call('PEXPIRE', KEYS[i], ARGV[#KEYS + i])
        tokenMap[KEYS[i]] = ''
    else
        -- 获取 key 对应的 tokens
        local tokens = redis.call('SMEMBERS', KEYS[i])

        -- 删除 tokens 里面所有过期的 token
        local expiredTokensCount = 0
        for j = 1, #tokens do
            if not redis.call('GET', tokens[j]) then
                redis.call('SREM', KEYS[i], tokens[j])
                expiredTokensCount = expiredTokensCount + 1
            end
        end

        -- 如果未过期的 token 数量大于等于 frequency，直接 break
        local unexpiredTokensCount = #tokens - expiredTokensCount
        if unexpiredTokensCount >= tonumber(ARGV[i]) then
            break
        end

        -- 生成唯一的 token，添加 token 到 redis 里和 key 对应的 set 里，并设置过期时间
        local token = redis.call('INCR', 'frequency-limit:token:increment-id')
        redis.call('SET', token, '', 'PX', ARGV[#KEYS + i])
        redis.call('SADD', KEYS[i], token)
        redis.call('PEXPIRE', KEYS[i], ARGV[#KEYS + i])
        tokenMap[KEYS[i]] = token
    end
end

-- 获取 tokenMap 的大小
local tokenMapSize = 0
for key, token in pairs(tokenMap) do
	tokenMapSize = tokenMapSize + 1
end

-- 判断是否获取所有 token 都成功，若失败需要释放已经获取的 token
if tokenMapSize < #KEYS then
    for key, token in pairs(tokenMap) do
        if token == '' then
            redis.call('INCRBY', key, -1)
        else
            redis.call('SREM', key, token)
            redis.call('DEL', token)
        end
    end
    return tokenMapSize
end

return -1