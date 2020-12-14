package com.yondervision.mi.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.common.message.SimpleHttpMessageUtil;
import com.yondervision.mi.dao.CMi098DAO;
import com.yondervision.mi.dao.CMi099DAO;
import com.yondervision.mi.dao.CMi424DAO;
import com.yondervision.mi.dao.CMi701DAO;
import com.yondervision.mi.dao.CMi712DAO;
import com.yondervision.mi.dao.Mi001DAO;
import com.yondervision.mi.dao.Mi007DAO;
import com.yondervision.mi.dao.Mi011DAO;
import com.yondervision.mi.dao.Mi040DAO;
import com.yondervision.mi.dao.Mi098DAO;
import com.yondervision.mi.dto.Mi001;
import com.yondervision.mi.dto.Mi001Example;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi007Example;
import com.yondervision.mi.dto.Mi011;
import com.yondervision.mi.dto.Mi011Example;
import com.yondervision.mi.dto.Mi040;
import com.yondervision.mi.dto.Mi040Example;
import com.yondervision.mi.dto.Mi098;
import com.yondervision.mi.dto.Mi098Example;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;
import com.yondervision.mi.util.WkfAccessTokenUtil;

public class InsertMi098Statistics {
	private Logger log = LoggerUtil.getLogger();
	private Mi098DAO mi098Dao;
	private Mi007DAO mi007Dao;
	
	public Mi007DAO getMi007Dao() {
		return mi007Dao;
	}

	public void setMi007Dao(Mi007DAO mi007Dao) {
		this.mi007Dao = mi007Dao;
	}

	public Mi098DAO getMi098Dao() {
		return mi098Dao;
	}

	public void setMi098Dao(Mi098DAO mi098Dao) {
		this.mi098Dao = mi098Dao;
	}

	public void insertMi098() throws Exception{
		CMi099DAO cmi099Dao = (CMi099DAO)com.yondervision.mi.util.SpringContextUtil.getBean("cmi099Dao");
		CMi098DAO cmi098Dao = (CMi098DAO)com.yondervision.mi.util.SpringContextUtil.getBean("cmi098Dao");
		Mi001DAO mi001Dao = (Mi001DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi001Dao");
		CMi712DAO cmi712Dao = (CMi712DAO)com.yondervision.mi.util.SpringContextUtil.getBean("cmi712Dao");
		CMi701DAO cmi701Dao = (CMi701DAO)com.yondervision.mi.util.SpringContextUtil.getBean("cmi701Dao"); 
		CMi424DAO cmi424Dao = (CMi424DAO)com.yondervision.mi.util.SpringContextUtil.getBean("cmi424Dao");
		
		//获取mi098表中最后统计的日期
		String lastDate = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "mi098LastDate");
		log.info("properties.properties lastDate="+lastDate);
		List<HashMap> list0 = cmi098Dao.getLatestTransdate();
		if(list0.size()>0){
			lastDate = list0.get(0).get("transday").toString();
		}
		log.info("lastDate="+lastDate);
		
		//获取开始统计前一天交易日期，098表最大日期后一天
		Calendar c = Calendar.getInstance(); 
		Date date=null; 
		date = new SimpleDateFormat("yyyy-MM-dd").parse(lastDate); 
		c.setTime(date); 
		int daystart=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,daystart+1); 
		String transday = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
		
