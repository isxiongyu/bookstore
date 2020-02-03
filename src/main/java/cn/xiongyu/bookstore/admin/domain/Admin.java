package cn.xiongyu.bookstore.admin.domain;

public class Admin {
	private int aid;
	private String aname;
	private String apassword;
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Admin(int aid, String aname, String apassword) {
		super();
		this.aid = aid;
		this.aname = aname;
		this.apassword = apassword;
	}
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public String getAname() {
		return aname;
	}
	public void setAname(String aname) {
		this.aname = aname;
	}
	public String getApassword() {
		return apassword;
	}
	public void setApassword(String apassword) {
		this.apassword = apassword;
	}
	@Override
	public String toString() {
		return "Admin [aid=" + aid + ", aname=" + aname + ", apassword="
				+ apassword + "]";
	}
	
}
