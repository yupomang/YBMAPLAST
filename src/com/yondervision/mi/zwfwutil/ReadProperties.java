package com.yondervision.mi.zwfwutil;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ReadProperties {
	private static final ResourceBundle bundle;

	static {
		// 读取propertiest资源文件
		bundle = ResourceBundle.getBundle("resources.config");
	}

	/**
	 * 根据name获得字符串的名
	 * 
	 * @param name
	 * @return
	 */
	public static String getString(String name) {
		String value = null;
		try {
			value = bundle.getString(name);
//			value = new String(value.getBytes("ISO8859-1"), "GBK");
		} catch (MissingResourceException e) {
			SysLog.writeError(e);
		} catch (Exception ee) {
			SysLog.writeError(ee);
		}
		if ("".equals(value)) {
			return null;
		} else {
			return value;
		}
	}

}
