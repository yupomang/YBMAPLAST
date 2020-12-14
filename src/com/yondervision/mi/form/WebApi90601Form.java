package com.yondervision.mi.form;

/** 
* @ClassName: WebApi90601Form 
* @Description: 工单系统——获取token
* @date 2016年9月23日 上午11:30:10   
* 
*/ 
public class WebApi90601Form extends WebApiCommonForm{

	private String key = "";
	
	private String secret = "";
	
	private String from_plat = "";

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getFrom_plat() {
		return from_plat;
	}

	public void setFrom_plat(String fromPlat) {
		from_plat = fromPlat;
	}

}
