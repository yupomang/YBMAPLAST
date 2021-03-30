package com.yondervision.mi.form;


/**
* @ClassName: AppApi00225Form
* @Description: 浙江省公积金个人账户基本信息查询接口
* @author luolin
* @date 2019-10-14
*
*/
public class AppApi00241Form {
/*
证件号码 ZJHM
*/
	private String businName;
	//private String ZJHM;
	private String channel;
/*
贷款异地证明打印
accnum 个人账号
certinum 身份证
centername 发送方中心名称
userid 柜员号
brccode 机构码


channel 渠道号
projectname 回执方中心名称*/
	private String accnum;
	private String certinum;
	private String centername;
	private String userid;
	private String brccode;
	private String projectname;

	private String centerId;
	private String buzType;


	//"职工姓名",
	private String accname;
	//":"个人公积金帐号",
	//private String accnum;
	//addr":"公积金贷款城市",
	private String addr;
	//amt":"贷款金额",
	private String amt;
	//bal":"缴存余额",
	private String bal;
	//basenum":"缴存基数",
	private String basenum;
	//certinum":"身份证号",
	//private String certinum;
	//flag":"该职工公积金贷款记录情况",
	private String flag;
	//indiaccstate":"账户状态",
	private String indiaccstate;
	//indipaysum":"月缴存额",
	private String indipaysum;
	//indiprop":"个人缴存比例",
	private String indiprop;
	//loancontrnum":"编号",
	private String loancontrnum;
	//month":"开始月",
	private String month;
	//month1":"结束月",
	private String month1;
	//opnaccdate":"开户时间",
	private String opnaccdate;
	//projectname":"缴存地名称",
	//private String projectname;
	//unitaccname":"单位名称",
	private String unitaccname;
	//unitprop":"单位缴存比例",
	private String unitprop;
	//year":"开始年",
	private String year;
	//year1":"结束年",
	private String year1;
	//linkman":"联系人",
	private String linkman;
	//linkphone":"联系电话"
	private String linkphone;
	//transdate":"网厅证明出具时间
	private String transdate;

	private String userId;

	private String brcCode;

	private String recode;

	private String quyu;

	private String instcode;

	private String zhlx;

	private String xm;

	private String zjlx;

	private String zjhm;

	private String spt_indiaccstatename;



	private String lpaym;

	private String bal1;

	private String bal2;

	private String bal3;

	private String bal4;



	private String spt_unitaccname;

	private String jkrxm;

	private String sfzh;

	private String txdz;

	private String sjhm;

	private String jkhtbh;

	private String gfdz;

	private String jkje;

	private String jkffr;

	private String jkll;

	private String ydkkr;

	private String kkzhhm;

	private String jkzt;

	private String wdyh;

	private String jkqx;

	private String jkdqr;

	private String fkfs;

	private String kkzh;

	private String ljghbj;

	private String sybj;

	private String dqhkzt;

	private String spt_ywid= "";
	//++++++++++++++++++++++++++++++++


	public String getSpt_ywid() {
		return spt_ywid;
	}

	public void setSpt_ywid(String spt_ywid) {
		this.spt_ywid = spt_ywid;
	}

	public String getJkrxm() {
		return jkrxm;
	}

	public void setJkrxm(String jkrxm) {
		this.jkrxm = jkrxm;
	}

	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

	public String getTxdz() {
		return txdz;
	}

	public void setTxdz(String txdz) {
		this.txdz = txdz;
	}

