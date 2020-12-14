package com.yondervision.mi.service;

import com.yondervision.mi.dto.CMi626;
import com.yondervision.mi.dto.Mi626;
import com.yondervision.mi.result.WebApi62604_queryResult;

/**
 * @ClassName: WebApi626Service 
 * @Description:预约注意事项处理接口
 * @author Lihongjie
 * @date 2014-07-31 18:30
 * 
 */
public interface WebApi626Service {
	/*
	 * 新增
	 */
	public void webapi62601(Mi626 form) throws Exception ;
	
	/*
	 * 删除
	 */
	public void webapi62602(Mi626 form) throws Exception ;

	/*
	 * 修改
	 */
	public int webapi62603(Mi626 form) throws Exception ;

	/*
	 * 查询
	 */
	public WebApi62604_queryResult webapi62604(CMi626 form) throws Exception ;
}
