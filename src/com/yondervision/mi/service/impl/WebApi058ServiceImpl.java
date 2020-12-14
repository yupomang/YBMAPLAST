package com.yondervision.mi.service.impl;
import java.net.URLDecoder;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi058DAO;
import com.yondervision.mi.form.WebApi05801Form;
import com.yondervision.mi.service.WebApi058Service;
import com.yondervision.mi.util.CommonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WebApi058ServiceImpl implements WebApi058Service {
	protected final Logger log = LoggerUtil.getLogger();
	static NumberFormat num = null;
	static{
		num = NumberFormat.getPercentInstance(); 
		num.setMaximumIntegerDigits(3); 
		num.setMaximumFractionDigits(2); 
	}
    
	@Autowired
	private CMi058DAO cmi058Dao;
	@Autowired
	private CommonUtil commonUtil;
	

	public JSONObject webapi05803(WebApi05801Form form) throws Exception {
		checkParam(form);
		
		JSONObject obj = new JSONObject();
		obj.put("title", "柜面业务服务评价");
		JSONArray typeList = new JSONArray();
		JSONArray dataList = new JSONArray();
		//[{name=1, value=91}, {name=2, value=1}, {name=3, value=8}]
		List<HashMap> list = cmi058Dao.selectWebapi05803(form);
		
		if(list !=null && !list.isEmpty()){
			boolean flag = true;
			JSONObject obj21 = new JSONObject();
			typeList.add("满意");
			obj21.put("name", "满意");
			for(Map map:list){
				String type = map.get("name").toString().trim();
				int v = (Integer)map.get("value");
				if("1".equals(type)){//满意
					obj21.put("value", v);
					flag = false;
					break;
				}
			}	
			if(flag){
				obj21.put("value", 0);
			}	
			dataList.add(obj21);
			
			
			flag = true;
			JSONObject obj22 = new JSONObject();
			typeList.add("基本满意");
			obj22.put("name", "基本满意");
			for(Map map:list){
				String type = map.get("name").toString().trim();
				int v = (Integer)map.get("value");
				if("2".equals(type)){//基本满意
					obj22.put("value", v);
					flag = false;
					break;
				}
			}	
			if(flag){
				obj22.put("value", 0);
			}	
			dataList.add(obj22);
			
			
			flag = true;
			JSONObject obj23 = new JSONObject();
			typeList.add("不满意");
			obj23.put("name", "不满意");
			for(Map map:list){
				String type = map.get("name").toString().trim();
				int v = (Integer)map.get("value");
				if("3".equals(type)){//不满意
					obj23.put("value", v);
					flag = false;
					break;
				}
			}	
			if(flag){
				obj23.put("value", 0);
			}	
			dataList.add(obj23);
		}
		obj.put("typeList", typeList);
		obj.put("dataList", dataList);
		return obj;
	}


	/**
	 * 柜面业务服务评价-不满意原因分类
	 * @param form
	 * @throws Exception
	 */
	public JSONObject webapi05804(WebApi05801Form form) throws Exception{
		checkParam(form);
		JSONObject obj = new JSONObject();
		obj.put("title", "不满意原因分类");
		JSONArray typeList = new JSONArray();
		JSONArray dataList = new JSONArray();
		//[{name=1, value=91}, {name=2, value=1}, {name=3, value=8}]
		List<HashMap> list = cmi058Dao.selectWebapi05804(form);
		
		if(list !=null && !list.isEmpty()){
			boolean flag = true;
			JSONObject obj21 = new JSONObject();
			typeList.add("服务态度");
			obj21.put("name", "服务态度");
			for(Map map:list){
				String type = map.get("name").toString().trim();
				int v = (Integer)map.get("value");
				if("1".equals(type)){//服务态度
					obj21.put("value", v);
					flag = false;
					break;
				}
			}	
			if(flag){
				obj21.put("value", 0);
			}	
			dataList.add(obj21);
			
			
			flag = true;
			JSONObject obj22 = new JSONObject();
			typeList.add("业务政策");
			obj22.put("name", "业务政策");
			for(Map map:list){
				String type = map.get("name").toString().trim();
				int v = (Integer)map.get("value");
				if("2".equals(type)){//业务政策
					obj22.put("value", v);
					flag = false;
					break;
				}
			}	
			if(flag){
				obj22.put("value", 0);
			}	
			dataList.add(obj22);
			
			
			flag = true;
			JSONObject obj23 = new JSONObject();
			typeList.add("流程效率");
			obj23.put("name", "流程效率");
			for(Map map:list){
				String type = map.get("name").toString().trim();
				int v = (Integer)map.get("value");
				if("3".equals(type)){//流程效率
					obj23.put("value", v);
					flag = false;
					break;
				}
			}	
			if(flag){
				obj23.put("value", 0);
			}	
			dataList.add(obj23);
			
			flag = true;
			JSONObject obj24 = new JSONObject();
			typeList.add("咨询解答");
			obj24.put("name", "咨询解答");
			for(Map map:list){
				String type = map.get("name").toString().trim();
				int v = (Integer)map.get("value");
				if("3".equals(type)){//咨询解答
					obj24.put("value", v);
					flag = false;
					break;
				}
			}	
			if(flag){
				obj24.put("value", 0);
			}	
			dataList.add(obj24);
		}
		
		obj.put("typeList", typeList);
		obj.put("dataList", dataList);
		return obj;
	}
	
	/**
	 * 柜面业务服务评价-不满意原因分类-业务政策
	 * @param form
	 * @throws Exception
	 */
	public JSONObject webapi05805(WebApi05801Form form) throws Exception{
		checkParam(form);
		JSONObject obj = new JSONObject();
		JSONArray dataList1 = new JSONArray();
		JSONArray dataList2 = new JSONArray();
		/*
		 * [{type1=贷款, value=3, type2=A贷款}, {type1=贷款, value=2, type2=B贷款},
		{type1=贷款, value=1, type2=C贷款}, {type1=贷款, value=1, type2=D贷款}, 
		{type1=提取, value=2, type2=提取AA}, {type1=提取, value=1, type2=提取BB}]
		*/
		List<HashMap> list = cmi058Dao.selectWebapi05805(form);
		if(list !=null && !list.isEmpty()){
			for(Map map:list){
				JSONObject obj2 = new JSONObject();
				String type1 = null;
				String type2 = "未知业务";
				if(!CommonUtil.isEmpty(map.get("type1"))){
					type1 = map.get("type1").toString().trim();
				}
				if(!CommonUtil.isEmpty(map.get("type2"))){
					type2 = map.get("type2").toString().trim();
				}
				int v = (Integer)map.get("value");
				if("提取".equals(type1)){//提取
					obj2.put("name", type2);
					obj2.put("value", v);
					dataList1.add(obj2);
				}
				if("柜台还款".equals(type1)){//贷款
					obj2.put("name", type2);
					obj2.put("value", v);
					dataList2.add(obj2);
				}
			}
		}
		obj.put("dataList1", dataList1);
		obj.put("dataList2", dataList2);
		return obj;
		
	}
	
	
	/**
	 * 柜面业务服务评价-不满意原因分类-服务态度
	 * @param form
	 * @throws Exception
	 */
	public JSONObject webapi05806(WebApi05801Form form) throws Exception{
		checkParam(form);
		JSONObject obj = new JSONObject();
		JSONArray dataList = new JSONArray();
		/*
		 * [{name=安宁管理部, value=1}, {name=城北管理部, value=1}, {name=城东管理部, value=1}, 
		 * {name=城南管理部, value=1}, {name=城西管理部, value=2}, {name=呈贡管理部, value=4}, 
		 * {name=省直机关分中心, value=2}, {name=主城区管理部, value=14}]
		*/
		List<HashMap> list = cmi058Dao.selectWebapi05806(form);
		if(list !=null && !list.isEmpty()){
			for(Map map:list){
				JSONObject obj2 = new JSONObject();
				String name = map.get("name").toString().trim();
				int v = (Integer)map.get("value");
				obj2.put("name", name);
				obj2.put("value", v);
				dataList.add(obj2);
			}
		}
		obj.put("dataList", dataList);
		return obj;
		
	}
	
	/**
	 * 柜面业务服务评价-不满意原因分类-服务态度-柜员信息
	 * @param form
	 * @throws Exception
	 */
	public JSONObject webapi05807(WebApi05801Form form) throws Exception{
		checkParam(form);
		if(CommonUtil.isEmpty(form.getAgentinstmsg())){
			log.error(ERROR.PARAMS_NULL.getLogText("网点为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "网点为空");
		}
		form.setAgentinstmsg(URLDecoder.decode(form.getAgentinstmsg(), "UTF-8"));
		JSONObject obj = new JSONObject();
		JSONArray dataList = new JSONArray();
		/*
		 * [{countername=张大炮, transdate=2016-08-05, counternum=775556, transid=6864, tradetype=提取},
		 *  {countername=张三1, transdate=2014-01-27, counternum=00001, transid=15048, tradetype=提取}, 
		 *  {countername=张三2, transdate=2016-11-08, counternum=00002, transid=23475, tradetype=提取},
		 *  {countername=张三丰, transdate=2015-11-02, counternum=7687434, transid=7122, tradetype=提取}, 
		 *   {countername=张三6, transdate=2014-09-15, counternum=00006, transid=10738, tradetype=提取}, 
		 *   {countername=张三9, transdate=2014-03-28, counternum=00009, transid=10674, tradetype=提取},
		 *   {countername=张三10, transdate=2012-07-19, counternum=00010, transid=12882, tradetype=提取},
		 *   {countername=张三a, transdate=2011-12-08, counternum=00011, transid=7959, tradetype=提取}, 
		 *   {countername=个GRE, transdate=2010-12-08, counternum=43432, transid=3901, tradetype=提取},
		 *   {countername= 个梵蒂冈, transdate=2016-07-14, counternum=56546, transid=9723, tradetype=提取},
		 *   {countername=而突然, transdate=2012-07-23, counternum=756756767, transid=12926, tradetype=提取},
		 *   {countername=个梵热天, transdate=2011-04-13, counternum=345345346, transid=9364, tradetype=提取}, 
		 *   {countername=规范, transdate=2010-02-22, counternum=456456, transid=4561, tradetype=提取},
		 *   {countername=大大, transdate=2016-11-01, counternum=5555566, transid=14568, tradetype=提取}]
		*/
		List<HashMap> list = cmi058Dao.selectWebapi05807(form);
		if(list !=null && !list.isEmpty()){
			for(Map map:list){
				JSONObject obj2 = new JSONObject();
				String countername = map.get("countername").toString().trim();
				String transdate = map.get("transdate").toString().trim();
				String counternum = map.get("counternum").toString().trim();
				String transid = map.get("transid").toString().trim();
				String tradetype = map.get("tradetype").toString().trim();
				obj2.put("countername", countername);
				obj2.put("transdate", transdate);
				obj2.put("counternum", counternum);
				obj2.put("transid", transid);
				obj2.put("tradetype", tradetype);
				dataList.add(obj2);
			}
		}
		obj.put("dataList", dataList);
		return obj;
		
	}
	
	private void checkParam(WebApi05801Form form)throws Exception{
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心代码为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "中心代码为空");
		}
		if(CommonUtil.isEmpty(form.getStartdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("开始日期为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "开始日期为空");
		}
		if(CommonUtil.isEmpty(form.getEnddate())){
			log.error(ERROR.PARAMS_NULL.getLogText("结束日期为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "结束日期为空");
		}
	}
	
	
	public CMi058DAO getCmi058Dao() {
		return cmi058Dao;
	}
	public void setCmi058Dao(CMi058DAO cmi058Dao) {
		this.cmi058Dao = cmi058Dao;
	}
	public CommonUtil getCommonUtil() {
		return commonUtil;
	}
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
}
