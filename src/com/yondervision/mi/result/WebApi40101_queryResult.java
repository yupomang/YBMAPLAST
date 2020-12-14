package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi103;

/** 
* @ClassName: WebApi40101_queryResult 
* @Description: app用户查询
* @author Caozhongyan
* @date Oct 28, 2013 3:01:03 PM   
* 
*/ 
public class WebApi40101_queryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 推送信息数据 **/
	private List<Mi103> list103;
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
	public List<Mi103> getList103() {
		return list103;
	}
	public void setList103(List<Mi103> list103) {
		this.list103 = list103;
	}
}
