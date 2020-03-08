package cn.xiongyu.bookstore.seckill.service.impl;

import cn.xiongyu.bookstore.common.aop.ServiceLimit;
import cn.xiongyu.bookstore.common.redis.SeckillRedisQueue;
import cn.xiongyu.bookstore.seckill.dao.ISeckillDao;
import cn.xiongyu.bookstore.seckill.dao.ISuccessDao;
import cn.xiongyu.bookstore.seckill.domain.SeckillBook;
import cn.xiongyu.bookstore.seckill.domain.SuccessSeckill;
import cn.xiongyu.bookstore.seckill.service.ISeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * ClassName: SeckillServiceImpl
 * Package: cn.xiongyu.bookstore.seckill.service.impl
 * Description:
 * Date: 2020/2/18 下午2:40
 * Author: xiongyu
 */
@Service("seckillService")
@EnableAspectJAutoProxy(exposeProxy = true)
public class SeckillServiceImpl implements ISeckillService {
    Logger logger = LoggerFactory.getLogger(SeckillServiceImpl.class);
    Lock lock = new ReentrantLock();
    @Autowired
    ISeckillDao seckillDao;
    @Autowired
    ISuccessDao successDao;
    @Autowired
    SeckillRedisQueue seckillPush;
    @Transactional
    @Override
    public void seckillUnlock(int seckillId) {
        SeckillBook seckillBook = seckillDao.selBookById(seckillId);
        if (seckillBook == null) {
            logger.info("查询为空-失败");
            return;
        }
        int seckillNumber = seckillBook.getSeckillNumber();
        logger.info("查询库存剩余：" + seckillBook);
        if (seckillNumber > 0) {
            SuccessSeckill successSeckill = new SuccessSeckill(seckillBook.getSeckillId(),  seckillNumber - 1, seckillBook.getVersion() + 1);
            successDao.addSuccess(successSeckill);
            seckillDao.reduceBook(seckillId);
            logger.info("秒杀成功");
        } else {
            logger.info("秒杀失败");
        }
    }

    @Override
    @Transactional
    public int seckillLock(int seckillId) {
        try {
            lock.lock();
            Integer number = seckillDao.selNumById(seckillId);
            if (number == null) {
                logger.info("查询为空-失败");
                return 0;
            }
            logger.info("查询库存剩余：" + number);
            if (number > 0) {
                SuccessSeckill successSeckill = new SuccessSeckill(seckillId,  number - 1, number);
                successDao.addSuccess(successSeckill);
                seckillDao.reduceBook(seckillId);
                logger.info("秒杀成功");
            } else {
                logger.info("秒杀失败");
            }
        } finally {
            lock.unlock();
        }
        return 1;
    }

    @Override
    public void seckillDBO(int seckillId) {
        SeckillBook seckillBook = seckillDao.selBookById(seckillId);
        if (seckillBook.getSeckillNumber() > 0) {
            int count = seckillDao.updateDBO(seckillId, seckillBook.getVersion());
            if (count > 0) {
                SuccessSeckill successSeckill = new SuccessSeckill(seckillId, seckillBook.getSeckillNumber() - 1, seckillBook.getVersion() + 1);
                successDao.addSuccess(successSeckill);
                logger.info("秒杀成功");
            } else {
                logger.info("秒杀失败");
            }
        } else {
            logger.info("库存不足");
        }
    }
    @Override
    public void seckillDBP(int seckillId) {
        int count = seckillDao.updateDBP(seckillId);
        if (count > 0) {
            SuccessSeckill successSeckill = new SuccessSeckill(
                    seckillId, 0, 0);
            successDao.addSuccess(successSeckill);
            logger.info("秒杀成功");
        } else {
            logger.info("库存不足");
        }
    }
    @Override
    public void addSeckillCache(int seckillId, int number) {
        seckillPush.push(seckillId, number);
    }
    @Override
    @ServiceLimit
    public void seckillRedisQueue(int seckillId) {
        lock.lock();
        try {
            Integer val = seckillPush.pop(seckillId);
            if (val != null) {
                logger.info("秒杀成功");
                seckillPush.addSuccess(seckillId);
            } else {
                logger.info("库存不足");
            }
        } finally {
            lock.unlock();
        }
    }
}
