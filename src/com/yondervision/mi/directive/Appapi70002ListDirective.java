package com.yondervision.mi.directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.filter.PermissionEncodingFilter;
import com.yondervision.mi.directive.freemarker.DirectiveUtils;
import com.yondervision.mi.form.AppApi70002Form;
import com.yondervision.mi.result.AppApi70002Result;
import com.yondervision.mi.service.AppApi700Service;
import com.yondervision.mi.service.impl.AppApi700ServiceImpl;
import com.yondervision.mi.util.SpringContextUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.WrappingTemplateModel;

public class Appapi70002ListDirective implements TemplateDirectiveModel {

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		String centerId = DirectiveUtils.getString("centerId", params);
		String titleId = DirectiveUtils.getString("titleId", params);
		String buzType = DirectiveUtils.getString("buzType", params);
		String channel = DirectiveUtils.getString("channel", params);

		System.out.println("centerId=="+centerId);
		System.out.println("titleId=="+titleId);
		System.out.println("buzType=="+buzType);
		System.out.println("channel=="+channel);
		
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(params);
		List<AppApi70002Result> resultList = new ArrayList<AppApi70002Result>();
		
		AppApi70002Form form = new AppApi70002Form();
		form.setCenterId(centerId);
		form.setTitleId(titleId);
		form.setBuzType(buzType);
		form.setChannel(channel);
		
		HttpServletRequest request = (HttpServletRequest)PermissionEncodingFilter.threadRequestLocal.get();
		 
		try {
			AppApi700ServiceImpl appApi700ServiceImpl = (AppApi700ServiceImpl)SpringContextUtil.getBean("appApi700ServiceImpl");
			resultList = appApi700ServiceImpl.appapi70002(form, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		paramWrap.put("appapi70002_list", WrappingTemplateModel.getDefaultObjectWrapper().wrap(resultList));
		@SuppressWarnings("unused")
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		
		body.render(env.getOut());
	}

}
