package com.yondervision.mi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.form.AppApi80301Form;

@Controller
public class AppApi803Contorller {
	
	
	/**
	 * 审计APP待办工单查询列表接口
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi80301.{ext}")
	public String appApi80301(AppApi80301Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		String resMsg = "{\"recode\":\"000000\",\"msg\":\"成功\",\"data\":{\"total\": \"2\",\"worksheets\":[{\"title\": \"移动互联服务管理员账号\",\"id\": \"100291\",\"login\": \"admin1\",\"display_name\": \"王某某\",\"prev_done_name\": \"张某某\",\"status\": \"0\",\"date\": \"2015-05-19\"},{\"title\": \"移动后台服务器管理员账号\",\"id\": \"100292\",\"login\": \"admin2\",\"display_name\": \"王某某\",\"prev_done_name\": \"张某某\",\"status\": \"0\",\"date\": \"2015-05-20\"}]}}";
		response.getOutputStream().write(resMsg.getBytes("GB2312"));	
		response.getOutputStream().close();
		modelMap.clear();
		return "";
	}
	
	/**
	 * 审计APP待办工单详情接口
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi80302.{ext}")
	public String appapi80302(AppApi80301Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		String resMsg = "{\"recode\":\"000000\",\"msg\":\"成功\",\"data\":{\"action\":{\"allow\":\"true\",\"deny\":\"true\",\"turn\":\"true\",\"getback\":\"true\"},\"content\":{\"title\":\"移动互联服务管理员账号\",\"login\":\"admin1\",\"display_name\":\"A某某\",\"last_submit_date\":\"2015-06-01\",\"start_date\":\"2015-05-31\",\"is_sms\":\"true\",\"status\":\"0\",\"ws_start_date\":\"2015-05-31 10:11:21\",\"ws_end_date\":\"2015-06-31 10:11:21\",\"perm_content\":[{\"permission\":\"申请一\",\"users\":[\"user1\",\"user2\"],\"servers\":[\"server1\",\"server2\"],\"accounts\":[\"account1\",\"acount2\"],\"services\":[\"service1\",\"service2\"],\"service_type\":[\"service_type1\",\"service_type2\"],\"service_protos\":[\"service_proto1\",\"service_proto1\"],\"commands\":\"ls,pwd,cd\",},{\"permission\":\"申请二\",\"users\":[\"user1\",\"user2\"],\"servers\":[\"server1\",\"server2\"],\"accounts\":[\"account1\",\"acount2\"],\"services\":[\"service1\",\"service2\"],\"service_type\":[\"service_type1\",\"service_type2\"],\"service_protos\":[\"service_proto1\",\"service_proto1\"],\"commands\":\"ls,pwd,cd\",}],\"operator\":{\"date\":\"2015-06-01\",\"reason\":\"操作原因\",\"desc\":\"操作描述\",\"perms\":\"操作权限\"}},\"detail:{\"next\":[{\"login\":\"user1\",\"display_name\":\"姓名1\",\"duty\":\"岗位职责1\",\"depart\": \"销售部\",\"ws_start_time\": \"2015-05-31\",\"ws_end_time\": \"2015-05-31\"},{\"login\":\"user1\",\"display_name\":\"姓名1\",\"duty\":\"岗位职责1\",\"depart\":\"销售部\",\"ws_start_time\":\"2015-05-31\",\"ws_end_time\":\"2015-05-31\"}],\"processed\":[{\"login\":\"userA\",\"display_name\":\"姓名A\",\"duty\":\"岗位职责\",\"depart\":\"管理部\",\"ws_start_time\":\"2015-06-01\",\"ws_end_time\":\"2015-06-01\",\"comment\":\"同意\"},{\"login\":\"userA\",\"display_name\":\"姓名A\",\"duty\":\"岗位职责\",\"depart\":\"管理部\",\"ws_start_time\":\"2015-06-01\",\"ws_end_time\":\"2015-06-01\",\"comment\":\"同意\"}]}}}";
		response.getOutputStream().write(resMsg.getBytes("GB2312"));	
		response.getOutputStream().close();
		modelMap.clear();
		return "";
	}

	/**
	 * 审计APP己办工单查询列表接口
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi80303.{ext}")
	public String appapi80303(AppApi80301Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		String resMsg = "{\"recode\":\"000000\",\"msg\":\"成功\",\"data\":{\"action\":{\"allow\":\"true\",\"deny\":\"true\",\"turn\":\"true\",\"getback\":\"true\"},\"content\":{\"title\":\"移动互联服务管理员账号\",\"login\":\"admin1\",\"display_name\":\"A某某\",\"last_submit_date\":\"2015-06-01\",\"start_date\":\"2015-05-31\",\"is_sms\":\"true\",\"status\":\"0\",\"ws_start_date\":\"2015-05-31 10:11:21\",\"ws_end_date\":\"2015-06-31 10:11:21\",\"perm_content\":[{\"permission\":\"申请一\",\"users\":[\"user1\",\"user2\"],\"servers\":[\"server1\",\"server2\"],\"accounts\":[\"account1\",\"acount2\"],\"services\":[\"service1\",\"service2\"],\"service_type\":[\"service_type1\",\"service_type2\"],\"service_protos\":[\"service_proto1\",\"service_proto1\"],\"commands\":\"ls,pwd,cd\",},{\"permission\":\"申请二\",\"users\":[\"user1\",\"user2\"],\"servers\":[\"server1\",\"server2\"],\"accounts\":[\"account1\",\"acount2\"],\"services\":[\"service1\",\"service2\"],\"service_type\":[\"service_type1\",\"service_type2\"],\"service_protos\":[\"service_proto1\",\"service_proto1\"],\"commands\":\"ls,pwd,cd\",}],\"operator\":{\"date\":\"2015-06-01\",\"reason\":\"操作原因\",\"desc\":\"操作描述\",\"perms\":\"操作权限\"}},\"detail:{\"next\":[{\"login\":\"user1\",\"display_name\":\"姓名1\",\"duty\":\"岗位职责1\",\"depart\": \"销售部\",\"ws_start_time\": \"2015-05-31\",\"ws_end_time\": \"2015-05-31\"},{\"login\":\"user1\",\"display_name\":\"姓名1\",\"duty\":\"岗位职责1\",\"depart\":\"销售部\",\"ws_start_time\":\"2015-05-31\",\"ws_end_time\":\"2015-05-31\"}],\"processed\":[{\"login\":\"userA\",\"display_name\":\"姓名A\",\"duty\":\"岗位职责\",\"depart\":\"管理部\",\"ws_start_time\":\"2015-06-01\",\"ws_end_time\":\"2015-06-01\",\"comment\":\"同意\"},{\"login\":\"userA\",\"display_name\":\"姓名A\",\"duty\":\"岗位职责\",\"depart\":\"管理部\",\"ws_start_time\":\"2015-06-01\",\"ws_end_time\":\"2015-06-01\",\"comment\":\"同意\"}]}}}";
		response.getOutputStream().write(resMsg.getBytes("GB2312"));	
		response.getOutputStream().close();
		modelMap.clear();		
		return "";
	}
	
	/**
	 * 审计APP己办工单详情接口
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi80304.{ext}")
	public String appapi80304(AppApi80301Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		String resMsg = "{\"recode\": \"000000\",\"msg\": \"成功\",\"data\":{\"total\": 2,\"worksheets\": [{\"title\": \"移动互联服务后台管理员\",\"id\": \"19021\",\"login\":\"useraaa\",\"display_name\": \"用户AAA\",\"prev_done_name\": \"用户BBB\",\"status\": \"0\",\"date\": \"2015-05-19\",\"comment\": \"同意\" },{\"title\": \"公积金后台管理员\",\"id\": \"19021\",\"login\":\"userbbb\",\"display_name\": \"用户甲\",\"prev_done_name\": \"用户CCC\",\"status\": \"0\",\"date\": \"2015-05-19\",\"comment\": \"同意\" }]}}";
		
		response.getOutputStream().write(resMsg.getBytes("GB2312"));	
		response.getOutputStream().close();
		modelMap.clear();	
		return "";
	}	
	
	/**
	 * 审计APP批准
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi80305.{ext}")
	public String appapi80305(AppApi80301Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		String resMsg = "{\"recode\": \"000000\",\"msg\": \"成功\"}";
		
		response.getOutputStream().write(resMsg.getBytes("GB2312"));	
		response.getOutputStream().close();
		modelMap.clear();	
		return "";
	}	
	/**
	 * 审计APP驳回
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi80306.{ext}")
	public String appapi80306(AppApi80301Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		String resMsg = "{\"recode\": \"000000\",\"msg\": \"成功\"}";
		
		response.getOutputStream().write(resMsg.getBytes("GB2312"));	
		response.getOutputStream().close();
		modelMap.clear();	
		return "";
	}	
	/**
	 * 审计APP转批
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi80307.{ext}")
	public String appapi80307(AppApi80301Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		String resMsg = "{\"recode\": \"000000\",\"msg\": \"成功\"}";
		
		response.getOutputStream().write(resMsg.getBytes("GB2312"));	
		response.getOutputStream().close();
		modelMap.clear();	
		return "";
	}	
	/**
	 * 审计APP取回
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi80308.{ext}")
	public String appapi80308(AppApi80301Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		String resMsg = "{\"recode\": \"000000\",\"msg\": \"成功\"}";
		
		response.getOutputStream().write(resMsg.getBytes("GB2312"));	
		response.getOutputStream().close();
		modelMap.clear();	
		return "";
	}	
	
	
}
