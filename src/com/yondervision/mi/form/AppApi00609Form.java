package com.yondervision.mi.form;


public class AppApi00609Form extends AppApiCommonForm {
	/** 是否分页标识 */	
	private String ispaging = "";
	/** 页码 */				
	private String pagenum = "";				
	/** 行数 */				
	private String pagerows = "";
	
	/** 提前全部还款金额 */			
	private String allamt = "";			
	/** 提前部分还款金额 */			
	private String dealamt = "";			
	/** 提前部分还款日 */			
	private String dealdate = "";			
	/** 是否转换还款方式 */			
	private String ifchgretmode = "";			
	/** 是否缩期 */			
	private String ifsq = "";			
	/** 借款合同号 */			
	private String loancontrnum = "";			
	/** 提前部分还款最小额 */			
	private String minamt = "";			
	/** 缩期后的贷款到期日 */			
	private String nattermdate = "";			
	/** 新产生利息 */			
	private String newcint = "";			
	/** 缩期后的剩余还款期数 */			
	private String nloantermM = "";			
	/** 缩期后的剩余年限 */			
	private String nloantermY = "";			
	/** 新还款方式 */			
	private String nretloanmode = "";			
	/** 欠款合计 */			
	private String oweall = "";			
	/** 欠利息 */			
	private String oweint = "";			
	/** 欠本金 */			
	private String oweprin = "";			
	/** 欠罚息 */			
	private String owepun = "";			
	/** 打印模式 */			
	private String printflag = "";			
	/** 缩短年限 */			
	private String SDtermY = "";			
	/** 未到期本金 */			
	private String undueprin = "";			
	/** 还款日期  */	
	private String payday = "";
	
	public String getPayday() {
		return payday;
	}

	public void setPayday(String payday) {
		this.payday = payday;
	}

	/**			
	 *<pre> 执行获取提前全部还款金额处理 </pre>			
	 * @return allamt 提前全部还款金额			
	 */			
	public String getAllamt() {			
	    return allamt;			
	}			
				
	/**			
	 *<pre> 执行设定提前全部还款金额处理 </pre>			
	 * @param allamt 提前全部还款金额			
	 */			
	public void setAllamt(String allamt) {			
	    this.allamt = allamt;			
	}			
				
	/**			
	 *<pre> 执行获取提前部分还款金额处理 </pre>			
	 * @return dealamt 提前部分还款金额			
	 */			
	public String getDealamt() {			
	    return dealamt;			
	}			
				
	/**			
	 *<pre> 执行设定提前部分还款金额处理 </pre>			
	 * @param dealamt 提前部分还款金额			
	 */			
	public void setDealamt(String dealamt) {			
	    this.dealamt = dealamt;			
	}			
				
	/**			
	 *<pre> 执行获取提前部分还款日处理 </pre>			
	 * @return dealdate 提前部分还款日			
	 */			
	public String getDealdate() {			
	    return dealdate;			
	}			
				
	/**			
	 *<pre> 执行设定提前部分还款日处理 </pre>			
	 * @param dealdate 提前部分还款日			
	 */			
	public void setDealdate(String dealdate) {			
	    this.dealdate = dealdate;			
	}			
				
	/**			
	 *<pre> 执行获取是否转换还款方式处理 </pre>			
	 * @return ifchgretmode 是否转换还款方式			
	 */			
	public String getIfchgretmode() {			
	    return ifchgretmode;			
	}			
				
	/**			
	 *<pre> 执行设定是否转换还款方式处理 </pre>			
	 * @param ifchgretmode 是否转换还款方式			
	 */			
	public void setIfchgretmode(String ifchgretmode) {			
	    this.ifchgretmode = ifchgretmode;			
	}			
				
	/**			
	 *<pre> 执行获取是否缩期处理 </pre>			
	 * @return ifsq 是否缩期			
	 */			
	public String getIfsq() {			
	    return ifsq;			
	}			
				
	/**			
	 *<pre> 执行设定是否缩期处理 </pre>			
	 * @param ifsq 是否缩期			
	 */			
	public void setIfsq(String ifsq) {			
	    this.ifsq = ifsq;			
	}			
				
	/**			
	 *<pre> 执行获取借款合同号处理 </pre>			
	 * @return loancontrnum 借款合同号			
	 */			
	public String getLoancontrnum() {			
	    return loancontrnum;			
	}			
				
	/**			
	 *<pre> 执行设定借款合同号处理 </pre>			
	 * @param loancontrnum 借款合同号			
	 */			
	public void setLoancontrnum(String loancontrnum) {			
	    this.loancontrnum = loancontrnum;			
	}			
				
	/**			
	 *<pre> 执行获取提前部分还款最小额处理 </pre>			
	 * @return minamt 提前部分还款最小额			
	 */			
	public String getMinamt() {			
	    return minamt;			
	}			
				
	/**			
	 *<pre> 执行设定提前部分还款最小额处理 </pre>			
	 * @param minamt 提前部分还款最小额			
	 */			
	public void setMinamt(String minamt) {			
	    this.minamt = minamt;			
	}			
				
	/**			
	 *<pre> 执行获取缩期后的贷款到期日处理 </pre>			
	 * @return nattermdate 缩期后的贷款到期日			
	 */			
	public String getNattermdate() {			
	    return nattermdate;			
	}			
				
	/**			
	 *<pre> 执行设定缩期后的贷款到期日处理 </pre>			
	 * @param nattermdate 缩期后的贷款到期日			
	 */			
	public void setNattermdate(String nattermdate) {			
	    this.nattermdate = nattermdate;			
	}			
				
