package com.taotao.content.service.jedis;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

public class JedisClientCluster implements JedisClient {

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public String set(String key, String value) {
        return jedisCluster.set(key,value);
    }

    @Override
    public String get(String key) {
        return  jedisCluster.get(key);
    }

    @Override
    public Boolean exists(String key) {
        return jedisCluster.exists(key);
    }

    @Override
    public Long expire(String key, int second) {
        return jedisCluster.expire(key,second);
    }

    @Override
    public Long ttl(String key) {
        return jedisCluster.ttl(key);
    }

    @Override
    public Long incr(String key) {
        return jedisCluster.incr(key);
    }

    @Override
    public Long hset(String key, String file, String value) {
        return jedisCluster.hset(key,file,value);
    }

    @Override
    public String hget(String key, String filed) {
        return jedisCluster.hget(key,filed);
    }

    @Override
    public Long hdel(String key, String... field) {
        return jedisCluster.hdel(key,field);
    }
}
