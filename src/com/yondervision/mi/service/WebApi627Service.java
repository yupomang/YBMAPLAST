package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.dto.CMi627;
import com.yondervision.mi.dto.Mi627;

/**
 * @ClassName: WebApi627Service 
 * @Description:节假日管理处理接口
 * @author Lihongjie
 * @date 2014-08-06 10:34
 * 
 */
public interface WebApi627Service {
	/*
	 * 新增一年
	 */
	public void webapi62701(CMi627 form) throws Exception ;
	/*
	 * 查询可预约工作日集合
	 */
	public List<Mi627> webapi62702(CMi627 form) throws Exception ;
	/*
	 * 查询某一个月
	 */
	public List<Mi627> webapi62703(CMi627 form) throws Exception ;
	/*
	 * 修改
	 */
	public int webbapi62704(Mi627 form) throws Exception ;
	/*
	 * 判断某个日期是否是节假日
	 * datePara格式  XXXX-XX-XX
	 */
	public boolean webbapi62705(String centerId, String datePara) throws Exception;
}
