package com.yondervision.mi.form;

/** 
* @ClassName: AppApi90411Form 
* @Description: 新媒体客服——应用用户创建留言
* @author Caozhongyan
* @date 2016年9月23日 上午11:29:50   
* 
*/ 
public class AppApi90411Form extends AppApiCommonForm{
	
	private String accessToken = "";
	private String app_user_name = "";
	private String info_plat = "";
	private String information = "";
	private String user_name = "";
	private String user_contact = "";
	private String info_desc = "";
	//2017-06-19新增 syw
	//留言信息展示
	private String show_info="";
	private String[] img_urls;
	
	public String[] getImg_urls() {
		return img_urls;
	}

	public void setImg_urls(String[] img_urls) {
		this.img_urls = img_urls;
	}

	public String getInfo_desc() {
		return info_desc;
	}

	public void setInfo_desc(String info_desc) {
		this.info_desc = info_desc;
	}

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

	public String getInfo_plat() {
		return info_plat;
	}

	public void setInfo_plat(String info_plat) {
		this.info_plat = info_plat;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_contact() {
		return user_contact;
	}

	public void setUser_contact(String user_contact) {
		this.user_contact = user_contact;
	}

	public String getShow_info() {
		return show_info;
	}

	public void setShow_info(String show_info) {
		this.show_info = show_info;
	}
	
}
