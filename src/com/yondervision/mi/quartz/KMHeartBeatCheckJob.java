package com.yondervision.mi.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.HeartBeatCheck;
import com.yondervision.mi.common.log.LoggerUtil;

public class KMHeartBeatCheckJob {
	@Autowired
	private HeartBeatCheck heartBeatCheck;
	
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		SimpleDateFormat timestampformatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		String logText = "心跳检查定时处理";
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText(logText));
		log.info("开始--当前系统时间："+timestampformatter.format(new Date()));
		
		try {
			heartBeatCheck.heartCheack();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		log.info("结束--当前系统时间："+timestampformatter.format(new Date()));
		log.info(LOG.END_BUSIN.getLogText(logText));
	}


	public HeartBeatCheck getHeartBeatCheck() {
		return heartBeatCheck;
	}

	public void setHeartBeatCheck(HeartBeatCheck heartBeatCheck) {
		this.heartBeatCheck = heartBeatCheck;
	}

}
