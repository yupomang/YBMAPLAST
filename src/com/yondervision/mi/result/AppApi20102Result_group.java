/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.result
 * 文件名：     AppApi20102Result_group.java
 * 创建日期：2013-10-22
 */
package com.yondervision.mi.result;

import java.util.List;

/**
 * @author LinXiaolong
 * 
 */
public class AppApi20102Result_group {
	private String groupname;
	private String groupid;
	private List<AppApi20102Result_conditiongroups> conditiongroups;

	/**
	 * @return the groupname
	 */
	public String getGroupname() {
		return groupname;
	}

	/**
	 * @param groupname
	 *            the groupname to set
	 */
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	/**
	 * @return the groupid
	 */
	public String getGroupid() {
		return groupid;
	}

	/**
	 * @param groupid
	 *            the groupid to set
	 */
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	/**
	 * @return the conditiongroups
	 */
	public List<AppApi20102Result_conditiongroups> getConditiongroups() {
		return conditiongroups;
	}

	/**
	 * @param conditiongroups
	 *            the conditiongroups to set
	 */
	public void setConditiongroups(
			List<AppApi20102Result_conditiongroups> conditiongroups) {
		this.conditiongroups = conditiongroups;
	}

}
