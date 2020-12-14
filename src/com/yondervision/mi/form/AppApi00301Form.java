package com.yondervision.mi.form;

/** 
* @ClassName: AppApi00301Form 
* @Description: 结息对账单请求Form
* @author Caozhongyan
* @date Sep 27, 2013 9:15:58 AM 
* 
*/ 
public class AppApi00301Form extends AppApiCommonForm {
	
	/** 公积金年度 */				
	private String year = "";					
					
	/**				
	 *<pre> 执行获取公积金年度处理 </pre>				
	 * @return year 公积金年度				
	 */				
	public String getYear() {				
	    return year;				
	}				
					
	/**				
	 *<pre> 执行设定公积金年度处理 </pre>				
	 * @param year 公积金年度				
	 */				
	public void setYear(String year) {				
	    this.year = year;				
	}
}
