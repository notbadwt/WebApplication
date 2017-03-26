package com.cache.redis;

import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class JedisClusterProxy implements Redis {
    private JedisCluster jedis;
    private int defaultExpireTime; //单位: 秒

    public JedisClusterProxy(JedisCluster jedis, int defaultExpireTime) {
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
        try {
            jedis.close();
        } catch (Exception e) {
        }
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
        ArrayList<Map<String, String>> hashList = new ArrayList<>();
        for (String key : redisList) {
            Map<String, String> hash = jedis.hgetAll(key);
            if (hash.keySet().size() > 0) {
                hashList.add(hash);
            }
        }
        return hashList;
    }

}
