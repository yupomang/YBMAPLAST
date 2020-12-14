package com.yondervision.mi.service;
 
import java.util.List;

import com.yondervision.mi.dto.CMi002;
import com.yondervision.mi.dto.CMi005;
import com.yondervision.mi.dto.CMi014;
import com.yondervision.mi.dto.CMi055;
import com.yondervision.mi.dto.CMi056;
import com.yondervision.mi.dto.Mi001;
import com.yondervision.mi.dto.Mi003;
import com.yondervision.mi.dto.Mi005;
import com.yondervision.mi.dto.Mi014;
import com.yondervision.mi.dto.Mi055;
import com.yondervision.mi.dto.Mi056;
import com.yondervision.mi.form.PwdModForm;
import com.yondervision.mi.result.WebApi00504_queryResult;
import com.yondervision.mi.result.WebApi01404_queryResult;
import com.yondervision.mi.result.WebApi05504_queryResult;
import com.yondervision.mi.result.WebApi05604_queryResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
 
public interface WebApi055Service {
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
	
	public List<Mi005> getMenuListByPid(String pid, String centerid);

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
	public JSONArray getMenuChildFuncJson(String pid);
	
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
	public void saveCenterPermission(String[] permissions);
	
	/**
	 * 获取城市中心名称
	 * @param centerid
	 * @return
	 */
	public String getCenterName(String centerid);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 以下是昆明
	 */
	/**
	 * 功能配置新增
	 * @param form
	 * @throws Exception
	 */
	public void webapi05501(CMi055 form) throws Exception;
	/**
	 * 功能配置删除
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public void webapi05502(CMi055 form) throws Exception;
	
	/**
	 * 功能配置修改
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public void webapi05503(String oldFuncid, CMi055 form) throws Exception;
	
	/**
	 * 功能配置查询
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public WebApi05504_queryResult webapi05504(CMi055 form) throws Exception;
	/**
	 * 取所有的功能
	 * @return
	 * @throws Exception
	 */
	public List<Mi055> webapi05505(CMi055 form) throws Exception;
	
	/**
	 * 菜单配置新增
	 * @param form
	 * @throws Exception
	 */
	public void webapi05601(CMi056 form) throws Exception;
	/**
	 * 菜单配置删除
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public void webapi05602(CMi056 form) throws Exception;
	
	/**
	 * 菜单配置修改
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public void webapi05603(CMi056 form) throws Exception;
	
	/**
	 * 菜单配置查询
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public WebApi05604_queryResult webapi05604(CMi056 form) throws Exception;
	/**
	 * 菜单保存排序
	 * @param json数组:arr
	 * @return
	 * @throws Exception
	 */
	public void webapi05605(JSONArray arr)throws Exception;
	/**
	 * 取所有菜单
	 * @throws Exception
	 */
	public List<Mi056> webapi05606()throws Exception;
	
	/**
	 * 子功能新增
	 * @param form
	 * @throws Exception
	 */
	public void webapi01401(CMi014 form) throws Exception;
	/**
	 * 子功能删除
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public void webapi01402(CMi014 form) throws Exception;
	
	/**
	 * 子功能修改
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public void webapi01403(String oldFuncid, String oldSubname, CMi014 form) throws Exception;
	
	/**
	 * 子功能查询
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public WebApi01404_queryResult webapi01404(CMi014 form) throws Exception;
	
	/**
	 * 子功能保存排序
	 * @param json数组:arr
	 * @return
	 * @throws Exception
	 */
	public void webapi01405(JSONArray arr)throws Exception;
	/**
	 * 功能-菜单配置新增
	 * @param form
	 * @throws Exception
	 */
	public void webapi00501(CMi005 form) throws Exception;
	/**
	 * 功能-菜单配置删除
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public void webapi00502(CMi005 form) throws Exception;
	
	/**
	 * 功能-菜单配置修改
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public void webapi00503(String oldCenterid, String oldFuncid, String oldCdid, CMi005 form) throws Exception;
	
	/**
	 * 功能-菜单配置查询
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public WebApi00504_queryResult webapi00504(CMi005 form) throws Exception;
	
	
	public void webapi00506(JSONArray arr)throws Exception;
	
	
	
}
