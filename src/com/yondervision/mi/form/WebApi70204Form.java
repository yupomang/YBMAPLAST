package com.yondervision.mi.form;

/** 
* @ClassName: WebApi70204Form 
* @Description: 新闻发布-分页查询请求Form
*/ 
public class WebApi70204Form extends WebApiCommonForm {
	private String classificationQry;
	private String newspaperforumQry;
	private String newspapercolumnsQry;
	private Integer page;
	private Integer rows;
	private String keyword;
	
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
	public String getNewspaperforumQry() {
		return newspaperforumQry;
	}
	public void setNewspaperforumQry(String newspaperforumQry) {
		this.newspaperforumQry = newspaperforumQry;
	}
	public String getNewspapercolumnsQry() {
		return newspapercolumnsQry;
	}
	public void setNewspapercolumnsQry(String newspapercolumnsQry) {
		this.newspapercolumnsQry = newspapercolumnsQry;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
