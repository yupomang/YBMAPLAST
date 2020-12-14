package com.yondervision.mi.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi040DAO;
import com.yondervision.mi.dao.CMi051DAO;
import com.yondervision.mi.dao.CMi702DAO;
import com.yondervision.mi.dao.Mi001DAO;
import com.yondervision.mi.dao.Mi122DAO;
import com.yondervision.mi.dao.Mi130DAO;
import com.yondervision.mi.dao.Mi401DAO;
import com.yondervision.mi.dao.Mi404DAO;
import com.yondervision.mi.dao.Mi707DAO;
import com.yondervision.mi.dto.CMi040;
import com.yondervision.mi.dto.CMi401;
import com.yondervision.mi.dto.Mi001;
import com.yondervision.mi.dto.Mi001Example;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi122;
import com.yondervision.mi.dto.Mi122Example;
import com.yondervision.mi.dto.Mi130;
import com.yondervision.mi.dto.Mi130Example;
import com.yondervision.mi.dto.Mi202;
import com.yondervision.mi.dto.Mi401;
import com.yondervision.mi.dto.Mi404;
import com.yondervision.mi.dto.Mi404Example;
import com.yondervision.mi.dto.Mi620;
import com.yondervision.mi.dto.Mi621;
import com.yondervision.mi.dto.Mi702;
import com.yondervision.mi.dto.Mi702Example;
import com.yondervision.mi.dto.Mi704;
import com.yondervision.mi.dto.Mi707;
import com.yondervision.mi.dto.Mi707Example;
import com.yondervision.mi.form.ItemInfo;
import com.yondervision.mi.form.WebApiCommonForm;
import com.yondervision.mi.result.NewspapersTitleInfoBean;
import com.yondervision.mi.result.Page70701QryResultAttr;
import com.yondervision.mi.result.WebApi04004_queryResult;
import com.yondervision.mi.result.WebApi13001Result;
import com.yondervision.mi.service.CodeListApi001Service;
import com.yondervision.mi.service.PtlApi001Service;
import com.yondervision.mi.service.WebApi050Service;
import com.yondervision.mi.service.WebApi405Service;
import com.yondervision.mi.service.WebApi707Service;
import com.yondervision.mi.service.WebApi999Service;
import com.yondervision.mi.util.CommonUtil;

/**
 * 所有WEB页面通过此controller映射，路径为*.html
 * 
 * @author LinXiaolong
 * 
 */
@Controller
public class YDWebSystemController {

	@Autowired
	private CodeListApi001Service codeListApi001Service = null;
	@Autowired
	private WebApi707Service webApi707Service = null;
	@Autowired
	private WebApi999Service webApi999ServiceImpl = null;
	@Autowired
	private WebApi405Service webApi405Service;
	@Autowired
	private WebApi050Service webApi050ServiceImpl;
	@Autowired
	private PtlApi001Service ptlApi001Service;
	@Autowired
	private Mi401DAO mi401Dao;
	@Autowired
	private Mi404DAO mi404Dao;
	@Autowired
	private Mi001DAO mi001Dao;
	@Autowired
	private CMi702DAO cmi702Dao;
	@Autowired
	private Mi130DAO mi130Dao;
	@Autowired
	private Mi122DAO mi122Dao;
	@Autowired
	public Mi707DAO mi707Dao = null;
	@Autowired
	private CMi051DAO cmi051Dao;	
	@Autowired
	private CMi040DAO cmi040DAO;
	
	
	public WebApi050Service getWebApi050ServiceImpl() {
		return webApi050ServiceImpl;
	}

	public void setWebApi050ServiceImpl(WebApi050Service webApi050ServiceImpl) {
		this.webApi050ServiceImpl = webApi050ServiceImpl;
	}

	public PtlApi001Service getPtlApi001Service() {
		return ptlApi001Service;
	}

	public void setPtlApi001Service(PtlApi001Service ptlApi001Service) {
		this.ptlApi001Service = ptlApi001Service;
	}

	public WebApi405Service getWebApi405Service() {
		return webApi405Service;
	}

	public void setWebApi405Service(WebApi405Service webApi405Service) {
		this.webApi405Service = webApi405Service;
	}

	public void setMi707Dao(Mi707DAO mi707Dao) {
		this.mi707Dao = mi707Dao;
	}

	public void setCmi702Dao(CMi702DAO cmi702Dao) {
		this.cmi702Dao = cmi702Dao;
	}

	public void setCodeListApi001Service(
			CodeListApi001Service codeListApi001Service) {
		this.codeListApi001Service = codeListApi001Service;
	}

	public void setWebApi707Service(WebApi707Service webApi707Service) {
		this.webApi707Service = webApi707Service;
	}

	public void setWebApi999ServiceImpl(WebApi999Service webApi999ServiceImpl) {
		this.webApi999ServiceImpl = webApi999ServiceImpl;
	}
	
	public CMi051DAO getCmi051Dao() {
		return cmi051Dao;
	}

	public void setCmi051Dao(CMi051DAO cmi051Dao) {
		this.cmi051Dao = cmi051Dao;
	}

	@RequestMapping("/index.{ext}")
	public String index(ModelMap modelMap) throws Exception {
		return "index";
	}	
	
	@RequestMapping("/error.html}")
	public String error(ModelMap modelMap) throws Exception {
		return "platform/error";
	}

	@RequestMapping("/west.{ext}")
	public String west(ModelMap modelMap) throws Exception {
		return "west";
	}

	@RequestMapping("/north.{ext}")
	public String north(ModelMap modelMap) throws Exception {
		return "north";
	}

	// 登录
	@RequestMapping("/login.{ext}")
	public String login(ModelMap modelMap) throws Exception {
		return "login";
	}

	// 验证码
	@RequestMapping("/vericode.{ext}")
	public String vericode(ModelMap modelMap) throws Exception {
		return "vericode";
	}

	// 主框架
	@RequestMapping("frames.{ext}")
	public String frames(ModelMap modelMap) throws Exception {
		return "platform/frames";
	}

	// 操作成功页面
	@RequestMapping("success.{ext}")
	public String success(ModelMap modelMap) throws Exception {
		return "platform/success";
	}

	// 首页
	@RequestMapping("home.{ext}")
	public String home(ModelMap modelMap) throws Exception {
		return "platform/home";
	}

	// 测试页面
	@RequestMapping("test.{ext}")
	public String test(ModelMap modelMap) throws Exception {
		return "platform/test";
	}

	// 测试页面
	@RequestMapping("json.{ext}")
	public String json(ModelMap modelMap) throws Exception {
		return "platform/json";
	}

	// 测试页面
	@RequestMapping("validata.{ext}")
	public String validata(ModelMap modelMap) throws Exception {
		return "platform/validata";
	}

	// 楼盘信息增加
	@RequestMapping("/page00801.{ext}")
	public String page00801(ModelMap modelMap) throws Exception {
		return "page008/page00801";
	}

	// 楼盘信息增加
	@RequestMapping("/page00804.{ext}")
	public String page00804(ModelMap modelMap) throws Exception {
//		UserContext user = UserContext.getInstance();
//		List<Mi202> list = this.codeListApi001Service.getAreaMessage(user
//				.getCenterid());
//		List<Mi201> listMi201 = this.codeListApi001Service.getMi201(user
//				.getCenterid());
//		modelMap.put("mi202list", list);
//		modelMap.put("mi201list", listMi201);
		return "page008/page00804";
	}
	
	// 楼盘信息增加
		@RequestMapping("/webapi00804GetArea.{ext}")
		public String webapi00804GetArea(ModelMap modelMap) throws Exception {
			UserContext user = UserContext.getInstance();
			List<Mi202> list = this.codeListApi001Service.getAreaMessage(user
					.getCenterid());
			modelMap.put("mi202list", list);
			return "page008/page00804";
		}

	// 利率信息查询
	@RequestMapping("/page00904.{ext}")
	public String page00904(ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		JSONArray jsonarray = codeListApi001Service.getCodeListJson(user
				.getCenterid(), "ratetype");
		List<Mi001> list = codeListApi001Service.getCityMessage();
		modelMap.put("ratetypelist", jsonarray);
		modelMap.put("mi001list", list);
		return "page009/page00904";
	}

	// 网点信息查询
	@RequestMapping("/page10104.{ext}")
	public String page10104(ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		List<Mi202> list = this.codeListApi001Service.getAreaMessage(user
				.getCenterid());
		List<Mi007> list1 = this.codeListApi001Service.getCodeList(user
				.getCenterid(), "websitetype");
		modelMap.put("mi202list", list);
		modelMap.put("mi007list", list1);
		return "page101/page10104";
	}

