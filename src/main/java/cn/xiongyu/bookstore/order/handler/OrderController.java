package cn.xiongyu.bookstore.order.handler;


import cn.xiongyu.bookstore.exception.AccessPermissionException;
import cn.xiongyu.bookstore.exception.AdminAccessPermissionException;
import cn.xiongyu.bookstore.exception.ExceedMaxRestException;
import cn.xiongyu.bookstore.exception.handler.ExceptionController;
import cn.xiongyu.bookstore.order.domain.Order;
import cn.xiongyu.bookstore.order.service.IOrderItemService;
import cn.xiongyu.bookstore.order.service.IOrderService;
import cn.xiongyu.bookstore.user.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import test.PaymentUtil;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@RequestMapping("order")
@Controller
public class OrderController extends ExceptionController{
	
	@Resource(name="orderItemService")
	private IOrderItemService orderItemService;
	@Resource(name="orderService")
	private IOrderService orderService;
	
	@RequestMapping("/addOrder.do")
	public ModelAndView addOrder(String uid, HttpServletRequest request) throws AccessPermissionException, ExceedMaxRestException {
		User user = (User) request.getSession().getAttribute("user");
		if(user == null){
			throw new AccessPermissionException("你还没有登录或者登录身份已失效，请重新登录");
		}
		ModelAndView mv = new ModelAndView();
		Order order = orderService.creatOrder(uid);
		order = orderItemService.creatOrderItem(order);
		mv.addObject("order", order);
		mv.setViewName("/jsps/pay/pay.jsp");
		return mv;
	}
	@RequestMapping("/myOrder.do")
	public ModelAndView myOrder(HttpServletRequest request) throws AccessPermissionException{
		User user = (User) request.getSession().getAttribute("user");
		if(user == null){
			throw new AccessPermissionException("你还没有登录或者登录身份已失效，请重新登录");
		}
		ModelAndView mv = new ModelAndView();
		List<Order> orderList = orderService.selOrderByUid(user.getUid());	
		if (orderList == null) {
			mv.setViewName("/jsps/right.jsp");
			return mv;
		}
		orderList = orderItemService.selOrderItem(orderList);
		mv.addObject("orderList", orderList);
		mv.setViewName("/jsps/order/order.jsp");
		return mv;
	}
	
	@RequestMapping("/pay.do")
	public String pay(String oid, HttpServletRequest request, HttpServletResponse response) throws IOException{
		//获取用户信息
		String url = "https://www.yeepay.com/app-merchant-proxy/node?";
		//加载配置文件
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("yibao.properties"));
		//准备12+1个参数
		String p0_Cmd = "Buy";
		String p1_MerId = props.getProperty("p1_MerId");//易宝账号
		String p2_Order = oid; //订单编号
		String p3_Amt = "0.01";//支付金额
		String p4_Cur = "CNY"; //支付币种
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		//回馈url
		String p8_Url = "http://localhost:80/bookstore/order/back.do";
		String p9_SAF = "1";
		String pa_MP = "";
		String pd_FrpId = "PSBC-NET";//选择支付银行为交通银行
		String pr_NeedResponse = "1";
		String keyValue = props.getProperty("keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
		url = url+"p0_Cmd="+p0_Cmd+"&p1_MerId="+p1_MerId+"&p2_Order="+p2_Order+
				"&p3_Amt="+p3_Amt+"&p4_Cur="+p4_Cur+"&p5_Pid="+p5_Pid+
				"&p6_Pcat="+p6_Pcat+"&p7_Pdesc="+p7_Pdesc+"&p8_Url="+p8_Url+
				"&p9_SAF="+p9_SAF+"&pa_MP="+pa_MP+"&pd_FrpId="+pd_FrpId+
				"&pr_NeedResponse="+pr_NeedResponse+"&hmac="+hmac;
		return "redirect:"+url;
	}
	//易宝回馈，确认收款
	@RequestMapping("/back.do")
	public String back(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//易宝传递的获取11+1个参数
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String hmac = request.getParameter("hmac");
		//加载配置文件获取密钥keyValue
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("yibao.properties"));
		String keyValue = props.getProperty("keyValue");
		//校验访问者是否为易宝，也就是校验参数中的hmac与获取的11个参数和密钥生成的hmac是否相同
		boolean bool = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);
		if(! bool){//校验失败
			request.setAttribute("msg", "您不是什么好东西");
		}
		Order order = orderService.selOrderByOid(r6_Order);
		order.setState(2);
		orderService.updateOrderState(order);
		/*
		 * 判断回调方式，若为1则是：引导客户端重定向方式，
		 * 若为2，则为服务器点对点通讯，这种方式需要向易宝反馈success
		 * */
		if(r9_BType.equals("2")){
			response.getWriter().write("success");
		}
		request.setAttribute("msg", "支付成功");
		return "f:/jsps/order/msg.jsp";
	}
	@RequestMapping("/updateOrderState.do")
	public ModelAndView updateOrderState(String oid, int state,HttpServletRequest request) throws AccessPermissionException, AdminAccessPermissionException{
		User user = (User)request.getSession().getAttribute("user");
		if(user == null){
			throw new AccessPermissionException("您还没有登录，或者登录身份已失效");
		}
		ModelAndView mv = new ModelAndView();
		Order order = orderService.selOrderByOid(oid);
		if (order != null) {
			order.setState(state);
		}
		orderService.updateOrderState(order);
		//查询所有order
		List<Order> OrderList = orderService.selAllOrder();
		if (OrderList == null) {
			mv.setViewName("/jsps/right.jsp");
			return mv;
		}
		OrderList = orderItemService.selOrderItem(OrderList);
		mv.addObject("orderList", OrderList);
		mv.setViewName("/jsps/order/order.jsp");
		return mv;
	}
}
