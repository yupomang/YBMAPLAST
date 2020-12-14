package com.yondervision.mi.form;

public class AppApi70106Form extends AppApiCommonForm {			
	private String newsSeqno = "";// 新闻ID
	private String user_Id = "";//唯一用户ID
	private String userName = "";//昵称
	private String praiseFlg = "";
	
	public String getNewsSeqno() {
		return newsSeqno;
	}
	public void setNewsSeqno(String newsSeqno) {
		this.newsSeqno = newsSeqno;
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
