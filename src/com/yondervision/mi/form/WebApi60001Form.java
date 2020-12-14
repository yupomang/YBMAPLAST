package com.yondervision.mi.form;

/** 
* @ClassName: WebApi60001Form 
* @Description: 留言查询请求Form
* @author gongqi
* @date July 16, 2014 9:09:35 PM   
* 
*/ 
public class WebApi60001Form extends WebApiCommonForm {
	private String status;
	private String startdate;
	private String enddate;
	private String validflag;
	/** 起始页 **/
	private Integer page;
	/** 每页显示条数 **/
	private Integer rows;  
	  public Integer getPage() {
		return page==null?1:page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows==null?5:rows;
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
}
