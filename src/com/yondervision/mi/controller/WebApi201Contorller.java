/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.controller
 * 文件名：     WebApi201Contorller.java
 * 创建日期：2013-10-15
 */
package com.yondervision.mi.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
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
import com.yondervision.mi.form.Page20122Form;
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
import com.yondervision.mi.service.WebApi201Service;
import com.yondervision.mi.util.CommonUtil;

/**
 * @author LinXiaolong
 * 
 */
@Controller
public class WebApi201Contorller {
	@Autowired
	private WebApi201Service webApi201Service;

	/**
	 * @return the webApi201Service
	 */
	public WebApi201Service getWebApi201Service() {
		return webApi201Service;
	}

	/**
	 * @param webApi201Service
	 *            the webApi201Service to set
	 */
	public void setWebApi201Service(WebApi201Service webApi201Service) {
		this.webApi201Service = webApi201Service;
	}

	/* /****************页面初始化***************** */
	/**
	 * 业务咨询配置页面初始化
	 * 
	 * @param form
	 *            centerId
	 * @param modelMap
	 * @return 业务咨询配置页面
	 * @throws Exception
	 */
	@RequestMapping("/page20101.html")
	public String page20101(WebApiCommonForm form, ModelMap modelMap)
			throws Exception {
		Logger log = LoggerUtil.getLogger();
		String businName = "业务咨询配置页面初始化";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			Page20101Result listPage20101Result = this.getWebApi201Service()
					.page20101(form);

			String resultJson = CommonUtil
					.getJSONStringParams(listPage20101Result);
			modelMap.put("result", resultJson);
			log.debug(DEBUG.SHOW_RESULT.getLogText(resultJson));

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}

