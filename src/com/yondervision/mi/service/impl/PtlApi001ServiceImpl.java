package com.yondervision.mi.service.impl;
/** 
* @ClassName: PtlApi001ServiceImpl 
* @Description: 登录程序和操作角色中心维护用的服务
* @author 韩占远
* @date 2013-10-04  
* 
*/ 
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.Mi001DAO;
import com.yondervision.mi.dao.Mi002DAO;
import com.yondervision.mi.dao.Mi003DAO;
import com.yondervision.mi.dao.Mi004DAO;
import com.yondervision.mi.dao.Mi005DAO;
import com.yondervision.mi.dao.Mi006DAO;
import com.yondervision.mi.dao.Mi013DAO;
import com.yondervision.mi.dao.Mi014DAO;
import com.yondervision.mi.dto.CMi002;
import com.yondervision.mi.dto.CMi006Key;
import com.yondervision.mi.dto.CMi013Key;
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
import com.yondervision.mi.dto.Mi013;
import com.yondervision.mi.dto.Mi013Example;
import com.yondervision.mi.dto.Mi014;
import com.yondervision.mi.dto.Mi014Example;
import com.yondervision.mi.dto.Mi006Example.Criteria;
import com.yondervision.mi.form.PwdModForm;
import com.yondervision.mi.service.PtlApi001Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;
import com.yondervision.mi.util.SpringContextUtil;

public class PtlApi001ServiceImpl implements PtlApi001Service {
    
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
	    if (!Constants.YD_ADMIN.equals(centerid)) {
	    	 example.createCriteria().andCenteridEqualTo(centerid);
	    }
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
	    SimpleDateFormat formatterD = new SimpleDateFormat(Constants.DATE_FORMAT_XH_YYMMDD);
	    SimpleDateFormat formatterT = new SimpleDateFormat(Constants.TIME_FORMAT);
	    mi002.setCreatedate(formatterD.format(System.currentTimeMillis()));
	    mi002.setCreatetime(formatterT.format(System.currentTimeMillis()));
	    if(list.size()==0){
		    mi002Dao.insert(mi002);
		    Mi004Example example4=new Mi004Example();
		    example4.createCriteria().andLoginidEqualTo(mi002.getLoginid());
		    mi004Dao.deleteByExample(example4);
		    String[] roleids = mi002.getRoles().split(",");
		    for(int i=0;i<roleids.length;i++){
		    	Mi004 mi004=new Mi004();
		    	mi004.setLoginid(mi002.getLoginid());
		    	mi004.setRoleid(roleids[i]);
		    	mi004Dao.insert(mi004);		    	
		    }
	    }else
	    	throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(),"操作代码："+mi002.getLoginid());
	}
	public void updateOper(CMi002 mi002){
		Mi002Example example=new Mi002Example();
		example.createCriteria().andLoginidEqualTo(mi002.getLoginid());	
		SimpleDateFormat formatterD = new SimpleDateFormat(Constants.DATE_FORMAT_XH_YYMMDD);
	    SimpleDateFormat formatterT = new SimpleDateFormat(Constants.TIME_FORMAT);
	    mi002.setModidate(formatterD.format(System.currentTimeMillis()));
	    mi002.setModitime(formatterT.format(System.currentTimeMillis()));
		mi002Dao.updateByExample(mi002, example);	
		
		Mi004Example example4=new Mi004Example();
	    example4.createCriteria().andLoginidEqualTo(mi002.getLoginid());
	    mi004Dao.deleteByExample(example4);
	    String[] roleids = mi002.getRoles().split(",");
	    for(int i=0;i<roleids.length;i++){
	    	Mi004 mi004=new Mi004();
	    	mi004.setLoginid(mi002.getLoginid());
	    	mi004.setRoleid(roleids[i]);
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
		System.out.println("MI003 :"+list.size());
		if(CommonUtil.isEmpty(list)){
			return null;
		}else{	
			return JSONArray.fromObject(list);		
		}
		//List<Mi003> list = mi003Dao.selectByExample(example); 
		//return mapper.convertValue(list, JSONArray.class);
		
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
	
	@Transactional(noRollbackFor=NoRollRuntimeErrorException.class)	  
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
	
	@Transactional(noRollbackFor=NoRollRuntimeErrorException.class)	  
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
		m5e.createCriteria().andParentfuncidEqualTo(funcid).andCenteridEqualTo(centerid);
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
		m5e1.createCriteria().andParentfuncidEqualTo(funcid).andCenteridEqualTo(centerid);
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
	
	@Transactional(noRollbackFor=NoRollRuntimeErrorException.class)	    
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
	public List<Mi005> getMenuListByPid(String pid){
		Mi005Example m5e=new Mi005Example();
		m5e.createCriteria().andParentfuncidEqualTo(pid);
	    m5e.setOrderByClause("orderid");
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
	@Transactional(noRollbackFor=NoRollRuntimeErrorException.class)	   
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
	public JSONArray getMenuChildFuncJson(String pid ,String centerid){
		JSONArray ary = new JSONArray();
		Mi005Example m5e = new Mi005Example();
		m5e.createCriteria().andParentfuncidEqualTo(pid).andCenteridEqualTo(centerid);
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
			m5e1.createCriteria().andParentfuncidEqualTo(mi005.getFuncid()).andCenteridEqualTo(centerid);
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
	@Transactional(noRollbackFor=NoRollRuntimeErrorException.class)	 
	public void saveCenterPermission(String[] permissions ,String centerId){
		Mi013Example m13e = new Mi013Example();
		m13e.createCriteria().andCenteridEqualTo(centerId);
		mi013Dao.deleteByExample(m13e);
		
		/*
		 * 算出每条数据权限十进制值，并放入mapMi301Key中
		 */
		if(!CommonUtil.isEmpty(permissions)){
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
}
