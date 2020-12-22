--[[
KEYS[1] 需要限频的 key
ARGV[1] 频率
ARGV[2] 过期时间戳
ARGV[3] 限频类型
ARGV[4] 当前时间戳
--]]

-- 若频率为0直接拒绝请求
local frequency = tonumber(ARGV[1])
if frequency == 0 then
    break
end

-- 范围刷新
if ARGV[3] == 'RANGE_REFRESH' then
    -- 删除 tokens 里面第一个过期的 token
    local expireTime = redis.call('LINDEX', KEYS[1], 0)
    if expireTime and (tonumber(expireTime) <= tonumber(ARGV[4])) then
        redis.call('LPOP', KEYS[1])
    end

    -- 如果 token 的数量大于等于 frequency，直接 return
    if redis.call('LLEN', KEYS[1]) >= frequency) then
        return false
    end

    -- 把 expireAt 作为 value 添加到对于 key 的 list 里，并设置 list 的过期时间
    redis.call('RPUSH', KEYS[1], ARGV[2])
    redis.call('PEXPIREAT', KEYS[1], ARGV[2])
-- 固定时间点刷新和固定延迟刷新
else
    -- 获取 key 对应的 token 数量
    local tokenNumbers = redis.call('GET', KEYS[1])

    -- 如果对应 key 存在，且数量大于等于 frequency，直接 return
    if tokenNumbers and tonumber(tokenNumbers) >= tonumber(ARGV[1]) then
        return false
    end

    -- 增加 token 数量，设置过期时间
    redis.call('INCR', KEYS[1])
    -- 如果原来的并不存在对应 key，需要设置过期时间
    if not tokenNumbers then
        redis.call('PEXPIREAT', KEYS[1], ARGV[2])
    end
end

return true