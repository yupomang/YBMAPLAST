package com.yondervision.mi.form;

/** 
* @ClassName: AppApi90413Form 
* @Description: 新媒体客服——应用用户接收消息
* @author Caozhongyan
* @date 2016年9月23日 上午11:29:50   
* 
*/ 
public class AppApi90413Form extends AppApiCommonForm{
	
	private String accessToken = "";
	private String app_user_name = "";
	
	
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
	
}
