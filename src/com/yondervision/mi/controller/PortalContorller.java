package com.yondervision.mi.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.CMi007;
import com.yondervision.mi.dto.CMi011;
import com.yondervision.mi.dto.CMi012;
import com.yondervision.mi.dto.CMi107;
import com.yondervision.mi.dto.Mi001;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi009;
import com.yondervision.mi.dto.Mi010;
import com.yondervision.mi.dto.Mi106;
import com.yondervision.mi.dto.Mi107;
import com.yondervision.mi.result.PtlApi002PageQueryResult;
import com.yondervision.mi.result.PtlApi20000ResultAttr;
import com.yondervision.mi.result.PtlApiTreeResult;
import com.yondervision.mi.service.CodeListApi001Service;
import com.yondervision.mi.service.ExcelApi001Service;
import com.yondervision.mi.service.PtlApi002Service;
import com.yondervision.mi.service.impl.CodeListApi001ServiceImpl;
import com.yondervision.mi.service.impl.WebApi302ServiceImpl;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: PortalContorller 
* @Description: 门户程序controller
* @author gongqi
* @date 2013-10-04  
* 
*/

@Controller
public class PortalContorller {
	
	@Autowired
	private PtlApi002Service ptlApi002Service = null;
	public void setPtlApi002Service(PtlApi002Service ptlApi002Service) {
		this.ptlApi002Service = ptlApi002Service;
	}
	
	@Autowired
	private ExcelApi001Service excelApi001Service = null;
	public void setExcelApi001Service(ExcelApi001Service excelApi001Service) {
		this.excelApi001Service = excelApi001Service;
	}
	
	@Autowired
	private CodeListApi001Service codeListApi001Service = null;
	public void setCodeListApi001Service(CodeListApi001Service codeListApi001Service) {
		this.codeListApi001Service = codeListApi001Service;
	}

	@RequestMapping("/ptl40006.html")
	public String ptl40006(ModelMap modelMap) throws Exception {
		return "ptl/ptl40006";
	}
	
	@RequestMapping("/ptl40006Qry.json")
	public String ptl40006Qry(String pid, String centerid, ModelMap modelMap) throws Exception{//码表管理 树  
		Logger log = LoggerUtil.getLogger();

		String businName = "码表树形菜单显示";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText("pid="+pid+";centerid="+centerid));
		
	    JSONArray ary = new JSONArray();
	    List<Mi007> list = ptlApi002Service.getCodeListByPid(pid, centerid) ;
	    Mi007 mi007 = new Mi007();
	    if ("000000000".equals(pid) && "000000000".equals(centerid)) {
    	    for(int i = 0; i < list.size(); i++){
    	    	mi007 = list.get(i);
    			JSONObject obj=new JSONObject();
    			obj.put("id", mi007.getDicid());
    			obj.put("text", mi007.getItemval());
    			//根据是否含有子项信息，设置state属性
    			int counts = ptlApi002Service.getChildCounts(mi007.getCenterid(), mi007.getDicid());
    			if (0 == counts) {
    				obj.put("state", "open");
    			}else{
    				obj.put("state", "closed");
    			}
    			
    	    	PtlApi20000ResultAttr attributes = new PtlApi20000ResultAttr();
    			attributes.setCenterid(mi007.getCenterid());
    			attributes.setCentername(ptlApi002Service.getCenterName(mi007.getCenterid()));
    			attributes.setDicid(mi007.getDicid());
    			attributes.setItemid(mi007.getItemid());
    			attributes.setItemval(mi007.getItemval());
    			attributes.setUpdicid(mi007.getUpdicid());
    			attributes.setUpdicname("000000000-码表管理");

    	    	obj.put("attributes", attributes);
    			ary.add(obj); 
    	    } 
	    }else {
    	    for(int i = 0; i < list.size(); i++){
    	    	mi007 = list.get(i);
    			JSONObject obj=new JSONObject();
    			obj.put("id", mi007.getDicid());
    			obj.put("text", mi007.getItemval());			 
    			//根据是否含有子项信息，设置state属性
    			int counts = ptlApi002Service.getChildCounts(mi007.getCenterid(), mi007.getDicid());
    			if (0 == counts) {
    				obj.put("state", "open");
    			}else{
    				obj.put("state", "closed");
    			}
    			
    			PtlApi20000ResultAttr attributes = new PtlApi20000ResultAttr();
    			attributes = ptlApi002Service.setNodeAttributes(mi007);
    	    	
    			obj.put("attributes", attributes);
    			ary.add(obj); 
    	    } 
	    }

