/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.form
 * 文件名：     WebApi13003rForm.java
 * 创建日期：2013-10-15
 */
package com.yondervision.mi.form;

/**
 * @author zhanglei
 * 
 */
public class WebApi13003Form extends WebApiCommonForm {
	/**分组id**/
	private String groupid;
	/**页数**/
	private String page;
	/**记录条数**/	
	private String num;
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
}
