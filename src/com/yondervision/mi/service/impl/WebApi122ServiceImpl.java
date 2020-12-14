package com.yondervision.mi.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi122DAO;
import com.yondervision.mi.dao.Mi007DAO;
import com.yondervision.mi.dao.Mi122DAO;
import com.yondervision.mi.dto.CMi122;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi007Example;
import com.yondervision.mi.dto.Mi122;
import com.yondervision.mi.dto.Mi122Example;
import com.yondervision.mi.dto.Mi122Example.Criteria;
import com.yondervision.mi.result.WebApi12201_queryResult;
import com.yondervision.mi.service.WebApi122Service;
import com.yondervision.mi.util.CommonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
* @ClassName: WebApi403ServiceImpl 
* @Description: TODO
* @author Caozhongyan
* @date Oct 9, 2013 9:13:18 AM   
* 
*/ 
@SuppressWarnings("deprecation")
public class WebApi122ServiceImpl implements WebApi122Service {
	
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	
	Mi122DAO mi122Dao = null;
	
	CMi122DAO cmi122Dao = null;
	@Autowired
	private Mi007DAO mi007Dao = null;

	public Mi007DAO getMi007Dao() {
		return mi007Dao;
	}

	public void setMi007Dao(Mi007DAO mi007Dao) {
		this.mi007Dao = mi007Dao;
	}

	public CMi122DAO getCmi122Dao() {
		return cmi122Dao;
	}

	public void setCmi122Dao(CMi122DAO cmi122Dao) {
		this.cmi122Dao = cmi122Dao;
	}

	public Mi122DAO getMi122Dao() {
		return mi122Dao;
	}

	public void setMi122Dao(Mi122DAO mi122Dao) {
		this.mi122Dao = mi122Dao;
	}

	@SuppressWarnings("unchecked")
	public void webapi12201(CMi122 form) throws Exception{
				
		Mi122Example m122e=new Mi122Example();
		com.yondervision.mi.dto.Mi122Example.Criteria ca= m122e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterid());
		ca.andMessageTopicTypeEqualTo(form.getMessageTopicType());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		List<Mi122> mi122List = mi122Dao.selectByExample(m122e);
		if(mi122List !=null && !mi122List.isEmpty()){
			throw new TransRuntimeErrorException(WEB_ALERT.DATA_CHECK_INSERT.getValue(),"消息主题类型己存在");
		}		
		
		Mi122Example m122e1=new Mi122Example();
		com.yondervision.mi.dto.Mi122Example.Criteria ca1= m122e1.createCriteria();
		ca1.andCenteridEqualTo(form.getCenterid());
		ca1.andMessageTopicTypeEqualTo(form.getMessageTopicType());
		ca1.andValidflagEqualTo(Constants.IS_NOT_VALIDFLAG);
		List<Mi122> mi122List1 = mi122Dao.selectByExample(m122e1);
		
