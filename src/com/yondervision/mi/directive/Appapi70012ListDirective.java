package com.yondervision.mi.directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yondervision.mi.common.filter.PermissionEncodingFilter;
import com.yondervision.mi.directive.bean.Mi701NewsBean;
import com.yondervision.mi.directive.freemarker.DirectiveUtils;
import com.yondervision.mi.dto.Mi701;
import com.yondervision.mi.form.AppApi70012Form;
import com.yondervision.mi.result.NewsBean;
import com.yondervision.mi.service.impl.AppApi700ServiceImpl;
import com.yondervision.mi.util.SpringContextUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.WrappingTemplateModel;

public class Appapi70012ListDirective implements TemplateDirectiveModel {


	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		String centerId = DirectiveUtils.getString("centerId", params);
		String classification = DirectiveUtils.getString("classification", params);
		String buzType = DirectiveUtils.getString("buzType", params);
		String channel = DirectiveUtils.getString("channel", params);
		String pagerows = DirectiveUtils.getString("pagerows", params);
		String pagenum = DirectiveUtils.getString("pagenum", params);
		String keyword = DirectiveUtils.getString("keyword", params);

		System.out.println("centerId=="+centerId);
		System.out.println("classification=="+classification);
		System.out.println("buzType=="+buzType);
		System.out.println("channel=="+channel);
		System.out.println("pagerows=="+pagerows);
		System.out.println("pagenum=="+pagenum);
		System.out.println("keyword=="+keyword);
		
		List<Mi701NewsBean> resultlist = new ArrayList<Mi701NewsBean>();
		
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(params);
		
		AppApi70012Form form = new AppApi70012Form();
		form.setCenterId(centerId);
		form.setClassification(classification);
		form.setBuzType(buzType);
		form.setChannel(channel);
		form.setPagerows(pagerows);
		form.setPagenum(pagenum);
		form.setKeyword(keyword);
		
		HttpServletRequest request = (HttpServletRequest)PermissionEncodingFilter.threadRequestLocal.get();
		
		NewsBean result = new NewsBean();
		try {
			AppApi700ServiceImpl appApi700ServiceImpl = (AppApi700ServiceImpl)SpringContextUtil.getBean("appApi700ServiceImpl");
			result = appApi700ServiceImpl.appapi70012(form, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Mi701> mi701List = result.getNewsList();
		for(int i = 0 ; i < mi701List.size(); i ++){
			Mi701NewsBean bean = new Mi701NewsBean();
			Mi701 mi701 = mi701List.get(i);
			bean.setSeqno(mi701.getSeqno().toString());
			bean.setCenterid(mi701.getCenterid());
			bean.setClassification(mi701.getClassification());
			bean.setTitle(mi701.getTitle());
			bean.setIntroduction(mi701.getIntroduction());
			bean.setReleasetime(mi701.getReleasetime());
			bean.setImage(mi701.getImage());
			bean.setValidflag(mi701.getValidflag());
			bean.setDatemodified(mi701.getDatemodified());
			bean.setDatecreated(mi701.getDatecreated());
			bean.setLoginid(mi701.getLoginid());
			bean.setFreeuse1(mi701.getFreeuse1());
			bean.setFreeuse2(mi701.getFreeuse2());
			bean.setFreeuse3(mi701.getFreeuse3());
			bean.setFreeuse4(mi701.getFreeuse4());
			bean.setFreeuse5(mi701.getFreeuse5());
			bean.setFreeuse6(mi701.getFreeuse6());
			bean.setNewspaperforum(mi701.getNewspaperforum());
			bean.setNewspapercolumns(mi701.getNewspapercolumns());
			bean.setCitedtitle(mi701.getCitedtitle());
			bean.setSubtopics(mi701.getSubtopics());
			bean.setSource(mi701.getSource());
			bean.setBlurbs(mi701.getBlurbs());
			bean.setPraisecounts(mi701.getPraisecounts());
			bean.setDoctype(mi701.getDoctype());
			bean.setDoclink(mi701.getDoclink());
			bean.setDocfilename(mi701.getDocfilename());
			bean.setDocauthor(mi701.getDocauthor());
			bean.setDoccreatetime(mi701.getDoccreatetime());
			bean.setDockeyword(mi701.getDockeyword());
			bean.setFreeuse7(mi701.getFreeuse7());
			bean.setFreeuse8(mi701.getFreeuse8());
			bean.setFreeuse9(mi701.getFreeuse9());
			bean.setFreeuse10(mi701.getFreeuse10());
			bean.setFreeuse11(mi701.getFreeuse11());
			bean.setFreeuse12(mi701.getFreeuse12());
			resultlist.add(bean);
		}
		
		
		paramWrap.put("appapi70012_list", WrappingTemplateModel.getDefaultObjectWrapper().wrap(resultlist));
		
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		
		body.render(env.getOut());
	}

}
