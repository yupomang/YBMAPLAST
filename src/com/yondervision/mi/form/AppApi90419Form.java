package com.yondervision.mi.form;

/** 
* @ClassName: AppApi90413Form 
* @Description: 新媒体客服——
* @author Caozhongyan
* @date 2016年9月23日 上午11:29:50   
* 
*/ 
public class AppApi90419Form extends AppApiCommonForm{
	
	private String accessToken = "";
	private String tpl_id = "";
	private String tpl_name = "";
	private String task_score = "";
	private String answer_id = "";
	private String answer_name = "";
	private String answer_ip = "";
	
	private String array = "";

	public String getAccessToken() {
		return accessToken;
	}

	public String getTpl_id() {
		return tpl_id;
	}

	public String getTpl_name() {
		return tpl_name;
	}

	public String getTask_score() {
		return task_score;
	}

	public String getAnswer_id() {
		return answer_id;
	}

	public String getAnswer_name() {
		return answer_name;
	}

	public String getAnswer_ip() {
		return answer_ip;
	}

	public String getArray() {
		return array;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setTpl_id(String tpl_id) {
		this.tpl_id = tpl_id;
	}

	public void setTpl_name(String tpl_name) {
		this.tpl_name = tpl_name;
	}

	public void setTask_score(String task_score) {
		this.task_score = task_score;
	}

	public void setAnswer_id(String answer_id) {
		this.answer_id = answer_id;
	}

	public void setAnswer_name(String answer_name) {
		this.answer_name = answer_name;
	}

	public void setAnswer_ip(String answer_ip) {
		this.answer_ip = answer_ip;
	}

	public void setArray(String array) {
		this.array = array;
	}
	
}
