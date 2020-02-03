package cn.xiongyu.bookstore.user.dao;

import cn.xiongyu.bookstore.user.domain.User;

public interface IUserDao {
	void addUser(User user);
	void delUser(User user);
	void modUser(User user);
	User selUserByUsername(String username);
	User selUserByCode(String code);
	User selUserByEmail(String email);
	User selUserByPhone(String phone);
	User selUserById(String uid);
}
