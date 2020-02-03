package cn.xiongyu.bookstore.book.service;

import cn.xiongyu.bookstore.book.dao.ICategoryDao;
import cn.xiongyu.bookstore.book.domain.Category;
import cn.xiongyu.bookstore.exception.CategoryRepetitionException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements ICategoryService {

	@Resource(name="ICategoryDao")
	private ICategoryDao categoryDao;
	@Override
	public void addCategory(Category category) throws CategoryRepetitionException {
		Category getCategory = categoryDao.selCategoryByName(category.getCname());
		if (getCategory != null) {	
			throw new CategoryRepetitionException("该目录已存在，请不要重新添加");
		}
		categoryDao.addCategory(category);
	}
	public List<Category> selAllCategory() {
		return categoryDao.selAllCategory();
	}
	
	@Override
	public Category selCategoryById(int cid) {
		return categoryDao.selCategoryById(cid);
	}
	@Override
	public void delCategoryById(int cid) {
		categoryDao.delCategoryById(cid);
	}

}
