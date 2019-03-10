package com.taotao.content.service.test;

import com.taotao.content.service.jedis.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author chenlin
 */
public class JedisClientTest {
    @Test
    public void testJedisClient(){
        ApplicationContext context = new ClassPathXmlApplicationContext
                ("classpath:spring/applicationContext-*.xml");
        JedisClient bean = context.getBean(JedisClient.class);
        bean.set("first","100");
        String first = bean.get("first");
        System.out.println(first);

    }
}
