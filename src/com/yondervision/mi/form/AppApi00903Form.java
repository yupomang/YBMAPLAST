package com.yondervision.mi.form;

/** 
* @ClassName: AppApi00903Form 
* @Description: TODO
* @author Caozhongyan
* @date Oct 12, 2013 11:02:24 AM   
* 
*/ 
public class AppApi00903Form extends AppApiCommonForm{
	// 公积金贷款金额
	  private String surplusLoanAmount = "";

	  // 贷款期限
	  private String loanDuration = "";

	  // 公积金贷款利率
	  private String surplusLoanInterestRate = "";

	  // 还款方式
	  private String repaymentType = "";

	  /**
	   * 公积金贷款金额取得<BR>
	   * 
	   * @return 公积金贷款金额
	   */
	  public String getSurplusLoanAmount() {
	    return surplusLoanAmount;
	  }

	  /**
	   * 公积金贷款金额赋值<BR>
	   * 
	   * @param surplusLoanAmount 公积金贷款金额
	   */
	  public void setSurplusLoanAmount(String surplusLoanAmount) {
	    this.surplusLoanAmount = surplusLoanAmount;
	  }

	  /**
	   * 贷款期限取得<BR>
	   * 
	   * @return 贷款期限
	   */
	  public String getLoanDuration() {
	    return loanDuration;
	  }

	  /**
	   * 贷款期限赋值<BR>
	   * 
	   * @param loanDuration 贷款期限
	   */
	  public void setLoanDuration(String loanDuration) {
	    this.loanDuration = loanDuration;
	  }

	  /**
	   * 公积金贷款利率取得<BR>
	   * 
	   * @return 公积金贷款利率
	   */
	  public String getSurplusLoanInterestRate() {
	    return surplusLoanInterestRate;
	  }

	  /**
	   * 公积金贷款利率赋值<BR>
	   * 
	   * @param surplusLoanInterestRate 公积金贷款利率
	   */
	  public void setSurplusLoanInterestRate(String surplusLoanInterestRate) {
	    this.surplusLoanInterestRate = surplusLoanInterestRate;
	  }

	  /**
	   * 还款方式取得<BR>
	   * 
	   * @return 还款方式
	   */
	  public String getRepaymentType() {
	    return repaymentType;
	  }

	  /**
	   * 还款方式赋值<BR>
	   * 
	   * @param repaymentType 还款方式
	   */
	  public void setRepaymentType(String repaymentType) {
	    this.repaymentType = repaymentType;
	  }
}
