/**
 * 
 */
package com.yondervision.mi.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE;
import com.yondervision.mi.common.PermissionContext;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi304DAO;
import com.yondervision.mi.dao.CMi308DAO;
import com.yondervision.mi.dao.CMi311DAO;
import com.yondervision.mi.dao.CMi312DAO;
import com.yondervision.mi.dao.Mi301DAO;
import com.yondervision.mi.dao.Mi302DAO;
import com.yondervision.mi.dao.Mi303DAO;
import com.yondervision.mi.dao.Mi305DAO;
import com.yondervision.mi.dao.Mi306DAO;
import com.yondervision.mi.dto.CMi301;
import com.yondervision.mi.dto.CMi302;
import com.yondervision.mi.dto.CMi303;
import com.yondervision.mi.dto.CMi304;
import com.yondervision.mi.dto.CMi305;
import com.yondervision.mi.dto.CMi306;
import com.yondervision.mi.dto.CMi308;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi301;
import com.yondervision.mi.dto.Mi301Example;
import com.yondervision.mi.dto.Mi302;
import com.yondervision.mi.dto.Mi302Example;
import com.yondervision.mi.dto.Mi303;
import com.yondervision.mi.dto.Mi303Example;
import com.yondervision.mi.dto.Mi304;
import com.yondervision.mi.dto.Mi304Example;
import com.yondervision.mi.dto.Mi305;
import com.yondervision.mi.dto.Mi305Example;
import com.yondervision.mi.dto.Mi306;
import com.yondervision.mi.dto.Mi306Example;
import com.yondervision.mi.dto.Mi308;
import com.yondervision.mi.dto.Mi308Example;
import com.yondervision.mi.dto.Mi311;
import com.yondervision.mi.dto.Mi311Example;
import com.yondervision.mi.dto.Mi312;
import com.yondervision.mi.dto.Mi312Example;
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
import com.yondervision.mi.result.Page20101ResultSubitemIcon;
import com.yondervision.mi.result.WebApi20111Result;
import com.yondervision.mi.result.WebApi20125Result;
import com.yondervision.mi.service.CodeListApi001Service;
import com.yondervision.mi.service.WebApi201Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;

/**
 * @author LinXiaolong
 * 
 */
public class WebApi201ServiceImpl implements WebApi201Service {

	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	
	@Autowired
	private CodeListApi001Service codeListApi001Service;
	private Mi301DAO mi301Dao;
	private Mi302DAO mi302Dao;
	private Mi303DAO mi303Dao;
	private CMi304DAO cMi304Dao;
	private Mi305DAO mi305Dao;
	private Mi306DAO mi306Dao;
	private CMi308DAO cMi308Dao;
	private CMi311DAO cMi311Dao;
	private CMi312DAO cMi312Dao;

	/**
	 * @return the codeListApi001Service
	 */
	public CodeListApi001Service getCodeListApi001Service() {
		return codeListApi001Service;
	}

	/**
	 * @param codeListApi001Service
	 *            the codeListApi001Service to set
	 */
	public void setCodeListApi001Service(
			CodeListApi001Service codeListApi001Service) {
		this.codeListApi001Service = codeListApi001Service;
	}

	/**
	 * @return the mi301Dao
	 */
	public Mi301DAO getMi301Dao() {
		return mi301Dao;
	}

	/**
	 * @param mi301Dao
	 *            the mi301Dao to set
	 */
	public void setMi301Dao(Mi301DAO mi301Dao) {
		this.mi301Dao = mi301Dao;
	}

	/**
	 * @return the mi302Dao
	 */
	public Mi302DAO getMi302Dao() {
		return mi302Dao;
	}

	/**
	 * @param mi302Dao
	 *            the mi302Dao to set
	 */
	public void setMi302Dao(Mi302DAO mi302Dao) {
		this.mi302Dao = mi302Dao;
	}

	/**
	 * @return the mi303Dao
	 */
	public Mi303DAO getMi303Dao() {
		return mi303Dao;
	}

	/**
	 * @param mi303Dao
	 *            the mi303Dao to set
	 */
	public void setMi303Dao(Mi303DAO mi303Dao) {
		this.mi303Dao = mi303Dao;
	}

	/**
	 * @return the cMi304Dao
	 */
	public CMi304DAO getcMi304Dao() {
		return cMi304Dao;
	}

	/**
	 * @param cMi304Dao
	 *            the cMi304Dao to set
	 */
	public void setcMi304Dao(CMi304DAO cMi304Dao) {
		this.cMi304Dao = cMi304Dao;
	}

	/**
	 * @return the mi305Dao
	 */
	public Mi305DAO getMi305Dao() {
		return mi305Dao;
	}

	/**
	 * @param mi305Dao
	 *            the mi305Dao to set
	 */
	public void setMi305Dao(Mi305DAO mi305Dao) {
		this.mi305Dao = mi305Dao;
	}

	/**
	 * @return the mi306Dao
	 */
	public Mi306DAO getMi306Dao() {
		return mi306Dao;
	}

	/**
	 * @param mi306Dao
	 *            the mi306Dao to set
	 */
	public void setMi306Dao(Mi306DAO mi306Dao) {
		this.mi306Dao = mi306Dao;
	}

	/**
	 * @return the cMi308Dao
	 */
	public CMi308DAO getcMi308Dao() {
		return cMi308Dao;
	}

	/**
	 * @param cMi308Dao
	 *            the cMi308Dao to set
	 */
	public void setcMi308Dao(CMi308DAO cMi308Dao) {
		this.cMi308Dao = cMi308Dao;
	}

	/**
	 * @return the cMi311Dao
	 */
	public CMi311DAO getcMi311Dao() {
		return cMi311Dao;
	}

	/**
	 * @param cMi311Dao
	 *            the cMi311Dao to set
	 */
	public void setcMi311Dao(CMi311DAO cMi311Dao) {
		this.cMi311Dao = cMi311Dao;
	}

	/**
	 * @return the cMi312Dao
	 */
	public CMi312DAO getcMi312Dao() {
		return cMi312Dao;
	}

	/**
	 * @param cMi312Dao
	 *            the cMi312Dao to set
	 */
	public void setcMi312Dao(CMi312DAO cMi312Dao) {
		this.cMi312Dao = cMi312Dao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervision.mi.service.WebApi201Service#page20101(com.yondervision
	 * .mi.form.WebApiCommonForm)
	 */
	public Page20101Result page20101(WebApiCommonForm form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}

		Page20101Result reslut = new Page20101Result();

		/*
		 * 取业务类型数据
		 */
		List<Mi007> consultTypeTmpList = this.getCodeListApi001Service().getCodeList(
				form.getCenterId(), "consulttype");
		
		PermissionContext pc = PermissionContext.getInstance();
		List<Mi007> consultType = new ArrayList<Mi007>();
		boolean authflg = false;
		for (int i = 0; i <= consultTypeTmpList.size()-1; i++ ){
			authflg = pc.isCan(consultTypeTmpList.get(i).getItemid());
			if(authflg){
				consultType.add(consultTypeTmpList.get(i));
			}
		}
		
