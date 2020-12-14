package com.yondervision.mi.service.impl;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi029DAO;
import com.yondervision.mi.dao.CMi607DAO;
import com.yondervision.mi.dao.Mi007DAO;
import com.yondervision.mi.dao.Mi029DAO;
import com.yondervision.mi.dao.Mi031DAO;
import com.yondervision.mi.dao.Mi034DAO;
import com.yondervision.mi.dao.Mi040DAO;
import com.yondervision.mi.dao.Mi048DAO;
import com.yondervision.mi.dao.impl.CMi008DAOImpl;
import com.yondervision.mi.dto.CMi029;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi007Example;
import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.dto.Mi029Example;
import com.yondervision.mi.dto.Mi031;
import com.yondervision.mi.dto.Mi031Example;
import com.yondervision.mi.dto.Mi034;
import com.yondervision.mi.dto.Mi034Example;
import com.yondervision.mi.dto.Mi040;
import com.yondervision.mi.dto.Mi040Example;
import com.yondervision.mi.dto.Mi048;
import com.yondervision.mi.dto.Mi048Example;
import com.yondervision.mi.dto.Mi607;
import com.yondervision.mi.dto.Mi607Example;
import com.yondervision.mi.dto.Mi607Example.Criteria;
import com.yondervision.mi.form.AppApi50001Form;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.WebApi02904_queryResult;
import com.yondervision.mi.service.WebApi029Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.SpringContextUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WebApi029ServiceImpl implements WebApi029Service {
	
	@Autowired
	private CMi029DAO cmi029DAO;
	@Autowired
	private Mi031DAO mi031DAO;
	@Autowired
	private Mi034DAO mi034DAO;
	@Autowired
	private Mi048DAO mi048DAO;
	@Autowired
	private Mi007DAO mi007Dao;
	@Autowired
	private Mi040DAO mi040DAO;
	@Autowired
	private CMi607DAO cmi607Dao;
	@Autowired
	private CommonUtil commonUtil;
	
	
	//add by 加.trim
	@SuppressWarnings("unchecked")
	public Mi029 webapi02901(AppApi50001Form form) throws Exception {
		Mi029Example mi029Example = new Mi029Example();
		mi029Example.createCriteria().andCertinumEqualTo(form.getBodyCardNumber().trim()).andCenteridEqualTo(form.getCenterId()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi029> list = cmi029DAO.selectByExample(mi029Example);
		System.out.println("=========注册时检查人员是否在MI029存在条件,证件号："+form.getBodyCardNumber().trim()+" ,城市码："+form.getCenterId());
		if(!CommonUtil.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		} 
	}

	public void webapi02902(AppApi50001Form form ,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("渠道用户城市代码为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "渠道用户城市代码为空");
		}
		if(CommonUtil.isEmpty(form.getFullName())){
			log.error(ERROR.PARAMS_NULL.getLogText("渠道用户用户名称为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "渠道用户用户名称为空");
		}
		if(CommonUtil.isEmpty(form.getBodyCardNumber())){
			log.error(ERROR.PARAMS_NULL.getLogText("渠道用户身份证号为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "渠道用户身份证号为空");
		}
		Mi029 mi029 = new Mi029();
		mi029.setPersonalid(genKeyStatic29("MI029",20));
		mi029.setCenterid(form.getCenterId());
		mi029.setUsername(form.getFullName());
		mi029.setCertinumtype(CommonUtil.isEmpty(form.getCertinumType())?"1":form.getCertinumType());
		mi029.setCertinum(form.getBodyCardNumber());
		mi029.setTel(form.getTel());
		mi029.setValidflag(Constants.IS_VALIDFLAG);
		mi029.setDatecreated(CommonUtil.getSystemDate());
		mi029.setDatemodified(CommonUtil.getSystemDate());
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(mi029)));
		cmi029DAO.insert(mi029);
		webapi02924(mi029 ,request ,response);//插入或更新70手机短信渠道信息
		Mi031 mi031 = new Mi031();
		mi031.setId(genKeyStatic31("MI031",20));
		mi031.setPersonalid(mi029.getPersonalid());
		mi031.setCenterid(form.getCenterId());
		mi031.setChannel(form.getChannel());
		mi031.setPid((String)request.getAttribute("MI040Pid").toString());
		mi031.setUserid(form.getUserId());
		mi031.setSendmessage("1");
		mi031.setValidflag(Constants.IS_VALIDFLAG);
		mi031.setDatecreated(CommonUtil.getSystemDate());
		mi031.setDatemodified(CommonUtil.getSystemDate());
		mi031DAO.insert(mi031);
		
		if(form.getCenterId().equals("00073300")){
			if("10000136".equals((String)request.getAttribute("MI040Pid").toString())){//APP注册同步网厅
				Mi031 mi031APP = new Mi031();
				mi031APP.setId(genKeyStatic31("MI031",20));
				mi031APP.setPersonalid(mi029.getPersonalid());
				mi031APP.setCenterid(form.getCenterId());
				mi031APP.setChannel("40");
				mi031APP.setPid("40000130");
				mi031APP.setUserid(form.getUserId());
				mi031APP.setSendmessage("1");
				mi031APP.setValidflag(Constants.IS_VALIDFLAG);
				mi031APP.setDatecreated(CommonUtil.getSystemDate());
				mi031APP.setDatemodified(CommonUtil.getSystemDate());
				mi031DAO.insert(mi031APP);
			}else if("40000130".equals((String)request.getAttribute("MI040Pid").toString())){//网厅注册同步APP
				Mi031 mi031WT = new Mi031();
				mi031WT.setId(genKeyStatic31("MI031",20));
				mi031WT.setPersonalid(mi029.getPersonalid());
				mi031WT.setCenterid(form.getCenterId());
				mi031WT.setChannel("10");
				mi031WT.setPid("10000136");
				mi031WT.setUserid(form.getUserId());
				mi031WT.setSendmessage("1");
				mi031WT.setValidflag(Constants.IS_VALIDFLAG);
				mi031WT.setDatecreated(CommonUtil.getSystemDate());
				mi031WT.setDatemodified(CommonUtil.getSystemDate());
				mi031DAO.insert(mi031WT);
			}
		}
		
		
		
	}

	public void webapi02903(CMi029 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getPersonalid())){
			log.error(ERROR.PARAMS_NULL.getLogText("渠道用户029主键为空："+form.getPersonalid()));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "渠道用户029主键为空："+form.getPersonalid());
		}
//		if(CommonUtil.isEmpty(form.getUselevel())){
//			log.error(ERROR.PARAMS_NULL.getLogText("渠道用户029用户级别为空:"+form.getUselevel()));
//			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "渠道用户029用户级别为空:"+form.getUselevel());
//		}
		if(CommonUtil.isEmpty(form.getVip())){
			log.error(ERROR.PARAMS_NULL.getLogText("渠道用户029VIP级别为空:"+form.getVip()));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "渠道用户029VIP级别为空:"+form.getVip());
		}
		if(CommonUtil.isEmpty(form.getSensitive())){
			log.error(ERROR.PARAMS_NULL.getLogText("渠道用户029用户是否敏感为空:"+form.getSensitive()));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "渠道用户029用户是否敏感为空:"+form.getSensitive());
		}
		Mi029 mi029 = cmi029DAO.selectByPrimaryKey(form.getPersonalid());
		if(CommonUtil.isEmpty(mi029)){
			log.error(ERROR.PARAMS_NULL.getLogText("渠道用户029用户不存在:"+form.getPersonalid()));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "渠道用户029用户不存在:"+form.getPersonalid());
		}
