package com.yondervision.mi.result;

import java.util.List;

public class AppApi70013Result {
	
	private String itemId = "";
	private String itemVal = "";
	private String hasChild = "";
	private NewsBean news = null;
	private List<ViewItemAndDetailBean> childViewItemList = null;
	
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
	public NewsBean getNews() {
		return news;
	}
	public void setNews(NewsBean news) {
		this.news = news;
	}
	public List<ViewItemAndDetailBean> getChildViewItemList() {
		return childViewItemList;
	}
	public void setChildViewItemList(List<ViewItemAndDetailBean> childViewItemList) {
		this.childViewItemList = childViewItemList;
	}
}
