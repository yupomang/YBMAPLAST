package com.yondervision.mi.form;

public class AppApi60002Form extends AppApiCommonForm {
	/** 页码 */				
	private String pagenum = "";
	/** 行数 */				
	private String pagerows = "";
	/** 上一次获取的最小序号*/
	private String lastSeqno = "";
					
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
	 * @return the lastSeqno
	 */
	public String getLastSeqno() {
		return lastSeqno;
	}

	/**
	 * @param lastSeqno the lastSeqno to set
	 */
	public void setLastSeqno(String lastSeqno) {
		this.lastSeqno = lastSeqno;
	}
	
}
