package com.yondervision.mi.service;

import org.springframework.web.multipart.MultipartFile;

import com.yondervision.mi.dto.CMi106;
import com.yondervision.mi.result.WebApi40304_queryResult;

/** 
* @ClassName: WebApi403Service 
* @Description: 软件更新
* @author Caozhongyan
* @date Oct 9, 2013 9:09:20 AM   
* 
*/ 
public interface WebApi403Service {
	/**
	 * 软件更新填加
	 */
	public void webapi40301(CMi106 form) throws Exception;
	/**
	 * 软件更新删除
	 */
	public void webapi40302(CMi106 form) throws Exception;
	/**
	 * 软件更新修改
	 */
	public int webapi40303(CMi106 form) throws Exception;
	/**
	 * 软件更新查询
	 */
	public WebApi40304_queryResult webapi40304(CMi106 form) throws Exception;
	/**
	 * 软件更新发布
	 */
	public int webapi40305(CMi106 form) throws Exception;
	
	/**
	 * 图片上传生成二维码
	 */
	public String webapi40306(String magecenterId, String versionid_pic, MultipartFile file) throws Exception;
	
	/**
	 * 软件更新风格维码存储
	 */
	public int webapi40307(CMi106 form) throws Exception;
}	
