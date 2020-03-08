package cn.xiongyu.bookstore.seckill.dao;

import cn.xiongyu.bookstore.seckill.domain.SeckillBook;
import cn.xiongyu.bookstore.seckill.domain.SuccessSeckill;

/**
 * ClassName: ISeckillDao
 * Package: cn.xiongyu.bookstore.seckill.dao
 * Description:
 * Date: 2020/2/18 下午2:43
 * Author: xiongyu
 */
public interface ISuccessDao {
    void addSuccess(SuccessSeckill successSeckill);
}
