/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.result
 * 文件名：     AppApi20102Result.java
 * 创建日期：2013-10-22
 */
package com.yondervision.mi.result;

import java.util.List;

/**
 * @author LinXiaolong
 *
 */
public class AppApi20102Result {
	/** 公共条件项目名称 **/
	private String conditionitemname;
	/** 公共条件项目ID **/
	private String conditionitemid;
	/** 公共条件分组数据 **/
	private List<AppApi20102Result_group> group;

	/**
	 * @return the conditionitemname
	 */
	public String getConditionitemname() {
		return conditionitemname;
	}

	/**
	 * @param conditionitemname
	 *            the conditionitemname to set
	 */
	public void setConditionitemname(String conditionitemname) {
		this.conditionitemname = conditionitemname;
	}

	/**
	 * @return the conditionitemid
	 */
	public String getConditionitemid() {
		return conditionitemid;
	}

	/**
	 * @param conditionitemid
	 *            the conditionitemid to set
	 */
	public void setConditionitemid(String conditionitemid) {
		this.conditionitemid = conditionitemid;
	}

	/**
	 * @return the group
	 */
	public List<AppApi20102Result_group> getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            the group to set
	 */
	public void setGroup(List<AppApi20102Result_group> group) {
		this.group = group;
	}
}
