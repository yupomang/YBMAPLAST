package com.yondervision.mi.result;

import java.util.ArrayList;
import java.util.List;


public class AppApi30202Result {
	private String topictypeid;
	private String topictypename;
	/**
	 * disable是否关注0未关注，1关注
	 */
	private String disable;
	public String getTopictypeid() {
		return topictypeid;
	}
	public void setTopictypeid(String topictypeid) {
		this.topictypeid = topictypeid;
	}
	public String getTopictypename() {
		return topictypename;
	}
	public void setTopictypename(String topictypename) {
		this.topictypename = topictypename;
	}
	public String getDisable() {
		return disable;
	}
	public void setDisable(String disable) {
		this.disable = disable;
	}
}
