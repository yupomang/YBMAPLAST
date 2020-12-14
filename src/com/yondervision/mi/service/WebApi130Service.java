package com.yondervision.mi.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.yondervision.mi.dto.Mi130;
import com.yondervision.mi.dto.Mi131WithBLOBs;
import com.yondervision.mi.form.WebApi13003Form;
import com.yondervision.mi.form.Webapi13013Form;
import com.yondervision.mi.result.WebApi13003_queryResult;

/** 
* @ClassName: WebApi130Service 
* @author zhanglei
* @date 2015-01-15
* 
*/ 
public interface WebApi130Service {
	
	/**
	 * 添加分组
	 * */
	void AddGroup(String groupname) throws Exception;
	
	/**
	 * 图片上传
	 */
	void uploadImage(HttpServletRequest request, HttpServletResponse response, MultipartFile file, String centerid, String opername);
	
	/**
	 * 查询图片信息
	 * */
	WebApi13003_queryResult GetImageInfoByGrouId(WebApi13003Form form);
	
	/**
	 * 修改分组名称
	 * */
	boolean UpdateGroupName(String groupname,String id);
	
	/**
	 * 删除分组
	 * */
	boolean DeleteGroup(String id,HttpServletRequest request,HttpServletResponse response);
	
	
	/**
	 * 删除图片
	 * */
	String DeletePicById(String id);
	
	/**
	 * 获取分组信息
	 * 
	 * */
	List<Mi130> GetGroupInfo();
	
	/**
	 * 图片移动分组
	 * 
	 * */
	boolean MovePicToOtherGroup(String groupid, String picidarr,HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 修改图片名称
	 * 
	 * */
	boolean UpdatePicName(String newname,String allpicid);
	
	/**
	 * 修改图片名称
	 * 
	 * */
	List<Mi131WithBLOBs> InitCurrentPage(String groupid);
	
	/**
	 * 保存图片到指定路劲
	 * @throws Exception 
	 * @throws Exception 
	 * 
	 * */
	String SavePicToSetUrl(HttpServletRequest request,HttpServletResponse response,String realpicurl,String savepicurl,String sysname) throws Exception;

	/**
	 * 上传图文素材
	 */
	public void uploadImgTxt(Webapi13013Form webapi13013form) throws Exception;

}