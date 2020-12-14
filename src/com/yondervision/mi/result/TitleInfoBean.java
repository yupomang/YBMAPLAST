/**
 * 
 */
package com.yondervision.mi.result;

/**
 * @author LinXiaolong
 * 
 */
public class TitleInfoBean {
	/** 标题 **/
	private String title;
	/** 内容 **/
	private String info;
	//
	private String name;
	private String format;
	/** 取得标题 **/
	public String getTitle() {
		return title;
	}

	/** 设置标题 **/
	public void setTitle(String title) {
		this.title = title;
	}

	/** 取得内容 **/
	public String getInfo() {
		return info;
	}

	/** 设置内容 **/
	public void setInfo(String info) {
		this.info = info;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
