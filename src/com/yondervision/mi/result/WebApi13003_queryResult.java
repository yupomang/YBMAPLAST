package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi131;
import com.yondervision.mi.dto.Mi131WithBLOBs;

/** 
* @ClassName: WebApi13003_queryResult 
* @Description: TODO
* @author gongqi
* @date July 18, 2014 9:33:25 PM  
* 
*/ 
public class WebApi13003_queryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 总页数 **/
	private Integer totalPage;
	/** 素材信息数据 **/
	private List<Mi131WithBLOBs> list131;
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
	public List<Mi131WithBLOBs> getList131() {
		return list131;
	}
	public void setList131(List<Mi131WithBLOBs> list131) {
		this.list131 = list131;
	}
}
