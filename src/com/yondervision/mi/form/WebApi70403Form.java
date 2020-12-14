package com.yondervision.mi.form;

/** 
* @ClassName: WebApi70403Form 
* @Description: 版块栏目-修改请求Form
*/ 
public class WebApi70403Form extends WebApiCommonForm {
	
	private String forumdicid;
	private String newsitemid;
	
	private String forum;
	private String[] columns = null;
	
	public String getForumdicid() {
		return forumdicid;
	}
	public void setForumdicid(String forumdicid) {
		this.forumdicid = forumdicid;
	}
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
}
