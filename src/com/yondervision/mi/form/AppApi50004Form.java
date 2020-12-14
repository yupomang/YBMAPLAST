package com.yondervision.mi.form;

public class AppApi50004Form extends AppApiCommonForm{	
	
	private String jgh="" ;
	/**
	 * 手机号
	 */
	private String tel="";
	/**
	 * 手机短信验证码
	 */
	private String checkcode="";
	/**
	 * 公积金密码
	 */
	private String password="";
	/**
	 * 新公积金密码
	 */
	private String newpassword="";
	/**
	 * 新公积金密码
	 */
	private String confirmnewpassword="";	
	/**
	 * 姓名
	 */
	private String fullName="";
	/**
	 * 银行卡号
	 */
	private String bankcode="";
	/** 登录方式 */			
	private String logintype = "";			
	private String cardno = "";
	/** 短信息类型 */
	private String smstype = "";
	/** 业务描述 */
	private String message = "";
	/** 验证码 */
	private String vcode = "";
	
	/** 推送主题 */
	private String sendTheme = "";
	
	public String getSendTheme() {
		return sendTheme;
	}

	public void setSendTheme(String sendTheme) {
		this.sendTheme = sendTheme;
	}

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSmstype() {
		return smstype;
	}

	public void setSmstype(String smstype) {
		this.smstype = smstype;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	/**			
	 *<pre> 执行获取登录方式处理 </pre>			
	 * @return logintype 登录方式			
	 */			
	public String getLogintype() {			
	    return logintype;			
	}			
				
	/**			
	 *<pre> 执行设定登录方式处理 </pre>			
	 * @param logintype 登录方式			
	 */			
	public void setLogintype(String logintype) {			
	    this.logintype = logintype;			
	}			
				

	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCheckcode() {
		return checkcode;
	}
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public String getConfirmnewpassword() {
		return confirmnewpassword;
	}
	public void setConfirmnewpassword(String confirmnewpassword) {
		this.confirmnewpassword = confirmnewpassword;
	}

	public String getJgh() {
		return jgh;
	}

	public void setJgh(String jgh) {
		this.jgh = jgh;
	}
	
}
