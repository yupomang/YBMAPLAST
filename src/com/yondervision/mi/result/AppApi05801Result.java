package com.yondervision.mi.result;

public class AppApi05801Result {
	/** ID*/
	private String id = "";
	/** 交易日期*/
	private String transdate = "";
	/** 业务类型码*/
	private String tradetype = "";
	/** 业务类型描述*/
	private String trademsg = "";
	/** 办理机构码*/
	private String agentinstcode = "";
	/** 办理机构描述*/
	private String agentinstmsg = "";
	/** 评价内容*/
	private String freedata = "";
	/** 评价类型*/
	private String freeflag = "";
	/** 评价日期*/
	private String freedate = "";
	
	private String freeuse1 = "";
	/** 业务详细描述*/
	private String detail = "";
	/** 柜员号*/
	private String counternum = "";
	/** 柜员名*/
	private String countername = "";
	
	public String getDetail() {
		return detail;
	}
	public String getCounternum() {
		return counternum;
	}
	public String getCountername() {
		return countername;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public void setCounternum(String counternum) {
		this.counternum = counternum;
	}
	public void setCountername(String countername) {
		this.countername = countername;
	}
	public String getFreeuse1() {
		return freeuse1;
	}
	public void setFreeuse1(String freeuse1) {
		this.freeuse1 = freeuse1;
	}
	public String getId() {
		return id;
	}
	public String getTransdate() {
		return transdate;
	}
	public String getTradetype() {
		return tradetype;
	}
	public String getTrademsg() {
		return trademsg;
	}
	public String getAgentinstcode() {
		return agentinstcode;
	}
	public String getAgentinstmsg() {
		return agentinstmsg;
	}
	public String getFreedata() {
		return freedata;
	}
	public String getFreeflag() {
		return freeflag;
	}
	public String getFreedate() {
		return freedate;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}
	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}
	public void setTrademsg(String trademsg) {
		this.trademsg = trademsg;
	}
	public void setAgentinstcode(String agentinstcode) {
		this.agentinstcode = agentinstcode;
	}
	public void setAgentinstmsg(String agentinstmsg) {
		this.agentinstmsg = agentinstmsg;
	}
	public void setFreedata(String freedata) {
		this.freedata = freedata;
	}
	public void setFreeflag(String freeflag) {
		this.freeflag = freeflag;
	}
	public void setFreedate(String freedate) {
		this.freedate = freedate;
	}
	
}
