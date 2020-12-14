package com.yondervision.mi.common;
/** 
* @ClassName: UserContext 
* @Description: 用户对象
* @author 韩占远
* @date 2013-10-04  
* 
*/ 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yondervision.mi.common.filter.PermissionEncodingFilter;
import com.yondervision.mi.dao.Mi001DAO; 
import com.yondervision.mi.dao.Mi004DAO;
import com.yondervision.mi.dao.Mi005DAO;
import com.yondervision.mi.dao.Mi006DAO;
import com.yondervision.mi.dto.Mi001Example;
import com.yondervision.mi.dto.Mi002;
import com.yondervision.mi.dto.Mi001; 
import com.yondervision.mi.dto.Mi004;
import com.yondervision.mi.dto.Mi004Example;
import com.yondervision.mi.dto.Mi005;
import com.yondervision.mi.dto.Mi005Example;
import com.yondervision.mi.dto.Mi006;
import com.yondervision.mi.dto.Mi006Example;
import com.yondervision.mi.util.SpringContextUtil; 
public class UserContext implements  java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4319837130965726874L;
	
	public final static String USERCONTEXT="_USERCONTEXT_";
	private String loginid;	 
	private String opername;
    private String centerid; 
	private String phone;	 
	private String stat;
	public UserContext(Mi002 mi002){
		this.loginid=mi002.getLoginid().trim();
		this.centerid=mi002.getCenterid();
		this.opername=mi002.getOpername();
		this.phone=mi002.getPhone();
		this.stat=mi002.getStat(); 
	}
	
	// 存放当前登录user的被允许登录的菜单项
	private Vector<String> urlvector = new Vector<String>();
	// 存放首页快捷菜单的url
	private List<Map<String, String>> shortcutsUrlList = new ArrayList<Map<String, String>>();
	
	public static UserContext getInstance(){
		return (UserContext)PermissionEncodingFilter.threadLocal.get();
	}
	public String getLoginid() {
		return loginid;
	} 
	public String getOpername() {
		return opername;
	} 
	public String getCenterid() {
		return centerid;
	}
	public String getPhone() {
		return phone;
	} 
	public String getStat() {		
		return stat;
	} 
	@SuppressWarnings("unchecked")
	public String getCenterName() {
	    Mi001DAO   mi001Dao=	(Mi001DAO)SpringContextUtil.getBean("mi001Dao");	 
		Mi001Example m1e=new Mi001Example();
		m1e.createCriteria().andCenteridEqualTo(this.centerid);
		List<Mi001> list= mi001Dao.selectByExample(m1e);
		String centerName=this.centerid;
		if(list.size()>0)
			centerName=list.get(0).getCentername();
		return centerName;
	}
	
	@SuppressWarnings("unchecked")
	public String getFreeUse1() {
	    Mi001DAO   mi001Dao=	(Mi001DAO)SpringContextUtil.getBean("mi001Dao");	 
		Mi001Example m1e=new Mi001Example();
		m1e.createCriteria().andCenteridEqualTo(this.centerid);
		List<Mi001> list= mi001Dao.selectByExample(m1e);
		String centerName=this.centerid;
		if(list.size()>0)
			centerName=list.get(0).getFreeuse1();
		return centerName;
	}

	@SuppressWarnings("unchecked")
	public JSONArray getFuncJson(){
		 Mi004DAO mi004Dao = (Mi004DAO)SpringContextUtil.getBean("mi004Dao");
		 Mi004Example m4e = new Mi004Example();
		 m4e.createCriteria().andLoginidEqualTo(this.loginid.trim());
		 //longinid,roleid
		 List<Mi004> list = mi004Dao.selectByExample(m4e);
		 JSONArray ary = new JSONArray();
		 if(list.size() > 0){
			 Mi006DAO mi006Dao = (Mi006DAO)SpringContextUtil.getBean("mi006Dao");
			 Mi006Example m6e = new Mi006Example();
			 List<String> rolelist = new ArrayList<String>();
			 for(int i = 0; i < list.size(); i++){				 
				 rolelist.add(list.get(i).getRoleid());
			 }

			 m6e.createCriteria().andRoleidIn(rolelist).andCenteridEqualTo(this.centerid.trim());
			 List<Mi006> list6 = mi006Dao.selectByExample(m6e);
			 Vector<String> vec = new Vector<String>();
			 Map<String, String> funcPermMap = new HashMap<String, String>();
			 for(int i=0;i<list6.size();i++){
				 Mi006 mi006 = list6.get(i);
				 vec.add(mi006.getFuncid());
				 funcPermMap.put(mi006.getFuncid(), mi006.getPermission());
			 }
			 Mi005DAO   mi005Dao=	(Mi005DAO)SpringContextUtil.getBean("mi005Dao");
			 Mi005Example m5e=new Mi005Example();
			 m5e.createCriteria().andParentfuncidEqualTo("00000000").andCenteridEqualTo(this.centerid);//综合服务平台新加城市代码
			 m5e.setOrderByClause("freeuse4");
			 List<Mi005> list5= mi005Dao.selectByExample(m5e);		 
			 for(int i=0;i<list5.size();i++){

				 JSONObject obj=new JSONObject();
				 obj.put("id", list5.get(i).getFuncid());
				 obj.put("name", list5.get(i).getFuncname());
				 obj.put("level", "1");
				 obj.put("url", "");
				 obj.put("image", list5.get(i).getImage()==null?"":list5.get(i).getImage());
				 ary.add(obj);
					
				m5e=new Mi005Example();
				//综合服务平台新加城市代码
				m5e.createCriteria().andParentfuncidEqualTo(list5.get(i).getFuncid()).andCenteridEqualTo(this.centerid);
				m5e.setOrderByClause("freeuse4");
				List<Mi005> list55= mi005Dao.selectByExample(m5e);
				int childCount = 0;
				for(int j=0;j<list55.size();j++){
					Mi005 mi005 = list55.get(j);
					if(vec.contains(mi005.getFuncid())){
						childCount = childCount + 1;
						obj=new JSONObject();
						obj.put("id", mi005.getFuncid());
						obj.put("name", mi005.getFuncname());
						obj.put("level", "2");
						obj.put("image", mi005.getImage()==null?"":mi005.getImage());
						String funcid = mi005.getFuncid();
						obj.put("url", funcid + funcPermMap.get(funcid)+".menu");
						// 首页快捷菜单项
//						String urlTmp = mi005.getUrl();
//						if("page20101.html".equals(urlTmp)
//								|| "page00804.html".equals(urlTmp)
//								|| "page10104.html".equals(urlTmp) || "page30201.html".equals(urlTmp) || "page30202.html".equals(urlTmp)){
//							Map<String, String> map = new HashMap<String, String>();
//							map.put(urlTmp, funcid + funcPermMap.get(funcid)+".menu");
//							shortcutsUrlList.add(map);
//						}
						urlvector.add(list55.get(j).getUrl());
						ary.add(obj);
					}
				}

				if(childCount < 1){
					 obj.put("id", list5.get(i).getFuncid());
					 obj.put("name", list5.get(i).getFuncname());
					 obj.put("level", "1");
					 obj.put("url", "");
					 obj.put("image", list5.get(i).getImage()==null?"":list5.get(i).getImage());
					 ary.remove(obj);
				}
			 } 
		 }
		return ary;
	}
	
	/**
	 * 校验当前访问的URL是否在当前用户被允许访问的菜单项中
	 * @return
	 */
	public boolean checkFuncUrlExist(String url) {
		if (urlvector.contains(url)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取首页快捷菜单的(菜单编码+子功能权限).menu的url
	 * @param url 快捷键的真实url路径
	 * @return
	 */
	public String getShortcutsUrl(String url) {
		String shortcutsUrl = "#";
		for(int i = 0; i < shortcutsUrlList.size(); i++){
			Map<String, String> shortcutsUrlMap = shortcutsUrlList.get(i);
			if(shortcutsUrlMap.containsKey(url)){
				shortcutsUrl = shortcutsUrlList.get(i).get(url);
				break;
			}
		}
		return shortcutsUrl;
	}
}
