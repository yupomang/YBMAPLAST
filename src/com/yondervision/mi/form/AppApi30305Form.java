/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.controller
 * 文件名：     AppApi30304Form.java
 * 创建日期：2013-11-18
 */
package com.yondervision.mi.form;

import com.yondervision.mi.form.AppApiCommonForm;

/**
 * @author LinXiaolong
 * 
 */
public class AppApi30305Form extends AppApiCommonForm {
	
	/** 日期 **/
	private String appodate;
	private String personalid;
	
	public String getAppodate() {
		return appodate;
	}
	public void setAppodate(String appodate) {
		this.appodate = appodate;
	}
	public String getPersonalid() {
		return personalid;
	}
	public void setPersonalid(String personalid) {
		this.personalid = personalid;
	}

}
