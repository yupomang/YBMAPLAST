package com.yondervision.mi.dto;

public class CMi627 extends Mi627 {
	/** 起始页 **/
	private Integer page;
	/** 每页显示条数 **/
	private Integer rows;
	/** 起始日期 **/
	private String startdate;
	/** 工作日天数 **/
	private Integer datenum;
	/** 年度 **/
	private int year;
	/** 查询的月份 **/
	private String yearmonth;
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public Integer getDatenum() {
		return datenum;
	}
	public void setDatenum(Integer datenum) {
		this.datenum = datenum;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getYearmonth() {
		return yearmonth;
	}
	public void setYearmonth(String yearmonth) {
		this.yearmonth = yearmonth;
	}
}
