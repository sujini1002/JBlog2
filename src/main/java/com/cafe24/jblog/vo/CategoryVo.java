package com.cafe24.jblog.vo;



public class CategoryVo {
	private Long no;
	private String id;
	private String name;
	private String description;
	private String reg_date;
	
	public CategoryVo() {}
	
	public CategoryVo(String id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	
	@Override
	public String toString() {
		return "CategoryVo [no=" + no + ", id=" + id + ", name=" + name + ", description=" + description + ", reg_date="
				+ reg_date + "]";
	}
	
}
