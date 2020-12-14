package com.yondervision.mi.service;

import org.springframework.web.multipart.MultipartFile;

import com.yondervision.mi.dto.CMi201;
import com.yondervision.mi.dto.Mi201;
import com.yondervision.mi.result.WebApi20104_queryResult;

/** 
* @ClassName: WebApi101Service 
* @Description:目前其点处理接口
* @author Caozhongyan
* @date Sep 29, 2013 2:55:31 PM   
* 
*/ 
public interface WebApi101Service {
	/**
	 * 楼盘新增
	 */
	public void webapi10101(CMi201 form) throws Exception;
	/**
	 * 楼盘删除
	 */
	public void webapi10102(CMi201 form) throws Exception;
	/**
	 * 楼盘修改
	 */
	public int webapi10103(CMi201 form) throws Exception;
	/**
	 * 楼盘查询
	 */
	public WebApi20104_queryResult webapi10104(CMi201 form) throws Exception;
	/**
	 * 楼盘导入
	 */
	public void webapi10105() throws Exception;
	/**
	 * 图片上传
	 */
	public String webapi10106(String magecenterId, String imageid, MultipartFile file) throws Exception;
	/**
	 * 图片URL
	 */
	public Mi201 webapi10107(String websiteId) throws Exception;
	/**
	 * 图片更新
	 */
	public int webapi10103_image(CMi201 form) throws Exception;
}
