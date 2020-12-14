/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.form
 * 文件名：     AppApi30201Form.java
 * 创建日期：2013-10-24
 */
package com.yondervision.mi.form;

/**
 * @author LinXiaolong
 *
 */
public class AppApi30203Form extends AppApiCommonForm {
	/** 主ID **/
	private String msgid;
	/** 上次查询的最后一条信息ID **/
	private Long messageid;
	
	public Long getMessageid() {
		return messageid;
	}

	public void setMessageid(Long messageid) {
		this.messageid = messageid;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}	
	
}