	/*********************
	 * 2013-10-14 更换新模式************************** //业务咨询项增加
	 * 
	 * @RequestMapping("/page20101.{ext ") public String page20101(ModelMap
	 *                                  modelMap) throws Exception { return
	 *                                  "page201/page20101"; }
	 * 
	 *                                  //业务咨询项删除
	 * @RequestMapping("/page20102.{ext ") public String page20102(ModelMap
	 *                                  modelMap) throws Exception { return
	 *                                  "page201/page20102"; }
	 * 
	 *                                  //业务咨询项修改
	 * @RequestMapping("/page20103.{ext ") public String page20103(ModelMap
	 *                                  modelMap) throws Exception { return
	 *                                  "page201/page20103"; }
	 * 
	 *                                  //业务咨询（子）项查询
	 * @RequestMapping("/page20104.{ext ") public String page20104(ModelMap
	 *                                  modelMap) throws Exception { return
	 *                                  "page201/page20104"; }
	 * 
	 *                                  //业务资询子项增加
	 * @RequestMapping("/page20105.{ext ") public String page20105(ModelMap
	 *                                  modelMap) throws Exception { return
	 *                                  "page201/page20105"; }
	 * 
	 *                                  //业务资询子项修改
	 * @RequestMapping("/page20106.{ext ") public String page20106(ModelMap
	 *                                  modelMap) throws Exception { return
	 *                                  "page201/page20106"; }
	 * 
	 *                                  //业务资询子项删除
	 * @RequestMapping("/page20107.{ext ") public String page20107(ModelMap
	 *                                  modelMap) throws Exception { return
	 *                                  "page201/page20107"; }
	 * 
	 *                                  //业务资询公共条件项目添加
	 * @RequestMapping("/page20108.{ext ") public String page20108(ModelMap
	 *                                  modelMap) throws Exception { return
	 *                                  "page201/page20108"; }
	 * 
	 * 
	 *                                  //业务资询公共条件查询
	 * @RequestMapping("/page20111.{ext ") public String page20111(ModelMap
	 *                                  modelMap) throws Exception { return
	 *                                  "page201/page20111"; }
	 * 
	 *                                  //业务资询公共条件分组添加
	 * @RequestMapping("/page20112.{ext ") public String page20112(ModelMap
	 *                                  modelMap) throws Exception { return
	 *                                  "page201/page20112"; }
	 * 
	 *                                  //业务资询公共条件添加
	 * @RequestMapping("/page20115.{ext ") public String page20115(ModelMap
	 *                                  modelMap) throws Exception { return
	 *                                  "page201/page20115"; }
	 * 
	 *                                  //业务资询向导步骤添加/修改
	 * @RequestMapping("/page20118.{ext ") public String page20118(ModelMap
	 *                                  modelMap) throws Exception { return
	 *                                  "page201/page20118"; }
	 * 
	 *                                  //业务资询向导信息查询
	 * @RequestMapping("/page20121.{ext ") public String page20121(ModelMap
	 *                                  modelMap) throws Exception { return
	 *                                  "page201/page20121"; }
	 * 
	 *                                  //业务资询向导信息添加/修改
	 * @RequestMapping("/page20122.{ext ") public String page20122(ModelMap
	 *                                  modelMap) throws Exception { return
	 *                                  "page201/page20122"; } 2013-10-14 end
	 **********************************/

	// 业务资询配置(新)
	@RequestMapping("/page20101.{ext}")
	public String page20101(ModelMap modelMap) throws Exception {
		return "page201/page20101";
	}

	// 业务资询配置(新)
	@RequestMapping("/page20123.{ext}")
	public String page20123(ModelMap modelMap) throws Exception {
		return "page201/page20123";
	}

	// 公共短消息推送
	@RequestMapping("/page30201.html")
	public String page30201(WebApiCommonForm form, ModelMap modelMap)
			throws Exception {
		
		return "page302/page30201";
	}
	
	@RequestMapping("/page30201GetPara.json")
	public String page30201GetPara(WebApiCommonForm form, ModelMap modelMap){
//		String pusMessageType = this.codeListApi001Service.getCodeListJson(
//				form.getCenterId(), "psh_message_type").toString();
//		String themeType = this.codeListApi001Service.getCodeListJson(
//				form.getCenterId(), "message_topic_type").toString();
		//取主题类型表信息START
//		StringBuffer themeType = new StringBuffer();
		Mi122Example example122 = new Mi122Example();
		example122.createCriteria().andCenteridEqualTo(form.getCenterId()).andValidflagEqualTo("1");
		example122.setOrderByClause("num asc");
		List<Mi122> list122 = mi122Dao.selectByExample(example122);
		List<Mi007> list007 = this.codeListApi001Service.getCodeList(form
				.getCenterId(), "message_topic_type");		
//		themeType.append("");
		for(int i=0;i<list122.size();i++){
			for(int j=0;j<list007.size();j++){
				if(list122.get(i).getMessageTopicType().equals(list007.get(j).getItemid())){
					list122.get(i).setFreeuse1(list007.get(j).getItemval());
					break;
				}
			}			
		}
//		String downloadPath = CommonUtil.getDownloadFileUrl("push_msg_img",
//				form.getCenterId() + File.separator + "mi401Img", true);
//		String tsmsgtype = this.codeListApi001Service.getCodeListJson(
//				form.getCenterId(), "tsmsgtype").toString();
//		modelMap.put("pusMessageType", pusMessageType);
		modelMap.put("themeType", list122);
//		modelMap.put("downloadPath", downloadPath);
//		modelMap.put("tsmsgtype", tsmsgtype);
		return "page302/page30201"; 
	}
	
	//消息主题类型配置
	@RequestMapping("/page30205.{ext}")
	public String page30205(WebApiCommonForm form,ModelMap modelMap) throws Exception {
//		UserContext user = UserContext.getInstance();
//		List<Mi007> list1 = this.codeListApi001Service.getCodeList(user
//				.getCenterid(), "message_topic_type");
//		List<Mi001> list = codeListApi001Service.getCityMessage();
//		String topic_type = this.codeListApi001Service.getCodeListJson(
//				form.getCenterId(), "message_topic_type").toString();
//		modelMap.put("mi001list", list);
//		modelMap.put("message_topic_type", list1);
//		modelMap.put("topictype", topic_type);
		return "page302/page30205";
	}
	
	//消息主题类型配置参数
	@RequestMapping("/page30205GetParam.json")
	public String page30205GetParam(WebApiCommonForm form,ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		List<Mi007> list1 = this.codeListApi001Service.getCodeList(user
				.getCenterid(), "message_topic_type");
		List<Mi001> list = codeListApi001Service.getCityMessage();
		String topic_type = this.codeListApi001Service.getCodeListJson(
				form.getCenterId(), "message_topic_type").toString();
		modelMap.put("mi001list", list);
		modelMap.put("message_topic_type", list1);
		modelMap.put("topictype", topic_type);
		return "";
	}
	
	//消息主题类型配置
	@RequestMapping("/page30206.{ext}")
	public String page30206(WebApiCommonForm form,ModelMap modelMap) throws Exception {
		List<Mi001> list = codeListApi001Service.getCityMessage();	
		modelMap.put("mi001list", list);
		return "page302/page30206";
	}

