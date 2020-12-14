package com.yondervision.mi.directive;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.filter.PermissionEncodingFilter;
import com.yondervision.mi.directive.freemarker.DirectiveUtils;
import com.yondervision.mi.dto.Mi120;
import com.yondervision.mi.dto.Mi121;
import com.yondervision.mi.form.AppApi41101Form;
import com.yondervision.mi.form.AppApi70001Form;
import com.yondervision.mi.result.AppApi41101Result01;
import com.yondervision.mi.result.AppApi41101Result02;
import com.yondervision.mi.result.AppApi70001Result;
import com.yondervision.mi.service.impl.AppApi411ServiceImpl;
import com.yondervision.mi.service.impl.AppApi700ServiceImpl;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.SpringContextUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.WrappingTemplateModel;

public class Appapi41101ListDirective implements TemplateDirectiveModel {
	
	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		String centerId = DirectiveUtils.getString("centerId", params);
		String buzType = DirectiveUtils.getString("buzType", params);
		String channel = DirectiveUtils.getString("channel", params);
		String animatecode = DirectiveUtils.getString("animatecode", params);
		String deviceType = DirectiveUtils.getString("deviceType", params);
		
		System.out.println("centerId=="+centerId);
		System.out.println("buzType=="+buzType);
		System.out.println("animatecode=="+animatecode);
		System.out.println("channel=="+channel);
		System.out.println("deviceType=="+deviceType);
		
		AppApi41101Form form = new AppApi41101Form();
		form.setCenterId(centerId);
		form.setBuzType(buzType);
		form.setChannel(channel);
		form.setAnimatecode(animatecode);
		form.setDeviceType(deviceType);
		
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(params);
		
		HttpServletRequest request = (HttpServletRequest)PermissionEncodingFilter.threadRequestLocal.get();
		AppApi411ServiceImpl appApi411ServiceImpl = (AppApi411ServiceImpl)SpringContextUtil.getBean("appApi411ServiceImpl");
		
		List<AppApi41101Result02> result02 = new ArrayList<AppApi41101Result02>();
		try {
			List<Mi120> listMi120 = appApi411ServiceImpl.appapi41101(form);
			if(listMi120==null||listMi120.size()==0){			
				throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"动画图片信息");
			}
			AppApi41101Result01 result01 = new AppApi41101Result01();
			result01.setIntervaltime(listMi120.get(0).getIntervaltime());
			result01.setLooptype(listMi120.get(0).getLooptype());
			
			List<Mi121> listMi121 = appApi411ServiceImpl.appapi41102(listMi120.get(0));
			if(listMi121==null||listMi121.size()==0){			
				throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"动画图片明细信息");
			}
			
			
			for(int i=0;i<listMi121.size();i++){
				AppApi41101Result02 res = new AppApi41101Result02();
				res.setXh(listMi121.get(i).getXh());
				
				String url = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)+CommonUtil.getDownloadFileUrl(
						"pushdhtp", form.getCenterId()+File.separator+listMi121.get(i).getImgpath(), true);
				res.setImgpath(url);
				res.setDisplaydirection(listMi121.get(i).getDisplaydirection());
				res.setContentlink(listMi121.get(i).getContentlink());
				result02.add(res);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		paramWrap.put("appapi41101_list", WrappingTemplateModel.getDefaultObjectWrapper().wrap(result02));
		@SuppressWarnings("unused")
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		
		body.render(env.getOut());
	}

}
