package cn.xiongyu.bookstore.admin.handler;

import cn.xiongyu.bookstore.admin.domain.Admin;
import cn.xiongyu.bookstore.admin.service.IAdminService;
import cn.xiongyu.bookstore.exception.AdminAccessPermissionException;
import cn.xiongyu.bookstore.exception.handler.ExceptionController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/admin")
@Controller
public class AdminController extends ExceptionController {
	
	@Resource(name="adminService")
    IAdminService adminService;
	@RequestMapping("login.do")
	public ModelAndView login(Admin admin, HttpServletRequest request) throws AdminAccessPermissionException{
		
		ModelAndView mv = new ModelAndView();
		adminService.login(admin);
		mv.addObject("admin", admin);
		request.getSession().setAttribute("admin", admin);
		mv.setViewName("redirect:/jsps/admin/main.jsp");
		return mv;
	}
	@RequestMapping("quit.do")
	public String quit(Admin admin, HttpServletRequest request) throws AdminAccessPermissionException{
		
		request.getSession().removeAttribute("admin");
		return "redirect:/jsps/admin/main.jsp";
	}
}
