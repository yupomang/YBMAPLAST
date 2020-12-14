/**
 * 在线预约-预约时段明细维护
 */
package com.yondervision.mi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.CMi625;
import com.yondervision.mi.dto.Mi627;
import com.yondervision.mi.result.WebApi62506_queryResult;
import com.yondervision.mi.service.WebApi625Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi623Contorller 
* @Description: 预约时段明细
* @author sunxl
* @date Sep 29, 2013 2:49:31 PM   
* 
*/ 
@Controller
public class WebApi625Contorller {
	@Autowired
	private WebApi625Service webApi625Service;

	@RequestMapping("/webapi62503.{ext}")
	public String webapi62503(CMi625 form , ModelMap modelMap){
		Logger log = LoggerUtil.getLogger();

		String businName = "预约状态变更";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi625Service.webapi62503(form);

		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		
		return "page303/page62504";
	}
	
	/**
	 * 预约信息查询（全部检索+模糊查询、分页）
	 * @param form 预约信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi62504.{ext}")
	public String webapi62504(CMi625 form , ModelMap modelMap, Integer page,Integer rows, HttpServletRequest request, HttpServletResponse response){
		Logger log = LoggerUtil.getLogger();

		String businName = "预约信息查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.info(LOG.SELF_LOG.getLogText("contrller page="+page+",rows="+rows));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
   		JSONObject obj= webApi625Service.webapi62504(form, page, rows,request,response);
   		System.out.println("obj.toString()============"+obj.toString());
   		for(Object key:obj.keySet()){
   			modelMap.put(key.toString(), obj.get(key));
   		} 
   		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page303/page62504";
	}
	
	/**
	 * 查询某一个月的预约汇总信息（页面日历控件显示）
	 * @param form 预约信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi62505.{ext}")
	public String webap162505(CMi625 form , ModelMap modelMap) throws Exception{
		UserContext user = UserContext.getInstance();
		form.setCenterid(user.getCenterid());
		List<Mi627> list=webApi625Service.webapi62505(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", list);
		return "page303/page62505";
	}
	
	/**
	 * 查询某一天的预约明细信息，可分页（页面列表显示）
	 * @param form 预约信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi62506.{ext}")
	public String webap162506(CMi625 form , ModelMap modelMap) throws Exception{
		UserContext user = UserContext.getInstance();
		form.setCenterid(user.getCenterid());
		WebApi62506_queryResult queryResult=new WebApi62506_queryResult();
		queryResult=webApi625Service.webapi63506(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList625());
		return "page303/page62505";
	}
	
	public void setWebApi623Service(WebApi625Service webApi625Service) {
		this.webApi625Service = webApi625Service;
	}
}
