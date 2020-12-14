package com.yondervision.mi.result;

import java.util.List;

public class AppApi70009Result {
	private List<ViewItemBean> childViewItemList = null;
	private String curChildViewItemId = "";
	private NewsBean news = null;
	
	public List<ViewItemBean> getChildViewItemList() {
		return childViewItemList;
	}
	public void setChildViewItemList(List<ViewItemBean> childViewItemList) {
		this.childViewItemList = childViewItemList;
	}
	public String getCurChildViewItemId() {
		return curChildViewItemId;
	}
	public void setCurChildViewItemId(String curChildViewItemId) {
		this.curChildViewItemId = curChildViewItemId;
	}
	public NewsBean getNews() {
		return news;
	}
	public void setNews(NewsBean news) {
		this.news = news;
	}
}
