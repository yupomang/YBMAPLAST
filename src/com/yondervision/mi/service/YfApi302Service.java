/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.service
 * 文件名：     YfApi302Service.java
 * 创建日期：2013-11-4
 */
package com.yondervision.mi.service;

import com.yondervision.mi.form.YfApi30201Form;
import com.yondervision.mi.form.YfApi30202Form;

/**
 * @author LinXiaolong
 * 
 */
public interface YfApi302Service {

	/**
	 * 公积金业务系统发起单笔短信息推送
	 * 
	 * @param form
	 *            form中包含centerId、userId、buzType、accnum、title、detail
	 * @return MI100表中的ID
	 * @throws Exception
	 */
	String yfapi30201(YfApi30201Form form) throws Exception;

	/**
	 * 公积金业务系统发起批量短信息推送
	 * @param form form中包含centerId、userId、buzType、count
	 * @param file 上传的批量数据文件
	 * @return MI100表中的ID
	 * @throws Exception
	 */
	String yfapi30202(YfApi30202Form form, String file) throws Exception;

}
