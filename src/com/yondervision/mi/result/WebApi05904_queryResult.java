package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi059;

public class WebApi05904_queryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 总页数 **/
	private Integer totalPage;
	/** 推送信息数据 **/
	private List<Mi059> list059;
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
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public List<Mi059> getList059() {
		return list059;
	}
	public void setList059(List<Mi059> list059) {
		this.list059 = list059;
	}
}
