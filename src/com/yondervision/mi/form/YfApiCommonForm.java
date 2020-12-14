/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.form
 * 文件名：     YfApiCommonForm.java
 * 创建日期：2013-11-4
 */
package com.yondervision.mi.form;

/**
 * @author LinXiaolong
 * 
 */
public class YfApiCommonForm {
	/** 中心ID **/
	private String centerId;
	/** 中心名称 **/
	private String centreName;
	/** WEB用户ID **/
	private String userId;
	/** WEB用户名称 **/
	private String username;
	/** WEB用户IP **/
	private String longinip;
	/** 业务类型 **/
	private String buzType;

	/**
	 * @return the centerId
	 */
	public String getCenterId() {
		return centerId;
	}

	/**
	 * @param centerId
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
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
	 * @return the buzType
	 */
	public String getBuzType() {
		return buzType;
	}

	/**
	 * @param buzType
	 *            the buzType to set
	 */
	public void setBuzType(String buzType) {
		this.buzType = buzType;
	}

}
