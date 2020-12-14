package com.yondervision.mi.util;

import java.util.Locale;

import org.springframework.beans.propertyeditors.LocaleEditor;
import org.springframework.context.MessageSource;

/**
 * 静态页面生成工具类
 * @author gongqi
 *
 */
public class FrontUtils {
	/**
	 * 模板后缀
	 */
	public static final String TPL_SUFFIX = ".html";
	
	/**
	 * 获得模板路径。将对模板文件名称进行本地化处理。
	 * @param solution
	 *            方案路径
	 * @param dir
	 *            模板目录。不本地化处理。
	 * @param name
	 *            模板名称。本地化处理。
	 * @return
	 * getTplPath(tplMessageSource, site
				.getLocaleAdmin(), site.getSolutionPath(), TPLDIR_INDEX,
				TPL_INDEX);
	 */
	public static String getTplPath(String solution, String dir, String name) {
		System.out.println("FrontUtils-----getTplPath==name==="+name);
		String path = PropertiesReader.getProperty("properties.properties", name);
		System.out.println("FrontUtils----getTplPath==path==="+path);
		System.out.println("FrontUtils----tpl==last----path==="+(solution + "/" + dir + "/" + path + TPL_SUFFIX));
		return solution + "/" + dir + "/" + path + TPL_SUFFIX;
	}
}
