package cn.xiongyu.bookstore.order.service;

import cn.xiongyu.bookstore.exception.ExceedMaxRestException;
import cn.xiongyu.bookstore.order.domain.Order;

import java.util.List;

public interface IOrderItemService {
	Order creatOrderItem(Order order) throws ExceedMaxRestException;
	List<Order> selOrderItem(List<Order> orderList);
}
