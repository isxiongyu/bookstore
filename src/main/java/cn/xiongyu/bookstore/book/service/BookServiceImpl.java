package cn.xiongyu.bookstore.book.service;

import cn.xiongyu.bookstore.book.dao.IBookDao;
import cn.xiongyu.bookstore.book.dao.ICategoryDao;
import cn.xiongyu.bookstore.book.dao.caching.BookRedis;
import cn.xiongyu.bookstore.book.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("bookService")
public class BookServiceImpl implements IBookService {
	
	@Resource(name="IBookDao")
	private IBookDao bookDao;
	
	@Resource(name="ICategoryDao")
	private ICategoryDao categoryDao;

	@Autowired
	private BookRedis bookRedis;
	
	public List<Book> selAllBook() {
		return bookDao.selAllBook();
	}
	
	@Override
	public Book selBookById(String bid) {
//		Book book = bookRedis.getBook(bid);
//		if (book == null) {
//			System.out.println("缓存失效");
//			book = bookDao.selBookById(bid);
//			bookRedis.putBook(book);
//		}
		return bookDao.selBookById(bid);
	}

	@Override
	public void delBookById(String bid) {
		bookRedis.removeBook(bid);
		bookDao.delBookById(bid);
	}

	@Override
	public void modBook(Book book) {
		bookDao.modBook(book);
		bookRedis.putBook(book);
	}

	@Override
	public void addBook(Book book) {
		bookDao.addBook(book);
		bookRedis.putBook(book);
	}
	@Override
	public List<Book> selBookByCategory(int cid) {
		return bookDao.selBookByCategory(cid);
	}

}
