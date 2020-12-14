package com.yondervision.mi.form;

public class WeiXin00401Form extends WeiXinApiCommonForm {
	//中心码
	private String regionId;
	//功能名称
	private String funcname;
	//是否有子项
	private String isflag;
	//功能类型
	private String functype;
	//功能键值
	private String funckey;
	//父类名称
	private String fathername;
	
	public String getFathername() {
		return fathername;
	}
	public void setFathername(String fathername) {
		this.fathername = fathername;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getFuncname() {
		return funcname;
	}
	public void setFuncname(String funcname) {
		this.funcname = funcname;
	}
	public String getIsflag() {
		return isflag;
	}
	public void setIsflag(String isflag) {
		this.isflag = isflag;
	}
	public String getFunctype() {
		return functype;
	}
	public void setFunctype(String functype) {
		this.functype = functype;
	}
	public String getFunckey() {
		return funckey;
	}
	public void setFunckey(String funckey) {
		this.funckey = funckey;
	}
	
	
	

	
}
