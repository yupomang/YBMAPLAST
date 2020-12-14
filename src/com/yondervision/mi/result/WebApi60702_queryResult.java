package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi607;

public class WebApi60702_queryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 推送信息数据 **/
	private List<Mi607> list607;
	
	private List<WebApi607Result> resultList;
	
	public List<Mi607> getList607() {
		return list607;
	}
	public void setList607(List<Mi607> list607) {
		this.list607 = list607;
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
	public List<WebApi607Result> getResultList() {
		return resultList;
	}
	public void setResultList(List<WebApi607Result> resultList) {
		this.resultList = resultList;
	}
}
