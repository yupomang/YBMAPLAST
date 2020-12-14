/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.service
 * 文件名：     AppApi303Service.java
 * 创建日期：2013-11-18
 */
package com.yondervision.mi.service;

import java.util.List;
import java.util.Map;

import com.yondervision.mi.dto.Mi627;
import com.yondervision.mi.form.AppApi30301Form;
import com.yondervision.mi.form.AppApi30302Form;
import com.yondervision.mi.form.AppApi30303Form;
import com.yondervision.mi.form.AppApi30304Form;
import com.yondervision.mi.form.AppApi30305Form;
import com.yondervision.mi.form.AppApi30306Form;
import com.yondervision.mi.form.AppApi30309Form;
import com.yondervision.mi.form.AppApi62601Form;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.AppApi30301Result;
import com.yondervision.mi.result.AppApi30302Result;
import com.yondervision.mi.result.AppApi30303Result;
import com.yondervision.mi.result.AppApi30305Result;
import com.yondervision.mi.result.AppApi30307Result;
import com.yondervision.mi.result.AppApi30308Result;

import net.sf.json.JSONObject;

/**
 * 业务预约
 * 
 * @author LinXiaolong
 * 
 */
public interface AppApi303Service {

	/**
	 * 预约网点查询
	 * 
	 * @param form
	 *            form中包含centerId、bussinesstype
	 * @return 可预约的网点信息
	 * @throws Exception
	 */
	List<AppApi30301Result> appApi30301(AppApi30301Form form) throws Exception;

	/**
	 * 预约日期人数查询
	 * 
	 * @param form
	 *            form中包含centerId、orgid
	 * @return 未来几天的可预约人数信息
	 * @throws Exception
	 */
	List<AppApi30302Result> appApi30302(AppApi30302Form form) throws Exception;

	/**
	 * 预约时间人数查询
	 * 
	 * @param form
	 *            form中包含centerId、orgid、orgiddate
	 * @return orgiddate这天各个时间段可预约的人数信息
	 * @throws Exception
	 */
	List<AppApi30303Result> appApi30303(AppApi30303Form form) throws Exception;

	/**
	 * 预约确定
	 * 
	 * @param form
	 *            form中包含centerId、userId、surplusAccount、orgid、orgiddate、orgidtime
	 *            、bussinesstype
	 * @throws Exception
	 */
	String appApi30304(AppApi30304Form form) throws Exception;

	/**
	 * 我的预约
	 * 
	 * @param form
	 *            form中包含centerId、userId、surplusAccount
	 * @return 个人公积金账号为surplusAccount的预约信息
	 * @throws Exception
	 */
	List<AppApi30305Result> appApi30305(AppApi30305Form form) throws Exception;

	/**
	 * 预约撤销
	 * 
	 * @param form
	 *            form中包含centerId、appointid
	 * @throws Exception
	 */
	void appApi30306(AppApi30306Form form) throws Exception;

	/**
	 * 注意事项
	 * 
	 * @param form
	 *            form中包含centerId
	 * @return 注意事项内容，以每条注意事项之间以逗号分隔
	 * @throws Exception
	 */
	List<AppApi30307Result> appApi30307(AppApi62601Form form) throws Exception;
	/**
	 * 预约业务类型查询
	 * 
	 * @param form
	 *            form中包含centerId
	 * @return 注意事项内容，以每条注意事项之间以逗号分隔
	 * @throws Exception
	 */
	List<AppApi30308Result> appApi30308(AppApiCommonForm form) throws Exception;
	
	//根据省份证查询预约情况，未预约的或未查到信息的返回空
	JSONObject appApi30314(AppApi30301Form form)throws Exception;
	
	//节假日
	List<Mi627> appApi30315(AppApi30309Form form)throws Exception;
	//预约办结确认
	JSONObject appApi30316(AppApi30301Form form)throws Exception;
}
