package cn.xiongyu.bookstore.user.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class User {
	private String uid;
	@NotNull(message="用户名不能为空")
	@Size(min=3, max=10, message="用户名必须在{min}-{max}内")
	private String username;
	private String password;
	@Pattern(regexp="^[1][3,4,5,7,8,9]\\d{9}$", message="手机号格式不正确")
	private String phone;
	@Pattern(regexp="^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$", message="邮箱格式不正确")
	private String email;
	private String code;
	private int State;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(String uid, String username, String password, String phone,
			String email, String code, int state) {
		super();
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.code = code;
		State = state;
	}

	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getState() {
		return State;
	}
	public void setState(int state) {
		State = state;
	}

	@Override
	public String toString() {
		return "UserDao [uid=" + uid + ", username=" + username + ", password="
				+ password + ", phone=" + phone + ", email=" + email
				+ ", code=" + code + ", State=" + State + "]";
	}
}
