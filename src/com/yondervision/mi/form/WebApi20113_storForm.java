/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.form
 * 文件名：     WebApi20113_storForm.java
 * 创建日期：2013-10-15
 */
package com.yondervision.mi.form;

/**
 * @author LinXiaolong
 * 
 */
public class WebApi20113_storForm extends WebApiCommonForm {
	/** 公共条件项目ID **/
	private String conditionItemId;
	/** 源顺序号 **/
	private Integer sourceOrderNo;
	/** 目标顺序号 **/
	private Integer targetOrderNo;

	/**
	 * @return the conditionItemId
	 */
	public String getConditionItemId() {
		return conditionItemId;
	}

	/**
	 * @param conditionItemId
	 *            the conditionItemId to set
	 */
	public void setConditionItemId(String conditionItemId) {
		this.conditionItemId = conditionItemId;
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
