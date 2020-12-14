package com.yondervision.mi.form;

public class AppApi70110Form extends AppApiCommonForm {			
	private String newspapercolumns = "";//订阅栏目范围，以逗号分隔
	private String newspapertimes = "";//报刊期次ID
	
	public String getNewspapercolumns() {
		return newspapercolumns;
	}

	public void setNewspapercolumns(String newspapercolumns) {
		this.newspapercolumns = newspapercolumns;
	}

	public String getNewspapertimes() {
		return newspapertimes;
	}

	public void setNewspapertimes(String newspapertimes) {
		this.newspapertimes = newspapertimes;
	}
}
