package com.yondervision.mi.form;

public class AppApi00202Form extends AppApiCommonForm {
	/** 摘要码 */				
	private String summarycode = "";				
			
	/** 交易日期 */				
	private String transdate = "";
	
	private String transseqno = "";

	public String getSummarycode() {
		return summarycode;
	}

	public void setSummarycode(String summarycode) {
		this.summarycode = summarycode;
	}

	public String getTransdate() {
		return transdate;
	}

	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}

	public String getTransseqno() {
		return transseqno;
	}

	public void setTransseqno(String transseqno) {
		this.transseqno = transseqno;
	}
	
	
}
