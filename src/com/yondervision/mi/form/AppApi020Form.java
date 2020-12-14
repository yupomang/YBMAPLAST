package com.yondervision.mi.form;

public class AppApi020Form extends AppApiCommonForm {
	
	private String 	trcode;
	private String 	ctlflag;
	private String 	linkman;//单位联系人
	private String 	cagorcode;//短信验证码
	private String 	comcialflag;
	private String 	loanbal;
	private String 	loancontrstate;

	private String 	pubaccnum;//账户账号
	//===================宁波新加for不动产抵押证明领取预约		
	private String 	lnundtkbcode;//受理分行
	private String 	lnundtksubbcode;//受理支行
	private String 	linkphone;//接收手机号

	//===================宁波新加for个人贷款试算		
	private String 	lnaccnum;//借款人个人公积金缴存账号
	private String 	basenum;//公积金缴存基数
	private String 	conpaymonth;//公积金账户连续缴存月份分类
	private String 	loantimes;//公积金贷款认定次数
	private String 	apploanterm;//借款人申请贷款年限
	private String 	apploanamt;//贷款申请金额
	private String 	usebal;//账户认定余额
	private String 	shisuantype;//试算类型
	private String 	housetype;//房屋类型
	private String 	rangeyear;//离退休年纪
	private String 	loanhousenum;//第n套房贷款
	private String 	age;//年龄

	private String loaneecertinum;//证件号码 
	//===================宁波新加for提前还款试算		
	private String curbal;//当前余额
	private String loanrate;//贷款月利率
	private String repaymode;//还款方式
	private String repaycycle;//还款周期
	private String remainterm;//剩余期限
	private String shttermflag;//计算方式
	private String transdate;//还款日期
	private String loancontrcode;//合同代码
	private String planrepaydate;//计划还款日期

	private String frzflag;
	//===================宁波新加for一般提取		
	private String buyhousetype;
	private String renttype;
	private String payeebankcode;
	private String FinancialDate;
	private String monthrepayamt;
	private String buyhouseamt;
	private String commonthrepayamt;
	private String rentnum3;
	private String rentname;
	private String rentcertinum;
	private String rentdate;
	private String rentenddate;
	private String rentamt;
	private String buyhousename;
	private String buyhousecerid;
	private String buyhousedate;
	private String lasttrsdate;
	private String loansum;
	private String loanterm;
	private String commloansum;
	private String repaylntype;
	private String contrsigndate;
	private String bankcode;
	private String houseaddr;
	private String inputamt;
	private String payeebankaccnum;
	private String bal;
	private String monbal;
	private String supbal;
	private String subbal;

	//===================宁波新加for一般提取可提取金额计算		
	private String 	clearamt;//结清金额
	private String 	cleardate;//结清日期
	private String 	ahdrepayamt;//提前部分还款金额
	private String 	conamt;//政府补贴金额

	
	/** 年审经办人手机号码 */				
	private String handset = "";		
	/** 外来务工人数 */				
	private String mannum = "";	
	/** 转出机构号 */				
	private String tsoinstcode = "";		
	/** 变更前比例 */				
	private String bfchgunitprop = "";				
	/** 变更后比例 */				
	private String afchgunitprop = "";			

	
	/** 确认标志 */			
	private String checkin = "";					
	/** 单位地址 */			
	private String unitaddr = "";			
	/** 经办部门 */			
	private String agentdept = "";			
	/** 联系人 */			
	private String unitlinkman = "";			
	/** 联系电话1 */			
	private String unitlinkphone = "";			
	/** 联系电话2 */			
	private String unitlinkphone2 = "";			
	/** 上传批量文件名 */			
	private String filename = "";			
	/** 业务流程号 */			
	private String flowids = "";			
	/** 申请调整原因 */			
	private String appadjustreason = "";			
	/** 批准文号 */			
	private String authfilenum = "";			
	/** 启用年月 */			
	private String beginpaydate = "";			
	/** 变更人数 */			
	private String changepeople = "";
	/** 调整后个人比例 */			
	private String newperprop = "";			
	/** 调整后单位比例 */			
	private String newunitprop = "";				
	/** 终止年月 */			
	private String endym = "";				
	/** 自筹财政标志 */			
	private String selfintype = "";			
	/** 本次补缴金额 */			
	private String payamt = "";			
			
	/** 本次补缴人数 */			
	private String paynum = "";			
	/** 补缴类型 */			
	private String spaytype = "";			

	/** 补贴方式 */			
	private String dpsubsidytype = "";			
	
//===================宁波新加for公共字段		
	/** 渠道受理号 */	
	private String qdapprnum= "";
	/** 上传文件路径 */	
	private String qdfilepath= "";
	/** 上传文件名 */	
	private String qdfilename= "";
	/** 分页标识 */	
	private String pageflag= "";
	/** 每页条数 */	
	private String pagesum= "";
//===================宁波新加for单位资料变更		
	private String basebankname= "";
	private String createfileregnum= "";
	private String leglphone= "";
	private String licensenum= "";
	private String linkmanmsn= "";
	private String msumsalry= "";
	private String tradetype= "";
	private String createfile= "";
	private String indihigprop= "";
	private String indispeprop= "";
	private String linkmancertinum= "";
	private String orgcode= "";
	private String relpelnum= "";
	private String salbankaccnum= "";
	private String salday= "";
	private String createdate= "";
	private String newpubcustid1= "";
	private String unitauth2= "";
	private String funcno= ""; 
	private String leglcertinum= "";
	private String unithigprop= "";
	private String cmfundsouflag= "";
	private String linkmanqq= "";
	private String salbankcode= "";
	private String speflag= "";
	private String zip= "";
	private String flag= "";
	private String leglcertitype= "";
	private String linkmancertitype= "";
	private String linkmanemail= "";
	private String unitareacode= "";
	private String unitauth1= "";
	private String unitspeprop= "";
	private String unitycode= "";
	private String leglaccname= "";
	private String newpubcustid2= "";
	private String salbankaccnm= "";
	private String unitsoicode= "";
	private String accbankcode= "";
	private String economytype= "";
	private String indinorprop= "";
	private String supsubrelation= "";
	private String unitlicaddr= "";
	private String unitnorprop= "";
	private String webbusmaget= "";
	
//===================株洲新加	
	/** 密码 */			
	private String password = "";
	private String acceptreason     = "";
	private String authop           = "";
	private String baseuppflag      = "";
	private String cocietycode      = "";
	private String comflag          = "";
	private String crefilnum        = "";
	private String dwdz             = "";
	private String dwdzxx           = "";
	private String dwfrdbxm         = "";
	private String dwfrdbzjhm       = "";
	private String dwfrdbzjlx       = "";
	private String dwfxr            = "";
	private String dwjjlx           = "";
	private String dwlsgx           = "";
	private String basebankaccname  = "";
	private String basebankaccnum   = "";
	private String dwslrq           = "";
	private String dwsshy           = "";
	private String dwyb             = "";
	private String finalflag        = "";
	private String insurancenum     = "";
	private String isserdispach     = "";
	private String jbrgddhhm        = "";
	private String resuflag         = "";
	private String jbrqq            = "";
	private String jbrrzsj          = "";
	private String jbrsfpx          = "";
	private String jbrsjhm          = "";
	private String jbrxm            = "";
	private String jbrzjhm          = "";
	private String jbrzjlx          = "";
	private String mngdept          = "";
	private String mngdepter        = "";
	private String mngdepterphone   = "";
	private String orgtype          = "";
	private String ownershipkind    = "";
	private String remark           = "";
	private String unitkind         = "";
	private String useflag          = "";
	private String workernum        = "";
	private String zzjgdm           = "";
	private String dwfrdbsjhm  = "";
	//个人缴存证明打印04203 start
	
