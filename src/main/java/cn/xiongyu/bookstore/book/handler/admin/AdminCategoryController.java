package cn.xiongyu.bookstore.book.handler.admin;

import cn.xiongyu.bookstore.admin.domain.Admin;
import cn.xiongyu.bookstore.book.domain.Book;
import cn.xiongyu.bookstore.book.domain.Category;
import cn.xiongyu.bookstore.book.service.IBookService;
import cn.xiongyu.bookstore.book.service.ICategoryService;
import cn.xiongyu.bookstore.exception.AdminAccessPermissionException;
import cn.xiongyu.bookstore.exception.CategoryRepetitionException;
import cn.xiongyu.bookstore.exception.handler.ExceptionController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("admin/category")
@Controller
public class AdminCategoryController extends ExceptionController {

	@Resource(name="categoryService")
	private ICategoryService categoryService;
	@Resource(name="bookService")
	private IBookService bookService;
	
	@RequestMapping("/addCategoryLoad.do")
	public ModelAndView addCategoryLoad(String bid, HttpServletRequest request) throws AdminAccessPermissionException{
		Admin admin = (Admin)request.getSession().getAttribute("admin");
		if(admin == null){
			throw new AdminAccessPermissionException("您还没有登录，或者登录身份已失效");
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("bid", bid);
		mv.setViewName("/jsps/admin/addCategory.jsp");
		return mv;
	}
	@RequestMapping("/addCategoryLoadLeft.do")
	public ModelAndView addCategoryLoadLeft(HttpServletRequest request) throws AdminAccessPermissionException {
		Admin admin = (Admin)request.getSession().getAttribute("admin");
		if(admin == null){
			throw new AdminAccessPermissionException("您还没有登录，或者登录身份已失效");
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/jsps/admin/addCategoryLeft.jsp");
		return mv;
	}
	@RequestMapping("/addCategory.do")
	public ModelAndView addCategory(String bid, Category category, HttpServletRequest request) throws AdminAccessPermissionException, CategoryRepetitionException{
		Admin admin = (Admin)request.getSession().getAttribute("admin");
		if(admin == null){
			throw new AdminAccessPermissionException("您还没有登录，或者登录身份已失效");
		}
		ModelAndView mv = new ModelAndView();
		categoryService.addCategory(category);
		List<Category> categories = categoryService.selAllCategory();
		if(bid == null){
			mv.addObject("msg", "添加成功");
			mv.setViewName("/jsps/admin/right.jsp");
		}else if (bid != "") {			
			Book book = bookService.selBookById(bid);
			mv.addObject("book", book);
			mv.setViewName("/jsps/admin/load.jsp");
		}else{
			mv.setViewName("/jsps/admin/addBook.jsp");			
		}
		mv.addObject("categories", categories);
		return mv;
	}
	@RequestMapping("/delCategory.do")
	public ModelAndView delCategory(int cid, HttpServletRequest request) throws AdminAccessPermissionException, CategoryRepetitionException{
		Admin admin = (Admin)request.getSession().getAttribute("admin");
		if(admin == null){
			throw new AdminAccessPermissionException("您还没有登录，或者登录身份已失效");
		}
		ModelAndView mv = new ModelAndView();
		List<Book> bookList = bookService.selBookByCategory(cid);
		if(bookList.size() != 0){
			mv.addObject("msg", "该目录下有图书，无法删除");
			mv.setViewName("/jsps/admin/right.jsp");
			return mv;
		}
		categoryService.delCategoryById(cid);
		mv.addObject("msg", "删除成功");
		mv.setViewName("/jsps/admin/right.jsp");
		return mv;
	}
}
