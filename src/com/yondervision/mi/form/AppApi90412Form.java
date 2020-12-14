package com.yondervision.mi.form;

/** 
* @ClassName: AppApi90412Form 
* @Description: 新媒体客服——应用用户查询留言列表
* @author Caozhongyan
* @date 2016年9月23日 上午11:29:50   
* 
*/ 
public class AppApi90412Form extends AppApiCommonForm{
	/**
	 * 如果查询所有的留言信息（例如门户网站），则app_user_name 的值上传 “all”； 是否回复”isreturn”:”true”-查询全部回复的，
	 * ”false”-查询全部未回复的；is_score:"all"-全部，"true"-已评价，"false"-未评价；read:当isreturn为true时条件有效，”all”/””-全部，
	 * ”true”-已读，”false”-未读，返回信息中score_num评价次数为0或空表示未评价，大于0表示已评价，如果应用允许评价时返回评分模板，只有已经回复的留言允许评价
	 * */
	
	private String accessToken = "";
	private String app_user_name = "";
	private String currentPage = "";
	private String pageSize = "";
	private String key = "";
	private String isreturn = "";
	private String info_desc = "";
	//2017-06-19 新增 syw
	//留言渠道
	private String info_plat = "";
	//2017-08-17 
	private String read = "";
	
	public String getRead() {
		return read;
	}

	public void setRead(String read) {
		this.read = read;
	}

	public String getInfo_desc() {
		return info_desc;
	}

	public void setInfo_desc(String info_desc) {
		this.info_desc = info_desc;
	}

	public String getIsreturn() {
		return isreturn;
	}

	public void setIsreturn(String isreturn) {
		this.isreturn = isreturn;
	}

	public String getKey() {
		return key;
	}
 
	public void setKey(String key) {
		this.key = key;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
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
	
	
}
