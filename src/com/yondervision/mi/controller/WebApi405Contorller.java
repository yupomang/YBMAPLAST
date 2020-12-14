package com.yondervision.mi.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dto.Mi405;
import com.yondervision.mi.dto.Mi406;
import com.yondervision.mi.form.WebApiCommonForm;
import com.yondervision.mi.result.WebApi40502_queryResult;
import com.yondervision.mi.service.WebApi405Service;

/** 
* @ClassName: WebApi304Contorller 
* @Description: 渠道禁发配置、信息组装模板、个人短信发送、公共信息发送
* @author syw
* @date 2015-09-14  
* 
*/

@Controller
public class WebApi405Contorller {
	@Autowired
	WebApi405Service webApi405Service; 
	
	public WebApi405Service getWebApi405Service() {
		return webApi405Service;
	}

	public void setWebApi405Service(WebApi405Service webApi405Service) {
		this.webApi405Service = webApi405Service;
	}


	/**
	 * 禁发渠道查询
	 * @param centerid
	 * @param modelMap
	 * @return
	 */
//	@RequestMapping("/page30401Qry.json")
//	public String page30401Qry(String centerid, ModelMap modelMap){		
//		modelMap.put("rows", WebApi405Service.getMessRuleAllList());
//		return "";
//	}
//	
//	/**
//	 * 禁发渠道删除
//	 * @param request
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("/page30401Del.json")
//	public String page30401Del(HttpServletRequest request,ModelMap modelMap){	
//		List<String> list=new ArrayList<String>();
//		String[] ary=request.getParameter("ruleId").split(",");
//		for(int i=0;i<ary.length;i++){
//			list.add(ary[i]);
//		} 
//		webApi304ServiceImpl.delRule(list);
//		modelMap.put("recode", "000000");
//		modelMap.put("msg", "成功");	
//		modelMap.put("rows", webApi304ServiceImpl.getMessRuleAllList());
//		return "";
//	}
//	
//	/**
//	 * 禁发渠道添加
//	 * @param messageRule
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("/page30401Add.json")
//	public String page30401JsonAdd(MessageRule messageRule ,ModelMap modelMap){
//		webApi304ServiceImpl.addRule(messageRule);
//		modelMap.put("recode", "000000");
//		modelMap.put("msg", "成功");		 
//		return 	"";
//	}
//	
//	/**
//	 * 禁发渠道修改
//	 * @param messageRule
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("/page30401Upd.json")
//	public String page30401JsonUpd(MessageRule messageRule ,ModelMap modelMap){ 
//		webApi304ServiceImpl.updateRule(messageRule);
//		modelMap.put("recode", "000000");
//		modelMap.put("msg", "成功");		 
//		return 	"";
//	}
//	
//	
//	/**
//	 * 消息模板查询
//	 * @param centerid
//	 * @param modelMap
//	 * @return
//	 */
	@RequestMapping("/page30402Qry.json")
	public String page30402Qry(String page,String rows,ModelMap modelMap){
	
		WebApi40502_queryResult query_Result = webApi405Service.getMi405AllList(page,rows);
		modelMap.put("total", query_Result.getTotal());
		modelMap.put("pageSize", query_Result.getPageSize());
		modelMap.put("pageNumber", query_Result.getPageNumber());
		modelMap.put("rows", query_Result.getList122());
		return "";
	}
//	
//	/**
//	 * 消息模板详细查询
//	 * @param centerid
//	 * @param modelMap
//	 * @return
//	 */
	@RequestMapping("/page3040201Qry.json")
	public String page3040201Qry(String templateId, ModelMap modelMap){		
		modelMap.put("rows", webApi405Service.getMi406AllList(templateId));
		return "";
	}
//	
//	/**
//	 * 通过模板id查询模板要素
//	 * @param templateDetailId
//	 * @param modelMap
//	 * @return
//	 */
	@RequestMapping("/page3040202.json")
	public String page3040202Load(String templateDetailId, ModelMap modelMap) {
		Mi406 result = webApi405Service.getMi406ById(templateDetailId);
		modelMap.put("result", result);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		return "";
	}
//	
//	/**
//	 * 弹出编辑页面
//	 * @param form
//	 * @param modelMap
//	 * @return
//	 * @throws Exception
//	 */
	@RequestMapping("/page3040203.html")
	public String page3040203(WebApiCommonForm form, ModelMap modelMap) throws Exception {
		return "page405/page3040203";
	}
//
//	/**
//	 * 修改模板详细要素
//	 * @param form
//	 * @param modelMap
//	 * @return
//	 */
	@RequestMapping("/page3040204.json")
	public String page3040204Upd(Mi406 form, ModelMap modelMap) {
		webApi405Service.updateMi406(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		return "";
	}
//	/**
//	 * 消息模板删除
//	 * @param request
//	 * @param modelMap
//	 * @return
//	 */
	@RequestMapping("/page30402Del.json")
	public String page30402Del(HttpServletRequest request,ModelMap modelMap){	
		List<String> list=new ArrayList<String>();
		String[] ary=request.getParameter("itemId").split(",");
		for(int i=0;i<ary.length;i++){
			list.add(ary[i]);
		} 
		webApi405Service.delTemplate(list);
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		modelMap.put("rows", webApi405Service.getMi405AllList("1","15"));
		return "";
	}
//	
//	/**
//	 * 添加信息模板
//	 * @param messageTemplate
//	 * @param modelMap
//	 * @return
//	 */
	@RequestMapping("/page30402Add.json")
	public String page30402JsonAdd(Mi405 mi405 ,ModelMap modelMap){
		String templateId = webApi405Service.addMessageTemplate(mi405);
		modelMap.put("templateId", templateId);
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");		 
		return 	"";
	}
//	
//	/**
//	 * 修改信息模板
//	 * @param messageTemplate
//	 * @param modelMap
//	 * @return
//	 */
	@RequestMapping("/page30402Upd.json")
	public String page30402JsonUpd(Mi405 mi405 ,ModelMap modelMap){ 
		webApi405Service.updateTemplate(mi405);
		modelMap.put("templateId", mi405.getTemplateId());
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");		 
		return 	"";
	}
//	
//	/**
//	 * 生成要素
//	 * @param messageTemplate
//	 * @param modelMap
//	 * @return
//	 */
	@RequestMapping("/page30402Build.json")
	public String page30402JsonBuild(Mi405 mi405 ,ModelMap modelMap){ 
		webApi405Service.buildMi406(mi405);
		modelMap.put("templateId", mi405.getTemplateId());
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");		 
		return 	"";
	}
//	
//	/**
//	 * 个人发送短信
//	 * @param request
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("/page30403Add.json")
//	public String page30403JsonAdd(HttpServletRequest request,ModelMap modelMap)
//	{
//		HashMap<String,String> hm = new HashMap<String, String>();
//		String sendUser = request.getParameter("sendUser");
//		String sendType = request.getParameter("sendType1");
//		String sendTime = request.getParameter("sendTime");
//		String sendContent = request.getParameter("sendContent");
//		String phoneName = request.getParameter("phoneName");
//		String phoneNumber = request.getParameter("phoneNumber");
//		hm.put("sendType", sendType);
//		hm.put("sendTime", sendTime);
//		hm.put("sendContent", sendContent);
//		hm.put("phoneName", phoneName);
//		hm.put("phoneNumber", phoneNumber);
//		//如果sendUser 为1 发送给所有用户
//		if(sendUser.equals("1"))
//		{
//			webApi304ServiceImpl.addMessageProcessForAllUser(hm);
//		}else
//		{
//			String[] phoneNumbers = phoneNumber.split("@");
//			for(int i=0;i<phoneNumbers.length;i++)
//			{
//				if(phoneNumbers[i].length() != 11)
//				{
//					throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"手机号码不正确，请重新填写！");
//				}
//			}
//			webApi304ServiceImpl.addMessageProcess(hm);
//		}
//		return "";
//	}
//	
//	/**
//	 * 常用号码列表
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("/page30403Qry.json")
//	public String page30403JsonQry(ModelMap modelMap)
//	{
//		modelMap.put("rows", webApi304ServiceImpl.getCommonPhoneList());
//		return "";
//	}
//	
//	/**
//	 * 查询公共消息
//	 * @param form
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("/page3040402Qry.json")
//	public String page3040402Qry(MessagePublicTemp form, ModelMap modelMap) {
//		Logger log = LoggerUtil.getLogger();
//		try {
//			// 业务处理
//			List<MessagePublicTemp> result = webApi304ServiceImpl.getMessagePublicTempList(form);
//			List<MessagePublicTemp> list = new ArrayList<MessagePublicTemp>();
//			for(MessagePublicTemp mt :result)
//			{
//				if(!CommonUtil.isEmpty(mt.getFreeuse1()))
//				{
//					if(!mt.getFreeuse1().equals("04"))
//					{
//						list.add(mt);
//					}
//				}
//				if(CommonUtil.isEmpty(mt.getFreeuse1()))
//				{
//					list.add(mt);
//				}
//			}
//			modelMap.put("rows", list);
//			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		}catch (Exception e) {
//			TransRuntimeErrorException tre = new TransRuntimeErrorException(
//					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
//			log.error(tre.getMessage());
//			log.error(e.getMessage(), e);
//			throw tre;
//		}
//		return "";
//	}
//	
//	/**
//	 * 添加公共消息-图文
//	 * @param form
//	 * @param file
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("/page3040402Add.html")
//	public void page3040402Add(MessagePublicTemp form,@RequestParam MultipartFile file,HttpServletResponse response) {
//		if(file.getSize()>(1024*1024)){
//			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_TOO_LONG
//					.getValue(), "图片","大小超过1M");
//		}
//		if(file.getOriginalFilename().lastIndexOf("jpg")<0){
//			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
//					.getValue(), "JPG格式图片");
//		}
//		webApi304ServiceImpl.addOrUpdMessagePublicTemp(form,file);
//	}
//	
//	/**
//	 * 添加公共消息-文字
//	 * @param form
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("/page3040403Add.html")
//	public void page3040403Add(MessagePublicTemp form,HttpServletResponse response) {
//		webApi304ServiceImpl.addOrUpdMessagePublicTemp(form);
//	}
//	
//	/**
//	 * 修改公共消息查询
//	 * @param tempId
//	 * @param modelMap
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/page3040402.json")
//	public String page3040402(String tempId, ModelMap modelMap)
//			throws Exception {
//		MessagePublicTemp result = webApi304ServiceImpl.loadMessagePublicTemp(tempId);
//		modelMap.put("result", result);	
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		return "";
//	}
//	
//	/**
//	 * 添加修改公共消息弹出框
//	 * @param tempId
//	 * @param modelMap
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/page3040401.html")
//	public String page3040401(String tempId, ModelMap modelMap)
//			throws Exception {
//		MessagePublicTemp result = webApi304ServiceImpl.loadMessagePublicTemp(tempId);
//		modelMap.put("result", result);	
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		return "page304/page3040401";
//	}
//	
//	/**
//	 * 删除公共消息
//	 * @param request
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("/page3040402Del.json")
//	public String page3040402Del(HttpServletRequest request,ModelMap modelMap)
//	{
//		List<String> list=new ArrayList<String>();
//		String[] ary=request.getParameterValues("listTempId");
//		for(int i=0;i<ary.length;i++){
//			list.add(ary[i]);
//		} 
//		webApi304ServiceImpl.delPublicTemp(list);
//		modelMap.put("recode", "000000");
//		modelMap.put("msg", "成功");	
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		return "";
//	}
//	
//	/**
//	 * 审批公共消息
//	 * @param request
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("/page3040402check.json")
//	public String page3040402check(HttpServletRequest request,ModelMap modelMap)
//	{
//		String tempId=request.getParameter("tempId");
//		webApi304ServiceImpl.checkPublicTemp(tempId);
//		modelMap.put("recode", "000000");
//		modelMap.put("msg", "成功");	
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		return "";
//	}
//	
//	/**
//	 * 预览公共消息
//	 * @param tempId
//	 * @param modelMap
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/page3040402Detail.html")
//	public String page3040402Detail(String tempId,ModelMap modelMap) throws Exception
//	{
//		MessagePublicTemp result = webApi304ServiceImpl.loadMessagePublicTemp(tempId);
//		modelMap.put("tempTitle", result.getTempTitle());
//		modelMap.put("tempAbstact", result.getTempAbstact());
//		modelMap.put("tempBigContent", result.getTempBigContent());
//		if(!CommonUtil.isEmpty(result.getTempTitleImg()))
//		{
//			modelMap.put("tempTitleImg", CommonUtil.getDownloadFileUrl(
//					"push_msg_img", UserContext.getInstance().getCenterid() + File.separator+result.getTempTitleImg(),true));
//		}
//		modelMap.put("tempType", result.getTempType());
//		return "page304/page3040402";
//	}
//	
//	/**
//	 * 推送公共消息
//	 * @param request
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("page3040402send.json")
//	public String page3040402send(HttpServletRequest request,ModelMap modelMap)
//	{
//		String tempId = request.getParameter("tempId");
//		String[] pushchannel = request.getParameterValues("pusMessageType");
//		webApi304ServiceImpl.sendPublicTemp(tempId,pushchannel);
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		return "";
//	}
//	
//	/**
//	 * 批量推送
//	 * @param request
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("page3040402sendlist.json")
//	public String page3040402sendlist(HttpServletRequest request,ModelMap modelMap)
//	{
//		String[] tempId = request.getParameterValues("listTempId");
//		String[] pushchannels = request.getParameterValues("pusMessageType1");
//		String pushchannel = "";
//		for(int i=0;i<pushchannels.length;i++)
//		{
//			pushchannel += pushchannels[i]+",";
//		}
//		pushchannel = pushchannel.substring(0,pushchannel.length()-1);
//		webApi304ServiceImpl.sendPublicTempList(tempId,pushchannel);
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		return "";
//	}
//	
//	/**
//	 * 查询公共消息-微博
//	 * @param form
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("/page3040602Qry.json")
//	public String page3040602Qry(MessagePublicTemp form, ModelMap modelMap) {
//		Logger log = LoggerUtil.getLogger();
//		try {
//			// 业务处理
//			List<MessagePublicTemp> list = new ArrayList<MessagePublicTemp>();
//			List<MessagePublicTemp> result = webApi304ServiceImpl.getMessagePublicTempList(form);
//			for(MessagePublicTemp mt :result)
//			{
//				if(!CommonUtil.isEmpty(mt.getFreeuse1()))
//				{
//					if(mt.getFreeuse1().equals("04"))
//					{
//						list.add(mt);
//					}
//				}
//			}
//			modelMap.put("rows", list);
//			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		}catch (Exception e) {
//			TransRuntimeErrorException tre = new TransRuntimeErrorException(
//					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
//			log.error(tre.getMessage());
//			log.error(e.getMessage(), e);
//			throw tre;
//		}
//		return "";
//	}
//	
//	/**
//	 * 添加公共消息-图文-微博
//	 * @param form
//	 * @param file
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("/page3040602Add.html")
//	public void page3040602Add(MessagePublicTemp form,@RequestParam MultipartFile file,HttpServletResponse response) {
//		if(file.getSize()>(1024*1024)){
//			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_TOO_LONG
//					.getValue(), "图片","大小超过1M");
//		}
//		if(file.getOriginalFilename().lastIndexOf("jpg")<0){
//			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
//					.getValue(), "JPG格式图片");
//		}
//		webApi304ServiceImpl.addOrUpdMessagePublicTemp(form,file);
//	}
//	
//	/**
//	 * 添加公共消息-文字-微博
//	 * @param form
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("/page3040603Add.html")
//	public void page3040603Add(MessagePublicTemp form,HttpServletResponse response) {
//		webApi304ServiceImpl.addOrUpdMessagePublicTemp(form);
//	}
//	
//	/**
//	 * 修改公共消息查询-微博
//	 * @param tempId
//	 * @param modelMap
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/page3040602.json")
//	public String page3040602(String tempId, ModelMap modelMap)
//			throws Exception {
//		MessagePublicTemp result = webApi304ServiceImpl.loadMessagePublicTemp(tempId);
//		modelMap.put("result", result);	
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		return "";
//	}
//	
//	/**
//	 * 添加修改公共消息弹出框-微博
//	 * @param tempId
//	 * @param modelMap
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/page3040601.html")
//	public String page3040601(String tempId, ModelMap modelMap)
//			throws Exception {
//		MessagePublicTemp result = webApi304ServiceImpl.loadMessagePublicTemp(tempId);
//		modelMap.put("result", result);	
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		return "page304/page3040601";
//	}
//	
//	/**
//	 * 删除公共消息-微博
//	 * @param request
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("/page3040602Del.json")
//	public String page3040602Del(HttpServletRequest request,ModelMap modelMap)
//	{
//		List<String> list=new ArrayList<String>();
//		String[] ary=request.getParameterValues("listTempId");
//		for(int i=0;i<ary.length;i++){
//			list.add(ary[i]);
//		} 
//		webApi304ServiceImpl.delPublicTemp(list);
//		modelMap.put("recode", "000000");
//		modelMap.put("msg", "成功");	
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		return "";
//	}
//	
//	/**
//	 * 审批公共消息-微博
//	 * @param request
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("/page3040602check.json")
//	public String page3040602check(HttpServletRequest request,ModelMap modelMap)
//	{
//		String tempId=request.getParameter("tempId");
//		webApi304ServiceImpl.checkPublicTemp(tempId);
//		modelMap.put("recode", "000000");
//		modelMap.put("msg", "成功");	
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		return "";
//	}
//	
//	/**
//	 * 预览公共消息-微博
//	 * @param tempId
//	 * @param modelMap
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/page3040602Detail.html")
//	public String page3040602Detail(String tempId,ModelMap modelMap) throws Exception
//	{
//		MessagePublicTemp result = webApi304ServiceImpl.loadMessagePublicTemp(tempId);
//		modelMap.put("tempTitle", result.getTempTitle());
//		modelMap.put("tempAbstact", result.getTempAbstact());
//		modelMap.put("tempBigContent", result.getTempBigContent());
//		if(!CommonUtil.isEmpty(result.getTempTitleImg()))
//		{
//			modelMap.put("tempTitleImg", CommonUtil.getDownloadFileUrl(
//					"push_msg_img", UserContext.getInstance().getCenterid() + File.separator+result.getTempTitleImg(),true));
//		}
//		modelMap.put("tempType", result.getTempType());
//		return "page304/page3040602";
//	}
//	
//	/**
//	 * 推送公共消息-微博
//	 * @param request
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("page3040602send.json")
//	public String page3040602send(HttpServletRequest request,ModelMap modelMap)
//	{
//		String tempId = request.getParameter("tempId");
//		String[] pushchannel = request.getParameterValues("pusMessageType");
//		webApi304ServiceImpl.sendPublicTemp(tempId,pushchannel);
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		return "";
//	}
//	
//	/**
//	 * 批量推送-微博
//	 * @param request
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("page3040602sendlist.json")
//	public String page3040602sendlist(HttpServletRequest request,ModelMap modelMap)
//	{
//		String[] tempId = request.getParameterValues("listTempId");
//		String pushchannel = request.getParameter("pusMessageType1");
//		webApi304ServiceImpl.sendPublicTempList(tempId,pushchannel);
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		return "";
//	}
//	
//	/**
//	 * 查询已发送消息
//	 * @param webApi30405Form
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("/page30405.json")
//	public String page30405(WebApi30405Form webApi30405Form,ModelMap modelMap)
//	{
//		//查询message_process
//		webApi30405Form.setRows(15);
//		if(CommonUtil.isEmpty(webApi30405Form.getStartdate()))
//		{
//			webApi30405Form.setStartdate(CommonUtil.getDate());
//		}
//		if(CommonUtil.isEmpty(webApi30405Form.getEnddate()))
//		{
//			webApi30405Form.setEnddate(CommonUtil.getDate());
//		}
//		WebApi30405_queryResult queryResult = webApi304ServiceImpl.getMessageProcessList(webApi30405Form);
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		modelMap.put("total", queryResult.getTotal());
//		modelMap.put("totalPage", queryResult.getTotalPage());
//		modelMap.put("pageSize", queryResult.getPageSize());
//		modelMap.put("pageNumber", queryResult.getPageNumber());
//		List<MessageProcess> l = new ArrayList<MessageProcess>();
//		for(MessageProcess m:queryResult.getList122())
//		{
//			if(!CommonUtil.isEmpty(m.getFreeuse4()))
//			{
//				Mi002DAO dao= (Mi002DAO)SpringContextUtil.getBean("mi002Dao");
//				Mi002Example m2e=new Mi002Example();
//				m2e.createCriteria().andLoginidEqualTo(m.getFreeuse4());		
//				@SuppressWarnings("unchecked")
//				List<Mi002> list=dao.selectByExample(m2e);
//				if(list.size()>0)
//				{
//					Mi002 bean=list.get(0);
//					m.setFreeuse4(bean.getOpername());
//				}
//			}
//			l.add(m);
//		}
//		modelMap.put("rows", l);
//		return "page304/page30405";
//	}
//	
//	/**
//	 * 统计已发送消息
//	 * @param webApi30407Form
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("/page30407.json")
//	public String page30407(WebApi30407Form webApi30407Form,ModelMap modelMap)
//	{
//		//所有渠道
//		HashMap<String,String> hm = new HashMap<String, String>();
//		hm.put("01", "APP");
//		hm.put("02", "微信");
//		hm.put("03", "短信");
//		hm.put("04", "微博");
//		hm.put("99", "网站");
//		if(CommonUtil.isEmpty(webApi30407Form.getStartdate()))
//		{
//			webApi30407Form.setStartdate(CommonUtil.getDate());
//		}
//		if(CommonUtil.isEmpty(webApi30407Form.getEnddate()))
//		{
//			webApi30407Form.setEnddate(CommonUtil.getDate());
//		}
//		List<WebApi30407_queryResult> resultList = new ArrayList<WebApi30407_queryResult>();
//		WebApi30407_queryResult result ;
//		//统计每个渠道的数量
//		for(Entry<String,String> e : hm.entrySet())
//		{
//			webApi30407Form.setMessageChannel(e.getKey());
//			result = webApi304ServiceImpl.countChannel(webApi30407Form);
//			resultList.add(result);
//		}
//		modelMap.put("rows", resultList);
//		return "page304/page30407";
//	}
//	
//	/**
//	 * 查询已发送消息详细
//	 * @param webApi30408Form
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("/page30408.json")
//	public String page30408(WebApi30408Form webApi30408Form,ModelMap modelMap)
//	{
//		//查询message_send
//		webApi30408Form.setRows(15);
//		if(CommonUtil.isEmpty(webApi30408Form.getPage()))webApi30408Form.setPage(0);
//		if(CommonUtil.isEmpty(webApi30408Form.getStartdate()))
//		{
//			webApi30408Form.setStartdate(CommonUtil.getDate());
//		}
//		if(CommonUtil.isEmpty(webApi30408Form.getEnddate()))
//		{
//			webApi30408Form.setEnddate(CommonUtil.getDate());
//		}
//		WebApi3040501_queryResult queryResult = webApi304ServiceImpl.getMessageSendDetailList(webApi30408Form);
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		modelMap.put("total", queryResult.getTotal());
//		modelMap.put("totalPage", queryResult.getTotalPage());
//		modelMap.put("pageSize", queryResult.getPageSize());
//		modelMap.put("pageNumber", queryResult.getPageNumber());
//		modelMap.put("rows", queryResult.getList122());
//		return "page304/page30408";
//	}
//	
//	/**
//	 * 消息模板统计
//	 * @param centerid
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("/page30409.json")
//	public String page30409(WebApi30409Form webApi30409Form,String page,String rows,ModelMap modelMap){		
//		webApi30409Form.setRows(15);
//		if(CommonUtil.isEmpty(webApi30409Form.getPage()))webApi30409Form.setPage(0);
//		if(CommonUtil.isEmpty(webApi30409Form.getStartdate()))
//		{
//			webApi30409Form.setStartdate(CommonUtil.getDate());
//		}
//		if(CommonUtil.isEmpty(webApi30409Form.getEnddate()))
//		{
//			webApi30409Form.setEnddate(CommonUtil.getDate());
//		}
//		List<MessageTemplate> lt = webApi304ServiceImpl.getMessTemplateAllList("0",Integer.MAX_VALUE+"").getList122();
//		List<WebApi30409_queryResult> result = new ArrayList<WebApi30409_queryResult>();
//		WebApi30409_queryResult queryResult ;
//		for(MessageTemplate mt:lt)
//		{
//			queryResult = webApi304ServiceImpl.getTempalteCountList(webApi30409Form,mt.getTemplateCode(),mt.getTemplateName());
//			result.add(queryResult);
//		}
//		modelMap.put("rows", result);
//		return "page304/page30409";
//	}
//	
//	/**
//	 * 查询已发送消息详细
//	 * @param processId
//	 * @param webApi3040501Form
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("/page3040501.json")
//	public String page3040501(String processId,WebApi3040501Form webApi3040501Form,ModelMap modelMap)
//	{
//		//查询message_send
//		webApi3040501Form.setRows(15);
//		if(CommonUtil.isEmpty(webApi3040501Form.getPage()))webApi3040501Form.setPage(0);
//		WebApi3040501_queryResult queryResult = webApi304ServiceImpl.getMessageSendList(processId,webApi3040501Form);
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		modelMap.put("total", queryResult.getTotal());
//		modelMap.put("totalPage", queryResult.getTotalPage());
//		modelMap.put("pageSize", queryResult.getPageSize());
//		modelMap.put("pageNumber", queryResult.getPageNumber());
//		modelMap.put("rows", queryResult.getList122());
//		return "page304/page3040501";
//	}
//	
//	/**
//	 * 跳转
//	 * @param processId
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("/page3040501.html")
//	public String page3040501(String processId,ModelMap modelMap)
//	{
//		modelMap.put("processId", processId);
//		return "page304/page3040501";
//	}
//	
//	/**
//	 * 查询已发送详细消息明细
//	 * @param sendId
//	 * @param modelMap
//	 * @return
//	 * @throws Exception 
//	 */
//	@RequestMapping("/page3040502.html")
//	public String page3040502(String sendId,ModelMap modelMap) throws Exception
//	{
//		MessageSend ms = webApi304ServiceImpl.getMessageSendById(sendId);
//		modelMap.put("sendTitle", ms.getSendTitle());
//		modelMap.put("sendAbstact", ms.getSendAbstact());
//		modelMap.put("sendContent", ms.getSendContent());
//		modelMap.put("phoneNumber", ms.getPhoneNumber());
//		String sendTitleImgUrl = webApi304ServiceImpl.getMessageSendDetailById(sendId);
//		if(!CommonUtil.isEmpty(sendTitleImgUrl))
//		{
//			modelMap.put("sendTitleImg", CommonUtil.getDownloadFileUrl(
//					"push_msg_img", UserContext.getInstance().getCenterid() + File.separator+sendTitleImgUrl,true));
//		}
//		if(CommonUtil.isEmpty(ms.getSendContent()))modelMap.put("sendContent", ms.getSendMessage());
//		modelMap.put("sendId", sendId);
//		return "page304/page3040502";
//	}
//	
//	/**
//	 * 跳转知识库
//	 * @param processId
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping("/page3040503.html")
//	public ModelAndView page3040503(String processId,ModelMap modelMap)
//	{
//		//知识库url
//		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "knowledge_url");
//		//根据中心代码获取custid
//		String custid = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "custid_"+UserContext.getInstance().getCenterid());
//		ModelAndView view = new ModelAndView();
//	    view.setViewName("redirect:"+url+custid);
//	    return view;
//	}
//	
//	public WebApi304Service getWebApi304ServiceImpl() {
//		return webApi304ServiceImpl;
//	}
//
//	public void setWebApi304ServiceImpl(WebApi304Service webApi304ServiceImpl) {
//		this.webApi304ServiceImpl = webApi304ServiceImpl;
//	}
//	
}
