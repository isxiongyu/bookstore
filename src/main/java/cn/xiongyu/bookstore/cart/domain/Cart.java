package cn.xiongyu.bookstore.cart.domain;


import cn.xiongyu.bookstore.book.domain.Book;
import cn.xiongyu.bookstore.cart.service.ICartService;
import cn.xiongyu.bookstore.user.domain.User;

import javax.annotation.Resource;

public class Cart {
	
	@Resource(name="cartService")
	private ICartService cartService;
	private String cartId;
	private Book book;
	private int num;
	private float subtotal;
	private User user;
	
	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Cart(ICartService cartService, String cartId, Book book, int num,
			float subtotal, User user) {
		super();
		this.cartService = cartService;
		this.cartId = cartId;
		this.book = book;
		this.num = num;
		this.subtotal = subtotal;
		this.user = user;
	}

	public String getCartId() {
		return cartId;
	}
	public void setCartId(String cartId) {
		this.cartId = cartId;
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
		return book.getBprice() * num;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", book=" + book + ", num=" + num
				+ ", subtotal=" + subtotal + ", user=" + user + "]";
	}
	
	
}
