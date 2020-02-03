package cn.xiongyu.bookstore.cart.handler;

import cn.xiongyu.bookstore.book.domain.Book;
import cn.xiongyu.bookstore.book.service.IBookService;
import cn.xiongyu.bookstore.cart.domain.Cart;
import cn.xiongyu.bookstore.cart.service.ICartService;
import cn.xiongyu.bookstore.exception.AccessPermissionException;
import cn.xiongyu.bookstore.exception.ExceedMaxRestException;
import cn.xiongyu.bookstore.exception.handler.ExceptionController;
import cn.xiongyu.bookstore.user.domain.User;
import cn.xiongyu.bookstore.user.service.IUserService;
import cn.xiongyu.common.beanUtils.MyBeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("cart")
@Controller
public class CartController extends ExceptionController{
	
	@Resource(name="cartService")
	private ICartService cartService;
	@Resource(name="bookService")
	private IBookService bookService;
	@Resource(name="userService")
	private IUserService userService;
	
	
	@RequestMapping("/myCart.do") 
	public ModelAndView myCart(String uid, HttpServletRequest request) throws ExceedMaxRestException, AccessPermissionException{
		ModelAndView mv = new ModelAndView();
		User user = (User) request.getSession().getAttribute("user");
		if(user == null){
			throw new AccessPermissionException("你还没有登录或者登录身份已失效，请重新登录");
		}
		List<Cart> cartList = cartService.selCartByUid(uid);
		mv.addObject("cartList", cartList);
		mv.addObject("total", cartService.getTotal(cartList));
		mv.setViewName("/jsps/cart/cart.jsp");
		return mv;
	}
	@RequestMapping("/addCart.do") 
	public ModelAndView addCart(String bid, String uid, int num, HttpServletRequest request) throws AccessPermissionException{
		User user = (User) request.getSession().getAttribute("user");
		if(user == null){
			throw new AccessPermissionException("你还没有登录或者登录身份已失效，请重新登录");
		}
		ModelAndView mv = new ModelAndView();
		Cart cart = new Cart();
		cart.setNum(num);
		cart.setCartId(MyBeanUtils.getUUID());
		Book book = bookService.selBookById(bid);
		cart.setBook(book);
		cart.setUser(user);
		cartService.addCart(cart);
		List<Cart> cartList = cartService.selCartByUid(uid);
		mv.addObject("cartList", cartList);
		mv.addObject("total", cartService.getTotal(cartList));
		mv.setViewName("/jsps/cart/cart.jsp");
		return mv;
	}
	@RequestMapping("/emptyCart.do") 
	public String emptyCart(HttpServletRequest request) throws ExceedMaxRestException, AccessPermissionException{
		User user = (User) request.getSession().getAttribute("user");
		if(user == null){
			throw new AccessPermissionException("你还没有登录或者登录身份已失效，请重新登录");
		}
		cartService.emptyCartByUid(user.getUid());
		return "/jsps/cart/cart.jsp";
	}
	@RequestMapping("/delCart.do") 
	public ModelAndView delCart(HttpServletRequest request, String cartId) throws ExceedMaxRestException, AccessPermissionException{
		ModelAndView mv = new ModelAndView(); 
		User user = (User) request.getSession().getAttribute("user");
		if(user == null){
			throw new AccessPermissionException("你还没有登录或者登录身份已失效，请重新登录");
		}
		List<Cart> cartList = cartService.delCartByCartId(cartId, user.getUid());
		mv.addObject("cartList", cartList);
		mv.addObject("total", cartService.getTotal(cartList));
		mv.setViewName("/jsps/cart/cart.jsp");
		return mv;
	}
	
}
