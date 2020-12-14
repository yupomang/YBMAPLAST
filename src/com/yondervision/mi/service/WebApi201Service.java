/**
 * 
 */
package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.dto.CMi301;
import com.yondervision.mi.dto.CMi302;
import com.yondervision.mi.dto.CMi303;
import com.yondervision.mi.dto.CMi304;
import com.yondervision.mi.dto.CMi305;
import com.yondervision.mi.dto.CMi306;
import com.yondervision.mi.dto.CMi308;
import com.yondervision.mi.dto.Mi301;
import com.yondervision.mi.dto.Mi302;
import com.yondervision.mi.dto.Mi303;
import com.yondervision.mi.dto.Mi306;
import com.yondervision.mi.dto.Mi308;
import com.yondervision.mi.form.WebApi20103_storForm;
import com.yondervision.mi.form.WebApi20104_storForm;
import com.yondervision.mi.form.WebApi20112_storForm;
import com.yondervision.mi.form.WebApi20113_storForm;
import com.yondervision.mi.form.WebApi20114_storForm;
import com.yondervision.mi.form.WebApi20122_storForm;
import com.yondervision.mi.form.WebApi20124_deleteForm;
import com.yondervision.mi.form.WebApi20124_storForm;
import com.yondervision.mi.form.WebApi20125_queryForm;
import com.yondervision.mi.form.WebApi20125_saveForm;
import com.yondervision.mi.form.WebApi20126_queryForm;
import com.yondervision.mi.form.WebApi20126_saveForm;
import com.yondervision.mi.form.WebApiCommonForm;
import com.yondervision.mi.result.Page20101Result;
import com.yondervision.mi.result.WebApi20111Result;
import com.yondervision.mi.result.WebApi20125Result;

/**
 * @author LinXiaolong
 * 
 */
public interface WebApi201Service {

	/**
	 * 业务咨询配置页面初始化
	 * 
	 * @param form
	 *            form中包含centerId
	 * @return 业务类型list、业务咨询子项图标list
	 * @throws Exception
	 */
	Page20101Result page20101(WebApiCommonForm form) throws Exception;

	/**
	 * 公共条件配置页面初始化
	 * 
	 * @param form
	 *            form中包含consultItemId
	 * @return 公共条件项目list
	 * @throws Exception
	 */
	List<Mi303> page20111(CMi303 form) throws Exception;

	/**
	 * 业务咨询向导配置（步骤）页面初始化
	 * 
	 * @param form
	 *            form中包含consultSubItemId
	 * @return 精微咨询向导步骤list
	 * @throws Exception
	 */
	List<Mi306> page20121(CMi306 form) throws Exception;

	/**
	 * 业务咨询内容配置页面初始化
	 * 
	 * @param form
	 *            form中包含consultItemId、conditionItemGroupId（可空）
	 * @return 公共条件项目、公共条件分组、公共条件内容下拉框数据
	 */
	// List<Page20122Result> page20122(Page20122Form form) throws Exception;

	/**
	 * 查询业务咨询项目
	 * 
	 * @param form
	 *            form中包含centerId、consultType
	 * @return 业务咨询项目信息list
	 * @throws Exception
	 */
	List<Mi301> webapi20101(CMi301 form) throws Exception;

	/**
	 * 查询业务咨询子项
	 * 
	 * @param form
	 *            form中包含consultItemId
	 * @return 业务咨询子项信息list
	 * @throws Exception
	 */
	List<Mi302> webapi20102(CMi302 form) throws Exception;

	/**
	 * 添加业务咨询项
	 * 
	 * @param form
	 *            form中包含centerId、consultType、orderNo、itemName、conditionTitle（可空）
	 * @return 业务咨询项目ID
	 * @throws Exception
	 */
	String webapi20103_add(CMi301 form) throws Exception;

	/**
	 * 修改业务咨询项
	 * 
	 * @param form
	 *            form中包含consultItemId、centerId（可空）、consultType（可空）、orderNo（可空）、itemName
	 *            （可空）、conditionTitle（可空）
	 * @throws Exception
	 */
	void webapi20103_edit(CMi301 form) throws Exception;

	/**
	 * 删除业务咨询项
	 * 
	 * @param form
	 *            form中包含consultItemId
	 * @throws Exception
	 */
	void webapi20103_delete(CMi301 form) throws Exception;

