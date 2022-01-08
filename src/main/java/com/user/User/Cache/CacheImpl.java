package com.user.User.Cache;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class CacheImpl implements Cache{

    private Jedis jedis;

    CacheImpl(){
        this.jedis = new Jedis();
    }

    public Jedis getJedis() {
        return jedis;
    }
}
