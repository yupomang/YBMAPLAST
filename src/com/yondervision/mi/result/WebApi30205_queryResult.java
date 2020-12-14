/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.result
 * 文件名：     WebApi30202_queryResult.java
 * 创建日期：2013-10-19
 */
package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi402;
import com.yondervision.mi.dto.Mi404;

/**
 * @author LinXiaolong
 *
 */
public class WebApi30205_queryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 推送信息数据 **/
	private List<Mi404> rows;
	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * @return the pageNumber
	 */
	public Integer getPageNumber() {
		return pageNumber;
	}
	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	/**
	 * @return the rows
	 */
	public List<Mi404> getRows() {
		return rows;
	}
	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<Mi404> rows) {
		this.rows = rows;
	}
	
	
}
