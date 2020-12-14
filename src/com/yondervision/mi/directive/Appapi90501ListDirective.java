package com.yondervision.mi.directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.filter.PermissionEncodingFilter;
import com.yondervision.mi.common.message.SimpleHttpMessageUtil;
import com.yondervision.mi.directive.bean.YwznBean;
import com.yondervision.mi.directive.freemarker.DirectiveUtils;
import com.yondervision.mi.form.AppApi70001Form;
import com.yondervision.mi.form.AppApi90501Form;
import com.yondervision.mi.result.AppApi70001Result;
import com.yondervision.mi.service.AppApi700Service;
import com.yondervision.mi.util.JsonUtil;
import com.yondervision.mi.util.PropertiesReader;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.WrappingTemplateModel;

public class Appapi90501ListDirective implements TemplateDirectiveModel {
	
	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		String centerId = DirectiveUtils.getString("centerId", params);
		String channel = DirectiveUtils.getString("channel", params);
		String buzType = DirectiveUtils.getString("buzType", params);
		String strucid = DirectiveUtils.getString("strucid", params);
		
		System.out.println("centerId=="+centerId);
		System.out.println("channel=="+channel);
		System.out.println("buzType=="+buzType);
		System.out.println("strucid=="+strucid);
		
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(params);

		AppApi90501Form form  = new AppApi90501Form();
		form.setCenterId(centerId);
		form.setChannel(channel);
		form.setBuzType(buzType);
		form.setStrucid(strucid);
		
		List<YwznBean> curMList = new ArrayList<YwznBean>();
		HttpServletRequest request = (HttpServletRequest)PermissionEncodingFilter.threadRequestLocal.get();
		
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		StringBuffer url = new StringBuffer();
		url.append(PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,"zskurl").trim()+"/FindStruc4Out.action");
		
		url.append("?strucid="+form.getStrucid());
		request.setAttribute("httpFlg", "1");
		String rep = "";
		try {
			rep = msm.sendGet(url.toString(), request.getCharacterEncoding());
			
			System.out.println("知识库查询——查询知识结构信息:"+rep);
			HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
			if(0 == ((Double)hasMap.get("returnCode"))){
				JSONObject jsonObject = new JSONObject(rep);
				JSONArray arr = jsonObject.getJSONArray("data"); 
				// 创建一个JsonParser
				JsonParser parser = new JsonParser();
				// 通过JsonParser对象可以把json格式的字符串解析成一个JsonElement对象
				JsonElement el = parser.parse(arr.toString());
				JsonArray ja = el.getAsJsonArray();
				Gson gson = new GsonBuilder().create();
				Iterator<JsonElement> it = ja.iterator();
				List<YwznBean> mList = new ArrayList<YwznBean>();
				while (it.hasNext()) {
					JsonElement je = (JsonElement) it.next();
					YwznBean consult = gson.fromJson(je, YwznBean.class);
					mList.add(consult);
				}
				for(int i = 0; i < mList.size(); i++){
					if("2".equals(mList.get(i).getFloor())){
						curMList.add(mList.get(i));
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		curMList
		
		paramWrap.put("appapi90501_list", WrappingTemplateModel.getDefaultObjectWrapper().wrap(curMList));
		@SuppressWarnings("unused")
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		
		body.render(env.getOut());
	}

}
