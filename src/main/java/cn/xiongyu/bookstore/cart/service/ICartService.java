package cn.xiongyu.bookstore.cart.service;

import cn.xiongyu.bookstore.cart.domain.Cart;

import java.util.List;

public interface ICartService {
	void addCart(Cart cart);
	List<Cart> selCartByUid(String uid);
	List<Cart> delCartByCartId(String cartId, String uid);
	void emptyCartByUid(String uid);
	float getTotal(List<Cart> cartList);
}
