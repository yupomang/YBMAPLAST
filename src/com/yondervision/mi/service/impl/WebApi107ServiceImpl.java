package com.yondervision.mi.service.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.common.message.SimpleHttpMessageUtil;
import com.yondervision.mi.dao.CMi031DAO;
import com.yondervision.mi.dao.CMi040DAO;
import com.yondervision.mi.dao.CMi051DAO;
import com.yondervision.mi.dao.CMi052DAO;
import com.yondervision.mi.dao.CMi053DAO;
import com.yondervision.mi.dao.CMi098DAO;
import com.yondervision.mi.dao.CMi099DAO;
import com.yondervision.mi.dao.CMi107DAO;
import com.yondervision.mi.dao.CMi403DAO;
import com.yondervision.mi.dao.CMi424DAO;
import com.yondervision.mi.dao.CMi701DAO;
import com.yondervision.mi.dao.CMi712DAO;
import com.yondervision.mi.dao.Mi007DAO;
import com.yondervision.mi.dao.Mi011DAO;
import com.yondervision.mi.dao.Mi029DAO;
import com.yondervision.mi.dao.Mi099DAO;
import com.yondervision.mi.dao.Mi709DAO;
import com.yondervision.mi.dto.CMi031;
import com.yondervision.mi.dto.CMi107;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi007Example;
import com.yondervision.mi.dto.Mi011;
import com.yondervision.mi.dto.Mi011Example;
import com.yondervision.mi.dto.Mi029Example;
import com.yondervision.mi.dto.Mi040;
import com.yondervision.mi.dto.Mi040Example;
import com.yondervision.mi.dto.Mi051;
import com.yondervision.mi.dto.Mi051Example;
import com.yondervision.mi.dto.Mi052;
import com.yondervision.mi.dto.Mi052Example;
import com.yondervision.mi.dto.Mi053;
import com.yondervision.mi.dto.Mi053Example;
import com.yondervision.mi.dto.Mi099;
import com.yondervision.mi.dto.Mi099Example;
import com.yondervision.mi.dto.Mi709;
import com.yondervision.mi.dto.Mi709Example;
import com.yondervision.mi.service.WebApi107Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;
import com.yondervision.mi.util.WkfAccessTokenUtil;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class WebApi107ServiceImpl implements WebApi107Service {
	protected final Logger log = LoggerUtil.getLogger();
	static NumberFormat num = null;
	static{
		num = NumberFormat.getPercentInstance(); 
		num.setMaximumIntegerDigits(3); 
		num.setMaximumFractionDigits(2); 
	}
	
	private CMi107DAO cmi107Dao = null;
	@Autowired
	private CMi031DAO cmi031DAO;
	@Autowired
	private CMi040DAO cmi040DAO;
	@Autowired
	private Mi029DAO mi029DAO;
	@Autowired
	private CMi051DAO cmi051DAO;
	@Autowired
	private CMi053DAO cmi053DAO;
	@Autowired
	private Mi709DAO mi709Dao;
	@Autowired
	private CMi701DAO cmi701Dao;
	@Autowired
	private Mi007DAO mi007Dao;
	@Autowired
	private CMi099DAO cmi099Dao;
	@Autowired
	private CMi403DAO cMi403Dao;
	@Autowired
	private CMi052DAO cmi052Dao;
	@Autowired
	private CMi051DAO cmi051Dao;
	@Autowired
	private CMi424DAO cmi424Dao;
	@Autowired
	private CMi098DAO cmi098Dao;
	@Autowired
	private CMi712DAO cmi712Dao;
	
	public CMi098DAO getCmi098Dao() {
		return cmi098Dao;
	}
	public void setCmi098Dao(CMi098DAO cmi098Dao) {
		this.cmi098Dao = cmi098Dao;
	}
	public void setCmi051Dao(CMi051DAO cmi051Dao) {
		this.cmi051Dao = cmi051Dao;
	}
	public CMi051DAO getCmi051Dao() {
		return cmi051Dao;
	}
	public CMi052DAO getCmi052Dao() {
		return cmi052Dao;
	}

	public void setCmi052Dao(CMi052DAO cmi052Dao) {
		this.cmi052Dao = cmi052Dao;
	}

	public CMi424DAO getCmi424Dao() {
		return cmi424Dao;
	}

	public void setCmi424Dao(CMi424DAO cmi424Dao) {
		this.cmi424Dao = cmi424Dao;
	}

	public CMi099DAO getCmi099Dao() {
		return cmi099Dao;
	}

	public void setCmi099Dao(CMi099DAO cmi099Dao) {
		this.cmi099Dao = cmi099Dao;
	}

	public CMi701DAO getCmi701Dao() {
		return cmi701Dao;
	}

	public void setCmi701Dao(CMi701DAO cmi701Dao) {
		this.cmi701Dao = cmi701Dao;
	}

	public CMi107DAO getCmi107Dao() {
		return cmi107Dao;
	}

	public void setCmi107Dao(CMi107DAO cmi107Dao) {
		this.cmi107Dao = cmi107Dao;
	}

	public CMi403DAO getcMi403Dao() {
		return cMi403Dao;
	}

	public void setcMi403Dao(CMi403DAO cMi403Dao) {
		this.cMi403Dao = cMi403Dao;
	}

	public List<HashMap> webapi10704(CMi107 form) throws Exception {
		List<HashMap> list = cmi107Dao.selectWebapi10704(form);
		for (int i = 0; i < list.size(); i++) {
			HashMap temp = list.get(i);
			temp.put("group", "功能统计汇总");
		}
		return list;
	}
	
	public List<HashMap> webapi10704Sun(CMi107 form) throws Exception {
		List<HashMap> list = cmi107Dao.selectWebapi10704Sun(form);
		String group="手机功能统计";
		if(form.getChanneltype().equals("20")){
			group="微信功能统计";
		}
		for (int i = 0; i < list.size(); i++) {
			HashMap temp = list.get(i);
			temp.put("group", group);
		}
		return list;
	}
	public JSONArray webapi10705(CMi107 form) throws Exception{
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "中心为空");
		}
		if(CommonUtil.isEmpty(form.getStartdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("开始日期为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "开始日期为空");
		}
		if(CommonUtil.isEmpty(form.getEnddate())){
			log.error(ERROR.PARAMS_NULL.getLogText("结束日期为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "结束日期为空");
		}
		//首先统计该中心下所有的渠道应用以及对应关联的栏目集合
		Mi040Example example = new Mi040Example();
		example.createCriteria().andCenteridEqualTo(form.getCenterid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi040> select = cmi040DAO.selectByExample(example);
		Map<String,String> appnameMap = new HashMap<String,String>();//该中心下的所有渠道应用
		Map<String,Set<String>> itemMap = new HashMap<String, Set<String>>();//渠道对应关联的栏目集合
		for(Mi040 mi040:select){
			String pid = mi040.getPid();
			appnameMap.put(pid,mi040.getAppname());
			Mi053Example mi053Example = new Mi053Example();
			mi053Example.createCriteria().andCenteridEqualTo(form.getCenterid()).andPidEqualTo(pid)
			.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			List<Mi053> selectList = cmi053DAO.selectByExample(mi053Example);
			Set<String> strSet = new HashSet<String>();
			for(Mi053 mi053:selectList){
				String serviceid = mi053.getServiceid();
				Mi709Example mi709Example = new Mi709Example();
				mi709Example.createCriteria().andCenteridEqualTo(form.getCenterid()).andServiceidEqualTo(serviceid)
				.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				List<Mi709> select2 = mi709Dao.selectByExample(mi709Example);
				for(Mi709 mi709:select2){
					strSet.add(mi709.getItemid());
				}
			}
			itemMap.put(pid, strSet);
		}
		Set<String> keySet = appnameMap.keySet();
		//各个渠道应用的数量--对应渠道访问量和渠道访问量占比
		List<HashMap> list1 = cmi107Dao.selectWebapi10705(form);
		double appTotal = 0;
		for(Map map:list1){
			int count = Integer.parseInt(map.get("countnum").toString());
			appTotal+=count;
		}
		//渠道访问成功率和渠道访问失败率
		List<HashMap> list2 = cmi107Dao.selectWebapi10706(form);
		//栏目内容更新数量
		List<HashMap> list3 = cmi107Dao.selectWebapi10706U(form);
		//信息推送量
		List<HashMap> list4 = cmi107Dao.selectWebapi10706T(form);
		//渠道注册人数
		List<HashMap> list5 = cmi107Dao.selectWebapi10706Z(form);
		//渠道注册人数2,不传时间，查询一个渠道所有的userid
		List<HashMap> list55 = cmi107Dao.selectWebapi10706ZZ(form);
		Mi029Example mi029Example = new Mi029Example();
		mi029Example.createCriteria().andCenteridEqualTo(form.getCenterid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		double personalTotal = mi029DAO.countByExample(mi029Example);//系统中029表总人数
		//渠道活跃用户数量
		List<HashMap> list6 = cmi107Dao.selectWebapi10708(form);
		
		JSONArray outerAry = new JSONArray();
		for(String key:keySet){
			JSONObject outerObj = new JSONObject();
			String appname = appnameMap.get(key);//渠道应用appname
			outerObj.put("appname", appname);
			JSONArray innerAry = new JSONArray();
			
			
			//渠道访问量，渠道访问量占比
			JSONObject innerObj1 = new JSONObject();
			JSONObject innerObj2 = new JSONObject();
			innerObj1.put("name", "渠道访问量");
			innerObj2.put("name", "渠道访问量占比");
			int k1=0;
			for(Map map:list1){
				String name = (String)map.get("appname");
				if(appname.equals(name)){
					k1 = Integer.parseInt(map.get("countnum").toString());
					break;
				}
			}
			innerObj1.put("value", k1);
			if(appTotal==0.0){
				innerObj2.put("value", 0);
			}else{
				innerObj2.put("value", num.format(k1/appTotal));
			}
			innerAry.add(innerObj1);
			innerAry.add(innerObj2);
			
			
			//渠道访问成功率，渠道访问失败率
			JSONObject innerObj3 = new JSONObject();
			JSONObject innerObj4 = new JSONObject();
			innerObj3.put("name", "渠道访问成功率");
			innerObj4.put("name", "渠道访问失败率");
			double k3=0;//成功数量
			double k4=0;//失败数量
			for(Map map:list2){
				String name = (String)map.get("appname");
				if(appname.equals(name)){
					String flag = map.get("flag").toString();
					if("0".equals(flag)){
						k3 = Integer.parseInt(map.get("countnum").toString());
					}
					if("1".equals(flag)){
						k4 = Integer.parseInt(map.get("countnum").toString());
					}
				}
			}
			if(k3==0.0&&k4==0.0){//说明当前渠道应用没有数据
				innerObj3.put("value", 0);
				innerObj4.put("value", 0);
			}else{
				innerObj3.put("value", num.format(k3/(k3+k4)));
				innerObj4.put("value", num.format(k4/(k3+k4)));
			}
			innerAry.add(innerObj3);
			innerAry.add(innerObj4);
			
			//栏目内容更新数量
			JSONObject innerObj5 = new JSONObject();
			innerObj5.put("name", "栏目内容更新数量");
			int k5=0;
			Set<String> set = itemMap.get(key);//栏目拿到了
			for(String itemid:set){
				for(Map map:list3){
					String name = (String)map.get("itemid");
					if(itemid.equals(name)){
						int count = Integer.parseInt(map.get("countnum").toString());
						k5+=count;
					}
				}
			}
			innerObj5.put("value", k5);
			innerAry.add(innerObj5);
			
			
			//信息推送量
			JSONObject innerObj6 = new JSONObject();
			innerObj6.put("name", "信息推送量");
			int k6=0;
			for(Map map:list4){
				String name = (String)map.get("appname");
				if(appname.equals(name)){
					int count = Integer.parseInt(map.get("successnum").toString());
					k6+=count;
					break;
				}
			}
			innerObj6.put("value", k6);
			innerAry.add(innerObj6);
			
			
			//渠道注册人数，用户注册率，活动用户占比
			JSONObject innerObj7 = new JSONObject();
			JSONObject innerObj8 = new JSONObject();
			JSONObject innerObj9 = new JSONObject();
			innerObj9.put("name", "活动用户占比");
			innerObj7.put("name", "渠道注册人数");
			innerObj8.put("name", "用户注册率");
			int k7=0;
			int k77=0;
			double k8=0;
			for(Map map:list5){
				String name = (String)map.get("appname");
				if(appname.equals(name)){
					k7 = Integer.parseInt(map.get("countnum").toString());
					break;
				}
			}
			for(Map map:list55){
				String name = (String)map.get("appname");
				if(appname.equals(name)){
					k77 = Integer.parseInt(map.get("countnum").toString());
					break;
				}
			}
			for(Map map:list6){
				String name = (String)map.get("appname");
				if(appname.equals(name)){
					k8 = Integer.parseInt(map.get("countnum").toString());
					break;
				}
			}
			if(k77==0){
				innerObj9.put("value", 0);
			}else{
				innerObj9.put("value", num.format(k8/k77));
			}
			
			innerObj7.put("value", k7);
			innerObj8.put("value", num.format(k7/personalTotal));
			innerAry.add(innerObj7);
			innerAry.add(innerObj8);
			innerAry.add(innerObj9);
			
			outerObj.put("data", innerAry);
			outerAry.add(outerObj);
		}
		
		return outerAry;
	}
	
	public JSONArray webapi1070501(CMi107 form) throws Exception{
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "中心为空");
		}
		if(CommonUtil.isEmpty(form.getStartdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("开始日期为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "开始日期为空");
		}
		if(CommonUtil.isEmpty(form.getEnddate())){
			log.error(ERROR.PARAMS_NULL.getLogText("结束日期为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "结束日期为空");
		}
		//首先统计该中心下所有的渠道应用以及对应关联的栏目集合
		Mi040Example example = new Mi040Example();
		example.setOrderByClause("pid asc");
		example.createCriteria().andCenteridEqualTo(form.getCenterid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi040> select = cmi040DAO.selectByExample(example);
//		Map<String,String> appnameMap = new HashMap<String,String>();//该中心下的所有渠道应用
		Map<String, String> appnameMap = new TreeMap<String, String>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 升序排序
                        return obj1.compareTo(obj2);
                    }
                });
		for(Mi040 mi040:select){
			String pid = mi040.getPid();
			appnameMap.put(pid,mi040.getAppname());
		}
		
		Set<String> keySet = appnameMap.keySet();
		
		String startdate =  form.getStartdate();
		String nowdate = CommonUtil.getDate();
		String enddate = form.getEnddate();
		//各个渠道活动用户占比
		List<HashMap> listActive = cmi107Dao.selectWebApiActive(form); 
		List<HashMap> listChannel = cmi107Dao.selectUserCount(form);
		//各个渠道用户注册率
		List<HashMap> listRegister = cmi107Dao.selectWebApiRegister(form);
		//各个渠道应用的数量--对应渠道访问量和渠道访问量占比
		List<HashMap> list099 = cmi099Dao.selectWebapi09906(form);
		List<HashMap> list10705 = new ArrayList<HashMap>();
		List<HashMap> list10706 = new ArrayList<HashMap>();
		HashMap<String,String> hm10705 = new HashMap<String, String>();
		HashMap<String,String> hm09905 = new HashMap<String, String>();
		HashMap<String,String> hm10706Suc = new HashMap<String, String>();
		HashMap<String,String> hm09906Suc = new HashMap<String, String>();
		HashMap<String,String> hm10706Fail = new HashMap<String, String>();
		HashMap<String,String> hm09906Fail = new HashMap<String, String>();
		if(nowdate.equals(enddate))
		{
			form.setStartdate(nowdate);
			form.setEnddate(nowdate);
			//各个渠道应用的数量--对应渠道访问量和渠道访问量占比
			list10705 = cmi107Dao.selectWebapi10705(form);
			if(!CommonUtil.isEmpty(list10705))
			{
				for(HashMap map:list10705){
					String transname = ((String)map.get("pid")).trim();	
					Integer total = Integer.parseInt(map.get("countnum").toString());
					hm10705.put(transname, total+"");
				}
			}
			//渠道访问成功率和渠道访问失败率
			list10706 = cmi107Dao.selectWebapi10706(form);
			if(!CommonUtil.isEmpty(list10706))
			{
				for(HashMap map:list10706){
					String transname = ((String)map.get("pid")).trim();	
					String flag = map.get("flag").toString();
					if("0".equals(flag)){
						Integer total = Integer.parseInt(map.get("countnum").toString());
						hm10706Suc.put(transname, total+"");
					}
					if("1".equals(flag)){
						Integer total = Integer.parseInt(map.get("countnum").toString());
						hm10706Fail.put(transname, total+"");
					}
				}
			}
		}
		if(!CommonUtil.isEmpty(list099))
		{
			int total = 0;
			int totalSuc = 0;
			int totalFail = 0;
			for(HashMap map:list099){
				String transname = ((String)map.get("pid")).trim();
				if(hm09905.containsKey(transname))
				{
					total = Integer.parseInt(hm09905.get(transname)) + Integer.parseInt(map.get("transcntsum").toString());
					totalSuc = Integer.parseInt(hm09906Suc.get(transname)) + Integer.parseInt(map.get("successsum").toString());
					totalFail = Integer.parseInt(hm09906Fail.get(transname)) + Integer.parseInt(map.get("failsum").toString());
				}else
				{
					total = Integer.parseInt(map.get("transcntsum").toString());
					totalSuc = Integer.parseInt(map.get("successsum").toString());
					totalFail = Integer.parseInt(map.get("failsum").toString());
				}
				hm09905.put(transname, total+"");
				hm09906Suc.put(transname, totalSuc+"");
				hm09906Fail.put(transname, totalFail+"");
			}
		}
		hm09905 = CommonUtil.mergeHashMap(hm10705, hm09905);
		hm09906Suc = CommonUtil.mergeHashMap(hm10706Suc, hm09906Suc);
		hm09906Fail = CommonUtil.mergeHashMap(hm10706Fail, hm09906Fail);
		double appTotal = 0;
		for(String key:keySet){
			int total = 0;
			if(hm09905.containsKey(key))
			{
				total = Integer.parseInt((String)hm09905.get(key));
				appTotal+=total;
			}
		}
		JSONArray outerAry = new JSONArray();
		for(String key:keySet){
			log.info("key="+key);
			String transname = key;
			int total = 0;
			if(hm09905.containsKey(transname))
			{
				total = Integer.parseInt((String)hm09905.get(transname));
			}
			
			JSONArray innerAry = new JSONArray();
			JSONObject outerObj = new JSONObject();
			outerObj.put("appname", appnameMap.get(transname));
			
			//渠道访问量，渠道访问量占比
			JSONObject innerObj1 = new JSONObject();
			JSONObject innerObj2 = new JSONObject();
			
			innerObj1.put("name", "渠道访问量");
			innerObj2.put("name", "渠道访问量占比");
			innerObj1.put("value", total);
			
			if(appTotal==0.0){
				innerObj2.put("value", "0%");
			}else{
				innerObj2.put("value", num.format(total/appTotal));
			}
			innerAry.add(innerObj1);
			innerAry.add(innerObj2);
			
			//渠道访问成功率，渠道访问失败率
			JSONObject innerObj3 = new JSONObject();
			JSONObject innerObj4 = new JSONObject();
			innerObj3.put("name", "渠道访问成功率");
			innerObj4.put("name", "渠道访问失败率");
			
			double totalSuc = 0;
			double totalFail = 0;
			if(hm09906Suc.containsKey(transname))
			{
				totalSuc = Double.parseDouble((String)hm09906Suc.get(transname));
			}
			if(hm09906Fail.containsKey(transname))
			{
				totalFail = Double.parseDouble((String)hm09906Fail.get(transname));
			}
			if((totalSuc+totalFail)==0)
			{
				innerObj3.put("value", "0%");
				innerObj4.put("value", "0%");
			}else
			{
				innerObj3.put("value", num.format(totalSuc/(totalSuc+totalFail)));
				innerObj4.put("value", num.format(totalFail/(totalSuc+totalFail)));
			}
			
			innerAry.add(innerObj3);
			innerAry.add(innerObj4);
			//特定渠道活动用户占比
			JSONObject innerObj5 = new JSONObject();
			innerObj5.put("name", "渠道活动用户占比");
			String fenzi = "0";
			String fenmu = "0";
			for(int i=0; i<listChannel.size(); i++){
				if(key.substring(0, 2).equals(listChannel.get(i).get("channel"))){
					fenmu=String.valueOf(listChannel.get(i).get("cnt"));
					for(int j=0; j<listActive.size(); j++){
						if(key.substring(0, 2).equals(listActive.get(j).get("channel"))){
							fenzi=String.valueOf(listActive.get(j).get("cnt"));
							break;
						}
						
					}
					break;
				}
				
			}
			log.info("fenzi="+fenzi+" fenmu="+fenmu);
			log.info("渠道活动用户占比="+num.format(Double.valueOf(fenzi)/Double.valueOf(fenmu)));
			if(!"0".equals(fenmu)){
				innerObj5.put("value", num.format(Double.valueOf(fenzi)/Double.valueOf(fenmu)));
			}else{
				innerObj5.put("value", "100%");
			}
			innerAry.add(innerObj5);
			
			//特定渠道用户注册率
			JSONObject innerObj6 = new JSONObject();
			innerObj6.put("name", "渠道用户注册率");
			String fenzi1 = "0";
			for(int i=0; i<listRegister.size(); i++){
				if(key.substring(0, 2).equals(listRegister.get(i).get("channel"))){
					fenzi1 = String.valueOf(listRegister.get(i).get("cnt"));
					break;
				}
				
			}
			log.info("fenzi1="+fenzi1);
			log.info("渠道用户注册率"+num.format(Double.valueOf(fenzi1)/Double.valueOf("1160875")));
			innerObj6.put("value", num.format(Double.valueOf(fenzi1)/Double.valueOf("1160875")));
			innerAry.add(innerObj6);
			
			//渠道运行指标栏目内容更新量
			JSONObject innerObj7 = new JSONObject();
			innerObj7.put("name", "渠道栏目内容更新量");
			log.info("startdate="+form.getStartdate() 
					+" enddate="+form.getEnddate()+" transname="+transname);
			List<HashMap> list = cmi701Dao.webapi701(form.getCenterid(), 
					transname, startdate, enddate);
			int pushcnt= 0; 
			log.info("list.size="+list.size());
			for(int i=0; i<list.size(); i++){
				log.info("cnt="+String.valueOf(list.get(i).get("cnt")));
				pushcnt += Integer.valueOf(String.valueOf(list.get(i).get("cnt")));
			}
			innerObj7.put("value", pushcnt);
			innerAry.add(innerObj7);
			
			outerObj.put("data", innerAry);
			outerAry.add(outerObj);
		}
		return outerAry;
	}
	
	//渠道统计-渠道访问量
	public List<LinkedHashMap> webapi1070601(CMi107 form) throws Exception{
//		checkCMi107(form);
		long time1 = System.currentTimeMillis();
		String nowdate = CommonUtil.getDate();
		String enddate = form.getEnddate();
		LinkedHashMap linkedMap107 = new LinkedHashMap();
		LinkedHashMap linkedMap099 = new LinkedHashMap();
		form.setCenterId(form.getCenterid());
		
		//2017-07-11修改为查询mi099数据统计，mi099由每天定时任务统计
		List<HashMap> list099 = cmi099Dao.selectWebapi09905(form);
		
//		List<HashMap> list = cmi107Dao.selectWebapi10705(form);
		
		List<LinkedHashMap> newList = new ArrayList<LinkedHashMap>();
		List<HashMap> list107 = new ArrayList<HashMap>();
		//2017-07-20添加查询当天记录 mi107
		if(nowdate.equals(enddate))
		{
			form.setStartdate(nowdate);
			form.setEnddate(nowdate);
			list107 = cmi107Dao.selectWebapi10705(form);
			if(!CommonUtil.isEmpty(list107))
			{
				for(HashMap map:list107){
					String transname = ((String)map.get("appname")).trim();	
					Integer total = Integer.parseInt(map.get("countnum").toString());
					linkedMap107.put(transname, total+"");
				}
			}
		}
		
		long time2 = System.currentTimeMillis();
		System.out.println("渠道访问量消耗时间"+(time2-time1));
		
		if(!CommonUtil.isEmpty(list099))
		{
			for(HashMap map:list099){
				String transname = ((String)map.get("appname")).trim();	
				Integer total = Integer.parseInt(map.get("countnum").toString());
				linkedMap099.put(transname, total+"");
			}
		}
		//把mi107Map的数据合并到mi099Map
		Iterator iter = linkedMap107.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			if (linkedMap099.containsKey(key)) {
				int map099 = Integer.parseInt((String)linkedMap099.get(key));
				int map107 = Integer.parseInt((String)linkedMap107.get(key));
				linkedMap099.put(key, (map099+map107)+"");
			}else {
				linkedMap099.put(key,val);
			}
		}
		
		//把mi099Map的数据返回前端
		Iterator iter099 = linkedMap099.entrySet().iterator();
		while (iter099.hasNext()) {
			Map.Entry entry = (Map.Entry) iter099.next();
			String transname = (String)entry.getKey();
			int total = Integer.parseInt((String)entry.getValue());
			LinkedHashMap linkedMap = new LinkedHashMap();
			linkedMap.put("appname", transname);
			linkedMap.put("count", total);
			newList.add(linkedMap);
		}
		
//		if(list!=null && !list.isEmpty()){
//			for(HashMap valueMap:list){
//				String c = ((String)valueMap.get("appname")).trim();//pid
//				Integer v = Integer.parseInt(valueMap.get("countnum").toString());
//				LinkedHashMap linkedMap = new LinkedHashMap();
//				linkedMap.put("appname", c);
//				linkedMap.put("count",v);
//				newList.add(linkedMap);
//			}
//		}
		return newList;
	}
	
	
	
	//渠道功能活跃度统计
	public List<LinkedHashMap> webapiChannel21(CMi107 form) throws Exception{
		if(CommonUtil.isEmpty(form.getPid())){
			log.error(ERROR.PARAMS_NULL.getLogText("应用主键为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "应用主键为空");
		}
		long time1 = System.currentTimeMillis();
		String nowdate = CommonUtil.getDate();
		String enddate = form.getEnddate();
		LinkedHashMap linkedMap107 = new LinkedHashMap();
		LinkedHashMap linkedMap099 = new LinkedHashMap();
		form.setCenterId(form.getCenterid());
		//2017-07-11修改为查询mi099数据统计，mi099由每天定时任务统计
		List<HashMap> list099 = cmi099Dao.selectWebapi09921(form);
		List<HashMap> list107 = new ArrayList<HashMap>();
		//2017-07-20添加查询当天记录 mi107
		if(nowdate.equals(enddate))
		{
			form.setStartdate(nowdate);
			form.setEnddate(nowdate);
			list107 = cmi107Dao.selectWebapi10721(form);
			if(!CommonUtil.isEmpty(list107))
			{
				for(HashMap map:list107){
					String transname = ((String)map.get("name")).trim();	
					Integer total = Integer.parseInt(map.get("value").toString());
					linkedMap107.put(transname, total+"");
				}
			}
		}
		
		if(!CommonUtil.isEmpty(list099))
		{
			for(HashMap map:list099){
				String transname = ((String)map.get("name")).trim();	
				Integer total = Integer.parseInt(map.get("value").toString());
				linkedMap099.put(transname, total+"");
			}
		}
		long time2 = System.currentTimeMillis();
		System.out.println("活跃度统计消耗时间"+(time2-time1));
		
		List<LinkedHashMap> newList = new ArrayList<LinkedHashMap>();
		//把mi107Map的数据合并到mi099Map
		Iterator iter = linkedMap107.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			if (linkedMap099.containsKey(key)) {
				int map099 = Integer.parseInt((String)linkedMap099.get(key));
				int map107 = Integer.parseInt((String)linkedMap107.get(key));
				linkedMap099.put(key, (map099+map107)+"");
			}else {
				linkedMap099.put(key,val);
			}
		}
		//把mi099Map的数据返回前端
		Iterator iter099 = linkedMap099.entrySet().iterator();
		while (iter099.hasNext()) {
			Map.Entry entry = (Map.Entry) iter099.next();
			String transname = (String)entry.getKey();
			int total = Integer.parseInt((String)entry.getValue());
			LinkedHashMap linkedMap = new LinkedHashMap();
			linkedMap.put("transname", transname);
			linkedMap.put("count", total);
			newList.add(linkedMap);
		}
//		if(list!=null && !list.isEmpty()){
//			for(HashMap map:list){
//				String transname = ((String)map.get("name")).trim();	
//				int total = Integer.parseInt(map.get("value").toString());
//				LinkedHashMap linkedMap = new LinkedHashMap();
//				linkedMap.put("transname", transname);
//				linkedMap.put("count", total);
//				newList.add(linkedMap);
//			}
//		}
		return newList;
	}
	
	//访问时间段分布统计
	public List<LinkedHashMap> webapiChannel22(CMi107 form) throws Exception{
		System.out.println("webapiChannel22==============time");
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "中心为空");
		}
		if(CommonUtil.isEmpty(form.getStartdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("开始日期为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "开始日期为空");
		}
		if(CommonUtil.isEmpty(form.getEnddate())){
			log.error(ERROR.PARAMS_NULL.getLogText("结束日期为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "结束日期为空");
		}
		if(CommonUtil.isEmpty(form.getPid())){
			log.error(ERROR.PARAMS_NULL.getLogText("应用主键为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "应用主键为空");
		}
		System.out.println("webapiChannel22==============check1");

		String startdateStr = form.getStartdate().trim();
		String enddateStr = form.getEnddate().trim();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long start = sdf.parse(startdateStr).getTime();
		long end = sdf.parse(enddateStr).getTime();
		if(end<start){
			log.error(ERROR.VALIDFLG_CHECK.getLogText("结束日期不能小于开始日期"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "结束日期不能小于开始日期");
		}
		System.out.println("webapiChannel22==============check2");
		//相差几天
		double day = (end-start)/(1000*3600*24)+1;
		String[] dayArray = {"00","01","02","03","04","05","06","07","08","09","10","11"
				,"12","13","14","15","16","17","18","19","20","21","22","23"};
		long time1 = System.currentTimeMillis();
		
		
		String nowdate = CommonUtil.getDate();
		String enddate = form.getEnddate();
		LinkedHashMap linkedMap107 = new LinkedHashMap();
		HashMap<String,String> hm099 = new HashMap<String, String>();
		form.setCenterId(form.getCenterid());
		
		Mi052Example mi052e = new Mi052Example();
		mi052e.createCriteria().andFreeuse1EqualTo("1").andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi052> mi052List = cmi052Dao.selectByExample(mi052e);		
		System.out.println("webapiChannel22==============check3+mi052List"+mi052List.toString());
		List<String> mi052ServiceIds = new ArrayList<String>();
		for(Mi052 mi052 : mi052List)
		{
			mi052ServiceIds.add(mi052.getServiceid());
		}
		Mi051Example mi051e = new Mi051Example();
		mi051e.createCriteria().andServiceidIn(mi052ServiceIds).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi051> mi051List = cmi051Dao.selectByExample(mi051e);
		System.out.println("webapiChannel22==============check4+mi051List"+mi051List.toString());
		List<String> mi051BuzTypeList = new ArrayList<String>();
		for(Mi051 mi051 : mi051List)
		{
			mi051BuzTypeList.add(mi051.getBuztype());
		}
		
		//2017-09-05修改为查询mi099数据统计，mi099由每天定时任务统计
		Mi099Example mi099e = new Mi099Example();
		Mi099Example.Criteria mi099c = mi099e.createCriteria();
		mi099c.andPidEqualTo(form.getPid()).andCenteridEqualTo(form.getCenterId())
		.andTransdateGreaterThanOrEqualTo(form.getStartdate()).andTransdateLessThanOrEqualTo(form.getEnddate())
		.andBuztypeIn(mi051BuzTypeList);
		List<Mi099> mi099List = cmi099Dao.selectByExample(mi099e);
		System.out.println("webapiChannel22==============check5+mi099List"+mi099List.toString());

		//2017-09-05添加查询当天记录 mi107
		if(nowdate.equals(enddate))
		{
			form.setStartdate(nowdate);
			form.setEnddate(nowdate);
			List<HashMap> list107 = cmi107Dao.selectWebapi10722(form);
			System.out.println("webapiChannel22==============check7+list107"+list107.toString());
			if(!CommonUtil.isEmpty(list107))
			{
				for(HashMap map:list107){
					String transname = ((String)map.get("name")).trim();	
					Integer total = Integer.parseInt(map.get("value").toString());
					linkedMap107.put(transname, total+"");
				}
			}
		}

		for(Mi099 mi099:mi099List){
			String h = mi099.getFreeuse2();
			System.out.println("webapiChannel22==============check8+h"+h);
			System.out.println(mi099.getTransdate());
			if(!CommonUtil.isEmpty(h))
			{
				String times[] = h.split(",");
				for(String t:times)
				{
					String htimes[] = t.split(":");
					if(hm099.containsKey(htimes[0]))
					{
						hm099.put(htimes[0], Integer.parseInt(htimes[1]) + Integer.parseInt(hm099.get(htimes[0])) +"");
					}else
					{
						hm099.put(htimes[0], Integer.parseInt(htimes[1]) + "");
					}
				}
			}
		}
		
		System.out.println("webapiChannel22==============check9+for mi099List over");

		hm099 = CommonUtil.mergeHashMap(linkedMap107, hm099);
		//TODO
		long time2 = System.currentTimeMillis();
		System.out.println("访问时间段分布统计消耗时间"+(time2-time1));
		List<LinkedHashMap> newList = new ArrayList<LinkedHashMap>();
		for(String str:dayArray){
			int k=0;
			
			if(hm099.containsKey(str))
			{
				double count = Double.parseDouble(hm099.get(str));
				k++;
				double d = count/day;
				d=(int)(d*100);
				LinkedHashMap linkedMap1 = new LinkedHashMap();
				linkedMap1.put("time",str+":00");
				linkedMap1.put("count", d/100);
				newList.add(linkedMap1);
			}
			
//					double count = Integer.parseInt(map.get("value").toString());
//					if(str.equals(h)){
//						k++;
//						double d = count/day;
//						d=(int)(d*100);
//						LinkedHashMap linkedMap1 = new LinkedHashMap();
//						linkedMap1.put("time",h+":00");
//						linkedMap1.put("count", d/100);
//						newList.add(linkedMap1);
//					}
			if(k==0){
				LinkedHashMap linkedMap2 = new LinkedHashMap();
				linkedMap2.put("time",str+":00");
				linkedMap2.put("count", 0);
				newList.add(linkedMap2);
			}
		}
		System.out.println("webapiChannel22==============check10+for dayArray over");
		return newList;
	}
	
	//用户增长统计
	public List<List<LinkedHashMap>> webapi10707User(CMi031 form) throws Exception{
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "中心为空");
		}
		if(CommonUtil.isEmpty(form.getStartdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("开始日期为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "开始日期为空");
		}
		if(CommonUtil.isEmpty(form.getEnddate())){
			log.error(ERROR.PARAMS_NULL.getLogText("结束日期为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "结束日期为空");
		}
		List<HashMap> list1 = cmi107Dao.webapi10707User01(form);//累计的用户
		List<HashMap> list2 = cmi107Dao.webapi10707User02(form);//新增的用户
		
		Calendar dayStart = Calendar.getInstance();//开始日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startdate = sdf.parse(form.getStartdate());
		dayStart.setTime(startdate);
		Date enddate = sdf.parse(form.getEnddate());
		Calendar dayEndtmp = Calendar.getInstance();
		dayEndtmp.setTime(enddate);
		
		List<List<LinkedHashMap>> newReturnList = new ArrayList<List<LinkedHashMap>>();
		List<LinkedHashMap> newList1 = new ArrayList<LinkedHashMap>();//累计返回的
		List<LinkedHashMap> newList2 = new ArrayList<LinkedHashMap>();//新增返回的
		
		List dateList = new ArrayList();//日期集合
		List appnameList = new ArrayList();//渠道应用集合
		
		List countsumList = new ArrayList();//累计数据集合
		List countnumList = new ArrayList();//新增数据集合
		
		dayStart.set(Calendar.DATE, dayStart.get(Calendar.DATE) - 1);
		while(dayEndtmp.after(dayStart)){
			Date time = dayEndtmp.getTime();
			String tmpdate=sdf.format(time);
			dateList.add(tmpdate);
			for(int j=0;j<list1.size();j++){
				Object appname = list1.get(j).get("appname");
				countsumList.add(list1.get(j).get("countsum"));
				if(tmpdate.equals(form.getEnddate())){//第一天才统计渠道应用名称，后面的都是重复的无需再统计
					appnameList.add(appname);
				}
				
				int k=-1;
				for(int i=0;i<list2.size();i++){
					if(list2.get(i).get("appname").equals(appname)&&list2.get(i).get("pidcreatedate").equals(tmpdate)){
						k=i;
						break;
					}
				}
				if(k==-1){
					countnumList.add(0);
				}else{
					Integer countnum = Integer.parseInt(list2.get(k).get("countnum").toString());
					countnumList.add(countnum);
					HashMap mapChange = (HashMap)list1.get(j);
					System.out.println("======================>>>>>>"+mapChange.get("countsum"));
					Integer countnumChange = Integer.parseInt((String.valueOf(mapChange.get("countsum")).trim()));
					list1.get(j).put("countsum", countnumChange-countnum);
				}
			}
			dayEndtmp.set(Calendar.DATE, dayEndtmp.get(Calendar.DATE) - 1);
		}
		
		
		List newDateList = new ArrayList();
		int dateSize = dateList.size();//日期数量
		int appSize = appnameList.size();//渠道数量
		for(int i=dateSize-1;i>=0;i--){
			newDateList.add(dateList.get(i));
		}
		int a=0;
		for(int i=appSize-1;i>=0;i=i-1){
			String appn = (String)appnameList.get(i);
			//累计的
			LinkedHashMap newMap1 = new LinkedHashMap();
			newMap1.put("appname", appn);
			newMap1.put("date", newDateList);
			
			List sumList = new ArrayList();
			for(int j=appSize*dateSize-1-a;j>=0;j=j-appSize){
				sumList.add(countsumList.get(j));
			}
			newMap1.put("countsum", sumList);
			newList1.add(newMap1);
			
			//新增的
			LinkedHashMap newMap2 = new LinkedHashMap();
			newMap2.put("appname", appn);
			newMap2.put("date", newDateList);
			
			List numList = new ArrayList();
			for(int j=appSize*dateSize-1-a;j>=0;j=j-appSize){
				numList.add(countnumList.get(j));
			}
			newMap2.put("countnum", numList);
			newList2.add(newMap2);
			a++;
		}
		newReturnList.add(newList1);
		newReturnList.add(newList2);
		return newReturnList;
		
		
	}
	
	//用户性别属性统计
	public List<LinkedHashMap> webapiChannel24(CMi107 form) throws Exception{
		List<HashMap> list = cmi107Dao.selectWebapi10724(form);
		List<LinkedHashMap> newList = new ArrayList<LinkedHashMap>();
		LinkedHashMap newMap1 = new LinkedHashMap();
		LinkedHashMap newMap2 = new LinkedHashMap();
		if(list !=null && !list.isEmpty()){
			int mansumcount = 0;
			int womansumcount = 0;
			for(Map map:list){
				int sex = Integer.parseInt(((String)map.get("sex")).trim());
				if(sex==1){
					womansumcount++;
				}else{
					mansumcount++;
				}
			}
			double total = mansumcount+womansumcount;
			newMap1.put("sex", "男性");
			newMap1.put("countsum", mansumcount);
			newMap1.put("percount", num.format(mansumcount/total));
			
			newMap2.put("sex", "女性");
			newMap2.put("countsum", womansumcount);
			newMap2.put("percount", num.format(womansumcount/total));
			
			
		}
		
		newList.add(newMap1);
		newList.add(newMap2);
		return newList;
	}
	
	//用户年龄属性统计
	public List<LinkedHashMap> webapiChannel25(CMi107 form) throws Exception{
		List<HashMap> list = cmi107Dao.selectWebapi10725(form);
		List<LinkedHashMap> newList = new ArrayList<LinkedHashMap>();
		
		if(list !=null && !list.isEmpty()){
			Set<String> appnameSet = new HashSet<String>();
			for(Map map:list){
				String name1 = ((String)map.get("appname")).trim();
				appnameSet.add(name1);
			}
			for(String appname:appnameSet){
				int k=0;//几个人
				double countAge = 0;//年龄和
				for(Map map:list){
					String name2 = ((String)map.get("appname")).trim();
					if(appname.equals(name2)){
						String birth = ((String)map.get("birth")).trim();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						double time = sdf.parse(birth).getTime();//出生年月
						double time2 = new Date().getTime();//系统时间
						double day = time2/(1000*3600*24)-time/(1000*3600*24);
						countAge+=(day/365);
						k++;
					}
				}
				LinkedHashMap averAge = new LinkedHashMap();
				averAge.put("appname", appname);
				averAge.put("average", ((int)(countAge/k*10))/10.0);
				newList.add(averAge);
			}
		}
		return newList;
	}
	
	private List<LinkedHashMap> getToday(CMi107 form) throws Exception{
		List<LinkedHashMap> newList = new ArrayList<LinkedHashMap>();
		log.info("Get today webapi10708 start!");
		List<HashMap> list = cmi107Dao.webapi10708(form);
		log.info("Get today webapi10708 end!");
		if(list !=null && !list.isEmpty()){
			double total = 0.0;
			for(Map map:list){
				int count = Integer.valueOf(map.get("pidcount").toString());
				total+=count;
			}
			for(Map map:list){
				LinkedHashMap newMap = new LinkedHashMap();
				int pidcount = Integer.valueOf(map.get("pidcount").toString());
				newMap.put("appname", map.get("appname"));
				newMap.put("count", map.get("pidcount"));
				newMap.put("percount", num.format(pidcount/total));
				newList.add(newMap);
			}
		}
		log.info("Get today return!");
		return newList;
	}
	
	private List<LinkedHashMap> getFromMi099(CMi107 form) throws Exception{
		List<LinkedHashMap> newList = new ArrayList<LinkedHashMap>();
		log.info("Get 099 webapi10708 start!");
		List<HashMap> list = cmi099Dao.webapi10708(form);
		log.info("Get 099 webapi10708 end!");
		if(list !=null && !list.isEmpty()){
			double total = 0.0;
			for(Map map:list){
				int count = Integer.valueOf(map.get("pidcount").toString());
				total+=count;
			}
			for(Map map:list){
				LinkedHashMap newMap = new LinkedHashMap();
				int pidcount = Integer.valueOf(map.get("pidcount").toString());
				newMap.put("appname", map.get("appname"));
				newMap.put("count", map.get("pidcount"));
				newMap.put("percount", num.format(pidcount/total));
				newList.add(newMap);
			}
		}
		log.info("Get 099 return!");
		return newList;
	}
	
	private List<LinkedHashMap> getMixData(List<HashMap> list107,
			List<HashMap> list099) throws Exception{
		log.info("Mix start!");
		List<LinkedHashMap> newList = new ArrayList<LinkedHashMap>();
		double total = 0.0;
		for(Map map:list107){
			int count = Integer.valueOf(map.get("pidcount").toString());
			total+=count;
		}
		log.info("total1="+total);
		for(Map map:list099){
			int count = Integer.valueOf(map.get("pidcount").toString());
			total+=count;
		}
		log.info("total2="+total);
		if((list107 !=null && !list107.isEmpty()) 
				&& (list099 !=null && !list099.isEmpty())){
			for(Map map:list099){
				LinkedHashMap newMap = new LinkedHashMap();
				int pidcount = Integer.valueOf(map.get("pidcount").toString());
				for(Map map1:list107){
					if(!map.get("appname").equals(map1.get("appname"))){
						continue;
					}else{
						pidcount += Integer.valueOf(map1.get("pidcount").toString());
						break;
					}
				}
				newMap.put("appname", map.get("appname"));
				newMap.put("count", String.valueOf(pidcount));
				newMap.put("percount", num.format(pidcount/total));
				newList.add(newMap);
			}
			log.info("newList1="+newList);
			//找出107数据在099中不存在的渠道
			for(Map map:list107){
				for(int i=0; i<list099.size(); i++){
					if(map.get("appname").equals(list099.get(i).get("appname"))){
						break;
					}
					if(!map.get("appname").equals(list099.get(i).get("appname"))
							&& i==list099.size()-1){
						LinkedHashMap newMap = new LinkedHashMap();
						int pidcount = Integer.valueOf(map.get("pidcount").toString());
						newMap.put("appname", map.get("appname"));
						newMap.put("count", String.valueOf(pidcount));
						newMap.put("percount", num.format(pidcount/total));
						newList.add(newMap);
					}
				}
			}
			log.info("newList2="+newList);
		}else if((list107 !=null && !list107.isEmpty()) 
				&& (list099.isEmpty() || list099 == null)){
			for(Map map:list107){
				LinkedHashMap newMap = new LinkedHashMap();
				int pidcount = Integer.valueOf(map.get("pidcount").toString());
				newMap.put("appname", map.get("appname"));
				newMap.put("count", map.get("pidcount"));
				newMap.put("percount", num.format(pidcount/total));
				newList.add(newMap);
			}
		}else if(list099!=null && !list099.isEmpty() 
				&& (list107==null || list107.isEmpty())){
			for(Map map:list099){
				LinkedHashMap newMap = new LinkedHashMap();
				int pidcount = Integer.valueOf(map.get("pidcount").toString());
				newMap.put("appname", map.get("appname"));
				newMap.put("count", map.get("pidcount"));
				newMap.put("percount", num.format(pidcount/total));
				newList.add(newMap);
			}
		}
		log.info("Mix end!");
		return newList;
	}
	
	//业务分析-渠道分布统计
	public List<LinkedHashMap> webapi10708(CMi107 form) throws Exception{
		String currentDate = CommonUtil.getDate();
		//查询时间只是当天
		if(currentDate.equals(form.getStartdate()) 
				&& currentDate.equals(form.getEnddate())){
			log.info("Just today!");
			return getToday(form);
		}
		//查询时间不包括当天则返回099表数据
		if(currentDate.compareTo(form.getStartdate())<0 
				|| currentDate.compareTo(form.getEnddate())>0 ){
			log.info("Not today!");
			return getFromMi099(form);
		}else{//查询时间包括当天先107表统计当天数据
			CMi107 cmi107 = new CMi107();
			cmi107.setCenterid(form.getCenterid());
			cmi107.setStartdate(currentDate);
			cmi107.setEnddate(currentDate);
			cmi107.setPid(form.getPid());
			log.info("Get 107 data!");
			List<HashMap> list107 = cmi107Dao.webapi10708(cmi107);
			log.info("Get 099 data!");
			List<HashMap> list099 = cmi099Dao.webapi10708(form);
			log.info("Get data end!");
			return getMixData(list107, list099);
		}
	}
	
	//业务分析-时间段分布统计
	public List webapi10709(CMi107 form) throws Exception{
		List<HashMap> list = cmi107Dao.webapi10709(form);
		List newList = new ArrayList();
		LinkedHashMap countTime = new LinkedHashMap();
		List<String> dateList = new ArrayList<String>();
		List<Integer> countList = new ArrayList<Integer>();
		if(list != null && !list.isEmpty()){
			for(Map map:list){
				String transdate = ((String)map.get("name")).trim();
				Integer count = (Integer)map.get("value");
				dateList.add(transdate);
				countList.add(count);
			}
		}
		newList.add(dateList);
		newList.add(countList);
		return newList;
	}
	
	//业务分析-用户分布统计
	public List<LinkedHashMap> webapi10711(CMi107 form) throws Exception{

		List<HashMap> list = cmi107Dao.webapi10711(form);
		List<LinkedHashMap> newList = new ArrayList<LinkedHashMap>();
		if(list !=null && !list.isEmpty()){
			double total = 0.0;
			for(Map map:list){
				int key = (Integer)map.get("value");
				total+=key;
			}
			for(Map map:list){
				LinkedHashMap newMap = new LinkedHashMap();
				int pidcount = (Integer)map.get("value");
				newMap.put("appname", map.get("name"));
				newMap.put("count", map.get("value"));
				newMap.put("percount", num.format(pidcount/total));
				newList.add(newMap);
			}
		}
		return newList;
	}
	
	//业务分析-业务量统计非资金类
	public JSONArray webapi1071001(CMi107 form) throws Exception{
		
		
		Mi051Example example = new Mi051Example();
		example.createCriteria().andValidflagEqualTo(Constants.IS_VALIDFLAG).andMoneytypeEqualTo("1");
		List<Mi051> select = cmi051DAO.selectByExample(example);
		if(select!=null && !select.isEmpty()){
			List<String> typeList = new ArrayList<String>();
			for(Mi051 mi051:select){
				typeList.add(mi051.getBuztype());
			}
			form.setTransTypeList(typeList);
		}
		List<HashMap> list = cmi107Dao.webapi1071001(form);
		
		if(list!=null && !list.isEmpty()){
			Set<String> appnameSet = new HashSet<String>();
			Set<String> transnameSet = new HashSet<String>();
			for(Map map:list){
				String key1 = (String)map.get("appname");
				String key2 = (String)map.get("transname");
				appnameSet.add(key1);
				transnameSet.add(key2);
			}
			
			JSONArray outoutAry = new JSONArray();
			for(String appname:appnameSet){
				JSONObject outoutObj = new JSONObject();
				outoutObj.put("appname", appname);
				
				JSONArray outAry = new JSONArray();
				
				//业务量
				JSONObject outObj = new JSONObject();
				outObj.put("name", "业务量");
				JSONArray transAry = new JSONArray();
				for(String transname:transnameSet){
					JSONObject transObj = new JSONObject();
					int total = 0;
					for(Map map:list){
						String name1 = (String)map.get("appname");
						String name2 = (String)map.get("transname");
						if(appname.equals(name1)&&transname.equals(name2)){
							int count = Integer.parseInt((String)map.get("countnum"));
							total+=count;
						}
					}
					transObj.put("name", transname);
					transObj.put("value", total);
					transAry.add(transObj);
					outObj.put("value", transAry);
				}
				outAry.add(outObj);
				//业务成功数
				JSONObject outObj2 = new JSONObject();
				outObj2.put("name", "业务成功数");
				JSONArray transAry2 = new JSONArray();
				for(String transname:transnameSet){
					
					JSONObject transObj = new JSONObject();
					int total = 0;
					for(Map map:list){
						String name1 = (String)map.get("appname");
						String name2 = (String)map.get("transname");
						String flag = (String)map.get("flag");
						if(appname.equals(name1)&&transname.equals(name2)&&"0".equals(flag)){
							int count = Integer.parseInt((String)map.get("countnum"));
							total+=count;
							break;
						}
					}
					transObj.put("name", transname);
					transObj.put("value", total);
					transAry2.add(transObj);
					outObj2.put("value", transAry2);
				}
				outAry.add(outObj2);
				//业务失败数
				JSONObject outObj3 = new JSONObject();
				outObj3.put("name", "业务失败数");
				JSONArray transAry3 = new JSONArray();
				for(String transname:transnameSet){
					JSONObject transObj = new JSONObject();
					int total = 0;
					for(Map map:list){
						String name1 = (String)map.get("appname");
						String name2 = (String)map.get("transname");
						String flag = (String)map.get("flag");
						if(appname.equals(name1)&&transname.equals(name2)&&"1".equals(flag)){
							int count = Integer.parseInt((String)map.get("countnum"));
							total+=count;
							break;
						}
					}
					transObj.put("name", transname);
					transObj.put("value", total);
					transAry3.add(transObj);
					outObj3.put("value", transAry3);
				}
				outAry.add(outObj3);
				
				outoutObj.put("data", outAry);
				outoutAry.add(outoutObj);
			}
			return outoutAry;
		}
		return null;
	}
	
	//业务分析-业务量统计非资金类New
	public JSONObject webapi1071001New(CMi107 form,HttpServletRequest request) throws Exception{
		checkCMi107Date(form);
		JSONObject objReturn = new JSONObject();
		
		Mi007Example example = new Mi007Example();
		example.createCriteria().andCenteridEqualTo(Constants.YD_ADMIN).andItemvalEqualTo("服务类型");
		List<Mi007> mi007List1 = mi007Dao.selectByExample(example);
		Integer dicid = mi007List1.get(0).getDicid();//dicid=574
		
		Mi007Example example2 = new Mi007Example();
		example2.setOrderByClause("itemid");
		example2.createCriteria().andCenteridEqualTo(Constants.YD_ADMIN).andUpdicidEqualTo(dicid);
		List<Mi007> mi007List2 = mi007Dao.selectByExample(example2);//找出了所有的服务类型，大类型，用于分别查询处理
		//业务种类大类对应数据集合,按大类分5大集合全部数据
		Map<String,List<HashMap>> dataMap = new LinkedHashMap<String,List<HashMap>>();
		for(Mi007 mi007:mi007List2){
			System.out.println("【业务分析】servicetype: "+mi007.getItemid()+"开始时间："+CommonUtil.getSystemDate());
			form.setServicetype(mi007.getItemid());
//			List<HashMap> list = cmi107Dao.webapi1071001New(form);
			List<HashMap> list = cmi099Dao.webapi09901(form);//日统计信息表查询统计，优化原有方案
			dataMap.put(mi007.getItemval(), list);
			System.out.println("【业务分析】servicetype: "+mi007.getItemid()+"结束时间："+CommonUtil.getSystemDate());
		}
		
		Mi040 mi040 = cmi040DAO.selectByPrimaryKey(form.getPid());
		List<HashMap> listSample = new ArrayList<HashMap>();//第三方系统统计数据
		
		//以下情况需要调用新媒体客户的接口	
		System.out.println("【业务分析——新媒体发送时间】："+CommonUtil.getSystemDate());
		/**贵港演示临量注释掉，2017-03-08，演示后非贵港环境需打开注释*//*
//		if(CommonUtil.isEmpty(form.getPid())||"10000136".equals(form.getPid())||"20000128".equals(form.getPid())||
//				"30000129".equals(form.getPid())||"80000134".equals(form.getPid())){
		if("10".equals(mi040.getChannel())||"20".equals(mi040.getChannel())||"30".equals(mi040.getChannel())
				||"80".equals(mi040.getChannel())){	
			SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
			String businName = "业务量统计非资金类-新媒体客服在线咨询";
			log.info(LOG.START_BUSIN.getLogText(businName));
			
			String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,"wkfurl").trim()+"/ybmapzh/chatnum";
			System.out.println("业务量统计非资金类-新媒体客服在线咨询 ，URL："+url);
			String accessToken = WkfAccessTokenUtil.WKF_GET_TOKEN(form.getCenterid());
			HashMap hashMap = new HashMap();
			hashMap.put("accessToken", accessToken);
			System.out.println("业务量统计非资金类-新媒体客服在线咨询，accessToken : "+accessToken);
			hashMap.put("centerid",form.getCenterid());
			hashMap.put("startdate", form.getStartdate().toString());
			hashMap.put("enddate", form.getEnddate().toString());
			
			if(CommonUtil.isEmpty(form.getPid())){
				hashMap.put("plat_name", "all");
			}else if("10".equals(mi040.getChannel())){//app
				hashMap.put("plat_name", "app");
			}else if("20".equals(mi040.getChannel())){//微信
				hashMap.put("plat_name", "weixin");
			}else if("30".equals(mi040.getChannel())){//网站web
				hashMap.put("plat_name", "web");
			}else if("80".equals(mi040.getChannel())){//微博
				hashMap.put("plat_name", "weibo");
			}
			System.out.println("【业务量统计非资金类-新媒体客服在线咨询参数】："+hashMap);
			String rep = "";
			HashMap remap = null;
			try {
				rep = msm.sendPost(url.toString(),hashMap, request.getCharacterEncoding());
				remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("业务量统计非资金类-新媒体客服在线咨询: "+rep);
			if(remap!=null && "0000".equals(remap.get("code").toString())){
				List<LinkedTreeMap > listMap = (List<LinkedTreeMap >)remap.get("datas");
				for(LinkedTreeMap map:listMap){
					String plat_name = map.get("plat_name").toString();
					String countnum1 = map.get("chat_num").toString();
					String countnum2 = map.get("succ_num").toString();
					String countnum3 = map.get("failed_num").toString();
					HashMap pushMapFail = new HashMap();
					pushMapFail.put("servicetype", "4");
					pushMapFail.put("servicename", "新媒体客服在线咨询");
					pushMapFail.put("zscountnum", countnum1);
					pushMapFail.put("cgcountnum", countnum2);
					pushMapFail.put("sbcountnum", countnum3);
					if("app".equals(plat_name)){
						pushMapFail.put("channel", "10");
						pushMapFail.put("appname", "手机客户端");
					}else if("web".equals(plat_name)){
						pushMapFail.put("channel", "30");
						pushMapFail.put("appname", "网站");
					}else if("weixin".equals(plat_name)){
						pushMapFail.put("channel", "20");
						pushMapFail.put("appname", "微信");
					}else if("weibo".equals(plat_name)){
						pushMapFail.put("channel", "80");
						pushMapFail.put("appname", "微博");
					}
					listSample.add(pushMapFail);
				}
			}
			
		}*/
		
		System.out.println("【业务分析——新媒体返回时间】："+CommonUtil.getSystemDate());
		//以下情况株洲需要调用短信统计的接口
		/** 
		if("70".equals(mi040.getChannel())){
			
			SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
			String businName = "业务量统计非资金类-短信业务统计";
			log.info(LOG.START_BUSIN.getLogText(businName));
			//http://10.43.12.13:7001/zzdxzf/tjSendHistoryAction!qryTj.action
			String url = null;
			try{
				url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,"smsTJurl_"+form.getCenterid()).trim();
			}catch(Exception e){
				e.printStackTrace();
			}
			if(!CommonUtil.isEmpty(url)){
				
				HashMap hashMap = new HashMap();
				hashMap.put("flag","1");
				hashMap.put("begindate", form.getStartdate().toString());
				hashMap.put("enddate", form.getEnddate().toString());
				System.out.println("【业务量统计非资金类-短信业务统计参数】："+hashMap);
				String rep = "";
				HashMap remap = null;
				try {
					rep = msm.sendPost(url.toString(),hashMap, request.getCharacterEncoding());
					remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				System.out.println("业务量统计非资金类-短信业务统计返回 "+rep);
				if(remap!=null && "000000".equals(remap.get("recode").toString())){
					List<LinkedTreeMap > listMap = (List<LinkedTreeMap >)remap.get("datas");
					for(LinkedTreeMap map:listMap){
						String business_name = map.get("business_name").toString().trim();
						String countnum1 = map.get("succ_num").toString().trim();
						String countnum2 = map.get("failed_num").toString().trim();
						HashMap busiMapSucc = new HashMap();
						busiMapSucc.put("flag", "0");
						busiMapSucc.put("transname", business_name);
						busiMapSucc.put("countnum", countnum1);
						busiMapSucc.put("appname", "手机短信");
						
						HashMap busiMapFail = new HashMap();
						busiMapFail.put("flag", "1");
						busiMapFail.put("transname", business_name);
						busiMapFail.put("countnum", countnum2);
						busiMapFail.put("appname", "手机短信");
						List<HashMap> listSample = dataMap.get("信息发布类");
						listSample.add(busiMapFail);
						listSample.add(busiMapSucc);
					}
					
				}
			}
			
		}
		*/
		
		//查询所有的渠道应用，应用名称
		Set<String> appnameSet = new HashSet<String>();
		if(CommonUtil.isEmpty(form.getPidname())){			
			appnameSet.add(mi040.getAppname());//前端传进来应用名称
		}else{
			appnameSet.add(form.getPidname());
		}
//		Collection<List<HashMap>> values = dataMap.values();
//		for(List<HashMap> listt:values){
//			for(Map map:listt){
//				String key1 = (String)map.get("appname");
//				appnameSet.add(key1);
//			}
//		}
		
		String[] ywltjOrder = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,"ywltjOrder").trim().split(",");
		JSONObject row = new JSONObject();
		Iterator<Entry<String, List<HashMap>>> iter = dataMap.entrySet().iterator();
		int t = 1;
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String serviceType = (String)entry.getKey();
			List<HashMap> val = (List<HashMap>)entry.getValue();
			JSONObject obj = new JSONObject();
			if("信息发布类".equals(serviceType)){
				obj = webapi107InfoRelease(form, serviceType, appnameSet);
			}else if("互动交流类".equals(serviceType)){
				obj = webapi107Interactive(form, serviceType, appnameSet);
			}else if("业务办理类".equals(serviceType)){//业务办理类的申请和办结交易从核心获取，如果综合平台自己统计则不需要此分支
				SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
				Mi011DAO mi011Dao = (Mi011DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi011Dao");
				Mi011Example example011 = new Mi011Example();
				example011.createCriteria().andCenteridEqualTo(form.getCenterid());
				List<Mi011> resultList = mi011Dao.selectByExample(example011);
				JSONObject repmap = JSONObject.fromObject("{\"recode\":\"999999\",\"result\":[]}");
				if(resultList.size()!=0 
						&& !CommonUtil.isEmpty(resultList.get(0).getClassname())){
					Mi011 result = resultList.get(0);
					String url = result.getUrl()+"/webapi50026.json";
					HashMap map = new HashMap();
					map.put("startdate", form.getStartdate());
					map.put("enddate", form.getEnddate());
					map.put("channel", request.getParameter("channel"));
					map.put("centerId", form.getCenterid());
					String rep = "{\"recode\":\"999999\",\"result\":[]}";
					try{
						log.info("BSP获取业务办理开始");
						rep = msm.sendPost(url, map, request.getCharacterEncoding());
						//rep = "{\"count\":\"2\",\"fail\":\"0\",\"msg\":\"成功\",\"recode\":\"000000\",\"result\":[{\"bsptype\":\"20\",\"detailCount\":\"3\",\"detailFail\":\"1\",\"detailName\":\"缴存1\",\"detailSuccess\":\"2\",\"type1\":\"40\",\"type2\":\"10\",\"type3\":\"10\"},{\"bsptype\":\"20\",\"detailCount\":\"30\",\"detailFail\":\"10\",\"detailName\":\"缴存2\",\"detailSuccess\":\"20\",\"type1\":\"40\",\"type2\":\"10\",\"type3\":\"20\"},{\"bsptype\":\"20\",\"detailCount\":\"33\",\"detailFail\":\"10\",\"detailName\":\"缴存3\",\"detailSuccess\":\"23\",\"type1\":\"40\",\"type2\":\"10\",\"type3\":\"20\"},{\"bsptype\":\"20\",\"detailCount\":\"33\",\"detailFail\":\"10\",\"detailName\":\"缴存4\",\"detailSuccess\":\"23\",\"type1\":\"40\",\"type2\":\"10\",\"type3\":\"20\"},{\"bsptype\":\"20\",\"detailCount\":\"2\",\"detailFail\":\"0\",\"detailName\":\"提取1\",\"detailSuccess\":\"2\",\"type1\":\"40\",\"type2\":\"20\",\"type3\":\"10\"},{\"bsptype\":\"20\",\"detailCount\":\"2\",\"detailFail\":\"0\",\"detailName\":\"提取2\",\"detailSuccess\":\"2\",\"type1\":\"40\",\"type2\":\"20\",\"type3\":\"20\"},{\"bsptype\":\"20\",\"detailCount\":\"222\",\"detailFail\":\"10\",\"detailName\":\"贷款1\",\"detailSuccess\":\"212\",\"type1\":\"40\",\"type2\":\"30\",\"type3\":\"10\"},{\"bsptype\":\"20\",\"detailCount\":\"32\",\"detailFail\":\"10\",\"detailName\":\"贷款2\",\"detailSuccess\":\"22\",\"type1\":\"40\",\"type2\":\"30\",\"type3\":\"20\"}],\"success\":\"2\"}";
						log.info("业务办理返回数据：" + rep);
						repmap = JSONObject.fromObject(rep);
					}catch(Exception e){
						e.printStackTrace();
						log.info("获取业务办理统计数据通讯失败！");
					}
				}
				List<HashMap> listAppoint = cmi107Dao.selectAppoint(form);
				obj = webapi107Business(repmap, val, listAppoint ,appnameSet ,serviceType , listSample);
			}else if("管理类".equals(serviceType)){
				obj = JSONObject.fromObject("{\"classType\":\"管理类\",\"count\":0,\"classCount\":0,\"classSuccess\":0,\"classFail\":0,\"data\":[]}");
			}else{
				//获取二级服务类型
				Mi007Example example3 = new Mi007Example();
				example3.setOrderByClause("itemid");
				example3.createCriteria().andCenteridEqualTo(Constants.YD_ADMIN)
					.andUpdicidEqualTo(dicid).andItemvalEqualTo(serviceType);
				List<Mi007> mi007List3 = mi007Dao.selectByExample(example3);
				List<Mi007> mi007List4 = new ArrayList();
				if(mi007List3.size()!=0){
					Mi007Example example4 = new Mi007Example();
					example4.setOrderByClause("itemid");
					example4.createCriteria().andCenteridEqualTo(Constants.YD_ADMIN)
						.andUpdicidEqualTo(mi007List3.get(0).getDicid());
					mi007List4 = mi007Dao.selectByExample(example4);
				}
				
				log.info("信息查询start");
				//JSONObject obj = webapi1071001NewMethod(val,appnameSet,serviceType);
				obj = webapi1071001CountDay(val ,appnameSet ,serviceType , listSample , form, mi007List4);
				log.info("obj="+obj);
				log.info("信息查询end");
			}
			row.put("rows"+t, obj);
			t++;
		}
		
		for(int i=0, j=1; i<ywltjOrder.length; i++, j++){
			objReturn.put("rows"+j, row.get("rows"+ywltjOrder[i]));
		}
		log.info("统计最终结果：" + objReturn);
		return objReturn;
	}
	
	//业务分析-业务量统计资金类
	public JSONArray webapi1071002(CMi107 form) throws Exception{
		Mi051Example example = new Mi051Example();
		example.createCriteria().andValidflagEqualTo(Constants.IS_VALIDFLAG).andMoneytypeEqualTo("1");
		List<Mi051> select = cmi051DAO.selectByExample(example);
		if(select!=null && !select.isEmpty()){
			List<String> typeList = new ArrayList<String>();
			for(Mi051 mi051:select){
				typeList.add(mi051.getBuztype());
			}
			form.setTransTypeList(typeList);
		}
		List<HashMap> list = cmi107Dao.webapi1071002(form);
		
		if(list!=null && !list.isEmpty()){
			Set<String> appnameSet = new HashSet<String>();
			Set<String> transnameSet = new HashSet<String>();
			for(Map map:list){
				String key1 = (String)map.get("appname");
				String key2 = (String)map.get("transname");
				appnameSet.add(key1);
				transnameSet.add(key2);
			}
			
			JSONArray outoutAry = new JSONArray();
			for(String appname:appnameSet){
				JSONObject outoutObj = new JSONObject();
				outoutObj.put("appname", appname);
				
				JSONArray outAry = new JSONArray();
				
				//业务量
				JSONObject outObj = new JSONObject();
				outObj.put("name", "业务量");
				JSONArray transAry = new JSONArray();
				for(String transname:transnameSet){
					JSONObject transObj = new JSONObject();
					int total = 0;
					for(Map map:list){
						String name1 = (String)map.get("appname");
						String name2 = (String)map.get("transname");
						if(appname.equals(name1)&&transname.equals(name2)){
							int count = Integer.parseInt((String)map.get("countnum"));
							total+=count;
						}
					}
					transObj.put("name", transname);
					transObj.put("value", total);
					transAry.add(transObj);
					outObj.put("value", transAry);
				}
				outAry.add(outObj);
				//业务成功数
				JSONObject outObj2 = new JSONObject();
				outObj2.put("name", "业务成功数");
				JSONArray transAry2 = new JSONArray();
				for(String transname:transnameSet){
					JSONObject transObj = new JSONObject();
					int total = 0;
					for(Map map:list){
						String name1 = (String)map.get("appname");
						String name2 = (String)map.get("transname");
						String flag = (String)map.get("flag");
						if(appname.equals(name1)&&transname.equals(name2)&&"0".equals(flag)){
							int count = Integer.parseInt((String)map.get("countnum"));
							total+=count;
							break;
						}
					}
					transObj.put("name", transname);
					transObj.put("value", total);
					transAry2.add(transObj);
					outObj2.put("value", transAry2);
				}
				outAry.add(outObj2);
				//业务失败数
				JSONObject outObj3 = new JSONObject();
				outObj3.put("name", "业务失败数");
				JSONArray transAry3 = new JSONArray();
				for(String transname:transnameSet){
					JSONObject transObj = new JSONObject();
					int total = 0;
					for(Map map:list){
						String name1 = (String)map.get("appname");
						String name2 = (String)map.get("transname");
						String flag = (String)map.get("flag");
						if(appname.equals(name1)&&transname.equals(name2)&&"1".equals(flag)){
							int count = Integer.parseInt((String)map.get("countnum"));
							total+=count;
							break;
						}
					}
					transObj.put("name", transname);
					transObj.put("value", total);
					transAry3.add(transObj);
					outObj3.put("value", transAry3);
				}
				outAry.add(outObj3);
				
				//业务金额
				JSONObject outObj4 = new JSONObject();
				outObj4.put("name", "业务金额");
				JSONArray transAry4 = new JSONArray();
				for(String transname:transnameSet){
					JSONObject transObj = new JSONObject();
					double total = 0;
					for(Map map:list){
						String name1 = (String)map.get("appname");
						String name2 = (String)map.get("transname");
						if(appname.equals(name1)&&transname.equals(name2)){
							double count = Double.parseDouble(map.get("money").toString());
							total+=count;
						}
					}
					transObj.put("name", transname);
					transObj.put("value", total);
					transAry4.add(transObj);
					outObj4.put("value", transAry4);
				}
				outAry.add(outObj4);
				
				
				outoutObj.put("data", outAry);
				outoutAry.add(outoutObj);
			}
			return outoutAry;
		}
		return null;
	}
	
	private JSONObject webapi1071001NewMethod(List<HashMap> list1, Set<String> appnameSet, String classType)throws Exception{
		JSONObject obj = new JSONObject();
		JSONArray outoutAry = new JSONArray();
		if(list1!=null && !list1.isEmpty()){
			Set<String> transnameSet = new HashSet<String>();
			for(Map map:list1){
				String key2 = (String)map.get("transname");
				transnameSet.add(key2);
			}
			for(String appname:appnameSet){
				JSONObject outoutObj = new JSONObject();
				outoutObj.put("appname", appname);
				
				JSONArray outAry = new JSONArray();
				
				//业务量
				JSONObject outObj = new JSONObject();
				outObj.put("name", "业务量");
				JSONArray transAry = new JSONArray();
				for(String transname:transnameSet){
					JSONObject transObj = new JSONObject();
					int total = 0;
					for(Map map:list1){
						String name1 = (String)map.get("appname");
						String name2 = (String)map.get("transname");
						if(appname.equals(name1)&&transname.equals(name2)){
							int count = Integer.parseInt(map.get("countnum").toString());
							total+=count;
						}
					}
					transObj.put("name", transname);
					transObj.put("value", total);
					transAry.add(transObj);
					outObj.put("value", transAry);
				}
				outAry.add(outObj);
				//业务成功数
				JSONObject outObj2 = new JSONObject();
				outObj2.put("name", "业务成功数");
				JSONArray transAry2 = new JSONArray();
				for(String transname:transnameSet){
					
					JSONObject transObj = new JSONObject();
					int total = 0;
					for(Map map:list1){
						String name1 = (String)map.get("appname");
						String name2 = (String)map.get("transname");
						String flag = (String)map.get("flag");
						if(appname.equals(name1)&&transname.equals(name2)&&"0".equals(flag)){
							int count = Integer.parseInt(map.get("countnum").toString());
							total+=count;
							break;
						}
					}
					transObj.put("name", transname);
					transObj.put("value", total);
					transAry2.add(transObj);
					outObj2.put("value", transAry2);
				}
				outAry.add(outObj2);
				//业务失败数
				JSONObject outObj3 = new JSONObject();
				outObj3.put("name", "业务失败数");
				JSONArray transAry3 = new JSONArray();
				for(String transname:transnameSet){
					JSONObject transObj = new JSONObject();
					int total = 0;
					for(Map map:list1){
						String name1 = (String)map.get("appname");
						String name2 = (String)map.get("transname");
						String flag = (String)map.get("flag");
						if(appname.equals(name1)&&transname.equals(name2)&&"1".equals(flag)){
							int count = Integer.parseInt(map.get("countnum").toString());
							total+=count;
							break;
						}
					}
					transObj.put("name", transname);
					transObj.put("value", total);
					transAry3.add(transObj);
					outObj3.put("value", transAry3);
				}
				outAry.add(outObj3);
				
				outoutObj.put("data", outAry);
				outoutAry.add(outoutObj);
			}
		}
		obj.put("classType", classType);
		obj.put("data", outoutAry);
		return obj;
	}
	
	/**
	 * 业务办理类bsp返回结果与综合自查结果组装
	 * @param map
	 * @param list1
	 * @param appnameSet
	 * @param classType
	 * @param listSample
	 * @param listMi007
	 * @return
	 */
	private JSONObject webapi107Business(JSONObject repmap, List<HashMap> list1,
			List<HashMap> listAppoint, Set<String> appnameSet, 
			String classType ,List<HashMap> listSample){
		JSONObject obj = new JSONObject();//返回结果
		JSONArray outoutAry = new JSONArray();//
		int count = 0;
		int classCount = 0;
		int classSuccess = 0;
		int classFail = 0;
		if(list1!=null && !list1.isEmpty() 
				|| ("000000".equals(repmap.get("recode")) 
						&& repmap.getJSONArray("result").size()!=0)){
			for(String appname:appnameSet){//应用名，调整后一次只查一个应用
				JSONObject outoutObj = new JSONObject();
				outoutObj.put("appname", appname);
				JSONArray outAry = new JSONArray();
				//业务量
				JSONObject Obj1 = new JSONObject();
				Obj1.put("name", "业务量");
				JSONArray transAry1 = new JSONArray();
				//业务成功数
				JSONObject Obj2 = new JSONObject();
				Obj2.put("name", "业务成功数");
				JSONArray transAry2 = new JSONArray();
				//业务失败数
				JSONObject Obj3 = new JSONObject();
				Obj3.put("name", "业务失败数");
				JSONArray transAry3 = new JSONArray();
				
				//业务办理小计
				log.info("业务办理json拼接开始");
				int jiaocunCount = 0;
				int jiaocunSucc = 0;
				int jiaocunFail = 0;
				int tiquCount = 0;
				int tiquSucc = 0;
				int tiquFail = 0;
				int daikuanCount = 0;
				int daikuanSucc = 0;
				int daikuanFail = 0;
				int qitaCount = 0;
				int qitaSucc = 0;
				int qitaFail = 0;
				
				//缴存业务数据参数，最后添加在小计后面
				//预约第二级json结构参数
				JSONObject jcSecondObj1 = new JSONObject();
				JSONObject jcSecondObj2 = new JSONObject();
				JSONObject jcSecondObj3 = new JSONObject();
				JSONArray jcSecondTransAry1 = new JSONArray();
				JSONArray jcSecondTransAry2 = new JSONArray();
				JSONArray jcSecondTransAry3 = new JSONArray();
				int jcSecondCnt = 0;//二级小计
				int jcSecondSuccCnt = 0;//二级小计
				int jcSecondFailCnt = 0;//二级小计
				//办结第二级json结构参数
				JSONObject jcbjSecondObj1 = new JSONObject();
				JSONObject jcbjSecondObj2 = new JSONObject();
				JSONObject jcbjSecondObj3 = new JSONObject();
				JSONArray jcbjSecondTransAry1 = new JSONArray();
				JSONArray jcbjSecondTransAry2 = new JSONArray();
				JSONArray jcbjSecondTransAry3 = new JSONArray();
				int jcbjSecondCnt = 0;//二级小计
				int jcbjSecondSuccCnt = 0;//二级小计
				int jcbjSecondFailCnt = 0;//二级小计
				//申请第二级json结构参数
				JSONObject jcsqSecondObj1 = new JSONObject();
				JSONObject jcsqSecondObj2 = new JSONObject();
				JSONObject jcsqSecondObj3 = new JSONObject();
				JSONArray jcsqSecondTransAry1 = new JSONArray();
				JSONArray jcsqSecondTransAry2 = new JSONArray();
				JSONArray jcsqSecondTransAry3 = new JSONArray();
				int jcsqSecondCnt = 0;//二级小计
				int jcsqSecondSuccCnt = 0;//二级小计
				int jcsqSecondFailCnt = 0;//二级小计
				
				//提取业务数据参数，最后添加在小计后面
				//预约第二级json结构参数
				JSONObject tqSecondObj1 = new JSONObject();
				JSONObject tqSecondObj2 = new JSONObject();
				JSONObject tqSecondObj3 = new JSONObject();
				JSONArray tqSecondTransAry1 = new JSONArray();
				JSONArray tqSecondTransAry2 = new JSONArray();
				JSONArray tqSecondTransAry3 = new JSONArray();
				int tqSecondCnt = 0;//二级小计
				int tqSecondSuccCnt = 0;//二级小计
				int tqSecondFailCnt = 0;//二级小计
				//办结第二级json结构参数
				JSONObject tqbjSecondObj1 = new JSONObject();
				JSONObject tqbjSecondObj2 = new JSONObject();
				JSONObject tqbjSecondObj3 = new JSONObject();
				JSONArray tqbjSecondTransAry1 = new JSONArray();
				JSONArray tqbjSecondTransAry2 = new JSONArray();
				JSONArray tqbjSecondTransAry3 = new JSONArray();
				int tqbjSecondCnt = 0;//二级小计
				int tqbjSecondSuccCnt = 0;//二级小计
				int tqbjSecondFailCnt = 0;//二级小计
				//申请第二级json结构参数
				JSONObject tqsqSecondObj1 = new JSONObject();
				JSONObject tqsqSecondObj2 = new JSONObject();
				JSONObject tqsqSecondObj3 = new JSONObject();
				JSONArray tqsqSecondTransAry1 = new JSONArray();
				JSONArray tqsqSecondTransAry2 = new JSONArray();
				JSONArray tqsqSecondTransAry3 = new JSONArray();
				int tqsqSecondCnt = 0;//二级小计
				int tqsqSecondSuccCnt = 0;//二级小计
				int tqsqSecondFailCnt = 0;//二级小计
				
				//贷款业务数据参数，最后添加在小计后面
				//预约第二级json结构参数
				JSONObject dkSecondObj1 = new JSONObject();
				JSONObject dkSecondObj2 = new JSONObject();
				JSONObject dkSecondObj3 = new JSONObject();
				JSONArray dkSecondTransAry1 = new JSONArray();
				JSONArray dkSecondTransAry2 = new JSONArray();
				JSONArray dkSecondTransAry3 = new JSONArray();
				int dkSecondCnt = 0;//二级小计
				int dkSecondSuccCnt = 0;//二级小计
				int dkSecondFailCnt = 0;//二级小计
				//办结第二级json结构参数
				JSONObject dkbjSecondObj1 = new JSONObject();
				JSONObject dkbjSecondObj2 = new JSONObject();
				JSONObject dkbjSecondObj3 = new JSONObject();
				JSONArray dkbjSecondTransAry1 = new JSONArray();
				JSONArray dkbjSecondTransAry2 = new JSONArray();
				JSONArray dkbjSecondTransAry3 = new JSONArray();
				int dkbjSecondCnt = 0;//二级小计
				int dkbjSecondSuccCnt = 0;//二级小计
				int dkbjSecondFailCnt = 0;//二级小计
				//申请第二级json结构参数
				JSONObject dksqSecondObj1 = new JSONObject();
				JSONObject dksqSecondObj2 = new JSONObject();
				JSONObject dksqSecondObj3 = new JSONObject();
				JSONArray dksqSecondTransAry1 = new JSONArray();
				JSONArray dksqSecondTransAry2 = new JSONArray();
				JSONArray dksqSecondTransAry3 = new JSONArray();
				int dksqSecondCnt = 0;//二级小计
				int dksqSecondSuccCnt = 0;//二级小计
				int dksqSecondFailCnt = 0;//二级小计
				
				//贷款业务数据参数，最后添加在小计后面
				//其它第二级json结果参数
				JSONObject qtSecondObj1 = new JSONObject();
				JSONObject qtSecondObj2 = new JSONObject();
				JSONObject qtSecondObj3 = new JSONObject();
				JSONArray qtSecondTransAry1 = new JSONArray();
				JSONArray qtSecondTransAry2 = new JSONArray();
				JSONArray qtSecondTransAry3 = new JSONArray();
				int qtSecondCnt = 0;//二级小计
				int qtSecondSuccCnt = 0;//二级小计
				int qtSecondFailCnt = 0;//二级小计
				
				log.info("预约统计开始"+ list1);
				//预约类统计数据从mi625表查询结果获取
				for(int i=0; i<listAppoint.size(); i++){
					HashMap temp = listAppoint.get(i);
					if(!("缴存预约办理".equals(temp.get("itemval")) 
							|| "提取预约办理".equals(temp.get("itemval"))
							|| "贷款预约办理".equals(temp.get("itemval")))){
						continue;
					}
					int zs = Integer.valueOf(String.valueOf(temp.get("zscountnum")));
					int cg = Integer.valueOf(String.valueOf(temp.get("cgcountnum")));
					int sb = Integer.valueOf(String.valueOf(temp.get("sbcountnum")));
					classCount += zs;
					classSuccess += cg;
					classFail += sb;
					//业务总量
					JSONObject transObj1 = new JSONObject();
					transObj1.put("name", temp.get("servicename").toString());
					transObj1.put("value", zs);
					//业务成功数
					JSONObject transObj2 = new JSONObject();
					transObj2.put("name", temp.get("servicename").toString());
					transObj2.put("value", cg);
					//业务失败数
					JSONObject transObj3 = new JSONObject();
					transObj3.put("name", temp.get("servicename").toString());
					transObj3.put("value", sb);
					if("缴存预约办理".equals(temp.get("itemval"))){
						count++;
						//业务总量
						jcSecondTransAry1.add(transObj1);
						jiaocunCount += zs;
						//业务成功数
						jcSecondTransAry2.add(transObj2);
						jiaocunSucc += cg;
						//业务失败数
						jcSecondTransAry3.add(transObj3);
						jiaocunFail += sb;
						jcSecondCnt += zs;
						jcSecondSuccCnt += cg;
						jcSecondFailCnt += sb;
					}else if("提取预约办理".equals(temp.get("itemval"))){
						count++;
						//业务总量
						tqSecondTransAry1.add(transObj1);
						tiquCount += zs;
						//业务成功数
						tqSecondTransAry2.add(transObj2);
						tiquSucc += cg;
						//业务失败数
						tqSecondTransAry3.add(transObj3);
						tiquFail += sb;
						tqSecondCnt += zs;
						tqSecondSuccCnt += cg;
						tqSecondFailCnt += sb;
					}else if("贷款预约办理".equals(temp.get("itemval"))){
						count++;
						//业务总量
						dkSecondTransAry1.add(transObj1);
						daikuanCount += zs;
						//业务成功数
						dkSecondTransAry2.add(transObj2);
						daikuanSucc += cg;
						//业务失败数
						dkSecondTransAry3.add(transObj3);
						daikuanFail += sb;
						dkSecondCnt += zs;
						dkSecondSuccCnt += cg;
						dkSecondFailCnt += sb;
					}
				}
				log.info("预约统计结束");
				
				log.info("其它统计开始"+ list1);
				//其它类统计数据从mi099表查询结果获取
				for(int i=0; i<list1.size(); i++){
					HashMap temp = list1.get(i);
					if(!("其它".equals(list1.get(i).get("itemval")))){
						continue;
					}
					int zs = Integer.valueOf(String.valueOf(temp.get("zscountnum")));
					int cg = Integer.valueOf(String.valueOf(temp.get("cgcountnum")));
					int sb = Integer.valueOf(String.valueOf(temp.get("sbcountnum")));
					classCount += zs;
					classSuccess += cg;
					classFail += sb;
					//业务总量
					JSONObject transObj1 = new JSONObject();
					transObj1.put("name", temp.get("servicename").toString());
					transObj1.put("value", zs);
					//业务成功数
					JSONObject transObj2 = new JSONObject();
					transObj2.put("name", temp.get("servicename").toString());
					transObj2.put("value", cg);
					//业务失败数
					JSONObject transObj3 = new JSONObject();
					transObj3.put("name", temp.get("servicename").toString());
					transObj3.put("value", sb);
					if("其它".equals(list1.get(i).get("itemval"))){
						count++;
						//业务总量
						qtSecondTransAry1.add(transObj1);
						qitaCount += zs;
						//业务成功数
						qtSecondTransAry2.add(transObj2);
						qitaSucc += cg;
						//业务失败数
						qtSecondTransAry3.add(transObj3);
						qitaFail += sb;
						qtSecondCnt += zs;
						qtSecondSuccCnt += cg;
						qtSecondFailCnt += sb;
					}
				}
				log.info("其它统计结束");
				
				log.info("办结申请统计开始");
				//申请和办结业务通过bsp接口获取
				if("000000".equals(repmap.get("recode").toString())){
					//将bsp返回汇总数据加入统计总数
					classCount += Integer.valueOf(repmap.get("count").toString());
					classSuccess += Integer.valueOf(repmap.get("success").toString());
					classFail += Integer.valueOf(repmap.get("fail").toString());

					JSONArray result = JSONArray.fromObject(repmap.get("result").toString());
					for(int i=0; i<result.size(); i++){
						JSONObject objData = result.getJSONObject(i);
						//type1为10-信息查询，20-信息发布，30-互动交流，40-业务办理
						//type2为10-缴存，20-提取，30-贷款
						//type3为10-申请办理，20-在线办结
						if("40".equals(objData.get("type1"))){
							count++;
							int zs = Integer.valueOf(String.valueOf(objData.get("detailCount")));
							int cg = Integer.valueOf(String.valueOf(objData.get("detailSuccess")));
							int sb = Integer.valueOf(String.valueOf(objData.get("detailFail")));
							//业务总量
							JSONObject transObj1 = new JSONObject();
							transObj1.put("name", objData.get("detailName").toString());
							transObj1.put("value", zs);
							//业务成功数
							JSONObject transObj2 = new JSONObject();
							transObj2.put("name", objData.get("detailName").toString());
							transObj2.put("value", cg);
							//业务失败数
							JSONObject transObj3 = new JSONObject();
							transObj3.put("name", objData.get("detailName").toString());
							transObj3.put("value", sb);
							if("10".equals(objData.get("type2")) && "10".equals(objData.get("type3"))){
								//缴存申请业务总量
								jcsqSecondTransAry1.add(transObj1);
								jiaocunCount += zs;
								//业务成功数
								jcsqSecondTransAry2.add(transObj2);
								jiaocunSucc += cg;
								//业务失败数
								jcsqSecondTransAry3.add(transObj3);
								jiaocunFail += sb;
								jcsqSecondCnt += zs;
								jcsqSecondSuccCnt += cg;
								jcsqSecondFailCnt += sb;
							}else if("10".equals(objData.get("type2")) && "20".equals(objData.get("type3"))){
								//缴存办结业务总量
								jcbjSecondTransAry1.add(transObj1);
								jiaocunCount += zs;
								//业务成功数
								jcbjSecondTransAry2.add(transObj2);
								jiaocunSucc += cg;
								//业务失败数
								jcbjSecondTransAry3.add(transObj3);
								jiaocunFail += sb;
								jcbjSecondCnt += zs;
								jcbjSecondSuccCnt += cg;
								jcbjSecondFailCnt += sb;
							}else if("20".equals(objData.get("type2")) && "10".equals(objData.get("type3"))){
								//提取申请业务总量
								tqsqSecondTransAry1.add(transObj1);
								tiquCount += zs;
								//业务成功数
								tqsqSecondTransAry2.add(transObj2);
								tiquSucc += cg;
								//业务失败数
								tqsqSecondTransAry3.add(transObj3);
								tiquFail += sb;
								tqsqSecondCnt += zs;
								tqsqSecondSuccCnt += cg;
								tqsqSecondFailCnt += sb;
							}else if("20".equals(objData.get("type2")) && "20".equals(objData.get("type3"))){
								//提取办结业务总量
								tqbjSecondTransAry1.add(transObj1);
								tiquCount += zs;
								//业务成功数
								tqbjSecondTransAry2.add(transObj2);
								tiquSucc += cg;
								//业务失败数
								tqbjSecondTransAry3.add(transObj3);
								tiquFail += sb;
								tqbjSecondCnt += zs;
								tqbjSecondSuccCnt += cg;
								tqbjSecondFailCnt += sb;
							}else if("30".equals(objData.get("type2")) && "10".equals(objData.get("type3"))){
								//贷款申请业务总量
								dksqSecondTransAry1.add(transObj1);
								daikuanCount += zs;
								//业务成功数
								dksqSecondTransAry2.add(transObj2);
								daikuanSucc += cg;
								//业务失败数
								dksqSecondTransAry3.add(transObj3);
								daikuanFail += sb;
								dksqSecondCnt += zs;
								dksqSecondSuccCnt += cg;
								dksqSecondFailCnt += sb;
							}else if("30".equals(objData.get("type2")) && "20".equals(objData.get("type3"))){
								//贷款办结业务总量
								dkbjSecondTransAry1.add(transObj1);
								daikuanCount += zs;
								//业务成功数
								dkbjSecondTransAry2.add(transObj2);
								daikuanSucc += cg;
								//业务失败数
								dkbjSecondTransAry3.add(transObj3);
								daikuanFail += sb;
								dkbjSecondCnt += zs;
								dkbjSecondSuccCnt += cg;
								dkbjSecondFailCnt += sb;
							}
						}
					}
					log.info("办结申请统计结束");
				}
				
				//缴存二级json结构拼接
				log.info("jiaocunCount="+jiaocunCount);
				if(jcSecondTransAry1.size()!=0 || jcsqSecondTransAry1.size()!=0 || jcbjSecondTransAry1.size()!=0){
					count++;//小计算一行
					transAry1.add(getXiaojiObject(jiaocunCount, "缴存业务（小计）"));
					transAry2.add(getXiaojiObject(jiaocunSucc, "缴存业务（小计）"));
					transAry3.add(getXiaojiObject(jiaocunFail, "缴存业务（小计）"));
					if(jcSecondTransAry1.size()!=0){
						count++;//二级小计
						jcSecondTransAry1.add(createThirdObj("", jcSecondCnt));
						jcSecondTransAry2.add(createThirdObj("", jcSecondSuccCnt));
						jcSecondTransAry3.add(createThirdObj("", jcSecondFailCnt));
						jcSecondObj1.put("classTitle", "缴存预约办理");
						jcSecondObj1.put("classValue", jcSecondTransAry1);
						jcSecondObj2.put("classTitle", "缴存预约办理");
						jcSecondObj2.put("classValue", jcSecondTransAry2);
						jcSecondObj3.put("classTitle", "缴存预约办理");
						jcSecondObj3.put("classValue", jcSecondTransAry3);
						transAry1.add(jcSecondObj1);
						transAry2.add(jcSecondObj2);
						transAry3.add(jcSecondObj3);
					}
					if(jcsqSecondTransAry1.size()!=0){
						count++;//二级小计
						jcsqSecondTransAry1.add(createThirdObj("", jcsqSecondCnt));
						jcsqSecondTransAry2.add(createThirdObj("", jcsqSecondSuccCnt));
						jcsqSecondTransAry3.add(createThirdObj("", jcsqSecondFailCnt));
						jcsqSecondObj1.put("classTitle", "缴存申请办理");
						jcsqSecondObj1.put("classValue", jcsqSecondTransAry1);
						jcsqSecondObj2.put("classTitle", "缴存申请办理");
						jcsqSecondObj2.put("classValue", jcsqSecondTransAry2);
						jcsqSecondObj3.put("classTitle", "缴存申请办理");
						jcsqSecondObj3.put("classValue", jcsqSecondTransAry3);
						transAry1.add(jcsqSecondObj1);
						transAry2.add(jcsqSecondObj2);
						transAry3.add(jcsqSecondObj3);
					}
					if(jcbjSecondTransAry1.size()!=0){
						count++;//二级小计
						jcbjSecondTransAry1.add(createThirdObj("", jcbjSecondCnt));
						jcbjSecondTransAry2.add(createThirdObj("", jcbjSecondSuccCnt));
						jcbjSecondTransAry3.add(createThirdObj("", jcbjSecondFailCnt));
						jcbjSecondObj1.put("classTitle", "缴存在线办结");
						jcbjSecondObj1.put("classValue", jcbjSecondTransAry1);
						jcbjSecondObj2.put("classTitle", "缴存在线办结");
						jcbjSecondObj2.put("classValue", jcbjSecondTransAry2);
						jcbjSecondObj3.put("classTitle", "缴存在线办结");
						jcbjSecondObj3.put("classValue", jcbjSecondTransAry3);
						transAry1.add(jcbjSecondObj1);
						transAry2.add(jcbjSecondObj2);
						transAry3.add(jcbjSecondObj3);
					}
				}
				//提取二级json结构拼接
				log.info("tiquCount="+tiquCount);
				if(tqSecondTransAry1.size()!=0 || tqsqSecondTransAry1.size()!=0 || tqbjSecondTransAry1.size()!=0){
					count++;
					transAry1.add(getXiaojiObject(tiquCount, "提取业务（小计）"));
					transAry2.add(getXiaojiObject(tiquSucc, "提取业务（小计）"));
					transAry3.add(getXiaojiObject(tiquFail, "提取业务（小计）"));
					if(tqSecondTransAry1.size()!=0){
						count++;//二级小计
						tqSecondTransAry1.add(createThirdObj("", tqSecondCnt));
						tqSecondTransAry2.add(createThirdObj("", tqSecondSuccCnt));
						tqSecondTransAry3.add(createThirdObj("", tqSecondFailCnt));
						tqSecondObj1.put("classTitle", "提取预约办理");
						tqSecondObj1.put("classValue", tqSecondTransAry1);
						tqSecondObj2.put("classTitle", "提取预约办理");
						tqSecondObj2.put("classValue", tqSecondTransAry2);
						tqSecondObj3.put("classTitle", "提取预约办理");
						tqSecondObj3.put("classValue", tqSecondTransAry3);
						transAry1.add(tqSecondObj1);
						transAry2.add(tqSecondObj2);
						transAry3.add(tqSecondObj3);
					}
					if(tqsqSecondTransAry1.size()!=0){
						count++;//二级小计
						tqsqSecondTransAry1.add(createThirdObj("", tqsqSecondCnt));
						tqsqSecondTransAry2.add(createThirdObj("", tqsqSecondSuccCnt));
						tqsqSecondTransAry3.add(createThirdObj("", tqsqSecondFailCnt));
						tqsqSecondObj1.put("classTitle", "提取申请办理");
						tqsqSecondObj1.put("classValue", tqsqSecondTransAry1);
						tqsqSecondObj2.put("classTitle", "提取申请办理");
						tqsqSecondObj2.put("classValue", tqsqSecondTransAry2);
						tqsqSecondObj3.put("classTitle", "提取申请办理");
						tqsqSecondObj3.put("classValue", tqsqSecondTransAry3);
						transAry1.add(tqsqSecondObj1);
						transAry2.add(tqsqSecondObj2);
						transAry3.add(tqsqSecondObj3);
					}
					if(tqbjSecondTransAry1.size()!=0){
						count++;//二级小计
						tqbjSecondTransAry1.add(createThirdObj("", tqbjSecondCnt));
						tqbjSecondTransAry2.add(createThirdObj("", tqbjSecondSuccCnt));
						tqbjSecondTransAry3.add(createThirdObj("", tqbjSecondFailCnt));
						tqbjSecondObj1.put("classTitle", "提取在线办结");
						tqbjSecondObj1.put("classValue", tqbjSecondTransAry1);
						tqbjSecondObj2.put("classTitle", "提取在线办结");
						tqbjSecondObj2.put("classValue", tqbjSecondTransAry2);
						tqbjSecondObj3.put("classTitle", "提取在线办结");
						tqbjSecondObj3.put("classValue", tqbjSecondTransAry3);
						transAry1.add(tqbjSecondObj1);
						transAry2.add(tqbjSecondObj2);
						transAry3.add(tqbjSecondObj3);
					}
				}
				log.info("daikuanCount="+daikuanCount);
				//贷款二级json结构拼接
				if(dkSecondTransAry1.size()!=0 || dksqSecondTransAry1.size()!=0 || dkbjSecondTransAry1.size()!=0){
					count++;
					transAry1.add(getXiaojiObject(daikuanCount, "贷款业务（小计）"));
					transAry2.add(getXiaojiObject(daikuanSucc, "贷款业务（小计）"));
					transAry3.add(getXiaojiObject(daikuanFail, "贷款业务（小计）"));
					if(dkSecondTransAry1.size()!=0){
						count++;//二级小计
						dkSecondTransAry1.add(createThirdObj("", dkSecondCnt));
						dkSecondTransAry2.add(createThirdObj("", dkSecondSuccCnt));
						dkSecondTransAry3.add(createThirdObj("", dkSecondFailCnt));
						dkSecondObj1.put("classTitle", "贷款预约办理");
						dkSecondObj1.put("classValue", dkSecondTransAry1);
						dkSecondObj2.put("classTitle", "贷款预约办理");
						dkSecondObj2.put("classValue", dkSecondTransAry2);
						dkSecondObj3.put("classTitle", "贷款预约办理");
						dkSecondObj3.put("classValue", dkSecondTransAry3);
						transAry1.add(dkSecondObj1);
						transAry2.add(dkSecondObj2);
						transAry3.add(dkSecondObj3);
					}
					if(dksqSecondTransAry1.size()!=0){
						count++;//二级小计
						dksqSecondTransAry1.add(createThirdObj("", dksqSecondCnt));
						dksqSecondTransAry2.add(createThirdObj("", dksqSecondSuccCnt));
						dksqSecondTransAry3.add(createThirdObj("", dksqSecondFailCnt));
						dksqSecondObj1.put("classTitle", "贷款申请办理");
						dksqSecondObj1.put("classValue", dksqSecondTransAry1);
						dksqSecondObj2.put("classTitle", "贷款申请办理");
						dksqSecondObj2.put("classValue", dksqSecondTransAry2);
						dksqSecondObj3.put("classTitle", "贷款申请办理");
						dksqSecondObj3.put("classValue", dksqSecondTransAry3);
						transAry1.add(dksqSecondObj1);
						transAry2.add(dksqSecondObj2);
						transAry3.add(dksqSecondObj3);
					}
					if(dkbjSecondTransAry1.size()!=0){
						count++;//二级小计
						dkbjSecondTransAry1.add(createThirdObj("", dkbjSecondCnt));
						dkbjSecondTransAry2.add(createThirdObj("", dkbjSecondSuccCnt));
						dkbjSecondTransAry3.add(createThirdObj("", dkbjSecondFailCnt));
						dkbjSecondObj1.put("classTitle", "贷款在线办结");
						dkbjSecondObj1.put("classValue", dkbjSecondTransAry1);
						dkbjSecondObj2.put("classTitle", "贷款在线办结");
						dkbjSecondObj2.put("classValue", dkbjSecondTransAry2);
						dkbjSecondObj3.put("classTitle", "贷款在线办结");
						dkbjSecondObj3.put("classValue", dkbjSecondTransAry3);
						transAry1.add(dkbjSecondObj1);
						transAry2.add(dkbjSecondObj2);
						transAry3.add(dkbjSecondObj3);
					}
				}
				log.info("qitaCount="+qitaCount);
				//其它二级json结构拼接
				if(qtSecondTransAry1.size()!=0){
					count++;
					transAry1.add(getXiaojiObject(qitaCount, "其它（小计）"));
					transAry2.add(getXiaojiObject(qitaSucc, "其它（小计）"));
					transAry3.add(getXiaojiObject(qitaFail, "其它（小计）"));
					count++;//二级小计
					qtSecondTransAry1.add(createThirdObj("", qtSecondCnt));
					qtSecondTransAry2.add(createThirdObj("", qtSecondSuccCnt));
					qtSecondTransAry3.add(createThirdObj("", qtSecondFailCnt));
					qtSecondObj1.put("classTitle", "其它");
					qtSecondObj1.put("classValue", qtSecondTransAry1);
					qtSecondObj2.put("classTitle", "其它");
					qtSecondObj2.put("classValue", qtSecondTransAry2);
					qtSecondObj3.put("classTitle", "其它");
					qtSecondObj3.put("classValue", qtSecondTransAry3);
					transAry1.add(qtSecondObj1);
					transAry2.add(qtSecondObj2);
					transAry3.add(qtSecondObj3);
				}
				log.info("业务办理json拼接结束");
				log.info("transAry1="+transAry1);
				log.info("transAry2="+transAry2);
				log.info("transAry3="+transAry3);
				Obj1.put("value", transAry1);
				Obj2.put("value", transAry2);
				Obj3.put("value", transAry3);
				log.info("Obj1="+Obj1);
				log.info("Obj2="+Obj2);
				log.info("Obj3="+Obj3);
				outAry.add(Obj1);
				outAry.add(Obj2);
				outAry.add(Obj3);
				log.info("outAry="+outAry);
				outoutObj.put("data", outAry);
				log.info("outoutObj="+outoutObj);
				outoutAry.add(outoutObj);
				log.info("outoutAry="+outoutAry);
			}
		}
		obj.put("classType", classType);
		obj.put("count", count);
		obj.put("classCount", classCount);
		obj.put("classSuccess", classSuccess);
		obj.put("classFail", classFail);
		obj.put("data", outoutAry);
		return obj;
	}
	
	/***
	 * 
	 * @param list1 大类集群
	 * @param appnameSet 应用名
	 * @param classType 大类型
	 * @return
	 * @throws Exception
	 */
	private JSONObject webapi1071001CountDay(List<HashMap> list1, Set<String> appnameSet, 
			String classType ,List<HashMap> listSample ,CMi107 form, List<Mi007> listMi007)	throws Exception{
		JSONObject obj = new JSONObject();//返回结果
		JSONArray outoutAry = new JSONArray();//
		int count = 0;
		int classCount = 0;
		int classSuccess = 0;
		int classFail = 0;
		if(list1!=null && !list1.isEmpty()){
			for(String appname:appnameSet){//应用名，调整后一次只查一个应用
				JSONObject outoutObj = new JSONObject();
				outoutObj.put("appname", appname);
				JSONArray outAry = new JSONArray();
				//业务量
				JSONObject Obj1 = new JSONObject();
				Obj1.put("name", "业务量");
				JSONArray transAry1 = new JSONArray();
				//业务成功数
				JSONObject Obj2 = new JSONObject();
				Obj2.put("name", "业务成功数");
				JSONArray transAry2 = new JSONArray();
				//业务失败数
				JSONObject Obj3 = new JSONObject();
				Obj3.put("name", "业务失败数");
				JSONArray transAry3 = new JSONArray();
				log.info("mi007二级服务类型循环开始");
				for(int i=0; i<listMi007.size(); i++){//统计每个二级服务类型下内容
					JSONObject secondObj1 = new JSONObject();
					JSONObject secondObj2 = new JSONObject();
					JSONObject secondObj3 = new JSONObject();
					JSONArray secondTransAry1 = new JSONArray();
					JSONArray secondTransAry2 = new JSONArray();
					JSONArray secondTransAry3 = new JSONArray();
					int secondCnt = 0;//二级小计
					int secondSuccCnt = 0;//二级小计
					int secondFailCnt = 0;//二级小计
					log.info("小计开始");
					//发布文件下载统计
					if("06".equals(listMi007.get(i).getItemid())){
						List<HashMap> fileDownload = cmi712Dao.webapi712(form.getCenterId(),
								form.getStartdate(), form.getEnddate(), form.getPid());
						for(Map map:fileDownload){
							count++;
							//业务总量
							JSONObject transObj1 = new JSONObject();
							transObj1.put("name", map.get("itemval").toString());
							int zscountnum = Integer.parseInt(map.get("cnt").toString());
							transObj1.put("value", zscountnum);
							secondTransAry1.add(transObj1);
							classCount += zscountnum;
							secondCnt += zscountnum;
							//业务成功数
							JSONObject transObj2 = new JSONObject();
							transObj2.put("name", map.get("itemval").toString());
							int cgcountnum = Integer.parseInt(map.get("cnt").toString());
							transObj2.put("value", cgcountnum);
							secondTransAry2.add(transObj2);
							classSuccess += cgcountnum;
							secondSuccCnt += cgcountnum;
							//业务成功数
							JSONObject transObj3 = new JSONObject();
							transObj3.put("name", map.get("itemval").toString());
							transObj3.put("value", 0);
							secondTransAry3.add(transObj3);
							classFail += 0;
							secondFailCnt += 0;
						}
					}else{
						for(Map map:list1){
							if("新媒体客服在线咨询".equals(map.get("servicename").toString())){
								continue;
							}
							
							if(!listMi007.get(i).getItemid().equals(map.get("itemid"))){
								continue;
							}
							count++;
							//业务总量
							JSONObject transObj1 = new JSONObject();
							transObj1.put("name", map.get("servicename").toString());
							int zscountnum = Integer.parseInt(map.get("zscountnum").toString());
							transObj1.put("value", zscountnum);
							secondTransAry1.add(transObj1);
							classCount += zscountnum;
							secondCnt += zscountnum;
							//业务成功数
							JSONObject transObj2 = new JSONObject();
							transObj2.put("name", map.get("servicename").toString());
							int cgcountnum = Integer.parseInt(map.get("cgcountnum").toString());
							transObj2.put("value", cgcountnum);
							secondTransAry2.add(transObj2);
							classSuccess += cgcountnum;
							secondSuccCnt += cgcountnum;
							//业务成功数
							JSONObject transObj3 = new JSONObject();
							transObj3.put("name", map.get("servicename").toString());
							int sbcountnum = Integer.parseInt(map.get("sbcountnum").toString());
							transObj3.put("value", sbcountnum);
							secondTransAry3.add(transObj3);
							classFail += sbcountnum;
							secondFailCnt += sbcountnum;
						}
					}
					log.info("小计结束");
					
					if(secondTransAry1.size()>0){
						count++;//二级小计
						secondTransAry1.add(createThirdObj("", secondCnt));
						secondTransAry2.add(createThirdObj("", secondSuccCnt));
						secondTransAry3.add(createThirdObj("", secondFailCnt));
						secondObj1.put("classTitle", listMi007.get(i).getItemval());
						secondObj1.put("classValue", secondTransAry1);
						transAry1.add(secondObj1);
						secondObj2.put("classTitle", listMi007.get(i).getItemval());
						secondObj2.put("classValue", secondTransAry2);
						transAry2.add(secondObj2);
						secondObj3.put("classTitle", listMi007.get(i).getItemval());
						secondObj3.put("classValue", secondTransAry3);
						transAry3.add(secondObj3);
					}
				}
				log.info("mi007二级服务类型循环结束");
				
				Obj1.put("value", transAry1);
				Obj2.put("value", transAry2);
				Obj3.put("value", transAry3);
				
				outAry.add(Obj1);
				outAry.add(Obj2);
				outAry.add(Obj3);
				
				outoutObj.put("data", outAry);
				outoutAry.add(outoutObj);
			}
		}
		obj.put("classType", classType);
		obj.put("count", count);
		obj.put("classCount", classCount);
		obj.put("classSuccess", classSuccess);
		obj.put("classFail", classFail);
		obj.put("data", outoutAry);
		return obj;
	}
	
	private JSONObject getXiaojiObject(int cnt, String xiaojiName){
		JSONObject xiaoji = new JSONObject();
		JSONArray xiaojiArray = new JSONArray();
		JSONObject xiaojiObject = new JSONObject();
		xiaojiObject.put("name", "");
		xiaojiObject.put("value", cnt);
		xiaojiArray.add(xiaojiObject);
		xiaoji.put("classTitle", xiaojiName);
		xiaoji.put("classValue", xiaojiArray);
		return xiaoji;
	}
	
	private JSONObject webapi107Interactive (CMi107 form, String serviceType, 
			Set<String> appnameSet) throws Exception{
		JSONObject obj = new JSONObject();//返回结果
		JSONArray outoutAry = new JSONArray();
		int count = 0;
		int classCount = 0;
		int classSuccess = 0;
		int classFail = 0;
		String channel = form.getPid().substring(0, 2);
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		String rep = "";//呼叫中心业务咨询结果
		String rep2 = "";//呼叫中心服务评价结果
		String rep3 = "";//工单投诉建意结果
		String rep4 = "";//新媒体业务咨询结果
		String rep5 = "";//新媒体在线调查结果
		String rep6 = "";//新媒体服务评价结果
		for(String appname:appnameSet){//应用名，调整后一次只查一个应用
			//获取统计信息
			if("60".equals(channel) && "服务热线".equals(appname)){
				//呼叫中心通话记录查询
				try{
					//String url = PropertiesReader.getHeartbeatURL(form.getCenterId(),
							//form.getPid()).trim() + "/CCAssessment/CCAssessment";
					String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
							"hjzxurl").trim()+"/CCAssessment/CCAssessment";
					log.info("呼叫中心业务咨询统计查询URL:"+url);
					HashMap hashMap = new HashMap();
					//传VCC的id，用,隔开
					log.info("PAVerticalAxisItems:"+form.getCenterId());
					hashMap.put("PAVerticalAxisItems", form.getCenterId());
					//YYYY-MM-dd  (例：2016-12-20)
					log.info("PStartDate:"+form.getStartdate());
					hashMap.put("PStartDate", form.getStartdate());
					log.info("PEndDate:"+form.getEnddate());
					hashMap.put("PEndDate", form.getEnddate());
					hashMap.put("TENANT_ID", "1");
					hashMap.put("selecttype", "12329");
					rep = msm.sendPost(url, hashMap, "UTF-8");
					log.info("rep:"+rep);
					//rep = "{\"recode\":\"000000\",\"msg\":\"\",\"data\":[{\"R_ZHRL\":\"20\",\"R_IVRSLL\":\"20\",\"R_HRZSC\":\"121\",\"R_PJTHSC\":\"10\",\"R_ACDSLL\":\"10\",\"R_JTL\":\"80%\",\"C_HCZL\":\"2\",\"C_THZSC\":\"20\",\"C_PJTHSC\":\"22\",\"R_FQL\":\"2\",\"R_FQLv\":\"20%\"}]}";
				}catch(Exception e){
					e.printStackTrace();
					log.info("呼叫中心业务咨询统计查询通讯失败！");
				}
				
				//呼叫中心满意度汇总查询
				try{
					//String url2 = PropertiesReader.getHeartbeatURL(form.getCenterId(),
						//form.getPid()).trim() + "/SatisfactionAggregateQueries/GetSatisfactionAggregateQueries";
					String url2 = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
							"hjzxurl").trim()+"/SatisfactionAggregateQueries/GetSatisfactionAggregateQueries";
					log.info("呼叫中心满意度汇总查询URL:"+url2);
					HashMap hashMap2 = new HashMap();
					hashMap2.put("ksrq", form.getStartdate());
					log.info("ksrq:"+form.getStartdate());
					hashMap2.put("jsrq", form.getEnddate());
					log.info("jsrq:"+form.getEnddate());
					hashMap2.put("centerid", form.getCenterId());
					log.info("centerid:"+form.getCenterId());
					rep2 = msm.sendPost(url2, hashMap2, "UTF-8");
					//rep2 = "{\"recode\":\"000000\",\"msg\":\"\",\"totalCount\":500,\"data\":{\"-1\":\"不满意\",\"1\":\"基本满意\",\"2\":\"满意\",\"-\":\"未评价\"},\"datas\":[{\"key\":\"1\",\"name\":\"不满意\",\"value\":20},{\"key\":\"1\",\"name\":\"基本满意\",\"value\":120},{\"key\":\"2\",\"name\":\"满意\",\"value\":200},{\"key\":\"-\",\"name\":\"未评价\",\"value\":160}]}";
					log.info("rep2:"+rep2);
				}catch(Exception e){
					e.printStackTrace();
					log.info("呼叫中心满意度汇总查询通讯失败！");
				}
			}
			//微信、微博、APP、门户网站、12329热线、短信      投诉、建意工单查询接口
			/*if("10".equals(channel) || "20".equals(channel) || "30".equals(channel)
					|| ("60".equals(channel)&&"服务热线".equals(appname)) 
					|| "70".equals(channel) || "80".equals(channel)){*/
			/*if("10".equals(channel) || "20".equals(channel) || "30".equals(channel) || "40".equals(channel)
					|| "50".equals(channel) || ("60".equals(channel)&&"服务热线".equals(appname)) 
					|| "70".equals(channel) || "80".equals(channel)){*/
				try{
					Mi007Example example = new Mi007Example();
					example.createCriteria().andCenteridEqualTo(form.getCenterId()).andItemidEqualTo("configgd");
					List<Mi007> mi007List1 = mi007Dao.selectByExample(example);
					Integer dicid = mi007List1.get(0).getDicid();
					Mi007Example example1 = new Mi007Example();
					example1.createCriteria().andCenteridEqualTo(form.getCenterId())
						.andItemidEqualTo("url").andUpdicidEqualTo(dicid);
					List<Mi007> mi007List2 = mi007Dao.selectByExample(example1);
					String url = mi007List2.get(0).getItemval()+"/activitis/wo/service/getProcessCountByType";
				
					String accessToken = WkfAccessTokenUtil.getGDTokenWithCouchBase(form.getCenterId(),"ybmap");
					log.info("工单accessToken:"+accessToken);
					//YYYY-MM-dd  (例：2016-12-20)
					url = url + "?start_time=" + form.getStartdate()
							+ "&end_time=" + form.getEnddate()
							+ "&token_=" + accessToken
							+ "&from_plat=ybmap"
							+ "&flow_from_plat=" + getPlatName(form.getPid(), appname, "1");
					log.info("工单投诉建意查询URL:"+url);
					rep3 = msm.sendGet(url, "UTF-8");
					//rep3 = "{{\"state\": \"SUCCESS\",\"code\": \"0000\",\"msg\": \"操作成功。\",\"data\": null,\"dataList\":[{\"TOTAL\": 1,\"ORG_ID\": \"00087100\",\"FLOW_FROM_PLAT \": \"APP\",\"TYPE\": \"1\"},{\"TOTAL\": 3,\"ORG_ID\": \"00087100\",\"FLOW_FROM_PLAT \": \"APP\",\"TYPE\": \"2\"}],\"totalCount\": null,\"totalPage\": null}}";
					log.info("rep3:"+rep3);
				}catch(Exception e){
					e.printStackTrace();
					log.info("工单投诉建意查询通讯失败！");
				}
			//}
			//新媒体 业务统计信息获取
			/*if("10".equals(channel) || "20".equals(channel) || "30".equals(channel) || "40".equals(channel)
					|| "50".equals(channel) || ("60".equals(channel)&&"服务热线".equals(appname)) 
					|| "70".equals(channel) || "80".equals(channel)){*/
				Mi007Example example = new Mi007Example();
				example.createCriteria().andCenteridEqualTo(form.getCenterId()).andItemidEqualTo("config");
				List<Mi007> mi007List1 = mi007Dao.selectByExample(example);
				List<Mi007> mi007List2 = new ArrayList();
				if(mi007List1.size()!=0){
					Integer dicid = mi007List1.get(0).getDicid();
					Mi007Example example1 = new Mi007Example();
					example1.createCriteria().andCenteridEqualTo(form.getCenterId())
						.andItemidEqualTo("url").andUpdicidEqualTo(dicid);
					mi007List2 = mi007Dao.selectByExample(example1);
					if(mi007List2.size()!=0){
						String url26 = mi007List2.get(0).getItemval()+"/ybmapzh/chatinfo";
						try{
							String accessToken26 = WkfAccessTokenUtil.WKF_GET_TOKEN(form.getCenterId());
							HashMap hashMap = new HashMap();
							hashMap.put("accessToken", accessToken26);
							hashMap.put("begindate", form.getStartdate() + " 00:00:00");
							hashMap.put("enddate", form.getEnddate() + " 23:59:59");
							hashMap.put("platform", getPlatName(form.getPid(), appname, "2"));
							log.info("accessToken26:" + accessToken26);
							log.info("begindate:" + form.getStartdate());
							log.info("enddate:" + form.getEnddate());
							log.info("platform:" + getPlatName(form.getPid(), appname, "2"));
							log.info("url26="+url26);
							rep4 = msm.sendPost(url26, hashMap, "UTF-8");
							log.info("rep4:"+rep4);
							//rep4 = "{\"code\":\"0000\",\"msg\":\"success\",datas:[{\"key\":\"chat_count\",\"title\":\"在线会话数\",\"total_value\":\"123\"},{\"key\":\"info_count\",\"title\":\"在线留言数\",\"total_value\":\"23\"}]}";
						}catch(Exception e){
							e.printStackTrace();
							log.info("新媒体/ybmapzh/chatinfo通讯失败！");
						}
						try{
							String url27 = mi007List2.get(0).getItemval()+"/ybmapzh/score";
							String accessToken27 = WkfAccessTokenUtil.WKF_GET_TOKEN(form.getCenterId());
							HashMap hashMap = new HashMap();
							hashMap.put("accessToken", accessToken27);
							hashMap.put("begindate", form.getStartdate() + " 00:00:00");
							hashMap.put("enddate", form.getEnddate() + " 23:59:59");
							hashMap.put("platform", getPlatName(form.getPid(), appname, "2"));
							log.info("begindate:" + form.getStartdate());
							log.info("enddate" + form.getEnddate());
							log.info("accessToken27:" + accessToken27);
							log.info("url27="+url27);
							rep5 = msm.sendPost(url27, hashMap, "UTF-8");
							log.info("rep5:"+rep5);
							//rep5 = "{\"code\":\"0000\",\"msg\":\"success\",datas:[{\"key\":\"count_2\",\"title\":\"满意评价数\",\"total_value\":\"35\"},{\"key\":\"count_1\",\"title\":\"基本满意评价数\",\"total_value\":\"15\"},{\"key\":\"count_-1\",\"title\":\"不满意评价数\",\"total_value\":\"5\"}]}";
						}catch(Exception e){
							e.printStackTrace();
							log.info("新媒体/ybmapzh/score通讯失败！");
						}
						try{
							String url7_7 = mi007List2.get(0).getItemval()+"/ybmapzh/question";
							String accessToken7_7 = WkfAccessTokenUtil.WKF_GET_TOKEN(form.getCenterId());
							HashMap hashMap2 = new HashMap();
							hashMap2.put("token", accessToken7_7);
							hashMap2.put("begindate", form.getStartdate() + " 00:00:00");
							hashMap2.put("enddate", form.getEnddate() + " 23:59:59");
							hashMap2.put("from_plat", getPlatName(form.getPid(), appname, "2"));
							log.info("accessToken7_7:" + accessToken7_7);
							log.info("url7_7="+url7_7);
							rep6 = msm.sendPost(url7_7, hashMap2, "UTF-8");
							log.info("rep6:"+rep6);
							//rep6 = "{\"code\":\"0000\",\"msg\":\"处理成功\",\"data\":{\"key\":\"answer_count\",\"title\":\"参与调查人次\",\"total_value\":\"35\"},\"totalCount\":\"0\"}";
						}catch(Exception e){
							e.printStackTrace();
							log.info("新媒体/ybmapzh/question通讯失败！");
						}
					}
				}
			//}
			
			//二级JSON拼装变量
			JSONObject outoutObj = new JSONObject();
			outoutObj.put("appname", appname);
			JSONArray outAry = new JSONArray();
			//业务量
			JSONObject Obj1 = new JSONObject();
			Obj1.put("name", "业务量");
			JSONArray transAry1 = new JSONArray();
			//业务成功数
			JSONObject Obj2 = new JSONObject();
			Obj2.put("name", "业务成功数");
			JSONArray transAry2 = new JSONArray();
			//业务失败数
			JSONObject Obj3 = new JSONObject();
			Obj3.put("name", "业务失败数");
			JSONArray transAry3 = new JSONArray();
			if("60".equals(channel)&&"服务热线".equals(appname)){
				if(!CommonUtil.isEmpty(rep)){
					log.info("服务热线互动交流start");
					JSONObject objRep1 = JSONObject.fromObject(rep);
					log.info("服务热线objRep1" + objRep1.toString());
					if("000000".equals(objRep1.get("recode"))){
						JSONObject secondObj1 = new JSONObject();
						secondObj1.put("classTitle", "业务咨询");
						JSONArray secondAry1 = new JSONArray();
						JSONObject secondObj2 = new JSONObject();
						secondObj2.put("classTitle", "业务咨询");
						JSONArray secondAry2 = new JSONArray();
						count++;
						String name = String.valueOf("呼入总量");
						int thirdCnt = Integer.parseInt(String.valueOf(objRep1.getJSONArray("data").getJSONObject(0).get("R_ZHRL")));
						secondAry1.add(createThirdObj(name, thirdCnt));
						secondAry1.add(createThirdObj("", thirdCnt));//二级小计
						secondObj1.put("classValue", secondAry1);
						secondAry2.add(createThirdObj(name,0));
						secondAry2.add(createThirdObj("",0));//二级小计
						secondObj2.put("classValue", secondAry2);
						count++;//二级小计
						classCount += thirdCnt;
						classSuccess += thirdCnt;
						transAry1.add(secondObj1);
						transAry2.add(secondObj1);
						transAry3.add(secondObj2);
					}
				}
				
				if(!CommonUtil.isEmpty(rep3)){
					JSONObject objRep3 = JSONObject.fromObject(rep3);
					if("0000".equals(objRep3.get("code"))){
						JSONObject secondObj1 = new JSONObject();
						secondObj1.put("classTitle", "投诉、建议");
						JSONArray secondAry1 = new JSONArray();
						JSONObject secondObj2 = new JSONObject();
						secondObj2.put("classTitle", "投诉、建议");
						JSONArray secondAry2 = new JSONArray();
						int secondCnt = 0;//二级小计
						JSONArray array = objRep3.getJSONArray("dataList");
						Iterator<Object> it = array.iterator();
						while(it.hasNext()){
							JSONObject objData = (JSONObject)it.next();
							String name = String.valueOf(objData.get("TYPE"));
							if("2".equals(name)){
								count++;
								name = "投诉";
							}else if("3".equals(name)){
								count++;
								name = "建议";
							}else{
								continue;
							}
							int thirdCnt = Integer.valueOf(String.valueOf(objData.get("TOTAL")));
							secondAry1.add(createThirdObj(name, thirdCnt));
							secondObj1.put("classValue", secondAry1);
							secondAry2.add(createThirdObj(name,0));
							secondObj2.put("classValue", secondAry2);
							classCount += thirdCnt;
							classSuccess += thirdCnt;
							secondCnt += thirdCnt;
						}
						count++;//二级小计
						secondAry1.add(createThirdObj("", secondCnt));
						secondObj1.put("classValue", secondAry1);
						secondAry2.add(createThirdObj("",0));
						secondObj2.put("classValue", secondAry2);
						transAry1.add(secondObj1);
						transAry2.add(secondObj1);
						transAry3.add(secondObj2);
					}
				}
				
				if(!CommonUtil.isEmpty(rep6)){
					JSONObject objRep6 = JSONObject.fromObject(rep6);
					if("0000".equals(objRep6.get("code"))){
						JSONObject secondObj1 = new JSONObject();
						secondObj1.put("classTitle", "在线调查/问卷调查");
						JSONArray secondAry1 = new JSONArray();
						JSONObject secondObj2 = new JSONObject();
						secondObj2.put("classTitle", "在线调查/问卷调查");
						JSONArray secondAry2 = new JSONArray();
						JSONObject objData = objRep6.getJSONObject("data");
						count++;
						String name = String.valueOf(objData.get("title"));
						int thirdCnt = Integer.valueOf(String.valueOf(objData.get("total_value")));
						secondAry1.add(createThirdObj(name, thirdCnt));
						secondAry1.add(createThirdObj("", thirdCnt));//二级小计
						secondObj1.put("classValue", secondAry1);
						secondAry2.add(createThirdObj(name,0));
						secondAry2.add(createThirdObj("",0));//二级小计
						secondObj2.put("classValue", secondAry2);
						count++;//二级小计
						classCount += thirdCnt;
						classSuccess += thirdCnt;
						transAry1.add(secondObj1);
						transAry2.add(secondObj1);
						transAry3.add(secondObj2);
					}
					log.info("在线调查/问卷调查end");
				}
				
				if(!CommonUtil.isEmpty(rep2)){
					JSONObject objRep2 = JSONObject.fromObject(rep2);
					log.info("服务热线objRep2" + objRep2.toString());
					if("000000".equals(objRep2.get("recode"))){
						JSONObject secondObj1 = new JSONObject();
						secondObj1.put("classTitle", "服务评价");
						JSONArray secondAry1 = new JSONArray();
						JSONObject secondObj2 = new JSONObject();
						secondObj2.put("classTitle", "服务评价");
						JSONArray secondAry2 = new JSONArray();
						int secondCnt = 0;//二级小计
						JSONArray array = objRep2.getJSONArray("datas");
						Iterator<Object> it = array.iterator();
						while(it.hasNext()){
							count++;
							JSONObject objData = (JSONObject)it.next();
							String name = String.valueOf(objData.get("name"));
							int thirdCnt = Integer.valueOf(String.valueOf(objData.get("value")));
							secondAry1.add(createThirdObj(name, thirdCnt));
							secondObj1.put("classValue", secondAry1);
							secondAry2.add(createThirdObj(name,0));
							secondObj2.put("classValue", secondAry2);
							classCount += thirdCnt;
							classSuccess += thirdCnt;
							secondCnt += thirdCnt;
						}
						count++;//二级小计
						secondAry1.add(createThirdObj("", secondCnt));
						secondObj1.put("classValue", secondAry1);
						secondAry2.add(createThirdObj("",0));
						secondObj2.put("classValue", secondAry2);
						transAry1.add(secondObj1);
						transAry2.add(secondObj1);
						transAry3.add(secondObj2);
					}
				}
				if(count>0){
					Obj1.put("value", transAry1);
					Obj2.put("value", transAry2);
					Obj3.put("value", transAry3);
					
					outAry.add(Obj1);
					outAry.add(Obj2);
					outAry.add(Obj3);
					
					outoutObj.put("data", outAry);
					outoutAry.add(outoutObj);
				}
				log.info("服务热线互动交流end");
			}else{
				log.info("互动交流start");
				if(!CommonUtil.isEmpty(rep4)){
					JSONObject objRep4 = JSONObject.fromObject(rep4);
					log.info("互动交流objRep4："+objRep4);
					if("0000".equals(objRep4.get("code"))){
						JSONObject secondObj1 = new JSONObject();
						secondObj1.put("classTitle", "业务咨询");
						JSONArray secondAry1 = new JSONArray();
						JSONObject secondObj2 = new JSONObject();
						secondObj2.put("classTitle", "业务咨询");
						JSONArray secondAry2 = new JSONArray();
						int secondCnt = 0;//二级小计
						JSONArray array = objRep4.getJSONArray("datas");
						Iterator<Object> it = array.iterator();
						while(it.hasNext()){
							count++;
							JSONObject objData = (JSONObject)it.next();
							String name = String.valueOf(objData.get("title"));
							int thirdCnt = Integer.valueOf(String.valueOf(objData.get("total_value")));
							secondAry1.add(createThirdObj(name, thirdCnt));
							secondObj1.put("classValue", secondAry1);
							secondAry2.add(createThirdObj(name,0));
							secondObj2.put("classValue", secondAry2);
							classCount += thirdCnt;
							classSuccess += thirdCnt;
							secondCnt += thirdCnt;
						}
						count++;//二级小计
						secondAry1.add(createThirdObj("", secondCnt));
						secondObj1.put("classValue", secondAry1);
						secondAry2.add(createThirdObj("",0));
						secondObj2.put("classValue", secondAry2);
						transAry1.add(secondObj1);
						transAry2.add(secondObj1);
						transAry3.add(secondObj2);
					}
					log.info("业务咨询end");
				}
				/*if("10".equals(channel) || "20".equals(channel) || "30".equals(channel)
						|| ("60".equals(channel)&&"服务热线".equals(appname)) 
						|| "70".equals(channel) || "80".equals(channel)){*/
					if(!CommonUtil.isEmpty(rep3)){
						JSONObject objRep3 = JSONObject.fromObject(rep3);
						if("0000".equals(objRep3.get("code"))){
							JSONObject secondObj1 = new JSONObject();
							secondObj1.put("classTitle", "投诉、建议");
							JSONArray secondAry1 = new JSONArray();
							JSONObject secondObj2 = new JSONObject();
							secondObj2.put("classTitle", "投诉、建议");
							JSONArray secondAry2 = new JSONArray();
							int secondCnt = 0;//二级小计
							JSONArray array = objRep3.getJSONArray("dataList");
							Iterator<Object> it = array.iterator();
							while(it.hasNext()){
								JSONObject objData = (JSONObject)it.next();
								String name = String.valueOf(objData.get("TYPE"));
								if("2".equals(name)){
									count++;
									name = "投诉";
								}else if("3".equals(name)){
									count++;
									name = "建议";
								}else{
									continue;
								}
								int thirdCnt = Integer.valueOf(String.valueOf(objData.get("TOTAL")));
								secondAry1.add(createThirdObj(name, thirdCnt));
								secondObj1.put("classValue", secondAry1);
								secondAry2.add(createThirdObj(name,0));
								secondObj2.put("classValue", secondAry2);
								classCount += thirdCnt;
								classSuccess += thirdCnt;
								secondCnt += thirdCnt;
							}
							count++;//二级小计
							secondAry1.add(createThirdObj("", secondCnt));
							secondObj1.put("classValue", secondAry1);
							secondAry2.add(createThirdObj("",0));
							secondObj2.put("classValue", secondAry2);
							transAry1.add(secondObj1);
							transAry2.add(secondObj1);
							transAry3.add(secondObj2);
						}
					}
				//}
				log.info("投诉、建议end");
				
				if(!CommonUtil.isEmpty(rep6)){
					JSONObject objRep6 = JSONObject.fromObject(rep6);
					if("0000".equals(objRep6.get("code"))){
						JSONObject secondObj1 = new JSONObject();
						secondObj1.put("classTitle", "在线调查/问卷调查");
						JSONArray secondAry1 = new JSONArray();
						JSONObject secondObj2 = new JSONObject();
						secondObj2.put("classTitle", "在线调查/问卷调查");
						JSONArray secondAry2 = new JSONArray();
						JSONObject objData = objRep6.getJSONObject("data");
						count++;
						String name = String.valueOf(objData.get("title"));
						int thirdCnt = Integer.valueOf(String.valueOf(objData.get("total_value")));
						secondAry1.add(createThirdObj(name, thirdCnt));
						secondAry1.add(createThirdObj("", thirdCnt));//二级小计
						secondObj1.put("classValue", secondAry1);
						secondAry2.add(createThirdObj(name,0));
						secondAry2.add(createThirdObj("",0));//二级小计
						secondObj2.put("classValue", secondAry2);
						count++;//二级小计
						classCount += thirdCnt;
						classSuccess += thirdCnt;
						transAry1.add(secondObj1);
						transAry2.add(secondObj1);
						transAry3.add(secondObj2);
					}
					log.info("在线调查/问卷调查end");
				}
				
				if(!CommonUtil.isEmpty(rep5)){
					JSONObject objRep5 = JSONObject.fromObject(rep5);
					if("0000".equals(objRep5.get("code"))){
						JSONObject secondObj1 = new JSONObject();
						secondObj1.put("classTitle", "服务评价");
						JSONArray secondAry1 = new JSONArray();
						JSONObject secondObj2 = new JSONObject();
						secondObj2.put("classTitle", "服务评价");
						JSONArray secondAry2 = new JSONArray();
						int secondCnt = 0;//二级小计
						JSONArray array = objRep5.getJSONArray("datas");
						Iterator<Object> it = array.iterator();
						while(it.hasNext()){
							count++;
							JSONObject objData = (JSONObject)it.next();
							String name = String.valueOf(objData.get("title"));
							int thirdCnt = Integer.valueOf(String.valueOf(objData.get("total_value")));
							secondAry1.add(createThirdObj(name, thirdCnt));
							secondObj1.put("classValue", secondAry1);
							secondAry2.add(createThirdObj(name,0));
							secondObj2.put("classValue", secondAry2);
							classCount += thirdCnt;
							classSuccess += thirdCnt;
							secondCnt += thirdCnt;
						}
						count++;//二级小计
						secondAry1.add(createThirdObj("", secondCnt));
						secondObj1.put("classValue", secondAry1);
						secondAry2.add(createThirdObj("",0));
						secondObj2.put("classValue", secondAry2);
						transAry1.add(secondObj1);
						transAry2.add(secondObj1);
						transAry3.add(secondObj2);
					}
					log.info("服务评价end");
				}
				
				if(count>0){
					Obj1.put("value", transAry1);
					Obj2.put("value", transAry2);
					Obj3.put("value", transAry3);
					
					outAry.add(Obj1);
					outAry.add(Obj2);
					outAry.add(Obj3);
					
					outoutObj.put("data", outAry);
					outoutAry.add(outoutObj);
				}
			}
		}
		
		obj.put("classType", serviceType);
		obj.put("count", count);
		obj.put("classCount", classCount);
		obj.put("classSuccess", classSuccess);
		obj.put("classFail", classFail);
		obj.put("data", outoutAry);
		return obj;
	}
	
	private String getPlatName(String pid, String appname, String flag){
		String name = "";
		String channel = pid.substring(0, 2);
		if("1".equals(flag)){
			if("10".equals(channel)){
				name = "APP";
			}else if("20".equals(channel)){
				name = "微信";
			}else if("30".equals(channel)){
				name = "门户网站";
			}else if("60".equals(channel)&&"服务热线".equals(appname)){
				name = "12329热线";
			}else if("70".equals(channel)){
				name = "短信";
			}else if("80".equals(channel)){
				name = "微博";
			}
		}else if("2".equals(flag)){
			//手机-app、微信-weixin、微博-sina、门户网站-web、呼叫中心-callcenter、网厅-ish、短信-sms、自助终端-self
			if("10".equals(channel)){
				name = "app";
			}else if("20".equals(channel)){
				name = "weixin";
			}else if("30".equals(channel)){
				name = "web";
			}else if("40".equals(channel)){
				name = "ish";
			}else if("50".equals(channel)){
				name = "self";
			}else if("60".equals(channel)&&"服务热线".equals(appname)){
				name = "callcenter";
			}else if("70".equals(channel)){
				name = "sms";
			}else if("80".equals(channel)){
				name = "sina";
			}
		}
		return name;
	}
	
	private JSONObject webapi107InfoRelease(CMi107 form, String serviceType, 
			Set<String> appnameSet) throws Exception{
		JSONObject obj = new JSONObject();//返回结果
		JSONArray outoutAry = new JSONArray();
		int count = 0;
		int classCount = 0;
		int classSuccess = 0;
		int classFail = 0;
		int cntSucc = 0;
		int cntFail = 0;
		int cntDZSucc = 0;
		int cntDZFail = 0;
		List<HashMap> list1 = new ArrayList<HashMap>();
		List<HashMap> list2 = new ArrayList<HashMap>();
		//List<HashMap> list3 = new ArrayList<HashMap>();
		List<HashMap> list4 = new ArrayList<HashMap>();
		List<HashMap> list5 = new ArrayList<HashMap>();
		String repWeibo = "";
		String repMessage = "";
		for(String appname:appnameSet){//应用名，调整后一次只查一个应用
			SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
			String channel = form.getPid().substring(0, 2);
			//公共信息发布 信息获取
			if("80".equals(channel)){//微博公共信息发布
				log.info("微博公共接口通讯开始");
				try{
					String accessToken = WkfAccessTokenUtil.getWBTokenWithCouchBase(form.getCenterId());
					String url = PropertiesReader.getHeartbeatURL(form.getCenterId(),
							"80").trim() + "/proc/sina/getCount";
					HashMap hashMap = new HashMap();
					hashMap.put("begindate", form.getStartdate() + " 00:00:00");
					hashMap.put("enddate", form.getEnddate() + " 23:59:59");
					hashMap.put("token", accessToken);
					log.info("微博token:" + accessToken);
					log.info("url:" + url);
					repWeibo = msm.sendPost(url, hashMap, "UTF-8");
					//repWeibo = "{\"code\":\"0000\",\"msg\":\"success\",datas:[{\"key\":\"ydmb_count\",\"title\":\"微博系统发布数量\",\"total_value\":\"123\"},{\"key\":\"out_count\",\"title\":\"外部接口发布数量\",\"total_value\":\"23\"}]}";
				}catch(Exception e){
					e.printStackTrace();
					log.info("微博通讯失败！");
				}
				log.info("微博公共接口通讯结束");
			}else{//非微博渠道通过栏目与服务关联关系统计公共信息发布数据
				log.info("非微博渠道通过栏目与服务关联关系统计公共信息发布数据start");
				list1 = cmi701Dao.webapi701(form.getCenterId(), 
					form.getPid(), form.getStartdate(), form.getEnddate());
				log.info("非微博渠道通过栏目与服务关联关系统计公共信息发布数据end");
			}
			//消息推送 信息获取
			if("10".equals(channel) || "20".equals(channel)){//微信、app消息推送通过mi423和mi403计算统计信息
				log.info("微信、app消息推送start");
				String today = CommonUtil.getSystemDate();
				String startDate = form.getStartdate();
				String endDate = form.getEnddate();
				String centerid = form.getCenterId();
				String pid = form.getPid();
				//只统计当天数据
				if(today.equals(startDate) && today.equals(endDate)){
					//群发消息
					cntSucc = cMi403Dao.webapi403(centerid, pid, startDate, endDate, "1", "01");
					log.info("cntSucc="+cntSucc);
					cntFail = cMi403Dao.webapi403(centerid, pid, startDate, endDate, "2", "01");
					log.info("cntFail="+cntFail);
					//定制消息
					cntDZSucc = cMi403Dao.webapi403(centerid, pid, startDate, endDate, "1", "02");
					log.info("cntDZSucc="+cntDZSucc);
					cntDZFail = cMi403Dao.webapi403(centerid, pid, startDate, endDate, "2", "02");
					log.info("cntDZFail="+cntDZFail);
					//模板消息
					list2 = cMi403Dao.webapi40301(centerid, pid, startDate, endDate);
					log.info("list2="+list2);
				}else if(today.compareTo(startDate)<0 || today.compareTo(endDate)>0){//不包含当天
					//群发消息 定制消息
					list4 = cmi424Dao.webapi42401(centerid, pid, startDate, endDate);
					log.info("list4="+list4);
					//模板消息
					list5 = cmi424Dao.webapi42402(centerid, pid, startDate, endDate);
					log.info("list5="+list5);
				}else{
					//群发消息
					cntSucc = cMi403Dao.webapi403(centerid, pid, startDate, endDate, "1", "01");
					log.info("cntSucc="+cntSucc);
					cntFail = cMi403Dao.webapi403(centerid, pid, startDate, endDate, "2", "01");
					log.info("cntFail="+cntFail);
					//定制消息
					cntDZSucc = cMi403Dao.webapi403(centerid, pid, startDate, endDate, "1", "02");
					log.info("cntDZSucc="+cntDZSucc);
					cntDZFail = cMi403Dao.webapi403(centerid, pid, startDate, endDate, "2", "02");
					log.info("cntDZFail="+cntDZFail);
					//模板消息
					list2 = cMi403Dao.webapi40301(centerid, pid, startDate, endDate);
					log.info("list2="+list2);
					//mi424表获取历史数据统计信息
					//群发消息 定制消息
					list4 = cmi424Dao.webapi42401(centerid, pid, startDate, endDate);
					log.info("list4="+list4);
					//模板消息
					list5 = cmi424Dao.webapi42402(centerid, pid, startDate, endDate);
					log.info("list5="+list5);
				}
				log.info("微信、app消息推送end");
			}else if("70".equals(channel)){//短信渠道消息推送统计数据
				log.info("短信渠道消息推送统计数据start");
				try{
					String hotLineURL = PropertiesReader.getHeartbeatURL(form.getCenterid(), form.getPid()).trim();
//					String url = hotLineURL + "/countSms.action";
					//add by xzw 2018-03-28
					String url = hotLineURL + "/webapi50028.json";
					 //beginDate,centerId,endDate
					HashMap hashMap = new HashMap();
					//YYYY-MM-dd  (例：2016-12-20)
					log.info("beginDate11111111111111111:"+form.getStartdate());
					hashMap.put("beginDate", form.getStartdate());
					log.info("endDate1111111111111111111:"+form.getEnddate());
					hashMap.put("endDate", form.getEnddate());
					log.info("centerId:"+form.getCenterId());
					hashMap.put("centerId", form.getCenterId());
					log.info("短信平台url：" + url);
					repMessage = msm.sendPost(url, hashMap, "UTF-8");
					//repMessage="{\"recode\":\"000000\",\"fail\":27878,\"sum\":2005542,\"modelMsg\":[{\"modelId\":\"KMDX0001\",\"failNum\":75,\"modelName\":\"短信签约通知\",\"successNum\":6235,\"smid\":\"\",\"sumRecord\":6310},{\"modelId\":\"KMDX0002\",\"failNum\":0,\"modelName\":\"线上提取\",\"successNum\":0,\"smid\":\"\",\"sumRecord\":0},{\"modelId\":\"KMDX0004\",\"failNum\":0,\"modelName\":\"汇补缴\",\"successNum\":4,\"smid\":\"\",\"sumRecord\":4},{\"modelId\":\"KMDX0005\",\"failNum\":0,\"modelName\":\"贷款审批\",\"successNum\":0,\"smid\":\"\",\"sumRecord\":0},{\"modelId\":\"KMDX0006\",\"failNum\":0,\"modelName\":\"公积金委托扣划还贷通知\",\"successNum\":0,\"smid\":\"\",\"sumRecord\":0},{\"modelId\":\"KMDX0007\",\"failNum\":5635,\"modelName\":\"还款提醒\",\"successNum\":628420,\"smid\":\"\",\"sumRecord\":634055},{\"modelId\":\"KMDX0008\",\"failNum\":841,\"modelName\":\"逾期催收\",\"successNum\":44125,\"smid\":\"\",\"sumRecord\":44966},{\"modelId\":\"KMDX0009\",\"failNum\":1003,\"modelName\":\"银行代扣-成功\",\"successNum\":77984,\"smid\":\"\",\"sumRecord\":78987},{\"modelId\":\"KMDX0011\",\"failNum\":699,\"modelName\":\"公积金扣划成功\",\"successNum\":124643,\"smid\":\"\",\"sumRecord\":125342},{\"modelId\":\"KMDX0013\",\"failNum\":0,\"modelName\":\"线上还款\",\"successNum\":0,\"smid\":\"\",\"sumRecord\":0},{\"modelId\":\"KMDX0010\",\"failNum\":757,\"modelName\":\"银行扣款失败\",\"successNum\":25451,\"smid\":\"\",\"sumRecord\":26208},{\"modelId\":\"KMDX0012\",\"failNum\":200,\"modelName\":\"公积金扣划失败\",\"successNum\":40245,\"smid\":\"\",\"sumRecord\":40445},{\"modelId\":\"KMDX0014\",\"failNum\":0,\"modelName\":\"贷款结清\",\"successNum\":0,\"smid\":\"\",\"sumRecord\":0},{\"modelId\":\"KMDX0015\",\"failNum\":0,\"modelName\":\"利率调整\",\"successNum\":0,\"smid\":\"\",\"sumRecord\":0},{\"modelId\":\"KMDX0016\",\"failNum\":74,\"modelName\":\"贷款放款\",\"successNum\":10629,\"smid\":\"\",\"sumRecord\":10703},{\"modelId\":\"KMDX0018\",\"failNum\":13351,\"modelName\":\"短信验证码\",\"successNum\":542973,\"smid\":\"\",\"sumRecord\":556324},{\"modelId\":\"KMDX0019\",\"failNum\":0,\"modelName\":\"线上服务评价\",\"successNum\":0,\"smid\":\"\",\"sumRecord\":0},{\"modelId\":\"KMDX0017\",\"failNum\":0,\"modelName\":\"归集托收\",\"successNum\":0,\"smid\":\"\",\"sumRecord\":0},{\"modelId\":\"KMDX0020\",\"failNum\":5163,\"modelName\":\"还款结果通知\",\"successNum\":469900,\"smid\":\"\",\"sumRecord\":475063},{\"modelId\":\"KM214\",\"failNum\":0,\"modelName\":\"退取\",\"successNum\":23,\"smid\":\"\",\"sumRecord\":23},{\"modelId\":\"KMDX0021\",\"failNum\":0,\"modelName\":\"注册成功\",\"successNum\":0,\"smid\":\"\",\"sumRecord\":0}],\"customizeMsg\":[{\"failNum\":74,\"modelName\":\"综合定制短信\",\"successNum\":6979,\"smid\":\"ZHDZDX\",\"sumRecord\":7053},{\"modelId\":\"\",\"failNum\":6,\"modelName\":\"短信平台定制消息\",\"successNum\":48,\"smid\":\"\",\"sumRecord\":54}],\"success\":1977664,\"msg\":\"成功\"}";
					log.info("repMessage="+repMessage);
				}catch(Exception e){
					e.printStackTrace();
					log.info("短信平台通讯失败！");
				}
				log.info("短信渠道消息推送统计数据end");
			}
			
			//二级JSON拼装变量
			JSONObject outoutObj = new JSONObject();
			outoutObj.put("appname", appname);
			JSONArray outAry = new JSONArray();
			//业务量
			JSONObject Obj1 = new JSONObject();
			Obj1.put("name", "业务量");
			JSONArray transAry1 = new JSONArray();
			//业务成功数
			JSONObject Obj2 = new JSONObject();
			Obj2.put("name", "业务成功数");
			JSONArray transAry2 = new JSONArray();
			//业务失败数
			JSONObject Obj3 = new JSONObject();
			Obj3.put("name", "业务失败数");
			JSONArray transAry3 = new JSONArray();
			log.info("信息发布拼接json开始");
			if("10".equals(channel) || "20".equals(channel)){
				if(list1.size()!=0){
					log.info("公开信息发布");
					JSONObject secondObj1 = new JSONObject();
					secondObj1.put("classTitle", "公开信息发布");
					JSONArray secondAry1 = new JSONArray();
					JSONObject secondObj2 = new JSONObject();
					secondObj2.put("classTitle", "公开信息发布");
					JSONArray secondAry2 = new JSONArray();
					int secondCnt = 0;//二级小计
					for(int i=0; i<list1.size(); i++){
						count++;
						String name = String.valueOf(list1.get(i).get("itemval"));
						int thirdCnt = Integer.parseInt(String.valueOf(list1.get(i).get("cnt")));
						secondCnt += thirdCnt;
						secondAry1.add(createThirdObj(name, thirdCnt));
						secondObj1.put("classValue", secondAry1);
						secondAry2.add(createThirdObj(name,0));
						secondObj2.put("classValue", secondAry2);
						classCount += thirdCnt;
						classSuccess += thirdCnt;
						if(i==list1.size()-1){
							count++;
							secondAry1.add(createThirdObj("", secondCnt));
							secondObj1.put("classValue", secondAry1);
							secondAry2.add(createThirdObj("",0));
							secondObj2.put("classValue", secondAry2);
						}
					}
					transAry1.add(secondObj1);
					transAry2.add(secondObj1);
					transAry3.add(secondObj2);
				}
				if(cntSucc+cntFail!=0 || cntDZSucc+cntDZFail!=0 
						|| list2.size()!=0 || list4.size()!=0 || list5.size()!=0){
					log.info("消息推送");
					JSONObject secondObj1 = new JSONObject();
					secondObj1.put("classTitle", "消息推送");
					JSONArray secondAry1 = new JSONArray();
					JSONObject secondObj2 = new JSONObject();
					secondObj2.put("classTitle", "消息推送");
					JSONArray secondAry2 = new JSONArray();
					JSONObject secondObj3 = new JSONObject();
					secondObj3.put("classTitle", "消息推送");
					JSONArray secondAry3 = new JSONArray();
					int secondCnt = 0;//二级小计
					int secondSuccCnt = 0;//二级小计
					int secondFailCnt = 0;//二级小计
					//mi424表统计的历史群发和定制消息
					int historyQFSucc = 0;
					int historyQFFail = 0;
					int historyQFCnt = 0;
					int historyDZSucc = 0;
					int historyDZFail = 0;
					int historyDZCnt = 0;
					for(int i=0; i<list4.size(); i++){
						HashMap map = list4.get(i);
						if("01".equals(map.get("flag"))){
							historyQFSucc = Integer.valueOf(String.valueOf(map.get("succ")));
							historyQFFail = Integer.valueOf(String.valueOf(map.get("fail")));
							historyQFCnt = Integer.valueOf(String.valueOf(map.get("cnt")));
						}else if("02".equals(map.get("flag"))){
							historyDZSucc = Integer.valueOf(String.valueOf(map.get("succ")));
							historyDZFail = Integer.valueOf(String.valueOf(map.get("fail")));
							historyDZCnt = Integer.valueOf(String.valueOf(map.get("cnt")));
						}
					}
					secondAry1.add(createThirdObj("消息群发", cntSucc+cntFail+historyQFCnt));
					secondObj1.put("classValue", secondAry1);
					secondAry2.add(createThirdObj("消息群发", cntSucc+historyQFSucc));
					secondObj2.put("classValue", secondAry2);
					secondAry3.add(createThirdObj("消息群发", cntFail+historyQFFail));
					secondObj3.put("classValue", secondAry3);
					count++;
					classCount += cntSucc + cntFail + historyQFCnt;
					classSuccess += cntSucc + historyQFSucc;
					classFail += cntFail + historyQFFail;
					secondCnt += cntSucc + cntFail + historyQFCnt;
					secondSuccCnt += cntSucc + historyQFSucc;
					secondFailCnt += cntFail + historyQFFail;
					
					secondAry1.add(createThirdObj("定制推送", cntDZSucc+cntDZFail+historyDZCnt));
					secondObj1.put("classValue", secondAry1);
					secondAry2.add(createThirdObj("定制推送", cntDZSucc+historyDZSucc));
					secondObj2.put("classValue", secondAry2);
					secondAry3.add(createThirdObj("定制推送", cntDZFail+historyDZFail));
					secondObj3.put("classValue", secondAry3);
					count++;
					classCount += cntDZSucc + cntDZFail + historyDZCnt;
					classSuccess += cntDZSucc + historyDZSucc;
					classFail += cntDZFail + historyDZFail;
					secondCnt += cntDZSucc + cntDZFail + historyDZCnt;
					secondSuccCnt += cntDZSucc + historyDZSucc;
					secondFailCnt += cntDZFail + historyDZFail;
					
					//模板消息汇总   list5为历史数据出自mi424，list2是当天数据出自mi403，两处数据先整合汇总
					List<HashMap> listTemplat = new ArrayList();
					for(int i=0; i<list5.size(); i++){
						HashMap map = new HashMap();
						map.put("itemid", list5.get(i).get("itemid"));
						map.put("itemval", list5.get(i).get("itemval"));
						map.put("cnt", list5.get(i).get("cnt"));
						map.put("succ", list5.get(i).get("succ"));
						map.put("fail", list5.get(i).get("fail"));
						listTemplat.add(map);
					}
					log.info("listTemplat1="+listTemplat);
					for(int i=0; i<list2.size(); i++){
						HashMap map = list2.get(i);
						for(int j=0; j<listTemplat.size(); j++){
							HashMap mapTemplat = listTemplat.get(j);
							if(map.get("itemid").equals(mapTemplat.get("itemid"))){
								mapTemplat.put("cnt", Integer.valueOf(String.valueOf(listTemplat.get(j).get("cnt"))) 
										+ Integer.valueOf(String.valueOf(map.get("cnt"))));
								mapTemplat.put("succ", Integer.valueOf(String.valueOf(listTemplat.get(j).get("succ"))) 
										+ Integer.valueOf(String.valueOf(map.get("succ"))));
								mapTemplat.put("fail", Integer.valueOf(String.valueOf(listTemplat.get(j).get("fail"))) 
										+ Integer.valueOf(String.valueOf(map.get("fail"))));
								listTemplat.set(j, mapTemplat);
								break;
							}else if(j==listTemplat.size()-1){
								listTemplat.add(map);
							}
						}
					}
					log.info("listTemplat2="+listTemplat);
					for(int i=0; i<listTemplat.size(); i++){
						String itemval = String.valueOf(listTemplat.get(i).get("itemval"));
						int cntThirdSucc = Integer.valueOf(String.valueOf(listTemplat.get(i).get("succ")));
						int cntThirdFail = Integer.valueOf(String.valueOf(listTemplat.get(i).get("fail")));
						secondAry1.add(createThirdObj(itemval, cntThirdSucc+cntThirdFail));
						secondObj1.put("classValue", secondAry1);
						secondAry2.add(createThirdObj(itemval, cntThirdSucc));
						secondObj2.put("classValue", secondAry2);
						secondAry3.add(createThirdObj(itemval, cntThirdFail));
						secondObj3.put("classValue", secondAry3);
						count++;
						classCount += cntThirdSucc+cntThirdFail;
						classSuccess += cntThirdSucc;
						classFail += cntThirdFail;
						secondCnt += cntThirdSucc+cntThirdFail;
						secondSuccCnt += cntThirdSucc;
						secondFailCnt += cntThirdFail;
					}
					count++;//二级小计+1
					secondAry1.add(createThirdObj("", secondCnt));
					secondObj1.put("classValue", secondAry1);
					secondAry2.add(createThirdObj("", secondSuccCnt));
					secondObj2.put("classValue", secondAry2);
					secondAry3.add(createThirdObj("", secondFailCnt));
					secondObj3.put("classValue", secondAry3);
					transAry1.add(secondObj1);
					transAry2.add(secondObj2);
					transAry3.add(secondObj3);
				}
				if(count>0){
					Obj1.put("value", transAry1);
					Obj2.put("value", transAry2);
					Obj3.put("value", transAry3);
					outAry.add(Obj1);
					outAry.add(Obj2);
					outAry.add(Obj3);
					outoutObj.put("data", outAry);
					outoutAry.add(outoutObj);
				}
			}else if("70".equals(channel) && !CommonUtil.isEmpty(repMessage)){
				JSONObject objMessage = JSONObject.fromObject(repMessage);
				if("000000".equals(objMessage.getString("recode")) 
						&& objMessage.getInt("sum")!=0){
					classCount = objMessage.getInt("sum");
					classSuccess = objMessage.getInt("success");
					classFail = objMessage.getInt("fail");
					JSONArray customizeMsg = objMessage.getJSONArray("customizeMsg");
					JSONArray modelMsg = objMessage.getJSONArray("modelMsg");
					
					JSONObject secondObj1 = new JSONObject();
					secondObj1.put("classTitle", "消息推送");
					JSONArray secondAry1 = new JSONArray();
					JSONObject secondObj2 = new JSONObject();
					secondObj2.put("classTitle", "消息推送");
					JSONArray secondAry2 = new JSONArray();
					JSONObject secondObj3 = new JSONObject();
					secondObj2.put("classTitle", "消息推送");
					JSONArray secondAry3 = new JSONArray();
					int secondCnt = 0;//二级小计
					int secondSuccCnt = 0;//二级小计
					int secondFailCnt = 0;//二级小计
					Iterator<Object> customize = customizeMsg.iterator();
					while(customize.hasNext()){
						count++;
						JSONObject objData = (JSONObject)customize.next();
						String thirdName = String.valueOf(objData.get("modelName"));
						int cntThird = Integer.valueOf(String.valueOf(objData.get("sumRecord")));
						int cntThirdSucc = Integer.valueOf(String.valueOf(objData.get("successNum")));
						int cntThirdFail = Integer.valueOf(String.valueOf(objData.get("failNum")));
						secondCnt += cntThird;
						secondSuccCnt += cntThirdSucc;
						secondFailCnt += cntThirdFail;
						secondAry1.add(createThirdObj(thirdName, cntThird));
						secondObj1.put("classValue", secondAry1);
						secondAry2.add(createThirdObj(thirdName, cntThirdSucc));
						secondObj2.put("classValue", secondAry2);
						secondAry3.add(createThirdObj(thirdName, cntThirdFail));
						secondObj3.put("classValue", secondAry3);
					}
					
					Iterator<Object> model = modelMsg.iterator();
					while(model.hasNext()){
						count++;
						JSONObject objData = (JSONObject)model.next();
						JSONObject thirdObj1 = new JSONObject();
						String thirdName = String.valueOf(objData.get("modelName"));
						int cntThird = Integer.valueOf(String.valueOf(objData.get("sumRecord")));
						int cntThirdSucc = Integer.valueOf(String.valueOf(objData.get("successNum")));
						int cntThirdFail = Integer.valueOf(String.valueOf(objData.get("failNum")));
						secondCnt += cntThird;
						secondSuccCnt += cntThirdSucc;
						secondFailCnt += cntThirdFail;
						secondAry1.add(createThirdObj(thirdName, cntThird));
						secondObj1.put("classValue", secondAry1);
						secondAry2.add(createThirdObj(thirdName, cntThirdSucc));
						secondObj2.put("classValue", secondAry2);
						secondAry3.add(createThirdObj(thirdName, cntThirdFail));
						secondObj3.put("classValue", secondAry3);
					}
					count++;
					secondAry1.add(createThirdObj("", secondCnt));
					secondObj1.put("classValue", secondAry1);
					secondAry2.add(createThirdObj("", secondSuccCnt));
					secondObj2.put("classValue", secondAry2);
					secondAry3.add(createThirdObj("", secondFailCnt));
					secondObj3.put("classValue", secondAry3);
					transAry1.add(secondObj1);
					transAry2.add(secondObj2);
					transAry3.add(secondObj3);
					Obj1.put("value", transAry1);
					Obj2.put("value", transAry2);
					Obj3.put("value", transAry3);
					outAry.add(Obj1);
					outAry.add(Obj2);
					outAry.add(Obj3);
					outoutObj.put("data", outAry);
					outoutAry.add(outoutObj);
				}
			}else if("80".equals(channel) && !CommonUtil.isEmpty(repWeibo)){
				JSONObject objWB = JSONObject.fromObject(repWeibo);
				if("0000".equals(objWB.getString("code"))){
					JSONArray arrayWB = objWB.getJSONArray("datas");
					JSONObject secondObj1 = new JSONObject();
					secondObj1.put("classTitle", "公开信息发布");
					JSONArray secondAry1 = new JSONArray();
					JSONObject secondObj2 = new JSONObject();
					secondObj2.put("classTitle", "公开信息发布");
					JSONArray secondAry2 = new JSONArray();
					int secondCnt = 0;//二级小计
					Iterator<Object> it = arrayWB.iterator();
					while(it.hasNext()){
						count++;
						JSONObject objData = (JSONObject)it.next();
						String name = String.valueOf(objData.get("title"));
						int thirdCnt = Integer.valueOf(String.valueOf(objData.get("total_value")));
						secondAry1.add(createThirdObj(name, thirdCnt));
						secondObj1.put("classValue", secondAry1);
						secondAry2.add(createThirdObj(name, 0));
						secondObj2.put("classValue", secondAry2);
						classCount += thirdCnt;
						classSuccess += thirdCnt;
						secondCnt += thirdCnt;
					}
					count++;
					secondAry1.add(createThirdObj("", secondCnt));
					secondObj1.put("classValue", secondAry1);
					secondAry2.add(createThirdObj("", 0));
					secondObj2.put("classValue", secondAry2);
					transAry1.add(secondObj1);
					transAry2.add(secondObj1);
					transAry3.add(secondObj2);
					Obj1.put("value", transAry1);
					Obj2.put("value", transAry2);
					Obj3.put("value", transAry3);
					outAry.add(Obj1);
					outAry.add(Obj2);
					outAry.add(Obj3);
					outoutObj.put("data", outAry);
					outoutAry.add(outoutObj);
				}
			}else{
				if(list1.size()!=0){
					JSONObject secondObj1 = new JSONObject();
					secondObj1.put("classTitle", "公开信息发布");
					JSONArray secondAry1 = new JSONArray();
					JSONObject secondObj2 = new JSONObject();
					secondObj2.put("classTitle", "公开信息发布");
					JSONArray secondAry2 = new JSONArray();
					int secondCnt = 0;//二级小计
					for(int i=0; i<list1.size(); i++){
						count++;
						String name = String.valueOf(list1.get(i).get("itemval"));
						int thirdCnt = Integer.parseInt(String.valueOf(list1.get(i).get("cnt")));
						secondAry1.add(createThirdObj(name, thirdCnt));
						secondObj1.put("classValue", secondAry1);
						secondAry2.add(createThirdObj(name,0));
						secondObj2.put("classValue", secondAry2);
						classCount += thirdCnt;
						classSuccess += thirdCnt;
						secondCnt += thirdCnt;
					}
					count++;
					secondAry1.add(createThirdObj("", secondCnt));
					secondObj1.put("classValue", secondAry1);
					secondAry2.add(createThirdObj("",0));
					secondObj2.put("classValue", secondAry2);
					transAry1.add(secondObj1);
					transAry2.add(secondObj1);
					transAry3.add(secondObj2);
					Obj1.put("value", transAry1);
					Obj2.put("value", transAry2);
					Obj3.put("value", transAry3);
					outAry.add(Obj1);
					outAry.add(Obj2);
					outAry.add(Obj3);
					outoutObj.put("data", outAry);
					outoutAry.add(outoutObj);
				}
			}
			log.info("信息发布拼接json结束");
		}
		obj.put("classType", serviceType);
		obj.put("count", count);
		obj.put("classCount", classCount);
		obj.put("classSuccess", classSuccess);
		obj.put("classFail", classFail);
		obj.put("data", outoutAry);
		return obj;
	}
	
	private JSONObject createThirdObj(String itemName, int value){
		JSONObject obj = new JSONObject();
		obj.put("name", itemName);
		obj.put("value", value);
		return obj;
	}
	
	private void checkCMi107(CMi107 form)throws Exception{
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心为空"+form.getCenterid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),
					"中心为空"+form.getCenterid());
		}
		if(CommonUtil.isEmpty(form.getStartdatetime())){
			log.error(ERROR.PARAMS_NULL.getLogText("开始时间为空"+form.getStartdatetime()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),
					"开始时间为空"+form.getStartdatetime());
		}
		if(CommonUtil.isEmpty(form.getEnddatetime())){
			log.error(ERROR.PARAMS_NULL.getLogText("结束时间为空"+form.getEnddatetime()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),
					"结束时间为空"+form.getEnddatetime());
		}
		
	}
	

	private void checkCMi107Date(CMi107 form)throws Exception{
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心为空"+form.getCenterid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),
					"中心为空"+form.getCenterid());
		}
		if(CommonUtil.isEmpty(form.getStartdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("开始日期为空"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),
					"开始日期为空");
		}
		if(CommonUtil.isEmpty(form.getEnddate())){
			log.error(ERROR.PARAMS_NULL.getLogText("结束日期为空"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),
					"结束日期为空");
		}
		
	}
	

	public void synMi107ToMi099(CMi107 form) throws Exception {
		// TODO Auto-generated method stub
		if(CommonUtil.isEmpty(form.getMitransdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("日期为空"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),
					"日期为空");
		}
		String dayBefore = form.getMitransdate();
		String transdataArr[] = dayBefore.split(",");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dBegin = sdf.parse(transdataArr[0]);
		Date dEnd = sdf.parse(transdataArr[1]);
		List<Date> lDate = CommonUtil.findDates(dBegin, dEnd);
		for (Date date : lDate)
		{
		  synMi099Data(form,sdf.format(date));
		}
	}

	private void synMi099Data(CMi107 form, String dayBefore) throws Exception {

		Mi099DAO mi099Dao = (Mi099DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi099Dao");
		
		
		form.setMitransdate(dayBefore);
		List<HashMap> result = cmi107Dao.webapi1071003(form);
		form.setStartdate(dayBefore);
		form.setEnddate(dayBefore);
		List<HashMap<String,String>> list107 = cmi107Dao.selectWebapi10723(form);
		Mi099Example mi099e = new Mi099Example();
		Mi099Example.Criteria mi099c = mi099e.createCriteria();
		mi099c.andTransdateGreaterThanOrEqualTo(form.getStartdate()).andTransdateLessThanOrEqualTo(form.getStartdate());
		List<Mi099> mi099List = cmi099Dao.selectByExample(mi099e);
		for(int i=0; i<result.size(); i++){
			HashMap map1 = result.get(i);
			String centerid = map1.get("centerid").toString();
			String transtype = map1.get("transtype").toString();
			String money = map1.get("money").toString();
			String pid = map1.get("pid").toString();
			String freeuse1 = map1.get("freeuse1").toString();
			String cnt = map1.get("cnt").toString();
			String channel = map1.get("channel").toString();
			String servicetype = map1.get("servicetype").toString();
			String serviceid = map1.get("serviceid").toString();
			String dicid = map1.get("dicid").toString();
			String itemid = map1.get("itemid").toString();
			String moneytype = map1.get("moneytype").toString();
			boolean temp = true;
			for(Mi099 mi099:mi099List)
			{
				if(centerid.equals(mi099.getCenterid())
						&&channel.equals(mi099.getChannel())
						&&pid.equals(mi099.getPid())
						&&transtype.equals(mi099.getBuztype()))
				{
					//根据业务统计前一天时间段数据
					String dayTimeCnt = dayTimeCntByBuzType(mi099,list107);
					mi099.setFreeuse2(dayTimeCnt);
					mi099.setFreeuse4(Integer.parseInt(moneytype));
					mi099Dao.updateByPrimaryKey(mi099);
					temp = false;
				}
			}
			if(temp)
			{
				HashMap map2 = null;
				Mi099 record = new Mi099();
				record.setId(Integer.valueOf(CommonUtil.genKeyStatic("MI099",10)));
				record.setBuztype(transtype);
				record.setCenterid(centerid);
				record.setChannel(channel);
				record.setPid(pid);
				record.setServiceid(serviceid);
				record.setServicetype(servicetype);
				record.setSubtype(itemid);
				record.setTransdate(dayBefore);
				record.setValidflag("1");
				record.setDatecreated(CommonUtil.getSystemDate());
				record.setDatemodified(CommonUtil.getSystemDate());
				record.setFreeuse1(dicid);
				record.setFreeuse4(Integer.parseInt(moneytype));
				//最后一条数据
				if(i==result.size()-1){
					//本条数据为成功统计
					if("0".equals(freeuse1)){
						record.setFailcnt(0);
						record.setSucccnt(Integer.valueOf(cnt));
						record.setTransamt(Double.valueOf(money));
						record.setTranscnt(Integer.valueOf(cnt));
					}else{
						record.setFailcnt(Integer.valueOf(cnt));
						record.setSucccnt(0);
						record.setTransamt(0.0);
						record.setTranscnt(Integer.valueOf(cnt));
					}
				}else{
					map2 = result.get(i+1);
					String centerid_2 = map2.get("centerid").toString();
					log.info("centerid_2："+centerid_2);
					String transtype_2 = map2.get("transtype").toString();
					log.info("transtype_2："+transtype_2);
					String pid_2 = map2.get("pid").toString();
					log.info("pid_2："+pid_2);
					String cnt_2 = map2.get("cnt").toString();
					log.info("cnt_2："+cnt_2);
					String money_2 = map2.get("money").toString();
					log.info("money_2："+money_2);
					//下一条与本条数据合并，中心、服务、渠道相同，一条为成功统计，一条为失败统计
					if(centerid.equals(centerid_2) && transtype.equals(transtype_2) && pid.equals(pid_2)){
						if("0".equals(freeuse1)){
							record.setFailcnt(Integer.valueOf(cnt_2));
							record.setSucccnt(Integer.valueOf(cnt));
							record.setTransamt(Double.valueOf(money));
						}else{
							record.setFailcnt(Integer.valueOf(cnt));
							record.setSucccnt(Integer.valueOf(cnt_2));
							record.setTransamt(Double.valueOf(money_2));
						}
						record.setTranscnt(Integer.valueOf(cnt)+Integer.valueOf(cnt_2));
						//统计结果合并后循环跳过下一条数据
						i++;
					}else{
						if("0".equals(freeuse1)){
							record.setFailcnt(0);
							record.setSucccnt(Integer.valueOf(cnt));
							record.setTransamt(Double.valueOf(money));
							record.setTranscnt(Integer.valueOf(cnt));
						}else{
							record.setFailcnt(Integer.valueOf(cnt));
							record.setSucccnt(0);
							record.setTransamt(0.0);
							record.setTranscnt(Integer.valueOf(cnt));
						}
					}
				}
				//根据业务统计前一天时间段数据
				String dayTimeCnt = dayTimeCntByBuzType(record,list107);
				record.setFreeuse2(dayTimeCnt);
				mi099Dao.insert(record);
				log.info("insert end！");
			}
		}
	}

	private String dayTimeCntByBuzType(Mi099 record,List<HashMap<String,String>> list107) {
		String centerid = record.getCenterid();
		String buzType = record.getBuztype();
		String pid = record.getPid();
		String channel = record.getChannel();
		String transdate = record.getTransdate();
		String[] dayArray = {"00","01","02","03","04","05","06","07","08","09","10","11"
				,"12","13","14","15","16","17","18","19","20","21","22","23"};
		String timeDayString = "";
		if(!CommonUtil.isEmpty(list107))
		{
			for(String str:dayArray){
				boolean temp = true;
				for(HashMap<String,String> hm:list107)
				{
					if(centerid.equals(hm.get("centerid"))&&
							channel.equals(hm.get("channeltype"))&&
							pid.equals(hm.get("pid"))&&
							transdate.equals(hm.get("transdate"))&&
							buzType.equals(hm.get("transtype"))&&str.equals(hm.get("transtime")))
					{
						timeDayString =str+":"+hm.get("value")+","+timeDayString;
						temp = false;
					}
				}
				if(temp)
				{
					timeDayString =str+":"+0+","+timeDayString;
				}
			}
		}
		return timeDayString;
	}
	
	public JSONObject webapi1071003(CMi107 form) throws Exception{
		JSONObject obj = new JSONObject();
		//获取服务四大类线上汇总数据
		List<HashMap> list1 = cmi098Dao.webapi107100301(form);
		int xxcx = 0;
		int ywbl = 0;
		int xxfb = 0;
		int hdjl = 0;
		for(int i=0; i<list1.size(); i++){
			HashMap map = list1.get(i);
			log.info("汇总map："+map);
			int cnt = Integer.valueOf(String.valueOf(map.get("cnt")));
			switch(Integer.valueOf(String.valueOf(map.get("servicetype")))){
				case 1:
					xxcx = cnt;//信息查询
					break;
				case 2:
					ywbl = cnt;//业务办理
					break;
				case 3:
					xxfb = cnt;//信息发布
					break;
				case 4:
					hdjl = cnt;//互动交流
					break;
				default:
					break;
			}
		}
		obj.put("xxcx", xxcx);//信息查询
		obj.put("ywbl", ywbl);//业务办理
		obj.put("xxfb", xxfb);//信息发布
		obj.put("hdjl", hdjl);//互动交流
		obj.put("xszj", xxcx + ywbl + xxfb + hdjl);//线上总计
		
		List<HashMap> list2 = cmi098Dao.webapi107100302(form);
		int jcxsfwl = 0;//缴存线上服务量小计
		int jcxsyw = 0;//缴存线上办结量小计
		int jcxxyw = 0;//缴存线下业务小计
		int tqxsfwl = 0;//提取线上服务量小计
		int tqxsyw = 0;//提取线上办结量小计
		int tqxxyw = 0;//提取线下业务小计
		int dkxsfwl = 0;//贷款线上服务量小计
		int dkxsyw = 0;//贷款线上办结量小计
		int dkxxyw = 0;//贷款线下业务小计
		for(int i=0; i<list2.size(); i++){
			HashMap map = list2.get(i);
			log.info("业务办理小计汇总："+map);
			String itemval = String.valueOf(map.get("itemval"));
			String bsptype = String.valueOf(map.get("bsptype"));
			int cnt = Integer.valueOf(String.valueOf(map.get("cnt")));
			if("缴存申请办理".equals(itemval)
					||"缴存预约办理".equals(itemval)){
				jcxsfwl += cnt;
			}else if("缴存在线办结".equals(itemval)){
				if("0".equals(bsptype)){
					jcxsyw += cnt;
					jcxsfwl += cnt;
				}else{
					jcxxyw += cnt;
				}
			}else if("提取申请办理".equals(itemval)
					||"提取预约办理".equals(itemval)){
				tqxsfwl += cnt;
			}else if("提取在线办结".equals(itemval)){
				if("0".equals(bsptype)){
					tqxsyw += cnt;
					tqxsfwl += cnt;
				}else{
					tqxxyw += cnt;
				}
			}else if("贷款申请办理".equals(itemval)
					||"贷款预约办理".equals(itemval)){
				dkxsfwl += cnt;
			}else if("贷款在线办结".equals(itemval)){	
				if("0".equals(bsptype)){
					dkxsfwl += cnt;
					dkxsyw += cnt;
				}else{
					dkxxyw += cnt;
				}
			}
		}
		JSONArray ary = new JSONArray();
		DecimalFormat df = new DecimalFormat("0.00");//格式化小数 
		JSONObject jc = new JSONObject();
		jc.put("xsfwl", jcxsfwl);
		jc.put("xsyw", jcxsyw);
		jc.put("xxyw", jcxxyw);
		jc.put("xsxx", jcxsfwl + jcxxyw);
		if(jcxsfwl + jcxxyw != 0){
			jc.put("xszb", df.format((float)jcxsyw/(jcxsfwl + jcxxyw)*100)+"%");
		}else{
			jc.put("xszb", "0.00%");
		}
		ary.add(jc);
		JSONObject tq = new JSONObject();
		tq.put("xsfwl", tqxsfwl);
		tq.put("xsyw", tqxsyw);
		tq.put("xxyw", tqxxyw);
		tq.put("xsxx", tqxsfwl + tqxxyw);
		if(tqxsfwl + tqxxyw != 0){
			tq.put("xszb", df.format((float)tqxsyw/(tqxsfwl + tqxxyw)*100)+"%");
		}else{
			tq.put("xszb", "0.00%");
		}
		ary.add(tq);
		JSONObject dk = new JSONObject();
		dk.put("xsfwl", dkxsfwl);
		dk.put("xsyw", dkxsyw);
		dk.put("xxyw", dkxxyw);
		dk.put("xsxx", dkxsfwl + dkxxyw);
		if(dkxsfwl + dkxxyw != 0){
			dk.put("xszb", df.format((float)dkxsyw/(dkxsfwl + dkxxyw)*100)+"%");
		}else{
			dk.put("xszb", "0.00%");
		}
		ary.add(dk);
		JSONObject zj = new JSONObject();
		int xsfwl = jcxsfwl + tqxsfwl + dkxsfwl;
		int xsyw = jcxsyw + tqxsyw + dkxsyw;
		int xxyw = jcxxyw + tqxxyw + dkxxyw;
		zj.put("xsfwl", xsfwl);
		zj.put("xsyw", xsyw);
		zj.put("xxyw", xxyw);
		zj.put("xsxx", xsfwl + xxyw);
		if(xsfwl + xxyw != 0){
			zj.put("xszb", df.format((float)xsyw/(xsfwl + xxyw)*100)+"%");
		}else{
			zj.put("xszb", "0.00%");
		}
		ary.add(zj);
		obj.put("datas", ary);
		log.info("webapi1071003最终结果："+obj);
		return obj;
	}
	public JSONObject webapi1071004(CMi107 form) throws Exception{
		JSONObject obj = new JSONObject();
		List<HashMap> list = cmi098Dao.webapi1071004(form);
		//统计表行数固定
		JSONObject line1 = initChannelDatas();
		JSONObject line2 = initChannelDatas();
		JSONObject line3 = initChannelDatas();
		JSONObject line4 = initChannelDatas();
		JSONObject line5 = initChannelDatas();
		JSONObject line6 = initChannelDatas();
		JSONObject line7 = initChannelDatas();
		JSONObject line8 = initChannelDatas();
		JSONObject line9 = initChannelDatas();
		JSONObject line10 = initChannelDatas();
		JSONObject line11 = initChannelDatas();
		JSONObject line12 = initChannelDatas();
		JSONObject line13 = initChannelDatas();
		JSONObject line14 = initChannelDatas();
		JSONObject line15 = initChannelDatas();
		JSONObject line16 = initChannelDatas();
		JSONObject line17 = initChannelDatas();
		JSONObject line18 = initChannelDatas();
		JSONObject line19 = initChannelDatas();
		JSONObject line20 = initChannelDatas();
		JSONObject line21 = initChannelDatas();
		JSONObject line22 = initChannelDatas();
		JSONObject line23 = initChannelDatas();
		JSONObject line24 = initChannelDatas();
		JSONObject line25 = initChannelDatas();
		JSONObject line26 = initChannelDatas();
		JSONObject line27 = initChannelDatas();
		JSONObject line28 = initChannelDatas();
		JSONObject line29 = initChannelDatas();
		JSONObject line30 = initChannelDatas();
		JSONObject line31 = initChannelDatas();
		JSONObject line32 = initChannelDatas();
		//每行数据汇总
		int line1Cnt = 0;
		int line2Cnt = 0;
		int line3Cnt = 0;
		int line4Cnt = 0;
		int line5Cnt = 0;
		int line6Cnt = 0;
		int line7Cnt = 0;
		int line8Cnt = 0;//信息查询小计
		int line9Cnt = 0;
		int line10Cnt = 0;
		int line11Cnt = 0;
		int line12Cnt = 0;//信息发布小计
		int line13Cnt = 0;
		int line14Cnt = 0;
		int line15Cnt = 0;
		int line16Cnt = 0;
		int line17Cnt = 0;//互动交流小计
		int line18Cnt = 0;//缴存小计
		int line19Cnt = 0;
		int line20Cnt = 0;
		int line21Cnt = 0;
		int line22Cnt = 0;//提取小计
		int line23Cnt = 0;
		int line24Cnt = 0;
		int line25Cnt = 0;
		int line26Cnt = 0;//贷款小计
		int line27Cnt = 0;
		int line28Cnt = 0;
		int line29Cnt = 0;
		int line30Cnt = 0;
		int line31Cnt = 0;//业务办理小计
		int line32Cnt = 0;//总计
		for(int i=0; i<list.size(); i++){
			HashMap map = list.get(i);
			log.info("map="+map);
			String pid = String.valueOf(map.get("pid"));
			int cnt = Integer.valueOf(String.valueOf(map.get("cnt")));
			String itemval = String.valueOf(map.get("itemval"));
			String servicetype = String.valueOf(map.get("servicetype"));
			if("1".equals(servicetype)){
				line8Cnt += cnt;
				log.info("line8Cnt="+line8Cnt);
				if("公开信息查询".equals(itemval)){
					line1 = updateLineObject(pid, line1, cnt);
					line1Cnt += cnt;
					log.info("line1Cnt="+line1Cnt);
				}else if("个人信息查询".equals(itemval)){
					line2 = updateLineObject(pid, line2, cnt);
					line2Cnt += cnt;
					log.info("line2Cnt="+line2Cnt);
				}else if("单位信息查询".equals(itemval)){	
					line3 = updateLineObject(pid, line3, cnt);
					line3Cnt += cnt;
					log.info("line3Cnt="+line3Cnt);
				}else if("凭证、单据打印".equals(itemval)){	
					line4 = updateLineObject(pid, line4, cnt);
					line4Cnt += cnt;
					log.info("line4Cnt="+line4Cnt);
				}else if("业务办理进度".equals(itemval)){	
					line5 = updateLineObject(pid, line5, cnt);
					line5Cnt += cnt;
					log.info("line5Cnt="+line5Cnt);
				}else if("发布文件下载".equals(itemval)){	
					line6 = updateLineObject(pid, line6, cnt);
					line6Cnt += cnt;
					log.info("line6Cnt="+line6Cnt);
				}else if("其他".equals(itemval)){	
					line7 = updateLineObject(pid, line7, cnt);
					line7Cnt += cnt;
					log.info("line7Cnt="+line7Cnt);
				}
			}else if("2".equals(servicetype)){
				line31Cnt += cnt;
				log.info("line31Cnt="+line31Cnt);
				if("缴存申请办理".equals(itemval)){
					line20 = updateLineObject(pid, line20, cnt);
					line20Cnt += cnt;
					line18Cnt += cnt;
					log.info("line20Cnt="+line20Cnt);
					log.info("line18Cnt="+line18Cnt);
				}else if("缴存预约办理".equals(itemval)){
					line19 = updateLineObject(pid, line19, cnt);
					line19Cnt += cnt;
					line18Cnt += cnt;
					log.info("line19Cnt="+line19Cnt);
					log.info("line18Cnt="+line18Cnt);
				}else if("缴存在线办结".equals(itemval)){
					line21 = updateLineObject(pid, line21, cnt);
					line21Cnt += cnt;
					line18Cnt += cnt;
					log.info("line21Cnt="+line21Cnt);
					log.info("line18Cnt="+line18Cnt);
				}else if("提取申请办理".equals(itemval)){
					line24 = updateLineObject(pid, line24, cnt);
					line24Cnt += cnt;
					line22Cnt += cnt;
					log.info("line24Cnt="+line24Cnt);
					log.info("line22Cnt="+line22Cnt);
				}else if("提取预约办理".equals(itemval)){
					line23 = updateLineObject(pid, line23, cnt);
					line23Cnt += cnt;
					line22Cnt += cnt;
					log.info("line23Cnt="+line23Cnt);
					log.info("line22Cnt="+line22Cnt);
				}else if("提取在线办结".equals(itemval)){
					line25 = updateLineObject(pid, line25, cnt);
					line25Cnt += cnt;
					line22Cnt += cnt;
					log.info("line25Cnt="+line25Cnt);
					log.info("line22Cnt="+line22Cnt);
				}else if("贷款申请办理".equals(itemval)){
					line28 = updateLineObject(pid, line28, cnt);
					line28Cnt += cnt;
					line26Cnt += cnt;
					log.info("line28Cnt="+line28Cnt);
					log.info("line26Cnt="+line26Cnt);
				}else if("贷款预约办理".equals(itemval)){
					line27 = updateLineObject(pid, line27, cnt);
					line27Cnt += cnt;
					line26Cnt += cnt;
					log.info("line27Cnt="+line27Cnt);
					log.info("line26Cnt="+line26Cnt);
				}else if("贷款在线办结".equals(itemval)){
					line29 = updateLineObject(pid, line29, cnt);
					line29Cnt += cnt;
					line26Cnt += cnt;
					log.info("line29Cnt="+line29Cnt);
					log.info("line26Cnt="+line26Cnt);
				}else if("其它".equals(itemval)){
					line30 = updateLineObject(pid, line30, cnt);
					line30Cnt += cnt;
					log.info("line30Cnt="+line30Cnt);
				}
			}else if("3".equals(servicetype)){
				if("公开信息发布".equals(itemval)){
					line9 = updateLineObject(pid, line9, cnt);
					line9Cnt += cnt;
					line12Cnt += cnt;
					log.info("line9Cnt="+line9Cnt);
					log.info("line12Cnt="+line12Cnt);
				}else if("消息推送".equals(itemval)){
					line10 = updateLineObject(pid, line10, cnt);
					line10Cnt += cnt;
					line12Cnt += cnt;
					log.info("line10Cnt="+line10Cnt);
					log.info("line12Cnt="+line12Cnt);
				}else if("其他".equals(itemval)){
					line11 = updateLineObject(pid, line11, cnt);
					line11Cnt += cnt;
					line12Cnt += cnt;
					log.info("line11Cnt="+line11Cnt);
					log.info("line12Cnt="+line12Cnt);
				}
			}else if("4".equals(servicetype)){
				if("业务咨询".equals(itemval)){
					line13 = updateLineObject(pid, line13, cnt);
					line13Cnt += cnt;
					line17Cnt += cnt;
					log.info("line13Cnt="+line13Cnt);
					log.info("line17Cnt="+line17Cnt);
				}else if("投诉、建议".equals(itemval)){
					line15 = updateLineObject(pid, line15, cnt);
					line15Cnt += cnt;
					line17Cnt += cnt;
					log.info("line15Cnt="+line15Cnt);
					log.info("line17Cnt="+line17Cnt);
				}else if("在线调查/问卷调查".equals(itemval)){
					line14 = updateLineObject(pid, line14, cnt);
					line14Cnt += cnt;
					line17Cnt += cnt;
					log.info("line14Cnt="+line14Cnt);
					log.info("line17Cnt="+line17Cnt);
				}else if("服务评价".equals(itemval)){
					line16 = updateLineObject(pid, line16, cnt);
					line16Cnt += cnt;
					line17Cnt += cnt;
					log.info("line16Cnt="+line16Cnt);
					log.info("line17Cnt="+line17Cnt);
				}
			}
		}
		//添加每行总计统计
		line1.put("zjVal", line1Cnt);
		line2.put("zjVal", line2Cnt);
		line3.put("zjVal", line3Cnt);
		line4.put("zjVal", line4Cnt);
		line5.put("zjVal", line5Cnt);
		line6.put("zjVal", line6Cnt);
		line7.put("zjVal", line7Cnt);
		
		line9.put("zjVal", line9Cnt);
		line10.put("zjVal", line10Cnt);
		line11.put("zjVal", line11Cnt);
		
		line13.put("zjVal", line13Cnt);
		line14.put("zjVal", line14Cnt);
		line15.put("zjVal", line15Cnt);
		line16.put("zjVal", line16Cnt);
		
		line19.put("zjVal", line19Cnt);
		line20.put("zjVal", line20Cnt);
		line21.put("zjVal", line21Cnt);
		line23.put("zjVal", line23Cnt);
		line24.put("zjVal", line24Cnt);
		line25.put("zjVal", line25Cnt);
		line27.put("zjVal", line27Cnt);
		line28.put("zjVal", line28Cnt);
		line29.put("zjVal", line29Cnt);
		line30.put("zjVal", line30Cnt);
		//信息查询小计行统计
		line8.put("hotLineVal", Integer.valueOf(String.valueOf(line1.get("hotLineVal"))) 
				+ Integer.valueOf(String.valueOf(line2.get("hotLineVal")))
				+ Integer.valueOf(String.valueOf(line3.get("hotLineVal")))
				+ Integer.valueOf(String.valueOf(line4.get("hotLineVal")))
				+ Integer.valueOf(String.valueOf(line5.get("hotLineVal")))
				+ Integer.valueOf(String.valueOf(line6.get("hotLineVal")))
				+ Integer.valueOf(String.valueOf(line7.get("hotLineVal"))));
		line8.put("msgVal", Integer.valueOf(String.valueOf(line1.get("msgVal"))) 
				+ Integer.valueOf(String.valueOf(line2.get("msgVal")))
				+ Integer.valueOf(String.valueOf(line3.get("msgVal")))
				+ Integer.valueOf(String.valueOf(line4.get("msgVal")))
				+ Integer.valueOf(String.valueOf(line5.get("msgVal")))
				+ Integer.valueOf(String.valueOf(line6.get("msgVal")))
				+ Integer.valueOf(String.valueOf(line7.get("msgVal"))));
		line8.put("webVal", Integer.valueOf(String.valueOf(line1.get("webVal"))) 
				+ Integer.valueOf(String.valueOf(line2.get("webVal")))
				+ Integer.valueOf(String.valueOf(line3.get("webVal")))
				+ Integer.valueOf(String.valueOf(line4.get("webVal")))
				+ Integer.valueOf(String.valueOf(line5.get("webVal")))
				+ Integer.valueOf(String.valueOf(line6.get("webVal")))
				+ Integer.valueOf(String.valueOf(line7.get("webVal"))));
		line8.put("ishVal", Integer.valueOf(String.valueOf(line1.get("ishVal"))) 
				+ Integer.valueOf(String.valueOf(line2.get("ishVal")))
				+ Integer.valueOf(String.valueOf(line3.get("ishVal")))
				+ Integer.valueOf(String.valueOf(line4.get("ishVal")))
				+ Integer.valueOf(String.valueOf(line5.get("ishVal")))
				+ Integer.valueOf(String.valueOf(line6.get("ishVal")))
				+ Integer.valueOf(String.valueOf(line7.get("ishVal"))));
		line8.put("selfVal", Integer.valueOf(String.valueOf(line1.get("selfVal"))) 
				+ Integer.valueOf(String.valueOf(line2.get("selfVal")))
				+ Integer.valueOf(String.valueOf(line3.get("selfVal")))
				+ Integer.valueOf(String.valueOf(line4.get("selfVal")))
				+ Integer.valueOf(String.valueOf(line5.get("selfVal")))
				+ Integer.valueOf(String.valueOf(line6.get("selfVal")))
				+ Integer.valueOf(String.valueOf(line7.get("selfVal"))));
		line8.put("appVal", Integer.valueOf(String.valueOf(line1.get("appVal"))) 
				+ Integer.valueOf(String.valueOf(line2.get("appVal")))
				+ Integer.valueOf(String.valueOf(line3.get("appVal")))
				+ Integer.valueOf(String.valueOf(line4.get("appVal")))
				+ Integer.valueOf(String.valueOf(line5.get("appVal")))
				+ Integer.valueOf(String.valueOf(line6.get("appVal")))
				+ Integer.valueOf(String.valueOf(line7.get("appVal"))));
		line8.put("wechatVal", Integer.valueOf(String.valueOf(line1.get("wechatVal"))) 
				+ Integer.valueOf(String.valueOf(line2.get("wechatVal")))
				+ Integer.valueOf(String.valueOf(line3.get("wechatVal")))
				+ Integer.valueOf(String.valueOf(line4.get("wechatVal")))
				+ Integer.valueOf(String.valueOf(line5.get("wechatVal")))
				+ Integer.valueOf(String.valueOf(line6.get("wechatVal")))
				+ Integer.valueOf(String.valueOf(line7.get("wechatVal"))));
		line8.put("weiboVal", Integer.valueOf(String.valueOf(line1.get("weiboVal"))) 
				+ Integer.valueOf(String.valueOf(line2.get("weiboVal")))
				+ Integer.valueOf(String.valueOf(line3.get("weiboVal")))
				+ Integer.valueOf(String.valueOf(line4.get("weiboVal")))
				+ Integer.valueOf(String.valueOf(line5.get("weiboVal")))
				+ Integer.valueOf(String.valueOf(line6.get("weiboVal")))
				+ Integer.valueOf(String.valueOf(line7.get("weiboVal"))));
		line8.put("zjVal", Integer.valueOf(String.valueOf(line1.get("zjVal"))) 
				+ Integer.valueOf(String.valueOf(line2.get("zjVal")))
				+ Integer.valueOf(String.valueOf(line3.get("zjVal")))
				+ Integer.valueOf(String.valueOf(line4.get("zjVal")))
				+ Integer.valueOf(String.valueOf(line5.get("zjVal")))
				+ Integer.valueOf(String.valueOf(line6.get("zjVal")))
				+ Integer.valueOf(String.valueOf(line7.get("zjVal"))));
		//消息发布小计行统计
		line12.put("hotLineVal", Integer.valueOf(String.valueOf(line9.get("hotLineVal"))) 
				+ Integer.valueOf(String.valueOf(line10.get("hotLineVal")))
				+ Integer.valueOf(String.valueOf(line11.get("hotLineVal"))));
		line12.put("msgVal", Integer.valueOf(String.valueOf(line9.get("msgVal"))) 
				+ Integer.valueOf(String.valueOf(line10.get("msgVal")))
				+ Integer.valueOf(String.valueOf(line11.get("msgVal"))));
		line12.put("webVal", Integer.valueOf(String.valueOf(line9.get("webVal"))) 
				+ Integer.valueOf(String.valueOf(line10.get("webVal")))
				+ Integer.valueOf(String.valueOf(line11.get("webVal"))));
		line12.put("ishVal", Integer.valueOf(String.valueOf(line9.get("ishVal"))) 
				+ Integer.valueOf(String.valueOf(line10.get("ishVal")))
				+ Integer.valueOf(String.valueOf(line11.get("ishVal"))));
		line12.put("selfVal", Integer.valueOf(String.valueOf(line9.get("selfVal"))) 
				+ Integer.valueOf(String.valueOf(line10.get("selfVal")))
				+ Integer.valueOf(String.valueOf(line11.get("selfVal"))));
		line12.put("appVal", Integer.valueOf(String.valueOf(line9.get("appVal"))) 
				+ Integer.valueOf(String.valueOf(line10.get("appVal")))
				+ Integer.valueOf(String.valueOf(line11.get("appVal"))));
		line12.put("wechatVal", Integer.valueOf(String.valueOf(line9.get("wechatVal"))) 
				+ Integer.valueOf(String.valueOf(line10.get("wechatVal")))
				+ Integer.valueOf(String.valueOf(line11.get("wechatVal"))));
		line12.put("weiboVal", Integer.valueOf(String.valueOf(line9.get("weiboVal"))) 
				+ Integer.valueOf(String.valueOf(line10.get("weiboVal")))
				+ Integer.valueOf(String.valueOf(line11.get("weiboVal"))));
		line12.put("zjVal", Integer.valueOf(String.valueOf(line9.get("zjVal"))) 
				+ Integer.valueOf(String.valueOf(line10.get("zjVal")))
				+ Integer.valueOf(String.valueOf(line11.get("zjVal"))));
		//互动交流小计行统计
		line17.put("hotLineVal", Integer.valueOf(String.valueOf(line13.get("hotLineVal"))) 
				+ Integer.valueOf(String.valueOf(line14.get("hotLineVal")))
				+ Integer.valueOf(String.valueOf(line15.get("hotLineVal")))
				+ Integer.valueOf(String.valueOf(line16.get("hotLineVal"))));
		line17.put("msgVal", Integer.valueOf(String.valueOf(line13.get("msgVal"))) 
				+ Integer.valueOf(String.valueOf(line14.get("msgVal")))
				+ Integer.valueOf(String.valueOf(line15.get("msgVal")))
				+ Integer.valueOf(String.valueOf(line16.get("msgVal"))));
		line17.put("webVal", Integer.valueOf(String.valueOf(line13.get("webVal"))) 
				+ Integer.valueOf(String.valueOf(line14.get("webVal")))
				+ Integer.valueOf(String.valueOf(line15.get("webVal")))
				+ Integer.valueOf(String.valueOf(line16.get("webVal"))));
		line17.put("ishVal", Integer.valueOf(String.valueOf(line13.get("ishVal"))) 
				+ Integer.valueOf(String.valueOf(line14.get("ishVal")))
				+ Integer.valueOf(String.valueOf(line15.get("ishVal")))
				+ Integer.valueOf(String.valueOf(line16.get("ishVal"))));
		line17.put("selfVal", Integer.valueOf(String.valueOf(line13.get("selfVal"))) 
				+ Integer.valueOf(String.valueOf(line14.get("selfVal")))
				+ Integer.valueOf(String.valueOf(line15.get("selfVal")))
				+ Integer.valueOf(String.valueOf(line16.get("selfVal"))));
		line17.put("appVal", Integer.valueOf(String.valueOf(line13.get("appVal"))) 
				+ Integer.valueOf(String.valueOf(line14.get("appVal")))
				+ Integer.valueOf(String.valueOf(line15.get("appVal")))
				+ Integer.valueOf(String.valueOf(line16.get("appVal"))));
		line17.put("wechatVal", Integer.valueOf(String.valueOf(line13.get("wechatVal"))) 
				+ Integer.valueOf(String.valueOf(line14.get("wechatVal")))
				+ Integer.valueOf(String.valueOf(line15.get("wechatVal")))
				+ Integer.valueOf(String.valueOf(line16.get("wechatVal"))));
		line17.put("weiboVal", Integer.valueOf(String.valueOf(line13.get("weiboVal"))) 
				+ Integer.valueOf(String.valueOf(line14.get("weiboVal")))
				+ Integer.valueOf(String.valueOf(line15.get("weiboVal")))
				+ Integer.valueOf(String.valueOf(line16.get("weiboVal"))));
		line17.put("zjVal", Integer.valueOf(String.valueOf(line13.get("zjVal"))) 
				+ Integer.valueOf(String.valueOf(line14.get("zjVal")))
				+ Integer.valueOf(String.valueOf(line15.get("zjVal")))
				+ Integer.valueOf(String.valueOf(line16.get("zjVal"))));
		//缴存小计行统计
		line18.put("hotLineVal", Integer.valueOf(String.valueOf(line19.get("hotLineVal"))) 
				+ Integer.valueOf(String.valueOf(line20.get("hotLineVal")))
				+ Integer.valueOf(String.valueOf(line21.get("hotLineVal"))));
		line18.put("msgVal", Integer.valueOf(String.valueOf(line19.get("msgVal"))) 
				+ Integer.valueOf(String.valueOf(line20.get("msgVal")))
				+ Integer.valueOf(String.valueOf(line21.get("msgVal"))));
		line18.put("webVal", Integer.valueOf(String.valueOf(line19.get("webVal"))) 
				+ Integer.valueOf(String.valueOf(line20.get("webVal")))
				+ Integer.valueOf(String.valueOf(line21.get("webVal"))));
		line18.put("ishVal", Integer.valueOf(String.valueOf(line19.get("ishVal"))) 
				+ Integer.valueOf(String.valueOf(line20.get("ishVal")))
				+ Integer.valueOf(String.valueOf(line21.get("ishVal"))));
		line18.put("selfVal", Integer.valueOf(String.valueOf(line19.get("selfVal"))) 
				+ Integer.valueOf(String.valueOf(line20.get("selfVal")))
				+ Integer.valueOf(String.valueOf(line21.get("selfVal"))));
		line18.put("appVal", Integer.valueOf(String.valueOf(line19.get("appVal"))) 
				+ Integer.valueOf(String.valueOf(line20.get("appVal")))
				+ Integer.valueOf(String.valueOf(line21.get("appVal"))));
		line18.put("wechatVal", Integer.valueOf(String.valueOf(line19.get("wechatVal"))) 
				+ Integer.valueOf(String.valueOf(line20.get("wechatVal")))
				+ Integer.valueOf(String.valueOf(line21.get("wechatVal"))));
		line18.put("weiboVal", Integer.valueOf(String.valueOf(line19.get("weiboVal"))) 
				+ Integer.valueOf(String.valueOf(line20.get("weiboVal")))
				+ Integer.valueOf(String.valueOf(line21.get("weiboVal"))));
		line18.put("zjVal", Integer.valueOf(String.valueOf(line19.get("zjVal"))) 
				+ Integer.valueOf(String.valueOf(line20.get("zjVal")))
				+ Integer.valueOf(String.valueOf(line21.get("zjVal"))));
		//提取小计行统计
		line22.put("hotLineVal", Integer.valueOf(String.valueOf(line23.get("hotLineVal"))) 
				+ Integer.valueOf(String.valueOf(line24.get("hotLineVal")))
				+ Integer.valueOf(String.valueOf(line25.get("hotLineVal"))));
		line22.put("msgVal", Integer.valueOf(String.valueOf(line23.get("msgVal"))) 
				+ Integer.valueOf(String.valueOf(line24.get("msgVal")))
				+ Integer.valueOf(String.valueOf(line25.get("msgVal"))));
		line22.put("webVal", Integer.valueOf(String.valueOf(line23.get("webVal"))) 
				+ Integer.valueOf(String.valueOf(line24.get("webVal")))
				+ Integer.valueOf(String.valueOf(line25.get("webVal"))));
		line22.put("ishVal", Integer.valueOf(String.valueOf(line23.get("ishVal"))) 
				+ Integer.valueOf(String.valueOf(line24.get("ishVal")))
				+ Integer.valueOf(String.valueOf(line25.get("ishVal"))));
		line22.put("selfVal", Integer.valueOf(String.valueOf(line23.get("selfVal"))) 
				+ Integer.valueOf(String.valueOf(line24.get("selfVal")))
				+ Integer.valueOf(String.valueOf(line25.get("selfVal"))));
		line22.put("appVal", Integer.valueOf(String.valueOf(line23.get("appVal"))) 
				+ Integer.valueOf(String.valueOf(line24.get("appVal")))
				+ Integer.valueOf(String.valueOf(line25.get("appVal"))));
		line22.put("wechatVal", Integer.valueOf(String.valueOf(line23.get("wechatVal"))) 
				+ Integer.valueOf(String.valueOf(line24.get("wechatVal")))
				+ Integer.valueOf(String.valueOf(line25.get("wechatVal"))));
		line22.put("weiboVal", Integer.valueOf(String.valueOf(line23.get("weiboVal"))) 
				+ Integer.valueOf(String.valueOf(line24.get("weiboVal")))
				+ Integer.valueOf(String.valueOf(line25.get("weiboVal"))));
		line22.put("zjVal", Integer.valueOf(String.valueOf(line23.get("zjVal"))) 
				+ Integer.valueOf(String.valueOf(line24.get("zjVal")))
				+ Integer.valueOf(String.valueOf(line25.get("zjVal"))));
		//贷款小计行统计
		line26.put("hotLineVal", Integer.valueOf(String.valueOf(line27.get("hotLineVal"))) 
				+ Integer.valueOf(String.valueOf(line28.get("hotLineVal")))
				+ Integer.valueOf(String.valueOf(line29.get("hotLineVal"))));
		line26.put("msgVal", Integer.valueOf(String.valueOf(line27.get("msgVal"))) 
				+ Integer.valueOf(String.valueOf(line28.get("msgVal")))
				+ Integer.valueOf(String.valueOf(line29.get("msgVal"))));
		line26.put("webVal", Integer.valueOf(String.valueOf(line27.get("webVal"))) 
				+ Integer.valueOf(String.valueOf(line28.get("webVal")))
				+ Integer.valueOf(String.valueOf(line29.get("webVal"))));
		line26.put("ishVal", Integer.valueOf(String.valueOf(line27.get("ishVal"))) 
				+ Integer.valueOf(String.valueOf(line28.get("ishVal")))
				+ Integer.valueOf(String.valueOf(line29.get("ishVal"))));
		line26.put("selfVal", Integer.valueOf(String.valueOf(line27.get("selfVal"))) 
				+ Integer.valueOf(String.valueOf(line28.get("selfVal")))
				+ Integer.valueOf(String.valueOf(line29.get("selfVal"))));
		line26.put("appVal", Integer.valueOf(String.valueOf(line27.get("appVal"))) 
				+ Integer.valueOf(String.valueOf(line28.get("appVal")))
				+ Integer.valueOf(String.valueOf(line29.get("appVal"))));
		line26.put("wechatVal", Integer.valueOf(String.valueOf(line27.get("wechatVal"))) 
				+ Integer.valueOf(String.valueOf(line28.get("wechatVal")))
				+ Integer.valueOf(String.valueOf(line29.get("wechatVal"))));
		line26.put("weiboVal", Integer.valueOf(String.valueOf(line27.get("weiboVal"))) 
				+ Integer.valueOf(String.valueOf(line28.get("weiboVal")))
				+ Integer.valueOf(String.valueOf(line29.get("weiboVal"))));
		line26.put("zjVal", Integer.valueOf(String.valueOf(line27.get("zjVal"))) 
				+ Integer.valueOf(String.valueOf(line28.get("zjVal")))
				+ Integer.valueOf(String.valueOf(line29.get("zjVal"))));
		//业务办理小计行统计
		line31.put("hotLineVal", Integer.valueOf(String.valueOf(line18.get("hotLineVal"))) 
				+ Integer.valueOf(String.valueOf(line22.get("hotLineVal")))
				+ Integer.valueOf(String.valueOf(line26.get("hotLineVal")))
				+ Integer.valueOf(String.valueOf(line30.get("hotLineVal"))));
		line31.put("msgVal", Integer.valueOf(String.valueOf(line18.get("msgVal"))) 
				+ Integer.valueOf(String.valueOf(line22.get("msgVal")))
				+ Integer.valueOf(String.valueOf(line26.get("msgVal")))
				+ Integer.valueOf(String.valueOf(line30.get("msgVal"))));
		line31.put("webVal", Integer.valueOf(String.valueOf(line18.get("webVal"))) 
				+ Integer.valueOf(String.valueOf(line22.get("webVal")))
				+ Integer.valueOf(String.valueOf(line26.get("webVal")))
				+ Integer.valueOf(String.valueOf(line30.get("webVal"))));
		line31.put("ishVal", Integer.valueOf(String.valueOf(line18.get("ishVal"))) 
				+ Integer.valueOf(String.valueOf(line22.get("ishVal")))
				+ Integer.valueOf(String.valueOf(line26.get("ishVal")))
				+ Integer.valueOf(String.valueOf(line30.get("ishVal"))));
		line31.put("selfVal", Integer.valueOf(String.valueOf(line18.get("selfVal"))) 
				+ Integer.valueOf(String.valueOf(line22.get("selfVal")))
				+ Integer.valueOf(String.valueOf(line26.get("selfVal")))
				+ Integer.valueOf(String.valueOf(line30.get("selfVal"))));
		line31.put("appVal", Integer.valueOf(String.valueOf(line18.get("appVal"))) 
				+ Integer.valueOf(String.valueOf(line22.get("appVal")))
				+ Integer.valueOf(String.valueOf(line26.get("appVal")))
				+ Integer.valueOf(String.valueOf(line30.get("appVal"))));
		line31.put("wechatVal", Integer.valueOf(String.valueOf(line18.get("wechatVal"))) 
				+ Integer.valueOf(String.valueOf(line22.get("wechatVal")))
				+ Integer.valueOf(String.valueOf(line26.get("wechatVal")))
				+ Integer.valueOf(String.valueOf(line30.get("wechatVal"))));
		line31.put("weiboVal", Integer.valueOf(String.valueOf(line18.get("weiboVal"))) 
				+ Integer.valueOf(String.valueOf(line22.get("weiboVal")))
				+ Integer.valueOf(String.valueOf(line26.get("weiboVal")))
				+ Integer.valueOf(String.valueOf(line30.get("weiboVal"))));
		line31.put("zjVal", Integer.valueOf(String.valueOf(line18.get("zjVal"))) 
				+ Integer.valueOf(String.valueOf(line22.get("zjVal")))
				+ Integer.valueOf(String.valueOf(line26.get("zjVal")))
				+ Integer.valueOf(String.valueOf(line30.get("zjVal"))));
		//总计行统计
		line32.put("hotLineVal", Integer.valueOf(String.valueOf(line8.get("hotLineVal"))) 
				+ Integer.valueOf(String.valueOf(line12.get("hotLineVal")))
				+ Integer.valueOf(String.valueOf(line17.get("hotLineVal")))
				+ Integer.valueOf(String.valueOf(line31.get("hotLineVal"))));
		line32.put("msgVal", Integer.valueOf(String.valueOf(line8.get("msgVal"))) 
				+ Integer.valueOf(String.valueOf(line12.get("msgVal")))
				+ Integer.valueOf(String.valueOf(line17.get("msgVal")))
				+ Integer.valueOf(String.valueOf(line31.get("msgVal"))));
		line32.put("webVal", Integer.valueOf(String.valueOf(line8.get("webVal"))) 
				+ Integer.valueOf(String.valueOf(line12.get("webVal")))
				+ Integer.valueOf(String.valueOf(line17.get("webVal")))
				+ Integer.valueOf(String.valueOf(line31.get("webVal"))));
		line32.put("ishVal", Integer.valueOf(String.valueOf(line8.get("ishVal"))) 
				+ Integer.valueOf(String.valueOf(line12.get("ishVal")))
				+ Integer.valueOf(String.valueOf(line17.get("ishVal")))
				+ Integer.valueOf(String.valueOf(line31.get("ishVal"))));
		line32.put("selfVal", Integer.valueOf(String.valueOf(line8.get("selfVal"))) 
				+ Integer.valueOf(String.valueOf(line12.get("selfVal")))
				+ Integer.valueOf(String.valueOf(line17.get("selfVal")))
				+ Integer.valueOf(String.valueOf(line31.get("selfVal"))));
		line32.put("appVal", Integer.valueOf(String.valueOf(line8.get("appVal"))) 
				+ Integer.valueOf(String.valueOf(line12.get("appVal")))
				+ Integer.valueOf(String.valueOf(line17.get("appVal")))
				+ Integer.valueOf(String.valueOf(line31.get("appVal"))));
		line32.put("wechatVal", Integer.valueOf(String.valueOf(line8.get("wechatVal"))) 
				+ Integer.valueOf(String.valueOf(line12.get("wechatVal")))
				+ Integer.valueOf(String.valueOf(line17.get("wechatVal")))
				+ Integer.valueOf(String.valueOf(line31.get("wechatVal"))));
		line32.put("weiboVal", Integer.valueOf(String.valueOf(line8.get("weiboVal"))) 
				+ Integer.valueOf(String.valueOf(line12.get("weiboVal")))
				+ Integer.valueOf(String.valueOf(line17.get("weiboVal")))
				+ Integer.valueOf(String.valueOf(line31.get("weiboVal"))));
		line32.put("zjVal", Integer.valueOf(String.valueOf(line8.get("zjVal"))) 
				+ Integer.valueOf(String.valueOf(line12.get("zjVal")))
				+ Integer.valueOf(String.valueOf(line17.get("zjVal")))
				+ Integer.valueOf(String.valueOf(line31.get("zjVal"))));
		
		//计算占比
		log.info("开始计算占比");
		int zjVal = Integer.valueOf(String.valueOf(line32.get("zjVal")));
		line1 = calcRate(line1, zjVal);
		line2 = calcRate(line2, zjVal);
		line3 = calcRate(line3, zjVal);
		line4 = calcRate(line4, zjVal);
		line5 = calcRate(line5, zjVal);
		line6 = calcRate(line6, zjVal);
		line7 = calcRate(line7, zjVal);
		line8 = calcRate(line8, zjVal);
		line9 = calcRate(line9, zjVal);
		line10 = calcRate(line10, zjVal);
		line11 = calcRate(line11, zjVal);
		line12 = calcRate(line12, zjVal);
		line13 = calcRate(line13, zjVal);
		line14 = calcRate(line14, zjVal);
		line15 = calcRate(line15, zjVal);
		line16 = calcRate(line16, zjVal);
		line17 = calcRate(line17, zjVal);
		line18 = calcRate(line18, zjVal);
		line19 = calcRate(line19, zjVal);
		line20 = calcRate(line20, zjVal);
		line21 = calcRate(line21, zjVal);
		line22 = calcRate(line22, zjVal);
		line23 = calcRate(line23, zjVal);
		line24 = calcRate(line24, zjVal);
		line25 = calcRate(line25, zjVal);
		line26 = calcRate(line26, zjVal);
		line27 = calcRate(line27, zjVal);
		line28 = calcRate(line28, zjVal);
		line29 = calcRate(line29, zjVal);
		line30 = calcRate(line30, zjVal);
		line31 = calcRate(line31, zjVal);
		line32 = calcRate(line32, zjVal);
		
		JSONArray ary = new JSONArray();
		ary.add(line1);
		ary.add(line2);
		ary.add(line3);
		ary.add(line4);
		ary.add(line5);
		ary.add(line6);
		ary.add(line7);
		ary.add(line8);
		ary.add(line9);
		ary.add(line10);
		ary.add(line11);
		ary.add(line12);
		ary.add(line13);
		ary.add(line14);
		ary.add(line15);
		ary.add(line16);
		ary.add(line17);
		ary.add(line18);
		ary.add(line19);
		ary.add(line20);
		ary.add(line21);
		ary.add(line22);
		ary.add(line23);
		ary.add(line24);
		ary.add(line25);
		ary.add(line26);
		ary.add(line27);
		ary.add(line28);
		ary.add(line29);
		ary.add(line30);
		ary.add(line31);
		ary.add(line32);
		obj.put("datas", ary);
		log.info("webapi1004最终结果："+obj);
		return obj;
	}
	private JSONObject initChannelDatas(){
		JSONObject obj = new JSONObject();
		obj.put("hotLineVal", 0);
		obj.put("hotLineRate", "0.00%");
		obj.put("msgVal", 0);
		obj.put("msgRate", "0.00%");
		obj.put("webVal", 0);
		obj.put("webRate", "0.00%");
		obj.put("ishVal", 0);
		obj.put("ishRate", "0.00%");
		obj.put("selfVal", 0);
		obj.put("selfRate", "0.00%");
		obj.put("appVal", 0);
		obj.put("appRate", "0.00%");
		obj.put("wechatVal", 0);
		obj.put("wechatRate", "0.00%");
		obj.put("weiboVal", 0);
		obj.put("weiboRate", "0.00%");
		obj.put("zjVal", 0);
		obj.put("zjRate", "0.00%");
		return obj;
	}
	private JSONObject updateLineObject(String pid, JSONObject line, int cnt){
		switch(Integer.valueOf(pid.substring(0,2))){
		case 10:
			line.put("appVal", cnt);
			break;
		case 20:
			line.put("wechatVal", cnt);
			break;
		case 30:
			line.put("webVal", cnt);
			break;
		case 40:
			line.put("ishVal", cnt);
			break;
		case 50:
			line.put("selfVal", cnt);
			break;
		case 60:
			line.put("hotLineVal", cnt);
			break;
		case 70:
			line.put("msgVal", cnt);
			break;
		case 80:
			line.put("weiboVal", cnt);
			break;
		default:
			break;
		}
		return line;
	}
	private JSONObject calcRate(JSONObject line, int zjVal){
		int lineTotal = line.getInt("zjVal");
		DecimalFormat df = new DecimalFormat("0.00");//格式化小数 
		if(lineTotal != 0){
			line.put("hotLineRate", df.format((float)line.getInt("hotLineVal")/lineTotal*100)+"%");
			line.put("msgRate", df.format((float)line.getInt("msgVal")/lineTotal*100)+"%");
			line.put("webRate", df.format((float)line.getInt("webVal")/lineTotal*100)+"%");
			line.put("ishRate", df.format((float)line.getInt("ishVal")/lineTotal*100)+"%");
			line.put("selfRate", df.format((float)line.getInt("selfVal")/lineTotal*100)+"%");
			line.put("appRate", df.format((float)line.getInt("appVal")/lineTotal*100)+"%");
			line.put("wechatRate", df.format((float)line.getInt("wechatVal")/lineTotal*100)+"%");
			line.put("weiboRate", df.format((float)line.getInt("weiboVal")/lineTotal*100)+"%");
			line.put("zjRate", df.format((float)line.getInt("zjVal")/zjVal*100)+"%");
		}
		return line;
	}
	
	public List<HashMap> contentUpdate(CMi107 form) throws Exception{
		return cmi701Dao.webapi701_01(form.getCenterId(), form.getStartdate(), form.getEnddate());
	}
	public CMi040DAO getCmi040DAO() {
		return cmi040DAO;
	}

	public void setCmi040DAO(CMi040DAO cmi040dao) {
		cmi040DAO = cmi040dao;
	}

	public CMi031DAO getCmi031DAO() {
		return cmi031DAO;
	}

	public void setCmi031DAO(CMi031DAO cmi031dao) {
		cmi031DAO = cmi031dao;
	}

	public Mi029DAO getMi029DAO() {
		return mi029DAO;
	}

	public void setMi029DAO(Mi029DAO mi029dao) {
		mi029DAO = mi029dao;
	}

	public CMi051DAO getCmi051DAO() {
		return cmi051DAO;
	}

	public void setCmi051DAO(CMi051DAO cmi051dao) {
		cmi051DAO = cmi051dao;
	}

	public CMi053DAO getCmi053DAO() {
		return cmi053DAO;
	}

	public void setCmi053DAO(CMi053DAO cmi053dao) {
		cmi053DAO = cmi053dao;
	}

	public Mi709DAO getMi709Dao() {
		return mi709Dao;
	}

	public void setMi709Dao(Mi709DAO mi709Dao) {
		this.mi709Dao = mi709Dao;
	}

	public Mi007DAO getMi007Dao() {
		return mi007Dao;
	}

	public void setMi007Dao(Mi007DAO mi007Dao) {
		this.mi007Dao = mi007Dao;
	}

}
