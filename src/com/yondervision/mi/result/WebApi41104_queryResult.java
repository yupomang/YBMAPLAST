package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi120;
import com.yondervision.mi.dto.Mi203;

/** 
* @ClassName: WebApi20304_queryResult 
* @Description: TODO
* @author Caozhongyan
* @date Oct 28, 2013 9:55:51 AM   
* 
*/ 
public class WebApi41104_queryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 推送信息数据 **/
	private List<Mi120> list120;
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
	public List<Mi120> getList120() {
		return list120;
	}
	public void setList120(List<Mi120> list120) {
		this.list120 = list120;
	}
}
