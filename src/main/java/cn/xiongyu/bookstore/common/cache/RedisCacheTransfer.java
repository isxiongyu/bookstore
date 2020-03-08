package cn.xiongyu.bookstore.common.cache;

import cn.xiongyu.bookstore.common.redis.SeckillRedisQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * ClassName: RedisCacheTransfer
 * Package: cn.xiongyu.bookstore.common.cache
 * Description:
 * Date: 2020/2/12 下午11:07
 * Author: xiongyu
 */
@Component
public class RedisCacheTransfer {
    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
//        System.out.println("执行setJedisConnectionFactory");
        RedisCache.setRedisTemplate(redisTemplate);
        SeckillRedisQueue.setRedisTemplate(redisTemplate);
    }
}
