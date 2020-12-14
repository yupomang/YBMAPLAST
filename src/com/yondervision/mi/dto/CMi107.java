package com.yondervision.mi.dto;

import java.util.List;

public class CMi107 extends Mi107{

	/** 中心ID **/
	private String centerId;
    /** 中心名称 */
    private String centername;
	/** 起始页 **/
	private Integer page;
	/** 每页显示条数 **/
	private Integer rows;
	//业务类型集合
	private List<String> transTypeList;
	public String getCenterId() {
		return centerId;
	}

	public void setCenterId(String centerId) {
		this.centerId = centerId;
	}

	/** 序号 **/
	private String miseqno;
	/** 中心ID **/
	private String micenterid;
	/** 用户ID **/
	private String miuserid;
	/** 渠道类型 **/
	private String michanneltype;
	/**  业务日期* */
	private String mitransdate;
	/**  业务类型 **/
	private String mitranstype;
	/**  业务名称 **/
	private String mitransname;
	/**  版本号 **/
	private String miversionno;
	/**  设备区分* */
	private String midevtype;
	/**  设备标识 **/
	private String midevid;
	/**  请求时间 **/
	private String mirequesttime;
	/**  响应时间 **/
	private String miresponsetime;
	/**  处理时间  **/
	private String misecondsafter;
	/**  有效标志  **/
	private String mivalidflag;
	/**  处理状态 **/
	private String mifreeuse1;
	/**  状态描述 **/
	private String mifreeuse2;
	/**  请求url **/
	private String mifreeuse3;
	/**  备用字段4 **/
	private String mifreeuse4;

	private String businName;
	
	private String channeluser;
	
	/** 开始日期 **/
	private String startdate;
	/** 结束日期 **/
	private String enddate;
	//开始时间
	private String startdatetime;
	//结束时间
	private String enddatetime;
	private String servicetype;
	private String pidname;
	
	public String getPidname() {
		return pidname;
	}

	public void setPidname(String pidname) {
		this.pidname = pidname;
	}

	public String getBusinName() {
		return businName;
	}

