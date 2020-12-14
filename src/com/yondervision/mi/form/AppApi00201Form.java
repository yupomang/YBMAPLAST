package com.yondervision.mi.form;

public class AppApi00201Form extends AppApiCommonForm {
	
	private String startDate = "";
	private String endDate = "";
	/** 页码 */				
	private String pagenum = "";				
	/** 行数 */				
	private String pagerows = "";				
	/** 开始日期 */				
	private String startdate = "";				
	/** 结束日期 */				
	private String enddate = "";				
	/** 是否分页标识 */	
	private String ispaging = "";	
	
//=====================	株洲新加
	/** 单位账号 */				
	private String unitaccnum = "";
	/** 业务流水号 */				
	private String ywlsh = "";
	/** 记账日期 */				
	private String jzrq = "";
	
//======================株洲新加	
	public String getIspaging() {
		return ispaging;
	}

	public void setIspaging(String ispaging) {
		this.ispaging = ispaging;
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
	 *<pre> 执行获取开始日期处理 </pre>				
	 * @return startdate 开始日期				
	 */				
	public String getStartdate() {				
	    return startdate;				
	}				
					
	/**				
	 *<pre> 执行设定开始日期处理 </pre>				
	 * @param startdate 开始日期				
	 */				
	public void setStartdate(String startdate) {				
	    this.startdate = startdate;				
	}				
					
	/**				
	 *<pre> 执行获取结束日期处理 </pre>				
	 * @return enddate 结束日期				
	 */				
	public String getEnddate() {				
	    return enddate;				
	}				
					
	/**				
	 *<pre> 执行设定结束日期处理 </pre>				
	 * @param enddate 结束日期				
	 */				
	public void setEnddate(String enddate) {				
	    this.enddate = enddate;				
	}

	public String getUnitaccnum() {
		return unitaccnum;
	}

	public void setUnitaccnum(String unitaccnum) {
		this.unitaccnum = unitaccnum;
	}

	public String getYwlsh() {
		return ywlsh;
	}

	public void setYwlsh(String ywlsh) {
		this.ywlsh = ywlsh;
	}

	public String getJzrq() {
		return jzrq;
	}

	public void setJzrq(String jzrq) {
		this.jzrq = jzrq;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
