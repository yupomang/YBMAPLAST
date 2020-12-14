package com.yondervision.mi.form;

/** 
* @ClassName: WebApi70305Form 
* @Description: 报刊-新闻评论维护-新闻评论（根据新闻SEQNO）查询请求Form
*/ 
public class WebApi70306Form extends WebApiCommonForm {
	
	private String newsseqno;
	private Integer page;
	private Integer rows;

	/**
	 * @return the newsseqno
	 */
	public String getNewsseqno() {
		return newsseqno;
	}

	/**
	 * @param newsseqno the newsseqno to set
	 */
	public void setNewsseqno(String newsseqno) {
		this.newsseqno = newsseqno;
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