		//获取结束统计前一天交易日期，当前日期前一天
		date = new Date(); 
		c.setTime(date); 
		int dayend=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,dayend-1); 
		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		String endDate = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "mi098endDate");
		log.info("properties.properties endDate="+endDate);
		if(!CommonUtil.isEmpty(endDate)){
			currentDate = endDate;
		}
		
		log.info("transday="+transday);
		log.info("currentDate="+currentDate);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = df.parse(transday);
        Date dt2 = df.parse(currentDate);
        if (dt1.getTime() > dt2.getTime()) {
            log.info("开始日期大于结束日期！");
            return;
        } 
        
		//获取系统中所有中心列表数据
		Mi001Example example = new Mi001Example();
		example.createCriteria().andCenteridIsNotNull().andCenteridNotEqualTo("00000000");
		List<Mi001> mi001List = mi001Dao.selectByExample(example);
		
		//信息查询类，不包含文件下载
		List<HashMap> list1 = cmi099Dao.selectSearchInfo(transday, currentDate);
		for(int i=0; i<list1.size(); i++){
			insertMi098(list1.get(i), "1");
		}
		
		//业务办理类，预约、其他
		/*List<HashMap> list2 = cmi099Dao.selectTransactions(transday, currentDate);
		for(int i=0; i<list2.size(); i++){
			insertMi098(list2.get(i), "2");
		}*/
		List<HashMap> list2 = cmi098Dao.selectAppointment(transday, currentDate);
		for(int i=0; i<list2.size(); i++){
			HashMap tmp = list2.get(i);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("centerid", tmp.get("centerid"));
			map.put("channel", tmp.get("pid").toString().subSequence(0, 2));
			map.put("pid", tmp.get("pid"));
			map.put("bsptype", "0");
			map.put("transdate", tmp.get("appodate"));
			map.put("amt", "0");
			map.put("subtype", tmp.get("subtype"));
			map.put("failcnt", 0);
			map.put("cnt", tmp.get("cnt"));
			map.put("succcnt", tmp.get("cnt"));
			log.info("appointInfo:" + map);
			insertMi098(map, "2");
		}
		
		for(int i=0; i<mi001List.size(); i++){
			String centerid = mi001List.get(i).getCenterid();
			log.info("centerid="+centerid);
			
			//获取该中心下所有渠道信息
			Mi040DAO mi040DAO = (Mi040DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi040DAO");
			Mi040Example example040 = new Mi040Example();
			example040.createCriteria().andCenteridEqualTo(centerid)
				.andPidIsNotNull().andAppnameNotEqualTo("新媒体客服系统");
			List<Mi040> mi040List = mi040DAO.selectByExample(example040);
			
			//bsp统计要一天一天获取
			String startDate = new String(lastDate);
			do{
				//获取下一天
				Date datetemp=null; 
				datetemp = new SimpleDateFormat("yyyy-MM-dd").parse(startDate); 
				c.setTime(datetemp); 
				daystart=c.get(Calendar.DATE); 
				c.set(Calendar.DATE,daystart+1); 
				startDate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
				log.info("do while startDate="+startDate);
				
				//与bsp通讯
				SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
				Mi011DAO mi011Dao = (Mi011DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi011Dao");
				Mi011Example example011 = new Mi011Example();
				example011.createCriteria().andCenteridEqualTo(centerid);
				List<Mi011> resultList = mi011Dao.selectByExample(example011);
				JSONObject repmap = JSONObject.fromObject("{\"recode\":\"999999\",\"result\":[]}");
				JSONObject repmap1 = JSONObject.fromObject("{\"recode\":\"999999\",\"result\":[]}");
				if(resultList.size()!=0 
						&& !CommonUtil.isEmpty(resultList.get(0).getClassname())){
					Mi011 result = resultList.get(0);
					//业务办理类，申请、办结
					for(int k=0; k<mi040List.size(); k++){
						String pid = mi040List.get(k).getPid();
						String channel = mi040List.get(k).getChannel();
						log.info("bsp circle pid="+pid);
						String url = result.getUrl()+"/webapi50026.json";
						HashMap map = new HashMap();
						map.put("startdate", startDate);
						map.put("enddate", startDate);
						map.put("channel", channel);
						map.put("centerId", centerid);
						String rep = "{\"recode\":\"999999\",\"result\":[]}";
						try{
							log.info("BSP获取业务办理开始");
							log.info("map="+map);
							rep = msm.sendPost(url, map, "UTF-8");
							//rep = "{\"count\":\"2\",\"fail\":\"0\",\"msg\":\"成功\",\"recode\":\"000000\",\"result\":[{\"bsptype\":\"20\",\"detailCount\":\"3\",\"detailFail\":\"1\",\"detailName\":\"缴存1\",\"detailSuccess\":\"2\",\"type1\":\"40\",\"type2\":\"10\",\"type3\":\"10\"},{\"bsptype\":\"20\",\"detailCount\":\"30\",\"detailFail\":\"10\",\"detailName\":\"缴存2\",\"detailSuccess\":\"20\",\"type1\":\"40\",\"type2\":\"10\",\"type3\":\"20\"},{\"bsptype\":\"20\",\"detailCount\":\"33\",\"detailFail\":\"10\",\"detailName\":\"缴存3\",\"detailSuccess\":\"23\",\"type1\":\"40\",\"type2\":\"10\",\"type3\":\"20\"},{\"bsptype\":\"20\",\"detailCount\":\"33\",\"detailFail\":\"10\",\"detailName\":\"缴存4\",\"detailSuccess\":\"23\",\"type1\":\"40\",\"type2\":\"10\",\"type3\":\"20\"},{\"bsptype\":\"20\",\"detailCount\":\"2\",\"detailFail\":\"0\",\"detailName\":\"提取1\",\"detailSuccess\":\"2\",\"type1\":\"40\",\"type2\":\"20\",\"type3\":\"10\"},{\"bsptype\":\"20\",\"detailCount\":\"2\",\"detailFail\":\"0\",\"detailName\":\"提取2\",\"detailSuccess\":\"2\",\"type1\":\"40\",\"type2\":\"20\",\"type3\":\"20\"},{\"bsptype\":\"20\",\"detailCount\":\"222\",\"detailFail\":\"10\",\"detailName\":\"贷款1\",\"detailSuccess\":\"212\",\"type1\":\"40\",\"type2\":\"30\",\"type3\":\"10\"},{\"bsptype\":\"20\",\"detailCount\":\"32\",\"detailFail\":\"10\",\"detailName\":\"贷款2\",\"detailSuccess\":\"22\",\"type1\":\"40\",\"type2\":\"30\",\"type3\":\"20\"}],\"success\":\"2\"}";
							log.info("业务办理返回数据：" + rep);
							repmap = JSONObject.fromObject(rep);
						}catch(Exception e){
							e.printStackTrace();
							log.info("获取业务办理统计数据通讯失败！");
						}
						if("000000".equals(repmap.get("recode")) 
								&& repmap.getJSONArray("result").size()!=0){
							transactionsDeal(repmap, centerid, pid, channel, startDate);
						}
					}
					
					//柜面业务统计数据
					String url1 = result.getUrl()+"/webapi50027.json";
					HashMap map1 = new HashMap();
					map1.put("startdate", startDate);
					map1.put("enddate", startDate);
					map1.put("centerId", centerid);
					String rep1 = "{\"recode\":\"999999\",\"result\":[]}";
					try{
						log.info("BSP获取柜面业务统计开始");
						log.info("map1="+map1);
						rep1 = msm.sendPost(url1, map1, "UTF-8");
						//rep1 = "{\"recode\":\"000000\",\"msg\":\"成功\",\"tqywl\":\"1\",\"jcywl\":\"3\",\"dkywl\":\"0\"}";
						log.info("柜面业务统计返回数据：" + rep1);
						repmap1 = JSONObject.fromObject(rep1);
					}catch(Exception e){
						e.printStackTrace();
						log.info("获取柜面业务统计数据通讯失败！");
					}
					if("000000".equals(repmap1.get("recode"))){
						webapi50027Deal(repmap1, centerid, startDate);
					}
				}
				
				
				for(int k=0; k<mi040List.size(); k++){
					String pid = mi040List.get(k).getPid();
					String channel = mi040List.get(k).getChannel();
					String appname = mi040List.get(k).getAppname();
					log.info("non bsp circle pid="+pid);
					//信息查询类文件下载统计
					List<HashMap> fileDownload = cmi712Dao.webapi712(centerid,
							startDate, startDate, pid);
					for(int j=0; j<fileDownload.size(); j++){
						HashMap tmp = fileDownload.get(j);
						log.info("fileDownload"+j+":"+tmp);
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("centerid", centerid);
						map.put("channel", channel);
						map.put("pid", pid);
						map.put("bsptype", "0");
						map.put("transdate", startDate);
						map.put("amt", "0");
						map.put("subtype", "06");
						map.put("failcnt", 0);
						map.put("cnt", tmp.get("cnt"));
						map.put("succcnt", tmp.get("cnt"));
						log.info("appointInfo:" + map);
						insertMi098(map, "1");
					}
					
					//微博公共接口通讯，获取信息发布类微博公共信息发布统计
					if("80".equals(channel)){
						log.info("微博公共接口通讯开始");
						try{
							String accessToken = WkfAccessTokenUtil.getWBTokenWithCouchBase(centerid);
							String urlWeibo = PropertiesReader.getHeartbeatURL(centerid,
									"80").trim() + "/proc/sina/getCount";
							HashMap hashMap = new HashMap();
							hashMap.put("begindate", startDate + " 00:00:00");
							hashMap.put("enddate", startDate + " 23:59:59");
							hashMap.put("token", accessToken);
							log.info("微博token:" + accessToken);
							log.info("urlWeibo:" + urlWeibo);
							String repWeibo = msm.sendPost(urlWeibo, hashMap, "UTF-8");
							//String repWeibo = "{\"code\":\"0000\",\"msg\":\"success\",datas:[{\"key\":\"ydmb_count\",\"title\":\"微博系统发布数量\",\"total_value\":\"123\"},{\"key\":\"out_count\",\"title\":\"外部接口发布数量\",\"total_value\":\"23\"}]}";
							log.info("repWeibo="+ repWeibo);
							JSONObject weibo = JSONObject.fromObject(repWeibo);
							if("0000".equals(weibo.get("code"))){
								weiboDeal(weibo, centerid, pid, startDate);
							}
						}catch(Exception e){
							e.printStackTrace();
							log.info("微博通讯失败！");
						}
						log.info("微博公共接口通讯结束");
					}else{//信息发布非微博渠道公共信息发布统计
						try{
							List<HashMap> listPublic = cmi701Dao.webapi701(centerid, 
									pid, startDate, startDate);
							otherChannelPublic(listPublic, centerid, pid, channel, startDate);
						}catch(Exception e){
							e.printStackTrace();
							log.info("非微博渠道公共信息发布统计异常！centerid="+centerid+" pid="+pid
									+" startDate="+startDate);
						}
					}
					
					//信息发布消息推送统计
					if("10".equals(channel) || "20".equals(channel)){
						try{
							log.info("微信、app消息推送start");
							//群发消息 定制消息
							List<HashMap> list4 = cmi424Dao.webapi42401(centerid, pid, startDate, startDate);
							log.info("list4="+list4);
							//模板消息
							List<HashMap> list5 = cmi424Dao.webapi42402(centerid, pid, startDate, startDate);
							log.info("list5="+list5);
							msgPush10_20(list4, list5, centerid, pid, channel, startDate);
							log.info("微信、app消息推送end");
						}catch(Exception e){
							e.printStackTrace();
							log.info("微信、app消息推送统计异常！centerid="+centerid+" pid="+pid
									+" startDate="+startDate);
						}
					}else if("70".equals(channel)){
						log.info("短信渠道消息推送统计数据start");
						try{
							String hotLineURL = PropertiesReader.getHeartbeatURL(centerid, pid).trim();
							String url = hotLineURL + "/countSms.action";
							 //beginDate,centerId,endDate
							HashMap hashMap = new HashMap();
							//YYYY-MM-dd  (例：2016-12-20)
							hashMap.put("beginDate", startDate);
							hashMap.put("endDate", startDate);
							hashMap.put("centerId", centerid);
							log.info("startDate:"+startDate+";centerid:"+centerid);
							log.info("短信平台url：" + url);
							String repMessage = msm.sendPost(url, hashMap, "UTF-8");
							//String repMessage="{\"recode\":\"000000\",\"fail\":27878,\"sum\":2005542,\"modelMsg\":[{\"modelId\":\"KMDX0001\",\"failNum\":75,\"modelName\":\"短信签约通知\",\"successNum\":6235,\"smid\":\"\",\"sumRecord\":6310},{\"modelId\":\"KMDX0002\",\"failNum\":0,\"modelName\":\"线上提取\",\"successNum\":0,\"smid\":\"\",\"sumRecord\":0},{\"modelId\":\"KMDX0004\",\"failNum\":0,\"modelName\":\"汇补缴\",\"successNum\":4,\"smid\":\"\",\"sumRecord\":4},{\"modelId\":\"KMDX0005\",\"failNum\":0,\"modelName\":\"贷款审批\",\"successNum\":0,\"smid\":\"\",\"sumRecord\":0},{\"modelId\":\"KMDX0006\",\"failNum\":0,\"modelName\":\"公积金委托扣划还贷通知\",\"successNum\":0,\"smid\":\"\",\"sumRecord\":0},{\"modelId\":\"KMDX0007\",\"failNum\":5635,\"modelName\":\"还款提醒\",\"successNum\":628420,\"smid\":\"\",\"sumRecord\":634055},{\"modelId\":\"KMDX0008\",\"failNum\":841,\"modelName\":\"逾期催收\",\"successNum\":44125,\"smid\":\"\",\"sumRecord\":44966},{\"modelId\":\"KMDX0009\",\"failNum\":1003,\"modelName\":\"银行代扣-成功\",\"successNum\":77984,\"smid\":\"\",\"sumRecord\":78987},{\"modelId\":\"KMDX0011\",\"failNum\":699,\"modelName\":\"公积金扣划成功\",\"successNum\":124643,\"smid\":\"\",\"sumRecord\":125342},{\"modelId\":\"KMDX0013\",\"failNum\":0,\"modelName\":\"线上还款\",\"successNum\":0,\"smid\":\"\",\"sumRecord\":0},{\"modelId\":\"KMDX0010\",\"failNum\":757,\"modelName\":\"银行扣款失败\",\"successNum\":25451,\"smid\":\"\",\"sumRecord\":26208},{\"modelId\":\"KMDX0012\",\"failNum\":200,\"modelName\":\"公积金扣划失败\",\"successNum\":40245,\"smid\":\"\",\"sumRecord\":40445},{\"modelId\":\"KMDX0014\",\"failNum\":0,\"modelName\":\"贷款结清\",\"successNum\":0,\"smid\":\"\",\"sumRecord\":0},{\"modelId\":\"KMDX0015\",\"failNum\":0,\"modelName\":\"利率调整\",\"successNum\":0,\"smid\":\"\",\"sumRecord\":0},{\"modelId\":\"KMDX0016\",\"failNum\":74,\"modelName\":\"贷款放款\",\"successNum\":10629,\"smid\":\"\",\"sumRecord\":10703},{\"modelId\":\"KMDX0018\",\"failNum\":13351,\"modelName\":\"短信验证码\",\"successNum\":542973,\"smid\":\"\",\"sumRecord\":556324},{\"modelId\":\"KMDX0019\",\"failNum\":0,\"modelName\":\"线上服务评价\",\"successNum\":0,\"smid\":\"\",\"sumRecord\":0},{\"modelId\":\"KMDX0017\",\"failNum\":0,\"modelName\":\"归集托收\",\"successNum\":0,\"smid\":\"\",\"sumRecord\":0},{\"modelId\":\"KMDX0020\",\"failNum\":5163,\"modelName\":\"还款结果通知\",\"successNum\":469900,\"smid\":\"\",\"sumRecord\":475063},{\"modelId\":\"KM214\",\"failNum\":0,\"modelName\":\"退取\",\"successNum\":23,\"smid\":\"\",\"sumRecord\":23},{\"modelId\":\"KMDX0021\",\"failNum\":0,\"modelName\":\"注册成功\",\"successNum\":0,\"smid\":\"\",\"sumRecord\":0}],\"customizeMsg\":[{\"failNum\":74,\"modelName\":\"综合定制短信\",\"successNum\":6979,\"smid\":\"ZHDZDX\",\"sumRecord\":7053},{\"modelId\":\"\",\"failNum\":6,\"modelName\":\"短信平台定制消息\",\"successNum\":48,\"smid\":\"\",\"sumRecord\":54}],\"success\":1977664,\"msg\":\"成功\"}";
							log.info("repMessage="+repMessage);
							JSONObject msg = JSONObject.fromObject(repMessage);
							if("000000".equals(msg.get("recode"))){
								msgPush70(msg, centerid, pid, startDate);
							}
						}catch(Exception e){
							e.printStackTrace();
							log.info("短信平台通讯失败！");
						}
						log.info("短信渠道消息推送统计数据end");
					}
					
					//互动交流类呼叫中心
					if("60".equals(channel) && "服务热线".equals(appname)){
						//呼叫中心通话记录查询   业务咨询类
						hotLineRecord(centerid, pid, channel, startDate);
						//呼叫中心满意度汇总查询  服务评价类
						hotLineServiceEvaluation(centerid, pid, channel, startDate);
					}else{//互动交流类新媒体 
						//业务咨询类
						otherChannelConsult(centerid, pid, channel, startDate, appname);
						//服务评价类
						otherChannelServiceEvaluation(centerid, pid, channel, startDate, appname);
					}
					//在线调查/问卷调查
					onlineSurvey(centerid, pid, channel, startDate, appname);
					//互动交流类工单  投诉建议类
					complaintSuggestion(centerid, pid, channel, startDate, appname);
				}
			}while(!currentDate.equals(startDate));
		}
	}
	
	private void otherChannelServiceEvaluation(String centerid, String pid,
			String channel, String startDate, String appname){
		Mi007Example example = new Mi007Example();
		example.createCriteria().andCenteridEqualTo(centerid).andItemidEqualTo("config");
		List<Mi007> mi007List1 = mi007Dao.selectByExample(example);
		List<Mi007> mi007List2 = new ArrayList();
		if(mi007List1.size()!=0){
			Integer dicid = mi007List1.get(0).getDicid();
			Mi007Example example1 = new Mi007Example();
			example1.createCriteria().andCenteridEqualTo(centerid)
				.andItemidEqualTo("url").andUpdicidEqualTo(dicid);
			mi007List2 = mi007Dao.selectByExample(example1);
			if(mi007List2.size()!=0){
				try{
					SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
					String url27 = mi007List2.get(0).getItemval()+"/ybmapzh/score";
					String accessToken27 = WkfAccessTokenUtil.WKF_GET_TOKEN(centerid);
					HashMap hashMap = new HashMap();
					hashMap.put("accessToken", accessToken27);
					hashMap.put("begindate", startDate + " 00:00:00");
					hashMap.put("enddate", startDate + " 23:59:59");
					hashMap.put("platform", getPlatName(pid, appname, "2"));
					log.info("begindate:" + startDate);
					log.info("accessToken27:" + accessToken27);
					log.info("url27="+url27);
					String rep5 = msm.sendPost(url27, hashMap, "UTF-8");
					//String rep5 = "{\"code\":\"0000\",\"msg\":\"success\",datas:[{\"key\":\"count_2\",\"title\":\"满意评价数\",\"total_value\":\"35\"},{\"key\":\"count_1\",\"title\":\"基本满意评价数\",\"total_value\":\"15\"},{\"key\":\"count_-1\",\"title\":\"不满意评价数\",\"total_value\":\"5\"}]}";
					log.info("rep5:"+rep5);
					JSONObject msg = JSONObject.fromObject(rep5);
					if("0000".equals(msg.get("code"))){
						int total = 0;
						JSONArray ary = msg.getJSONArray("datas");
						for(int i=0; i<ary.size(); i++){
							JSONObject tmp = ary.getJSONObject(i);
							total += Integer.valueOf(String.valueOf(tmp.get("total_value")));
						}
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("centerid", centerid);
						map.put("channel", channel);
						map.put("pid", pid);
						map.put("bsptype", "0");
						map.put("transdate", startDate);
						map.put("amt", "0");
						map.put("subtype", "14");
						map.put("failcnt", 0);
						map.put("cnt", total);
						map.put("succcnt", total);
						insertMi098(map, "4");
					}
				}catch(Exception e){
					e.printStackTrace();
					log.info("新媒体/ybmapzh/score通讯失败！");
				}
			}
		}
	}
	
	private void otherChannelConsult(String centerid, String pid,
			String channel, String startDate, String appname){
		Mi007Example example = new Mi007Example();
		example.createCriteria().andCenteridEqualTo(centerid).andItemidEqualTo("config");
		List<Mi007> mi007List1 = mi007Dao.selectByExample(example);
		List<Mi007> mi007List2 = new ArrayList();
		if(mi007List1.size()!=0){
			Integer dicid = mi007List1.get(0).getDicid();
			Mi007Example example1 = new Mi007Example();
			example1.createCriteria().andCenteridEqualTo(centerid)
				.andItemidEqualTo("url").andUpdicidEqualTo(dicid);
			mi007List2 = mi007Dao.selectByExample(example1);
			if(mi007List2.size()!=0){
				SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
				String url26 = mi007List2.get(0).getItemval()+"/ybmapzh/chatinfo";
				try{
					String accessToken26 = WkfAccessTokenUtil.WKF_GET_TOKEN(centerid);
					HashMap hashMap = new HashMap();
					hashMap.put("accessToken", accessToken26);
					hashMap.put("begindate", startDate + " 00:00:00");
					hashMap.put("enddate", startDate + " 23:59:59");
					hashMap.put("platform", getPlatName(pid, appname, "2"));
					log.info("accessToken26:" + accessToken26);
					log.info("begindate:" + startDate);
					log.info("platform:" + getPlatName(pid, appname, "2"));
					log.info("url26="+url26);
					String rep4 = msm.sendPost(url26, hashMap, "UTF-8");
					//String rep4 = "{\"code\":\"0000\",\"msg\":\"success\",datas:[{\"key\":\"chat_count\",\"title\":\"在线会话数\",\"total_value\":\"123\"},{\"key\":\"info_count\",\"title\":\"在线留言数\",\"total_value\":\"23\"}]}";
					log.info("rep4:"+rep4);
					JSONObject msg = JSONObject.fromObject(rep4);
					if("0000".equals(msg.get("code"))){
						int total = 0;
						JSONArray ary = msg.getJSONArray("datas");
						for(int i=0; i<ary.size(); i++){
							JSONObject tmp = ary.getJSONObject(i);
							total += Integer.valueOf(String.valueOf(tmp.get("total_value")));
						}
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("centerid", centerid);
						map.put("channel", channel);
						map.put("pid", pid);
						map.put("bsptype", "0");
						map.put("transdate", startDate);
						map.put("amt", "0");
						map.put("subtype", "11");
						map.put("failcnt", 0);
						map.put("cnt", total);
						map.put("succcnt", total);
						insertMi098(map, "4");
					}
				}catch(Exception e){
					e.printStackTrace();
					log.info("新媒体/ybmapzh/chatinfo通讯失败！");
				}
			}
		}
	}
	
	private void onlineSurvey(String centerid, String pid,
			String channel, String startDate, String appname){
		Mi007Example example = new Mi007Example();
		example.createCriteria().andCenteridEqualTo(centerid).andItemidEqualTo("config");
		List<Mi007> mi007List1 = mi007Dao.selectByExample(example);
		List<Mi007> mi007List2 = new ArrayList();
		if(mi007List1.size()!=0){
			Integer dicid = mi007List1.get(0).getDicid();
			Mi007Example example1 = new Mi007Example();
			example1.createCriteria().andCenteridEqualTo(centerid)
				.andItemidEqualTo("url").andUpdicidEqualTo(dicid);
			mi007List2 = mi007Dao.selectByExample(example1);
			if(mi007List2.size()!=0){
				try{
					SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
					String url7_7 = mi007List2.get(0).getItemval()+"/ybmapzh/question";
					String accessToken7_7 = WkfAccessTokenUtil.WKF_GET_TOKEN(centerid);
					HashMap hashMap2 = new HashMap();
					hashMap2.put("token", accessToken7_7);
					hashMap2.put("begindate", startDate + " 00:00:00");
					hashMap2.put("enddate", startDate + " 23:59:59");
					hashMap2.put("from_plat", getPlatName(pid, appname, "2"));
					log.info("accessToken7_7:" + accessToken7_7);
					log.info("url7_7="+url7_7);
					String rep6 = msm.sendPost(url7_7, hashMap2, "UTF-8");
					//String rep6 = "{\"code\":\"0000\",\"msg\":\"处理成功\",\"data\":{\"key\":\"answer_count\",\"title\":\"参与调查人次\",\"total_value\":\"35\"},\"totalCount\":\"0\"}";
					log.info("rep6:"+rep6);
					JSONObject msg = JSONObject.fromObject(rep6);
					if("0000".equals(msg.get("code"))){
						int total = 0;
						JSONArray ary = msg.getJSONArray("data");
						for(int i=0; i<ary.size(); i++){
							JSONObject tmp = ary.getJSONObject(i);
							total += Integer.valueOf(String.valueOf(tmp.get("total_value")));
						}
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("centerid", centerid);
						map.put("channel", channel);
						map.put("pid", pid);
						map.put("bsptype", "0");
						map.put("transdate", startDate);
						map.put("amt", "0");
						map.put("subtype", "13");
						map.put("failcnt", 0);
						map.put("cnt", total);
						map.put("succcnt", total);
						insertMi098(map, "4");
					}
				}catch(Exception e){
					e.printStackTrace();
					log.info("新媒体/ybmapzh/question通讯失败！");
				}
			}
		}
	}
	
	private void complaintSuggestion(String centerid, String pid, 
			String channel, String startDate, String appname){
		try{
			SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
			Mi007Example example = new Mi007Example();
			example.createCriteria().andCenteridEqualTo(centerid).andItemidEqualTo("configgd");
			List<Mi007> mi007List1 = mi007Dao.selectByExample(example);
			Integer dicid = mi007List1.get(0).getDicid();
			Mi007Example example1 = new Mi007Example();
			example1.createCriteria().andCenteridEqualTo(centerid)
				.andItemidEqualTo("url").andUpdicidEqualTo(dicid);
			List<Mi007> mi007List2 = mi007Dao.selectByExample(example1);
			String url = mi007List2.get(0).getItemval()+"/activitis/wo/service/getProcessCountByType";
		
			String accessToken = WkfAccessTokenUtil.getGDTokenWithCouchBase(centerid,"ybmap");
			log.info("工单accessToken:"+accessToken);
			//YYYY-MM-dd  (例：2016-12-20)
			url = url + "?start_time=" + startDate
					+ "&end_time=" + startDate
					+ "&token_=" + accessToken
					+ "&from_plat=ybmap"
					+ "&flow_from_plat=" + getPlatName(pid, appname, "1");
			log.info("工单投诉建意查询URL:"+url);
			String rep3 = msm.sendGet(url, "UTF-8");
			//String rep3 = "{{\"state\": \"SUCCESS\",\"code\": \"0000\",\"msg\": \"操作成功。\",\"data\": null,\"dataList\":[{\"TOTAL\": 1,\"ORG_ID\": \"00087100\",\"FLOW_FROM_PLAT \": \"APP\",\"TYPE\": \"1\"},{\"TOTAL\": 3,\"ORG_ID\": \"00087100\",\"FLOW_FROM_PLAT \": \"APP\",\"TYPE\": \"2\"}],\"totalCount\": null,\"totalPage\": null}}";
			log.info("rep3:"+rep3);
			JSONObject msg = JSONObject.fromObject(rep3);
			if("0000".equals(msg.get("code"))){
				int total = 0;
				JSONArray ary = msg.getJSONArray("dataList");
				for(int i=0; i<ary.size(); i++){
					JSONObject tmp = ary.getJSONObject(i);
					if("2".equals(tmp.get("TYPE")) || "3".equals(tmp.get("TYPE"))){
						total += Integer.valueOf(String.valueOf(tmp.get("TOTAL")));
					}
				}
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("centerid", centerid);
				map.put("channel", channel);
				map.put("pid", pid);
				map.put("bsptype", "0");
				map.put("transdate", startDate);
				map.put("amt", "0");
				map.put("subtype", "12");
				map.put("failcnt", 0);
				map.put("cnt", total);
				map.put("succcnt", total);
				insertMi098(map, "4");
			}
		}catch(Exception e){
			e.printStackTrace();
			log.info("工单投诉建意查询通讯失败！");
		}
	}
	
	private void hotLineServiceEvaluation(String centerid, String pid, 
			String channel, String startDate){
		try{
			SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
			//String url2 = PropertiesReader.getHeartbeatURL(form.getCenterId(),
				//form.getPid()).trim() + "/SatisfactionAggregateQueries/GetSatisfactionAggregateQueries";
			String url2 = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
					"hjzxurl").trim()+"/SatisfactionAggregateQueries/GetSatisfactionAggregateQueries";
			log.info("呼叫中心满意度汇总查询URL:"+url2);
			HashMap hashMap2 = new HashMap();
			hashMap2.put("ksrq", startDate);
			log.info("ksrq:"+startDate);
			hashMap2.put("jsrq", startDate);
			hashMap2.put("centerid", centerid);
			log.info("centerid:"+centerid);
			String rep2 = msm.sendPost(url2, hashMap2, "UTF-8");
			//String rep2 = "{\"recode\":\"000000\",\"msg\":\"\",\"totalCount\":500,\"data\":{\"-1\":\"不满意\",\"1\":\"基本满意\",\"2\":\"满意\",\"-\":\"未评价\"},\"datas\":[{\"key\":\"1\",\"name\":\"不满意\",\"value\":20},{\"key\":\"1\",\"name\":\"基本满意\",\"value\":120},{\"key\":\"2\",\"name\":\"满意\",\"value\":200},{\"key\":\"-\",\"name\":\"未评价\",\"value\":160}]}";
			log.info("rep2:"+rep2);
			JSONObject msg = JSONObject.fromObject(rep2);
			if("000000".equals(msg.get("recode"))){
				JSONArray array = msg.getJSONArray("datas");
				Iterator<Object> it = array.iterator();
				int total = 0;
				while(it.hasNext()){
					JSONObject objData = (JSONObject)it.next();
					total += Integer.valueOf(String.valueOf(objData.get("value")));
				}
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("centerid", centerid);
				map.put("channel", channel);
				map.put("pid", pid);
				map.put("bsptype", "0");
				map.put("transdate", startDate);
				map.put("amt", "0");
				map.put("subtype", "14");
				map.put("failcnt", 0);
				map.put("cnt", total);
				map.put("succcnt", total);
				insertMi098(map, "4");
			}
		}catch(Exception e){
			e.printStackTrace();
			log.info("呼叫中心满意度汇总查询通讯失败！");
		}
	}
	
	private void hotLineRecord(String centerid, String pid, 
			String channel, String startDate){
		try{
			SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
			//String url = PropertiesReader.getHeartbeatURL(form.getCenterId(),
					//form.getPid()).trim() + "/CCAssessment/CCAssessment";
			String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
					"hjzxurl").trim()+"/CCAssessment/CCAssessment";
			log.info("呼叫中心业务咨询统计查询URL:"+url);
			HashMap hashMap = new HashMap();
			//传VCC的id，用,隔开
			log.info("PAVerticalAxisItems:"+centerid);
			hashMap.put("PAVerticalAxisItems", centerid);
			//YYYY-MM-dd  (例：2016-12-20)
			log.info("PStartDate:"+startDate);
			hashMap.put("PStartDate", startDate);
			hashMap.put("PEndDate", startDate);
			hashMap.put("TENANT_ID", "1");
			hashMap.put("selecttype", "12329");
			String rep = msm.sendPost(url, hashMap, "UTF-8");
			//String rep = "{\"recode\":\"000000\",\"msg\":\"\",\"data\":[{\"R_ZHRL\":\"20\",\"R_IVRSLL\":\"20\",\"R_HRZSC\":\"121\",\"R_PJTHSC\":\"10\",\"R_ACDSLL\":\"10\",\"R_JTL\":\"80%\",\"C_HCZL\":\"2\",\"C_THZSC\":\"20\",\"C_PJTHSC\":\"22\",\"R_FQL\":\"2\",\"R_FQLv\":\"20%\"}]}";
			log.info("rep:"+rep);
			JSONObject msg = JSONObject.fromObject(rep);
			if("000000".equals(msg.get("recode"))){
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("centerid", centerid);
				map.put("channel", channel);
				map.put("pid", pid);
				map.put("bsptype", "0");
				map.put("transdate", startDate);
				map.put("amt", "0");
				map.put("subtype", "11");
				map.put("failcnt", 0);
				map.put("cnt", msg.getJSONArray("data").getJSONObject(0).get("R_ZHRL"));
				map.put("succcnt", msg.getJSONArray("data").getJSONObject(0).get("R_ZHRL"));
				insertMi098(map, "4");
			}
		}catch(Exception e){
			e.printStackTrace();
			log.info("呼叫中心业务咨询统计查询通讯失败！");
		}
	}
	
	private void msgPush10_20(List<HashMap> list4, List<HashMap> list5, String centerid, 
			String pid, String channel, String startDate) throws Exception{
		int cnt = 0;
		int succ = 0;
		int fail = 0;
		for(int i=0; i<list4.size(); i++){
			cnt += Integer.valueOf(String.valueOf(list4.get(i).get("cnt")));
			succ += Integer.valueOf(String.valueOf(list4.get(i).get("succ")));
			fail += Integer.valueOf(String.valueOf(list4.get(i).get("fail")));
		}
		for(int i=0; i<list5.size(); i++){
			cnt += Integer.valueOf(String.valueOf(list5.get(i).get("cnt")));
			succ += Integer.valueOf(String.valueOf(list5.get(i).get("succ")));
			fail += Integer.valueOf(String.valueOf(list5.get(i).get("fail")));
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("centerid", centerid);
		map.put("channel", channel);
		map.put("pid", pid);
		map.put("bsptype", "0");
		map.put("transdate", startDate);
		map.put("amt", "0");
		map.put("subtype", "09");
		map.put("failcnt", fail);
		map.put("cnt", cnt);
		map.put("succcnt", succ);
		insertMi098(map, "3");
	}
	
	private void msgPush70(JSONObject msg, String centerid,
			String pid, String startDate) throws Exception{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("centerid", centerid);
		map.put("channel", "70");
		map.put("pid", pid);
		map.put("bsptype", "0");
		map.put("transdate", startDate);
		map.put("amt", "0");
		map.put("subtype", "09");
		map.put("failcnt", Integer.valueOf(String.valueOf(msg.get("fail"))));
		map.put("cnt", Integer.valueOf(String.valueOf(msg.get("sum"))));
		map.put("succcnt", Integer.valueOf(String.valueOf(msg.get("success"))));
		insertMi098(map, "3");
	}
	
	private void otherChannelPublic(List<HashMap> listPublic, String centerid,
			String pid, String channel, String startDate) throws Exception{
		int total = 0;
		for(int i=0; i<listPublic.size(); i++){
			total += Integer.valueOf(String.valueOf(listPublic.get(i).get("cnt")));
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("centerid", centerid);
		map.put("channel", channel);
		map.put("pid", pid);
		map.put("bsptype", "0");
		map.put("transdate", startDate);
		map.put("amt", "0");
		map.put("subtype", "08");
		map.put("failcnt", 0);
		map.put("cnt", total);
		map.put("succcnt", total);
		insertMi098(map, "3");
	}
	
	private void weiboDeal(JSONObject weibo, String centerid,
			String pid,  String startDate) throws Exception{
		JSONArray result = JSONArray.fromObject(weibo.get("datas").toString());
		int total = 0;
		for(int i=0; i<result.size(); i++){
			total += Integer.valueOf(String.valueOf(result.getJSONObject(i).get("total_value")));
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("centerid", centerid);
		map.put("channel", "80");
		map.put("pid", pid);
		map.put("bsptype", "0");
		map.put("transdate", startDate);
		map.put("amt", "0");
		map.put("subtype", "08");
		map.put("failcnt", 0);
		map.put("cnt", total);
		map.put("succcnt", total);
		insertMi098(map, "3");
	}
	
	private void webapi50027Deal(JSONObject repmap, String centerid, 
			String startDate) throws Exception{
		int jc = Integer.valueOf(String.valueOf(repmap.get("jcywl")));
		int dk = Integer.valueOf(String.valueOf(repmap.get("dkywl")));
		int tq = Integer.valueOf(String.valueOf(repmap.get("tqywl")));
		HashMap map = new HashMap();
		map.put("centerid", centerid);
		map.put("channel", "");
		map.put("pid", "");
		map.put("bsptype", "1");
		map.put("transdate", startDate);
		map.put("amt", "0");
		map.put("failcnt", 0);
		//缴存
		map.put("subtype","18");
		map.put("cnt", jc);
		map.put("succcnt", jc);
		insertMi098(map, "2");
		//提取
		map.put("subtype","21");
		map.put("cnt", tq);
		map.put("succcnt", tq);
		insertMi098(map, "2");
		//贷款
		map.put("subtype","24");
		map.put("cnt", dk);
		map.put("succcnt", dk);
		insertMi098(map, "2");
	}
		
	private void transactionsDeal(JSONObject repmap, String centerid, 
			String pid, String channel, String transdate) throws Exception{
		JSONArray result = JSONArray.fromObject(repmap.get("result").toString());
		//贷款办结
		int dkbjSecondCnt = 0;
		int dkbjSecondSuccCnt = 0;
		int dkbjSecondFailCnt = 0;
		//贷款申请
		int dksqSecondCnt = 0;
		int dksqSecondSuccCnt = 0;
		int dksqSecondFailCnt = 0;
		//提取办结
		int tqbjSecondCnt = 0;
		int tqbjSecondSuccCnt = 0;
		int tqbjSecondFailCnt = 0;
		//提取申请
		int tqsqSecondCnt = 0;
		int tqsqSecondSuccCnt = 0;
		int tqsqSecondFailCnt = 0;
		//缴存办结
		int jcbjSecondCnt = 0;
		int jcbjSecondSuccCnt = 0;
		int jcbjSecondFailCnt = 0;
		//缴存申请
		int jcsqSecondCnt = 0;
		int jcsqSecondSuccCnt = 0;
		int jcsqSecondFailCnt = 0;
		for(int i=0; i<result.size(); i++){
			JSONObject objData = result.getJSONObject(i);
			//type1为10-信息查询，20-信息发布，30-互动交流，40-业务办理
			//type2为10-缴存，20-提取，30-贷款
			//type3为10-申请办理，20-在线办结
			if("40".equals(objData.get("type1"))){//业务办理
				int detailCount = objData.getInt("detailCount");
				int detailFail = objData.getInt("detailFail");
				int detailSuccess = objData.getInt("detailSuccess");
				if("10".equals(objData.get("type2"))){//缴存
					if("10".equals(objData.get("type3"))){//申请
						jcsqSecondCnt += detailCount;
						jcsqSecondSuccCnt += detailSuccess;
						jcsqSecondFailCnt += detailFail;
					}else if("20".equals(objData.get("type3"))){//办结
						jcbjSecondCnt += detailCount;
						jcbjSecondSuccCnt += detailSuccess;
						jcbjSecondFailCnt += detailFail;
					}
				}else if("20".equals(objData.get("type2"))){//提取
					if("10".equals(objData.get("type3"))){//申请
						tqsqSecondCnt += detailCount;
						tqsqSecondSuccCnt += detailSuccess;
						tqsqSecondFailCnt += detailFail;
					}else if("20".equals(objData.get("type3"))){//办结
						tqbjSecondCnt += detailCount;
						tqbjSecondSuccCnt += detailSuccess;
						tqbjSecondFailCnt += detailFail;
					}
				}else if("30".equals(objData.get("type2"))){//贷款
					if("10".equals(objData.get("type3"))){//申请
						dksqSecondCnt += detailCount;
						dksqSecondSuccCnt += detailSuccess;
						dksqSecondFailCnt += detailFail;
					}else if("20".equals(objData.get("type3"))){//办结
						dkbjSecondCnt += detailCount;
						dkbjSecondSuccCnt += detailSuccess;
						dkbjSecondFailCnt += detailFail;
					}
				}
			}
		}
		HashMap map = new HashMap();
		map.put("centerid", centerid);
		map.put("channel", channel);
		map.put("pid", pid);
		map.put("bsptype", "0");
		map.put("transdate", transdate);
		map.put("amt", "0");
		//缴存申请
		map.put("subtype","15");
		map.put("cnt", jcsqSecondCnt);
		map.put("succcnt", jcsqSecondSuccCnt);
		map.put("failcnt", jcsqSecondFailCnt);
		insertMi098(map, "2");
		//缴存办结
		map.put("subtype","18");
		map.put("cnt", jcbjSecondCnt);
		map.put("succcnt", jcbjSecondSuccCnt);
		map.put("failcnt", jcbjSecondFailCnt);
		insertMi098(map, "2");
		//提取办结
		map.put("subtype","21");
		map.put("cnt", tqbjSecondCnt);
		map.put("succcnt", tqbjSecondSuccCnt);
		map.put("failcnt", tqbjSecondFailCnt);
		insertMi098(map, "2");
		//提取申请
		map.put("subtype","19");
		map.put("cnt", tqsqSecondCnt);
		map.put("succcnt", tqsqSecondSuccCnt);
		map.put("failcnt", tqsqSecondFailCnt);
		insertMi098(map, "2");
		//贷款申请
		map.put("subtype","22");
		map.put("cnt", dksqSecondCnt);
		map.put("succcnt", dksqSecondSuccCnt);
		map.put("failcnt", dksqSecondFailCnt);
		insertMi098(map, "2");
		//贷款办结
		map.put("subtype","24");
		map.put("cnt", dkbjSecondCnt);
		map.put("succcnt", dkbjSecondSuccCnt);
		map.put("failcnt", dkbjSecondFailCnt);
		insertMi098(map, "2");
	}
	
	private void insertMi098(HashMap map, String serviceType) throws Exception{
		Mi098 mi098 = new Mi098();
		mi098.setId(Integer.valueOf(CommonUtil.genKeyStatic("MI098",10)));
		mi098.setCenterid(String.valueOf(map.get("centerid")));
		mi098.setChannel(String.valueOf(map.get("channel")));
		mi098.setPid(String.valueOf(map.get("pid")));
		mi098.setBsptype(String.valueOf(map.get("bsptype")));
		mi098.setServicetype(serviceType);
		mi098.setItemid(String.valueOf(map.get("subtype")));
		mi098.setTransday(String.valueOf(map.get("transdate")));
		mi098.setValidflag("1");
		mi098.setAmt(String.valueOf(map.get("amt")));
		mi098.setTranscnt(Integer.valueOf(String.valueOf(map.get("cnt"))));
		mi098.setSucccnt(Integer.valueOf(String.valueOf(map.get("succcnt"))));
		mi098.setFailcnt(Integer.valueOf(String.valueOf(map.get("failcnt"))));
		mi098.setDatecreated(CommonUtil.getSystemDate());
		mi098.setDatemodified(CommonUtil.getSystemDate());
		mi098Dao.insert(mi098);
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
}
