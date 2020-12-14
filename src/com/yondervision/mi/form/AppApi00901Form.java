/**
 * 
 */
package com.yondervision.mi.form;

/** 
* @ClassName: AppApi00901Form 
* @Description: TODO
* @author Caozhongyan
* @date Oct 11, 2013 7:02:00 PM   
* 
*/ 
public class AppApi00901Form extends AppApiCommonForm {
	/**
	 * 公积金贷款金额
	 */
	private String surplusLoanAmount;
	/**
	 * 商业贷款金额
	 */
	private String commercialLoanAmount;
	/**
	 * 贷款期限
	 */
	private String loanDuration;
	/**
	 * 公积金贷款利率
	 */
	private String surplusLoanInterestRate;
	/**
	 * 商业贷款利率
	 */
	private String commercialLoanInterestRate;
	/**
	 * 还款方式
	 */
	private String repaymentType;
	public String getSurplusLoanAmount() {
		return surplusLoanAmount;
	}
	public void setSurplusLoanAmount(String surplusLoanAmount) {
		this.surplusLoanAmount = surplusLoanAmount;
	}
	public String getCommercialLoanAmount() {
		return commercialLoanAmount;
	}
	public void setCommercialLoanAmount(String commercialLoanAmount) {
		this.commercialLoanAmount = commercialLoanAmount;
	}
	public String getLoanDuration() {
		return loanDuration;
	}
	public void setLoanDuration(String loanDuration) {
		this.loanDuration = loanDuration;
	}
	public String getSurplusLoanInterestRate() {
		return surplusLoanInterestRate;
	}
	public void setSurplusLoanInterestRate(String surplusLoanInterestRate) {
		this.surplusLoanInterestRate = surplusLoanInterestRate;
	}
	public String getCommercialLoanInterestRate() {
		return commercialLoanInterestRate;
	}
	public void setCommercialLoanInterestRate(String commercialLoanInterestRate) {
		this.commercialLoanInterestRate = commercialLoanInterestRate;
	}
	public String getRepaymentType() {
		return repaymentType;
	}
	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}
}
