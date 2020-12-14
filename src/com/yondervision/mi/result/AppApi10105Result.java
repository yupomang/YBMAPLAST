package com.yondervision.mi.result;

import java.util.List;

/** 
* @ClassName: AppApi10105Result 
* @Description: 
* @author gongqi
* 
*/ 
public class AppApi10105Result {
	private AppApi10101Result01 map = null;
	
	private List<TitleInfoBean> list = null;
	
	private List<TitleInfoBean> content = null;
	
	private String websitecode = "";

	public AppApi10101Result01 getMap() {
		return map;
	}

	public void setMap(AppApi10101Result01 map) {
		this.map = map;
	}

	public List<TitleInfoBean> getList() {
		return list;
	}

	public void setList(List<TitleInfoBean> list) {
		this.list = list;
	}

	public List<TitleInfoBean> getContent() {
		return content;
	}

	public void setContent(List<TitleInfoBean> content) {
		this.content = content;
	}

	public String getWebsitecode() {
		return websitecode;
	}

	public void setWebsitecode(String websitecode) {
		this.websitecode = websitecode;
	}
}


