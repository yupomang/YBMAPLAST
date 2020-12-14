package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi701;

public class NewsBean {
	private int total = 0;
	private int totalPage = 0;
	private int pageSize = 0;
	private int pageNumber = 0;
	private List<Mi701> newsList = null;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public List<Mi701> getNewsList() {
		return newsList;
	}
	public void setNewsList(List<Mi701> newsList) {
		this.newsList = newsList;
	}
}
