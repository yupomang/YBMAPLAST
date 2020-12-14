/**
 * 
 */
package com.yondervision.mi.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.yondervision.mi.common.filter.ParameterRequestWrapper;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.Mi201;
import com.yondervision.mi.dto.Mi203;
import com.yondervision.mi.form.AppApi00801Form;
import com.yondervision.mi.form.AppApi00802Form;
import com.yondervision.mi.result.AppApi00801Result;
import com.yondervision.mi.result.AppApi00801Result01;
import com.yondervision.mi.result.AppApi00802Result;
import com.yondervision.mi.result.AppApi10101Result;
import com.yondervision.mi.result.AppApi10101Result01;
import com.yondervision.mi.result.TitleInfoBean;
import com.yondervision.mi.service.AppApi008Service;
import com.yondervision.mi.service.AppApi101Service;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JavaBeanUtil;

/** 
* @ClassName: AppApi008Contorller 
* @Description: 楼盘查询Controller
* @author Caozhongyan
* @date Sep 27, 2013 9:17:04 AM 
* 
*/ 
@Controller
public class AppApi008Contorller {
	//Autowired设置Spring自动注入与其同名的bean
	@Autowired
	private MsgSendApi001Service msgSendApi001Service = null;
	public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
		this.msgSendApi001Service = msgSendApi001Service;
	}
	
	@Autowired
	private AppApi008Service appApi008ServiceImpl = null;
	

	public AppApi008Service getAppApi008ServiceImpl() {
		return appApi008ServiceImpl;
	}


	public void setAppApi008ServiceImpl(AppApi008Service appApi008ServiceImpl) {
		this.appApi008ServiceImpl = appApi008ServiceImpl;
	}

	@Autowired
	private AppApi101Service appApi101ServiceImpl = null;
	

	public AppApi101Service getAppApi101ServiceImpl() {
		return appApi101ServiceImpl;
	}


	public void setAppApi101ServiceImpl(AppApi101Service appApi101ServiceImpl) {
		this.appApi101ServiceImpl = appApi101ServiceImpl;
	}

	/**
	 * 楼盘查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00801.{ext}")
	public String appApi00801(AppApi00801Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("楼盘信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));				
		List<Mi203> list = appApi008ServiceImpl.appApi00801Select(form);
		if(list.isEmpty()||list.size()==0){
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"楼盘信息");
		}
		List resultList = new ArrayList();
		
		if(form.getCenterId().equals("00075500")){
			for(int i=0;i<list.size();i++){
				AppApi00801Result appApi00801Result = new AppApi00801Result();					
				List<TitleInfoBean> TitleInfoBeanContent = new ArrayList<TitleInfoBean>();
				List<TitleInfoBean> TitleInfoBeanList = new ArrayList<TitleInfoBean>();				
				Mi203 mi203 = (Mi203)list.get(i);
				
				mi203.setTel("tel://"+mi203.getTel());			
				if(!(CommonUtil.isEmpty(mi203.getPositionX())||CommonUtil.isEmpty(mi203.getPositionY()))){
					mi203.setAddress("map://"+mi203.getAddress());
				}
				
				AppApi00801Result01 appApi00801Result01 = new AppApi00801Result01();
				appApi00801Result01.setX(mi203.getPositionX()==null?"":mi203.getPositionX());
				appApi00801Result01.setY(mi203.getPositionY()==null?"":mi203.getPositionY());
				//需要进行图片地址拼接
				appApi00801Result01.setImg(mi203.getImageUrl()==null?"":request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)+CommonUtil.getDownloadFileUrl(
						"push_house_img", form.getCenterId()+File.separator+mi203.getImageUrl(), true));
				appApi00801Result.setMap(appApi00801Result01);
				if(form.getCenterId().equals("00075500")){
					TitleInfoBeanContent = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00801sz.content", mi203);
				}else{
					TitleInfoBeanContent = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00801"+form.getCenterId().trim()+".content", mi203);
				}							
				appApi00801Result.setContent(TitleInfoBeanContent);
				TitleInfoBeanList = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00801.list", mi203);
				appApi00801Result.setList(TitleInfoBeanList);
				if(form.getCenterId().equals("00075500")){
					List<AppApi10101Result> banks = new ArrayList<AppApi10101Result>();
					if(!CommonUtil.isEmpty(mi203.getBankNames())){
						log.debug("合作银行："+mi203.getBankNames());
						String[] bankNames = mi203.getBankNames().split("\\,");						
						for(int j=0;j<bankNames.length;j++){
							Mi201 mi201 = null;						
							mi201 =	appApi101ServiceImpl.appApi10103Select(bankNames[j], mi203.getCenterid(), form.getChannel());
							if(mi201==null){
								log.info(LOG.SELF_LOG.getLogText("合作银行:"+bankNames[j]+",未查询到与网点信息关联"));
							}else{
								AppApi10101Result appApi10101Result = new AppApi10101Result();					
								List<TitleInfoBean> TitleInfoBeanContent101 = new ArrayList<TitleInfoBean>();
								List<TitleInfoBean> TitleInfoBeanList101 = new ArrayList<TitleInfoBean>();			
								
								mi201.setTel("tel://"+mi201.getTel());
								mi201.setServiceTime("time://"+mi201.getServiceTime());
								if(!(CommonUtil.isEmpty(mi201.getPositionX())||CommonUtil.isEmpty(mi201.getPositionY()))){
									mi201.setAddress("map://"+mi201.getAddress());
								}			
								AppApi10101Result01 appApi10101Result01 = new AppApi10101Result01();
								appApi10101Result01.setX(mi201.getPositionX()==null?"":mi201.getPositionX());
								appApi10101Result01.setY(mi201.getPositionY()==null?"":mi201.getPositionY());
								//需要进行图片地址拼接
								appApi10101Result01.setImg(mi201.getImageUrl()==null?"":request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)+CommonUtil.getDownloadFileUrl(
										"push_website_img", form.getCenterId()+File.separator+mi201.getImageUrl(), true));
								appApi10101Result.setMap(appApi10101Result01);				
								TitleInfoBeanContent101 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi10101.content", mi201);				
								appApi10101Result.setContent(TitleInfoBeanContent101);
								TitleInfoBeanList101 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi10101.list", mi201);										
								appApi10101Result.setList(TitleInfoBeanList101);
								banks.add(appApi10101Result);
							}					
						}
					}
					if(!(banks==null)){
						appApi00801Result.setBanks(banks);
					}
				}			
				resultList.add(appApi00801Result);			
			}
		}else{
			for(int i=0;i<list.size();i++){
				AppApi00802Result appApi00802Result = new AppApi00802Result();					
				List<TitleInfoBean> TitleInfoBeanContent = new ArrayList<TitleInfoBean>();
				List<TitleInfoBean> TitleInfoBeanList = new ArrayList<TitleInfoBean>();				
				Mi203 mi203 = (Mi203)list.get(i);
				
				mi203.setTel("tel://"+mi203.getTel());			
				if(!(CommonUtil.isEmpty(mi203.getPositionX())||CommonUtil.isEmpty(mi203.getPositionY()))){
					mi203.setAddress("map://"+mi203.getAddress());
				}
				
				AppApi00801Result01 appApi00801Result01 = new AppApi00801Result01();
				appApi00801Result01.setX(mi203.getPositionX()==null?"":mi203.getPositionX());
				appApi00801Result01.setY(mi203.getPositionY()==null?"":mi203.getPositionY());
				//需要进行图片地址拼接
				appApi00801Result01.setImg(mi203.getImageUrl()==null?"":request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)+CommonUtil.getDownloadFileUrl(
						"push_house_img", form.getCenterId()+File.separator+mi203.getImageUrl(), true));
				appApi00802Result.setMap(appApi00801Result01);
				TitleInfoBeanContent = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00801"+form.getCenterId()+".content", mi203);
											
				appApi00802Result.setContent(TitleInfoBeanContent);
				TitleInfoBeanList = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00801.list", mi203);
				appApi00802Result.setList(TitleInfoBeanList);			
							
				resultList.add(appApi00802Result);			
			}
		}
		
				
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", resultList);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}	
	
	/**
	 * 楼盘查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/appapi00802.{ext}")
	public String appApi00802(AppApi00802Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("楼盘信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));				
				
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		request.setAttribute("centerId", form.getCenterId());		
		
		if(!"20".equals(request.getParameter("channel"))){
			HttpServletRequest request1 = (HttpServletRequest) request;
			HashMap m = new HashMap(request.getParameterMap());
			System.out.println("appapi00802=====form.getProjectname()=="+form.getProjectname());
//			System.out.println("appapi00802=====form.getProjectname()--UTF-8=="+new String(form.getProjectname().getBytes("iso8859-1"),"UTF-8"));
//			m.put("selectValue", new String(form.getSelectValue().getBytes("iso8859-1"),"UTF-8"));
//			m.put("projectname", new String(form.getProjectname().getBytes("iso8859-1"),"UTF-8"));
			ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
			msgSendApi001Service.send(wrapRequest, response);
		}else{
			msgSendApi001Service.send(request, response);		
		}
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 楼盘查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/appapi00803.{ext}")
	public String appApi00803(AppApi00802Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("楼盘栋号信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));				
				
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		request.setAttribute("centerId", form.getCenterId());		
		
		if(!"20".equals(request.getParameter("channel"))){
			HttpServletRequest request1 = (HttpServletRequest) request;
			HashMap m = new HashMap(request.getParameterMap());
			//m.put("selectValue", new String(form.getSelectValue().getBytes("iso8859-1"),"UTF-8"));
			ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
			msgSendApi001Service.send(wrapRequest, response);
		}else{
			msgSendApi001Service.send(request, response);		
		}
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
}
