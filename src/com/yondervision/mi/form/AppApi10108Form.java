package com.yondervision.mi.form;

/** 
* @ClassName: AppApi10108Form 
* @Description: 获取我的排号记录请求Form
* @author gongqi
* 
*/ 
public class AppApi10108Form extends AppApiCommonForm {
	private String websitecode = "";    //网点编号
	private String jobid = "";       //业务编号
	private String ticketno = "";    //排队号码
	private String idcardNumber = "";
	
	
	public String getWebsitecode() {
		return websitecode;
	}

	public void setWebsitecode(String websitecode) {
		this.websitecode = websitecode;
	}

	public String getJobid() {
		return jobid;
	}

	public void setJobid(String jobid) {
		this.jobid = jobid;
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
