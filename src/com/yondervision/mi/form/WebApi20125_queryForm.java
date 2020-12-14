/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.form
 * 文件名：     WebApi20125_queryForm.java
 * 创建日期：2013-11-2
 */
package com.yondervision.mi.form;

import java.util.List;

/**
 * @author LinXiaolong
 *
 */
public class WebApi20125_queryForm extends WebApiCommonForm {
	/** 问题内容ID **/
	private List<String> listConditionId;
	/** 咨询项目ID **/
	private String consultItemId;
	/** 向导步骤ID **/
	private String stepId;

	/**
	 * @return the listConditionId
	 */
	public List<String> getListConditionId() {
		return listConditionId;
	}

	/**
	 * @param listConditionId
	 *            the listConditionId to set
	 */
	public void setListConditionId(List<String> listConditionId) {
		this.listConditionId = listConditionId;
	}

	/**
	 * @return the consultItemId
	 */
	public String getConsultItemId() {
		return consultItemId;
	}

	/**
	 * @param consultItemId
	 *            the consultItemId to set
	 */
	public void setConsultItemId(String consultItemId) {
		this.consultItemId = consultItemId;
	}

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
}
