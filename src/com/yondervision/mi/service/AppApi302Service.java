/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.service
 * 文件名：     AppApi302Service.java
 * 创建日期：2013-10-24
 */
package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi124;
import com.yondervision.mi.form.AppApi30201Form;
import com.yondervision.mi.form.AppApi30202Form;
import com.yondervision.mi.form.AppApi30203Form;
import com.yondervision.mi.form.AppApi30205Form;
import com.yondervision.mi.form.AppApi30207Form;
import com.yondervision.mi.result.AppApi30201Result;
import com.yondervision.mi.result.AppApi30202Result;

/**
 * @author LinXiaolong
 * 
 */
public interface AppApi302Service {

	/**
	 * 推送信息查询
	 * 
	 * @param form
	 *            form中包含userId、centerId、rowsum、messageid（可空）
	 * @return 推送信息list
	 * @throws Exception
	 */
	List<AppApi30201Result> appApi30201(AppApi30201Form form) throws Exception;

	/**
	 * 推送信息设置为已读
	 * @param form form中包含messageid
	 * @throws Exception
	 */
	void appApi30202(AppApi30202Form form) throws Exception;
	
	AppApi30201Result appApi30203(AppApi30203Form form) throws Exception;
	/**
	 * 查询主题信息
	 * @param form
	 * @throws Exception
	 */
	List<AppApi30202Result> appApi30204(AppApi30202Form form,List<Mi007> list) throws Exception;
	/**
	 * 配置主题信息
	 * @param form
	 * @throws Exception
	 */
	void appApi30205(AppApi30205Form form) throws Exception;
	/**
	 * 免打扰查询
	 * @param form
	 * @throws Exception
	 */
	Mi124 appApi30206(AppApi30207Form form) throws Exception;
	/**
	 * 配置免打扰信息
	 * @param form
	 * @throws Exception
	 */
	void appApi30207(AppApi30207Form form) throws Exception;

}
