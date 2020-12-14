/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.form
 * 文件名：     WebApi30203Form.java
 * 创建日期：2013-12-3
 */
package com.yondervision.mi.form;

/**
 * @author LinXiaolong
 *
 */
public class WebApi30203Form extends WebApiCommonForm {
	/** 起始页 **/
	private Integer page;
	/** 每页显示条数 **/
	private Integer rows;
	
	private String commsgid;
	
	private String startdate = "";
	
	private String enddate = "";
	
	private String pusMessageType = "";
	
	private String msgsource = "";
	
	//1-今天   0-不是
	private String isToday = "";
	
	public String getIsToday() {
		return isToday;
	}

	public void setIsToday(String isToday) {
		this.isToday = isToday;
	}

	public String getStartdate() {
		return startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public String getPusMessageType() {
		return pusMessageType;
	}

	public String getMsgsource() {
		return msgsource;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public void setPusMessageType(String pusMessageType) {
		this.pusMessageType = pusMessageType;
	}

	public void setMsgsource(String msgsource) {
		this.msgsource = msgsource;
	}

	public String getCommsgid() {
		return commsgid;
	}

	public void setCommsgid(String commsgid) {
		this.commsgid = commsgid;
	}

	/**
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * @return the rows
	 */
	public Integer getRows() {
		return rows;
	}

	/**
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(Integer rows) {
		this.rows = rows;
	}
}
