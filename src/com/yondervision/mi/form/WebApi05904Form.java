package com.yondervision.mi.form;

public class WebApi05904Form extends AppApiCommonForm {
	private String ispaging="";//是否分页
	private String pagenum="";
	private String pagesize="";
	
	public String getIspaging() {
		return ispaging;
	}
	public void setIspaging(String ispaging) {
		this.ispaging = ispaging;
	}
	public String getPagenum() {
		return pagenum;
	}
	public void setPagenum(String pagenum) {
		this.pagenum = pagenum;
	}
	public String getPagesize() {
		return pagesize;
	}
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}
}
