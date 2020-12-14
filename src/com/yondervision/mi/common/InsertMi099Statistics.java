package com.yondervision.mi.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi107DAO;
import com.yondervision.mi.dao.Mi099DAO;
import com.yondervision.mi.dto.CMi107;
import com.yondervision.mi.dto.Mi099;
import com.yondervision.mi.util.CommonUtil;

public class InsertMi099Statistics {
	public void insertMi099() throws Exception{
		Logger log = LoggerUtil.getLogger();
		CMi107DAO cmi107Dao = (CMi107DAO)com.yondervision.mi.util.SpringContextUtil.getBean("cmi107Dao");
		Mi099DAO mi099Dao = (Mi099DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi099Dao");
		
		//获取前一天交易日期
		Calendar c = Calendar.getInstance(); 
		Date date = new Date(); 
		c.setTime(date); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day-1); 
		String dayBefore=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		log.info("统计日期："+dayBefore);
//		dayBefore = "2017-09-04";
		CMi107 form = new CMi107();
		form.setMitransdate(dayBefore);
		List<HashMap> result = cmi107Dao.webapi1071003(form);
		form.setStartdate(dayBefore);
		form.setEnddate(dayBefore);
		//2017-09-01 syw
		List<HashMap<String,String>> list107 = cmi107Dao.selectWebapi10723(form);
		
		
		for(int i=0; i<result.size(); i++){
			log.info("insert start！");
			Mi099 record = new Mi099();
			HashMap map1 = result.get(i);
			String centerid = map1.get("centerid").toString();
			log.info("centerid："+centerid);
			String transtype = map1.get("transtype").toString();
			log.info("transtype："+transtype);
			String money = map1.get("money").toString();
			log.info("money："+money);
			String pid = map1.get("pid").toString();
			log.info("pid："+pid);
			String freeuse1 = map1.get("freeuse1").toString();
			log.info("freeuse1："+freeuse1);
			String cnt = map1.get("cnt").toString();
			log.info("cnt："+cnt);
			String channel = map1.get("channel").toString();
			log.info("channel："+channel);
			String servicetype = map1.get("servicetype").toString();
			log.info("servicetype："+servicetype);
			String serviceid = map1.get("serviceid").toString();
			log.info("serviceid："+serviceid);
			String dicid = map1.get("dicid").toString();
			log.info("dicid："+dicid);
			String itemid = map1.get("itemid").toString();
			log.info("itemid："+itemid);
			String moneytype = map1.get("moneytype").toString();
			log.info("moneytype："+moneytype);
			HashMap map2 = null;
			
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
}
