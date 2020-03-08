package cn.xiongyu.bookstore.seckill.dao;

import cn.xiongyu.bookstore.book.domain.Book;
import cn.xiongyu.bookstore.seckill.domain.SeckillBook;

import java.util.List;

/**
 * ClassName: ISeckillDao
 * Package: cn.xiongyu.bookstore.seckill.dao
 * Description:
 * Date: 2020/2/18 下午2:43
 * Author: xiongyu
 */
public interface ISeckillDao {
    void reduceBook(int seckillId);
    SeckillBook selBookById(int seckillId);
    int selNumById(int seckillId);
    int updateDBO(int seckillId, int version);
    int updateDBP(int seckillId);
}
