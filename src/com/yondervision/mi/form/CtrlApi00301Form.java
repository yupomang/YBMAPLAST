package com.yondervision.mi.form;

public class CtrlApi00301Form extends ApiCtrlCommonForm {
	private String name = "";
	private String group = "";
	private String platform = "";
	private String callback = "";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}			
}
