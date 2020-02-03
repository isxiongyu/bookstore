package cn.xiongyu.bookstore.book.dao;

import cn.xiongyu.bookstore.book.domain.Book;

import java.util.List;

public interface IBookDao {
	void addBook(Book book);
	void delBookById(String bid);
	void modBook(Book book);
	Book selBookById(String bid);
	List<Book> selBookByCategory(int cid);
	List<Book> selAllBook();
}
