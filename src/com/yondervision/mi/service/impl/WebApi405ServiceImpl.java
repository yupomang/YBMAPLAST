package com.yondervision.mi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi405DAO;
import com.yondervision.mi.dao.Mi405DAO;
import com.yondervision.mi.dao.Mi406DAO;
import com.yondervision.mi.dto.Mi405;
import com.yondervision.mi.dto.Mi405Example;
import com.yondervision.mi.dto.Mi406;
import com.yondervision.mi.dto.Mi406Example;
import com.yondervision.mi.result.WebApi40502_queryResult;
import com.yondervision.mi.service.WebApi405Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi304ServiceImpl 
* @Description: 渠道配置、信息组装模板、个人短信、公共消息维护的服务
* @author syw
* @date 2015-09-14  
* 
*/ 
@SuppressWarnings("unchecked")
public class WebApi405ServiceImpl implements WebApi405Service {
	
	@Autowired
	private Mi405DAO mi405Dao;
	
	@Autowired
	private Mi406DAO mi406Dao;
	
	@Autowired
	private CMi405DAO cmi405Dao;
	
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	
	public CommonUtil getCommonUtil() {
		return commonUtil;
	}	

	public Mi405DAO getMi405Dao() {
		return mi405Dao;
	}

	public void setMi405Dao(Mi405DAO mi405Dao) {
		this.mi405Dao = mi405Dao;
	}

	public Mi406DAO getMi406Dao() {
		return mi406Dao;
	}

	public void setMi406Dao(Mi406DAO mi406Dao) {
		this.mi406Dao = mi406Dao;
	}

	public CMi405DAO getCmi405Dao() {
		return cmi405Dao;
	}

	public void setCmi405Dao(CMi405DAO cmi405Dao) {
		this.cmi405Dao = cmi405Dao;
	}

	public List<Mi405> getMi405AllList() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addMi405(Mi405 mi405) {
		// TODO Auto-generated method stub
		
	}

	public void delMi405(List<String> list) {
		// TODO Auto-generated method stub
		
	}

	public void updateRule(Mi405 mi405) {
		// TODO Auto-generated method stub
		
	}

	public WebApi40502_queryResult getMi405AllList(String page, String rows) {
		Mi405Example example = new Mi405Example();
		example.createCriteria().andValidflagEqualTo(Constants.IS_VALIDFLAG);
		example.setOrderByClause("TEMPLATE_ID asc");
		return cmi405Dao.select40502Page(example,page,rows);
		
	}

