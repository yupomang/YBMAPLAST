package com.yondervision.mi.form;

/** 
* @ClassName: AppApi00905Form 
* @Description: TODO
* @author Caozhongyan
* @date Oct 12, 2013 10:57:29 AM   
* 
*/ 
public class AppApi00907Form extends AppApiCommonForm{
	/**
	 * 贷款类型
	 */
	private String dklx;
	/**
	 * 贷款金额
	 */
	private String dkje;
	/**
	 * 商业贷款金额
	 */
	private String sdje;
	/**
	 * 贷款期限
	 */
	private String dknx;
	/**
	 * 还款方式
	 */
	private String hkfs;
	/**
	 * 利率倍数
	 */
	private String llbs;
	/**
	 * 贷款期限（月数）
	 */
	private String loanDuration;
	
	public String getDkje() {
		return dkje;
	}
	public void setDkje(String dkje) {
		this.dkje = dkje;
	}
	public String getDknx() {
		return dknx;
	}
	public void setDknx(String dknx) {
		this.dknx = dknx;
	}
	public String getHkfs() {
		return hkfs;
	}
	public void setHkfs(String hkfs) {
		this.hkfs = hkfs;
	}
	public String getLlbs() {
		return llbs;
	}
	public void setLlbs(String llbs) {
		this.llbs = llbs;
	}
	public String getLoanDuration() {
		return loanDuration;
	}
	public void setLoanDuration(String loanDuration) {
		this.loanDuration = loanDuration;
	}
	
}
