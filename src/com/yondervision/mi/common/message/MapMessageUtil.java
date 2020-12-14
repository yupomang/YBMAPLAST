package com.yondervision.mi.common.message;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.Mi011DAO;
import com.yondervision.mi.dto.Mi011;
import com.yondervision.mi.dto.Mi011Example;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;

/**
 * 移动互联应用服务平台同互联网（百度地图）通信实现类
 * @author gongqi
 *
 */
public class MapMessageUtil implements APPMessageI{
	
	/**
	 * 移动互联应用服务平台同互联网（百度地图）通信
	 */
	@SuppressWarnings("unchecked")
	public String send(HttpServletRequest request, HttpServletResponse response)throws Exception {
		Logger log = LoggerUtil.getLogger();
		try {
			String httpURL = null;
			String[] mapParams = (String[])request.getAttribute("mapParams");
			
			// 1. 根据中心id，获取目标静态URL
			String centerid = request.getAttribute("centerId").toString();

			Mi011Example example = new Mi011Example();
			List<Mi011> resultList = new ArrayList<Mi011>();
			Mi011DAO mi011Dao = (Mi011DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi011Dao");
			
			example.createCriteria().andCenteridEqualTo(centerid);
			resultList = mi011Dao.selectByExample(example);
		
			if (CommonUtil.isEmpty(resultList)) {
				throw new TransRuntimeErrorException(ERROR.CONNECT_ERROR.getValue(),"获取通讯URL失败，通讯表中无记录");
			}else{
				if (CommonUtil.isEmpty(resultList.get(0).getUrl())) {
					throw new TransRuntimeErrorException(ERROR.CONNECT_ERROR.getValue(),"获取通讯URL失败，通讯表中url为空");
				}
			}
	
			httpURL = resultList.get(0).getUrl();
			for (int i = 0; i < mapParams.length; i++) {
				httpURL = httpURL.replaceAll("\\{" + String.valueOf(i) + "\\}", URLEncoder.encode(mapParams[i],"UTF-8"));
			}
			
			// 2. 进行通信转发
			send(request, response, httpURL);

		}catch (UnsupportedEncodingException e){
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					ERROR.SYS.getValue(), "通讯地址转码异常，请查看日志或者联系管理员。");
			log.error(e.getMessage(), e);
			throw tre;
		}catch (TransRuntimeErrorException e) {
			throw e;
		}catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					ERROR.SYS.getValue(), "未知错误，请查看日志或者联系管理员。");
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}
	
	/**
	 * 通信转发
	 */
	@SuppressWarnings("unchecked")
	private void send(HttpServletRequest request, HttpServletResponse response, String url) throws Exception{
		Logger log = LoggerUtil.getLogger();
		Map propsMap = request.getParameterMap();
		
		PostMethod mypost = new PostMethod(url);

		//参数设置
		Set<String> keySet = propsMap.keySet();
		NameValuePair[] postData = new NameValuePair[keySet.size()];
		int index=0;
		for(String key:keySet){
			postData[index++] = new NameValuePair(key,propsMap.get(key).toString());
		}
		mypost.addParameters(postData);
		
		HttpClient httpClient = new HttpClient();
		String connectTimeout = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "http_connection_time").trim();
		String readTimeout = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "http_read_time").trim();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(Integer.valueOf(connectTimeout));
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(Integer.valueOf(readTimeout));
		JSONObject jsonObj = null;
		try {
			int re_code = httpClient.executeMethod(mypost);
			String repMsg = null;
			if (re_code == 200) {
				repMsg = mypost.getResponseBodyAsString();
				log.info(LOG.REV_INFO.getLogText(repMsg));
				
			} else {
				throw new TransRuntimeErrorException(ERROR.CONNECT_SEND_ERROR.getValue(),"http错误码" + re_code);
			}

			// 将通信接收结果封转成JSONObject
			jsonObj = JSONObject.fromObject(repMsg);
			log.info(LOG.REV_INFO.getLogText("转成jsonObj为"+repMsg));
			
			// 将通信结果JSONObject返回给上一层
			request.setAttribute("recMapJsonObj", jsonObj);
			

			/*String encoding = null;
			if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
				encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}
			response.getOutputStream().write(repMsg.getBytes(encoding));*/
			
		} catch (TransRuntimeErrorException e) {
			throw e;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					ERROR.SYS.getValue(), "请检查通信相关参数是否正确或连接通道是否畅通。");
			log.error(e.getMessage(), e);
			throw tre;
		}
	}
}
