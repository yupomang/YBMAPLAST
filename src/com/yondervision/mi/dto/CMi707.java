package com.yondervision.mi.dto;

/** 
* @ClassName: CMi707
* @Description: 栏目管理维护请求FORM
* @author gongqi
* @date 2013-10-04
* 
*/ 
public class CMi707 extends Mi707 {
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
	
	private String stat;
	/** 旧状态 **/
	private String oldstat;
	/** 旧有效日期 **/
	private String oldfreeuse1;

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

	/**
	 * @return the stat
	 */
	public String getStat() {
		return stat;
	}

	/**
	 * @param stat the stat to set
	 */
	public void setStat(String stat) {
		this.stat = stat;
	}

	/**
	 * @return the oldstat
	 */
	public String getOldstat() {
		return oldstat;
	}

	/**
	 * @param oldstat the oldstat to set
	 */
	public void setOldstat(String oldstat) {
		this.oldstat = oldstat;
	}

	/**
	 * @return the oldfreeuse1
	 */
	public String getOldfreeuse1() {
		return oldfreeuse1;
	}

	/**
	 * @param oldfreeuse1 the oldfreeuse1 to set
	 */
	public void setOldfreeuse1(String oldfreeuse1) {
		this.oldfreeuse1 = oldfreeuse1;
	}
}