		Mi122Example m122e2=new Mi122Example();
		com.yondervision.mi.dto.Mi122Example.Criteria ca2= m122e2.createCriteria();
		ca2.andCenteridEqualTo(form.getCenterid());
		int num = mi122Dao.countByExample(m122e2)+1;
		if(mi122List1.size()>0){
			Mi122 mi122 = new Mi122();
			mi122.setSeqid(mi122List1.get(0).getSeqid());
			mi122.setValidflag(Constants.IS_VALIDFLAG);
			mi122.setDatemodified(CommonUtil.getSystemDate());
			mi122.setMustsend(form.getMustsend());
			mi122.setLoginid(form.getUserid());
			mi122.setNum(num);
			mi122.setDefinitiontype(form.getDefinitiontype());
			mi122Dao.updateByPrimaryKeySelective(mi122);
		}else{
			Mi122 mi122 = new Mi122();
			mi122.setSeqid(commonUtil.genKey("MI122", 0));
			mi122.setCenterid(form.getCenterid());
			mi122.setDatecreated(CommonUtil.getSystemDate());		
			mi122.setDatemodified(CommonUtil.getSystemDate());
			mi122.setMessageTopicType(form.getMessageTopicType());
			mi122.setMessageTopicTypeName(form.getMessageTopicTypeName());
			mi122.setMustsend(form.getMustsend());
			mi122.setFreeuse1(form.getFreeuse1());
			mi122.setFreeuse2(form.getFreeuse2());
			mi122.setFreeuse3(form.getFreeuse3());
			mi122.setFreeuse4(form.getFreeuse4());
			mi122.setLoginid(form.getUserid());		
			mi122.setValidflag(Constants.IS_VALIDFLAG);
			mi122.setDefinitiontype(form.getDefinitiontype());
			mi122.setNum(num);
			mi122Dao.insert(mi122);
		}
	}

	public void webapi12202(CMi122 form) throws Exception{
		String[] deletes=form.getListSeqid().split(",");
		for(int i=0;i<deletes.length;i++){
			SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
			Date date = new Date();
			Mi122 mi122 = new Mi122();
			mi122.setSeqid(deletes[i]);
			mi122.setDatemodified(formatter.format(date));
			mi122.setValidflag(Constants.IS_NOT_VALIDFLAG);
			mi122.setLoginid(form.getUserid());
			mi122Dao.updateByPrimaryKeySelective(mi122);
		}		
	}

	
	public WebApi12201_queryResult webapi12204(CMi122 form) throws Exception{		
		return cmi122Dao.select122Page(form);
	}	

	@SuppressWarnings("unchecked")
	public int webapi12203(CMi122 form) throws Exception{		
		Mi122Example m122e=new Mi122Example();
		com.yondervision.mi.dto.Mi122Example.Criteria ca= m122e.createCriteria();
		ca.andSeqidNotEqualTo(form.getSeqid());
		ca.andCenteridEqualTo(form.getCenterid());
		ca.andMessageTopicTypeEqualTo(form.getMessageTopicType());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi122> mi122List = mi122Dao.selectByExample(m122e);
		if(mi122List !=null && !mi122List.isEmpty()){
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(),"消息主题类型己存在");
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();		
		Mi122Example m122e1=new Mi122Example();
		com.yondervision.mi.dto.Mi122Example.Criteria ca1= m122e1.createCriteria();
		ca1.andSeqidNotEqualTo(form.getSeqid());
		ca1.andCenteridEqualTo(form.getCenterid());
		ca1.andMessageTopicTypeEqualTo(form.getMessageTopicType());
		ca1.andValidflagEqualTo(Constants.IS_NOT_VALIDFLAG);
		List<Mi122> mi122List1 = mi122Dao.selectByExample(m122e1);		
		if(mi122List1.size()>0){
			Mi122 mi122 = new Mi122();
			mi122.setSeqid(mi122List1.get(0).getSeqid());
			mi122.setDatemodified(formatter.format(date));
			mi122.setMessageTopicType(form.getMessageTopicType());
			mi122.setMessageTopicTypeName(form.getMessageTopicTypeName());
			mi122.setMustsend(form.getMustsend());
			mi122.setValidflag(Constants.IS_VALIDFLAG);
			mi122.setLoginid(form.getUserid());
			mi122.setNum(mi122Dao.selectByPrimaryKey(form.getSeqid()).getNum());
			mi122.setDefinitiontype(form.getDefinitiontype());
			int i = mi122Dao.updateByPrimaryKeySelective(mi122);
			
			Mi122 mi122Key = new Mi122();
			mi122Key.setSeqid(form.getSeqid());
			mi122Key.setDatemodified(formatter.format(date));
			mi122Key.setValidflag(Constants.IS_NOT_VALIDFLAG);
			mi122Key.setLoginid(form.getUserid());
			mi122Dao.updateByPrimaryKeySelective(mi122Key);
			return i;
		}else{			
			Mi122 mi122 = new Mi122();
			mi122.setSeqid(form.getSeqid());
			mi122.setDatemodified(formatter.format(date));
			mi122.setMessageTopicType(form.getMessageTopicType());
			mi122.setMessageTopicTypeName(form.getMessageTopicTypeName());
			mi122.setMustsend(form.getMustsend());
			mi122.setLoginid(form.getUserid());
			mi122.setDefinitiontype(form.getDefinitiontype());
			return mi122Dao.updateByPrimaryKeySelective(mi122);
		}
		
		
	}
	
	@Transactional(noRollbackFor=NoRollRuntimeErrorException.class)
	public void webapi12205(JSONArray arr) throws Exception {
		for(int i=0;i<arr.size();i++){
			JSONObject obj = arr.getJSONObject(i);
			Mi122 mi122 = new Mi122();
			mi122.setSeqid((String)obj.getString("seqid"));
			mi122.setNum(Integer.parseInt(obj.getString("num")));
			mi122Dao.updateByPrimaryKeySelective(mi122);
		}
		
	}	
	
	/**
	 * 根据中心获取定制的主题列表
	 * @param form
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Mi122> webapi12206(CMi122 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		List<Mi122> resultList = new ArrayList<Mi122>();
		String dicparam = form.getDicparam();
		if(CommonUtil.isEmpty(dicparam)){
			log.error(ERROR.PARAMS_NULL.getLogText("查询编码参数为空"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "查询编码参数为空");
		}
		Mi007Example example = new Mi007Example();
		example.createCriteria().andCenteridEqualTo(Constants.YD_ADMIN).andItemidEqualTo(dicparam)
		.andUpdicidEqualTo(0);
		List<Mi007> selecte = mi007Dao.selectByExample(example);
		if(selecte !=null && !selecte.isEmpty()){
			Mi007 mi007 = selecte.get(0);
			Integer dicid = mi007.getDicid();
			
			String centerid = form.getCenterid();
			if(CommonUtil.isEmpty(centerid)){
				UserContext instance = UserContext.getInstance();
				centerid = instance.getCenterid();
			}
			Mi122Example mi122Example = new Mi122Example();
			Criteria ca = mi122Example.createCriteria();
			if(!Constants.YD_ADMIN.equals(centerid)){
				ca.andCenteridEqualTo(centerid);
			}
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi122Example.setOrderByClause("NUM");
			List<Mi122> list = mi122Dao.selectByExample(mi122Example);
			if(list !=null && !list.isEmpty()){
				for(Mi122 mi122:list){
					String itemid = mi122.getMessageTopicType();
					Mi007Example example2 = new Mi007Example();
					example2.createCriteria().andCenteridEqualTo(Constants.YD_ADMIN).andItemidEqualTo(itemid)
					.andUpdicidEqualTo(dicid);
					Mi007 mi007Tem = (Mi007)mi007Dao.selectByExample(example2).get(0);
					mi122.setMessageTopicTypeName(mi007Tem.getItemval());
					resultList.add(mi122);
				}
			}
		}
		return resultList;
	
	}
	
}
