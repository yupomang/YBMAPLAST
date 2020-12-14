package com.yondervision.mi.result;

import java.util.List;

public class AppApi05802Result {
	private String recode = "";
	private String msg = "";
	private List<AppApi05801Result> result;
	public String getRecode() {
		return recode;
	}
	public String getMsg() {
		return msg;
	}
	public List<AppApi05801Result> getResult() {
		return result;
	}
	public void setRecode(String recode) {
		this.recode = recode;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setResult(List<AppApi05801Result> result) {
		this.result = result;
	}
	
}
