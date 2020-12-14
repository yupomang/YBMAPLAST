package com.yondervision.mi.result;

import java.util.List;
import java.util.Map;

public class AppApi70010Result {
	private List<Map<String, String>> parentViewItemList = null;
	private String curViewItemId = "";
	private String curViewItemVal = "";

	public List<Map<String, String>> getParentViewItemList() {
		return parentViewItemList;
	}
	public void setParentViewItemList(List<Map<String, String>> parentViewItemList) {
		this.parentViewItemList = parentViewItemList;
	}
	public String getCurViewItemId() {
		return curViewItemId;
	}
	public void setCurViewItemId(String curViewItemId) {
		this.curViewItemId = curViewItemId;
	}
	public String getCurViewItemVal() {
		return curViewItemVal;
	}
	public void setCurViewItemVal(String curViewItemVal) {
		this.curViewItemVal = curViewItemVal;
	}
	
}
