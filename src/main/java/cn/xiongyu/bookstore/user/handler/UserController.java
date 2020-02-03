package cn.xiongyu.bookstore.user.handler;

import cn.xiongyu.bookstore.exception.ActiveException;
import cn.xiongyu.bookstore.exception.LoginException;
import cn.xiongyu.bookstore.exception.handler.ExceptionController;
import cn.xiongyu.bookstore.user.domain.User;
import cn.xiongyu.bookstore.user.service.IUserService;
import cn.xiongyu.common.beanUtils.MyBeanUtils;
import cn.xiongyu.common.mailutils.Mail;
import cn.xiongyu.common.mailutils.MailUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.mail.Session;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.Properties;

@RequestMapping("user")
@Controller
public class UserController extends ExceptionController {
	
	@Resource(name="userService")
	private IUserService service;
	
	@RequestMapping("/register.do")
	public ModelAndView register(@Validated User user, BindingResult br) throws Exception{
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("user", user);
		int errorCount = br.getErrorCount();
		if(errorCount > 0){
			FieldError nameError = br.getFieldError("username");
			FieldError phoneError = br.getFieldError("phone");
			FieldError emailError = br.getFieldError("email");
			if(nameError != null){
				String nameErrorMessage = nameError.getDefaultMessage();
				mv.addObject("nameError", nameErrorMessage);
			}
			if(phoneError != null){
				String phoneErrorMessage = phoneError.getDefaultMessage();
				mv.addObject("phoneError", phoneErrorMessage);
			}
			if(emailError != null){
				String emailErrorMessage = emailError.getDefaultMessage();
				mv.addObject("emailError", emailErrorMessage);
			}
			mv.setViewName("/jsps/user/register.jsp");
			return mv;
		}
		user.setUid(MyBeanUtils.getUUID());
		user.setCode(MyBeanUtils.getUUID());
		user.setState(0);
		service.register(user);
		/*发送邮件*/
		this.sendMail(user);
		mv.setViewName("/jsps/user/active.jsp");
		return mv;
	}
	
	@RequestMapping("/login.do")
	public ModelAndView login(User user, HttpServletRequest request, HttpServletResponse response) throws LoginException {
		user = service.login(user);
		Cookie cookie = new Cookie("uname", user.getUsername());
		cookie.setMaxAge(60 * 60 * 24 * 365);
		response.addCookie(cookie);
		request.getSession().setAttribute("user", user);
		ModelAndView mv = new ModelAndView();
		mv.addObject("user", user);
		mv.setViewName("redirect:/index.jsp");
		return mv;
	}
	
	@RequestMapping("/quit.do")
	public String quit(HttpServletRequest request) throws LoginException {
		request.getSession().removeAttribute("user");
		return "redirect:/jsps/user/login.jsp";
	}
	
	@RequestMapping("active.do")
	public ModelAndView active(String code) throws ActiveException{
		ModelAndView mv = new ModelAndView();
		mv.addObject("activeMsg", "恭喜您，激活成功，可以完成登录");
		service.active(code);
		mv.setViewName("/jsps/user/login.jsp");
		return mv;
	}
	
	//发送邮件方法
	public void sendMail(User user) throws Exception{
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("resources/mail_template.properties"));
		String smtp = props.getProperty("smtp");
		String username = props.getProperty("username");
		String password = props.getProperty("password");
		String from = props.getProperty("from");
		String subject = props.getProperty("subject");
		String to = user.getEmail();
		String content = props.getProperty("content");
		content = MessageFormat.format(content, user.getCode());
		Session session = MailUtils.creatSession(smtp, username, password);
		Mail mail = new Mail(session, from, to, subject, content);
		MailUtils.send(mail);
	}
	
}
