package cn.xiongyu.bookstore.exception.handler;

import cn.xiongyu.bookstore.cart.domain.Cart;
import cn.xiongyu.bookstore.cart.service.ICartService;
import cn.xiongyu.bookstore.exception.*;
import cn.xiongyu.bookstore.user.domain.User;
import cn.xiongyu.common.beanUtils.MyBeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RequestMapping("user")
@ControllerAdvice
public class ExceptionController {

	
	@Resource(name="cartService")
	private ICartService cartService;
	
	
	@ExceptionHandler(LoginException.class)
	public ModelAndView loginExceptionResolver(LoginException ex, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		mv.addObject("loginError", ex);
		User user = MyBeanUtils.toBean(request.getParameterMap(), User.class);
		mv.addObject("user", user);
		mv.setViewName("/jsps/user/login.jsp");
		return mv;
	}
	
	@ExceptionHandler(RegisterException.class)
	public ModelAndView registerExceptionResolver(RegisterException ex, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		mv.addObject("registerError", ex);
		User user = MyBeanUtils.toBean(request.getParameterMap(), User.class);
		mv.addObject("user", user);
		mv.setViewName("/jsps/user/register.jsp");
		return mv;
	}
	
	@ExceptionHandler(ActiveException.class)
	public String activeExceptionResolver(ActiveException ex, HttpServletRequest request){
		request.setAttribute("ex", ex);
		return "/jsps/user/msg.jsp";
	}
	
	
	@ExceptionHandler(ExceedMaxRestException.class)
	public ModelAndView exceedMaxRestExceptionResolver(ExceedMaxRestException ex, HttpServletRequest request){
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
	public String accessPermissionExceptionResolver(AccessPermissionException ex, HttpServletRequest request){
		request.setAttribute("ex", ex);
		return "/jsps/user/login.jsp";
	}
	@ExceptionHandler(AdminAccessPermissionException.class)
	public String adminAccessPermissionExceptionResolver(AdminAccessPermissionException ex, HttpServletRequest request){
		System.out.println("密码错误");
		request.setAttribute("ex", ex);
		return "/jsps/adminLogin.jsp";
	}
	@ExceptionHandler(CategoryRepetitionException.class)
	public String categoryRepetitionExceptionResolver(CategoryRepetitionException ex, HttpServletRequest request){
		request.setAttribute("ex", ex);
		return "/jsps/admin/addCategory.jsp";
	}
	@ExceptionHandler(LimitException.class)
	@ResponseBody
	public Map<String, Integer> limitExceptionResolver(LimitException ex) {
		Map<String, Integer> map = new HashMap<>();
		System.out.println(ex.getMessage());
		map.put(ex.getMessage(), 500);
		return map;
	}
}
