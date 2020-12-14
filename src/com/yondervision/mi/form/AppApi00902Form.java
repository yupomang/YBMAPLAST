package com.yondervision.mi.form;

/** 
* @ClassName: AppApi00902Form 
* @Description: TODO
* @author Caozhongyan
* @date Oct 12, 2013 11:02:31 AM   
* 
*/ 
public class AppApi00902Form extends AppApiCommonForm{
	// 月还款额
	  private String monthRepaymentAmount = "";

	  // 还款总额
	  private String repaymentSum = "";

	  // 商业贷款金额
	  private String commercialLoanAmount = "";

	  // 贷款期限
	  private String loanDuration = "";

	  // 商业贷款利率
	  private String commercialLoanInterestRate = "";

	  // 还款方式
	  private String repaymentType = "";

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
	   * 还款总额取得<BR>
	   * 
	   * @return 还款总额
	   */
	  public String getRepaymentSum() {
	    return repaymentSum;
	  }

	  /**
	   * 还款总额赋值<BR>
	   * 
	   * @param repaymentSum 还款总额
	   */
	  public void setRepaymentSum(String repaymentSum) {
	    this.repaymentSum = repaymentSum;
	  }

	  /**
	   * 商业贷款金额取得<BR>
	   * 
	   * @return 商业贷款金额
	   */
	  public String getCommercialLoanAmount() {
	    return commercialLoanAmount;
	  }

	  /**
	   * 商业贷款金额赋值<BR>
	   * 
	   * @param commercialLoanAmount 商业贷款金额
	   */
	  public void setCommercialLoanAmount(String commercialLoanAmount) {
	    this.commercialLoanAmount = commercialLoanAmount;
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
	   * 商业贷款利率取得<BR>
	   * 
	   * @return 商业贷款利率
	   */
	  public String getCommercialLoanInterestRate() {
	    return commercialLoanInterestRate;
	  }

	  /**
	   * 商业贷款利率赋值<BR>
	   * 
	   * @param commercialLoanInterestRate 商业贷款利率
	   */
	  public void setCommercialLoanInterestRate(String commercialLoanInterestRate) {
	    this.commercialLoanInterestRate = commercialLoanInterestRate;
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
