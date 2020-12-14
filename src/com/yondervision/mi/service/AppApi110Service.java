package com.yondervision.mi.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import com.yondervision.mi.dto.Mi110;
import com.yondervision.mi.form.AppApi40102Form;
import com.yondervision.mi.form.AppApiCommonForm;

/** 
* @ClassName: AppApi008Service 
* @Description: 楼盘信息查询
* @author Caozhongyan
* @date Sep 27, 2013 2:50:40 PM   
* 
*/ 
public interface AppApi110Service {
	/**登陆
	 * @param form
	 * @return list<Mi110>
	 * @throws Exception
	 */
	public List<Mi110> appApi11001Select(AppApiCommonForm form) throws Exception;
	/**解绑
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public List<Mi110> appApi11002Select(AppApiCommonForm form) throws Exception;
	/**注册新加记录
	 * @param form
	 * @return String
	 * @throws Exception
	 */
	public String appApi11003Insert(AppApiCommonForm form) throws Exception;
	/**绑定验证新加
	 * @param form
	 * @return String
	 * @throws Exception
	 */
	public String appApi11004Insert(AppApiCommonForm form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception;
	/**注销标识修改
	 * @param form
	 * @return list<Mi110>
	 * @throws Exception
	 */
	public List<Mi110> appApi11005Select(AppApiCommonForm form) throws Exception;
	
	/**绑定信息检索
	 * @param form
	 * @return list<Mi110>
	 * @throws Exception
	 */
	public List<Mi110> appApi11006Select(AppApi40102Form form) throws Exception;
	
	/**更新实名身份认证信息
	 * 预留手机号freeuse1、收款行卡号freeuse2、身份认证标记freeuse3=1
	 * @param unifyuserid
	 * @param form
	 * @return list<Mi110>
	 * @throws Exception
	 */
	public void appApi11007Update(String unifyuserid, AppApi40102Form form) throws Exception;
	
}
