package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi203;

/** 
* @ClassName: WebApi20304_queryResult 
* @Description: TODO
* @author Caozhongyan
* @date Oct 28, 2013 9:55:51 AM   
* 
*/ 
public class WebApi20304_queryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 推送信息数据 **/
	private List<Mi203> list203;
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
	public List<Mi203> getList203() {
		return list203;
	}
	public void setList203(List<Mi203> list203) {
		this.list203 = list203;
	}
}
