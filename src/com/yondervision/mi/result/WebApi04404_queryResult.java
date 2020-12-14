package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi040;
import com.yondervision.mi.dto.Mi041;
import com.yondervision.mi.dto.Mi042;
import com.yondervision.mi.dto.Mi044;

public class WebApi04404_queryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 推送信息数据 **/
	private List<Mi044> list044;
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
	public List<Mi044> getList044() {
		return list044;
	}
	public void setList044(List<Mi044> list044) {
		this.list044 = list044;
	}
}
