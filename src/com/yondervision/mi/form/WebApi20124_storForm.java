/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.form
 * 文件名：     WebApi20124_storForm.java
 * 创建日期：2013-10-15
 */
package com.yondervision.mi.form;

/**
 * @author LinXiaolong
 *
 */
public class WebApi20124_storForm extends WebApiCommonForm {
	/** 向导步骤ID **/
	private String stepId;
	/** 上级向导内容ID **/
	private String parentId;
	/** 源顺序号 **/
	private Integer sourceOrderNo;
	/** 目标顺序号 **/
	private Integer targetOrderNo;

	/**
	 * @return the stepId
	 */
	public String getStepId() {
		return stepId;
	}

	/**
	 * @param stepId
	 *            the stepId to set
	 */
	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
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
