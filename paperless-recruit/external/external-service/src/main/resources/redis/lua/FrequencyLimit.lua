    public Boolean isAllowed(KEYS[1] key, ARGV[1] frequency, ARGV[2] time) {

        // 获取 set 里的元素
        // smembers(key)
        Set<String> keys = redisTemplate.opsForSet().members(key);

        // 删除 set 里面过期的元素
        // srem(key+uid)
        int expiredKeysCount = 0;
        // 删除 keys 里面所有过期的 key
        for (String key0 : keys) {
            if (redisTemplate.opsForValue().get(key0) == null) {
                redisTemplate.opsForSet().remove(key, key0);
                expiredKeysCount++;
            }
        }

        // 如果未过期的 key 数量大于 frequency
        // return false
        int unexpiredKeysCount = keys.size() - expiredKeysCount;
        if (unexpiredKeysCount >= frequency) {
            return false;
        }

        // 添加 key 到 redis 里，设置过期时间
        // set(key+uid, "", time, unit)
        // sadd(key, key+uid)
        // expire(key, time, unit)
        // return true
        String member = key + UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(member, "", time, unit);
        redisTemplate.opsForSet().add(key, member);
        redisTemplate.expire(key, time, unit);
        return true;

local id = redis.call('SMEMBERS', KEYS[1])
if not id then
    redis.call('SET', KEYS[1], ARGV[1])
end
return redis.call('INCR', KEYS[1])

