package com.yondervision.mi.form;

/** 
* @ClassName: AppApi90408Form 
* @Description: 新媒体客服——用户关闭会话
* @author Caozhongyan
* @date 2016年9月23日 上午11:29:50   
* 
*/ 
public class AppApi90408Form extends AppApiCommonForm{
	private String accessToken = "";
	private String app_user_name = "";
	private String chat_id = "";
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

	public String getChat_id() {
		return chat_id;
	}

	public void setChat_id(String chat_id) {
		this.chat_id = chat_id;
	}

}
