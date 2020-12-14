/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.form
 * 文件名：     WebApi20103_storForm.java
 * 创建日期：2013-10-15
 */
package com.yondervision.mi.form;

/**
 * @author LinXiaolong
 * 
 */
public class WebApi20103_storForm extends WebApiCommonForm {
	/** 业务类型编码 **/
	private String consultType;
	/** 源顺序号 **/
	private Integer sourceOrderNo;
	/** 目标顺序号 **/
	private Integer targetOrderNo;

	/**
	 * @return the consultType
	 */
	public String getConsultType() {
		return consultType;
	}

	/**
	 * @param consultType
	 *            the consultType to set
	 */
	public void setConsultType(String consultType) {
		this.consultType = consultType;
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
