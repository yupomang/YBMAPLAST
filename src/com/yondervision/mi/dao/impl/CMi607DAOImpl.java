package com.yondervision.mi.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.dao.CMi607DAO;
import com.yondervision.mi.dto.CMi607;
import com.yondervision.mi.dto.Mi607;
import com.yondervision.mi.dto.Mi607Example;
import com.yondervision.mi.result.WebApi60702_queryResult;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;

public class CMi607DAOImpl extends Mi607DAOImpl implements CMi607DAO {

	@SuppressWarnings("unchecked")
	public WebApi60702_queryResult selectWebPage(CMi607 form) {
		// TODO Auto-generated method stub
		Mi607Example mi607e=new Mi607Example();
		mi607e.setOrderByClause("centerid desc,blacklistid asc,validflag desc");
		Mi607Example.Criteria ca=mi607e.createCriteria();
		if(!CommonUtil.isEmpty(form.getCenterid())&&!form.getCenterid().equals("00000000")){
			ca.andCenteridEqualTo(form.getCenterid());
		}
		if(!CommonUtil.isEmpty(form.getBlacklistid())){
			ca.andBlacklistidEqualTo(form.getBlacklistid());
		}
		if(!CommonUtil.isEmpty(form.getChannel())){
			ca.andChannelEqualTo(form.getChannel());
		}
		if(!CommonUtil.isEmpty(form.getLoginid())){
			ca.andLoginidEqualTo(form.getLoginid());
		}
		if(!CommonUtil.isEmpty(form.getPersonalid())){
			ca.andPersonalidEqualTo(form.getPersonalid());
		}
		if(!CommonUtil.isEmpty(form.getValidflag())){
			ca.andValidflagEqualTo(form.getValidflag());
		}
		if(!CommonUtil.isEmpty(form.getBegindate())&&!CommonUtil.isEmpty(form.getEnddate())){
			try {
				Date enddate = (new SimpleDateFormat("yyyy-MM-dd")).parse(form.getEnddate());
				Calendar cal = Calendar.getInstance();
				cal.setTime(enddate);
				cal.add(Calendar.DATE, 1);
				String enddateStr=(new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime()).toString();
				ca.andDatecreatedBetween(form.getBegindate(), enddateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		List<Mi607> list=getSqlMapClientTemplate().queryForList("MI607.abatorgenerated_selectByExample",mi607e,skipResults,rows);
		if(list.size()==0){			
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"黑名单");
		}
		int total = this.countByExample(mi607e);
		WebApi60702_queryResult queryResult = new WebApi60702_queryResult();
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList607(list);
		return queryResult;
	}

	@SuppressWarnings("unchecked")
	public boolean selectAppInfo(CMi607 form) throws Exception {
		String defNumStr=PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "defNum"+form.getCenterid());
		defNumStr=(defNumStr==null?"3":defNumStr.trim());
		int defNum=Integer.parseInt(defNumStr);
		List<Mi607> result=new ArrayList<Mi607>();
		if(defNum==0){
			return true;
		}
		result=getSqlMapClientTemplate().queryForList("CMI607.selectAppInfo", form);
		if(result !=null && result.size()>0){
			return false;
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);
		Date date = new Date();
		form.setAppodate(formatter.format(date));
		int count=Integer.parseInt(getSqlMapClientTemplate().queryForObject("CMI625.countMi625ForMi607", form).toString());
		if(count>=defNum){
			form.setBlacklistid(CommonUtil.genKeyAndCommit("MI607", 20));
			form.setCenterid(form.getCenterid());
			form.setPersonalid(form.getPersonalid());
			form.setCause("预约违约");
			form.setValidflag("1");
			form.setDatecreated(CommonUtil.getSystemDate());
			form.setDatemodified(CommonUtil.getSystemDate());
			getSqlMapClientTemplate().insert("CMI607.insertMi607", form);
			getSqlMapClientTemplate().getDataSource().getConnection().commit();
			return false;
		}
		return true;
	}
}
