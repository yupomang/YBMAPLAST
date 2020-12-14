/**
 * 
 */
package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.form.AppApi20101Form;
import com.yondervision.mi.form.AppApi20102Form;
import com.yondervision.mi.form.AppApi20103Form;
import com.yondervision.mi.result.AppApi20101Result;
import com.yondervision.mi.result.AppApi20102Result;
import com.yondervision.mi.result.AppApi20103Result;

/**
 * @author LinXiaolong
 * 
 */
public interface AppApi201Service {

	/**
	 * 业务咨询（子）项查询
	 * 
	 * @param form
	 *            form中包含centerId、bussinesstype
	 * @return 业务咨询项和业务咨询子项数据
	 * @throws Exception
	 */
	public List<AppApi20101Result> appApi20101(AppApi20101Form form)
			throws Exception;

	/**
	 * 公共条件查询
	 * 
	 * @param form
	 *            form中包含consultitemid
	 * @return 公共条件项目、分组、内容list
	 * @throws Exception
	 */
	public List<AppApi20102Result> appApi20102(AppApi20102Form form)
			throws Exception;

	/**
	 * 向导步骤（内容）查询
	 * 
	 * @param form
	 *            form中包含consultitemid、titleid（业务咨询子项ID）、conditionid1（可空）、conditionid2
	 *            （可空）、conditionid3（可空）、conditionid4（可空）、conditionid5（可空）、
	 *            conditionid6（可空）、conditionid7（可空）、conditionid8（可空）
	 * @return 业务咨询向导步骤和内容list
	 * @throws Exception
	 */
	public List<AppApi20103Result> appApi20103(AppApi20103Form form)
			throws Exception;
}
