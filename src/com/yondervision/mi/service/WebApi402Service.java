package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.dto.CMi102;
import com.yondervision.mi.dto.Mi102;

/** 
* @ClassName: WebApi403Service 
* @Description: 中心客服通讯
* @author Caozhongyan
* @date Oct 9, 2013 9:09:20 AM   
* 
*/ 
public interface WebApi402Service {
	/**
	 * 中心客服通讯填加
	 */
	public void webapi40205(CMi102 form) throws Exception;
	/**
	 * 中心客服通讯删除
	 */
	public void webapi40206(List<String> list) throws Exception;
	/**
	 * 中心客服通讯修改
	 */
	public int webapi40207(CMi102 form) throws Exception;
	/**
	 * 中心客服通讯查询
	 */
	public List<Mi102> webapi40208(CMi102 form) throws Exception;
	
}	
