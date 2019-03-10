package com.taotao.content.service.jedis;

/**
 * @author chenlin
 */
public interface JedisClient {
    String set(String key,String value);
    String get(String key);
    Boolean exists(String key);
    Long expire(String key,int second);
    Long ttl(String key);
    Long incr(String key);
    Long hset(String key,String file,String value);
    String hget(String key,String filed);
    Long hdel(String key,String... field);//删除hkey
}
