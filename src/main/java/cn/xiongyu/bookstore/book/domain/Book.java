package cn.xiongyu.bookstore.book.domain;


import java.io.Serializable;

public class Book {
	private String bid;
	private String bname;
	private float bprice;
	private String author;
	private int rest;
	private Category category;
	private String img;
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Book(String bid, String bname, float bprice, String author,
			int rest, Category category, String img) {
		super();
		this.bid = bid;
		this.bname = bname;
		this.bprice = bprice;
		this.author = author;
		this.rest = rest;
		this.category = category;
		this.img = img;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public float getBprice() {
		return bprice;
	}
	public void setBprice(float bprice) {
		this.bprice = bprice;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getRest() {
		return rest;
	}
	public void setRest(int rest) {
		this.rest = rest;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@Override
	public String toString() {
		return "Book [bid=" + bid + ", bname=" + bname + ", bprice=" + bprice
				+ ", author=" + author + ", rest=" + rest + ", category="
				+ category + ", img=" + img + "]";
	}
	
}	
