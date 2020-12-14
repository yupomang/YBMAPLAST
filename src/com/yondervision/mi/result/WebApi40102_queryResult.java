package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi105;

public class WebApi40102_queryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 推送信息数据 **/
	private List<Mi105> list105;
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public List<Mi105> getList105() {
		return list105;
	}
	public void setList105(List<Mi105> list105) {
		this.list105 = list105;
	}
}
