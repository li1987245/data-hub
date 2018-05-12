package cn.credit.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class RedisUtil {

    private static final String LOCK_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;
    private static JedisClusterUtil jedisClusterUtil = JedisClusterUtil.getInstance();

    public void set(String key, String value, String typeNo) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        jedis.set(getUnionKey(key, typeNo), value);
    }

    public void set(String key, String value) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        jedis.set(key, value);
    }


    public void set(String key, String value, Integer period, String typeNo) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        jedis.setex(getUnionKey(key, typeNo), period, value);

    }

    public void set(String key, Map<String, String> value, String typeNo) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        jedis.hmset(getUnionKey(key, typeNo), value);

    }

    public void set(String key, Map<String, String> value, Integer period, String typeNo) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        String unionKey = getUnionKey(key, typeNo);
        jedis.hmset(unionKey, value);
        jedis.expire(unionKey, period);

    }

    public void set(String key, List<String> value, String typeNo) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        String unionKey = getUnionKey(key, typeNo);
        jedis.rpush(unionKey, list2array(value));
    }

    public void set(String key, List<String> value, Integer period, String typeNo) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        String unionKey = getUnionKey(key, typeNo);
        jedis.rpush(unionKey, list2array(value));
        jedis.expire(unionKey, period);
    }

    public String get(String key) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        String str = jedis.get(key);
        return str;
    }

    public String get(String key, String typeNo) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        String str = jedis.get(getUnionKey(key, typeNo));
        return str;
    }

    public List<String> mgetValueByField(String key, String typeNo, String... fields) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        String unionKey = getUnionKey(key, typeNo);
        List<String> str = jedis.hmget(unionKey, fields);
        return str;
    }

    //对key的模糊查询
    public Set<String> gets(String key, String typeNo) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        Set<String> mySet = null;
        String unionKey = getUnionKey(key, typeNo);
        System.out.println("*********************" + unionKey);
        mySet = jedis.hkeys(unionKey + "*");
        System.out.println("**********mySet***********" + mySet.size());
        return mySet;
    }

    public Set<String> gets(String key, Integer pageSize, String typeNo) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        Set<String> mySet = null;
        Set<String> tmpSet = null;
        String unionKey = getUnionKey(key, typeNo);
        tmpSet = jedis.hkeys("*" + unionKey + "*");
        if (mySet.size() < pageSize) {
            mySet = tmpSet;
            return mySet;
        } else {
            for (int i = 0; i < pageSize; i++) {
                mySet.add(tmpSet.iterator().next());
            }
        }
        ;
        return mySet;
    }

    //删除key
    public long del(String key) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        long size = jedis.del(key);
        return size;
    }

    //删除key
    public long del(String key, String typeNo) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        long size = jedis.del(getUnionKey(key, typeNo));
        return size;
    }

    //设置联合主键
    private String getUnionKey(String key, String typeNo) {
        StringBuffer strBuf = new StringBuffer();
        if (typeNo != null && !"".equals(typeNo.trim())) {
            strBuf.append(typeNo).append("_");
        }
        strBuf.append(key);
        return strBuf.toString();
    }

    private String[] list2array(List<String> value) {
        String[] array = (String[]) value.toArray(new String[value.size()]);
        return array;
    }

    public boolean exists(String key) {
        return this.exists(key, "");
    }

    public boolean exists(String key, String typeNo) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        boolean flag = jedis.exists(getUnionKey(key, typeNo));
        return flag;
    }

    public String type(String key) {
        return type(key, "");
    }

    public String type(String key, String typeNo) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        String type = jedis.type(getUnionKey(key, typeNo));
        return type;
    }


    public Long sadd(String key, String... member) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        Long count = jedis.sadd(key, member);
        return count;
    }

    public Set<String> smembers(String key) {
        return this.smembers(key, "");
    }

    public Set<String> smembers(String key, String typeNo) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        Set<String> members = jedis.smembers(getUnionKey(key, typeNo));
        return members;
    }

    /**
     * @param key
     * @param seconds 秒
     * @return
     */
    public Long expire(String key, int seconds) {
        return this.expire(key, "", seconds);
    }

    public Long expire(String key, String typeNo, int seconds) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        Long result = jedis.expire(getUnionKey(key, typeNo), seconds);
        return result;
    }

    public String hget(String hkey, String key) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        String result = jedis.hget(hkey, key);
        return result;
    }

    public long hset(String hkey, String key, String value) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        Long result = jedis.hset(hkey, key, value);
        return result;
    }

    public String hget(String hkey, String typeNo, String key) {
        return this.hget(getUnionKey(hkey, typeNo), key);
    }


    public long hset(String hkey, String typeNo, String key, String value) {
        return this.hset(getUnionKey(hkey, typeNo), key, value);
    }

    public long hdel(String hkey, String key) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        Long result = jedis.hdel(hkey, key);
        return result;
    }

    public long hdel(String hkey, String key, String typeNo) {
        return this.hdel(hkey, getUnionKey(key, typeNo));
    }

    public long setnx(String key, String value) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        //1:成功  2：失败
        Long result = jedis.setnx(key, value);
        return result;
    }

    public long setnx(String key, String typeNo, String value) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        Long result = jedis.setnx(getUnionKey(key, typeNo), value);
        return result;
    }

    public String getSet(String key, String newValue) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        String result = jedis.getSet(key, newValue);
        return result;
    }

    public String getSet(String key, String typeNo, String newValue) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        String result = jedis.getSet(getUnionKey(key, typeNo), newValue);
        return result;
    }

    /**
     * 频次限制
     * 说明：默认有效期60秒,默认次数1次
     *
     * @param key
     * @return
     */
    public boolean rateLimit(String key) {
        return rateLimit(key, 60, 1);
    }

    /**
     * 频次限制
     * 说明：默认次数1次
     *
     * @param key
     * @return
     */
    public boolean rateLimit(String key, int limitTime) {
        return rateLimit(key, limitTime, 1);
    }

    /**
     * 频次限制
     *
     * @param key        关键字
     * @param limitTime  有效期
     * @param limitCount 有效期内的访问次数，默认1次
     * @return
     */
    public boolean rateLimit(String key, int limitTime, Integer limitCount) {
        String redisKey = "10000" + "_" + key;
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        limitCount = (limitCount == null ? 1 : limitCount);
        final Long count = jedis.incr(redisKey);
        if (count == 1) {
            //设置有效期一分钟
            jedis.expire(redisKey, limitTime);
        }
        if (count > limitCount) {
            return false;
        }
        return true;
    }

    /**
     * INCR命令用于由一个递增key的整数值。如果该key不存在，它被设置为0执行操作之前
     *
     * @param key
     * @return
     */
    public Long incr(String key) {
        String redisKey = "10000" + "_" + key;
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        Long count = jedis.incr(redisKey);
        return count;
    }

    public Long incrBy(String key, String typeNo, long number) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        Long count = jedis.incrBy(getUnionKey(key, typeNo), number);
        return count;
    }

    /**
     * 分布式锁
     *
     * @param lockKey 锁的key
     * @return
     */
    public boolean lock(String lockKey, String requestId) {
        return lock(lockKey, requestId, 1000);
    }

    public boolean lock(String lockKey, String requestId, long milliseconds) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        String result = jedis.set(lockKey, requestId, "NX", "PX", milliseconds);
        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    /**
     * 释放分布式锁
     *
     * @return
     */
    public boolean unlock(String lockKey, String requestId) {
        JedisCluster jedis = jedisClusterUtil.getJedisCluster();
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

}
