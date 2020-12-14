package com.yondervision.mi.form;

/** 
* @ClassName: WebApi70001Form 
* @Description: 新闻动态请求Form
* @author gongqi
* @date July 18, 2014 9:09:35 PM   
* 
*/ 
public class WebApi70004Form extends WebApiCommonForm {
	private String classificationQry;
	private String startdate;
	private String enddate;
	private Integer page;
	private Integer rows;
	private String keyword;
	
	private String pubStatusQry;
	private String pubQudaoQry;
	private String sourceQry;
	
	public String getClassificationQry() {
		return classificationQry;
	}
	public void setClassificationQry(String classificationQry) {
		this.classificationQry = classificationQry;
	}
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
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getPubStatusQry() {
		return pubStatusQry;
	}
	public void setPubStatusQry(String pubStatusQry) {
		this.pubStatusQry = pubStatusQry;
	}
	public String getSourceQry() {
		return sourceQry;
	}
	public void setSourceQry(String sourceQry) {
		this.sourceQry = sourceQry;
	}
	public String getPubQudaoQry() {
		return pubQudaoQry;
	}
	public void setPubQudaoQry(String pubQudaoQry) {
		this.pubQudaoQry = pubQudaoQry;
	}
	
}
