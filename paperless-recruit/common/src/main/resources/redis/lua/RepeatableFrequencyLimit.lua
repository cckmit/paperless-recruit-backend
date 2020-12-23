--[[
KEYS[i] 需要限频的 key
ARGV[(i - 1) * 3 + 1] 频率
ARGV[(i - 1) * 3 + 2] expireAt or refreshTime or delayTime
ARGV[(i - 1) * 3 + 3] 限频类型
--]]

-- 由于调用了时间函数，因此需要调用此函数，让 Redis 只复制写命令，避免主从不一致
redis.replicate_commands()

-- 记录已经获取成功的 key，值是失败时需要进行的操作
local tokenMap = {}

-- 获取当前时间
local now = redis.call('TIME')
local currentTime = tonumber(now[1]) * 1000 + math.ceil(tonumber(now[2]) / 1000)

-- 最后一个获取成功的 key 下标
local lastIndex = 0

-- 循环处理每个限频请求
for i = 1, #KEYS do
    -- 若频率为0直接拒绝请求
    local frequency = tonumber(ARGV[(i - 1) * 3 + 1])
    if frequency == 0 then
        break
    end

    -- 范围刷新
    if ARGV[(i - 1) * 3 + 3] == 'RANGE_REFRESH' then
        -- 删除 tokens 里面第一个过期的 token
        local expireTime = redis.call('LINDEX', KEYS[i], 0)
        if expireTime and (tonumber(expireTime) <= currentTime) then
            redis.call('LPOP', KEYS[i])
        end

        -- 如果 token 的数量大于等于 frequency，直接 break
        if redis.call('LLEN', KEYS[i]) >= frequency then
            break
        end

        -- 把 expireAt 作为 value 添加到对于 key 的 list 里，并设置 list 的过期时间
        local expireAt = currentTime + tonumber(ARGV[(i - 1) * 3 + 2])
        redis.call('RPUSH', KEYS[i], expireAt)
        redis.call('PEXPIREAT', KEYS[i], expireAt)
        tokenMap[KEYS[i]] = 'RPOP'

    -- 固定延迟刷新
    elseif ARGV[(i - 1) * 3 + 3] == 'FIXED_DELAY_REFRESH' then
        -- 获取 key 对应的 token 数量
        local tokenNumbers = redis.call('GET', KEYS[i])

        -- 如果对应 key 存在，且数量大于等于 frequency，直接 break
        if tokenNumbers and tonumber(tokenNumbers) >= frequency then
            break
        end

        -- 增加 token 数量，设置过期时间
        redis.call('INCR', KEYS[i])
        -- 如果原来的并不存在对应 key，需要设置过期时间
        if not tokenNumbers then
            redis.call('PEXPIRE', KEYS[i], ARGV[(i - 1) * 3 + 2])
        end
        tokenMap[KEYS[i]] = 'INCRBY'

    -- 固定时间点刷新
    else
        -- expireAt 小于当前时间直接拒绝
        local expireAt = tonumber(ARGV[(i - 1) * 3 + 2])
        if expireAt <= currentTime then
            break
        end

        -- 获取 key 对应的 token 数量
        local tokenNumbers = redis.call('GET', KEYS[i])

        -- 如果对应 key 存在，且数量大于等于 frequency，直接 break
        if tokenNumbers and tonumber(tokenNumbers) >= frequency then
            break
        end

        -- 增加 token 数量，设置过期时间
        redis.call('INCR', KEYS[i])
        -- 如果原来的并不存在对应 key，需要设置过期时间
        if not tokenNumbers then
            redis.call('PEXPIREAT', KEYS[i], ARGV[(i - 1) * 3 + 2])
        end
        tokenMap[KEYS[i]] = 'INCRBY'
    end
    lastIndex = i
end

-- 判断是否所有限频都成功，若失败需要释放已经成功的 token
if lastIndex < #KEYS then
    for key, token in pairs(tokenMap) do
        if token == 'RPOP' then
            redis.call('RPOP', key)
        else
            redis.call('INCRBY', key, -1)
        end
    end
    return lastIndex
end

return -1
