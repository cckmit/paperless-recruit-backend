--[[
KEYS[1] 需要限频的 key
ARGV[1] 频率
ARGV[2] 过期时间
--]]

-- 若频率为0直接拒绝请求
if tonumber(ARGV[1]) == 0 then
    return false
end

-- 获取 key 对应的 token 数量
local tokenNumbers = redis.call('GET', KEYS[1])

-- 如果对应 key 存在，且数量大于等于 frequency，直接返回 false
if tokenNumbers and tokenNumbers >= tonumber(ARGV[1]) then
    return false
end

-- 增加token数量，设置过期时间
redis.call('INCR', KEYS[1])
redis.call('PEXPIRE', ARGV[2])
return true
