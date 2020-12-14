package com.yondervision.mi.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.Flow;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.CMi045;
import com.yondervision.mi.dto.CMi046;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi046;
import com.yondervision.mi.result.WebApi04504_queryResult;
import com.yondervision.mi.result.WebApi04604_queryResult;
import com.yondervision.mi.service.CodeListApi001Service;
import com.yondervision.mi.service.WebApi045Service;
import com.yondervision.mi.util.CommonUtil;
/**
 * 渠道流量控制和历史查询
 * @author lixu
 *
 */
@Controller
public class WebApi045Contorller {
	@Autowired
	private WebApi045Service webApi045ServiceImpl;
	@Autowired
	private CodeListApi001Service codeListApi001Service; 
	
	
	@RequestMapping("/webapi04501.{ext}")
	public String webapi04501(CMi045 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道流量控制新增";
		UserContext user = UserContext.getInstance();
		List<Mi007> list1 = this.codeListApi001Service.getCodeList(user
				.getCenterid(), "channel");
		if(CommonUtil.isEmpty(list1)){
			log.error(ERROR.PARAMS_NULL.getLogText("该中心下没有渠道："+form.getCenterid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "该中心下没有渠道："+form.getCenterid());
		}
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi045ServiceImpl.webapi04501(form,list1);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page045/page04501";
	}

	@RequestMapping("/webapi04502.{ext}")
	public String webapi04502(CMi045 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道流量控制删除";
		
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi045ServiceImpl.webapi04502(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page045/page04502";
	}
	
	@RequestMapping("/webapi04503.{ext}")
	public String webapi04503(CMi045 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道流量控制修改";
		
		UserContext user = UserContext.getInstance();
		List<Mi007> list1 = this.codeListApi001Service.getCodeList(user
				.getCenterid(), "channel");
		if(CommonUtil.isEmpty(list1)){
			log.error(ERROR.PARAMS_NULL.getLogText("该中心下没有渠道："+form.getCenterid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "该中心下没有渠道："+form.getCenterid());
		}
		
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi045ServiceImpl.webapi04503(form, list1);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page045/page04503";
	}
	
	
	
	@RequestMapping("/webapi04504.{ext}")
	public String webapi04504(CMi045 form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道流量控制查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		WebApi04504_queryResult queryResult = webApi045ServiceImpl.webapi04504(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList045());
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page045/page04504";
	}

	@RequestMapping("/webapi04604.{ext}")
	public String webapi04604(CMi046 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道流量控制分页查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		WebApi04604_queryResult queryResult = webApi045ServiceImpl.webapi04604(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList046());
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page043/page04304";
	}
	
	@RequestMapping("/webapi04605.{ext}")
	public String webapi04605(CMi046 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道流量控制历史查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		List<Mi046> webapi04605 = webApi045ServiceImpl.webapi04605(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		modelMap.put("rows", webapi04605);
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page046/page04605";
	}
	
	@RequestMapping("/webapi04606.{ext}")
	public String webapi04606(CMi046 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道流量控制历史查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		Flow flow = webApi045ServiceImpl.webapi04606(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		modelMap.put("rows", flow);
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page046/page04605";
	}
	

	public WebApi045Service getWebApi045ServiceImpl() {
		return webApi045ServiceImpl;
	}
	public void setWebApi045ServiceImpl(WebApi045Service webApi045ServiceImpl) {
		this.webApi045ServiceImpl = webApi045ServiceImpl;
	}

	public CodeListApi001Service getCodeListApi001Service() {
		return codeListApi001Service;
	}

	public void setCodeListApi001Service(CodeListApi001Service codeListApi001Service) {
		this.codeListApi001Service = codeListApi001Service;
	}
}
