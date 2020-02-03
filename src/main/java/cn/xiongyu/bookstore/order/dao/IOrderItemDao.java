package cn.xiongyu.bookstore.order.dao;

import cn.xiongyu.bookstore.order.domain.OrderItem;

import java.util.List;

public interface IOrderItemDao {
	void addOrderItem(OrderItem orderItem);
	List<OrderItem> selOrderItemByOid(String oid);
}
