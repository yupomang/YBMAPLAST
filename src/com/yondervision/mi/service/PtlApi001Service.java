package com.yondervision.mi.service;
 
import java.util.List;

import com.yondervision.mi.dto.CMi002;
import com.yondervision.mi.dto.Mi001;
import com.yondervision.mi.dto.Mi003;
import com.yondervision.mi.dto.Mi005;
import com.yondervision.mi.dto.Mi014;
import com.yondervision.mi.form.PwdModForm;
 

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
 
public interface PtlApi001Service {
	public   JSONObject getCenterListJson(String centerid) ;
	public   List<CMi002>  getOperListByCenterJson(String centerid) ;
	public void addOper(CMi002 mi002);
	public void updateOper(CMi002 mi002);
	public void delOper(List<String> list);	
	public JSONArray getRoleListJson(String centerid);
	public JSONArray getRoleListJsonByOper(String centerid);
	
	public List<Mi003> getRoleAllList(String centerid);
	public void delRole(List<String> list);
	public void addRole(Mi003 mi003);
	public void updateRole(Mi003 mi003);
	public JSONArray getCenterListJsonArray(String centerid);
	
	public List<Mi001> getCenterAllList(String centerid);
	public void delCenter(List<String> list);
	public void addCenter(Mi001 mi001);
	public void updateCenter(Mi001 mi001);
	public void activationCenter(List<String> list);
	
	public JSONArray getMenuJson(String centerid, String funcid);
	
	public void savePermission(String[] permissions, String centerid);
	
	public List<Mi005> getMenuListByPid(String pid);

	public JSONArray getPermissionMenuJson(List<String> roles, String centerid);
	
	public void delFunc(List<String> list);
	public void addFunc(Mi005 mi005);
	public void updateFunc(String oldfuncid,Mi005 mi005);
	
	public void updatePwd(PwdModForm form);
	
	/**
	 * 获取子功能列表
	 */
	public List<Mi014> getChildFuncListByPid(String pid);
	
	/**
	 * 增加子功能
	 * @param mi005
	 */
	public void addChildFunc(Mi014 mi014);
	
	/**
	 * 删除子功能
	 * @param list
	 */
	public void delChildFunc(List<Mi014> list);
	
	/**
	 * 修改子功能
	 */
	public void updateChildFunc(String oldSubname, Mi014 mi014);
	
	/**
	 * 获取所有功能列表（包括子功能）
	 * @param roles
	 * @return
	 */
	public JSONArray getMenuChildFuncJson(String pid ,String centerid);
	
	/**
	 * 获取机构权限
	 * @param roles
	 * @return
	 */
	public JSONArray getCenterPermissionJson(List<String> centers);
	
	/**
	 * 更新机构权限
	 * @param permissions
	 */
	public void saveCenterPermission(String[] permissions ,String centerid);
	
	/**
	 * 获取城市中心名称
	 * @param centerid
	 * @return
	 */
	public String getCenterName(String centerid);
}
