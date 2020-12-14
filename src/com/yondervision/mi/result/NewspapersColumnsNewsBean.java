package com.yondervision.mi.result;

import java.util.List;

public class NewspapersColumnsNewsBean {
	private NewspapersTitleInfoBean columns;
	private List<NewspapersNewsBean> newsList = null;
	
	public NewspapersTitleInfoBean getColumns() {
		return columns;
	}
	public void setColumns(NewspapersTitleInfoBean columns) {
		this.columns = columns;
	}
	public List<NewspapersNewsBean> getNewsList() {
		return newsList;
	}
	public void setNewsList(List<NewspapersNewsBean> newsList) {
		this.newsList = newsList;
	}
}
