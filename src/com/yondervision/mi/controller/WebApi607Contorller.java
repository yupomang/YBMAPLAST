package com.yondervision.mi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.dto.CMi607;
import com.yondervision.mi.dto.Mi607;
import com.yondervision.mi.service.WebApi607Service;

import net.sf.json.JSONObject;

/**
 * @ClassName: WebApi607Contorller 
 * @Description: 黑名单维护
 * @author Lihongjie
 * @date 2014-08-05 09：27
 */
@Controller
public class WebApi607Contorller {

	@Autowired
	private WebApi607Service webApi607ServiceImpl;

	public WebApi607Service getWebApi607ServiceImpl() {
		return webApi607ServiceImpl;
	}

	public void setWebApi607ServiceImpl(WebApi607Service webApi607ServiceImpl) {
		this.webApi607ServiceImpl = webApi607ServiceImpl;
	}
	
	/**
	 * 黑名单信息有效性修改
	 * @param form 黑名单参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi60701.{ext}")
	public String webapi60701(Mi607 form,ModelMap modelMap) throws Exception{
		webApi607ServiceImpl.webapi60701(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		return "page607/page60701";
	}
	
	/**
	 * 黑名单查询
	 * @param form 黑名单参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi60702.{ext}")
	public String webapi60702(CMi607 form,ModelMap modelMap) throws Exception{
		UserContext user = UserContext.getInstance();
		form.setCenterid(user.getCenterid());
		JSONObject obj = webApi607ServiceImpl.webapi60702(form);
		modelMap.clear();
		for(Object key:obj.keySet()){
   			modelMap.put(key.toString(), obj.get(key));
   		} 
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		modelMap.put("total", k);
//		modelMap.put("pageSize", form.getPage());
//		modelMap.put("pageNumber", form.getRows());
//		modelMap.put("rows", queryResult.getResultList());
		return "page607/page60701";
	}
}
