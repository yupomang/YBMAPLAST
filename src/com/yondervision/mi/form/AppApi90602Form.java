package com.yondervision.mi.form;

/** 
* @ClassName: AppApi90601Form 
* @Description: 工单系统——用户获取待办任务
* @author Caozhongyan
* @date 2016年9月23日 上午11:30:10   
* 
*/ 
public class AppApi90602Form extends AppApiCommonForm{
	
	private String token_ = "";
	
	private String user_id = "";
	
	private String task_id = "";

	public String getToken_() {
		return token_;
	}

	public void setToken_(String token_) {
		this.token_ = token_;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	
}
