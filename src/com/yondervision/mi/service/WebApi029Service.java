package com.yondervision.mi.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yondervision.mi.dto.CMi029;
import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.dto.Mi031;
import com.yondervision.mi.dto.Mi040;
import com.yondervision.mi.dto.Mi048;
import com.yondervision.mi.form.AppApi50001Form;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.WebApi02904_queryResult;

import net.sf.json.JSONArray;

public interface WebApi029Service {
	/**
	 * 渠道用户管理-客户信息查询MI029
	 * @param form
	 * @throws Exception
	 */
	public Mi029 webapi02901(AppApi50001Form form) throws Exception;
	/**
	 * 渠道用户管理-客户信息新增MI029
	 * @param form
	 * @throws Exception
	 */
	public void webapi02902(AppApi50001Form form ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	/**
	 * 渠道用户管理-用户设置MI029
	 * @param form
	 * @throws Exception
	 */
	public void webapi02903(CMi029 form) throws Exception;
	
	/**
	 * 分页查询
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public WebApi02904_queryResult webapi02904(CMi029 form) throws Exception;
	/**
	 * 渠道用户管理-查看渠道信息
	 * @param form
	 * @throws Exception
	 */
	public JSONArray webapi02905(CMi029 form) throws Exception;
	/**
	 * 渠道用户管理-加入黑名单
	 * @param form
	 * @throws Exception
	 */
	public void webapi02906(CMi029 form) throws Exception;
	
	
	
	/**
	 * 渠道用户管理-渠道用户查询
	 * @param form
	 * @throws Exception
	 */
	public Mi031 webapi02907(AppApiCommonForm form ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 通过客户号查询客户信息
	 * @param form
	 * @throws Exception
	 */
	public Mi029 webapi02908(String personalid) throws Exception;
	/**
	 * 客户高级验证检查客户信息
	 * @param form
	 * @throws Exception
	 */
	public Mi029 webapi02909(AppApi50001Form form) throws Exception;
	/**
	 * 预留手机号确认
	 * @param form
	 * @throws Exception
	 */
	public Mi029 webapi02910(AppApi50001Form form) throws Exception;

	/**
	 * 客户关联人信息处理
	 * @param form
	 * @throws Exception
	 */
	public void webapi02911(String centerId ,HashMap hasMap ,String personalid ,Mi029 mi029) throws Exception;
	
	/**
	 * 渠道用户信息判断增加
	 * @param form
	 * @throws Exception
	 */
	public String webapi02912(Mi029 mi029 ,AppApi50001Form form ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 个人登录返回信息更新
	 * @param form
	 * @throws Exception
	 */
	public Mi029 webapi02913(Mi029 mi029 ,AppApi50001Form form ,HashMap map ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 认证后更新高级用户信息
	 * @param form
	 * @throws Exception
	 */
	public void webapi02914(Mi029 mi029 ,AppApi50001Form form ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	

	/**
	 * 根据身份证号修改预留手机号
	 * @param form
	 * @throws Exception
	 */
	public void webapi02915(AppApi50001Form form ,HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * 特殊渠道登录客户MI029信息表
	 * @param form
	 * @throws Exception
	 */
	public Mi029 webapi02916(AppApi50001Form form ,HashMap map) throws Exception;
	
	/**
	 * 更新客户个人账号信息表MI048
	 * @param form
	 * @throws Exception
	 */
	public void webapi02917(Mi029 mi029 ,AppApi50001Form form ,HashMap hasMap) throws Exception;
	
	/**
	 * 通过渠道用户查询客户信息
	 * @param form
	 * @throws Exception
	 */
	public Mi029 webapi02918(AppApi50001Form form ,HashMap map ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 通过客户ID查询指定类型账号 type为账号类型
	 * @param form
	 * @throws Exception
	 */
	public Mi048 webapi02919(Mi029 mi029 ,String type ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 通过客户ID查指定渠道渠道用户名
	 * @param form
	 * @throws Exception
	 */
	public Mi031 webapi02920(Mi029 mi029 ,AppApi50001Form form ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 渠道用户管理-消息通知渠道查询
	 * @param form
	 * @throws Exception
	 */
	public List<Mi031> webapi02921(Mi031 form ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 渠道用户管理-消息通知渠道设置
	 * @param form
	 * @throws Exception
	 */
	public void webapi02922(Mi031 form ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 渠道用户管理-查询渠道下应用信息
	 * @param form
	 * @throws Exception
	 */
	public List<Mi040> webapi02923(AppApi50001Form form ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 渠道用户管理-新增或更新短信渠道用户信息
	 * @param form
	 * @throws Exception
	 */
	public void webapi02924(Mi029 mi029 ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 渠道用户管理-渠道用户注册解绑删除
	 * @param form
	 * @throws Exception
	 */
	public int webapi02925(AppApi50001Form form ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 通过公积金账号查询个人信息
	 * @param form
	 * @throws Exception
	 */
	public Mi029 webapi02926(AppApi50001Form form ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	/**
	 * 渠道用户管理-客户信息新增MI029   for ningbo login
	 * @param form
	 * @throws Exception
	 */
	public void webapi02927(HashMap hasMap, AppApi50001Form form, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 根据mi029查找048个人公积金账号信息
	 * @param mi029 request response
	 * @throws Exception
	 */
	public Mi048 webapi02928(Mi029 mi029 ,HttpServletRequest request, HttpServletResponse response) throws Exception;
}
