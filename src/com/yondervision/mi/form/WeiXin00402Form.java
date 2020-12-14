package com.yondervision.mi.form;

import java.util.Map;

public class WeiXin00402Form extends WeiXinApiCommonForm {
	//标题
	private String title;
	//图片路径
	private String imgUrl;
	//图片服务器路劲
	private String realurl;
	//欢迎语
	private String welcome;
	//中心id
	private String centerid;
	//功能配置
	private Map<String,String> funcArray;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getRealurl() {
		return realurl;
	}
	public void setRealurl(String realurl) {
		this.realurl = realurl;
	}
	public String getWelcome() {
		return welcome;
	}
	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}
	public String getCenterid() {
		return centerid;
	}
	public void setCenterid(String centerid) {
		this.centerid = centerid;
	}
	public Map<String, String> getFuncArray() {
		return funcArray;
	}
	public void setFuncArray(Map<String, String> funcArray) {
		this.funcArray = funcArray;
	}

	
	
}
