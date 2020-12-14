package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi106;
import com.yondervision.mi.dto.Mi122;


public class WebApi12201_queryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 推送信息数据 **/
	private List<Mi122> list122;
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
	public List<Mi122> getList122() {
		return list122;
	}
	public void setList122(List<Mi122> list122) {
		this.list122 = list122;
	}
}
