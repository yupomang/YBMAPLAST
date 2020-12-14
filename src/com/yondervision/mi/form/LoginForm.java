package com.yondervision.mi.form;

import net.sf.json.JSONArray;

public class LoginForm {
	private String username;
	private String password;
	private String rancode;
	private String record;//返回结果编码
	private String msg;//返回结果信息
	private String funcJson;
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getRancode() {
		return rancode;
	}
	public void setRancode(String rancode)	 {
		this.rancode = rancode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFuncJson() {
		return funcJson;
	}
	public void setFuncJson(String funcJson) {
		this.funcJson = funcJson;
	}	
}
