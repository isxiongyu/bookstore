package cn.xiongyu.bookstore.order.domain;

import cn.xiongyu.bookstore.user.domain.User;

import java.util.Date;
import java.util.List;

public class Order {
	private String oid;
	private Date orderTime;
	private float total;
	private int state;
	private User user;
	private String address;
	private List<OrderItem> orderItems;
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Order(String oid, Date orderTime, float total, int state, User user,
			String address, List<OrderItem> orderItems) {
		super();
		this.oid = oid;
		this.orderTime = orderTime;
		this.total = total;
		this.state = state;
		this.user = user;
		this.address = address;
		this.orderItems = orderItems;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	@Override
	public String toString() {
		return "Order [oid=" + oid + ", orderTime=" + orderTime + ", total="
				+ total + ", state=" + state + ", user=" + user + ", address="
				+ address + ", orderItems=" + orderItems + "]";
	}
	
}
