package com.yondervision.mi.dto;

public class CMi029 extends Mi029 {
	/** WEB用户ID **/
	private String userid;
	/** 添加黑名单原因 **/
	private String cause;
	private String longinip;
	/** 起始页 **/
	private Integer page;
	/** 每页显示条数 **/
	private Integer rows;
	
	private String startdate = "";
	private String enddate = "";
	private String chat_id = "";
	private String appostate;
	public String getChat_id() {
		return chat_id;
	}
	public void setChat_id(String chat_id) {
		this.chat_id = chat_id;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	//个人姓名
	private String personalname;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
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
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getPersonalname() {
		return personalname;
	}
	public void setPersonalname(String personalname) {
		this.personalname = personalname;
	}
	public String getAppostate() {
		return appostate;
	}
	public void setAppostate(String appostate) {
		this.appostate = appostate;
	} 
}
