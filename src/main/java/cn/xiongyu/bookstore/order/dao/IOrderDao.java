package cn.xiongyu.bookstore.order.dao;

import cn.xiongyu.bookstore.order.domain.Order;

import java.util.List;

public interface IOrderDao {
	void addOrder(Order order);
	List<Order> selOrderByUid(String uid);
	Order selOrderByOid(String oid);
	List<Order> selAllOrder();
	void delOrder(Order order);
	void modOrder(Order order);
}
