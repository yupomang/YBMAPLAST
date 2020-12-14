package com.yondervision.mi.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.APP_ALERT;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi308DAO;
import com.yondervision.mi.dao.CMi311DAO;
import com.yondervision.mi.dao.CMi312DAO;
import com.yondervision.mi.dao.Mi301DAO;
import com.yondervision.mi.dao.Mi302DAO;
import com.yondervision.mi.dao.Mi303DAO;
import com.yondervision.mi.dao.Mi304DAO;
import com.yondervision.mi.dao.Mi305DAO;
import com.yondervision.mi.dao.Mi306DAO;
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
import com.yondervision.mi.form.AppApi20101Form;
import com.yondervision.mi.form.AppApi20102Form;
import com.yondervision.mi.form.AppApi20103Form;
import com.yondervision.mi.result.AppApi20101Result;
import com.yondervision.mi.result.AppApi20101Result01;
import com.yondervision.mi.result.AppApi20102Result;
import com.yondervision.mi.result.AppApi20102Result_conditiongroups;
import com.yondervision.mi.result.AppApi20102Result_group;
import com.yondervision.mi.result.AppApi20103Result;
import com.yondervision.mi.result.AppApi20103Result_guiderelation;
import com.yondervision.mi.service.AppApi201Service;
import com.yondervision.mi.util.CommonUtil;

public class AppApi201ServiceImpl implements AppApi201Service {

	private Mi301DAO mi301Dao;
	private Mi302DAO mi302Dao;
	private Mi303DAO mi303Dao;
	private Mi304DAO mi304Dao;
	private Mi305DAO mi305Dao;
	private Mi306DAO mi306Dao;
	private CMi308DAO cMi308Dao;
	private CMi311DAO cMi311Dao;
	private CMi312DAO cMi312Dao;

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
	 * @return the mi304Dao
	 */
	public Mi304DAO getMi304Dao() {
		return mi304Dao;
	}

