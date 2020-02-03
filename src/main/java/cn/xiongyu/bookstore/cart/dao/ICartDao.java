package cn.xiongyu.bookstore.cart.dao;

import cn.xiongyu.bookstore.cart.domain.Cart;

import java.util.List;

public interface ICartDao {
	void addCart(Cart cart);
	void modCart(Cart cart);
	void delCartByCartId(String cartId);
	void emptyCartByUid(String uid);
	Cart selCart(Cart cart);
	List<Cart> selCartByUid(String uid);
}