	// 公共短消息推送（展示消息明细）
	@RequestMapping("/page3020101.html")
	public String page3020101(CMi401 form,
			ModelMap modelMap) throws Exception {
		List<Mi007> list1 = this.codeListApi001Service.getCodeList(form.getCenterid(), "message_topic_type");
		if("01".equals(form.getTsmsgtype())){
			Mi401 mi401 = mi401Dao.selectByPrimaryKey(form.getCommsgid());
			modelMap.put("title", mi401.getTitle());
			modelMap.put("detail", mi401.getDetail());
			modelMap.put("tsmsg", mi401.getTsmsg());
			modelMap.put("tsmsgtype", mi401.getTsmsgtype());
			modelMap.put("param1", mi401.getParam1());
			modelMap.put("theme", "");
			for(int i=0;i<list1.size();i++){
				if(list1.get(i).getItemid().equals(mi401.getTheme())){
					modelMap.put("theme", list1.get(i).getItemval());
					break;
				}
			}
			
			int defWidth = 200;
			if (!CommonUtil.isEmpty(mi401.getParam2())) {
				List<String> listImgUrl = new ArrayList<String>();
				String[] pImgs = mi401.getParam2().split(",");
				for (int i = 0; i < pImgs.length; i++) {
					String img = pImgs[i];
					String imgUrl = CommonUtil.getDownloadFileUrl("push_msg_img",
							form.getCenterid() + File.separator + img, true);
					listImgUrl.add(imgUrl);
				}
				modelMap.put("listImg", listImgUrl);
				modelMap.put("imgWidth",
						800 / listImgUrl.size() - 3 > defWidth ? defWidth
								: 800 / listImgUrl.size() - 3);
			}
		}else if("02".equals(form.getTsmsgtype())){
			Mi401 mi401 = mi401Dao.selectByPrimaryKey(form.getCommsgid());
			modelMap.put("title", mi401.getTitle());
			modelMap.put("detail", mi401.getDetail());
			modelMap.put("tsmsg", mi401.getTsmsg());
			modelMap.put("tsmsgtype", mi401.getTsmsgtype());
			modelMap.put("param1", mi401.getParam1());
			
			modelMap.put("theme", "");
			for(int i=0;i<list1.size();i++){
				if(list1.get(i).getItemid().equals(mi401.getTheme())){
					modelMap.put("theme", list1.get(i).getItemval());
					break;
				}
			}
			
			int defWidth = 200;
			if (!CommonUtil.isEmpty(mi401.getParam2())) {
				List<String> listImgUrl = new ArrayList<String>();
				String[] pImgs = mi401.getParam2().split(",");
				for (int i = 0; i < pImgs.length; i++) {
					String img = pImgs[i];
					String imgUrl = CommonUtil.getDownloadFileUrl("push_msg_img",
							form.getCenterid() + File.separator + img, true);
					listImgUrl.add(imgUrl);
				}
				modelMap.put("listImg", listImgUrl);
				modelMap.put("imgWidth",
						800 / listImgUrl.size() - 3 > defWidth ? defWidth
								: 800 / listImgUrl.size() - 3);
			}
		}else if("03".equals(form.getTsmsgtype())){
			Mi404Example mi404Example = new Mi404Example();
			mi404Example.setOrderByClause("abs(msnum) asc,datecreated desc");
			Mi404Example.Criteria mCriteria = mi404Example.createCriteria()
					.andCenteridEqualTo(form.getCenterid());
			mCriteria.andCommsgidEqualTo(form.getCommsgid());			
			mCriteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			List<Mi404> list = mi404Dao.selectByExampleWithBLOBs(mi404Example);
			
			for(int i=0;i<list.size();i++){
				Mi404 mi404 = list.get(i);
				
				mi404.setFreeuse1("");
				for(int j=0;j<list1.size();j++){
					if(list1.get(j).getItemid().equals(mi404.getTheme())){
						mi404.setFreeuse1(list1.get(j).getItemval());
						break;
					}
				}
				
				if (!CommonUtil.isEmpty(mi404.getParam2())) {
					StringBuffer listImgUrl = new StringBuffer();
					String[] pImgs = mi404.getParam2().split(",");
					for (int j = 0; j < pImgs.length; j++) {
						String img = pImgs[j];
						String imgUrl = CommonUtil.getDownloadFileUrl("push_msg_img",
								form.getCenterid() + File.separator + img, true);
						if(j==0){
							listImgUrl.append(imgUrl);
						}else{
							listImgUrl.append(","+imgUrl);
						}
						
					}
					list.get(i).setParam2(listImgUrl.toString());					
				}
			}
			
			
			
			
			
			modelMap.put("list", list);
			modelMap.put("tsmsgtype", form.getTsmsgtype());
		}
		
		return "page302/page3020101";
	}

	// 公共短消息推送（添加推送消息）
	@RequestMapping("/page3020102.html")
	public String page3020102(WebApiCommonForm form, ModelMap modelMap) throws Exception {
		Mi001 mi001 = mi001Dao.selectByPrimaryKey(form.getCenterId());
		modelMap.put("custsvctel", mi001.getCustsvctel());
		return "page302/page3020102";
	}
	
	// 公共短消息推送（添加推送消息）
	@RequestMapping("/page3020103.html")
	public String page3020103(WebApiCommonForm form, ModelMap modelMap) throws Exception {
		Mi001 mi001 = mi001Dao.selectByPrimaryKey(form.getCenterId());
		modelMap.put("custsvctel", mi001.getCustsvctel());
		return "page302/page3020103";
	}
	
	// 公共短消息推送（添加推送消息）
	@RequestMapping("/page3020104.html")
	public String page3020104(WebApiCommonForm form, ModelMap modelMap) throws Exception {
		Mi001 mi001 = mi001Dao.selectByPrimaryKey(form.getCenterId());
		modelMap.put("custsvctel", mi001.getCustsvctel());
		return "page302/page3020104";
	}

	// 公共短消息推送
	@RequestMapping("/page3020105.html")
	public String page3020105(WebApiCommonForm form, ModelMap modelMap)
			throws Exception {
		String pusMessageType = this.codeListApi001Service.getCodeListJson(
				form.getCenterId(), "psh_message_type").toString();
		String downloadPath = CommonUtil.getDownloadFileUrl("push_msg_img",
				form.getCenterId() + File.separator + "mi401Img", true);
		String tsmsgtype = this.codeListApi001Service.getCodeListJson(
				form.getCenterId(), "tsmsgtype").toString();
		modelMap.put("pusMessageType", pusMessageType);
		modelMap.put("downloadPath", downloadPath);
		modelMap.put("tsmsgtype", tsmsgtype);
		Mi001 mi001 = mi001Dao.selectByPrimaryKey(form.getCenterId());
		modelMap.put("custsvctel", mi001.getCustsvctel());
		return "page302/page3020105";
	}
	
	
	
	// 短消息报盘推送
	@RequestMapping("/page30202.{ext}")
	public String page30202(WebApiCommonForm form, ModelMap modelMap)
			throws Exception {
		String pusMessageType = this.codeListApi001Service.getCodeListJson(
				form.getCenterId(), "psh_message_type").toString();
//		String themeType = this.codeListApi001Service.getCodeListJson(
//				form.getCenterId(), "message_topic_type").toString();
		
		//取主题类型表信息START
		StringBuffer themeType = new StringBuffer();
		Mi122Example example122 = new Mi122Example();
		example122.createCriteria().andCenteridEqualTo(form.getCenterId()).andValidflagEqualTo("1");
		example122.setOrderByClause("num asc");
		List<Mi122> list122 = mi122Dao.selectByExample(example122);
		
		List<Mi007> list007 = this.codeListApi001Service.getCodeList(form
				.getCenterId(), "message_topic_type");		
		themeType.append("");
		int i=0;
		for(i=0;i<list122.size();i++){
			for(int j=0;j<list007.size();j++){
				if(list122.get(i).getMessageTopicType().equals(list007.get(j).getItemid())){
					if(i==0){
						themeType.append("[{\"itemid\":\""+list007.get(j).getItemid()+"\",\"itemval\":\""+list007.get(j).getItemval()+"\"}");
					}else{
						themeType.append(",{\"itemid\":\""+list007.get(j).getItemid()+"\",\"itemval\":\""+list007.get(j).getItemval()+"\"}");
					}
					break;
				}
			}			
		}
		if(i==list122.size()){
			themeType.append("]");
		}
		//取主题类型表信息end
		
		modelMap.put("pusMessageType", pusMessageType);
		modelMap.put("themeType", themeType);
		return "page302/page30202";
	}

	// 报盘短消息推送（展示消息明细）
	@RequestMapping("/page3020201.html")
	public String page3020201(@RequestParam Map<String, Object> form,
			ModelMap modelMap) throws Exception {
//		String themeType = this.codeListApi001Service.getCodeListJson(
//				(String)form.get("centerId"), "message_topic_type").toString();
		
		//取主题类型表信息START
		StringBuffer themeType = new StringBuffer();
		Mi122Example example122 = new Mi122Example();
		example122.createCriteria().andCenteridEqualTo((String)form.get("centerId")).andValidflagEqualTo("1");
		example122.setOrderByClause("num asc");
		List<Mi122> list122 = mi122Dao.selectByExample(example122);
		
		List<Mi007> list007 = this.codeListApi001Service.getCodeList((String)form
				.get("centerId"), "message_topic_type");		
		themeType.append("");
		int i=0;
		for(i=0;i<list122.size();i++){
			for(int j=0;j<list007.size();j++){
				if(list122.get(i).getMessageTopicType().equals(list007.get(j).getItemid())){
					if(i==0){
						themeType.append("[{\"itemid\":\""+list007.get(j).getItemid()+"\",\"itemval\":\""+list007.get(j).getItemval()+"\"}");
					}else{
						themeType.append(",{\"itemid\":\""+list007.get(j).getItemid()+"\",\"itemval\":\""+list007.get(j).getItemval()+"\"}");
					}
					break;
				}
			}			
		}
		if(i==list122.size()){
			themeType.append("]");
		}
		//取主题类型表信息end
		modelMap.put("themeType", themeType);
		modelMap.putAll(form);
		return "page302/page3020201";
	}
	
	// 已推送公共短消息查询
	@RequestMapping("/page30203.html")
	public String page30203(WebApiCommonForm form, ModelMap modelMap)
			throws Exception {
		String pusMessageType = this.codeListApi001Service.getCodeListJson(
				form.getCenterId(), "psh_message_type").toString();
		String downloadPath = CommonUtil.getDownloadFileUrl("push_msg_img",
				form.getCenterId() + File.separator + "mi401Img", true);
		modelMap.put("pusMessageType", pusMessageType);
		modelMap.put("downloadPath", downloadPath);
		Mi001 mi001 = mi001Dao.selectByPrimaryKey(form.getCenterId());
		modelMap.put("custsvctel", mi001.getCustsvctel());
		return "page302/page30203";
	}
	
	// 已推送报盘短消息查询
	@RequestMapping("/page30204.html")
	public String page30204(WebApiCommonForm form, ModelMap modelMap)
			throws Exception {
//		String pusMessageType = this.codeListApi001Service.getCodeListJson(
//				form.getCenterId(), "psh_message_type").toString();
//		modelMap.put("pusMessageType", pusMessageType);
//		Mi001 mi001 = mi001Dao.selectByPrimaryKey(form.getCenterId());
//		modelMap.put("custsvctel", mi001.getCustsvctel());
		return "page302/page30204";
	}

