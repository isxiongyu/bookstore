package cn.xiongyu.bookstore.user.service;

import cn.xiongyu.bookstore.exception.ActiveException;
import cn.xiongyu.bookstore.exception.LoginException;
import cn.xiongyu.bookstore.exception.RegisterException;
import cn.xiongyu.bookstore.user.domain.User;

public interface IUserService {
	void register(User user) throws RegisterException;
	User login(User user) throws LoginException;
	void active(String code) throws ActiveException;
	User selUserById(String uid);
}
