package com.yondervision.mi.form;

public class WeiXin00103Form extends WeiXinApiCommonForm {
	/** 起始页 **/
	private String page="";
	/** 每页显示条数 **/
	private String rows="";
	/** 中心ID **/
	private String centerId="";
	/** 中心ID **/
	private String nextopenid="";
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	public String getCenterId() {
		return centerId;
	}
	public void setCenterId(String centerId) {
		this.centerId = centerId;
	}
	public String getNextopenid() {
		return nextopenid;
	}
	public void setNextopenid(String nextopenid) {
		this.nextopenid = nextopenid;
	}
}
