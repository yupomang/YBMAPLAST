package com.yondervision.mi.form;

public class Addon {
	public String id = "";
	public String name = "";
	public String code = "";
	public String assignee = "";
	public String state = "";
	public Addon[] children;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Addon[] getChildren() {
		return children;
	}
	public void setChildren(Addon[] children) {
		this.children = children;
	}
}
