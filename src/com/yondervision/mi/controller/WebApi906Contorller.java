package com.yondervision.mi.controller;

import java.io.File;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.message.SimpleHttpMessageUtil;
import com.yondervision.mi.form.WebApi90601Form;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;
import com.yondervision.mi.util.couchbase.CouchBase;

/** 
* @ClassName: WebApi906Contorller 
* @Description:	综合服务平台工单接口
* @date 2016年9月24日 上午12:25:22   
* 
*/ 
@Controller
public class WebApi906Contorller {

	/**
	 * 工单系统——如何获得认证token-缓存到本地
	 * 参数 request接受 key,secret
	 * POST
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping("/webapi90601.json")
	public String webapi90601(WebApi90601Form form ,ModelMap modelMap,  HttpServletRequest request) throws Exception{
		String centerid = "";
		if(CommonUtil.isEmpty(form.getCenterId())){
			UserContext user = UserContext.getInstance();
			centerid = user.getCenterid();
		}else{
			centerid = form.getCenterId();
		}
		
		System.out.println("webapi90605---centerid==="+centerid);
		
		String token = "";
		CouchBase cb = CouchBase.getInstance();
		String key = "gd|token|"+centerid;
		Object obj = cb.get(key);
		if(CommonUtil.isEmpty(obj)){
			
			SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
			String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
					"gdurl").trim()+"/service/tokens";
			
			String appKey = "";
			String appSecret = "";
			if(CommonUtil.isEmpty(form.getKey())){
				appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "gd_app_key_"+centerid).trim();
			}else{
				appKey = form.getKey();
			}
			if(CommonUtil.isEmpty(form.getSecret())){
				appSecret = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "gd_app_secret_"+centerid).trim();
			}else{
				appSecret = form.getSecret();
			}
			HashMap hashMap = new HashMap();
			hashMap.put("key", appKey);
			hashMap.put("secret", appSecret);
			hashMap.put("from_plat", form.getFrom_plat());
			
			System.out.println("webapi90605---url=="+url + "?key=" + appKey + "&secret=" + appSecret + "&from_plat=" + form.getFrom_plat());
			String reqUrl = url+ "?key=" + appKey + "&secret=" + appSecret + "&from_plat=" + form.getFrom_plat();
//			String resStr = msm.sendPost(url, hashMap, request.getCharacterEncoding());
			String resStr = msm.sendGet(reqUrl, request.getCharacterEncoding());
			System.out.println("/webapi90605.json---res====" + resStr);
			
			JSONObject tokenJson = JSONObject.fromObject(resStr);
			
		    String recode = "", msg = "";
		    recode = tokenJson.getString("code");
		    msg = tokenJson.getString("msg");
		    if(!"0000".equals(recode)){
		    	throw new TransRuntimeErrorException("299992", new String[] { msg });
		    }
		      
		    token = tokenJson.getString("data");
		      
		    cb.save(key, token, 7200-100); 
			
		}else{
			token = String.valueOf(obj);
		}
		
//		String resMsg = "";
//		HashMap map = new HashMap();
//		map.put("recode", Constants.WEB_SUCCESS_CODE);
//		map.put("msg", Constants.WEB_SUCCESS_MSG);
//		map.put("token", token);
//		
//		ObjectMapper mapper = new  ObjectMapper();
//		JSONObject resJsonObj = mapper.convertValue(map, JSONObject.class);
//		resMsg = resJsonObj.toString();
//		
//		String encoding = null;
//		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
//			encoding = "UTF-8";
//		}else {
//			encoding = request.getCharacterEncoding();
//		}
//		
//		response.getOutputStream().write(resMsg.getBytes(encoding));

		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("token", token);
		
		return "";
	}
}
