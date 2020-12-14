package com.yondervision.mi.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.common.message.APPMessageI;
import com.yondervision.mi.common.message.SimpleHttpMessageI;
import com.yondervision.mi.dao.Mi011DAO;
import com.yondervision.mi.dto.Mi011;
import com.yondervision.mi.dto.Mi011Example;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;

public class MsgSendApi001ServiceImpl implements MsgSendApi001Service{
	
	/** 
	 * 移动互联应用服务平台同公积金中心通信接口
	 */
	@SuppressWarnings("unchecked")
	public String send(HttpServletRequest request, HttpServletResponse response)throws Exception {
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText("移动互联应用服务平台同公积金中心通信"));

		// 对传入参数进行校验
		String centerid = request.getAttribute("centerId").toString();
		if (CommonUtil.isEmpty(centerid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市代码"));
			throw new NoRollRuntimeErrorException(ERROR.PARAMS_NULL.getValue(), "中心城市代码");
		}
		
		// 设定使用通讯标志，用于拦截转发过程返回结果中多余的{}
		request.setAttribute("httpFlg", "1"); 
		
		// 1.根据中心id，获取对应该中心的通信接口类
		Mi011Example example = new Mi011Example();
		List<Mi011> resultList = new ArrayList<Mi011>();
		Mi011DAO mi011Dao = (Mi011DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi011Dao");
		String req = "";
		try {
			example.createCriteria().andCenteridEqualTo(centerid);
			resultList = mi011Dao.selectByExample(example);
		
			if (CommonUtil.isEmpty(resultList)) {
				throw new TransRuntimeErrorException(ERROR.CONNECT_ERROR.getValue(),"获取通讯处理类失败，通讯表中无记录");
			}else {
				if(CommonUtil.isEmpty(resultList.get(0).getClassname())) {
					throw new TransRuntimeErrorException(ERROR.CONNECT_ERROR.getValue(),"获取通讯处理类失败，通讯表通信接口类为空");
				}
			}
			Mi011 result = resultList.get(0);
		
			// 2.根据通信接口类，实例化具体的通信接口			
			APPMessageI messageObj = (APPMessageI)com.yondervision.mi.util.SpringContextUtil.getBean(result.getClassname());
			
			// 3.进行通信转发
			req = messageObj.send(request, response);
			log.info(LOG.END_BUSIN.getLogText("移动互联应用服务平台同公积金中心通信"));
		} catch (NoRollRuntimeErrorException e) {
			throw e;
		} catch (TransRuntimeErrorException e) {
			throw e;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					ERROR.SYS.getValue(), "未知错误，请查看日志或者联系管理员。");
			log.error(e.getMessage(), e);
			throw tre;
		}
		return req;
	}
	
	/**
	 * 移动互联应用服务平台同互联网（百度地图）通信接口
	 * @param:城市、地址
	 */
	@SuppressWarnings("unchecked")
	public void mapHttpSend(HttpServletRequest request, HttpServletResponse response, String...params)throws Exception{
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText("移动互联应用服务平台同互联网（百度地图）通信"));
		
		// 对传入参数进行校验
		for (int i = 0; i < params.length; i++) {
			if (CommonUtil.isEmpty(params[i])) {
				log.error(ERROR.PARAMS_NULL.getLogText(params[i]));
				throw new NoRollRuntimeErrorException(ERROR.PARAMS_NULL.getValue(), params[i]); 
			}
		}
		String centerid = request.getAttribute("centerId").toString();
		if (CommonUtil.isEmpty(centerid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市代码"));
			throw new NoRollRuntimeErrorException(ERROR.PARAMS_NULL.getValue(), "中心城市代码");
		}
		
		// 设定使用通讯标志，用于拦截转发过程返回结果中多余的{}
		request.setAttribute("httpFlg", "1"); 

		// 1.根据中心id，获取对应该中心的通信接口类
		Mi011Example example = new Mi011Example();
		List<Mi011> resultList = new ArrayList<Mi011>();
		Mi011DAO mi011Dao = (Mi011DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi011Dao");
		try {
			example.createCriteria().andCenteridEqualTo(centerid);
			resultList = mi011Dao.selectByExample(example);
		
			if (resultList == null || resultList.size() ==0) {
				throw new TransRuntimeErrorException(ERROR.CONNECT_ERROR.getValue(),"获取通讯处理类失败，通讯表中无记录");
			}else {
				if(CommonUtil.isEmpty(resultList.get(0).getClassname())) {
					throw new TransRuntimeErrorException(ERROR.CONNECT_ERROR.getValue(),"获取通讯处理类失败，通讯表通信接口类为空");
				}
			}
			Mi011 result = resultList.get(0);
		
			// 2.根据通信接口类，实例化具体的通信接口			
			APPMessageI messageObj = (APPMessageI)com.yondervision.mi.util.SpringContextUtil.getBean(result.getClassname());

			request.setAttribute("mapParams", params);
			// 3.进行通信转发
			messageObj.send(request, response);
			
			log.info(LOG.END_BUSIN.getLogText("移动互联应用服务平台同互联网（百度地图）通信"));
		} catch (NoRollRuntimeErrorException e) {
			throw e;
		} catch (TransRuntimeErrorException e) {
			throw e;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					ERROR.SYS.getValue(), "未知错误，请查看日志或者联系管理员。");
			log.error(e.getMessage(), e);
			throw tre;
		}
	}
	
	/**
	 * 移动互联应用服务平台-内容同步导出到第三方平台
	 */
	public String synchroExportSend(String centerid, String param) throws Exception{
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText("移动互联应用服务平台内容同步导出到第三方平台通信"));

		// 对传入参数进行校验
		if (CommonUtil.isEmpty(centerid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("城市中心代码"));
			throw new NoRollRuntimeErrorException(ERROR.PARAMS_NULL.getValue(), "中心城市代码");
		}
		if (CommonUtil.isEmpty(param)) {
			log.error(ERROR.PARAMS_NULL.getLogText("同步导出内容"));
			throw new NoRollRuntimeErrorException(ERROR.PARAMS_NULL.getValue(), "同步导出内容");
		}
		
		// 1.根据中心id，properties文件获取对应该中心的通信接口类
		String synchroExportSendClassname = PropertiesReader.getProperty("properties.properties", "simplehttp_classname");
		String synchroExportSendUrl = PropertiesReader.getProperty("properties.properties", "synchroexport_sendurl_"+centerid);
		String req = "";
		// 2.根据通信接口类，实例化具体的通信接口			
		SimpleHttpMessageI messageObj = (SimpleHttpMessageI)com.yondervision.mi.util.SpringContextUtil.getBean(synchroExportSendClassname);
		
		// 3.进行通信转发
		req = messageObj.synchroExportContentPostBySign(synchroExportSendUrl, param, centerid);
		log.info(LOG.END_BUSIN.getLogText("移动互联应用服务平台内容同步导出到第三方平台通信"));
		return req;
	}
}