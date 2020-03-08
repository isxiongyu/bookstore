package cn.xiongyu.bookstore.admin.service;

import cn.xiongyu.bookstore.admin.dao.IAdminDao;
import cn.xiongyu.bookstore.admin.domain.Admin;
import cn.xiongyu.bookstore.exception.AdminAccessPermissionException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("adminService")
public class AdminServiceImpl implements IAdminService {

	@Resource(name="IAdminDao")
    IAdminDao adminDao;
	@Override
	public void login(Admin admin) throws AdminAccessPermissionException {
		Admin getAdmin = adminDao.selectAdminByName(admin.getAname());
		if (getAdmin == null) {
			throw new AdminAccessPermissionException("用户名不存在或者密码错误");
		}else if (! getAdmin.getApassword().equals(admin.getApassword())) {			
			throw new AdminAccessPermissionException("用户名不存在或者密码错误");
		}
		System.out.println("登录");
	}

}
