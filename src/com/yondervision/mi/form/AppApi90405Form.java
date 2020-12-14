package com.yondervision.mi.form;

/** 
* @ClassName: AppApi90405Form 
* @Description: 新媒体客服——用户发送消息
* @author Caozhongyan
* @date 2016年9月23日 上午11:29:50   
* 
*/ 
public class AppApi90405Form extends AppApiCommonForm{
	
	private String accessToken = "";
	
	private String app_id = "";
	
	private String app_user_name = "";

	private String app_user_nickname = "";
	
	private String app_plat_name = "";
	
	private String msg_data = "";
	
	private String plat_user_name = "";
	
	private String msg_cmd = "";

	public String getMsg_cmd() {
		return msg_cmd;
	}

	public void setMsg_cmd(String msg_cmd) {
		this.msg_cmd = msg_cmd;
	}

	public String getPlat_user_name() {
		return plat_user_name;
	}

	public void setPlat_user_name(String plat_user_name) {
		this.plat_user_name = plat_user_name;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getApp_id() {
		return app_id;
	}
	
	public String getApp_user_name() {
		return app_user_name;
	}

	public void setApp_user_name(String app_user_name) {
		this.app_user_name = app_user_name;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getApp_user_nickname() {
		return app_user_nickname;
	}

	public void setApp_user_nickname(String app_user_nickname) {
		this.app_user_nickname = app_user_nickname;
	}

	public String getApp_plat_name() {
		return app_plat_name;
	}

	public void setApp_plat_name(String app_plat_name) {
		this.app_plat_name = app_plat_name;
	}

	public String getMsg_data() {
		return msg_data;
	}

	public void setMsg_data(String msg_data) {
		this.msg_data = msg_data;
	}
	
}
