package com.cafe24.jblog.vo;

public class PostVo {
	private Long no;
	private Long category_no;
	private String title;
	private String content;
	private String reg_date;
	
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public Long getCategory_no() {
		return category_no;
	}
	public void setCategory_no(Long category_no) {
		this.category_no = category_no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	
	@Override
	public String toString() {
		return "PostVo [no=" + no + ", category_no=" + category_no + ", title=" + title + ", content=" + content
				+ ", reg_date=" + reg_date + "]";
	}
	
}