	public void buildMi406(Mi405 mi405) {
		updateTemplate(mi405);//首先把mi405更新一下
		String content = mi405.getTemplateContent();
		String templateId = mi405.getTemplateId();
		if (CommonUtil.isEmpty(templateId)) {
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(), "模板不存在不能生成要素，请先保存模板！");
		}
		Mi406Example example1 = new Mi406Example();
		example1.createCriteria().andTemplateIdEqualTo(templateId);
		List<Mi406> l = mi406Dao.selectByExample(example1);
		for (int i = 0; i < l.size(); i++) {
			mi406Dao.deleteByPrimaryKey(l.get(i).getTemplateDetailId());
		}
		int mi406Count=0;
		// 先根据｝截取
		String[] templateDetailName = content.split("}");// 如:xx{name1,xx{name2,xx{name3,xxxx
		for (int i = 0; i < templateDetailName.length; i++) {
			// 再根据｛截取，后半部分则为关键词
			String[] t = templateDetailName[i].split("\\{");
			if (t.length > 1 && (!"".equals(t[1].trim()))) {
				Mi406 mtd = new Mi406();
				mtd.setTemplateId(templateId);
				mtd.setDatecreated(CommonUtil.getSystemDate());
				try {
					mtd.setTemplateDetailId(commonUtil.genKey("MI406") + "");
				} catch (Exception e) {
					e.printStackTrace();
				}
				mtd.setValidflag(Constants.IS_VALIDFLAG);
				mtd.setTemplateDetailName(t[1]);
				mtd.setFreeuse4(mi406Count);
				mtd.setSerialNumber(mi406Count+"");
				mi406Dao.insert(mtd); 
				mi406Count++;
			}
		}
	}

	public void updateTemplate(Mi405 mi405) {
		Mi405 m2 = mi405Dao.selectByPrimaryKey(mi405.getTemplateId());
		//如果当前模版号有了变化
		if(!mi405.getTemplateCode().equals(m2.getTemplateCode()))
		{
			Logger log = LoggerUtil.getLogger();
			// 校验新增信息已经存在
			int count = getMessageTemplateCounts(mi405);
			if(0 != count){
				log.error(ERROR.ADD_CHECK.getLogText(CommonUtil.getStringParams(mi405)));
				throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"此模版编号已存在，请确认后提交！");
			}
		}
		Mi405Example example3=new Mi405Example();
		example3.createCriteria().andTemplateIdEqualTo(mi405.getTemplateId());
		m2.setDatemodified(CommonUtil.getSystemDate());
		m2.setValidflag(Constants.IS_VALIDFLAG);
		m2.setTemplateContent(mi405.getTemplateContent());
		m2.setDatecreated(mi405.getDatecreated());
		m2.setTemplateCode(mi405.getTemplateCode());
		m2.setCenterid(mi405.getCenterid());
		m2.setChannel(mi405.getChannel());
		m2.setTemplateName(mi405.getTemplateName());
		m2.setBatchkeynum(mi405.getBatchkeynum());
		m2.setBatchsplit(mi405.getBatchsplit());
		m2.setBatchstartnum(mi405.getBatchstartnum());
		m2.setFreeuse1(mi405.getFreeuse1());
		m2.setFreeuse2(mi405.getFreeuse2());
		m2.setFreeuse3(mi405.getFreeuse3());
		m2.setFreeuse4(mi405.getFreeuse4());
