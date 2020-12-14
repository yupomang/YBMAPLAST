package com.yondervision.mi.common.filter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.dao.Mi039DAO;
import com.yondervision.mi.dao.Mi047DAO;
import com.yondervision.mi.dto.Mi037;
import com.yondervision.mi.dto.Mi039;
import com.yondervision.mi.dto.Mi039Example;
import com.yondervision.mi.dto.Mi047;
import com.yondervision.mi.dto.Mi047Example;
import com.yondervision.mi.util.CommonUtil;

public class Monitor {
	public void monitorNotice(String type ,String centerId ,String content) throws NoRollRuntimeErrorException{
		System.out.println("监控异常，准备进行短信息通知相关人员,type["+type+"],centerId["+centerId+"],centend["+content+"]");
		Mi047DAO mi047DAO = (Mi047DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi047DAO");
		Mi047Example m047e=new Mi047Example();
		com.yondervision.mi.dto.Mi047Example.Criteria ca= m047e.createCriteria();
		ca.andCenteridEqualTo(centerId).andControlidEqualTo(type).andValidflagEqualTo("1");
		List<Mi047> mi047List = mi047DAO.selectByExample(m047e);
		if(CommonUtil.isEmpty(mi047List)){
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS
					.getValue(), "监控通讯录查无数据");
		}
		
		Mi039DAO mi039DAO = (Mi039DAO)com.yondervision.mi.util.SpringContextUtil.getBean("Mi039DAO");
		Mi039Example m039e=new Mi039Example();
		com.yondervision.mi.dto.Mi039Example.Criteria ca039= m039e.createCriteria();
		ca039.andTypeEqualTo(type).andValidflagEqualTo("1");
		List<Mi039> mi039List = mi039DAO.selectByExample(m039e);
		if(CommonUtil.isEmpty(mi039List)){
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS
					.getValue(), "监控消息通知通讯记录");
		}
		Mi039 mi039 = mi039List.get(0);
		CommonUtil commonUtil = new CommonUtil();
		for(int i=0;i<mi047List.size();i++){
			Mi047 mi047 = mi047List.get(i);
			//处理短信息推送
			
			
			
			
			//
			Mi037 mi037 = new Mi037();
			try {
				mi037.setCommunicationid(commonUtil.genKey("MI037", 20).toString());
			} catch (Exception e) {
				e.printStackTrace();
				throw new NoRollRuntimeErrorException(WEB_ALERT.SYS
						.getValue(), "监控消息通知历史处理异常");
			}
			mi037.setCenterid(centerId);
			mi037.setControlid(type);
			mi037.setPhone(mi047.getPhone());
			mi037.setName(mi047.getName());
			mi037.setTitle(mi039.getMessage());
			mi037.setContent(content);
		}
		
	}
}
