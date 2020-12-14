package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi626;

/** 
* @ClassName: WebApi62604_queryResult 
* @Description: TODO
* @author Lihongjie
* @date 2014-08-04 9:30   
* 
*/
public class WebApi62604_queryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 推送信息数据 **/
	private List<Mi626> list626;
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
	public List<Mi626> getList626() {
		return list626;
	}
	public void setList626(List<Mi626> list626) {
		this.list626 = list626;
	}
}
