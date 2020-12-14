package com.yondervision.mi.common;

public class Quotai {
	private String centerid = "";
	private String channel = "";
	private String pid = "";
	private String buztype = "";
	/**
	 * 单笔限额
	 */
	private double onequotaiTop = 0;
	/**
	 * 当日限额
	 */
	private double dayquotaiTop = 0;
	/**
	 * 当日实际
	 */
	private double dayquotai = 0;
	/**
	 * 天
	 */
	private String today = "";
	
	public String getCenterid() {
		return centerid;
	}
	public String getChannel() {
		return channel;
	}
	public String getPid() {
		return pid;
	}
	public String getBuztype() {
		return buztype;
	}
	public void setCenterid(String centerid) {
		this.centerid = centerid;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public void setBuztype(String buztype) {
		this.buztype = buztype;
	}
	public double getOnequotaiTop() {
		return onequotaiTop;
	}
	public void setOnequotaiTop(double onequotaiTop) {
		this.onequotaiTop = onequotaiTop;
	}
	public double getDayquotaiTop() {
		return dayquotaiTop;
	}
	public void setDayquotaiTop(double dayquotaiTop) {
		this.dayquotaiTop = dayquotaiTop;
	}
	public double getDayquotai() {
		return dayquotai;
	}
	public void setDayquotai(double dayquotai) {
		this.dayquotai = dayquotai;
	}
	public String getToday() {
		return today;
	}
	public void setToday(String today) {
		this.today = today;
	}	
}
