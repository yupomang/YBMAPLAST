package com.yondervision.mi.service;
 
import java.util.HashMap;
import java.util.List;


import com.yondervision.mi.dto.Mi405;
import com.yondervision.mi.dto.Mi406;
import com.yondervision.mi.result.WebApi40502_queryResult;
 
public interface WebApi405Service {
	
	public List<Mi405> getMi405AllList();
	
	public void addMi405(Mi405 mi405);
	
	public void delMi405(List<String> list);
	
	public void updateRule(Mi405 mi405);
	
	public WebApi40502_queryResult getMi405AllList(String page,String rows);
//	
	public List<Mi406> getMi406AllList(String tempId);
//
	public String addMessageTemplate(Mi405 mi405);
//	
	public void delTemplate(List<String> list);
//	
	public void updateTemplate(Mi405 mi405);
//	
	public void buildMi406(Mi405 Mi405);

	public Mi406 getMi406ById(
			String templateDetailId);
//
	public void updateMi406(Mi406 form);
//
//	public void addMessageProcess(HashMap<String,String> hm);
//
//	public List<CommonPhone> getCommonPhoneList();
//
//	public List<MessagePublicTemp> getMessagePublicTempList(
//			MessagePublicTemp form);
//
//	public void addOrUpdMessagePublicTemp(MessagePublicTemp form,MultipartFile file);
//	
//	public void addOrUpdMessagePublicTemp(MessagePublicTemp form);
//
//	public MessagePublicTemp loadMessagePublicTemp(String tempId);
//
//	public void delPublicTemp(List<String> list);
//
//	public void checkPublicTemp(String tempId);
//
//	public void sendPublicTemp(String tempId, String[] pushchannel);
//
//	public void sendPublicTempList(String[] tempId, String pushchannel);
//
//	public void addMessageProcessForAllUser(HashMap<String, String> hm);
//
//	public WebApi30405_queryResult getMessageProcessList(WebApi30405Form webApi30405Form);
//
//	public WebApi3040501_queryResult getMessageSendList(String processId,WebApi3040501Form webApi3040501Form);
//
//	public MessageSend getMessageSendById(String sendId);
//
//	public String getMessageSendDetailById(String sendId);
//
//	public WebApi30407_queryResult countChannel(WebApi30407Form webApi30407Form);
//
//	public WebApi3040501_queryResult getMessageSendDetailList(
//			WebApi30408Form webApi30408Form);
//
//	public WebApi30409_queryResult getTempalteCountList(
//			WebApi30409Form webApi30409Form,String templateCode,String templateName);
//	


}
