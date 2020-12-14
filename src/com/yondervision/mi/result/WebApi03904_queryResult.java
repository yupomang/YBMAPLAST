package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi039;
import com.yondervision.mi.dto.Mi046;

public class WebApi03904_queryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 推送信息数据 **/
	private List<Mi039> list039;
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
	public List<Mi039> getList039() {
		return list039;
	}
	public void setList039(List<Mi039> list039) {
		this.list039 = list039;
	}
}
