package com.yondervision.mi.form;

public class AppApi70003Form extends AppApiCommonForm {
	/** 待查询的分类ID列表（以逗号分隔） */	
	private String classifications;

	/**
	 * @return the classifications
	 */
	public String getClassifications() {
		return classifications;
	}

	/**
	 * @param classifications the classifications to set
	 */
	public void setClassifications(String classifications) {
		this.classifications = classifications;
	}
}