	private String 	drawreason;//提取原因
	private String 	drawreasoncode1;//提取材料号
	private String 	accloantype;//贷款类型
	private String 	certitype;//证件类型
	private String 	relation;//关系
	private String accname;	//个人姓名
	private String indiaccstate;	//个人账户状态
	/** 个人账户类型 */				
	private String indiacctype = "";
	/** 单位账户类型 */				
	private String unitacctype = "";

	/** 查询年月 */		
	private String ym = "";	

	/**	提取原因 */
	private String tqyy = "";
	/** 页码 */				
	private String pagenum = "";				
	/** 行数 */				
	private String pagerows = "";
	/** 是否分页标识 */	
	private String ispaging = "";
	/** 开发商名称 */	
	private String empunitname = "";
	
	/** 证件号码*/
	private String certinum = "";
	/** 卡号*/
	private String cardno = "";
	
	private String startdate ="";
	/** 单位客户号 */			
	private String unitcustid = "";	
	
	/** 单位公积金账号 */			
	private String unitaccnum = "";			
	/** 起始日期 */			
	private String begdate = "";			
	/** 身份证号 */			
	private String enddate = "";			
	/** 财务状态 */			
	private String queryflag = "";			
	/** 登记状态 */			
	private String regstate = "";			
	/** 查询输入 */			
	private String querynum = "";			
	/** 查询类型 */			
	private String querytype = "";			
	/** 业务类型 */			
	private String chgtype = "";			
	/** 查询标志 */			
	private String acflag = "";			
	/** 个人公积金账号 */			
	private String accnum = "";			
	/** 转出单位公积金账号 */			
	private String tsounitaccnum = "";			
	/** 转入单位公积金账号 */			
	private String tsiunitaccnum = "";			
	/** 查询年份 */			
	private String year = "";			
	/** 项目协议号 */			
	private String protonum = "";			
	/** 机构编码 */			
	private String instcode = "";			
	/** 楼盘名称 */			
	private String projectname = "";			
	/** 借款人一身份证号 */			
	private String lncertinum1 = "";			
	/** 贷款合同号 */			
	private String lncontrnum = "";			
	/** 本次实还金额 */			
	private String retamt = "";			
	/** 交易标志 */			
	private String tranflag = "";			
	/** 代扣账号 */			
	private String dedaccnum = "";			

	//=================株洲新加============================	
	private String grzh = "";
	private String accinstcode      = "";
	private String apprreason       = "";
	private String apprnum          = "";
	private String approveop        = "";
	private String attworkdate      = "";
	private String checkid          = "";
	private String chkop            = "";
	private String crelevel         = "";
	private String csny             = "";
	private String custid           = "";
	private String unitaccname             = "";
	private String email            = "";
	private String gddhhm           = "";
	private String hyzk             = "";
	private String indisoicode      = "";
	private String jtysr            = "";
	private String jtzz             = "";
	private String tel            = "";
	private String workid           = "";
	private String xingbie          = "";
	private String fullName = "";
	private String xueli            = "";
	private String yzbm             = "";
	private String zhichen          = "";
	private String zhiwu            = "";
	private String zhiye            = "";
	private String agentop          = "";
	//02401start
	/**	经办机构 */
	private String agentinstcode = "";
	/**	交易类型 */
	private String transtype = "";
	/**	登记号 */
	private String regnum = "";
	/**	单位账号 */
	private String dwzh = "";
	/**	缴存类型 */
	private String dptype = "";
	/**	汇缴类型 */
	private String colpaytype = "";
	/**	入账柜员 */
	private String inaccop = "";
	/**	入账标志 */
	private String inaccflag = "";
	/**	录入柜员 */
	private String inputop = "";
	/**	实际缴款金额 */
	private String factpayamt = "";
	/**	单位名称 */
	private String dwmc = "";
	/**	缴款方式 */
	private String paymode = "";
	//02401end
	//02003start
	/**	单位预缴账号 */
	private String unitpreaccnum = "";
	//02003end
	//02204start
	/**	交易代码 */
	private String transcode = "";
	/**	类型 */
	private String grtype = "";
	//02204end
	//02303start
	/**	实例号 */
	private String instance = "";
	/**	异地公积金中心*/
	private String ydgjjzx = "";
	//02303end
	//==================株洲新加===========================
	
	
	public String getAccname() {
		return accname;
	}

	public void setAccname(String accname) {
		this.accname = accname;
	}

	public String getIndiaccstate() {
		return indiaccstate;
	}

	public void setIndiaccstate(String indiaccstate) {
		this.indiaccstate = indiaccstate;
	}

	public String getIndiacctype() {
		return indiacctype;
	}

	public void setIndiacctype(String indiacctype) {
		this.indiacctype = indiacctype;
	}
	public String getCertinum() {
		return certinum;
	}

	public String getUnitpreaccnum() {
		return unitpreaccnum;
	}

	public void setUnitpreaccnum(String unitpreaccnum) {
		this.unitpreaccnum = unitpreaccnum;
	}

	public String getTranscode() {
		return transcode;
	}

	public void setTranscode(String transcode) {
		this.transcode = transcode;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getYdgjjzx() {
		return ydgjjzx;
	}

	public void setYdgjjzx(String ydgjjzx) {
		this.ydgjjzx = ydgjjzx;
	}

	public String getGrtype() {
		return grtype;
	}

	public void setGrtype(String grtype) {
		this.grtype = grtype;
	}

	public String getAgentinstcode() {
		return agentinstcode;
	}

	public void setAgentinstcode(String agentinstcode) {
		this.agentinstcode = agentinstcode;
	}

	public String getTranstype() {
		return transtype;
	}

	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}

	public String getRegnum() {
		return regnum;
	}

	public void setRegnum(String regnum) {
		this.regnum = regnum;
	}

	public String getDwzh() {
		return dwzh;
	}

	public void setDwzh(String dwzh) {
		this.dwzh = dwzh;
	}

	public String getDptype() {
		return dptype;
	}

	public void setDptype(String dptype) {
		this.dptype = dptype;
	}

	public String getColpaytype() {
		return colpaytype;
	}

	public void setColpaytype(String colpaytype) {
		this.colpaytype = colpaytype;
	}

	public String getInaccop() {
		return inaccop;
	}

	public void setInaccop(String inaccop) {
		this.inaccop = inaccop;
	}

	public String getInaccflag() {
		return inaccflag;
	}

	public void setInaccflag(String inaccflag) {
		this.inaccflag = inaccflag;
	}

