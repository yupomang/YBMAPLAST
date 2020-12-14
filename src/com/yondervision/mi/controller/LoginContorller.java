package com.yondervision.mi.controller;
/** 
* @ClassName: LoginContorller 
* @Description: 登录程序,权限管理，操作员管理，角色管理等
* @author 韩占远
* @date 2013-10-04  
* 
*/

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.filter.PermissionEncodingFilter;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.Mi002DAO;
import com.yondervision.mi.dto.*;
import com.yondervision.mi.dto.Mi002Example.Criteria;
import com.yondervision.mi.form.LoginForm;
import com.yondervision.mi.form.PwdModForm;
import com.yondervision.mi.service.CodeListApi001Service;
import com.yondervision.mi.service.PtlApi001Service;
import com.yondervision.mi.service.WebApi055Service;
import com.yondervision.mi.util.Base;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.SpringContextUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class LoginContorller {
	@Autowired
	PtlApi001Service ptlApi001ServiceImpl=null; 
	@Autowired
	WebApi055Service webApi055ServiceImpl;
	
	@Autowired
	private CodeListApi001Service codeListApi001Service = null;
	public void setCodeListApi001Service(CodeListApi001Service codeListApi001Service) {
		this.codeListApi001Service = codeListApi001Service;
	}
	
	@RequestMapping("/login.html")
	public String index( ModelMap modelMap){
		return "login";		
	}
	
	@RequestMapping("/logout.do")
	public @ResponseBody LoginForm logout(HttpServletRequest request){
		request.getSession().invalidate();
		LoginForm resultForm = new LoginForm();
		resultForm.setRancode("000000");
		resultForm.setMsg("登出成功");
		return resultForm;
	}
	
	@RequestMapping("/lout.html")
	public String lout( ModelMap modelMap){
		return "platform/lout";		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/login.do")
	public @ResponseBody LoginForm login(LoginForm form,  ModelMap modelMap,HttpServletRequest request) {
		//登录名密码解密
		String usname= Base.decode(form.getUsername());
		String pwd= Base.decode(form.getPassword());
		form.setUsername(usname);
		form.setPassword(pwd);
		LoginForm resultForm = new LoginForm();
		resultForm.setRecord("999999");
		Mi002DAO dao= (Mi002DAO)SpringContextUtil.getBean("mi002Dao");
		Mi002Example m2e=new Mi002Example();
		Criteria ca= m2e.createCriteria();

		//log.info("加密前用户名form.getUsername()===="+form.getUsername());
		////加密用户名
		//String loginid= MD5Util.generate(form.getUsername());
		//log.info("加密后用户名form.getUsername()===="+loginid);
		//加密密码
		/*log.info("加密前密码form.getPassword()===="+form.getPassword());
		String loginkey = MD5Util.MD5(form.getPassword());
		log.info("加密后密码form.getPassword()===="+loginkey);*/
		ca.andLoginidEqualTo(form.getUsername().trim());
		List<Mi002> list=dao.selectByExample(m2e);
		if(list.size()==0){
			resultForm.setMsg("操作员不存在");
			return resultForm;
		}else{
			com.yondervision.mi.dto.Mi002 bean=list.get(0);
			if(!bean.getPassword().equals(EncryptionByMD5.getMD5(form.getPassword().getBytes()))){
				resultForm.setMsg("密码不正确");
				return resultForm;
			}
		}
		// 验证当前session中验证码是否过期
		if(CommonUtil.isEmpty(request.getSession().getAttribute("loginVeriCode"))) {
			//modelMap.put("message", "当前验证码过期，请刷新后重新填写");
			//return "platform/error";
			resultForm.setMsg("当前验证码过期，请刷新后重新填写");
			return resultForm;
		}
		
		// 对登录验证码进行判断
		if (!CommonUtil.isEmpty(form.getRancode())) {
			String createVeriCode = request.getSession().getAttribute("loginVeriCode").toString();
			long veriCodeCreateTime = Long.valueOf(request.getSession().getAttribute("loginVeriCodeTime").toString());
			long nowDate = new Date().getTime();
			if (nowDate - veriCodeCreateTime > 60000) {
				//modelMap.put("message", "当前验证码过期，请刷新后重新填写");
				//return "platform/error";
				resultForm.setMsg("当前验证码过期，请刷新后重新填写");
				return resultForm;
			}
			if(!createVeriCode.equals(form.getRancode())) {
				//modelMap.put("message", "验证码不正确");
				//return "platform/error";
				resultForm.setMsg("验证码不正确");
				return resultForm;
			}
		}else {
			//modelMap.put("message", "请输入验证码后提交");
			//return "platform/error";
			resultForm.setMsg("请输入验证码后提交");
			return resultForm;
		}

		UserContext user= new UserContext(list.get(0));
		request.getSession().setAttribute(UserContext.USERCONTEXT,user);
		PermissionEncodingFilter.threadLocal.set(user); 
		PermissionEncodingFilter.setPathByUser(user,request);
		Logger logger =LoggerUtil.getLogger();
		logger.info("登录成功2"); 
		resultForm.setFuncJson(user.getFuncJson().toString());
		//return "redirect:/frames.html";
		resultForm.setRecord(Constants.WEB_SUCCESS_CODE);
		resultForm.setMsg(Constants.WEB_SUCCESS_MSG);
		return resultForm;
	}

	/*@RequestMapping("/login.do")
	public @ResponseBody LoginForm login(LoginForm form,  ModelMap modelMap,HttpServletRequest request) throws Exception{
		//Logger log = LoggerUtil.getLogger();
		LoginForm resultForm = new LoginForm();
		resultForm.setRecord("999999");
		Mi002DAO dao= (Mi002DAO) SpringContextUtil.getBean("mi002Dao");
		Mi002Example m2e=new Mi002Example();
		Criteria ca= m2e.createCriteria();

		//log.info("form.getUsername()===="+form.getUsername());
		//add by Carter King  2018/11/9   中心让加密用户名，密码
		//String loginid= AESUtil.desEncrypt(form.getUsername());
		//log.info("解密后  form.getUsername()===="+loginid);
		ca.andLoginidEqualTo(form.getUsername().trim());
		List<com.yondervision.mi.dto.Mi002> list=dao.selectByExample(m2e);
		if(list.size()==0){
			//modelMap.put("message", "操作员不存在");
			resultForm.setMsg("操作员不存在");
			return resultForm;
			//return "platform/error";
		}else{
			com.yondervision.mi.dto.Mi002 bean=list.get(0);
			if(!bean.getPassword().equals(EncryptionByMD5.getMD5(form.getPassword().trim().getBytes()))){
				//modelMap.put("message", "密码不正确");
				//return "platform/error";
				resultForm.setMsg("密码不正确");
				return resultForm;
			}
		}
		// 验证当前session中验证码是否过期
		if(CommonUtil.isEmpty(request.getSession().getAttribute("loginVeriCode"))) {
			//modelMap.put("message", "当前验证码过期，请刷新后重新填写");
			//return "platform/error";
			resultForm.setMsg("当前验证码过期，请刷新后重新填写");
			return resultForm;
		}

		// 对登录验证码进行判断
		if (!CommonUtil.isEmpty(form.getRancode())) {
			String createVeriCode = request.getSession().getAttribute("loginVeriCode").toString();
			long veriCodeCreateTime = Long.valueOf(request.getSession().getAttribute("loginVeriCodeTime").toString());
			long nowDate = new Date().getTime();
			if (nowDate - veriCodeCreateTime > 60000) {
				//modelMap.put("message", "当前验证码过期，请刷新后重新填写");
				//return "platform/error";
				resultForm.setMsg("当前验证码过期，请刷新后重新填写");
				return resultForm;
			}
			if(!createVeriCode.equals(form.getRancode())) {
				//modelMap.put("message", "验证码不正确");
				//return "platform/error";
				resultForm.setMsg("验证码不正确");
				return resultForm;
			}
		}else {
			//modelMap.put("message", "请输入验证码后提交");
			//return "platform/error";
			resultForm.setMsg("请输入验证码后提交");
			return resultForm;
		}

		UserContext user= new UserContext(list.get(0));
		request.getSession().setAttribute(UserContext.USERCONTEXT,user);
		PermissionEncodingFilter.threadLocal.set(user);
		PermissionEncodingFilter.setPathByUser(user,request);
		Logger logger =LoggerUtil.getLogger();
		logger.info("登录成功2");
		resultForm.setFuncJson(user.getFuncJson().toString());
		//return "redirect:/frames.html";
		resultForm.setRecord(Constants.WEB_SUCCESS_CODE);
		resultForm.setMsg(Constants.WEB_SUCCESS_MSG);
		return resultForm;
	}*/

	@RequestMapping("/frames.html")
	public String frames(ModelMap modelMap){
		return "platform/frames";
	}
	
	@RequestMapping("/header.html")
	public String header(ModelMap modelMap){
		return "platform/header";
	}
	
	@RequestMapping("/navigation.html")
	public String navigation(ModelMap modelMap){
		return "platform/navigation";
	}
	
	@RequestMapping("/ptl40001.html")
	public String pt40001(ModelMap modelMap){
		UserContext user = UserContext.getInstance();
		modelMap.put("clist", ptlApi001ServiceImpl.getCenterListJson(user.getCenterid()).toString());		
		return "ptl/ptl40001";
	}
	
	@RequestMapping("/ptl40001.json")
	public String pt40001Json(ModelMap modelMap,HttpServletRequest request){
		modelMap.put("rows", ptlApi001ServiceImpl.getOperListByCenterJson(request.getParameter("centerid")));
		return 	"";
	}
	
	@RequestMapping("/ptl40001RoleList.json")
	public String ptl40001RoleList(ModelMap modelMap, String centerid){
		JSONArray jsonarry = ptlApi001ServiceImpl.getRoleListJson(centerid);
		if(CommonUtil.isEmpty(jsonarry)){
			modelMap.clear();
			modelMap.put("rolelist", "");
			modelMap.put("recode", "999999");
			modelMap.put("msg", "查无数据");
		}else{
			modelMap.clear();
			modelMap.put("rolelist", jsonarry);
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
		}
			
		return 	"";
	}

	@RequestMapping("/ptl40001Add.json")
	public String pt40001JsonAdd(CMi002 mi002 ,ModelMap modelMap){	
		
		ptlApi001ServiceImpl.addOper(mi002);
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		return 	"";
	}
	
	@RequestMapping("/ptl40001Upd.json")
	public String pt40001JsonUpd(CMi002 mi002,ModelMap modelMap){
		ptlApi001ServiceImpl.updateOper(mi002);
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		return 	"";
	}
	
	@RequestMapping("/ptl40001Del.json")
	public String pt40001JsonDel(HttpServletRequest request,ModelMap modelMap){
		
		List<String> list=new ArrayList<String>();
		String[] ary=request.getParameter("loginid").split(",");
		for(int i=0;i<ary.length;i++){
			list.add(ary[i]);
		}
		ptlApi001ServiceImpl.delOper(list);
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		return 	"";
	}
	
	@RequestMapping("/ptl40002.html")
	public String pt40002(ModelMap modelMap){		
		return "ptl/ptl40002";
	}
	
	@RequestMapping("/ptl40002Param.json")
	public String pt40002Param(ModelMap modelMap){
		UserContext user = UserContext.getInstance();
		System.out.println("ptl40002Param.json   ,  centerid:"+user.getCenterid());
//		JSONArray jsonarray =  ptlApi001ServiceImpl.getCenterListJsonArray(user.getCenterid());
		// 获取 永道用户 （城市中心8个0）对应的所属标志（公有、私有）
		JSONArray attributeJsonarray =  codeListApi001Service.getCodeListJson(user.getCenterid(), "attributeFlg");
		// 获取 永道用户 以外 的所属标志（私有）
		JSONArray attributeTmpJsonarray =  codeListApi001Service.getCodeListJson(user.getCenterid(), "attributeFlgTmp");

		modelMap.put("centerid", user.getCenterid());
//		if(jsonarray!=null)
//			modelMap.put("centerList", jsonarray);
		if(attributeJsonarray!=null)
			modelMap.put("attributeFlgList", attributeJsonarray);
		if(attributeTmpJsonarray!=null)
			modelMap.put("attributeFlgListTmp", attributeTmpJsonarray);
		return "";
	}
	
	@RequestMapping("/ptl40002Qry.json")
	public String pt40002Qry(String centerid, ModelMap modelMap){
		System.out.println("/ptl40002Qry.json,上传中心码："+centerid);
		modelMap.put("rows", ptlApi001ServiceImpl.getRoleAllList(centerid));
		return "";
	}
	
	@RequestMapping("/ptl40002Del.json")
	public String pt40002Del(HttpServletRequest request,ModelMap modelMap){	
		List<String> list=new ArrayList<String>();
		String[] ary=request.getParameter("roleid").split(",");
		for(int i=0;i<ary.length;i++){
			list.add(ary[i]);
		} 
		ptlApi001ServiceImpl.delRole(list);
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		UserContext user = UserContext.getInstance();
		modelMap.put("rows", ptlApi001ServiceImpl.getRoleAllList(user.getCenterid()));
		return "";
	}
	
	@RequestMapping("/ptl40002Add.json")
	public String pt40002JsonAdd(Mi003 mi003 ,ModelMap modelMap){
		ptlApi001ServiceImpl.addRole(mi003);
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");		 
		return 	"";
	}
	
	@RequestMapping("/ptl40002Upd.json")
	public String pt40002JsonUpd(Mi003 mi003 ,ModelMap modelMap){ 
		ptlApi001ServiceImpl.updateRole(mi003);
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");		 
		return 	"";
	}
	
	@RequestMapping("/ptl40003.html")
	public String pt40003(ModelMap modelMap){
//		UserContext user = UserContext.getInstance();
//		modelMap.put("contacttypelist", codeListApi001Service.getCodeListJson(user.getCenterid(), "contacttype"));
		return "ptl/ptl40003";
	}
	
	@RequestMapping("/ptl40003Qry.json")
	public String pt40003Qry(String centerid, ModelMap modelMap){
		modelMap.put("rows", ptlApi001ServiceImpl.getCenterAllList(centerid));
		return "";
	}
	
	@RequestMapping("/ptl40003Del.json")
	public String pt40003Del(HttpServletRequest request, String userCenterid, ModelMap modelMap){	
		List<String> list=new ArrayList<String>();
		String[] ary=request.getParameter("centerid").split(",");
		for(int i=0;i<ary.length;i++){
			list.add(ary[i]);
		} 
		ptlApi001ServiceImpl.delCenter(list);
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		modelMap.put("rows", ptlApi001ServiceImpl.getCenterAllList(userCenterid));

		return "";
	}
	
	@RequestMapping("/ptl40003Add.json")
	public String pt40003JsonAdd(Mi001 mi001 ,ModelMap modelMap){ 
		ptlApi001ServiceImpl.addCenter(mi001);
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");		 
		return 	"";
	}
	
	@RequestMapping("/ptl40003Upd.json")
	public String pt40003JsonUpd(Mi001 mi001 ,ModelMap modelMap){ 
		ptlApi001ServiceImpl.updateCenter(mi001);
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");		 
		return 	"";
	}
	
	@RequestMapping("/ptl40003Activ.json")
	public String ptl40003Activ(HttpServletRequest request, ModelMap modelMap){
		List<String> list=new ArrayList<String>();
		String[] ary=request.getParameter("centerid").split(",");
		for(int i=0;i<ary.length;i++){
			list.add(ary[i]);
		}
		ptlApi001ServiceImpl.activationCenter(list);
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");		 
		return 	"";
	}

	@RequestMapping("/ptl40004Chose.html")
	public String ptl40004Pre(ModelMap modelMap){//权限管理
		UserContext user = UserContext.getInstance();
		if(Constants.YD_ADMIN.equals(user.getCenterid())){
			return "redirect:/ptl40004Pre.html";
		}else{
			return "redirect:/ptl40004.html";
		}
	}
	
	@RequestMapping("/ptl40004Pre.html")
	public String ptl40004PreView(ModelMap modelMap){//权限管理
		UserContext user = UserContext.getInstance();
		JSONArray jsonarray =  ptlApi001ServiceImpl.getCenterListJsonArray(user.getCenterid());
		modelMap.put("centerList", jsonarray);
		return "ptl/ptl40004Pre";
	}
	
	@RequestMapping("/ptl40004.do")
	public String ptl40004Do(String centerid, ModelMap modelMap){//权限管理
		return "redirect:/ptl40004.html?centerid="+centerid+"";
	}
	
	@RequestMapping("/ptl40004.html")
	public String pt40004(String centerid, ModelMap modelMap){//权限管理
		return "ptl/ptl40004";
	}
	/**
	 * 根据中心获取角色
	 * @param centerid
	 * @param modelMap
	 */
	@RequestMapping("/getRoleByCenterid.json")
	public void getAllRoleListByCenterid(String centerid, ModelMap modelMap){
		List<Mi003> roleAllList = webApi055ServiceImpl.getRoleAllList(centerid);
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("rows", roleAllList);
	}
	
	@RequestMapping("/ptl40004Get.json")
	public void ptl40004Get(String centerid, ModelMap modelMap){//权限管理
		String centerId = null;
		UserContext user = UserContext.getInstance();
		if(CommonUtil.isEmpty(centerid)){
			centerId = user.getCenterid();
		}else{
			centerId = centerid;
		}

		HashMap<String,String> centerIdMap = new HashMap<String,String>();
		centerIdMap.put("centerid", centerId);
		
		String centerName =  ptlApi001ServiceImpl.getCenterName(centerId);
		
		modelMap.clear();
		modelMap.put("menujson", ptlApi001ServiceImpl.getMenuJson(centerId, "00000000"));
		modelMap.put("rolejson", ptlApi001ServiceImpl.getRoleListJson(centerId));
		modelMap.put("permissionMenuJson",	ptlApi001ServiceImpl.getPermissionMenuJson(null, centerId));
		modelMap.put("centeridJson", new  ObjectMapper().convertValue(centerIdMap, JSONObject.class));
		modelMap.put("centerName", centerName);
	}

	@RequestMapping("/ptl40004Upd.json")
	public String pt40004Upd(HttpServletRequest request,ModelMap modelMap){//权限管理
		String[] permissions=request.getParameterValues("permission");
		String centerid = request.getParameter("centerid");
		ptlApi001ServiceImpl.savePermission(permissions, centerid);
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");		 
		return "";
	}
	
	@RequestMapping("/ptl40005.html")
	public String pt40005(ModelMap modelMap){//菜单管理        
		return "ptl/ptl40005";
	}
	
	@RequestMapping("/ptl40005Qry.html")
	public String pt40005Qry(String pid,ModelMap modelMap){//菜单管理   
			
	    JSONArray ary=new JSONArray(); 
	    List<Mi005> list= ptlApi001ServiceImpl.getMenuListByPid(pid) ;	
	    for(int i=0;i<list.size();i++){
				JSONObject obj=new JSONObject();
				obj.put("id", list.get(i).getFuncid());
				obj.put("text", list.get(i).getFuncname());			 
				obj.put("state", "closed");
				ary.add(obj); 
	    } 
	    modelMap.put("ary", ary);
		return "ptl/ptl40005Qry";
	} 
	@RequestMapping("/ptl40005Qry.json")
	public String pt40005QryJson(String pid,ModelMap modelMap){//菜单管理 
	   List<Mi005> list=null;
	   if(pid!=null)
	       list= ptlApi001ServiceImpl.getMenuListByPid(pid);
	   else
		   list=new ArrayList<Mi005>();
	   modelMap.put("rows", list);
	   return "";  
	}
	
	@RequestMapping("/ptl40005Upd.json")
	public String pt40005Upd(String oldfuncid,Mi005 mi005 ,HashMap modelMap){//菜单修改
	   ptlApi001ServiceImpl.updateFunc(oldfuncid, mi005);	
	   modelMap.put("recode", "000000");
	   modelMap.put("msg", "成功");		
	   return "";  
	}
	
	@RequestMapping("/ptl40005Add.json")
	public String pt40005Add(Mi005 mi005 ,HashMap modelMap){//菜单添加
	   ptlApi001ServiceImpl.addFunc(mi005);
	   modelMap.put("recode", "000000");
	   modelMap.put("msg", "成功");		
	   return "";  
	}
	
	@RequestMapping("/ptl40005Del.json")
	public String pt40005Del(HttpServletRequest request ,HashMap modelMap){//菜单删除
		List<String> list=new ArrayList<String>();
		String[] ary=request.getParameter("funcid").split(",");
		for(int i=0;i<ary.length;i++){
			list.add(ary[i]);
		} 
	   ptlApi001ServiceImpl.delFunc(list);
	   modelMap.put("recode", "000000");
	   modelMap.put("msg", "成功");		
	   return "";  
	}

	@RequestMapping("/ptl40011.html")
	public String ptl40011(){
		return "ptl/ptl40011";
	}
	
	@RequestMapping("/ptl40011Upd.json")
	public String ptl40011Upd(PwdModForm form, ModelMap modelMap){//密码修改

		Logger log = LoggerUtil.getLogger();

		String businName = "密码修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		ptlApi001ServiceImpl.updatePwd(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);	
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "ptl/ptl40011";
	}

	@RequestMapping("/ptl40014.html")
	public String pt40014(ModelMap modelMap){//子功能管理        
		return "ptl/ptl40014";
	}
	
	@RequestMapping("/ptl40014Qry.html")
	public String pt40014Qry(String pid, ModelMap modelMap){//子功能管理
		
	    JSONArray ary = new JSONArray();
	    List<Mi005> list = ptlApi001ServiceImpl.getMenuListByPid(pid);	
	    for(int i = 0; i < list.size(); i++){
	    	JSONObject obj = new JSONObject();
	    	obj.put("id", list.get(i).getFuncid());
	    	obj.put("text", list.get(i).getFuncname());			 
	    	obj.put("state", "closed");	
	    	ary.add(obj); 
	    }
	    modelMap.put("ary", ary);
	    return "ptl/ptl40014Qry";
	}
	
	@RequestMapping("/ptl40014Qry.json")
	public String pt40014QryJson(String funcid, ModelMap modelMap){//子功能管理
	   List<Mi014> list = null;
	   if(funcid != null)
	       list = ptlApi001ServiceImpl.getChildFuncListByPid(funcid);
	   else
		   list = new ArrayList<Mi014>();
	   modelMap.put("rows", list);
	   return "";  
	}

	@RequestMapping("/ptl40014Add.json")
	public String pt40014Add(Mi014 mi014,ModelMap modelMap){//子功能添加
	   ptlApi001ServiceImpl.addChildFunc(mi014);
	   modelMap.put("recode", "000000");
	   modelMap.put("msg", "成功");		
	   return "";  
	}
	
	@RequestMapping("/ptl40014Del.json")
	public String pt40014Del(HttpServletRequest request ,ModelMap modelMap){//子功能删除
		List<Mi014> list = new ArrayList<Mi014>();
		String[] ary1 = request.getParameter("funcid").split(",");
		String[] ary2 = request.getParameter("subname").split(",");
		for(int i=0;i<ary1.length;i++){
			Mi014 mi014 = new Mi014();
			mi014.setFuncid(ary1[i]);
			mi014.setSubname(ary2[i]);
			list.add(mi014);
		} 
	   ptlApi001ServiceImpl.delChildFunc(list);
	   modelMap.put("recode", "000000");
	   modelMap.put("msg", "成功");		
	   return "";  
	}
	
	@RequestMapping("/ptl40014Upd.json")
	public String pt40014Upd(String oldSubname, Mi014 mi014, ModelMap modelMap){//子功能修改
	   ptlApi001ServiceImpl.updateChildFunc(oldSubname, mi014);
	   modelMap.put("recode", "000000");
	   modelMap.put("msg", "成功");
	   return "";
	}
	
	@RequestMapping("/ptl40015.html")
	public String pt40015(String centerid, ModelMap modelMap){//机构权限管理
//		modelMap.put("functionjson", ptlApi001ServiceImpl.getMenuChildFuncJson("00000000" ,centerid));//菜单项及子项
//		modelMap.put("centerjson",	ptlApi001ServiceImpl.getCenterListJsonArray("00000000"));//城市中心
//		modelMap.put("permissionMenuJson",	ptlApi001ServiceImpl.getCenterPermissionJson(null));
		return "ptl/ptl40015";
	}
	
	@RequestMapping("/ptl40015Qry.json")
	public String ptl40015Qry(String centerid, ModelMap modelMap){//机构权限管理
		modelMap.clear();
		modelMap.put("functionjson", ptlApi001ServiceImpl.getMenuChildFuncJson("00000000" ,centerid));//菜单项及子项
//		modelMap.put("centerjson",	ptlApi001ServiceImpl.getCenterListJsonArray("00000000"));//城市中心
		List<String> centers = new ArrayList();
		centers.add(centerid);
		modelMap.put("permissionMenuJson",	ptlApi001ServiceImpl.getCenterPermissionJson(centers));
		return "";
	}
	
	@RequestMapping("/ptl40015Upd.json")
	public String pt40015Upd(String centerid ,String permission,HttpServletRequest request,ModelMap modelMap){//机构权限更新
		String[] permissions = null;
				//request.getParameterValues("permission");	
		if(!CommonUtil.isEmpty(permission)){
			permissions = permission.split(",");
		}
		System.out.println("系统功能分配开始,permissions:"+permission+"    ,centerid:"+centerid);
		ptlApi001ServiceImpl.saveCenterPermission(permissions,centerid);
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");		 
		return "";
	}
	
	@RequestMapping("/welcome.html")
	public String welcome(){
		return "platform/welcome";
	}
	
	@RequestMapping("/getUserMessage.json")
	public String getUserMessage( ModelMap modelMap){
		UserContext user = UserContext.getInstance();
		if(!CommonUtil.isEmpty(user)){
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("loginid", user.getLoginid());
			modelMap.put("centerid", user.getCenterid());
			modelMap.put("opername", user.getOpername());
			modelMap.put("phone", user.getPhone());
			modelMap.put("centerName", user.getCenterName());
			modelMap.put("funcJson",user.getFuncJson().toString());
		}else{
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "请重新登录");
		}
		return "";
	}

	public PtlApi001Service getPtlApi001ServiceImpl() {
		return ptlApi001ServiceImpl;
	}

	public void setPtlApi001ServiceImpl(PtlApi001Service ptlApi001ServiceImpl) {
		this.ptlApi001ServiceImpl = ptlApi001ServiceImpl;
	}

	public WebApi055Service getWebApi055ServiceImpl() {
		return webApi055ServiceImpl;
	}

	public void setWebApi055ServiceImpl(WebApi055Service webApi055ServiceImpl) {
		this.webApi055ServiceImpl = webApi055ServiceImpl;
	}
	
}
