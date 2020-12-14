/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.form
 * 文件名：     Page20122Form.java
 * 创建日期：2013-10-17
 */
package com.yondervision.mi.form;

/**
 * @author LinXiaolong
 *
 */
public class Page20122Form extends WebApiCommonForm {
	/** 业务咨询项目ID **/
	private String consultItemId;
	/** 公共条件组合ID **/
	private String conditionItemGroupId;

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
	 * @return the conditionItemGroupId
	 */
	public String getConditionItemGroupId() {
		return conditionItemGroupId;
	}

	/**
	 * @param conditionItemGroupId
	 *            the conditionItemGroupId to set
	 */
	public void setConditionItemGroupId(String conditionItemGroupId) {
		this.conditionItemGroupId = conditionItemGroupId;
	}
}