	public String getSjhm() {
		return sjhm;
	}

	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}

	public String getJkhtbh() {
		return jkhtbh;
	}

	public void setJkhtbh(String jkhtbh) {
		this.jkhtbh = jkhtbh;
	}

	public String getGfdz() {
		return gfdz;
	}

	public void setGfdz(String gfdz) {
		this.gfdz = gfdz;
	}

	public String getJkje() {
		return jkje;
	}

	public void setJkje(String jkje) {
		this.jkje = jkje;
	}

	public String getJkffr() {
		return jkffr;
	}

	public void setJkffr(String jkffr) {
		this.jkffr = jkffr;
	}

	public String getJkll() {
		return jkll;
	}

	public void setJkll(String jkll) {
		this.jkll = jkll;
	}

	public String getYdkkr() {
		return ydkkr;
	}

	public void setYdkkr(String ydkkr) {
		this.ydkkr = ydkkr;
	}

	public String getKkzhhm() {
		return kkzhhm;
	}

	public void setKkzhhm(String kkzhhm) {
		this.kkzhhm = kkzhhm;
	}

	public String getJkzt() {
		return jkzt;
	}

	public void setJkzt(String jkzt) {
		this.jkzt = jkzt;
	}

	public String getWdyh() {
		return wdyh;
	}

	public void setWdyh(String wdyh) {
		this.wdyh = wdyh;
	}

	public String getJkqx() {
		return jkqx;
	}

	public void setJkqx(String jkqx) {
		this.jkqx = jkqx;
	}

	public String getJkdqr() {
		return jkdqr;
	}

	public void setJkdqr(String jkdqr) {
		this.jkdqr = jkdqr;
	}

	public String getFkfs() {
		return fkfs;
	}

	public void setFkfs(String fkfs) {
		this.fkfs = fkfs;
	}

	public String getKkzh() {
		return kkzh;
	}

	public void setKkzh(String kkzh) {
		this.kkzh = kkzh;
	}

	public String getLjghbj() {
		return ljghbj;
	}

	public void setLjghbj(String ljghbj) {
		this.ljghbj = ljghbj;
	}

	public String getSybj() {
		return sybj;
	}

	public void setSybj(String sybj) {
		this.sybj = sybj;
	}

	public String getDqhkzt() {
		return dqhkzt;
	}

	public void setDqhkzt(String dqhkzt) {
		this.dqhkzt = dqhkzt;
	}

	public String getBal1() {
		return bal1;
	}

	public void setBal1(String bal1) {
		this.bal1 = bal1;
	}

	public String getBal2() {
		return bal2;
	}

	public void setBal2(String bal2) {
		this.bal2 = bal2;
	}

	public String getBal3() {
		return bal3;
	}

	public void setBal3(String bal3) {
		this.bal3 = bal3;
	}

	public String getBal4() {
		return bal4;
	}

	public void setBal4(String bal4) {
		this.bal4 = bal4;
	}

	public String getQuyu() {
		return quyu;
	}

	public void setQuyu(String quyu) {
		this.quyu = quyu;
	}

	public String getInstcode() {
		return instcode;
	}

	public void setInstcode(String instcode) {
		this.instcode = instcode;
	}

	public String getZhlx() {
		return zhlx;
	}

	public void setZhlx(String zhlx) {
		this.zhlx = zhlx;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getZjlx() {
		return zjlx;
	}

	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}

	public String getZjhm() {
		return zjhm;
	}

	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}

	public String getSpt_indiaccstatename() {
		return spt_indiaccstatename;
	}

	public void setSpt_indiaccstatename(String spt_indiaccstatename) {
		this.spt_indiaccstatename = spt_indiaccstatename;
	}

	public String getLpaym() {
		return lpaym;
	}

	public void setLpaym(String lpaym) {
		this.lpaym = lpaym;
	}

	public String getSpt_unitaccname() {
		return spt_unitaccname;
	}

	public void setSpt_unitaccname(String spt_unitaccname) {
		this.spt_unitaccname = spt_unitaccname;
	}

	public String getRecode() {
		return recode;
	}

	public void setRecode(String recode) {
		this.recode = recode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBrcCode() {
		return brcCode;
	}

	public void setBrcCode(String brcCode) {
		this.brcCode = brcCode;
	}

	public String getBuzType() {
		return buzType;
	}

	public void setBuzType(String buzType) {
		this.buzType = buzType;
	}

	public String getCenterId() {
		return this.centerId;
	}

	public void setCenterId(final String centerId) {
		this.centerId = centerId;
	}

	public String getAccnum() {
		return this.accnum;
	}

	public void setAccnum(final String accnum) {
		this.accnum = accnum;
	}

	public String getCertinum() {
		return this.certinum;
	}

	public void setCertinum(final String certinum) {
		this.certinum = certinum;
	}

	public String getCentername() {
		return this.centername;
	}

	public void setCentername(final String centername) {
		this.centername = centername;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(final String userid) {
		this.userid = userid;
	}

	public String getBrccode() {
		return this.brccode;
	}

	public void setBrccode(final String brccode) {
		this.brccode = brccode;
	}

	public String getProjectname() {
		return this.projectname;
	}

	public void setProjectname(final String projectname) {
		this.projectname = projectname;
	}

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(final String channel) {
		this.channel = channel;
	}

	public String getBusinName() {
		return this.businName;
	}

	public void setBusinName(final String businName) {
		this.businName = businName;
	}

	/*public String getZJHM() {
		return this.ZJHM;
	}*/

	/*public void setZJHM(final String ZJHM) {
		this.ZJHM = ZJHM;
	}*/

	public String getAccname() {
		return accname;
	}

	public void setAccname(String accname) {
		this.accname = accname;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getBal() {
		return bal;
	}

	public void setBal(String bal) {
		this.bal = bal;
	}

	public String getBasenum() {
		return basenum;
	}

	public void setBasenum(String basenum) {
		this.basenum = basenum;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getIndiaccstate() {
		return indiaccstate;
	}

	public void setIndiaccstate(String indiaccstate) {
		this.indiaccstate = indiaccstate;
	}

	public String getIndipaysum() {
		return indipaysum;
	}

	public void setIndipaysum(String indipaysum) {
		this.indipaysum = indipaysum;
	}

	public String getIndiprop() {
		return indiprop;
	}

	public void setIndiprop(String indiprop) {
		this.indiprop = indiprop;
	}

	public String getLoancontrnum() {
		return loancontrnum;
	}

	public void setLoancontrnum(String loancontrnum) {
		this.loancontrnum = loancontrnum;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getMonth1() {
		return month1;
	}

	public void setMonth1(String month1) {
		this.month1 = month1;
	}

	public String getOpnaccdate() {
		return opnaccdate;
	}

	public void setOpnaccdate(String opnaccdate) {
		this.opnaccdate = opnaccdate;
	}

	public String getUnitaccname() {
		return unitaccname;
	}

	public void setUnitaccname(String unitaccname) {
		this.unitaccname = unitaccname;
	}

	public String getUnitprop() {
		return unitprop;
	}

	public void setUnitprop(String unitprop) {
		this.unitprop = unitprop;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getYear1() {
		return year1;
	}

	public void setYear1(String year1) {
		this.year1 = year1;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getLinkphone() {
		return linkphone;
	}

	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}

	public String getTransdate() {
		return transdate;
	}

	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}
}
