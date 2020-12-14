package com.yondervision.mi.result;

import java.util.List;

public class AppApi70203Result{
	private NewspapersNewsDetailBean newsDetal;
	private List<NewspapersNewsCommentBean> commentList;
	
	public NewspapersNewsDetailBean getNewsDetal() {
		return newsDetal;
	}
	public void setNewsDetal(NewspapersNewsDetailBean newsDetal) {
		this.newsDetal = newsDetal;
	}
	public List<NewspapersNewsCommentBean> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<NewspapersNewsCommentBean> commentList) {
		this.commentList = commentList;
	}
}
