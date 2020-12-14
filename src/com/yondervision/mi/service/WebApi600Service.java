package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.dto.CMi601;
import com.yondervision.mi.dto.Mi601;
import com.yondervision.mi.dto.Mi602;
import com.yondervision.mi.form.WebApi60001Form;
import com.yondervision.mi.form.WebApi60004Form;
import com.yondervision.mi.result.WebApi60001_queryResult;

/** 
* @ClassName: WebApi600Service 
* @Description:留言信息处理接口
* @author gongqi
* @date July 16, 2014 9:33:25 PM  
* 
*/ 
public interface WebApi600Service {
	/**
	 * 留言信息查询
	 */
	public WebApi60001_queryResult webapi60001(WebApi60001Form form) throws Exception;
	
	/**
	 * 留言信息更新
	 */
	public int webapi60002(CMi601 form) throws Exception;
	
	public List<Mi601> webapi60003(WebApi60001Form form) throws Exception;
	
	/**
	 * 留言回复信息查询Mi602
	 */
	public List<Mi602> webapi60004(WebApi60004Form form) throws Exception;
}
