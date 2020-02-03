package cn.xiongyu.bookstore.order.handler.admin;

import cn.xiongyu.bookstore.admin.domain.Admin;
import cn.xiongyu.bookstore.exception.AccessPermissionException;
import cn.xiongyu.bookstore.exception.AdminAccessPermissionException;
import cn.xiongyu.bookstore.order.domain.Order;
import cn.xiongyu.bookstore.order.service.IOrderItemService;
import cn.xiongyu.bookstore.order.service.IOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("admin/order")
@Controller
public class AdminOrderController {

	
	@Resource(name="orderItemService")
	private IOrderItemService orderItemService;
	@Resource(name="orderService")
	private IOrderService orderService;
	
	@RequestMapping("/allOrder.do")
	public ModelAndView allOrder(HttpServletRequest request) throws AccessPermissionException, AdminAccessPermissionException{
		Admin admin = (Admin)request.getSession().getAttribute("admin");
		if(admin == null){
			throw new AdminAccessPermissionException("您还没有登录，或者登录身份已失效");
		}
		ModelAndView mv = new ModelAndView();
		List<Order> allOrderList = orderService.selAllOrder();
		if (allOrderList == null) {
			mv.setViewName("/jsps/admin/right.jsp");
			return mv;
		}
		allOrderList = orderItemService.selOrderItem(allOrderList);
		mv.addObject("allOrderList", allOrderList);
		mv.setViewName("/jsps/admin/allOrder.jsp");
		return mv;
	}
	@RequestMapping("/updateOrderState.do")
	public ModelAndView updateOrderState(String oid, int state,HttpServletRequest request) throws AccessPermissionException, AdminAccessPermissionException{
		Admin admin = (Admin)request.getSession().getAttribute("admin");
		if(admin == null){
			throw new AdminAccessPermissionException("您还没有登录，或者登录身份已失效");
		}
		ModelAndView mv = new ModelAndView();
		Order order = orderService.selOrderByOid(oid);
		if (order != null) {
			order.setState(state);
		}
		orderService.updateOrderState(order);
		//查询所有order
		List<Order> allOrderList = orderService.selAllOrder();
		if (allOrderList == null) {
			mv.setViewName("/jsps/admin/right.jsp");
			return mv;
		}
		allOrderList = orderItemService.selOrderItem(allOrderList);
		mv.addObject("allOrderList", allOrderList);
		mv.setViewName("/jsps/admin/allOrder.jsp");
		return mv;
	}
}
