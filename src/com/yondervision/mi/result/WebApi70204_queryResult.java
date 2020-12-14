package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi701;

/** 
* @ClassName: WebApi70204_queryResult 
* @Description: TODO
* @author gongqi
* @date July 18, 2014 9:33:25 PM  
* 
*/ 
public class WebApi70204_queryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 总页数 **/
	private Integer totalPage;
	/** 新闻信息数据 **/
	private List<WebApi70204_NewsBean> list701;
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
	public List<WebApi70204_NewsBean> getList701() {
		return list701;
	}
	public void setList701(List<WebApi70204_NewsBean> list701) {
		this.list701 = list701;
	}
}