//		mi029.setUselevel(form.getUselevel());
		mi029.setVip(form.getVip());
		mi029.setSensitive(form.getSensitive());
		mi029.setDatemodified(CommonUtil.getSystemDate());
		int updateResult = cmi029DAO.updateByPrimaryKeySelective(mi029);
		if(updateResult!=1){
			log.error(ERROR.PARAMS_NULL.getLogText("渠道用户029用户更新失败:"+form.getPersonalid()));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "渠道用户029用户更新失败:"+form.getPersonalid());
		}
	}
	
	/**
	 * 分页查询
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public WebApi02904_queryResult webapi02904(CMi029 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form)){
			log.error(ERROR.PARAMS_NULL.getLogText("渠道用户MI029"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "渠道接口配置表MI050");
		}
		if(CommonUtil.isEmpty(form.getPage())){
			log.error(ERROR.PARAMS_NULL.getLogText("page为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "page为空");
		}
		if(CommonUtil.isEmpty(form.getRows())){
			log.error(ERROR.PARAMS_NULL.getLogText("rows为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "rows为空");
		}
		//首先找出黑名单的personalid
		List<String> ids = new ArrayList<String>();
		Mi607Example mi607Example = new Mi607Example();
		Criteria ca = mi607Example.createCriteria();
		String centerid = form.getCenterid();
		if(CommonUtil.isEmpty(centerid)){
			UserContext instance = UserContext.getInstance();
			centerid = instance.getCenterid();
		}
		ca.andCenteridEqualTo(centerid);
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi607> selectByExample = cmi607Dao.selectByExample(mi607Example);
		if(selectByExample !=null && !selectByExample.isEmpty()){
			for(Mi607 mi607:selectByExample){
				ids.add(mi607.getPersonalid());
			}
		}
		return cmi029DAO.select029Page(form,ids);
	}
	@SuppressWarnings("unchecked")
	public JSONArray webapi02905(CMi029 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getPersonalid())){
			log.error(ERROR.PARAMS_NULL.getLogText("渠道用户029用户主键为空:"+form.getPersonalid()));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "渠道用户029用户主键为空:"+form.getPersonalid());
		}
		JSONArray array = new JSONArray();
		
		Mi031Example mi031Example = new Mi031Example();
		mi031Example.createCriteria().andPersonalidEqualTo(form.getPersonalid())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi031> list = mi031DAO.selectByExample(mi031Example);
		if(list!=null && !list.isEmpty()){
			for(int i=0; i<list.size();i++){
				JSONObject obj = new JSONObject();
				Mi031 mi031 = list.get(i);
				obj.put("userid", mi031.getUserid());
				obj.put("nickname", mi031.getNickname());
				obj.put("status", mi031.getStatus());
				obj.put("sendmessage", mi031.getSendmessage());
				Mi007Example mi007Example = new Mi007Example();
				mi007Example.createCriteria().andCenteridEqualTo(mi031.getCenterid()).andItemidEqualTo("channel")
				.andUpdicidEqualTo(0);
				Mi007 mi007 = (Mi007)mi007Dao.selectByExample(mi007Example).get(0);
				Integer dicid = mi007.getDicid();
				
				Mi007Example mi007Example2 = new Mi007Example();
				mi007Example2.createCriteria().andItemidEqualTo(mi031.getChannel()).andCenteridEqualTo(mi031.getCenterid())
				.andUpdicidEqualTo(dicid);
				String itemval = ((Mi007)mi007Dao.selectByExample(mi007Example2).get(0)).getItemval();
				obj.put("channelname", itemval);
				
				
				Mi040Example mi040Example = new Mi040Example();
				mi040Example.createCriteria().andCenteridEqualTo(mi031.getCenterid()).andPidEqualTo(mi031.getPid().trim())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				List<Mi040> list2 = mi040DAO.selectByExample(mi040Example);
				if(list2 !=null && !list2.isEmpty()){
					String appname = list2.get(0).getAppname();
					obj.put("appname", appname);
				}else{
					obj.put("appname", "");
				}
				array.add(obj);
			}
		}
		return array;
		
	}
	
	@Transactional
	public void webapi02906(CMi029 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getPersonalid())){
			log.error(ERROR.PARAMS_NULL.getLogText("渠道用户029用户主键为空:"+form.getPersonalid()));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "渠道用户029用户主键为空:");
		}
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("渠道用户029用户中心为空:"+form.getCenterid()));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "渠道用户029用户中心为空:");
		}
		if(CommonUtil.isEmpty(form.getCause())){
			log.error(ERROR.PARAMS_NULL.getLogText("添加黑名单原因为空:"+form.getCause()));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "添加黑名单原因为空:");
		}
		
		String[] ids = form.getPersonalid().split(",");
		for(String id:ids){
			//验证
			Mi607Example mi607EXample = new Mi607Example();
			mi607EXample.createCriteria().andPersonalidEqualTo(id).andValidflagEqualTo(Constants.IS_VALIDFLAG);
			int count = cmi607Dao.countByExample(mi607EXample);
			if(count!=0){
				log.error(ERROR.ADD_CHECK.getLogText("黑名单已经存在:"+form.getPersonalid()));
				throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(), "黑名单已经存在:"+form.getPersonalid());
			}
			
			//添加黑名单
			UserContext userContext = UserContext.getInstance();
			String loginid = userContext.getLoginid();
			// 采号
			String blacklistid = commonUtil.genKey("MI607", 6).toString();// 采号生成，前补0，总长度20
			if (CommonUtil.isEmpty(blacklistid)) {
				log.error(ERROR.NULL_KEY.getLogText("黑名单MI607"));
				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.NULL_KEY.getLogText("应用信息MI040"));
			}
			Mi607 mi607 = new Mi607();
			mi607.setBlacklistid(blacklistid);
			mi607.setPersonalid(id);
			mi607.setCause(form.getCause());
			mi607.setCenterid(form.getCenterid());
			mi607.setLoginid(loginid);
			mi607.setDatecreated(CommonUtil.getSystemDate());
			mi607.setDatemodified(CommonUtil.getSystemDate());
			mi607.setValidflag(Constants.IS_VALIDFLAG);
			cmi607Dao.insert(mi607);
			
		}
		
	}
	
	
	
	
	public Mi031 webapi02907(AppApiCommonForm form ,HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
			Mi031Example mi031Example = new Mi031Example();
			mi031Example.createCriteria().andCenteridEqualTo(form.getCenterId()).andChannelEqualTo(form.getChannel())
			.andPidEqualTo((String)request.getAttribute("MI040Pid")).andUseridEqualTo(form.getUserId());
			System.out.println("查询渠道用户表——form.getCenterId()："+form.getCenterId());
			System.out.println("查询渠道用户表——form.getChannel()："+form.getChannel());
			System.out.println("查询渠道用户表——PID："+((String)request.getAttribute("MI040Pid")));
			System.out.println("查询渠道用户表——form.getUserId()："+form.getUserId());
			List<Mi031> list = mi031DAO.selectByExample(mi031Example);
			if (!CommonUtil.isEmpty(list)) {
				return list.get(0);
			}else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
//			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "客户不存在");
		}
		return null;
	}

	
	
	public Mi029 webapi02908(String personalid) throws Exception {
		Mi029Example mi029Example = new Mi029Example();
		mi029Example.createCriteria().andPersonalidEqualTo(personalid).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi029> list = cmi029DAO.selectByExample(mi029Example);
		if(!CommonUtil.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		} 
	}

	
	
	public Mi029 webapi02909(AppApi50001Form form) throws Exception {
		Mi029Example mi029Example = new Mi029Example();
		mi029Example.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andCertinumEqualTo(form.getBodyCardNumber())
		.andTelEqualTo(form.getTel())
		.andUsernameEqualTo(form.getFullName())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi029> list = cmi029DAO.selectByExample(mi029Example);
		if(!CommonUtil.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		} 
	}
	
	public Mi029 webapi02910(AppApi50001Form form) throws Exception {
		Mi029Example mi029Example = new Mi029Example();
		mi029Example.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andTelEqualTo(CommonUtil.isEmpty(form.getTel())?"":(form.getTel().length()>11?form.getTel().substring(0,11):form.getTel()))
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi029> list = cmi029DAO.selectByExample(mi029Example);
		if(!CommonUtil.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		} 
	}

	public void webapi02911(String centerId ,HashMap hasMap ,String personalid ,Mi029 mi029) throws Exception{
		mi029.setUnitaccnum((String)hasMap.get("unitaccnum"));
		mi029.setUsername((String)hasMap.get("accname"));
		this.cmi029DAO.updateByPrimaryKeySelective(mi029);
		//亲属关系1-配偶2-父亲3-母亲4-子女
		Logger log = LoggerUtil.getLogger();
		
		Set set=hasMap.entrySet();   

        Iterator   iterator=set.iterator();   
        System.out.println("#$%$#@@@@@@@@@@  ===============================================");
        while (iterator.hasNext()) {   

          Map.Entry  mapentry = (Map.Entry) iterator.next();   

          System.out.println(mapentry.getKey()+"/"+ mapentry.getValue());   

        }   

		
		
		for(int i=1;i<=4;i++){
			System.out.println("#$%$#@@@@@@@@@@  "+hasMap.get("srelation"+i));
			if(!CommonUtil.isEmpty(hasMap.get("srelation"+i))){
				Mi034Example mi034Example = new Mi034Example();
				mi034Example.createCriteria().andCenteridEqualTo(centerId).andPersonalidEqualTo(personalid)
				.andRelationEqualTo((String)hasMap.get("srelation"+i))
				.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				List<Mi034> list034 = mi034DAO.selectByExample(mi034Example);
				
				String relationpersonalid = "";
				if("1".equals((String)hasMap.get("isuser"+i))){
					Mi029Example mi029Example = new Mi029Example();
					mi029Example.createCriteria().andCenteridEqualTo(centerId).andCertinumEqualTo((String)hasMap.get("relcertinum"+i))
					.andValidflagEqualTo(Constants.IS_VALIDFLAG);
					List<Mi029> list029 = this.cmi029DAO.selectByExample(mi029Example);
					if(!CommonUtil.isEmpty(list029)){
						relationpersonalid = list029.get(0).getPersonalid();
					}
				}
				if(!CommonUtil.isEmpty(list034)){
					System.out.println("更新关联人信息"+(String)hasMap.get("relcustid"+i));
					Mi034 mi034 = list034.get(0);	
					mi034.setPersonalid(mi029.getPersonalid());
					mi034.setCertinum(CommonUtil.isEmpty((String)hasMap.get("relcertinum"+i))?"--":(String)hasMap.get("relcertinum"+i));
					mi034.setTel(CommonUtil.isEmpty((String)hasMap.get("phone"+i))?"--":(String)hasMap.get("phone"+i));
					mi034.setUsername(CommonUtil.isEmpty((String)hasMap.get("relname"+i))?"--":(String)hasMap.get("relname"+i));
					mi034.setRelation(CommonUtil.isEmpty((String)hasMap.get("srelation"+i))?"--":(String)hasMap.get("srelation"+i));
					mi034.setCertinumcustomerid(CommonUtil.isEmpty((String)hasMap.get("relcustid"+i))?"--":(String)hasMap.get("relcustid"+i));
					mi034.setSex(CommonUtil.isEmpty((String)hasMap.get("relsex"+i))?"--":(String)hasMap.get("relsex"+i));
					mi034.setBorth((String)hasMap.get("borth"+i));
					mi034.setRelationpersonalid(relationpersonalid);
					mi034.setFreeuse1(CommonUtil.isEmpty((String)hasMap.get("isuser1"))?"0":(String)hasMap.get("isuser1"));
					log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(mi034)));
					mi034DAO.updateByPrimaryKeySelective(mi034);			
				}else{
					//去核心去查关信人信息
					Mi034 mi034 = new Mi034();
					mi034.setId(CommonUtil.genKeyStatic("MI034",20));
					mi034.setCenterid(centerId);
					mi034.setPersonalid(mi029.getPersonalid());
					mi034.setCertinum(CommonUtil.isEmpty((String)hasMap.get("relcertinum"+i))?"--":(String)hasMap.get("relcertinum"+i));
					mi034.setTel(CommonUtil.isEmpty((String)hasMap.get("phone"+i))?"--":(String)hasMap.get("phone"+i));
					mi034.setUsername(CommonUtil.isEmpty((String)hasMap.get("relname"+i))?"--":(String)hasMap.get("relname"+i));
					mi034.setRelation(CommonUtil.isEmpty((String)hasMap.get("srelation"+i))?"--":(String)hasMap.get("srelation"+i));
					mi034.setCertinumcustomerid(CommonUtil.isEmpty((String)hasMap.get("relcustid"+i))?"--":(String)hasMap.get("relcustid"+i));
					mi034.setSex(CommonUtil.isEmpty((String)hasMap.get("relsex"+i))?"--":(String)hasMap.get("relsex"+i));
					mi034.setBorth((String)hasMap.get("borth"+i));
					mi034.setRelationpersonalid(relationpersonalid);
					mi034.setFreeuse1(CommonUtil.isEmpty((String)hasMap.get("isuser1"))?"0":(String)hasMap.get("isuser1"));
					mi034.setRelationpersonalid(relationpersonalid);
					mi034.setValidflag(Constants.IS_VALIDFLAG);
					mi034.setDatecreated(commonUtil.getSystemDate());
					mi034.setDatemodified(commonUtil.getSystemDate());
					mi034.setFreeuse1((String)hasMap.get("isuser1"));
					System.out.println("新建关联人信息"+(String)hasMap.get("relcustid"+i));
					log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(mi034)));
					mi034DAO.insert(mi034);
				}
			}
		}
	}
	
	public String webapi02912(Mi029 mi029 ,AppApi50001Form form ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("webapi02912进来啦");
		String pid = (String)request.getAttribute("MI040Pid");
		Mi031Example mi031Example = new Mi031Example();
		mi031Example.createCriteria().andCenteridEqualTo(form.getCenterId()).andChannelEqualTo(form.getChannel())
		.andPidEqualTo(pid).andUseridEqualTo(form.getUserId());
		System.out.println("渠道用户表查询条件 ，centerid:"+form.getCenterId()+" ,channel:"+form.getChannel());
		System.out.println("渠道用户表查询条件 ，pid:"+pid+" ,UserId:"+form.getUserId());
		List<Mi031> list = mi031DAO.selectByExample(mi031Example);
		if (CommonUtil.isEmpty(list)) {
			Mi031Example mi031Example1 = new Mi031Example();
			mi031Example1.createCriteria().andCenteridEqualTo(form.getCenterId()).andChannelEqualTo(form.getChannel())
			.andPidEqualTo(pid).andPersonalidEqualTo(mi029.getPersonalid());
			
			System.out.println("查询到渠用户表中有应用渠道信息，判断该信息是渠道用户名与当前注册渠道用户名是否相同，相同则不允许注册，城市："+form.getCenterId()+" ,渠道:"+form.getChannel()
					+",应用ID："+pid+",用户ID："+mi029.getPersonalid()+",当前注册USERID:"+form.getUserId());
			List<Mi031> list1 = mi031DAO.selectByExample(mi031Example1);
			Mi031 mi031 = new Mi031();
			if(CommonUtil.isEmpty(list1)){
				try{
					mi031.setId(genKeyStatic31("MI031",20));
					mi031.setPersonalid(mi029.getPersonalid());
					mi031.setPid(pid);
					mi031.setCenterid(form.getCenterId());
					mi031.setChannel(form.getChannel());
					mi031.setUserid(form.getUserId());
					mi031.setSendmessage("1");
					mi031.setValidflag(Constants.IS_VALIDFLAG);
					mi031.setDatecreated(commonUtil.getSystemDate());
					mi031.setDatemodified(commonUtil.getSystemDate());
					System.out.println("###$$$%%%  ========  核心单笔推送23 mi031.getId():"+mi031.getId());
					System.out.println("###$$$%%%  ========  核心单笔推送23 mi029.getPersonalid():"+mi029.getPersonalid());
					mi031DAO.insert(mi031);
				}catch(Exception e){
					e.printStackTrace();
					throw new  TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "用户渠道信息重复！");
				}
				
				
				if(form.getCenterId().equals("00073300")){
					if("10000136".equals((String)request.getAttribute("MI040Pid").toString())){//APP注册同步网厅
						Mi031 mi031APP = new Mi031();
						mi031APP.setId(genKeyStatic31("MI031",20));
						mi031APP.setPersonalid(mi029.getPersonalid());
						mi031APP.setCenterid(form.getCenterId());
						mi031APP.setChannel(form.getChannel());
						mi031APP.setPid("40000130");
						mi031APP.setUserid(form.getUserId());
						mi031APP.setSendmessage("1");
						mi031APP.setValidflag(Constants.IS_VALIDFLAG);
						mi031APP.setDatecreated(CommonUtil.getSystemDate());
						mi031APP.setDatemodified(CommonUtil.getSystemDate());
						mi031DAO.insert(mi031);
					}else if("40000130".equals((String)request.getAttribute("MI040Pid").toString())){//网厅注册同步APP
						Mi031 mi031WT = new Mi031();
						mi031WT.setId(genKeyStatic31("MI031",20));
						mi031WT.setPersonalid(mi029.getPersonalid());
						mi031WT.setCenterid(form.getCenterId());
						mi031WT.setChannel(form.getChannel());
						mi031WT.setPid("10000136");
						mi031WT.setUserid(form.getUserId());
						mi031WT.setSendmessage("1");
						mi031WT.setValidflag(Constants.IS_VALIDFLAG);
						mi031WT.setDatecreated(CommonUtil.getSystemDate());
						mi031WT.setDatemodified(CommonUtil.getSystemDate());
						mi031DAO.insert(mi031);
					}
				}		
			}else{
				if(list1.get(0).getUserid().trim().equals(form.getUserId().trim())){
//					throw new  TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "该用户已经注册!");
					return list1.get(0).getUserid();
				}else{
					if("50000131".equals(pid) || "60000132".equals(pid) || "40000130".equals(pid) || "30000129".equals(pid) ){
						return list1.get(0).getUserid();
					}
//					if("00087100".equals(form.getCenterId())){
//						if("10000136".equals(pid)){
//							Mi031 mi031UPtel = new Mi031();
//							mi031UPtel.setUserid(form.getUserId());
//							mi031UPtel.setId(list1.get(0).getId());
//							mi031DAO.updateByPrimaryKeySelective(mi031UPtel);
//							return form.getUserId();
//						}
//					}
					if("70000133".equals(pid)){
						Mi031 mi031UPtel = new Mi031();
						mi031UPtel.setUserid(form.getUserId());
						mi031UPtel.setId(list1.get(0).getId());
						mi031DAO.updateByPrimaryKeySelective(mi031UPtel);
					}else{
						throw new  TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "该用户已经注册!不可以重复注册");
					}					
				}
			}
			
			return mi031.getUserid();
		}
		return list.get(0).getUserid();
	}
	
	public Mi029 webapi02913(Mi029 mi029 ,AppApi50001Form form ,HashMap hasMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		mi029.setUsername((String)hasMap.get("accname"));//个人姓名
		mi029.setCertinum((String)hasMap.get("certinum"));//身份证号码
		if(!CommonUtil.isEmpty(hasMap.get("phone"))){
			mi029.setTel((String)hasMap.get("phone"));//手机号码
		}	
		if(!CommonUtil.isEmpty(hasMap.get("perstate"))){
			mi029.setUselevel((String)hasMap.get("perstate"));//核心返回认证标志
		}
		if(!CommonUtil.isEmpty(hasMap.get("unitaccnum"))){
			mi029.setUnitaccnum((String)hasMap.get("unitaccnum"));//核心返回认证标志
		}
		if(!CommonUtil.isEmpty(hasMap.get("wt_instcode"))){
			mi029.setFreeuse1((String)hasMap.get("wt_instcode"));//核心返回认证标志
		}
		
		mi029.setSex((String)hasMap.get("sex"));//性别
		mi029.setCertinumcustomerid((String)hasMap.get("custid"));//个人客户号
		mi029.setBirth((String)hasMap.get("birthday"));
		mi029.setDatemodified(CommonUtil.getSystemDate());
		cmi029DAO.updateByPrimaryKeySelective(mi029);
		webapi02917(mi029 ,form ,hasMap);
		return mi029;
	}
	
	public void webapi02914(Mi029 mi029 ,AppApi50001Form form ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		mi029.setUselevel("2");
		cmi029DAO.updateByPrimaryKeySelective(mi029);
	}
	
	

	public void webapi02915(AppApi50001Form form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("webapi02915执行进来啦");
		Mi029Example mi029Example = new Mi029Example();
		mi029Example.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andCertinumEqualTo(form.getBodyCardNumber().trim())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		System.out.println("webapi02915执行进来啦"+form.getBodyCardNumber().trim()+form.getCertinumType());
		if(!CommonUtil.isEmpty(form.getCertinumType())){
			mi029Example.createCriteria().andCertinumtypeEqualTo(form.getCertinumType());
		}
		Mi029 mi029 = new Mi029();
		mi029.setTel(form.getTel());
		cmi029DAO.updateByExampleSelective(mi029, mi029Example);
		System.out.println("webapi02915执行更新啦");

	}

	

	public Mi029 webapi02916(AppApi50001Form form, HashMap map) throws Exception {
		Mi029 mi029 = new Mi029();
		try{
			
			mi029.setPersonalid(genKeyStatic29("MI029",20));
			mi029.setCenterid(form.getCenterId());
			mi029.setUsername((String)map.get("accname"));
			mi029.setCertinumtype(CommonUtil.isEmpty(form.getCertinumType())?"1":form.getCertinumType());
			mi029.setCertinum((String)map.get("certinum"));
			mi029.setTel((String)map.get("phone"));
			mi029.setSex((String)map.get("sex"));
			mi029.setCertinumcustomerid((String)map.get("custid"));
			mi029.setBirth((String)map.get("birthday"));
			mi029.setUselevel((String)map.get("perstate"));
			mi029.setFreeuse1((String)map.get("wt_instcode"));
			mi029.setVip("0");
			mi029.setSensitive("0");
			mi029.setValidflag(Constants.IS_VALIDFLAG);
			mi029.setDatecreated(CommonUtil.getSystemDate());
			mi029.setDatemodified(CommonUtil.getSystemDate());
			System.out.println("###$$$%%%  ========  核心单笔推送42");
			System.out.println("###$$$%%%  ========  mi029.getCenterid()"+mi029.getCenterid());
			System.out.println("###$$$%%%  ========  mi029.setPersonalid()"+mi029.getPersonalid());
			System.out.println("###$$$%%%  ========  mi029.getUsername()"+mi029.getUsername());
			System.out.println("###$$$%%%  ========  mi029.getCertinumtype()"+mi029.getCertinumtype());
			System.out.println("###$$$%%%  ========  mi029.getCertinum()"+mi029.getCertinum());
			System.out.println("###$$$%%%  ========  mi029.getTel()"+mi029.getTel());
			System.out.println("###$$$%%%  ========  mi029.getSex()"+mi029.getSex());
			System.out.println("###$$$%%%  ========  mi029.getCertinumcustomerid()"+mi029.getCertinumcustomerid());
			System.out.println("###$$$%%%  ========  mi029.getBirth()"+mi029.getBirth());
			System.out.println("###$$$%%%  ========  mi029.getLevel()"+mi029.getUselevel());
			System.out.println("###$$$%%%  ========  mi029.getVip()"+mi029.getVip());
			System.out.println("###$$$%%%  ========  mi029.getSensitive()"+mi029.getSensitive());
			System.out.println("###$$$%%%  ========  mi029.getValidflag()"+mi029.getValidflag());
			System.out.println("###$$$%%%  ========  mi029.getDatecreated()"+mi029.getDatecreated());
			System.out.println("###$$$%%%  ========  mi029.getDatemodified()"+mi029.getDatemodified());
			cmi029DAO.insert(mi029);
			System.out.println("###$$$%%%  ========  核心单笔推送43");
			webapi02917(mi029 ,form ,map);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("###$$$%%%  ========  核心单笔推送44");
		return mi029;
	}
	
	public void webapi02917(Mi029 mi029 ,AppApi50001Form form ,HashMap hasMap) throws Exception{
		Mi048Example mi048Example = new Mi048Example();
		mi048Example.createCriteria().andPersonalidEqualTo(mi029.getPersonalid())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		mi048DAO.deleteByExample(mi048Example);
		String accnum = (String)hasMap.get("accnum");//个人公积金账号
		String loanaccnum = (String)hasMap.get("loanaccnum");//贷款账号
		String loancontrnum = (String)hasMap.get("loancontrnum");//借款合同号		
		System.out.println("登录更新个人公积金账号"+accnum);
		System.out.println("登录更新个人贷款账号"+loanaccnum);
		System.out.println("登录更新个人借款合同号"+loancontrnum);
		//账户类型01-公积金02联名卡号03-贷款账号04借款合同号05-补贴账户
		if(!CommonUtil.isEmpty(accnum)){
			Mi048 mi048 = new Mi048();
			mi048.setId(CommonUtil.genKeyStatic("MI048",20));
			mi048.setPersonalid(mi029.getPersonalid());
			mi048.setAccounttype("01");
			mi048.setAccountcode(accnum);
			mi048.setValidflag(Constants.IS_VALIDFLAG);
			mi048.setDatecreated(commonUtil.getSystemDate());
			mi048.setDatemodified(commonUtil.getSystemDate());
			mi048DAO.insert(mi048);
		}
		if(!CommonUtil.isEmpty(loanaccnum)){
			Mi048 mi048 = new Mi048();
			mi048.setId(CommonUtil.genKeyStatic("MI048",20));
			mi048.setPersonalid(mi029.getPersonalid());
			mi048.setAccounttype("03");
			mi048.setAccountcode(loanaccnum);
			mi048.setValidflag(Constants.IS_VALIDFLAG);
			mi048.setDatecreated(commonUtil.getSystemDate());
			mi048.setDatemodified(commonUtil.getSystemDate());
			mi048DAO.insert(mi048);
		}
		if(!CommonUtil.isEmpty(loancontrnum)){
			Mi048 mi048 = new Mi048();
			mi048.setId(CommonUtil.genKeyStatic("MI048",20));
			mi048.setPersonalid(mi029.getPersonalid());
			mi048.setAccounttype("04");
			mi048.setAccountcode(loancontrnum);
			mi048.setValidflag(Constants.IS_VALIDFLAG);
			mi048.setDatecreated(commonUtil.getSystemDate());
			mi048.setDatemodified(commonUtil.getSystemDate());
			mi048DAO.insert(mi048);
		}
	}
	
	public Mi029 webapi02918(AppApi50001Form form ,HashMap map ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		String pid = (String)request.getAttribute("MI040Pid");
		Mi031Example mi031Example = new Mi031Example();
		mi031Example.createCriteria().andCenteridEqualTo(form.getCenterId()).andChannelEqualTo(form.getChannel())
		.andPidEqualTo(pid).andUseridEqualTo(form.getUserId());
		System.out.println("90402  userid :"+form.getUserId());
		System.out.println("90402  pid :"+pid);
		System.out.println("90402  channel :"+form.getChannel());
		System.out.println("90402  centerId :"+form.getCenterId());
		List<Mi031> list = mi031DAO.selectByExample(mi031Example);
		Mi031 mi031 = null;
		if (!CommonUtil.isEmpty(list)) {
			mi031 = list.get(0);
		}else{
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "客户不存在"); 
		}
		
		Mi029 mi029 = cmi029DAO.selectByPrimaryKey(mi031.getPersonalid());
		if (!CommonUtil.isEmpty(mi029)) {
			return mi029;
		}else{
			return null;
		}
	}
	
	public Mi048 webapi02919(Mi029 mi029 ,String type ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Mi048Example mi048Example = new Mi048Example();
		mi048Example.createCriteria().andPersonalidEqualTo(mi029.getPersonalid())
		.andAccounttypeEqualTo(type).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi048> list = mi048DAO.selectByExample(mi048Example);
		Mi048 mi048 = null;
		if (!CommonUtil.isEmpty(list)) {
			mi048 = list.get(0);
		}else{
//			账户类型01-公积金02联名卡号03-贷款账号04借款合同号05-补贴账户
			String message = "";
			if("01".equals(type)){
				message = "您没有公积金账号";
			}else if("02".equals(type)){
				message = "您没有联名卡号账号";
			}else if("03".equals(type)){
				message = "您没有公积金贷款";
			}else if("04".equals(type)){
				message = "您没有公积金贷款";
			}else if("05".equals(type)){
				message = "您没有补贴账户";
			}
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(), message); 
		}
		return mi048;
	}
	
	public Mi031 webapi02920(Mi029 mi029 ,AppApi50001Form form ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Mi031Example mi031Example = new Mi031Example();
		mi031Example.setOrderByClause("datecreated desc");
		mi031Example.createCriteria().andCenteridEqualTo(form.getCenterId()).andChannelEqualTo(form.getChannel())
		.andPidEqualTo((String)request.getAttribute("MI040Pid")).andPersonalidEqualTo(mi029.getPersonalid());
		System.out.println("***************** "+form.getCenterId());
		System.out.println("***************** "+form.getChannel());
		System.out.println("***************** "+(String)request.getAttribute("MI040Pid"));
		System.out.println("***************** "+mi029.getPersonalid());
		List<Mi031> list = mi031DAO.selectByExample(mi031Example);
		if (!CommonUtil.isEmpty(list)) {
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public List<Mi031> webapi02921(Mi031 form ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Mi031Example mi031Example = new Mi031Example();
		mi031Example.setOrderByClause("datecreated desc");
		mi031Example.createCriteria().andCenteridEqualTo(form.getCenterid())
		.andPersonalidEqualTo(form.getPersonalid());
		List<Mi031> list = mi031DAO.selectByExample(mi031Example);
		if (!CommonUtil.isEmpty(list)) {
			return list;
		}else{
			return null;
		}
	}
	
	public void webapi02922(Mi031 form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		mi031DAO.updateByPrimaryKeySelective(form);
	}
	
	public List<Mi040> webapi02923(AppApi50001Form form ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Mi040Example mi040Example = new Mi040Example();
		mi040Example.setOrderByClause("datecreated desc");
		mi040Example.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andChannelEqualTo(form.getChannel()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi040> list = mi040DAO.selectByExample(mi040Example);
		if (!CommonUtil.isEmpty(list)) {
			return list;
		}else{
			return null;
		}
	}
	
	public void webapi02924(Mi029 mi029 ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("webapi02924执行进来啦");
		System.out.println("更新渠道用户手机渠道信息  ,返回tel信息："+mi029.getTel());
		if(!CommonUtil.isEmpty(mi029.getTel())){
			Mi031Example mi031Example = new Mi031Example();
			mi031Example.setOrderByClause("datecreated desc");
			mi031Example.createCriteria().andCenteridEqualTo(mi029.getCenterid())
			.andPersonalidEqualTo(mi029.getPersonalid())
			.andChannelEqualTo("70").andPidEqualTo("70000133");			
			List<Mi031> list = mi031DAO.selectByExample(mi031Example);
			if (!CommonUtil.isEmpty(list)) {
				Mi031 mi031 = list.get(0);
				if(!mi029.getTel().equals(list.get(0).getUserid())){
					mi031.setUserid(mi029.getTel()+"-"+mi029.getCertinum());
					mi031.setDatemodified(CommonUtil.getSystemDate());
					mi031DAO.updateByPrimaryKeySelective(mi031);
				}
			}else{
				Mi031Example mi031E = new Mi031Example();
				mi031E.setOrderByClause("datecreated desc");
				mi031E.createCriteria().andCenteridEqualTo(mi029.getCenterid())
				.andChannelEqualTo("70").andPidEqualTo("70000133")
				.andUseridEqualTo(mi029.getTel());			
				List<Mi031> listCZ = mi031DAO.selectByExample(mi031E);
				if(!CommonUtil.isEmpty(listCZ)){
					//throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(), "您的手机号已经存在！");
					mi031DAO.deleteByPrimaryKey(listCZ.get(0).getId());
				}
				Mi031 mi031 = new Mi031();
				mi031.setId(genKeyStatic31("MI031",20));
				mi031.setPersonalid(mi029.getPersonalid());
				mi031.setCenterid(mi029.getCenterid());
				mi031.setChannel("70");
				mi031.setSendmessage("1");
				mi031.setPid("70000133");
				mi031.setUserid(mi029.getTel()+"-"+mi029.getCertinum());
				mi031.setValidflag(Constants.IS_VALIDFLAG);
				mi031.setDatecreated(CommonUtil.getSystemDate());
				mi031.setDatemodified(CommonUtil.getSystemDate());
				mi031DAO.insert(mi031);
				System.out.println("webapi02915执行结束啦");
			}
		}
	}
	
	public int webapi02925(AppApi50001Form form ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Mi031Example mi031Example = new Mi031Example();
		mi031Example.setOrderByClause("datecreated desc");
		mi031Example.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andChannelEqualTo("20").andUseridEqualTo(form.getUserId())
		.andValidflagEqualTo("1");
		int num = mi031DAO.deleteByExample(mi031Example);
		return num;
	}
	
	public Mi029 webapi02926(AppApi50001Form form ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Mi048Example mi048Example = new Mi048Example();
//		mi048Example.setOrderByClause("datecreated desc");
		mi048Example.createCriteria().andAccounttypeEqualTo("01").andAccountcodeEqualTo(form.getUserId());
		List<Mi048> list = mi048DAO.selectByExample(mi048Example);
		if(CommonUtil.isEmpty(list)){
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(), "公积金账户不存在！"); 
		}else{
			return cmi029DAO.selectByPrimaryKey(list.get(0).getPersonalid());
		}
	}
	
	public void webapi02927(HashMap hasMap, AppApi50001Form form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("渠道用户城市代码为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "渠道用户城市代码为空");
		}
		if(CommonUtil.isEmpty(String.valueOf(hasMap.get("accname")).trim())){
			log.error(ERROR.PARAMS_NULL.getLogText("渠道用户用户名称为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "渠道用户用户名称为空");
		}
		if(CommonUtil.isEmpty(String.valueOf(hasMap.get("certinum")).trim())){
			log.error(ERROR.PARAMS_NULL.getLogText("渠道用户身份证号为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "渠道用户身份证号为空");
		}
		Mi029 mi029 = new Mi029();
		mi029.setPersonalid(genKeyStatic29("MI029",20));
		mi029.setCenterid(form.getCenterId());
		mi029.setUsername(String.valueOf(hasMap.get("accname")));
		mi029.setCertinumtype(CommonUtil.isEmpty(form.getCertinumType())?"01":form.getCertinumType());
		String certinum = String.valueOf(hasMap.get("certinum")).trim();
		mi029.setCertinum(certinum);
		if(certinum.length() == 15){
			mi029.setBirth("19"+certinum.substring(6, 8)+"-"+certinum.substring(8, 10)+"-"+certinum.substring(10, 12));
			if(Integer.valueOf(certinum.substring(14))%2 != 0){
				mi029.setSex("0");
			}else{
				mi029.setSex("1");
			}
		}else if(certinum.length() == 18){
			mi029.setBirth(certinum.substring(6, 10)+"-"+certinum.substring(10, 12)+"-"+certinum.substring(12, 14));
			if(Integer.valueOf(certinum.substring(16, 17))%2 != 0){
				mi029.setSex("0");
			}else{
				mi029.setSex("1");
			}
		}
		mi029.setTel(String.valueOf(hasMap.get("phone")));
		mi029.setValidflag(Constants.IS_VALIDFLAG);
		mi029.setDatecreated(CommonUtil.getSystemDate());
		mi029.setDatemodified(CommonUtil.getSystemDate());
		mi029.setUnitaccnum(String.valueOf(hasMap.get("unitaccnum")));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(mi029)));
		cmi029DAO.insert(mi029);
		log.debug("***3###");
		webapi02924(mi029 ,request ,response);//插入或更新70手机短信渠道信息
		webapi02917(mi029 ,form ,hasMap);
		log.debug("***4###");
		Mi031 mi031 = new Mi031();
		mi031.setId(genKeyStatic31("MI031",20));
		mi031.setPersonalid(mi029.getPersonalid());
		mi031.setCenterid(form.getCenterId());
		mi031.setChannel(form.getChannel());
		mi031.setPid((String)request.getAttribute("MI040Pid").toString());
		mi031.setUserid(form.getUserId());
		mi031.setSendmessage("1");
		mi031.setValidflag(Constants.IS_VALIDFLAG);
		mi031.setDatecreated(CommonUtil.getSystemDate());
		mi031.setDatemodified(CommonUtil.getSystemDate());
		mi031DAO.insert(mi031);
		if(form.getCenterId().equals("00057400")){
			if("10000136".equals((String)request.getAttribute("MI040Pid").toString())){//APP注册同步网厅
				Mi031 mi031APP = new Mi031();
				mi031APP.setId(genKeyStatic31("MI031",20));
				mi031APP.setPersonalid(mi029.getPersonalid());
				mi031APP.setCenterid(form.getCenterId());
				mi031APP.setChannel("40");
				mi031APP.setPid("40000130");
				mi031APP.setUserid(form.getUserId());
				mi031APP.setSendmessage("1");
				mi031APP.setValidflag(Constants.IS_VALIDFLAG);
				mi031APP.setDatecreated(CommonUtil.getSystemDate());
				mi031APP.setDatemodified(CommonUtil.getSystemDate());
				mi031DAO.insert(mi031APP);
			}else if("40000130".equals((String)request.getAttribute("MI040Pid").toString())){//网厅注册同步APP
				Mi031 mi031WT = new Mi031();
				mi031WT.setId(genKeyStatic31("MI031",20));
				mi031WT.setPersonalid(mi029.getPersonalid());
				mi031WT.setCenterid(form.getCenterId());
				mi031WT.setChannel("10");
				mi031WT.setPid("10000136");
				mi031WT.setUserid(form.getUserId());
				mi031WT.setSendmessage("1");
				mi031WT.setValidflag(Constants.IS_VALIDFLAG);
				mi031WT.setDatecreated(CommonUtil.getSystemDate());
				mi031WT.setDatemodified(CommonUtil.getSystemDate());
				mi031DAO.insert(mi031WT);
			}
		}
	}
	
	public Mi048 webapi02928(Mi029 mi029 ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Mi048Example mi048Example = new Mi048Example();
		mi048Example.createCriteria().andAccounttypeEqualTo("01").andPersonalidEqualTo(mi029.getPersonalid());
		List<Mi048> list = mi048DAO.selectByExample(mi048Example);
		if(CommonUtil.isEmpty(list)){
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(), "公积金账户不存在！"); 
		}else{
			return list.get(0);
		}
	}
	
	public CMi029DAO getCmi029DAO() {
		return cmi029DAO;
	}

	public void setCmi029DAO(CMi029DAO cmi029dao) {
		cmi029DAO = cmi029dao;
	}

	public Mi031DAO getMi031DAO() {
		return mi031DAO;
	}

	public void setMi031DAO(Mi031DAO mi031dao) {
		mi031DAO = mi031dao;
	}

	public CommonUtil getCommonUtil() {
		return commonUtil;
	}

	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}

	public CMi607DAO getCmi607Dao() {
		return cmi607Dao;
	}

	public void setCmi607Dao(CMi607DAO cmi607Dao) {
		this.cmi607Dao = cmi607Dao;
	}

	public Mi034DAO getMi034DAO() {
		return mi034DAO;
	}

	public void setMi034DAO(Mi034DAO mi034dao) {
		mi034DAO = mi034dao;
	}

	public Mi048DAO getMi048DAO() {
		return mi048DAO;
	}

	public void setMi048DAO(Mi048DAO mi048dao) {
		mi048DAO = mi048dao;
	}

	public Mi007DAO getMi007Dao() {
		return mi007Dao;
	}

	public void setMi007Dao(Mi007DAO mi007Dao) {
		this.mi007Dao = mi007Dao;
	}

	public Mi040DAO getMi040DAO() {
		return mi040DAO;
	}

	public void setMi040DAO(Mi040DAO mi040dao) {
		mi040DAO = mi040dao;
	}

	//add by Carter King  2017-11-08
	public static String genKeyStatic29(String key , int num) throws Exception{
		int n = genKeyAndIncreaseStatic29().intValue();
		StringBuffer value = new StringBuffer();		
		for(int i=String.valueOf(n).length();i<num;i++){
			value.append("0");
		}
		value.append(n);
		return value.toString();
	}
	
	//add by Carter King  2017-11-08   
	public static Integer genKeyAndIncreaseStatic29() throws Exception{
		CMi008DAOImpl cmi008Dao = (CMi008DAOImpl)SpringContextUtil.getBean("cmi008Dao");
		Connection conn=cmi008Dao.getDataSource().getConnection();
		//Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//172.16.0.165:1521/netdb","midev","midev") ;
		String sql = "select mi029_sequence.nextval as sequence from dual";
		PreparedStatement pstm = conn.prepareStatement(sql) ;
		ResultSet rs = pstm.executeQuery() ;
		 Integer sequence29 =null;
		while(rs.next()){
			 sequence29= Integer.parseInt(rs.getObject("sequence").toString());
		 }
		 rs.close();
		 pstm.close() ;
		 conn.close();
		 return sequence29;
	}
	
	//add by Carter King  2017-11-20
	public static String genKeyStatic31(String key , int num) throws Exception{
		int n = genKeyAndIncreaseStatic31().intValue();
		StringBuffer value = new StringBuffer();		
		for(int i=String.valueOf(n).length();i<num;i++){
			value.append("0");
		}
		value.append(n);
		return value.toString();
	}
	
	//add by Carter King  2017-11-20
	public static Integer genKeyAndIncreaseStatic31() throws Exception{
		CMi008DAOImpl cmi008Dao = (CMi008DAOImpl)SpringContextUtil.getBean("cmi008Dao");
		Connection conn=cmi008Dao.getDataSource().getConnection();
		String sql = "select mi031_sequence.nextval as sequence from dual";
		PreparedStatement pstm = conn.prepareStatement(sql) ;
		ResultSet rs = pstm.executeQuery() ;
		 Integer sequence31 =null;
		while(rs.next()){
			 sequence31= Integer.parseInt(rs.getObject("sequence").toString());
		 }
		 rs.close();
		 pstm.close() ;
		 conn.close();
		 return sequence31;
	}
	
}
