/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.service.impl
 * 文件名：     YfApi302ServiceImpl.java
 * 创建日期：2013-11-4
 */
package com.yondervision.mi.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.ERRCODE.APP_ALERT;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.Mi100DAO;
import com.yondervision.mi.dao.Mi402DAO;
import com.yondervision.mi.dao.Mi403DAO;
import com.yondervision.mi.dto.Mi100;
import com.yondervision.mi.dto.Mi402;
import com.yondervision.mi.form.YfApi30201Form;
import com.yondervision.mi.form.YfApi30202Form;
import com.yondervision.mi.service.YfApi302Service;
import com.yondervision.mi.util.CommonUtil;

/**
 * @author LinXiaolong
 *
 */
public class YfApi302ServiceImpl implements YfApi302Service {
	
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	
	private Mi100DAO mi100Dao;
	private Mi402DAO mi402Dao;
	private Mi403DAO mi403Dao;

	/**
	 * @return the mi100Dao
	 */
	public Mi100DAO getMi100Dao() {
		return mi100Dao;
	}

	/**
	 * @param mi100Dao the mi100Dao to set
	 */
	public void setMi100Dao(Mi100DAO mi100Dao) {
		this.mi100Dao = mi100Dao;
	}

	/**
	 * @return the mi402Dao
	 */
	public Mi402DAO getMi402Dao() {
		return mi402Dao;
	}

	/**
	 * @param mi402Dao the mi402Dao to set
	 */
	public void setMi402Dao(Mi402DAO mi402Dao) {
		this.mi402Dao = mi402Dao;
	}

	/**
	 * @return the mi403Dao
	 */
	public Mi403DAO getMi403Dao() {
		return mi403Dao;
	}

	/**
	 * @param mi403Dao the mi403Dao to set
	 */
	public void setMi403Dao(Mi403DAO mi403Dao) {
		this.mi403Dao = mi403Dao;
	}

	/* (non-Javadoc)
	 * @see com.yondervision.mi.service.YfApi302Service#yfapi30201(com.yondervision.mi.form.YfApi30201Form)
	 */
	public String yfapi30201(YfApi30201Form form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}
		if (CommonUtil.isEmpty(form.getUserId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("userId"));
			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
					.getValue(), "安全标识");
		}
		if (CommonUtil.isEmpty(form.getBuzType())) {
			log.error(ERROR.PARAMS_NULL.getLogText("buzType"));
			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
					.getValue(), "业务类型");
		}
		if (CommonUtil.isEmpty(form.getAccnum())) {
			log.error(ERROR.PARAMS_NULL.getLogText("accnum"));
			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
					.getValue(), "个人公积金账号");
		}
		if (CommonUtil.isEmpty(form.getTitle())) {
			log.error(ERROR.PARAMS_NULL.getLogText("title"));
			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
					.getValue(), "消息标题");
		}
		if (CommonUtil.isEmpty(form.getDetail())) {
			log.error(ERROR.PARAMS_NULL.getLogText("detail"));
			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
					.getValue(), "消息内容");
		}
		
		/*
		 * 登记MI412
		 */
		String userid = this.selectUserid(form.getAccnum());
		if (CommonUtil.isEmpty(userid)) {
			// TODO 抛出异常
		}
		String seqid = commonUtil.genKey("MI402.SEQID", 0);
		Long msgid = commonUtil.genKey("MI402");
//		Mi402 mi402 = new Mi402();
//		mi402.setSeqid(seqid);
//		mi402.setCenterid(form.getCenterId());
//		mi402.setMsgid(msgid);
//		mi402.setUserid(userid);
//		mi402.setTitle(form.getTitle());
//		mi402.setDetail(form.getDetail());
//		mi402.setPusMessageType(form.getBuzType());
//		mi402.setParam1(this.getCustsvctel(form.getCenterId()));
		
		/*
		 * 登记MI100
		 */
		Long seqno = commonUtil.genKey("MI100");
		Mi100 mi100 = new Mi100();
		mi100.setSeqno(seqno.intValue());
		
		return seqno.toString();
	}

	/**
	 * 查询中心客服电话，无客服电话抛出异常
	 * @param centerId 中心代码
	 * @return 客服电话
	 */
	private String getCustsvctel(String centerId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据个人公积金账号查询用户名
	 * @param accnum 个人公积金账号
	 * @return 用户名，如果无注册用户则返回null
	 */
	private String selectUserid(String accnum) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yondervision.mi.service.YfApi302Service#yfapi30202(com.yondervision.mi.form.YfApi30202Form, java.lang.String)
	 */
	public String yfapi30202(YfApi30202Form form, String file) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
