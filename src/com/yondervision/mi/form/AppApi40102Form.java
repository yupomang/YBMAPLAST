package com.yondervision.mi.form;

/** 
* @ClassName: AppApi40102Form 
* @Description: TODO
* @author Caozhongyan
* @date Oct 12, 2013 3:31:37 PM   
* 
*/ 
public class AppApi40102Form extends AppApiCommonForm{
	/**
	 * 姓名
	 */
	private String fullName="";
	/**
	 * 证件号码 
	 */
	private String idcardNumber="";
	/**
	 * 手机号码
	 */
	private String mobileNumber="";
	/**
	 * 电子邮箱
	 */
	private String email="";
	/**
	 * 密码
	 */
	private String password="";
	/**
	 * 新密码
	 */
	private String newpassword="";
	/**
	 * 验证码
	 */
	private String checkid="";
	/**
	 * 设备devtoken
	 */
	private String devtoken="";
	/**
	 * 联名卡卡号
	 */
	private String cardno="";
	
	/**
	 * 应用类型
	 */
	private String ischeck="";
	
	/**
	 * 绑定通知类型
	 */
	private String bindingnotice = "";
	/**
	 * 正常登录标识(0或者没有此参数:正常，1（修改+登录）)
	 */
	private String nomalLogin = "";
	/**
	 * 身份认证码（需要二次登录验证确认身份时使用）
	 */
	private String authCode = "";
	
	/**
	 * 收款行卡号
	 */
	private String bankaccnum = "";
	
	public String getNomalLogin() {
		return nomalLogin;
	}
	public void setNomalLogin(String nomalLogin) {
		this.nomalLogin = nomalLogin;
	}
	public String getBindingnotice() {
		return bindingnotice;
	}
	public void setBindingnotice(String bindingnotice) {
		this.bindingnotice = bindingnotice;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getIdcardNumber() {
		return idcardNumber;
	}
	public void setIdcardNumber(String idcardNumber) {
		this.idcardNumber = idcardNumber;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCheckid() {
		return checkid;
	}
	public void setCheckid(String checkid) {
		this.checkid = checkid;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	/**
	 * 设备TOKEN
	 */
	public String getDevtoken() {
		return devtoken;
	}
	/**
	 * 设备TOKEN
	 */
	public void setDevtoken(String devtoken) {
		this.devtoken = devtoken;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public String getIscheck() {
		return ischeck;
	}
	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getBankaccnum() {
		return bankaccnum;
	}
	public void setBankaccnum(String bankaccnum) {
		this.bankaccnum = bankaccnum;
	}
}
