package cn.xiongyu.bookstore.admin.dao;

import cn.xiongyu.bookstore.admin.domain.Admin;

public interface IAdminDao {
	Admin selectAdminByName(String name);
}
