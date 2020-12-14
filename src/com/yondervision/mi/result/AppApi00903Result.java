package com.yondervision.mi.result;

/** 
* @ClassName: AppApi00903Result 
* @Description: TODO
* @author Caozhongyan
* @date Oct 12, 2013 8:53:59 AM   
* 
*/ 
public class AppApi00903Result {
	// 期数
	  private String repaymentNumber = "";

	  // 应还本金
	  private String repaymentPrincipal = "";

	  // 应还利息
	  private String repaymentInterest = "";

	  // 月还款额
	  private String monthRepaymentAmount = "";

	  // 贷款余额
	  private String loanBalance = "";

	  /**
	   * 期数取得<BR>
	   * 
	   * @return 期数
	   */
	  public String getRepaymentNumber() {
	    return repaymentNumber;
	  }

	  /**
	   * 期数赋值<BR>
	   * 
	   * @param repaymentNumber 期数
	   */
	  public void setRepaymentNumber(String repaymentNumber) {
	    this.repaymentNumber = repaymentNumber;
	  }

	  /**
	   * 应还本金取得<BR>
	   * 
	   * @return 应还本金
	   */
	  public String getRepaymentPrincipal() {
	    return repaymentPrincipal;
	  }

	  /**
	   * 应还本金赋值<BR>
	   * 
	   * @param repaymentPrincipal 应还本金
	   */
	  public void setRepaymentPrincipal(String repaymentPrincipal) {
	    this.repaymentPrincipal = repaymentPrincipal;
	  }

	  /**
	   * 应还利息取得<BR>
	   * 
	   * @return 应还利息
	   */
	  public String getRepaymentInterest() {
	    return repaymentInterest;
	  }

	  /**
	   * 应还利息赋值<BR>
	   * 
	   * @param repaymentInterest 应还利息
	   */
	  public void setRepaymentInterest(String repaymentInterest) {
	    this.repaymentInterest = repaymentInterest;
	  }

	  /**
	   * 月还款额取得<BR>
	   * 
	   * @return 月还款额
	   */
	  public String getMonthRepaymentAmount() {
	    return monthRepaymentAmount;
	  }

	  /**
	   * 月还款额赋值<BR>
	   * 
	   * @param monthRepaymentAmount 月还款额
	   */
	  public void setMonthRepaymentAmount(String monthRepaymentAmount) {
	    this.monthRepaymentAmount = monthRepaymentAmount;
	  }

	  /**
	   * 贷款余额取得<BR>
	   * 
	   * @return 贷款余额
	   */
	  public String getLoanBalance() {
	    return loanBalance;
	  }

	  /**
	   * 贷款余额赋值<BR>
	   * 
	   * @param loanBalance 贷款余额
	   */
	  public void setLoanBalance(String loanBalance) {
	    this.loanBalance = loanBalance;
	  }
}
