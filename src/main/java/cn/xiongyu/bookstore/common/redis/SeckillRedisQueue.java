package cn.xiongyu.bookstore.common.redis;

import cn.xiongyu.bookstore.common.cache.RedisCache;
import cn.xiongyu.bookstore.seckill.domain.SuccessSeckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * ClassName: SeckillPush
 * Package: cn.xiongyu.bookstore.common.redis
 * Description:
 * Date: 2020/3/4 下午5:03
 * Author: xiongyu
 */
@Repository
public class SeckillRedisQueue {
    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

    static RedisTemplate<String, Object> redisTemplate;

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public static void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        SeckillRedisQueue.redisTemplate = redisTemplate;
    }

    public void push(int seckillId, int number) {
        logger.info("管理员添加{}号秒杀商品{}件至redis", seckillId, number);
        for (int i = 1; i <= number; i++)
            redisTemplate.opsForList().leftPush("seckill" + seckillId, i);
    }
    public Integer pop(int seckillId) {
        logger.info("开始秒杀{}号商品", seckillId);
        return (Integer) redisTemplate.opsForList().leftPop("seckill" + seckillId);
    }
    public void addSuccess(int seckillId) {
        long state = redisTemplate.opsForList().size("seckill" + seckillId);
        SuccessSeckill successSeckill = new SuccessSeckill(seckillId, (int) state, (int) Thread.currentThread().getId());
        redisTemplate.opsForSet().add("success" + seckillId, successSeckill);
    }
}
