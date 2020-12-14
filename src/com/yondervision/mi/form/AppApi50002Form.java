package com.yondervision.mi.form;

public class AppApi50002Form extends AppApiCommonForm{	
	private String  speflag;//网厅登录检查密码标志 0-检查 1-不检查

	/** U盾序列号 */			
	private String ukeynum = "";	
	/** 单位专办员号 */			
	private String opercode = "";			
	/** 单位专办员证件号码 */			
	private String certinum = "";			
	/** 公积金单位密码 */			
	private String password = "";			
	/** 手机号码 */			
	private String unitlinkphone2 = "";			
	/** 单位专办员姓名 */			
	private String opname = "";			
	/** 手机短信验证码 */			
	private String checkcode = "";			
	/** 新密码 */			
	private String newpasswd = "";			
	/** 单位账号 */			
	private String unitaccnum = "";			
	/** 单位客户号 */			
	private String unitcustid = "";			
	/** 组织机构代码 */			
	private String orgcode = "";			
	/** 社会信用代码 */			
	private String licensenum = "";			
	/** 单位名称 */			
	private String unitaccname = "";			
	/** 单位地址 */			
	private String unitaddr = "";	
	/** 成立时间 */			
	private String exitdate = "";			
	//======株洲   开始=======	
		/** 登录方式 */
		private String dlfs = "";
		/** 证书编号 */
		private String zsbh = "";
		/** 确认新密码 */
		private String confirmnewpassword="";
		
	//======株洲   结束=======	
	
	public String getConfirmnewpassword() {
		return confirmnewpassword;
	}

	public void setConfirmnewpassword(String confirmnewpassword) {
		this.confirmnewpassword = confirmnewpassword;
	}

	public String getDlfs() {
		return dlfs;
	}

	public void setDlfs(String dlfs) {
		this.dlfs = dlfs;
	}

	public String getZsbh() {
		return zsbh;
	}

	public void setZsbh(String zsbh) {
		this.zsbh = zsbh;
	}
	/**			
	 *<pre> 执行获取成立时间处理 </pre>			
	 * @return exitdate 成立时间			
	 */			
	public String getExitdate() {			
	    return exitdate;			
	}			
				
	/**			
	 *<pre> 执行设定成立时间处理 </pre>			
	 * @param exitdate 成立时间			
	 */			
	public void setExitdate(String exitdate) {			
	    this.exitdate = exitdate;			
	}			
				
	

				
	/**			
	 *<pre> 执行获取单位地址处理 </pre>			
	 * @return unitaddr 单位地址			
	 */			
	public String getUnitaddr() {			
	    return unitaddr;			
	}			
				
	/**			
	 *<pre> 执行设定单位地址处理 </pre>			
	 * @param unitaddr 单位地址			
	 */			
	public void setUnitaddr(String unitaddr) {			
	    this.unitaddr = unitaddr;			
	}			
			
	/**			
	 *<pre> 执行获取单位专办员号处理 </pre>			
	 * @return opercode 单位专办员号			
	 */			
	public String getOpercode() {			
	    return opercode;			
	}			
				
	/**			
	 *<pre> 执行设定单位专办员号处理 </pre>			
	 * @param opercode 单位专办员号			
	 */			
	public void setOpercode(String opercode) {			
	    this.opercode = opercode;			
	}			
				
	/**			
	 *<pre> 执行获取单位专办员证件号码处理 </pre>			
	 * @return certinum 单位专办员证件号码			
	 */			
	public String getCertinum() {			
	    return certinum;			
	}			
				
	/**			
	 *<pre> 执行设定单位专办员证件号码处理 </pre>			
	 * @param certinum 单位专办员证件号码			
	 */			
	public void setCertinum(String certinum) {			
	    this.certinum = certinum;			
	}			
				
	/**			
	 *<pre> 执行获取公积金单位密码处理 </pre>			
	 * @return password 公积金单位密码			
	 */			
	public String getPassword() {			
	    return password;			
	}			
				
	/**			
	 *<pre> 执行设定公积金单位密码处理 </pre>			
	 * @param password 公积金单位密码			
	 */			
	public void setPassword(String password) {			
	    this.password = password;			
	}			
				
	/**			
	 *<pre> 执行获取手机号码处理 </pre>			
	 * @return unitlinkphone2 手机号码			
	 */			
	public String getUnitlinkphone2() {			
	    return unitlinkphone2;			
	}			
				
