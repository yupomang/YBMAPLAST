/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.result
 * 文件名：     WebApi20125Result.java
 * 创建日期：2013-11-2
 */
package com.yondervision.mi.result;

/**
 * @author LinXiaolong
 *
 */
public class WebApi20125Result {
	/** 咨询内容 **/
	private String consultId;
	/** 是否可只读（true=只读;false=可编辑） **/
	private boolean isReadOnly;
	/** 此内容必选的条件项目序号 **/
	private String usedConditionItemOrderNo;
	/**
	 * @return the consultId
	 */
	public String getConsultId() {
		return consultId;
	}
	/**
	 * @param consultId the consultId to set
	 */
	public void setConsultId(String consultId) {
		this.consultId = consultId;
	}
	/**
	 * @return the isReadOnly
	 */
	public boolean isReadOnly() {
		return isReadOnly;
	}
	/**
	 * @param isReadOnly the isReadOnly to set
	 */
	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}
	/**
	 * @return the usedConditionItemOrderNo
	 */
	public String getUsedConditionItemOrderNo() {
		return usedConditionItemOrderNo;
	}
	/**
	 * @param usedConditionItemOrderNo the usedConditionItemOrderNo to set
	 */
	public void setUsedConditionItemOrderNo(String usedConditionItemOrderNo) {
		this.usedConditionItemOrderNo = usedConditionItemOrderNo;
	}
	
	
}
