package cn.xiongyu.bookstore.book.service;

import cn.xiongyu.bookstore.book.dao.IBookDao;
import cn.xiongyu.bookstore.book.dao.ICategoryDao;
import cn.xiongyu.bookstore.book.domain.Book;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("bookService")
public class BookServiceImpl implements IBookService {
	
	@Resource(name="IBookDao")
	private IBookDao bookDao;
	
	@Resource(name="ICategoryDao")
	private ICategoryDao categoryDao;
	
	public List<Book> selAllBook() {
		return bookDao.selAllBook();
	}
	
	@Override
	public Book selBookById(String bid) {
		return bookDao.selBookById(bid);
	}

	@Override
	public void delBookById(String bid) {
		bookDao.delBookById(bid);
	}

	@Override
	public void modBook(Book book) {
		bookDao.modBook(book);
	}

	@Override
	public void addBook(Book book) {
		bookDao.addBook(book);
	}
	@Override
	public List<Book> selBookByCategory(int cid) {
		return bookDao.selBookByCategory(cid);
	}

}
