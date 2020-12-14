/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.service
 * 文件名：     WebApi302Service.java
 * 创建日期：2013-10-8
 */
package com.yondervision.mi.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.dto.CMi401;
import com.yondervision.mi.dto.CMi404;
import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.dto.Mi040;
import com.yondervision.mi.dto.Mi401;
import com.yondervision.mi.dto.Mi403;
import com.yondervision.mi.dto.Mi404;
import com.yondervision.mi.dto.Mi415;
import com.yondervision.mi.form.AppApi50004Form;
import com.yondervision.mi.form.AppApi90421Form;
import com.yondervision.mi.form.WebApi30201_deleteForm;
import com.yondervision.mi.form.WebApi30202_quertForm;
import com.yondervision.mi.form.WebApi30203Form;
import com.yondervision.mi.form.WebApi30204Form;
import com.yondervision.mi.form.WebApiCommonForm;
import com.yondervision.mi.result.WebApi30202_queryResult;
import com.yondervision.mi.result.WebApi30203_queryResult;
import com.yondervision.mi.result.WebApi30204_queryResult;
import com.yondervision.mi.result.WebApi30205_queryResult;
import com.yondervision.mi.result.WebApi30206_queryResult;
import com.yondervision.mi.result.WebApi30207_queryResult;

/**
 * 短信息WEB后台业务处理类
 * 
 * @author LinXiaolong
 * 
 */
public interface WebApi302Service {
	

	/**
	 * 查询公共短信息
	 * 
	 * @param form
	 *            form中包含centerId
	 * @return 公共短信息list
	 */
	List<Mi401> webapi30201_query(CMi401 form) throws Exception;
	
	/**
	 * 根据短信息ID查询公共短信息
	 * 
	 * @param commsgid
	 * 
	 * @return 公共短信息list
	 */
	Mi401 webapi30201_queryById(String commsgid) throws Exception;

	/**
	 * 删除公共短信息
	 * 
	 * @param form
	 *            form中包含listCommsgid
	 * @throws Exception
	 */
	void webapi30201_delete(WebApi30201_deleteForm form) throws Exception;
	
	/**
	 * 推送公共短信息
	 * @param form form中包含commsgid、centerId
	 * @return 推送的终端数
	 * @throws Exception
	 */
//	int webapi30201_send(CMi401 form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * 修改公共短信息
	 * 
	 * @param form
	 *            form中包含commsgid、centerid（可空）、title（可空）、detail（可空）、param1（可空）、param2
	 *            （可空）
	 * @throws Exception
	 */
	void webapi30201_edit(CMi401 form) throws Exception;
	
	/**
	 * 审批公共短信息
	 * 
	 * @param form
	 * @throws Exception
	 */
	void webapi30201_auth(CMi401 form) throws Exception;
	
	/**
	 * 审批报盘短信息
	 * 
	 * @param form
	 * @throws Exception
	 */
//	void webapi30202_auth(WebApi30201_deleteForm form) throws Exception;
	
	/**
	 * 查询报盘短信息批次
	 * @param form form中包含centerId
	 * @return 报盘短信息批次数据
	 * @throws Exception
	 */
	List<Mi401> webapi30202_query(WebApiCommonForm form) throws Exception;

	/**
	 * 查询报盘短信息明细（分页）
	 * 
	 * @param form
	 *            form中包含centerId、seqid、page、rows
	 * @return 报盘短信息分页数据
	 * @throws Exception
	 */
	WebApi30202_queryResult webapi3020201_query(WebApi30202_quertForm form)
			throws Exception;

	/**
	 * 导入报盘短信息
	 * @param form form中包含centerId
	 * @param importFile 上传的excel文件
	 * @return 推送短信息批次号
	 * @throws Exception
	 */
	public CMi401 webapi30202_import(CMi401 form, MultipartFile importFile) throws Exception;

	
	
	/**
	 * 删除报盘短信息
	 * @param form form中包含centerId、listSerno
	 * @throws Exception
	 */
	void webapi30202_dlete(CMi401 form) throws Exception;

