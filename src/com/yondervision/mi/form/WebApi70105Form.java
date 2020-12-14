package com.yondervision.mi.form;

/** 
* @ClassName: WebApi70105Form 
* @Description: 报刊期次维护-发布请求Form
*/ 
public class WebApi70105Form extends WebApiCommonForm {
	
	private String seqno;
	
	private String itemid;
	private String itemval;

	/**
	 * @return the seqno
	 */
	public String getSeqno() {
		return seqno;
	}

	/**
	 * @param seqno the seqno to set
	 */
	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}
	
	/**
	 * @return the itemid
	 */
	public String getItemid() {
		return itemid;
	}
	/**
	 * @param itemid the itemid to set
	 */
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	/**
	 * @return the itemval
	 */
	public String getItemval() {
		return itemval;
	}
	/**
	 * @param itemval the itemval to set
	 */
	public void setItemval(String itemval) {
		this.itemval = itemval;
	}
}
