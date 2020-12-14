/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.form
 * 文件名：     AppApi30301Form.java
 * 创建日期：2013-11-18
 */
package com.yondervision.mi.form;

/**
 * @author LinXiaolong
 * 
 */
public class AppApi30301Form extends AppApiCommonForm {
	/** 业务类型 **/
	private String bussinesstype;
	private String centerid;
	private String websiteCode;
	private String appobusiid;
	private String appostate;

	/**
	 * @return the bussinesstype
	 */
	public String getBussinesstype() {
		return bussinesstype;
	}

	/**
	 * @param bussinesstype
	 *            the bussinesstype to set
	 */
	public void setBussinesstype(String bussinesstype) {
		this.bussinesstype = bussinesstype;
	}

	public String getCenterid() {
		return centerid;
	}

	public void setCenterid(String centerid) {
		this.centerid = centerid;
	}

	public String getWebsiteCode() {
		return websiteCode;
	}

	public void setWebsiteCode(String websiteCode) {
		this.websiteCode = websiteCode;
	}

	public String getAppobusiid() {
		return appobusiid;
	}

	public void setAppobusiid(String appobusiid) {
		this.appobusiid = appobusiid;
	}

	public String getAppostate() {
		return appostate;
	}

	public void setAppostate(String appostate) {
		this.appostate = appostate;
	}
	
}
