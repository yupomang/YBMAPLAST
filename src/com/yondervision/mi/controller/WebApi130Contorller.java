/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.controller
 * 文件名：     WebApi130Contorller.java
 * 创建日期：2015-01-15
 */
package com.yondervision.mi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.Mi130;
import com.yondervision.mi.dto.Mi131WithBLOBs;
import com.yondervision.mi.form.WebApi13003Form;
import com.yondervision.mi.form.Webapi13013Form;
import com.yondervision.mi.result.WebApi13003_queryResult;
import com.yondervision.mi.service.WebApi130Service;
import com.yondervision.mi.util.CommonUtil;

/**
 * 素材管理WEB后台控制类
 * 
 * @author Zhanglei
 * 
 */

@Controller
public class WebApi130Contorller {
	@Autowired
	private WebApi130Service webApi130Service;
	/**
	 * 添加分组
	 * @throws Exception 
	 * 
	 * */
	@RequestMapping("/webapi13001.{ext}")
	public String AddGroup(String groupname, ModelMap modelMap){
		Logger log = LoggerUtil.getLogger();
		String businName = "添加分组";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		
		try {
			webApi130Service.AddGroup(groupname);
		} catch (Exception e) {
			modelMap.put("recodde", "系统错误");
			e.printStackTrace();
		}
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	/**
	 * 上传图片
	 * 
	 * */
	@RequestMapping("/webapi13002.{ext}")
	public String uploadimg(@RequestParam("file")MultipartFile file,HttpServletRequest request,HttpServletResponse response){
		Logger log = LoggerUtil.getLogger();
		
		//验证城市中心码
		UserContext user = UserContext.getInstance();
		if (CommonUtil.isEmpty(user.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		
		webApi130Service.uploadImage(request, response, file, user.getCenterid(), user.getOpername());
		return null;
	}
	
	/**
	 * 查询图片信息
	 * @throws Exception 
	 * 
	 * */
	@RequestMapping("/webapi13003.{ext}")
	public String GetImageInfoByGrouId(WebApi13003Form form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("查询某分组下素材信息");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		WebApi13003_queryResult qryResult = webApi130Service.GetImageInfoByGrouId(form);
		if(qryResult.getList131().isEmpty()||qryResult.getList131().size()==0){
			//异常处理
			log.error(ERROR.NO_DATA.getLogText("MI131", "groupid="+form.getGroupid()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"素材信息");
		}
		
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("total", qryResult.getTotal());
		modelMap.put("totalPage", qryResult.getTotalPage());
		modelMap.put("pageSize", qryResult.getPageSize());
		modelMap.put("pageNumber", qryResult.getPageNumber());
		modelMap.put("rows", qryResult.getList131());
		return null;
	}
	
	/**
	 * 修改分组名称
	 * @throws Exception 
	 * 
	 * */
	@RequestMapping("/webapi13004.{ext}")
	public String UpdateGroupName(String groupname, String id,ModelMap modelMap) throws Exception{
		modelMap.clear();
		boolean flag = webApi130Service.UpdateGroupName(groupname, id);
		if(flag){
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		}else{
			modelMap.put("recode", "999999");
			modelMap.put("msg", "更新失败");
		}
		return null;
	}
	
	/**
	 * 删除分组
	 * @throws Exception 
	 * 
	 * */
	@RequestMapping("/webapi13005.{ext}")
	public ModelMap DeleteGroup(String id,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		modelMap.clear();
		boolean flag = webApi130Service.DeleteGroup(id,request,response);
		if(flag){
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		}else{
			modelMap.put("recode", "999999");
			modelMap.put("msg", "更新失败");
		}
		return modelMap;
	}
	
	/**
	 * 删除图片
	 * @throws Exception 
	 * 
	 * */
	@RequestMapping("/webapi13006.{ext}")
	public String DeletePicById(String idarr,ModelMap modelMap) throws Exception{
		modelMap.clear();
		String flag = webApi130Service.DeletePicById(idarr);
		if("000000".equals(flag)){
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		}else{
			modelMap.put("recode", "999999");
			modelMap.put("msg", "登录超时");
		}
		return null;
	}
	
	/**
	 * 获取分组信息
	 * @throws Exception 
	 * 
	 * */
	@RequestMapping("/webapi13007.{ext}")
	public String GetGroupIno(ModelMap modelMap) throws Exception{
		modelMap.clear();
		List<Mi130> list = webApi130Service.GetGroupInfo();
		//String str = JsonUtil.getGson().toJson(list);
		if(list == null){
			modelMap.put("recode", "999999");
			modelMap.put("msg", "登录超时请重新登录");
		}else{
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("result", list);
		}
		return null;
	}
	
	/**
	 * 图片移动分组
	 * @throws Exception 
	 * 
	 * */
	@RequestMapping("/webapi13008.{ext}")
	public ModelMap MovePicToOtherGroup(String groupid, String picidarr, ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		modelMap.clear();
		boolean flag = webApi130Service.MovePicToOtherGroup(groupid, picidarr, request, response);
		if(flag){
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		}else{
			modelMap.put("recode", "999999");
			modelMap.put("msg", "登录超时请重新登录");
		}
		return modelMap;
	}
	
	/**
	 * 修改图片名称
	 * @throws Exception 
	 * 
	 * */
	@RequestMapping("/webapi13009.{ext}")
	public String UpdatePicName(String newname,String allpicid,ModelMap modelMap) throws Exception{
		modelMap.clear();
		boolean flag = webApi130Service.UpdatePicName(newname, allpicid);
		if(flag){
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		}else{
			modelMap.put("recode", "999999");
			modelMap.put("msg", "登录超时请重新登录");
		}
		return null;
	}
	
	/**
	 * 初始化分页
	 * @throws Exception 
	 * 
	 * */
	@RequestMapping("/webapi13010.{ext}")
	public String InitCurrentPage(String groupid,ModelMap modelMap) throws Exception{
		modelMap.clear();
		List<Mi131WithBLOBs> list = webApi130Service.InitCurrentPage(groupid);
		if(list == null){
			modelMap.put("recode", "999999");
			modelMap.put("msg", "登录超时请重新登录");
		}else if(list.size() == 0){
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("page", 0);
		}else{
			int count = list.size();
			if(count%12 == 0){
				modelMap.put("page", count/12);
			}else{
				modelMap.put("page", count/12 + 1);
			}
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		}
		return null;
	}
	
	/**
	 * 初始化分页
	 * @throws Exception 
	 * 
	 * */
	@RequestMapping("/webapi13011.{ext}")
	public String InitCurrentPageTen(String groupid,ModelMap modelMap) throws Exception{
		modelMap.clear();
		List<Mi131WithBLOBs> list = webApi130Service.InitCurrentPage(groupid);
		if(list == null){
			modelMap.put("recode", "999999");
			modelMap.put("msg", "登录超时请重新登陆");
		}else if(list.size() == 0){
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("page", 0);
		}else{
			int count = list.size();
			if(count%10 == 0){
				modelMap.put("page", count/10);
			}else{
				modelMap.put("page", count/10 + 1);
			}
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		}
		return null;
	}
	
	/**
	 * 保存图片到指定路劲
	 * @throws Exception 
	 * 
	 * */
	@RequestMapping("/webapi13012.{ext}")
	public ModelMap SavePicToSetUrl(HttpServletRequest request,HttpServletResponse response,
			String realpicurl,String savepicurl,String sysname,ModelMap modelMap) throws Exception{
		modelMap.clear();
		String flag = webApi130Service.SavePicToSetUrl(request,response,realpicurl, savepicurl, sysname);
		if(flag == null){
			modelMap.put("recode", "999999");
			modelMap.put("msg", "登录超时请重新登录");
		}else if("2".equals(flag)){
			modelMap.put("recode", "999999");
			modelMap.put("msg", "文件不存在请重新选择文件");
		}else{
			String img[] = flag.split("\\*");
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("imgUrl", img[0]);
			modelMap.put("serverimg", img[1]);
		}
		return modelMap;
	}
	
	/**
	 * 图文素材保存
	 * @throws Exception 
	 * 
	 * */
	@RequestMapping("/webapi13013.{ext}")
	public String webapi13013(Webapi13013Form form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();
		
		//验证城市中心码
		UserContext user = UserContext.getInstance();
		if (CommonUtil.isEmpty(user.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		
		if (CommonUtil.isEmpty(form.getRealname())) {
			log.error(ERROR.PARAMS_NULL.getLogText("素材名称"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"素材名称");
		}
		if (CommonUtil.isEmpty(form.getPicurl())) {
			log.error(ERROR.PARAMS_NULL.getLogText("图片素材"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"图片素材");
		}
		if (CommonUtil.isEmpty(form.getHtmlContent())
				&& CommonUtil.isEmpty(form.getTxtContent())) {
			log.error(ERROR.PARAMS_NULL.getLogText("文字素材"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"文字素材");
		}
		webApi130Service.uploadImgTxt(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		return "";
	}
}
