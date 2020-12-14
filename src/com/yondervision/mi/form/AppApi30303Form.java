/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.form
 * 文件名：     AppApi30303Form.java
 * 创建日期：2013-11-18
 */
package com.yondervision.mi.form;

/**
 * @author LinXiaolong
 *
 */
public class AppApi30303Form extends AppApiCommonForm{
	/** 网点ID **/  
	private String appobranchid;
	/** 业务id **/
	private String appobusiid;
	/** 日期 **/
	private String appodate;
	public String getAppobranchid() {
		return appobranchid;
	}
	public void setAppobranchid(String appobranchid) {
		this.appobranchid = appobranchid;
	}
	public String getAppobusiid() {
		return appobusiid;
	}
	public void setAppobusiid(String appobusiid) {
		this.appobusiid = appobusiid;
	}
	public String getAppodate() {
		return appodate;
	}
	public void setAppodate(String appodate) {
		this.appodate = appodate;
	}

	
}
