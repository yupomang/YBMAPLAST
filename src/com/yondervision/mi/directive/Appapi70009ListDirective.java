package com.yondervision.mi.directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yondervision.mi.directive.bean.Mi701NewsBean;
import com.yondervision.mi.directive.freemarker.DirectiveUtils;
import com.yondervision.mi.dto.Mi701;
import com.yondervision.mi.form.AppApi70009Form;
import com.yondervision.mi.result.AppApi70009Result;
import com.yondervision.mi.result.ViewItemBean;
import com.yondervision.mi.service.impl.AppApi700ServiceImpl;
import com.yondervision.mi.util.SpringContextUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.WrappingTemplateModel;

public class Appapi70009ListDirective implements TemplateDirectiveModel {

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String centerId = DirectiveUtils.getString("centerId", params);
		String parentViewItemId = DirectiveUtils.getString("parentViewItemId", params);
		String buzType = DirectiveUtils.getString("buzType", params);
		String curChildViewItemId = DirectiveUtils.getString("curChildViewItemId", params);
		String pagenum = DirectiveUtils.getString("pagenum", params);
		String pagerows = DirectiveUtils.getString("pagerows", params);
		String keyword = DirectiveUtils.getString("keyword", params);
		String channel = DirectiveUtils.getString("channel", params);
		
		System.out.println("centerId=="+centerId);
		System.out.println("parentViewItemId=="+parentViewItemId);
		System.out.println("buzType=="+buzType);
		System.out.println("curChildViewItemId=="+curChildViewItemId);
		System.out.println("pagenum=="+pagenum);
		System.out.println("pagerows=="+pagerows);
		System.out.println("keyword=="+keyword);
		System.out.println("channel=="+channel);
		
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(params);
		AppApi70009Result result = new AppApi70009Result();
		List<Mi701NewsBean> appapi70009Resultlist = new ArrayList<Mi701NewsBean>();
		List<Mi701> appapi70009list = new ArrayList<Mi701>();
		List<ViewItemBean> childViewItemList = new ArrayList<ViewItemBean>();
		
		AppApi70009Form form = new AppApi70009Form();
		form.setCenterId(centerId);
		form.setParentViewItemId(parentViewItemId);
		form.setBuzType(buzType);
		form.setCurChildViewItemId(curChildViewItemId);
		form.setPagenum(pagenum);
		form.setPagerows(pagerows);
		form.setKeyword(keyword);
		
		try {
			AppApi700ServiceImpl appApi700ServiceImpl = (AppApi700ServiceImpl)SpringContextUtil.getBean("appApi700ServiceImpl");
			result = appApi700ServiceImpl.appapi70009(form,"");
			appapi70009list = result.getNews().getNewsList();
			childViewItemList = result.getChildViewItemList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i = 0 ; i < appapi70009list.size(); i ++){
			Mi701NewsBean bean = new Mi701NewsBean();
			Mi701 mi701 = appapi70009list.get(i);
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
			appapi70009Resultlist.add(bean);
		}
		paramWrap.put("appapi70009_list", WrappingTemplateModel.getDefaultObjectWrapper().wrap(appapi70009Resultlist));
		paramWrap.put("appapi70009_childList", WrappingTemplateModel.getDefaultObjectWrapper().wrap(childViewItemList));
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		
		body.render(env.getOut());
	}

}
