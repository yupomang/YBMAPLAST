package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.dto.Mi701;
import com.yondervision.mi.dto.Mi701WithBLOBs;
import com.yondervision.mi.form.WebApi70201Form;
import com.yondervision.mi.form.WebApi70202Form;
import com.yondervision.mi.form.WebApi70203Form;
import com.yondervision.mi.form.WebApi70204Form;
import com.yondervision.mi.form.WebApi70205Form;
import com.yondervision.mi.result.WebApi70204_queryResult;

/** 
* @ClassName: WebApi702Service 
* @Description:新闻发布处理接口
* @author gongqi
* @date July 18, 2014 9:33:25 PM  
* 
*/ 
public interface WebApi702Service {
	/**
	 * 新闻新增
	 */
	public void webapi70201(WebApi70201Form form, String reqUrl) throws Exception;
	
	/**
	 * 新闻删除
	 */
	public int webapi70202(WebApi70202Form form) throws Exception;
	
	/**
	 * 新闻修改
	 */
	public int webapi70203(WebApi70203Form form, String reqUrl) throws Exception;
	
	/**
	 * 新闻查询-分页
	 */
	public WebApi70204_queryResult webapi70204(WebApi70204Form form) throws Exception;
	
	/**
	 * 新闻查询-根据seqno
	 */
	public List<Mi701WithBLOBs> webapi70205(WebApi70205Form form) throws Exception;
	
//	/**
//	 * 图片上传
//	 */
//	public void uploadImage(HttpServletRequest request, HttpServletResponse response, MultipartFile image) throws Exception;
	
}
