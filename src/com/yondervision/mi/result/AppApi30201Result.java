/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.result
 * 文件名：     AppApi30201Result.java
 * 创建日期：2013-10-24
 */
package com.yondervision.mi.result;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LinXiaolong
 *
 */
public class AppApi30201Result {
	/** 信息ID **/
	private Long messageid;
	/** 信息标题 **/
	private String messagetitle;
	/** 信息时间（yyyy-MM-dd HH:mm:ss） **/
	private String messagetime;
	/** 信息内容 **/
	private String messagebody;
	/** 客服电话 **/
	private String phone;
	/** 图片下载地址 **/
	private List<String> image = new ArrayList<String>();
	/** 信息是否已读 **/
	private String isread;
	/** 消息类型 **/
	private String pusMessageType;
	/** 信息富文本内容 **/
	private String tsmsg;
	/** 信息富文本类型 **/
	private String tsmsgtype;
	/** 主ID **/
	private String msgid;
	/**	模板编号 */
	private String templateCode = "";
	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	/**
	 * @return the messageid
	 */
	public Long getMessageid() {
		return messageid;
	}

	/**
	 * @param messageid
	 *            the messageid to set
	 */
	public void setMessageid(Long messageid) {
		this.messageid = messageid;
	}

	/**
	 * @return the messagetitle
	 */
	public String getMessagetitle() {
		return messagetitle;
	}

	/**
	 * @param messagetitle
	 *            the messagetitle to set
	 */
	public void setMessagetitle(String messagetitle) {
		this.messagetitle = messagetitle;
	}

	/**
	 * @return the messagetime
	 */
	public String getMessagetime() {
		return messagetime;
	}

	/**
	 * @param messagetime
	 *            the messagetime to set
	 */
	public void setMessagetime(String messagetime) {
		this.messagetime = messagetime;
	}

	/**
	 * @return the messagebody
	 */
	public String getMessagebody() {
		return messagebody;
	}

	/**
	 * @param messagebody
	 *            the messagebody to set
	 */
	public void setMessagebody(String messagebody) {
		this.messagebody = messagebody;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the image
	 */
	public List<String> getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(List<String> image) {
		this.image = image;
	}

	/**
	 * @return the isread
	 */
	public String getIsread() {
		return isread;
	}

	/**
	 * @param isread the isread to set
	 */
	public void setIsread(String isread) {
		this.isread = isread;
	}

	/**
	 * @return the pusMessageType
	 */
	public String getPusMessageType() {
		return pusMessageType;
	}

	/**
	 * @param pusMessageType the pusMessageType to set
	 */
	public void setPusMessageType(String pusMessageType) {
		this.pusMessageType = pusMessageType;
	}

	public String getTsmsg() {
		return tsmsg;
	}

	public void setTsmsg(String tsmsg) {
		this.tsmsg = tsmsg;
	}

	public String getTsmsgtype() {
		return tsmsgtype;
	}

	public void setTsmsgtype(String tsmsgtype) {
		this.tsmsgtype = tsmsgtype;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	
}
