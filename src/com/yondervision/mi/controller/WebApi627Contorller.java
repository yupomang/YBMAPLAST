package com.yondervision.mi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.dto.CMi627;
import com.yondervision.mi.dto.Mi627;
import com.yondervision.mi.service.WebApi626Service;
import com.yondervision.mi.service.WebApi627Service;

/**
 * @ClassName: WebApi627Contorller 
 * @Description: 节假日管理维护
 * @author Lihongjie
 * @date 2014-08-06 11：05
 */
@Controller
public class WebApi627Contorller {
	@Autowired
	private WebApi627Service webApi627ServiceImpl;

	public WebApi627Service getWebApi627ServiceImpl() {
		return webApi627ServiceImpl;
	}

	public void setWebApi627ServiceImpl(WebApi627Service webApi627ServiceImpl) {
		this.webApi627ServiceImpl = webApi627ServiceImpl;
	}
	
	/**
	 * 节假日增加一年基本信息
	 * @param form 节假日新增参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi62701.{ext}")
	public String webapi62701(CMi627 form,ModelMap modelMap) throws Exception{
		UserContext user = UserContext.getInstance();
		form.setCenterid(user.getCenterid());
		form.setLoginid(user.getLoginid());
		webApi627ServiceImpl.webapi62701(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		return "page627/page62701";
	}
	
	/**
	 * 查询可预约的工作日集合
	 * @param form 节假日查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi62702.{ext}")
	public String webapi62702(CMi627 form,ModelMap modelMap) throws Exception{
		UserContext user = UserContext.getInstance();
		form.setCenterid(user.getCenterid());
		List<Mi627> list= webApi627ServiceImpl.webapi62702(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", list);
		return "page627/page62701";
	}
	
	/**
	 * 查询某个月的节假日信息
	 * @param form 节假日查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi62703.{ext}")
	public String webapi62703(CMi627 form,ModelMap modelMap) throws Exception{
		UserContext user = UserContext.getInstance();
		form.setCenterid(user.getCenterid());
		List<Mi627> list= webApi627ServiceImpl.webapi62703(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", list);
		return "page627/page62701";
	}
	
	/**
	 * 修改某天的节假日信息
	 * @param form 节假日修改参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi62704.{ext}")
	public String webapi62704(CMi627 form,ModelMap modelMap) throws Exception{
		UserContext user = UserContext.getInstance();
		form.setCenterid(user.getCenterid());
		webApi627ServiceImpl.webbapi62704(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		return "page627/page62701";
	}
}
