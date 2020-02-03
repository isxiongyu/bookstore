package cn.xiongyu.bookstore.order.service;

import cn.xiongyu.bookstore.order.domain.Order;

import java.util.List;

public interface IOrderService {
	Order creatOrder(String uid);
	List<Order> selOrderByUid(String uid);
	List<Order> selAllOrder();
	Order selOrderByOid(String oid);
	void updateOrderState(Order order);
}
