package com.yondervision.mi.form;

public class AppApi059Form extends AppApiCommonForm {
	//脱敏主题码
	private String desensitizationId = "";
	//是否分页
	private String ispaging="";
	//第几页
	private String pagenum="";
	//每页条数
	private String pagesize="";

	public String getDesensitizationId() {
		return desensitizationId;
	}

	public void setDesensitizationId(String desensitizationId) {
		this.desensitizationId = desensitizationId;
	}

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
