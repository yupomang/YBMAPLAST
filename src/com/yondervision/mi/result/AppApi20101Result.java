/**
 * 
 */
package com.yondervision.mi.result;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LinXiaolong
 *
 */
public class AppApi20101Result {
	/** 业务咨询项目名称 **/
	private String consultitem;
	/** 业务咨询项目ID **/
	private String consultitemid;
	/** 业务咨询公共条件标题 **/
	private String conditionTitle;
	/** 界面调用向导分类：guide   (10 分步向导，20 公共条件) **/
	private String guide;
	private List<AppApi20101Result01> consultsubitem = new ArrayList<AppApi20101Result01>();

	public String getConsultitem() {
		return consultitem;
	}

	public void setConsultitem(String consultitem) {
		this.consultitem = consultitem;
	}

	public String getConsultitemid() {
		return consultitemid;
	}

	public void setConsultitemid(String consultitemid) {
		this.consultitemid = consultitemid;
	}

	/**
	 * @return the conditionTitle
	 */
	public String getConditionTitle() {
		return conditionTitle;
	}

	/**
	 * @param conditionTitle the conditionTitle to set
	 */
	public void setConditionTitle(String conditionTitle) {
		this.conditionTitle = conditionTitle;
	}

	public String getGuide() {
		return guide;
	}

	public void setGuide(String guide) {
		this.guide = guide;
	}

	public List<AppApi20101Result01> getConsultsubitem() {
		return consultsubitem;
	}

	public void setConsultsubitem(List<AppApi20101Result01> consultsubitem) {
		this.consultsubitem = consultsubitem;
	}
}
