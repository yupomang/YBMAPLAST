package com.yondervision.mi.service.impl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi005DAO;
import com.yondervision.mi.dao.CMi014DAO;
import com.yondervision.mi.dao.CMi055DAO;
import com.yondervision.mi.dao.CMi056DAO;
import com.yondervision.mi.dao.Mi001DAO;
import com.yondervision.mi.dao.Mi002DAO;
import com.yondervision.mi.dao.Mi003DAO;
import com.yondervision.mi.dao.Mi004DAO;
import com.yondervision.mi.dao.Mi005DAO;
import com.yondervision.mi.dao.Mi006DAO;
import com.yondervision.mi.dao.Mi013DAO;
import com.yondervision.mi.dao.Mi014DAO;
import com.yondervision.mi.dto.CMi002;
import com.yondervision.mi.dto.CMi005;
import com.yondervision.mi.dto.CMi006Key;
import com.yondervision.mi.dto.CMi013Key;
import com.yondervision.mi.dto.CMi014;
import com.yondervision.mi.dto.CMi055;
import com.yondervision.mi.dto.CMi056;
import com.yondervision.mi.dto.Mi001;
import com.yondervision.mi.dto.Mi001Example;
import com.yondervision.mi.dto.Mi002;
import com.yondervision.mi.dto.Mi002Example;
import com.yondervision.mi.dto.Mi003;
import com.yondervision.mi.dto.Mi003Example;
import com.yondervision.mi.dto.Mi004;
import com.yondervision.mi.dto.Mi004Example;
import com.yondervision.mi.dto.Mi005;
import com.yondervision.mi.dto.Mi005Example;
import com.yondervision.mi.dto.Mi006;
import com.yondervision.mi.dto.Mi006Example;
import com.yondervision.mi.dto.Mi006Example.Criteria;
import com.yondervision.mi.dto.Mi013;
import com.yondervision.mi.dto.Mi013Example;
import com.yondervision.mi.dto.Mi014;
import com.yondervision.mi.dto.Mi014Example;
import com.yondervision.mi.dto.Mi055;
import com.yondervision.mi.dto.Mi055Example;
import com.yondervision.mi.dto.Mi056;
import com.yondervision.mi.dto.Mi056Example;
import com.yondervision.mi.form.PwdModForm;
import com.yondervision.mi.result.WebApi00504_queryResult;
import com.yondervision.mi.result.WebApi01404_queryResult;
import com.yondervision.mi.result.WebApi05504_queryResult;
import com.yondervision.mi.result.WebApi05604_queryResult;
import com.yondervision.mi.service.WebApi055Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;
import com.yondervision.mi.util.SpringContextUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WebApi055ServiceImpl implements WebApi055Service {
    
	@Autowired
	private Mi001DAO mi001Dao;
	
	@Autowired
	private Mi002DAO mi002Dao;
	
	@Autowired
	private Mi003DAO mi003Dao;
	
	@Autowired
	private Mi004DAO mi004Dao;
	
	@Autowired
	private Mi005DAO mi005Dao;
	
	@Autowired
	private Mi006DAO mi006Dao;
	
	@Autowired
	private Mi013DAO mi013Dao;
	
	@Autowired
	private Mi014DAO mi014Dao;
	
	public Mi001DAO getMi001Dao() {
		return mi001Dao;
	}
	public void setMi001Dao(Mi001DAO mi001Dao) {
		this.mi001Dao = mi001Dao;
	}
	public Mi002DAO getMi002Dao() {
		return mi002Dao;
	}
	public void setMi002Dao(Mi002DAO mi002Dao) {
		this.mi002Dao = mi002Dao;
	}
	public Mi003DAO getMi003Dao() {
		return mi003Dao;
	}
	public void setMi003Dao(Mi003DAO mi003Dao) {
		this.mi003Dao = mi003Dao;
	}
	public Mi004DAO getMi004Dao() {
		return mi004Dao;
	}
	public void setMi004Dao(Mi004DAO mi004Dao) {
		this.mi004Dao = mi004Dao;
	}
	public Mi005DAO getMi005Dao() {
		return mi005Dao;
	}
	public void setMi005Dao(Mi005DAO mi005Dao) {
		this.mi005Dao = mi005Dao;
	}
	public Mi006DAO getMi006Dao() {
		return mi006Dao;
	}
	public void setMi006Dao(Mi006DAO mi006Dao) {
		this.mi006Dao = mi006Dao;
	}
	public Mi013DAO getMi013Dao() {
		return mi013Dao;
	}
	public void setMi013Dao(Mi013DAO mi013Dao) {
		this.mi013Dao = mi013Dao;
	}
	public Mi014DAO getMi014Dao() {
		return mi014Dao;
	}
	public void setMi014Dao(Mi014DAO mi014Dao) {
		this.mi014Dao = mi014Dao;
	}
	
	@SuppressWarnings("unchecked")
	public List<Mi003> getRoleAllList(String centerid){
	    Mi003Example example=new Mi003Example();
    	example.createCriteria().andCenteridEqualTo(centerid);
	    List<Mi003> list=mi003Dao.selectByExample(example);	    
		return list;
	}
	@SuppressWarnings("unchecked")
	public   JSONObject getCenterListJson(String centerid) {
		  ObjectMapper mapper = new  ObjectMapper();
		  Mi001Example example=new Mi001Example();
		  // 判断登陆者的城市中心代码进行查询条件的确定（‘00000000’：永道管理员）
		  if (!Constants.YD_ADMIN.equals(centerid)) {
			  example.createCriteria().andCenteridEqualTo(centerid);
		  }
		  example.createCriteria().andValidflagEqualTo(Constants.IS_VALIDFLAG);
		  List<Mi001> list=mi001Dao.selectByExample(example);
		  HashMap map=new HashMap();
		  map.put("rows", list);
		  return mapper.convertValue(map, JSONObject.class); 
	}
	
	@SuppressWarnings("unchecked")
	public void addOper(CMi002 mi002){	
		Mi002Example example=new Mi002Example();
	    example.createCriteria().andLoginidEqualTo(mi002.getLoginid()); 
	    List<Mi002> list=mi002Dao.selectByExample(example);
		String defaultpwd = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "default_pwd");
	    mi002.setPassword(EncryptionByMD5.getMD5(defaultpwd.getBytes()));
	    if(list.size()==0){
		    mi002Dao.insert(mi002);
		    Mi004Example example4=new Mi004Example();
		    example4.createCriteria().andLoginidEqualTo(mi002.getLoginid());
		    mi004Dao.deleteByExample(example4);
		    for(int i=0;i<mi002.getRole().length;i++){
		    	Mi004 mi004=new Mi004();
		    	mi004.setLoginid(mi002.getLoginid());
		    	mi004.setRoleid(mi002.getRole()[i]);
		    	mi004Dao.insert(mi004);		    	
		    }
	    }else
	    	throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(),"操作代码："+mi002.getLoginid());
	}
	public void updateOper(CMi002 mi002){
		Mi002Example example=new Mi002Example();
		example.createCriteria().andLoginidEqualTo(mi002.getLoginid());		
		mi002Dao.updateByExample(mi002, example);	
		
		Mi004Example example4=new Mi004Example();
	    example4.createCriteria().andLoginidEqualTo(mi002.getLoginid());
	    mi004Dao.deleteByExample(example4);
	    for(int i=0;i<mi002.getRole().length;i++){
	    	Mi004 mi004=new Mi004();
	    	mi004.setLoginid(mi002.getLoginid());
	    	mi004.setRoleid(mi002.getRole()[i]);
	    	mi004Dao.insert(mi004);		    	
	    }
	}
	public void delOper(List<String> list){
		Mi002Example example=new Mi002Example();
		example.createCriteria().andLoginidIn(list);
		mi002Dao.deleteByExample(example);
		Mi004Example example4=new Mi004Example();
		example4.createCriteria().andLoginidIn(list);
		mi004Dao.deleteByExample(example4);		
	}
	public void delRole(List<String> list){
		Mi003Example example=new Mi003Example();
		example.createCriteria().andRoleidIn(list);
		mi003Dao.deleteByExample(example);
		Mi004Example example4=new Mi004Example();
		example4.createCriteria().andRoleidIn(list);
		mi004Dao.deleteByExample(example4);		
	}
	public void addRole(Mi003 mi003){
		Logger log = LoggerUtil.getLogger();
		// 校验新增信息是否已经存在
		int count = getRoleCounts(mi003);
		if(0 != count){
			log.error(ERROR.ADD_CHECK.getLogText(CommonUtil.getStringParams(mi003)));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"此角色代码已存在，请确认后提交！");
		}
		
		UserContext user = UserContext.getInstance();
		if(!Constants.YD_ADMIN.equals(user.getCenterid())){
			mi003.setAttributeflg(Constants.ATTRIBUTE_FLG_PRIVATE);
		}
		//入库格式化目的：为了操作员页面获取角色列表时的JSONArray转换不报错
		if(CommonUtil.isEmpty(mi003.getRolename())){
			mi003.setRolename("");
		}
		if(CommonUtil.isEmpty(mi003.getRight())){
			mi003.setRight("");
		}
		if(CommonUtil.isEmpty(mi003.getCenterid())){
			mi003.setCenterid("");
		}
		if(CommonUtil.isEmpty(mi003.getAttributeflg())){
			mi003.setAttributeflg("");
		}
		mi003Dao.insert(mi003);
	}
	public void updateRole(Mi003 mi003){
		Mi003Example example3=new Mi003Example();
		example3.createCriteria().andRoleidEqualTo(mi003.getRoleid());
		//入库格式化目的：为了操作员页面获取角色列表时的JSONArray转换不报错
		if(CommonUtil.isEmpty(mi003.getRolename())){
			mi003.setRolename("");
		}
		if(CommonUtil.isEmpty(mi003.getRight())){
			mi003.setRight("");
		}
		if(CommonUtil.isEmpty(mi003.getCenterid())){
			mi003.setCenterid("");
		}
		if(CommonUtil.isEmpty(mi003.getAttributeflg())){
			mi003.setAttributeflg("");
		}
		mi003Dao.updateByExample(mi003, example3); 
	}
	
	@SuppressWarnings("unchecked")
	public JSONArray getRoleListJson(String centerid) {
		ObjectMapper mapper = new  ObjectMapper();
		Mi003Example example = new Mi003Example();
		example.createCriteria().andCenteridEqualTo(centerid);
		if(!Constants.YD_ADMIN.equals(centerid)){
			Mi003Example exa = new Mi003Example();
			example.or(exa.createCriteria().andCenteridEqualTo(Constants.YD_ADMIN)
					.andAttributeflgEqualTo(Constants.ATTRIBUTE_FLG_PUBLIC));
		}
		example.setOrderByClause("roleid");
		List<Mi003> list = mi003Dao.selectByExample(example); 
		return mapper.convertValue(list, JSONArray.class); 
	}
	
	/**
	 * 根据城市代码的不同获取操作员管理页面的角色信息的列表
	 * （“00000000”：获取所有角色列表，其他获取对应的角色+“00000000”的公共角色）
	 */
	@SuppressWarnings("unchecked")
	public JSONArray getRoleListJsonByOper(String centerid) {
		ObjectMapper mapper = new  ObjectMapper();
		Mi003Example example = new Mi003Example();
		if(!Constants.YD_ADMIN.equals(centerid)){
			example.createCriteria().andCenteridEqualTo(centerid);
			Mi003Example exa = new Mi003Example();
			example.or(exa.createCriteria().andCenteridEqualTo(Constants.YD_ADMIN)
					.andAttributeflgEqualTo(Constants.ATTRIBUTE_FLG_PUBLIC));
		}
		example.setOrderByClause("roleid");
		List<Mi003> list = mi003Dao.selectByExample(example); 
		return mapper.convertValue(list, JSONArray.class); 
	}

	/**
	 * 角色表记录数查询
	 */
	private int getRoleCounts(Mi003 form){
		int counts = 0;
		
		Mi003Example example = new Mi003Example();
		example.createCriteria().andRoleidEqualTo(form.getRoleid());
		
		counts = mi003Dao.countByExample(example);

		return counts;
	}
	
	@SuppressWarnings("unchecked")
	public JSONArray getCenterListJsonArray(String centerid) {
		ObjectMapper mapper = new  ObjectMapper();
		Mi001Example example=new Mi001Example();
		if (!Constants.YD_ADMIN.equals(centerid)) {
			example.createCriteria().andCenteridEqualTo(centerid);
		}
		example.createCriteria().andValidflagEqualTo(Constants.IS_VALIDFLAG);
		example.setOrderByClause("centerid");
		List<Mi003> list= mi001Dao.selectByExample(example);
		return mapper.convertValue(list, JSONArray.class); 	
	}
	
	@SuppressWarnings("unchecked")
	public   List<CMi002> getOperListByCenterJson(String centerid) {
		if(centerid==null)
			return new ArrayList<CMi002>();
		Mi002Example example=new Mi002Example();
		example.createCriteria().andCenteridEqualTo(centerid);
		List<Mi002> list=mi002Dao.selectByExample(example);
		List<CMi002> clist=new ArrayList<CMi002>();
	    ObjectMapper mapper = new  ObjectMapper(); 
		for(int i=0;i<list.size();i++){
			 String loginid=list.get(i).getLoginid();
			 Mi004Example te=new Mi004Example();
			 te.createCriteria().andLoginidEqualTo(loginid);
			 List<Mi004> tlist=mi004Dao.selectByExample(te);
			 CMi002 cmi002= mapper.convertValue(list.get(i), CMi002.class); 			 
			 cmi002.setRoleList(tlist);		
			 clist.add(cmi002);
		}
		return clist;
	}
	
	@SuppressWarnings("unchecked")
	public List<Mi001> getCenterAllList(String centerid){
	    Mi001Example example=new Mi001Example();
	    if (!Constants.YD_ADMIN.equals(centerid)) {
	    	example.createCriteria().andCenteridEqualTo(centerid);
	    }
    	//example.createCriteria().andValidflagEqualTo(Constants.IS_VALIDFLAG);
	    List<Mi001> list=mi001Dao.selectByExample(example);	    
		return list; 
	}
	
	@Transactional(noRollbackFor=TransRuntimeErrorException.class)	  
	public void delCenter(List<String> list){
		Logger log = LoggerUtil.getLogger();
		// 校验当前待删除list中的是否含有已注销记录，如有，则提示信息“当前【{待删除}】内容中包含已【{注销}】记录，不能进行【{删除}】，请重新选择！”
		for(int i=0;i<list.size();i++){
			Mi001 mi001 = mi001Dao.selectByPrimaryKey((String)list.get(i));
			if(Constants.IS_NOT_VALIDFLAG.equals(mi001.getValidflag())){
				log.error(ERROR.VALIDFLG_CHECK.getLogText("中心编码："+mi001.getCenterid()));
				throw new TransRuntimeErrorException(WEB_ALERT.VALIDFLG_LIST_CHECK.getValue(),"待删除","注销","删除");
			}
		}
		
		// 记录删除
		for(int i=0;i<list.size();i++){
			Mi001 mi001 = new Mi001();
			mi001.setValidflag(Constants.IS_NOT_VALIDFLAG);
			Mi001Example example=new Mi001Example();
			example.createCriteria().andCenteridEqualTo((String)list.get(i));
			mi001Dao.updateByExampleSelective(mi001, example);			
		}
	}
	
	@SuppressWarnings("unchecked")
	public void addCenter(Mi001 mi001){
		Logger log = LoggerUtil.getLogger();
		
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);
		Date date = new Date();
		
		// 根据传入的中心代码，查询其对应的中心信息
		Mi001Example example = new Mi001Example();
		example.createCriteria().andCenteridEqualTo(mi001.getCenterid());
		List<Mi001> qryList = mi001Dao.selectByExample(example);
		// 当前centerid记录存在的场合
		if(!CommonUtil.isEmpty(qryList)){
			// 判断当前记录的有效标志是否为无效
			if(Constants.IS_NOT_VALIDFLAG.equals(qryList.get(0).getValidflag())) {
				// 将此条记录的有效标志状态进行更新
				log.error(ERROR.VALIDFLG_CHECK.getLogText("中心编码："+mi001.getCenterid()));
				throw new TransRuntimeErrorException(WEB_ALERT.VALIDFLG_CHECK.getValue(),"中心代码");
				
			}else{
				log.error(ERROR.ADD_CHECK.getLogText(CommonUtil.getStringParams(mi001)));
				throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"当前中心代码已存在，请确认后提交！");
			}
		}else{
			// 进行记录的新增
			mi001.setValidflag(Constants.IS_VALIDFLAG);
			mi001.setDatecreated(formatter.format(date));
			mi001.setDatemodified(formatter.format(date));
			mi001Dao.insert(mi001); 
		}
	}
	
	public void updateCenter(Mi001 mi001){
		Logger log = LoggerUtil.getLogger();
		// 判断当前记录有效标志，如无效，则提示信息“此【{0}】记录已注销,请“激活”后，再进行其他操作。”
		if(Constants.IS_NOT_VALIDFLAG.equals(mi001.getValidflag())){
			log.error(ERROR.VALIDFLG_CHECK.getLogText("中心编码："+mi001.getCenterid()));
			throw new TransRuntimeErrorException(WEB_ALERT.VALIDFLG_CHECK.getValue(),"中心代码");
		}
		Mi001Example example=new Mi001Example();
		example.createCriteria().andCenteridEqualTo(mi001.getCenterid());
		mi001Dao.updateByExampleSelective(mi001, example);
	}
	
	@Transactional(noRollbackFor=TransRuntimeErrorException.class)	  
	public void activationCenter(List<String> list){
		Logger log = LoggerUtil.getLogger();
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);

		// 校验当前待激活list中的是否含有已激活记录，如有，则提示信息“当前【{待激活}】内容中包含已【{激活}】记录，不能进行【{激活}】，请重新选择！”
		for(int i=0;i<list.size();i++){
			Mi001 mi001 = mi001Dao.selectByPrimaryKey((String)list.get(i));
			if(Constants.IS_VALIDFLAG.equals(mi001.getValidflag())){
				log.error(ERROR.VALIDFLG_CHECK_TRUE.getLogText(CommonUtil.getStringParams(mi001)));
				throw new TransRuntimeErrorException(WEB_ALERT.VALIDFLG_LIST_CHECK.getValue(),"待激活","激活","激活");
			}
		}
		for(int i=0;i<list.size();i++){
			Mi001 record = new Mi001();
			record.setCenterid((String)list.get(i));
			record.setValidflag(Constants.IS_VALIDFLAG);
			record.setDatemodified(formatter.format(new Date()));
			mi001Dao.updateByPrimaryKeySelective(record);
		}
	}

	@SuppressWarnings("unchecked")
	public JSONArray getMenuJson(String centerid, String funcid){
		
		// 根据中心ID到机构权限中获取权限存入vec中
		Mi013Example m13e = new Mi013Example();
		m13e.createCriteria().andCenteridEqualTo(centerid);
		List<Mi013> mi013QryList = mi013Dao.selectByExample(m13e);
		Vector<String> menuvec = new Vector<String>();
		Map<CMi013Key, Integer> mapMi301Key = new HashMap<CMi013Key, Integer>();
		for(int i = 0; i < mi013QryList.size(); i++){
			Mi013 mi013 = mi013QryList.get(i);
			menuvec.add(mi013.getFuncid());
			 
			CMi013Key key = new CMi013Key();
			key.setCenterid(centerid);
			key.setFuncid(mi013.getFuncid());
			mapMi301Key.put(key, new Integer(mi013.getPermission()));
		}
		
		JSONArray ary=new JSONArray();
		// 菜单表获取菜单项
		Mi005DAO mi005Dao = (Mi005DAO)SpringContextUtil.getBean("mi005Dao");
		Mi005Example m5e = new Mi005Example();
		m5e.createCriteria().andParentfuncidEqualTo(funcid);
		m5e.setOrderByClause("orderid");
		List<Mi005> mainMenulist = mi005Dao.selectByExample(m5e);
		for(int i = 0;i < mainMenulist.size(); i++){
			Mi005 mi005 = mainMenulist.get(i);
			
			JSONArray tary = getFuncPermissionJson(mi005.getFuncid(), centerid,
					mapMi301Key, menuvec);

			JSONObject obj = new JSONObject();
			// 主菜单有子菜单，才将其加入到返回结果中
			if(tary.size()>0){
				obj.put("funcid", mi005.getFuncid());
				obj.put("funcname", mi005.getFuncname());
				obj.put("pid", mi005.getParentfuncid());
				obj.put("children", tary); 
				//用于页面checkbox框的显示的判断条件之一
				obj.put("leafFlg", Constants.IS_NOT_LEAF_FLG);
				ary.add(obj);
			}
		 }
		
		return ary;
	}
	
	// 检索符合某种权限范围的菜单编码的JSONArray(包括各菜单下的各子功能)
	@SuppressWarnings("unchecked")
	private JSONArray getFuncPermissionJson(String funcid, String centerid,
			Map<CMi013Key, Integer> mapMi301Key, Vector<String> menuvec){
		JSONArray tary = new JSONArray();
		
		Mi005Example m5e1 = new Mi005Example();
		m5e1.createCriteria().andParentfuncidEqualTo(funcid);
		m5e1.setOrderByClause("orderid");
		List<Mi005> childlist5 = mi005Dao.selectByExample(m5e1);
		// 循环下级菜单
		for(int j = 0;j < childlist5.size(); j++){
			// 判断当前菜单编码是否在权限vec中
			if(menuvec.contains(childlist5.get(j).getFuncid())){
				Mi005 childmi005 = childlist5.get(j);
				JSONObject childobj = new JSONObject();
				childobj.put("funcid", childmi005.getFuncid());
				childobj.put("funcname", childmi005.getFuncname());
				childobj.put("pid", childmi005.getParentfuncid());
				
				// 检索对应菜单编码下的符合某种权限范围的子功能的JSONArray
				JSONArray subary = getChildFuncPermissionJson(childmi005.getFuncid(), centerid, mapMi301Key);
				
				if(subary.size() > 0){
					childobj.put("children", subary);
					//用于页面checkbox框的显示的判断条件之一
					childobj.put("leafFlg", Constants.IS_NOT_LEAF_FLG);
				}else{
					//用于页面checkbox框的显示的判断条件之一
					childobj.put("leafFlg", Constants.IS_LEAF_FLG);
				}
				tary.add(childobj);
			}
		}
		return tary;
	}
	
	// 检索对应菜单编码下的符合某种权限范围的子功能的JSONArray
	@SuppressWarnings("unchecked")
	private JSONArray getChildFuncPermissionJson(String funcid, String centerid, Map<CMi013Key, Integer> mapMi301Key){
		String temp = "00000000";
		// 检索该子菜单下的子功能
		JSONArray subary = new JSONArray();
		Mi014Example m14e = new Mi014Example();
		m14e.createCriteria().andFuncidEqualTo(funcid);
		m14e.setOrderByClause("subno");
		List<Mi014> list14 = mi014Dao.selectByExample(m14e); 
		for(int k = 0; k < list14.size(); k++){
			Mi014 mi014 = list14.get(k);
			// 对当前子功能序号进行十进制到二进制转换
			int subnoInt = 8-mi014.getSubno().intValue();
			Double subnoDouble = Math.pow(2, subnoInt);
			String subnoStr = Integer.toBinaryString(subnoDouble.intValue());
			if (subnoStr.length()<8) {
				subnoStr = temp.substring(subnoStr.length()) + subnoStr;
			}
			// 获取对应中心id，功能编码对应的子功能的权限
			CMi013Key cmi013KeyTmp = new CMi013Key();
			cmi013KeyTmp.setCenterid(centerid);
			cmi013KeyTmp.setFuncid(funcid);
			Integer permission = mapMi301Key.get(cmi013KeyTmp);
			// 判断当前子功能是否在该机构权限(将subnoStr 与  funcvec 中的Permission做按位与，如结果=subnoStr，则有此权限)
			String bitwiseAnd = Integer.toBinaryString(Integer.parseInt(subnoStr, 2)&Integer.parseInt(permission.toString(), 2));
			if(bitwiseAnd.length()<8){
				bitwiseAnd = temp.substring(bitwiseAnd.length()) + bitwiseAnd;
			}
			if (subnoStr.equals(bitwiseAnd)){
				JSONObject subobj = new JSONObject();
				subobj.put("funcid", mi014.getFuncid()+(k+1));
				subobj.put("funcname", mi014.getSubdesc());
				subobj.put("pid", mi014.getFuncid());
				//用于页面checkbox框的显示的判断条件之一
				subobj.put("leafFlg", Constants.IS_LEAF_FLG);

				subary.add(subobj);
			}
		}
		return subary;
	}

	@SuppressWarnings("unchecked")
	public JSONArray getPermissionMenuJson(List<String> roles, String centerid){
		Mi006Example m6e=new Mi006Example();
		Criteria criteria = m6e.createCriteria();
		if(roles!=null)
			criteria.andRoleidIn(roles);
		criteria.andCenteridEqualTo(centerid);
		List<Mi006> list= mi006Dao.selectByExample(m6e);
		ObjectMapper mapper = new  ObjectMapper();
		return mapper.convertValue(list, JSONArray.class);
	}
	
	@Transactional(noRollbackFor=TransRuntimeErrorException.class)	    
	public void savePermission(String[] permissions, String centerid){
		// 删除待更新城市的角色权限分配记录
		Mi006Example m6e = new Mi006Example();
		m6e.createCriteria().andCenteridEqualTo(centerid);
		mi006Dao.deleteByExample(m6e);
		
		/*
		 * 算出每条数据权限十进制值，并放入mapMi006中
		 */
		Map<CMi006Key, Integer> mapMi006 = new HashMap<CMi006Key, Integer>();
		for(int i = 0; i < permissions.length; i++){
			String[] permission = permissions[i].split("_");
			String funcid = permission[0];
			String roleid = permission[1];
			String seqno = permission[2];
			
			if (seqno.equals("0")) {
				Mi006 mi006 = new Mi006();
				mi006.setFuncid(funcid);
				mi006.setRoleid(roleid);
				mi006.setPermission("00000000");
				mi006.setCenterid(centerid);
				mi006Dao.insert(mi006);
				continue;
			}

			CMi006Key key = new CMi006Key();
			key.setFuncid(funcid);
			key.setRoleid(roleid);
			key.setCenterid(centerid);
			Integer tempSeqno = mapMi006.get(key);
			if (CommonUtil.isEmpty(tempSeqno)) {
				mapMi006.put(key, new Integer(seqno));
			} else {
				mapMi006.put(key, tempSeqno + new Integer(seqno));
			}
		}
		
		/*
		 * 保存数据
		 */
		String temp = "00000000";
		for (Iterator<CMi006Key> iterator = mapMi006.keySet().iterator(); iterator.hasNext();) {
			CMi006Key key = (CMi006Key) iterator.next();
			String permission = Integer.toBinaryString(mapMi006.get(key));
			if (permission.length()<8) {
				permission = temp.substring(permission.length()) + permission;
			}
			Mi006 mi006 = new Mi006();
			mi006.setFuncid(key.getFuncid());
			mi006.setRoleid(key.getRoleid());
			mi006.setPermission(permission);
			mi006.setCenterid(key.getCenterid());
			mi006Dao.insert(mi006);
		}
	} 
		
	@SuppressWarnings("unchecked")
	public List<Mi005> getMenuListByPid(String pid,String centerid){
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(pid)||CommonUtil.isEmpty(centerid)){
			log.error(ERROR.PARAMS_NULL.getLogText("父功能id或者中心为空："));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"父功能id或者中心为空：");
		}
		Mi005Example m5e=new Mi005Example();
		m5e.createCriteria().andParentfuncidEqualTo(pid).andCenteridEqualTo(centerid)
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
	    m5e.setOrderByClause("freeuse4");
	    return  mi005Dao.selectByExample(m5e); 
		 
	}
	
	public void delFunc(List<String> list){
		Mi005Example m5e=new Mi005Example();
		m5e.createCriteria().andFuncidIn(list);
		mi005Dao.deleteByExample(m5e);
	}
	public void addFunc(Mi005 mi005){
		Logger log = LoggerUtil.getLogger();
		// 根据菜单编码获取对应记录数
		int counts = getCounts(mi005.getFuncid());
		// 对应当前菜单编码的记录已存在
		if (0 != counts){
			log.error(ERROR.ADD_CHECK.getLogText("功能(菜单)编码："+mi005.getFuncid()));
			throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(),"功能(菜单)编码："+mi005.getFuncid());
		}else {
			mi005Dao.insert(mi005);
		}
	}
	public void updateFunc(String oldfuncid,Mi005 mi005){
		Logger log = LoggerUtil.getLogger();
		// 当前记录的菜单编码字段更新的场合
		if(!oldfuncid.equals(mi005.getFuncid())){
			// 判断对应新菜单编码的记录是否存在
			int counts = getCounts(mi005.getFuncid());
			if(0 != counts){
				log.error(ERROR.ADD_CHECK.getLogText("功能(菜单)编码："+mi005.getFuncid()));
				throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(),"功能(菜单)编码："+mi005.getFuncid());
			}
		}
		
		Mi005Example m5e=new Mi005Example();
		m5e.createCriteria().andFuncidEqualTo(oldfuncid);
		mi005Dao.updateByExample(mi005, m5e);
	}
	
	// 根据菜单编码获取对应记录数
	private int getCounts(String funcid){
		int counts = 0;
		Mi005Example m5e=new Mi005Example();
		m5e.createCriteria().andFuncidEqualTo(funcid);
		counts = mi005Dao.countByExample(m5e);
		return counts;
	}
	
	@SuppressWarnings("unchecked")
	public void updatePwd(PwdModForm form){
		Logger log = LoggerUtil.getLogger();
		
		// 检验登录名是否当前登录人做验证
		UserContext user = UserContext.getInstance();

		if (!form.getLoginid().equals(user.getLoginid())){
			log.debug(DEBUG.SHOW_PARAM.getLogText("输入登录名："+form.getLoginid()));
			log.debug(DEBUG.SHOW_PARAM.getLogText("实际登录名："+user.getLoginid()));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"输入登录名与当前登录名不符");
		}
		
		// 根据登录名获取登录密码
		Mi002Example example = new Mi002Example();
		example.createCriteria().andLoginidEqualTo(form.getLoginid());
		List<Mi002> list = mi002Dao.selectByExample(example);
		if (CommonUtil.isEmpty(list)) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("输入登录名："+form.getLoginid()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"登录名："+form.getLoginid());
		}
		
		//确认传入密码是否和数据库中一致，以此确认是否为本人（页面中已经对登录名是否当前登录人做验证）
		if (list.get(0).getPassword().equals(EncryptionByMD5.getMD5(form.getPassword().getBytes()))) {
			// 进行密码更新
			Mi002Example  mi002example = new Mi002Example();
			mi002example.createCriteria().andLoginidEqualTo(form.getLoginid());
			
			Mi002 mi002 = new Mi002();
			mi002.setPassword(EncryptionByMD5.getMD5(form.getNewpassword().getBytes()));
			mi002.setModidate(CommonUtil.getDate());
			mi002.setModitime(CommonUtil.getTime());
			
			int result = mi002Dao.updateByExampleSelective(mi002, mi002example);
			
			if (0 == result){
				log.error(ERROR.NO_DATA.getLogText("操作员表MI002","登录名："+form.getLoginid()));
				throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(),"操作员表MI002");
			}
		}else{
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"输入密码与当前登录密码不符");
			
		}
		
	}
	
	/**
	 * 获取子功能列表
	 */
	@SuppressWarnings("unchecked")
	public List<Mi014> getChildFuncListByPid(String pid){

		// 返回结果List
		List<Mi014> mi014List = new ArrayList<Mi014>();
		
		Mi014Example example = new Mi014Example();
		example.createCriteria().andFuncidEqualTo(pid);
		example.setOrderByClause("subno");
		mi014List = mi014Dao.selectByExample(example);
		
	    return  mi014List;
	}
	
	/**
	 * 增加子功能
	 * @param mi005
	 */
	public void addChildFunc(Mi014 mi014){
		Logger log = LoggerUtil.getLogger();
		
		// 检索当前待添加记录是否已经存在
		Mi014Example example = new Mi014Example();
		example.createCriteria().andFuncidEqualTo(mi014.getFuncid())
		.andSubnameEqualTo(mi014.getSubname());
		int count = mi014Dao.countByExample(example);
		if(0 != count){
			log.error(ERROR.ADD_CHECK.getLogText(CommonUtil.getStringParams(mi014)));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"此记录已存在，请确认后提交！");
		}
		
		mi014Dao.insert(mi014);
	}
	
	/**
	 * 删除子功能
	 * @param list
	 */
	@Transactional(noRollbackFor=TransRuntimeErrorException.class)	   
	public void delChildFunc(List<Mi014> list){
		
		for(int i = 0; i < list.size(); i++){
			Mi014 mi014 = list.get(i);
			Mi014Example example = new Mi014Example();
			example.createCriteria().andFuncidEqualTo(mi014.getFuncid())
			.andSubnameEqualTo(mi014.getSubname());
			mi014Dao.deleteByExample(example);
		}
	}
	
	/**
	 * 修改子功能
	 */
	public void updateChildFunc(String oldSubname, Mi014 mi014){
		Logger log = LoggerUtil.getLogger();
		
		// 判断子功能名称是否变化
		if(!oldSubname.equals(mi014.getSubname())){
			//检索对应当前的功能编码下，是否含有变更后的子功能名称
			Mi014Example example = new Mi014Example();
			example.createCriteria().andFuncidEqualTo(mi014.getFuncid())
			.andSubnameEqualTo(mi014.getSubname());
			int count = mi014Dao.countByExample(example);
			if(0 != count){
				log.error(ERROR.ADD_CHECK.getLogText(CommonUtil.getStringParams(mi014)));
				throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"此记录已存在，请确认后提交！");
			}
		}
		
		Mi014Example example = new Mi014Example();
		example.createCriteria().andFuncidEqualTo(mi014.getFuncid()).andSubnameEqualTo(oldSubname);
		mi014Dao.updateByExampleSelective(mi014, example);
	}
	
	/**
	 * 获取所有功能列表（包括子功能）
	 * @param roles
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONArray getMenuChildFuncJson(String pid){
		JSONArray ary = new JSONArray();
		Mi005Example m5e = new Mi005Example();
		m5e.createCriteria().andParentfuncidEqualTo(pid);
	    m5e.setOrderByClause("orderid");
		List<Mi005> list5 = mi005Dao.selectByExample(m5e); 
		for(int i = 0; i < list5.size(); i++){
			Mi005 mi005 = list5.get(i);
			JSONObject obj = new JSONObject();
			obj.put("funcid", mi005.getFuncid());
			obj.put("funcname", mi005.getFuncname());
			obj.put("pid", mi005.getParentfuncid());
			
			JSONArray tary = new JSONArray();
			
			Mi005Example m5e1 = new Mi005Example();
			m5e1.createCriteria().andParentfuncidEqualTo(mi005.getFuncid());
		    m5e1.setOrderByClause("orderid");
			List<Mi005> childlist5 = mi005Dao.selectByExample(m5e1); 
			for(int j = 0; j < childlist5.size(); j++){
				Mi005 childmi005 = childlist5.get(j);
				JSONObject childobj = new JSONObject();
				childobj.put("funcid", childmi005.getFuncid());
				childobj.put("funcname", childmi005.getFuncname());
				childobj.put("pid", childmi005.getParentfuncid());
				
				JSONArray childFuncAry = getChildFuncJson(childmi005.getFuncid());
				if(childFuncAry.size() > 0){
					childobj.put("children", childFuncAry);
					childobj.put("leafFlg", Constants.IS_NOT_LEAF_FLG);//用于页面checkbox框的显示的判断条件之一
				}else{
					childobj.put("leafFlg", Constants.IS_LEAF_FLG);//用于页面checkbox框的显示的判断条件之一
				}
				tary.add(childobj);
			}

			if(tary.size()>0){
				obj.put("children", tary); 
				obj.put("leafFlg", Constants.IS_NOT_LEAF_FLG);//用于页面checkbox框的显示的判断条件之一
			}else{
				obj.put("leafFlg", Constants.IS_LEAF_FLG);//用于页面checkbox框的显示的判断条件之一
			}

			ary.add(obj);
		}
		
		return ary;
	}
	
	/**
	 * 获取对应功能编码的子功能列表JSONArray
	 * @param roles
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private JSONArray getChildFuncJson(String pid){
		JSONArray ary = new JSONArray();
		Mi014Example m14e = new Mi014Example();
		m14e.createCriteria().andFuncidEqualTo(pid);
		m14e.setOrderByClause("subno");
		List<Mi014> list14 = mi014Dao.selectByExample(m14e); 
		for(int i = 0; i < list14.size(); i++){
			Mi014 mi014 = list14.get(i);
			JSONObject obj = new JSONObject();
			obj.put("funcid", mi014.getFuncid()+(i+1));
			obj.put("funcname", mi014.getSubdesc());
			obj.put("pid", mi014.getFuncid());
			obj.put("leafFlg", Constants.IS_LEAF_FLG);//用于页面checkbox框的显示的判断条件之一

			ary.add(obj);
		}
		
		return ary;
	}
	
	/**
	 * 查询子功能信息列表List
	 * @param roles
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Mi014> getChildFunc(String funcid){
		Mi014Example m14e = new Mi014Example();
		if(!CommonUtil.isEmpty(funcid)){
			m14e.createCriteria().andFuncidEqualTo(funcid);
		}
		m14e.setOrderByClause("funcid,subno");
		List<Mi014> list14 = mi014Dao.selectByExample(m14e); 
		return list14;
	}
	
	/**
	 * 获取机构权限JSONArray
	 * @param roles
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONArray getCenterPermissionJson(List<String> centers){
		Mi013Example m13e = new Mi013Example();
		if(centers!=null)
			m13e.createCriteria().andCenteridIn(centers);
		List<Mi013> list = mi013Dao.selectByExample(m13e);
		ObjectMapper mapper = new  ObjectMapper();
		return mapper.convertValue(list, JSONArray.class);
	}
	
	/**
	 * 获取机构权限List
	 * @param roles
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getCenterPermission(String centerid, String funcid){
		Mi013Example m13e = new Mi013Example();
		m13e.createCriteria().andCenteridEqualTo(centerid).andFuncidEqualTo(funcid);
		List<Mi013> list = mi013Dao.selectByExample(m13e);
		return list;
	}
	
	/**
	 * 更新机构权限
	 * @param roles
	 * @return
	 */
	public void updCenterPermission(Mi013 mi013){
		Mi013Example m13e = new Mi013Example();
		m13e.createCriteria().andCenteridEqualTo(mi013.getCenterid()).andFuncidEqualTo(mi013.getFuncid());
		mi013Dao.updateByExampleSelective(mi013, m13e);
	}
	
	/**
	 * 更新机构权限
	 * @param permissions
	 */
	@Transactional(noRollbackFor=TransRuntimeErrorException.class)	 
	public void saveCenterPermission(String[] permissions){
		Mi013Example m13e = new Mi013Example();
		mi013Dao.deleteByExample(m13e);
		
		/*
		 * 算出每条数据权限十进制值，并放入mapMi301Key中
		 */
		Map<CMi013Key, Integer> mapMi301Key = new HashMap<CMi013Key, Integer>();
		for(int i = 0; i < permissions.length; i++){
			String[] permission = permissions[i].split("_");
			String funcid = permission[0];
			String centerid = permission[1];
			String seqno = permission[2];
			
			if (seqno.equals("0")) {
				Mi013 mi013 = new Mi013();
				mi013.setCenterid(centerid);
				mi013.setFuncid(funcid);
				mi013.setPermission("00000000");
				this.getMi013Dao().insert(mi013);
				continue;
			}

			CMi013Key key = new CMi013Key();
			key.setCenterid(centerid);
			key.setFuncid(funcid);
			Integer tempSeqno = mapMi301Key.get(key);
			if (CommonUtil.isEmpty(tempSeqno)) {
				mapMi301Key.put(key, new Integer(seqno));
			} else {
				mapMi301Key.put(key, tempSeqno + new Integer(seqno));
			}
		}
		
		/*
		 * 保存数据
		 */
		String temp = "00000000";
		for (Iterator<CMi013Key> iterator = mapMi301Key.keySet().iterator(); iterator.hasNext();) {
			CMi013Key key = (CMi013Key) iterator.next();
			String permission = Integer.toBinaryString(mapMi301Key.get(key));
			if (permission.length()<8) {
				permission = temp.substring(permission.length()) + permission;
			}
			Mi013 mi013 = new Mi013();
			mi013.setCenterid(key.getCenterid());
			mi013.setFuncid(key.getFuncid());
			mi013.setPermission(permission);
			this.getMi013Dao().insert(mi013);
		}
	}
	
	/**
	 * 获取城市中心名称
	 * @param centerid
	 * @return
	 */
	public String getCenterName(String centerid) {
		Mi001 mi001 = mi001Dao.selectByPrimaryKey(centerid);
		return mi001.getCentername();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 以下是昆明
	 */
	@Autowired
	private CMi055DAO cmi055DAO;
	@Autowired
	private CMi056DAO cmi056DAO;
	@Autowired
	private CMi014DAO cmi014DAO;
	@Autowired
	private CMi005DAO cmi005DAO;
	@Autowired
	private CommonUtil commonUtil;
	
	public void webapi05501(CMi055 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		checkCmi055Form(log,form);
		int mi055Count = getMi055Count(form.getFuncid());
		if(0!=mi055Count){
			log.error(ERROR.PARAMS_NULL.getLogText("Mi055中功能编号已存在："+form.getFuncid()));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "Mi055中功能编号已存在："+form.getFuncid());
		}
		//添加
		Mi055 mi055 = new Mi055();
		mi055.setFuncid(form.getFuncid());
		mi055.setFuncname(form.getFuncname());
		mi055.setUselevel(form.getUselevel());
		mi055.setSubfunction(form.getSubfunction());
		mi055.setDatecreated(CommonUtil.getSystemDate());
		mi055.setDatemodified(CommonUtil.getSystemDate());
		mi055.setValidflag(Constants.IS_VALIDFLAG);
		cmi055DAO.insert(mi055);		
	}
	@Transactional
	public void webapi05502(CMi055 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		String formId = form.getFuncid();
		if (CommonUtil.isEmpty(formId)) {
			log.error(ERROR.PARAMS_NULL.getLogText("功能编码为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "功能编码为空");
		}
		String[] funcIds = formId.split(",");
		for(String funcid:funcIds){
			int mi055Count = getMi055Count(funcid);
			//1.该记录不存在
			if(0==mi055Count){
				log.error(ERROR.NULL_KEY.getLogText("功能配置Mi055该记录不存在,funcid:"+funcid));
				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.NULL_KEY.getLogText("功能配置Mi055该记录不存在,funcid:"+funcid));
			}
			//2.已经配置在菜单中不许删除
			Mi005Example mi005Example = new Mi005Example();
			mi005Example.createCriteria().andFuncidEqualTo(funcid).andValidflagEqualTo(Constants.IS_VALIDFLAG);
			int countMi005 = cmi005DAO.countByExample(mi005Example);
			if(0!=countMi005){
				log.error(ERROR.NULL_KEY.getLogText("已经配置在菜单中不许删除,funcid:"+funcid));
				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.NULL_KEY.getLogText("已经配置在菜单中不许删除,funcid:"+funcid));
			}
			//删除
			Mi055Example mi055Example = new Mi055Example();
			mi055Example.createCriteria().andFuncidEqualTo(funcid).andValidflagEqualTo(Constants.IS_VALIDFLAG);
			cmi055DAO.deleteByExample(mi055Example);
			//删除子功能
			Mi014Example mi014Example = new Mi014Example();
			mi014Example.createCriteria().andFuncidEqualTo(funcid);
			cmi014DAO.deleteByExample(mi014Example);
		}
	}
	@Transactional
	public void webapi05503(String oldFuncid, CMi055 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(oldFuncid)){
			log.error(ERROR.UPDATE_NO_DATA.getLogText("Mi055中功能主键："+ oldFuncid));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "Mi055中功能主键："+ oldFuncid);
		}
		checkCmi055Form(log, form);
		
		int mi055Count = getMi055Count(form.getFuncid());
		if(0!=mi055Count && !oldFuncid.equals(form.getFuncid())){
			log.error(ERROR.ADD_CHECK.getLogText("Mi055中功能编号已存在："+form.getFuncid()));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "Mi055中功能编号已存在："+form.getFuncid());
		}

		Mi055 mi055 = new Mi055();
		mi055.setFuncid(form.getFuncid());
		mi055.setFuncname(form.getFuncname());
		mi055.setUselevel(form.getUselevel());
		mi055.setSubfunction(form.getSubfunction());
		mi055.setDatemodified(CommonUtil.getSystemDate());
		Mi055Example mi055Example = new Mi055Example();
		mi055Example.createCriteria().andFuncidEqualTo(oldFuncid).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		cmi055DAO.updateByExampleSelective(mi055, mi055Example);
		
		//更新子表
		Mi014 mi014 = new Mi014();
		mi014.setFuncid(form.getFuncid());
		Mi014Example mi014Example = new Mi014Example();
		mi014Example.createCriteria().andFuncidEqualTo(oldFuncid);
		cmi014DAO.updateByExampleSelective(mi014, mi014Example);
		
		//更新功能-菜单
		Mi005 mi005 = new Mi005();
		mi005.setFuncid(form.getFuncid());
		mi005.setFunname(form.getFuncname());
		mi005.setDatemodified(CommonUtil.getSystemDate());
		Mi005Example mi005Example = new Mi005Example();
		mi005Example.createCriteria().andFuncidEqualTo(oldFuncid).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		cmi005DAO.updateByExampleSelective(mi005, mi005Example);
	}
	
	public WebApi05504_queryResult webapi05504(CMi055 form) throws Exception {
		WebApi05504_queryResult select055Page = cmi055DAO.select055Page(form);
		return select055Page;
	}
	/**
	 * 取所有功能
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Mi055> webapi05505(CMi055 form)throws Exception{
		Logger log = LoggerUtil.getLogger();
		String level=null;
		if(!CommonUtil.isEmpty(form.getCenterid())){
			Mi001Example mi001Example = new Mi001Example();
			mi001Example.createCriteria().andCenteridEqualTo(form.getCenterid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
			List<Mi001> select = mi001Dao.selectByExample(mi001Example);
			if(CommonUtil.isEmpty(select)){
				log.error(ERROR.ADD_CHECK.getLogText("该中心在001表不存在："+form.getCenterid()));
				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "该中心在001表不存在："+form.getCenterid());
			}
			level = select.get(0).getUselevel();
		}
		if(!CommonUtil.isEmpty(form.getUselevel())){
			level = form.getUselevel();
		}
		if(CommonUtil.isEmpty(level)){
			log.error(ERROR.PARAMS_NULL.getLogText("客户级别为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "客户级别为空");
		}
		Mi055Example example = new Mi055Example();
		example.createCriteria().andUselevelLessThanOrEqualTo(level).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		example.setOrderByClause("funcid asc");
		List<Mi055> selectByExample = cmi055DAO.selectByExample(example);
		return selectByExample;
	}
	
	//菜单增删改查
	@SuppressWarnings("unchecked")
	public void webapi05601(CMi056 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		checkCmi056Form(log, form);
		Mi056Example mi056Example = new Mi056Example();
		com.yondervision.mi.dto.Mi056Example.Criteria ca056 = mi056Example.createCriteria();
		ca056.andUrlnameEqualTo(form.getUrlname());
		ca056.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi056> select056 = cmi056DAO.selectByExample(mi056Example);
		if(!CommonUtil.isEmpty(select056)){
			log.error(ERROR.ADD_CHECK.getLogText("菜单名称已存在："+form.getUrlname()));
			throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(), "菜单名称已存在："+form.getUrlname());
		}
		//检查排序是否存在
		Mi056Example mi056Example2 = new Mi056Example();
		mi056Example2.createCriteria().andOrderidEqualTo(form.getOrderid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		int count = cmi056DAO.countByExample(mi056Example2);
		if(count!=0){
			log.error(ERROR.ADD_CHECK.getLogText("菜单排序已存在："+form.getOrderid()));
			throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(), "菜单排序已存在："+form.getOrderid());
		}
		
		//添加
		String id = commonUtil.genKey("MI056", 20).toString();// 采号生成，前补0，总长度20
		if (CommonUtil.isEmpty(id)) {
			log.error(ERROR.NULL_KEY.getLogText("菜单配置MI056"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.NULL_KEY.getLogText("菜单配置MI056"));
		}
		Mi056 mi056 = new Mi056();
		mi056.setCdid(id);
		mi056.setUrlname(form.getUrlname());
		mi056.setOrderid(form.getOrderid());
		mi056.setDatecreated(CommonUtil.getSystemDate());
		mi056.setDatemodified(CommonUtil.getSystemDate());
		mi056.setValidflag(Constants.IS_VALIDFLAG);
		cmi056DAO.insert(mi056);		
	}
	@Transactional
	public void webapi05602(CMi056 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		String formId = form.getCdid();
		if (CommonUtil.isEmpty(formId)) {
			log.error(ERROR.PARAMS_NULL.getLogText("主键为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "主键为空");
		}
		String[] ids = formId.split(",");
		for(String cdid:ids){
			log.info("菜单主键是~~~~~~~~~~~："+cdid);
			Mi056 selectMi056 = cmi056DAO.selectByPrimaryKey(cdid);
			log.info("根据主键查询到的菜单："+selectMi056);
			//1.该记录不存在
			if(selectMi056==null){
				log.error(ERROR.NULL_KEY.getLogText("菜单配置Mi056该记录不存在,cdid:"+cdid));
				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.NULL_KEY.getLogText("菜单配置Mi056该记录不存在,cdid:"+cdid));
			}
			//2.已经配置在菜单中不许删除
			Mi005Example mi005Example = new Mi005Example();
			com.yondervision.mi.dto.Mi005Example.Criteria ca005 = mi005Example.createCriteria();
			ca005.andCdidEqualTo(cdid);
			ca005.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			int countMi005 = cmi005DAO.countByExample(mi005Example);
			if(0!=countMi005){
				log.error(ERROR.NULL_KEY.getLogText("已经配置在菜单中不许删除,cdid:"+cdid));
				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.NULL_KEY.getLogText("已经配置在菜单中不许删除,cdid:"+cdid));
			}
			//删除
			int deleteResult = cmi056DAO.deleteByPrimaryKey(cdid);
			if(deleteResult!=1){
				log.error(ERROR.DEL_CHECK.getLogText("删除失败,cdid:"+cdid));
				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.NULL_KEY.getLogText("删除失败,cdid:"+cdid));
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public void webapi05603(CMi056 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		String formId = form.getCdid();
		if (CommonUtil.isEmpty(formId)) {
			log.error(ERROR.PARAMS_NULL.getLogText("主键为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "主键为空");
		}
		Mi056 mi056 = cmi056DAO.selectByPrimaryKey(form.getCdid());
		if(mi056==null){
			log.error(ERROR.PARAMS_NULL.getLogText("菜单表Mi056中无此记录"+form.getCdid()));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "菜单表Mi056中无此记录"+form.getCdid());
		}
		Mi056Example mi056Example = new Mi056Example();
		com.yondervision.mi.dto.Mi056Example.Criteria ca056 = mi056Example.createCriteria();
		ca056.andUrlnameEqualTo(form.getUrlname());
		ca056.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi056> list056 = cmi056DAO.selectByExample(mi056Example);
		if(!CommonUtil.isEmpty(list056)){
			if(!(list056.get(0).getCdid().equals(mi056.getCdid()))){
				
				log.error(ERROR.PARAMS_NULL.getLogText("Mi056中菜单名称已存在："+form.getCdid()));
				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "Mi056中菜单名称已存在："+form.getCdid());
			}
		}
		
		//支持手动更改顺序
		if(!mi056.getOrderid().equals(form.getOrderid())){//说明顺序修改了
			Mi056Example mi056Example2 = new Mi056Example();
			mi056Example2.createCriteria().andOrderidEqualTo(form.getOrderid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
			int count = cmi056DAO.countByExample(mi056Example2);
			if(count!=0){
				//大于或等于
				Mi056Example mi056Example3 = new Mi056Example();
				mi056Example3.createCriteria().andOrderidGreaterThanOrEqualTo(form.getOrderid())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				List<Mi056> select1 = cmi056DAO.selectByExample(mi056Example3);
				if(select1!=null && !select1.isEmpty()){
					for(Mi056 mi0561:select1){
						mi0561.setOrderid(mi0561.getOrderid()+1);
						cmi056DAO.updateByPrimaryKeySelective(mi0561);
					}
				}
				//小于
				Mi056Example mi056Example4 = new Mi056Example();
				mi056Example4.createCriteria().andOrderidLessThan(form.getOrderid())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				List<Mi056> select2 = cmi056DAO.selectByExample(mi056Example4);
				if(select2!=null && !select2.isEmpty()){
					for(Mi056 mi0561:select1){
						mi0561.setOrderid(mi0561.getOrderid()-1);
						cmi056DAO.updateByPrimaryKeySelective(mi0561);
					}
				}
			}
		}
		
		
		
		//更新自己
		mi056.setDatemodified(CommonUtil.getSystemDate());
		mi056.setUrlname(form.getUrlname());
		mi056.setOrderid(form.getOrderid());
		cmi056DAO.updateByPrimaryKey(mi056);
		
		//更新功能-菜单表
		Mi005 mi005 = new Mi005();
		mi005.setOrderid(form.getOrderid());
		mi005.setFuncname(form.getUrlname());
		mi005.setDatemodified(CommonUtil.getSystemDate());
		Mi005Example mi005Example = new Mi005Example();
		mi005Example.createCriteria().andCdidEqualTo(form.getCdid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		cmi005DAO.updateByExampleSelective(mi005, mi005Example);
		
	}
	
	public WebApi05604_queryResult webapi05604(CMi056 form) throws Exception {
		WebApi05604_queryResult select056Page = cmi056DAO.select056Page(form);
		return select056Page;
	}
	/**
	 * 菜单保存排序
	 * @param json数组:arr
	 * @return
	 * @throws Exception
	 */
	public void webapi05605(JSONArray arr)throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(arr)){
			log.error(ERROR.PARAMS_NULL.getLogText("要保存顺序的数组为空:"+arr));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"要保存顺序的数组为空:"+arr);
		}
		for(int i=0;i<arr.size();i++){
			JSONObject obj = arr.getJSONObject(i);
			String cdid = obj.getString("cdid");
			int orderid = obj.getInt("orderid");
			Mi056 mi056 = new Mi056();
			mi056.setCdid(cdid);
			mi056.setOrderid(orderid);
			int updateresult = cmi056DAO.updateByPrimaryKeySelective(mi056);
			if(updateresult!=1){
				log.error(ERROR.UPDATE_NO_DATA.getLogText("保存顺序失败:"+arr));
				throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(),"保存顺序失败:"+arr);
			}
		}
	}
	
	/**
	 * 取所有菜单
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Mi056> webapi05606()throws Exception{
		Mi056Example example = new Mi056Example();
		example.setOrderByClause("orderid");
		example.createCriteria().andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi056> selectByExample = cmi056DAO.selectByExample(example);
		return selectByExample;
	}
	
	//子功能增删改查
	public void webapi01401(CMi014 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		checkCmi014Form(log, form);
		int mi014Count = getMi014Count(form.getFuncid(), form.getSubname());
		if(0!=mi014Count){
			log.error(ERROR.ADD_CHECK.getLogText("子功能配置Mi014："+form.getFuncid()));
			throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(), "子功能配置Mi014："+form.getFuncid());
		}
		
		Mi014 mi014 = new Mi014();
		mi014.setFuncid(form.getFuncid());
		mi014.setSubdesc(form.getSubdesc());
		mi014.setSubname(form.getSubname());
		mi014.setSubno(form.getSubno());
		cmi014DAO.insert(mi014);		
	}
	
	@Transactional
	public void webapi01402(CMi014 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		String funcid = form.getFuncid();
		String subnames = form.getSubname();
		if (CommonUtil.isEmpty(funcid)||CommonUtil.isEmpty(subnames)) {
			log.error(ERROR.PARAMS_NULL.getLogText("功能编码或者子功能描述"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "功能编码或者子功能描述为空");
		}
		String[] split = form.getSubname().split(",");
		for(String subname:split){
			//1.该记录不存在
			int mi014Count = getMi014Count(funcid, subname);
			if(mi014Count<1){
				log.error(ERROR.NULL_KEY.getLogText("子功能不存在"+funcid));
				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.NULL_KEY.getLogText("子功能不存在"+funcid));
			}
			//删除
			Mi014Example mi014Example = new Mi014Example();
			mi014Example.createCriteria().andFuncidEqualTo(funcid).andSubnameEqualTo(subname);
			cmi014DAO.deleteByExample(mi014Example);
		}
		
	}
	@Transactional
	public void webapi01403(String oldFuncid, String oldSubname, CMi014 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(oldFuncid)||CommonUtil.isEmpty(oldSubname)){
			log.error(ERROR.PARAMS_NULL.getLogText("父功能编号oldFuncid或子功能编号oldSubname为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "父功能编号oldFuncid或子功能编号oldSubname为空");
		}
		checkCmi014Form(log, form);
		log.error("开始测试---开始测试----开始测试----开始测试---开始测试");
		int mi014Count = getMi014Count(form.getFuncid(),form.getSubname());
		boolean flag = (!oldFuncid.equals(form.getFuncid()))||(!oldSubname.equals(form.getSubname()));
		log.error("测试flag:"+flag+"  ++++mi014Count:"+mi014Count);
		if((0!=mi014Count)&& flag){
			log.error(ERROR.PARAMS_NULL.getLogText("改子功能已存在"+form.getFuncid()));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "改子功能已存在"+form.getFuncid());
		}
		
		//更新
		Mi014 mi014 = new Mi014();
		mi014.setFuncid(form.getFuncid());
		mi014.setSubname(form.getSubname());
		mi014.setSubno(form.getSubno());
		mi014.setSubdesc(form.getSubdesc());
		Mi014Example mi014Example = new Mi014Example();
		mi014Example.createCriteria().andFuncidEqualTo(oldFuncid).andSubnameEqualTo(oldSubname);
		cmi014DAO.updateByExample(mi014, mi014Example);
		
	}
	
	public WebApi01404_queryResult webapi01404(CMi014 form) throws Exception {
		WebApi01404_queryResult select014Page = cmi014DAO.select014Page(form);
		return select014Page;
	}
	
	/**
	 * 子功能保存排序
	 * @param json数组:arr
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void webapi01405(JSONArray arr)throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(arr)){
			log.error(ERROR.PARAMS_NULL.getLogText("要保存顺序的数组为空:"+arr));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"要保存顺序的数组为空:"+arr);
		}
		for(int i=0;i<arr.size();i++){
			JSONObject obj = arr.getJSONObject(i);
			String funcid = obj.getString("funcid");
			String subname = obj.getString("subname");
			int subno = obj.getInt("subno");
			Mi014 mi014 = new Mi014();
			mi014.setSubno(subno);
			Mi014Example mi014Example = new Mi014Example();
			mi014Example.createCriteria().andFuncidEqualTo(funcid).andSubnameEqualTo(subname);
			int updateresult = cmi014DAO.updateByExampleSelective(mi014, mi014Example);
			if(updateresult!=1){
				log.error(ERROR.UPDATE_NO_DATA.getLogText("保存顺序失败:"+arr));
				throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(),"保存顺序失败:"+arr);
			}
		}
	}
	
	//功能-菜单增删改查
	public void webapi00501(CMi005 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		checkCmi005Form(log, form);
		int count = getMi005Count(form.getCenterid(),form.getFuncid(),form.getCdid());
		if(0!=count){
			log.error(ERROR.ADD_CHECK.getLogText("功能(菜单)中心："+form.getCenterid()));
			throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(),"功能(菜单)中心："+form.getCenterid());
		}
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		form.setValidflag(Constants.IS_VALIDFLAG);
		cmi005DAO.insert(form);		
	}
	
	@Transactional
	public void webapi00502(CMi005 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		checkCmi005Form(log,form);
		String centerid = form.getCenterid();
		String formFuncid = form.getFuncid();
		String formCdid = form.getCdid();
		
		String[] funcids = formFuncid.split(",");
		String[] cdids = formCdid.split(",");	
		
		for(int i=0;i<funcids.length;i++){
			int mi005Count = getMi005Count(centerid, funcids[i], cdids[i]);
			if(0==mi005Count){
				log.error(ERROR.DEL_CHECK.getLogText("mi005该条记录不存在"));
				throw new TransRuntimeErrorException(WEB_ALERT.DEL_CHECK.getValue(), "mi005该条记录不存在");
			}
			Mi005Example mi005Example2 = new Mi005Example();
			mi005Example2.createCriteria().andCenteridEqualTo(centerid).andParentfuncidEqualTo(funcids[i])
			.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			int count = cmi005DAO.countByExample(mi005Example2);
			if(count!=0){
				log.error(ERROR.DEL_CHECK.getLogText("mi005有子菜单不许删除"));
				throw new TransRuntimeErrorException(WEB_ALERT.DEL_CHECK.getValue(), "mi005有子菜单不许删除");
			}
			//删除
			Mi005Example mi005Example = new Mi005Example();
			mi005Example.createCriteria().andCenteridEqualTo(centerid).andFuncidEqualTo(funcids[i])
			.andCdidEqualTo(cdids[i]).andValidflagEqualTo(Constants.IS_VALIDFLAG);
			cmi005DAO.deleteByExample(mi005Example);
		}
		
	}
	public void webapi00503(String oldCenterid, String oldFuncid, String oldCdid, CMi005 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(oldCenterid)||CommonUtil.isEmpty(oldCdid)||CommonUtil.isEmpty(oldFuncid)){
			log.error(ERROR.ADD_CHECK.getLogText("三个参数有空值：oldCenterid，oldFuncid，oldCdid"));
			throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(), "三个参数有空值：oldCenterid，oldFuncid，oldCdid");
		}
		checkCmi005Form(log, form);
		int mi005Count = getMi005Count(form.getCenterid(), form.getFuncid(), form.getCdid());
		boolean flag = !oldCenterid.equals(form.getCenterid())||!oldFuncid.equals(form.getFuncid())||!oldCdid.equals(form.getCdid());
		if((0!=mi005Count) && flag){
			log.error(ERROR.ADD_CHECK.getLogText("mi005该条记录已存在"));
			throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(), "mi005该条记录已存在");
		}
		//更新
		Mi005 mi005 = new Mi005();
		mi005.setCdid(form.getCdid());
		mi005.setCenterid(form.getCenterid());
		mi005.setFuncid(form.getFuncid());
		mi005.setFuncname(form.getFuncname());
		mi005.setOrderid(form.getOrderid());
		mi005.setUrl(form.getUrl());
		mi005.setParentfuncid(form.getParentfuncid());
		mi005.setFunname(form.getFunname());
		mi005.setDatemodified(CommonUtil.getSystemDate());
		mi005.setImage(form.getImage());
		Mi005Example mi005Example = new Mi005Example();
		mi005Example.createCriteria().andCenteridEqualTo(oldCenterid).andCdidEqualTo(oldCdid)
		.andFuncidEqualTo(oldFuncid).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		cmi005DAO.updateByExampleSelective(mi005, mi005Example);
	}
	
	public WebApi00504_queryResult webapi00504(CMi005 form) throws Exception {
		WebApi00504_queryResult select005Page = cmi005DAO.select005Page(form);
		return select005Page;
	}
	
	//保存排序
	@Transactional
	public void webapi00506(JSONArray arr)throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(arr)){
			log.error(ERROR.PARAMS_NULL.getLogText("要保存顺序的数组为空:"+arr));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"要保存顺序的数组为空:"+arr);
		}
		for(int i=0;i<arr.size();i++){
			JSONObject obj = arr.getJSONObject(i);
			String cdid = obj.getString("cdid");
			int freeuse4 = obj.getInt("freeuse4");
			String centerid = obj.getString("centerid");
			String funcid = obj.getString("funcid");
			Mi005 mi005 = new Mi005();
			mi005.setFreeuse4(freeuse4);
			Mi005Example mi005Example = new Mi005Example();
			mi005Example.createCriteria().andCenteridEqualTo(centerid).andFuncidEqualTo(funcid).andCdidEqualTo(cdid)
			.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			
			int updateresult = cmi005DAO.updateByExampleSelective(mi005, mi005Example);
			if(updateresult!=1){
				log.error(ERROR.UPDATE_NO_DATA.getLogText("保存顺序失败:"+arr));
				throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(),"保存顺序失败:"+arr);
			}
		}
		
		
	}
	private int getMi005Count(String centerid,String funcid, String cdid){
		Mi005Example mi005Example = new Mi005Example();
		mi005Example.createCriteria().andCenteridEqualTo(centerid).andFuncidEqualTo(funcid)
		.andCdidEqualTo(cdid).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		return cmi005DAO.countByExample(mi005Example);
	}
	private void checkCmi005Form(Logger log, CMi005 form)throws Exception{
		if(CommonUtil.isEmpty(form)){
			log.error(ERROR.PARAMS_NULL.getLogText("参数为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "参数为空");
		}
		String funcid = form.getFuncid();
		String centerid = form.getCenterid();
		String cdid = form.getCdid();
		if (CommonUtil.isEmpty(funcid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("功能编码为空："+funcid));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "功能编码为空："+funcid);
		}
		if (CommonUtil.isEmpty(centerid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心为空:"+centerid));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "中心为空:"+centerid);
		}
		if (CommonUtil.isEmpty(cdid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("菜单编码为空:"+cdid));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "菜单编码为空:"+cdid);
		}
	}
	
	private void checkCmi055Form(Logger log, CMi055 form)throws Exception{
		if(CommonUtil.isEmpty(form)){
			log.error(ERROR.PARAMS_NULL.getLogText("参数为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "参数为空");
		}
		String funcid = form.getFuncid();
		String funcname = form.getFuncname();
		String level = form.getUselevel();
		if (CommonUtil.isEmpty(funcid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("功能编码为空："+funcid));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "功能编码为空："+funcid);
		}
		if (CommonUtil.isEmpty(funcname)) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心为空:"+funcname));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "中心为空:"+funcname);
		}
		if (CommonUtil.isEmpty(level)) {
			log.error(ERROR.PARAMS_NULL.getLogText("菜单编码为空:"+level));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "菜单编码为空:"+level);
		}
	}
	private void checkCmi056Form(Logger log, CMi056 form)throws Exception{
		if(CommonUtil.isEmpty(form)){
			log.error(ERROR.PARAMS_NULL.getLogText("参数为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "参数为空");
		}
		String urlName = form.getUrlname();
		Integer orderid = form.getOrderid();
		if (CommonUtil.isEmpty(urlName)) {
			log.error(ERROR.PARAMS_NULL.getLogText("菜单名称为空："+urlName));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "菜单名称为空："+urlName);
		}
		if (CommonUtil.isEmpty(orderid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("顺序号为空:"+orderid));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "顺序号为空:"+orderid);
		}
	}
	private void checkCmi014Form(Logger log, CMi014 form)throws Exception{
		if(CommonUtil.isEmpty(form)){
			log.error(ERROR.PARAMS_NULL.getLogText("参数为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "参数为空");
		}
		String funcid = form.getFuncid();
		String subname = form.getSubname();
		Integer subno = form.getSubno();
		String subdesc = form.getSubdesc();
		if(CommonUtil.isEmpty(funcid)){
			log.error(ERROR.PARAMS_NULL.getLogText("功能编码"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "功能编码为空");
		}
		if(CommonUtil.isEmpty(subname)){
			log.error(ERROR.PARAMS_NULL.getLogText("子功能名称"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "子功能名称为空");
		}
		if(CommonUtil.isEmpty(subno)){
			log.error(ERROR.PARAMS_NULL.getLogText("子功能序号"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "子功能序号为空");
		}
		if(CommonUtil.isEmpty(subdesc)){
			log.error(ERROR.PARAMS_NULL.getLogText("子功能描述"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "子功能描述为空");
		}
	}
	private int getMi055Count(String funcid){
		Mi055Example mi055Example = new Mi055Example();
		mi055Example.createCriteria().andFuncidEqualTo(funcid).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		return cmi055DAO.countByExample(mi055Example);
	}
	private int getMi014Count(String funcid, String subname){

		Mi014Example mi014Example = new Mi014Example();
		mi014Example.createCriteria().andFuncidEqualTo(funcid).andSubnameEqualTo(subname);
		return cmi014DAO.countByExample(mi014Example);
	}
}
