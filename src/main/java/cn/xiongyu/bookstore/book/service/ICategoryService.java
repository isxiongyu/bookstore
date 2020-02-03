package cn.xiongyu.bookstore.book.service;

import cn.xiongyu.bookstore.book.domain.Category;
import cn.xiongyu.bookstore.exception.CategoryRepetitionException;

import java.util.List;

public interface ICategoryService {
	void addCategory(Category category) throws CategoryRepetitionException;
	Category selCategoryById(int cid);
	List<Category> selAllCategory();
	void delCategoryById(int cid);
}
