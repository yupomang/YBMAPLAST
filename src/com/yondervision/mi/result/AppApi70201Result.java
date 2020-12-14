package com.yondervision.mi.result;

import java.util.List;

public class AppApi70201Result {

	private List<NewspapersTitleInfoBean> forumList = null;
	
	private List<NewspapersColumnsNewsBean> columnsNewsList = null;

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
