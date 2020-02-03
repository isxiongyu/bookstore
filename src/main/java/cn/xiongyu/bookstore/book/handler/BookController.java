package cn.xiongyu.bookstore.book.handler;

import cn.xiongyu.bookstore.book.domain.Book;
import cn.xiongyu.bookstore.book.domain.Category;
import cn.xiongyu.bookstore.book.service.IBookService;
import cn.xiongyu.bookstore.book.service.ICategoryService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("book")
@Controller
public class BookController {
	@Resource(name="bookService")
	private IBookService bookService;
	@Resource(name="categoryService")
	private ICategoryService categoryService;
	@RequestMapping("/selAllBook.do")
	public ModelAndView selAllBook(){
		List<Book> bookList = bookService.selAllBook();
		ModelAndView mv = new ModelAndView();
		mv.addObject("bookList", bookList);
		mv.setViewName("/jsps/right.jsp");
		return mv;
	}
	
	@RequestMapping("/selAllCategory.do")
	public ModelAndView selAllCategory(){
		List<Category> categoryList = categoryService.selAllCategory();
		ModelAndView mv = new ModelAndView();
		mv.addObject("categoryList", categoryList);
		mv.setViewName("/jsps/left.jsp");
		return mv;
	}
	
	@RequestMapping("/selBook.do")
	public ModelAndView selBook(int cid){
		List<Book> bookList = bookService.selBookByCategory(cid);
		ModelAndView mv = new ModelAndView();
		mv.addObject("bookList", bookList);
		mv.setViewName("/jsps/right.jsp");
		return mv;
	}
	
	@RequestMapping("/detail.do")
	public ModelAndView detail(String bid){
		Book book = bookService.selBookById(bid);
		ModelAndView mv = new ModelAndView();
		mv.addObject("book", book);
		mv.setViewName("/jsps/book/detail.jsp");
		return mv;
	}
	@RequestMapping("/judgeRest.do")
	public void judgeRest(String bid, int num, 
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html;character=utf-8");
		Book book = bookService.selBookById(bid);
		//为零说明可以添加购物车
		int restState = 0;
		Map<String, Integer> map = new HashMap<String, Integer>();		
		if(book.getRest() < num){
			restState = 1;
		}
		map.put("restState", restState);
		JSONObject jsonObject = JSONObject.fromObject(map);
		String jsonState = jsonObject.toString();
		response.getWriter().write(jsonState);
	}
	

}
