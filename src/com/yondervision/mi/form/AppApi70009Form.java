package com.yondervision.mi.form;

public class AppApi70009Form extends AppApiCommonForm {
	/** 待查询的展示项 （适用于传递的itemid为父级的itemid）*/	
	private String parentViewItemId = "";
	private String curChildViewItemId = "";
	private String keyword = "";
	
	/** 页码 */				
	private String pagenum = "";
	/** 行数 */				
	private String pagerows = "";
	
	public String getParentViewItemId() {
		return parentViewItemId;
	}
	public void setParentViewItemId(String parentViewItemId) {
		this.parentViewItemId = parentViewItemId;
	}
	public String getCurChildViewItemId() {
		return curChildViewItemId;
	}
	public void setCurChildViewItemId(String curChildViewItemId) {
		this.curChildViewItemId = curChildViewItemId;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getPagenum() {
		return pagenum;
	}
	public void setPagenum(String pagenum) {
		this.pagenum = pagenum;
	}
	public String getPagerows() {
		return pagerows;
	}
	public void setPagerows(String pagerows) {
		this.pagerows = pagerows;
	}

}
