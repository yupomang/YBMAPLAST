/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.form
 * 文件名：     WebApi20125_saveForm.java
 * 创建日期：2013-11-2
 */
package com.yondervision.mi.form;

import java.util.List;

/**
 * @author LinXiaolong
 * 
 */
public class WebApi20125_saveForm extends WebApiCommonForm {
	/** 问题内容ID **/
	private List<String> listConditionId;
	/** 向导内容ID **/
	private List<String> listConsultId;
	/** 咨询项目ID **/
	private String consultItemId;
	/** 业务咨询子项ID **/
	private String consultSubItemId;
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
	 * @return the listConsultId
	 */
	public List<String> getListConsultId() {
		return listConsultId;
	}

	/**
	 * @param listConsultId
	 *            the listConsultId to set
	 */
	public void setListConsultId(List<String> listConsultId) {
		this.listConsultId = listConsultId;
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
