package com.yondervision.mi.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi711DAO;
import com.yondervision.mi.dao.Mi712DAO;
import com.yondervision.mi.dto.Mi711Example;
import com.yondervision.mi.dto.Mi712;
import com.yondervision.mi.util.CommonUtil;

public class InsertMi712Statistics {
	public void insertMi712() throws Exception{
		Logger log = LoggerUtil.getLogger();
		CMi711DAO mi711Dao = (CMi711DAO)com.yondervision.mi.util.SpringContextUtil.getBean("cmi711Dao");
		Mi712DAO mi712Dao = (Mi712DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi712Dao");
		
		//获取前一天日期
		Calendar c = Calendar.getInstance(); 
		Date date = new Date(); 
		c.setTime(date); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day-1); 
		String dayBefore=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		System.out.println("统计日期："+dayBefore);
		Mi711Example example=new Mi711Example();
		example.createCriteria().andDatecreatedLessThanOrEqualTo(dayBefore+" 23:59:59")
		.andDatecreatedGreaterThanOrEqualTo(dayBefore+" 00:00:00").andValidflagEqualTo("1");
		List<HashMap<String,Object>> result=mi711Dao.selectGroupMi711(example);
		
		for(int i=0; i<result.size(); i++){
			log.info("insert start！");
			Mi712 record = new Mi712();
			HashMap map1 = result.get(i);
			String centerid = map1.get("centerid").toString();
			log.info("centerid："+centerid);
			String seqno = map1.get("seqno").toString();
			log.info("seqno："+seqno);
			String classfication = map1.get("classfication").toString();
			log.info("classfication："+classfication);
			String title = map1.get("title").toString();
			log.info("title："+title);
			String cnt = map1.get("cnt").toString();
			log.info("cnt："+cnt);
			String freeuse1 = map1.get("pid").toString();
			log.info("freeuse1："+cnt);
			
			record.setSeqno(Integer.parseInt(seqno));
			record.setCenterid(centerid);
			record.setClassfication(classfication);
			record.setTitle(title);
			record.setCnt(Integer.parseInt(cnt));
			record.setDaydate(dayBefore);
			record.setValidflag("1");
			record.setDatecreated(CommonUtil.getSystemDate());
			record.setFreeuse1(freeuse1);
			mi712Dao.insert(record);
			log.info("insert end！");
		}
	}
}
