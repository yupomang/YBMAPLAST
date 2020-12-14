/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.result
 * 文件名：     Page20101Result.java
 * 创建日期：2013-10-15
 */
package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi007;

/**
 * @author LinXiaolong
 *
 */
public class Page20101Result {
	/** 业务类型 **/
	private List<Mi007> consultType;
	/** 业务咨询子项图标 **/
	private List<Page20101ResultSubitemIcon> subitemIcon;

	/**
	 * @return the consultType
	 */
	public List<Mi007> getConsultType() {
		return consultType;
	}

	/**
	 * @param consultType
	 *            the consultType to set
	 */
	public void setConsultType(List<Mi007> consultType) {
		this.consultType = consultType;
	}

	/**
	 * @return the subitemIcon
	 */
	public List<Page20101ResultSubitemIcon> getSubitemIcon() {
		return subitemIcon;
	}

	/**
	 * @param subitemIcon
	 *            the subitemIcon to set
	 */
	public void setSubitemIcon(List<Page20101ResultSubitemIcon> subitemIcon) {
		this.subitemIcon = subitemIcon;
	}
}
