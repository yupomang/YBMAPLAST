package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi601;

/** 
* @ClassName: WebApi60001_queryResult 
* @Description: TODO
* @author gongqi
* @date July 16, 2014 9:33:25 PM  
* 
*/ 
public class WebApi60001_queryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 总页数 **/
	private Integer totalPage;
	/** 推送信息数据 **/
	private List<Mi601> list601;
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
	public List<Mi601> getList601() {
		return list601;
	}
	public void setList601(List<Mi601> list601) {
		this.list601 = list601;
	}
}
