package com.yondervision.mi.result;

import java.util.List;

/** 
* @ClassName: AppApi00801Result 
* @Description: TODO
* @author Caozhongyan
* @date Sep 27, 2013 2:56:13 PM   
* 
*/ 
public class AppApi10102Result {
	private AppApi10101Result02 map = null;
	
	private List<TitleInfoBean> list = null;
	
	private List<TitleInfoBean> content = null;

	public AppApi10101Result02 getMap() {
		return map;
	}

	public void setMap(AppApi10101Result02 map) {
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
	
	
}


