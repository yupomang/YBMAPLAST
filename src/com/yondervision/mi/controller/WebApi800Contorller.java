package com.yondervision.mi.controller;

import com.yd.util.DateUtils;
import com.yd.util.FileUtil;
import com.yd.util.StringUtil;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.filter.ParameterRequestWrapper;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.form.AppApi030Form;
import com.yondervision.mi.form.AppApi80007Form;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.DigitalSealService;
import com.yondervision.mi.util.ReadProperty;
import com.yondervision.mi.zwfwutil.RSAUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/** 
* @ClassName: WebApi800Contorller 
* @Description: 发布在大数据平台的接口
* @author Carter King  
* @date Mar 15, 2018 17:38:25 PM   
*/ 
@Controller
public class WebApi800Contorller {
	@Autowired
	private MsgSendApi001Service msgSendApi001Service = null;
	public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
		this.msgSendApi001Service = msgSendApi001Service;
	}
	/*private static String TEMPLATE_PATH = ReadProperty.getString("template_path");
	private static String TEMP_PATH = ReadProperty.getString("temp_path");*/
	/**
	 * 发布在大数据平台供房管局调用的接口
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi80001.{ext}")
	public String webapi80001(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("发布在大数据平台供房管局调用的接口");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		request.setAttribute("centerId", form.getCenterId());
		String rep=msgSendApi001Service.send(request, response);
		response.getOutputStream().write(rep.getBytes("UTF-8"));
		log.info("rep="+rep);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "/index";
	}

	/**
	 * 发布在大数据平台的单位信息查询接口
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception
	 */
	@RequestMapping("/webapi80009.{ext}")
	public String webapi80009(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request,
							  HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("发布在大数据平台的单位信息查询组件");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));

		log.debug("spt_dwmc 转换前："+ form.getSpt_dwmc());
		log.debug("spt_dwmc 解密转换中："+ URLDecoder.decode(form.getSpt_dwmc(),"utf-8"));

		log.debug("request 请求参数 spt_dwmc："+ request.getParameter("spt_dwmc"));
		log.debug("request 请求参数 spt_tyxyydm："+ request.getParameter("spt_tyxyydm"));

		request.setAttribute("centerId", form.getCenterId());
		String IP2 = getIpAddr(request);
		System.out.println("webapi80009 Get Access IP2="+IP2);
		String rep = null;

		log.debug("request 请求参数 fullPara ："+ request.getParameter("fullPara"));
		log.debug("request 请求参数 spt_dwmc ："+ request.getParameter("spt_dwmc"));

		rep = msgSendApi001Service.send( request, response);

		response.getOutputStream().write(rep.getBytes("UTF-8"));
		log.info("rep="+rep);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		modelMap.clear();
		return "/index";
	}

	/**
	 * 发布在大数据平台的个人账户查询接口
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi80002.{ext}")
	public String webapi80002(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("发布在大数据平台的个人账户查询接口");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		request.setAttribute("centerId", form.getCenterId());
//		String IP1 = getRemortIP(request);
//		System.out.println("IP1==========="+IP1);
		String IP2 = getIpAddr(request);
		System.out.println("webapi80002 Get Access IP2："+IP2);
		String rep= null;

/*		请原先对IP地址59.202.34.236、59.202.34.32做过IP白名单设置的部门在本周三（2018-05-23）下班前，
		增加对59.202.30.144、59.202.30.145、59.202.30.164、59.202.34.156、59.202.34.184的IP白名单限制*/
		if (IP2.equals("112.13.226.98")||IP2.equals("59.202.34.236")||IP2.equals("59.202.34.32")
				||IP2.equals("59.202.30.144")||IP2.equals("59.202.30.145")||IP2.equals("59.202.30.164")
				||IP2.equals("59.202.34.156")||IP2.equals("59.202.34.184")||IP2.equals("60.190.56.8")
				||IP2.equals("10.68.138.197") || IP2.equals("10.68.138.198") || IP2.equals("59.202.62.174")
				||IP2.equals("60.12.18.8") || IP2.equals("59.202.62.248") || IP2.equals("220.191.249.136"))  {
			rep=msgSendApi001Service.send(request, response);
		}else{
			log.info(IP2 + " IP NO ACCESS");
			rep="您的IP:"+ IP2 +"不在白名单内，无权限访问该接口";
		}
		response.getOutputStream().write(rep.getBytes("UTF-8"));
		log.info("rep="+rep);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		modelMap.clear();
		return "/index";
	}
	
	/**
	 * 发布在大数据平台的个人明细查询接口
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi80003.{ext}")
	public String webapi80003(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("发布在大数据平台的个人明细查询接口");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		request.setAttribute("centerId", form.getCenterId());
//		String IP1 = getRemortIP(request);
//		System.out.println("IP1==========="+IP1);
		String IP2 = getIpAddr(request);
		System.out.println("webapi80003 Get Access IP2："+IP2);
		String rep= null;
/*		请原先对IP地址59.202.34.236、59.202.34.32做过IP白名单设置的部门在本周三（2018-05-23）下班前，
		增加对59.202.30.144、59.202.30.145、59.202.30.164、59.202.34.156、59.202.34.184的IP白名单限制*/
		if (IP2.equals("112.13.226.98")||IP2.equals("59.202.34.236")||IP2.equals("59.202.34.32")
				||IP2.equals("59.202.30.144")||IP2.equals("59.202.30.145")||IP2.equals("59.202.30.164")
				||IP2.equals("59.202.34.156")||IP2.equals("59.202.34.184")||IP2.equals("60.190.56.8")) {
			rep=msgSendApi001Service.send(request, response);
		}else{
			log.info(IP2 + "IP NO ACCESS");
			rep="您的IP:"+IP2+"不在白名单内，无权限访问该接口";
		}
		response.getOutputStream().write(rep.getBytes("UTF-8"));
		log.info("rep="+rep);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		modelMap.clear();
		return "/index";
	}

	/**
	 * 住建委业务受理号上传
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi80004.{ext}")
	public String webapi80004(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("住建委业务受理号上传");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		request.setAttribute("centerId", form.getCenterId());
		String IP1 = getRemortIP(request);
		System.out.println("IP1==========="+IP1);
		String IP2 = getIpAddr(request);
		System.out.println("IP2==========="+IP2);
		String rep= null;
		
		System.out.println("form.getBaseInfoXml()========="+form.getBaseInfoXml());
		System.out.println("form.getAttrXml()============"+form.getAttrXml());
		System.out.println("form.getFormXml()==========="+form.getFormXml());
		
		//转码成utf-8
		String baseinfo=new String(form.getBaseInfoXml().getBytes("iso-8859-1"), "utf-8");
		String attr=new String(form.getAttrXml().getBytes("iso-8859-1"), "utf-8");
		String formx=new String(form.getFormXml().getBytes("iso-8859-1"), "utf-8");
		form.setBaseInfoXml(baseinfo);
		form.setAttrXml(attr);
		form.setFormXml(formx);
		System.out.println("baseinfo========="+baseinfo);
		System.out.println("attr============"+attr);
		System.out.println("formx==========="+formx);

		if (IP2.equals("112.13.226.98")||IP2.equals("10.19.93.230")||IP2.equals("112.49.30.67")||IP2.equals("60.190.56.5")||IP2.equals("59.83.198.145")||IP2.equals("220.191.249.254")) {
			rep=msgSendApi001Service.send(request, response);
		}else{
			log.info(IP2 + "IP NO ACCESS");
			rep="您的IP"+IP2+"不在白名单内，无权限访问该接口";
		}
		response.getOutputStream().write(rep.getBytes("UTF-8"));
		log.info("rep="+rep);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		modelMap.clear();
		return "/index";
	}
	
	
	/**
	 * 个人账户余额查询
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi80005.{ext}")
	public String webapi80005(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人账户余额查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		request.setAttribute("centerId", form.getCenterId());

		HashMap m=new HashMap(request.getParameterMap());
		HttpServletRequest request1=(HttpServletRequest) request;

		String para=form.getPara();
		System.out.println("para0==========="+para);
		para=RSAUtil.decrypt(para.replace("%2B", "+"));
		System.out.println("para1==========="+para);
		para=para.substring(para.lastIndexOf("{\"")+1);
		para="{"+para;
		System.out.println("para3==========="+para);
		JSONObject jsonobject1 = JSONObject.fromObject(para);
		
		Iterator<String> it=jsonobject1.keys();
		String key="";
		String value="";
		while(it.hasNext()){
			key=it.next();
			value=jsonobject1.getString(key);
			if(value==null){
				value="";
			}
			m.put(key,value);
			System.out.println("key========="+key+"value========="+value);
		}
		
		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
		
		String rep= null;
		
		rep=msgSendApi001Service.send(wrapRequest, response);	
		JSONObject jsonobject = JSONObject.fromObject(rep);
		
		jsonobject.put("xm", jsonobject1.get("xm").toString());
		String result=jsonobject.toString();
		
		System.out.println("result==========="+result);
		response.getOutputStream().write(RSAUtil.encrypt(result).getBytes("UTF-8"));
		
/*		String xx=RSAUtil.encrypt("222");
		response.getOutputStream().write(xx.getBytes("UTF-8"));
		log.info("111="+xx);
		log.info("333="+RSAUtil.decrypt(xx));*/
		log.info("result="+result);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		modelMap.clear();
		return "/index";
	}

	/**
	 * 住建委办件中心-收件服务
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception
	 */
	@RequestMapping("/webapi80007.{ext}")
	public String webapi80007(AppApi80007Form form, ModelMap modelMap, HttpServletRequest request,
							  HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("住建委办件中心-收件服务");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		request.setAttribute("centerId", form.getCenterId());
		String IP1 = getRemortIP(request);
		System.out.println("IP1==========="+IP1);
		String IP2 = getIpAddr(request);
		System.out.println("IP2==========="+IP2);
		String rep= null;
		System.out.println("form.getParam()"+form.getParam());
		System.out.println("form.getParam()iso-8859-1"+new String(form.getParam().getBytes("iso-8859-1"), "utf-8"));

		//System.out.println("form.getApplicantVO()========="+form.getApplicantVO());
		//System.out.println("form.getSuffInfoList()============"+form.getSuffInfoList());

		//转码成utf-8
		/*String baseinfo=new String(form.getApplicantVO().getBytes("iso-8859-1"), "utf-8");
		String attr=new String(form.getSuffInfoList().getBytes("iso-8859-1"), "utf-8");
		form.setApplicantVO(baseinfo);
		form.setSuffInfoList(attr);
		System.out.println("baseinfo========="+baseinfo);
		System.out.println("attr============"+attr);*/

/*		if (IP2.equals("112.13.226.98")||IP2.equals("10.19.93.230")||IP2.equals("112.49.30.67")||IP2.equals("60.190.56.5")||IP2.equals("59.83.198.145")||IP2.equals("220.191.249.254")||
		IP2.equals("60.190.56.8")||IP2.equals("60.12.18.8")||IP2.equals("211.140.48.5")) {*/
			rep=msgSendApi001Service.send(request, response);
		/*}else{
			rep="您的IP"+IP2+"不在白名单内，无权限访问该接口";
		}*/
		response.getOutputStream().write(rep.getBytes("UTF-8"));
		log.info("rep="+rep);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		modelMap.clear();
		return "/index";
	}

	/**
	 * 发布在大数据平台的公积金贷款信息查询接口
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception
	 */
	@RequestMapping("/webapi80006.{ext}")
	public String webapi80006(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request,
							  HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("发布在大数据平台的公积金贷款信息查询接口");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		request.setAttribute("centerId", form.getCenterId());
//		String IP1 = getRemortIP(request);
//		System.out.println("IP1==========="+IP1);
		String IP2 = getIpAddr(request);
		System.out.println("IP2==========="+IP2);
		String rep= null;
		/*请原先对IP地址59.202.34.236、59.202.34.32做过IP白名单设置的部门在本周三（2018-05-23）下班前，
		增加对59.202.30.144、59.202.30.145、59.202.30.164、59.202.34.156、59.202.34.184的IP白名单限制
		增加税务ip 60.190.56.15 , 60.12.18.15 ,211.140.48.15
		本地ip60.12.18.8 211.140.48.8*/

		if (IP2.equals("112.13.226.98")||IP2.equals("59.202.34.236")||IP2.equals("59.202.34.32")
				||IP2.equals("59.202.30.144")||IP2.equals("59.202.30.145")||IP2.equals("59.202.30.164")
				||IP2.equals("59.202.34.156")||IP2.equals("59.202.34.184")||IP2.equals("60.190.56.8")
				||IP2.equals("60.190.56.15")||IP2.equals("60.12.18.15")||IP2.equals("211.140.48.15")||
				IP2.equals("60.12.18.8")||IP2.equals("211.140.48.8")) {
			rep=msgSendApi001Service.send(request, response);
		}else{
			log.info(IP2 + "IP NO ACCESS");
			rep="您的IP不在白名单内，无权限访问该接口";

		}
		response.getOutputStream().write(rep.getBytes("UTF-8"));
		log.info("rep="+rep);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		modelMap.clear();
		return "/index";
	}

	/**
	 * 创建PDF文件
	 */

	//@ResponseBody
	private static String TEMPLATE_PATH = ReadProperty.getString("template_path");
	private static String TEMP_PATH = ReadProperty.getString("temp_path");
	private static String signed_pdf_path = ReadProperty.getString("signed_pdf_path");
	@RequestMapping("/webapi80008.{ext}")
	public Map<String, String> createPdf(AppApi80007Form form,  HttpServletRequest request,HttpServletResponse response) throws Exception {
		Logger logger = LoggerUtil.getLogger();
		form.setBusinName("发布在大数据平台的个人缴存证明打印接口");
		logger.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		logger.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		request.setAttribute("centerId", form.getCenterId());
		String rep=msgSendApi001Service.send(request, response);
		logger.debug("rep========"+rep);
		String seqno = "00057400";//凭证流水号
		String templateName = "zgjczm.doc";//模板文件名
		//String voucherData = request.getParameter("voucherData");
		JSONObject json = JSONObject.fromObject(rep);
		if (String.valueOf(json.get("recode")).equals("999999")){
			response.getOutputStream().write(json.toString().getBytes("UTF-8"));
			return json;
		}
		String voucherData = String.valueOf(json.get("voucherData"));
		//String voucherData=new String(form.getVoucherData().getBytes("iso-8859-1"), "utf-8");//数据
		String tranDate = DateUtils.getyyyyMMddHHmmss();
		logger.debug("voucherData========"+voucherData);
		logger.debug("接收到的请求参数：凭证流水号：" + seqno + ",templateName：" + templateName + ",voucherData:" + voucherData + ",tranDate:"
				+ tranDate + ",sfzh:" + form.getSfzh());
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isBlank(seqno)) {
			logger.warn("seqno不能为空！");
			map.put("returnCode", "1");
			map.put("message", "凭证编号不能为空");
			response.getOutputStream().write(map.toString().getBytes("UTF-8"));
			return map;
		}

		String fullName = seqno + "_" + templateName + "_" + form.getSfzh();

		File pdfFile = null;
		if (templateName.endsWith(".xls")) {
			// 创建excel文件
			File templateFile = new File(TEMPLATE_PATH, templateName);
			File excelFile = FileUtil.createExcel(templateFile, voucherData, new File(TEMP_PATH, fullName + ".xls"));
			if (excelFile == null) {
				logger.error("excel文件生成失败！");
				map.put("returnCode", "1");
				map.put("message", "凭证文件生成异常！");
				response.getOutputStream().write(map.toString().getBytes("UTF-8"));
				return map;
			}

			pdfFile = FileUtil.excelToPdf(excelFile);
			//if (pdfFile != null)
			//	excelFile.delete();
		} else if (templateName.endsWith(".doc")) {
			String templatePath = ReadProperty.getString("template_path");
			File docFile = FileUtil.createWord(templatePath + templateName, new File(TEMP_PATH, fullName + ".doc"), voucherData);
			pdfFile = FileUtil.wordToPdf(docFile, new File(TEMP_PATH, fullName + ".pdf"));
		}


		if (pdfFile == null) {
			logger.error("生成的PDF文件为空！");
			json.remove("voucherData");
			json.put("returnCode", "1");
			json.put("message", "凭证文件生成异常！");
			response.getOutputStream().write(json.toString().getBytes("UTF-8"));
			return map;
		}
		logger.info("PDF文件生成完毕！");

		//map.put("returnCode", "0");
		//map.put("pdfUrl", "file/showTempPDF?fileName=" + pdfFile.getName());

		//String[] imgNameArr = FileUtil.pdfToImage(TEMP_PATH, pdfFile.getName());
		//StringBuffer imgStr = new StringBuffer();
		//for (String boo : imgNameArr) {
		//	imgStr.append(boo).append(",");
		//}
		//String imageName = StringUtil.trimStr(imgStr.toString(), ",");
		//map.put("imgName", imageName);
		//String str = "file/showImg?imgName=";
		//map.put("imgUrl", str + imageName.replaceAll(",", "," + str));
		//logger.info("PDF文件转图片完毕！");

		json.remove("voucherData");
		//生产http://61.153.144.77:7001/YBMAPZH/webapi90001.json?filepath=/ispshare/ftpdir/
		//测试http://61.153.144.77:7006/YBMAPZH/webapi90001.json?filepath=/wls/saleContractPDF/temp/
		String pdftempUrl = TEMP_PATH + pdfFile.getName();//盖章前pdf存放位置
		String pdfUrl = signed_pdf_path + pdfFile.getName();//盖章后pdf存放位置
		//===============调用天谷系统盖章方法根据关键字盖章==========================
		//String errmassage = DigitalSealService.digitalSealByKey("(章)",pdftempUrl,pdfUrl);
		String errmassage = DigitalSealService.digitalSealByLocation(90,740,pdftempUrl,pdfUrl);
		if (errmassage.equals("成功")){
			//json.put("filepath","http://61.153.144.77:7006/YBMAPZH/webapi90001.json?filepath=/wls/saleContractPDF/pdf/");
			json.put("filepath","http://61.153.144.77:7001/YBMAPZH/webapi90001.json?filepath=/ispshare/ftpdir/pdf/");
			json.put("filename",pdfFile.getName());
			response.getOutputStream().write(json.toString().getBytes("UTF-8"));
			logger.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			map.clear();
			return map;
		}else{
			json.put("errmassage",errmassage);
			response.getOutputStream().write(json.toString().getBytes("UTF-8"));
			return map;
		}
	}
	@RequestMapping("/webapi80010.{ext}")
	public Map<String, String> webapi80010(AppApi80007Form form,  HttpServletRequest request,HttpServletResponse response) throws Exception {
		Logger logger = LoggerUtil.getLogger();
		form.setBusinName("发布在大数据平台的个人缴存证明打印接口");
		logger.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		logger.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		request.setAttribute("centerId", form.getCenterId());
		String rep=msgSendApi001Service.send(request, response);
		logger.debug("rep========"+rep);
		String seqno = "00057400";//凭证流水号
		String templateName = "zgjczm.doc";//模板文件名
		//String voucherData = request.getParameter("voucherData");
		JSONObject json = JSONObject.fromObject(rep);
		if (String.valueOf(json.get("recode")).equals("999999")){
			response.getOutputStream().write(json.toString().getBytes("UTF-8"));
			return json;
		}
		String voucherData = String.valueOf(json.get("voucherData"));
		//String voucherData=new String(form.getVoucherData().getBytes("iso-8859-1"), "utf-8");//数据
		String tranDate = DateUtils.getyyyyMMddHHmmss();
		logger.debug("voucherData========"+voucherData);
		logger.debug("接收到的请求参数：凭证流水号：" + seqno + ",templateName：" + templateName + ",voucherData:" + voucherData + ",tranDate:"
				+ tranDate + ",sfzh:" + form.getSfzh());
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isBlank(seqno)) {
			logger.warn("seqno不能为空！");
			map.put("returnCode", "1");
			map.put("message", "凭证编号不能为空");
			response.getOutputStream().write(map.toString().getBytes("UTF-8"));
			return map;
		}

		String fullName = seqno + "_" + templateName + "_" + form.getSfzh();

		File pdfFile = null;
		if (templateName.endsWith(".xls")) {
			// 创建excel文件
			File templateFile = new File(TEMPLATE_PATH, templateName);
			File excelFile = FileUtil.createExcel(templateFile, voucherData, new File(TEMP_PATH, fullName + ".xls"));
			if (excelFile == null) {
				logger.error("excel文件生成失败！");
				map.put("returnCode", "1");
				map.put("message", "凭证文件生成异常！");
				response.getOutputStream().write(map.toString().getBytes("UTF-8"));
				return map;
			}

			pdfFile = FileUtil.excelToPdf(excelFile);
			//if (pdfFile != null)
			//	excelFile.delete();
		} else if (templateName.endsWith(".doc")) {
			String templatePath = ReadProperty.getString("template_path");
			File docFile = FileUtil.createWord(templatePath + templateName, new File(TEMP_PATH, fullName + ".doc"), voucherData);
			pdfFile = FileUtil.wordToPdf(docFile, new File(TEMP_PATH, fullName + ".pdf"));
		}


		if (pdfFile == null) {
			logger.error("生成的PDF文件为空！");
			json.remove("voucherData");
			json.put("returnCode", "1");
			json.put("message", "凭证文件生成异常！");
			response.getOutputStream().write(json.toString().getBytes("UTF-8"));
			return map;
		}
		logger.info("PDF文件生成完毕！");

		//map.put("returnCode", "0");
		//map.put("pdfUrl", "file/showTempPDF?fileName=" + pdfFile.getName());

		//String[] imgNameArr = FileUtil.pdfToImage(TEMP_PATH, pdfFile.getName());
		//StringBuffer imgStr = new StringBuffer();
		//for (String boo : imgNameArr) {
		//	imgStr.append(boo).append(",");
		//}
		//String imageName = StringUtil.trimStr(imgStr.toString(), ",");
		//map.put("imgName", imageName);
		//String str = "file/showImg?imgName=";
		//map.put("imgUrl", str + imageName.replaceAll(",", "," + str));
		//logger.info("PDF文件转图片完毕！");

		json.remove("voucherData");
		//生产http://61.153.144.77:7001/YBMAPZH/webapi90001.json?filepath=/ispshare/ftpdir/
		//测试http://61.153.144.77:7006/YBMAPZH/webapi90001.json?filepath=/wls/saleContractPDF/temp/
		String pdftempUrl = TEMP_PATH + pdfFile.getName();//盖章前pdf存放位置
		String pdfUrl = signed_pdf_path + pdfFile.getName();//盖章后pdf存放位置
		//===============调用天谷系统盖章方法根据关键字盖章==========================
		//String errmassage = DigitalSealService.digitalSealByKey("(章)",pdftempUrl,pdfUrl);
		String errmassage = DigitalSealService.digitalSealByLocation(90,740,pdftempUrl,pdfUrl);


		if (errmassage.equals("成功")){
			File file = new File(pdfUrl);
			String fileName = pdfFile.getName();
			String PDFToBase64 = PDFToBase64(file);//对pdf文件进行Base64加密
			//String filepath = pdfFile.getPath();
			contentToTxt("/ispshare/ftpdir/"+fileName+".txt",PDFToBase64);
			//json.put("filepath","http://61.153.144.77:7006/YBMAPZH/webapi90001.json?filepath=/wls/saleContractPDF/pdf/");
			json.put("filepath","http://61.153.144.77:7001/YBMAPZH/webapi90001.json?filepath=/ispshare/ftpdir/");
			json.put("filename",pdfFile.getName()+".txt");
			response.getOutputStream().write(json.toString().getBytes("UTF-8"));
			logger.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			map.clear();
			return map;
		}else{
			json.put("errmassage",errmassage);
			response.getOutputStream().write(json.toString().getBytes("UTF-8"));
			return map;
		}
	}
	/**
	 * 创建新文件并写入内容
	 * @param filePath,content
	 * @return
	 */
	public static void contentToTxt(String filePath, String content) {
		Logger logger = LoggerUtil.getLogger();
		String str = new String(); //原有txt内容
		String s1 = new String();//内容更新
		try {
			File f = new File(filePath);
			if (f.exists()) {
				System.out.print("文件存在");
				f =new File(filePath);
			} else {
				System.out.print("文件不存在");
				f.createNewFile();// 不存在则创建
			}
			BufferedReader input = new BufferedReader(new FileReader(f));

			/*while ((str = input.readLine()) != null) {
				s1 += str + "\n";
			}
			System.out.println(s1);*/
			input.close();
			s1 += content;
			logger.info("weu_s1======"+s1);

			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			output.write(s1);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description: 将pdf文件转换为Base64编码
	 * @param  file
	 * @Author fuyuwei
	 * Create Date: 2015年8月3日 下午9:52:30
	 */
	public static String PDFToBase64(File file) {
		BASE64Encoder encoder = new BASE64Encoder();
		FileInputStream fin =null;
		BufferedInputStream bin =null;
		ByteArrayOutputStream baos = null;
		BufferedOutputStream bout =null;
		try {
			fin = new FileInputStream(file);
			bin = new BufferedInputStream(fin);
			baos = new ByteArrayOutputStream();
			bout = new BufferedOutputStream(baos);
			byte[] buffer = new byte[1024];
			int len = bin.read(buffer);
			while(len != -1){
				bout.write(buffer, 0, len);
				len = bin.read(buffer);
			}
			//刷新此输出流并强制写出所有缓冲的输出字节
			bout.flush();
			byte[] bytes = baos.toByteArray();
			return encoder.encodeBuffer(bytes).trim();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				fin.close();
				bin.close();
				bout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 发布在大数据平台的个人账户查询接口
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception
	 */

	@RequestMapping("/appapi00228.{ext}")
	public String appapi00228(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request,
							  HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("发布在大数据平台的宁波公积金注销申请接收接口");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		request.setAttribute("centerId", form.getCenterId());
		String IP1 = getRemortIP(request);
		System.out.println("IP1==========="+IP1);
		String IP2 = getIpAddr(request);
		System.out.println("IP2==========="+IP2);
		String rep= null;
		response.getOutputStream().write(rep.getBytes("UTF-8"));
		log.info("rep="+rep);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		modelMap.clear();
		return "/index";
	}

	public String getRemortIP(HttpServletRequest request) { 
		  if (request.getHeader("x-forwarded-for") == null) { 
		   return request.getRemoteAddr(); 
		  } 
		  return request.getHeader("x-forwarded-for"); 
	}
	
	public String getIpAddr(HttpServletRequest request) { 
	       String ip = request.getHeader("x-forwarded-for"); 
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	           ip = request.getHeader("Proxy-Client-IP"); 
	       } 
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	           ip = request.getHeader("WL-Proxy-Client-IP"); 
	       } 
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	           ip = request.getRemoteAddr(); 
	       } 
	       return ip; 
	}
}
