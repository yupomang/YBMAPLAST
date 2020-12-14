package com.yondervision.mi.form;

/** 
* @ClassName: AppApi90401Form 
* @Description: 新媒体客服——如何获得认证token
* @author Caozhongyan
* @date 2016年9月23日 上午11:30:10   
* 
*/ 
public class AppApi90401Form extends AppApiCommonForm{
	
	private String appKey = "";
	
	private String appSecret = "";
	
	private String platform = "";

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
}
