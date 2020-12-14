package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi057;

public class WebApi05701_queryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 推送信息数据 **/
	private List<Mi057> list057;
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
	public List<Mi057> getList057() {
		return list057;
	}
	public void setList057(List<Mi057> list057) {
		this.list057 = list057;
	}
}
