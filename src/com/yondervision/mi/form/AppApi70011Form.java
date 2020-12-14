package com.yondervision.mi.form;

public class AppApi70011Form extends AppApiCommonForm {
	/** 所属分类 */	
	private String classification;
	/** 所属分类信息列表中的序号 */				
	private String num = "";
	/** 所属分类信息列表中的关键字 */				
	private String keyword = "";
	/** 信息seqno */				
	private String seqno = "";
	
	/**				
	 *<pre> 执行获取所属分类处理 </pre>				
	 * @return pagenum 所属分类				
	 */	
	public String getClassification() {
		return classification;
	}

	/**				
	 *<pre> 执行设定所属分类处理 </pre>				
	 * @param pagenum 所属分类				
	 */	
	public void setClassification(String classification) {
		this.classification = classification;
	}

	/**
	 * @return the num
	 */
	public String getNum() {
		return num;
	}

	/**
	 * @param num the num to set
	 */
	public void setNum(String num) {
		this.num = num;
	}

	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

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

}
