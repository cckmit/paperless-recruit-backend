local id = redis.call('GET', KEYS[1])
if not id then
    redis.call('SET', KEYS[1], ARGV[1])
end
return redis.call('INCR', KEYS[1])