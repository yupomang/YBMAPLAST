package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi040;

/** 
* @ClassName: WebApi20104_queryResult 
* @Description: TODO
* @author Caozhongyan
* @date Oct 28, 2013 10:59:35 AM   
* 
*/ 
public class WebApi04004_queryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 推送信息数据 **/
	private List<Mi040> list040;
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
	public List<Mi040> getList040() {
		return list040;
	}
	public void setList040(List<Mi040> list040) {
		this.list040 = list040;
	}
}
