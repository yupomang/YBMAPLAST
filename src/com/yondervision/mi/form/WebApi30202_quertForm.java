/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.form
 * 文件名：     WebApi30202_quertForm.java
 * 创建日期：2013-10-19
 */
package com.yondervision.mi.form;

/**
 * @author LinXiaolong
 * 
 */
public class WebApi30202_quertForm extends WebApiCommonForm {
	/** 起始页 **/
	private Integer page;
	/** 每页显示条数 **/
	private Integer rows;
	/** 批次号 **/
	private String commsgid;
	/** 类型 **/
	private String transtype;
	private String pid;
	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
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

	public String getCommsgid() {
		return commsgid;
	}

	public void setCommsgid(String commsgid) {
		this.commsgid = commsgid;
	}

	public String getTranstype() {
		return transtype;
	}

	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}

	

	

}
