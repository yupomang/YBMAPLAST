package com.yondervision.mi.result;


/** 
* @ClassName: AppApi10107Result 
* @Description: 
* @author gongqi
* 
*/ 
public class AppApi10107Result {
	private QueueTicketInfo queueinfo = null;
	private String tips = "";
	public QueueTicketInfo getQueueinfo() {
		return queueinfo;
	}
	public void setQueueinfo(QueueTicketInfo queueinfo) {
		this.queueinfo = queueinfo;
	}
	public String getTips() {
		return tips;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}
}