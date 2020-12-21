--[[
KEYS[i] 需要限频的 key
ARGV[i] 频率
ARGV[#KEYS + i] 过期时间 unix 类型
ARGV[#KEYS * 2 + i] 限频类型
ARGV[#KEYS * 3 + 1] 当前时间戳
--]]

-- 记录已经获取成功的 key: token 键值对
local tokenMap = {}
local currentTime = tonumber(ARGV[#KEYS * 3 + 1])

local lastIndex = 0
-- 循环获取每个 token
for i = 1, #KEYS do
    -- 若频率为0直接拒绝请求
    if tonumber(ARGV[i]) == 0 then
        break
    end
    if ARGV[#KEYS * 2 + i] == 'FIXED_POINT_REFRESH' then
        -- 获取 key 对应的 token 数量
        local tokenNumbers = redis.call('GET', KEYS[i])

        -- 如果对应 key 存在，且数量大于等于 frequency，直接 break
        if tokenNumbers and tonumber(tokenNumbers) >= tonumber(ARGV[i]) then
            break
        end

        -- 增加token数量，设置过期时间
        redis.call('INCR', KEYS[i])
        redis.call('PEXPIREAT', KEYS[i], ARGV[#KEYS + i])
        tokenMap[KEYS[i]] = ''
    else
        -- 删除 tokens 里面第一个过期的 token
        local expireTime = redis.call('LINDEX', KEYS[i], 0)
        if expireTime and (tonumber(expireTime) <= currentTime) then
            redis.call('LPOP', KEYS[i])
        end

        -- 如果未过期的 token 数量大于等于 frequency，直接 break
        if redis.call('LLEN', KEYS[i]) >= tonumber(ARGV[i]) then
            break
        end

        -- 把 expireAt 作为 value 添加到对于 key 的 list 里，并设置 list 的过期时间
        redis.call('RPUSH', KEYS[i], ARGV[#KEYS + i])
        redis.call('PEXPIREAT', KEYS[i], ARGV[#KEYS + i])
    end
    lastIndex = i
end

-- 判断是否获取所有 token 都成功，若失败需要释放已经获取的 token
if lastIndex < #KEYS then
    for key, token in pairs(tokenMap) do
        redis.call('INCRBY', key, -1)
    end
    return lastIndex
end

return -1