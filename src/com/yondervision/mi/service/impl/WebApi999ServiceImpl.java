package com.yondervision.mi.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CJobOffersDAO;
import com.yondervision.mi.dto.JobOffers;
import com.yondervision.mi.dto.JobOffersExample;
import com.yondervision.mi.form.ItemInfo;
import com.yondervision.mi.form.WebApi99901Form;
import com.yondervision.mi.form.WebApi99902Form;
import com.yondervision.mi.result.WebApi99901_queryResult;
import com.yondervision.mi.service.WebApi999Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;

/** 
* @ClassName: WebApi999ServiceImpl 
* @Description: 应聘信息管理
* @author gongqi
* 
*/ 
public class WebApi999ServiceImpl implements WebApi999Service {
	@Autowired
	public CJobOffersDAO cJobOffersDAO = null;
	public void setcJobOffersDAO(CJobOffersDAO cJobOffersDAO) {
		this.cJobOffersDAO = cJobOffersDAO;
	}
	
	// 遍历当前名称节点
	@SuppressWarnings("unchecked")
	private ItemInfo getCurNodes(Element node){
		
		//当前节点的名称、文本内容和属性
		List<Attribute> listAttr=node.attributes();//当前节点的所有属性的list
		
		ItemInfo item = new ItemInfo();
		for(Attribute attr:listAttr){//遍历当前节点的所有属性
			String name=attr.getName();//属性名称
			String value=attr.getValue();//属性的值
			
			if("code".equals(name)){
				item.setItemid(value);
			}
			if("name".equals(name)){
				item.setItemval(value);
			}
		}
		return item;
	}
	
	// 遍历当前名称节点的子节点
	@SuppressWarnings("unchecked")
	private List<ItemInfo> getCurChildNodes(Element node){
		List<ItemInfo> itemList = new ArrayList<ItemInfo>();
		
		//递归遍历当前节点所有的子节点
		List<Element> listElement = node.elements();//所有一级子节点的list
		for(Element e:listElement){//遍历所有一级子节点
			ItemInfo item = new ItemInfo();
			item = getCurNodes(e);
			itemList.add(item);
		}
		
		return itemList;
	}
	
	/**
	 * 应聘区域列表获取
	 */
	@SuppressWarnings("unchecked")
	public List<ItemInfo> getApplyAreaList(String nodeName) throws Exception{
		List<ItemInfo> itemList = new ArrayList<ItemInfo>();
		
//		System.out.println("classpath==="+WebApi999ServiceImpl.class.getResource("").toURI().getPath());
//		String path = WebApi999ServiceImpl.class.getResource("").toURI().getPath();
//		int startindex = path.indexOf("/YBMAP");
//		System.out.println("startindex==="+startindex);
		String path = PropertiesReader.getProperty("properties.properties", "JOB_OFFERS_XML");
		SAXReader sax=new SAXReader();//创建一个SAXReader对象
		File xmlFile=new File(path);//根据指定的路径创建file对象
		Document document=sax.read(xmlFile);//获取document对象,如果文档无节点，则会抛出Exception提前结束
		Element root=document.getRootElement();//获取根节点
		List skills = root.elements(nodeName);
		for (Iterator<?> it = skills.iterator(); it.hasNext();) {
			ItemInfo item = new ItemInfo();
			Element e = (Element) it.next();
			item = getCurNodes(e);//从只定节点开始遍历所有此名称节点
			itemList.add(item);
		}
		return itemList;
	}
	
	/**
	 * 应聘职位列表获取
	 */
	@SuppressWarnings("unchecked")
	public List<ItemInfo> getApplyPositionList(String nodeName, String code) throws Exception{
		List<ItemInfo> childItemList = new ArrayList<ItemInfo>();
		String path = PropertiesReader.getProperty("properties.properties", "JOB_OFFERS_XML");
		SAXReader sax=new SAXReader();//创建一个SAXReader对象
		//File xmlFile=new File("E://workspaces//Area.xml");//根据指定的路径创建file对象
		File xmlFile=new File(path);//根据指定的路径创建file对象
		Document document=sax.read(xmlFile);//获取document对象,如果文档无节点，则会抛出Exception提前结束
		Element root=document.getRootElement();//获取根节点
		List skills = root.elements(nodeName);
		
		for (Iterator<?> it = skills.iterator(); it.hasNext();) {
			Element e = (Element) it.next();
			ItemInfo item = new ItemInfo();
			item = getCurNodes(e);
			if(code.equals(item.getItemid())){
				childItemList = getCurChildNodes(e);//从只定节点开始遍历所有此名称节点
			}

		}
		return childItemList;
	}
	
	/**
	 * 应聘信息查询
	 */
	public WebApi99901_queryResult webapi99901(WebApi99901Form form) throws Exception{
		WebApi99901_queryResult queryResult = cJobOffersDAO.selectJobOffersPage(form);
		return queryResult;
	}
	
