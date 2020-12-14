package com.yondervision.mi.form;

/** 
* @ClassName: WebApi99901Form 
* @Description: 应聘信息查询请求Form
* @author gongqi
* @date July 16, 2014 9:09:35 PM   
* 
*/ 
public class WebApi99901Form extends WebApiCommonForm {
	private String applyareaQry;
	private String applypositionQry;
	private String startdate;
	private String enddate;
	private String isread;
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

	public String getApplyareaQry() {
		return applyareaQry;
	}
	public void setApplyareaQry(String applyareaQry) {
		this.applyareaQry = applyareaQry;
	}
	public String getApplypositionQry() {
		return applypositionQry;
	}
	public void setApplypositionQry(String applypositionQry) {
		this.applypositionQry = applypositionQry;
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
	public String getIsread() {
		return isread;
	}
	public void setIsread(String isread) {
		this.isread = isread;
	}
	
}
