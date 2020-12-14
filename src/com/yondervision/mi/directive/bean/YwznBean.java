package com.yondervision.mi.directive.bean;

import java.io.Serializable;

public class YwznBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2927241562303973478L;
	
	private String id;
	private String pid;
	private String title;
	private String floor;
	private String order;
	private String content;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