	/**
	 * 业务咨询项排序
	 * 
	 * @param form
	 *            form中包含centerId、consultType、sourceOrderNo、targetOrderNo
	 * @throws Exception
	 */
	void webapi20103_sort(WebApi20103_storForm form) throws Exception;

	/**
	 * 添加业务咨询子项
	 * 
	 * @param form
	 *            form中包含consultItemId、orderNo、subitemName、iconId
	 * @throws Exception
	 */
	String webapi20104_add(CMi302 form) throws Exception;

	/**
	 * 修改业务咨询子项
	 * 
	 * @param form
	 *            form加包含consultSubItemId、consultItemId（可空）、orderNo（可空）、subitemName
	 *            （可空）、iconId（可空）
	 * @throws Exception
	 */
	void webapi20104_edit(CMi302 form) throws Exception;

	/**
	 * 删除业务咨询子项
	 * 
	 * @param form
	 *            form中包含consultSubItemId
	 * @throws Exception
	 */
	void webapi20104_delete(CMi302 form) throws Exception;

	/**
	 * 业务咨询子项排序
	 * 
	 * @param form
	 *            form中包含consultItemId、sourceOrderNo、targetOrderNo
	 * @throws Exception
	 */
	void webapi20104_sort(WebApi20104_storForm form) throws Exception;

	/**
	 * 查询公共条件分组和公共条件内容
	 * 
	 * @param form
	 *            form中包含conditionItemId、consultItemId
	 * @return 公共条件分组和公共条件内容数据
	 * @throws Exception
	 */
	List<WebApi20111Result> webapi20111(CMi304 form) throws Exception;

	/**
	 * 添加公共条件项目
	 * 
	 * @param form
	 *            form中包含consultItemId、itemNo、conditionItemName
	 * @return 公共条件项目ID
	 * @throws Exception
	 */
	String webapi20112_add(CMi303 form) throws Exception;

	/**
	 * 修改公共条件项目
	 * 
	 * @param form
	 *            form中包含conditionItemId、consultItemId（可空）、itemNo（可空）、conditionItemName
	 *            （可空）
	 * @throws Exception
	 */
	void webapi20112_edit(CMi303 form) throws Exception;

	/**
	 * 删除公共条件项目
	 * 
	 * @param form
	 *            form中包含conditionItemId
	 */
	void webapi20112_delete(CMi303 form) throws Exception;

	/**
	 * 公共条件项目排序
	 * 
	 * @param form
	 *            form中包含consultItemId、sourceOrderNo、targetOrderNo
	 * @throws Exception
	 */
	void webapi20112_sort(WebApi20112_storForm form) throws Exception;

	/**
	 * 添加公共条件分组
	 * 
	 * @param form
	 *            form中包含consultItemId、conditionItemId、noOreder、groupName
	 * @return 公共条件分组ID
	 * @throws Exception
	 */
	String webapi20113_add(CMi304 form) throws Exception;

	/**
	 * 修改公共条件分组
	 * 
	 * @param form
	 *            form中包含conditionGroupId、consultItemId（可空）、conditionItemId（可空）、noOreder
	 *            （可空）、groupName（可空）
	 * @throws Exception
	 */
	void webapi20113_edit(CMi304 form) throws Exception;

	/**
	 * 删除公共条件分组
	 * 
	 * @param form
	 *            form中包含conditionGroupId
	 * @throws Exception
	 */
	void webapi20113_delete(CMi304 form) throws Exception;

	/**
	 * 公共条件分组排序
	 * 
	 * @param form
	 *            form中包含conditionItemId、sourceOrderNo、targetOrderNo
	 * @throws Exception
	 */
	void webapi20113_sort(WebApi20113_storForm form) throws Exception;

	/**
	 * 添加公共条件内容
	 * 
	 * @param form
	 *            form中包含consultItemId、conditionGroupId、noOreder、conditionDetail
	 * @return 公共条件内容ID
	 * @throws Exception
	 */
	String webapi20114_add(CMi305 form) throws Exception;

	/**
	 * 修改公共条件内容
	 * 
	 * @param form
	 *            form中包含conditionId、consultItemId（可空）、conditionGroupId（可空）、noOreder
	 *            （可空）、conditionDetail（可空）
	 * @throws Exception
	 */
	void webapi20114_edit(CMi305 form) throws Exception;

