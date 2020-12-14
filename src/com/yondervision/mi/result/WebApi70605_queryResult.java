package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi701;

public class WebApi70605_queryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 总页数 **/
	private Integer totalPage;
	/** 新闻评论数据 **/
	private List<Mi701> list701;
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
	public List<Mi701> getList701() {
		return list701;
	}
	public void setList701(List<Mi701> list701) {
		this.list701 = list701;
	}
}
