package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi106;

/** 
* @ClassName: WebApi40304_queryResult 
* @Description: TODO
* @author Caozhongyan
* @date Oct 28, 2013 5:26:06 PM   
* 
*/ 
public class WebApi40304_queryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 推送信息数据 **/
	private List<Mi106> list106;
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
	public List<Mi106> getList106() {
		return list106;
	}
	public void setList106(List<Mi106> list106) {
		this.list106 = list106;
	}
}
