package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi201;

/** 
* @ClassName: WebApi20104_queryResult 
* @Description: TODO
* @author Caozhongyan
* @date Oct 28, 2013 10:59:35 AM   
* 
*/ 
public class WebApi20104_queryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 推送信息数据 **/
	private List<Mi201> list201;
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
	public List<Mi201> getList201() {
		return list201;
	}
	public void setList201(List<Mi201> list201) {
		this.list201 = list201;
	}
}
