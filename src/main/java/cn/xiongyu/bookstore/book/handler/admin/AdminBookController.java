package cn.xiongyu.bookstore.book.handler.admin;

import cn.xiongyu.bookstore.admin.domain.Admin;
import cn.xiongyu.bookstore.book.domain.Book;
import cn.xiongyu.bookstore.book.domain.Category;
import cn.xiongyu.bookstore.book.service.IBookService;
import cn.xiongyu.bookstore.book.service.ICategoryService;
import cn.xiongyu.bookstore.exception.AdminAccessPermissionException;
import cn.xiongyu.common.beanUtils.MyBeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@RequestMapping("admin/book")
@Controller
public class AdminBookController {
	
	@Resource(name="bookService")
	private IBookService bookService;
	@Resource(name="categoryService")
	private ICategoryService categoryService;
	
	@RequestMapping("/selAllBookAdmin.do")
	public ModelAndView selAllBookAdmin(){
		List<Book> bookList = bookService.selAllBook();
		ModelAndView mv = new ModelAndView();
		mv.addObject("bookList", bookList);
		mv.setViewName("/jsps/admin/right.jsp");
		return mv;
	}
	@RequestMapping("/selAllCategoryAdmin.do")
	public ModelAndView selAllCategoryAdmin(){
		List<Category> categoryList = categoryService.selAllCategory();
		ModelAndView mv = new ModelAndView();
		mv.addObject("categoryList", categoryList);
		mv.setViewName("/jsps/admin/left.jsp");
		return mv;
	}
	@RequestMapping("/selBookAdmin.do")
	public ModelAndView selBookAdmin(int cid){
		List<Book> bookList = bookService.selBookByCategory(cid);
		ModelAndView mv = new ModelAndView();
		mv.addObject("bookList", bookList);
		mv.addObject("cid", cid);
		mv.setViewName("/jsps/admin/right.jsp");
		return mv;
	}
	@RequestMapping("/loadAdmin.do")
	public ModelAndView loadAdmin(String bid, HttpServletRequest request) throws AdminAccessPermissionException{
		Admin admin = (Admin)request.getSession().getAttribute("admin");
		if(admin == null){
			throw new AdminAccessPermissionException("您还没有登录，或者登录身份已失效");
		}
		Book book = bookService.selBookById(bid);
		List<Category> categories = categoryService.selAllCategory();
		ModelAndView mv = new ModelAndView();
		mv.addObject("book", book);
		mv.addObject("categories", categories);
		mv.setViewName("/jsps/admin/load.jsp");
		return mv;
	}
	@RequestMapping("/del.do")
	public ModelAndView del(String bid, String cid, HttpServletRequest request) throws AdminAccessPermissionException{
		Admin admin = (Admin)request.getSession().getAttribute("admin");
		if(admin == null){
			throw new AdminAccessPermissionException("您还没有登录，或者登录身份已失效");
		}
		List<Book> bookList;
		bookService.delBookById(bid);
		if (cid == null || cid == "") {
			bookList = bookService.selAllBook();
		}else{			
			bookList = bookService.selBookByCategory(Integer.parseInt(cid));
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("bookList", bookList);
		mv.setViewName("/jsps/admin/right.jsp");
		return mv;
	}
	@RequestMapping("/mod.do")
	public ModelAndView mod(int cid, MultipartFile img, HttpServletRequest request) throws IllegalStateException, IOException, AdminAccessPermissionException{
		Admin admin = (Admin)request.getSession().getAttribute("admin");
		if(admin == null){
			throw new AdminAccessPermissionException("您还没有登录，或者登录身份已失效");
		}
		ModelAndView mv = new ModelAndView();
		Book book = MyBeanUtils.toBean(request.getParameterMap(), Book.class);
		List<Category> categories = categoryService.selAllCategory();
		Category category = categoryService.selCategoryById(cid);
		book.setCategory(category);
		mv.addObject("book", book);
		mv.addObject("categories", categories);
		mv.setViewName("/jsps/admin/load.jsp");
		if(book.getBname() == ""){
			mv.addObject("nameError", "请输入书名");
			return mv;
		}else if(book.getBprice() == 0){
			mv.addObject("priceError", "请输入价格");
			return mv;
		}else if(book.getAuthor() == "") {
			mv.addObject("authorError", "请输入作者");
			return mv;
		}else if(book.getRest() == 0) {
			mv.addObject("restError", "请输入库存");
			return mv;
			
		}
		//文件上传
		if (img.getSize() > 0) {			
			Properties props = new Properties();
			props.load(this.getClass().getClassLoader().getResourceAsStream("resources/upload_load.properties"));
			String url = props.getProperty("upload.url");
			File file = new File(url, book.getBid() + ".jpg");
			img.transferTo(file);
		}
		String imgUrl = book.getBid() + ".jpg";
		book.setImg(imgUrl);
		bookService.modBook(book);
		mv.addObject("msg", "修改成功！");
		
		return mv;
	}
	@RequestMapping("/addLoad.do")
	public ModelAndView addLoad(String cid, HttpServletRequest request) throws AdminAccessPermissionException{
		Admin admin = (Admin)request.getSession().getAttribute("admin");
		if(admin == null){
			throw new AdminAccessPermissionException("您还没有登录，或者登录身份已失效");
		}
		ModelAndView mv = new ModelAndView();
		List<Category> categories = categoryService.selAllCategory();;
		if(!(cid == null || cid.equals(""))){
			Category category = categoryService.selCategoryById(Integer.parseInt(cid));
			mv.addObject("category", category);
		}
		mv.addObject("categories", categories);
		mv.setViewName("/jsps/admin/addBook.jsp");
		return mv;
	}
	@RequestMapping("/add.do")
	public ModelAndView add(int cid, MultipartFile img, HttpServletRequest request) throws IOException, AdminAccessPermissionException{
		Admin admin = (Admin)request.getSession().getAttribute("admin");
		if(admin == null){
			throw new AdminAccessPermissionException("您还没有登录，或者登录身份已失效");
		}
		ModelAndView mv = new ModelAndView();
		Book book = MyBeanUtils.toBean(request.getParameterMap(), Book.class);
		List<Category> categories = categoryService.selAllCategory();
		Category category = categoryService.selCategoryById(cid);
		book.setCategory(category);
		book.setBid(MyBeanUtils.getUUID());
		mv.addObject("book", book);
		mv.addObject("categories", categories);
		mv.addObject("category", category);
		mv.setViewName("/jsps/admin/addBook.jsp");
		if(book.getBname() == ""){
			mv.addObject("nameError", "请输入书名");
			return mv;
		}else if(book.getBprice() == 0){
			mv.addObject("priceError", "请输入价格");
			return mv;
		}else if(book.getAuthor() == "") {
			mv.addObject("authorError", "请输入作者");
			return mv;
		}else if(book.getRest() == 0) {
			mv.addObject("restError", "请输入库存");
			return mv;
			
		}
		//文件上传
		if (img.getSize() > 0) {			
			Properties props = new Properties();
			props.load(this.getClass().getClassLoader().getResourceAsStream("resources/upload_load.properties"));
			String url = props.getProperty("upload.url");
			String imgUrl = book.getBid() + ".jpg";
			File file = new File(url, book.getBid() + ".jpg");
			img.transferTo(file);
			book.setImg(imgUrl);
		}else {
			mv.addObject("imgError", "请添加图片");
			return mv;
		}
		bookService.addBook(book);
		mv.addObject("msg", "添加成功！");
		return mv;
	}
}
