package com.yondervision.mi.form;

public class AppApi70001Form extends AppApiCommonForm {
	/** 所属分类 */	
	private String classification;
	/** 页码 */				
	private String pagenum = "";
	/** 行数 */				
	private String pagerows = "";
	/** 检索关键字 */	
	private String keyword;
	
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
	 *<pre> 执行获取页码处理 </pre>				
	 * @return pagenum 页码				
	 */				
	public String getPagenum() {
	    return pagenum;				
	}				
					
	/**				
	 *<pre> 执行设定页码处理 </pre>				
	 * @param pagenum 页码				
	 */				
	public void setPagenum(String pagenum) {				
	    this.pagenum = pagenum;				
	}				
					
	/**				
	 *<pre> 执行获取行数处理 </pre>				
	 * @return pagerows 行数				
	 */				
	public String getPagerows() {				
	    return pagerows;				
	}				
					
	/**				
	 *<pre> 执行设定行数处理 </pre>				
	 * @param pagerows 行数				
	 */				
	public void setPagerows(String pagerows) {				
	    this.pagerows = pagerows;				
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
	
}
