package com.yondervision.mi.service;
 
import com.yondervision.mi.form.WebApi05801Form;

import net.sf.json.JSONObject;
 
public interface WebApi058Service {
	
	/**
	 * 用户体验评价统计-柜面业务服务评价
	 * @param form
	 * @throws Exception
	 */
	public JSONObject webapi05803(WebApi05801Form form) throws Exception;
	

	/**
	 * 柜面业务服务评价-不满意原因分类
	 * @param form
	 * @throws Exception
	 */
	public JSONObject webapi05804(WebApi05801Form form) throws Exception;
	

	/**
	 * 柜面业务服务评价-不满意原因分类-业务政策
	 * @param form
	 * @throws Exception
	 */
	public JSONObject webapi05805(WebApi05801Form form) throws Exception;
	

	/**
	 * 柜面业务服务评价-不满意原因分类-服务态度
	 * @param form
	 * @throws Exception
	 */
	public JSONObject webapi05806(WebApi05801Form form) throws Exception;
	
	/**
	 * 柜面业务服务评价-不满意原因分类-服务态度-柜员信息
	 * @param form
	 * @throws Exception
	 */
	public JSONObject webapi05807(WebApi05801Form form) throws Exception;
}
