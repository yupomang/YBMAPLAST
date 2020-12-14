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
public class AppApi30304Form extends AppApiCommonForm {
	/** 网点ID **/
	private String appobranchid;
	/**
	 * 网点名称
	 */
	private String appobranchname;
	/**
	 * 时段id
	 */
	private String appotpldetailid;
	/**
	 * 电话
	 */
	private String phone;

	/**
	 * 业务id
	 */
	private String appobusiid;
	/** 日期 **/
	private String appodate;
	/** 时间段 **/
	private String timeinterval;
	/** 业务名称 **/
	private String appobusiname;
	/** 微信昵称 **/
	private String nickname;
	private String personalid;
	private String pid;
	private String pidname;
	private String channelname;
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getAppobranchid() {
		return appobranchid;
	}
	public void setAppobranchid(String appobranchid) {
		this.appobranchid = appobranchid;
	}
	public String getAppobranchname() {
		return appobranchname;
	}
	public void setAppobranchname(String appobranchname) {
		this.appobranchname = appobranchname;
	}
	public String getAppotpldetailid() {
		return appotpldetailid;
	}
	public void setAppotpldetailid(String appotpldetailid) {
		this.appotpldetailid = appotpldetailid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public String getTimeinterval() {
		return timeinterval;
	}
	public void setTimeinterval(String timeinterval) {
		this.timeinterval = timeinterval;
	}
	public String getAppobusiname() {
		return appobusiname;
	}
	public void setAppobusiname(String appobusiname) {
		this.appobusiname = appobusiname;
	}
	public String getPersonalid() {
		return personalid;
	}
	public void setPersonalid(String personalid) {
		this.personalid = personalid;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPidname() {
		return pidname;
	}
	public void setPidname(String pidname) {
		this.pidname = pidname;
	}
	public String getChannelname() {
		return channelname;
	}
	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}

}
