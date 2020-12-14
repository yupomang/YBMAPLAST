package com.yondervision.mi.form;

public class AppApi50003Form extends AppApiCommonForm{	
	/**
	 * 开发商登录号
	 */
	private String empnum = "";
	/** 开发商专办员姓名 */			
	private String opname = "";	
	/**
	 * 开发商专办员证件号码
	 */
	private String certinum = "";
	/**
	 * 公积金单位密码
	 */
	private String password = "";
	/**
	 * 公积金单位新密码
	 */
	private String newpassword = "";
	
	/** 开发商专办员号 */			
	private String opercode = "";			
	/** 手机号码 */			
	private String tel = "";			
				
	/**			
	 *<pre> 执行获取开发商专办员号处理 </pre>			
	 * @return opercode 开发商专办员号			
	 */			
	public String getOpercode() {			
	    return opercode;			
	}			
				
	/**			
	 *<pre> 执行设定开发商专办员号处理 </pre>			
	 * @param opercode 开发商专办员号			
	 */			
	public void setOpercode(String opercode) {			
	    this.opercode = opercode;			
	}			
				
				
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	/**			
	 *<pre> 执行获取开发商专办员姓名处理 </pre>			
	 * @return opname 开发商专办员姓名			
	 */			
	public String getOpname() {			
	    return opname;			
	}			
				
	/**			
	 *<pre> 执行设定开发商专办员姓名处理 </pre>			
	 * @param opname 开发商专办员姓名			
	 */			
	public void setOpname(String opname) {			
	    this.opname = opname;			
	}			

	
	/**
	 * 手机短信验证码
	 */
	private String checkcode = "";


	public String getEmpnum() {
		return empnum;
	}

	public void setEmpnum(String empnum) {
		this.empnum = empnum;
	}

	public String getCertinum() {
		return certinum;
	}

	public void setCertinum(String certinum) {
		this.certinum = certinum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCheckcode() {
		return checkcode;
	}

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	
}
