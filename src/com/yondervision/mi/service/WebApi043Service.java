package com.yondervision.mi.service;

import com.yondervision.mi.dto.CMi036;
import com.yondervision.mi.dto.CMi043;
import com.yondervision.mi.dto.CMi044;
import com.yondervision.mi.result.WebApi03604_queryResult;
import com.yondervision.mi.result.WebApi04304_queryResult;
import com.yondervision.mi.result.WebApi04404_queryResult;

public interface WebApi043Service {
	/**
	 * 新增
	 * @param form
	 * @throws Exception
	 */
	public void webapi04301(CMi043 form) throws Exception;
	/**
	 * 删除
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public int webapi04302(CMi043 form) throws Exception;
	
	/**
	 * 修改
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public int webapi04303(CMi043 form) throws Exception;
	
	/**
	 * 分页查询
	 * @param form
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 * @throws Exception
	 */
	public WebApi04304_queryResult webapi04304(CMi043 form) throws Exception;

	
	public void webapi04401(CMi044 form)throws Exception;
	/**
	 * 
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public WebApi04404_queryResult webapi04404(CMi044 form) throws Exception;
	
	/**
	 * 单日限额新增
	 * @param form
	 * @throws Exception
	 */
	public void webapi03601(CMi036 form)throws Exception;
	
	/**
	 * 单日限额分页查询
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public WebApi03604_queryResult webapi03604(CMi036 form) throws Exception;

}
