package com.yondervision.mi.dto;

public class CMi626 extends Mi626 {

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
	
}
