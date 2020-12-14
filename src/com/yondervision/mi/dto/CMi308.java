/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dto
 * 文件名：     CMi308.java
 * 创建日期：2013-10-4
 */
package com.yondervision.mi.dto;

import java.util.List;

/**
 * 业务咨询向导内容，包含公共条件项目组合对象
 * 
 * @author LinXiaolong
 * 
 */
public class CMi308 extends Mi308 {
	/** 中心ID **/
	private String centerId;
	/** 中心名称 **/
	private String centerName;
	/** WEB用户ID **/
	private String userid;
	/** WEB用户名称 **/
	private String username;
	/** WEB用户IP **/
	private String longinip;
	/** WEB用户角色ID(多个角色用逗号分割) **/
	private String roleid;

	
	/**  此业务咨询向导内容的子内容  **/
	private List<CMi308> children;

	

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
	 * @return the centerName
	 */
	public String getCenterName() {
		return centerName;
	}

	/**
	 * @param centerName
	 *            the centerName to set
	 */
	public void setCenterName(String centerName) {
		this.centerName = centerName;
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
	 * @return the children
	 */
	public List<CMi308> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<CMi308> children) {
		this.children = children;
	}

	

	
}
