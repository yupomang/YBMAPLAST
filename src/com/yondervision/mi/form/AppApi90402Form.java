package com.yondervision.mi.form;

/** 
* @ClassName: AppApi90402Form 
* @Description: 新媒体客服——应用用户注册接口
* @author Caozhongyan
* @date 2016年9月23日 上午11:30:01   
* 
*/ 
public class AppApi90402Form extends AppApiCommonForm{

	private String accessToken = "";
	
	private String app_user_name = "";
	
	private String app_user_nickname = "";
	
	private String app_user_passwd = "";

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getApp_user_name() {
		return app_user_name;
	}

	public void setApp_user_name(String app_user_name) {
		this.app_user_name = app_user_name;
	}

	public String getApp_user_nickname() {
		return app_user_nickname;
	}

	public void setApp_user_nickname(String app_user_nickname) {
		this.app_user_nickname = app_user_nickname;
	}

	public String getApp_user_passwd() {
		return app_user_passwd;
	}

	public void setApp_user_passwd(String app_user_passwd) {
		this.app_user_passwd = app_user_passwd;
	}
	
}
