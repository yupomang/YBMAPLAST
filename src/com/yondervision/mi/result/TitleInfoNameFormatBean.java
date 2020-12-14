package com.yondervision.mi.result;

public class TitleInfoNameFormatBean {
	/** 标题 **/
	private String title="";
	/** 内容 **/
	private String info="";
	/** 变量名 **/
	private String name="";
	/** 格式 **/
	private String format="";

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

}
