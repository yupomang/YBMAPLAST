package com.yondervision.mi.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi701DAO;
import com.yondervision.mi.dao.CMi704DAO;
import com.yondervision.mi.dao.Mi001DAO;
import com.yondervision.mi.dao.Mi706DAO;
import com.yondervision.mi.dao.Mi707DAO;
import com.yondervision.mi.dto.Mi001;
import com.yondervision.mi.dto.Mi701;
import com.yondervision.mi.dto.Mi701Example;
import com.yondervision.mi.dto.Mi701WithBLOBs;
import com.yondervision.mi.dto.Mi704;
import com.yondervision.mi.dto.Mi704Example;
import com.yondervision.mi.dto.Mi706;
import com.yondervision.mi.dto.Mi706Example;
import com.yondervision.mi.dto.Mi707;
import com.yondervision.mi.dto.Mi707Example;
import com.yondervision.mi.form.AppApi70001Form;
import com.yondervision.mi.form.AppApi70002Form;
import com.yondervision.mi.form.AppApi70004Form;
import com.yondervision.mi.form.AppApi70008Form;
import com.yondervision.mi.form.AppApi70009Form;
import com.yondervision.mi.form.AppApi70010Form;
import com.yondervision.mi.form.AppApi70012Form;
import com.yondervision.mi.result.AppApi70001Result;
import com.yondervision.mi.result.AppApi70002Result;
import com.yondervision.mi.result.AppApi70004Result;
import com.yondervision.mi.result.AppApi70009Result;
import com.yondervision.mi.result.AppApi70010Result;
import com.yondervision.mi.result.AppApi70013Result;
import com.yondervision.mi.result.NewsBean;
import com.yondervision.mi.result.ViewItemAndDetailBean;
import com.yondervision.mi.result.ViewItemBean;
import com.yondervision.mi.service.AppApi700Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.HtmlUtil;
import com.yondervision.mi.util.PropertiesReader;

/** 
* @ClassName: AppApi700ServiceImpl 
* @Description: 内容管理
* @author gongqi
* @date July 18, 2014 9:33:25 PM  
* 
*/ 
public class AppApi700ServiceImpl implements AppApi700Service {
	
	private CMi701DAO cmi701Dao = null;
	public void setCmi701Dao(CMi701DAO cmi701Dao) {
		this.cmi701Dao = cmi701Dao;
	}
	private Mi001DAO mi001Dao = null;
	
	public void setMi001Dao(Mi001DAO mi001Dao) {
		this.mi001Dao = mi001Dao;
	}
	private Mi706DAO mi706Dao = null;
	public void setMi706Dao(Mi706DAO mi706Dao) {
		this.mi706Dao = mi706Dao;
	}
	private Mi707DAO mi707Dao = null;
	public void setMi707Dao(Mi707DAO mi707Dao) {
		this.mi707Dao = mi707Dao;
	}
	private CMi704DAO cmi704Dao = null;
	public void setCmi704Dao(CMi704DAO cmi704Dao) {
		this.cmi704Dao = cmi704Dao;
	}
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}

	@SuppressWarnings("unchecked")
	public List<AppApi70001Result> appapi70001(AppApi70001Form form, HttpServletRequest request)
			throws Exception {
		List<Mi706> list706 = new ArrayList<Mi706>();
		if(!CommonUtil.isEmpty(form.getUserId())){
			Mi706Example m706e=new Mi706Example();
			com.yondervision.mi.dto.Mi706Example.Criteria ca= m706e.createCriteria();
			ca.andCenteridEqualTo(form.getCenterId());
			ca.andUseridEqualTo(form.getUserId());
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			ca.andStatusEqualTo(Constants.IS_VALIDFLAG);
			list706 = mi706Dao.selectByExample(m706e);			
		}
		String validDate = "";
		Mi707Example mi707Exa = new Mi707Example();
		mi707Exa.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andItemidEqualTo(form.getClassification());
		List<Mi707> mi707List = mi707Dao.selectByExample(mi707Exa);
		if (!CommonUtil.isEmpty(mi707List)){
			validDate = getValidDate(form.getCenterId(), mi707List.get(0).getDicid().toString());
		}
		
		List<Mi701> list = cmi701Dao.selectMi701(form, validDate,request.getAttribute("MI040Pid").toString());
		List<AppApi70001Result> resultList = new ArrayList<AppApi70001Result>();
		AppApi70001Result result;
		Mi701 mi701;
		for (int i = 0; i < list.size(); i++) {
			result = new AppApi70001Result();
			mi701 = list.get(i);
			result.setTitleId(mi701.getSeqno().toString());
			result.setTitle(mi701.getTitle());
			result.setIntroduction(mi701.getIntroduction());
			if (!CommonUtil.isEmpty(mi701.getImage())){
				String turnImageUrl  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "turnImageUrl"+form.getCenterId());
				if(turnImageUrl.equals("1")){
					String rquestUrlTmp = request.getRequestURL().toString();
					String contextPath = request.getContextPath();
					String reqUrl = rquestUrlTmp.substring(0,request.getRequestURL().lastIndexOf(contextPath));
					String pathYuan  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "serverPath"+form.getCenterId());
//					if (Constants.CHANNELTYPE_APP.equals(form.getChannel())){
					String path  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appServerPath"+form.getCenterId());
					result.setImgUrl(mi701.getImage().replaceAll(pathYuan, path));
//					}else if(Constants.CHANNELTYPE_WEIXIN.equals(form.getChannel())){
//						// 以后按照微信的servlet及地址要求修改
//						String path  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "wxServerPath");
//						result.setImgUrl(mi701.getImage().replaceAll(reqUrl.concat(contextPath)+"/", path));
//					}
				}else{
					result.setImgUrl(mi701.getImage());
				}
			}else{
				result.setImgUrl("");
			}
 			result.setReleaseDate(mi701.getReleasetime());
			
			if(!CommonUtil.isEmpty(form.getUserId())){
				for(int j=0;j<list706.size();j++){
					if(mi701.getSeqno()==list706.get(j).getNewsid()){
						result.setCollect(list706.get(j).getStatus());
						break;
					}
				}
			}	
			resultList.add(result);
		}

		return resultList;
	}

	public List<AppApi70002Result> appapi70002(AppApi70002Form form, HttpServletRequest request) throws Exception {
		Mi701WithBLOBs mi701 = cmi701Dao.selectByPrimaryKey(Integer.parseInt(form.getTitleId()));
		Mi001 mi001 = mi001Dao.selectByPrimaryKey(form.getCenterId());
		
		AppApi70002Result result = new AppApi70002Result();
		result.setTitle(mi701.getTitle());
		result.setCentername(mi001.getCentername());
	
		String releasetime = mi701.getReleasetime().substring(0, 16);
		result.setImage(mi701.getImage());
		result.setIntroduction(mi701.getIntroduction());
		result.setReleasetime(releasetime);
		String contentTmp = "";
//		if(!"00041100".equals(form.getCenterId())){
//			String path  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "serverPath");
//			contentTmp = mi701.getContent().replaceAll("src=\"/YBMAP/", "src=\""+path+"/YBMAP/");
//		}else{
		String turnImageUrl  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "turnImageUrl"+form.getCenterId());
		if(turnImageUrl.equals("1")){
			String rquestUrlTmp = request.getRequestURL().toString();
			String contextPath = request.getContextPath();
			String reqUrl = rquestUrlTmp.substring(0,request.getRequestURL().lastIndexOf(contextPath));
			String pathYuan  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "serverPath"+form.getCenterId());
			String path  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appServerPath"+form.getCenterId());
			System.out.println("【新闻内容内网地址:"+pathYuan+"】");
			System.out.println("【新闻内容外网地址:"+path+"】");
			contentTmp = mi701.getContent().replaceAll(pathYuan, path);
