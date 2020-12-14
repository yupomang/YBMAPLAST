package com.yondervision.mi.dto;
/** 
* @ClassName: CMi002
* @Description: 
* @author 韩占远
* @date 2013-10-06
* 
*/ 
import java.util.List;

public class CMi002 extends Mi002 {
	private String[] role=null;
	private String roles = "";
    private List<Mi004> roleList=null;

	public List<Mi004> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Mi004> roleList) {
		this.roleList = roleList;
	}

	public String[] getRole() {
		return role;
	}

	public void setRole(String[] role) {
		this.role = role;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
    
}