	// 已推送报盘短消息查询（展示消息明细）
	@RequestMapping("/page3020401.html")
	public String page3020401(@RequestParam Map<String, Object> form,
			ModelMap modelMap) throws Exception {
		//取主题类型表信息START
		StringBuffer themeType = new StringBuffer();
		Mi122Example example122 = new Mi122Example();
		example122.createCriteria().andCenteridEqualTo((String)form.get("centerId")).andValidflagEqualTo("1");
		example122.setOrderByClause("num asc");
		List<Mi122> list122 = mi122Dao.selectByExample(example122);
		
		List<Mi007> list007 = this.codeListApi001Service.getCodeList((String)form
				.get("centerId"), "message_topic_type");		
		themeType.append("");
		int i=0;
		for(i=0;i<list122.size();i++){
			for(int j=0;j<list007.size();j++){
				if(list122.get(i).getMessageTopicType().equals(list007.get(j).getItemid())){
					if(i==0){
						themeType.append("[{\"itemid\":\""+list007.get(j).getItemid()+"\",\"itemval\":\""+list007.get(j).getItemval()+"\"}");
					}else{
						themeType.append(",{\"itemid\":\""+list007.get(j).getItemid()+"\",\"itemval\":\""+list007.get(j).getItemval()+"\"}");
					}
					break;
				}
			}			
		}
		if(i==list122.size()){
			themeType.append("]");
		}
		//取主题类型表信息end
		modelMap.put("themeType", themeType);
		modelMap.putAll(form);
		return "page302/page3020401";
	}
	
	// 已推送报盘短消息查询（展示消息明细）
	@RequestMapping("/page3020402.html")
	public String page3020402(@RequestParam Map<String, Object> form,
			ModelMap modelMap) throws Exception {
		modelMap.putAll(form);
		return "page302/page3020402";
	}

	// APP用户注册信息查询
	@RequestMapping("/page40101.{ext}")
	public String page40101(ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		List<Mi001> list = codeListApi001Service.getCityMessage();
		List<Mi007> list1 = this.codeListApi001Service.getCodeList(user
				.getCenterid(), "devicetype");
		List<Mi007> list2 = this.codeListApi001Service.getCodeList(user
				.getCenterid(), "contacttype");
		modelMap.put("mi001list", list);
		modelMap.put("devicetypeList", list1);
		modelMap.put("contacttypeList", list2);
		return "page401/page40101";
	}

	// 中心客服通讯信息查询
	@RequestMapping("/page40201.{ext}")
	public String page40201(ModelMap modelMap) throws Exception {
		return "page402/page40201";
	}

	// 软件更新信息查询
	@RequestMapping("/page40301.{ext}")
	public String page40301(ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		List<Mi001> list = codeListApi001Service.getCityMessage();
		List<Mi007> list1 = this.codeListApi001Service.getCodeList(user
				.getCenterid(), "devicetype");
		List<Mi007> list2 = this.codeListApi001Service.getCodeList(user
				.getCenterid(), "isno");
		List<Mi007> list3 = this.codeListApi001Service.getCodeList(user
				.getCenterid(), "publishflag");
		modelMap.put("publishflagList", list3);
		modelMap.put("mi001list", list);
		modelMap.put("devicetypeList", list1);
		modelMap.put("isnoList", list2);
		return "page403/page40301";
	}

	// 意见反馈信息查询
	@SuppressWarnings("unchecked")
	@RequestMapping("/page40401.{ext}")
	public String page40401(ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		Mi001Example example = new Mi001Example();
		example.setOrderByClause("centerid asc");
		Mi001Example.Criteria criteria = example.createCriteria();
		criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		if(!Constants.YD_ADMIN.equals(user.getCenterid())){
			criteria.andCenteridEqualTo(user.getCenterid());
		}
		
		List<Mi001> list = mi001Dao.selectByExample(example);
		
		//List<Mi001> list = codeListApi001Service.getCityMessage();
		List<Mi007> list1 = this.codeListApi001Service.getCodeList(user
				.getCenterid(), "devicetype");
		List<Mi007> list2 = this.codeListApi001Service.getCodeList(user
				.getCenterid(), "status");
		List<Mi007> list3 = this.codeListApi001Service.getCodeList(user
				.getCenterid(), "yjfkType");
		modelMap.put("mi001list", list);
		modelMap.put("devicetypeList", list1);
		modelMap.put("statusList", list2);
		modelMap.put("yjfkList", list3);
		return "page404/page40401";
	}
	
	@RequestMapping("/get40401.json")
	public String get40401(ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		Mi001Example example = new Mi001Example();
		example.setOrderByClause("centerid asc");
		Mi001Example.Criteria criteria = example.createCriteria();
		criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		if(!Constants.YD_ADMIN.equals(user.getCenterid())){
			criteria.andCenteridEqualTo(user.getCenterid());
		}
		
		List<Mi001> list = mi001Dao.selectByExample(example);
		
		List<Mi007> list1 = this.codeListApi001Service.getCodeList(user
				.getCenterid(), "devicetype");
		List<Mi007> list2 = this.codeListApi001Service.getCodeList(user
				.getCenterid(), "status");
		List<Mi007> list3 = this.codeListApi001Service.getCodeList(user
				.getCenterid(), "yjfkType");
		modelMap.put("mi001list", list);
		modelMap.put("devicetypeList", list1);
		modelMap.put("statusList", list2);
		modelMap.put("yjfkList", list3);
		return "";
	}
	
	// 意见反馈信息查询
	@RequestMapping("/page41101.html")
	public String page41101(ModelMap modelMap) throws Exception {
		return "page411/page41101";
	}
	
	@RequestMapping("/page41101GetPara.json")
	public String page41101GetPara(ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		List<Mi001> list = codeListApi001Service.getCityMessage();
		List<Mi007> list1 = this.codeListApi001Service.getCodeList(user
				.getCenterid(), "devicetype");
		List<Mi007> list2 = this.codeListApi001Service.getCodeList(user
				.getCenterid(), "displaydirection");
		List<Mi007> list3 = this.codeListApi001Service.getCodeList(user
				.getCenterid(), "looptype");
		modelMap.put("mi001list", list);
		modelMap.put("devicetypeList", list1);		
		modelMap.put("displaydirectionList", list2);
		modelMap.put("looptypeList", list3);
		return "";
	}
	
	// 中心微信发布
	@RequestMapping("/pageweixin001.{ext}")
	public String pagepageweixin001(ModelMap modelMap) throws Exception {
		List<Mi001> list = codeListApi001Service.getCityMessage();
		modelMap.put("mi001list", list);
		return "weixin/pageweixin001";
	}
	// 微信功能配置
	@RequestMapping("/pageweixin002.{ext}")
	public String pagepageweixin002(ModelMap modelMap) throws Exception {		
		return "weixin/pageweixin002";
	}
	// 微信菜单配置
	@RequestMapping("/pageweixin003.{ext}")
	public String pagepageweixin003(ModelMap modelMap) throws Exception {	
		List<Mi001> list = codeListApi001Service.getCityMessage();
		modelMap.put("mi001list", list);
		return "weixin/pageweixin003";
	}
	// 微信菜单策略配置
	@RequestMapping("/pageweixin004.{ext}")
	public String pagepageweixin004(ModelMap modelMap) throws Exception {	
		List<Mi001> list = codeListApi001Service.getCityMessage();
		modelMap.put("mi001list", list);
		return "weixin/pageweixin004";
	}
	// 微信用户查询
	@RequestMapping("/pageweixin005.{ext}")
	public String pagepageweixin005(ModelMap modelMap) throws Exception {	
		List<Mi001> list = codeListApi001Service.getCityMessage();
		modelMap.put("mi001list", list);
		return "weixin/pageweixin005";
	}
//	
//	@RequestMapping("/pageapi001.{ext}")
//	public String pageapi001(ModelMap modelMap) throws Exception {
//		return "api/pageapi001";
//	}
//	@RequestMapping("/pageapi002.{ext}")
//	public String pageapi002(ModelMap modelMap) throws Exception {
//		return "api/pageapi002";
//	}
//	@RequestMapping("/pageapi003.{ext}")
//	public String pageapi003(ModelMap modelMap) throws Exception {
//		return "api/pageapi003";
//	}
	
	// 留言信息查询
	@RequestMapping("/page60001.{ext}")
	public String page60001(ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		List<Mi007> list = this.codeListApi001Service.getCodeList(user
				.getCenterid(), "msgStatus");
		JSONArray devTypeJsonArray = this.codeListApi001Service.getCodeListJson(user
				.getCenterid(), "devicetype");
		JSONArray channelJsonArray = this.codeListApi001Service.getCodeListJson(user
				.getCenterid(), "channel");
		
		modelMap.put("msgStatusList", list);
		modelMap.put("devTypeJsonArray", devTypeJsonArray);
		modelMap.put("channelJsonArray", channelJsonArray);
		return "page600/page60001";
	}
	
