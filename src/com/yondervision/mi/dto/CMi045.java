package com.yondervision.mi.dto;

public class CMi045 extends Mi045 {
	/** WEB用户ID **/
	private String userid;
	/** WEB用户名称 **/
	private String username;
	/** WEB用户IP **/
	private String longinip;
	/**渠道**/
	private String channel;
	/** 起始页 **/
	private Integer page;
	/** 每页显示条数 **/
	private Integer rows;
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
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	} 
}
