package com.yondervision.mi.form;

/** 
* @ClassName: AppApi60001Form 
* @Description: 在线留言-发送
* @author gongqi
* @date July 16, 2014 9:33:25 PM 
* 
*/ 
public class AppApi60001Form extends AppApiCommonForm{
	// 留言信息 
	private String message;
	
	private String username;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
