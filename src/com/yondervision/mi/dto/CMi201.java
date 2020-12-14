package com.yondervision.mi.dto;

/** 
* @ClassName: CMi203 
* @Description: 网点维护请求FORM
* @author Caozhongyan
* @date Sep 29, 2013 3:33:12 PM   
* 
*/ 
public class CMi201 extends Mi201 {
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
	/**
	 * 删除ID集合
	 */
	private String deletes;
	/**
	 * 查询区域ID
	 */
	private String areaid;
	// 业务名称
	private String businName = "";
	/** 起始页 **/
	private Integer page;
	/** 每页显示条数 **/
	private Integer rows;  
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
	/**
	   * 业务名称取得<BR>
	   * 
	   * @return 业务名称
	   */
	  public String getBusinName() {
		return businName;
	  }
	  /**
	   * 业务名称赋值<BR>
	   * 
	   * @param centerId 业务名称
	   */
	  public void setBusinName(String businName) {
		this.businName = businName;
	  }

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getDeletes() {
		return deletes;
	}

	public void setDeletes(String deletes) {
		this.deletes = deletes;
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
}
