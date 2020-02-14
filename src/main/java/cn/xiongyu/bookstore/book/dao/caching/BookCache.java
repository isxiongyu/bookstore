package cn.xiongyu.bookstore.book.dao.caching;

import cn.xiongyu.bookstore.book.domain.Book;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.apache.ibatis.cache.Cache;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * ClassName: BookCache
 * Package: cn.xiongyu.bookstore.book.dao.caching
 * Description:
 * Date: 2020/2/12 下午9:22
 * Author: xiongyu
 */
public class BookCache implements Cache {

    private String host = "127.0.0.1";
    private int port = 6379;
    private String password = "123";
    private JedisPool jedisPool;
    private RuntimeSchema<Book> schema = RuntimeSchema.createFrom(Book.class);

    public BookCache() {
        jedisPool = new JedisPool(new JedisPoolConfig(), host, port, 6000,password);
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void putObject(Object key, Object value) {
        Jedis jedis = jedisPool.getResource();
        try {
            byte[] bytes = ProtostuffIOUtil.toByteArray((Book) value, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
            int time = 60 * 60;
            jedis.setex(((String) key).getBytes(), time, bytes);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Object getObject(Object key) {
        Jedis jedis = jedisPool.getResource();
        try {
            byte[] bytes = jedis.get(((String) key).getBytes());
            if (bytes != null){
                Book book = schema.newMessage();
                ProtostuffIOUtil.mergeFrom(bytes, book, schema);
                return book;
            }
        } finally {
            jedis.close();
        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.del(((String) key).getBytes());
            return true;
        } finally {
            jedis.close();
        }
//        return true;
    }

    @Override
    public void clear() {

    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
