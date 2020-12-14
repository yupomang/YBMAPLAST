package com.yondervision.mi.form;

/** 
* @ClassName: SendApiCommonForm 
* @Description: TODO
* @author Caozhongyan
* @date Feb 5, 2015 4:12:09 PM   
* 
*/ 
public class SendSelectApiCommonForm {
	/** 关键字 */						
	private String sendNo = "";						
	/** 姓名 */						
	private String miSeqno = "";						

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
	/** IP */
	private String longinip="";
	/** 业务类型 */
	private String buzType="";
	/** 发送方流水号 */
	private String sendSeqno = "";
	public String getSendNo() {
		return sendNo;
	}
	public void setSendNo(String sendNo) {
		this.sendNo = sendNo;
	}
	public String getMiSeqno() {
		return miSeqno;
	}
	public void setMiSeqno(String miSeqno) {
		this.miSeqno = miSeqno;
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
	public String getSendSeqno() {
		return sendSeqno;
	}
	public void setSendSeqno(String sendSeqno) {
		this.sendSeqno = sendSeqno;
	}
}
