package cn.xiongyu.bookstore.cache;

import org.apache.ibatis.cache.Cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ClassName: RedisCache
 * Package: cn.xiongyu.bookstore.cache
 * Description:
 * Date: 2020/2/12 下午10:23
 * Author: xiongyu
 */
public class RedisCache implements Cache //实现类
{
    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

    private static RedisTemplate<String,Object> redisTemplate;

    private final String id;

    /**
     * The {@code ReadWriteLock}.
     */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Override
    public ReadWriteLock getReadWriteLock()
    {
        return this.readWriteLock;
    }

    public static void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisCache.redisTemplate = redisTemplate;
    }

    public RedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        logger.debug("MybatisRedisCache:id=" + id);
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        try{
            if(null!=value) {
                logger.info("将数据存入缓存: " + value);
                redisTemplate.opsForValue().set(key.toString(),value,60 * 60, TimeUnit.SECONDS);//控制存放时间60s
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("redis保存数据异常！");
        }
    }

    @Override
    public Object getObject(Object key) {
        try{
            if(null!=key) {
                Object o = redisTemplate.opsForValue().get(key.toString());
                logger.info("从缓存中获取数据: " + o);
                return o;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("redis获取数据异常！");
        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        try{
            if(null!=key)
                return redisTemplate.expire(key.toString(),1,TimeUnit.DAYS);//设置过期时间
        }catch (Exception e){
            e.printStackTrace();
            logger.error("redis获取数据异常！");
        }
        return null;
    }

    @Override
    public void clear() {
        Long size=redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                Long size = redisConnection.dbSize();
                //连接清除数据
                redisConnection.flushDb();
                redisConnection.flushAll();
                return size;
            }
        });
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>clear: 清除了" + size + "个对象");
    }

    @Override
    public int getSize() {
        Long size = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.dbSize();
            }
        });
        return size.intValue();
    }
}
