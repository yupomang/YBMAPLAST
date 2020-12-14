/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.form
 * 文件名：     WebApi30201_deleteForm.java
 * 创建日期：2013-10-19
 */
package com.yondervision.mi.form;

import java.util.List;

/**
 * @author LinXiaolong
 *
 */
public class WebApi30201_deleteForm extends WebApiCommonForm {
	/** 要删除的公共短信息ID组 **/
	private String listCommsgid;

	/**
	 * @return the listCommsgid
	 */
	public String getListCommsgid() {
		return listCommsgid;
	}

	/**
	 * @param listCommsgid the listCommsgid to set
	 */
	public void setListCommsgid(String listCommsgid) {
		this.listCommsgid = listCommsgid;
	}
	
}
