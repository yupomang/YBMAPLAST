package com.yondervision.mi.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.InsertMi098Statistics;
import com.yondervision.mi.common.InsertMi099Statistics;
import com.yondervision.mi.common.InsertMi712Statistics;
import com.yondervision.mi.common.log.LoggerUtil;

public class InsertMi099Data {
	@Autowired
	private InsertMi099Statistics insertMi099Statistics;
	@Autowired
	private InsertMi098Statistics insertMi098Statistics;
	@Autowired
	private InsertMi712Statistics insertMi712Statistics;
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		SimpleDateFormat timestampformatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		String logText = "交易统计Mi099表插入定时处理";
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText(logText));
		log.info("开始--当前系统时间："+timestampformatter.format(new Date()));
		
		try {
			insertMi099Statistics.insertMi099();
			insertMi712Statistics.insertMi712();
			insertMi098Statistics.insertMi098();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		log.info("结束--当前系统时间："+timestampformatter.format(new Date()));
		log.info(LOG.END_BUSIN.getLogText(logText));
	}
	public InsertMi099Statistics getInsertMi099Statistics() {
		return insertMi099Statistics;
	}
	public void setInsertMi099Statistics(InsertMi099Statistics insertMi099Statistics) {
		this.insertMi099Statistics = insertMi099Statistics;
	}
	public InsertMi098Statistics getInsertMi098Statistics() {
		return insertMi098Statistics;
	}
	public void setInsertMi098Statistics(InsertMi098Statistics insertMi098Statistics) {
		this.insertMi098Statistics = insertMi098Statistics;
	}
	public InsertMi712Statistics getInsertMi712Statistics() {
		return insertMi712Statistics;
	}
	public void setInsertMi712Statistics(InsertMi712Statistics insertMi712Statistics) {
		this.insertMi712Statistics = insertMi712Statistics;
	}
	
}
