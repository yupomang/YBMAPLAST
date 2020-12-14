/**
 * @Title: PropertiesReader.java
 * @Package com.yondervision.common.util
 * @Description: 读取配置文件
 * Copyright: Copyright (c) 2009
 * Company: pkpm
 * @author 林小龙
 * @date 2012-4-18 下午03:04:01
 *@version 1.0
 */
package com.yondervision.mi.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.yondervision.mi.dao.Mi041DAO;
import com.yondervision.mi.dto.Mi041;
import com.yondervision.mi.dto.Mi041Example;

/**
 * @ClassName: PropertiesReader
 * @Description: 读取配置文件
 * @author 林小龙
 * @date 2012-4-18 下午03:04:01
 */
public class PropertiesReader {
	private static Logger logger = Logger.getLogger(PropertiesReader.class
			.getName());
	private static Map<String, Properties> mapProperties;
	static {
		mapProperties = new HashMap<String, Properties>();
	}

	private static Properties getPropertiesByName(String propertiesName) {
		Properties prpoperties = mapProperties.get(propertiesName);
		if (prpoperties != null) {
			return prpoperties;
		}
		prpoperties = new Properties();
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		if (classLoader == null) {
			classLoader = ClassLoader.getSystemClassLoader();
		}

		InputStream is = classLoader.getResourceAsStream(propertiesName);
		try {
			prpoperties.load(is);
		} catch (Exception e) {
			logger.error("取配置文件[" + propertiesName + "]失败", e);
		}
		mapProperties.put(propertiesName, prpoperties);
		return prpoperties;
	}

	/**
	 * @Title: getProperty
	 * @Description:通过配置文件名和配置属性取得配置值
	 * @param @param fileName 配置文件名
	 * @param @param propertyName 配置属性
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 * @date 2012-4-18 下午03:22:58
	 */
	public static String getProperty(String fileName, String propertyName) {
		Properties prpoperties = getPropertiesByName(fileName);
		return prpoperties.getProperty(propertyName);
	}

	/**
	 * @Title: getHeatbeatURL
	 * @Description:通过心跳地址获取渠道URL
	 * @param centerId
	 * @param channel
	 * @return String 返回类型
	 */
	public static String getHeartbeatURL(String centerId, String pid){
		System.out.println("centerId:" + centerId + " pid:" + pid);
		Mi041DAO mi041Dao = (Mi041DAO)SpringContextUtil.getBean("mi041DAO");
		Mi041Example mi041Example = new Mi041Example();
		if(pid.length() == 2){
			mi041Example.createCriteria()
				.andCenteridEqualTo(centerId).andChannelEqualTo(pid);
		}else{
			mi041Example.createCriteria()
				.andCenteridEqualTo(centerId).andPidEqualTo(pid);
		}
		List<Mi041> list = mi041Dao.selectByExample(mi041Example);
		String result = "";
		if(!CommonUtil.isEmpty(list)){
			String tmp = list.get(0).getCheckurl();
			System.out.println("心跳地址：" + tmp);
			int index = tmp.indexOf("heartbeat")-1;
			result = tmp.substring(0, index);
		}
		System.out.println("渠道URL：" + result);
		return result;
	}
}
