package com.yondervision.mi.form;

/** 
* @ClassName: AppApi99901Form 
* @Description: 招聘信息-录入
* @author gongqi
*/ 
public class AppApi99901Form extends AppApiCommonForm{
	/**
	 * 姓名
	 */
	private String username = "";
	/**
	 * 手机号码
	 */
	private String phone = "";
	/**
	 * 电子邮箱
	 */
	private String email = "";
	/**
	 * 应聘区域
	 */
	private String applyarea = "";
	/**
	 * 应聘职位
	 */
	private String applyposition = "";
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getApplyarea() {
		return applyarea;
	}
	public void setApplyarea(String applyarea) {
		this.applyarea = applyarea;
	}
	public String getApplyposition() {
		return applyposition;
	}
	public void setApplyposition(String applyposition) {
		this.applyposition = applyposition;
	}
}
