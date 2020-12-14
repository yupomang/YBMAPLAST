/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.form
 * 文件名：     AppApi30201Form.java
 * 创建日期：2013-10-24
 */
package com.yondervision.mi.form;

/**
 * @author LinXiaolong
 *
 */
public class AppApi30201Form extends AppApiCommonForm {
	/** 要查询的记录条数 **/
	private Integer rowsum;
	/** 上次查询的最后一条信息ID **/
	private Long messageid;
	/**
	 * @return the rowsum
	 */
	public Integer getRowsum() {
		return rowsum;
	}
	/**
	 * @param rowsum the rowsum to set
	 */
	public void setRowsum(Integer rowsum) {
		this.rowsum = rowsum;
	}
	/**
	 * @return the messageid
	 */
	public Long getMessageid() {
		return messageid;
	}
	/**
	 * @param messageid the messageid to set
	 */
	public void setMessageid(Long messageid) {
		this.messageid = messageid;
	}
	
	
}
