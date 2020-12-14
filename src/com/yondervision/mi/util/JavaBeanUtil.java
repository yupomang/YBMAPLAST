/**
 * 
 */
package com.yondervision.mi.util;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.yondervision.mi.result.TitleInfoBean;
import com.yondervision.mi.result.TitleInfoNameFormatBean;

/**
 * @author LinXiaolong
 * 
 */
public class JavaBeanUtil {

	private static final String PROPERTIES_FILE_NAME = "titleinfo.properties";

	public static List<TitleInfoBean> javaBeanToListTitleInfoBean(
			String propertiesKey, Object javaBean) {
		List<TitleInfoBean> listTitleInfoBean = new ArrayList<TitleInfoBean>();
		String strKeys = PropertiesReader.getProperty(PROPERTIES_FILE_NAME,
				propertiesKey);
		if (strKeys != null) {
			String [] pstrKeys = strKeys.split(",");
			for (int i = 0; i < pstrKeys.length; i++) {
				String name = pstrKeys[i].trim();
				String key = propertiesKey + "." + name;
				String title = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, key);
				String info = (String) getter(javaBean, name);
				
				TitleInfoBean titleInfoBean = new TitleInfoBean();
				titleInfoBean.setTitle(title);
				titleInfoBean.setInfo(info);
				listTitleInfoBean.add(titleInfoBean);
			}
		}

		return listTitleInfoBean;
	}

	public static void setter(Object pojo, String name, Object value) {
		try {
			// 取得这个参数在Bean中的数据类开
			Class<?> cls = PropertyUtils.getPropertyType(pojo, name);
			// 把相应的数据转换成对应的数据类型
			// System.out.println(name+":"+cls);

			// modified by yin.hl
			Object beanValue = null;
			if (cls.getSimpleName().equals("BigDecimal")
					&& (value == null || "".equals(value))) {
				beanValue = new BigDecimal(0);
			} else {
				beanValue = ConvertUtils.convert(value, cls);
			}

			// 添冲Bean值
			PropertyUtils.setProperty(pojo, name, beanValue);

		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getter(Object pojo, String name) {
		String value = null;
		try {
			Object valueTemp = PropertyUtils.getProperty(pojo, name);
			value = (String) ConvertUtils.convert(valueTemp, String.class);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// modified by yin.hl 当Pojo的某属性为空时,会出现NullPointer异常
		return value == null ? "" : value.toString();
	}

	public static Object creatJavaBean(String className) {
		Object pojo = null;
		try {
			Class<?> pojoClass = Class.forName(className);
			pojo = pojoClass.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pojo;
	}
	
	public static List<TitleInfoNameFormatBean> javaBeanToListTitleInfoNameFormatBean(
			String propertiesKey, Object javaBean) {
		Logger log = Logger.getLogger("YFMAP");
		List<TitleInfoNameFormatBean> listTitleInfoBean = new ArrayList<TitleInfoNameFormatBean>();
		String strKeys = PropertiesReader.getProperty(PROPERTIES_FILE_NAME,
				propertiesKey);
		if (strKeys != null) {
			String [] pstrKeys = strKeys.split(",");
			for (int i = 0; i < pstrKeys.length; i++) {
				String name = pstrKeys[i].trim();
				String key = propertiesKey + "." + name;
				String title = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, key);
				
				String[] values = title.split(",");
				
				String info = (String) getter(javaBean, name);
				
				TitleInfoNameFormatBean titleInfoBean = new TitleInfoNameFormatBean();
				titleInfoBean.setTitle(values[0]);
				titleInfoBean.setInfo(info);
				titleInfoBean.setName(name);
				if(values.length>1)
					titleInfoBean.setFormat(values[1]);
				
				listTitleInfoBean.add(titleInfoBean);
				System.out.println("key :"+key+"  ,  title :"+title+"  ,  info :"+info);
			}
		}

		return listTitleInfoBean;
	}
}
