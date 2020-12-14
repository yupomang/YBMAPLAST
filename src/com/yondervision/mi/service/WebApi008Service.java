package com.yondervision.mi.service;

import org.springframework.web.multipart.MultipartFile;

import com.yondervision.mi.dto.CMi203;
import com.yondervision.mi.dto.Mi203;
import com.yondervision.mi.result.WebApi20304_queryResult;

/** 
* @ClassName: WebApi008Service 
* @Description: 楼盘处理接口
* @author Caozhongyan
* @date Sep 29, 2013 2:55:31 PM   
* 
*/ 
public interface WebApi008Service {
	/**
	 * 楼盘新增
	 */
	public void webapi00801(CMi203 form) throws Exception;
	/**
	 * 楼盘删除
	 */
	public void webapi00802(CMi203 form) throws Exception;
	/**
	 * 楼盘修改
	 */
	public int webapi00803(CMi203 form) throws Exception;
	/**
	 * 楼盘查询
	 */
	public WebApi20304_queryResult webapi00804(CMi203 form) throws Exception;
	/**
	 * 楼盘导入
	 */
	public void webapi00805() throws Exception;
	/**
	 * 图片上传
	 */
	public String webapi00806(String magecenterId, String imageid, MultipartFile file) throws Exception;
	/**
	 * 图片URL修改
	 */
	public void webapi00807(String magecenterId, String houseId, String filePath) throws Exception;
	
	/**
	 * 图片URL
	 */
	public Mi203 webapi00808(String houseId) throws Exception;
}