//			if (Constants.CHANNELTYPE_APP.equals(form.getChannel())){				
//				String path  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appServerPath");
////				contentTmp = mi701.getContent().replaceAll("src=\""+reqUrl.concat(contextPath)+"//", "src=\""+path);
//				contentTmp = mi701.getContent().replaceAll("src=\""+pathYuan, "src=\""+path);
//			}else if(Constants.CHANNELTYPE_WEIXIN.equals(form.getChannel())){
//				// 以后按照微信的servlet及地址要求修改
//				String path  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "wxServerPath");
////				contentTmp = mi701.getContent().replaceAll("src=\"/YCMAP/", "src=\""+path+"/YBMAP/");
//				contentTmp = mi701.getContent().replaceAll("src=\""+pathYuan, "src=\""+path);
//			}
//		}
		result.setContent(contentTmp);
		}else{
			result.setContent(mi701.getContent());
		}
		if(CommonUtil.isEmpty(mi701.getContenttxt())){
			result.setContenttxt(HtmlUtil.delHTMLTag(contentTmp));
		}else{
			result.setContenttxt(mi701.getContenttxt());
		}

		List<AppApi70002Result> resultList = new ArrayList<AppApi70002Result>();
		resultList.add(result);
		
		/**add by lwj 20170712 中心网站 使用mi701表中，freeuse9 作为访问量统计字段， 在获取一次信息后，访问量增加1*/
		if ("30".equals(form.getChannel()) ) {
			int count = mi701.getFreeuse9() == null ? 0 : mi701.getFreeuse9();
			count = count + 1;
			result.setFreeuse9(count);
			mi701.setFreeuse9(count);
			cmi701Dao.updateByPrimaryKey(mi701);
		}
		/**end add*/
		
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	public int appapi70003(AppApi70004Form form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		int value = 0;
		Mi706Example m706e=new Mi706Example();
		com.yondervision.mi.dto.Mi706Example.Criteria ca= m706e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterId());
		ca.andUseridEqualTo(form.getUserId());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);		
		ca.andNewsidEqualTo(Integer.valueOf(form.getNewsId()));
		List<Mi706> list706 = mi706Dao.selectByExample(m706e);	
		if(list706.size()>0){
			Mi706 mi706Up = list706.get(0);
			if("1".equals(mi706Up.getStatus())){
				mi706Up.setStatus("2");//不关注
				value = 2;
			}else{
				mi706Up.setStatus("1");//关注
				value = 1;
			}
			mi706Dao.updateByPrimaryKey(mi706Up);
			return value;
		}else{
			//收藏表中无收藏新加收藏记录
			Mi706 mi706 = new Mi706();
			String seqno = commonUtil.genKey("MI706").toString();
			if (CommonUtil.isEmpty(seqno)) {
				log.error(ERROR.NULL_KEY.getLogText("新闻收藏MI706"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
						ERROR.NULL_KEY.getLogText("新闻动态MI706"));
			}
			mi706.setSeqno(Integer.valueOf(seqno));
			mi706.setCenterid(form.getCenterId());
			mi706.setUserid(form.getUserId());
			mi706.setNewsid(Integer.valueOf(form.getNewsId().trim()));
			mi706.setNewsurl(form.getUrl());
			mi706.setStatus(Constants.IS_VALIDFLAG);
			mi706.setValidflag(Constants.IS_VALIDFLAG);
			mi706.setDatecreated(CommonUtil.getSystemDate());
			mi706.setDatemodified(CommonUtil.getSystemDate());
			mi706Dao.insert(mi706);
			return 1;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<AppApi70001Result> appapi70004(AppApi70004Form form)
			throws Exception {
		List<Mi706> list706 = new ArrayList<Mi706>();
		if(!CommonUtil.isEmpty(form.getUserId())){
			Mi706Example m706e=new Mi706Example();
			com.yondervision.mi.dto.Mi706Example.Criteria ca= m706e.createCriteria();
			ca.andCenteridEqualTo(form.getCenterId());
			ca.andUseridEqualTo(form.getUserId());
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			ca.andStatusEqualTo(Constants.IS_VALIDFLAG);
			list706 = mi706Dao.selectByExample(m706e);			
		}
		List<AppApi70001Result> resultList = new ArrayList<AppApi70001Result>();		
		if(list706.size()>0){
			List list = new ArrayList();
			for(int i=0;i<list706.size();i++){
				list.add(list706.get(i).getNewsid());				
			}
			Mi701Example m701e=new Mi701Example();
			com.yondervision.mi.dto.Mi701Example.Criteria ca= m701e.createCriteria();
			ca.andCenteridEqualTo(form.getCenterId());
			ca.andSeqnoIn(list);
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			List<Mi701> list701 = cmi701Dao.selectByExampleWithBLOBs(m701e);
			Mi701 mi701;
			for(int j=0;j<list701.size();j++){
				AppApi70001Result api70001 = new AppApi70001Result();
				mi701 = list701.get(j);
				api70001.setTitleId(mi701.getSeqno().toString());
				api70001.setTitle(mi701.getTitle());
				api70001.setIntroduction(mi701.getIntroduction());
				if (!CommonUtil.isEmpty(mi701.getImage())){
					String turnImageUrl  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "turnImageUrl"+form.getCenterId());
					if(turnImageUrl.equals("1")){
						String pathYuan  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "serverPath"+form.getCenterId());
						String path  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appServerPath"+form.getCenterId());
						System.out.println("【新闻内容内网地址:"+pathYuan+"】");
						System.out.println("【新闻内容外网地址:"+path+"】");
					api70001.setImgUrl(mi701.getImage().replaceAll(pathYuan, path));
					}else{
						api70001.setImgUrl(mi701.getImage());
					}
				}else{
					api70001.setImgUrl("");
				}
				api70001.setReleaseDate(mi701.getReleasetime());
				api70001.setCollect(Constants.IS_VALIDFLAG);
				resultList.add(api70001);
			}
		}		
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	public int appapi70005(AppApi70004Form form) throws Exception {
		String[] news = form.getNewsId().split(",");
		List list = new ArrayList();		
		for(int i=0;i<news.length;i++){
			list.add(news[i]);
		}
		Mi706Example m706e=new Mi706Example();
		com.yondervision.mi.dto.Mi706Example.Criteria ca= m706e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterId());
		ca.andUseridEqualTo(form.getUserId());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		ca.andNewsidIn(list);
		Mi706 mi706 = new Mi706();
		mi706.setStatus("2");
		return mi706Dao.updateByExampleSelective(mi706, m706e);
	}
	
	@SuppressWarnings("unchecked")
	private String getValidDate(String centerid, String dicid){
		String validDate = null;
		Mi707Example mi707Example = new Mi707Example();
		mi707Example.createCriteria().andCenteridEqualTo(centerid)
		.andDicidEqualTo(Integer.parseInt(dicid))
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi707> mi707List = mi707Dao.selectByExample(mi707Example);
		if (0 != mi707List.size()){
			String validDateTmp = mi707List.get(0).getFreeuse1();
			if(CommonUtil.isEmpty(validDateTmp)){
				validDate = getValidDate(centerid, mi707List.get(0).getUpdicid().toString());
			}else{
				validDate = validDateTmp;
			}
		}
		return validDate;
	}
	
	/**
	 * 获取对应城市id下的所有有效栏目列表（不分开放关闭）
	 */
	@SuppressWarnings("unchecked")
	public List<Mi707> getClassificationList(String centerid,String channel) throws Exception {
		List<Mi707> classificationList = new ArrayList<Mi707>();
		Mi707Example mi707Example = new Mi707Example();
		mi707Example.createCriteria().andCenteridEqualTo(centerid)
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andFreeuse3Like("%"+channel+"%");
		mi707Example.setOrderByClause("abs(itemid) ASC");
		classificationList = mi707Dao.selectByExample(mi707Example);
		if (CommonUtil.isEmpty(classificationList)){
			Mi707Example mi707ExampleTmp = new Mi707Example();
			mi707ExampleTmp.createCriteria().andCenteridEqualTo("00000000")
			.andValidflagEqualTo(Constants.IS_VALIDFLAG).andFreeuse3Like("%"+channel+"%");
			mi707ExampleTmp.setOrderByClause("abs(itemid) ASC");
			classificationList = mi707Dao.selectByExample(mi707ExampleTmp);
		}
		return classificationList;
	}
	
	@SuppressWarnings("unchecked")
	public List<AppApi70004Result> appapi70006(AppApi70001Form form, HttpServletRequest request) throws Exception{
		List<Mi706> list706 = new ArrayList<Mi706>();
		if(!CommonUtil.isEmpty(form.getUserId())){
			Mi706Example m706e=new Mi706Example();
			com.yondervision.mi.dto.Mi706Example.Criteria ca= m706e.createCriteria();
			ca.andCenteridEqualTo(form.getCenterId());
			ca.andUseridEqualTo(form.getUserId());
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			ca.andStatusEqualTo(Constants.IS_VALIDFLAG);
			list706 = mi706Dao.selectByExample(m706e);			
		}
		String validDate = "";
		List<Mi707> mi707List = new ArrayList<Mi707>();
		Mi707Example mi707Exa = new Mi707Example();
		mi707Exa.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andItemidEqualTo(form.getClassification());
		mi707List = mi707Dao.selectByExample(mi707Exa);
		if (!CommonUtil.isEmpty(mi707List)){
			validDate = getValidDate(form.getCenterId(), mi707List.get(0).getDicid().toString());
		}
		
		List<Mi701WithBLOBs> list = cmi701Dao.selectMi701ByRandom(form, validDate,request.getAttribute("MI040Pid").toString());
		List<AppApi70004Result> resultList = new ArrayList<AppApi70004Result>();
		AppApi70004Result result;
		Mi701WithBLOBs mi701;
		for (int i = 0; i < list.size(); i++) {
			result = new AppApi70004Result();
			mi701 = list.get(i);
			result.setTitleId(mi701.getSeqno().toString());
			result.setTitle(mi701.getTitle());
			result.setIntroduction(mi701.getIntroduction());
			String contentTmp = mi701.getContent();
			System.out.println("mi701.getContent()===="+contentTmp);
			if(!CommonUtil.isEmpty(contentTmp)){
				result.setContent(contentTmp.replaceAll("\\&[a-zA-Z]{0,9};", "").replaceAll("<[^>]*>", ""));
			}else{
				result.setContent("");
			}
			if (!CommonUtil.isEmpty(mi701.getImage())){
				String turnImageUrl  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "turnImageUrl"+form.getCenterId());
				if(turnImageUrl.equals("1")){
					String pathYuan  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "serverPath"+form.getCenterId());
					String path  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appServerPath"+form.getCenterId());
					result.setImgUrl(mi701.getImage().replaceAll(pathYuan, path));
				}else{
					result.setImgUrl(mi701.getImage());
				}
			}else{
				result.setImgUrl("");
			}
			result.setReleaseDate(mi701.getReleasetime());
			
			if(!CommonUtil.isEmpty(form.getUserId())){
				for(int j=0;j<list706.size();j++){
					if(mi701.getSeqno()==list706.get(j).getNewsid()){
						result.setCollect(list706.get(j).getStatus());
						break;
					}
				}
			}	
			resultList.add(result);
		}

		return resultList;
	}
	
	//获取某一栏目下的某条新闻的详细内容，i为获取列表中的位置序号
	@SuppressWarnings("unchecked")
	public Mi701WithBLOBs appapi70007(String centerid, String classification, String num, String keyword, String seqno) throws Exception{
		Mi701WithBLOBs result = new Mi701WithBLOBs();
		
		Mi701Example mi701Example = new Mi701Example();
		mi701Example.setOrderByClause("seqno asc");
		Mi701Example.Criteria mi701Crt = mi701Example.createCriteria();
		mi701Crt.andCenteridEqualTo(centerid);
		mi701Crt.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		if(!CommonUtil.isEmpty(seqno)){
			result = cmi701Dao.selectByPrimaryKey(Integer.parseInt(seqno));
		}else if(!CommonUtil.isEmpty(num)){
			mi701Crt.andClassificationEqualTo(classification);
			List<Mi701WithBLOBs> list = cmi701Dao.selectByExampleWithBLOBs(mi701Example);
			if(!CommonUtil.isEmpty(list)){
				result = list.get(Integer.parseInt(num));
			}
		}else if(!CommonUtil.isEmpty(keyword)){
			mi701Crt.andClassificationEqualTo(classification);
			mi701Crt.andTitleLike("%"+keyword+"%");
			List<Mi701WithBLOBs> list = cmi701Dao.selectByExampleWithBLOBs(mi701Example);
			if(!CommonUtil.isEmpty(list)){
				result = list.get(0);
			}
		}

		return result;
	}
	
	//获取对应页面展示展示项的配置内容
	@SuppressWarnings("unchecked")
	public List<ViewItemBean> appapi70008(AppApi70008Form form) throws Exception{
		List<ViewItemBean> resultList = new ArrayList<ViewItemBean>();
		
		Mi704Example mi704Exa = new Mi704Example();
		mi704Exa.setOrderByClause("abs(itemid) asc");
		mi704Exa.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andUpdicidEqualTo("C"+form.getClassification().split("_")[1]);
		
		List<Mi704> mi704ParentList = new ArrayList<Mi704>();
		mi704ParentList = cmi704Dao.selectByExample(mi704Exa);
		
		if(CommonUtil.isEmpty(mi704ParentList)){
			return resultList;
		}else{
			if("1".equals(form.getClassification().split("_")[1])){
				ViewItemBean result = new ViewItemBean();
				result.setItemId("first");
				result.setItemVal("首页");
				result.setHasChild("false");
				result.setChildViewItemList(new ArrayList<ViewItemBean>());
				resultList.add(result);
			}

			resultList.addAll(getViewItem(form.getCenterId(), mi704ParentList));
		}
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	public AppApi70009Result appapi70009(AppApi70009Form form,String yingyong) throws Exception{
		AppApi70009Result result = new AppApi70009Result();
		List<ViewItemBean> childViewItemList = new ArrayList<ViewItemBean>();
		List<ViewItemBean> childViewItemListTmp = new ArrayList<ViewItemBean>();
		String curChildViewItemId = "";
		NewsBean news = new NewsBean();
		
		String keyword = null;
		if(!CommonUtil.isEmpty(form.getKeyword())){
			if("10".equals(form.getChannel())){
				keyword = form.getKeyword();
			}else{
				try {
					keyword = new String(form.getKeyword().getBytes("iso8859-1"),"UTF-8");
					
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (!CommonUtil.isEmpty(form.getParentViewItemId())){
			// 唯一的一条
			Mi707Example mi707Exa = new Mi707Example();
			mi707Exa.createCriteria().andCenteridEqualTo(form.getCenterId())
			.andItemidEqualTo(form.getParentViewItemId())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andStatusEqualTo(Constants.IS_OPEN);
			
			List<Mi707> mi707ParentList = new ArrayList<Mi707>();
			mi707ParentList = mi707Dao.selectByExample(mi707Exa);
			
			if(!CommonUtil.isEmpty(mi707ParentList)){
				
				Mi707Example mi707ChildExa = new Mi707Example();
				mi707ChildExa.setOrderByClause("abs(itemid) asc");
				mi707ChildExa.createCriteria().andCenteridEqualTo(form.getCenterId())
				.andUpdicidEqualTo(mi707ParentList.get(0).getDicid())
				.andFreeuse3Like("%"+yingyong+"%")
				.andValidflagEqualTo(Constants.IS_VALIDFLAG)
				.andStatusEqualTo(Constants.IS_OPEN);
				List<Mi707> mi707ChildList = new ArrayList<Mi707>();
				mi707ChildList = mi707Dao.selectByExample(mi707ChildExa);
				if(!CommonUtil.isEmpty(mi707ChildList)){
					
					childViewItemListTmp = getViewItemFromMi707(form.getCenterId(), mi707ChildList);
					
					String validDate = "";
					if(!"all".equals(form.getCurChildViewItemId())){
						if(CommonUtil.isEmpty(form.getCurChildViewItemId())){
							curChildViewItemId = mi707ChildList.get(0).getItemid();
						}else {
							curChildViewItemId = form.getCurChildViewItemId();	
						}

						Mi707Example mi707NewsExa = new Mi707Example();
						mi707NewsExa.createCriteria().andCenteridEqualTo(form.getCenterId())
						.andValidflagEqualTo(Constants.IS_VALIDFLAG)
						.andItemidEqualTo(curChildViewItemId)
						.andStatusEqualTo(Constants.IS_OPEN);
						List<Mi707> mi707List = mi707Dao.selectByExample(mi707Exa);
						if (!CommonUtil.isEmpty(mi707List)){
							validDate = getValidDate(form.getCenterId(), mi707List.get(0).getDicid().toString());
						}

						news = cmi701Dao.selectMi701ToApp(form.getCenterId(), form.getChannel(), curChildViewItemId,
								keyword, form.getPagenum(), form.getPagerows(), validDate,yingyong);
						childViewItemList.addAll(childViewItemListTmp);
					}else {
						List<String> classList = new ArrayList<String>();
						classList.add(form.getParentViewItemId());
						classList.addAll(getclassList(childViewItemListTmp));
						
						validDate = getValidDate(form.getCenterId(), mi707ParentList.get(0).getDicid().toString());
						
						news = cmi701Dao.selectMi701ForAllClassfication(form.getCenterId(), form.getChannel(), classList,
								keyword, form.getPagenum(), form.getPagerows(), validDate,yingyong);	
						ViewItemBean itemBean = new ViewItemBean();
						itemBean.setItemId("all");
						itemBean.setItemVal("all");
						itemBean.setHasChild("false");
						itemBean.setChildViewItemList(new ArrayList<ViewItemBean>());
						childViewItemList.add(itemBean);
						childViewItemList.addAll(childViewItemListTmp);
					}
				}else{
					String validDate = "";
					if(!CommonUtil.isEmpty(form.getCurChildViewItemId())
							&& !"all".equals(form.getCurChildViewItemId())){
						curChildViewItemId = form.getCurChildViewItemId();
					}else{
						curChildViewItemId = form.getParentViewItemId();
					}
					Mi707Example mi707NewsExa = new Mi707Example();
					mi707NewsExa.createCriteria().andCenteridEqualTo(form.getCenterId())
					.andValidflagEqualTo(Constants.IS_VALIDFLAG)
					.andItemidEqualTo(curChildViewItemId);
					List<Mi707> mi707List = mi707Dao.selectByExample(mi707Exa);
					if (!CommonUtil.isEmpty(mi707List)){
						validDate = getValidDate(form.getCenterId(), mi707List.get(0).getDicid().toString());
					}

					news = cmi701Dao.selectMi701ToApp(form.getCenterId(), form.getChannel(), curChildViewItemId,
							keyword, form.getPagenum(), form.getPagerows(), validDate,yingyong);
				}

			}
		}else{
			if(!"all".equals(form.getCurChildViewItemId())){
				curChildViewItemId = form.getCurChildViewItemId();
			}
			
			news = cmi701Dao.selectMi701ToApp(form.getCenterId(), form.getChannel(), curChildViewItemId,
					keyword, form.getPagenum(), form.getPagerows(), "",yingyong);
			
		}

		if("all".equals(form.getCurChildViewItemId())){
			curChildViewItemId = form.getCurChildViewItemId();
		}
		result.setChildViewItemList(childViewItemList);
		result.setCurChildViewItemId(curChildViewItemId);
		result.setNews(news);
		return result;
	}
	
	//获取对应itemid的所有归属父级列表
	@SuppressWarnings("unchecked")
	public AppApi70010Result appapi70010(AppApi70010Form form) throws Exception{
		AppApi70010Result result = new AppApi70010Result();
		String curViewItemId = "";
		String curViewItemVal = "";
		
		curViewItemId = form.getCurViewItemId();
		
		Mi707Example mi707Exa = new Mi707Example();
		mi707Exa.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andItemidEqualTo(form.getCurViewItemId())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andStatusEqualTo(Constants.IS_OPEN);
		
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		List<Map<String, String>> resultTmpList = new ArrayList<Map<String, String>>();
		Map<String, String> itemMap = new HashMap<String, String>();
		
		List<Mi707> mi707CurList = new ArrayList<Mi707>();
		mi707CurList = mi707Dao.selectByExample(mi707Exa);
		if(!CommonUtil.isEmpty(mi707CurList)){
			Mi707 curItemInfo = mi707CurList.get(0);
			curViewItemVal = curItemInfo.getItemval();
			itemMap.put("itemid", curItemInfo.getItemid());
			itemMap.put("itemval", curItemInfo.getItemval());
			resultTmpList.add(itemMap);
			if(!"0".equals(curItemInfo.getUpdicid())){
				resultTmpList = getRootItemInfoFromMi707(form.getCenterId(), curItemInfo, resultTmpList);
			}
		}
		
		for(int i = resultTmpList.size()-1; i>=0; i--){
			resultList.add(resultTmpList.get(i));
		}
		result.setParentViewItemList(resultList);
		result.setCurViewItemId(curViewItemId);
		result.setCurViewItemVal(curViewItemVal);
		return result;
	}
	
	// 获取对应配置项列表中各元素的子项Mi704
	@SuppressWarnings("unchecked")
	private List<ViewItemBean> getViewItem(String centerid, List<Mi704> mi704List){
		List<ViewItemBean> resultList = new ArrayList<ViewItemBean>();
		ViewItemBean result = new ViewItemBean();
		for(int i = 0; i < mi704List.size(); i++){
			Mi704 mi704 = mi704List.get(i);
			
			result = new ViewItemBean();
			
			result.setItemId(mi704.getItemid());
			result.setItemVal(mi704.getItemval());

			//判断是否有无子项
			Mi704Example mi704ChildExa = new Mi704Example();
			mi704ChildExa.setOrderByClause("abs(itemid) asc");
			mi704ChildExa.createCriteria().andCenteridEqualTo(centerid)
			.andUpdicidEqualTo(mi704.getDicid());
			
			List<ViewItemBean> childViewItemList = new ArrayList<ViewItemBean>();
			
			List<Mi704> mi704ChildList = new ArrayList<Mi704>();
			mi704ChildList = cmi704Dao.selectByExample(mi704ChildExa);
			if(!CommonUtil.isEmpty(mi704ChildList)){
				result.setHasChild("true");
				childViewItemList = getViewItem(centerid, mi704ChildList);
			}else{
				result.setHasChild("false");
				
			}
			result.setChildViewItemList(childViewItemList);
			resultList.add(result);
		}
		
		return resultList;
	}
	
	// 获取对应配置项列表中各元素的子项
	@SuppressWarnings("unchecked")
	private List<ViewItemBean> getViewItemFromMi707(String centerid, List<Mi707> mi707List){
		List<ViewItemBean> resultList = new ArrayList<ViewItemBean>();
		ViewItemBean result = new ViewItemBean();
		for(int i = 0; i < mi707List.size(); i++){
			Mi707 mi707 = mi707List.get(i);
			
			result = new ViewItemBean();
			
			result.setItemId(mi707.getItemid());
			result.setItemVal(mi707.getItemval());

			//判断是否有无子项
			Mi707Example mi707ChildExa = new Mi707Example();
			mi707ChildExa.setOrderByClause("abs(itemid) asc");
			mi707ChildExa.createCriteria().andCenteridEqualTo(centerid)
			.andUpdicidEqualTo(mi707.getDicid())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andStatusEqualTo(Constants.IS_OPEN);
			
			List<ViewItemBean> childViewItemList = new ArrayList<ViewItemBean>();
			
			List<Mi707> mi707ChildList = new ArrayList<Mi707>();
			mi707ChildList = mi707Dao.selectByExample(mi707ChildExa);
			if(!CommonUtil.isEmpty(mi707ChildList)){
				result.setHasChild("true");
				childViewItemList = getViewItemFromMi707(centerid, mi707ChildList);
			}else{
				result.setHasChild("false");
				
			}
			result.setChildViewItemList(childViewItemList);
			resultList.add(result);
		}
		
		return resultList;
	}
	
	// 获取对应配置项的最父级的信息记录
	@SuppressWarnings("unchecked")
	private List<Map<String, String>> getRootItemInfoFromMi707(String centerid, Mi707 mi707, List<Map<String, String>> curItemMapList){
		
		Map<String, String> itemMap = new HashMap<String, String>();
		
		Mi707Example mi707Exa = new Mi707Example();
		mi707Exa.createCriteria().andCenteridEqualTo(centerid)
		.andDicidEqualTo(mi707.getUpdicid())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andStatusEqualTo(Constants.IS_OPEN);
		List<Mi707> curList = mi707Dao.selectByExample(mi707Exa);
		if(!CommonUtil.isEmpty(curList)){
			Mi707 curItemInfo = curList.get(0);
			itemMap.put("itemid", curItemInfo.getItemid());
			itemMap.put("itemval", curItemInfo.getItemval());
			curItemMapList.add(itemMap);
			if(0 == curItemInfo.getUpdicid()){
				return curItemMapList;
			}else{
				curItemMapList = getRootItemInfoFromMi707(centerid, curItemInfo, curItemMapList);
			}
		}
		
		return curItemMapList;

	}
	
	//获取某一栏目（包含此栏目）下所有子栏目包含的内容列表，按发布时间倒序
	@SuppressWarnings("unchecked")
	public NewsBean appapi70012(AppApi70012Form form, HttpServletRequest request) throws Exception{
		NewsBean result  = new NewsBean();
		
		List<ViewItemBean> childViewItemList = new ArrayList<ViewItemBean>();
		
		String validDate = "";
		Mi707Example mi707Exa = new Mi707Example();
		mi707Exa.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andItemidEqualTo(form.getClassification());
		List<Mi707> mi707List = mi707Dao.selectByExample(mi707Exa);
		if (!CommonUtil.isEmpty(mi707List)){
			validDate = getValidDate(form.getCenterId(), mi707List.get(0).getDicid().toString());
		}
		List<String> classList = new ArrayList<String>();
		classList.add(form.getClassification());
		
		// 获取该栏目下所有子栏目
		Mi707Example mi707ChildExa = new Mi707Example();
		mi707ChildExa.setOrderByClause("abs(itemid) asc");
		mi707ChildExa.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andUpdicidEqualTo(mi707List.get(0).getDicid())
		.andFreeuse3Like("%"+request.getAttribute("MI040Pid").toString()+"%")
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andStatusEqualTo(Constants.IS_OPEN);
		List<Mi707> mi707ChildList = new ArrayList<Mi707>();
		mi707ChildList = mi707Dao.selectByExample(mi707ChildExa);
		if(!CommonUtil.isEmpty(mi707ChildList)){
			childViewItemList = getViewItemFromMi707(form.getCenterId(), mi707ChildList);
		}
		
		classList.addAll(getclassList(childViewItemList));
		
		result = cmi701Dao.selectMi701ForAllClassfication(form.getCenterId(), form.getChannel(), classList,
				form.getKeyword(), form.getPagenum(), form.getPagerows(), validDate,request.getAttribute("MI040Pid").toString());
		
		return result;
	}
	
	private List<String> getclassList(List<ViewItemBean> childViewItemList){
		List<String> classList = new ArrayList<String>();
		for(int i = 0; i < childViewItemList.size(); i++){
			ViewItemBean bean = childViewItemList.get(i);
			if("true".equals(bean.getHasChild())){
				classList.add(bean.getItemId());
				classList.addAll(getclassList(bean.getChildViewItemList()));
			}else{
				classList.add(bean.getItemId());
			}
		}
		return classList;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<AppApi70013Result> appapi70013(AppApi70009Form form,HttpServletRequest request) throws Exception{
		List<AppApi70013Result> resultList = new ArrayList<AppApi70013Result>();
		
		AppApi70013Result result = new AppApi70013Result();
		List<ViewItemAndDetailBean> childViewItemList = new ArrayList<ViewItemAndDetailBean>();
//		String curChildViewItemId = "";
		NewsBean news = new NewsBean();
		
		String keyword = null;
		if(!CommonUtil.isEmpty(form.getKeyword())){
			if("10".equals(form.getChannel())){
				keyword = form.getKeyword();
			}else{
				try {
					keyword = new String(form.getKeyword().getBytes("iso8859-1"),"UTF-8");
					
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		
		// 唯一的一条
		Mi707Example mi707Exa = new Mi707Example();
		mi707Exa.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andItemidEqualTo(form.getParentViewItemId())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andStatusEqualTo(Constants.IS_OPEN);
		
		List<Mi707> mi707ParentList = new ArrayList<Mi707>();
		mi707ParentList = mi707Dao.selectByExample(mi707Exa);
		
		if(!CommonUtil.isEmpty(mi707ParentList)){
			
			Mi707Example mi707ChildExa = new Mi707Example();
			mi707ChildExa.setOrderByClause("abs(itemid) asc");
			mi707ChildExa.createCriteria().andCenteridEqualTo(form.getCenterId())
			.andUpdicidEqualTo(mi707ParentList.get(0).getDicid())
			.andFreeuse3Like("%"+request.getAttribute("MI040Pid").toString()+"%")
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andStatusEqualTo(Constants.IS_OPEN);
			List<Mi707> mi707ChildList = new ArrayList<Mi707>();
			mi707ChildList = mi707Dao.selectByExample(mi707ChildExa);
			String itemId = "";
			if(!CommonUtil.isEmpty(mi707ChildList)){
				
				for(int i = 0; i < mi707ChildList.size(); i++){
					result = new AppApi70013Result();
					childViewItemList = getViewItemFromMi707AndDetail(form.getCenterId(), mi707ChildList,
							form.getChannel(), form.getPagenum(), form.getPagerows(),request.getAttribute("MI040Pid").toString());
					
					String validDate = "";
					itemId = mi707ChildList.get(i).getItemid();
					result.setItemId(itemId);
					result.setItemVal(mi707ChildList.get(i).getItemval());
					if(CommonUtil.isEmpty(childViewItemList)){
						result.setHasChild("false");
					}else{
						result.setHasChild("true");
					}
					
					
					Mi707Example mi707NewsExa = new Mi707Example();
					mi707NewsExa.createCriteria().andCenteridEqualTo(form.getCenterId())
					.andValidflagEqualTo(Constants.IS_VALIDFLAG)
					.andItemidEqualTo(itemId)
					.andStatusEqualTo(Constants.IS_OPEN);
					List<Mi707> mi707List = mi707Dao.selectByExample(mi707Exa);
					if (!CommonUtil.isEmpty(mi707List)){
						validDate = getValidDate(form.getCenterId(), mi707List.get(0).getDicid().toString());
					}

					news = cmi701Dao.selectMi701ToAppForPageOrAll(form.getCenterId(), form.getChannel(), itemId,
							keyword, form.getPagenum(), form.getPagerows(), validDate,request.getAttribute("MI040Pid").toString());
					result.setNews(news);
					result.setChildViewItemList(childViewItemList);
					resultList.add(result);
					
				}
			}else{
				String validDate = "";
				itemId = form.getParentViewItemId();
				
				Mi707Example mi707NewsExa = new Mi707Example();
				mi707NewsExa.createCriteria().andCenteridEqualTo(form.getCenterId())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG)
				.andItemidEqualTo(itemId);
				List<Mi707> mi707List = mi707Dao.selectByExample(mi707Exa);
				if (!CommonUtil.isEmpty(mi707List)){
					validDate = getValidDate(form.getCenterId(), mi707List.get(0).getDicid().toString());
				}

				news = cmi701Dao.selectMi701ToAppForPageOrAll(form.getCenterId(), form.getChannel(), itemId,
						keyword, form.getPagenum(), form.getPagerows(), validDate,request.getAttribute("MI040Pid").toString());
				
				result.setItemId(itemId);
				result.setItemVal(mi707List.get(0).getItemval());
				result.setHasChild("false");
				result.setNews(news);
				result.setChildViewItemList(childViewItemList);
				resultList.add(result);
			}

		}
		
		return resultList;
	}
	
	// 获取对应配置项列表中各元素的子项
	@SuppressWarnings("unchecked")
	private List<ViewItemAndDetailBean> getViewItemFromMi707AndDetail(String centerid, List<Mi707> mi707List,
			String channel, String pagenum, String pagerows,String yingyong) throws Exception {
		List<ViewItemAndDetailBean> resultList = new ArrayList<ViewItemAndDetailBean>();
		ViewItemAndDetailBean result = new ViewItemAndDetailBean();
		for(int i = 0; i < mi707List.size(); i++){
			Mi707 mi707 = mi707List.get(i);
			
			result = new ViewItemAndDetailBean();
			
			result.setItemId(mi707.getItemid());
			result.setItemVal(mi707.getItemval());

			//判断是否有无子项
			Mi707Example mi707ChildExa = new Mi707Example();
			mi707ChildExa.setOrderByClause("abs(itemid) asc");
			mi707ChildExa.createCriteria().andCenteridEqualTo(centerid)
			.andUpdicidEqualTo(mi707.getDicid())
			.andFreeuse3Like("%"+yingyong+"%")
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andStatusEqualTo(Constants.IS_OPEN);
			
			List<ViewItemAndDetailBean> childViewItemList = new ArrayList<ViewItemAndDetailBean>();
			
			List<Mi707> mi707ChildList = new ArrayList<Mi707>();
			mi707ChildList = mi707Dao.selectByExample(mi707ChildExa);
			if(!CommonUtil.isEmpty(mi707ChildList)){
				result.setHasChild("true");
				childViewItemList = getViewItemFromMi707AndDetail(centerid, mi707ChildList, channel, pagenum, pagerows,yingyong);
			}else{
				result.setHasChild("false");
				
			}
			String validDate = getValidDate(centerid, mi707.getDicid().toString());

			NewsBean news = new NewsBean();
			news = cmi701Dao.selectMi701ToAppForPageOrAll(centerid, channel, mi707.getItemid(),
					"", pagenum, pagerows, validDate,yingyong);
			
			result.setNews(news);
			result.setChildViewItemList(childViewItemList);
			resultList.add(result);
		}
		
		return resultList;
	}
}
