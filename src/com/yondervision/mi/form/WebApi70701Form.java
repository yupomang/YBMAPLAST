package com.yondervision.mi.form;

/** 
* @ClassName: WebApi70701Form 
* @Description: 内容展现项-增加请求Form
*/ 
public class WebApi70701Form extends WebApiCommonForm {
	
	private String newsviewitemid;
	private String[] columns = null;
	
	public String getNewsviewitemid() {
		return newsviewitemid;
	}
	public void setNewsviewitemid(String newsviewitemid) {
		this.newsviewitemid = newsviewitemid;
	}
	public String[] getColumns() {
		return columns;
	}
	public void setColumns(String[] columns) {
		this.columns = columns;
	}
}