	// 新闻信息查询
	@SuppressWarnings("unchecked")
	@RequestMapping("/page70001.{ext}")
	public String page70001(ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		Mi707Example example = new Mi707Example();
		example.createCriteria()
		.andCenteridEqualTo(user.getCenterid())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		example.setOrderByClause("DICID ASC");
		List<Mi707> mi707List = new ArrayList<Mi707>();
		mi707List = mi707Dao.selectByExample(example);
		if (CommonUtil.isEmpty(mi707List)){
			Mi707Example exampleTmp = new Mi707Example();
			exampleTmp.createCriteria()
			.andCenteridEqualTo("00000000")
			.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			exampleTmp.setOrderByClause("DICID ASC");
			mi707List = mi707Dao.selectByExample(exampleTmp);
		}
		List<Mi007> list = new ArrayList<Mi007>();
		for (int i = 0; i < mi707List.size(); i++){
			Mi007 mi007 = new Mi007();
			Mi707 mi707 = mi707List.get(i);
			mi007.setItemid(mi707.getItemid());
			mi007.setItemval(mi707.getItemval());
			list.add(mi007);
		}
		JSONArray ary = new JSONArray();
		ary = codeListApi001Service.getClassificationTreeJsonArray("0", user.getCenterid());
		
		List<Mi007> infoSource = this.codeListApi001Service.getCodeList(user
				.getCenterid(), "infoSource");
		List<Mi007> attrlist = this.codeListApi001Service.getCodeList(user
				.getCenterid(), "publishflag");
		
		//List<Mi007> list1 = this.codeListApi001Service.getCodeList("00000000" ,"channel");
		CMi040 form=new CMi040();
		form.setCenterid(user.getCenterid());
		form.setPage(1);
		form.setRows(999);
		WebApi04004_queryResult result = cmi040DAO.select040Page(form);
		modelMap.put("qudaolist", result.getList040());
	   
	    modelMap.put("ary", ary);
		modelMap.put("classificationlist", list);
		modelMap.put("infoSource", infoSource);
		modelMap.put("attrlist", attrlist);
		return "page700/page70001";
	}
	
	//预约业务类型查询
	@RequestMapping("/page62004.{ext}")
	public String page62004(ModelMap modelMap) throws Exception {
		List<Mi001> list = codeListApi001Service.getCityMessage();
		modelMap.put("mi001list", list);
		return "page303/page62004";
	}
	//预约时段管理
	@RequestMapping("/page62104.{ext}")
	public String page62104(ModelMap modelMap) throws Exception {
		return "page303/page62104";
	}
	//预约网点信息查询
	@RequestMapping("/page62301.{ext}")
	public String page62301(ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		List<Mi620> list = codeListApi001Service.getBussType(user.getCenterid());
		modelMap.put("mi620list", list);
		List<Mi621> list1 = codeListApi001Service.getBussTempla(user.getCenterid());
		modelMap.put("mi621list", list1);
		return "page303/page62301";
	}
	//预约信息查询
	@RequestMapping("/page62504.{ext}")
	public String page62504(ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		List<Mi620> list = codeListApi001Service.getBussType(user.getCenterid());
		modelMap.put("mi620list", list);
		List<Mi202> list202 = this.codeListApi001Service.getAreaMessage(user.getCenterid());
		modelMap.put("mi202list", list202);
		return "page303/page62504";
	}
	
	//预约注意事项查询
	@RequestMapping("/page62601.{ext}")
	public String page62601(ModelMap modelMap) throws Exception {
		return "page626/page62601";
	}
	
	//	黑名单管理
	@RequestMapping("/page60701.{ext}")
	public String page60701(ModelMap modelMap) throws Exception {
		return "page607/page60701";
	}
	
	//	节假日管理
	@RequestMapping("/page62701.{ext}")
	public String page62701(ModelMap modelMap) throws Exception {
		return "page627/page62701";
	}
	
	//	网点预约情况查询
	@RequestMapping("/page62505.{ext}")
	public String page62505(ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		List<Mi620> list = codeListApi001Service.getBussType(user.getCenterid());
		modelMap.put("mi620list", list);
		List<HashMap> list1 = codeListApi001Service.getWebSiteInfo(user.getCenterid());
		modelMap.put("mi623list", list1);
		return "page303/page62505";
	}
	//用户统计
	@RequestMapping("/page10301.{ext}")
	public String page10301(ModelMap modelMap) throws Exception {
		Mi001Example example = new Mi001Example();
		List temp = new ArrayList();
		temp.add("00000000");
		example.createCriteria().andValidflagEqualTo(Constants.IS_VALIDFLAG).andCenteridNotIn(temp);
		List<Mi001> list = mi001Dao.selectByExample(example);
		modelMap.put("mi001list", list);
		return "page103/page10301";
	}
	
	//用户统计
	@RequestMapping("/page10304.{ext}")
	public String page10304(ModelMap modelMap) throws Exception {

		return "page103/page10304";
	}
	//用户统计(APP)
	@RequestMapping("/page10305.{ext}")
	public String page10305(ModelMap modelMap) throws Exception {
		return "page103/page10305";
	}	
	//功能统计
		@RequestMapping("/page10704.{ext}")
		public String page10704(ModelMap modelMap) throws Exception {

			return "page107/page10704";
		}
	// 登录
	@RequestMapping("/yszc.{ext}")
	public String yszc(ModelMap modelMap) throws Exception {
		return "yszc";
	}
	
	// 报刊期次信息查询
	@SuppressWarnings("unchecked")
	@RequestMapping("/page70101.{ext}")
	public String page70101(ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		// Mi702中获取所属报社的报刊期次列表（itemid、itemval）newsItemIdList
		Mi702Example example = new Mi702Example();
		example.createCriteria().andCenteridEqualTo(user.getCenterid())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi702> newsItemIdList = cmi702Dao.selectByExample(example);
		// Mi007获取发布标记
		List<Mi007> publishflagList = this.codeListApi001Service.getCodeList(user
				.getCenterid(), "publishflag");
		modelMap.put("newsItemIdList", newsItemIdList);
		modelMap.put("publishflagList", publishflagList);
		return "page701/page70101";
	}
	
	// 报刊新闻查询
	@SuppressWarnings("unchecked")
	@RequestMapping("/page70201.{ext}")
	public String page70201(ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		//1.期次表进行有效期次的列表的获取,期次编号同seqno，降序排列（用于页面初始显示）
		Mi702Example example = new Mi702Example();
		example.setOrderByClause("seqno desc");
		example.createCriteria().andCenteridEqualTo(user.getCenterid())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi702> classificationlist = cmi702Dao.selectByExample(example);
		
		List<Mi704> forumByTimesListInit = new ArrayList<Mi704>();
		List<NewspapersTitleInfoBean> columnsByTimesListInit = new ArrayList<NewspapersTitleInfoBean>();
		if (classificationlist.size() > 0){
			// 2.版块栏目配置表最新期次包含的版块列表，版块升序（用于页面初始显示）
			forumByTimesListInit = codeListApi001Service.getForumByTimesFromMi704(user
					.getCenterid(), classificationlist.get(0).getItemid());
			
			columnsByTimesListInit = codeListApi001Service.getColumnsByTimesFromMi704(user
					.getCenterid(), classificationlist.get(0).getItemid());
		}
		
		// 3. 码表获取版块列表、栏目列表，用于弹出对话框中下拉的初始显示
		List<Mi007> forumList = codeListApi001Service.getCodeList(user.getCenterid(), Constants.FORUM_CODE);
		List<Mi007> columnsList = codeListApi001Service.getCodeList(user.getCenterid(), Constants.COLUMNS_CODE);
		
		HashMap map = new HashMap();
		map.put("rows", classificationlist);
		ObjectMapper mapper = new  ObjectMapper();
		JSONObject timesJsonObj = mapper.convertValue(map, JSONObject.class);
		modelMap.put("timesJsonObj", timesJsonObj.toString());
		
		modelMap.put("classificationlist", classificationlist);
		modelMap.put("newspaperforumlistInit", forumByTimesListInit);
		modelMap.put("newspapercolumnslistInit", columnsByTimesListInit);
		
		modelMap.put("newspaperforumlist", forumList);
		modelMap.put("newspapercolumnslist", columnsList);
		
		return "page702/page70201";
	}
	
