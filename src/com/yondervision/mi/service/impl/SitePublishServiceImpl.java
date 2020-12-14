package com.yondervision.mi.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.directive.Appapi41101ListDirective;
import com.yondervision.mi.directive.Appapi70001ListDirective;
import com.yondervision.mi.directive.Appapi70002ListDirective;
import com.yondervision.mi.directive.Appapi70009ListDirective;
import com.yondervision.mi.directive.Appapi70012ListDirective;
import com.yondervision.mi.directive.Appapi90501ListDirective;
import com.yondervision.mi.service.SitePublishService;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/** 
* @ClassName: SitePublishServiceImpl
* @Description: 站点发布
* @author gongqi
* @date July 16, 2014 9:33:25 PM  
* 
*/ 
public class SitePublishServiceImpl implements SitePublishService {
	
	/**
	 * 首页静态页面生成
	 */
	public void indexStatic(String centerId, HttpServletRequest request) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		boolean indexRoot = false;
		String staticDir = "";
		staticDir = PropertiesReader.getProperty("properties.properties", "staticDirconfig"+centerId);
		if(CommonUtil.isEmpty(staticDir)){
			staticDir = "/html";
		}
		String staticSuffix = "";
		staticSuffix = PropertiesReader.getProperty("properties.properties", "staticSuffixconfig"+centerId);
		if(CommonUtil.isEmpty(staticSuffix)){
			staticSuffix = ".html";
		}
		
