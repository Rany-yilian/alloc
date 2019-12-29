package com.shinemo.mpush.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class PubSubTest {

    public static void main( String[] args )
    {
        // 连接redis服务端
        JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "94.191.72.51", 6379,10000,"123456");

        System.out.println(String.format("redis pool is starting, redis ip %s, redis port %d", "94.191.72.51", 6379));
        SubThread subThread = new SubThread(jedisPool);  //订阅者
        subThread.start();
        Publisher publisher = new Publisher(jedisPool);    //发布者
        publisher.start();
    }
}
