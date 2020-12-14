/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.result
 * 文件名：     AppApi20103Result.java
 * 创建日期：2013-10-22
 */
package com.yondervision.mi.result;

import java.util.List;

/**
 * @author LinXiaolong
 *
 */
public class AppApi20103Result {
	/** 步骤向导名称 **/
	private String stepname;
	/** 步骤向导ID **/
	private String stepid;
	/** 向导内容数据 **/
	private List<AppApi20103Result_guiderelation> guiderelation;

	/**
	 * @return the stepname
	 */
	public String getStepname() {
		return stepname;
	}

	/**
	 * @param stepname
	 *            the stepname to set
	 */
	public void setStepname(String stepname) {
		this.stepname = stepname;
	}

	/**
	 * @return the stepid
	 */
	public String getStepid() {
		return stepid;
	}

	/**
	 * @param stepid
	 *            the stepid to set
	 */
	public void setStepid(String stepid) {
		this.stepid = stepid;
	}

	/**
	 * @return the guiderelation
	 */
	public List<AppApi20103Result_guiderelation> getGuiderelation() {
		return guiderelation;
	}

	/**
	 * @param guiderelation
	 *            the guiderelation to set
	 */
	public void setGuiderelation(
			List<AppApi20103Result_guiderelation> guiderelation) {
		this.guiderelation = guiderelation;
	}
}
