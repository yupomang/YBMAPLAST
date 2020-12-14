package com.yondervision.mi.result;

import java.util.List;

public class AppApi70101Result {
	private List<NewspapersTitleInfoBean> timesList = null;
	
	private List<NewspapersTitleInfoBean> forumList = null;
	
	private List<NewspapersColumnsNewsBean> columnsNewsList = null;

	public List<NewspapersTitleInfoBean> getTimesList() {
		return timesList;
	}

	public void setTimesList(List<NewspapersTitleInfoBean> timesList) {
		this.timesList = timesList;
	}

	public List<NewspapersTitleInfoBean> getForumList() {
		return forumList;
	}

	public void setForumList(List<NewspapersTitleInfoBean> forumList) {
		this.forumList = forumList;
	}

	public List<NewspapersColumnsNewsBean> getColumnsNewsList() {
		return columnsNewsList;
	}

	public void setColumnsNewsList(List<NewspapersColumnsNewsBean> columnsNewsList) {
		this.columnsNewsList = columnsNewsList;
	}
}
