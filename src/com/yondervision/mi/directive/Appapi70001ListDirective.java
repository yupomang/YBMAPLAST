package com.yondervision.mi.directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yondervision.mi.common.filter.PermissionEncodingFilter;
import com.yondervision.mi.directive.freemarker.DirectiveUtils;
import com.yondervision.mi.form.AppApi70001Form;
import com.yondervision.mi.result.AppApi70001Result;
import com.yondervision.mi.service.impl.AppApi700ServiceImpl;
import com.yondervision.mi.util.SpringContextUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.WrappingTemplateModel;

public class Appapi70001ListDirective implements TemplateDirectiveModel {
	
	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		String centerId = DirectiveUtils.getString("centerId", params);
		String classification = DirectiveUtils.getString("classification", params);
		String buzType = DirectiveUtils.getString("buzType", params);
		String pagenum = DirectiveUtils.getString("pagenum", params);
		String pagerows = DirectiveUtils.getString("pagerows", params);
		String keyword = DirectiveUtils.getString("keyword", params);
		String channel = DirectiveUtils.getString("channel", params);
		
		System.out.println("centerId=="+centerId);
		System.out.println("classification=="+classification);
		System.out.println("buzType=="+buzType);
		System.out.println("pagenum=="+pagenum);
		System.out.println("pagerows=="+pagerows);
		System.out.println("keyword=="+keyword);
		System.out.println("channel=="+channel);
		
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(params);
		List<AppApi70001Result> resultList = new ArrayList<AppApi70001Result>();
		
		AppApi70001Form form = new AppApi70001Form();
		form.setUserId("");
		form.setCenterId(centerId);
		form.setClassification(classification);
		form.setBuzType(buzType);
		form.setPagenum(pagenum);
		form.setPagerows(pagerows);
		form.setKeyword(keyword);
		
		 HttpServletRequest request = (HttpServletRequest)PermissionEncodingFilter.threadRequestLocal.get();
		 
		try {
			AppApi700ServiceImpl appApi700ServiceImpl = (AppApi700ServiceImpl)SpringContextUtil.getBean("appApi700ServiceImpl");
			resultList = appApi700ServiceImpl.appapi70001(form, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		paramWrap.put("appapi70001_list", WrappingTemplateModel.getDefaultObjectWrapper().wrap(resultList));
		@SuppressWarnings("unused")
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		
		body.render(env.getOut());
	}

}
