package cn.xiongyu.bookstore.book.service;

import cn.xiongyu.bookstore.book.domain.Book;

import java.util.List;

public interface IBookService {
	List<Book> selAllBook();
	
	List<Book> selBookByCategory(int cid);
	Book selBookById(String bid);
	void delBookById(String bid);
	void modBook(Book book);
	void addBook(Book book);

}
