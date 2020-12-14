package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi704;

public class WebApi70404_queryResult {
	
	private String newsitemid;//报刊期次ID
	private String dicid;//序号
	private String newspaperforum;//版块
	private List<Mi704> newspapercolumnsList;

	public String getNewsitemid() {
		return newsitemid;
	}
	public void setNewsitemid(String newsitemid) {
		this.newsitemid = newsitemid;
	}
	public String getDicid() {
		return dicid;
	}
	public void setDicid(String dicid) {
		this.dicid = dicid;
	}
	public String getNewspaperforum() {
		return newspaperforum;
	}
	public void setNewspaperforum(String newspaperforum) {
		this.newspaperforum = newspaperforum;
	}
	
	public List<Mi704> getNewspapercolumnsList() {
		return newspapercolumnsList;
	}
	public void setNewspapercolumnsList(List<Mi704> newspapercolumnsList) {
		this.newspapercolumnsList = newspapercolumnsList;
	}

}
