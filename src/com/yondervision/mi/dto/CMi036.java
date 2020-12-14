package com.yondervision.mi.dto;

public class CMi036 extends Mi036 {
	/** WEB用户ID **/
	private String userid;
	/** WEB用户名称 **/
	private String username;
	/** WEB用户IP **/
	private String longinip;
	/**
	 * 开始时间
	 */
	private String startDate = "";
	/**
	 * 结束时间
	 */
	private String endDate = "";
	/** 起始页 **/
	private Integer page;
	/** 每页显示条数 **/
	private Integer rows;
	//应用名称
	private String appname;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLonginip() {
		return longinip;
	}
	public void setLonginip(String longinip) {
		this.longinip = longinip;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	} 
}