//		if(!m.getTemplateContent().equals(messageTemplate.getTemplateContent()))
//		{
//			deleteTemplateDetail(messageTemplate.getTemplateId());
//			saveTemplateDetail(messageTemplate.getTemplateContent(),messageTemplate.getTemplateId());
//		}
		mi405Dao.updateByExample(m2, example3); 
		
	}
	
	
	
	private int getMessageTemplateCounts(Mi405 mi405){
		int counts = 0;
		Mi405Example example = new Mi405Example();
		example.createCriteria().andValidflagEqualTo(Constants.IS_VALIDFLAG).andTemplateCodeEqualTo(mi405.getTemplateCode());
		counts = mi405Dao.countByExample(example);
		return counts;
	}

	public String addMessageTemplate(Mi405 mi405) {
		Logger log = LoggerUtil.getLogger();
		// 校验新增信息已经存在
		int count = getMessageTemplateCounts(mi405);
		if(0 != count){
			log.error(ERROR.ADD_CHECK.getLogText(CommonUtil.getStringParams(mi405)));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"此模版编号已存在，请确认后提交！");
		}
		try {
			String templateId = commonUtil.genKey("MI405")+"";
			mi405.setTemplateId(templateId);
			mi405.setValidflag(Constants.IS_VALIDFLAG);
			mi405.setDatecreated(CommonUtil.getSystemDate());
			mi405Dao.insert(mi405);
			saveTemplateDetail(mi405.getTemplateContent(),mi405.getTemplateId());
			return templateId;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private void saveTemplateDetail(String templateDetail,String templateId)
	{
		String[] templateDetailName = templateDetail.split("}");
		int count=0;
		for(int i=0;i<templateDetailName.length;i++)
		{
			String[] t = templateDetailName[i].split("\\{");
			if(t.length>1)
			{
				Mi406 mtd = new Mi406();
				try {
					mtd.setTemplateId(templateId);
					mtd.setDatecreated(CommonUtil.getSystemDate());
					mtd.setTemplateDetailId(commonUtil.genKey("MI406")+"");
					mtd.setValidflag(Constants.IS_VALIDFLAG);
					mtd.setTemplateDetailName(t[1]);
					mtd.setFreeuse4(count);
					mtd.setSerialNumber(count+"");
					mi406Dao.insert(mtd);
					count++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void delTemplate(List<String> list) {
//		MessageSendExample e = new MessageSendExample();
//		e.createCriteria().andTemplateIdIn(list);
//		List<MessageSend> ms = messageSendDao.selectByExampleWithBLOBs(e);
//		if(ms.size()>0)
//		{
//			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"信息模板已被使用，不能删除！");
//		}
		Mi405Example example=new Mi405Example();
		example.createCriteria().andTemplateIdIn(list);
		List<Mi405> mr = mi405Dao.selectByExample(example);
		for(int i=0;i<mr.size();i++)
		{
			Mi405 m = mr.get(i);
			m.setValidflag(Constants.IS_NOT_VALIDFLAG);
			m.setDatemodified(CommonUtil.getSystemDate());
			Mi405Example example1=new Mi405Example();
			example1.createCriteria().andTemplateIdEqualTo(m.getTemplateId());
			deleteMi406(m.getTemplateId());
			mi405Dao.updateByExample(m,example1); 
		}
		
	}
	
	
	/**
	 * 删除模版要素
	 * @param templateId
	 */
	private void deleteMi406(String templateId) {
		Mi406Example example = new Mi406Example();		
		example.createCriteria().andTemplateIdEqualTo(templateId);
		List<Mi406> mi406List = mi406Dao.selectByExample(example);
		for(Mi406 m:mi406List){
			m.setValidflag(Constants.IS_NOT_VALIDFLAG);
			m.setDatemodified(CommonUtil.getSystemDate());
			Mi406Example example1=new Mi406Example();
			example1.createCriteria().andTemplateDetailIdEqualTo(m.getTemplateDetailId());
			mi406Dao.updateByExample(m,example1);
		}
	}

	public List<Mi406> getMi406AllList(String tempId) {
		// 如果tempId为空，则获取第一个模版的要素
		if (CommonUtil.isEmpty(tempId)) {
			return new ArrayList<Mi406>();
		}
		Mi406Example example = new Mi406Example();
		example.setOrderByClause("to_number(freeuse4) asc");
		example.createCriteria().andValidflagEqualTo(Constants.IS_VALIDFLAG).andTemplateIdEqualTo(tempId);
		List<Mi406> list = mi406Dao.selectByExample(example);
		return list;
	}

	public Mi406 getMi406ById(String templateDetailId) {
		Mi406Example example = new Mi406Example();
		example.createCriteria().andTemplateDetailIdEqualTo(templateDetailId);
		List<Mi406> list = mi406Dao.selectByExample(example);
		if(list.size()>0)
		{
			return list.get(0);
		}
		return null;
	}

	public void updateMi406(Mi406 form) {
		Mi406 m = mi406Dao.selectByPrimaryKey(form.getTemplateDetailId());
		//如果编号有改变
		if(!m.getSerialNumber().equals(form.getSerialNumber()))
		{
			Mi406Example example1 = new Mi406Example();
			example1.createCriteria().andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andSerialNumberEqualTo(form.getSerialNumber()).andTemplateIdEqualTo(m.getTemplateId());
			List<Mi406> l = mi406Dao.selectByExample(example1);
			if(l.size() > 0)
			{
				throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"编号重复！");
			}
		}
		Mi406Example example = new Mi406Example();
		example.createCriteria().andTemplateDetailIdEqualTo(form.getTemplateDetailId());
		form.setDatemodified(CommonUtil.getSystemDate());
		form.setTemplateId(m.getTemplateId());
		form.setValidflag(Constants.IS_VALIDFLAG);
		form.setDatecreated(m.getDatecreated());
		form.setFreeuse1(m.getFreeuse1());
		form.setFreeuse2(m.getFreeuse2());
		form.setFreeuse3(m.getFreeuse3());
		form.setFreeuse4(m.getFreeuse4());
		mi406Dao.updateByExample(form, example);
		
	}
	
}