		return "page201/page20101";
	}

	/**
	 * 公共条件配置页面初始化
	 * 
	 * @param form
	 *            consultItemId
	 * @param modelMap
	 * @return 公共条件询配置页面
	 * @throws Exception
	 */
	@RequestMapping("/page20111.html")
	public String page20111(CMi303 form, ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();
		String businName = "公共条件配置页面初始化";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			List<Mi303> listMi303 = this.getWebApi201Service().page20111(form);

			String resultJson = CommonUtil.getJSONStringParams(listMi303);
			modelMap.put("rows", resultJson);
			log.debug(DEBUG.SHOW_RESULT.getLogText(resultJson));

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}

		return "page201/page20111";
	}

	/**
	 * 业务咨询向导配置（步骤）页面初始化
	 * 
	 * @param form
	 *            consultSubItemId
	 * @param modelMap
	 * @return 公共条件询配置页面
	 * @throws Exception
	 */
	@RequestMapping("/page20121.html")
	public String page20121(CMi306 form, ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();
		String businName = "业务咨询向导配置（步骤）页面初始化";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			List<Mi306> result = this.getWebApi201Service().page20121(form);

			String resultJson = CommonUtil.getJSONStringParams(result);
			modelMap.put("rows", resultJson);
			log.debug(DEBUG.SHOW_RESULT.getLogText(resultJson));

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}

		return "page201/page20121";
	}

	/**
	 * 业务咨询内容配置页面初始化
	 * 
	 * @param form
	 *            consultItemId、conditionItemGroupId（可空）
	 * @param modelMap
	 * @return 业务咨询内容配置页面
	 * @throws Exception
	 */
	@RequestMapping("/page20122.html")
	public String page20122(Page20122Form form, ModelMap modelMap)
			throws Exception {
		Logger log = LoggerUtil.getLogger();
		String businName = "业务咨询内容配置页面初始化";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			// List<Page20122Result> result = this.getWebApi201Service()
			// .page20122(form);
			//
			// String resultJson = CommonUtil.getJSONStringParams(result);
			// modelMap.put("result", resultJson);
			// log.debug(DEBUG.SHOW_RESULT.getLogText(resultJson));
			//
			// modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			// modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}

		return "page201/page20122";
	}

	/* /****************业务咨询（子）项查询***************** */
	/**
	 * 查询业务咨询项目
	 * 
	 * @param form
	 *            centerId、consultType
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20101.json")
	public String webapi20101(CMi301 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "查询业务咨询项目";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			List<Mi301> listMi301 = this.getWebApi201Service()
					.webapi20101(form);

			modelMap.put("rows", listMi301);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 查询业务咨询子项
	 * 
	 * @param form
	 *            consultItemId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20102.json")
	public String webapi20102(CMi302 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "查询业务咨询子项";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			List<Mi302> listMi302 = this.getWebApi201Service()
					.webapi20102(form);

			modelMap.put("rows", listMi302);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/* /****************业务咨询项更新***************** */
	/**
	 * 添加业务咨询项
	 * 
	 * @param form
	 *            centerId、consultType、orderNo、itemName、conditionTitle（可空）
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20103.json", params = "method=add")
	public String webapi20103_add(CMi301 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "添加业务咨询项";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			String consultItemId = this.getWebApi201Service().webapi20103_add(
					form);

			modelMap.put("consultItemId", consultItemId);
			log.debug(DEBUG.SHOW_RESULT.getLogText("consultItemId="
					+ consultItemId));

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 修改业务咨询项
	 * 
	 * @param form
	 *            consultItemId、centerId（可空）、consultType（可空）、orderNo（可空）、itemName
	 *            （可空）、conditionTitle（可空）
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20103.json", params = "method=edit")
	public String webapi20103_edit(CMi301 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "修改业务咨询项";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi201Service().webapi20103_edit(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 删除业务咨询项
	 * 
	 * @param form
	 *            consultItemId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20103.json", params = "method=delete")
	public String webapi20103_delete(CMi301 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "删除业务咨询项";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi201Service().webapi20103_delete(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 业务咨询项排序
	 * 
	 * @param form
	 *            centerId、consultType、sourceOrderNo、targetOrderNo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20103.json", params = "method=sort")
	public String webapi20103_sort(WebApi20103_storForm form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "业务咨询项排序";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi201Service().webapi20103_sort(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/* /****************业务咨询子项更新***************** */
	/**
	 * 添加业务咨询子项
	 * 
	 * @param form
	 *            consultItemId、orderNo、subitemName、iconId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20104.json", params = "method=add")
	public String webapi20104_add(CMi302 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "添加业务咨询子项";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			String consultSubItemId = this.getWebApi201Service()
					.webapi20104_add(form);

			modelMap.put("consultSubItemId", consultSubItemId);
			log.debug(DEBUG.SHOW_RESULT.getLogText("consultSubItemId="
					+ consultSubItemId));

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 修改业务咨询子项
	 * 
	 * @param form
	 *            consultSubItemId、consultItemId（可空）、orderNo（可空）、subitemName（可空）、
	 *            iconId（可空）
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20104.json", params = "method=edit")
	public String webapi20104_edit(CMi302 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "修改业务咨询子项";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi201Service().webapi20104_edit(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 删除业务咨询子项
	 * 
	 * @param form
	 *            consultSubItemId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20104.json", params = "method=delete")
	public String webapi20104_delete(CMi302 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "删除业务咨询子项";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi201Service().webapi20104_delete(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 业务咨询子项排序
	 * 
	 * @param form
	 *            consultItemId、sourceOrderNo、targetOrderNo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20104.json", params = "method=sort")
	public String webapi20104_sort(WebApi20104_storForm form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "业务咨询子项排序";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi201Service().webapi20104_sort(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/* /****************查询公共条件分组和公共条件内容***************** */
	/**
	 * 查询公共条件分组和公共条件内容
	 * 
	 * @param form
	 *            conditionItemId（可空）、consultItemId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20111.json")
	public String webapi20111(CMi304 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "查询公共条件分组和公共条件内容";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			List<WebApi20111Result> result = this.getWebApi201Service()
					.webapi20111(form);

			modelMap.put("rows", result);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			// e.printStackTrace();
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/* /****************公共条件项目更新***************** */
	/**
	 * 添加公共条件项目
	 * 
	 * @param form
	 *            consultItemId、itemNo、conditionItemName
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20112.json", params = "method=add")
	public String webapi20112_add(CMi303 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "添加公共条件项目";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			String conditionItemId = this.getWebApi201Service()
					.webapi20112_add(form);

			modelMap.put("conditionItemId", conditionItemId);
			log.debug(DEBUG.SHOW_RESULT.getLogText("conditionItemId="
					+ conditionItemId));

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 修改公共条件项目
	 * 
	 * @param form
	 *            conditionItemId、consultItemId（可空）、itemNo（可空）、conditionItemName（
	 *            可空）
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20112.json", params = "method=edit")
	public String webapi20112_edit(CMi303 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "修改公共条件项目";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi201Service().webapi20112_edit(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 删除公共条件项目
	 * 
	 * @param form
	 *            conditionItemId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20112.json", params = "method=delete")
	public String webapi20112_delete(CMi303 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "删除公共条件项目";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi201Service().webapi20112_delete(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 公共条件项目排序
	 * 
	 * @param form
	 *            consultItemId、sourceOrderNo、targetOrderNo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20112.json", params = "method=sort")
	public String webapi20112_sort(WebApi20112_storForm form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "公共条件项目排序";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi201Service().webapi20112_sort(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/* /****************公共条件分组更新***************** */
	/**
	 * 添加公共条件分组
	 * 
	 * @param form
	 *            consultItemId、conditionItemId、noOreder、groupName
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20113.json", params = "method=add")
	public String webapi20113_add(CMi304 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "添加公共条件分组";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			String conditionGroupId = this.getWebApi201Service()
					.webapi20113_add(form);
			modelMap.put("conditionGroupId", conditionGroupId);
			log.debug(DEBUG.SHOW_RESULT.getLogText("conditionGroupId="
					+ conditionGroupId));

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 修改公共条件分组
	 * 
	 * @param form
	 *            conditionGroupId、consultItemId（可空）、conditionItemId（可空）、noOreder
	 *            （可空）、groupName（可空）
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20113.json", params = "method=edit")
	public String webapi20113_edit(CMi304 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "修改公共条件分组";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi201Service().webapi20113_edit(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 删除公共条件分组
	 * 
	 * @param form
	 *            conditionGroupId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20113.json", params = "method=delete")
	public String webapi20113_delete(CMi304 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "删除公共条件分组";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi201Service().webapi20113_delete(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 公共条件分组排序
	 * 
	 * @param form
	 *            conditionItemId、sourceOrderNo、targetOrderNo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20113.json", params = "method=sort")
	public String webapi20113_sort(WebApi20113_storForm form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "公共条件分组排序";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi201Service().webapi20113_sort(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/* /****************公共条件内容更新***************** */
	/**
	 * 添加公共条件内容
	 * 
	 * @param form
	 *            consultItemId、conditionGroupId、noOreder、conditionDetail
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20114.json", params = "method=add")
	public String webapi20114_add(CMi305 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "添加公共条件内容";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			String conditionId = this.getWebApi201Service().webapi20114_add(
					form);
			modelMap.put("conditionId", conditionId);
			log.debug(DEBUG.SHOW_RESULT
					.getLogText("conditionId=" + conditionId));

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 修改公共条件内容
	 * 
	 * @param form
	 *            conditionId、consultItemId（可空）、conditionGroupId（可空）、noOreder（可空）
	 *            、conditionDetail（可空）
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20114.json", params = "method=edit")
	public String webapi20114_edit(CMi305 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "修改公共条件内容";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi201Service().webapi20114_edit(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 删除公共条件内容
	 * 
	 * @param form
	 *            conditionId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20114.json", params = "method=delete")
	public String webapi20114_delete(CMi305 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "删除公共条件内容";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi201Service().webapi20114_delete(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 公共条件内容排序
	 * 
	 * @param form
	 *            conditionGroupId、sourceOrderNo、targetOrderNo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20114.json", params = "method=sort")
	public String webapi20114_sort(WebApi20114_storForm form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "公共条件内容排序";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi201Service().webapi20114_sort(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/* /****************查询业务咨询向导内容***************** */
	/**
	 * 查询业务咨询向导内容
	 * 
	 * @param form
	 *            stepId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20121.json")
	public String webapi20121(CMi308 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "查询业务咨询向导内容";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			List<CMi308> result = this.getWebApi201Service().webapi20121(form);

			modelMap.put("rows", result);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/* /****************业务咨询向导步骤更新***************** */
	/**
	 * 添加业务咨询向导步骤
	 * 
	 * @param form
	 *            consultSubItemId、step、stepName
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20122.json", params = "method=add")
	public String webapi20122_add(CMi306 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "添加业务咨询向导步骤";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			String stepId = this.getWebApi201Service().webapi20122_add(form);
			modelMap.put("stepId", stepId);
			log.debug(DEBUG.SHOW_RESULT.getLogText("stepId=" + stepId));

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 修改业务咨询向导步骤
	 * 
	 * @param form
	 *            stepId、consultSubItemId（可空）、step（可空）、stepName（可空）
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20122.json", params = "method=edit")
	public String webapi20122_edit(CMi306 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "修改业务咨询向导步骤";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi201Service().webapi20122_edit(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 删除业务咨询向导步骤
	 * 
	 * @param form
	 *            stepId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20122.json", params = "method=delete")
	public String webapi20122_delete(CMi306 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "删除业务咨询向导步骤";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi201Service().webapi20122_delete(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 业务咨询向导步骤排序
	 * 
	 * @param form
	 *            consultSubItemId、sourceOrderNo、targetOrderNo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20122.json", params = "method=sort")
	public String webapi20122_sort(WebApi20122_storForm form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "业务咨询向导步骤排序";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi201Service().webapi20122_sort(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/* /****************业务咨询向导内容更新***************** */
	/**
	 * 添加业务咨询向导内容
	 * 
	 * @param form
	 *            stepId、parentId、orderNo、detail
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20124.json", params = "method=add")
	public String webapi20124_add(CMi308 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "添加业务咨询向导内容";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			Mi308 mi308 = this.getWebApi201Service().webapi20124_add(form);

			modelMap.put("consultId", mi308.getConsultId());
			log.debug(DEBUG.SHOW_RESULT.getLogText("consultId="
					+ mi308.getConsultId()));

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 修改业务咨询向导内容
	 * 
	 * @param form
	 *            consultId、stepId（可空）、parentId（可空）、orderNo（可空）、detail（可空）
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20124.json", params = "method=edit")
	public String webapi20124_edit(CMi308 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "修改业务咨询向导内容";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi201Service().webapi20124_edit(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 删除业务咨询向导内容
	 * 
	 * @param form
	 *            listConsultId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20124.json", params = "method=delete")
	public String webapi20124_delete(WebApi20124_deleteForm form,
			ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "删除业务咨询向导内容";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi201Service().webapi20124_delete(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 业务咨询向导内容排序
	 * 
	 * @param form
	 *            stepId、parentId、sourceOrderNo、targetOrderNo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20124.json", params = "method=sort")
	public String webapi20124_sort(WebApi20124_storForm form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "业务咨询向导内容排序";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi201Service().webapi20124_sort(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 查询条件组合对应的向导内容ID
	 * 
	 * @param form
	 *            listConditionId、consultItemId、stepId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20125.json", params = "method=query")
	public String webapi20125_sort(WebApi20125_queryForm form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "查询条件组合对应的向导内容ID";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			List<WebApi20125Result> result = this.getWebApi201Service()
					.webapi20125_query(form);

			modelMap.put("result", result);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 条件组合与向导内容映射保存
	 * 
	 * @param form
	 *            listConditionId、listConsultId、consultItemId、consultSubItemId、stepId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20125.json", params = "method=save")
	public String webapi20125_save(WebApi20125_saveForm form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "条件组合与向导内容映射保存";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi201Service().webapi20125_save(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 查询公共的向导内容ID
	 * 
	 * @param form
	 *            consultItemId、stepId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20126.json", params = "method=query")
	public String webapi20126_sort(WebApi20126_queryForm form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "查询公共的向导内容ID";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			List<String> result = this.getWebApi201Service()
					.webapi20126_query(form);

			modelMap.put("result", result);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}

	/**
	 * 条件组合与向导内容映射保存
	 * 
	 * @param form
	 *            listConditionId、listConsultId、consultItemId、consultSubItemId、stepId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "webapi20126.json", params = "method=save")
	public String webapi20126_save(WebApi20126_saveForm form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "条件组合与向导内容映射保存";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi201Service().webapi20126_save(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}
}
