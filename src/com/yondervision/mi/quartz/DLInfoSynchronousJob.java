package com.yondervision.mi.quartz;
/**
 * 大连信息同步Timer
 **/

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.Mi125DAO;
import com.yondervision.mi.dto.Mi125;
import com.yondervision.mi.dto.Mi125Example;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;

/**
 * <pre>大连信息同步定时任务</pre>
 * @PackageName: com.yondervision.mi.auto
 * @FileName: DLInfoSynchronousJob.java
 */

public class DLInfoSynchronousJob {
	
	private String centerid = "00041100";
	//private SimpleDateFormat timestampformatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
	
	@Autowired
	public DLInfoSynchronous dlInfoSynchronous = null;
	public void setDlInfoSynchronous(DLInfoSynchronous dlInfoSynchronous) {
		this.dlInfoSynchronous = dlInfoSynchronous;
	}

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		SimpleDateFormat timestampformatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		String logText = "内容同步定时处理";
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText(logText));
		log.info("开始--当前系统时间："+timestampformatter.format(new Date()));
		
		int seqno = 0;
		try {
			//seqno = CommonUtil.genKeyStatic("MI125").intValue();
			CommonUtil commonUtil = (CommonUtil)com.yondervision.mi.util.SpringContextUtil.getBean("commonUtil");
			seqno = commonUtil.genKey("MI125").intValue();
			// TODO 先忽略
			insertDLLogTable(seqno, centerid, "appapi99999", "新闻同步");
			
			String resMsg = dlInfoSynchronous.synHttp();
			dlInfoSynchronous.synDataDeal(resMsg);
			
			updDLLogTable(seqno, Constants.DL_LOG_BZ_SUC, "处理成功");
		} catch (JSONException e) {
			String msg = exceptionMsgDeal(e);
			try {
				updDLLogTable(seqno, Constants.DL_LOG_BZ_ERR, msg);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			String msg = exceptionMsgDeal(e);
			try {
				updDLLogTable(seqno, Constants.DL_LOG_BZ_ERR, msg);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		log.info("结束--当前系统时间："+timestampformatter.format(new Date()));
		log.info(LOG.END_BUSIN.getLogText(logText));
	}
	
	/**
	 * 向大连日志信息表插入一条记录
	 * @param centerid
	 * @param buzType 外围交易码
	 * @param buzName 交易描述
	 * @throws Exception 
	 */
	private void insertDLLogTable(int seqno, String centerid, String buzType, String buzName) throws Exception{
		Mi125 mi125 = new Mi125();
		Date date = new Date();
	
		mi125.setId(seqno);
		mi125.setJyrq(date);
		mi125.setQdly(PropertiesReader.getProperty("properties.properties", "quarz_chanel_source"));
		mi125.setWwjym(buzType);
		mi125.setJyms(buzName);
		mi125.setWwfqsj(date);
		mi125.setWwfssj(date);
		mi125.setYwlb("2");
		
		Mi125DAO mi125Dao = (Mi125DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi125Dao");
		mi125Dao.insert(mi125);
	}
	
	/**
	 * 更新大连业务日志表记录
	 * @param req
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void updDLLogTable(int seqno, String dealStat, String description) throws Exception {
		Logger logger = com.yondervision.mi.common.log.LoggerUtil.getLogger();
		
		// 获取对应此seqno的记录
		Mi125Example example = new Mi125Example();
		example.createCriteria().andIdEqualTo(seqno);
		Mi125DAO mi125Dao = (Mi125DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi125Dao");
		List<Mi125> qryList = mi125Dao.selectByExample(example);
		
		if (CommonUtil.isEmpty(qryList)) {
			logger.error(ERROR.NO_DATA.getLogText("大连业务日志MI125","业务SEQNO："+seqno));
			throw new TransRuntimeErrorException(WEB_ALERT.BUZ_LOG_SYS.getValue(),"没有对应业务SEQNO："+
					seqno+"的记录");
		}
		
		// 对此记录进行更新
		Mi125 mi125 = new Mi125();
		Date date = new Date();
		
		mi125.setWwjssj(date);
		mi125.setWwjssj1(date);
		mi125.setBz(dealStat);
		mi125.setSbyy(description);
		
		mi125Dao.updateByExampleSelective(mi125, example);
	}
	
	private String exceptionMsgDeal(Exception e){
		String msg = null;
		if (e instanceof TransRuntimeErrorException) {
			TransRuntimeErrorException e2=(TransRuntimeErrorException)e;
			String mes = e2.getMessage();
			if (mes == null)
				mes = "空null";
			int wz = mes.indexOf("Exception:");
			if (wz > 0){
				wz += 10;
				msg = mes.substring(wz);
			}else{
				msg = mes;
			}
			
		} else {
			msg = "系统错误，请联系管理员！";
		}
		return msg;
	}
}
