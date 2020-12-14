package com.yondervision.mi.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.CMi005;
import com.yondervision.mi.dto.CMi014;
import com.yondervision.mi.dto.CMi055;
import com.yondervision.mi.dto.CMi056;
import com.yondervision.mi.dto.Mi005;
import com.yondervision.mi.dto.Mi055;
import com.yondervision.mi.dto.Mi056;
import com.yondervision.mi.result.WebApi00504_queryResult;
import com.yondervision.mi.result.WebApi01404_queryResult;
import com.yondervision.mi.result.WebApi05504_queryResult;
import com.yondervision.mi.result.WebApi05604_queryResult;
import com.yondervision.mi.service.WebApi055Service;
import com.yondervision.mi.util.CommonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 功能与菜单
 * @author lixu
 *
 */
@Controller
public class WebApi055Contorller {
	@Autowired
	private WebApi055Service webApi055ServiceImpl;
	
	
	
	//渠道接口
	@RequestMapping("/webapi05501.{ext}")
	public String webapi05501(CMi055 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "功能配置新增";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi055ServiceImpl.webapi05501(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page055/page05501";
	}

	@RequestMapping("/webapi05502.{ext}")
	public String webapi05502(CMi055 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "功能配置删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi055ServiceImpl.webapi05502(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page055/page05502";
	}
	
	@RequestMapping("/webapi05503.{ext}")
	public String webapi05503(String oldFuncid, CMi055 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "功能配置修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi055ServiceImpl.webapi05503(oldFuncid,form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page055/page05503";
	}
	@RequestMapping("/webapi05504.{ext}")
	public String webapi05504(CMi055 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "功能配置查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		WebApi05504_queryResult queryResult = webApi055ServiceImpl.webapi05504(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList055());
		log.info("总共查询到几条数据："+queryResult.getTotal());
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page055/page05504";
	}

	@RequestMapping("/webapi05505.{ext}")
	public String webapi05505(CMi055 form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "查询所有功能配置";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		
		List<Mi055> list = webApi055ServiceImpl.webapi05505(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", list);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page055/page05505";
	}
	
	
	
	//菜单配置
	@RequestMapping("/webapi05601.{ext}")
	public String webapi05601(CMi056 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "菜单配置新增";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi055ServiceImpl.webapi05601(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page056/page05601";
	}

	@RequestMapping("/webapi05602.{ext}")
	public String webapi05602(CMi056 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "菜单务配置删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi055ServiceImpl.webapi05602(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page056/page05602";
	}
	
	@RequestMapping("/webapi05603.{ext}")
	public String webapi05603(CMi056 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "菜单配置修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi055ServiceImpl.webapi05603(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page056/page05603";
	}
	
	@RequestMapping("/webapi05604.{ext}")
	public String webapi05604(CMi056 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "菜单配置查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		WebApi05604_queryResult queryResult = webApi055ServiceImpl.webapi05604(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList056());
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page056/page05604";
	}
	@RequestMapping("/webapi05605.{ext}")
	public String webapi05605(String datalist,ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "菜单保存排序";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(datalist));
		
	    JSONArray arr= JSONArray.fromObject(datalist);
	    webApi055ServiceImpl.webapi05605(arr);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page056/page05605";
	}

	@RequestMapping("/webapi05606.{ext}")
	public String webapi05606(ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "查询所有菜单配置";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		
		List<Mi056> list = webApi055ServiceImpl.webapi05606();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", list);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page056/page05606";
	}
	
	//子功能配置
	@RequestMapping("/webapi01401.{ext}")
	public String webapi01401(CMi014 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "子功能配置新增";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi055ServiceImpl.webapi01401(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page014/page01401";
	}

	@RequestMapping("/webapi01402.{ext}")
	public String webapi01402(CMi014 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "子功能配置删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi055ServiceImpl.webapi01402(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page014/page01402";
	}
	
	@RequestMapping("/webapi01403.{ext}")
	public String webapi01403(CMi014 form ,String oldFuncid, String oldSubname, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "子功能配置修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi055ServiceImpl.webapi01403(oldFuncid, oldSubname, form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page014/page01403";
	}
	@RequestMapping("/webapi01404.{ext}")
	public String webapi01404(CMi014 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "子功能配置查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		WebApi01404_queryResult queryResult = webApi055ServiceImpl.webapi01404(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList014());
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page014/page01404";
	}
	@RequestMapping("/webapi01405.{ext}")
	public String webapi01405(String datalist,ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "子功能保存排序";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(datalist));
		
	    JSONArray arr= JSONArray.fromObject(datalist);
	    webApi055ServiceImpl.webapi01405(arr);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page014/page01405";
	}
	
	
	
	//功能-菜单配置控制
	@RequestMapping("/webapi00501.{ext}")
	public String webapi00501(CMi005 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "功能菜单配置新增";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi055ServiceImpl.webapi00501(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page005/page00501";
	}

	@RequestMapping("/webapi00502.{ext}")
	public String webapi00502(CMi005 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "功能菜单配置删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi055ServiceImpl.webapi00502(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page005/page00502";
	}
	
	@RequestMapping("/webapi00503.{ext}")
	public String webapi00503(String oldCenterid, String oldFuncid, String oldCdid, CMi005 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "功能菜单配置修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi055ServiceImpl.webapi00503(oldCenterid,oldFuncid, oldCdid, form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page005/page00503";
	}
	
	
	
	@RequestMapping("/webapi00504.{ext}")
	public String webapi00504(CMi005 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "功能菜单配置查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		WebApi00504_queryResult queryResult = webApi055ServiceImpl.webapi00504(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList005());
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page005/page00504";
	}


	@RequestMapping("/webapi00505.{ext}")
	public String webapi00505(String parentfuncid ,String centerid, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "根据parentfuncid获取子功能-菜单";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(parentfuncid));
		
		JSONArray ary=new JSONArray(); 
	    List<Mi005> list= webApi055ServiceImpl.getMenuListByPid(parentfuncid,centerid) ;	
	    for(int i=0;i<list.size();i++){
				JSONObject obj=new JSONObject();
				obj.put("id", list.get(i).getFuncid());
				obj.put("funcname", list.get(i).getFuncname());	
				obj.put("image", list.get(i).getImage()==null?"":list.get(i).getImage());
				obj.put("funname", list.get(i).getFunname()==null?"":list.get(i).getFunname());
				obj.put("parentfuncid", list.get(i).getParentfuncid()==null?"":list.get(i).getParentfuncid());
				obj.put("cdid", list.get(i).getCdid()==null?"":list.get(i).getCdid());
				obj.put("orderid", list.get(i).getOrderid()==null?"":list.get(i).getOrderid());
				obj.put("centerid", list.get(i).getCenterid()==null?"":list.get(i).getCenterid());
				obj.put("freeuse4", list.get(i).getFreeuse4()==null?"":list.get(i).getFreeuse4());
				obj.put("state", "closed");
				obj.put("url", list.get(i).getUrl()==null?"":list.get(i).getUrl());
				ary.add(obj); 
	    } 
	    modelMap.put("ary", ary);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info("看看ary~~~~："+ary);
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page005/page00505";
	}
	
	@RequestMapping("/webapi00506.{ext}")
	public void webapi00506(String datalist ,ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "保存排序";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(datalist));
		
	    JSONArray arr= JSONArray.fromObject(datalist);
	    webApi055ServiceImpl.webapi00506(arr);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
	}
	
	
	
	
	
	public WebApi055Service getWebApi055ServiceImpl() {
		return webApi055ServiceImpl;
	}

	public void setWebApi055ServiceImpl(WebApi055Service webApi055ServiceImpl) {
		this.webApi055ServiceImpl = webApi055ServiceImpl;
	}
	
}
