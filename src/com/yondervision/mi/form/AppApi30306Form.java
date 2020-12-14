/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.form
 * 文件名：     AppApi30306Form.java
 * 创建日期：2013-11-18
 */
package com.yondervision.mi.form;

/**
 * @author LinXiaolong
 *
 */
public class AppApi30306Form extends AppApiCommonForm {
	/** 预约ID **/
	private String appointid;
	
	private String pid;
	private String pidname;
	private String channelname;
	/**
	 * @return the appointid
	 */
	public String getAppointid() {
		return appointid;
	}

	/**
	 * @param appointid
	 *            the appointid to set
	 */
	public void setAppointid(String appointid) {
		this.appointid = appointid;
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
