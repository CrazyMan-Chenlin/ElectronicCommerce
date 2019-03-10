package com.taotao.content.service.test;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chenlin
 */
public class JedisTest {
    @Test
    public void testJedis() throws Exception {
        //1.创建Jedis对象
        Jedis jedis = new Jedis("192.168.111.14",6379);
        //2.使用redis对象操作数据库
        jedis.set("key1234","value");
        System.out.println(jedis.get("key1234"));
        //3.关闭Jedis
        jedis.close();
    }

    /**
     * 使用连接池
     */
    @Test
    public void testJedisPool(){
         //1.创建一个连接池对象
        JedisPool jedisPool = new JedisPool("192.168.111.14",6379);
        // 2. 从池中获取Jedis对象
        Jedis jedis = jedisPool.getResource();
        // 3. 操作redis服务器
        jedis.set("jedis","chenlin");
        // 4. 输出值
        System.out.println(jedis.get("jedis"));
        // 5. 将对象返回池中
        jedis.close();
        // 6.关闭池对象(系统关闭是调用)
        jedisPool.close();
    }

    /**
     * 连接集群版
     */
    @Test
    public void testJedisCluster() throws IOException {
        //1.建立一个集合
        Set<HostAndPort> nodes = new HashSet<>();
        //2. 封装集群信息
        nodes.add(new HostAndPort("192.168.111.14",7001));
        nodes.add(new HostAndPort("192.168.111.14",7002));
        nodes.add(new HostAndPort("192.168.111.14",7003));
        nodes.add(new HostAndPort("192.168.111.14",7004));
        nodes.add(new HostAndPort("192.168.111.14",7005));
        nodes.add(new HostAndPort("192.168.111.14",7006));
        // 3.使用JedisCluster对象
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("key1212","roy");
        System.out.println(jedisCluster.get("key1212"));
        // 4.关闭JedisCluster(系统关闭是调用)
        jedisCluster.close();
    }
}
