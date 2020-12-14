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
import com.yondervision.mi.form.AppApi70104Form;
import com.yondervision.mi.form.AppApi70105Form;
import com.yondervision.mi.form.AppApi70106Form;
import com.yondervision.mi.form.AppApi70107Form;
import com.yondervision.mi.form.AppApi70109Form;
import com.yondervision.mi.form.AppApi70110Form;
import com.yondervision.mi.form.AppApi701CommonForm;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.AppApi70101Result;
import com.yondervision.mi.result.AppApi70102Result;
import com.yondervision.mi.result.AppApi70103Result;
import com.yondervision.mi.result.AppApi70104Result;
import com.yondervision.mi.result.AppApi70108Result;
import com.yondervision.mi.result.AppApi70109Result;
import com.yondervision.mi.result.AppApi70110Result;
import com.yondervision.mi.service.AppApi701Service;
import com.yondervision.mi.util.CommonUtil;

/**
 * @ClassName: AppApi701Contorller
 * @Description: 公积金报-移动客户端访问处理
 * @author gongqi
 * @date July 16, 2014 9:33:25 PM
 */
@Controller
public class AppApi701Contorller {
	@Autowired
	private AppApi701Service appApi701ServiceImpl = null;

	public void setAppApi701ServiceImpl(AppApi701Service appApi701ServiceImpl) {
		this.appApi701ServiceImpl = appApi701ServiceImpl;
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
	@RequestMapping("/appapi70101.{ext}")
	public String appapi70101(AppApi701CommonForm form, ModelMap modelMap,HttpServletRequest request) throws Exception{		
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("新闻列表获取");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"城市中心ID获取失败");
		}

		AppApi70101Result result = appApi701ServiceImpl.appapi70101(form,request);

		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", result);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
	
