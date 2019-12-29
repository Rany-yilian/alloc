package com.shinemo.mpush.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class RedisUtils {

    private static JedisPool pool=null;

    static {
        //加载配置文件
        int i = 1;
        InputStream in = RedisUtils.class.getClassLoader().getResourceAsStream("redis.properties");
        Properties pro = new Properties();
        try {
            pro.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获得池子对象
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(Integer.parseInt(pro.get("redis.maxIdle").toString()));//最大闲置个数
        poolConfig.setMaxWaitMillis(Integer.parseInt(pro.get("redis.maxWait").toString()));//最大闲置个数
        poolConfig.setMinIdle(Integer.parseInt(pro.get("redis.minIdle").toString()));//最小闲置个数
        poolConfig.setMaxTotal(Integer.parseInt(pro.get("redis.maxTotal").toString()));//最大连接数


       /* JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(Integer.parseInt(maxIdle));
        poolConfig.setMaxWaitMillis(Integer.parseInt(maxWait));
        poolConfig.setMinIdle(Integer.parseInt(minIdle));
        poolConfig.setMaxTotal(Integer.parseInt(maxTotal));*/
       // pool = new JedisPool(poolConfig,url,Integer.parseInt(port),5000,password);

    }

    public static Jedis getJedis(){
        return pool.getResource();
    }

    public static void main(String[] args){
        Jedis jedis = getJedis();
        System.out.println(jedis);
    }
}
