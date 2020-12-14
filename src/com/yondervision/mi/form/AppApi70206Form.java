package com.yondervision.mi.form;

public class AppApi70206Form extends AppApiCommonForm {			
	private String commentSeqno = "";// 评论ID
	private String user_Id = "";//唯一用户ID
	private String userName = "";
	private String praiseFlg = "";
	
	public String getCommentSeqno() {
		return commentSeqno;
	}

	public void setCommentSeqno(String commentSeqno) {
		this.commentSeqno = commentSeqno;
	}
	public String getUser_Id() {
		return user_Id;
	}
	public void setUser_Id(String userId) {
		user_Id = userId;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPraiseFlg() {
		return praiseFlg;
	}
	public void setPraiseFlg(String praiseFlg) {
		this.praiseFlg = praiseFlg;
	}
}