	/**
	 * 公共短信息图片上传
	 * @param form form中包含certerId
	 * @param file 上传的文件
	 * @return 上传后的文件名
	 * @throws Exception
	 */
	String uploadFile(WebApiCommonForm form, MultipartFile file) throws Exception;

	/**
	 * 查询已推送公共短信息
	 * @param form form中包含centerId、page、rows
	 * @return 已推送的公共信息结果集
	 */
	WebApi30203_queryResult webapi3020203(WebApi30203Form form);

	/**
	 * 查询已推送报盘短信息批次
	 * @param form form中包含centerId、page、rows
	 * @return 已推送报盘短信息批次结果集
	 */
	WebApi30204_queryResult webapi30204(WebApi30204Form form);
	
	/**
	 * 按条件查询标题、内容、时间
	 * @param form form中包含centerId、page、rows
	 * @return 已推送报盘短信息批次结果集
	 */
	WebApi30204_queryResult webapi30205(WebApi30204Form form);
	
	/**
	 * 查询已推送应用短信息
	 * @param form form中包含centerId、page、rows
	 * @return 已推送的公共信息结果集
	 */
	WebApi30202_queryResult webapi3020206(WebApi30203Form form);
	
	/**
	 * 查询已推送报盘短信息明细（分页）
	 * @param form form中包含centerId、seqid、page、rows
	 * @return 已推送报盘短信息明细结果集
	 */
	WebApi30206_queryResult webapi3020401(WebApi30202_quertForm form);
	
	/**
	 * 查询已推送报盘短信息明细（分页）
	 * @param form form中包含centerId、seqid、page、rows
	 * @return 已推送报盘短信息明细结果集
	 */
	WebApi30205_queryResult webapi3020402(WebApi30202_quertForm form);

	/**
	 * 添加公共短信息文本
	 * 
	 * @param form
	 *            form中包含centerId、title、detail、param2（可空）
	 * @return 公共短信息ID
	 * @throws Exception
	 */
	public String webapi30201_addText(CMi401 form) throws Exception;

	
	
	/**
	 * 添加公共短信息图片
	 * 
	 * @param form
	 *            form中包含centerId、title、detail、param2（不可空）
	 * @return 公共短信息ID
	 * @throws Exception
	 */
	public String webapi30201_addImage(CMi401 form) throws Exception;
	
	/**
	 * 添加公共短信息——音频文件
	 * 
	 * @param form
	 *            form中包含centerId、title、detail、param2（不可空）
	 * @return 公共短信息ID
	 * @throws Exception
	 */
	public String webapi30201_addAudio(CMi401 form) throws Exception;
	
	/**
	 * 添加公共短信息——视频文件
	 * 
	 * @param form
	 *            form中包含centerId、title、detail、param2（不可空）
	 * @return 公共短信息ID
	 * @throws Exception
	 */
	public String webapi30201_addVideo(CMi401 form) throws Exception;
	
	/**
	 * 添加公共短信息图片
	 * 
	 * @param form
	 *            form中包含centerId、title、detail、param2（不可空）
	 * @return 公共短信息ID
	 * @throws Exception
	 */
	String webapi30201_addTextImageMai(CMi401 form) throws Exception;
	
	/**
	 * 添加公共短信息图片
	 * 
	 * @param form
	 *            form中包含centerId、title、detail、param2（不可空）
	 * @return 公共短信息ID
	 * @throws Exception
	 */
	String webapi30201_addTextImage(CMi404 form) throws Exception;
	
	
	public List<Mi404> webapi30201_queryTextImage(CMi401 form) throws Exception;
	
	int webapi30201_updateTextImage(CMi404 form) throws Exception;
	
	void webapi30201_deletems(CMi404 form) throws Exception;
	
	void webapi30201_editText(CMi401 form) throws Exception;
	
	void webapi30201_editImage(CMi401 form) throws Exception;
	
	void webapi30201_orderbynum(JSONArray arr) throws Exception;
	
