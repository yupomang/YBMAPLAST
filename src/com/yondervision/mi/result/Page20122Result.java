/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.result
 * 文件名：     Page20122Result.java
 * 创建日期：2013-10-17
 */
package com.yondervision.mi.result;

import java.util.List;

/**
 * 取条件项目与条件分组结果集
 * 
 * @author LinXiaolong
 * 
 */
public class Page20122Result {
	/** 公共条件项目ID **/
	private String conditionItemId;
	/** 公共条件项目名称 **/
	private String conditionItemName;
	/** 此项目下的公共条件分组 **/
	private List<Page20122Result_children> children;

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
	 * @return the conditionItemName
	 */
	public String getConditionItemName() {
		return conditionItemName;
	}

	/**
	 * @param conditionItemName
	 *            the conditionItemName to set
	 */
	public void setConditionItemName(String conditionItemName) {
		this.conditionItemName = conditionItemName;
	}

	/**
	 * @return the children
	 */
	public List<Page20122Result_children> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<Page20122Result_children> children) {
		this.children = children;
	}

	
}
