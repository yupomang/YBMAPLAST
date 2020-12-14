package com.yondervision.mi.result;

/** 
* @ClassName: AppApi00901Result 
* @Description: TODO
* @author Caozhongyan
* @date Oct 11, 2013 6:58:49 PM   
* 
*/ 
public class AppApi00901Result {	
	/**
	 * 月还款额
	 */
	private String monthRepaymentAmount = "";
	/**
	 * 应还利息总额
	 */
	private String repaymentRateSum = "";
	/**
	 * 还款总额
	 */
	private String repaymentSum = "";
	
	/**
	 * 商贷月还款额
	 */
	private String sdmonthRepaymentAmount = "";
	/**
	 * 商贷应还利息总额
	 */
	private String sdrepaymentRateSum = "";
	/**
	 * 商贷还款总额
	 */
	private String sdrepaymentSum = "";
	/**
	 * 两种还贷方式的差额
	 */
	private String balance = "";
	
	public String getMonthRepaymentAmount() {
		return monthRepaymentAmount;
	}
	public void setMonthRepaymentAmount(String monthRepaymentAmount) {
		this.monthRepaymentAmount = monthRepaymentAmount;
	}
	public String getRepaymentRateSum() {
		return repaymentRateSum;
	}
	public void setRepaymentRateSum(String repaymentRateSum) {
		this.repaymentRateSum = repaymentRateSum;
	}
	public String getRepaymentSum() {
		return repaymentSum;
	}
	public void setRepaymentSum(String repaymentSum) {
		this.repaymentSum = repaymentSum;
	}
	public String getSdmonthRepaymentAmount() {
		return sdmonthRepaymentAmount;
	}
	public void setSdmonthRepaymentAmount(String sdmonthRepaymentAmount) {
		this.sdmonthRepaymentAmount = sdmonthRepaymentAmount;
	}
	public String getSdrepaymentRateSum() {
		return sdrepaymentRateSum;
	}
	public void setSdrepaymentRateSum(String sdrepaymentRateSum) {
		this.sdrepaymentRateSum = sdrepaymentRateSum;
	}
	public String getSdrepaymentSum() {
		return sdrepaymentSum;
	}
	public void setSdrepaymentSum(String sdrepaymentSum) {
		this.sdrepaymentSum = sdrepaymentSum;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
}
