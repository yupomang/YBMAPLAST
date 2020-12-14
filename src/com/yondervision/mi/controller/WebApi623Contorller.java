/**
 * 在线预约-预约时段明细维护
 */
package com.yondervision.mi.controller;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.CMi623;
import com.yondervision.mi.dto.CMi623;
import com.yondervision.mi.dto.CMi624;
import com.yondervision.mi.dto.Mi005;
import com.yondervision.mi.dto.Mi201;
import com.yondervision.mi.dto.Mi202;
import com.yondervision.mi.dto.Mi622;
import com.yondervision.mi.service.WebApi623Service;
import com.yondervision.mi.service.WebApi623Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi623Contorller 
* @Description: 预约时段明细
* @author sunxl
* @date Sep 29, 2013 2:49:31 PM   
* 
*/ 
@Controller
public class WebApi623Contorller {
	@Autowired
	private WebApi623Service webApi623Service;

	public void setWebApi623Service(WebApi623Service webApi623Service) {
		this.webApi623Service = webApi623Service;
	}

	/**
	 * 利率信息增加
	 * @param form 利率信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi62301.{ext}")
	public String webapi62301(CMi623 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "时段明细增加";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi623Service.webapi62301(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page303/page62301";
	}
	
	/**
	 * 利率信息删除
	 * @param form 利率信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi62302.{ext}")
	public String webapi62302(CMi623 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "时段明细删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi623Service.webapi62302(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page303/page62301";
	}
	
	/**
	 * 利率信息修改
	 * @param form 利率信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi62303.{ext}")
	public String webapi62303(CMi623 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "时段明细修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi623Service.webapi62303(form);

		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page303/page62301";
	}
	
	/**
	 * 利率信息查询（全部检索+模糊查询、分页）
	 * @param form 利率信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi62304.{ext}")
	public String webapi62304(CMi623 form , ModelMap modelMap, Integer page,Integer rows){
		Logger log = LoggerUtil.getLogger();

		String businName = "网点时段信息查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.info(LOG.SELF_LOG.getLogText("contrller page="+page+",rows="+rows));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
   		JSONObject obj= webApi623Service.webapi62304(form, page, rows);
   		for(Object key:obj.keySet()){
   			modelMap.put(key.toString(), obj.get(key));
   		} 
   		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page303/page62301";
	}
	@RequestMapping("/webapi62401.{ext}")
	public String webapi62401(CMi624 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "时段明细增加";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi623Service.webapi62401(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page303/page62301";
	}
	
	/**
	 * 利率信息删除
	 * @param form 利率信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi62402.{ext}")
	public String webapi62402(CMi624 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "时段明细删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi623Service.webapi62402(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page303/page62301";
	}
	
	/**
	 * 利率信息修改
	 * @param form 利率信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi62403.{ext}")
	public String webapi62403(CMi624 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "时段明细修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi623Service.webapi62403(form);

		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page303/page62301";
	}
	
	/**
	 * 利率信息查询（全部检索+模糊查询、分页）
	 * @param form 利率信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi62404.{ext}")
	public String webapi62404(CMi624 form , ModelMap modelMap, Integer page,Integer rows){
		Logger log = LoggerUtil.getLogger();

		String businName = "网点时段信息查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.info(LOG.SELF_LOG.getLogText("contrller page="+page+",rows="+rows));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
   		JSONObject obj= webApi623Service.webapi62404(form, page, rows);
   		for(Object key:obj.keySet()){
   			modelMap.put(key.toString(), obj.get(key));
   		} 
   		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page303/page62301";
	}
	@RequestMapping("/page62301Qry.html")
	public String page62301Qry(String pid,ModelMap modelMap){//网点管理   
	    JSONArray ary=new JSONArray(); 
		if(pid.trim().equals("00000000")){
			List<Mi202> list= webApi623Service.getArea();	
		    for(int i=0;i<list.size();i++){
				JSONObject obj=new JSONObject();
				obj.put("id", list.get(i).getAreaId());
				obj.put("text", list.get(i).getAreaName());			 
				obj.put("state", "closed");
				ary.add(obj); 
		    } 
		}else{
		    List<Mi201> list= webApi623Service.getWebsiteByArea(pid);	
		    for(int i=0;i<list.size();i++){
				JSONObject obj=new JSONObject();
				obj.put("id", list.get(i).getWebsiteCode());
				obj.put("text", list.get(i).getWebsiteName());			 
				obj.put("state", "open");
				ary.add(obj); 
		    } 
		}
	    modelMap.put("ary", ary);
		return "page303/page62301Qry";
	} 
	@RequestMapping("/webapi623SaveSort.json")
	public String webapi623SaveSort(String datalist,ModelMap modelMap){
		Logger log = LoggerUtil.getLogger();

		String businName = "时段明细修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		
		
	    JSONArray arr= JSONArray.fromObject(datalist);
	    webApi623Service.webapiUpdateSort(arr);

		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page303/page62301";
	} 
}
