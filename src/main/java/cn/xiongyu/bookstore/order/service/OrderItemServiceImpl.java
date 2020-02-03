package cn.xiongyu.bookstore.order.service;

import cn.xiongyu.bookstore.book.dao.IBookDao;
import cn.xiongyu.bookstore.book.domain.Book;
import cn.xiongyu.bookstore.cart.dao.ICartDao;
import cn.xiongyu.bookstore.cart.domain.Cart;
import cn.xiongyu.bookstore.exception.ExceedMaxRestException;
import cn.xiongyu.bookstore.order.dao.IOrderDao;
import cn.xiongyu.bookstore.order.dao.IOrderItemDao;
import cn.xiongyu.bookstore.order.domain.Order;
import cn.xiongyu.bookstore.order.domain.OrderItem;
import cn.xiongyu.common.beanUtils.MyBeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("orderItemService")
public class OrderItemServiceImpl implements IOrderItemService {

	@Resource(name="ICartDao")
	private ICartDao cartDao;
	@Resource(name="IOrderItemDao")
	private IOrderItemDao orderItemDao;
	@Resource(name="IOrderDao")
	private IOrderDao orderDao;
	@Resource(name="IBookDao")
	private IBookDao bookDao;
	@Transactional
	public Order creatOrderItem(Order order) throws ExceedMaxRestException {
		List<Cart> cartList = cartDao.selCartByUid(order.getUser().getUid());
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		for (Cart cart : cartList) {
			OrderItem orderItem = new OrderItem();
			orderItem.setOid(order.getOid());
			Book book = cart.getBook();
			int num = cart.getNum();
			int rest = book.getRest() - num;
			if(rest < 0){	
				orderDao.delOrder(order);
				throw new ExceedMaxRestException("您提交的订单中有的商品库存不足");
			}
			book.setRest(rest);
			bookDao.modBook(book);
			orderItem.setBook(book);
			orderItem.setIid(MyBeanUtils.getUUID());
			orderItem.setNum(num);
			orderItem.setSubtotal(cart.getSubtotal());
			orderItems.add(orderItem);
			orderItemDao.addOrderItem(orderItem);
		}
		order.setOrderItems(orderItems);
		cartDao.emptyCartByUid(order.getUser().getUid());
		return order;
	}
	@Override
	public List<Order> selOrderItem(List<Order> orderList) {
		for (Order order : orderList) {
			List<OrderItem> orderItems = orderItemDao.selOrderItemByOid(order.getOid());
			order.setOrderItems(orderItems);
		}
		return orderList;
	}

}
