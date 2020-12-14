package com.yondervision.mi.form;

import java.util.List;

/** 
* @ClassName: SendApiCommonForm 
* @Description: TODO
* @author Caozhongyan
* @date Feb 5, 2015 4:12:09 PM   
* 
*/ 
public class SendBatchApiCommonForm {
	/** 交易代码 */					
	private String transCode = "";					
	/** 发送日期 */					
	private String sendDate = "";					
	/** 发送时间 */					
	private String sendTime = "";					
	/** 发送方流水号 */					
	private String sendSeqno = "";					
	/** 安全标识 */					
	private String key = "";					
	/** 操作员IP */					
	private String longinip = "";					
	/** 业务类型 */					
	private String buzType = "";
	/** 关键字 */						
	private String filename = "";						
	/** 姓名 */						
	private String count = "";
	/** 主题 */						
	private String theme = "";
	/** 渠道 */						
	private String chanel = "";
	
	/** 本期缴存（含转入） */						
	private String freeuse1 = "";			
	/** 本期提取 */						
	private String freeuse2 = "";						
	/** 利息 */						
	private String freeuse3 = "";
	/** 城市中心ID */						
	private String centerId = "";
	/** 城市中心 */						
	private String centreName = "";
	/** 城市中心 */						
	private String username = "";
	/** 用户 */						
	private String userId = "";
	
	private String mslist;

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getSendSeqno() {
		return sendSeqno;
	}

	public void setSendSeqno(String sendSeqno) {
		this.sendSeqno = sendSeqno;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLonginip() {
		return longinip;
	}

	public void setLonginip(String longinip) {
		this.longinip = longinip;
	}

	public String getBuzType() {
		return buzType;
	}

	public void setBuzType(String buzType) {
		this.buzType = buzType;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getFreeuse1() {
		return freeuse1;
	}

	public void setFreeuse1(String freeuse1) {
		this.freeuse1 = freeuse1;
	}

	public String getFreeuse2() {
		return freeuse2;
	}

	public void setFreeuse2(String freeuse2) {
		this.freeuse2 = freeuse2;
	}

	public String getFreeuse3() {
		return freeuse3;
	}

	public void setFreeuse3(String freeuse3) {
		this.freeuse3 = freeuse3;
	}

	public String getCenterId() {
		return centerId;
	}

	public void setCenterId(String centerId) {
		this.centerId = centerId;
	}

	public String getCentreName() {
		return centreName;
	}

	public void setCentreName(String centreName) {
		this.centreName = centreName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMslist() {
		return mslist;
	}

	public void setMslist(String mslist) {
		this.mslist = mslist;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getChanel() {
		return chanel;
	}

	public void setChanel(String chanel) {
		this.chanel = chanel;
	}
	
	
	
}
