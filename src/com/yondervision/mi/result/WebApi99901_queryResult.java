package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.JobOffers;

/** 
* @ClassName: WebApi99901_queryResult 
* @Description: TODO
* @author gongqi
* 
*/ 
public class WebApi99901_queryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 总页数 **/
	private Integer totalPage;
	/** 应聘信息数据 **/
	private List<JobOffers> listJobOffers;
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
	public List<JobOffers> getListJobOffers() {
		return listJobOffers;
	}
	public void setListJobOffers(List<JobOffers> listJobOffers) {
		this.listJobOffers = listJobOffers;
	}

}