	// 评论查询
	@SuppressWarnings("unchecked")
	@RequestMapping("/page70301.{ext}")
	public String page70301(ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		
		// 1.期次表进行已发布期次的列表的获取,期次编号同seqno，降序排列
		Mi702Example timesExample = new Mi702Example();
		timesExample.setOrderByClause("seqno desc");
		timesExample.createCriteria().andCenteridEqualTo(user.getCenterid())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andPublishflagEqualTo(Constants.PUBLISH_FLG_ONE);
		List<NewspapersTitleInfoBean> timesList = cmi702Dao.selectTimesList(timesExample);
		
		List<NewspapersTitleInfoBean> forumList = new ArrayList<NewspapersTitleInfoBean>();
		List<NewspapersTitleInfoBean> columnsList = new ArrayList<NewspapersTitleInfoBean>();
		if (timesList.size() > 0){
			// 2.新闻信息表已发布最新期次包含的版块列表，版块升序
			forumList = codeListApi001Service.getForumByTimesFromMi701(user.getCenterid(), timesList.get(0).getItemid());
			
			// 3. 新闻信息表已发布最新期次包含的栏目列表,栏目升序（栏目不等于空）
			columnsList = codeListApi001Service.getColumnsByTimesFromMi701(user.getCenterid(), timesList.get(0).getItemid());
		}
		
		// 4.用于评论来源的名称转义
		List<Mi007> devtypeList = this.codeListApi001Service.getCodeList(user
				.getCenterid(), Constants.MOBILE_CLIENT_CODE);
		
		HashMap map = new HashMap();
		map.put("rows", timesList);
		ObjectMapper mapper = new  ObjectMapper();
		JSONObject timesJsonObj = mapper.convertValue(map, JSONObject.class);
		modelMap.put("timesJsonObj", timesJsonObj.toString());
		modelMap.put("newsItemIdList", timesList);
		modelMap.put("newspaperforumList", forumList);
		modelMap.put("newspapercolumnsList", columnsList);
		modelMap.put("devtypeList", devtypeList);
		return "page703/page70301";
	}
	
	
	// 版块栏目配置查询
	@SuppressWarnings("unchecked")
	@RequestMapping("/page70401.{ext}")
	public String page70401(ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		Mi702Example example = new Mi702Example();
		example.setOrderByClause("seqno desc");
		example.createCriteria().andCenteridEqualTo(user.getCenterid())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi702> newsItemIdList = cmi702Dao.selectByExample(example);
		HashMap map = new HashMap();
		map.put("rows", newsItemIdList);
		ObjectMapper mapper = new  ObjectMapper();
		JSONObject newsItemIdJsonObj = mapper.convertValue(map, JSONObject.class);
		
		// Mi007获取版块、栏目
		List<Mi007> forumList = this.codeListApi001Service.getCodeList(user
				.getCenterid(), Constants.FORUM_CODE);
		List<Mi007> columnsList = this.codeListApi001Service.getCodeList(user
				.getCenterid(), Constants.COLUMNS_CODE);

		modelMap.put("newsItemIdList", newsItemIdJsonObj.toString());
		modelMap.put("forumList", forumList);
		modelMap.put("columnsList", columnsList);
		return "page704/page70401";
	}
	
	// 报刊新闻查询-无期次概念
	@RequestMapping("/page70501.{ext}")
	public String page70501(ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		
		Mi707Example example = new Mi707Example();
		example.createCriteria()
		.andCenteridEqualTo(user.getCenterid())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		example.setOrderByClause("abs(itemid) ASC");
		List<Mi707> mi707List = mi707Dao.selectByExample(example);
		List<Mi007> list = new ArrayList<Mi007>();
		for (int i = 0; i < mi707List.size(); i++){
			Mi007 mi007 = new Mi007();
			Mi707 mi707 = mi707List.get(i);
			mi007.setItemid(mi707.getDicid().toString());
			mi007.setItemval(mi707.getItemval());
			list.add(mi007);
		}
		JSONArray ary = new JSONArray();
		ary = codeListApi001Service.getClassificationTreeJsonArray("0", user.getCenterid());
	   
	    modelMap.put("ary", ary);
		modelMap.put("classificationlist", list);
		return "page705/page70501";
	}
	
	// 评论查询
	@SuppressWarnings("unchecked")
	@RequestMapping("/page70601.{ext}")
	public String page70601(ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();

		Mi707Example example = new Mi707Example();
		example.createCriteria()
		.andCenteridEqualTo(user.getCenterid())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		example.setOrderByClause("abs(itemid) ASC");
		List<Mi707> mi707List = mi707Dao.selectByExample(example);
		List<Mi007> list = new ArrayList<Mi007>();
		for (int i = 0; i < mi707List.size(); i++){
			Mi007 mi007 = new Mi007();
			Mi707 mi707 = mi707List.get(i);
			mi007.setItemid(mi707.getDicid().toString());
			mi007.setItemval(mi707.getItemval());
			list.add(mi007);
		}
		
		// 用于评论来源的名称转义
		List<Mi007> devtypeList = this.codeListApi001Service.getCodeList(user
				.getCenterid(), Constants.MOBILE_CLIENT_CODE);
		
		JSONArray ary = new JSONArray();
		ary = codeListApi001Service.getClassificationTreeJsonArray("0", user.getCenterid());
	   
	    modelMap.put("ary", ary);
	    
		modelMap.put("classificationlist", list);
		modelMap.put("devtypeList", devtypeList);
		
		return "page706/page70601";
	}
	
	// TODO
	// 栏目管理（功能升级）
	@RequestMapping("/page70701.html")
	public String page70701(ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		modelMap.put("centerid", user.getCenterid());
		return "page707/page70701";
	}
	
	@RequestMapping("/page70701Qry.html")
	public String page70701Qry(String pid, String centerid, ModelMap modelMap) throws Exception{//栏目管理 树  
		Logger log = LoggerUtil.getLogger();

		String businName = "栏目管理树形菜单显示";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText("pid="+pid+""));
		
//		UserContext user = UserContext.getInstance();
//		String centeridTmp = user.getCenterid();
		
	    JSONArray ary = new JSONArray();
	    List<Mi707> list = webApi707Service.getCodeListByPid(pid, centerid) ;
	    Mi707 mi707 = new Mi707();
	    if ("000000000".equals(pid) && "000000000".equals(centerid)) {
    	    for(int i = 0; i < list.size(); i++){
    	    	mi707 = list.get(i);
    			JSONObject obj=new JSONObject();
    			obj.put("id", mi707.getDicid());
    			obj.put("text", mi707.getItemval());
    			//根据是否含有子项信息，设置state属性
    			int counts = webApi707Service.getChildCounts(mi707.getCenterid(), mi707.getDicid());
    			if (0 == counts) {
    				obj.put("state", "open");
    			}else{
    				obj.put("state", "closed");
    			}
    			
    			Page70701QryResultAttr attributes = new Page70701QryResultAttr();
    			attributes.setCenterid(mi707.getCenterid());
    			attributes.setCentername(webApi707Service.getCenterName(mi707.getCenterid()));
    			attributes.setDicid(mi707.getDicid());
    			attributes.setItemid(mi707.getItemid());
    			attributes.setItemval(mi707.getItemval());
    			attributes.setUpdicid(mi707.getUpdicid());
    			attributes.setUpdicname("000000000-栏目管理");

    	    	obj.put("attributes", attributes);
    			ary.add(obj); 
    	    } 
	    }else {
    	    for(int i = 0; i < list.size(); i++){
    	    	mi707 = list.get(i);
    			JSONObject obj=new JSONObject();
    			obj.put("id", mi707.getDicid());
    			obj.put("text", mi707.getItemval());			 
    			//根据是否含有子项信息，设置state属性
    			int counts = webApi707Service.getChildCounts(mi707.getCenterid(), mi707.getDicid());
    			if (0 == counts) {
    				obj.put("state", "open");
    			}else{
    				obj.put("state", "closed");
    			}
    			
    			Page70701QryResultAttr attributes = new Page70701QryResultAttr();
    			attributes = webApi707Service.setNodeAttributes(mi707);
    	    	
    			obj.put("attributes", attributes);
    			ary.add(obj); 
    	    } 
	    }

	    modelMap.put("ary", ary);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
    	
