package com.yondervision.mi.result;

import java.util.List;

import com.yondervision.mi.dto.Mi704;

public class WebApi70704_queryResult {
	
	private String newsviewitemid;//内容展现项编号
	//private String dicid;//序号
	private List<Mi704> newspapercolumnsList;
	public String getNewsviewitemid() {
		return newsviewitemid;
	}
	public void setNewsviewitemid(String newsviewitemid) {
		this.newsviewitemid = newsviewitemid;
	}
	/*public String getDicid() {
		return dicid;
	}
	public void setDicid(String dicid) {
		this.dicid = dicid;
	}*/
	public List<Mi704> getNewspapercolumnsList() {
		return newspapercolumnsList;
	}
	public void setNewspapercolumnsList(List<Mi704> newspapercolumnsList) {
		this.newspapercolumnsList = newspapercolumnsList;
	}

}
