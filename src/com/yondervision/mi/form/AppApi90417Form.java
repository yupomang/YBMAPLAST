package com.yondervision.mi.form;

/** 
* @ClassName: AppApi90413Form 
* @Description: 新媒体客服——
* @author Caozhongyan
* @date 2016年9月23日 上午11:29:50   
* 
*/ 
public class AppApi90417Form extends AppApiCommonForm{
	
	private String accessToken = "";
	private String app_user_name = "";
	private String chat_id = "";
	
	private String starttime = "";
	private String endtime = "";
	private String flag = "";
	private String tpl_type = "";
	private String tpl_name = "";
	private String currentPage = "";
	private String pageSize = "";
	
	private String tpl_id = "";

	public String getAccessToken() {
		return accessToken;
	}

	public String getApp_user_name() {
		return app_user_name;
	}

	public String getChat_id() {
		return chat_id;
	}

	public String getStarttime() {
		return starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public String getFlag() {
		return flag;
	}

	public String getTpl_type() {
		return tpl_type;
	}

	public String getTpl_name() {
		return tpl_name;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public String getPageSize() {
		return pageSize;
	}

	public String getTpl_id() {
		return tpl_id;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setApp_user_name(String app_user_name) {
		this.app_user_name = app_user_name;
	}

	public void setChat_id(String chat_id) {
		this.chat_id = chat_id;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public void setTpl_type(String tpl_type) {
		this.tpl_type = tpl_type;
	}

	public void setTpl_name(String tpl_name) {
		this.tpl_name = tpl_name;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public void setTpl_id(String tpl_id) {
		this.tpl_id = tpl_id;
	}
	
}