	/**			
	 *<pre> 执行获取新产生利息处理 </pre>			
	 * @return newcint 新产生利息			
	 */			
	public String getNewcint() {			
	    return newcint;			
	}			
				
	/**			
	 *<pre> 执行设定新产生利息处理 </pre>			
	 * @param newcint 新产生利息			
	 */			
	public void setNewcint(String newcint) {			
	    this.newcint = newcint;			
	}			
				
	/**			
	 *<pre> 执行获取缩期后的剩余还款期数处理 </pre>			
	 * @return nloantermM 缩期后的剩余还款期数			
	 */			
	public String getNloantermM() {			
	    return nloantermM;			
	}			
				
	/**			
	 *<pre> 执行设定缩期后的剩余还款期数处理 </pre>			
	 * @param nloantermM 缩期后的剩余还款期数			
	 */			
	public void setNloantermM(String nloantermM) {			
	    this.nloantermM = nloantermM;			
	}			
				
	/**			
	 *<pre> 执行获取缩期后的剩余年限处理 </pre>			
	 * @return nloantermY 缩期后的剩余年限			
	 */			
	public String getNloantermY() {			
	    return nloantermY;			
	}			
				
	/**			
	 *<pre> 执行设定缩期后的剩余年限处理 </pre>			
	 * @param nloantermY 缩期后的剩余年限			
	 */			
	public void setNloantermY(String nloantermY) {			
	    this.nloantermY = nloantermY;			
	}			
				
	/**			
	 *<pre> 执行获取新还款方式处理 </pre>			
	 * @return nretloanmode 新还款方式			
	 */			
	public String getNretloanmode() {			
	    return nretloanmode;			
	}			
				
	/**			
	 *<pre> 执行设定新还款方式处理 </pre>			
	 * @param nretloanmode 新还款方式			
	 */			
	public void setNretloanmode(String nretloanmode) {			
	    this.nretloanmode = nretloanmode;			
	}			
				
	/**			
	 *<pre> 执行获取欠款合计处理 </pre>			
	 * @return oweall 欠款合计			
	 */			
	public String getOweall() {			
	    return oweall;			
	}			
				
	/**			
	 *<pre> 执行设定欠款合计处理 </pre>			
	 * @param oweall 欠款合计			
	 */			
	public void setOweall(String oweall) {			
	    this.oweall = oweall;			
	}			
				
	/**			
	 *<pre> 执行获取欠利息处理 </pre>			
	 * @return oweint 欠利息			
	 */			
	public String getOweint() {			
	    return oweint;			
	}			
				
	/**			
	 *<pre> 执行设定欠利息处理 </pre>			
	 * @param oweint 欠利息			
	 */			
	public void setOweint(String oweint) {			
	    this.oweint = oweint;			
	}			
				
	/**			
	 *<pre> 执行获取欠本金处理 </pre>			
	 * @return oweprin 欠本金			
	 */			
	public String getOweprin() {			
	    return oweprin;			
	}			
				
	/**			
	 *<pre> 执行设定欠本金处理 </pre>			
	 * @param oweprin 欠本金			
	 */			
	public void setOweprin(String oweprin) {			
	    this.oweprin = oweprin;			
	}			
				
	/**			
	 *<pre> 执行获取欠罚息处理 </pre>			
	 * @return owepun 欠罚息			
	 */			
	public String getOwepun() {			
	    return owepun;			
	}			
				
	/**			
	 *<pre> 执行设定欠罚息处理 </pre>			
	 * @param owepun 欠罚息			
	 */			
	public void setOwepun(String owepun) {			
	    this.owepun = owepun;			
	}			
				
	/**			
	 *<pre> 执行获取打印模式处理 </pre>			
	 * @return printflag 打印模式			
	 */			
	public String getPrintflag() {			
	    return printflag;			
	}			
				
	/**			
	 *<pre> 执行设定打印模式处理 </pre>			
	 * @param printflag 打印模式			
	 */			
	public void setPrintflag(String printflag) {			
	    this.printflag = printflag;			
	}			
				
	/**			
	 *<pre> 执行获取缩短年限处理 </pre>			
	 * @return SDtermY 缩短年限			
	 */			
	public String getSDtermY() {			
	    return SDtermY;			
	}			
				
	/**			
	 *<pre> 执行设定缩短年限处理 </pre>			
	 * @param SDtermY 缩短年限			
	 */			
	public void setSDtermY(String SDtermY) {			
	    this.SDtermY = SDtermY;			
	}			
				
	/**			
	 *<pre> 执行获取未到期本金处理 </pre>			
	 * @return undueprin 未到期本金			
	 */			
	public String getUndueprin() {			
	    return undueprin;			
	}			
				
	/**			
	 *<pre> 执行设定未到期本金处理 </pre>			
	 * @param undueprin 未到期本金			
	 */			
	public void setUndueprin(String undueprin) {			
	    this.undueprin = undueprin;			
	}

	public String getIspaging() {
		return ispaging;
	}

	public String getPagenum() {
		return pagenum;
	}

	public String getPagerows() {
		return pagerows;
	}

	public void setIspaging(String ispaging) {
		this.ispaging = ispaging;
	}

	public void setPagenum(String pagenum) {
		this.pagenum = pagenum;
	}

	public void setPagerows(String pagerows) {
		this.pagerows = pagerows;
	}			
	
}
