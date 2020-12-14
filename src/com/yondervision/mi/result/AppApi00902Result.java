package com.yondervision.mi.result;

/** 
* @ClassName: AppApi00902Result 
* @Description: 与商贷比较
* @author Caozhongyan
* @date Oct 11, 2013 9:44:19 PM   
* 
*/ 
public class AppApi00902Result {
	// 月还款额(公积金贷款)
	  private String surplusMonthRepaymentAmount = "";

	  // 还款总额(公积金贷款)
	  private String surplusRepaymentSum = "";

	  // 月还款额(商业贷款)
	  private String commercialMonthRepaymentAmount = "";

	  // 还款总额(商业贷款)
	  private String commercialRepaymentSum = "";

	  // 月还款额(差额)
	  private String balanceMonthRepaymentAmount = "";

	  // 还款总额(差额)
	  private String balanceRepaymentSum = "";

	  /**
	   * 月还款额(公积金贷款)取得<BR>
	   * 
	   * @return 月还款额(公积金贷款)
	   */
	  public String getSurplusMonthRepaymentAmount() {
	    return surplusMonthRepaymentAmount;
	  }

	  /**
	   * 月还款额(公积金贷款)赋值<BR>
	   * 
	   * @param surplusMonthRepaymentAmount 月还款额(公积金贷款)
	   */
	  public void setSurplusMonthRepaymentAmount(String surplusMonthRepaymentAmount) {
	    this.surplusMonthRepaymentAmount = surplusMonthRepaymentAmount;
	  }

	  /**
	   * 还款总额(公积金贷款)取得<BR>
	   * 
	   * @return 还款总额(公积金贷款)
	   */
	  public String getSurplusRepaymentSum() {
	    return surplusRepaymentSum;
	  }

	  /**
	   * 还款总额(公积金贷款)赋值<BR>
	   * 
	   * @param surplusRepaymentSum 还款总额(公积金贷款)
	   */
	  public void setSurplusRepaymentSum(String surplusRepaymentSum) {
	    this.surplusRepaymentSum = surplusRepaymentSum;
	  }

	  /**
	   * 月还款额(商业贷款)取得<BR>
	   * 
	   * @return 月还款额(商业贷款)
	   */
	  public String getCommercialMonthRepaymentAmount() {
	    return commercialMonthRepaymentAmount;
	  }

	  /**
	   * 月还款额(商业贷款)赋值<BR>
	   * 
	   * @param commercialMonthRepaymentAmount 月还款额(商业贷款)
	   */
	  public void setCommercialMonthRepaymentAmount(String commercialMonthRepaymentAmount) {
	    this.commercialMonthRepaymentAmount = commercialMonthRepaymentAmount;
	  }

	  /**
	   * 还款总额(商业贷款)取得<BR>
	   * 
	   * @return 还款总额(商业贷款)
	   */
	  public String getCommercialRepaymentSum() {
	    return commercialRepaymentSum;
	  }

	  /**
	   * 还款总额(商业贷款)赋值<BR>
	   * 
	   * @param commercialRepaymentSum 还款总额(商业贷款)
	   */
	  public void setCommercialRepaymentSum(String commercialRepaymentSum) {
	    this.commercialRepaymentSum = commercialRepaymentSum;
	  }

	  /**
	   * 月还款额(差额)取得<BR>
	   * 
	   * @return 月还款额(差额)
	   */
	  public String getBalanceMonthRepaymentAmount() {
	    return balanceMonthRepaymentAmount;
	  }

	  /**
	   * 月还款额(差额)赋值<BR>
	   * 
	   * @param balanceMonthRepaymentAmount 月还款额(差额)
	   */
	  public void setBalanceMonthRepaymentAmount(String balanceMonthRepaymentAmount) {
	    this.balanceMonthRepaymentAmount = balanceMonthRepaymentAmount;
	  }

	  /**
	   * 还款总额(差额)取得<BR>
	   * 
	   * @return 还款总额(差额)
	   */
	  public String getBalanceRepaymentSum() {
	    return balanceRepaymentSum;
	  }

	  /**
	   * 还款总额(差额)赋值<BR>
	   * 
	   * @param balanceRepaymentSum 还款总额(差额)
	   */
	  public void setBalanceRepaymentSum(String balanceRepaymentSum) {
	    this.balanceRepaymentSum = balanceRepaymentSum;
	  }
}
