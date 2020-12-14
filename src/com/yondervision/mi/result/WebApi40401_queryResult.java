package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi108;

/** 
* @ClassName: WebApi40401_queryResult 
* @Description: TODO
* @author Caozhongyan
* @date Oct 28, 2013 8:41:52 PM   
* 
*/ 
public class WebApi40401_queryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 推送信息数据 **/
	private List<Mi108> list108;
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
	public List<Mi108> getList108() {
		return list108;
	}
	public void setList108(List<Mi108> list108) {
		this.list108 = list108;
	}
}
