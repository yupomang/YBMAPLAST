package com.yondervision.mi.form;

/** 
* @ClassName: AppApi00501Form 
* @Description: 提取明细查询请求Form
* @author Caozhongyan
* @date Sep 27, 2013 9:15:58 AM 
* 
*/ 
public class AppApi00501Form extends AppApiCommonForm {
	/** 页码 */					
	private String pagenum = "";					
	/** 行数  */					
	private String pagerows = "";					
	/** 开始日期 */					
	private String startdate = "";					
	/** 结束日期 */					
	private String enddate = "";
	/** 借款合同号 */					
	private String loancontractno = "";
						
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
	 *<pre> 执行获取行数 处理 </pre>					
	 * @return pagerows 行数 					
	 */					
	public String getPagerows() {					
	    return pagerows;					
	}					
						
	/**					
	 *<pre> 执行设定行数 处理 </pre>					
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

	public String getLoancontractno() {
		return loancontractno;
	}

	public void setLoancontractno(String loancontractno) {
		this.loancontractno = loancontractno;
	}
}
