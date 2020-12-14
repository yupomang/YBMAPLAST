package com.yondervision.mi.form;

/** 
* @ClassName: AppApi10107Form 
* @Description: 排队取号请求Form
* @author gongqi
* 
*/ 
public class AppApi10107Form extends AppApiCommonForm {
	private String websitecode = "";    //网点编号
	private String jobid = "";       //业务编号
	private String idcardNumber = "";
	private String getMethod = "";
	private String ticketno = "";    //排队号码
	private String ordertype = "";   //预约排号类型：1-预约排号，2-普通排号
	
	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public String getTicketno() {
		return ticketno;
	}

	public void setTicketno(String ticketno) {
		this.ticketno = ticketno;
	}

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

	public String getIdcardNumber() {
		return idcardNumber;
	}

	public void setIdcardNumber(String idcardNumber) {
		this.idcardNumber = idcardNumber;
	}

	public String getGetMethod() {
		return getMethod;
	}

	public void setGetMethod(String getMethod) {
		this.getMethod = getMethod;
	}
	
}