	/**
	 * @param mi304Dao
	 *            the mi304Dao to set
	 */
	public void setMi304Dao(Mi304DAO mi304Dao) {
		this.mi304Dao = mi304Dao;
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

	@SuppressWarnings("unchecked")
	public List<AppApi20101Result> appApi20101(AppApi20101Form form)
			throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}
		if (CommonUtil.isEmpty(form.getBussinesstype())) {
			log.error(ERROR.PARAMS_NULL.getLogText("bussinesstype"));
			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
					.getValue(), "业务类型");
		}
		// 存放业务咨询子项图标目录的配置名（properties.properties）
		String purlParam = "consult_sub_item_ioc_path";
		List<AppApi20101Result> result = new ArrayList<AppApi20101Result>();

		/*
		 * 查询业务咨询项目数据
		 */
		Mi301Example mi301Example = new Mi301Example();
		mi301Example.setOrderByClause("order_no asc");
		Mi301Example.Criteria mi301Criteria = mi301Example.createCriteria();
		mi301Criteria.andCenteridEqualTo(form.getCenterId());
		mi301Criteria.andConsultTypeEqualTo(form.getBussinesstype());
		mi301Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi301> listMi301 = this.getMi301Dao()
				.selectByExample(mi301Example);
		/*
		 * 查询结果验证
		 */
		if (CommonUtil.isEmpty(listMi301)) {
			log.error(ERROR.NO_DATA.getLogText("MI301", mi301Criteria
					.getCriteriaWithSingleValue().toString()));
			throw new NoRollRuntimeErrorException(APP_ALERT.NO_DATA.getValue(),
					"业务咨询项目");
		}

		/*
		 * 遍历业务咨询项目数据
		 */
		for (Iterator<Mi301> iterator = listMi301.iterator(); iterator
				.hasNext();) {
			Mi301 mi301 = (Mi301) iterator.next();

			AppApi20101Result appApi20101Result = new AppApi20101Result();

			appApi20101Result.setConsultitem(mi301.getItemName());
			appApi20101Result.setConsultitemid(mi301.getConsultItemId());
			appApi20101Result.setConditionTitle(mi301.getConditionTitle());

			/*
			 * 查询业务咨询项目下是否含有公共条件
			 */
			Mi303Example mi303Example = new Mi303Example();
			mi303Example.setOrderByClause("order_no asc");
			Mi303Example.Criteria mi303Criteria = mi303Example.createCriteria();
			mi303Criteria.andConsultItemIdEqualTo(mi301.getConsultItemId());
			mi303Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			int mi303Count = this.getMi303Dao().countByExample(mi303Example);
			// 界面调用向导分类：guide (10 分步向导，20 公共条件)
			appApi20101Result.setGuide(mi303Count > 0 ? "20" : "10");

			List<AppApi20101Result01> consultsubitem = new ArrayList<AppApi20101Result01>();

			/*
			 * 查询业务咨询子项数据
			 */
			Mi302Example mi302Example = new Mi302Example();
			mi302Example.setOrderByClause("order_no asc");
			Mi302Example.Criteria mi302Criteria = mi302Example.createCriteria();
			mi302Criteria.andConsultItemIdEqualTo(mi301.getConsultItemId());
			mi302Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			List<Mi302> listMi302 = this.getMi302Dao().selectByExample(
					mi302Example);
			/*
			 * 查询结果验证
			 */
			if (CommonUtil.isEmpty(listMi302)) {
				log.debug(ERROR.NO_DATA.getLogText("MI302", mi302Criteria
						.getCriteriaWithSingleValue().toString()));
			}
			for (Iterator<Mi302> iterator2 = listMi302.iterator(); iterator2
					.hasNext();) {
				Mi302 mi302 = (Mi302) iterator2.next();

				// 取得业务咨询子项图标的下载路径
				String iconURL = CommonUtil.getDownloadFileUrl(purlParam, mi302
						.getIconId(), false);

				AppApi20101Result01 appApi20101Result01 = new AppApi20101Result01();
				appApi20101Result01.setImageurl(iconURL);
				appApi20101Result01.setTitle(mi302.getSubitemName());
				appApi20101Result01.setTitleid(mi302.getConsultSubItemId());
				consultsubitem.add(appApi20101Result01);
			}
			appApi20101Result.setConsultsubitem(consultsubitem);

			result.add(appApi20101Result);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervision.mi.service.AppApi201Service#appApi20102(com.yondervision
	 * .mi.form.AppApi20102Form)
	 */
	@SuppressWarnings("unchecked")
	public List<AppApi20102Result> appApi20102(AppApi20102Form form)
			throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form.getConsultitemid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("consultitemid"));
			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
					.getValue(), "业务咨询项目ID");
		}

		List<AppApi20102Result> result = new ArrayList<AppApi20102Result>();

		/*
		 * 查询公共条件项目数据
		 */
		Mi303Example mi303Example = new Mi303Example();
		mi303Example.setOrderByClause("item_no asc");
		Mi303Example.Criteria mi303Criteria = mi303Example.createCriteria();
		mi303Criteria.andConsultItemIdEqualTo(form.getConsultitemid());
		mi303Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi303> listMi303 = this.getMi303Dao()
				.selectByExample(mi303Example);
		// 查询结果验证
		if (CommonUtil.isEmpty(listMi303)) {
			log.debug(ERROR.NO_DATA.getLogText("MI303", mi303Criteria
					.getCriteriaWithSingleValue().toString()));
			throw new NoRollRuntimeErrorException(APP_ALERT.NO_DATA.getValue(),
					"公共条件项目");
		}

		/*
		 * 遍历公共条件项目数据
		 */
		for (Iterator<Mi303> iterator = listMi303.iterator(); iterator
				.hasNext();) {
			Mi303 mi303 = (Mi303) iterator.next();
			AppApi20102Result rs = new AppApi20102Result();
			List<AppApi20102Result_group> resultGroup = new ArrayList<AppApi20102Result_group>();

			rs.setConditionitemid(mi303.getConditionItemId());
			rs.setConditionitemname(mi303.getConditionItemName());

			/*
			 * 查询公共条件分组数据
			 */
			Mi304Example mi304Example = new Mi304Example();
			mi304Example.setOrderByClause("no_oreder asc");
			Mi304Example.Criteria mi304Criteria = mi304Example.createCriteria();
			mi304Criteria.andConditionItemIdEqualTo(mi303.getConditionItemId());
			mi304Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			List<Mi304> listMi304 = this.getMi304Dao().selectByExample(
					mi304Example);
			// 查询结果验证
			if (CommonUtil.isEmpty(listMi304)) {
				log.error(ERROR.NO_DATA.getLogText("MI304", mi304Criteria
						.getCriteriaWithSingleValue().toString()));
			}

			/*
			 * 遍历公共条件分组数据
			 */
			for (Iterator<Mi304> iterator2 = listMi304.iterator(); iterator2
					.hasNext();) {
				Mi304 mi304 = (Mi304) iterator2.next();
				AppApi20102Result_group rsGroup = new AppApi20102Result_group();
				List<AppApi20102Result_conditiongroups> resultConditiongroups = new ArrayList<AppApi20102Result_conditiongroups>();

				rsGroup.setGroupid(mi304.getConditionGroupId());
				rsGroup.setGroupname(mi304.getGroupName());

				/*
				 * 查询公共条件内容数据
				 */
				Mi305Example mi305Example = new Mi305Example();
				mi305Example.setOrderByClause("no_oreder asc");
				Mi305Example.Criteria mi305Criteria = mi305Example
						.createCriteria();
				mi305Criteria.andConditionGroupIdEqualTo(mi304
						.getConditionGroupId());
				mi305Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				List<Mi305> listMi305 = this.getMi305Dao().selectByExample(
						mi305Example);
				// 查询结果验证
				if (CommonUtil.isEmpty(listMi305)) {
					log.debug(ERROR.NO_DATA.getLogText("MI305", mi305Criteria
							.getCriteriaWithSingleValue().toString()));
				}

				for (Iterator<Mi305> iterator3 = listMi305.iterator(); iterator3
						.hasNext();) {
					Mi305 mi305 = (Mi305) iterator3.next();
					AppApi20102Result_conditiongroups rsConditiongroups = new AppApi20102Result_conditiongroups();
					rsConditiongroups.setConditionid(mi305.getConditionId());
					rsConditiongroups.setConditiondetail(mi305
							.getConditionDetail());

					resultConditiongroups.add(rsConditiongroups);
				}

				rsGroup.setConditiongroups(resultConditiongroups);
				resultGroup.add(rsGroup);
			}

			rs.setGroup(resultGroup);
			result.add(rs);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervision.mi.service.AppApi201Service#appApi20103(com.yondervision
	 * .mi.form.AppApi20103Form)
	 */
	@SuppressWarnings("unchecked")
	public List<AppApi20103Result> appApi20103(AppApi20103Form form)
			throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form.getConsultitemid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("consultitemid"));
			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
					.getValue(), "业务咨询项目ID");
		}
		if (CommonUtil.isEmpty(form.getTitleid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("titleid"));
			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
					.getValue(), "业务咨询子项ID");
		}
		List<AppApi20103Result> result = new ArrayList<AppApi20103Result>();

		/*
		 * 查询公共条件分组ID
		 */
		List<String> conditionRadioId = new ArrayList<String>(); // 公共条件组合
		conditionRadioId.add(Constants.MI312_CONDITION_RADIO_ID_COM);
		if (CommonUtil.isEmpty(form.getConditionid1())) {
			log.debug(ERROR.PARAMS_NULL.getLogText("conditionid1"));
		} else {
			List<String> listConditionId = new ArrayList<String>();
			if (!CommonUtil.isEmpty(form.getConditionid1()))
				listConditionId.add(form.getConditionid1());
			if (!CommonUtil.isEmpty(form.getConditionid2()))
				listConditionId.add(form.getConditionid2());
			if (!CommonUtil.isEmpty(form.getConditionid3()))
				listConditionId.add(form.getConditionid3());
			if (!CommonUtil.isEmpty(form.getConditionid4()))
				listConditionId.add(form.getConditionid4());
			if (!CommonUtil.isEmpty(form.getConditionid5()))
				listConditionId.add(form.getConditionid5());
			if (!CommonUtil.isEmpty(form.getConditionid6()))
				listConditionId.add(form.getConditionid6());
			if (!CommonUtil.isEmpty(form.getConditionid7()))
				listConditionId.add(form.getConditionid7());
			if (!CommonUtil.isEmpty(form.getConditionid8()))
				listConditionId.add(form.getConditionid8());

			List<Mi311> listMi311 = this.getcMi311Dao()
					.selectAllByListConditionId(listConditionId);
			// 查询结果验证
			if (CommonUtil.isEmpty(listMi311)) {
				log.error(ERROR.NO_DATA.getLogText("MI311", "Condition_Id"
						+ listConditionId.toString()));
			} else {
				/*
				 * 将符合此条件组合的公共条件组合ID放入conditionRadioId中
				 */
				for (Iterator<Mi311> iterator = listMi311.iterator(); iterator
						.hasNext();) {
					Mi311 mi311 = (Mi311) iterator.next();
					conditionRadioId.add(mi311.getConditionRadioId());
				}
			}
		}

		/*
		 * 查询业务咨询向导步骤数据
		 */
		Mi306Example mi306Example = new Mi306Example();
		mi306Example.setOrderByClause("step asc");
		Mi306Example.Criteria mi306Criteria = mi306Example.createCriteria();
		mi306Criteria.andConsultSubItemIdEqualTo(form.getTitleid());
		mi306Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi306> listMi306 = this.getMi306Dao()
				.selectByExample(mi306Example);
		// 查询结果验证
		if (CommonUtil.isEmpty(listMi306)) {
			log.debug(ERROR.NO_DATA.getLogText("MI306", mi306Criteria
					.getCriteriaWithSingleValue().toString()));
			throw new NoRollRuntimeErrorException(APP_ALERT.NO_DATA.getValue(),
					"业务咨询向导步骤");
		}

		/*
		 * 遍历向导步骤数据
		 */
		for (Iterator<Mi306> iterator = listMi306.iterator(); iterator
				.hasNext();) {
			Mi306 mi306 = (Mi306) iterator.next();
			List<AppApi20103Result_guiderelation> resultGuiderelation = new ArrayList<AppApi20103Result_guiderelation>();
			AppApi20103Result rs = new AppApi20103Result();
			rs.setStepid(mi306.getStepId());
			rs.setStepname(mi306.getStepName());

			/*
			 * 查询满足条件的向导内容ID
			 */
			List<String> listConsultId = this
					.getcMi312Dao()
					.selectListConsultIdByListConditionRadioId(conditionRadioId, mi306.getStepId());

			listConsultId.add(Constants.MI312_CONDITION_RADIO_ID_COM);
			
			/*
			 * 将向导内容数据放入结果集中
			 */
			this.inputMi308Data(resultGuiderelation, mi306.getStepId(),
					Constants.MI308_SRC_PARENT_ID, listConsultId);

			rs.setGuiderelation(resultGuiderelation);

			result.add(rs);
		}

		return result;
	}

	/**
	 * 将业务咨询向导内容加入到结果集中
	 * 
	 * @param result
	 *            结果集（出口参数）
	 * @param stepId
	 *            向导步骤ID
	 * @param parentId
	 *            上级向导内容ID
	 * @param conditionItemGroupId
	 *            满足条件的向导内容ID组
	 */
	@SuppressWarnings("unchecked")
	private boolean inputMi308Data(
			List<AppApi20103Result_guiderelation> result, String stepId,
			String parentId, List<String> listConsultId) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (result == null) {
			log.error(ERROR.PARAMS_NULL.getLogText("result"));
			throw new NoRollRuntimeErrorException(APP_ALERT.SYS.getValue(),
					ERROR.PARAMS_NULL.getValue());
		}
		if (CommonUtil.isEmpty(stepId)) {
			log.error(ERROR.PARAMS_NULL.getLogText("stepId"));
			throw new NoRollRuntimeErrorException(APP_ALERT.SYS.getValue(),
					ERROR.PARAMS_NULL.getValue());
		}
		if (CommonUtil.isEmpty(parentId)) {
			log.error(ERROR.PARAMS_NULL.getLogText("parentId"));
			throw new NoRollRuntimeErrorException(APP_ALERT.SYS.getValue(),
					ERROR.PARAMS_NULL.getValue());
		}

		/*
		 * 查询向导内容数据
		 */
		Mi308Example mi308Example = new Mi308Example();
		mi308Example.setOrderByClause("order_no asc");
		Mi308Example.Criteria mi308Criteria = mi308Example.createCriteria();
		mi308Criteria.andConsultIdIn(listConsultId);
		mi308Criteria.andStepIdEqualTo(stepId);
		mi308Criteria.andParentIdEqualTo(parentId);
		mi308Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);

		List<Mi308> listMi308 = this.getcMi308Dao().selectByExample(
				mi308Example);
		// 查询结果验证
		if (CommonUtil.isEmpty(listMi308)) {
			log.debug(ERROR.NO_DATA.getLogText("MI306", mi308Criteria
					.getCriteriaWithSingleValue().toString()));
			return true;
		}

		/*
		 * 遍历查询结果
		 */
		for (Iterator<Mi308> iterator = listMi308.iterator(); iterator
				.hasNext();) {
			Mi308 mi308 = (Mi308) iterator.next();
			// 将数据放入到结果集中
			List<AppApi20103Result_guiderelation> rsChdren = new ArrayList<AppApi20103Result_guiderelation>();
			AppApi20103Result_guiderelation rs = new AppApi20103Result_guiderelation();

			rs.setConsultid(mi308.getConsultId());
			rs.setDetail(mi308.getDetail());
			rs.setParentid(mi308.getParentId());

			// 取子向导内容放入到结果集中
			boolean isLeafFlg = this.inputMi308Data(rsChdren, stepId, mi308
					.getConsultId(), listConsultId);

			if (isLeafFlg) {
				rs.setIsleafflg(Constants.IS_LEAF_FLG);
			} else {
				rs.setIsleafflg(Constants.IS_NOT_LEAF_FLG);
			}

			rs.setChildren(rsChdren);
			result.add(rs);
		}

		return false;
	}

}
