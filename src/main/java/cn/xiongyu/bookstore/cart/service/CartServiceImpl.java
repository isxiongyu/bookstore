package cn.xiongyu.bookstore.cart.service;

import cn.xiongyu.bookstore.cart.dao.ICartDao;
import cn.xiongyu.bookstore.cart.domain.Cart;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("cartService")
public class CartServiceImpl implements ICartService {

	@Resource(name="ICartDao")
	private ICartDao cartDao;
	public void addCart(Cart cart) {
		Cart getCart = cartDao.selCart(cart);
		if(getCart != null){
			int num = getCart.getNum() + cart.getNum();
			getCart.setNum(num);
			cartDao.modCart(getCart);
		}else {			
			cartDao.addCart(cart);
		}
	}
	public List<Cart> selCartByUid(String uid){
		return cartDao.selCartByUid(uid);
	}
	@Override
	public List<Cart> delCartByCartId(String cartId, String uid) {
		cartDao.delCartByCartId(cartId);
		return cartDao.selCartByUid(uid);
	}
	@Override
	public void emptyCartByUid(String uid) {
		cartDao.emptyCartByUid(uid);
	}
	@Override
	public float getTotal(List<Cart> cartList){
		float subTotal = 0;
		for (Cart cart : cartList) {
			subTotal += cart.getSubtotal();
		}
		return subTotal;
	}

}
