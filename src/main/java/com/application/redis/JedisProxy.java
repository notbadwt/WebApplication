package com.application.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by WangTao on 2016/7/20 0020.
 */
public class JedisProxy implements Redis {

    private Jedis jedis;
    private int defaultExpireTime; //单位: 秒

    public JedisProxy(Jedis jedis, int defaultExpireTime) {
        this.jedis = jedis;
        this.defaultExpireTime = defaultExpireTime;
    }

    public int getDefaultExpireTime() {
        return defaultExpireTime;
    }

    public void setDefaultExpireTime(int defaultExpireTime) {
        this.defaultExpireTime = defaultExpireTime;
    }

    @Override
    public void setHash(String key, Map<String, String> value, int seconds) {
        for (String vk : value.keySet()) {
            jedis.hset(key, vk, value.get(vk));
        }
        jedis.expire(key, seconds);
    }

    @Override
    public void setHash(String key, Map<String, String> value) {
        setHash(key, value, defaultExpireTime);
    }


    @Override
    public Map<String, String> getHash(String key) {
        return jedis.hgetAll(key);
    }

    @Override
    public void set(String key, String value, int seconds) {
        jedis.set(key, value);
        jedis.expire(key, seconds);
    }

    @Override
    public void set(String key, String value) {
        set(key, value, defaultExpireTime);
    }

    @Override
    public String get(String key) {
        return jedis.get(key);
    }


    @Override
    public void del(String key) {
        jedis.del(key);
    }


    @Override
    public void close() {
        jedis.close();
    }

    @Override
    public String ping() {
        return jedis.ping();
    }


    @Override
    public List<String> getList(String key) {
        return jedis.lrange(key, 0, -1);
    }


    @Override
    public void setList(String key, Collection<String> stringList) {
        if (stringList.isEmpty()) {
            return;
        }
        String[] stringArray = new String[stringList.size()];
        jedis.del(key);
        jedis.lpush(key, stringList.toArray(stringArray));
    }

    public List<Map<String, String>> getListHash(Collection<String> redisList) throws Exception {
        Pipeline pipeline = jedis.pipelined();
        ArrayList<Map<String, String>> hashList = new ArrayList<>();
        for (String key : redisList) {
            pipeline.hgetAll(key);
        }
        pipeline.multi();
        List<Object> result = pipeline.syncAndReturnAll();
        for (Object object : result) {
            if (!(object instanceof String)) {
                HashMap<String, String> hash = (HashMap<String, String>) object;
                if (hash.keySet().size() > 0) {
                    hashList.add(hash);
                }
            }
        }
        try {
            pipeline.close();
        } catch (Exception e) {
            throw e;
        }
        return hashList;
    }

}
