package com.yondervision.mi.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi029DAO;
import com.yondervision.mi.dao.CMi607DAO;
import com.yondervision.mi.dao.Mi607DAO;
import com.yondervision.mi.dto.CMi607;
import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.dto.Mi607;
import com.yondervision.mi.dto.Mi607Example;
import com.yondervision.mi.service.WebApi607Service;
import com.yondervision.mi.util.CommonUtil;

import net.sf.json.JSONObject;
/**
 * @ClassName WebApi626ServiceImpl
 * @Description 黑名单操作实现
 * @author Lihongjie
 * @date 2014-08-05 09:27
 */
public class WebApi607ServiceImpl implements WebApi607Service {
	protected final Logger log = LoggerUtil.getLogger();
	private Mi607DAO mi607Dao=null;
	private CMi607DAO cmi607Dao=null;

	@Autowired
	private CMi029DAO cmi029DAO;
	
	public CMi029DAO getCmi029DAO() {
		return cmi029DAO;
	}

	public void setCmi029DAO(CMi029DAO cmi029dao) {
		cmi029DAO = cmi029dao;
	}

	public CMi607DAO getCmi607Dao() {
		return cmi607Dao;
	}

	public void setCmi607Dao(CMi607DAO cmi607Dao) {
		this.cmi607Dao = cmi607Dao;
	}

	public Mi607DAO getMi607Dao() {
		return mi607Dao;
	}

	public void setMi607Dao(Mi607DAO mi607Dao) {
		this.mi607Dao = mi607Dao;
	}

	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	
	/*
	 * 修改
	 */
	public int webapi60701(Mi607 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getBlacklistid())){
			log.error(ERROR.PARAMS_NULL.getLogText("主键为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "主键为空");
		}
		String[] ids = form.getBlacklistid().split(",");
		int t=0;
		for(String id:ids){
			Mi607 select = cmi607Dao.selectByPrimaryKey(id);
			if(CommonUtil.isEmpty(select)){
				log.error(ERROR.NO_DATA.getLogText("该条黑名单不存在："+id));
				throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "该条黑名单不存在："+id);
			}
			select.setValidflag(Constants.IS_NOT_VALIDFLAG);
			int update = cmi607Dao.updateByPrimaryKeySelective(select);
			t=t+update;
		}
		return t;
	}

	/*
	 * 查询
	 */
	public JSONObject webapi60702(CMi607 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		String sql = null;
		Integer page = form.getPage();
		Integer rows = form.getRows();
		if(CommonUtil.isEmpty(page)){
			log.error(ERROR.PARAMS_NULL.getLogText("page为空"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "page为空");
		}
		if(CommonUtil.isEmpty(rows)){
			log.error(ERROR.PARAMS_NULL.getLogText("rows为空"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "rows为空");
		}
		String centerid = form.getCenterid();
		if(CommonUtil.isEmpty(centerid)){
			centerid = UserContext.getInstance().getCenterid();
		}
		if (Constants.YD_ADMIN.equals(centerid)){
			sql="select a.*,b.* from mi029 a join mi607 b on a.personalid = b.personalid where b.validflag = '"+Constants.IS_VALIDFLAG +"'";
		}else{
			sql="select a.*,b.* from mi029 a join mi607 b on a.personalid = b.personalid where a.centerid = '"+centerid+"' and b.validflag = '"+Constants.IS_VALIDFLAG +"'";	
		}
		
		if(!CommonUtil.isEmpty(form.getPersonalname())){
			sql += " and a.username like '%" +form.getPersonalname()+ "%'";
		}
		if(!CommonUtil.isEmpty(form.getCertinum())){
			sql += " and a.certinum like '%" +form.getCertinum()+ "%'";
		}
		if(!CommonUtil.isEmpty(form.getTel())){
			sql += " and a.tel like '%" +form.getTel()+ "%'";
		}
		sql += " order by b.DATECREATED asc";
		JSONObject obj=CommonUtil.selectByPage(sql, page, rows); 
		
		return obj;
		
		
		
//		WebApi60702_queryResult list = cmi607Dao.selectWebPage(form);
//		if (CommonUtil.isEmpty(list)) {
//			log.error(ERROR.ERRCODE_LOG_800004.getLogText("黑名单"));
//			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),
//					"黑名单");
//		}else{
//			List<WebApi607Result> resultList = new ArrayList<WebApi607Result>();
//			List<Mi607> list607 = list.getList607();
//			if(list607 !=null && !list607.isEmpty()){
//				for(Mi607 mi607:list607){
//					WebApi607Result result = new WebApi607Result();
//					String personalid = mi607.getPersonalid();
//					Mi029 select = cmi029DAO.selectByPrimaryKey(personalid);
//					result.setMi029(select);
//					result.setMi607(mi607);
//					resultList.add(result);
//				}
//			}
//			list.setResultList(resultList);
//		}
//		return list;
	}
	
	public Mi607 webapi60703(String personalid) throws Exception{
		Mi607Example example = new Mi607Example();
		example.createCriteria().andPersonalidEqualTo(personalid).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		List<Mi607> list = mi607Dao.selectByExample(example);
		if(!CommonUtil.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}
}
