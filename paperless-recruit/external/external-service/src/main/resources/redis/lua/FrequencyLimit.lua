--[[
KEYS[1] 需要限频的 key
ARGV[1] 频率
ARGV[2] 限频时间
--]]

-- 获取 key 对应的 tokens
local tokens = redis.call('SMEMBERS', KEYS[1])

-- 删除 tokens 里面所有过期的 token
local expiredTokensCount = 0
for i = 1, #tokens do
    if not redis.call('GET', tokens[i]) then
        redis.call('SREM', KEYS[1], tokens[i])
        expiredTokensCount = expiredTokensCount + 1
    end
end

-- 如果未过期的 token 数量大于等于 frequency，return false
local unexpiredTokensCount = #tokens - expiredTokensCount;
if unexpiredTokensCount >= tonumber(ARGV[1]) then
    return false
end

-- 生成唯一的 token，添加 token 到 redis 里和 key 对应的 set 里，并设置过期时间
local token = redis.call('INCR', 'frequency-limit:token:increment-id')
redis.call('SET', token, '', 'PX', ARGV[2])
redis.call('SADD', KEYS[1], token)
redis.call('PEXPIRE', KEYS[1], ARGV[2])
return true
