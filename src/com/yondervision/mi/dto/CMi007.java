package com.yondervision.mi.dto;

/** 
* @ClassName: CMi007
* @Description: 码表维护请求FORM
* @author gongqi
* @date 2013-10-04
* 
*/ 
public class CMi007 extends Mi007 {
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
	/** 旧编码 **/
	private String olditemid;
	/** 编码代码数组对象 */
	private String dicids;
	//是否是资金类业务
	private String ismoneytype;

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
	 * @return the olditemid
	 */
	public String getOlditemid() {
		return olditemid;
	}

	/**
	 * @param olditemid the olditemid to set
	 */
	public void setOlditemid(String olditemid) {
		this.olditemid = olditemid;
	}
	
	/**
	 * @return the dicids
	 */
	public String getDicids() {
		return dicids;
	}

	/**
	 * @param dicids the dicids to set
	 */
	public void setDicids(String dicids) {
		this.dicids = dicids;
	}

	public String getIsmoneytype() {
		return ismoneytype;
	}

	public void setIsmoneytype(String ismoneytype) {
		this.ismoneytype = ismoneytype;
	}
}
