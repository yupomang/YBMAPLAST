/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.result
 * 文件名：     PtlApi002PageQueryResult.java
 * 创建日期：2013-10-21
 */
package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.CMi107;
import com.yondervision.mi.dto.Mi009;
import com.yondervision.mi.dto.Mi010;
import com.yondervision.mi.dto.Mi107;

/**
 * @author gongqi
 *
 */
public class PtlApi002PageQueryResult {
	/** 总记录数 **/
	private Integer total;
	/** 每页显示条数 **/
	private Integer pageSize;
	/** 当前页码 **/
	private Integer pageNumber;
	/** 日志代码信息数据 **/
	private List<Mi009> mi009Rows;
	/** 错误代码信息数据 **/
	private List<Mi010> mi010Rows;
	/** APP业务日志信息数据 **/
	private List<Mi107> mi107Rows;
	/**MI业务日志信息数据 **/
	private List<CMi107> cmi107Rows;

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
	 * @return the mi009Rows
	 */
	public List<Mi009> getMi009Rows() {
		return mi009Rows;
	}
	/**
	 * @param mi009Rows the mi009Rows to set
	 */
	public void setMi009Rows(List<Mi009> mi009Rows) {
		this.mi009Rows = mi009Rows;
	}
	
	/**
	 * @return the mi010Rows
	 */
	public List<Mi010> getMi010Rows() {
		return mi010Rows;
	}
	/**
	 * @param mi010Rows the mi010Rows to set
	 */
	public void setMi010Rows(List<Mi010> mi010Rows) {
		this.mi010Rows = mi010Rows;
	}
	
	/**
	 * @return the mi107Rows
	 */
	public List<Mi107> getMi107Rows() {
		return mi107Rows;
	}
	
	/**
	 * @param mi107Rows the mi107Rows to set
	 */
	public void setMi107Rows(List<Mi107> mi107Rows) {
		this.mi107Rows = mi107Rows;
	}
	
	/**
	 * @return the cmi107Rows
	 */
	public List<CMi107> getCmi107Rows() {
		return cmi107Rows;
	}
	/**
	 * @param cmi107Rows the cmi107Rows to set
	 */
	public void setCmi107Rows(List<CMi107> cmi107Rows) {
		this.cmi107Rows = cmi107Rows;
	}
}