	public String getInputop() {
		return inputop;
	}

	public void setInputop(String inputop) {
		this.inputop = inputop;
	}

	public String getFactpayamt() {
		return factpayamt;
	}

	public void setFactpayamt(String factpayamt) {
		this.factpayamt = factpayamt;
	}

	public String getDwmc() {
		return dwmc;
	}

	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}

	public String getPaymode() {
		return paymode;
	}

	public void setPaymode(String paymode) {
		this.paymode = paymode;
	}

	public void setCertinum(String certinum) {
		this.certinum = certinum;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getTqyy() {
		return tqyy;
	}

	public void setTqyy(String tqyy) {
		this.tqyy = tqyy;
	}

	public String getPagenum() {
		return pagenum;
	}

	public void setPagenum(String pagenum) {
		this.pagenum = pagenum;
	}

	public String getPagerows() {
		return pagerows;
	}

	public void setPagerows(String pagerows) {
		this.pagerows = pagerows;
	}

	public String getIspaging() {
		return ispaging;
	}

	public void setIspaging(String ispaging) {
		this.ispaging = ispaging;
	}

	public String getEmpunitname() {
		return empunitname;
	}

	public void setEmpunitname(String empunitname) {
		this.empunitname = empunitname;
	}

	/**			
	 *<pre> 执行获取单位公积金账号处理 </pre>			
	 * @return unitaccnum 单位公积金账号			
	 */			
	public String getUnitaccnum() {			
	    return unitaccnum;			
	}			
				
	/**			
	 *<pre> 执行设定单位公积金账号处理 </pre>			
	 * @param unitaccnum 单位公积金账号			
	 */			
	public void setUnitaccnum(String unitaccnum) {			
	    this.unitaccnum = unitaccnum;			
	}			
				
	/**			
	 *<pre> 执行获取起始日期处理 </pre>			
	 * @return begdate 起始日期			
	 */			
	public String getBegdate() {			
	    return begdate;			
	}			
				
	/**			
	 *<pre> 执行设定起始日期处理 </pre>			
	 * @param begdate 起始日期			
	 */			
	public void setBegdate(String begdate) {			
	    this.begdate = begdate;			
	}			
				
	/**			
	 *<pre> 执行获取身份证号处理 </pre>			
	 * @return enddate 身份证号			
	 */			
	public String getEnddate() {			
	    return enddate;			
	}			
				
	/**			
	 *<pre> 执行设定身份证号处理 </pre>			
	 * @param enddate 身份证号			
	 */			
	public void setEnddate(String enddate) {			
	    this.enddate = enddate;			
	}			
				
	/**			
	 *<pre> 执行获取财务状态处理 </pre>			
	 * @return queryflag 财务状态			
	 */			
	public String getQueryflag() {			
	    return queryflag;			
	}			
				
	/**			
	 *<pre> 执行设定财务状态处理 </pre>			
	 * @param queryflag 财务状态			
	 */			
	public void setQueryflag(String queryflag) {			
	    this.queryflag = queryflag;			
	}			
				
	/**			
	 *<pre> 执行获取登记状态处理 </pre>			
	 * @return regstate 登记状态			
	 */			
	public String getRegstate() {			
	    return regstate;			
	}			
				
	/**			
	 *<pre> 执行设定登记状态处理 </pre>			
	 * @param regstate 登记状态			
	 */			
	public void setRegstate(String regstate) {			
	    this.regstate = regstate;			
	}			
				
	/**			
	 *<pre> 执行获取查询输入处理 </pre>			
	 * @return querynum 查询输入			
	 */			
	public String getQuerynum() {			
	    return querynum;			
	}			
				
	/**			
	 *<pre> 执行设定查询输入处理 </pre>			
	 * @param querynum 查询输入			
	 */			
	public void setQuerynum(String querynum) {			
	    this.querynum = querynum;			
	}			
				
	/**			
	 *<pre> 执行获取查询类型处理 </pre>			
	 * @return querytype 查询类型			
	 */			
	public String getQuerytype() {			
	    return querytype;			
	}			
				
	/**			
	 *<pre> 执行设定查询类型处理 </pre>			
	 * @param querytype 查询类型			
	 */			
	public void setQuerytype(String querytype) {			
	    this.querytype = querytype;			
	}			
				
	/**			
	 *<pre> 执行获取业务类型处理 </pre>			
	 * @return chgtype 业务类型			
	 */			
	public String getChgtype() {			
	    return chgtype;			
	}			
				
	/**			
	 *<pre> 执行设定业务类型处理 </pre>			
	 * @param chgtype 业务类型			
	 */			
	public void setChgtype(String chgtype) {			
	    this.chgtype = chgtype;			
	}			
				
	/**			
	 *<pre> 执行获取查询标志处理 </pre>			
	 * @return acflag 查询标志			
	 */			
	public String getAcflag() {			
	    return acflag;			
	}			
				
	/**			
	 *<pre> 执行设定查询标志处理 </pre>			
	 * @param acflag 查询标志			
	 */			
	public void setAcflag(String acflag) {			
	    this.acflag = acflag;			
	}			
				
	/**			
	 *<pre> 执行获取个人公积金账号处理 </pre>			
	 * @return accnum 个人公积金账号			
	 */			
	public String getAccnum() {			
	    return accnum;			
	}			
				
	/**			
	 *<pre> 执行设定个人公积金账号处理 </pre>			
	 * @param accnum 个人公积金账号			
	 */			
	public void setAccnum(String accnum) {			
	    this.accnum = accnum;			
	}			
				
	/**			
	 *<pre> 执行获取转出单位公积金账号处理 </pre>			
	 * @return tsounitaccnum 转出单位公积金账号			
	 */			
	public String getTsounitaccnum() {			
	    return tsounitaccnum;			
	}			
				
	/**			
	 *<pre> 执行设定转出单位公积金账号处理 </pre>			
	 * @param tsounitaccnum 转出单位公积金账号			
	 */			
	public void setTsounitaccnum(String tsounitaccnum) {			
	    this.tsounitaccnum = tsounitaccnum;			
	}			
				
	/**			
	 *<pre> 执行获取转入单位公积金账号处理 </pre>			
	 * @return tsiunitaccnum 转入单位公积金账号			
	 */			
	public String getTsiunitaccnum() {			
	    return tsiunitaccnum;			
	}			
				
	/**			
	 *<pre> 执行设定转入单位公积金账号处理 </pre>			
	 * @param tsiunitaccnum 转入单位公积金账号			
	 */			
	public void setTsiunitaccnum(String tsiunitaccnum) {			
	    this.tsiunitaccnum = tsiunitaccnum;			
	}			
				
	/**			
	 *<pre> 执行获取查询年份处理 </pre>			
	 * @return year 查询年份			
	 */			
	public String getYear() {			
	    return year;			
	}			
				
	/**			
	 *<pre> 执行设定查询年份处理 </pre>			
	 * @param year 查询年份			
	 */			
	public void setYear(String year) {			
	    this.year = year;			
	}			
				
	/**			
	 *<pre> 执行获取项目协议号处理 </pre>			
	 * @return protonum 项目协议号			
	 */			
	public String getProtonum() {			
	    return protonum;			
	}			
				
	/**			
	 *<pre> 执行设定项目协议号处理 </pre>			
	 * @param protonum 项目协议号			
	 */			
	public void setProtonum(String protonum) {			
	    this.protonum = protonum;			
	}			
				
	/**			
	 *<pre> 执行获取机构编码处理 </pre>			
	 * @return instcode 机构编码			
	 */			
	public String getInstcode() {			
	    return instcode;			
	}			
				
	/**			
	 *<pre> 执行设定机构编码处理 </pre>			
	 * @param instcode 机构编码			
	 */			
	public void setInstcode(String instcode) {			
	    this.instcode = instcode;			
	}			
				
	/**			
	 *<pre> 执行获取楼盘名称处理 </pre>			
	 * @return projectname 楼盘名称			
	 */			
	public String getProjectname() {			
	    return projectname;			
	}			
				
	/**			
	 *<pre> 执行设定楼盘名称处理 </pre>			
	 * @param projectname 楼盘名称			
	 */			
	public void setProjectname(String projectname) {			
	    this.projectname = projectname;			
	}			
				
	/**			
	 *<pre> 执行获取借款人一身份证号处理 </pre>			
	 * @return lncertinum1 借款人一身份证号			
	 */			
	public String getLncertinum1() {			
	    return lncertinum1;			
	}			
				
	/**			
	 *<pre> 执行设定借款人一身份证号处理 </pre>			
	 * @param lncertinum1 借款人一身份证号			
	 */			
	public void setLncertinum1(String lncertinum1) {			
	    this.lncertinum1 = lncertinum1;			
	}			
				
	/**			
	 *<pre> 执行获取贷款合同号处理 </pre>			
	 * @return lncontrnum 贷款合同号			
	 */			
	public String getLncontrnum() {			
	    return lncontrnum;			
	}			
				
	/**			
	 *<pre> 执行设定贷款合同号处理 </pre>			
	 * @param lncontrnum 贷款合同号			
	 */			
	public void setLncontrnum(String lncontrnum) {			
	    this.lncontrnum = lncontrnum;			
	}			
				
	/**			
	 *<pre> 执行获取本次实还金额处理 </pre>			
	 * @return retamt 本次实还金额			
	 */			
	public String getRetamt() {			
	    return retamt;			
	}			
				
	/**			
	 *<pre> 执行设定本次实还金额处理 </pre>			
	 * @param retamt 本次实还金额			
	 */			
	public void setRetamt(String retamt) {			
	    this.retamt = retamt;			
	}			
				
	/**			
	 *<pre> 执行获取交易标志处理 </pre>			
	 * @return tranflag 交易标志			
	 */			
	public String getTranflag() {			
	    return tranflag;			
	}			
				
	/**			
	 *<pre> 执行设定交易标志处理 </pre>			
	 * @param tranflag 交易标志			
	 */			
	public void setTranflag(String tranflag) {			
	    this.tranflag = tranflag;			
	}			
				
	/**			
	 *<pre> 执行获取代扣账号处理 </pre>			
	 * @return dedaccnum 代扣账号			
	 */			
	public String getDedaccnum() {			
	    return dedaccnum;			
	}			
				
	/**			
	 *<pre> 执行设定代扣账号处理 </pre>			
	 * @param dedaccnum 代扣账号			
	 */			
	public void setDedaccnum(String dedaccnum) {			
	    this.dedaccnum = dedaccnum;			
	}

	public String getAccinstcode() {
		return accinstcode;
	}

	public void setAccinstcode(String accinstcode) {
		this.accinstcode = accinstcode;
	}

	public String getApprreason() {
		return apprreason;
	}

	public void setApprreason(String apprreason) {
		this.apprreason = apprreason;
	}

	public String getApprnum() {
		return apprnum;
	}

	public void setApprnum(String apprnum) {
		this.apprnum = apprnum;
	}

	public String getApproveop() {
		return approveop;
	}

	public void setApproveop(String approveop) {
		this.approveop = approveop;
	}

	public String getAttworkdate() {
		return attworkdate;
	}

	public void setAttworkdate(String attworkdate) {
		this.attworkdate = attworkdate;
	}

	public String getCheckid() {
		return checkid;
	}

	public void setCheckid(String checkid) {
		this.checkid = checkid;
	}

	public String getChkop() {
		return chkop;
	}

	public void setChkop(String chkop) {
		this.chkop = chkop;
	}

	public String getCrelevel() {
		return crelevel;
	}

	public void setCrelevel(String crelevel) {
		this.crelevel = crelevel;
	}

	public String getCsny() {
		return csny;
	}

	public void setCsny(String csny) {
		this.csny = csny;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getUnitaccname() {
		return unitaccname;
	}

	public void setUnitaccname(String unitaccname) {
		this.unitaccname = unitaccname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGddhhm() {
		return gddhhm;
	}

	public void setGddhhm(String gddhhm) {
		this.gddhhm = gddhhm;
	}

	public String getHyzk() {
		return hyzk;
	}

	public void setHyzk(String hyzk) {
		this.hyzk = hyzk;
	}

	public String getIndisoicode() {
		return indisoicode;
	}

	public void setIndisoicode(String indisoicode) {
		this.indisoicode = indisoicode;
	}

	public String getJtysr() {
		return jtysr;
	}

	public void setJtysr(String jtysr) {
		this.jtysr = jtysr;
	}

	public String getJtzz() {
		return jtzz;
	}

	public void setJtzz(String jtzz) {
		this.jtzz = jtzz;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getWorkid() {
		return workid;
	}

	public void setWorkid(String workid) {
		this.workid = workid;
	}

	public String getXingbie() {
		return xingbie;
	}

	public void setXingbie(String xingbie) {
		this.xingbie = xingbie;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getXueli() {
		return xueli;
	}

	public void setXueli(String xueli) {
		this.xueli = xueli;
	}

	public String getYzbm() {
		return yzbm;
	}

	public void setYzbm(String yzbm) {
		this.yzbm = yzbm;
	}

	public String getZhichen() {
		return zhichen;
	}

	public void setZhichen(String zhichen) {
		this.zhichen = zhichen;
	}

	public String getZhiwu() {
		return zhiwu;
	}

	public void setZhiwu(String zhiwu) {
		this.zhiwu = zhiwu;
	}

	public String getZhiye() {
		return zhiye;
	}

	public void setZhiye(String zhiye) {
		this.zhiye = zhiye;
	}

	public String getAgentop() {
		return agentop;
	}

	public void setAgentop(String agentop) {
		this.agentop = agentop;
	}

	public String getGrzh() {
		return grzh;
	}

	public void setGrzh(String grzh) {
		this.grzh = grzh;
	}

	public String getUnitcustid() {
		return unitcustid;
	}

	public void setUnitcustid(String unitcustid) {
		this.unitcustid = unitcustid;
	}			
				
	public String getYm() {
		return ym;
	}

	public void setYm(String ym) {
		this.ym = ym;
	}			
	
	public String getUnitacctype() {
		return unitacctype;
	}
	public void setUnitacctype(String unitacctype) {
		this.unitacctype = unitacctype;
	}

	public String getDrawreason() {
		return drawreason;
	}

	public void setDrawreason(String drawreason) {
		this.drawreason = drawreason;
	}

	public String getDrawreasoncode1() {
		return drawreasoncode1;
	}

	public void setDrawreasoncode1(String drawreasoncode1) {
		this.drawreasoncode1 = drawreasoncode1;
	}

	public String getAccloantype() {
		return accloantype;
	}

	public void setAccloantype(String accloantype) {
		this.accloantype = accloantype;
	}

	public String getCertitype() {
		return certitype;
	}

	public void setCertitype(String certitype) {
		this.certitype = certitype;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getComcialflag() {
		return comcialflag;
	}

	public void setComcialflag(String comcialflag) {
		this.comcialflag = comcialflag;
	}

	public String getLoanbal() {
		return loanbal;
	}

	public void setLoanbal(String loanbal) {
		this.loanbal = loanbal;
	}

	public String getLoancontrstate() {
		return loancontrstate;
	}

	public void setLoancontrstate(String loancontrstate) {
		this.loancontrstate = loancontrstate;
	}

	public String getPubaccnum() {
		return pubaccnum;
	}

	public void setPubaccnum(String pubaccnum) {
		this.pubaccnum = pubaccnum;
	}

	public String getLnundtkbcode() {
		return lnundtkbcode;
	}

	public void setLnundtkbcode(String lnundtkbcode) {
		this.lnundtkbcode = lnundtkbcode;
	}

	public String getLnundtksubbcode() {
		return lnundtksubbcode;
	}

	public void setLnundtksubbcode(String lnundtksubbcode) {
		this.lnundtksubbcode = lnundtksubbcode;
	}

	public String getLinkphone() {
		return linkphone;
	}

	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}

	public String getLnaccnum() {
		return lnaccnum;
	}

	public void setLnaccnum(String lnaccnum) {
		this.lnaccnum = lnaccnum;
	}

	public String getBasenum() {
		return basenum;
	}

	public void setBasenum(String basenum) {
		this.basenum = basenum;
	}

	public String getConpaymonth() {
		return conpaymonth;
	}

	public void setConpaymonth(String conpaymonth) {
		this.conpaymonth = conpaymonth;
	}

	public String getLoantimes() {
		return loantimes;
	}

	public void setLoantimes(String loantimes) {
		this.loantimes = loantimes;
	}

	public String getApploanterm() {
		return apploanterm;
	}

	public void setApploanterm(String apploanterm) {
		this.apploanterm = apploanterm;
	}

	public String getApploanamt() {
		return apploanamt;
	}

	public void setApploanamt(String apploanamt) {
		this.apploanamt = apploanamt;
	}

	public String getUsebal() {
		return usebal;
	}

	public void setUsebal(String usebal) {
		this.usebal = usebal;
	}

	public String getShisuantype() {
		return shisuantype;
	}

	public void setShisuantype(String shisuantype) {
		this.shisuantype = shisuantype;
	}

	public String getHousetype() {
		return housetype;
	}

	public void setHousetype(String housetype) {
		this.housetype = housetype;
	}

	public String getRangeyear() {
		return rangeyear;
	}

	public void setRangeyear(String rangeyear) {
		this.rangeyear = rangeyear;
	}

	public String getLoanhousenum() {
		return loanhousenum;
	}

	public void setLoanhousenum(String loanhousenum) {
		this.loanhousenum = loanhousenum;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getLoaneecertinum() {
		return loaneecertinum;
	}

	public void setLoaneecertinum(String loaneecertinum) {
		this.loaneecertinum = loaneecertinum;
	}

	public String getCurbal() {
		return curbal;
	}

	public void setCurbal(String curbal) {
		this.curbal = curbal;
	}

	public String getLoanrate() {
		return loanrate;
	}

	public void setLoanrate(String loanrate) {
		this.loanrate = loanrate;
	}

	public String getRepaymode() {
		return repaymode;
	}

	public void setRepaymode(String repaymode) {
		this.repaymode = repaymode;
	}

	public String getRepaycycle() {
		return repaycycle;
	}

	public void setRepaycycle(String repaycycle) {
		this.repaycycle = repaycycle;
	}

	public String getRemainterm() {
		return remainterm;
	}

	public void setRemainterm(String remainterm) {
		this.remainterm = remainterm;
	}

	public String getShttermflag() {
		return shttermflag;
	}

	public void setShttermflag(String shttermflag) {
		this.shttermflag = shttermflag;
	}

	public String getTransdate() {
		return transdate;
	}

	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}

	public String getLoancontrcode() {
		return loancontrcode;
	}

	public void setLoancontrcode(String loancontrcode) {
		this.loancontrcode = loancontrcode;
	}

	public String getPlanrepaydate() {
		return planrepaydate;
	}

	public void setPlanrepaydate(String planrepaydate) {
		this.planrepaydate = planrepaydate;
	}

	public String getFrzflag() {
		return frzflag;
	}

	public void setFrzflag(String frzflag) {
		this.frzflag = frzflag;
	}

	public String getBuyhousetype() {
		return buyhousetype;
	}

	public void setBuyhousetype(String buyhousetype) {
		this.buyhousetype = buyhousetype;
	}

	public String getRenttype() {
		return renttype;
	}

	public void setRenttype(String renttype) {
		this.renttype = renttype;
	}

	public String getPayeebankcode() {
		return payeebankcode;
	}

	public void setPayeebankcode(String payeebankcode) {
		this.payeebankcode = payeebankcode;
	}

	public String getFinancialDate() {
		return FinancialDate;
	}

	public void setFinancialDate(String financialDate) {
		FinancialDate = financialDate;
	}

	public String getMonthrepayamt() {
		return monthrepayamt;
	}

	public void setMonthrepayamt(String monthrepayamt) {
		this.monthrepayamt = monthrepayamt;
	}

	public String getBuyhouseamt() {
		return buyhouseamt;
	}

	public void setBuyhouseamt(String buyhouseamt) {
		this.buyhouseamt = buyhouseamt;
	}

	public String getCommonthrepayamt() {
		return commonthrepayamt;
	}

	public void setCommonthrepayamt(String commonthrepayamt) {
		this.commonthrepayamt = commonthrepayamt;
	}

	public String getRentnum3() {
		return rentnum3;
	}

	public void setRentnum3(String rentnum3) {
		this.rentnum3 = rentnum3;
	}

	public String getRentname() {
		return rentname;
	}

	public void setRentname(String rentname) {
		this.rentname = rentname;
	}

	public String getRentcertinum() {
		return rentcertinum;
	}

	public void setRentcertinum(String rentcertinum) {
		this.rentcertinum = rentcertinum;
	}

	public String getRentdate() {
		return rentdate;
	}

	public void setRentdate(String rentdate) {
		this.rentdate = rentdate;
	}

	public String getRentenddate() {
		return rentenddate;
	}

	public void setRentenddate(String rentenddate) {
		this.rentenddate = rentenddate;
	}

	public String getRentamt() {
		return rentamt;
	}

	public void setRentamt(String rentamt) {
		this.rentamt = rentamt;
	}

	public String getBuyhousename() {
		return buyhousename;
	}

	public void setBuyhousename(String buyhousename) {
		this.buyhousename = buyhousename;
	}

	public String getBuyhousecerid() {
		return buyhousecerid;
	}

	public void setBuyhousecerid(String buyhousecerid) {
		this.buyhousecerid = buyhousecerid;
	}

	public String getBuyhousedate() {
		return buyhousedate;
	}

	public void setBuyhousedate(String buyhousedate) {
		this.buyhousedate = buyhousedate;
	}

	public String getLasttrsdate() {
		return lasttrsdate;
	}

	public void setLasttrsdate(String lasttrsdate) {
		this.lasttrsdate = lasttrsdate;
	}

	public String getLoansum() {
		return loansum;
	}

	public void setLoansum(String loansum) {
		this.loansum = loansum;
	}

	public String getLoanterm() {
		return loanterm;
	}

	public void setLoanterm(String loanterm) {
		this.loanterm = loanterm;
	}

	public String getCommloansum() {
		return commloansum;
	}

	public void setCommloansum(String commloansum) {
		this.commloansum = commloansum;
	}

	public String getRepaylntype() {
		return repaylntype;
	}

	public void setRepaylntype(String repaylntype) {
		this.repaylntype = repaylntype;
	}

	public String getContrsigndate() {
		return contrsigndate;
	}

	public void setContrsigndate(String contrsigndate) {
		this.contrsigndate = contrsigndate;
	}

	public String getBankcode() {
		return bankcode;
	}

	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}

	public String getHouseaddr() {
		return houseaddr;
	}

	public void setHouseaddr(String houseaddr) {
		this.houseaddr = houseaddr;
	}

	public String getInputamt() {
		return inputamt;
	}

	public void setInputamt(String inputamt) {
		this.inputamt = inputamt;
	}

	public String getPayeebankaccnum() {
		return payeebankaccnum;
	}

	public void setPayeebankaccnum(String payeebankaccnum) {
		this.payeebankaccnum = payeebankaccnum;
	}

	public String getBal() {
		return bal;
	}

	public void setBal(String bal) {
		this.bal = bal;
	}

	public String getMonbal() {
		return monbal;
	}

	public void setMonbal(String monbal) {
		this.monbal = monbal;
	}

	public String getSupbal() {
		return supbal;
	}

	public void setSupbal(String supbal) {
		this.supbal = supbal;
	}

	public String getSubbal() {
		return subbal;
	}

	public void setSubbal(String subbal) {
		this.subbal = subbal;
	}

	public String getClearamt() {
		return clearamt;
	}

	public void setClearamt(String clearamt) {
		this.clearamt = clearamt;
	}

	public String getCleardate() {
		return cleardate;
	}

	public void setCleardate(String cleardate) {
		this.cleardate = cleardate;
	}

	public String getAhdrepayamt() {
		return ahdrepayamt;
	}

	public void setAhdrepayamt(String ahdrepayamt) {
		this.ahdrepayamt = ahdrepayamt;
	}

	public String getConamt() {
		return conamt;
	}

	public void setConamt(String conamt) {
		this.conamt = conamt;
	}

	public String getHandset() {
		return handset;
	}

	public void setHandset(String handset) {
		this.handset = handset;
	}

	public String getMannum() {
		return mannum;
	}

	public void setMannum(String mannum) {
		this.mannum = mannum;
	}

	public String getTsoinstcode() {
		return tsoinstcode;
	}

	public void setTsoinstcode(String tsoinstcode) {
		this.tsoinstcode = tsoinstcode;
	}

	public String getBfchgunitprop() {
		return bfchgunitprop;
	}

	public void setBfchgunitprop(String bfchgunitprop) {
		this.bfchgunitprop = bfchgunitprop;
	}

	public String getAfchgunitprop() {
		return afchgunitprop;
	}

	public void setAfchgunitprop(String afchgunitprop) {
		this.afchgunitprop = afchgunitprop;
	}

	public String getCheckin() {
		return checkin;
	}

	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}

	public String getUnitaddr() {
		return unitaddr;
	}

	public void setUnitaddr(String unitaddr) {
		this.unitaddr = unitaddr;
	}

	public String getAgentdept() {
		return agentdept;
	}

	public void setAgentdept(String agentdept) {
		this.agentdept = agentdept;
	}

	public String getUnitlinkman() {
		return unitlinkman;
	}

	public void setUnitlinkman(String unitlinkman) {
		this.unitlinkman = unitlinkman;
	}

	public String getUnitlinkphone() {
		return unitlinkphone;
	}

	public void setUnitlinkphone(String unitlinkphone) {
		this.unitlinkphone = unitlinkphone;
	}

	public String getUnitlinkphone2() {
		return unitlinkphone2;
	}

	public void setUnitlinkphone2(String unitlinkphone2) {
		this.unitlinkphone2 = unitlinkphone2;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFlowids() {
		return flowids;
	}

	public void setFlowids(String flowids) {
		this.flowids = flowids;
	}

	public String getAppadjustreason() {
		return appadjustreason;
	}

	public void setAppadjustreason(String appadjustreason) {
		this.appadjustreason = appadjustreason;
	}

	public String getAuthfilenum() {
		return authfilenum;
	}

	public void setAuthfilenum(String authfilenum) {
		this.authfilenum = authfilenum;
	}

	public String getBeginpaydate() {
		return beginpaydate;
	}

	public void setBeginpaydate(String beginpaydate) {
		this.beginpaydate = beginpaydate;
	}

	public String getChangepeople() {
		return changepeople;
	}

	public void setChangepeople(String changepeople) {
		this.changepeople = changepeople;
	}

	public String getNewperprop() {
		return newperprop;
	}

	public void setNewperprop(String newperprop) {
		this.newperprop = newperprop;
	}

	public String getNewunitprop() {
		return newunitprop;
	}

	public void setNewunitprop(String newunitprop) {
		this.newunitprop = newunitprop;
	}

	public String getEndym() {
		return endym;
	}

	public void setEndym(String endym) {
		this.endym = endym;
	}

	public String getSelfintype() {
		return selfintype;
	}

	public void setSelfintype(String selfintype) {
		this.selfintype = selfintype;
	}

	public String getPayamt() {
		return payamt;
	}

	public void setPayamt(String payamt) {
		this.payamt = payamt;
	}

	public String getPaynum() {
		return paynum;
	}

	public void setPaynum(String paynum) {
		this.paynum = paynum;
	}

	public String getSpaytype() {
		return spaytype;
	}

	public void setSpaytype(String spaytype) {
		this.spaytype = spaytype;
	}

	public String getDpsubsidytype() {
		return dpsubsidytype;
	}

	public void setDpsubsidytype(String dpsubsidytype) {
		this.dpsubsidytype = dpsubsidytype;
	}

	public String getQdapprnum() {
		return qdapprnum;
	}

	public void setQdapprnum(String qdapprnum) {
		this.qdapprnum = qdapprnum;
	}

	public String getQdfilepath() {
		return qdfilepath;
	}

	public void setQdfilepath(String qdfilepath) {
		this.qdfilepath = qdfilepath;
	}

	public String getQdfilename() {
		return qdfilename;
	}

	public void setQdfilename(String qdfilename) {
		this.qdfilename = qdfilename;
	}

	public String getPageflag() {
		return pageflag;
	}

	public void setPageflag(String pageflag) {
		this.pageflag = pageflag;
	}

	public String getPagesum() {
		return pagesum;
	}

	public void setPagesum(String pagesum) {
		this.pagesum = pagesum;
	}

	public String getBasebankname() {
		return basebankname;
	}

	public void setBasebankname(String basebankname) {
		this.basebankname = basebankname;
	}

	public String getCreatefileregnum() {
		return createfileregnum;
	}

	public void setCreatefileregnum(String createfileregnum) {
		this.createfileregnum = createfileregnum;
	}

	public String getLeglphone() {
		return leglphone;
	}

	public void setLeglphone(String leglphone) {
		this.leglphone = leglphone;
	}

	public String getLicensenum() {
		return licensenum;
	}

	public void setLicensenum(String licensenum) {
		this.licensenum = licensenum;
	}

	public String getLinkmanmsn() {
		return linkmanmsn;
	}

	public void setLinkmanmsn(String linkmanmsn) {
		this.linkmanmsn = linkmanmsn;
	}

	public String getMsumsalry() {
		return msumsalry;
	}

	public void setMsumsalry(String msumsalry) {
		this.msumsalry = msumsalry;
	}

	public String getTradetype() {
		return tradetype;
	}

	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}

	public String getCreatefile() {
		return createfile;
	}

	public void setCreatefile(String createfile) {
		this.createfile = createfile;
	}

	public String getIndihigprop() {
		return indihigprop;
	}

	public void setIndihigprop(String indihigprop) {
		this.indihigprop = indihigprop;
	}

	public String getIndispeprop() {
		return indispeprop;
	}

	public void setIndispeprop(String indispeprop) {
		this.indispeprop = indispeprop;
	}

	public String getLinkmancertinum() {
		return linkmancertinum;
	}

	public void setLinkmancertinum(String linkmancertinum) {
		this.linkmancertinum = linkmancertinum;
	}

	public String getOrgcode() {
		return orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	public String getRelpelnum() {
		return relpelnum;
	}

	public void setRelpelnum(String relpelnum) {
		this.relpelnum = relpelnum;
	}

	public String getSalbankaccnum() {
		return salbankaccnum;
	}

	public void setSalbankaccnum(String salbankaccnum) {
		this.salbankaccnum = salbankaccnum;
	}

	public String getSalday() {
		return salday;
	}

	public void setSalday(String salday) {
		this.salday = salday;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getNewpubcustid1() {
		return newpubcustid1;
	}

	public void setNewpubcustid1(String newpubcustid1) {
		this.newpubcustid1 = newpubcustid1;
	}

	public String getUnitauth2() {
		return unitauth2;
	}

	public void setUnitauth2(String unitauth2) {
		this.unitauth2 = unitauth2;
	}

	public String getFuncno() {
		return funcno;
	}

	public void setFuncno(String funcno) {
		this.funcno = funcno;
	}

	public String getLeglcertinum() {
		return leglcertinum;
	}

	public void setLeglcertinum(String leglcertinum) {
		this.leglcertinum = leglcertinum;
	}

	public String getUnithigprop() {
		return unithigprop;
	}

	public void setUnithigprop(String unithigprop) {
		this.unithigprop = unithigprop;
	}

	public String getCmfundsouflag() {
		return cmfundsouflag;
	}

	public void setCmfundsouflag(String cmfundsouflag) {
		this.cmfundsouflag = cmfundsouflag;
	}

	public String getLinkmanqq() {
		return linkmanqq;
	}

	public void setLinkmanqq(String linkmanqq) {
		this.linkmanqq = linkmanqq;
	}

	public String getSalbankcode() {
		return salbankcode;
	}

	public void setSalbankcode(String salbankcode) {
		this.salbankcode = salbankcode;
	}

	public String getSpeflag() {
		return speflag;
	}

	public void setSpeflag(String speflag) {
		this.speflag = speflag;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getLeglcertitype() {
		return leglcertitype;
	}

	public void setLeglcertitype(String leglcertitype) {
		this.leglcertitype = leglcertitype;
	}

	public String getLinkmancertitype() {
		return linkmancertitype;
	}

	public void setLinkmancertitype(String linkmancertitype) {
		this.linkmancertitype = linkmancertitype;
	}

	public String getLinkmanemail() {
		return linkmanemail;
	}

	public void setLinkmanemail(String linkmanemail) {
		this.linkmanemail = linkmanemail;
	}

	public String getUnitareacode() {
		return unitareacode;
	}

	public void setUnitareacode(String unitareacode) {
		this.unitareacode = unitareacode;
	}

	public String getUnitauth1() {
		return unitauth1;
	}

	public void setUnitauth1(String unitauth1) {
		this.unitauth1 = unitauth1;
	}

	public String getUnitspeprop() {
		return unitspeprop;
	}

	public void setUnitspeprop(String unitspeprop) {
		this.unitspeprop = unitspeprop;
	}

	public String getUnitycode() {
		return unitycode;
	}

	public void setUnitycode(String unitycode) {
		this.unitycode = unitycode;
	}

	public String getLeglaccname() {
		return leglaccname;
	}

	public void setLeglaccname(String leglaccname) {
		this.leglaccname = leglaccname;
	}

	public String getNewpubcustid2() {
		return newpubcustid2;
	}

	public void setNewpubcustid2(String newpubcustid2) {
		this.newpubcustid2 = newpubcustid2;
	}

	public String getSalbankaccnm() {
		return salbankaccnm;
	}

	public void setSalbankaccnm(String salbankaccnm) {
		this.salbankaccnm = salbankaccnm;
	}

	public String getUnitsoicode() {
		return unitsoicode;
	}

	public void setUnitsoicode(String unitsoicode) {
		this.unitsoicode = unitsoicode;
	}

	public String getAccbankcode() {
		return accbankcode;
	}

	public void setAccbankcode(String accbankcode) {
		this.accbankcode = accbankcode;
	}

	public String getEconomytype() {
		return economytype;
	}

	public void setEconomytype(String economytype) {
		this.economytype = economytype;
	}

	public String getIndinorprop() {
		return indinorprop;
	}

	public void setIndinorprop(String indinorprop) {
		this.indinorprop = indinorprop;
	}

	public String getSupsubrelation() {
		return supsubrelation;
	}

	public void setSupsubrelation(String supsubrelation) {
		this.supsubrelation = supsubrelation;
	}

	public String getUnitlicaddr() {
		return unitlicaddr;
	}

	public void setUnitlicaddr(String unitlicaddr) {
		this.unitlicaddr = unitlicaddr;
	}

	public String getUnitnorprop() {
		return unitnorprop;
	}

	public void setUnitnorprop(String unitnorprop) {
		this.unitnorprop = unitnorprop;
	}

	public String getWebbusmaget() {
		return webbusmaget;
	}

	public void setWebbusmaget(String webbusmaget) {
		this.webbusmaget = webbusmaget;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAcceptreason() {
		return acceptreason;
	}

	public void setAcceptreason(String acceptreason) {
		this.acceptreason = acceptreason;
	}

	public String getAuthop() {
		return authop;
	}

	public void setAuthop(String authop) {
		this.authop = authop;
	}

	public String getBaseuppflag() {
		return baseuppflag;
	}

	public void setBaseuppflag(String baseuppflag) {
		this.baseuppflag = baseuppflag;
	}

	public String getCocietycode() {
		return cocietycode;
	}

	public void setCocietycode(String cocietycode) {
		this.cocietycode = cocietycode;
	}

	public String getComflag() {
		return comflag;
	}

	public void setComflag(String comflag) {
		this.comflag = comflag;
	}

	public String getCrefilnum() {
		return crefilnum;
	}

	public void setCrefilnum(String crefilnum) {
		this.crefilnum = crefilnum;
	}

	public String getDwdz() {
		return dwdz;
	}

	public void setDwdz(String dwdz) {
		this.dwdz = dwdz;
	}

	public String getDwdzxx() {
		return dwdzxx;
	}

	public void setDwdzxx(String dwdzxx) {
		this.dwdzxx = dwdzxx;
	}

	public String getDwfrdbxm() {
		return dwfrdbxm;
	}

	public void setDwfrdbxm(String dwfrdbxm) {
		this.dwfrdbxm = dwfrdbxm;
	}

	public String getDwfrdbzjhm() {
		return dwfrdbzjhm;
	}

	public void setDwfrdbzjhm(String dwfrdbzjhm) {
		this.dwfrdbzjhm = dwfrdbzjhm;
	}

	public String getDwfrdbzjlx() {
		return dwfrdbzjlx;
	}

	public void setDwfrdbzjlx(String dwfrdbzjlx) {
		this.dwfrdbzjlx = dwfrdbzjlx;
	}

	public String getDwfxr() {
		return dwfxr;
	}

	public void setDwfxr(String dwfxr) {
		this.dwfxr = dwfxr;
	}

	public String getDwjjlx() {
		return dwjjlx;
	}

	public void setDwjjlx(String dwjjlx) {
		this.dwjjlx = dwjjlx;
	}

	public String getDwlsgx() {
		return dwlsgx;
	}

	public void setDwlsgx(String dwlsgx) {
		this.dwlsgx = dwlsgx;
	}

	public String getBasebankaccname() {
		return basebankaccname;
	}

	public void setBasebankaccname(String basebankaccname) {
		this.basebankaccname = basebankaccname;
	}

	public String getBasebankaccnum() {
		return basebankaccnum;
	}

	public void setBasebankaccnum(String basebankaccnum) {
		this.basebankaccnum = basebankaccnum;
	}

	public String getDwslrq() {
		return dwslrq;
	}

	public void setDwslrq(String dwslrq) {
		this.dwslrq = dwslrq;
	}

	public String getDwsshy() {
		return dwsshy;
	}

	public void setDwsshy(String dwsshy) {
		this.dwsshy = dwsshy;
	}

	public String getDwyb() {
		return dwyb;
	}

	public void setDwyb(String dwyb) {
		this.dwyb = dwyb;
	}

	public String getFinalflag() {
		return finalflag;
	}

	public void setFinalflag(String finalflag) {
		this.finalflag = finalflag;
	}

	public String getInsurancenum() {
		return insurancenum;
	}

	public void setInsurancenum(String insurancenum) {
		this.insurancenum = insurancenum;
	}

	public String getIsserdispach() {
		return isserdispach;
	}

	public void setIsserdispach(String isserdispach) {
		this.isserdispach = isserdispach;
	}

	public String getJbrgddhhm() {
		return jbrgddhhm;
	}

	public void setJbrgddhhm(String jbrgddhhm) {
		this.jbrgddhhm = jbrgddhhm;
	}

	public String getResuflag() {
		return resuflag;
	}

	public void setResuflag(String resuflag) {
		this.resuflag = resuflag;
	}

	public String getJbrqq() {
		return jbrqq;
	}

	public void setJbrqq(String jbrqq) {
		this.jbrqq = jbrqq;
	}

	public String getJbrrzsj() {
		return jbrrzsj;
	}

	public void setJbrrzsj(String jbrrzsj) {
		this.jbrrzsj = jbrrzsj;
	}

	public String getJbrsfpx() {
		return jbrsfpx;
	}

	public void setJbrsfpx(String jbrsfpx) {
		this.jbrsfpx = jbrsfpx;
	}

	public String getJbrsjhm() {
		return jbrsjhm;
	}

	public void setJbrsjhm(String jbrsjhm) {
		this.jbrsjhm = jbrsjhm;
	}

	public String getJbrxm() {
		return jbrxm;
	}

	public void setJbrxm(String jbrxm) {
		this.jbrxm = jbrxm;
	}

	public String getJbrzjhm() {
		return jbrzjhm;
	}

	public void setJbrzjhm(String jbrzjhm) {
		this.jbrzjhm = jbrzjhm;
	}

	public String getJbrzjlx() {
		return jbrzjlx;
	}

	public void setJbrzjlx(String jbrzjlx) {
		this.jbrzjlx = jbrzjlx;
	}

	public String getMngdept() {
		return mngdept;
	}

	public void setMngdept(String mngdept) {
		this.mngdept = mngdept;
	}

	public String getMngdepter() {
		return mngdepter;
	}

	public void setMngdepter(String mngdepter) {
		this.mngdepter = mngdepter;
	}

	public String getMngdepterphone() {
		return mngdepterphone;
	}

	public void setMngdepterphone(String mngdepterphone) {
		this.mngdepterphone = mngdepterphone;
	}

	public String getOrgtype() {
		return orgtype;
	}

	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}

	public String getOwnershipkind() {
		return ownershipkind;
	}

	public void setOwnershipkind(String ownershipkind) {
		this.ownershipkind = ownershipkind;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUnitkind() {
		return unitkind;
	}

	public void setUnitkind(String unitkind) {
		this.unitkind = unitkind;
	}

	public String getUseflag() {
		return useflag;
	}

	public void setUseflag(String useflag) {
		this.useflag = useflag;
	}

	public String getWorkernum() {
		return workernum;
	}

	public void setWorkernum(String workernum) {
		this.workernum = workernum;
	}

	public String getZzjgdm() {
		return zzjgdm;
	}

	public void setZzjgdm(String zzjgdm) {
		this.zzjgdm = zzjgdm;
	}

	public String getDwfrdbsjhm() {
		return dwfrdbsjhm;
	}

	public void setDwfrdbsjhm(String dwfrdbsjhm) {
		this.dwfrdbsjhm = dwfrdbsjhm;
	}

	public String getCagorcode() {
		return cagorcode;
	}

	public void setCagorcode(String cagorcode) {
		this.cagorcode = cagorcode;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getCtlflag() {
		return ctlflag;
	}

	public void setCtlflag(String ctlflag) {
		this.ctlflag = ctlflag;
	}

	public String getTrcode() {
		return trcode;
	}

	public void setTrcode(String trcode) {
		this.trcode = trcode;
	}

}
