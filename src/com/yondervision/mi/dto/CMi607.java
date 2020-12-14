package com.yondervision.mi.dto;

public class CMi607 extends Mi607 {
	/** 起始页 **/
	private Integer page;
	/** 每页显示条数 **/
	private Integer rows;
	/** 起始日期 **/
	private String begindate;
	/** 终止日期 **/
	private String enddate;
	/** 用户帐号 **/
	private String Userid;
	/** 预约日期 **/
	private String appodate;
	//中心
	private String centerid;
	//姓名
	private String personalname;
	private String personalid;
	//身份证号
	private String certinum;
	//电话
	private String tel;
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
	public String getBegindate() {
		return begindate;
	}
	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getUserid() {
		return Userid;
	}
	public void setUserid(String userid) {
		Userid = userid;
	}
	public String getAppodate() {
		return appodate;
	}
	public void setAppodate(String appodate) {
		this.appodate = appodate;
	}
	public String getCenterid() {
		return centerid;
	}
	public void setCenterid(String centerid) {
		this.centerid = centerid;
	}
	public String getPersonalname() {
		return personalname;
	}
	public void setPersonalname(String personalname) {
		this.personalname = personalname;
	}
	public String getCertinum() {
		return certinum;
	}
	public void setCertinum(String certinum) {
		this.certinum = certinum;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPersonalid() {
		return personalid;
	}
	public void setPersonalid(String personalid) {
		this.personalid = personalid;
	}
}
