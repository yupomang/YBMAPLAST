package com.yondervision.mi.form;

/** 
* @ClassName: AppApi90410Form 
* @Description: 新媒体客服——应用用户查询会话下消息信息
* @author Caozhongyan
* @date 2016年9月23日 上午11:29:50   
* 
*/ 
public class AppApi90410Form extends AppApiCommonForm{
	private String accessToken = "";
	private String app_user_name = "";
	private String chat_id = "";
	private String currentPage = "";
	private String pageSize = "";
	private String begindate = "";
	private String enddate = "";
	
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

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getBegindate() {
		return begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

}