	/**
	 * 根据报刊期次，进行信息展示数据获取处理
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70102.{ext}")
	public String appapi70102(AppApi701CommonForm form, ModelMap modelMap,HttpServletRequest request) throws Exception{		
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("根据期次，获取新闻列表");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"城市中心ID获取失败");
		}
		if(CommonUtil.isEmpty(form.getNewspapertimes())){
			log.error(ERROR.PARAMS_NULL.getLogText("newspapertimes"));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"请选择期次");
		}

		AppApi70102Result result = appApi701ServiceImpl.appapi70102(form,request);

		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", result);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
	
	/**
	 * 根据报刊期次、版块，进行信息展示数据获取处理
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70103.{ext}")
	public String appapi70103(AppApi701CommonForm form, ModelMap modelMap,HttpServletRequest request) throws Exception{		
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("根据期次、版块，获取新闻列表");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"城市中心ID获取失败");
		}
		if(CommonUtil.isEmpty(form.getNewspapertimes())){
			log.error(ERROR.PARAMS_NULL.getLogText("newspapertimes"));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"请选择期次");
		}
		if(CommonUtil.isEmpty(form.getNewspaperforum())){
			log.error(ERROR.PARAMS_NULL.getLogText("报刊版块"));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"请选择版块");
		}

		AppApi70103Result result = appApi701ServiceImpl.appapi70103(form,request);

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
	@RequestMapping("/appapi70104.{ext}")
	public String appapi70104(AppApi70104Form form, ModelMap modelMap) throws Exception{		
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
		AppApi70104Result result = appApi701ServiceImpl.appapi70104(form);
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
	@RequestMapping("/appapi70105.{ext}")
	public String appapi70105(AppApi70105Form form, ModelMap modelMap) throws Exception{		
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
		
		appApi701ServiceImpl.appapi70105(form);
		
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
	@RequestMapping("/appapi70106.{ext}")
	public String appapi70106(AppApi70106Form form, ModelMap modelMap) throws Exception{		
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

		appApi701ServiceImpl.appapi70106(form);
		
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
	@RequestMapping("/appapi70107.{ext}")
	public String appapi70107(AppApi70107Form form, ModelMap modelMap) throws Exception{
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

		appApi701ServiceImpl.appapi70107(form);
		
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
	@RequestMapping("/appapi70108.{ext}")
	public String appapi70108(AppApiCommonForm form, ModelMap modelMap,HttpServletRequest request) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("待订阅栏目范围获取");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));

		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"城市中心ID获取失败");
		}
		
		List<AppApi70108Result> resultList = new ArrayList<AppApi70108Result>();
		resultList = appApi701ServiceImpl.appapi70108(form,request);
		
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
	@RequestMapping("/appapi70109.{ext}")
	public String appapi70109(AppApi70109Form form, ModelMap modelMap,HttpServletRequest request) throws Exception{		
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

		AppApi70109Result result = appApi701ServiceImpl.appapi70109(form,request);

		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", result);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
	
	/**
	 * 我的订阅-根据报刊期次，进行信息展示数据获取处理
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70110.{ext}")
	public String appapi70110(AppApi70110Form form, ModelMap modelMap,HttpServletRequest request) throws Exception{		
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("我的订阅-根据期次，获取新闻列表");
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
		if(CommonUtil.isEmpty(form.getNewspapertimes())){
			log.error(ERROR.PARAMS_NULL.getLogText("newspapertimes"));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"请选择期次");
		}

		AppApi70110Result result = appApi701ServiceImpl.appapi70110(form,request);

		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", result);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
	
	/**
	 * 订阅设置-获取待订阅的版块范围
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70111.{ext}")
	public String appapi70111(AppApiCommonForm form, ModelMap modelMap,HttpServletRequest request) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("待订阅版块范围获取");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));

		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"城市中心ID获取失败");
		}
		
		List<AppApi70108Result> resultList = new ArrayList<AppApi70108Result>();
		resultList = appApi701ServiceImpl.appapi70111(form,request);
		
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", resultList);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
	
	/**
	 * 我的订阅-初始页面访问数据获取处理（根据订阅版块范围）
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70112.{ext}")
	public String appapi70112(AppApi70109Form form, ModelMap modelMap,HttpServletRequest request) throws Exception{		
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
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"版块获取失败");
		}

		AppApi70109Result result = appApi701ServiceImpl.appapi70112(form,request);

		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", result);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
	
	/**
	 * 我的订阅-根据报刊期次，进行信息展示数据获取处理（及订阅版块范围）
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70113.{ext}")
	public String appapi70113(AppApi70110Form form, ModelMap modelMap,HttpServletRequest request) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("我的订阅-根据期次，获取新闻列表");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"城市中心ID获取失败");
		}
		if(CommonUtil.isEmpty(form.getNewspapercolumns())){
			log.error(ERROR.PARAMS_NULL.getLogText("newspapercolumns"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"版块获取失败");
		}
		if(CommonUtil.isEmpty(form.getNewspapertimes())){
			log.error(ERROR.PARAMS_NULL.getLogText("newspapertimes"));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"请选择期次");
		}

		AppApi70110Result result = appApi701ServiceImpl.appapi70113(form,request);

		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", result);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
	
	/**
	 * 今日新闻，初始页面获取处理
	 * （把期次列表也返回，等页面版块切换时，再传回来，直接利用之前的接口appapi70103.json,不再新增接口）
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70114.{ext}")
	public String appapi70114(AppApi701CommonForm form, ModelMap modelMap,HttpServletRequest request) throws Exception{		
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("新闻列表获取");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"城市中心ID获取失败");
		}

		AppApi70101Result result = appApi701ServiceImpl.appapi70114(form,request);

		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", result);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
	
	/**
	 * 往日新闻，初始页面访问数据获取处理
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70115.{ext}")
	public String appapi70115(AppApi701CommonForm form, ModelMap modelMap,HttpServletRequest request) throws Exception{		
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("新闻列表获取");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"城市中心ID获取失败");
		}

		AppApi70101Result result = appApi701ServiceImpl.appapi70115(form,request);

		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", result);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
}