		String tpl = PropertiesReader.getProperty("properties.properties", "indexstaticconfig"+centerId);
		index(centerId, tpl, data, indexRoot, staticDir, staticSuffix, request);
		
	}
	
	/**
	 * 栏目静态页面生成
	 * @param reqUrl
	 * @throws Exception
	 */
	public void classficationStatic(String centerId, HttpServletRequest request) throws Exception {
		
		Map<String, Object> data = new HashMap<String, Object>();
		boolean indexRoot = false;
		String staticDir = "";
		staticDir = PropertiesReader.getProperty("properties.properties", "staticDirconfig"+centerId);
		if(CommonUtil.isEmpty(staticDir)){
			staticDir = "/html";
		}
		String staticSuffix = "";
		staticSuffix = PropertiesReader.getProperty("properties.properties", "staticSuffixconfig"+centerId);
		if(CommonUtil.isEmpty(staticSuffix)){
			staticSuffix = ".html";
		}
		
		String config = PropertiesReader.getProperty("properties.properties", "classficationstaticconfig"+centerId);
		String[] configs = config.split(",");
		for(int i = 0; i < configs.length; i ++){
			//String classfication = configs[i].split(":")[0];
			String tpl = configs[i].split(":")[1];
			classfication(centerId, tpl, data, indexRoot, staticDir, staticSuffix, request);
		}
	}
	
	/**
	 * 内容静态页面生成
	 * @param reqUrl
	 * @throws Exception
	 */
	public void contentStatic(String centerId, HttpServletRequest request) throws Exception {
		
		Map<String, Object> data = new HashMap<String, Object>();
		boolean indexRoot = false;
		String staticDir = "";
		staticDir = PropertiesReader.getProperty("properties.properties", "staticDirconfig"+centerId);
		if(CommonUtil.isEmpty(staticDir)){
			staticDir = "/html";
		}
		String staticSuffix = "";
		staticSuffix = PropertiesReader.getProperty("properties.properties", "staticSuffixconfig"+centerId);
		if(CommonUtil.isEmpty(staticSuffix)){
			staticSuffix = ".html";
		}
		
		String config = PropertiesReader.getProperty("properties.properties", "contentstaticconfig"+centerId);
		String[] configs = config.split(",");
		for(int i = 0; i < configs.length; i ++){
			//String classfication = configs[i].split(":")[0];
			String tpl = configs[i].split(":")[1];
			// TODO 要修改成内容的
			classfication(centerId, tpl, data, indexRoot, staticDir, staticSuffix, request);
		}
	}
	
	public void index(String centerId, String tpl, Map<String, Object> data, 
			boolean indexRoot, String staticDir,
			String staticSuffix, HttpServletRequest request)
			throws IOException{
		long time = System.currentTimeMillis();
		String indexpath = getIndexPath(indexRoot, staticDir, staticSuffix, request);
		System.out.println("indexpath======="+indexpath);
		
		File f = new File(indexpath);
		File parent = f.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		Writer out = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(f), "UTF8");
			
			Configuration cfg = new Configuration();
			cfg.setEncoding(Locale.getDefault(), "UTF-8");
			
			String pathurl = request.getSession().getServletContext().getRealPath("");
			System.out.println("pathurl====="+pathurl);
			String templateUrlTmp = PropertiesReader.getProperty("properties.properties", "templateUrl"+centerId);
			String templateUrl = pathurl+templateUrlTmp;
			System.out.println("templateUrl====="+templateUrl);
			
			cfg.setDirectoryForTemplateLoading(new File(templateUrl));
			cfg.setSharedVariable("cms_appapi41101_list", new Appapi41101ListDirective());
			cfg.setSharedVariable("cms_appapi70009_list", new Appapi70009ListDirective());
			cfg.setSharedVariable("cms_appapi70001_list", new Appapi70001ListDirective());
			cfg.setSharedVariable("cms_appapi90501_list", new Appapi90501ListDirective());
			cfg.setSharedVariable("cms_appapi70002_list", new Appapi70002ListDirective());
			cfg.setSharedVariable("cms_appapi70012_list", new Appapi70012ListDirective());
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			
			Template temp = cfg.getTemplate(tpl);
		       
			temp.process(data, out);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
		time = System.currentTimeMillis() - time;
		System.out.println("create index page, use time   ms===="+time);
	}
	
	public void classfication(String centerId, String tpl, Map<String, Object> data, 
			boolean indexRoot, String staticDir,
			String staticSuffix, HttpServletRequest request)
			throws IOException{
		long time = System.currentTimeMillis();
		String classficationpath = getClassficationPath(tpl, indexRoot, staticDir, staticSuffix, request);
		System.out.println("indexpath======="+classficationpath);
		File f = new File(classficationpath);
		File parent = f.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		Writer out = null;
		try {
			// FileWriter不能指定编码确实是个问题，只能用这个代替了。
			out = new OutputStreamWriter(new FileOutputStream(f), "UTF8");
			
			Configuration cfg = new Configuration();
			cfg.setEncoding(Locale.getDefault(), "UTF-8");
			
			System.out.println("request.getRequestURI()==="+request.getRequestURI());
			System.out.println("request.getLocalAddr()==="+request.getLocalAddr());
			System.out.println("request.getRequestURL()==="+request.getRequestURL());
			System.out.println("request.getContextPath()==="+request.getContextPath());
			
			
			String pathurl = request.getSession().getServletContext().getRealPath("");
			System.out.println("pathurl====="+pathurl);
			String templateUrlTmp = PropertiesReader.getProperty("properties.properties", "templateUrl"+centerId);
			String templateUrl = pathurl+templateUrlTmp;
			System.out.println("templateUrl====="+templateUrl);
			cfg.setDirectoryForTemplateLoading(new File(templateUrl));
//			cfg.setSharedVariable("cms_appapi41101_list", new Appapi41101ListDirective());
//			cfg.setSharedVariable("cms_appapi70009_list", new Appapi70009ListDirective());
			cfg.setSharedVariable("cms_appapi70001_list", new Appapi70001ListDirective());
//			cfg.setSharedVariable("cms_appapi90501_list", new Appapi90501ListDirective());
			cfg.setSharedVariable("cms_appapi70002_list", new Appapi70002ListDirective());
//			cfg.setSharedVariable("cms_appapi70012_list", new Appapi70012ListDirective());
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			
			Template temp = cfg.getTemplate(tpl);
		       
			temp.process(data, out);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
		time = System.currentTimeMillis() - time;
		System.out.println("create classfication page, use time   ms===="+time);
	}
	
	private String getIndexPath(boolean indexRoot, String staticDir,
			String staticSuffix, HttpServletRequest request) {
		StringBuilder pathBuff = new StringBuilder();
		if (!indexRoot) {
			if (!StringUtils.isBlank(staticDir)) {
				pathBuff.append(staticDir);
			}
		}
		pathBuff.append("/").append(Constants.INDEX).append(staticSuffix);
		System.out.println("getIndexPath-----pathBuff==="+pathBuff.toString());
		return request.getRealPath(pathBuff.toString());
	}
	
	
	private String getClassficationPath(String tpl, boolean indexRoot, String staticDir,
			String staticSuffix, HttpServletRequest request) {
		StringBuilder pathBuff = new StringBuilder();
		if (!indexRoot) {
			if (!StringUtils.isBlank(staticDir)) {
				pathBuff.append(staticDir);
			}
		}
		// TODO 注意之后所有栏目都生成静态页时，下述静态页面的名字要修改。现在是什么名字模板就生成什么名字的静态页
		pathBuff.append("/").append(tpl.substring(0, tpl.indexOf("."))).append(staticSuffix);
		System.out.println("getClassficationPath-----pathBuff==="+pathBuff.toString());
		return request.getRealPath(pathBuff.toString());
	}

}
