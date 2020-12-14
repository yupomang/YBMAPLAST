package com.yondervision.mi.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi107DAO;
import com.yondervision.mi.dao.CMi421DAO;
import com.yondervision.mi.dao.CMi422DAO;
import com.yondervision.mi.dao.CMi423DAO;
import com.yondervision.mi.dao.Mi001DAO;
import com.yondervision.mi.dao.Mi045DAO;
import com.yondervision.mi.dao.Mi401DAO;
import com.yondervision.mi.dao.Mi402DAO;
import com.yondervision.mi.dao.Mi403DAO;
import com.yondervision.mi.dao.Mi424DAO;
import com.yondervision.mi.dto.Mi001;
import com.yondervision.mi.dto.Mi001Example;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi045;
import com.yondervision.mi.dto.Mi045Example;
import com.yondervision.mi.dto.Mi099;
import com.yondervision.mi.dto.Mi401Example;
import com.yondervision.mi.dto.Mi402Example;
import com.yondervision.mi.dto.Mi403Example;
import com.yondervision.mi.dto.Mi423;
import com.yondervision.mi.dto.Mi424;
import com.yondervision.mi.service.CodeListApi001Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;
import com.yondervision.mi.util.SpringContextUtil;
import com.yondervision.mi.util.couchbase.CouchBase;
import com.yondervision.mi.util.couchbase.JsonUtil;

