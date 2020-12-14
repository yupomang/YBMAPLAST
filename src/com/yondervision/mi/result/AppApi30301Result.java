/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.result
 * 文件名：     AppApi30301Result.java
 * 创建日期：2013-11-18
 */
package com.yondervision.mi.result;

import java.util.List;

/**
 * @author LinXiaolong
 *
 */
public class AppApi30301Result {
	private AppApi30301ResultMap map;
	private List<TitleInfoBean> content;
	private List<TitleInfoBean> list;
	private String orgid;

	/**
	 * @return the map
	 */
	public AppApi30301ResultMap getMap() {
		return map;
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMap(AppApi30301ResultMap map) {
		this.map = map;
	}

	/**
	 * @return the content
	 */
	public List<TitleInfoBean> getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(List<TitleInfoBean> content) {
		this.content = content;
	}

	/**
	 * @return the list
	 */
	public List<TitleInfoBean> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<TitleInfoBean> list) {
		this.list = list;
	}

	/**
	 * @return the orgid
	 */
	public String getOrgid() {
		return orgid;
	}

	/**
	 * @param orgid
	 *            the orgid to set
	 */
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
}
