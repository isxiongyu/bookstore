package cn.xiongyu.bookstore.seckill.service;

/**
 * ClassName: ISeckillService
 * Package: cn.xiongyu.bookstore.seckill.service
 * Description:
 * Date: 2020/2/18 下午2:39
 * Author: xiongyu
 */
public interface ISeckillService {
    void seckillUnlock(int seckillId);
    int seckillLock(int seckillId);
    void seckillDBO(int seckillId);
    void seckillDBP(int seckillId);
    void addSeckillCache(int seckillIdm, int number);
    void seckillRedisQueue(int seckillId);
}