	/**
	 * 删除公共条件内容
	 * 
	 * @param form
	 *            form中包含conditionId
	 * @throws Exception
	 */
	void webapi20114_delete(CMi305 form) throws Exception;

	/**
	 * 公共条件内容排序
	 * 
	 * @param form
	 *            form中包含conditionGroupId、sourceOrderNo、targetOrderNo
	 * @throws Exception
	 */
	void webapi20114_sort(WebApi20114_storForm form) throws Exception;

	/**
	 * 查询业务咨询向导内容
	 * 
	 * @param form
	 *            form中包含stepId
	 * @return 业务咨询向导内容及其所有子内容
	 * @throws Exception
	 */
	List<CMi308> webapi20121(CMi308 form) throws Exception;

	/**
	 * 添加业务咨询向导步骤
	 * 
	 * @param form
	 *            form中包含consultSubItemId、step、stepName
	 * @return 向导步骤ID
	 * @throws Exception
	 */
	String webapi20122_add(CMi306 form) throws Exception;

	/**
	 * 修改业务咨询向导步骤
	 * 
	 * @param form
	 *            form中包含stepId、consultSubItemId（可空）、step（可空）、stepName（可空）
	 */
	void webapi20122_edit(CMi306 form) throws Exception;

	/**
	 * 删除业务咨询向导步骤
	 * 
	 * @param form
	 *            form中包含stepId
	 * @throws Exception
	 */
	void webapi20122_delete(CMi306 form) throws Exception;

	/**
	 * 业务咨询向导步骤排序
	 * 
	 * @param form
	 *            form包含consultSubItemId、sourceOrderNo、targetOrderNo
	 * @throws Exception
	 */
	void webapi20122_sort(WebApi20122_storForm form) throws Exception;

	/**
	 * 业务咨询向导内容排序
	 * 
	 * @param form
	 *            form中包含stepId、parentId、sourceOrderNo、targetOrderNo
	 * @throws Exception
	 */
	void webapi20124_sort(WebApi20124_storForm form) throws Exception;

	/**
	 * 添加业务咨询向导内容
	 * 
	 * @param form
	 *            form中包含stepId、parentId、orderNo、detail
	 * @return 返回结果中包含consultId
	 * @throws Exception
	 */
	Mi308 webapi20124_add(CMi308 form) throws Exception;

	/**
	 * 修改业务咨询向导内容
	 * 
	 * @param form
	 *            form中包含consultId、stepId（可空）、parentId（可空）、orderNo（可空）、detail（可空）
	 * @throws Exception
	 */
	void webapi20124_edit(CMi308 form) throws Exception;

	/**
	 * 删除业务咨询向导内容
	 * 
	 * @param form
	 *            form中包含listConsultId
	 * @throws Exception
	 */
	void webapi20124_delete(WebApi20124_deleteForm form) throws Exception;

	/**
	 * 查询条件组合对应的向导内容ID
	 * 
	 * @param form
	 *            form中包含listConditionId、consultItemId、stepId
	 * @return 满足条件的咨询内容ID、是否可编辑和其必选的公共条件项目ID
	 * @throws Exception
	 */
	List<WebApi20125Result> webapi20125_query(WebApi20125_queryForm form)
			throws Exception;

	/**
	 * 条件组合与向导内容映射保存
	 * 
	 * @param form
	 *            form中包含listConditionId、listConsultId、consultItemId、consultSubItemId
	 *            、stepId
	 * @throws Exception
	 */
	void webapi20125_save(WebApi20125_saveForm form) throws Exception;

	/**
	 * 查询公共的向导内容ID
	 * 
	 * @param form
	 *            form中包含consultItemId、stepId
	 * @return 咨询内容ID组
	 * @throws Exception
	 */
	List<String> webapi20126_query(WebApi20126_queryForm form) throws Exception;

	/**
	 * 条件组合与向导内容映射保存
	 * 
	 * @param form
	 *            form中包含listConditionId、listConsultId、consultItemId、consultSubItemId
	 *            、stepId
	 * @throws Exception
	 */
	void webapi20126_save(WebApi20126_saveForm form) throws Exception;

}
