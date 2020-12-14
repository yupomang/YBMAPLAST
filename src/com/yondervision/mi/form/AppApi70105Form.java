package com.yondervision.mi.form;

public class AppApi70105Form extends AppApiCommonForm {			
	private String newsSeqno = "";// 新闻ID
	private String commentUser = "";//评论人
	private String commentContent = "";//评论内容
	private String commentUserId = "";//评论人Id(微信，唯一标识)
	
	public String getNewsSeqno() {
		return newsSeqno;
	}
	public void setNewsSeqno(String newsSeqno) {
		this.newsSeqno = newsSeqno;
	}
	public String getCommentUser() {
		return commentUser;
	}
	public void setCommentUser(String commentUser) {
		this.commentUser = commentUser;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public String getCommentUserId() {
		return commentUserId;
	}
	public void setCommentUserId(String commentUserId) {
		this.commentUserId = commentUserId;
	}
}
