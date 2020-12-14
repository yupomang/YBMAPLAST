package com.yondervision.mi.result;

import java.util.List;

public class AppApi70109Result {
	private List<NewspapersTitleInfoBean> timesList = null;
	
	private List<NewspapersColumnsNewsBean> columnsNewsList = null;

	public List<NewspapersTitleInfoBean> getTimesList() {
		return timesList;
	}

	public void setTimesList(List<NewspapersTitleInfoBean> timesList) {
		this.timesList = timesList;
	}
	
	public List<NewspapersColumnsNewsBean> getColumnsNewsList() {
		return columnsNewsList;
	}

	public void setColumnsNewsList(List<NewspapersColumnsNewsBean> columnsNewsList) {
		this.columnsNewsList = columnsNewsList;
	}
}