		return "page707/page70701Qry";
	}
	
	@RequestMapping("/page707Query.json")
	public String page707Query(String itemid,ModelMap modelMap) throws Exception{//栏目管理 树  
		Logger log = LoggerUtil.getLogger();
		UserContext user = UserContext.getInstance();

		String businName = "根据编码查新栏目";
		log.info(LOG.START_BUSIN.getLogText(businName));
		
	    Mi707 mi707 = webApi707Service.get707(user.getCenterid(),itemid);
	    modelMap.put("mi707", mi707);
		
		log.info(LOG.END_BUSIN.getLogText(businName));
    	
		return "";
	}
	
	//素材管理
	@SuppressWarnings("unchecked")
	@RequestMapping("/page13001.{ext}")
	public String page13001(ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		if (CommonUtil.isEmpty(user.getCenterid())) {
			return null;
		}
		Mi130Example example = new Mi130Example();
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.APP_DATE_TIME_FORMAT);
		example.createCriteria().andCenteridEqualTo(user.getCenterid());
		example.setOrderByClause("groupid");
		List groupList = mi130Dao.selectByExample(example);
		if(groupList == null || groupList.size() == 0){
			long id = CommonUtil.genKeyStatic("MI130");
			Mi130 record = new Mi130();
			record.setCenterid(user.getCenterid());
			record.setCreatetime(dateFormatter.format(new Date()));
			record.setGroupid(String.valueOf(id));
			record.setGroupname("未分组");
			record.setOperator(user.getOpername());
			mi130Dao.insert(record);
			String filePath = CommonUtil.getFileFullPath("server_upload_img", user.getCenterid() + File.separator + id, true) + File.separator;
			File dirCityFile = new File(filePath);
			if (!dirCityFile.exists()) {
				dirCityFile.mkdirs();
			}
		}
		Mi130 mi130 = new Mi130();
		mi130.setCenterid(user.getCenterid());
		List<WebApi13001Result> result = mi130Dao.getGroupInfoAndPiccount(mi130);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("groupList", result);
		return "page130/page13001";
	}
	
	//素材管理调用实例
	@RequestMapping("/page13002.{ext}")
	public String page13002(ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		if (CommonUtil.isEmpty(user.getCenterid())) {
			return null;
		}
		return "page130/page13002";
	}
	
	//微信关注信息功能配置
	@RequestMapping("/pageweixin007.{ext}")
	public String pageweixin007(ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		if (CommonUtil.isEmpty(user.getCenterid())) {
			return null;
		}
		List<Mi001> list = codeListApi001Service.getCityMessage();
		modelMap.put("mi001list", list);
		return "weixin/pageweixin007";
	}
	
	//微信关注信息配置
	@RequestMapping("/pageweixin006.{ext}")
	public String pageweixin006(ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		if (CommonUtil.isEmpty(user.getCenterid())) {
			return null;
		}
		return "weixin/pageweixin006";
	}
	
	// 内容展现配置查询
	@SuppressWarnings("unchecked")
	@RequestMapping("/page70702.{ext}")
	public String page70702(ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		Mi702Example example = new Mi702Example();
		example.setOrderByClause("seqno desc");
		example.createCriteria().andCenteridEqualTo(user.getCenterid())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi007> newsViewItemsList = this.codeListApi001Service.getCodeList(user
				.getCenterid(), "uiconfig");
		HashMap map = new HashMap();
		map.put("rows", newsViewItemsList);
		ObjectMapper mapper = new  ObjectMapper();
		JSONObject newsViewItemsJsonObj = mapper.convertValue(map, JSONObject.class);
		
		// Mi007获取第一层级栏目，以后升级此页面再获取所有
		//升级情况一：现版本栏目管理树形结构不变，该页面使用两个树形结构（一个用来展现，一个用来选择），再直接把所有都查出
		//升级情况二：无版本栏目管理，直接把所有项都维护在一个维度，同过此功能进行层级管理
		// 目前实现缺点，只要在版本栏目里的层级关系，此处都会出现在所配置的项中，即，只要选择了此第一层级，则对应该层级下的所有都同时维护过来。
		Mi707Example mi707Exa = new Mi707Example();
		mi707Exa.createCriteria()
		.andCenteridEqualTo(user.getCenterid())
		.andUpdicidEqualTo(Integer.parseInt(Constants.IS_NOT_VALIDFLAG))
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		mi707Exa.setOrderByClause("DICID ASC");
		List<Mi707> mi707List = new ArrayList<Mi707>();
		mi707List = mi707Dao.selectByExample(mi707Exa);
		if (CommonUtil.isEmpty(mi707List)){
			Mi707Example exampleTmp = new Mi707Example();
			exampleTmp.createCriteria()
			.andCenteridEqualTo("00000000")
			.andUpdicidEqualTo(Integer.parseInt(Constants.IS_NOT_VALIDFLAG))
			.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			exampleTmp.setOrderByClause("DICID ASC");
			mi707List = mi707Dao.selectByExample(exampleTmp);
		}
		List<Mi007> list = new ArrayList<Mi007>();
		for (int i = 0; i < mi707List.size(); i++){
			Mi007 mi007 = new Mi007();
			Mi707 mi707 = mi707List.get(i);
			mi007.setItemid(mi707.getItemid());
			mi007.setItemval(mi707.getItemval());
			list.add(mi007);
		}

		modelMap.put("newsViewItemsList", newsViewItemsJsonObj.toString());
		modelMap.put("columnsList", list);
		return "page707/page70702";
	}
	
	// 应聘信息查询
	@RequestMapping("/page99901.{ext}")
	public String page99901(ModelMap modelMap) throws Exception {
		// 应聘区域
		List<ItemInfo> applyAreaList = this.webApi999ServiceImpl.getApplyAreaList("area");
		// 应聘职位
		//List<ItemInfo> applyPositionList = this.webApi999ServiceImpl.getApplyPositionList("area", "01");
		//已读未读
		List<ItemInfo> isreadList = new ArrayList<ItemInfo>();
		ItemInfo isreadItem = new ItemInfo();
		isreadItem.setItemid("0");
		isreadItem.setItemval("未读");
		isreadList.add(isreadItem);
		isreadItem = new ItemInfo();
		isreadItem.setItemid("1");
		isreadItem.setItemval("已读");
		isreadList.add(isreadItem);

		HashMap map = new HashMap();
		map.put("rows", applyAreaList);
		ObjectMapper mapper = new  ObjectMapper();
		JSONObject applyareaQryObj = mapper.convertValue(map, JSONObject.class);
		modelMap.put("applyareaQryObj", applyareaQryObj.toString());
		
		modelMap.put("applyAreaList", applyAreaList);
		//modelMap.put("applyPositionList", applyPositionList);
		modelMap.put("isreadList", isreadList);

		return "page999/page99901";
	}

	@RequestMapping("/page30402.html")
	public String page3040203(WebApiCommonForm form, ModelMap modelMap) throws Exception {
		UserContext user = UserContext.getInstance();
		/*JSONArray jsonarray = codeListApi001Service.getCodeListJson(
				user.getCenterid(), "pushchannel");

		HashMap<String, String> centerIdMap = new HashMap<String, String>();
		centerIdMap.put("centerid", user.getCenterid());
		modelMap.put("channelList", jsonarray);*/
		List<Mi001> mi001List = ptlApi001Service.getCenterAllList("00000000");
		modelMap.put("mi001List",mi001List);
		modelMap.put("clist",
				webApi405Service.getMi405AllList("1", "15"));
		return "page405/page30402";
	}
	public Mi122DAO getMi122Dao() {
		return mi122Dao;
	}

	public void setMi122Dao(Mi122DAO mi122Dao) {
		this.mi122Dao = mi122Dao;
	}
	
	
	
	/**
	 *  昆明新加
	 * */
	@RequestMapping("/channelAppManagement.html")
	public String channelAppManagement(ModelMap modelMap) throws Exception {		
		return "ptl/channelAppManagement";
	}
	@RequestMapping("/channelMonitor.html")
	public String channelMonitor(ModelMap modelMap) throws Exception {		
		return "ptl/channelMonitor";
	}
	@RequestMapping("/channelFlowControl.html")
	public String channelFlowControl(ModelMap modelMap) throws Exception {		
		return "ptl/channelFlowControl";
	}
	@RequestMapping("/channelLimitAmount.html")
	public String channelLimitAmount(ModelMap modelMap) throws Exception {		
		return "ptl/channelLimitAmount";
	}
	@RequestMapping("/channelMailList.html")
	public String channelMailList(ModelMap modelMap) throws Exception {		
		return "ptl/channelMailList";
	}
	@RequestMapping("/channelRunning.html")
	public String channelRunning(ModelMap modelMap) throws Exception {		
		return "ptl/channelRunning";
	}
	@RequestMapping("/channelFlowMonitor.html")
	public String channelFlowMonitor(ModelMap modelMap) throws Exception {		
		return "ptl/channelFlowMonitor";
	}
	@RequestMapping("/channelOverflow.html")
	public String channelOverflow(ModelMap modelMap) throws Exception {		
		return "ptl/channelOverflow";
	}
	@RequestMapping("/channelSet.html")
	public String channelSet(ModelMap modelMap) throws Exception {		
		return "ptl/channelSet";
	}
	@RequestMapping("/channelService.html")
	public String channelService(ModelMap modelMap) throws Exception {		
		return "ptl/channelService";
	}
	@RequestMapping("/channelState.html")
	public String channelState(ModelMap modelMap) throws Exception {		
		return "ptl/channelState";
	}
	@RequestMapping("/network.html")
	public String network(ModelMap modelMap) throws Exception {		
		return "ptl/network";
	}
	@RequestMapping("/building.html")
	public String building(ModelMap modelMap) throws Exception {		
		return "ptl/building";
	}
	@RequestMapping("/channelUserManagement.html")
	public String channelUserManagement(ModelMap modelMap) throws Exception {		
		return "ptl/channelUserManagement";
	}
	@RequestMapping("/animationSet.html")
	public String animationSet(ModelMap modelMap) throws Exception {		
		return "ptl/animationSet";
	}
	
	
	@RequestMapping("/webappcomCenterId.json")
	public String webappcomCenterId(ModelMap modelMap) throws Exception {	
		List<Mi001> list = codeListApi001Service.getCityMessage();
		modelMap.put("mi001list", list);
		return "";
	}
	@RequestMapping("/webappcomChannel.json")
	public String webappcomChannel(String centerid ,ModelMap modelMap) throws Exception {	
//		UserContext user = UserContext.getInstance();
//		if(CommonUtil.isEmpty(centerid)){
//			centerid = user.getCenterid();
//		}
		List<Mi007> list1 = this.codeListApi001Service.getCodeList("00000000" ,"channel");
		modelMap.put("mi007list", list1);
		return "";
	}
	
	@RequestMapping("/customerLevel.json")
	public String customerLevel(ModelMap modelMap) throws Exception {
		List<Mi007> list1 = this.codeListApi001Service.getCodeList("00000000", "customerlevel");
		modelMap.put("mi007list", list1);
		return "";
	}
	
	@RequestMapping("/configMenu.html")
	public String configMenu( ModelMap modelMap){
		return "ptl/configMenu";		
	}
	@RequestMapping("/customerMenu.html")
	public String customerMenu( ModelMap modelMap){
		return "ptl/customerMenu";		
	}
	@RequestMapping("/userAnalysis.html")
	public String userAnalysis( ModelMap modelMap){
		return "ptl/userAnalysis";		
	}
	
	@RequestMapping("/monitorTheme.html")
	public String monitorTheme( ModelMap modelMap){
		return "ptl/monitorTheme";		
	}
	
	@RequestMapping("/getApptranstype.json")
	public String getApptranstype(ModelMap modelMap) throws Exception {
		List<Mi007> list1 = this.codeListApi001Service.getCodeList("00000000", "apptranstype");
		List<Mi007> newList = new ArrayList<Mi007>();
		List<String> sList = new ArrayList<String>();
		for(Mi007 mi007:list1){
//			String moneytype = "0";
			String itemid = mi007.getItemid();//业务类型编码
//			CMi051 form = new CMi051();
//			form.setPage(new Integer(1));
//			form.setRows(new Integer(10));
//			form.setBuztype(itemid);
//			WebApi05104_queryResult result = webApi050ServiceImpl.webapi05104(form);
//			//在051表配置过
//			if(!CommonUtil.isEmpty(result) && (result.getList051() !=null) && (!result.getList051().isEmpty())){
//				moneytype = result.getList051().get(0).getMoneytype();
//				
//			}
//			
//			CMi007 cmi007 = new CMi007();
//			BeanUtils.copyProperties(cmi007, mi007);
//			cmi007.setIsmoneytype(moneytype);
//			
//			newList.add(cmi007);
			sList.add(itemid);
			
		}
		newList = webApi050ServiceImpl.webapi05107(list1,sList);
		modelMap.put("mi007list", newList);
		return "";
	}
	
	@RequestMapping("/getServicetype.json")
	public String getServicetype(ModelMap modelMap) throws Exception {
		List<Mi007> list1 = this.codeListApi001Service.getCodeList("00000000", "servicetype");
		modelMap.put("mi007list", list1);
		return "";
	}
	
	@RequestMapping("/getSubServicetype.json")
	public String getSubServicetype(int dicid, ModelMap modelMap) throws Exception {
		List<Mi007> list1 = this.codeListApi001Service.getSubCodeList("00000000", dicid);
		modelMap.put("mi007list", list1);
		return "";
	}
	
	@RequestMapping("/getCustomLevel.json")
	public String getCustomLevel(ModelMap modelMap) throws Exception {
		List<Mi007> list1 = this.codeListApi001Service.getCodeList("00000000", "customlevel");
		modelMap.put("customlevel", list1);
		return "";
	}
	
	@RequestMapping("/blacklist.html")
	public String blacklist( ModelMap modelMap){
		return "ptl/blacklist";		
	}
	@RequestMapping("/ptl40013.html")
	public String ptl40013( ModelMap modelMap){
		return "ptl/ptl40013";		
	}
	@RequestMapping("/getApiType.json")
	public String getApiType(ModelMap modelMap) throws Exception {	
		List<Mi007> list1 = this.codeListApi001Service.getCodeList("00000000" ,"apitype");
		modelMap.put("mi007list", list1);
		return "";
	}
	
	@RequestMapping("/userView.html")
	public String userView(ModelMap modelMap) throws Exception {	
		return "ptl/userView";
	}
	
	@RequestMapping("/userView1.html")
	public String userView1(ModelMap modelMap) throws Exception {	
		return "ptl/userView1";
	}
	
	@RequestMapping("/userView2.html")
	public String userView2(ModelMap modelMap) throws Exception {	
		return "ptl/userView2";
	}
	
	@RequestMapping("/userView3.html")
	public String userView3(ModelMap modelMap) throws Exception {	
		return "ptl/userView3";
	}
	
	@RequestMapping("/userView4.html")
	public String userView4(ModelMap modelMap) throws Exception {	
		return "ptl/userView4";
	}
	
	@RequestMapping("/userView5.html")
	public String userView5(ModelMap modelMap) throws Exception {	
		return "ptl/userView5";
	}
	
	@RequestMapping("/userView6.html")
	public String userView6(ModelMap modelMap) throws Exception {	
		return "ptl/userView6";
	}
	
	@RequestMapping("/userView7.html")
	public String userView7(ModelMap modelMap) throws Exception {	
		return "ptl/userView7";
	}
	
	@RequestMapping("/link.html")
	public String link(ModelMap modelMap) throws Exception {	
		return "/js/3rd/dialogs/link/link";
	}
	
	@RequestMapping("/attachment.html")
	public String attachment(ModelMap modelMap) throws Exception {	
		return "/js/3rd/dialogs/attachment/attachment.jsp";
	}
	
	@RequestMapping("/preview.html")
	public String preview(ModelMap modelMap) throws Exception {	
		return "/js/3rd/dialogs/preview/preview";
	}
	@RequestMapping("/businessLog.html")
	public String businessLog(ModelMap modelMap) throws Exception {	
		return "/ptl/businessLog";
	}
	@RequestMapping("/serviceLevel.html")
	public String serviceLevel(ModelMap modelMap) throws Exception {	
		return "/ptl/serviceLevel";
	}
	
	@RequestMapping("/page05701.html")
	public String page05701(ModelMap modelMap) throws Exception {	
		return "/ptl/page05701";
	}
	
	@RequestMapping("/sitePublish.html")
	public String sitePublish(ModelMap modelMap) throws Exception {	
		return "/ptl/sitePublish";
	}
	
	@RequestMapping("/customerComplaints.html")
	public String customerComplaints(ModelMap modelMap) throws Exception {	
		return "/ptl/customerComplaints";
	}
	
	@RequestMapping("/hotIssues.html")
	public String hotIssues(ModelMap modelMap) throws Exception {	
		return "/ptl/hotIssues";
	}
	
	@RequestMapping("/generateReports.html")
	public String generateReports(ModelMap modelMap) throws Exception {	
		return "/ptl/generateReports";
	}
	
	@RequestMapping("/desensitizationRule.html")
	public String desensitizationRule(ModelMap modelMap) throws Exception {	
		return "/ptl/desensitizationRule";
	}
	
	@RequestMapping("/getBmCode.json")
	public String getBmCode(String bmcode ,ModelMap modelMap) throws Exception {
		List<Mi007> list = this.codeListApi001Service.getCodeList("00000000", bmcode);
		modelMap.put(bmcode, list);
		return "";
	}
	
	
	@RequestMapping("/warningMonitor.html")
	public String warningMonitor(ModelMap modelMap) throws Exception {	
		return "/ptl/warningMonitor";
	}
	
	@RequestMapping("/dailyOperation.html")
	public String dailyOperation(ModelMap modelMap) throws Exception {	
		return "/ptl/dailyOperation";
	}
	
	@RequestMapping("/assessmentReport.html")
	public String assessmentReport(ModelMap modelMap) throws Exception {	
		return "/ptl/assessmentReport";
	}
	
	@RequestMapping("/hotSpots1.html")
	public String hotSpots1(ModelMap modelMap) throws Exception {	
		return "/ptl/hotSpots1";
	}
	
	@RequestMapping("/hotSpots2.html")
	public String hotSpots2(ModelMap modelMap) throws Exception {	
		return "/ptl/hotSpots2";
	}
	
	@RequestMapping("/hotSpots3.html")
	public String hotSpots3(ModelMap modelMap) throws Exception {	
		return "/ptl/hotSpots3";
	}
	
	@RequestMapping("/hotSpots4.html")
	public String hotSpots4(ModelMap modelMap) throws Exception {	
		return "/ptl/hotSpots4";
	}
	
	@RequestMapping("/customerSatisfaction.html")
	public String customerSatisfaction(ModelMap modelMap) throws Exception {	
		return "/ptl/customerSatisfaction";
	}
	
	@RequestMapping("/hotSort.html")
	public String hotSort(ModelMap modelMap) throws Exception {	
		return "/ptl/hotSort";
	}
	
	@RequestMapping("/userComplaintRate.html")
	public String userComplaintRate(ModelMap modelMap) throws Exception {	
		return "/ptl/userComplaintRate";
	}
	
	@RequestMapping("/channelUpdateAmount.html")
	public String channelUpdateAmount(ModelMap modelMap) throws Exception {	
		return "/ptl/channelUpdateAmount";
	}
}