	public void setBusinName(String businName) {
		this.businName = businName;
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

	
	public String getCentername() {
		return centername;
	}

	public void setCentername(String centername) {
		this.centername = centername;
	}
	
	/**
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * @return the rows
	 */
	public Integer getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	
	/**
	 * @return the miseqno
	 */
	public String getMiseqno() {
		return miseqno;
	}

	/**
	 * @param miseqno the miseqno to set
	 */
	public void setMiseqno(String miseqno) {
		this.miseqno = miseqno;
	}

	/**
	 * @return the micenterid
	 */
	public String getMicenterid() {
		return micenterid;
	}

	/**
	 * @param micenterid the micenterid to set
	 */
	public void setMicenterid(String micenterid) {
		this.micenterid = micenterid;
	}

	/**
	 * @return the miuserid
	 */
	public String getMiuserid() {
		return miuserid;
	}

	/**
	 * @param miuserid the miuserid to set
	 */
	public void setMiuserid(String miuserid) {
		this.miuserid = miuserid;
	}

	/**
	 * @return the michanneltype
	 */
	public String getMichanneltype() {
		return michanneltype;
	}

	/**
	 * @param michanneltype the michanneltype to set
	 */
	public void setMichanneltype(String michanneltype) {
		this.michanneltype = michanneltype;
	}

	/**
	 * @return the mitransdate
	 */
	public String getMitransdate() {
		return mitransdate;
	}

	/**
	 * @param mitransdate the mitransdate to set
	 */
	public void setMitransdate(String mitransdate) {
		this.mitransdate = mitransdate;
	}

	/**
	 * @return the mitranstype
	 */
	public String getMitranstype() {
		return mitranstype;
	}

	/**
	 * @param mitranstype the mitranstype to set
	 */
	public void setMitranstype(String mitranstype) {
		this.mitranstype = mitranstype;
	}

	/**
	 * @return the mitransname
	 */
	public String getMitransname() {
		return mitransname;
	}

	/**
	 * @param mitransname the mitransname to set
	 */
	public void setMitransname(String mitransname) {
		this.mitransname = mitransname;
	}

	/**
	 * @return the miversionno
	 */
	public String getMiversionno() {
		return miversionno;
	}

	/**
	 * @param miversionno the miversionno to set
	 */
	public void setMiversionno(String miversionno) {
		this.miversionno = miversionno;
	}

	/**
	 * @return the midevtype
	 */
	public String getMidevtype() {
		return midevtype;
	}

	/**
	 * @param midevtype the midevtype to set
	 */
	public void setMidevtype(String midevtype) {
		this.midevtype = midevtype;
	}

	/**
	 * @return the midevid
	 */
	public String getMidevid() {
		return midevid;
	}

	/**
	 * @param midevid the midevid to set
	 */
	public void setMidevid(String midevid) {
		this.midevid = midevid;
	}

	/**
	 * @return the mirequesttime
	 */
	public String getMirequesttime() {
		return mirequesttime;
	}

	/**
	 * @param mirequesttime the mirequesttime to set
	 */
	public void setMirequesttime(String mirequesttime) {
		this.mirequesttime = mirequesttime;
	}

	/**
	 * @return the miresponsetime
	 */
	public String getMiresponsetime() {
		return miresponsetime;
	}

	/**
	 * @param miresponsetime the miresponsetime to set
	 */
	public void setMiresponsetime(String miresponsetime) {
		this.miresponsetime = miresponsetime;
	}

	/**
	 * @return the misecondsafter
	 */
	public String getMisecondsafter() {
		return misecondsafter;
	}

	/**
	 * @param misecondsafter the misecondsafter to set
	 */
	public void setMisecondsafter(String misecondsafter) {
		this.misecondsafter = misecondsafter;
	}

	/**
	 * @return the mivalidflag
	 */
	public String getMivalidflag() {
		return mivalidflag;
	}

	/**
	 * @param mivalidflag the mivalidflag to set
	 */
	public void setMivalidflag(String mivalidflag) {
		this.mivalidflag = mivalidflag;
	}

	/**
	 * @return the mifreeuse1
	 */
	public String getMifreeuse1() {
		return mifreeuse1;
	}

	/**
	 * @param mifreeuse1 the mifreeuse1 to set
	 */
	public void setMifreeuse1(String mifreeuse1) {
		this.mifreeuse1 = mifreeuse1;
	}

	/**
	 * @return the mifreeuse2
	 */
	public String getMifreeuse2() {
		return mifreeuse2;
	}

	/**
	 * @param mifreeuse2 the mifreeuse2 to set
	 */
	public void setMifreeuse2(String mifreeuse2) {
		this.mifreeuse2 = mifreeuse2;
	}

	/**
	 * @return the mifreeuse3
	 */
	public String getMifreeuse3() {
		return mifreeuse3;
	}

	/**
	 * @param mifreeuse3 the mifreeuse3 to set
	 */
	public void setMifreeuse3(String mifreeuse3) {
		this.mifreeuse3 = mifreeuse3;
	}

	/**
	 * @return the mifreeuse4
	 */
	public String getMifreeuse4() {
		return mifreeuse4;
	}

	/**
	 * @param mifreeuse4 the mifreeuse4 to set
	 */
	public void setMifreeuse4(String mifreeuse4) {
		this.mifreeuse4 = mifreeuse4;
	}

	public String getStartdatetime() {
		return startdatetime;
	}

	public void setStartdatetime(String startdatetime) {
		this.startdatetime = startdatetime;
	}

	public String getEnddatetime() {
		return enddatetime;
	}

	public void setEnddatetime(String enddatetime) {
		this.enddatetime = enddatetime;
	}

	public List<String> getTransTypeList() {
		return transTypeList;
	}

	public void setTransTypeList(List<String> transTypeList) {
		this.transTypeList = transTypeList;
	}

	public String getChanneluser() {
		return channeluser;
	}

	public void setChanneluser(String channeluser) {
		this.channeluser = channeluser;
	}

	public String getServicetype() {
		return servicetype;
	}

	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}

}