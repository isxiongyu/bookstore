package cn.xiongyu.bookstore.exception.handler;

import cn.xiongyu.bookstore.cart.domain.Cart;
import cn.xiongyu.bookstore.cart.service.ICartService;
import cn.xiongyu.bookstore.exception.*;
import cn.xiongyu.bookstore.user.domain.User;
import cn.xiongyu.common.beanUtils.MyBeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("user")
@Controller
public class ExceptionController {
	
	
	@Resource(name="cartService")
	private ICartService cartService;
	
	
	@ExceptionHandler(LoginException.class)
	public ModelAndView loginExceptionResolver(Exception ex, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		mv.addObject("loginError", ex);
		User user = MyBeanUtils.toBean(request.getParameterMap(), User.class);
		mv.addObject("user", user);
		mv.setViewName("/jsps/user/login.jsp");
		return mv;
	}
	
	@ExceptionHandler(RegisterException.class)
	public ModelAndView registerExceptionResolver(Exception ex, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		mv.addObject("registerError", ex);
		User user = MyBeanUtils.toBean(request.getParameterMap(), User.class);
		mv.addObject("user", user);
		mv.setViewName("/jsps/user/register.jsp");
		return mv;
	}
	
	@ExceptionHandler(ActiveException.class)
	public String activeExceptionResolver(Exception ex, HttpServletRequest request){
		request.setAttribute("ex", ex);
		return "/jsps/user/msg.jsp";
	}
	
	
	@ExceptionHandler(ExceedMaxRestException.class)
	public ModelAndView exceedMaxRestExceptionResolver(Exception ex, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		String uid = request.getParameter("uid");
		List<Cart> cartList = cartService.selCartByUid(uid);
		mv.addObject("ExceedMaxRestError", ex);
		mv.addObject("cartList", cartList);
		mv.addObject("total", cartService.getTotal(cartList));
		mv.setViewName("/jsps/cart/cart.jsp");
		return mv;
	}
	
	@ExceptionHandler(AccessPermissionException.class)
	public String accessPermissionExceptionResolver(Exception ex, HttpServletRequest request){
		request.setAttribute("ex", ex);
		return "/jsps/user/login.jsp";
	}
	@ExceptionHandler(AdminAccessPermissionException.class)
	public String adminAccessPermissionExceptionResolver(Exception ex, HttpServletRequest request){
		request.setAttribute("ex", ex);
		return "/jsps/adminLogin.jsp";
	}
	@ExceptionHandler(CategoryRepetitionException.class)
	public String categoryRepetitionExceptionResolver(Exception ex, HttpServletRequest request){
		request.setAttribute("ex", ex);
		return "/jsps/admin/addCategory.jsp";
	}
	
}
