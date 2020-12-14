package com.yondervision.mi.form;


/**
* @ClassName: AppApi00234Form
* @Description: 个人开户
* @author luolin
* @date 2019-12-19
*
*/
public class AppApi00235Form extends AppApiCommonForm{
	/*
	qdapprnum		办件唯一编号
servicecode		事项编码
servicename		事项名称
unitaccnum		单位公积金账号
unitaccname		单位名称
unitaccaddr		单位地址
unioncode		社会信用代码
transdate		申请时间/业务办理时间
flag 	1-录用,2-调任,3-转任,4-辞职,5-退休,6-开除,7-辞退,8-调出	办理业务类型
ispflag	0-开启,1-不开启	是否开启按月补贴账户
accname		人员姓名
certitype	01-身份证,02-军官证,03-护照,04-外国人永久居留证,06-户口簿,07-港澳通行证,08-台湾同胞证,09-其他	人员证件类型
certinum   		人员身份证号
phone  		人员手机号码
basenum      		公积金工资缴费基数
begpym       		起缴开始年月
endpym       		停缴开始年月
unitlinkman		经办人姓名
unitlinkphone		经办人联系电话
instanceid		实例号

 */
	private String json;
	private String qdapprnum;
	private String servicecode;
	private String servicename;
	private String unitaccnum;
	private String unitaccname;
	private String unitaccaddr;
	private String unioncode;
	private String transdate;
	private String flag;
	private String accname;
	private String ispflag;
	private String certitype;
	private String certinum;
	private String phone;
	private String basenum;
	private String begpym;
	private String endpym;
	private String unitlinkman;
	private String unitlinkphone;
	private String instanceid;

	public String getJson() {
		return this.json;
	}

	public void setJson(final String json) {
		this.json = json;
	}

	public String getQdapprnum() {
		return this.qdapprnum;
	}

	public void setQdapprnum(final String qdapprnum) {
		this.qdapprnum = qdapprnum;
	}

	public String getServicecode() {
		return this.servicecode;
	}

	public void setServicecode(final String servicecode) {
		this.servicecode = servicecode;
	}

	public String getServicename() {
		return this.servicename;
	}

	public void setServicename(final String servicename) {
		this.servicename = servicename;
	}

	public String getUnitaccnum() {
		return this.unitaccnum;
	}

	public void setUnitaccnum(final String unitaccnum) {
		this.unitaccnum = unitaccnum;
	}

	public String getUnitaccname() {
		return this.unitaccname;
	}

	public void setUnitaccname(final String unitaccname) {
		this.unitaccname = unitaccname;
	}

	public String getUnitaccaddr() {
		return this.unitaccaddr;
	}

	public void setUnitaccaddr(final String unitaccaddr) {
		this.unitaccaddr = unitaccaddr;
	}

	public String getUnioncode() {
		return this.unioncode;
	}

	public void setUnioncode(final String unioncode) {
		this.unioncode = unioncode;
	}

	public String getTransdate() {
		return this.transdate;
	}

	public void setTransdate(final String transdate) {
		this.transdate = transdate;
	}

	@Override
	public String getFlag() {
		return this.flag;
	}

	@Override
	public void setFlag(final String flag) {
		this.flag = flag;
	}

	public String getAccname() {
		return this.accname;
	}

	public void setAccname(final String accname) {
		this.accname = accname;
	}

	public String getIspflag() {
		return this.ispflag;
	}

	public void setIspflag(final String ispflag) {
		this.ispflag = ispflag;
	}

	public String getCertitype() {
		return this.certitype;
	}

	public void setCertitype(final String certitype) {
		this.certitype = certitype;
	}

	public String getCertinum() {
		return this.certinum;
	}

	public void setCertinum(final String certinum) {
		this.certinum = certinum;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public String getBasenum() {
		return this.basenum;
	}

	public void setBasenum(final String basenum) {
		this.basenum = basenum;
	}

	public String getBegpym() {
		return this.begpym;
	}

	public void setBegpym(final String begpym) {
		this.begpym = begpym;
	}

	public String getEndpym() {
		return this.endpym;
	}

	public void setEndpym(final String endpym) {
		this.endpym = endpym;
	}

	public String getUnitlinkman() {
		return this.unitlinkman;
	}

	public void setUnitlinkman(final String unitlinkman) {
		this.unitlinkman = unitlinkman;
	}

	public String getUnitlinkphone() {
		return this.unitlinkphone;
	}

	public void setUnitlinkphone(final String unitlinkphone) {
		this.unitlinkphone = unitlinkphone;
	}

	public String getInstanceid() {
		return this.instanceid;
	}

	public void setInstanceid(final String instanceid) {
		this.instanceid = instanceid;
	}
}