	    modelMap.put("ary", ary);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
    	
		return "ptl/ptl40006Qry";
	}
	
	
	

	/**
	 * 码表添加
	 * @param form
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ptl40006Add.json")
	public String ptl40006Add(CMi007 form , ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();

		String businName = "码表添加";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		PtlApiTreeResult treeResult = new PtlApiTreeResult();
		treeResult = ptlApi002Service.addMulDicReturnObj(form);
		
		modelMap.put("addTreeData", treeResult);
		modelMap.put("rows", treeResult);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "ptl/ptl40006";
	}
	
	/**
	 * 码表删除
	 * @param form
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ptl40006Del.json")
	public String ptl40006Del(CMi007 form , ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();

		String businName = "码表删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		ptlApi002Service.delMulDic(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "ptl/ptl40006";
	}
	
	/**
	 * 码表修改
	 * @param form
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ptl40006Mod.json")
	public String ptl40006Mod(CMi007 form , ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();

		String businName = "码表修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		ptlApi002Service.updMulDic(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "ptl/ptl40006";
	}
	
	/**
	 * 码表查询
	 * @param form
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ptl40006Query.json")
	public String ptl40006Query(String pid, String centerid, ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();

		String businName = "码表查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(DEBUG.SHOW_PARAM.getLogText("pid="+pid+";centerid="+centerid)));
		
		List<Mi007> list = new ArrayList<Mi007>();
		list = ptlApi002Service.getCodeListByPid(pid, centerid) ;
		
		modelMap.put("rows", list);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));

		return "ptl/ptl40006";
	}
	
	@RequestMapping("/ptl40007.html")
	public String ptl40007(ModelMap modelMap) throws Exception {
		modelMap.put("clist", ptlApi002Service.getCenterListJson().toString());		
		return "ptl/ptl40007";
	}
	
	// 通讯表增加记录
	@RequestMapping("/ptl40007Add.json")
	public String ptl40007Add(CMi011 form, ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();

		String businName = "通讯表信息增加";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		ptlApi002Service.addMessage(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "ptl/ptl40007";
	}
	
	// 通讯表删除记录
	@RequestMapping("/ptl40007Del.json")
	public String ptl40007Del(CMi011 form, ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();

		String businName = "通讯表删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		ptlApi002Service.delMessage(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "ptl/ptl40007";
	}
	
	// 通讯表修改记录
	@RequestMapping("/ptl40007Mod.json")
	public String ptl40007Mod(CMi011 form, ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();

		String businName = "通讯表修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		ptlApi002Service.updMessage(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "ptl/ptl40007";
	}
	
	// 通讯表记录查询
	@RequestMapping("/ptl40007Query.json")
	public String ptl40007Query(CMi011 form, ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();
		
		String businName = "通讯表信息查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		List <CMi011> list= new ArrayList<CMi011>();	
		list = ptlApi002Service.queryMessage(form);	
		
		modelMap.put("rows", list);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "ptl/ptl40007";
	}

	// 日志代码表维护页面显示
	@RequestMapping("/ptl40008.html")
	public String ptl40008(ModelMap modelMap) throws Exception {
		return "ptl/ptl40008";
	}
	
	// 日志代码表增加记录
	@RequestMapping("/ptl40008Add.json")
	public String ptl40008Add(Mi009 form, ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();

		String businName = "日志代码信息增加";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		ptlApi002Service.addLog(form);
    	
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "ptl/ptl40008";
	}
		
	// 日志代码表删除记录
	@RequestMapping("/ptl40008Del.json")
	public String ptl40008Del(Mi009 form, ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();

		String businName = "日志代码信息删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		ptlApi002Service.delLog(form);
    	
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "ptl/ptl40008";
	}

	// 日志代码表修改记录
	@RequestMapping("/ptl40008Mod.json")
	public String ptl40008Mod(Mi009 form, ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();

		String businName = "日志代码信息修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		ptlApi002Service.updLog(form);
    	
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "ptl/ptl40008";
	}
	
	// 日志代码表查询记录（全部检索+模糊查询）
	@RequestMapping("/ptl40008Qry.json")
	public String ptl40008Qry(Mi009 form, ModelMap modelMap,Integer page,Integer rows) throws Exception {

		Logger log = LoggerUtil.getLogger();

		String businName = "日志代码信息查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.info(LOG.SELF_LOG.getLogText("contrller page="+page+",rows="+rows));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));

   		JSONObject obj= ptlApi002Service.queryLog(form,page,rows);
   		for(Object key:obj.keySet()){
   			modelMap.put(key.toString(), obj.get(key));
   		} 

		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "ptl/ptl40008";
	}
	
	//日志代码表批量导出数据到excel文件
	@RequestMapping("/mi009ToExcel.do")
	public void mi009ToExcel(HttpServletRequest request, HttpServletResponse response, String expotrTableName, String titles,
			String fileName, ModelMap modelMap){
		Logger log=LoggerUtil.getLogger();
		
		String businName = "日志代码表批量导出数据到excel文件";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText("expotrTableName:"+expotrTableName+";titles:"+titles+";fileName:"+fileName));
		try{
			// 确定要查询表的查询条件
			List<Mi009> list = ptlApi002Service.queryLogCodeAll();
			
			// 直接往response的输出流中写excel
			OutputStream outputStream = response.getOutputStream();
			//清空输出流
			response.reset();
			
			// 下载格式设置、定义输出类型
			response.setContentType("APPLICATION/OCTET-STREAM");
			//设置响应头和下载保存的文件名   
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			
			excelApi001Service.generalExcelFileInOutputStream(outputStream, list, expotrTableName, titles);
			outputStream.close();

	        //这一行非常关键，否则在实际中有可能出现莫名其妙的问题！！！
		    response.flushBuffer();//强行将响应缓存中的内容发送到目的地    
		}catch(Exception e){
			log.error(e);
	    	throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(), e.getMessage());
		}
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
			
	} 
	
	// 错误代码表维护界面显示
	@RequestMapping("/ptl40009.html")
	public String ptl40009(ModelMap modelMap) throws Exception {
		return "ptl/ptl40009";
	}
	
	// 错误代码表增加记录
	@RequestMapping("/ptl40009Add.json")
	public String ptl40009Add(Mi010 form, ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();

		String businName = "错误代码信息增加";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		ptlApi002Service.addErrCode(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
    	
		return "ptl/ptl40009";
	}
	
	// 错误代码表删除记录
	@RequestMapping("/ptl40009Del.json")
	public String ptl40009Del(Mi010 form, ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();

		String businName = "错误代码信息删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		ptlApi002Service.delErrCode(form);
    	
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "ptl/ptl40009";
	}

	// 错误代码表修改记录
	@RequestMapping("/ptl40009Mod.json")
	public String ptl40009Mod(Mi010 form, ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();

		String businName = "错误代码信息修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		ptlApi002Service.updErrCode(form);
    	
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "ptl/ptl40009";
	}
	
	// 错误代码表查询记录（全部检索+模糊查询）
	@RequestMapping("/ptl40009Qry.json")
	public String ptl40009Qry(Mi010 form, ModelMap modelMap, Integer page, Integer rows) throws Exception {
		Logger log = LoggerUtil.getLogger();

		String businName = "错误代码信息查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.info(LOG.SELF_LOG.getLogText("contrller page="+page+",rows="+rows));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
   		JSONObject obj= ptlApi002Service.pageQueryErrCode(form, page, rows);
   		for(Object key:obj.keySet()){
   			modelMap.put(key.toString(), obj.get(key));
   		} 
   		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
    	
		return "ptl/ptl40008";
	}
	
	// 错误代码表批量导出数据到excel文件
	@RequestMapping("/mi010ToExcel.do")
	public void mi010ToExcel(HttpServletRequest request, HttpServletResponse response, String expotrTableName, String titles,
			String fileName, ModelMap modelMap) throws Exception {
		Logger log=LoggerUtil.getLogger();
		String businName = "错误代码表批量导出数据到excel文件";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText("expotrTableName:"+expotrTableName+";titles:"+titles+";fileName:"+fileName));

		// 确定要查询表的查询条件
		List<Mi010> list = ptlApi002Service.queryErrCodeAll();
		
		// 直接往response的输出流中写excel
		OutputStream outputStream = response.getOutputStream();
		//清空输出流
		response.reset();
		
		// 下载格式设置、定义输出类型
		response.setContentType("APPLICATION/OCTET-STREAM");
		//设置响应头和下载保存的文件名   
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		
		excelApi001Service.generalExcelFileInOutputStream(outputStream, list, expotrTableName, titles);
		outputStream.close();

        //这一行非常关键，否则在实际中有可能出现莫名其妙的问题！！！
	    response.flushBuffer();//强行将响应缓存中的内容发送到目的地        
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));

	}

	@RequestMapping("/ptl40010.html")
	public String ptl40010(ModelMap modelMap) throws Exception {
		return "ptl/ptl40010";
	}
	
	@RequestMapping("/clearCache.do")
	public String clearCache(ModelMap modelMap) throws Exception {
		CodeListApi001ServiceImpl.clearCodeMap();
		LoggerUtil.clearLogTextMap();
		TransRuntimeErrorException.clearErrTxtMap();
		WebApi302ServiceImpl.clearCenterTelMap();
		return "ptl/ptl40010";
	}
	
	@RequestMapping("/ptl40012.html")
	public String ptl40012(ModelMap modelMap) throws Exception {			
		return "ptl/ptl40012";
	}

	@RequestMapping("/ptl40012Get.json")
	public String ptl40012Get(ModelMap modelMap) throws Exception {	
		modelMap.put("clist", ptlApi002Service.getCenterListJson().toString());	
		return "";
	}
	
	
	// 推送消息通讯参数配置表增加记录
	@RequestMapping("/ptl40012Add.json")
	public String ptl40012Add(CMi012 form, ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();

		String businName = "推送消息通讯参数配置信息增加";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		ptlApi002Service.addSendMessageParam(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "ptl/ptl40012";
	}
	
	// 推送消息通讯参数配置表删除记录
	@RequestMapping("/ptl40012Del.json")
	public String ptl40012Del(CMi012 form, ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();

		String businName = "推送消息通讯参数配置表删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		ptlApi002Service.delSendMessageParam(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "ptl/ptl40012";
	}
	
	// 推送消息通讯参数配置表修改记录
	@RequestMapping("/ptl40012Mod.json")
	public String ptl40012Mod(CMi012 form, ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();

		String businName = "推送消息通讯参数配置表修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		ptlApi002Service.updSendMessageParam(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "ptl/ptl40012";
	}
	
	// 推送消息通讯参数配置表记录查询
	@RequestMapping("/ptl40012Query.json")
	public String ptl40012Query(CMi012 form, ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();
		
		String businName = "推送消息通讯参数配置表信息查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		List <CMi012> list= new ArrayList<CMi012>();	
		list = ptlApi002Service.querySendMessageParam(form);	
		
		modelMap.put("rows", list);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "ptl/ptl40012";
	}
	
	// 根据中心和设备获取版本号
	@RequestMapping("/ptl40013.json")
	public String ptl40013(String centerid,String devtype,ModelMap modelMap) throws Exception {
		
		
		UserContext user = UserContext.getInstance();
		//获取中心列表
		List<Mi001> centerlist = codeListApi001Service.getCityMessage();
		// 获取app对应中心的版本号列表
		JSONObject centerVernoJsonObj = codeListApi001Service.getVersionnoJson();
		// 获取app业务类型列表
		JSONArray consulttypejsonarray = codeListApi001Service.getCodeListJson(user
				.getCenterid(), "apptranstype");
		// 获取mi中心前置业务类型列表
		JSONArray miconsulttypejsonarray = codeListApi001Service.getCodeListJson(user
				.getCenterid(), "mitranstype");
		// 获取app设备区分列表
		JSONArray devicetypejsonarray = this.codeListApi001Service.getCodeListJson(user
				.getCenterid(), "devicetype");
		modelMap.put("centerlist", centerlist);
		modelMap.put("versionnolist", centerVernoJsonObj);
		modelMap.put("consulttypelist", consulttypejsonarray);
		modelMap.put("miconsulttypelist", miconsulttypejsonarray);
		modelMap.put("devicetypelist", devicetypejsonarray);
		return "";
	}
	
	// 查询对应某条日志记录的明细内容
	@RequestMapping("/ptl40013Detail.html")
	public String ptl40013Detail(String seqno, ModelMap modelMap) throws Exception {

		Mi107 mi107 = ptlApi002Service.queryBusiLog(seqno);
		// 对返回值进行空值处理，避免页面toString()报错
		if (CommonUtil.isEmpty(mi107.getFreeuse3())) {
			mi107.setFreeuse3("");
		}
		if(CommonUtil.isEmpty(mi107.getFreeuse2())){
			mi107.setFreeuse2("");
		}
		modelMap.put("url", mi107.getFreeuse3());
		modelMap.put("describe", mi107.getFreeuse2());
		return "ptl/ptl40013Detail";
	}
	
	// APP业务日志表记录查询（按条件进行分页查询）
	@RequestMapping("/ptl40013PageQry.json")
	public String ptl40013PageQry(CMi107 form, ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();
		
		String businName = "APP业务日志信息查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
			
		PtlApi002PageQueryResult result = ptlApi002Service.pageQueryAppBusiLog(form);	
		
		modelMap.put("total", result.getTotal());
		modelMap.put("pageSize", result.getPageSize());
		modelMap.put("pageNumber", result.getPageNumber());
		modelMap.put("rows", result.getMi107Rows());
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "ptl/ptl40013";
	}
	
	// APP业务日志表记录删除
	@RequestMapping("/ptl40013Del.json")
	public String ptl40013Del(String seqno, ModelMap modelMap) throws Exception {		
		Logger log = LoggerUtil.getLogger();

		String businName = "APP业务日志信息删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(seqno));
		
		ptlApi002Service.delBusiLog(seqno);
    	
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "ptl/ptl40013";
	}
	
	//APP业务日志批量导出数据到excel文件，此功能暂注掉
	@RequestMapping("/mi107ToExcel.do")
	public void mi107ToExcel(HttpServletRequest request, HttpServletResponse response, String expotrTableName, String titles,
			String fileName, ModelMap modelMap){
		Logger log=LoggerUtil.getLogger();
		
		String businName = "APP业务日志批量导出数据到excel文件";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText("expotrTableName:"+expotrTableName+";titles:"+titles+";fileName:"+fileName));
		try{
			// 确定要查询表的查询条件
			List<Mi107> list = ptlApi002Service.queryAppBusiLogAll();
			
			// 直接往response的输出流中写excel
			OutputStream outputStream = response.getOutputStream();
			//清空输出流
			response.reset();
			
			// 下载格式设置、定义输出类型
			response.setContentType("APPLICATION/OCTET-STREAM");
			//设置响应头和下载保存的文件名   
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			
			excelApi001Service.generalExcelFileInOutputStream(outputStream, list, expotrTableName, titles);
			outputStream.close();

	        //这一行非常关键，否则在实际中有可能出现莫名其妙的问题！！！
		    response.flushBuffer();//强行将响应缓存中的内容发送到目的地    
		}catch(Exception e){
			log.error(e);
	    	throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(), e.getMessage());
		}
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
			
	} 
	
	// MI前置业务日志表记录查询（按条件进行分页查询）
	@RequestMapping("/ptl4001301PageQry.json")
	public String ptl4001301PageQry(CMi107 form, ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();
		
		String businName = "MI中心前置业务日志信息查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
			
		PtlApi002PageQueryResult result = ptlApi002Service.pageQueryMiBusiLog(form);	
		
		modelMap.put("total", result.getTotal());
		modelMap.put("pageSize", result.getPageSize());
		modelMap.put("pageNumber", result.getPageNumber());
		modelMap.put("rows", result.getCmi107Rows());
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "ptl/ptl40013";
	}
	
	// 查询对应某条日志记录的明细内容
	@RequestMapping("/ptl4001301Detail.html")
	public String ptl4001301Detail(String miseqno, ModelMap modelMap) throws Exception {

		Mi107 mi107 = ptlApi002Service.queryBusiLog(miseqno);
		// 对返回值进行空值处理，避免页面toString()报错
		if (CommonUtil.isEmpty(mi107.getFreeuse3())) {
			mi107.setFreeuse3("");
		}
		if(CommonUtil.isEmpty(mi107.getFreeuse2())){
			mi107.setFreeuse2("");
		}
		modelMap.put("url", mi107.getFreeuse3());
		modelMap.put("describe", mi107.getFreeuse2());
		return "ptl/ptl40013Detail";
	}
	
	// MI中心前置业务日志表记录删除
	@RequestMapping("/ptl4001301Del.json")
	public String ptl4001301Del(String miseqno, ModelMap modelMap) throws Exception {		
		Logger log = LoggerUtil.getLogger();

		String businName = "MI中心前置业务日志信息删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(miseqno));
		
		ptlApi002Service.delBusiLog(miseqno);
    	
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "ptl/ptl40013";
	}
	
	//MI中心前置业务日志批量导出数据到excel文件，此功能暂未使用
	@RequestMapping("/mi10701ToExcel.do")
	public void mi10701ToExcel(HttpServletRequest request, HttpServletResponse response, String miexpotrTableName, String mititles,
			String mifileName, ModelMap modelMap){
		Logger log=LoggerUtil.getLogger();
		
		String businName = "MI中心前置业务日志批量导出数据到excel文件";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText("miexpotrTableName:"+miexpotrTableName+";mititles:"+mititles+";mifileName:"+mifileName));
		try{
			// 确定要查询表的查询条件
			List<Mi107> list = ptlApi002Service.queryAppBusiLogAll();
			
			// 直接往response的输出流中写excel
			OutputStream outputStream = response.getOutputStream();
			//清空输出流
			response.reset();
			
			// 下载格式设置、定义输出类型
			response.setContentType("APPLICATION/OCTET-STREAM");
			//设置响应头和下载保存的文件名   
			response.setHeader("Content-Disposition", "attachment; mifilename=\"" + mifileName + "\"");
			
			excelApi001Service.generalExcelFileInOutputStream(outputStream, list, miexpotrTableName, mititles);
			outputStream.close();

	        //这一行非常关键，否则在实际中有可能出现莫名其妙的问题！！！
		    response.flushBuffer();//强行将响应缓存中的内容发送到目的地    
		}catch(Exception e){
			log.error(e);
	    	throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(), e.getMessage());
		}
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
			
	} 
	
	//根据中心和设备获取版本号
	@RequestMapping("/ptl40013Verno.json")
	public void ptl40013Verno(String centerid,String devtype,ModelMap modelMap){
		Logger log=LoggerUtil.getLogger();
		
		String businName = "根据中心和设备获取版本号";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		List<Mi106> ptl40013Verno = null;
		try {
			ptl40013Verno = codeListApi001Service.ptl40013Verno(centerid,devtype);
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", ptl40013Verno);
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
			
	} 
}
