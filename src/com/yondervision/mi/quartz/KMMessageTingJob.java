package com.yondervision.mi.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.service.WebApi302Service;
import com.yondervision.mi.service.impl.WebApi045ServiceImpl;
import com.yondervision.mi.service.impl.WebApi302ServiceImpl;

public class KMMessageTingJob {

	@Autowired 
	private WebApi302Service webApi302Service;
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		SimpleDateFormat timestampformatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		String logText = "检查定时消息推送_定时处理";
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText(logText));
		log.info("开始--当前系统时间："+timestampformatter.format(new Date()));
		
		try {
			webApi302Service.checkMi415();
		} catch (Exception e) {
			log.info("mi415时间重置出错"+e.getMessage());
		}
		
		try {
			webApi302Service.checkTimingAndSend();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		log.info("结束--当前系统时间："+timestampformatter.format(new Date()));
		log.info(LOG.END_BUSIN.getLogText(logText));
	}

	public WebApi302Service getWebApi302Service() {
		return webApi302Service;
	}

	public void setWebApi302Service(WebApi302Service webApi302Service) {
		this.webApi302Service = webApi302Service;
	}

	
		
}
