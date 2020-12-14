package com.yondervision.mi.controller;

import java.util.ArrayList;
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
import com.yondervision.mi.form.AppApi70203Form;
import com.yondervision.mi.form.AppApi70204Form;
import com.yondervision.mi.form.AppApi70205Form;
import com.yondervision.mi.form.AppApi70206Form;
import com.yondervision.mi.form.AppApi70208Form;
import com.yondervision.mi.form.AppApi702CommonForm;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.AppApi70201Result;
import com.yondervision.mi.result.AppApi70202Result;
import com.yondervision.mi.result.AppApi70203Result;
import com.yondervision.mi.result.AppApi70207Result;
import com.yondervision.mi.result.AppApi70208Result;
import com.yondervision.mi.service.AppApi702Service;
import com.yondervision.mi.util.CommonUtil;

/**
 * @ClassName: AppApi702Contorller
 * @Description: 公积金报-移动客户端访问处理-无期次
 * @author gongqi
 * @date July 16, 2014 9:33:25 PM
 */
@Controller
public class AppApi702Contorller {
	@Autowired
	private AppApi702Service appApi702ServiceImpl = null;

	public void setAppApi702ServiceImpl(AppApi702Service appApi702ServiceImpl) {
		this.appApi702ServiceImpl = appApi702ServiceImpl;
	}
	
	/**
	 * 初始页面访问数据获取处理
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70201.{ext}")
	public String appapi70201(AppApi702CommonForm form, ModelMap modelMap,HttpServletRequest request) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("新闻列表获取");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"城市中心ID获取失败");
		}

		AppApi70201Result result = appApi702ServiceImpl.appapi70201(form,request);

		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", result);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
	
	/**
	 * 根据版块，进行信息展示数据获取处理
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70202.{ext}")
	public String appapi70202(AppApi702CommonForm form, ModelMap modelMap,HttpServletRequest request) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("根据版块，获取新闻列表");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"城市中心ID获取失败");
		}
		if(CommonUtil.isEmpty(form.getNewspaperforum())){
			log.error(ERROR.PARAMS_NULL.getLogText("报刊版块"));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"请选择版块");
		}

		AppApi70202Result result = appApi702ServiceImpl.appapi70202(form,request);

		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", result);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
	
	/**
	 * 新闻详细获取
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70203.{ext}")
	public String appapi70203(AppApi70203Form form, ModelMap modelMap) throws Exception{		
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("新闻详细获取");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"城市中心ID获取失败");
		}
		if(CommonUtil.isEmpty(form.getTitleId())){
			log.error(ERROR.PARAMS_NULL.getLogText("titleId"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"新闻序号获取失败");
		}
		AppApi70203Result result = appApi702ServiceImpl.appapi70203(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", result);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
	
	/**
	 * 对新闻进行评论--增加评论
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70204.{ext}")
	public String appapi70204(AppApi70204Form form, ModelMap modelMap) throws Exception{		
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("增加新闻评论");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"城市中心ID获取失败");
		}
		if(CommonUtil.isEmpty(form.getNewsSeqno())){
			log.error(ERROR.PARAMS_NULL.getLogText("newsseqno"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"新闻序号获取失败");
		}
		if(CommonUtil.isEmpty(form.getDeviceType())){
			log.error(ERROR.PARAMS_NULL.getLogText("devicetype"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"设备类型获取失败");
		}
		if(CommonUtil.isEmpty(form.getDeviceToken())){
			log.error(ERROR.PARAMS_NULL.getLogText("devicetoken"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"设备标识获取失败");
		}
		if(CommonUtil.isEmpty(form.getCommentUser())){
			log.error(ERROR.PARAMS_NULL.getLogText("commentuser"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"评论人获取失败");
		}
		if(CommonUtil.isEmpty(form.getCommentContent())){
			log.error(ERROR.PARAMS_NULL.getLogText("commentContent"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"评论内容");
		}
		
		appApi702ServiceImpl.appapi70204(form);
		
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
	
	/**
	 * 对新闻进行点赞/取消点赞
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70205.{ext}")
	public String appapi70205(AppApi70205Form form, ModelMap modelMap) throws Exception{		
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("对新闻进行点赞");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));

		if(CommonUtil.isEmpty(form.getNewsSeqno())){
			log.error(ERROR.PARAMS_NULL.getLogText("newsseqno"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"新闻序号获取失败");
		}
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"城市中心ID获取失败");
		}
		if(CommonUtil.isEmpty(form.getUser_Id())){
			log.error(ERROR.PARAMS_NULL.getLogText("user_Id"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"用户唯一ID获取失败");
		}
		if(CommonUtil.isEmpty(form.getPraiseFlg())){
			log.error(ERROR.PARAMS_NULL.getLogText("praiseFlg"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"点赞标记获取失败");
		}

		appApi702ServiceImpl.appapi70205(form);
		
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}

	/**
	 * 对评论进行点赞/取消点赞
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70206.{ext}")
	public String appapi70206(AppApi70206Form form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("对评论进行点赞");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));

		if(CommonUtil.isEmpty(form.getCommentSeqno())){
			log.error(ERROR.PARAMS_NULL.getLogText("commentSeqno"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"评论序号获取失败");
		}
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"城市中心ID获取失败");
		}
		if(CommonUtil.isEmpty(form.getUser_Id())){
			log.error(ERROR.PARAMS_NULL.getLogText("user_Id"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"用户唯一ID获取失败");
		}
		if(CommonUtil.isEmpty(form.getPraiseFlg())){
			log.error(ERROR.PARAMS_NULL.getLogText("praiseFlg"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"点赞标记获取失败");
		}

		appApi702ServiceImpl.appapi70206(form);
		
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
	
	/**
	 * 订阅设置-获取待订阅的栏目范围
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70207.{ext}")
	public String appapi70207(AppApiCommonForm form, ModelMap modelMap,HttpServletRequest request) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("待订阅栏目范围获取");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));

		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"城市中心ID获取失败");
		}
		
		List<AppApi70207Result> resultList = new ArrayList<AppApi70207Result>();
		resultList = appApi702ServiceImpl.appapi70207(form,request);
		
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", resultList);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
	
	/**
	 * 我的订阅-初始页面访问数据获取处理
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70208.{ext}")
	public String appapi70208(AppApi70208Form form, ModelMap modelMap,HttpServletRequest request) throws Exception{		
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("我的订阅-新闻列表获取");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"城市中心ID获取失败");
		}
		if(CommonUtil.isEmpty(form.getNewspapercolumns())){
			log.error(ERROR.PARAMS_NULL.getLogText("newspapercolumns"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"栏目获取失败");
		}

		AppApi70208Result result = appApi702ServiceImpl.appapi70208(form,request);

		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", result);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
}