	/**
	 * 推送公共短信息
	 * @param form form中包含commsgid、centerId
	 * @return 推送的终端数
	 * @throws Exception
	 */
//	int webapi30201_sendText(CMi401 form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception;
//	int webapi30201_sendImage(CMi401 form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception;
//	int webapi30201_sendTextImage(CMi401 form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public int webapi30201_send(CMi401 form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception;
	/**
	 * 推送报盘短信息
	 * @param form form中包含centerId、seqid
	 * @return 成功推送的终端设备数
	 * @throws Exception
	 */
	public void webapi30202_insertWaitSend(CMi401 form,ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public String webapi30210_CSPSend_Batch_Select(CMi401 form) throws TransRuntimeErrorException;
	
	
	/**
	 * 入推送短信息表
	 * @return
	 * @throws Exception
	 */
	public int insertGroupWaitMessage(Object obj) throws Exception;
	
	/**
	 * 入推送短信息表
	 * @return
	 * @throws Exception
	 */
	public Mi401 insertCustomizationWaitMessage(CMi401 mi401) throws Exception;
	/**
	 * 推送待推短信息表
	 * @return
	 * @throws Exception
	 */
	public int sendGroupWaitMessage(Object obj) throws Exception;
	
	/**
	 * 入推定时推送信息表
	 * @return
	 * @throws Exception
	 */
//	public void insertTimingMessage(CMi401 obj) throws Exception;
	
	/**
	 * 入模板原始信息表
	 * @return
	 * @throws Exception
	 */
	public String insertTemplateParam(CMi401 obj , Mi029 mi029 ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 插入己推送信息表MI403
	 * @return
	 * @throws Exception
	 */
	public void insertSendTable(CMi401 mi401 ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * APP推送
	 * @return
	 * @throws Exception
	 */
	public int sendApp(Mi403 mi403 , HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 微信推送
	 * @return
	 * @throws Exception
	 */
	public int sendWeixin(Mi403 mi403 , HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 手机短信SMS推送
	 * @return
	 * @throws Exception
	 */
	public int sendSms(Mi403 mi403 , HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 入定时表
	 * @return
	 * @throws Exception
	 */
	public void insertTimingTable(CMi401 mi401) throws Exception;
	
	/**
	 * 状态修改
	 * @return
	 * @throws Exception
	 */
	public void updateGroupSendStatus(CMi401 mi401) throws Exception;
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public HashMap sendSmsCheckAndMessage(AppApi50004Form form, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 手机短信WeiBo推送
	 * @return
	 * @throws Exception
	 */
	public int sendWeiBo(Mi403 mi403 , HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 检查消息定时表——并推送
	 * @return
	 * @throws Exception
	 */
	public void checkTimingAndSend() throws Exception;
	
	/**
	 * 检查定时状态是否正常,十分钟还未是正常状态则重置
	 * @return
	 * @throws Exception
	 */
	public  void checkMi415() throws Exception;
	
	public List<Mi040> getChannelPid(String centerid ,String channel) throws Exception;
	/**
	 * 图文评论开关
	 * @return
	 * @throws Exception
	 */
	public String webapi30201_commentCtrl(CMi404 form) throws Exception;
	/**
	 * 获取图文信息
	 * @return
	 * @throws Exception
	 */
	public HashMap getTextImage(AppApi90421Form form) throws Exception;
	/**
	 * 多图文预览发送
	 * @return
	 * @throws Exception
	 */
	public String webapi30201_perviewSend(CMi401 form, ModelMap modelMap) throws Exception;
	/**
	 * 多图文消息删除
	 * @return
	 * @throws Exception
	 */
	public String webapi30201_deleteSend(CMi404 form, ModelMap modelMap) throws Exception;
	/**
	 * 单图文插入mi401表
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public String webapi30201_addTextImageSingle401(CMi401 form) throws Exception;
	/**
	 * 单图文修改mi401表
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public String webapi30201_editTextImageSingle401(CMi401 form) throws Exception;
	/**
	 * 单图文更新mi404表
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public int webapi30201_updateTextImageSingle(CMi401 form) throws Exception;
	/**
	 * 单图文添加mi404表
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public void webapi30201_addTextImageSingle(CMi401 form) throws Exception;
}
