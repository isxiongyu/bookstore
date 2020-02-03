package cn.xiongyu.bookstore.user.service;

import cn.xiongyu.bookstore.exception.ActiveException;
import cn.xiongyu.bookstore.exception.LoginException;
import cn.xiongyu.bookstore.exception.RegisterException;
import cn.xiongyu.bookstore.user.dao.IUserDao;
import cn.xiongyu.bookstore.user.domain.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements IUserService {
	@Resource(name="IUserDao")
	private IUserDao dao;
	
	public IUserDao getDao() {
		return dao;
	}

	public void setDao(IUserDao dao) {
		this.dao = dao;
	}

	@Override
	public void register(User user) throws RegisterException {
		
		User usernameGet = dao.selUserByUsername(user.getUsername());
		if (usernameGet != null) {
			throw new RegisterException("用户名已存在");
		}
		dao.addUser(user);
	}

	@Override
	public User login(User user) throws LoginException {
		User userGet = dao.selUserByUsername(user.getUsername());
		if(userGet == null){
			throw new LoginException("用户名不存在");
		}else if (! userGet.getPassword().equals(user.getPassword())) {
			throw new LoginException("用户名密码错误");
		}else if (userGet.getState() == 0) {
			throw new LoginException("该用户还未激活，请前往用户注册邮箱进行激活");
		}
		return userGet;
	}

	@Override
	public void active(String code) throws ActiveException {
		User user = dao.selUserByCode(code);
		if(user != null){
			if(user.getState() == 1){
				throw new ActiveException("该激活码已经激活，请不要重复激活");
			}
			user.setState(1);
			dao.modUser(user);
		}else {
			throw new ActiveException("该激活码无效"); 
		}
	}

	@Override
	public User selUserById(String uid) {
		return dao.selUserById(uid);
	}
	
}
