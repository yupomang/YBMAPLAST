package com.yondervision.mi.form;

/** 
* @ClassName: WebApi70104Form 
* @Description: 报刊期次维护-查询请求Form
*/ 
public class WebApi70104Form extends WebApiCommonForm {
	
	private String qry_itemid;
	private Integer page;
	private Integer rows;

	/**
	 * @return the qry_itemid
	 */
	public String getQry_itemid() {
		return qry_itemid;
	}

	/**
	 * @param qryItemid the qry_itemid to set
	 */
	public void setQry_itemid(String qryItemid) {
		qry_itemid = qryItemid;
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
