package com.yondervision.mi.result;

import java.util.List;

/** 
* @ClassName: AppApi00801Result 
* @Description: TODO
* @author Caozhongyan
* @date Sep 27, 2013 2:56:13 PM   
* 
*/ 
public class AppApi00801Result {
	private AppApi00801Result01 map = null;
	
	private List<TitleInfoBean> list = null;
	
	private List<TitleInfoBean> content = null;
	
	private List<AppApi10101Result> banks = null;

	public List<AppApi10101Result> getBanks() {
		return banks;
	}

	public void setBanks(List<AppApi10101Result> banks) {
		this.banks = banks;
	}

	public AppApi00801Result01 getMap() {
		return map;
	}

	public void setMap(AppApi00801Result01 map) {
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


