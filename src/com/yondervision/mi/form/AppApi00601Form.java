package com.yondervision.mi.form;


public class AppApi00601Form extends AppApiCommonForm {
	/**
	 * 借款合同号
	 */
	private String loancontractno="";
	
	private String dealamt = "";  // 还款金额
	private String dealdate = "";  // 划款日期
	
	private String loancontrnum = "";
	
	public String getLoancontrnum() {
		return loancontrnum;
	}

	public void setLoancontrnum(String loancontrnum) {
		this.loancontrnum = loancontrnum;
	}

	public String getDealamt() {
		return dealamt;
	}

	public void setDealamt(String dealamt) {
		this.dealamt = dealamt;
	}

	public String getDealdate() {
		return dealdate;
	}

	public void setDealdate(String dealdate) {
		this.dealdate = dealdate;
	}

	public String getLoancontractno() {
		return loancontractno;
	}

	public void setLoancontractno(String loancontractno) {
		this.loancontractno = loancontractno;
	}
}
