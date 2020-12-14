package com.yondervision.mi.form;

public class AppApi701CommonForm extends AppApiCommonForm {
	private String newspapertimes = "";//报刊期次ID
	private String newspaperforum = "";//版块ID
	
	public String getNewspaperforum() {
		return newspaperforum;
	}
	public String getNewspapertimes() {
		return newspapertimes;
	}
	public void setNewspapertimes(String newspapertimes) {
		this.newspapertimes = newspapertimes;
	}
	public void setNewspaperforum(String newspaperforum) {
		this.newspaperforum = newspaperforum;
	}
}
