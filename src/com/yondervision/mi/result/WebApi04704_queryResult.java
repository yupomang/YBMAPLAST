package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi046;
import com.yondervision.mi.dto.Mi047;

public class WebApi04704_queryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 推送信息数据 **/
	private List<Mi047> list047;
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
	public List<Mi047> getList047() {
		return list047;
	}
	public void setList047(List<Mi047> list047) {
		this.list047 = list047;
	}
}
