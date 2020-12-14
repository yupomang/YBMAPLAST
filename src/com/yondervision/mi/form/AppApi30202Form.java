/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.form
 * 文件名：     AppApi30202Form.java
 * 创建日期：2013-11-10
 */
package com.yondervision.mi.form;

/**
 * @author LinXiaolong
 *
 */
public class AppApi30202Form extends AppApiCommonForm {
	/** 要设置为已读的短信息ID **/
	private String messageid;

	/**
	 * @return the messageid
	 */
	public String getMessageid() {
		return messageid;
	}

	/**
	 * @param messageid the messageid to set
	 */
	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}
}
