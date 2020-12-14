package com.yondervision.mi.form;
/** 
* @ClassName: AppApi90424Form 
* @Description: 留言已读
* @author CZY
* @date 2017-08-17
*/
public class AppApi90424Form extends AppApiCommonForm{
	private String accessToken;
	private String app_user_name;
	private String info_id;
	public String getAccessToken() {
		return accessToken;
	}
	public String getApp_user_name() {
		return app_user_name;
	}
	public String getInfo_id() {
		return info_id;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public void setApp_user_name(String app_user_name) {
		this.app_user_name = app_user_name;
	}
	public void setInfo_id(String info_id) {
		this.info_id = info_id;
	}
	
	
}
