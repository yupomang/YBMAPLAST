package com.yondervision.mi.form;

/** 
* @ClassName: WebApi70103Form 
* @Description: 报刊期次维护-修改请求Form
*/ 
public class WebApi70103Form extends WebApiCommonForm {
	
	private String seqno;
	
	//private String itemid;
	
	private String itemval;

	public String getSeqno() {
		return seqno;
	}

	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}
	
	/*public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}*/

	public String getItemval() {
		return itemval;
	}

	public void setItemval(String itemval) {
		this.itemval = itemval;
	}
}
