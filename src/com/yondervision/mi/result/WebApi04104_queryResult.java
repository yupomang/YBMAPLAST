package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi040;
import com.yondervision.mi.dto.Mi041;

public class WebApi04104_queryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 推送信息数据 **/
	private List<Mi041> list041;
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
	public List<Mi041> getList041() {
		return list041;
	}
	public void setList041(List<Mi041> list041) {
		this.list041 = list041;
	}
}
