/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.result
 * 文件名：     Page20122Result_chidren.java
 * 创建日期：2013-10-6
 */
package com.yondervision.mi.result;

import java.util.ArrayList;
import java.util.List;

/**
 * 公共条件分组数据
 * 
 * @author LinXiaolong
 * 
 */
public class Page20122Result_children {
	/** 公共条件分组ID **/
	private String conditionGroupId;
	/** 公共条件分组名称 **/
	private String groupName;
	/** 默认选中 **/
	private boolean selected;
	/** 此分组下的公共条件 **/
	private List<Page20122Result_children_children> children = new ArrayList<Page20122Result_children_children>();

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
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * @return the children
	 */
	public List<Page20122Result_children_children> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<Page20122Result_children_children> children) {
		this.children = children;
	}

	
}
