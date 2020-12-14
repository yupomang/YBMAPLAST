package com.yondervision.mi.form;

public class WebApi05801Form extends WebApiCommonForm {
	private String startdate;
	private String enddate;
	////对应 "-1":"不满意","1":"基本满意","2":"满意","-":"未评价"
	private String selecttype;
	private String centerid;
	private String agentinstmsg;//网店
	public String getStartdate() {
		return startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public String getSelecttype() {
		return selecttype;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public void setSelecttype(String selecttype) {
		this.selecttype = selecttype;
	}
	public String getCenterid() {
		return centerid;
	}
	public void setCenterid(String centerid) {
		this.centerid = centerid;
	}
	public String getAgentinstmsg() {
		return agentinstmsg;
	}
	public void setAgentinstmsg(String agentinstmsg) {
		this.agentinstmsg = agentinstmsg;
	}
	
}
