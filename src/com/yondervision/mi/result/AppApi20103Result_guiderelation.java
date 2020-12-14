/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.result
 * 文件名：     AppApi20103Result_guiderelation.java
 * 创建日期：2013-10-22
 */
package com.yondervision.mi.result;

import java.util.List;

/**
 * @author LinXiaolong
 *
 */
public class AppApi20103Result_guiderelation {
	private String consultid;
	private String parentid;
	private String detail;
	private String isleafflg;
	private List<AppApi20103Result_guiderelation> children;

	/**
	 * @return the consultid
	 */
	public String getConsultid() {
		return consultid;
	}

	/**
	 * @param consultid
	 *            the consultid to set
	 */
	public void setConsultid(String consultid) {
		this.consultid = consultid;
	}

	/**
	 * @return the parentid
	 */
	public String getParentid() {
		return parentid;
	}

	/**
	 * @param parentid
	 *            the parentid to set
	 */
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * @param detail
	 *            the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * @return the isleafflg
	 */
	public String getIsleafflg() {
		return isleafflg;
	}

	/**
	 * @param isleafflg
	 *            the isleafflg to set
	 */
	public void setIsleafflg(String isleafflg) {
		this.isleafflg = isleafflg;
	}

	/**
	 * @return the children
	 */
	public List<AppApi20103Result_guiderelation> getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public void setChildren(List<AppApi20103Result_guiderelation> children) {
		this.children = children;
	}
}
