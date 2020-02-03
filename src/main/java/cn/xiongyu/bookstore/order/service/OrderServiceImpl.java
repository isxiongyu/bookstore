package cn.xiongyu.bookstore.order.service;

import cn.xiongyu.bookstore.cart.domain.Cart;
import cn.xiongyu.bookstore.cart.service.ICartService;
import cn.xiongyu.bookstore.order.dao.IOrderDao;
import cn.xiongyu.bookstore.order.domain.Order;
import cn.xiongyu.bookstore.user.dao.IUserDao;
import cn.xiongyu.bookstore.user.domain.User;
import cn.xiongyu.common.beanUtils.MyBeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements IOrderService {

	@Resource(name="cartService")
	private ICartService cartService;
	@Resource(name="IUserDao")
	private IUserDao userDao;
	@Resource(name="IOrderDao")
	private IOrderDao orderDao;
	@Override
	public Order creatOrder(String uid) {
		List<Cart> cartList = cartService.selCartByUid(uid);
		Order order = new Order();
		order.setTotal(cartService.getTotal(cartList));
		User user = userDao.selUserById(uid);
		order.setUser(user);
		order.setOid(MyBeanUtils.getUUID());
		order.setState(1);
		Date orderTime = new Date();
		order.setOrderTime(orderTime);
		orderDao.addOrder(order);
		return order;
	}
	@Override
	public List<Order> selOrderByUid(String uid) {
		return orderDao.selOrderByUid(uid);
	}
	@Override
	public List<Order> selAllOrder() {
		return orderDao.selAllOrder();
	}
	@Override
	public Order selOrderByOid(String oid) {
		return orderDao.selOrderByOid(oid);
	}
	@Override
	public void updateOrderState(Order order) {
		orderDao.modOrder(order);
	}

}
