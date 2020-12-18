local keys = redis.call('SMEMBERS', KEYS[1])
local expiredKeysCount = 0
for i = 1, #keys do
    if not redis.call('GET', keys[i]) then
        redis.call('SREM', key, keys[i])
        expiredKeysCount = expiredKeysCount + 1
    end
end

local unexpiredKeysCount = #keys - expiredKeysCount;
if unexpiredKeysCount >= ARGV[1] then
    return 0
end

redis.call('SET', '', KEYS[2], time)
redis.call('SADD', KEYS[1], KEYS[2])
redis.call('EXPIRE', KEYS[1], ARGV[2])
return 1
