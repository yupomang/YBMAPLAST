package com.yondervision.mi.dto;

public class CMi102 extends Mi102 {
	/** 中心ID **/
	private String centerId;
	/** 中心名称 **/
	private String centreName;
	/** WEB用户ID **/
	private String userid;
	/** WEB用户名称 **/
	private String username;
	/** WEB用户IP **/
	private String longinip;
	/** WEB用户角色ID(多个角色用逗号分割) **/
	private String roleid;
	private String businName;
	
	/** 修改前客服类型 **/
	private String oldcustsvctype;

	/** 修改前客服账号 **/
	private String oldcustsvcaccnum;
	
	public String getBusinName() {
		return businName;
	}

	public void setBusinName(String businName) {
		this.businName = businName;
	}
	/**
	 * @return the centerId
	 */
	public String getCenterId() {
		return centerId;
	}

	/**
	 * @param centreId
	 *            the centerId to set
	 */
	public void setCenterId(String centerId) {
		this.centerId = centerId;
	}

	/**
	 * @return the centreName
	 */
	public String getCentreName() {
		return centreName;
	}

	/**
	 * @param centreName
	 *            the centreName to set
	 */
	public void setCentreName(String centreName) {
		this.centreName = centreName;
	}

	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid
	 *            the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the longinip
	 */
	public String getLonginip() {
		return longinip;
	}

	/**
	 * @param longinip
	 *            the longinip to set
	 */
	public void setLonginip(String longinip) {
		this.longinip = longinip;
	}

	/**
	 * @return the roleid
	 */
	public String getRoleid() {
		return roleid;
	}

	/**
	 * @param roleid
	 *            the roleid to set
	 */
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	
	/**
	 * @return the oldcustsvctype
	 */
	public String getOldcustsvctype() {
		return oldcustsvctype;
	}

	/**
	 * @param oldcustsvctype the oldcustsvctype to set
	 */
	public void setOldcustsvctype(String oldcustsvctype) {
		this.oldcustsvctype = oldcustsvctype;
	}

	/**
	 * @return the oldcustsvcaccnum
	 */
	public String getOldcustsvcaccnum() {
		return oldcustsvcaccnum;
	}

	/**
	 * @param oldcustsvcaccnum the oldcustsvcaccnum to set
	 */
	public void setOldcustsvcaccnum(String oldcustsvcaccnum) {
		this.oldcustsvcaccnum = oldcustsvcaccnum;
	}
}
