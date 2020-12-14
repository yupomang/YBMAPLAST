package com.yondervision.mi.form;

/** 
* @ClassName: AppApi40401Form 
* @Description: 意见反馈
* @author Caozhongyan
* @date Oct 15, 2013 4:01:40 PM   
* 
*/ 
public class AppApi40401Form extends AppApiCommonForm{
	//反馈信息 
	private String message;
	
	//电话 
	private String tel;
	
	//邮箱
	private String email;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
