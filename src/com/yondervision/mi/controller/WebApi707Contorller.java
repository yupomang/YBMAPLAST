package com.yondervision.mi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.CMi707;
import com.yondervision.mi.dto.Mi707;
import com.yondervision.mi.form.WebApi70701Form;
import com.yondervision.mi.form.WebApi70702Form;
import com.yondervision.mi.form.WebApi70704Form;
import com.yondervision.mi.result.Page70701TreeResult;
import com.yondervision.mi.result.WebApi70704_queryResult;
import com.yondervision.mi.service.WebApi707Service;
import com.yondervision.mi.util.CommonUtil;


/** 
* @ClassName: WebApi707Contorller 
* @Description:栏目管理
* @date Sep 29, 2013 2:49:31 PM   
* 
*/ 
@Controller
public class WebApi707Contorller {

	@Autowired
	private WebApi707Service webApi707Service = null;
	public void setWebApi707Service(WebApi707Service webApi707Service) {
		this.webApi707Service = webApi707Service;
	}

	/**
	 * 栏目添加
	 * @param form
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/page70701Add.json")
	public String page70701Add(CMi707 form , ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();

		String businName = "栏目添加";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		Page70701TreeResult treeResult = new Page70701TreeResult();
		treeResult = webApi707Service.addMulDicReturnObj(form);
		
		modelMap.put("addTreeData", treeResult);
		modelMap.put("rows", treeResult);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page707/page70701";
	}
	
	/**
	 * 栏目删除
	 * @param form
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/page70701Del.json")
	public String page70701Del(CMi707 form , ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();

		String businName = "栏目删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi707Service.delMulDic(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page707/page70701";
	}
	
	/**
	 * 栏目修改
	 * @param form
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/page70701Mod.json")
	public String page70701Mod(CMi707 form , ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();

		String businName = "栏目修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		// 上传参数空值校验
		if (CommonUtil.isEmpty(form.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码："+form.getCenterid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		
		if(CommonUtil.isEmpty(form.getDicid()) && form.getCenterid().equals(form.getItemid())){
			throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"此条记录为客户信息，不能进行修改！");
		}
		webApi707Service.updMulDic(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page707/page70701";
	}
	
	/**
	 * 栏目管理查询
	 * @param form
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/page70701Query.json")
	public String ptl40006Query(String pid, String centerid, ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();

		String businName = "栏目管理查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(DEBUG.SHOW_PARAM.getLogText("pid="+pid+";centerid="+centerid)));
		
		List<Mi707> list = new ArrayList<Mi707>();
		list = webApi707Service.getCodeListByPid(pid, centerid);
		
		modelMap.put("rows", list);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));

		return "page707/page70701";
	}
	
	// page70702使用
	@RequestMapping("/webapi707NewsConfigsList.json")
	public String webapi707NewsConfigsList(ModelMap modelMap)throws Exception {
		UserContext user = UserContext.getInstance();
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		List<Mi707> mi707list = new ArrayList<Mi707>();
		mi707list = webApi707Service.getCodeListByPid(Constants.IS_NOT_VALIDFLAG, user.getCenterid());
		HashMap<String,String> map = new HashMap<String, String>();
		for(int i = 0; i < mi707list.size(); i++){
			map = new HashMap<String, String>();
			map.put("itemid", mi707list.get(i).getItemid());
			map.put("itemval", mi707list.get(i).getItemval());
			list.add(map);
		}
		ObjectMapper mapper = new  ObjectMapper();
		
		modelMap.put("newsconfigslist", mapper.convertValue(list, JSONArray.class));
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		return 	"";
	}
	
	/**
	 * 对应的内容展现项的配置内容填加
	 * @param form 版块栏目配置填加信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi7070201.{ext}")
	public String webapi7070201(WebApi70701Form form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("内容展现项的配置内容填加");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		webApi707Service.webapi70701(form);			
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page707/page70702";
	}
	
	/**
	 * 对应的内容展现项的配置内容删除
	 * @param form 版块栏目配置信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi7070202.{ext}")
	public String webapi7070202(WebApi70702Form form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("内容展现项的配置内容删除");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		webApi707Service.webapi70702(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page707/page70702";
	}
	
	/**
	 * 对应的内容展现项的配置内容查询
	 * @param form 内容展现项查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi7070204.{ext}")
	public String webapi7070204(WebApi70704Form form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("对应的内容展现项的配置内容查询");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			
		List<WebApi70704_queryResult> resultList = webApi707Service.webapi70704(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", resultList);
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page707/page70702";
	}
	
	/**
	 * 根据中心查询所有的栏目，包含一级，二级
	 * @param centerid
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/webapi70705.{ext}")
	public String webapi70705(String centerid , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String businName = "根据中心查询所有的栏目";
		log.info(LOG.START_BUSIN.getLogText(businName));
		
		List<Mi707> list = webApi707Service.webapi70705(centerid);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", list);
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
}