		/*
		 * 取图标数据
		 */
		List<Page20101ResultSubitemIcon> listSubitemIcon = new ArrayList<Page20101ResultSubitemIcon>();
		String purlParam = "consult_sub_item_ioc_path";
		String purl = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, purlParam);
		if (CommonUtil.isEmpty(purl)) {
			// TODO 抛出异常
			System.err.println("purl is null");
		}
		// 遍历IOC文件夹下的IOC文件，并将其信息放入结果集中
		String iocFilePath = CommonUtil.getFullURL(purl);
		File[] iocFiles = new File(iocFilePath).listFiles();
		for (int i = 0; i < iocFiles.length; i++) {
			File file = iocFiles[i];
			Page20101ResultSubitemIcon subitemIcon = new Page20101ResultSubitemIcon();
			String iocFileName = file.getName();
			subitemIcon.setIconId(iocFileName);
			subitemIcon.setUrl(CommonUtil.getDownloadFileUrl(purlParam,
					iocFileName, false));
			listSubitemIcon.add(subitemIcon);
		}

		/*
		 * 将业务类型数据和业务咨询子项图标数据放入返回结果中
		 */
		reslut.setConsultType(consultType);
		reslut.setSubitemIcon(listSubitemIcon);

		return reslut;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervision.mi.service.WebApi201Service#page20111(com.yondervision
	 * .mi.form.WebApiCommonForm)
	 */
	public List<Mi303> page20111(CMi303 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form.getConsultItemId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("consultItemId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "业务咨询项目ID");
		}

		/*
		 * 查询公共条件项目数据
		 */
		Mi303Example mi303Example = new Mi303Example();
		mi303Example.setOrderByClause("item_no asc");
		Mi303Example.Criteria mCriteria = mi303Example.createCriteria()
				.andConsultItemIdEqualTo(form.getConsultItemId());
		mCriteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		@SuppressWarnings("unchecked")
		List<Mi303> listMi303 = this.getMi303Dao()
				.selectByExample(mi303Example);

		/*
		 * 返回结果验证
		 */
		if (CommonUtil.isEmpty(listMi303)) {
			log.debug(ERROR.NO_DATA.getLogText("MI303", mCriteria
					.getCriteriaWithSingleValue().toString()));
		}

		return listMi303;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervision.mi.service.WebApi201Service#page20121(com.yondervision
	 * .mi.form.WebApiCommonForm)
	 */
	public List<Mi306> page20121(CMi306 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form.getConsultSubItemId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("consultSubItemId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "业务咨询子项ID");
		}

		/*
		 * 查询业务咨询向导步骤数据
		 */
		Mi306Example mi306Example = new Mi306Example();
		mi306Example.setOrderByClause("step asc");
		Mi306Example.Criteria mCriteria = mi306Example.createCriteria()
				.andConsultSubItemIdEqualTo(form.getConsultSubItemId());
		mCriteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		@SuppressWarnings("unchecked")
		List<Mi306> listMi306 = this.getMi306Dao()
				.selectByExample(mi306Example);

		/*
		 * 返回结果验证
		 */
		if (CommonUtil.isEmpty(listMi306)) {
			log.debug(ERROR.NO_DATA.getLogText("MI306", mCriteria
					.getCriteriaWithSingleValue().toString()));
		}

		return listMi306;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervision.mi.service.WebApi201Service#page20122(com.yondervision
	 * .mi.form.Page20122Form)
	 */
	// @SuppressWarnings("unchecked")
	// public List<Page20122Result> page20122(Page20122Form form) {
	// Logger log = LoggerUtil.getLogger();
	// /*
	// * 参数验证
	// */
	// if (CommonUtil.isEmpty(form.getConsultItemId())) {
	// log.error(ERROR.PARAMS_NULL.getLogText("consultItemId"));
	// throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
	// .getValue(), "业务咨询项目ID");
	// }
	//
	// /*
	// * 根据公共条件组合ID取得公共条件组合数据
	// */
	// Mi307 mi307 = null;
	// if (!CommonUtil.isEmpty(form.getConditionItemGroupId())) {
	// mi307 = this.getMi307Dao().selectByPrimaryKey(
	// form.getConditionItemGroupId());
	// if (CommonUtil.isEmpty(mi307)) {
	// log.error(ERROR.NO_DATA.getLogText("MI307",
	// "ConditionItemGroupId="
	// + form.getConditionItemGroupId()));
	// throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA
	// .getValue(), "公共条件组合");
	// }
	// }
	//
	// /*
	// * 根据业务咨询项目ID取得公共条件分组项目信息
	// */
	// List<Page20122Result> reslut = new ArrayList<Page20122Result>();
	// Mi303Example mi303Example = new Mi303Example();
	// mi303Example.setOrderByClause("item_no asc");
	// Mi303Example.Criteria mi303Criteria = mi303Example.createCriteria()
	// .andConsultItemIdEqualTo(form.getConsultItemId())
	// .andValidflagEqualTo(Constants.IS_VALIDFLAG);
	// List<Mi303> listMi303 = this.getMi303Dao()
	// .selectByExample(mi303Example);
	// if (CommonUtil.isEmpty(listMi303)) { // 无公共条件项目
	// log.info(ERROR.NO_DATA.getLogText("MI303", mi303Criteria
	// .getCriteriaWithSingleValue().toString()));
	// return reslut;
	// }
	//
	// int conditionIdNo = 0;
	// /*
	// * 遍历业务咨询公共条件项目信息
	// */
	// for (Iterator iterator = listMi303.iterator(); iterator.hasNext();) {
	// Mi303 mi303 = (Mi303) iterator.next();
	// conditionIdNo++;
	// String conditionId = mi307 == null ? null : JavaBeanUtil.getter(
	// mi307, "conditionId" + conditionIdNo);
	// List<Page20122Result_children> conditionGroup = new
	// ArrayList<Page20122Result_children>();
	// /*
	// * 将公共条件项目信息放入结果集中
	// */
	// Page20122Result rs02 = new Page20122Result();
	// rs02.setConditionItemId(mi303.getConditionItemId());
	// rs02.setConditionItemName(mi303.getConditionItemName());
	//
	// /*
	// * 查询公共条件分组信息
	// */
	// Mi304Example mi304Example = new Mi304Example();
	// mi304Example.setOrderByClause("no_oreder asc");
	// mi304Example.createCriteria().andConditionItemIdEqualTo(
	// rs02.getConditionItemId()).andValidflagEqualTo(
	// Constants.IS_VALIDFLAG);
	// List<Mi304> listMi304 = this.getcMi304Dao().selectByExample(
	// mi304Example);
	// /*
	// * 遍历业务咨询公共条件分组信息
	// */
	// for (Iterator iterator2 = listMi304.iterator(); iterator2.hasNext();) {
	// Mi304 mi304 = (Mi304) iterator2.next();
	// List<Page20122Result_children_children> condition = new
	// ArrayList<Page20122Result_children_children>();
	// /*
	// * 将公共条件分组信息放入结果集中
	// */
	// Page20122Result_children rs02conditionGroup = new
	// Page20122Result_children();
	// rs02conditionGroup.setConditionGroupId(mi304
	// .getConditionGroupId());
	// rs02conditionGroup.setGroupName(mi304.getGroupName());
	//
	// /*
	// * 查询公共条件内容
	// */
	// Mi305Example mi305Example = new Mi305Example();
	// mi305Example.setOrderByClause("no_oreder asc");
	// mi305Example.createCriteria().andConditionGroupIdEqualTo(
	// mi304.getConditionGroupId()).andValidflagEqualTo(
	// Constants.IS_VALIDFLAG);
	// List<Mi305> listMi305 = this.getMi305Dao().selectByExample(
	// mi305Example);
	// /*
	// * 遍历公共条件内容信息
	// */
	// for (Iterator iterator3 = listMi305.iterator(); iterator3
	// .hasNext();) {
	// Mi305 mi305 = (Mi305) iterator3.next();
	// /*
	// * 将公共条件内容放入结果集中
	// */
	// Page20122Result_children_children rs02condition = new
	// Page20122Result_children_children();
	// rs02condition.setConditionId(mi305.getConditionId());
	// rs02condition
	// .setConditionDetail(mi305.getConditionDetail());
	//
	// /*
	// * 判断此问题是否被选中
	// */
	// if (!CommonUtil.isEmpty(conditionId)) {
	// if (conditionId.equals(rs02condition.getConditionId())) {
	// rs02condition.setSelected(true);
	// rs02conditionGroup.setSelected(true);
	// }
	// }
	//
	// condition.add(rs02condition);
	// }
	// rs02conditionGroup.setChildren(condition);
	//
	// conditionGroup.add(rs02conditionGroup);
	// }
	//
	// rs02.setChildren(conditionGroup);
	// reslut.add(rs02);
	// }
	//
	// return reslut;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervision.mi.service.WebApi201Service#webapi20101(com.yondervision
	 * .mi.dto.CMi301)
	 */

	public List<Mi301> webapi20101(CMi301 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form)) {
			log.error(ERROR.PARAMS_NULL.getLogText("form"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "表单");
		}

		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}

		/*
		 * 查询业务咨询项目数据
		 */
		Mi301Example mi301Example = new Mi301Example();
		mi301Example.setOrderByClause("order_no asc");
		Mi301Example.Criteria mCriteria = mi301Example.createCriteria()
				.andCenteridEqualTo(form.getCenterId());
		if (!CommonUtil.isEmpty(form.getConsultType())) {
			mCriteria.andConsultTypeEqualTo(form.getConsultType());
		}
		mCriteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		@SuppressWarnings("unchecked")
		List<Mi301> listMi301 = this.getMi301Dao()
				.selectByExample(mi301Example);

		/*
		 * 返回结果验证
		 */
		if (CommonUtil.isEmpty(listMi301)) {
			log.error(ERROR.NO_DATA.getLogText("MI301", mCriteria
					.getCriteriaWithSingleValue().toString()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),
					"业务咨询项目");
		}

		return listMi301;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervision.mi.service.WebApi201Service#webapi20102(com.yondervision
	 * .mi.dto.CMi302)
	 */
	public List<Mi302> webapi20102(CMi302 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form)) {
			log.error(ERROR.PARAMS_NULL.getLogText("form"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "表单");
		}

		if (CommonUtil.isEmpty(form.getConsultItemId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("consultItemId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "业务咨询项目ID");
		}

		/*
		 * 查询业务咨询子项数据
		 */
		Mi302Example mi302Example = new Mi302Example();
		mi302Example.setOrderByClause("order_no asc");
		Mi302Example.Criteria mi302Criteria = mi302Example.createCriteria()
				.andConsultItemIdEqualTo(form.getConsultItemId());
		mi302Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		@SuppressWarnings("unchecked")
		List<Mi302> listMi302 = this.getMi302Dao()
				.selectByExample(mi302Example);

		/*
		 * 返回结果验证
		 */
		if (CommonUtil.isEmpty(listMi302)) {
			log.error(ERROR.NO_DATA.getLogText("MI302", mi302Criteria
					.getCriteriaWithSingleValue().toString()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),
					"业务咨询子项");
		}

		return listMi302;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervision.mi.service.WebApi201Service#webapi20103_add(com.yondervision
	 * .mi.dto.CMi301)
	 */
	public String webapi20103_add(CMi301 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * TODO 参数验证
		 */

		/*
		 * 添加业务咨询信息
		 */
		String consultItemId = commonUtil.genKey("MI301", 0);
		if (CommonUtil.isEmpty(consultItemId)) {
			log.error(ERROR.NULL_KEY.getLogText("MI301"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.NULL_KEY.getValue());
		}
		form.setConsultItemId(consultItemId);
		form.setCenterid(form.getCenterId());
		form.setValidflag(Constants.IS_VALIDFLAG);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		this.getMi301Dao().insert(form);

		return consultItemId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yondervision.mi.service.WebApi201Service#webapi20103_edit(com.
	 * yondervision.mi.dto.CMi301)
	 */
	public void webapi20103_edit(CMi301 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * TODO 参数验证
		 */

		/*
		 * 修改业务咨询信息
		 */
		form.setDatemodified(CommonUtil.getSystemDate());
		int count = this.getMi301Dao().updateByPrimaryKeySelective(form);
		/*
		 * 无对应数据抛出异常
		 */
		if (count == 0) {
			log.error(ERROR.UPDATE_NO_DATA.getLogText("CMi301", form
					.getConsultItemId()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.UPDATE_NO_DATA.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yondervision.mi.service.WebApi201Service#webapi20103_delete(com.
	 * yondervision.mi.dto.CMi301)
	 */
	public void webapi20103_delete(CMi301 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form.getConsultItemId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("consultItemId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "业务咨询项目ID");
		}

		/*
		 * 验证此业务咨询项是否有业务咨询子项
		 */
		Mi302Example mi302Example = new Mi302Example();
		Mi302Example.Criteria mi302Criteria = mi302Example.createCriteria()
				.andConsultItemIdEqualTo(form.getConsultItemId());
		mi302Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		int mi302Count = this.getMi302Dao().countByExample(mi302Example);
		/*
		 * 验证此业务咨询项是否有公共条件项目
		 */
		Mi303Example mi303Example = new Mi303Example();
		Mi303Example.Criteria mi303Criteria = mi303Example.createCriteria()
				.andConsultItemIdEqualTo(form.getConsultItemId());
		mi303Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		int mi303Count = this.getMi303Dao().countByExample(mi303Example);
		// 要删除的业务咨询项目包含业务咨询子项或公共条件项目则不能删除
		if (mi302Count > 0 || mi303Count > 0) {
			throw new NoRollRuntimeErrorException(WEB_ALERT.MI301_COUNT_DELETE
					.getValue(), String.valueOf(mi302Count), String
					.valueOf(mi303Count));
		}

		/*
		 * 将业务咨询项目ID为consultItemId的数据设置为无效
		 */
		Mi301 mi301 = new Mi301();
		mi301.setConsultItemId(form.getConsultItemId());
		mi301.setValidflag(Constants.IS_NOT_VALIDFLAG);
		mi301.setDatemodified(CommonUtil.getSystemDate());
		int count = this.getMi301Dao().updateByPrimaryKeySelective(mi301);

		/*
		 * 无对应数据抛出异常
		 */
		if (count == 0) {
			log.error(ERROR.UPDATE_NO_DATA.getLogText("CMi301", form
					.getConsultItemId()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.UPDATE_NO_DATA.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yondervision.mi.service.WebApi201Service#webapi20103_sort(com.
	 * yondervision.mi.form.WebApi20103_storForm)
	 */
	@Transactional(noRollbackFor = NoRollRuntimeErrorException.class)
	public void webapi20103_sort(WebApi20103_storForm form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * TODO 参数验证
		 */

		/*
		 * 拖拽方向判断
		 */
		boolean isUp; // true=向上拖拽；false=向下拖拽
		Integer start; // 较小的顺序号
		Integer end; // 较大的顺序号
		if (form.getSourceOrderNo().compareTo(form.getTargetOrderNo()) > 0) {
			isUp = true;
			start = form.getTargetOrderNo();
			end = form.getSourceOrderNo();
		} else {
			isUp = false;
			start = form.getSourceOrderNo();
			end = form.getTargetOrderNo();
		}

		/*
		 * 查询出排序将要影响的业务咨询项目数据
		 */
		Mi301Example mi301Example = new Mi301Example();
		mi301Example.setOrderByClause("ORDER_NO asc");
		Mi301Example.Criteria mi301Criteria = mi301Example.createCriteria()
				.andCenteridEqualTo(form.getCenterId());
		mi301Criteria.andConsultTypeEqualTo(form.getConsultType());
		mi301Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		mi301Criteria.andOrderNoBetween(start, end);
		@SuppressWarnings("unchecked")
		List<Mi301> listMi301 = this.getMi301Dao()
				.selectByExample(mi301Example);
		// 未找到数据时报出异常
		if (CommonUtil.isEmpty(listMi301)) {
			log.error(ERROR.NO_DATA.getLogText("MI301", mi301Criteria
					.getCriteriaWithSingleValue().toString()
					+ mi301Criteria.getCriteriaWithBetweenValue().toString()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),
					"业务咨询项目");
		}

		/*
		 * 更新业务咨询项目的顺序号
		 */
		try {
			for (Iterator<Mi301> iterator = listMi301.iterator(); iterator
					.hasNext();) {
				Mi301 mi301 = (Mi301) iterator.next();
				boolean isChanged = false;
				/*
				 * 向上拖拽——除源顺序号外，其它受影响的顺序号加1
				 */
				if (isUp) {
					if (form.getSourceOrderNo().compareTo(mi301.getOrderNo()) != 0) {
						mi301.setOrderNo(mi301.getOrderNo() + 1);
						isChanged = true;
					}
				}
				/*
				 * 向下拖拽——除源顺序号外，其它受影响的顺序号减1
				 */
				if (!isUp) {
					if (form.getSourceOrderNo().compareTo(mi301.getOrderNo()) != 0) {
						mi301.setOrderNo(mi301.getOrderNo() - 1);
						isChanged = true;
					}
				}
				/*
				 * 源顺序号设置为目标顺序号
				 */
				if (!isChanged
						&& form.getSourceOrderNo()
								.compareTo(mi301.getOrderNo()) == 0) {
					mi301.setOrderNo(form.getTargetOrderNo());
				}

				/*
				 * 更新序号和最后修改时间
				 */
				mi301.setDatemodified(CommonUtil.getSystemDate());
				this.getMi301Dao().updateByPrimaryKey(mi301);
			}
		} catch (Exception e) {
			log.error(ERROR.SYS.getLogText(e.getMessage()), e);
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.SYS.getValue());
		}
	}

	public String webapi20104_add(CMi302 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * TODO 参数验证
		 */

		/*
		 * 添加业务咨询信息
		 */
		String consultSubItemId = commonUtil.genKey("MI302", 0);
		if (CommonUtil.isEmpty(consultSubItemId)) {
			log.error(ERROR.NULL_KEY.getLogText("MI302"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.NULL_KEY.getValue());
		}
		form.setConsultSubItemId(consultSubItemId);
		form.setValidflag(Constants.IS_VALIDFLAG);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		this.getMi302Dao().insert(form);

		return consultSubItemId;
	}

	public void webapi20104_edit(CMi302 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * TODO 参数验证
		 */

		/*
		 * 修改业务咨询项目
		 */
		form.setDatemodified(CommonUtil.getSystemDate());
		int count = this.getMi302Dao().updateByPrimaryKeySelective(form);
		/*
		 * 无对应数据抛出异常
		 */
		if (count == 0) {
			log.error(ERROR.UPDATE_NO_DATA.getLogText("CMi302", form
					.getConsultSubItemId()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.UPDATE_NO_DATA.getValue());
		}
	}

	public void webapi20104_delete(CMi302 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form.getConsultSubItemId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("consultSubItemId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "业务咨询子项ID");
		}

		/*
		 * 验证此业务咨询子项是否有咨询向导步骤
		 */
		Mi306Example example = new Mi306Example();
		Mi306Example.Criteria mi306Criteria = example.createCriteria()
				.andConsultSubItemIdEqualTo(form.getConsultSubItemId());
		mi306Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		int mi306Count = this.getMi306Dao().countByExample(example);
		// 要删除的业务咨询子项有咨询向导步骤则不能删除
		if (mi306Count > 0) {
			throw new NoRollRuntimeErrorException(WEB_ALERT.MI302_COUNT_DELETE
					.getValue(), String.valueOf(mi306Count));
		}

		/*
		 * 将业务咨询子项ID为consultSubItemId的数据设置为无效
		 */
		Mi302 mi302 = new Mi302();
		mi302.setConsultSubItemId(form.getConsultSubItemId());
		mi302.setValidflag(Constants.IS_NOT_VALIDFLAG);
		mi302.setDatemodified(CommonUtil.getSystemDate());
		int count = this.getMi302Dao().updateByPrimaryKeySelective(mi302);

		/*
		 * 无对应数据抛出异常
		 */
		if (count == 0) {
			log.error(ERROR.UPDATE_NO_DATA.getLogText("CMi302", form
					.getConsultSubItemId()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.UPDATE_NO_DATA.getValue());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yondervision.mi.service.WebApi201Service#webapi20104_sort(com.
	 * yondervision.mi.form.WebApi20104_storForm)
	 */
	@Transactional(noRollbackFor = NoRollRuntimeErrorException.class)
	public void webapi20104_sort(WebApi20104_storForm form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * TODO 参数验证
		 */

		/*
		 * 拖拽方向判断
		 */
		boolean isUp; // true=向上拖拽；false=向下拖拽
		Integer start; // 较小的顺序号
		Integer end; // 较大的顺序号
		if (form.getSourceOrderNo().compareTo(form.getTargetOrderNo()) > 0) {
			isUp = true;
			start = form.getTargetOrderNo();
			end = form.getSourceOrderNo();
		} else {
			isUp = false;
			start = form.getSourceOrderNo();
			end = form.getTargetOrderNo();
		}

		/*
		 * 查询出排序将要影响的业务咨询子项数据
		 */
		Mi302Example mi302Example = new Mi302Example();
		mi302Example.setOrderByClause("ORDER_NO asc");
		Mi302Example.Criteria mi302Criteria = mi302Example.createCriteria()
				.andConsultItemIdEqualTo(form.getConsultItemId());
		mi302Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		mi302Criteria.andOrderNoBetween(start, end);
		@SuppressWarnings("unchecked")
		List<Mi302> listMi302 = this.getMi302Dao()
				.selectByExample(mi302Example);
		// 未找到数据时报出异常
		if (CommonUtil.isEmpty(listMi302)) {
			log.error(ERROR.NO_DATA.getLogText("MI302", mi302Criteria
					.getCriteriaWithSingleValue().toString()
					+ mi302Criteria.getCriteriaWithBetweenValue().toString()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),
					"业务咨询子项");
		}

		/*
		 * 更新业务咨询子项的顺序号
		 */
		try {
			for (Iterator<Mi302> iterator = listMi302.iterator(); iterator
					.hasNext();) {
				Mi302 mi302 = (Mi302) iterator.next();
				boolean isChanged = false;

				/*
				 * 向上拖拽——除源顺序号外，其它受影响的顺序号加1
				 */
				if (isUp) {
					if (form.getSourceOrderNo().compareTo(mi302.getOrderNo()) != 0) {
						mi302.setOrderNo(mi302.getOrderNo() + 1);
						isChanged = true;
					}
				}
				/*
				 * 向下拖拽——除源顺序号外，其它受影响的顺序号减1
				 */
				if (!isUp) {
					if (form.getSourceOrderNo().compareTo(mi302.getOrderNo()) != 0) {
						mi302.setOrderNo(mi302.getOrderNo() - 1);
						isChanged = true;
					}
				}
				/*
				 * 源顺序号设置为目标顺序号
				 */
				if (!isChanged
						&& form.getSourceOrderNo()
								.compareTo(mi302.getOrderNo()) == 0) {
					mi302.setOrderNo(form.getTargetOrderNo());
				}
				/*
				 * 更新序号和最后修改时间
				 */
				mi302.setDatemodified(CommonUtil.getSystemDate());
				this.getMi302Dao().updateByPrimaryKey(mi302);
			}
		} catch (Exception e) {
			log.error(ERROR.SYS.getLogText(e.getMessage()), e);
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.SYS.getValue());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervision.mi.service.WebApi201Service#webapi20111(com.yondervision
	 * .mi.dto.CMi304)
	 */
	public List<WebApi20111Result> webapi20111(CMi304 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form.getConsultItemId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("consultItemId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "业务咨询项目ID");
		}

		/*
		 * 查公共条件分组和内容数据
		 */
		List<WebApi20111Result> result = null;
		if (CommonUtil.isEmpty(form.getConditionItemId())) { // 查询此业务咨询项目ID下的全部
			result = this.getcMi304Dao()
					.selectWebApi20111ResultByConsultItemId(
							form.getConsultItemId());
		} else { // 查询指定公共条件项目
			result = this.getcMi304Dao()
					.selectWebApi20111ResultByConditionItemId(
							form.getConditionItemId());
		}

		/*
		 * 返回结果验证
		 */
		if (CommonUtil.isEmpty(result)) {
			log.error(ERROR.NO_DATA.getLogText("MI304", "conditionItemId="
					+ form.getConditionItemId()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),
					"公共条件分组");
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervision.mi.service.WebApi201Service#webapi20112_add(com.yondervision
	 * .mi.dto.CMi303)
	 */
	public String webapi20112_add(CMi303 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * TODO 参数验证
		 */

		/*
		 * 添加公共条件项目
		 */
		Mi303Example mi303Example = new Mi303Example();
		mi303Example.createCriteria().andConsultItemIdEqualTo(
				form.getConsultItemId()).andValidflagEqualTo(
				Constants.IS_VALIDFLAG);
		int count = this.getMi303Dao().countByExample(mi303Example);
		if (count >= 8) {
			throw new NoRollRuntimeErrorException(
					WEB_ALERT.MI303_CONSULTITEM_THAN_MAX.getValue());
		}

		String conditionItemId = commonUtil.genKey("MI303", 0);
		if (CommonUtil.isEmpty(conditionItemId)) {
			log.error(ERROR.NULL_KEY.getLogText("MI303"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.NULL_KEY.getValue());
		}
		form.setConditionItemId(conditionItemId);
		form.setValidflag(Constants.IS_VALIDFLAG);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		this.getMi303Dao().insert(form);

		return conditionItemId;
	}

	public void webapi20112_edit(CMi303 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * TODO 参数验证
		 */

		/*
		 * 修改业务咨询子项
		 */
		form.setDatemodified(CommonUtil.getSystemDate());
		int count = this.getMi303Dao().updateByPrimaryKeySelective(form);
		/*
		 * 无对应数据抛出异常
		 */
		if (count == 0) {
			log.error(ERROR.UPDATE_NO_DATA.getLogText("CMi303", form
					.getConditionItemId()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.UPDATE_NO_DATA.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yondervision.mi.service.WebApi201Service#webapi20112_delete(com.
	 * yondervision.mi.dto.CMi303)
	 */
	public void webapi20112_delete(CMi303 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form.getConditionItemId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("conditionItemId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "公共条件项目ID");
		}

		/*
		 * 验证此公共条件项目下是否有公共条件分组
		 */
		Mi304Example example = new Mi304Example();
		Mi304Example.Criteria mi304Criteria = example.createCriteria()
				.andConditionItemIdEqualTo(form.getConditionItemId());
		mi304Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		int mi304Count = this.getcMi304Dao().countByExample(example);
		// 要删除的公共条件项目下有公共条件分组则不能删除
		if (mi304Count > 0) {
			throw new NoRollRuntimeErrorException(WEB_ALERT.MI303_COUNT_DELETE
					.getValue(), String.valueOf(mi304Count));
		}

		/*
		 * 将公共条件项目ID为conditionItemId的数据设置为无效
		 */
		Mi303 mi303 = new Mi303();
		mi303.setConditionItemId(form.getConditionItemId());
		mi303.setValidflag(Constants.IS_NOT_VALIDFLAG);
		mi303.setDatemodified(CommonUtil.getSystemDate());
		int count = this.getMi303Dao().updateByPrimaryKeySelective(mi303);

		/*
		 * 无对应数据抛出异常
		 */
		if (count == 0) {
			log.error(ERROR.UPDATE_NO_DATA.getLogText("CMi303", form
					.getConditionItemId()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.UPDATE_NO_DATA.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yondervision.mi.service.WebApi201Service#webapi20112_sort(com.
	 * yondervision.mi.form.WebApi20112_storForm)
	 */
	@Transactional(noRollbackFor = NoRollRuntimeErrorException.class)
	public void webapi20112_sort(WebApi20112_storForm form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * TODO 参数验证
		 */

		/*
		 * 拖拽方向判断
		 */
		boolean isUp; // true=向上拖拽；false=向下拖拽
		Integer start; // 较小的顺序号
		Integer end; // 较大的顺序号
		if (form.getSourceOrderNo().compareTo(form.getTargetOrderNo()) > 0) {
			isUp = true;
			start = form.getTargetOrderNo();
			end = form.getSourceOrderNo();
		} else {
			isUp = false;
			start = form.getSourceOrderNo();
			end = form.getTargetOrderNo();
		}

		/*
		 * 查询出排序将要影响的公共条件项目数据
		 */
		Mi303Example mi303Example = new Mi303Example();
		mi303Example.setOrderByClause("ITEM_NO asc");
		Mi303Example.Criteria mi303Criteria = mi303Example.createCriteria()
				.andConsultItemIdEqualTo(form.getConsultItemId());
		mi303Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		mi303Criteria.andItemNoBetween(start, end);
		@SuppressWarnings("unchecked")
		List<Mi303> listMi303 = this.getMi303Dao()
				.selectByExample(mi303Example);
		// 未找到数据时报出异常
		if (CommonUtil.isEmpty(listMi303)) {
			log.error(ERROR.NO_DATA.getLogText("MI303", mi303Criteria
					.getCriteriaWithSingleValue().toString()
					+ mi303Criteria.getCriteriaWithBetweenValue().toString()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),
					"公共条件项目");
		}

		/*
		 * 更新公共条件项目的顺序号
		 */
		try {
			for (Iterator<Mi303> iterator = listMi303.iterator(); iterator
					.hasNext();) {
				Mi303 mi303 = (Mi303) iterator.next();
				boolean isChanged = false;

				/*
				 * 向上拖拽——除源顺序号外，其它受影响的顺序号加1
				 */
				if (isUp) {
					if (form.getSourceOrderNo().compareTo(mi303.getItemNo()) != 0) {
						mi303.setItemNo(mi303.getItemNo() + 1);
						isChanged = true;
					}
				}
				/*
				 * 向下拖拽——除源顺序号外，其它受影响的顺序号减1
				 */
				if (!isUp) {
					if (form.getSourceOrderNo().compareTo(mi303.getItemNo()) != 0) {
						mi303.setItemNo(mi303.getItemNo() - 1);
						isChanged = true;
					}
				}
				/*
				 * 源顺序号设置为目标顺序号
				 */
				if (!isChanged
						&& form.getSourceOrderNo().compareTo(mi303.getItemNo()) == 0) {
					mi303.setItemNo(form.getTargetOrderNo());
				}
				/*
				 * 更新序号和最后修改时间
				 */
				mi303.setDatemodified(CommonUtil.getSystemDate());
				this.getMi303Dao().updateByPrimaryKey(mi303);
			}
		} catch (Exception e) {
			log.error(ERROR.SYS.getLogText(e.getMessage()), e);
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.SYS.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervision.mi.service.WebApi201Service#webapi20113_add(com.yondervision
	 * .mi.dto.CMi304)
	 */
	public String webapi20113_add(CMi304 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * TODO 参数验证
		 */

		/*
		 * 添加公共条件分组
		 */
		String conditionGroupId = commonUtil.genKey("MI304", 0);
		if (CommonUtil.isEmpty(conditionGroupId)) {
			log.error(ERROR.NULL_KEY.getLogText("MI304"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.NULL_KEY.getValue());
		}
		form.setConditionGroupId(conditionGroupId);
		form.setValidflag(Constants.IS_VALIDFLAG);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		this.getcMi304Dao().insert(form);

		return conditionGroupId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yondervision.mi.service.WebApi201Service#webapi20113_edit(com.
	 * yondervision.mi.dto.CMi304)
	 */
	public void webapi20113_edit(CMi304 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * TODO 参数验证
		 */

		/*
		 * 修改公共条件分组
		 */
		form.setDatemodified(CommonUtil.getSystemDate());
		int count = this.getcMi304Dao().updateByPrimaryKeySelective(form);
		/*
		 * 无对应数据抛出异常
		 */
		if (count == 0) {
			log.error(ERROR.UPDATE_NO_DATA.getLogText("CMi304", form
					.getConditionGroupId()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.UPDATE_NO_DATA.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yondervision.mi.service.WebApi201Service#webapi20113_delete(com.
	 * yondervision.mi.dto.CMi304)
	 */
	public void webapi20113_delete(CMi304 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form.getConditionGroupId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("conditionGroupId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "公共条件分组ID");
		}

		/*
		 * 验证此公共条件分组下是否有公共条件内容
		 */
		Mi305Example example = new Mi305Example();
		Mi305Example.Criteria mi305Criteria = example.createCriteria()
				.andConditionGroupIdEqualTo(form.getConditionGroupId());
		mi305Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		int mi305Count = this.getMi305Dao().countByExample(example);
		// 要删除的公共条件分组下有公共条件内容则不能删除
		if (mi305Count > 0) {
			throw new NoRollRuntimeErrorException(WEB_ALERT.MI304_COUNT_DELETE
					.getValue(), String.valueOf(mi305Count));
		}

		/*
		 * 将公共条件分组ID为conditionGroupId的数据设置为无效
		 */
		Mi304 mi304 = new Mi304();
		mi304.setConditionGroupId(form.getConditionGroupId());
		mi304.setValidflag(Constants.IS_NOT_VALIDFLAG);
		mi304.setDatemodified(CommonUtil.getSystemDate());
		int count = this.getcMi304Dao().updateByPrimaryKeySelective(mi304);

		/*
		 * 无对应数据抛出异常
		 */
		if (count == 0) {
			log.error(ERROR.UPDATE_NO_DATA.getLogText("CMi304", form
					.getConditionGroupId()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.UPDATE_NO_DATA.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yondervision.mi.service.WebApi201Service#webapi20113_sort(com.
	 * yondervision.mi.form.WebApi20113_storForm)
	 */
	@Transactional(noRollbackFor = NoRollRuntimeErrorException.class)
	public void webapi20113_sort(WebApi20113_storForm form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * TODO 参数验证
		 */

		/*
		 * 拖拽方向判断
		 */
		boolean isUp; // true=向上拖拽；false=向下拖拽
		Integer start; // 较小的顺序号
		Integer end; // 较大的顺序号
		if (form.getSourceOrderNo().compareTo(form.getTargetOrderNo()) > 0) {
			isUp = true;
			start = form.getTargetOrderNo();
			end = form.getSourceOrderNo();
		} else {
			isUp = false;
			start = form.getSourceOrderNo();
			end = form.getTargetOrderNo();
		}

		/*
		 * 查询出排序将要影响的公共条件分组数据
		 */
		Mi304Example mi304Example = new Mi304Example();
		mi304Example.setOrderByClause("NO_OREDER asc");
		Mi304Example.Criteria mi304Criteria = mi304Example.createCriteria()
				.andConditionItemIdEqualTo(form.getConditionItemId());
		mi304Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		mi304Criteria.andNoOrederBetween(start, end);
		@SuppressWarnings("unchecked")
		List<Mi304> listMi304 = this.getcMi304Dao().selectByExample(
				mi304Example);
		// 未找到数据时报出异常
		if (CommonUtil.isEmpty(listMi304)) {
			log.error(ERROR.NO_DATA.getLogText("MI304", mi304Criteria
					.getCriteriaWithSingleValue().toString()
					+ mi304Criteria.getCriteriaWithBetweenValue().toString()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),
					"公共条件分组");
		}

		/*
		 * 更新公共条件分组的顺序号
		 */
		try {
			for (Iterator<Mi304> iterator = listMi304.iterator(); iterator
					.hasNext();) {
				Mi304 mi304 = (Mi304) iterator.next();
				boolean isChanged = false;

				/*
				 * 向上拖拽——除源顺序号外，其它受影响的顺序号加1
				 */
				if (isUp) {
					if (form.getSourceOrderNo().compareTo(mi304.getNoOreder()) != 0) {
						mi304.setNoOreder(mi304.getNoOreder() + 1);
						isChanged = true;
					}
				}
				/*
				 * 向下拖拽——除源顺序号外，其它受影响的顺序号减1
				 */
				if (!isUp) {
					if (form.getSourceOrderNo().compareTo(mi304.getNoOreder()) != 0) {
						mi304.setNoOreder(mi304.getNoOreder() - 1);
						isChanged = true;
					}
				}
				/*
				 * 源顺序号设置为目标顺序号
				 */
				if (!isChanged
						&& form.getSourceOrderNo().compareTo(
								mi304.getNoOreder()) == 0) {
					mi304.setNoOreder(form.getTargetOrderNo());
				}
				/*
				 * 更新序号和最后修改时间
				 */
				mi304.setDatemodified(CommonUtil.getSystemDate());
				this.getcMi304Dao().updateByPrimaryKey(mi304);
			}
		} catch (Exception e) {
			log.error(ERROR.SYS.getLogText(e.getMessage()), e);
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.SYS.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervision.mi.service.WebApi201Service#webapi20114_add(com.yondervision
	 * .mi.dto.CMi305)
	 */
	public String webapi20114_add(CMi305 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * TODO 参数验证
		 */

		/*
		 * 添加公共条件内容
		 */
		String conditionId = commonUtil.genKey("MI305", 0);
		if (CommonUtil.isEmpty(conditionId)) {
			log.error(ERROR.NULL_KEY.getLogText("MI305"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.NULL_KEY.getValue());
		}
		form.setConditionId(conditionId);
		form.setValidflag(Constants.IS_VALIDFLAG);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		this.getMi305Dao().insert(form);

		return conditionId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yondervision.mi.service.WebApi201Service#webapi20114_edit(com.
	 * yondervision.mi.dto.CMi305)
	 */
	public void webapi20114_edit(CMi305 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * TODO 参数验证
		 */

		/*
		 * 修改公共条件内容
		 */
		form.setDatemodified(CommonUtil.getSystemDate());
		int count = this.getMi305Dao().updateByPrimaryKeySelective(form);
		/*
		 * 无对应数据抛出异常
		 */
		if (count == 0) {
			log.error(ERROR.UPDATE_NO_DATA.getLogText("CMi305", form
					.getConditionId()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.UPDATE_NO_DATA.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yondervision.mi.service.WebApi201Service#webapi20114_delete(com.
	 * yondervision.mi.dto.CMi305)
	 */
	public void webapi20114_delete(CMi305 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form.getConditionId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("conditionId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "公共条件内容ID");
		}

		/*
		 * 验证此公共条件内容否被公共条件组合（mi311)使用
		 */
		Mi311Example mi311Example = new Mi311Example();
		Mi311Example.Criteria mi311Criteria = mi311Example.createCriteria();
		mi311Criteria.andConditionIdEqualTo(form.getConditionId());
		mi311Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		int mi311Count = this.getcMi311Dao().countByExample(mi311Example);
		// 要删除的公共条件内容被公共条件组合使用则不能删除
		if (mi311Count > 0) {
			throw new NoRollRuntimeErrorException(WEB_ALERT.MI305_COUNT_DELETE
					.getValue(), String.valueOf(mi311Count));
		}

		/*
		 * 将公共条件内容ID为conditionId的数据设置为无效
		 */
		Mi305 mi305 = new Mi305();
		mi305.setConditionId(form.getConditionId());
		mi305.setValidflag(Constants.IS_NOT_VALIDFLAG);
		mi305.setDatemodified(CommonUtil.getSystemDate());
		int count = this.getMi305Dao().updateByPrimaryKeySelective(mi305);

		/*
		 * 无对应数据抛出异常
		 */
		if (count == 0) {
			log.error(ERROR.UPDATE_NO_DATA.getLogText("CMi305", form
					.getConditionId()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.UPDATE_NO_DATA.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yondervision.mi.service.WebApi201Service#webapi20114_sort(com.
	 * yondervision.mi.form.WebApi20114_storForm)
	 */
	@Transactional(noRollbackFor = NoRollRuntimeErrorException.class)
	public void webapi20114_sort(WebApi20114_storForm form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * TODO 参数验证
		 */

		/*
		 * 拖拽方向判断
		 */
		boolean isUp; // true=向上拖拽；false=向下拖拽
		Integer start; // 较小的顺序号
		Integer end; // 较大的顺序号
		if (form.getSourceOrderNo().compareTo(form.getTargetOrderNo()) > 0) {
			isUp = true;
			start = form.getTargetOrderNo();
			end = form.getSourceOrderNo();
		} else {
			isUp = false;
			start = form.getSourceOrderNo();
			end = form.getTargetOrderNo();
		}

		/*
		 * 查询出排序将要影响的公共条件内容数据
		 */
		Mi305Example mi305Example = new Mi305Example();
		mi305Example.setOrderByClause("NO_OREDER asc");
		Mi305Example.Criteria mi305Criteria = mi305Example.createCriteria()
				.andConditionGroupIdEqualTo(form.getConditionGroupId());
		mi305Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		mi305Criteria.andNoOrederBetween(start, end);
		@SuppressWarnings("unchecked")
		List<Mi305> listMi305 = this.getMi305Dao()
				.selectByExample(mi305Example);
		// 未找到数据时报出异常
		if (CommonUtil.isEmpty(listMi305)) {
			log.error(ERROR.NO_DATA.getLogText("MI305", mi305Criteria
					.getCriteriaWithSingleValue().toString()
					+ mi305Criteria.getCriteriaWithBetweenValue().toString()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),
					"公共条件内容");
		}

		/*
		 * 更新公共条件内容的顺序号
		 */
		try {
			for (Iterator<Mi305> iterator = listMi305.iterator(); iterator
					.hasNext();) {
				Mi305 mi305 = (Mi305) iterator.next();
				boolean isChanged = false;

				/*
				 * 向上拖拽——除源顺序号外，其它受影响的顺序号加1
				 */
				if (isUp) {
					if (form.getSourceOrderNo().compareTo(mi305.getNoOreder()) != 0) {
						mi305.setNoOreder(mi305.getNoOreder() + 1);
						isChanged = true;
					}
				}
				/*
				 * 向下拖拽——除源顺序号外，其它受影响的顺序号减1
				 */
				if (!isUp) {
					if (form.getSourceOrderNo().compareTo(mi305.getNoOreder()) != 0) {
						mi305.setNoOreder(mi305.getNoOreder() - 1);
						isChanged = true;
					}
				}
				/*
				 * 源顺序号设置为目标顺序号
				 */
				if (!isChanged
						&& form.getSourceOrderNo().compareTo(
								mi305.getNoOreder()) == 0) {
					mi305.setNoOreder(form.getTargetOrderNo());
				}
				/*
				 * 更新序号和最后修改时间
				 */
				mi305.setDatemodified(CommonUtil.getSystemDate());
				this.getMi305Dao().updateByPrimaryKey(mi305);
			}
		} catch (Exception e) {
			log.error(ERROR.SYS.getLogText(e.getMessage()), e);
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.SYS.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervision.mi.service.WebApi201Service#webapi20121(com.yondervision
	 * .mi.dto.CMi308)
	 */
	public List<CMi308> webapi20121(CMi308 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form.getStepId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("stepId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "业务咨询步骤向导ID");
		}

		/*
		 * 查询业务咨询向导内容及其所有子内容
		 */
		List<CMi308> result = this.getcMi308Dao().selectAllCMi308ByStepId(
				form.getStepId());

		/*
		 * 返回结果验证
		 */
		if (CommonUtil.isEmpty(result)) {
			log.error(ERROR.NO_DATA.getLogText("MI308", "stepId="
					+ form.getStepId()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),
					"业务咨询向导内容");
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervision.mi.service.WebApi201Service#webapi20122_add(com.yondervision
	 * .mi.dto.CMi306)
	 */
	public String webapi20122_add(CMi306 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * TODO 参数验证
		 */

		/*
		 * 添加向导步骤
		 */
		String stepId = commonUtil.genKey("MI306", 0);
		if (CommonUtil.isEmpty(stepId)) {
			log.error(ERROR.NULL_KEY.getLogText("MI306"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.NULL_KEY.getValue());
		}
		form.setStepId(stepId);
		form.setValidflag(Constants.IS_VALIDFLAG);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		this.getMi306Dao().insert(form);

		return stepId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yondervision.mi.service.WebApi201Service#webapi20122_edit(com.
	 * yondervision.mi.dto.CMi306)
	 */
	public void webapi20122_edit(CMi306 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * TODO 参数验证
		 */

		/*
		 * 修改向导步骤信息
		 */
		form.setDatemodified(CommonUtil.getSystemDate());
		int count = this.getMi306Dao().updateByPrimaryKeySelective(form);
		/*
		 * 无对应数据抛出异常
		 */
		if (count == 0) {
			log.error(ERROR.UPDATE_NO_DATA.getLogText("CMi306", form
					.getStepId()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.UPDATE_NO_DATA.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yondervision.mi.service.WebApi201Service#webapi20122_delete(com.
	 * yondervision.mi.dto.CMi306)
	 */
	public void webapi20122_delete(CMi306 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form.getStepId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("stepId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "步骤向导ID");
		}

		/*
		 * 验证此向导步骤下是否有向导内容
		 */
		Mi308Example example = new Mi308Example();
		Mi308Example.Criteria mi308Criteria = example.createCriteria()
				.andStepIdEqualTo(form.getStepId());
		mi308Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		int mi308Count = this.getcMi308Dao().countByExample(example);
		// 要删除的向导步骤下有向导内容则不能删除
		if (mi308Count > 0) {
			throw new NoRollRuntimeErrorException(WEB_ALERT.MI306_COUNT_DELETE
					.getValue(), String.valueOf(mi308Count));
		}

		/*
		 * 将业务咨询向导ID为stepId的数据设置为无效
		 */
		Mi306 mi306 = new Mi306();
		mi306.setStepId(form.getStepId());
		mi306.setValidflag(Constants.IS_NOT_VALIDFLAG);
		mi306.setDatemodified(CommonUtil.getSystemDate());
		int count = this.getMi306Dao().updateByPrimaryKeySelective(mi306);

		/*
		 * 无对应数据抛出异常
		 */
		if (count == 0) {
			log.error(ERROR.UPDATE_NO_DATA.getLogText("CMi306", form
					.getStepId()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.UPDATE_NO_DATA.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yondervision.mi.service.WebApi201Service#webapi20122_sort(com.
	 * yondervision.mi.form.WebApi20122_storForm)
	 */
	@Transactional(noRollbackFor = NoRollRuntimeErrorException.class)
	public void webapi20122_sort(WebApi20122_storForm form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * TODO 参数验证
		 */

		/*
		 * 拖拽方向判断
		 */
		boolean isUp; // true=向上拖拽；false=向下拖拽
		Integer start; // 较小的顺序号
		Integer end; // 较大的顺序号
		if (form.getSourceOrderNo().compareTo(form.getTargetOrderNo()) > 0) {
			isUp = true;
			start = form.getTargetOrderNo();
			end = form.getSourceOrderNo();
		} else {
			isUp = false;
			start = form.getSourceOrderNo();
			end = form.getTargetOrderNo();
		}

		/*
		 * 查询出排序将要影响的业务咨询向导步骤数据
		 */
		Mi306Example mi306Example = new Mi306Example();
		mi306Example.setOrderByClause("step asc");
		Mi306Example.Criteria mi306Criteria = mi306Example.createCriteria()
				.andConsultSubItemIdEqualTo(form.getConsultSubItemId());
		mi306Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		mi306Criteria.andStepBetween(start, end);
		@SuppressWarnings("unchecked")
		List<Mi306> listMi306 = this.getMi306Dao()
				.selectByExample(mi306Example);
		// 未找到数据时报出异常
		if (CommonUtil.isEmpty(listMi306)) {
			log.error(ERROR.NO_DATA.getLogText("MI306", mi306Criteria
					.getCriteriaWithSingleValue().toString()
					+ mi306Criteria.getCriteriaWithBetweenValue().toString()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),
					"业务咨询向导步骤");
		}

		/*
		 * 更新业务咨询向导步骤的顺序号
		 */
		try {
			for (Iterator<Mi306> iterator = listMi306.iterator(); iterator
					.hasNext();) {
				Mi306 mi306 = (Mi306) iterator.next();
				boolean isChanged = false;

				/*
				 * 向上拖拽——除源顺序号外，其它受影响的顺序号加1
				 */
				if (isUp) {
					if (form.getSourceOrderNo().compareTo(mi306.getStep()) != 0) {
						mi306.setStep(mi306.getStep() + 1);
						isChanged = true;
					}
				}
				/*
				 * 向下拖拽——除源顺序号外，其它受影响的顺序号减1
				 */
				if (!isUp) {
					if (form.getSourceOrderNo().compareTo(mi306.getStep()) != 0) {
						mi306.setStep(mi306.getStep() - 1);
						isChanged = true;
					}
				}
				/*
				 * 源顺序号设置为目标顺序号
				 */
				if (!isChanged
						&& form.getSourceOrderNo().compareTo(mi306.getStep()) == 0) {
					mi306.setStep(form.getTargetOrderNo());
				}
				/*
				 * 更新序号和最后修改时间
				 */
				mi306.setDatemodified(CommonUtil.getSystemDate());
				this.getMi306Dao().updateByPrimaryKey(mi306);
			}
		} catch (Exception e) {
			log.error(ERROR.SYS.getLogText(e.getMessage()), e);
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.SYS.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yondervision.mi.service.WebApi201Service#webapi20124_sort(com.
	 * yondervision.mi.form.WebApi20124_storForm)
	 */
	@Transactional(noRollbackFor = NoRollRuntimeErrorException.class)
	public void webapi20124_sort(WebApi20124_storForm form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * TODO 参数验证
		 */

		/*
		 * 拖拽方向判断
		 */
		boolean isUp; // true=向上拖拽；false=向下拖拽
		Integer start; // 较小的顺序号
		Integer end; // 较大的顺序号
		if (form.getSourceOrderNo().compareTo(form.getTargetOrderNo()) > 0) {
			isUp = true;
			start = form.getTargetOrderNo();
			end = form.getSourceOrderNo();
		} else {
			isUp = false;
			start = form.getSourceOrderNo();
			end = form.getTargetOrderNo();
		}

		/*
		 * 查询出排序将要影响的业务咨询向导内容数据
		 */
		Mi308Example mi308Example = new Mi308Example();
		mi308Example.setOrderByClause("order_no asc");
		Mi308Example.Criteria mi308Criteria = mi308Example.createCriteria()
				.andStepIdEqualTo(form.getStepId());
		mi308Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		mi308Criteria.andParentIdEqualTo(form.getParentId());
		mi308Criteria.andOrderNoBetween(start, end);
		@SuppressWarnings("unchecked")
		List<Mi308> listMi308 = this.getcMi308Dao().selectByExample(
				mi308Example);
		// 未找到数据时报出异常
		if (CommonUtil.isEmpty(listMi308)) {
			log.error(ERROR.NO_DATA.getLogText("MI308", mi308Criteria
					.getCriteriaWithSingleValue().toString()
					+ mi308Criteria.getCriteriaWithBetweenValue().toString()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),
					"业务咨询向导内容");
		}

		/*
		 * 更新业务咨询向导内容的顺序号
		 */
		try {
			for (Iterator<Mi308> iterator = listMi308.iterator(); iterator
					.hasNext();) {
				Mi308 mi308 = (Mi308) iterator.next();
				boolean isChanged = false;

				/*
				 * 向上拖拽——除源顺序号外，其它受影响的顺序号加1
				 */
				if (isUp) {
					if (form.getSourceOrderNo().compareTo(mi308.getOrderNo()) != 0) {
						mi308.setOrderNo(mi308.getOrderNo() + 1);
						isChanged = true;
					}
				}
				/*
				 * 向下拖拽——除源顺序号外，其它受影响的顺序号减1
				 */
				if (!isUp) {
					if (form.getSourceOrderNo().compareTo(mi308.getOrderNo()) != 0) {
						mi308.setOrderNo(mi308.getOrderNo() - 1);
						isChanged = true;
					}
				}
				/*
				 * 源顺序号设置为目标顺序号
				 */
				if (!isChanged
						&& form.getSourceOrderNo()
								.compareTo(mi308.getOrderNo()) == 0) {
					mi308.setOrderNo(form.getTargetOrderNo());
				}
				/*
				 * 更新序号和最后修改时间
				 */
				mi308.setDatemodified(CommonUtil.getSystemDate());
				this.getcMi308Dao().updateByPrimaryKey(mi308);
			}
		} catch (Exception e) {
			log.error(ERROR.SYS.getLogText(e.getMessage()), e);
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.SYS.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervision.mi.service.WebApi201Service#webapi20124_add(com.yondervision
	 * .mi.dto.CMi308)
	 */
	@Transactional(noRollbackFor = NoRollRuntimeErrorException.class)
	public Mi308 webapi20124_add(CMi308 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * TODO 参数验证
		 */

		try {
			// /*
			// * 根据条件判断是否添加公共条件组合
			// */
			// List<String> listConditionId = form.getListConditionId();
			// if (CommonUtil.isEmpty(form.getConditionItemGroupId())
			// && (!CommonUtil.isEmpty(listConditionId))) {
			// String conditionItemGroupId = CommonUtil.genKey("MI307", 0);
			// form.setConditionItemGroupId(conditionItemGroupId);
			// if (CommonUtil.isEmpty(conditionItemGroupId)) {
			// log.error(ERROR.NULL_KEY.getLogText("MI307"));
			// throw new NoRollRuntimeErrorException(WEB_ALERT.SYS
			// .getValue(), ERROR.NULL_KEY.getValue());
			// }
			// String consultSubItemId = this.getMi306Dao()
			// .selectByPrimaryKey(form.getStepId())
			// .getConsultSubItemId();
			// String consultItemId = this.getMi302Dao().selectByPrimaryKey(
			// consultSubItemId).getConsultItemId();
			// Mi307 mi307 = new Mi307();
			// mi307.setConditionItemGroupId(conditionItemGroupId);
			// mi307.setConsultItemId(consultItemId);
			// int conditionIdNo = 0;
			// for (Iterator<String> iterator = listConditionId.iterator();
			// iterator
			// .hasNext();) {
			// String conditionId = (String) iterator.next();
			// if (CommonUtil.isEmpty(conditionId)) {
			// break;
			// }
			// conditionIdNo++;
			// JavaBeanUtil.setter(mi307, "conditionId" + conditionIdNo,
			// conditionId);
			// }
			// mi307.setValidflag(Constants.IS_VALIDFLAG);
			// mi307.setDatecreated(CommonUtil.getSystemDate());
			// mi307.setDatemodified(CommonUtil.getSystemDate());
			//
			// this.getMi307Dao().insert(mi307);
			//
			// }

			/*
			 * 添加向导内容
			 */
			String consultId = commonUtil.genKey("MI308", 0);
			if (CommonUtil.isEmpty(consultId)) {
				log.error(ERROR.NULL_KEY.getLogText("MI308"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
						ERROR.NULL_KEY.getValue());
			}
			form.setConsultId(consultId);
			form.setIsLeafFlg(Constants.IS_LEAF_FLG);
			form.setValidflag(Constants.IS_VALIDFLAG);
			form.setDatecreated(CommonUtil.getSystemDate());
			form.setDatemodified(CommonUtil.getSystemDate());
			this.getcMi308Dao().insert(form);
		} catch (NoRollRuntimeErrorException ne) {
			throw ne;
		} catch (Exception e) { // 出现未知异常回滚
			log.error(ERROR.SYS.getLogText(e.getMessage()), e);
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.SYS.getValue());
		}
		return form;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yondervision.mi.service.WebApi201Service#webapi20124_edit(com.
	 * yondervision.mi.dto.CMi308)
	 */
	public void webapi20124_edit(CMi308 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * TODO 参数验证
		 */

		/*
		 * 修改向导步骤信息
		 */
		form.setDatemodified(CommonUtil.getSystemDate());
		int count = this.getcMi308Dao().updateByPrimaryKeySelective(form);
		/*
		 * 无对应数据抛出异常
		 */
		if (count == 0) {
			log.error(ERROR.UPDATE_NO_DATA.getLogText("CMi308", form
					.getCenterId()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.UPDATE_NO_DATA.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yondervision.mi.service.WebApi201Service#webapi20124_delete(com.
	 * yondervision.mi.form.WebApi20124_deleteForm)
	 */
	@SuppressWarnings("unchecked")
	@Transactional(noRollbackFor = NoRollRuntimeErrorException.class)
	public void webapi20124_delete(WebApi20124_deleteForm form)
			throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		List<String> listConsultId = form.getListConsultId();
		if (CommonUtil.isEmpty(listConsultId)) {
			log.error(ERROR.PARAMS_NULL.getLogText("listConsultId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "向导内容ID");
		}
		try {
			/*
			 * 每条咨询内容都需要验证本身和上级咨询内容是否配置公共条件组合。
			 * 如本身已设置公共条件组合且（上级未设置公共条件组合或无上级）时需要把对应的公共条件组合设置为无效
			 */
			for (Iterator<String> iterator = listConsultId.iterator(); iterator
					.hasNext();) {
				String consultId = (String) iterator.next();
				Mi308 mi308 = this.getcMi308Dao().selectByPrimaryKey(consultId);
				if (CommonUtil.isEmpty(mi308)) {
					log.error(ERROR.NO_DATA.getLogText("MI308", "consultId="
							+ consultId));
					throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA
							.getValue(), "业务咨询向导内容");
				}
				mi308.setValidflag(Constants.IS_NOT_VALIDFLAG);
				mi308.setDatemodified(CommonUtil.getSystemDate());
				this.getcMi308Dao().updateByPrimaryKeySelective(mi308);

				/*
				 * 删除mi312表中包含此向导内容的数据， 并且删除mi311表中仅包含此内容的组合
				 */
				Mi312Example mi312Example = new Mi312Example();
				Mi312Example.Criteria mi312Criteria = mi312Example
						.createCriteria();
				mi312Criteria.andConsultIdEqualTo(mi308.getConsultId());
				mi312Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				List<Mi312> listMi312 = this.getcMi312Dao().selectByExample(
						mi312Example);
				if (CommonUtil.isEmpty(listMi312)) {
					// 此向导内容未配置条件组合
					log.debug(ERROR.NO_DATA.getLogText("MI312", mi312Criteria
							.getCriteriaWithSingleValue().toString()));
					continue;
				}
				// 判断此向导内容用到的条件组合ID是否被其它向导内容使用，如无则删除mi311表中组合ID与之相同的数据
				for (Iterator<Mi312> iterator2 = listMi312.iterator(); iterator2
						.hasNext();) {
					Mi312 mi312 = (Mi312) iterator2.next();
					Mi312Example mi312E = new Mi312Example();
					mi312E.createCriteria().andConditionRadioIdEqualTo(
							mi312.getConditionRadioId()).andValidflagEqualTo(
							Constants.IS_VALIDFLAG);
					int iMi312Count = this.getcMi312Dao()
							.countByExample(mi312E);
					if (iMi312Count == 1) {
						Mi311Example mi311Example = new Mi311Example();
						mi311Example.createCriteria()
								.andConditionRadioIdEqualTo(
										mi312.getConditionRadioId());
						this.getcMi311Dao().deleteByExample(mi311Example);
					}
				}
				// 删除mi312表中包含此向导内容的数据
				this.getcMi312Dao().deleteByExample(mi312Example);

				// /*
				// * 未配置公共条件组合直接将此条记录设置为无效
				// */
				// if (CommonUtil.isEmpty(mi308.getConditionItemGroupId())) {
				// this.getcMi308Dao().updateByPrimaryKeySelective(mi308);
				// }
				// /*
				// * 已配置公共条件组合查询上级业务咨询内容
				// */
				// if (!CommonUtil.isEmpty(mi308.getConditionItemGroupId())) {
				// // 此咨询内容为顶级时将此条记录和对应的公共条件组合数据设置为无效
				// if (Constants.MI308_SRC_PARENT_ID.equals(mi308
				// .getParentId())) {
				// Mi307 mi307 = new Mi307();
				// mi307.setConditionItemGroupId(mi308
				// .getConditionItemGroupId());
				// mi307.setValidflag(Constants.IS_NOT_VALIDFLAG);
				// mi307.setDatemodified(CommonUtil.getSystemDate());
				// this.getMi307Dao().updateByPrimaryKeySelective(mi307);
				// this.getcMi308Dao().updateByPrimaryKeySelective(mi308);
				// }
				// // 此咨询内容不是顶级时查询上级咨询内容
				// else {
				// Mi308 parentMi308 = this.getcMi308Dao()
				// .selectByPrimaryKey(mi308.getParentId());
				// if (CommonUtil.isEmpty(parentMi308)) {
				// log.error(ERROR.NO_DATA.getLogText("MI308",
				// "consultId=" + mi308.getParentId()));
				// throw new NoRollRuntimeErrorException(
				// WEB_ALERT.NO_DATA.getValue(), "上级业务咨询向导内容");
				// }
				// // 上级咨询内容未设置公共条件组合时将此条记录和对应的公共条件组合数据设置为无效
				// if
				// (CommonUtil.isEmpty(parentMi308.getConditionItemGroupId())) {
				// Mi307 mi307 = new Mi307();
				// mi307.setConditionItemGroupId(mi308
				// .getConditionItemGroupId());
				// mi307.setValidflag(Constants.IS_NOT_VALIDFLAG);
				// mi307.setDatemodified(CommonUtil.getSystemDate());
				// this.getMi307Dao().updateByPrimaryKeySelective(
				// mi307);
				// this.getcMi308Dao().updateByPrimaryKeySelective(
				// mi308);
				// }
				// // 上级咨询内容已设置公共条件组合时只将此条记录和设置为无效
				// else {
				// this.getcMi308Dao().updateByPrimaryKeySelective(
				// mi308);
				// }
				// }
				// }
			}
		} catch (NoRollRuntimeErrorException ne) {
			throw ne;
		} catch (Exception e) {
			log.error(ERROR.SYS.getLogText(e.getMessage()), e);
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.SYS.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yondervision.mi.service.WebApi201Service#webapi20125_query(com.
	 * yondervision.mi.form.WebApi20125_queryForm)
	 */
	@SuppressWarnings("unchecked")
	public List<WebApi20125Result> webapi20125_query(WebApi20125_queryForm form)
			throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		List<String> listConditionId = form.getListConditionId();
		if (CommonUtil.isEmpty(listConditionId)) {
			log.error(ERROR.PARAMS_NULL.getLogText("listConditionId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "条件内容ID");
		}
		if (CommonUtil.isEmpty(form.getConsultItemId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("consultItemId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "业务咨询项目ID");
		}
		if (CommonUtil.isEmpty(form.getStepId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("stepId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "向导步骤ID");
		}

		List<WebApi20125Result> result = new ArrayList<WebApi20125Result>();

		/*
		 * 查询公共向导内容ID
		 */
		Mi312Example commi312Example = new Mi312Example();
		Mi312Example.Criteria commi312Criteria = commi312Example
				.createCriteria().andStepIdEqualTo(form.getStepId())
				.andConditionRadioIdEqualTo(
						Constants.MI312_CONDITION_RADIO_ID_COM)
				.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi312> listMi312Com = this.getcMi312Dao().selectByExample(
				commi312Example);
		if (!CommonUtil.isEmpty(listMi312Com)) {
			for (Iterator<Mi312> iterator = listMi312Com.iterator(); iterator
					.hasNext();) {
				Mi312 mi312 = (Mi312) iterator.next();
				WebApi20125Result rs = new WebApi20125Result();
				rs.setConsultId(mi312.getConsultId());
				rs.setReadOnly(true);
				rs.setUsedConditionItemOrderNo("公共咨询内容");
				result.add(rs);
			}
		} else {
			log.debug(ERRCODE.ERROR.NO_DATA.getLogText("MI312",
					commi312Criteria.getCriteriaWithSingleValue().toString()));
		}

		/*
		 * 查询符合此条件组合的向导内容ID
		 */
		// 与选中条件完全符合的条件组合
		Mi311 mi311 = this.getcMi311Dao().selectByListConditionId(
				listConditionId);
		if (CommonUtil.isEmpty(mi311)) {
			mi311 = new Mi311();
		}
		// 所有适用于选中条件的条件组合
		List<Mi311> listMi311 = this.getcMi311Dao().selectAllByListConditionId(
				listConditionId);
		// 将符合此条件组合的公共条件组合ID放入conditionRadioId中，
		// 并且将各个组合所使用的公共条件项目顺序号放入到mapConditionOrderNo中
		List<String> conditionRadioId = new ArrayList<String>();
		Map<String, List<Integer>> mapConditionOrderNo = new HashMap<String, List<Integer>>();
		for (Iterator<Mi311> iterator = listMi311.iterator(); iterator
				.hasNext();) {
			Mi311 temp = (Mi311) iterator.next();
			// 放入公共条件组合ID
			conditionRadioId.add(temp.getConditionRadioId());

			// 查询此公共条件对应的公共条件项目顺序号
			List<Integer> listTemp = mapConditionOrderNo.get(temp
					.getConditionRadioId());
			if (listTemp == null) { // 此条件组合未添加过才进行添加
				listTemp = new ArrayList<Integer>();
				mapConditionOrderNo.put(temp.getConditionRadioId(), listTemp);

				Mi311Example mi311Example = new Mi311Example();
				mi311Example.createCriteria().andConditionRadioIdEqualTo(
						temp.getConditionRadioId()).andValidflagEqualTo(
						Constants.IS_VALIDFLAG);
				List<Mi311> listMi311Temp = this.getcMi311Dao()
						.selectByExample(mi311Example);
				for (Iterator iterator2 = listMi311Temp.iterator(); iterator2
						.hasNext();) {
					Mi311 mi311Temp = (Mi311) iterator2.next();
					Mi305 mi305 = this.getMi305Dao().selectByPrimaryKey(
							mi311Temp.getConditionId());
					Mi304 mi304 = this.getcMi304Dao().selectByPrimaryKey(
							mi305.getConditionGroupId());
					Mi303 mi303 = this.getMi303Dao().selectByPrimaryKey(
							mi304.getConditionItemId());
					listTemp.add(mi303.getItemNo());
				}
			}

		}
		/*
		 * 如果没有要查询的组合则只返回公共咨询内容
		 */
		if (CommonUtil.isEmpty(conditionRadioId)) {
			return result;
		}

		// 查询所有适用于选中条件的向导内容ID
		Mi312Example mi312Example = new Mi312Example();
		Mi312Example.Criteria mi312Criteria = mi312Example.createCriteria()
				.andConditionRadioIdIn(conditionRadioId).andValidflagEqualTo(
						Constants.IS_VALIDFLAG);
		List<Mi312> listMi312 = this.getcMi312Dao().selectByExample(
				mi312Example);
		if (!CommonUtil.isEmpty(listMi312)) {
			for (Iterator<Mi312> iterator = listMi312.iterator(); iterator
					.hasNext();) {
				Mi312 mi312 = (Mi312) iterator.next();
				WebApi20125Result rs = new WebApi20125Result();
				rs.setConsultId(mi312.getConsultId());
				// 与此条件组合完全匹配时不只读（false）,否则为只读(true)
				rs.setReadOnly(!mi312.getConditionRadioId().equals(
						mi311.getConditionRadioId()));
				rs.setUsedConditionItemOrderNo(this
						.getStringFromListInt(mapConditionOrderNo.get(mi312
								.getConditionRadioId())));
				result.add(rs);
			}
		} else {
			log.debug(ERRCODE.ERROR.NO_DATA.getLogText("MI312", mi312Criteria
					.getCriteriaWithSingleValue().toString()
					+ mi312Criteria.getCriteriaWithListValue().toString()));
		}

		return result;
	}

	/**
	 * 将公共条件项目顺序号列表转化为页面要显示的字符串
	 * 
	 * @param list
	 *            公共条件项目ID顺序号列表
	 * @return 页面要显示的字符串
	 */
	private String getStringFromListInt(List<Integer> list) {
		if (CommonUtil.isEmpty(list)) {
			return "公共咨询内容";
		}
		Collections.sort(list);
		String[] tempString = new String[] { "①", "②", "③", "④", "⑤", "⑥", "⑦",
				"⑧" };
		StringBuffer str = new StringBuffer();
		for (Iterator<Integer> iterator = list.iterator(); iterator.hasNext();) {
			Integer integer = (Integer) iterator.next();
			if (integer > tempString.length) {
				str.append(integer);
			} else {
				str.append(tempString[integer - 1]);
			}
		}

		return str.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yondervision.mi.service.WebApi201Service#webapi20125_save(com.
	 * yondervision.mi.form.WebApi20125_saveForm)
	 */
	@Transactional(noRollbackFor = NoRollRuntimeErrorException.class)
	public void webapi20125_save(WebApi20125_saveForm form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		List<String> listConditionId = form.getListConditionId();
		List<String> listConsultId = form.getListConsultId();
		if (CommonUtil.isEmpty(listConditionId)) {
			log.error(ERROR.PARAMS_NULL.getLogText("listConditionId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "条件内容ID");
		}
		// if (CommonUtil.isEmpty(listConsultId)) {
		// log.error(ERROR.PARAMS_NULL.getLogText("listConsultId"));
		// throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
		// .getValue(), "向导内容ID");
		// }
		if (CommonUtil.isEmpty(form.getConsultItemId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("consultItemId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "业务咨询项目ID");
		}
		if (CommonUtil.isEmpty(form.getStepId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("stepId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "向导步骤ID");
		}
		try {
			/*
			 * 判断mi311表中是否已有此组合
			 */
			boolean needDeleteMi312 = true;
			Mi311 mi311 = this.getcMi311Dao().selectByListConditionId(
					listConditionId);
			// mi311表中没有此组合时加入此组合
			if (CommonUtil.isEmpty(mi311)) {
				mi311 = new Mi311();
				needDeleteMi312 = false;
				String conditionRadioId = commonUtil.genKey("MI311", 0);
				for (Iterator<String> iterator = listConditionId.iterator(); iterator
						.hasNext();) {
					String string = (String) iterator.next();
					mi311.setConditionId(string);
					mi311.setConditionRadioId(conditionRadioId);
					mi311.setConsultItemId(form.getConsultItemId());
					mi311.setDatecreated(CommonUtil.getSystemDate());
					mi311.setValidflag(Constants.IS_VALIDFLAG);
					this.getcMi311Dao().insert(mi311);
				}
			}

			/*
			 * 将映射关系保存到mi312表中
			 */
			// 如果已经配置过映射则删除之前的配置
			if (needDeleteMi312) {
				Mi312Example mi312Example = new Mi312Example();
				mi312Example.createCriteria().andConditionRadioIdEqualTo(
						mi311.getConditionRadioId()).andStepIdEqualTo(
						form.getStepId());
				this.getcMi312Dao().deleteByExample(mi312Example);
			}
			// 如果没有咨询内容则查询此公共条件组合是否被其它咨询内容使用，如未使用则删除此组合
			if (CommonUtil.isEmpty(listConsultId)) {
				Mi312Example mi312Example = new Mi312Example();
				mi312Example.createCriteria().andConditionRadioIdEqualTo(
						mi311.getConditionRadioId()).andValidflagEqualTo(
						Constants.IS_VALIDFLAG);
				int count = this.getcMi312Dao().countByExample(mi312Example);
				if (count==0) {
					Mi311Example mi311Example = new Mi311Example();
					mi311Example.createCriteria().andConditionRadioIdEqualTo(mi311.getConditionRadioId());
					this.getcMi311Dao().deleteByExample(mi311Example);
				}
				return;
			}
			// 将新的映射关系插入到mi312表中
			for (Iterator<String> iterator = listConsultId.iterator(); iterator
					.hasNext();) {
				String string = (String) iterator.next();
				Mi312 mi312 = new Mi312();
				mi312.setConditionRadioId(mi311.getConditionRadioId());
				mi312.setConsultId(string);
				mi312.setConsultItemId(form.getConsultItemId());
				mi312.setDatecreated(CommonUtil.getSystemDate());
				mi312.setStepId(form.getStepId());
				mi312.setValidflag(Constants.IS_VALIDFLAG);
				this.getcMi312Dao().insert(mi312);
			}
		} catch (NoRollRuntimeErrorException ne) {
			throw ne;
		} catch (Exception e) {
			log.error(ERROR.SYS.getLogText(e.getMessage()), e);
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.SYS.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yondervision.mi.service.WebApi201Service#webapi20126_query(com.
	 * yondervision.mi.form.WebApi20126_queryForm)
	 */
	public List<String> webapi20126_query(WebApi20126_queryForm form)
			throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form.getStepId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("stepId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "向导步骤ID");
		}

		List<String> conditionRadioId = new ArrayList<String>();
		conditionRadioId.add(Constants.MI312_CONDITION_RADIO_ID_COM);

		List<String> result = this.getcMi312Dao()
				.selectListConsultIdByListConditionRadioId(conditionRadioId,
						form.getStepId());

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yondervision.mi.service.WebApi201Service#webapi20126_save(com.
	 * yondervision.mi.form.WebApi20126_saveForm)
	 */
	public void webapi20126_save(WebApi20126_saveForm form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		List<String> listConsultId = form.getListConsultId();
		// if (CommonUtil.isEmpty(listConsultId)) {
		// log.error(ERROR.PARAMS_NULL.getLogText("listConsultId"));
		// throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
		// .getValue(), "向导内容ID");
		// }
		if (CommonUtil.isEmpty(form.getConsultItemId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("consultItemId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "业务咨询项目ID");
		}
		if (CommonUtil.isEmpty(form.getStepId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("stepId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "向导步骤ID");
		}

		try {
			/*
			 * 将公共咨询内容ID组保存到mi312表中
			 */
			// 删除之前配置的公共咨询内容ID组
			Mi312Example mi312Example = new Mi312Example();
			mi312Example.createCriteria().andConditionRadioIdEqualTo(
					Constants.MI312_CONDITION_RADIO_ID_COM).andStepIdEqualTo(
					form.getStepId());
			this.getcMi312Dao().deleteByExample(mi312Example);
			// 将新的映射关系插入到mi312表中
			if (CommonUtil.isEmpty(listConsultId)) {
				return;
			}
			for (Iterator<String> iterator = listConsultId.iterator(); iterator
					.hasNext();) {
				String string = (String) iterator.next();
				Mi312 mi312 = new Mi312();
				mi312
						.setConditionRadioId(Constants.MI312_CONDITION_RADIO_ID_COM);
				mi312.setConsultId(string);
				mi312.setConsultItemId(form.getConsultItemId());
				mi312.setDatecreated(CommonUtil.getSystemDate());
				mi312.setStepId(form.getStepId());
				mi312.setValidflag(Constants.IS_VALIDFLAG);
				this.getcMi312Dao().insert(mi312);
			}
		} catch (NoRollRuntimeErrorException ne) {
			throw ne;
		} catch (Exception e) {
			log.error(ERROR.SYS.getLogText(e.getMessage()), e);
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.SYS.getValue());
		}
	}

}