	/**
	 * 应聘信息已读设置
	 */
	public int webapi99902(WebApi99902Form form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		// 传入参数非空校验
		if (CommonUtil.isEmpty(form.getSeqnos())) {
			log.error(ERROR.PARAMS_NULL.getLogText("序号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"序号获取失败");
		}
		
		String[] seqnos = form.getSeqnos().split(",");
		List<String> seqnoList = new ArrayList<String>();
		for (int i = 0; i < seqnos.length; i++) {
			seqnoList.add(seqnos[i]);
		}
		
		JobOffers jobOffers = new JobOffers();
		// 修改时间
		jobOffers.setDatemodified(CommonUtil.getSystemDate());
		// 已读未读标记
		jobOffers.setFreeuse1(Constants.IS_VALIDFLAG);
		
		JobOffersExample example = new JobOffersExample();
		example.createCriteria().andSeqnoIn(seqnoList);
		
		int result = cJobOffersDAO.updateByExampleSelective(jobOffers, example);
		
		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("序号"+form.getSeqnos()));
			throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(),"应聘信息");
		}
		return result;
	}
	
	/**
	 * 应聘信息删除
	 */
	public int webapi99903(WebApi99902Form form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		// 传入参数非空校验
		if (CommonUtil.isEmpty(form.getSeqnos())) {
			log.error(ERROR.PARAMS_NULL.getLogText("序号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"序号获取失败");
		}
		
		String[] seqnos = form.getSeqnos().split(",");
		List<String> seqnoList = new ArrayList<String>();
		for (int i = 0; i < seqnos.length; i++) {
			seqnoList.add(seqnos[i]);
		}
		
		JobOffers jobOffers = new JobOffers();
		// 修改时间
		jobOffers.setDatemodified(CommonUtil.getSystemDate());
		// 有效标记
		jobOffers.setValidflag(Constants.IS_NOT_VALIDFLAG);
		
		JobOffersExample example = new JobOffersExample();
		example.createCriteria().andSeqnoIn(seqnoList);
		
		int result = cJobOffersDAO.updateByExampleSelective(jobOffers, example);
		
		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("序号"+form.getSeqnos()));
			throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(),"应聘信息");
		}
		return result;
	}
	
	/**
	 * 应聘信息查询
	 */
	@SuppressWarnings("unchecked")
	public List<JobOffers> webapi99904(WebApi99901Form form) throws Exception{
		List<JobOffers> resultList = new ArrayList<JobOffers>();
		JobOffersExample jobOffersExample = new JobOffersExample();
		jobOffersExample.setOrderByClause("FREEUSE1 ASC, SEQNO DESC");
		JobOffersExample.Criteria ca = jobOffersExample.createCriteria();
		if(!CommonUtil.isEmpty(form.getCenterId())){
			if (!"00000000".equals(form.getCenterId())) {
				ca.andCenteridEqualTo(form.getCenterId());
			}
		}else{
			UserContext user = UserContext.getInstance();
			String centerid = user.getCenterid();
			if (!"00000000".equals(form.getCenterId())) {
				ca.andCenteridEqualTo(centerid);
			}
		}
		if(!CommonUtil.isEmpty(form.getApplyareaQry())){
			ca.andApplyareaEqualTo(form.getApplyareaQry());
		}
		if(!CommonUtil.isEmpty(form.getApplypositionQry())){
			ca.andApplypositionEqualTo(form.getApplypositionQry());
		}
		if(!CommonUtil.isEmpty(form.getStartdate())){
			ca.andDatecreatedGreaterThanOrEqualTo(form.getStartdate()+" 00:00:00.000");
		}
		if(!CommonUtil.isEmpty(form.getEnddate())){
			ca.andDatecreatedLessThanOrEqualTo(form.getEnddate()+" 23:59:59.999");
		}
		if(!CommonUtil.isEmpty(form.getIsread())){
			ca.andFreeuse1EqualTo(form.getIsread());
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);	
		resultList = cJobOffersDAO.selectByExample(jobOffersExample);
		return resultList;
	}
	
	/**
	 * 应聘信息查询
	 */
	@SuppressWarnings("unchecked")
	public List<JobOffers> webapi99905(String seqno) throws Exception{
		List<JobOffers> resultList = new ArrayList<JobOffers>();
		String[] seqnosStr = seqno.split(",");
		List seqnoList = new ArrayList();
		for(int i = 0 ; i < seqnosStr.length; i++){
			seqnoList.add(seqnosStr[i]);
		}
		JobOffersExample jobOffersExample = new JobOffersExample();
		jobOffersExample.setOrderByClause("SEQNO DESC");
		JobOffersExample.Criteria ca = jobOffersExample.createCriteria();
		ca.andSeqnoIn(seqnoList);
		resultList = cJobOffersDAO.selectByExample(jobOffersExample);
		
		return resultList;
	}
}
