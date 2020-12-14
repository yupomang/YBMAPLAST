package com.yondervision.mi.dto;

public class CMi042 extends Mi042 {
	/** WEB用户ID **/
	private String userid;
	/** WEB用户名称 **/
	private String username;
	/** WEB用户IP **/
	private String longinip;
	/** 起始页 **/
	private Integer page;
	/** 每页显示条数 **/
	private Integer rows;
	/**
	 * 开始时间
	 */
	private String begdate="";
	/**
	 * 结束时间
	 */
	private String enddate="";
	
	public String getBegdate() {
		return begdate;
	}
	public void setBegdate(String begdate) {
		this.begdate = begdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
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
}
