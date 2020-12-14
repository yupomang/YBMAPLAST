/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.form
 * 文件名：     WebApi20114_storForm.java
 * 创建日期：2013-10-15
 */
package com.yondervision.mi.form;

/**
 * @author LinXiaolong
 *
 */
public class WebApi20114_storForm extends WebApiCommonForm {
	/** 公共条件分组ID **/
	private String conditionGroupId;
	/** 源顺序号 **/
	private Integer sourceOrderNo;
	/** 目标顺序号 **/
	private Integer targetOrderNo;

	/**
	 * @return the conditionGroupId
	 */
	public String getConditionGroupId() {
		return conditionGroupId;
	}

	/**
	 * @param conditionGroupId
	 *            the conditionGroupId to set
	 */
	public void setConditionGroupId(String conditionGroupId) {
		this.conditionGroupId = conditionGroupId;
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
