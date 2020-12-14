package com.yondervision.mi.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.yondervision.mi.dto.CMi701;
import com.yondervision.mi.dto.Mi701WithBLOBs;
import com.yondervision.mi.form.SynchroImportFileInfoBean;
import com.yondervision.mi.form.WebApi70004Form;
import com.yondervision.mi.result.WebApi70004_queryResult;

/** 
* @ClassName: WebApi700Service 
* @Description:内容管理处理接口
* @author gongqi
* @date July 18, 2014 9:33:25 PM  
* 
*/ 
public interface WebApi700Service {
	/**
	 * 内容新增
	 */
	public void webapi70001(CMi701 form, String reqUrl) throws Exception;
	
	/**
	 * 内容删除
	 */
	public int webapi70002(CMi701 form) throws Exception;
	
	/**
	 * 内容修改
	 */
	public int webapi70003(CMi701 form, String reqUrl) throws Exception;
	
	/**
	 * 内容查询-分页
	 */
	public WebApi70004_queryResult webapi70004(WebApi70004Form form) throws Exception;
	
	/**
	 * 内容查询
	 */
	public List<Mi701WithBLOBs> webapi70005(CMi701 form) throws Exception;
	
	/**
	 * 图片上传
	 */
	public void uploadImage(HttpServletRequest request, HttpServletResponse response,
			MultipartFile image, long maxSize, String filePath, String centerid) throws Exception;
	
	public String uploadImageReturnStr(HttpServletRequest request, HttpServletResponse response,
			MultipartFile image, long maxSize, String fileExtPath, String centerid) throws Exception;
	/**
	 * 内容发布
	 */
	public int webapi70007(CMi701 form, HttpServletRequest request) throws Exception;
	
	/**
	 * 内容审批申请
	 */
	public int webapi70008(CMi701 form, HttpServletRequest request) throws Exception;
	
	/**
	 * 接收审批结果
	 */
	public int recAuthResultDeal(String seqno, String resultFlg, String msg) throws Exception;
	
	// 上传文件与内容id关联关系管理MI708 增加数据
	public void webapi70008(String centerid, Integer docid, List<SynchroImportFileInfoBean> fileinfolist, String operUser) throws Exception;
	
}
