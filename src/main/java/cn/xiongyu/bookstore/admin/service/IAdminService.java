package cn.xiongyu.bookstore.admin.service;

import cn.xiongyu.bookstore.admin.domain.Admin;
import cn.xiongyu.bookstore.exception.AdminAccessPermissionException;

public interface IAdminService {
	void login(Admin admin) throws AdminAccessPermissionException;
}
