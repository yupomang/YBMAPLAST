/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.form
 * 文件名：     WebApi20122_storForm.java
 * 创建日期：2013-10-15
 */
package com.yondervision.mi.form;

/**
 * @author LinXiaolong
 *
 */
public class WebApi20122_storForm extends WebApiCommonForm {
	/** 业务咨询子项ID **/
	private String consultSubItemId;
	/** 源顺序号 **/
	private Integer sourceOrderNo;
	/** 目标顺序号 **/
	private Integer targetOrderNo;

	/**
	 * @return the consultSubItemId
	 */
	public String getConsultSubItemId() {
		return consultSubItemId;
	}

	/**
	 * @param consultSubItemId
	 *            the consultSubItemId to set
	 */
	public void setConsultSubItemId(String consultSubItemId) {
		this.consultSubItemId = consultSubItemId;
	}

	/**
	 * @return the sourceOrderNo
	 */
	public Integer getSourceOrderNo() {
		return sourceOrderNo;
	}

	/**
	 * @param sourceOrderNo
	 *            the sourceOrderNo to set
	 */
	public void setSourceOrderNo(Integer sourceOrderNo) {
		this.sourceOrderNo = sourceOrderNo;
	}

	/**
	 * @return the targetOrderNo
	 */
	public Integer getTargetOrderNo() {
		return targetOrderNo;
	}

	/**
	 * @param targetOrderNo
	 *            the targetOrderNo to set
	 */
	public void setTargetOrderNo(Integer targetOrderNo) {
		this.targetOrderNo = targetOrderNo;
	}
}
