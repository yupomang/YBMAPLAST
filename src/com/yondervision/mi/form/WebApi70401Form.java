package com.yondervision.mi.form;

/** 
* @ClassName: WebApi70401Form 
* @Description: 版块栏目-增加请求Form
*/ 
public class WebApi70401Form extends WebApiCommonForm {
	
	private String newsitemid;
	private String forum;
	private String[] columns = null;
	
	public String getNewsitemid() {
		return newsitemid;
	}
	public void setNewsitemid(String newsitemid) {
		this.newsitemid = newsitemid;
	}
	public String getForum() {
		return forum;
	}
	public void setForum(String forum) {
		this.forum = forum;
	}
	public String[] getColumns() {
		return columns;
	}
	public void setColumns(String[] columns) {
		this.columns = columns;
	}
	
	//private String newspaperforum;
	//private String newspapercolumns;

}
