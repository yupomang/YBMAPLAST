package com.yondervision.mi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

public class ReadProperty {
	private static final Properties properties = new Properties();

	static {
		try {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("yd.properties");
			properties.load(new BufferedReader(new InputStreamReader(is, "utf-8")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getString(String name) {
		String value = properties.getProperty(name);
		if ((value == null) || ("".equals(value))) {
			return null;
		}
		return value;
	}

	public static String getString(String name, String defvalue) {
		String value = properties.getProperty(name);
		if ((value == null) || ("".equals(value))) {
			return defvalue;
		}
		return value;
	}

	public static URL getResource(String name) {
		return ReadProperty.class.getResource(name);
	}
}