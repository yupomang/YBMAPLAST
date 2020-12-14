package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi625;
import com.yondervision.mi.dto.Mi626;

public class WebApi62506_queryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 推送信息数据 **/
	private List<Mi625> list625;
	public List<Mi625> getList625() {
		return list625;
	}
	public void setList625(List<Mi625> list625) {
		this.list625 = list625;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
}
