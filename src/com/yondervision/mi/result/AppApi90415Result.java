package com.yondervision.mi.result;

import java.util.List;

public class AppApi90415Result {
	private String recode = "";
	private String code = "";
	private String msg = "";
	private List datas;
	private String totalCount = "";
	public String getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	public List getDatas() {
		return datas;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setDatas(List datas) {
		this.datas = datas;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getRecode() {
		return recode;
	}
	public void setRecode(String recode) {
		this.recode = recode;
	}
	
}