public class TransferMessage {
	public void transfer() throws Exception{
		try{
			Logger log = LoggerUtil.getLogger();
			log.info("transfer start！");
			Date date = new Date();
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			String currentDate = dateformat.format(date);
			int n = Integer.valueOf(PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "transferMessage"));
			//sql里使用<，使用n-1表示n天前的数据
			String beforeDate = CommonUtil.getSpecifiedDayBefore(currentDate, n-1);
			log.info("beforeDate:" + beforeDate);
			log.info("备份mi401表");
			CMi421DAO cmi421Dao = (CMi421DAO)com.yondervision.mi.util.SpringContextUtil.getBean("cmi421Dao");
			cmi421Dao.batchInsert(beforeDate);
			log.info("备份mi402表");
			CMi422DAO cmi422Dao = (CMi422DAO)com.yondervision.mi.util.SpringContextUtil.getBean("cmi422Dao");
			cmi422Dao.batchInsert(beforeDate);
			log.info("备份mi403表");
			CMi423DAO cmi423Dao = (CMi423DAO)com.yondervision.mi.util.SpringContextUtil.getBean("cmi423Dao");
			cmi423Dao.batchInsert(beforeDate);
			
			log.info("开始删除历史数据！");
			List<String> list = new ArrayList();
			list.add("1");
			list.add("2");
			log.info("删除mi401表历史数据");
			Mi401DAO mi401Dao = (Mi401DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi401Dao");
			Mi401Example mi401Example = new Mi401Example();
			Mi401Example.Criteria m401Criteria = mi401Example.createCriteria();
			m401Criteria.andStatusIn(list)
				.andValidflagEqualTo("1")
				.andDatecreatedLessThan(beforeDate);
			mi401Dao.deleteByExample(mi401Example);
			
			log.info("删除mi402表历史数据");
			Mi402DAO mi402Dao = (Mi402DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi402Dao");
			Mi402Example mi402Example = new Mi402Example();
			Mi402Example.Criteria m402Criteria = mi402Example.createCriteria();
			m402Criteria.andStatusIn(list)
				.andValidflagEqualTo("1")
				.andDatecreatedLessThan(beforeDate);
			mi402Dao.deleteByExample(mi402Example);
			
			log.info("删除mi403表历史数据");
			Mi403DAO mi403Dao = (Mi403DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi403Dao");
			Mi403Example mi403Example = new Mi403Example();
			Mi403Example.Criteria m403Criteria = mi403Example.createCriteria();
			m403Criteria.andStatusIn(list)
				.andValidflagEqualTo("1")
				.andDatecreatedLessThan(beforeDate);
			mi403Dao.deleteByExample(mi403Example);
			
//统计开始======================================
			//定时统计入mi424表
			Mi424DAO mi424Dao = (Mi424DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi424Dao");
			
			//获取前一天交易日期
			Calendar c = Calendar.getInstance(); 
			c.setTime(date); 
			int day=c.get(Calendar.DATE); 
			c.set(Calendar.DATE,day-1); 
			String dayBefore=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
			log.info("统计日期："+dayBefore);
			//dayBefore = "2017-07-11";
			List<HashMap> resultTemplate = cmi423Dao.messageStatisticsTemplate(dayBefore);
			List<HashMap> result = cmi423Dao.messageStatistics(dayBefore);
			
			for(int i=0; i<result.size(); i++){
				log.info("insert 01 02 type statistics data");
				HashMap map1 = result.get(i);
				log.info("result:"+map1);
				Mi424 record = new Mi424();
				record.setId(CommonUtil.genKeyStatic("MI424",10));
				record.setCenterid(String.valueOf(map1.get("centerid")));
				record.setChannel(String.valueOf(map1.get("pid")).substring(0, 2));
				record.setPid(String.valueOf(map1.get("pid")));
				record.setPusMessageType(String.valueOf(map1.get("pus_message_type")));
				record.setDatecreated(CommonUtil.getSystemDate());
				record.setDatemodified(CommonUtil.getSystemDate());
				int succ = Integer.valueOf(String.valueOf(map1.get("succ")));
				int fail = Integer.valueOf(String.valueOf(map1.get("fail")));
				record.setTranscnt(succ + fail);
				record.setFailcnt(fail);
				record.setSucccnt(succ);
				record.setValidflag("1");
				record.setTransdate(dayBefore);
				mi424Dao.insert(record);
				log.info("insert 01 02 type statistics data end");
			}
			for(int i=0; i<resultTemplate.size(); i++){
				log.info("insert 03 type statistics data");
				HashMap map1 = resultTemplate.get(i);
				log.info("resultTemplate:"+map1);
				Mi424 record = new Mi424();
				record.setId(CommonUtil.genKeyStatic("MI424",10));
				record.setCenterid(String.valueOf(map1.get("centerid")));
				record.setChannel(String.valueOf(map1.get("pid")).substring(0, 2));
				record.setPid(String.valueOf(map1.get("pid")));
				record.setPusMessageType("03");
				record.setDatecreated(CommonUtil.getSystemDate());
				record.setDatemodified(CommonUtil.getSystemDate());
				int succ = Integer.valueOf(String.valueOf(map1.get("succ")));
				int fail = Integer.valueOf(String.valueOf(map1.get("fail")));
				record.setTranscnt(succ + fail);
				record.setFailcnt(fail);
				record.setSucccnt(succ);
				record.setValidflag("1");
				record.setTransdate(dayBefore);
				record.setTheme(String.valueOf(map1.get("theme")));
				mi424Dao.insert(record);
				log.info("insert 03 type statistics data end");
			}
//统计结束======================================
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("transfer 异常");
		}
	}

	public void transferFlow() {
		Mi001DAO mi001Dao = (Mi001DAO)SpringContextUtil.getBean("mi001Dao");
		Mi001Example mi001Example = new Mi001Example();
		mi001Example.createCriteria().andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi001> list = mi001Dao.selectByExample(mi001Example);
		System.out.println("【重务起动，清理流量信息！！！！！2】");
		if(list!=null && !list.isEmpty()){
			CouchBase cb=CouchBase.getInstance();
			for(Mi001 mi001:list){
				String centerid = mi001.getCenterid();
				String key = centerid+"|Flow";
				Object object = cb.get(key);
				if(object!=null && !"".equals(object.toString())){
					Flow flow = JsonUtil.getGson().fromJson(object.toString(), Flow.class);
					flow.setBussinessflow(0);
					flow.setUserFlow(0);
					List<ChannelFlow> channelList = flow.getChannelbusinesses();
					if(channelList!=null && !channelList.isEmpty()){
						for(int i=0;i<channelList.size();i++){
							ChannelFlow channelFlow = channelList.get(i);
							channelFlow.setCountBusinesses(0);
							channelFlow.setCountUser(0);
						}
					}
//					cb.delete(key);
					cb.save(key, flow);
//					CASValue cas = cb.getLock(key, 10);
//					if ( cas.getCas() != -1 ){
//						cb.delete(key);
//						cb.save(key, flow);
//						cb.unLock(key, cas.getCas());
//					}
				}else{
					Mi045DAO mi045Dao = (Mi045DAO)SpringContextUtil.getBean("mi045DAO");
					Mi045Example mi045Example = new Mi045Example();
					mi045Example.createCriteria().andCenteridEqualTo(centerid)
						.andValidflagEqualTo(Constants.IS_VALIDFLAG);
					List<Mi045> list045 = mi045Dao.selectByExample(mi045Example);
					if(!CommonUtil.isEmpty(list045)){
						CodeListApi001Service codeListApi001Service = (CodeListApi001Service)SpringContextUtil.getBean("codeListApi001Service");
						
						Flow flow = new Flow();
						flow.setCenterid(centerid);
						System.out.println("BussinessflowTOP:"+list045.get(0).getBussinessflow());
						if(!CommonUtil.isEmpty(list045.get(0).getBussinessflow())){
							flow.setBussinessflowTOP(Integer.parseInt(list045.get(0).getBussinessflow()));
						}else{
							flow.setBussinessflowTOP(0);
						}
						System.out.println("BussinessflowTOP:"+list045.get(0).getUserflow());
						if(!CommonUtil.isEmpty(list045.get(0).getUserflow())){
							flow.setUserFlowTop(Integer.parseInt(list045.get(0).getUserflow()));
						}else{
							flow.setUserFlowTop(0);
						}
						flow.setBussinessflow(0);
						flow.setUserFlow(0);
						List<Mi007> list1 = codeListApi001Service.getCodeList(centerid, "channel");
						List<ChannelFlow> channelList = new ArrayList<ChannelFlow>();
						if(list1!=null && !list1.isEmpty()){
							for(int i=0;i<list1.size();i++){
								ChannelFlow channelFlow = new ChannelFlow();
								channelFlow.setChannel(list1.get(i).getItemid());
								channelFlow.setCountBusinesses(0);
								channelFlow.setCountUser(0);
								channelList.add(channelFlow);
							}
						}
						flow.setChannelbusinesses(channelList);
						
						CouchBase couchBase = CouchBase.getInstance();
						couchBase.delete(key);
						couchBase.save(key, flow);
					}
				}
				Object obj = cb.get(key);
				System.out.println(centerid+"~~~obj:"+obj);
			}
		}
		System.out.println("流量清零");
		
	}
}