	/**			
	 *<pre> 执行设定手机号码处理 </pre>			
	 * @param unitlinkphone2 手机号码			
	 */			
	public void setUnitlinkphone2(String unitlinkphone2) {			
	    this.unitlinkphone2 = unitlinkphone2;			
	}			
				
	/**			
	 *<pre> 执行获取单位专办员姓名处理 </pre>			
	 * @return opname 单位专办员姓名			
	 */			
	public String getOpname() {			
	    return opname;			
	}			
				
	/**			
	 *<pre> 执行设定单位专办员姓名处理 </pre>			
	 * @param opname 单位专办员姓名			
	 */			
	public void setOpname(String opname) {			
	    this.opname = opname;			
	}			
				
	/**			
	 *<pre> 执行获取手机短信验证码处理 </pre>			
	 * @return checkcode 手机短信验证码			
	 */			
	public String getCheckcode() {			
	    return checkcode;			
	}			
				
	/**			
	 *<pre> 执行设定手机短信验证码处理 </pre>			
	 * @param checkcode 手机短信验证码			
	 */			
	public void setCheckcode(String checkcode) {			
	    this.checkcode = checkcode;			
	}			
				
	/**			
	 *<pre> 执行获取新密码处理 </pre>			
	 * @return newpasswd 新密码			
	 */			
	public String getNewpasswd() {			
	    return newpasswd;			
	}			
				
	/**			
	 *<pre> 执行设定新密码处理 </pre>			
	 * @param newpasswd 新密码			
	 */			
	public void setNewpasswd(String newpasswd) {			
	    this.newpasswd = newpasswd;			
	}			
				
	/**			
	 *<pre> 执行获取单位账号处理 </pre>			
	 * @return unitaccnum 单位账号			
	 */			
	public String getUnitaccnum() {			
	    return unitaccnum;			
	}			
				
	/**			
	 *<pre> 执行设定单位账号处理 </pre>			
	 * @param unitaccnum 单位账号			
	 */			
	public void setUnitaccnum(String unitaccnum) {			
	    this.unitaccnum = unitaccnum;			
	}			
				
	/**			
	 *<pre> 执行获取单位客户号处理 </pre>			
	 * @return unitcustid 单位客户号			
	 */			
	public String getUnitcustid() {			
	    return unitcustid;			
	}			
				
	/**			
	 *<pre> 执行设定单位客户号处理 </pre>			
	 * @param unitcustid 单位客户号			
	 */			
	public void setUnitcustid(String unitcustid) {			
	    this.unitcustid = unitcustid;			
	}			
				
	/**			
	 *<pre> 执行获取组织机构代码处理 </pre>			
	 * @return orgcode 组织机构代码			
	 */			
	public String getOrgcode() {			
	    return orgcode;			
	}			
				
	/**			
	 *<pre> 执行设定组织机构代码处理 </pre>			
	 * @param orgcode 组织机构代码			
	 */			
	public void setOrgcode(String orgcode) {			
	    this.orgcode = orgcode;			
	}			
				
	/**			
	 *<pre> 执行获取社会信用代码处理 </pre>			
	 * @return licensenum 社会信用代码			
	 */			
	public String getLicensenum() {			
	    return licensenum;			
	}			
				
	/**			
	 *<pre> 执行设定社会信用代码处理 </pre>			
	 * @param licensenum 社会信用代码			
	 */			
	public void setLicensenum(String licensenum) {			
	    this.licensenum = licensenum;			
	}			
				
	/**			
	 *<pre> 执行获取单位名称处理 </pre>			
	 * @return unitaccname 单位名称			
	 */			
	public String getUnitaccname() {			
	    return unitaccname;			
	}			
				
	/**			
	 *<pre> 执行设定单位名称处理 </pre>			
	 * @param unitaccname 单位名称			
	 */			
	public void setUnitaccname(String unitaccname) {			
	    this.unitaccname = unitaccname;			
	}

	public String getUkeynum() {
		return ukeynum;
	}

	public void setUkeynum(String ukeynum) {
		this.ukeynum = ukeynum;
	}

	public String getSpeflag() {
		return speflag;
	}

	public void setSpeflag(String speflag) {
		this.speflag = speflag;
	}			

}
