/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.result
 * 文件名：     AppApi30302Result.java
 * 创建日期：2013-11-18
 */
package com.yondervision.mi.result;

import java.util.List;

/**
 * @author LinXiaolong
 *
 */
public class AppApi30302Result {
	private String appobranchid;
	private String appobranchname;
	private List<DateCountBean> content;
	public String getAppobranchid() {
		return appobranchid;
	}
	public void setAppobranchid(String appobranchid) {
		this.appobranchid = appobranchid;
	}
	public String getAppobranchname() {
		return appobranchname;
	}
	public void setAppobranchname(String appobranchname) {
		this.appobranchname = appobranchname;
	}
	public List<DateCountBean> getContent() {
		return content;
	}
	public void setContent(List<DateCountBean> content) {
		this.content = content;
	}
	

}
