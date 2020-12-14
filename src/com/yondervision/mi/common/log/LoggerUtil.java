package com.yondervision.mi.common.log;
/** 
* @ClassName: LoggerUtil 
* @Description: 日志对象
* @author 韩占远
* @date 2013-10-04  
* 
*/ 

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

import com.yondervision.mi.dao.Mi009DAO;
import com.yondervision.mi.dto.Mi009Example;

 

@SuppressWarnings("unchecked")
public class LoggerUtil {
	static Map<String,Logger> loggerSet=new HashMap();
	public static Logger getLogger() {
		 String name=(String)com.yondervision.mi.common.filter.PermissionEncodingFilter.threadRequetPath.get();		 
		 
		 if(name==null)
			 name="other";
		 if(name.equals("null"))
			 name="other";	 
		 Logger logger = null;
		 if(loggerSet.get(name)==null){
			 logger=Logger.getLogger(name); 
			 loggerSet.put(name, logger);
			 RollingFileAppender ydf=new RollingFileAppender();
			 String initpath=YDLogFileAppender.getInitPath();	
			 if(!initpath.endsWith("/")){
				 initpath+="/";
			 }		 
			 ydf.setFile(initpath+name); 
			 ydf.setEncoding("UTF-8");	
			 PatternLayout layout = new PatternLayout();
			 String conversionPattern = ">>> %d %5p [%t] (%F:%L) - %m%n";
			 layout.setConversionPattern(conversionPattern);
			 ydf.setLayout(layout);
			 ydf.setAppend(true); 
			 ydf.setEncoding("UTF-8");
			 ydf.activateOptions();			 
			 logger.setAdditivity(true);
			 logger.addAppender(ydf); 
			 logger.addAppender( new ConsoleAppender());
		 }else{
			 logger=loggerSet.get(name);
		 }  
		 return logger;
	}
	
    static Map<String,String> logTextMap = new HashMap<String, String>();
	public static String getLogText(String errcode,String... mes){
		if(logTextMap==null)
			logTextMap=new HashMap<String,String>();
		String text=errcode+"";
		if(logTextMap.get(errcode)==null){
			Mi009DAO mi009Dao = (Mi009DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi009Dao");
			Mi009Example m9e=new Mi009Example();
			m9e.createCriteria().andLogcodeEqualTo(errcode);
			List<com.yondervision.mi.dto.Mi009> list=mi009Dao.selectByExample(m9e);
			if(list.size()>0)
				text=list.get(0).getLogtext();			
		} 
		for (int i = 0; i < mes.length; i++) {
			System.out.println(text+":"+mes[i]);
			if(mes[i].indexOf("$") != -1){
				mes[i] = mes[i].replaceAll("\\$", "\\\\\\$");  
			}
			text=text.replaceAll("\\{" + String.valueOf(i) + "\\}", mes[i]);
		}
		return text;
	}
 
	/**
	 * 清楚日志内容缓存，在日志内容有变更时调用
	 */
	public static void clearLogTextMap() {
		logTextMap.clear();
	}
}
