/**
 * 
 */
package com.yondervision.mi.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.CMi120;
import com.yondervision.mi.dto.CMi121;
import com.yondervision.mi.result.WebApi41104_queryResult;
import com.yondervision.mi.result.WebApi41108_queryResult;
import com.yondervision.mi.service.WebApi411Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi411Contorller 
* @Description: APP图片动图
* @author Caozhongyan
* @date Dec 2, 2013 9:35:05 AM   
* 
*/  
@Controller
public class WebApi411Contorller {
	@Autowired
	private WebApi411Service webApi411ServiceImpl;

	public WebApi411Service getWebApi411ServiceImpl() {
		return webApi411ServiceImpl;
	}

	public void setWebApi411ServiceImpl(WebApi411Service webApi411ServiceImpl) {
		this.webApi411ServiceImpl = webApi411ServiceImpl;
	}

	/**
	 * APP图片动画
	 * @param form APP图片动画信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi41101.{ext}")
	public String webapi41101(CMi120 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();	
		form.setBusinName("APP图片动画信息填加");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}
		if (CommonUtil.isEmpty(form.getAnimatecode())) {
			log.error(ERROR.PARAMS_NULL.getLogText("animatecode"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "动画编号");
		}
		if (CommonUtil.isEmpty(form.getAnimatename())) {
			log.error(ERROR.PARAMS_NULL.getLogText("animatename"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "动画名称");
		}
		if (CommonUtil.isEmpty(form.getDevid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("devid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "设备区分");
		}
		if (CommonUtil.isEmpty(form.getIntervaltime())) {
			log.error(ERROR.PARAMS_NULL.getLogText("intervaltime"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "间隔时间");
		}
		if (CommonUtil.isEmpty(form.getLooptype())) {
			log.error(ERROR.PARAMS_NULL.getLogText("loptype"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "循环方式");
		}		
		// TODO 业务处理
		webApi411ServiceImpl.webapi41101(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page411/page41101";
	}
	
	/**
	 * APP图片动画删除
	 * @param form APP用户设备信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi41102.{ext}")
	public String webapi41102(CMi120 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("APP图片动画删除");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		// TODO 业务处理
		webApi411ServiceImpl.webapi41102(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page411/page41101";
	}
	
	/**
	 * APP图片动画信息修改
	 * @param form APP用户通讯信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi41103.{ext}")
	public String webapi41103(CMi120 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("APP用户通讯信息修改");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			
		// TODO 业务处理
		webApi411ServiceImpl.webapi41103(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page411/page41101";
	}
	/**
	 * APP图片动画信息查询
	 * @param form APP用户通讯信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi41104.{ext}")
	public String webapi41104(CMi120 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();			
		form.setBusinName("APP用户通讯信息查询");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		// TODO 业务处理
		WebApi41104_queryResult queryResult = webApi411ServiceImpl.webapi41104(form);
		if(queryResult.getList120().isEmpty()||queryResult.getList120().size()==0){		
			log.error(ERROR.NO_DATA.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"APP图片动画信息");
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList120());
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page411/page41101";
	}
	
	
	/**
	 * APP图片动画明细
	 * @param form APP图片动画信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi41105.{ext}")
	public String webapi41105(CMi121 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();	
		form.setBusinName("APP图片动画明细信息填加");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}
		if (CommonUtil.isEmpty(form.getXh())) {
			log.error(ERROR.PARAMS_NULL.getLogText("xh"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "动画序号");
		}
		if (CommonUtil.isEmpty(form.getAnid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("anid()"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "动画ID");
		}
		if (CommonUtil.isEmpty(form.getImgpath())) {
			log.error(ERROR.PARAMS_NULL.getLogText("imgpath"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "图片名称");
		}
		if (CommonUtil.isEmpty(form.getDisplaydirection())) {
			log.error(ERROR.PARAMS_NULL.getLogText("displaydirection"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "图片切换方式");
		}			
		// TODO 业务处理
		webApi411ServiceImpl.webapi41105(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page411/page41101";
	}
	
	/**
	 * APP图片动画明细删除
	 * @param form APP用户设备信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi41106.{ext}")
	public String webapi41106(CMi121 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("APP图片动画明细删除");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		// TODO 业务处理
		webApi411ServiceImpl.webapi41106(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page411/page41101";
	}
	
	/**
	 * APP图片动画明细信息修改
	 * @param form APP用户通讯信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi41107.{ext}")
	public String webapi41107(CMi121 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("APP用户通讯明细信息修改");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			
		// TODO 业务处理
		webApi411ServiceImpl.webapi41107(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page411/page41101";
	}
	
	/**
	 * APP图片动画明细信息查询
	 * @param form APP用户通讯信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi41108.{ext}")
	public String webapi41108(CMi121 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();			
		form.setBusinName("APP用户通讯明细信息查询");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		// TODO 业务处理
		WebApi41108_queryResult queryResult = webApi411ServiceImpl.webapi41108(form);
		if(queryResult.getList121().isEmpty()||queryResult.getList121().size()==0){		
			log.error(ERROR.NO_DATA.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"APP图片动画明细信息");
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList121());
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page411/page41101";
	}
	
	/**
	 * 图片上传并返回文件名称，路径
	 * @param form 楼盘查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi41109.do")
	public @ResponseBody Map<String,String> webapi41109(String magecenterId, @RequestParam MultipartFile file, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String fileName = webApi411ServiceImpl.webapi41109(magecenterId, file);
		//request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)
		
		String filePath = CommonUtil.getDownloadFileUrl(
				"pushdhtp", magecenterId+File.separator+fileName, true);
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("recode", Constants.WEB_SUCCESS_CODE);
		map.put("msg", Constants.WEB_SUCCESS_MSG);
		map.put("fileName", fileName); // TODO 改为fielName
		map.put("downloadPath", filePath);
		return map;
//		response.setHeader("content-type", "application/json;charset=UTF-8"); 
//		response.setContentType("application/json;charset=utf-8");
//		response.setContentType("Content-Type:text/html;charset=UTF-8");
//		response.getWriter().println("{fileName:'"+fileName+"',filePath:'"+filePath+"'}");
//		response.getWriter().println(filePath);
//		request.setAttribute("downloadPath", filePath);
//		List<Mi202> list = this.codeListApi001Service.getAreaMessage(magecenterId);
//		modelMap.put("mi202list", list);
//		response.setContentType("Content-Type:text/html;charset=UTF-8");
	}
}
