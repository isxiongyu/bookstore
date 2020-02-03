package cn.xiongyu.bookstore.order.domain;

import cn.xiongyu.bookstore.book.domain.Book;

public class OrderItem {
	private String iid;
	private Book book;
	private int num;
	private float subtotal;
	private String oid;
	public OrderItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderItem(String iid, Book book, int num, float subtotal, String oid) {
		super();
		this.iid = iid;
		this.book = book;
		this.num = num;
		this.subtotal = subtotal;
		this.oid = oid;
	}
	public String getIid() {
		return iid;
	}
	public void setIid(String iid) {
		this.iid = iid;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public float getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	@Override
	public String toString() {
		return "OrderItem [iid=" + iid + ", book=" + book + ", num=" + num
				+ ", subtotal=" + subtotal + ", oid=" + oid + "]";
	}
	
}
