package com.yondervision.mi.controller;

import com.yondervision.mi.common.ApiUserContext;
import com.yondervision.mi.common.ERRCODE;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.filter.ParameterRequestWrapper;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.dto.Mi031;
import com.yondervision.mi.dto.Mi201;
import com.yondervision.mi.form.*;
import com.yondervision.mi.result.*;
import com.yondervision.mi.service.*;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JavaBeanUtil;
import com.yondervision.mi.util.PropertiesReader;
import com.yondervision.mi.util.WebService;
import com.yondervision.mi.util.security.AES;
import net.sf.json.JSONObject;
import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class AppApi101Contorller
{

  @Autowired
  private AppApi101Service appApi101ServiceImpl = null;

  @Autowired
  private CodeListApi001Service codeListApi001Service = null;

  @Autowired
  private MsgSendApi001Service msgSendApi001Service = null;

  @Autowired
  private AppApi700Service appApi700ServiceImpl = null;

  @Autowired
  private WebApi029Service webApi029ServiceImpl = null;

  @Autowired
  private AppApi303Service appApi303Service;

  @Autowired
  private WebApi627Service webApi627Service = null;

  public WebApi627Service getWebApi627Service() {
    return this.webApi627Service;
  }

  public void setWebApi627Service(WebApi627Service webApi627Service) {
    this.webApi627Service = webApi627Service;
  }

  public AppApi303Service getAppApi303Service() {
    return this.appApi303Service;
  }

  public void setAppApi303Service(AppApi303Service appApi303Service) {
    this.appApi303Service = appApi303Service;
  }

  public WebApi029Service getWebApi029ServiceImpl() {
    return this.webApi029ServiceImpl;
  }

  public void setWebApi029ServiceImpl(WebApi029Service webApi029ServiceImpl) {
    this.webApi029ServiceImpl = webApi029ServiceImpl;
  }

  public void setAppApi700ServiceImpl(AppApi700Service appApi700ServiceImpl) {
    this.appApi700ServiceImpl = appApi700ServiceImpl;
  }

  public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
    this.msgSendApi001Service = msgSendApi001Service;
  }

  public void setCodeListApi001Service(CodeListApi001Service codeListApi001Service) {
    this.codeListApi001Service = codeListApi001Service;
  }

  public void setAppApi101ServiceImpl(AppApi101Service appApi101ServiceImpl) {
    this.appApi101ServiceImpl = appApi101ServiceImpl;
  }

  @RequestMapping({"/appapi10101.{ext}"})
  public String appApi10101(AppApi10101Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Logger log = LoggerUtil.getLogger();
    form.setBusinName("APP网点查询");
    log.info(ERRCODE.LOG.START_BUSIN.getLogText(new String[] { form.getBusinName() }));
    log.debug(ERRCODE.DEBUG.SHOW_PARAM.getLogText(new String[] { 
      CommonUtil.getStringParams(form) }));
    if (CommonUtil.isEmpty(form.getCenterId())) {
      log.error(ERRCODE.ERROR.PARAMS_NULL.getLogText(new String[] { "centerId" }));
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.PARAMS_NULL.getValue(), new String[] { "城市中心ID" });
    }
    List list = this.appApi101ServiceImpl.appApi10101Select(form);
    if ((list.isEmpty()) || (list.size() == 0)) {
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.NO_DATA.getValue(), new String[] { "网点信息" });
    }
    List resultList = new ArrayList();
    for (int i = 0; i < list.size(); i++) {
      AppApi10101Result appApi10101Result = new AppApi10101Result();
      List TitleInfoBeanContent = new ArrayList();
      List TitleInfoBeanList = new ArrayList();
      Mi201 mi201 = (Mi201)list.get(i);
      mi201.setTel("tel://" + mi201.getTel());
      mi201.setServiceTime("time://" + mi201.getServiceTime());
      if ((!CommonUtil.isEmpty(mi201.getPositionX())) && (!CommonUtil.isEmpty(mi201.getPositionY()))) {
        mi201.setAddress("map://" + mi201.getAddress());
      }
      AppApi10101Result01 appApi10101Result01 = new AppApi10101Result01();
      appApi10101Result01.setX(mi201.getPositionX() == null ? "" : mi201.getPositionX());
      appApi10101Result01.setY(mi201.getPositionY() == null ? "" : mi201.getPositionY());

      appApi10101Result01.setImg(request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf("/") + 1) + CommonUtil.getDownloadFileUrl(
        "push_website_img", new StringBuilder(String.valueOf(form.getCenterId())).append(File.separator).append(mi201.getImageUrl()).toString(), true));
      appApi10101Result.setMap(appApi10101Result01);
      TitleInfoBeanContent = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi10101.content", mi201);
      appApi10101Result.setContent(TitleInfoBeanContent);
      TitleInfoBeanList = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi10101.list", mi201);
      appApi10101Result.setList(TitleInfoBeanList);
      resultList.add(appApi10101Result);
    }
    modelMap.clear();
    modelMap.put("recode", "000000");
    modelMap.put("msg", "成功");
    modelMap.put("result", resultList);
    log.info(ERRCODE.LOG.END_BUSIN.getLogText(new String[] { form.getBusinName() }));
    return "";
  }

  @RequestMapping({"/appapi10102.{ext}"})
  public String appApi10102(AppApi10101Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Logger log = LoggerUtil.getLogger();
    form.setBusinName("APP摇一摇网点查询");
    System.out.println("网点查询坐标positionX：" + form.getPositionX() + "   positionY：" + form.getPositionY());
    log.info(ERRCODE.LOG.START_BUSIN.getLogText(new String[] { form.getBusinName() }));
    log.debug(ERRCODE.DEBUG.SHOW_PARAM.getLogText(new String[] { 
      CommonUtil.getStringParams(form) }));

    if ((CommonUtil.isEmpty(form.getPositionX())) || (Double.parseDouble(form.getPositionX()) == 0.0D) || (CommonUtil.isEmpty(form.getPositionY())) || (Double.parseDouble(form.getPositionY()) == 0.0D)) {
      log.error(ERRCODE.ERROR.PARAMS_NULL.getLogText(new String[] { "PositionX,PositionY" }));
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.PARAMS_NULL.getValue(), new String[] { "地理信息" });
    }

    List list = this.appApi101ServiceImpl.appApi10102Select(form);
    if ((list.isEmpty()) || (list.size() == 0)) {
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.NO_DATA.getValue(), new String[] { "摇一摇网点信息" });
    }
    List resultList = new ArrayList();
    for (int i = 0; i < list.size(); i++) {
      AppApi10102Result appApi10102Result = new AppApi10102Result();
      List TitleInfoBeanContent = new ArrayList();
      List TitleInfoBeanList = new ArrayList();
      Mi201 mi201 = (Mi201)list.get(i);
      mi201.setTel("tel://" + mi201.getTel());
      mi201.setServiceTime("time://" + mi201.getServiceTime());
      if ((mi201.getPositionX() != null) && (mi201.getPositionY() != null))
        mi201.setAddress("map://" + mi201.getAddress());
      else {
        mi201.setAddress(mi201.getAddress());
      }
      AppApi10101Result02 appApi10101Result02 = new AppApi10101Result02();
      appApi10101Result02.setX(mi201.getPositionX() == null ? "" : mi201.getPositionX());
      appApi10101Result02.setY(mi201.getPositionY() == null ? "" : mi201.getPositionY());
      appApi10101Result02.setDistance(mi201.getDistance() + "km");

      appApi10101Result02.setImg(request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf("/") + 1) + CommonUtil.getDownloadFileUrl(
        "push_website_img", new StringBuilder(String.valueOf(form.getCenterId())).append(File.separator).append(mi201.getImageUrl()).toString(), true));
      appApi10102Result.setMap(appApi10101Result02);
      TitleInfoBeanContent = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi10101.content", mi201);
      appApi10102Result.setContent(TitleInfoBeanContent);
      TitleInfoBeanList = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi10101.list", mi201);
      appApi10102Result.setList(TitleInfoBeanList);
      resultList.add(appApi10102Result);
    }
    modelMap.clear();
    modelMap.put("recode", "000000");
    modelMap.put("msg", "成功");
    modelMap.put("result", resultList);
    log.info(ERRCODE.LOG.END_BUSIN.getLogText(new String[] { form.getBusinName() }));
    return "/index";
  }

  @RequestMapping({"/appapi10103.{ext}"})
  public String appApi10103(AppApiCommonForm form, ModelMap modelMap)
    throws Exception
  {
    Logger log = LoggerUtil.getLogger();
    form.setBusinName("网点类型");
    log.info(ERRCODE.LOG.START_BUSIN.getLogText(new String[] { form.getBusinName() }));
    log.debug(ERRCODE.DEBUG.SHOW_PARAM.getLogText(new String[] { 
      CommonUtil.getStringParams(form) }));

    List list = this.codeListApi001Service.getCodeList(form.getCenterId(), "websitetype");
    if ((list.isEmpty()) || (list.size() == 0)) {
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.NO_DATA.getValue(), new String[] { "网点类型" });
    }
    List listResult = new ArrayList();
    for (int i = 0; i < list.size(); i++) {
      AppApi10103Result api10103 = new AppApi10103Result();
      api10103.setWebSiteTypeId(((Mi007)list.get(i)).getItemid());
      api10103.setWebSiteTypeName(((Mi007)list.get(i)).getItemval());
      listResult.add(api10103);
    }

    modelMap.clear();
    modelMap.put("recode", "000000");
    modelMap.put("msg", "成功");
    modelMap.put("result", listResult);
    log.info(ERRCODE.LOG.END_BUSIN.getLogText(new String[] { form.getBusinName() }));
    return "/index";
  }

  @RequestMapping({"/appapi10104.{ext}"})
  public String appApi10104(AppApi10101Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Logger log = LoggerUtil.getLogger();
    form.setBusinName("多条件网点查询");
    log.info(ERRCODE.LOG.START_BUSIN.getLogText(new String[] { form.getBusinName() }));
    log.debug(ERRCODE.DEBUG.SHOW_PARAM.getLogText(new String[] { 
      CommonUtil.getStringParams(form) }));

    if (CommonUtil.isEmpty(form.getCenterId())) {
      log.error(ERRCODE.ERROR.PARAMS_NULL.getLogText(new String[] { "centerId" }));
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.PARAMS_NULL.getValue(), new String[] { "城市中心ID" });
    }
    List list = this.appApi101ServiceImpl.appApi10104Select(form);
    if ((list.isEmpty()) || (list.size() == 0)) {
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.NO_DATA.getValue(), new String[] { "网点信息" });
    }
    List resultList = new ArrayList();
    for (int i = 0; i < list.size(); i++) {
      AppApi10101Result appApi10101Result = new AppApi10101Result();
      List TitleInfoBeanContent = new ArrayList();
      List TitleInfoBeanList = new ArrayList();
      Mi201 mi201 = (Mi201)list.get(i);
      mi201.setTel("tel://" + mi201.getTel());
      mi201.setServiceTime("time://" + mi201.getServiceTime());
      if ((!CommonUtil.isEmpty(mi201.getPositionX())) && (!CommonUtil.isEmpty(mi201.getPositionY()))) {
        mi201.setAddress("map://" + mi201.getAddress());
      }
      AppApi10101Result01 appApi10101Result01 = new AppApi10101Result01();
      appApi10101Result01.setX(mi201.getPositionX() == null ? "" : mi201.getPositionX());
      appApi10101Result01.setY(mi201.getPositionY() == null ? "" : mi201.getPositionY());

      appApi10101Result01.setImg(request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf("/") + 1) + CommonUtil.getDownloadFileUrl(
        "push_website_img", new StringBuilder(String.valueOf(form.getCenterId())).append(File.separator).append(mi201.getImageUrl()).toString(), true));
      appApi10101Result.setMap(appApi10101Result01);
      TitleInfoBeanContent = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi10101.content", mi201);
      appApi10101Result.setContent(TitleInfoBeanContent);
      TitleInfoBeanList = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi10101.list", mi201);
      appApi10101Result.setList(TitleInfoBeanList);
      resultList.add(appApi10101Result);
    }

    modelMap.clear();
    modelMap.put("recode", "000000");
    modelMap.put("msg", "成功");
    modelMap.put("result", resultList);
    log.info(ERRCODE.LOG.END_BUSIN.getLogText(new String[] { form.getBusinName() }));
    return "/index";
  }

  @RequestMapping({"/appapi10105.{ext}"})
  public String appApi10105(AppApi10101Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Logger log = LoggerUtil.getLogger();
    form.setBusinName("多条件网点查询-区域与网点类型-带网点编码");
    log.info(ERRCODE.LOG.START_BUSIN.getLogText(new String[] { form.getBusinName() }));
    log.debug(ERRCODE.DEBUG.SHOW_PARAM.getLogText(new String[] { 
      CommonUtil.getStringParams(form) }));

    if (CommonUtil.isEmpty(form.getCenterId())) {
      log.error(ERRCODE.ERROR.PARAMS_NULL.getLogText(new String[] { "centerId" }));
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.PARAMS_NULL.getValue(), new String[] { "城市中心ID" });
    }
    List list = this.appApi101ServiceImpl.appApi10104Select(form);
    if ((list.isEmpty()) || (list.size() == 0)) {
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.NO_DATA.getValue(), new String[] { "网点信息" });
    }
    List resultList = new ArrayList();
    for (int i = 0; i < list.size(); i++) {
      AppApi10105Result appApi10105Result = new AppApi10105Result();
      List TitleInfoBeanContent = new ArrayList();
      List TitleInfoBeanList = new ArrayList();
      Mi201 mi201 = (Mi201)list.get(i);
      mi201.setTel("tel://" + mi201.getTel());
      mi201.setServiceTime("time://" + mi201.getServiceTime());
      if ((!CommonUtil.isEmpty(mi201.getPositionX())) && (!CommonUtil.isEmpty(mi201.getPositionY()))) {
        mi201.setAddress("map://" + mi201.getAddress());
      }
      AppApi10101Result01 appApi10101Result01 = new AppApi10101Result01();
      appApi10101Result01.setX(mi201.getPositionX() == null ? "" : mi201.getPositionX());
      appApi10101Result01.setY(mi201.getPositionY() == null ? "" : mi201.getPositionY());

      appApi10101Result01.setImg(request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf("/") + 1) + CommonUtil.getDownloadFileUrl(
        "push_website_img", new StringBuilder(String.valueOf(form.getCenterId())).append(File.separator).append(mi201.getImageUrl()).toString(), true));
      appApi10105Result.setMap(appApi10101Result01);
      TitleInfoBeanContent = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi10101.content", mi201);
      appApi10105Result.setContent(TitleInfoBeanContent);
      TitleInfoBeanList = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi10101.list", mi201);
      appApi10105Result.setList(TitleInfoBeanList);
      appApi10105Result.setWebsitecode(mi201.getWebsiteCode());

      resultList.add(appApi10105Result);
    }

    modelMap.clear();
    modelMap.put("recode", "000000");
    modelMap.put("msg", "成功");
    modelMap.put("result", resultList);
    log.info(ERRCODE.LOG.END_BUSIN.getLogText(new String[] { form.getBusinName() }));
    return "";
  }

  @RequestMapping({"/appapi10106.{ext}"})
  public String appApi10106(AppApi10106Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Logger log = LoggerUtil.getLogger();
    form.setBusinName("获取网点排队信息");
    log.info(ERRCODE.LOG.START_BUSIN.getLogText(new String[] { form.getBusinName() }));
    log.debug(ERRCODE.DEBUG.SHOW_PARAM.getLogText(new String[] { 
      CommonUtil.getStringParams(form) }));

    if (CommonUtil.isEmpty(form.getCenterId())) {
      log.error(ERRCODE.ERROR.PARAMS_NULL.getLogText(new String[] { "centerId" }));
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.PARAMS_NULL.getValue(), new String[] { "城市中心ID" });
    }

    if (CommonUtil.isEmpty(form.getWebsitecode())) {
      log.error(ERRCODE.ERROR.PARAMS_NULL.getLogText(new String[] { "websitecode" }));
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.PARAMS_NULL.getValue(), new String[] { "网点编号" });
    }
    request.setAttribute("centerId", form.getCenterId());

    Mi201 mi201 = this.appApi101ServiceImpl.appApi10105Select(form.getWebsitecode(), form.getCenterId());

    if (CommonUtil.isEmpty(mi201)) {
      modelMap.clear();
      modelMap.put("recode", "999999");
      modelMap.put("msg", "网点不存在！");
      return "";
    }

    String url = PropertiesReader.getProperty("properties.properties", "queue_url").trim();

    OMElement omElement = WebService.WebServiceClient(url, 
      "http://www.hzhnkj.com.cn/", "getGroupWaitting2", 
      "ctrllerid,status", form.getWebsitecode() + "& ");

    System.out.println("response:" + omElement);
    int last = omElement.toString().lastIndexOf("</status>");
    System.out.println("last:" + last);
    System.out.println("status:" + omElement.toString().substring(last - 1, last));
    if (!"1".equals(omElement.toString().substring(last - 1, last))) {
      modelMap.clear();
      log.info("获取网点排队信息失败！网点编码：" + form.getWebsitecode());
      modelMap.put("recode", "999999");
      modelMap.put("msg", "获取网点排队信息失败！");
      return "";
    }
    OMElement elementReturn = omElement.getFirstElement();
    System.out.println("response1:" + elementReturn.getText());

    Document document = null;
    SAXReader reader = new SAXReader();
    
	//生产使用
	ByteArrayInputStream inputStream = new ByteArrayInputStream(elementReturn.getText().getBytes("UTF-8"));
	//测试使用
    //ByteArrayInputStream inputStream = new ByteArrayInputStream(elementReturn.getText().getBytes());
    
    InputStreamReader ir = new InputStreamReader(inputStream);
    document = reader.read(ir);
    Element rootElement = document.getRootElement();
	List<Element> listElement = rootElement.elements();
    int workcount = 0;
    int queueNumber = 0;
    List resultList = new ArrayList();
    for (Element level2 : listElement) {
      HashMap map = new HashMap();
      workcount += (level2.elementText("AcceptanceNumber") == "" ? 0 : Integer.valueOf(level2.elementText("AcceptanceNumber")).intValue());
      queueNumber += (level2.elementText("QueueNumber") == "" ? 0 : Integer.valueOf(level2.elementText("QueueNumber")).intValue());

      System.out.print("Ctrllerid:" + level2.elementText("Ctrllerid"));
      map.put("websitecode", level2.elementText("Ctrllerid"));
      System.out.print("GroupNo:" + level2.elementText("GroupNo"));
      map.put("jobid", level2.elementText("GroupNo"));
      System.out.print("GroupName:" + level2.elementText("GroupName"));

		//生产使用
		if(CommonUtil.isEmpty(level2.elementText("GroupName"))){
			map.put("jobname", "");
		}else{
			map.put("jobname", new String(level2.elementText("GroupName").getBytes("iso-8859-1"), "utf-8"));
		}
		//测试使用
		//map.put("jobname", level2.elementText("GroupName"));
		//System.out.print("GroupMold:" + level2.elementText("GroupMold"));
		//生产使用
		if(CommonUtil.isEmpty(level2.elementText("GroupMold"))){
			map.put("GroupMold", 0);
		}else{
			if("预约".equals(new String(level2.elementText("GroupMold").getBytes("iso-8859-1"), "utf-8"))){
				map.put("GroupMold", "1");
			}else{
				map.put("GroupMold", "0");
			}
		}
		//测试使用
		/*if("预约".equals(level2.elementText("GroupMold"))){
			map.put("GroupMold", "1");
		}else{
			map.put("GroupMold", "0");
		}*/

      System.out.print("WaitingNumber:" + level2.elementText("WaitingNumber"));
      map.put("waitcount", level2.elementText("WaitingNumber"));
      map.put("ordertype", "");
      resultList.add(map);
    }

    modelMap.clear();
    modelMap.put("recode", "000000");
    modelMap.put("msg", "成功");
    modelMap.put("result", resultList);
    modelMap.put("workcount", String.valueOf(workcount));
    modelMap.put("lastworkcount", String.valueOf(queueNumber));

    modelMap.put("websitecode", mi201.getWebsiteCode());
    modelMap.put("websitename", mi201.getWebsiteName());
    modelMap.put("distance", "");
    modelMap.put("businesstype", mi201.getBusinessType());
    modelMap.put("tel", mi201.getTel());
    modelMap.put("servicetime", mi201.getServiceTime());
    modelMap.put("windows", mi201.getFreeuse3() + "个");
    modelMap.put("address", mi201.getAddress());

    modelMap.put("tips", "");
    log.info(ERRCODE.LOG.END_BUSIN.getLogText(new String[] { form.getBusinName() }));
    return "";
  }

  @RequestMapping({"/appapi10107.{ext}"})
  public String appApi10107(AppApi10107Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Logger log = LoggerUtil.getLogger();
    form.setBusinName("排队取号");
    log.info(ERRCODE.LOG.START_BUSIN.getLogText(new String[] { form.getBusinName() }));
    log.debug(ERRCODE.DEBUG.SHOW_PARAM.getLogText(new String[] { 
      CommonUtil.getStringParams(form) }));
    ApiUserContext.getInstance();

    if (CommonUtil.isEmpty(form.getCenterId())) {
      log.error(ERRCODE.ERROR.PARAMS_NULL.getLogText(new String[] { "centerId" }));
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.PARAMS_NULL.getValue(), new String[] { "城市中心ID" });
    }
    if (CommonUtil.isEmpty(form.getWebsitecode())) {
      log.error(ERRCODE.ERROR.PARAMS_NULL.getLogText(new String[] { "websitecode" }));
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.PARAMS_NULL.getValue(), new String[] { "网点编号" });
    }
    if (CommonUtil.isEmpty(form.getJobid())) {
      log.error(ERRCODE.ERROR.PARAMS_NULL.getLogText(new String[] { "jobid" }));
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.PARAMS_NULL.getValue(), new String[] { "业务编号" });
    }

    if (CommonUtil.isEmpty(form.getGetMethod())) {
      log.error(ERRCODE.ERROR.PARAMS_NULL.getLogText(new String[] { "getmethod" }));
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.PARAMS_NULL.getValue(), new String[] { "取号方式" });
    }

    Mi201 mi201 = this.appApi101ServiceImpl.appApi10105Select(form.getWebsitecode(), form.getCenterId());
    if (CommonUtil.isEmpty(mi201)) {
      modelMap.clear();
      modelMap.put("recode", "999999");
      modelMap.put("msg", "网点不存在！");
      return "";
    }
    if (this.webApi627Service.webbapi62705(form.getCenterId(), CommonUtil.getDate())) {
      modelMap.clear();
      modelMap.put("recode", "999999");
      modelMap.put("msg", "节假日期间取号业务暂停！");
      return "";
    }
    if (!CommonUtil.isEmpty(mi201.getFreeuse2())) {
      String currentTime = CommonUtil.getTime().substring(0, 5);
      String[] times = mi201.getFreeuse2().split("-");
      if ((currentTime.compareTo(times[0]) < 0) || 
        (currentTime.compareTo(times[1]) > 0)) {
        modelMap.clear();
        modelMap.put("recode", "999999");
        modelMap.put("msg", mi201.getFreeuse2() + "时间段内才可取号！");
        return "";
      }
    }
    request.setAttribute("centerId", form.getCenterId());

    AES aes = new AES(form.getCenterId(), form.getChannel(), form.getAppid(), form.getAppkey());
    HashMap m = new HashMap(request.getParameterMap());
    String sfz = "";
    if (!CommonUtil.isEmpty(m.get("userId"))) {
      String usid = request.getParameter("userId");
      sfz = aes.decrypt(usid);
      form.setUserId(sfz);
    }

    if (form.getChannel().equals("51")) {
        log.info("机器人排队取号！channel=51");
    }else{	    	
	    Mi031 mi031 = this.webApi029ServiceImpl.webapi02907(form, request, response);
	    if (CommonUtil.isEmpty(mi031)) {
	      modelMap.clear();
	      modelMap.put("recode", "999999");
	      modelMap.put("msg", "渠道用户信息不存在！");
	      return "";
	    }
	    Mi029 mi029 = this.webApi029ServiceImpl.webapi02908(mi031.getPersonalid());
	    
	    if (CommonUtil.isEmpty(mi029)) {
	      modelMap.clear();
	      modelMap.put("recode", "999999");
	      modelMap.put("msg", "客户信息不存在！");
	      return "";
	    }
	    
	    //判断有没有进行过预约
    if ((!CommonUtil.isEmpty(form.getOrdertype())) && 
      ("1".equals(form.getOrdertype()))) {
      AppApi30301Form form303 = new AppApi30301Form();
      log.info("form.getCenterId()="+form.getCenterId());
      log.info("mi029.getCertinum()="+mi029.getCertinum());
      log.info("form.getWebsitecode()="+form.getWebsitecode());
      form303.setCenterid(form.getCenterId());
      form303.setCenterId(form.getCenterId());
      form303.setBodyCardNumber(mi029.getCertinum());
      form303.setWebsiteCode(form.getWebsitecode());
      JSONObject result = this.appApi303Service.appApi30314(form303);
      if ((!CommonUtil.isEmpty(result.getString("recode"))) && 
        (!"000000".equals(result.getString("recode")))) {
        modelMap.clear();
        modelMap.put("recode", result.getString("recode"));
        modelMap.put("msg", result.getString("msg"));
        return "";
      }
    }
	form.setIdcardNumber(mi029.getCertinum());
  }

	   /* Mi031 mi031 = this.webApi029ServiceImpl.webapi02907(form, request, response);
	    if (CommonUtil.isEmpty(mi031)) {
	      modelMap.clear();
	      modelMap.put("recode", "999999");
	      modelMap.put("msg", "渠道用户信息不存在！");
	      return "";
	    }
	    Mi029 mi029 = this.webApi029ServiceImpl.webapi02908(mi031.getPersonalid());
	    
	    if (CommonUtil.isEmpty(mi029)) {
	      modelMap.clear();
	      modelMap.put("recode", "999999");
	      modelMap.put("msg", "客户信息不存在！");
	      return "";
	    }

	    //判断有没有进行过预约
	if ((!CommonUtil.isEmpty(form.getOrdertype())) && 
	  ("1".equals(form.getOrdertype()))) {
	  AppApi30301Form form303 = new AppApi30301Form();
	  log.info("form.getCenterId()="+form.getCenterId());
	  log.info("mi029.getCertinum()="+mi029.getCertinum());
	  log.info("form.getWebsitecode()="+form.getWebsitecode());
	  form303.setCenterid(form.getCenterId());
	  form303.setCenterId(form.getCenterId());
	  form303.setBodyCardNumber(mi029.getCertinum());
	  form303.setWebsiteCode(form.getWebsitecode());
	  JSONObject result = this.appApi303Service.appApi30314(form303);
	  if ((!CommonUtil.isEmpty(result.getString("recode"))) && 
	    (!"000000".equals(result.getString("recode")))) {
	    modelMap.clear();
	    modelMap.put("recode", result.getString("recode"));
	    modelMap.put("msg", result.getString("msg"));
	    return "";
	  }
      //log.info("form.getIdcardNumber()"+form.getIdcardNumber());
      //form.setIdcardNumber(mi029.getCertinum());
      //log.info("form.getIdcardNumber()"+form.getIdcardNumber());
	}*/

    String url = PropertiesReader.getProperty("properties.properties", "queue_url").trim();

    OMElement omElement = WebService.WebServiceClient(url,
      "http://www.hzhnkj.com.cn/", "getTicket", 
      "ctrllerid,groupno,sfzh,Tickitmode,status", 
      form.getWebsitecode() + "&" + form.getJobid() + "&" +
      form.getIdcardNumber() + "&" + form.getOrdertype() + "& ");

    log.info("response:" + omElement);
    int last = omElement.toString().lastIndexOf("</status>");
    System.out.println("last:" + last);
    System.out.println("status:" + omElement.toString().substring(last - 1, last));
    if (!"1".equals(omElement.toString().substring(last - 1, last))) {
      int start = omElement.toString().lastIndexOf("<status>");
      String result = omElement.toString().substring(start + 8, last);
      modelMap.clear();
      log.info("排队取号失败！");
      log.info("网点编号：" + form.getWebsitecode());
      log.info("业务编号：" + form.getJobid());
      log.info("身份证号：" + form.getIdcardNumber());
      log.info("取号方式（1预约，0普通）：" + form.getOrdertype());
      modelMap.put("recode", "999999");
      if (CommonUtil.isEmpty(result))
        modelMap.put("msg", "排队取号失败！");
      else {
        modelMap.put("msg", result);
      }
      return "";
    }
    OMElement elementReturn = omElement.getFirstElement();
    System.out.println("response1:" + elementReturn.getText());

    Document document = null;
    SAXReader reader = new SAXReader();
	//生产使用
	ByteArrayInputStream inputStream = new ByteArrayInputStream(elementReturn.getText().getBytes("UTF-8"));
	//测试使用
	//ByteArrayInputStream inputStream = new ByteArrayInputStream(elementReturn.getText().getBytes());

    InputStreamReader ir = new InputStreamReader(inputStream);

    document = reader.read(ir);
    Element rootElement = document.getRootElement();
	List<Element> listElement = rootElement.elements();
    HashMap map = new HashMap();
    for (Element level2 : listElement) {
      System.out.print("Ctrllerid:" + level2.elementText("Ctrllerid"));
      map.put("websitecode", level2.elementText("Ctrllerid"));
      map.put("websitename", mi201.getWebsiteName());
      System.out.print("Ctrllerid:" + level2.elementText("groupno"));
      map.put("jobid", level2.elementText("groupno"));
      System.out.print("Ctrllerid:" + level2.elementText("groupname"));

      if (CommonUtil.isEmpty(level2.elementText("groupname")))
        map.put("jobname", "");
      else {
        map.put("jobname", new String(level2.elementText("groupname").getBytes("iso-8859-1"), "utf-8"));
      }

      System.out.print("Ctrllerid:" + level2.elementText("qno"));
      map.put("ticketNo", level2.elementText("qno"));
      System.out.print("Ctrllerid:" + level2.elementText("datetime"));
      map.put("getTime", level2.elementText("datetime"));
      System.out.print("Ctrllerid:" + level2.elementText("WaitingNumber"));
      map.put("waitcount", level2.elementText("WaitingNumber"));
    }

    modelMap.clear();
    modelMap.put("recode", "000000");
    modelMap.put("msg", "成功");
    modelMap.put("result", map);
    log.info(ERRCODE.LOG.END_BUSIN.getLogText(new String[] { form.getBusinName() }));
    return "";
  }

  @RequestMapping({"/appapi10108.{ext}"})
  public String appApi10108(AppApi10108Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, String centerid, String content, String destPath, String needCompress)
    throws Exception
  {
    Logger log = LoggerUtil.getLogger();
    form.setBusinName("获取我的排号记录");
    log.info(ERRCODE.LOG.START_BUSIN.getLogText(new String[] { form.getBusinName() }));
    log.debug(ERRCODE.DEBUG.SHOW_PARAM.getLogText(new String[] { 
      CommonUtil.getStringParams(form) }));
    ApiUserContext.getInstance();
    if (CommonUtil.isEmpty(form.getCenterId())) {
      log.error(ERRCODE.ERROR.PARAMS_NULL.getLogText(new String[] { "centerId" }));
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.PARAMS_NULL.getValue(), new String[] { "城市中心ID" });
    }

    request.setAttribute("centerId", form.getCenterId());

    AES aes = new AES(form.getCenterId(), form.getChannel(), form.getAppid(), form.getAppkey());
    HashMap m = new HashMap(request.getParameterMap());
    String sfz = "";
    if (!CommonUtil.isEmpty(m.get("userId"))) {
      String usid = request.getParameter("userId");
      sfz = aes.decrypt(usid);
      form.setUserId(sfz);
    }

    Mi031 mi031 = this.webApi029ServiceImpl.webapi02907(form, request, response);
    if (CommonUtil.isEmpty(mi031)) {
      modelMap.clear();
      modelMap.put("recode", "999999");
      modelMap.put("msg", "渠道用户信息不存在！");
      return "";
    }
    Mi029 mi029 = this.webApi029ServiceImpl.webapi02908(mi031.getPersonalid());
    if (CommonUtil.isEmpty(mi029)) {
      modelMap.clear();
      modelMap.put("recode", "999999");
      modelMap.put("msg", "客户信息不存在！");
      return "";
    }
    form.setIdcardNumber(mi029.getCertinum());

    System.out.println("appapi10108获取我的排号记录");

    String url = PropertiesReader.getProperty("properties.properties", "queue_url").trim();
    OMElement omElement = WebService.WebServiceClient(url, 
      "http://www.hzhnkj.com.cn/", "getMyQnoInfo2", 
      "sfzh,status", form.getIdcardNumber() + "& ");

    System.out.println("response:" + omElement);
    int last = omElement.toString().lastIndexOf("</status>");
    System.out.println("last:" + last);
    System.out.println("status:" + omElement.toString().substring(last - 1, last));
    if (!"1".equals(omElement.toString().substring(last - 1, last))) {
      modelMap.clear();
      log.info("获取个人排队信息失败！身份证号：" + form.getIdcardNumber());
      modelMap.put("recode", "999999");
      modelMap.put("msg", "获取个人排队信息失败！");
      return "";
    }

    OMElement elementReturn = omElement.getFirstElement();
    System.out.println("response1:" + elementReturn.getText());

    Document document = null;
    SAXReader reader = new SAXReader();

    //生产使用
    	ByteArrayInputStream inputStream = new ByteArrayInputStream(elementReturn.getText().getBytes("UTF-8"));
	//测试使用
	//ByteArrayInputStream inputStream = new ByteArrayInputStream(elementReturn.getText().getBytes());

    InputStreamReader ir = new InputStreamReader(inputStream);

    document = reader.read(ir);
    Element rootElement = document.getRootElement();
	List<Element> listElement = rootElement.elements();
    HashMap map = new HashMap();
    List resultList = new ArrayList();
    for (Element level2 : listElement) {
      List TitleInfoBeanList = new ArrayList();
      System.out.print("ctrllerid:" + level2.elementText("Ctrllerid"));
      map.put("websitecode", level2.elementText("Ctrllerid"));
      System.out.print("ctrllername:" + level2.elementText("Ctrllername"));
      log.info("Ctrllername============"+level2.elementText("Ctrllername"));
      //log.info("Ctrllername111============"+new String(level2.elementText("Ctrllername").getBytes("iso-8859-1"),"utf-8"));
      if (CommonUtil.isEmpty(level2.elementText("Ctrllername")))
        map.put("websitename", "");
      else {
        map.put("websitename", new String(level2.elementText("Ctrllername").getBytes("iso-8859-1"), "utf-8"));
        //map.put("websitename", level2.elementText("Ctrllername"));
      }

      System.out.print("qno:" + level2.elementText("Qno"));
      map.put("ticketNo", level2.elementText("Qno"));
      System.out.print("regdatetime:" + level2.elementText("Regdatetime"));
      map.put("getTime", level2.elementText("Regdatetime"));
      System.out.print("groupname:" + level2.elementText("GroupName"));

      
		//生产使用
		if(CommonUtil.isEmpty(level2.elementText("groupname"))){
			map.put("jobname", "");
		}else{
			map.put("jobname", new String(level2.elementText("groupname").getBytes("iso-8859-1"), "utf-8"));
		}
		//测试使用
		//map.put("jobname", level2.elementText("groupname"));

      System.out.print("Roomno:" + level2.elementText("Roomno"));
      map.put("windowNo", level2.elementText("Roomno"));
      System.out.print("Curedatetime:" + level2.elementText("Curedatetime"));
      map.put("getTime1", level2.elementText("Curedatetime"));
      map.put("getTime2", level2.elementText(""));
      System.out.print("Tickitmode:" + level2.elementText("Tickitmode"));
      map.put("gettype", level2.elementText("Tickitmode"));
      map.put("status", level2.elementText("Queuestate"));
      TitleInfoBeanList = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi10108.list", map);
      resultList.add(TitleInfoBeanList);
    }

    modelMap.clear();
    modelMap.put("recode", "000000");
    modelMap.put("msg", "成功");
    modelMap.put("result", resultList);
    log.info(ERRCODE.LOG.END_BUSIN.getLogText(new String[] { form.getBusinName() }));
    return "";
  }

  @RequestMapping({"/appapi10109.{ext}"})
  public String appApi10109(AppApi10109Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Logger log = LoggerUtil.getLogger();
    form.setBusinName("获取当前排队号码状态");
    log.info(ERRCODE.LOG.START_BUSIN.getLogText(new String[] { form.getBusinName() }));
    log.debug(ERRCODE.DEBUG.SHOW_PARAM.getLogText(new String[] { 
      CommonUtil.getStringParams(form) }));
    ApiUserContext.getInstance();
    if (CommonUtil.isEmpty(form.getCenterId())) {
      log.error(ERRCODE.ERROR.PARAMS_NULL.getLogText(new String[] { "centerId" }));
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.PARAMS_NULL.getValue(), new String[] { "城市中心ID" });
    }
    if (CommonUtil.isEmpty(form.getWebsitecode())) {
      log.error(ERRCODE.ERROR.PARAMS_NULL.getLogText(new String[] { "websitecode" }));
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.PARAMS_NULL.getValue(), new String[] { "网点编号" });
    }
    if (CommonUtil.isEmpty(form.getTicketno())) {
      log.error(ERRCODE.ERROR.PARAMS_NULL.getLogText(new String[] { "ticketno" }));
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.PARAMS_NULL.getValue(), new String[] { "排队号码" });
    }

    request.setAttribute("centerId", form.getCenterId());

    AES aes = new AES(form.getCenterId(), form.getChannel(), form.getAppid(), form.getAppkey());
    HttpServletRequest request1 = request;
    HashMap m = new HashMap(request.getParameterMap());
    String sfz = "";
    if (!CommonUtil.isEmpty(m.get("userId"))) {
      String usid = request.getParameter("userId");
      sfz = aes.decrypt(usid);
      form.setUserId(sfz);
    }

    Mi031 mi031 = this.webApi029ServiceImpl.webapi02907(form, request, response);
    if (CommonUtil.isEmpty(mi031)) {
      modelMap.clear();
      modelMap.put("recode", "999999");
      modelMap.put("msg", "渠道用户信息不存在！");
      return "";
    }
    Mi029 mi029 = this.webApi029ServiceImpl.webapi02908(mi031.getPersonalid());
    if (CommonUtil.isEmpty(mi029)) {
      modelMap.clear();
      modelMap.put("recode", "999999");
      modelMap.put("msg", "客户信息不存在！");
      return "";
    }

    form.setIdcardNumber(mi029.getCertinum());

    String url = PropertiesReader.getProperty("properties.properties", "queue_url").trim();
    OMElement omElement = WebService.WebServiceClient(url, 
      "http://www.hzhnkj.com.cn/", "getLocalQnoInfo", 
      "ctrllerid,qno,sfzh,status", form.getWebsitecode() + "&" + 
      form.getTicketno() + "&" + form.getIdcardNumber() + "& ");

    System.out.println("response:" + omElement);
    int last = omElement.toString().lastIndexOf("</status>");
    System.out.println("last:" + last);
    System.out.println("status:" + omElement.toString().substring(last - 1, last));
    if (!"1".equals(omElement.toString().substring(last - 1, last))) {
      modelMap.clear();
      log.info("获取排队进度信息失败！");
      log.info("身份证号：" + form.getIdcardNumber());
      log.info("网点编号：" + form.getWebsitecode());
      log.info("排队号码：" + form.getTicketno());
      modelMap.put("recode", "999999");
      modelMap.put("msg", "获取排队进度信息失败！");
      return "";
    }

    OMElement elementReturn = omElement.getFirstElement();
    System.out.println("response1:" + elementReturn.getText());

    Document document = null;
    SAXReader reader = new SAXReader();
	//生产使用
	ByteArrayInputStream inputStream = new ByteArrayInputStream(elementReturn.getText().getBytes("UTF-8"));
	//测试使用
	//ByteArrayInputStream inputStream = new ByteArrayInputStream(elementReturn.getText().getBytes());

    InputStreamReader ir = new InputStreamReader(inputStream);

    document = reader.read(ir);
    Element rootElement = document.getRootElement();
	List<Element> listElement = rootElement.elements();
    HashMap map = new HashMap();
    for (Element level2 : listElement) {
      System.out.print("Ctrllerid:" + level2.elementText("Ctrllerid"));
      map.put("websitecode", level2.elementText("Ctrllerid"));
      System.out.print("Ctrllername:" + level2.elementText("Ctrllername"));
		//生产使用
		if(CommonUtil.isEmpty(level2.elementText("Ctrllername"))){
			map.put("websitename", "");
		}else{
			map.put("websitename", new String(level2.elementText("Ctrllername").getBytes("iso-8859-1"), "utf-8"));
		}
		//测试使用
      //map.put("websitename", level2.elementText("Ctrllername"));

      System.out.print("Groupno:" + level2.elementText("Groupno"));
      map.put("jobid", level2.elementText("Groupno"));
      System.out.print("Groupname:" + level2.elementText("Groupname"));

		//生产使用
		if(CommonUtil.isEmpty(level2.elementText("GroupName"))){
			map.put("jobname", "");
		}else{
			map.put("jobname", new String(level2.elementText("GroupName").getBytes("iso-8859-1"), "utf-8"));
		}
		//测试使用
		//map.put("jobname", level2.elementText("GroupName"));

      map.put("ticketNo", form.getTicketno());
      System.out.print("DateTime:" + level2.elementText("DateTime"));
      map.put("getTime", level2.elementText("DateTime"));
      System.out.print("Queuestate:" + level2.elementText("Queuestate"));
      map.put("status", level2.elementText("Queuestate"));
      System.out.print("WaitingNumber:" + level2.elementText("WaitingNumber"));
      map.put("waitcount", level2.elementText("WaitingNumber"));
    }

    modelMap.clear();
    modelMap.put("recode", "000000");
    modelMap.put("msg", "成功");
    modelMap.put("result", map);
    log.info(ERRCODE.LOG.END_BUSIN.getLogText(new String[] { form.getBusinName() }));
    return "";
  }

  @RequestMapping({"/appapi10110.{ext}"})
  public String appApi10110(AppApi10101Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Logger log = LoggerUtil.getLogger();
    form.setBusinName("获取网点排队人数列表");
    System.out.println("网点查询坐标positionX：" + form.getPositionX() + "   positionY：" + form.getPositionY());
    log.info(ERRCODE.LOG.START_BUSIN.getLogText(new String[] { form.getBusinName() }));
    log.debug(ERRCODE.DEBUG.SHOW_PARAM.getLogText(new String[] { 
      CommonUtil.getStringParams(form) }));

    if ((CommonUtil.isEmpty(form.getPositionX())) || (Double.parseDouble(form.getPositionX()) == 0.0D) || (CommonUtil.isEmpty(form.getPositionY())) || (Double.parseDouble(form.getPositionY()) == 0.0D)) {
      log.error(ERRCODE.ERROR.PARAMS_NULL.getLogText(new String[] { "PositionX,PositionY" }));
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.PARAMS_NULL.getValue(), new String[] { "地理信息" });
    }

    List list = this.appApi101ServiceImpl.appApi10102Select(form);
    if ((list.isEmpty()) || (list.size() == 0)) {
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.NO_DATA.getValue(), new String[] { "获取网点排队人数列表" });
    }
    List resultList = new ArrayList();
    for (int i = 0; i < list.size(); i++) {
      List TitleInfoBeanList = new ArrayList();
      Mi201 mi201 = (Mi201)list.get(i);
      HashMap map = new HashMap();
      map.put("websitecode", mi201.getWebsiteCode());
      map.put("websitename", mi201.getWebsiteName());
      map.put("distance", mi201.getDistance() + "km");
      map.put("businesstype", mi201.getBusinessType());
      map.put("tel", mi201.getTel());
      map.put("servicetime", mi201.getServiceTime());
      map.put("windows", mi201.getFreeuse3() + "个");
      map.put("address", mi201.getAddress());

      String url = PropertiesReader.getProperty("properties.properties", "queue_url").trim();
      OMElement omElement = WebService.WebServiceClient(url, 
        "http://www.hzhnkj.com.cn/", "getGroupWaitting2", 
        "ctrllerid,status", mi201.getWebsiteCode() + "& ");
//      OMElement omElement = null;
      System.out.println("response:" + omElement);
      int last = omElement.toString().lastIndexOf("</status>");
      System.out.println("last:" + last);
      System.out.println("status:" + omElement.toString().substring(last - 1, last));

      if (!"1".equals(omElement.toString().substring(last - 1, last))) {
        log.info("获取网点排号人数列表失败！网点编码：" + mi201.getWebsiteCode());
        map.put("waitcount", "");
        TitleInfoBeanList = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi10101.list", map);
        resultList.add(TitleInfoBeanList);
      }
      else
      {
        OMElement elementReturn = omElement.getFirstElement();
        System.out.println("response1:" + elementReturn.getText());

        Document document = null;
        SAXReader reader = new SAXReader();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(elementReturn.getText().getBytes());
        InputStreamReader ir = new InputStreamReader(inputStream);

        document = reader.read(ir);
        Element rootElement = document.getRootElement();
		List<Element> listElement = rootElement.elements();
        int waitingNumber = 0;
        for (Element level2 : listElement) {
          System.out.print("WaitingNumber:" + level2.elementText("WaitingNumber"));
          waitingNumber += (level2.elementText("WaitingNumber") == "" ? 0 : Integer.valueOf(level2.elementText("WaitingNumber")).intValue());
        }
        map.put("waitcount", String.valueOf(waitingNumber));
        TitleInfoBeanList = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi10101.list", map);
        resultList.add(TitleInfoBeanList);
      }
    }
    modelMap.clear();
    modelMap.put("recode", "000000");
    modelMap.put("msg", "成功");
    modelMap.put("result", resultList);
    log.info(ERRCODE.LOG.END_BUSIN.getLogText(new String[] { form.getBusinName() }));
    return "";
  }

  @RequestMapping({"/appapi10111.{ext}"})
  public String appapi10111(AppApi10101Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Logger log = LoggerUtil.getLogger();
    form.setBusinName("核心数据网点信息查询");
    log.info(ERRCODE.LOG.START_BUSIN.getLogText(new String[] { form.getBusinName() }));
    log.debug(ERRCODE.DEBUG.SHOW_PARAM.getLogText(new String[] { 
      CommonUtil.getStringParams(form) }));
    if (CommonUtil.isEmpty(form.getCenterId())) {
      log.error(ERRCODE.ERROR.PARAMS_NULL.getLogText(new String[] { "centerId" }));
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.PARAMS_NULL.getValue(), new String[] { "城市中心ID" });
    }
    request.setAttribute("centerId", form.getCenterId());

    HttpServletRequest request1 = request;
    HashMap m = new HashMap(request.getParameterMap());
    System.out.println("appapi00802=====form.getSelectValue()==" + form.getSelectValue());
    System.out.println("appapi00802=====form.getSelectValue()--UTF-8==" + new String(form.getSelectValue().getBytes("iso8859-1"), "UTF-8"));

    ParameterRequestWrapper wrapRequest = new ParameterRequestWrapper(request1, m);
    this.msgSendApi001Service.send(wrapRequest, response);
    log.info(ERRCODE.LOG.END_BUSIN.getLogText(new String[] { form.getBusinName() }));
    return "";
  }

  @RequestMapping({"/appapi10112.{ext}"})
  public String appapi10112(AppApi10101Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Logger log = LoggerUtil.getLogger();
    form.setBusinName("银行网点排队信息查询");
    log.info(ERRCODE.LOG.START_BUSIN.getLogText(new String[] { form.getBusinName() }));
    log.debug(ERRCODE.DEBUG.SHOW_PARAM.getLogText(new String[] { 
      CommonUtil.getStringParams(form) }));
    if (CommonUtil.isEmpty(form.getCenterId())) {
      log.error(ERRCODE.ERROR.PARAMS_NULL.getLogText(new String[] { "centerId" }));
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.PARAMS_NULL.getValue(), new String[] { "城市中心ID" });
    }
    request.setAttribute("centerId", form.getCenterId());

    if (!"20".equals(request.getParameter("channel"))) {
      HttpServletRequest request1 = request;
      HashMap m = new HashMap(request.getParameterMap());

      ParameterRequestWrapper wrapRequest = new ParameterRequestWrapper(request1, m);
      this.msgSendApi001Service.send(wrapRequest, response);
    } else {
      this.msgSendApi001Service.send(request, response);
    }
    log.info(ERRCODE.LOG.END_BUSIN.getLogText(new String[] { form.getBusinName() }));
    return "";
  }

  @RequestMapping({"/appapi10113.{ext}"})
  public String appApi10113(AppApi10101Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Logger log = LoggerUtil.getLogger();
    form.setBusinName("网点查询(分页处理)");
    log.info(ERRCODE.LOG.START_BUSIN.getLogText(new String[] { form.getBusinName() }));
    log.debug(ERRCODE.DEBUG.SHOW_PARAM.getLogText(new String[] { CommonUtil.getStringParams(form) }));
    if (CommonUtil.isEmpty(form.getCenterId())) {
      log.error(ERRCODE.ERROR.PARAMS_NULL.getLogText(new String[] { "centerId" }));
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.PARAMS_NULL.getValue(), new String[] { "城市中心ID" });
    }
    List list = this.appApi101ServiceImpl.appApi10101Select(form);
    if ((list.isEmpty()) || (list.size() == 0)) {
      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.NO_DATA.getValue(), new String[] { "网点信息" });
    }
    List resultList = new ArrayList();

    AppApi10113_queryResult queryResult = (AppApi10113_queryResult)this.appApi101ServiceImpl.appApi10101Select(form);

    for (int i = 0; i < list.size(); i++) {
      AppApi10101Result appApi10101Result = new AppApi10101Result();
      List TitleInfoBeanContent = new ArrayList();
      List TitleInfoBeanList = new ArrayList();
      Mi201 mi201 = (Mi201)list.get(i);
      mi201.setTel("tel://" + mi201.getTel());
      mi201.setServiceTime("time://" + mi201.getServiceTime());
      if ((!CommonUtil.isEmpty(mi201.getPositionX())) && (!CommonUtil.isEmpty(mi201.getPositionY()))) {
        mi201.setAddress("map://" + mi201.getAddress());
      }
      AppApi10101Result01 appApi10101Result01 = new AppApi10101Result01();
      appApi10101Result01.setX(mi201.getPositionX() == null ? "" : mi201.getPositionX());
      appApi10101Result01.setY(mi201.getPositionY() == null ? "" : mi201.getPositionY());

      appApi10101Result01.setImg(request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf("/") + 1) + CommonUtil.getDownloadFileUrl(
        "push_website_img", new StringBuilder(String.valueOf(form.getCenterId())).append(File.separator).append(mi201.getImageUrl()).toString(), true));
      appApi10101Result.setMap(appApi10101Result01);
      TitleInfoBeanContent = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi10101.content", mi201);
      appApi10101Result.setContent(TitleInfoBeanContent);
      TitleInfoBeanList = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi10101.list", mi201);
      appApi10101Result.setList(TitleInfoBeanList);
      resultList.add(appApi10101Result);
    }
    modelMap.clear();
    modelMap.put("recode", "000000");
    modelMap.put("msg", "成功");

    modelMap.put("total", queryResult.getTotal());
    modelMap.put("pageSize", queryResult.getPageSize());
    modelMap.put("pageNumber", queryResult.getPageNumber());
    modelMap.put("rows", queryResult.getList201());
    modelMap.put("result", resultList);
    log.info(ERRCODE.LOG.END_BUSIN.getLogText(new String[] { form.getBusinName() }));
    return "";
  }
}