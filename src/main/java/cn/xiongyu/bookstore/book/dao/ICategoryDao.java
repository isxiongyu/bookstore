package cn.xiongyu.bookstore.book.dao;

import cn.xiongyu.bookstore.book.domain.Category;

import java.util.List;

public interface ICategoryDao {
	void addCategory(Category category);
	void delCategoryById(int cid);
	void modCategory(Category category);
	Category selCategoryById(int cid);
	Category selCategoryByName(String cname);
	List<Category> selAllCategory();
}
