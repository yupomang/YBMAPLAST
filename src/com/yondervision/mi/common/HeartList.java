package com.yondervision.mi.common;

import java.util.List;

public class HeartList {	
	/**
	 * 城市码
	 */
	public String centerid = "";
	/**
	 * 检查时间
	 */
	public String checkDate = "";
	/**
	 * 中心下应用检查结果集合
	 */
	public List<HeartBeat> list;
	public String getCenterid() {
		return centerid;
	}
	public void setCenterid(String centerid) {
		this.centerid = centerid;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public List<HeartBeat> getList() {
		return list;
	}
	public void setList(List<HeartBeat> list) {
		this.list = list;
	}
	
}
