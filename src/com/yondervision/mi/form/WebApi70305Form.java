package com.yondervision.mi.form;

/** 
* @ClassName: WebApi70305Form 
* @Description: 报刊-新闻评论维护-新闻标题查询请求Form
*/ 
public class WebApi70305Form extends WebApiCommonForm {
	
	private String classification;
	private String newspaperforum;
	private String newspapercolumns;
	private String title;
	private Integer page;
	private Integer rows;

	/**
	 * @return the classification
	 */
	public String getClassification() {
		return classification;
	}

	/**
	 * @param classification the classification to set
	 */
	public void setClassification(String classification) {
		this.classification = classification;
	}

	/**
	 * @return the newspaperforum
	 */
	public String getNewspaperforum() {
		return newspaperforum;
	}

	/**
	 * @param newspaperforum the newspaperforum to set
	 */
	public void setNewspaperforum(String newspaperforum) {
		this.newspaperforum = newspaperforum;
	}

	/**
	 * @return the newspapercolumns
	 */
	public String getNewspapercolumns() {
		return newspapercolumns;
	}

	/**
	 * @param newspapercolumns the newspapercolumns to set
	 */
	public void setNewspapercolumns(String newspapercolumns) {
		this.newspapercolumns = newspapercolumns;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * @return the rows
	 */
	public Integer getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	
}
