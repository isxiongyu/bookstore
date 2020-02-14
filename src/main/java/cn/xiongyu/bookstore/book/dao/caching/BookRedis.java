package cn.xiongyu.bookstore.book.dao.caching;

import cn.xiongyu.bookstore.admin.domain.Admin;
import cn.xiongyu.bookstore.book.domain.Book;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.swing.text.html.HTMLEditorKit;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: BookRedis
 * Package: cn.xiongyu.bookstore.book.dao.caching
 * Description:
 * Date: 2020/2/12 下午7:47
 * Author: xiongyu
 */
@Component
public class BookRedis {
    private String host = "127.0.0.1";
    private int port = 6379;
    private String password = "123";
    private JedisPool jedisPool;
    private RuntimeSchema<Book> schema = RuntimeSchema.createFrom(Book.class);
    private RuntimeSchema<ArrayList> schemaList = RuntimeSchema.createFrom(ArrayList.class);
    public BookRedis() {
        jedisPool = new JedisPool(new JedisPoolConfig(), host, port, 6000,password);
    }
    public void putBook(Book book) {
        Jedis jedis = jedisPool.getResource();
        try {
            String key = "book" + book.getBid();
            byte[] bytes = ProtostuffIOUtil.toByteArray(book, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
            int time = 60 * 60;
            jedis.setex(key.getBytes(), time, bytes);
        } finally {
            jedis.close();
        }
    }

    public Book getBook(String bid) {
        Jedis jedis = jedisPool.getResource();
        try {
            String key = "book" + bid;
            byte[] bytes = jedis.get(key.getBytes());
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
    public void removeBook(String bid) {
        Jedis jedis = jedisPool.getResource();
        try {
            String key = "book" + bid;
            jedis.del(key);
        } finally {
            jedis.close();
        }
    }
//    public void putBookList(List<Book> allBook) {
//        Jedis jedis = jedisPool.getResource();
//        try {
//            String key = "allBook";
//            byte[] bytes = ProtostuffIOUtil.toByteArray(allBook, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
//            int time = 60 * 60;
//            jedis.setex(key.getBytes(), time, bytes);
//        } finally {
//            jedis.close();
//        }
//    }
//
//    public Book getBook(String bid) {
//        Jedis jedis = jedisPool.getResource();
//        try {
//            String key = "book" + bid;
//            byte[] bytes = jedis.get(key.getBytes());
//            if (bytes != null){
//                Book book = schema.newMessage();
//                ProtostuffIOUtil.mergeFrom(bytes, book, schema);
//                return book;
//            }
//        } finally {
//            jedis.close();
//        }
//        return null;
//    }
}
