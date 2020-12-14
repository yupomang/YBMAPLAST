package com.yondervision.mi.dto;

/** 
* @ClassName: CMi109
* @Description: 利率维护请求FORM
* @author gongq
* @date Sep 29, 2013 3:33:12 PM   
* 
*/ 
public class CMi109 extends Mi109 {
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
	
	/** 修改前利率类型 **/
	private String oldratetype;
	/** 修改前月数期限 **/
	private String oldterms;
	/** 修改前生效日期 **/
	private String oldeffectiveDate;
	/** 生效日期(分页查询)**/
	private String effective_date;
	
	/** 利率类型-查询条件 **/
	private String ratetypeQry;

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
	 * @return the oldratetype
	 */
	public String getOldratetype() {
		return oldratetype;
	}

	/**
	 * @param oldratetype the oldratetype to set
	 */
	public void setOldratetype(String oldratetype) {
		this.oldratetype = oldratetype;
	}

	/**
	 * @return the oldterms
	 */
	public String getOldterms() {
		return oldterms;
	}

	/**
	 * @param oldterms the oldterms to set
	 */
	public void setOldterms(String oldterms) {
		this.oldterms = oldterms;
	}

	/**
	 * @return the oldeffectiveDate
	 */
	public String getOldeffectiveDate() {
		return oldeffectiveDate;
	}

	/**
	 * @param oldeffectiveDate the oldeffectiveDate to set
	 */
	public void setOldeffectiveDate(String oldeffectiveDate) {
		this.oldeffectiveDate = oldeffectiveDate;
	}
	
	/**
	 * @return the effective_date
	 */
	public String getEffective_date() {
		return effective_date;
	}

	/**
	 * @param effectiveDate the effective_date to set
	 */
	public void setEffective_date(String effectiveDate) {
		effective_date = effectiveDate;
	}
	
	/**
	 * @return the ratetypeQry
	 */
	public String getRatetypeQry() {
		return ratetypeQry;
	}

	/**
	 * @param ratetypeQry the ratetypeQry to set
	 */
	public void setRatetypeQry(String ratetypeQry) {
		this.ratetypeQry = ratetypeQry;
	}
}
