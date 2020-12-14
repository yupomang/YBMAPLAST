package com.yondervision.mi.result;

import java.util.List;

public class ViewItemBean {
	private String itemId = "";
	private String itemVal = "";
	private String hasChild = "";
	private List<ViewItemBean> childViewItemList = null;
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemVal() {
		return itemVal;
	}
	public void setItemVal(String itemVal) {
		this.itemVal = itemVal;
	}
	public String getHasChild() {
		return hasChild;
	}
	public void setHasChild(String hasChild) {
		this.hasChild = hasChild;
	}
	public List<ViewItemBean> getChildViewItemList() {
		return childViewItemList;
	}
	public void setChildViewItemList(List<ViewItemBean> childViewItemList) {
		this.childViewItemList = childViewItemList;
	}
}
