/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.result
 * 文件名：     AppApi30305Result.java
 * 创建日期：2013-11-18
 */
package com.yondervision.mi.result;

import java.util.List;

/**
 * @author LinXiaolong
 *
 */
public class AppApi30305Result {
	private String appointid;
	private List<TitleInfoBean> appointmes;

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

	/**
	 * @return the appointmes
	 */
	public List<TitleInfoBean> getAppointmes() {
		return appointmes;
	}

	/**
	 * @param appointmes
	 *            the appointmes to set
	 */
	public void setAppointmes(List<TitleInfoBean> appointmes) {
		this.appointmes = appointmes;
	}
}
