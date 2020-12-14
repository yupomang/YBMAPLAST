package com.yondervision.mi.common;
/** 
* @ClassName: PermissionContext 
* @Description: 权限对象
* @author gongq
* @date 2013-11-06 
* 
*/ 

import java.util.List;
import com.yondervision.mi.common.filter.PermissionEncodingFilter;
import com.yondervision.mi.dao.Mi014DAO;
import com.yondervision.mi.dto.Mi014;
import com.yondervision.mi.dto.Mi014Example;
import com.yondervision.mi.util.SpringContextUtil; 
public class PermissionContext implements  java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2216479934049567723L;

	public final static String PERMISSIONCONTEXT = "_PERMISSIONCONTEXT_";

	/**
	 * 菜单/功能编码
	 */
	private String funcid;
	
	/**
	 * 权限编码
	 */
	private String permission;

	/**
	 * 构造函数 
	 * @param funcPermission 16位字符串,八位功能码,八位权限码
	 */
	public PermissionContext(String funcPermission){
		this.funcid = funcPermission.substring(0, 8);
		this.permission = funcPermission.substring(8);
	}
	
	/**
	 * 对象实例
	 * @return
	 */
	public static PermissionContext getInstance(){
		return (PermissionContext)PermissionEncodingFilter.permissionThreadLocal.get();
	}
	
	/**
	 * 校验子功能是否可以访问
	 * @param subname 子功能名
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isCan(String subname) {
		// 根据传入子功能名及当前访问对象的功能编码，到mi014中获取对应子功能序号
	    Mi014DAO mi014Dao =	(Mi014DAO)SpringContextUtil.getBean("mi014Dao");
		Mi014Example example = new Mi014Example();
		example.createCriteria().andFuncidEqualTo(this.funcid).andSubnameEqualTo(subname);
		List<Mi014> mi014List = mi014Dao.selectByExample(example);
		Mi014 mi014 = mi014List.get(0);
		Integer subno = mi014.getSubno();
		// 子功能序号进行进制转换
		int subnoInt = 8-subno.intValue();
		Double subnoDouble = Math.pow(2, subnoInt);
		String subnoStr = Integer.toBinaryString(subnoDouble.intValue());
		String temp = "00000000";
		if (subnoStr.length()<8) {
			subnoStr = temp.substring(subnoStr.length()) + subnoStr;
		}

		// 判断当前子功能是否在该机构权限(将subnoStr 与  funcvec 中的Permission做按位与，如结果=subnoStr，则有此权限)
		String bitwiseAnd = Integer.toBinaryString(Integer.parseInt(subnoStr, 2)&Integer.parseInt(this.permission.toString(), 2));
		if(bitwiseAnd.length()<8){
			bitwiseAnd = temp.substring(bitwiseAnd.length()) + bitwiseAnd;
		}
		if (subnoStr.equals(bitwiseAnd)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 如果检查当前进程不是通过.menu近来的旧抛出异常
	 */
	public void chkeckMenu(){
		
	}

	/**
	 * @return the funcid
	 */
	public String getFuncid() {
		return funcid;
	}

	/**
	 * @param funcid the funcid to set
	 */
	public void setFuncid(String funcid) {
		this.funcid = funcid;
	}

	/**
	 * @return the permission
	 */
	public String getPermission() {
		return permission;
	}

	/**
	 * @param permission the permission to set
	 */
	public void setPermission(String permission) {
		this.permission = permission;
	}
}
