package com.yondervision.mi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.Mi701WithBLOBs;
import com.yondervision.mi.form.WebApi70501Form;
import com.yondervision.mi.form.WebApi70502Form;
import com.yondervision.mi.form.WebApi70503Form;
import com.yondervision.mi.form.WebApi70504Form;
import com.yondervision.mi.form.WebApi70505Form;
import com.yondervision.mi.result.WebApi70504_queryResult;
import com.yondervision.mi.result.WebApi70505_queryResult;
import com.yondervision.mi.service.WebApi705Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi705Contorller 
* @Description: 新闻发布-无期次
* @author gongqi  
* @date July 18, 2014 9:33:25 PM   
*/ 
@Controller
public class WebApi705Contorller {

	@Autowired
	private WebApi705Service webApi705ServiceImpl;
	public void setWebApi705ServiceImpl(WebApi705Service webApi705ServiceImpl) {
		this.webApi705ServiceImpl = webApi705ServiceImpl;
	}

	/**
	 * 增加新闻
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi70501.{ext}")
	public String webapi70501(WebApi70501Form form , ModelMap modelMap, HttpServletRequest request) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "增加新闻";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		String rquestUrlTmp = request.getRequestURL().toString();
		String reqUrl = rquestUrlTmp.substring(0,rquestUrlTmp.length()-23);
		
		webApi705ServiceImpl.webapi70501(form, reqUrl);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page705/page70501";
	}
	
	/**
	 * 删除新闻
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi70502.{ext}")
	public String webapi70502(WebApi70502Form form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "删除新闻";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi705ServiceImpl.webapi70502(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page705/page70501";
	}
	
	/**
	 * 修改新闻
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi70503.{ext}")
	public String webapi70503(WebApi70503Form form , ModelMap modelMap, HttpServletRequest request) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "修改新闻";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		String rquestUrlTmp = request.getRequestURL().toString();
		String reqUrl = rquestUrlTmp.substring(0,rquestUrlTmp.length()-23);
		System.out.println("reqUrl="+reqUrl);
		
		webApi705ServiceImpl.webapi70503(form,reqUrl);

		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page705/page70501";
	}
	
	/**
	 * 信息查询--分页
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi70504.{ext}")
	public String webapi70504(WebApi70504Form form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("分页查询新闻（无期次）信息");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		// 业务处理
		WebApi70504_queryResult queryResult = webApi705ServiceImpl.webapi70504(form);
		if(queryResult.getList701().isEmpty()||queryResult.getList701().size()==0){
			//异常处理
			log.error(ERROR.NO_DATA.getLogText("MI701", "form"));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"新闻");
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("total", queryResult.getTotal());
		modelMap.put("totalPage", queryResult.getTotalPage());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList701());
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page705/page70501";
	}
	
	/**
	 * 信息查询-根据seqno
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi70505.{ext}")
	public String webapi70505(WebApi70505Form form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("根据seqno信息查询");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			
		// 业务处理
		List<Mi701WithBLOBs> mi701List = webApi705ServiceImpl.webapi70505(form);//有且只有一条
		
		WebApi70505_queryResult queryResult = new WebApi70505_queryResult();
		Mi701WithBLOBs mi701 = (Mi701WithBLOBs)mi701List.get(0);
		queryResult.setSeqno(mi701.getSeqno());
		queryResult.setCenterid(mi701.getCenterid());
		/*if ("0".equals(mi701.getNewspaperforum())){
			queryResult.setNewspaperforum(mi701.getClassification());
			queryResult.setNewspapercolumns("");
		}else{
			queryResult.setNewspaperforum(mi701.getNewspaperforum());
			queryResult.setNewspapercolumns(mi701.getClassification());
		}*/
		queryResult.setClassification(mi701.getClassification());
		queryResult.setTitle(mi701.getTitle());
		queryResult.setCitedtitle(mi701.getCitedtitle());
		queryResult.setSubtopics(mi701.getSubtopics());
		queryResult.setSource(mi701.getSource());
		queryResult.setIntroduction(mi701.getIntroduction());
		queryResult.setBlurbs(mi701.getBlurbs());
		queryResult.setReleasetime(mi701.getReleasetime());
		queryResult.setImage(mi701.getImage());
		queryResult.setValidflag(mi701.getValidflag());
		queryResult.setDatemodified(mi701.getDatemodified());
		queryResult.setDatecreated(mi701.getDatecreated());
		queryResult.setLoginid(mi701.getLoginid());
		queryResult.setPraisecounts(mi701.getPraisecounts());
		queryResult.setFreeuse1(mi701.getFreeuse1());
		queryResult.setFreeuse2(mi701.getFreeuse2());
		queryResult.setFreeuse3(mi701.getFreeuse3());
		queryResult.setFreeuse4(mi701.getFreeuse4());
		queryResult.setFreeuse5(mi701.getFreeuse5());
		queryResult.setFreeuse6(mi701.getFreeuse6());
		queryResult.setContent(mi701.getContent());

		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", queryResult);

		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page705/page70501";
	}
}
