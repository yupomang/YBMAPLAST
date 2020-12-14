/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.form
 * 文件名：     YfApi30201Form.java
 * 创建日期：2013-11-4
 */
package com.yondervision.mi.form;

/**
 * @author LinXiaolong
 * 
 */
public class YfApi30201Form extends YfApiCommonForm {
	/** 个人公积金账号 **/
	private String accnum;
	/** 消息标题 **/
	private String title;
	/** 消息内容 **/
	private String detail;
	/** 备用字段1 **/
	private String freeuse1;
	/** 备用字段2 **/
	private String freeuse2;
	/** 备用字段3 **/
	private String freeuse3;

	/**
	 * @return the accnum
	 */
	public String getAccnum() {
		return accnum;
	}

	/**
	 * @param accnum
	 *            the accnum to set
	 */
	public void setAccnum(String accnum) {
		this.accnum = accnum;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * @return the freeuse1
	 */
	public String getFreeuse1() {
		return freeuse1;
	}

	/**
	 * @param freeuse1
	 *            the freeuse1 to set
	 */
	public void setFreeuse1(String freeuse1) {
		this.freeuse1 = freeuse1;
	}

	/**
	 * @return the freeuse2
	 */
	public String getFreeuse2() {
		return freeuse2;
	}

	/**
	 * @param freeuse2
	 *            the freeuse2 to set
	 */
	public void setFreeuse2(String freeuse2) {
		this.freeuse2 = freeuse2;
	}

	/**
	 * @return the freeuse3
	 */
	public String getFreeuse3() {
		return freeuse3;
	}

	/**
	 * @param freeuse3
	 *            the freeuse3 to set
	 */
	public void setFreeuse3(String freeuse3) {
		this.freeuse3 = freeuse3;
	}
}
