/**
 * 
 */
package com.yondervision.mi.dto;

/**
 * @author Administrator
 * 
 */
public class CMi411 extends Mi411 {
	private String businName = "";
	/** 起始页 **/
	private Integer page;
	/** 每页显示条数 **/
	private Integer rows;
	/** 中心ID **/
	private String centerid;
	/** 中心名称 **/
	private String centreName;
	/** WEB用户ID **/
	private String userid;
	/** WEB用户名称 **/
	private String username;
	/** WEB用户IP **/
	private String longinip;
	public String getBusinName() {
		return businName;
	}
	public void setBusinName(String businName) {
		this.businName = businName;
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
	public String getCentreName() {
		return centreName;
	}
	public void setCentreName(String centreName) {
		this.centreName = centreName;
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
	public String getCenterid() {
		return centerid;
	}
	public void setCenterid(String centerid) {
		this.centerid = centerid;
	}
}
