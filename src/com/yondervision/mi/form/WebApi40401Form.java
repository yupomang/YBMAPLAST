package com.yondervision.mi.form;

/** 
* @ClassName: WebApi40401Form 
* @Description: 意见反馈请求Form
* @author Caozhongyan
* @date Oct 5, 2013 9:09:35 PM   
* 
*/ 
public class WebApi40401Form extends WebApiCommonForm {
	private String devType;
	private String status;
	private String startdate;
	private String enddate;
	private String validflag;
	/** 中心ID **/
	private String centerid;
	/** 版本编号 **/
	private String versionno;
	/** 起始页 **/
	private Integer page;
	/** 每页显示条数 **/
	private Integer rows;  
	  public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public String getValidflag() {
		return validflag;
	}
	public void setValidflag(String validflag) {
		this.validflag = validflag;
	}
	public String getDevType() {
		return devType;
	}
	public void setDevType(String devType) {
		this.devType = devType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getCenterid() {
		return centerid;
	}
	public void setCenterid(String centerid) {
		this.centerid = centerid;
	}
	public String getVersionno() {
		return versionno;
	}
	public void setVersionno(String versionno) {
		this.versionno = versionno;
	}
}
