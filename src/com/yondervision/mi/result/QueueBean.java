package com.yondervision.mi.result;

/**
 * 排队信息bean
 * @author gongqi
 */
public class QueueBean {
	private String type = "";
	private String websitecode = "";
	private String jobid = "";
	private String jobname = "";
	private String waitcount = "";
	private String result = "";
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getJobname() {
		return jobname;
	}
	public void setJobname(String jobname) {
		this.jobname = jobname;
	}
	public String getWaitcount() {
		return waitcount;
	}
	public void setWaitcount(String waitcount) {
		this.waitcount = waitcount;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
