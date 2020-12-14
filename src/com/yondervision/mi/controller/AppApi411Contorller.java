/**
 * 
 */
package com.yondervision.mi.controller;

import java.io.File;
import java.util.ArrayList;
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
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.Mi120;
import com.yondervision.mi.dto.Mi121;
import com.yondervision.mi.form.AppApi41101Form;
import com.yondervision.mi.result.AppApi41101Result01;
import com.yondervision.mi.result.AppApi41101Result02;
import com.yondervision.mi.service.AppApi411Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: AppApi411Contorller 
* @Description: APP动画图片获取
* @author Caozhongyan
* @date Nov 14, 2013 3:06:43 PM   
* 
*/ 
@Controller
public class AppApi411Contorller {
	@Autowired
	private AppApi411Service appApi411ServiceImpl = null;

	public AppApi411Service getAppApi411ServiceImpl() {
		return appApi411ServiceImpl;
	}
	public void setAppApi411ServiceImpl(AppApi411Service appApi411ServiceImpl) {
		this.appApi411ServiceImpl = appApi411ServiceImpl;
	}



	/**
	 * APP动画图片获取
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi41101.{ext}")
	public String appapi41101(AppApi41101Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{		
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("手机APPAPP动画图片获取");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getAnimatecode())){
			log.error(ERROR.PARAMS_NULL.getLogText("animatecode"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"动画编号");
		}
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
		List<AppApi41101Result02> result02 = new ArrayList<AppApi41101Result02>();
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
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result",result01);
		modelMap.put("detail",result02);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
	
}
