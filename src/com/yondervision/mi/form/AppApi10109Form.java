package com.yondervision.mi.form;

/** 
* @ClassName: AppApi10109Form 
* @Description: 获取排队号码当前状态请求Form
* @author gongqi
* 
*/ 
public class AppApi10109Form extends AppApiCommonForm {
	private String websitecode = "";
	private String ticketno = "";
	private String idcardNumber = "";

	public String getWebsitecode() {
		return websitecode;
	}

	public void setWebsitecode(String websitecode) {
		this.websitecode = websitecode;
	}

	public String getTicketno() {
		return ticketno;
	}

	public void setTicketno(String ticketno) {
		this.ticketno = ticketno;
	}

	public String getIdcardNumber() {
		return idcardNumber;
	}

	public void setIdcardNumber(String idcardNumber) {
		this.idcardNumber = idcardNumber;
	}
}
