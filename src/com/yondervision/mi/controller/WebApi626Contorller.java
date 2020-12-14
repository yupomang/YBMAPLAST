package com.yondervision.mi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.dto.CMi626;
import com.yondervision.mi.dto.Mi626;
import com.yondervision.mi.result.WebApi62604_queryResult;
import com.yondervision.mi.service.WebApi626Service;

/**
 * @ClassName: WebApi626Contorller 
 * @Description: 预约注意事项维护
 * @author Lihongjie
 * @date 2014-08-01
 */
@Controller
public class WebApi626Contorller {

	@Autowired
	private WebApi626Service webApi626ServiceImpl;

	public WebApi626Service getWebApi626ServeiceImpl() {
		return webApi626ServiceImpl;
	}

	public void setWebApi626Serveice(WebApi626Service webApi626ServiceImpl) {
		this.webApi626ServiceImpl = webApi626ServiceImpl;
	}
	
	/**
	 * 预约注意事项增加
	 * @param form 预约注意事项参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi62601.{ext}")
	public String webapi62601(Mi626 form,ModelMap modelMap) throws Exception{
		UserContext user = UserContext.getInstance();
		form.setCenterid(user.getCenterid());
		form.setLoginid(user.getLoginid());
		webApi626ServiceImpl.webapi62601(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		return "page626/page62601";
	}
	
	/**
	 * 预约注意事项删除
	 * @param form 预约注意事项参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi62602.{ext}")
	public String webapi62602(Mi626 form,ModelMap modelMap) throws Exception{
		webApi626ServiceImpl.webapi62602(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		return "page626/page62601";
	}
	
	/**
	 * 预约注意事项修改
	 * @param form 预约注意事项修改参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi62603.{ext}")
	public String webapi62603(Mi626 form,ModelMap modelMap) throws Exception{
		UserContext user = UserContext.getInstance();
		form.setLoginid(user.getLoginid());
		
//		form.getAppoattenid()
//		
//		form.setAppoattenid(appoattenid);
		webApi626ServiceImpl.webapi62603(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		return "page626/page62601";
	}
	
	/**
	 * 预约注意事项查询
	 * @param form 预约注意事项参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi62604.{ext}")
	public String webapi62604(CMi626 form,ModelMap modelMap) throws Exception{
		UserContext user = UserContext.getInstance();
		form.setCenterid(user.getCenterid());
		WebApi62604_queryResult queryResult = webApi626ServiceImpl.webapi62604(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList626());
		return "page626/page62601";
	}
}
