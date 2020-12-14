/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.form
 * 文件名：     WebApi30204Form.java
 * 创建日期：2013-12-3
 */
package com.yondervision.mi.form;

/**
 * @author LinXiaolong
 *
 */
public class WebApi30204Form extends WebApiCommonForm {
	/** 起始页 **/
	private Integer page;
	/** 每页显示条数 **/
	private Integer rows;
	/** 开始业务日期 **/
	private String startdate;
	/** 结束业务日期 **/
	private String enddate;
	private String commsgid;
	/** 标题 **/
	private String checktitle;
	/** 内容 **/
	private String checktext;
	
	public String getCommsgid() {
		return commsgid;
	}

	public void setCommsgid(String commsgid) {
		this.commsgid = commsgid;
	}

	/**
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * @param page
	 *            the page to set
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
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(Integer rows) {
		this.rows = rows;
	}

	/**
	 * @return the startdate
	 */
	public String getStartdate() {
		return startdate;
	}

	/**
	 * @param startdate the startdate to set
	 */
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	/**
	 * @return the enddate
	 */
	public String getEnddate() {
		return enddate;
	}

	/**
	 * @param enddate the enddate to set
	 */
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getChecktitle() {
		return checktitle;
	}

	public void setChecktitle(String checktitle) {
		this.checktitle = checktitle;
	}

	public String getChecktext() {
		return checktext;
	}

	public void setChecktext(String checktext) {
		this.checktext = checktext;
	}
}
