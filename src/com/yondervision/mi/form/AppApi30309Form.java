package com.yondervision.mi.form;



/** 
* @ClassName: AppApi30309Form 
* @Description: 查询核心预约信息参数
* @author Caozhongyan
* @date Apr 12, 2016 3:27:29 PM   
* 
*/ 
public class AppApi30309Form extends AppApiCommonForm {
	
	/** 网点ID **/  
	private String appobranchid;	
	/** 日期 **/
	private String appodate;
	/**
	 * 网点名称
	 */
	private String appobranchname;
	/**
	 * 时段id
	 */
	private String appotpldetailid;
	/**
	 * 电话
	 */
	private String phone;	
	/** 时间段 **/
	private String timeinterval;
	/** 业务名称 **/
	private String appobusiname;
	/** 微信昵称 **/
	private String nickname;
	/** 预约ID **/
	private String appointid;	
	private String appobusiid="";
	private String areid="";
	private String witename="";
	private String value1="";//暂时作为姓名传入
	private String value2="";
	private String value3="";
	private String value4="";
	private String value5="";
	private String pagenum = "";
	private String pagerows = "";
	private String startdate = "";
	private String enddate = "";
	public String getAppobusiid() {
		return appobusiid;
	}
	public void setAppobusiid(String appobusiid) {
		this.appobusiid = appobusiid;
	}
	public String getAreid() {
		return areid;
	}
	public void setAreid(String areid) {
		this.areid = areid;
	}
	public String getWitename() {
		return witename;
	}
	public void setWitename(String witename) {
		this.witename = witename;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getValue3() {
		return value3;
	}
	public void setValue3(String value3) {
		this.value3 = value3;
	}
	public String getValue4() {
		return value4;
	}
	public void setValue4(String value4) {
		this.value4 = value4;
	}
	public String getValue5() {
		return value5;
	}
	public void setValue5(String value5) {
		this.value5 = value5;
	}
	public String getPagerows() {
		return pagerows;
	}
	public void setPagerows(String pagerows) {
		this.pagerows = pagerows;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getPagenum() {
		return pagenum;
	}
	public void setPagenum(String pagenum) {
		this.pagenum = pagenum;
	}
	public String getAppobranchid() {
		return appobranchid;
	}
	public void setAppobranchid(String appobranchid) {
		this.appobranchid = appobranchid;
	}
	public String getAppodate() {
		return appodate;
	}
	public void setAppodate(String appodate) {
		this.appodate = appodate;
	}
	public String getAppobranchname() {
		return appobranchname;
	}
	public void setAppobranchname(String appobranchname) {
		this.appobranchname = appobranchname;
	}
	public String getAppotpldetailid() {
		return appotpldetailid;
	}
	public void setAppotpldetailid(String appotpldetailid) {
		this.appotpldetailid = appotpldetailid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTimeinterval() {
		return timeinterval;
	}
	public void setTimeinterval(String timeinterval) {
		this.timeinterval = timeinterval;
	}
	public String getAppobusiname() {
		return appobusiname;
	}
	public void setAppobusiname(String appobusiname) {
		this.appobusiname = appobusiname;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getAppointid() {
		return appointid;
	}
	public void setAppointid(String appointid) {
		this.appointid = appointid;
	}
}
