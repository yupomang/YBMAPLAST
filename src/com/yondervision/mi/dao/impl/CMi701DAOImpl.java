package com.yondervision.mi.dao.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi701DAO;
import com.yondervision.mi.dao.CMi703DAO;
import com.yondervision.mi.dao.Mi707DAO;
import com.yondervision.mi.dto.Mi701;
import com.yondervision.mi.dto.Mi701Example;
import com.yondervision.mi.dto.Mi701WithBLOBs;
import com.yondervision.mi.dto.Mi703Example;
import com.yondervision.mi.dto.Mi707;
import com.yondervision.mi.dto.Mi707Example;
import com.yondervision.mi.form.AppApi70001Form;
import com.yondervision.mi.form.AppApi701CommonForm;
import com.yondervision.mi.form.AppApi702CommonForm;
import com.yondervision.mi.form.WebApi70004Form;
import com.yondervision.mi.form.WebApi70204Form;
import com.yondervision.mi.form.WebApi70305Form;
import com.yondervision.mi.form.WebApi70504Form;
import com.yondervision.mi.form.WebApi70605Form;
import com.yondervision.mi.result.NewsBean;
import com.yondervision.mi.result.WebApi70004_queryResult;
import com.yondervision.mi.result.WebApi70204_NewsBean;
import com.yondervision.mi.result.WebApi70204_queryResult;
import com.yondervision.mi.result.WebApi70305_queryResult;
import com.yondervision.mi.result.WebApi70504_NewsBean;
import com.yondervision.mi.result.WebApi70504_queryResult;
import com.yondervision.mi.result.WebApi70605_queryResult;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.HtmlUtil;
import com.yondervision.mi.util.PropertiesReader;

public class CMi701DAOImpl extends Mi701DAOImpl implements CMi701DAO {

	@Autowired
	private CMi703DAO cmi703Dao = null;
	public void setCmi703Dao(CMi703DAO cmi703Dao) {
		this.cmi703Dao = cmi703Dao;
	}
	
	@Autowired
	private Mi707DAO mi707Dao = null;
	public void setMi707Dao(Mi707DAO mi707Dao) {
		this.mi707Dao = mi707Dao;
	}

	@SuppressWarnings("unchecked")
	public WebApi70004_queryResult selectMi701Page(WebApi70004Form form) throws Exception {
		Mi701Example mi701Example = new Mi701Example();
		//mi701Example.setOrderByClause("classification asc,releasetime desc,seqno desc");
		mi701Example.setOrderByClause("freeuse3 asc,releasetime desc,seqno desc");
		if (!CommonUtil.isEmpty(form.getKeyword())){
			Mi701Example.Criteria caOrTmp1 = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(form.getCenterId())){
				caOrTmp1.andCenteridEqualTo(form.getCenterId());
			}
			if(!CommonUtil.isEmpty(form.getClassificationQry())){
				caOrTmp1.andClassificationEqualTo(form.getClassificationQry());
			}
			if(!CommonUtil.isEmpty(form.getStartdate())){
				caOrTmp1.andReleasetimeGreaterThanOrEqualTo(form.getStartdate());
			}
			if(!CommonUtil.isEmpty(form.getEnddate())){
				caOrTmp1.andReleasetimeLessThanOrEqualTo(form.getEnddate());
			}
			if(!CommonUtil.isEmpty(form.getPubStatusQry())){
				caOrTmp1.andFreeuse3EqualTo(form.getPubStatusQry());
			}
			if(!CommonUtil.isEmpty(form.getPubQudaoQry())){
				if(!form.getPubQudaoQry().equals("-1"))
				caOrTmp1.andFreeuse8Like("%"+form.getPubQudaoQry()+"%");
			}
			if(!CommonUtil.isEmpty(form.getSourceQry())){
				caOrTmp1.andFreeuse5EqualTo(form.getSourceQry());
			}
			caOrTmp1.andIntroductionLike("%"+form.getKeyword()+"%");
			caOrTmp1.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(caOrTmp1);
			Mi701Example.Criteria caOrTmp2 = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(form.getCenterId())){
				caOrTmp2.andCenteridEqualTo(form.getCenterId());
			}
			if(!CommonUtil.isEmpty(form.getClassificationQry())){
				caOrTmp2.andClassificationEqualTo(form.getClassificationQry());
			}
			if(!CommonUtil.isEmpty(form.getStartdate())){
				caOrTmp2.andReleasetimeGreaterThanOrEqualTo(form.getStartdate());
			}
			if(!CommonUtil.isEmpty(form.getEnddate())){
				caOrTmp2.andReleasetimeLessThanOrEqualTo(form.getEnddate());
			}
			if(!CommonUtil.isEmpty(form.getPubStatusQry())){
				caOrTmp2.andFreeuse3EqualTo(form.getPubStatusQry());
			}
			if(!CommonUtil.isEmpty(form.getPubQudaoQry())){
				if(!form.getPubQudaoQry().equals("-1"))
				caOrTmp2.andFreeuse8Like("%"+form.getPubQudaoQry()+"%");
			}
			if(!CommonUtil.isEmpty(form.getSourceQry())){
				caOrTmp2.andFreeuse5EqualTo(form.getSourceQry());
			}
			caOrTmp2.andContentLike("%"+form.getKeyword()+"%");
			caOrTmp2.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(caOrTmp2);
			
			Mi701Example.Criteria ca = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(form.getCenterId())){
				ca.andCenteridEqualTo(form.getCenterId());
			}
			if(!CommonUtil.isEmpty(form.getClassificationQry())){
				ca.andClassificationEqualTo(form.getClassificationQry());
			}
			if(!CommonUtil.isEmpty(form.getStartdate())){
				ca.andReleasetimeGreaterThanOrEqualTo(form.getStartdate());
			}
			if(!CommonUtil.isEmpty(form.getEnddate())){
				ca.andReleasetimeLessThanOrEqualTo(form.getEnddate());
			}
			if(!CommonUtil.isEmpty(form.getPubStatusQry())){
				ca.andFreeuse3EqualTo(form.getPubStatusQry());
			}
			if(!CommonUtil.isEmpty(form.getPubQudaoQry())){
				if(!form.getPubQudaoQry().equals("-1"))
				ca.andFreeuse8Like("%"+form.getPubQudaoQry()+"%");
			}
			if(!CommonUtil.isEmpty(form.getSourceQry())){
				ca.andFreeuse5EqualTo(form.getSourceQry());
			}
			ca.andTitleLike("%"+form.getKeyword()+"%");
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(ca);
		}else{
			Mi701Example.Criteria ca = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(form.getCenterId())){
				ca.andCenteridEqualTo(form.getCenterId());
			}
			if(!CommonUtil.isEmpty(form.getClassificationQry())){
				ca.andClassificationEqualTo(form.getClassificationQry());
			}
			if(!CommonUtil.isEmpty(form.getStartdate())){
				ca.andReleasetimeGreaterThanOrEqualTo(form.getStartdate());
			}
			if(!CommonUtil.isEmpty(form.getEnddate())){
				ca.andReleasetimeLessThanOrEqualTo(form.getEnddate());
			}
			if(!CommonUtil.isEmpty(form.getPubStatusQry())){
				ca.andFreeuse3EqualTo(form.getPubStatusQry());
			}
			if(!CommonUtil.isEmpty(form.getPubQudaoQry())){
				if(!form.getPubQudaoQry().equals("-1"))
				ca.andFreeuse8Like("%"+form.getPubQudaoQry()+"%");
			}
			if(!CommonUtil.isEmpty(form.getSourceQry())){
				ca.andFreeuse5EqualTo(form.getSourceQry());
			}

			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		}

		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;
		int skipResults = (page-1) * rows;
		List<Mi701> list = getSqlMapClientTemplate().queryForList("MI701.abatorgenerated_selectByExample", mi701Example, skipResults, rows);
		int total = this.countByExample(mi701Example);
		int totalPage = total/rows;
		int mod = total%rows;
		if(mod > 0){
			totalPage = totalPage + 1;
		}
		
		WebApi70004_queryResult queryResult = new WebApi70004_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setTotalPage(totalPage);
		for (int i =0; i <= list.size()-1; i ++) {
			Mi701 mi701 = list.get(i);
			if (!CommonUtil.isEmpty(mi701.getReleasetime())){
				mi701.setReleasetime(mi701.getReleasetime().substring(0, 10));
			}
			list.set(i, mi701);
		}
		queryResult.setList701(list);
		return queryResult;
	}

	@SuppressWarnings("unchecked")
	public List<Mi701> selectMi701(AppApi70001Form form, String validDate,String yingyong) throws Exception {
		
		List<Mi701> list = new ArrayList<Mi701>();
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);
		Mi701Example mi701Example = new Mi701Example();
		mi701Example.setOrderByClause("releasetime desc");
		
		if (!CommonUtil.isEmpty(form.getKeyword())){
			String keyword = null;
			if(!"20".equals(form.getChannel())){
				keyword = form.getKeyword();
			}else{
				try {
					keyword = new String(form.getKeyword().getBytes("iso8859-1"),"UTF-8");
					
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			
			Mi701Example.Criteria ca = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(form.getCenterId())){
				ca.andCenteridEqualTo(form.getCenterId());
			}
			if(!CommonUtil.isEmpty(form.getClassification())){
				ca.andClassificationEqualTo(form.getClassification());
			}
			ca.andTitleLike("%"+keyword+"%");
			ca.andFreeuse8Like("%"+yingyong+"%");
			ca.andFreeuse3EqualTo(Constants.PUBLISH_FLG_ONE);
			ca.andReleasetimeLessThanOrEqualTo(dateFormatter.format(new Date()) + " 23:59:59");
			// 过期日期小于系统日期时，发布日期还得大于等于过期日期
			if (!CommonUtil.isEmpty(validDate)){
				if (dateFormatter.parse(validDate).before(dateFormatter.parse(CommonUtil.getSystemDate()))){
					ca.andReleasetimeGreaterThanOrEqualTo(dateFormatter.format(validDate) + " 00:00:00");
				}
			}
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(ca);
			Mi701Example.Criteria ca1 = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(form.getCenterId())){
				ca1.andCenteridEqualTo(form.getCenterId());
			}
			if(!CommonUtil.isEmpty(form.getClassification())){
				ca1.andClassificationEqualTo(form.getClassification());
			}
			ca1.andIntroductionLike("%"+keyword+"%");
			ca1.andFreeuse8Like("%"+yingyong+"%");
			ca1.andFreeuse3EqualTo(Constants.PUBLISH_FLG_ONE);
			ca1.andReleasetimeLessThanOrEqualTo(dateFormatter.format(new Date()) + " 23:59:59");
			// 过期日期小于系统日期时，发布日期还得大于等于过期日期
			if (!CommonUtil.isEmpty(validDate)){
				if (dateFormatter.parse(validDate).before(dateFormatter.parse(CommonUtil.getSystemDate()))){
					ca1.andReleasetimeGreaterThanOrEqualTo(dateFormatter.format(validDate) + " 00:00:00");
				}
			}
			ca1.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(ca1);
			Mi701Example.Criteria ca2 = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(form.getCenterId())){
				ca2.andCenteridEqualTo(form.getCenterId());
			}
			if(!CommonUtil.isEmpty(form.getClassification())){
				ca2.andClassificationEqualTo(form.getClassification());
			}
			ca2.andContentLike("%"+keyword+"%");
			ca2.andFreeuse8Like("%"+yingyong+"%");
			ca2.andFreeuse3EqualTo(Constants.PUBLISH_FLG_ONE);
			ca2.andReleasetimeLessThanOrEqualTo(dateFormatter.format(new Date()) + " 23:59:59");
			// 过期日期小于系统日期时，发布日期还得大于等于过期日期
			if (!CommonUtil.isEmpty(validDate)){
				if (dateFormatter.parse(validDate).before(dateFormatter.parse(CommonUtil.getSystemDate()))){
					ca2.andReleasetimeGreaterThanOrEqualTo(dateFormatter.format(validDate) + " 00:00:00");
				}
			}
			ca2.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(ca2);
		}else{
			Mi701Example.Criteria ca = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(form.getCenterId())){
				ca.andCenteridEqualTo(form.getCenterId());
			}
			if(!CommonUtil.isEmpty(form.getClassification())){
				ca.andClassificationEqualTo(form.getClassification());
			}
			ca.andFreeuse3EqualTo(Constants.PUBLISH_FLG_ONE);
			ca.andFreeuse8Like("%"+yingyong+"%");
			ca.andReleasetimeLessThanOrEqualTo(dateFormatter.format(new Date()) + " 23:59:59");
			// 过期日期小于系统日期时，发布日期还得大于等于过期日期
			if (!CommonUtil.isEmpty(validDate)){
				if (dateFormatter.parse(validDate).before(dateFormatter.parse(CommonUtil.getSystemDate()))){
					ca.andReleasetimeGreaterThanOrEqualTo(dateFormatter.format(validDate) + " 00:00:00");
				}
			}
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		}

		int page = Integer.valueOf(form.getPagenum());
		int rows = Integer.valueOf(form.getPagerows());
		page = page==0 ? Integer.valueOf(1) : (page + 1);
		rows = rows==0 ? Integer.valueOf(10) : rows;
		int skipResults = (page-1) * rows;
		list = getSqlMapClientTemplate().queryForList("MI701.abatorgenerated_selectByExample", mi701Example, skipResults, rows);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public WebApi70305_queryResult selectMi701Page_Title(WebApi70305Form form)throws Exception {
		String classification = form.getClassification();
		String newspaperforum = form.getNewspaperforum();
		String newspapercolumns = form.getNewspapercolumns();
		String title = form.getTitle();
		
		Mi701Example mi701Example = new Mi701Example();
		mi701Example.setOrderByClause("seqno asc");
		Mi701Example.Criteria ca = mi701Example.createCriteria();
		ca.andCenteridEqualTo(form.getCenterId());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		if (!CommonUtil.isEmpty(classification)){
			ca.andClassificationEqualTo(classification);
		}
		if (!CommonUtil.isEmpty(newspaperforum)){
			ca.andNewspaperforumEqualTo(newspaperforum);
		}
		if (!CommonUtil.isEmpty(newspapercolumns)){
			if("other".equals(newspapercolumns)){
				ca.andNewspapercolumnsEqualTo("");
			}else{
				ca.andNewspapercolumnsEqualTo(newspapercolumns);
			}
		}
		if (!CommonUtil.isEmpty(title)){
			ca.andTitleLike("%"+title+"%");
		}
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;
		int skipResults = (page-1) * rows;
		List<Mi701> list = getSqlMapClientTemplate().queryForList("MI701.abatorgenerated_selectByExample", mi701Example, skipResults, rows);
		int total = this.countByExample(mi701Example);
		int totalPage = total/rows;
		int mod = total%rows;
		if(mod > 0){
			totalPage = totalPage + 1;
		}
		
		WebApi70305_queryResult queryResult = new WebApi70305_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setTotalPage(totalPage);
		queryResult.setList701(list);
		return queryResult;
	}
	
	@SuppressWarnings("unchecked")
	public WebApi70204_queryResult selectMi701Page_WebApi70204(WebApi70204Form form) throws Exception {
		Mi701Example mi701Example = new Mi701Example();
		mi701Example.setOrderByClause("classification desc,newspaperforum asc,newspapercolumns asc,seqno asc");
		if (!CommonUtil.isEmpty(form.getKeyword())){
			Mi701Example.Criteria caOrTmp1 = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(form.getCenterId())){
				caOrTmp1.andCenteridEqualTo(form.getCenterId());
			}
			if(!CommonUtil.isEmpty(form.getClassificationQry())){
				caOrTmp1.andClassificationEqualTo(form.getClassificationQry());
			}
			if(!CommonUtil.isEmpty(form.getNewspaperforumQry())){
				caOrTmp1.andNewspaperforumEqualTo(form.getNewspaperforumQry());
			}
			if(!CommonUtil.isEmpty(form.getNewspapercolumnsQry())){
				caOrTmp1.andNewspapercolumnsEqualTo(form.getNewspapercolumnsQry());
			}
			caOrTmp1.andIntroductionLike("%"+form.getKeyword()+"%");
			caOrTmp1.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(caOrTmp1);
			Mi701Example.Criteria caOrTmp2 = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(form.getCenterId())){
				caOrTmp2.andCenteridEqualTo(form.getCenterId());
			}
			if(!CommonUtil.isEmpty(form.getClassificationQry())){
				caOrTmp2.andClassificationEqualTo(form.getClassificationQry());
			}
			if(!CommonUtil.isEmpty(form.getNewspaperforumQry())){
				caOrTmp2.andNewspaperforumEqualTo(form.getNewspaperforumQry());
			}
			if(!CommonUtil.isEmpty(form.getNewspapercolumnsQry())){
				caOrTmp2.andNewspapercolumnsEqualTo(form.getNewspapercolumnsQry());
			}
			caOrTmp2.andContentLike("%"+form.getKeyword()+"%");
			caOrTmp2.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(caOrTmp2);
			Mi701Example.Criteria ca3 = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(form.getCenterId())){
				ca3.andCenteridEqualTo(form.getCenterId());
			}
			if(!CommonUtil.isEmpty(form.getClassificationQry())){
				ca3.andClassificationEqualTo(form.getClassificationQry());
			}
			if(!CommonUtil.isEmpty(form.getNewspaperforumQry())){
				ca3.andNewspaperforumEqualTo(form.getNewspaperforumQry());
			}
			if(!CommonUtil.isEmpty(form.getNewspapercolumnsQry())){
				ca3.andNewspapercolumnsEqualTo(form.getNewspapercolumnsQry());
			}
			ca3.andCitedtitleLike("%"+form.getKeyword()+"%");
			ca3.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(ca3);
			Mi701Example.Criteria ca4 = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(form.getCenterId())){
				ca4.andCenteridEqualTo(form.getCenterId());
			}
			if(!CommonUtil.isEmpty(form.getClassificationQry())){
				ca4.andClassificationEqualTo(form.getClassificationQry());
			}
			if(!CommonUtil.isEmpty(form.getNewspaperforumQry())){
				ca4.andNewspaperforumEqualTo(form.getNewspaperforumQry());
			}
			if(!CommonUtil.isEmpty(form.getNewspapercolumnsQry())){
				ca4.andNewspapercolumnsEqualTo(form.getNewspapercolumnsQry());
			}
			ca4.andSubtopicsLike("%"+form.getKeyword()+"%");
			ca4.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(ca4);
			Mi701Example.Criteria ca5 = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(form.getCenterId())){
				ca5.andCenteridEqualTo(form.getCenterId());
			}
			if(!CommonUtil.isEmpty(form.getClassificationQry())){
				ca5.andClassificationEqualTo(form.getClassificationQry());
			}
			if(!CommonUtil.isEmpty(form.getNewspaperforumQry())){
				ca5.andNewspaperforumEqualTo(form.getNewspaperforumQry());
			}
			if(!CommonUtil.isEmpty(form.getNewspapercolumnsQry())){
				ca5.andNewspapercolumnsEqualTo(form.getNewspapercolumnsQry());
			}
			ca5.andSourceLike("%"+form.getKeyword()+"%");
			ca5.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(ca5);
			Mi701Example.Criteria ca6 = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(form.getCenterId())){
				ca6.andCenteridEqualTo(form.getCenterId());
			}
			if(!CommonUtil.isEmpty(form.getClassificationQry())){
				ca6.andClassificationEqualTo(form.getClassificationQry());
			}
			if(!CommonUtil.isEmpty(form.getNewspaperforumQry())){
				ca6.andNewspaperforumEqualTo(form.getNewspaperforumQry());
			}
			if(!CommonUtil.isEmpty(form.getNewspapercolumnsQry())){
				ca6.andNewspapercolumnsEqualTo(form.getNewspapercolumnsQry());
			}
			ca6.andBlurbsLike("%"+form.getKeyword()+"%");
			ca6.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(ca6);
			Mi701Example.Criteria ca = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(form.getCenterId())){
				ca.andCenteridEqualTo(form.getCenterId());
			}
			if(!CommonUtil.isEmpty(form.getClassificationQry())){
				ca.andClassificationEqualTo(form.getClassificationQry());
			}
			if(!CommonUtil.isEmpty(form.getNewspaperforumQry())){
				ca.andNewspaperforumEqualTo(form.getNewspaperforumQry());
			}
			if(!CommonUtil.isEmpty(form.getNewspapercolumnsQry())){
				ca.andNewspapercolumnsEqualTo(form.getNewspapercolumnsQry());
			}
			ca.andTitleLike("%"+form.getKeyword()+"%");
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(ca);
		}else{
			Mi701Example.Criteria ca = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(form.getCenterId())){
				ca.andCenteridEqualTo(form.getCenterId());
			}
			if(!CommonUtil.isEmpty(form.getClassificationQry())){
				ca.andClassificationEqualTo(form.getClassificationQry());
			}
			if(!CommonUtil.isEmpty(form.getNewspaperforumQry())){
				ca.andNewspaperforumEqualTo(form.getNewspaperforumQry());
			}
			if(!CommonUtil.isEmpty(form.getNewspapercolumnsQry())){
				ca.andNewspapercolumnsEqualTo(form.getNewspapercolumnsQry());
			}
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		}

		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;
		int skipResults = (page-1) * rows;
		List<Mi701> list = getSqlMapClientTemplate().queryForList("MI701.abatorgenerated_selectByExample", mi701Example, skipResults, rows);
		//List<WebApi70204_NewsBean> list = getSqlMapClientTemplate().queryForList("CMI701.self_selectMi701Page_WebApi70204", form, skipResults, rows);
		int total = this.countByExample(mi701Example);
		int totalPage = total/rows;
		int mod = total%rows;
		if(mod > 0){
			totalPage = totalPage + 1;
		}
		List<WebApi70204_NewsBean> resultList = new ArrayList<WebApi70204_NewsBean>();
		//获取每条记录的评论数
		for(int i = 0; i< list.size(); i++){
			Mi701 mi701 = (Mi701)list.get(i);
			Mi703Example mi703Example = new Mi703Example();
			mi703Example.createCriteria().andCenteridEqualTo(form.getCenterId())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andNewsseqnoEqualTo(mi701.getSeqno());
			int commentcounts = cmi703Dao.countByExample(mi703Example);
			
			WebApi70204_NewsBean resultBean = new WebApi70204_NewsBean();
			resultBean.setSeqno(mi701.getSeqno());
			resultBean.setNewspaperforum(mi701.getNewspaperforum());
			resultBean.setNewspapercolumns(mi701.getNewspapercolumns());
			resultBean.setTitle(mi701.getTitle());
			resultBean.setIntroduction(mi701.getIntroduction());
			resultBean.setImage(mi701.getImage());
			resultBean.setPraisecounts(mi701.getPraisecounts());
			resultBean.setFreeuse1(mi701.getFreeuse1());
			resultBean.setFreeuse2(mi701.getFreeuse2());
			resultBean.setCommentcounts(commentcounts);
			resultList.add(resultBean);
		}
		
		WebApi70204_queryResult queryResult = new WebApi70204_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setTotalPage(totalPage);
//		for (int i =0; i <= list.size()-1; i ++) {
//			Mi701 mi701 = list.get(i);
//			if (!CommonUtil.isEmpty(mi701.getReleasetime())){
//				mi701.setReleasetime(mi701.getReleasetime().substring(0, 10));
//			}
//			list.set(i, mi701);
//		}
		/*for (int i =0; i <= list.size()-1; i ++) {
			WebApi70204_NewsBean mi701 = list.get(i);
			if (!CommonUtil.isEmpty(mi701.getReleasetime())){
				mi701.setReleasetime(mi701.getReleasetime().substring(0, 10));
			}
			list.set(i, mi701);
		}*/
		queryResult.setList701(resultList);
		return queryResult;
	}
	
	/*
	 * 根据期次、版块下栏目不为空的栏目列表
	 * @see com.yondervision.mi.dao.CMi701DAO#selectColumns(com.yondervision.mi.form.AppApi701CommonForm)
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap<String,String>> selectColumns(AppApi701CommonForm form) throws Exception {
		List<HashMap<String,String>> result = getSqlMapClientTemplate().queryForList("CMI701.self_columns_selectByAppApi701CommonForm", form);
		return result;
	}
	
	public int updatePraisecountsByPrimaryKey(Mi701 record) throws Exception{
        int rows = getSqlMapClientTemplate().update("CMI701.updatePraisecountsByPrimaryKey", record);
        return rows;
	}
	
	public int updatePraisecountsSubByPrimaryKey(Mi701 record) throws Exception{
        int rows = getSqlMapClientTemplate().update("CMI701.updatePraisecountsSubByPrimaryKey", record);
        return rows;
	}
	
	/*
	 * 根据期次，获取新闻信息表中包含的版块
	 * @see com.yondervision.mi.dao.CMi701DAO#selectForumByClassification(java.util.HashMap)
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> selectForumByClassification(HashMap<String, String> paraMap) throws Exception{
		List<HashMap<String,String>> resultList = getSqlMapClientTemplate().queryForList("CMI701.self_selectForumByClassification", paraMap);
        return resultList;
	}
	
	/*
	 *  根据期次，获取新闻信息表中包含的栏目（栏目不为空）
	 * @see com.yondervision.mi.dao.CMi701DAO#selectColumnsByClassification(java.util.HashMap)
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> selectColumnsByClassification(HashMap<String, String> paraMap) throws Exception{
		List<HashMap<String,String>> resultList = getSqlMapClientTemplate().queryForList("CMI701.self_selectColumnsByClassification", paraMap);
        return resultList;
	}
	
	/*
	 * 根据期次，获取新闻信息表中是否包含栏目为空的记录
	 * @see com.yondervision.mi.dao.CMi701DAO#selectColumnsNullCountByClassification(java.util.HashMap)
	 */
	public int selectColumnsNullCountByClassification(HashMap<String, String> paraMap) throws Exception{
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject("CMI701.self_selectColumnsNullCountByClassification", paraMap);
        return count.intValue();
	}
	
	/*
	 *  根据期次、版块，获取新闻信息表中包含的栏目（栏目不为空）(non-Javadoc)
	 * @see com.yondervision.mi.dao.CMi701DAO#selectColumnsByTimesForum(java.util.HashMap)
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> selectColumnsByTimesForum(HashMap<String, String> paraMap) throws Exception{
		List<HashMap<String,String>> resultList = getSqlMapClientTemplate().queryForList("CMI701.self_selectColumnsByTimesForum", paraMap);
        return resultList;
	}
	
	/*
	 *  根据期次，获取新闻信息表中是否包含栏目为空的记录(non-Javadoc)
	 * @see com.yondervision.mi.dao.CMi701DAO#selectColumnsNullCountByTimesForum(java.util.HashMap)
	 */
	public int selectColumnsNullCountByTimesForum(HashMap<String, String> paraMap) throws Exception{
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject("CMI701.self_selectColumnsNullCountByTimesForum", paraMap);
        return count.intValue();
	}
	
	// 根据城市中心ID获取栏目列表
	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> selfSelectColumnsByCenterid(HashMap<String, String> paraMap) throws Exception{
		List<HashMap<String,String>> resultList = getSqlMapClientTemplate().queryForList("CMI701.self_selectColumnsByCenterid", paraMap);
        return resultList;
	}
	
	// 根据城市中心ID获取版块列表
	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> selfSelectForumByCenterid(HashMap<String, String> paraMap) throws Exception{
		List<HashMap<String,String>> resultList = getSqlMapClientTemplate().queryForList("CMI701.self_selectForumByCenterid", paraMap);
        return resultList;
	}
	
	@SuppressWarnings("unchecked")
	public WebApi70504_queryResult selectMi701Page_WebApi70504(WebApi70504Form form) throws Exception {
		Mi701Example mi701Example = new Mi701Example();
		mi701Example.setOrderByClause("seqno desc");
		if (!CommonUtil.isEmpty(form.getKeyword())){
			if (CommonUtil.isEmpty(form.getClassificationQry())){
				Mi701Example.Criteria caOrTmp1 = mi701Example.createCriteria();
				if(!CommonUtil.isEmpty(form.getCenterId())){
					caOrTmp1.andCenteridEqualTo(form.getCenterId());
				}

				caOrTmp1.andIntroductionLike("%"+form.getKeyword()+"%");
				caOrTmp1.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi701Example.or(caOrTmp1);
				Mi701Example.Criteria caOrTmp2 = mi701Example.createCriteria();
				if(!CommonUtil.isEmpty(form.getCenterId())){
					caOrTmp2.andCenteridEqualTo(form.getCenterId());
				}
				caOrTmp2.andContentLike("%"+form.getKeyword()+"%");
				caOrTmp2.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi701Example.or(caOrTmp2);
				Mi701Example.Criteria ca3 = mi701Example.createCriteria();
				if(!CommonUtil.isEmpty(form.getCenterId())){
					ca3.andCenteridEqualTo(form.getCenterId());
				}
				ca3.andCitedtitleLike("%"+form.getKeyword()+"%");
				ca3.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi701Example.or(ca3);
				Mi701Example.Criteria ca4 = mi701Example.createCriteria();
				if(!CommonUtil.isEmpty(form.getCenterId())){
					ca4.andCenteridEqualTo(form.getCenterId());
				}
				ca4.andSubtopicsLike("%"+form.getKeyword()+"%");
				ca4.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi701Example.or(ca4);
				Mi701Example.Criteria ca5 = mi701Example.createCriteria();
				if(!CommonUtil.isEmpty(form.getCenterId())){
					ca5.andCenteridEqualTo(form.getCenterId());
				}
				ca5.andSourceLike("%"+form.getKeyword()+"%");
				ca5.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi701Example.or(ca5);
				Mi701Example.Criteria ca6 = mi701Example.createCriteria();
				if(!CommonUtil.isEmpty(form.getCenterId())){
					ca6.andCenteridEqualTo(form.getCenterId());
				}
				ca6.andBlurbsLike("%"+form.getKeyword()+"%");
				ca6.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi701Example.or(ca6);
				Mi701Example.Criteria ca = mi701Example.createCriteria();
				if(!CommonUtil.isEmpty(form.getCenterId())){
					ca.andCenteridEqualTo(form.getCenterId());
				}
				ca.andTitleLike("%"+form.getKeyword()+"%");
				ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi701Example.or(ca);
			}else{
				Mi701Example.Criteria caOrTmp1 = mi701Example.createCriteria();
				if(!CommonUtil.isEmpty(form.getCenterId())){
					caOrTmp1.andCenteridEqualTo(form.getCenterId());
				}
				caOrTmp1.andClassificationEqualTo(form.getClassificationQry());
				caOrTmp1.andIntroductionLike("%"+form.getKeyword()+"%");
				caOrTmp1.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi701Example.or(caOrTmp1);
				/*Mi701Example.Criteria caOrTmp11 = mi701Example.createCriteria();
				if(!CommonUtil.isEmpty(form.getCenterId())){
					caOrTmp11.andCenteridEqualTo(form.getCenterId());
				}
				caOrTmp11.andNewspaperforumLike('%'+form.getClassificationQry()+'%');
				caOrTmp11.andIntroductionLike("%"+form.getKeyword()+"%");
				caOrTmp11.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi701Example.or(caOrTmp11);*/
				Mi701Example.Criteria caOrTmp2 = mi701Example.createCriteria();
				if(!CommonUtil.isEmpty(form.getCenterId())){
					caOrTmp2.andCenteridEqualTo(form.getCenterId());
				}
				caOrTmp2.andClassificationEqualTo(form.getClassificationQry());
				caOrTmp2.andContentLike("%"+form.getKeyword()+"%");
				caOrTmp2.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi701Example.or(caOrTmp2);
				/*Mi701Example.Criteria caOrTmp21 = mi701Example.createCriteria();
				if(!CommonUtil.isEmpty(form.getCenterId())){
					caOrTmp21.andCenteridEqualTo(form.getCenterId());
				}
				caOrTmp21.andNewspaperforumLike('%'+form.getClassificationQry()+'%');
				caOrTmp21.andContentLike("%"+form.getKeyword()+"%");
				caOrTmp21.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi701Example.or(caOrTmp21);*/
				Mi701Example.Criteria ca3 = mi701Example.createCriteria();
				if(!CommonUtil.isEmpty(form.getCenterId())){
					ca3.andCenteridEqualTo(form.getCenterId());
				}
				ca3.andClassificationEqualTo(form.getClassificationQry());
				ca3.andCitedtitleLike("%"+form.getKeyword()+"%");
				ca3.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi701Example.or(ca3);
				/*Mi701Example.Criteria ca31 = mi701Example.createCriteria();
				if(!CommonUtil.isEmpty(form.getCenterId())){
					ca31.andCenteridEqualTo(form.getCenterId());
				}
				ca31.andNewspaperforumLike('%'+form.getClassificationQry()+'%');
				ca31.andCitedtitleLike("%"+form.getKeyword()+"%");
				ca31.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi701Example.or(ca31);*/
				Mi701Example.Criteria ca4 = mi701Example.createCriteria();
				if(!CommonUtil.isEmpty(form.getCenterId())){
					ca4.andCenteridEqualTo(form.getCenterId());
				}
				ca4.andClassificationEqualTo(form.getClassificationQry());
				ca4.andSubtopicsLike("%"+form.getKeyword()+"%");
				ca4.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi701Example.or(ca4);
				/*Mi701Example.Criteria ca41 = mi701Example.createCriteria();
				if(!CommonUtil.isEmpty(form.getCenterId())){
					ca41.andCenteridEqualTo(form.getCenterId());
				}
				ca41.andNewspaperforumLike('%'+form.getClassificationQry()+'%');
				ca41.andSubtopicsLike("%"+form.getKeyword()+"%");
				ca41.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi701Example.or(ca41);*/
				Mi701Example.Criteria ca5 = mi701Example.createCriteria();
				if(!CommonUtil.isEmpty(form.getCenterId())){
					ca5.andCenteridEqualTo(form.getCenterId());
				}
				ca5.andClassificationEqualTo(form.getClassificationQry());
				ca5.andSourceLike("%"+form.getKeyword()+"%");
				ca5.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi701Example.or(ca5);
				/*Mi701Example.Criteria ca51 = mi701Example.createCriteria();
				if(!CommonUtil.isEmpty(form.getCenterId())){
					ca51.andCenteridEqualTo(form.getCenterId());
				}
				ca51.andNewspaperforumLike('%'+form.getClassificationQry()+'%');
				ca51.andSourceLike("%"+form.getKeyword()+"%");
				ca51.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi701Example.or(ca51);*/
				Mi701Example.Criteria ca6 = mi701Example.createCriteria();
				if(!CommonUtil.isEmpty(form.getCenterId())){
					ca6.andCenteridEqualTo(form.getCenterId());
				}
				ca6.andClassificationEqualTo(form.getClassificationQry());
				ca6.andBlurbsLike("%"+form.getKeyword()+"%");
				ca6.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi701Example.or(ca6);
				/*Mi701Example.Criteria ca61 = mi701Example.createCriteria();
				if(!CommonUtil.isEmpty(form.getCenterId())){
					ca61.andCenteridEqualTo(form.getCenterId());
				}
				ca61.andNewspaperforumLike('%'+form.getClassificationQry()+'%');
				ca61.andBlurbsLike("%"+form.getKeyword()+"%");
				ca61.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi701Example.or(ca61);*/
				Mi701Example.Criteria ca = mi701Example.createCriteria();
				if(!CommonUtil.isEmpty(form.getCenterId())){
					ca.andCenteridEqualTo(form.getCenterId());
				}
				ca.andClassificationEqualTo(form.getClassificationQry());
				ca.andTitleLike("%"+form.getKeyword()+"%");
				ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi701Example.or(ca);
				/*Mi701Example.Criteria ca1 = mi701Example.createCriteria();
				if(!CommonUtil.isEmpty(form.getCenterId())){
					ca1.andCenteridEqualTo(form.getCenterId());
				}
				ca1.andNewspaperforumLike('%'+form.getClassificationQry()+'%');
				ca1.andTitleLike("%"+form.getKeyword()+"%");
				ca1.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi701Example.or(ca1);*/
			}
		}else{
			if (CommonUtil.isEmpty(form.getClassificationQry())){
				Mi701Example.Criteria ca = mi701Example.createCriteria();
				if(!CommonUtil.isEmpty(form.getCenterId())){
					ca.andCenteridEqualTo(form.getCenterId());
				}
				ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			}else{
				Mi701Example.Criteria ca = mi701Example.createCriteria();
				if(!CommonUtil.isEmpty(form.getCenterId())){
					ca.andCenteridEqualTo(form.getCenterId());
				}
				ca.andClassificationEqualTo(form.getClassificationQry());
				ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi701Example.or(ca);
				/*Mi701Example.Criteria ca1 = mi701Example.createCriteria();
				if(!CommonUtil.isEmpty(form.getCenterId())){
					ca1.andCenteridEqualTo(form.getCenterId());
				}
				ca1.andNewspaperforumLike('%'+form.getClassificationQry()+'%');
				ca1.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi701Example.or(ca1);*/
			}
		}

		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;
		int skipResults = (page-1) * rows;
		List<Mi701> list = getSqlMapClientTemplate().queryForList("MI701.abatorgenerated_selectByExample", mi701Example, skipResults, rows);
		//List<WebApi70204_NewsBean> list = getSqlMapClientTemplate().queryForList("CMI701.self_selectMi701Page_WebApi70204", form, skipResults, rows);
		int total = this.countByExample(mi701Example);
		int totalPage = total/rows;
		int mod = total%rows;
		if(mod > 0){
			totalPage = totalPage + 1;
		}
		List<WebApi70504_NewsBean> resultList = new ArrayList<WebApi70504_NewsBean>();
		//获取每条记录的评论数
		for(int i = 0; i< list.size(); i++){
			Mi701 mi701 = (Mi701)list.get(i);
			Mi703Example mi703Example = new Mi703Example();
			mi703Example.createCriteria().andCenteridEqualTo(form.getCenterId())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andNewsseqnoEqualTo(mi701.getSeqno());
			int commentcounts = cmi703Dao.countByExample(mi703Example);
			
			WebApi70504_NewsBean resultBean = new WebApi70504_NewsBean();
			resultBean.setSeqno(mi701.getSeqno());
			if ("0".equals(mi701.getNewspaperforum())){
				resultBean.setNewspaperforum(mi701.getClassification());
				resultBean.setNewspapercolumns("");
			}else{
				resultBean.setNewspaperforum(mi701.getNewspaperforum());
				resultBean.setNewspapercolumns(mi701.getClassification());
			}

			resultBean.setTitle(mi701.getTitle());
			resultBean.setIntroduction(mi701.getIntroduction());
			resultBean.setImage(mi701.getImage());
			resultBean.setPraisecounts(mi701.getPraisecounts());
			resultBean.setFreeuse1(mi701.getFreeuse1());
			resultBean.setFreeuse2(mi701.getFreeuse2());
			resultBean.setCommentcounts(commentcounts);
			//resultBean.setFreeuse4(mi701.getFreeuse4().toString());
			resultList.add(resultBean);
		}
		
		WebApi70504_queryResult queryResult = new WebApi70504_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setTotalPage(totalPage);
//		for (int i =0; i <= list.size()-1; i ++) {
//			Mi701 mi701 = list.get(i);
//			if (!CommonUtil.isEmpty(mi701.getReleasetime())){
//				mi701.setReleasetime(mi701.getReleasetime().substring(0, 10));
//			}
//			list.set(i, mi701);
//		}
		/*for (int i =0; i <= list.size()-1; i ++) {
			WebApi70204_NewsBean mi701 = list.get(i);
			if (!CommonUtil.isEmpty(mi701.getReleasetime())){
				mi701.setReleasetime(mi701.getReleasetime().substring(0, 10));
			}
			list.set(i, mi701);
		}*/
		queryResult.setList701(resultList);
		return queryResult;
	}
	
	/*
	 * 获取新闻信息表中包含的版块
	 * @see com.yondervision.mi.dao.CMi701DAO#selectClassificInMi701Count(java.util.HashMap)
	 */
	public int selectClassificInMi701Count(HashMap<String, String> paraMap) throws Exception{
		Integer count = (Integer)getSqlMapClientTemplate().queryForObject("CMI701.self_selectClassificInMi701Count", paraMap);
        return count.intValue();
	}
    
	public int selectForumInMi701Count(HashMap<String, String> paraMap) throws Exception{
		Integer count = (Integer)getSqlMapClientTemplate().queryForObject("CMI701.self_selectForumInMi701Count", paraMap);
        return count.intValue();
	}
	
	/*
	 *  获取新闻信息表中包含的栏目（栏目不为空）
	 * @see com.yondervision.mi.dao.CMi701DAO#selectColumnsInMi701Count(java.util.HashMap)
	 */
	public int selectColumnsInMi701Count(HashMap<String, String> paraMap) throws Exception{
		Integer count = (Integer)getSqlMapClientTemplate().queryForObject("CMI701.self_selectColumnsInMi701Count", paraMap);
		return count.intValue();
	}
	
	/*
	 *  获取新闻信息表中是否包含栏目为空的记录（即只有版块归属没有栏目归属，父级为0的记录）(non-Javadoc)
	 * @see com.yondervision.mi.dao.CMi701DAO#selectUpdicidZeroCountFromMi701(java.util.HashMap)
	 */
	public int selectUpdicidZeroCountFromMi701(HashMap<String, String> paraMap) throws Exception{
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject("CMI701.self_selectUpdicidZeroCountFromMi701", paraMap);
        return count.intValue();
	}
	
	// 评论管理中，获取新闻标题列表
	@SuppressWarnings("unchecked")
	public WebApi70605_queryResult selectMi701Page_TitleNoTimes(WebApi70605Form form)throws Exception {
		/*String newspaperforum = form.getNewspaperforum();
		String newspapercolumns = form.getNewspapercolumns();
		String title = form.getTitle();
		
		Mi701Example mi701Example = new Mi701Example();
		mi701Example.setOrderByClause("seqno desc");
		if(!CommonUtil.isEmpty(newspapercolumns)){
			Mi701Example.Criteria ca = mi701Example.createCriteria();
			ca.andCenteridEqualTo(form.getCenterId());
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			
			if("other".equals(newspapercolumns)){
				ca.andClassificationEqualTo(newspaperforum);
				ca.andNewspaperforumEqualTo("0");
			}else{
				ca.andClassificationEqualTo(newspapercolumns);
				ca.andNewspaperforumEqualTo(newspaperforum);
			}
			if (!CommonUtil.isEmpty(title)){
				ca.andTitleLike("%"+title+"%");
			}
		}else{
			Mi701Example.Criteria ca = mi701Example.createCriteria();
			ca.andCenteridEqualTo(form.getCenterId());
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			ca.andClassificationEqualTo(newspaperforum);
			ca.andNewspaperforumEqualTo("0");
			if (!CommonUtil.isEmpty(title)){
				ca.andTitleLike("%"+title+"%");
			}
			mi701Example.or(ca);
			Mi701Example.Criteria ca1 = mi701Example.createCriteria();
			ca1.andCenteridEqualTo(form.getCenterId());
			ca1.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			ca1.andNewspaperforumEqualTo(newspaperforum);
			if (!CommonUtil.isEmpty(title)){
				ca1.andTitleLike("%"+title+"%");
			}
			mi701Example.or(ca1);
		}*/
		String classification = form.getClassification();
		String title = form.getTitle();
		
		Mi701Example mi701Example = new Mi701Example();
		mi701Example.setOrderByClause("seqno desc");
		if(CommonUtil.isEmpty(classification)){
			Mi701Example.Criteria ca = mi701Example.createCriteria();
			ca.andCenteridEqualTo(form.getCenterId());
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			if (!CommonUtil.isEmpty(title)){
				ca.andTitleLike("%"+title+"%");
			}
		}else{
			Mi701Example.Criteria ca = mi701Example.createCriteria();
			ca.andCenteridEqualTo(form.getCenterId());
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			ca.andClassificationEqualTo(classification);
			if (!CommonUtil.isEmpty(title)){
				ca.andTitleLike("%"+title+"%");
			}
			mi701Example.or(ca);
			Mi701Example.Criteria ca1 = mi701Example.createCriteria();
			ca1.andCenteridEqualTo(form.getCenterId());
			ca1.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			ca1.andNewspaperforumLike('%'+classification+'%');
			if (!CommonUtil.isEmpty(title)){
				ca1.andTitleLike("%"+title+"%");
			}
			mi701Example.or(ca1);
		}

		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;
		int skipResults = (page-1) * rows;
		List<Mi701> list = getSqlMapClientTemplate().queryForList("MI701.abatorgenerated_selectByExample", mi701Example, skipResults, rows);
		int total = this.countByExample(mi701Example);
		int totalPage = total/rows;
		int mod = total%rows;
		if(mod > 0){
			totalPage = totalPage + 1;
		}
		
		WebApi70605_queryResult queryResult = new WebApi70605_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setTotalPage(totalPage);
		queryResult.setList701(list);
		return queryResult;
	}
	
	/*
	 * 根据版块下栏目不为空的栏目列表
	 * @see com.yondervision.mi.dao.CMi701DAO#selectColumnsNoTimes(com.yondervision.mi.form.AppApi702CommonForm)
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap<String,String>> selectColumnsNoTimes(AppApi702CommonForm form) throws Exception {
		List<HashMap<String,String>> result = getSqlMapClientTemplate().queryForList("CMI701.self_columns_selectByAppApi702CommonForm", form);
		return result;
	}
	
	// 根据城市中心ID获取栏目列表（无期次，版块和栏目维护在同一字段）
	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> selfSelectColumnsNoTimesByCenterid(HashMap<String, String> paraMap) throws Exception{
		List<HashMap<String,String>> resultList = getSqlMapClientTemplate().queryForList("CMI701.self_selectColumnsNoTimesByCenterid", paraMap);
        return resultList;
	}
	
	/*
	 *  根据版块，获取新闻信息表中包含的栏目（栏目不为空）(non-Javadoc)
	 * @see com.yondervision.mi.dao.CMi701DAO#selectColumnsByForum(java.util.HashMap)
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> selectColumnsByForum(HashMap<String, String> paraMap) throws Exception{
		List<HashMap<String,String>> resultList = getSqlMapClientTemplate().queryForList("CMI701.self_selectColumnsByForum", paraMap);
        return resultList;
	}
	
	/*
	 *  根据版块，获取新闻信息表中是否包含栏目为空的记录(non-Javadoc)
	 * @see com.yondervision.mi.dao.CMi701DAO#selectColumnsNullCountByForum(java.util.HashMap)
	 */
	public int selectColumnsNullCountByForum(HashMap<String, String> paraMap) throws Exception{
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject("CMI701.self_selectColumnsNullCountByForum", paraMap);
        return count.intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<Mi701WithBLOBs> selectMi701ByRandom(AppApi70001Form form, String validDate,String yingyong) throws Exception {
		
		List<Mi701WithBLOBs> list = new ArrayList<Mi701WithBLOBs>();
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);
		Mi701Example mi701Example = new Mi701Example();
		
		if (!CommonUtil.isEmpty(form.getKeyword())){
			String keyword = null;
			if(!"20".equals(form.getChannel())){
				keyword = form.getKeyword();
			}else{
				try {//服务器升级后注释
					keyword = new String(form.getKeyword().getBytes("iso8859-1"),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			
			Mi701Example.Criteria ca = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(form.getCenterId())){
				ca.andCenteridEqualTo(form.getCenterId());
			}
			if(!CommonUtil.isEmpty(form.getClassification())){
				ca.andClassificationEqualTo(form.getClassification());
			}
			ca.andTitleLike("%"+keyword+"%");
			ca.andFreeuse8Like("%"+yingyong+"%");
			ca.andFreeuse3EqualTo(Constants.PUBLISH_FLG_ONE);
			ca.andReleasetimeLessThanOrEqualTo(dateFormatter.format(new Date()) + " 23:59:59");
			// 过期日期小于系统日期时，发布日期还得大于等于过期日期
			if (!CommonUtil.isEmpty(validDate)){
				if (dateFormatter.parse(validDate).before(dateFormatter.parse(CommonUtil.getSystemDate()))){
					ca.andReleasetimeGreaterThanOrEqualTo(dateFormatter.format(validDate) + " 00:00:00");
				}
			}
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(ca);
			Mi701Example.Criteria ca1 = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(form.getCenterId())){
				ca1.andCenteridEqualTo(form.getCenterId());
			}
			if(!CommonUtil.isEmpty(form.getClassification())){
				ca1.andClassificationEqualTo(form.getClassification());
			}
			ca1.andIntroductionLike("%"+keyword+"%");
			ca1.andFreeuse8Like("%"+yingyong+"%");
			ca1.andFreeuse3EqualTo(Constants.PUBLISH_FLG_ONE);
			ca1.andReleasetimeLessThanOrEqualTo(dateFormatter.format(new Date()) + " 23:59:59");
			// 过期日期小于系统日期时，发布日期还得大于等于过期日期
			if (!CommonUtil.isEmpty(validDate)){
				if (dateFormatter.parse(validDate).before(dateFormatter.parse(CommonUtil.getSystemDate()))){
					ca.andReleasetimeGreaterThanOrEqualTo(dateFormatter.format(validDate) + " 00:00:00");
				}
			}
			ca1.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(ca1);
			Mi701Example.Criteria ca2 = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(form.getCenterId())){
				ca2.andCenteridEqualTo(form.getCenterId());
			}
			if(!CommonUtil.isEmpty(form.getClassification())){
				ca2.andClassificationEqualTo(form.getClassification());
			}
			ca2.andContentLike("%"+keyword+"%");
			ca2.andFreeuse8Like("%"+yingyong+"%");
			ca2.andFreeuse3EqualTo(Constants.PUBLISH_FLG_ONE);
			ca2.andReleasetimeLessThanOrEqualTo(dateFormatter.format(new Date()) + " 23:59:59");
			// 过期日期小于系统日期时，发布日期还得大于等于过期日期
			if (!CommonUtil.isEmpty(validDate)){
				if (dateFormatter.parse(validDate).before(dateFormatter.parse(CommonUtil.getSystemDate()))){
					ca.andReleasetimeGreaterThanOrEqualTo(dateFormatter.format(validDate) + " 00:00:00");
				}
			}
			ca2.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(ca2);
		}else{
			Mi701Example.Criteria ca = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(form.getCenterId())){
				ca.andCenteridEqualTo(form.getCenterId());
			}
			if(!CommonUtil.isEmpty(form.getClassification())){
				ca.andClassificationEqualTo(form.getClassification());
			}
			ca.andFreeuse8Like("%"+yingyong+"%");
			ca.andFreeuse3EqualTo(Constants.PUBLISH_FLG_ONE);
			ca.andReleasetimeLessThanOrEqualTo(dateFormatter.format(new Date()) + " 23:59:59");
			// 过期日期小于系统日期时，发布日期还得大于等于过期日期
			if (!CommonUtil.isEmpty(validDate)){
				if (dateFormatter.parse(validDate).before(dateFormatter.parse(CommonUtil.getSystemDate()))){
					ca.andReleasetimeGreaterThanOrEqualTo(dateFormatter.format(validDate) + " 00:00:00");
				}
			}
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		}

		/*int page = Integer.valueOf(form.getPagenum());
		int rows = Integer.valueOf(form.getPagerows());
		page = page==0 ? Integer.valueOf(1) : (page + 1);
		rows = rows==0 ? Integer.valueOf(10) : rows;
		int skipResults = (page-1) * rows;
		list = getSqlMapClientTemplate().queryForList("MI701.abatorgenerated_selectByExample", mi701Example, skipResults, rows);*/
		list = getSqlMapClientTemplate().queryForList("CMI701.selectByExampleByRandomOnlyOne", mi701Example);
		return list;
	}
	
	// 查询mi701，如果上传分页标记则分页查询，否则按默认分页查询
	@SuppressWarnings("unchecked")
	public NewsBean selectMi701ToApp(String centerid, String channel, String classification,
			String keyword, String pagenum, String pagerows, String validDate,String yingyong) throws Exception {
		
		List<Mi701> list = new ArrayList<Mi701>();
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);
		Mi701Example mi701Example = new Mi701Example();
		mi701Example.setOrderByClause("releasetime desc");
		
		if (!CommonUtil.isEmpty(keyword)){
			String keywordTmp = null;
			System.out.println("keyword===="+keyword);
			if(CommonUtil.isEmpty(channel) || "30".equals(channel)){// 30现用于网站改版，前端做utf-8编码
				try {
					keywordTmp = URLDecoder.decode(keyword,"UTF-8");
					System.out.println("keywordTmp===="+keywordTmp);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}else if(!"20".equals(channel) && !"30".equals(channel)){
				keywordTmp = keyword;
			}else{
				try {
					keywordTmp = new String(keyword.getBytes("iso8859-1"),"UTF-8");
					
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			
			Mi701Example.Criteria ca = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(centerid)){
				ca.andCenteridEqualTo(centerid);
			}
			if(!CommonUtil.isEmpty(classification)){
				ca.andClassificationEqualTo(classification);
			}
			ca.andTitleLike("%"+keywordTmp+"%");
			ca.andFreeuse8Like("%"+yingyong+"%");
			ca.andFreeuse3EqualTo(Constants.PUBLISH_FLG_ONE);
			ca.andReleasetimeLessThanOrEqualTo(dateFormatter.format(new Date()) + " 23:59:59");
			// 过期日期小于系统日期时，发布日期还得大于等于过期日期
			if (!CommonUtil.isEmpty(validDate)){
				if (dateFormatter.parse(validDate).before(dateFormatter.parse(CommonUtil.getSystemDate()))){
					ca.andReleasetimeGreaterThanOrEqualTo(dateFormatter.format(validDate) + " 00:00:00");
				}
			}
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(ca);
			Mi701Example.Criteria ca1 = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(centerid)){
				ca1.andCenteridEqualTo(centerid);
			}
			if(!CommonUtil.isEmpty(classification)){
				ca1.andClassificationEqualTo(classification);
			}
			ca1.andIntroductionLike("%"+keywordTmp+"%");
			ca1.andFreeuse8Like("%"+yingyong+"%");
			ca1.andFreeuse3EqualTo(Constants.PUBLISH_FLG_ONE);
			ca1.andReleasetimeLessThanOrEqualTo(dateFormatter.format(new Date()) + " 23:59:59");
			// 过期日期小于系统日期时，发布日期还得大于等于过期日期
			if (!CommonUtil.isEmpty(validDate)){
				if (dateFormatter.parse(validDate).before(dateFormatter.parse(CommonUtil.getSystemDate()))){
					ca1.andReleasetimeGreaterThanOrEqualTo(dateFormatter.format(validDate) + " 00:00:00");
				}
			}
			ca1.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(ca1);
			Mi701Example.Criteria ca2 = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(centerid)){
				ca2.andCenteridEqualTo(centerid);
			}
			if(!CommonUtil.isEmpty(classification)){
				ca2.andClassificationEqualTo(classification);
			}
			ca2.andContentLike("%"+keywordTmp+"%");
			ca2.andFreeuse8Like("%"+yingyong+"%");
			ca2.andFreeuse3EqualTo(Constants.PUBLISH_FLG_ONE);
			ca2.andReleasetimeLessThanOrEqualTo(dateFormatter.format(new Date()) + " 23:59:59");
			// 过期日期小于系统日期时，发布日期还得大于等于过期日期
			if (!CommonUtil.isEmpty(validDate)){
				if (dateFormatter.parse(validDate).before(dateFormatter.parse(CommonUtil.getSystemDate()))){
					ca2.andReleasetimeGreaterThanOrEqualTo(dateFormatter.format(validDate) + " 00:00:00");
				}
			}
			ca2.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(ca2);
		}else{
			System.out.println("333=====333333");
			Mi701Example.Criteria ca = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(centerid)){
				ca.andCenteridEqualTo(centerid);
			}
			if(!CommonUtil.isEmpty(classification)){
				ca.andClassificationEqualTo(classification);
			}
			ca.andFreeuse8Like("%"+yingyong+"%");
			ca.andFreeuse3EqualTo(Constants.PUBLISH_FLG_ONE);
			ca.andReleasetimeLessThanOrEqualTo(dateFormatter.format(new Date()) + " 23:59:59");
			// 过期日期小于系统日期时，发布日期还得大于等于过期日期
			if (!CommonUtil.isEmpty(validDate)){
				if (dateFormatter.parse(validDate).before(dateFormatter.parse(CommonUtil.getSystemDate()))){
					ca.andReleasetimeGreaterThanOrEqualTo(dateFormatter.format(validDate) + " 00:00:00");
				}
			}
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		}

		int page = Integer.valueOf(pagenum);
		int rows = Integer.valueOf(pagerows);
		page = page==0 ? Integer.valueOf(1) : (page + 1);
		rows = rows==0 ? Integer.valueOf(10) : rows;
		int skipResults = (page-1) * rows;
		list = getSqlMapClientTemplate().queryForList("MI701.abatorgenerated_selectByExample", mi701Example, skipResults, rows);
		int total = this.countByExample(mi701Example);
		int totalPage = total/rows;
		int mod = total%rows;
		if(mod > 0){
			totalPage = totalPage + 1;
		}
		
		NewsBean queryResult = new NewsBean();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setTotalPage(totalPage);
		for (int i =0; i <= list.size()-1; i ++) {
			Mi701 mi701 = list.get(i);
			if (!CommonUtil.isEmpty(mi701.getReleasetime())){
				mi701.setReleasetime(mi701.getReleasetime().substring(0, 10));
			}
			if(!CommonUtil.isEmpty(mi701.getImage())){
				String turnImageUrl  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "turnImageUrl"+centerid);
				if(turnImageUrl.equals("1")){
					String pathYuan  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "serverPath"+centerid);
					String path  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appServerPath"+centerid);
					mi701.setImage(mi701.getImage().replaceAll(pathYuan, path));
				}
			}
			Mi707Example mi707exa = new Mi707Example();
			mi707exa.createCriteria().andCenteridEqualTo(centerid)
			.andItemidEqualTo(mi701.getClassification())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andStatusEqualTo(Constants.IS_OPEN);
			List<Mi707> mi707List = mi707Dao.selectByExample(mi707exa);
			if(!CommonUtil.isEmpty(mi707List)){
				mi701.setFreeuse6(mi707List.get(0).getItemval());
			}else{
				mi701.setFreeuse6("");
			}
			list.set(i, mi701);
		}
		queryResult.setNewsList(list);
		return queryResult;
	}
	
	// 查询mi701，如果上传分页标记则分页查询，否则查询所有
	@SuppressWarnings("unchecked")
	public NewsBean selectMi701ToAppForPageOrAll(String centerid, String channel, String classification,
			String keyword, String pagenum, String pagerows, String validDate,String yingyong) throws Exception {
		
		List<Mi701> list = new ArrayList<Mi701>();
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);
		Mi701Example mi701Example = new Mi701Example();
		mi701Example.setOrderByClause("releasetime desc");
		
		if (!CommonUtil.isEmpty(keyword)){
			String keywordTmp = null;
			System.out.println("keyword===="+keyword);
			if(CommonUtil.isEmpty(channel) || "30".equals(channel)){// 30现用于网站改版，前端做utf-8编码
				try {
					keywordTmp = URLDecoder.decode(keyword,"UTF-8");
					System.out.println("keywordTmp===="+keywordTmp);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}else if(!"20".equals(channel) && !"30".equals(channel)){
				keywordTmp = keyword;
			}else{
				try {
					keywordTmp = new String(keyword.getBytes("iso8859-1"),"UTF-8");
					
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			
			Mi701Example.Criteria ca = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(centerid)){
				ca.andCenteridEqualTo(centerid);
			}
			if(!CommonUtil.isEmpty(classification)){
				ca.andClassificationEqualTo(classification);
			}
			ca.andTitleLike("%"+keywordTmp+"%");
			ca.andFreeuse8Like("%"+yingyong+"%");
			ca.andFreeuse3EqualTo(Constants.PUBLISH_FLG_ONE);
			ca.andReleasetimeLessThanOrEqualTo(dateFormatter.format(new Date()) + " 23:59:59");
			// 过期日期小于系统日期时，发布日期还得大于等于过期日期
			if (!CommonUtil.isEmpty(validDate)){
				if (dateFormatter.parse(validDate).before(dateFormatter.parse(CommonUtil.getSystemDate()))){
					ca.andReleasetimeGreaterThanOrEqualTo(dateFormatter.format(validDate) + " 00:00:00");
				}
			}
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(ca);
			Mi701Example.Criteria ca1 = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(centerid)){
				ca1.andCenteridEqualTo(centerid);
			}
			if(!CommonUtil.isEmpty(classification)){
				ca1.andClassificationEqualTo(classification);
			}
			ca1.andIntroductionLike("%"+keywordTmp+"%");
			ca1.andFreeuse8Like("%"+yingyong+"%");
			ca1.andFreeuse3EqualTo(Constants.PUBLISH_FLG_ONE);
			ca1.andReleasetimeLessThanOrEqualTo(dateFormatter.format(new Date()) + " 23:59:59");
			// 过期日期小于系统日期时，发布日期还得大于等于过期日期
			if (!CommonUtil.isEmpty(validDate)){
				if (dateFormatter.parse(validDate).before(dateFormatter.parse(CommonUtil.getSystemDate()))){
					ca1.andReleasetimeGreaterThanOrEqualTo(dateFormatter.format(validDate) + " 00:00:00");
				}
			}
			ca1.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(ca1);
			Mi701Example.Criteria ca2 = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(centerid)){
				ca2.andCenteridEqualTo(centerid);
			}
			if(!CommonUtil.isEmpty(classification)){
				ca2.andClassificationEqualTo(classification);
			}
			ca2.andFreeuse8Like("%"+yingyong+"%");
			ca2.andContentLike("%"+keywordTmp+"%");
			ca2.andFreeuse3EqualTo(Constants.PUBLISH_FLG_ONE);
			ca2.andReleasetimeLessThanOrEqualTo(dateFormatter.format(new Date()) + " 23:59:59");
			// 过期日期小于系统日期时，发布日期还得大于等于过期日期
			if (!CommonUtil.isEmpty(validDate)){
				if (dateFormatter.parse(validDate).before(dateFormatter.parse(CommonUtil.getSystemDate()))){
					ca2.andReleasetimeGreaterThanOrEqualTo(dateFormatter.format(validDate) + " 00:00:00");
				}
			}
			ca2.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(ca2);
		}else{
			System.out.println("333=====333333");
			Mi701Example.Criteria ca = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(centerid)){
				ca.andCenteridEqualTo(centerid);
			}
			if(!CommonUtil.isEmpty(classification)){
				ca.andClassificationEqualTo(classification);
			}
			ca.andFreeuse8Like("%"+yingyong+"%");
			ca.andFreeuse3EqualTo(Constants.PUBLISH_FLG_ONE);
			ca.andReleasetimeLessThanOrEqualTo(dateFormatter.format(new Date()) + " 23:59:59");
			// 过期日期小于系统日期时，发布日期还得大于等于过期日期
			if (!CommonUtil.isEmpty(validDate)){
				if (dateFormatter.parse(validDate).before(dateFormatter.parse(CommonUtil.getSystemDate()))){
					ca.andReleasetimeGreaterThanOrEqualTo(dateFormatter.format(validDate) + " 00:00:00");
				}
			}
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		}

		int total = 0;
		int totalPage = 0;
		int page = Integer.valueOf(pagenum);
		int rows = Integer.valueOf(pagerows);
		if(rows==0){
			list = getSqlMapClientTemplate().queryForList("MI701.abatorgenerated_selectByExample", mi701Example);
			
		}else {
			page = page==0 ? Integer.valueOf(1) : (page + 1);
			rows = rows==0 ? Integer.valueOf(10) : rows;
			int skipResults = (page-1) * rows;
			list = getSqlMapClientTemplate().queryForList("MI701.abatorgenerated_selectByExample", mi701Example, skipResults, rows);
			total = this.countByExample(mi701Example);
			totalPage = total/rows;
			int mod = total%rows;
			if(mod > 0){
				totalPage = totalPage + 1;
			}
		}

		NewsBean queryResult = new NewsBean();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setTotalPage(totalPage);
		for (int i =0; i <= list.size()-1; i ++) {
			Mi701 mi701 = list.get(i);
			if (!CommonUtil.isEmpty(mi701.getReleasetime())){
				mi701.setReleasetime(mi701.getReleasetime().substring(0, 10));
			}
			if(!CommonUtil.isEmpty(mi701.getImage())){
				String turnImageUrl  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "turnImageUrl"+centerid);
				if(turnImageUrl.equals("1")){
					String pathYuan  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "serverPath"+centerid);
					String path  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appServerPath"+centerid);
					mi701.setImage(mi701.getImage().replaceAll(pathYuan, path));
				}
			}
			Mi707Example mi707exa = new Mi707Example();
			mi707exa.createCriteria().andCenteridEqualTo(centerid)
			.andItemidEqualTo(mi701.getClassification())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andStatusEqualTo(Constants.IS_OPEN);
			List<Mi707> mi707List = mi707Dao.selectByExample(mi707exa);
			if(!CommonUtil.isEmpty(mi707List)){
				mi701.setFreeuse6(mi707List.get(0).getItemval());
			}else{
				mi701.setFreeuse6("");
			}
			list.set(i, mi701);
		}
		queryResult.setNewsList(list);
		return queryResult;
	}
	
	@SuppressWarnings("unchecked")
	public NewsBean selectMi701ForAllClassfication(String centerid, String channel, List<String> classlist,
			String keyword, String pagenum, String pagerows, String validDate,String yingyong) throws Exception{
		
		List<Mi701> list = new ArrayList<Mi701>();
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);
		Mi701Example mi701Example = new Mi701Example();
		mi701Example.setOrderByClause("releasetime desc");
		
		if (!CommonUtil.isEmpty(keyword)){
			String keywordTmp = null;
			System.out.println("keyword===="+keyword);
			if(CommonUtil.isEmpty(channel) || "30".equals(channel)){// 30现用于网站改版，前端做utf-8编码
				try {
					keywordTmp = URLDecoder.decode(keyword,"UTF-8");
					System.out.println("keywordTmp===="+keywordTmp);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}else if(!"20".equals(channel) && !"30".equals(channel)){
				keywordTmp = keyword;
			}else{
				try {
					keywordTmp = new String(keyword.getBytes("iso8859-1"),"UTF-8");
					
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			
			Mi701Example.Criteria ca = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(centerid)){
				ca.andCenteridEqualTo(centerid);
			}
			if(!CommonUtil.isEmpty(classlist)){
				ca.andClassificationIn(classlist);
			}
			ca.andTitleLike("%"+keywordTmp+"%");
			ca.andFreeuse8Like("%"+yingyong+"%");
			ca.andFreeuse3EqualTo(Constants.PUBLISH_FLG_ONE);
			ca.andReleasetimeLessThanOrEqualTo(dateFormatter.format(new Date()) + " 23:59:59");
			// 过期日期小于系统日期时，发布日期还得大于等于过期日期
			if (!CommonUtil.isEmpty(validDate)){
				if (dateFormatter.parse(validDate).before(dateFormatter.parse(CommonUtil.getSystemDate()))){
					ca.andReleasetimeGreaterThanOrEqualTo(dateFormatter.format(validDate) + " 00:00:00");
				}
			}
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(ca);
			Mi701Example.Criteria ca1 = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(centerid)){
				ca1.andCenteridEqualTo(centerid);
			}
			if(!CommonUtil.isEmpty(classlist)){
				ca1.andClassificationIn(classlist);
			}
			ca1.andIntroductionLike("%"+keywordTmp+"%");
			ca1.andFreeuse8Like("%"+yingyong+"%");
			ca1.andFreeuse3EqualTo(Constants.PUBLISH_FLG_ONE);
			ca1.andReleasetimeLessThanOrEqualTo(dateFormatter.format(new Date()) + " 23:59:59");
			// 过期日期小于系统日期时，发布日期还得大于等于过期日期
			if (!CommonUtil.isEmpty(validDate)){
				if (dateFormatter.parse(validDate).before(dateFormatter.parse(CommonUtil.getSystemDate()))){
					ca1.andReleasetimeGreaterThanOrEqualTo(dateFormatter.format(validDate) + " 00:00:00");
				}
			}
			ca1.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(ca1);
			Mi701Example.Criteria ca2 = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(centerid)){
				ca2.andCenteridEqualTo(centerid);
			}
			if(!CommonUtil.isEmpty(classlist)){
				ca2.andClassificationIn(classlist);
			}
			ca2.andContentLike("%"+keywordTmp+"%");
			ca2.andFreeuse8Like("%"+yingyong+"%");
			ca2.andFreeuse3EqualTo(Constants.PUBLISH_FLG_ONE);
			ca2.andReleasetimeLessThanOrEqualTo(dateFormatter.format(new Date()) + " 23:59:59");
			// 过期日期小于系统日期时，发布日期还得大于等于过期日期
			if (!CommonUtil.isEmpty(validDate)){
				if (dateFormatter.parse(validDate).before(dateFormatter.parse(CommonUtil.getSystemDate()))){
					ca2.andReleasetimeGreaterThanOrEqualTo(dateFormatter.format(validDate) + " 00:00:00");
				}
			}
			ca2.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701Example.or(ca2);
		}else{
			System.out.println("333=====333333");
			Mi701Example.Criteria ca = mi701Example.createCriteria();
			if(!CommonUtil.isEmpty(centerid)){
				ca.andCenteridEqualTo(centerid);
			}
			if(!CommonUtil.isEmpty(classlist)){
				ca.andClassificationIn(classlist);
			}
			ca.andFreeuse8Like("%"+yingyong+"%");
			ca.andFreeuse3EqualTo(Constants.PUBLISH_FLG_ONE);
			ca.andReleasetimeLessThanOrEqualTo(dateFormatter.format(new Date()) + " 23:59:59");
			// 过期日期小于系统日期时，发布日期还得大于等于过期日期
			if (!CommonUtil.isEmpty(validDate)){
				if (dateFormatter.parse(validDate).before(dateFormatter.parse(CommonUtil.getSystemDate()))){
					ca.andReleasetimeGreaterThanOrEqualTo(dateFormatter.format(validDate) + " 00:00:00");
				}
			}
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		}

		int page = Integer.valueOf(pagenum);
		int rows = Integer.valueOf(pagerows);
		page = page==0 ? Integer.valueOf(1) : (page + 1);
		rows = rows==0 ? Integer.valueOf(10) : rows;
		int skipResults = (page-1) * rows;
		list = getSqlMapClientTemplate().queryForList("MI701.abatorgenerated_selectByExample", mi701Example, skipResults, rows);
		int total = this.countByExample(mi701Example);
		int totalPage = total/rows;
		int mod = total%rows;
		if(mod > 0){
			totalPage = totalPage + 1;
		}
		
		NewsBean queryResult = new NewsBean();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setTotalPage(totalPage);
		for (int i =0; i <= list.size()-1; i ++) {
			Mi701 mi701 = list.get(i);
			if (!CommonUtil.isEmpty(mi701.getReleasetime())){
				mi701.setReleasetime(mi701.getReleasetime().substring(0, 10));
			}
			if(!CommonUtil.isEmpty(mi701.getImage())){
				String turnImageUrl  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "turnImageUrl"+centerid);
				if(turnImageUrl.equals("1")){
					String pathYuan  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "serverPath"+centerid);
					String path  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appServerPath"+centerid);
					mi701.setImage(mi701.getImage().replaceAll(pathYuan, path));
				}
			}
			Mi707Example mi707exa = new Mi707Example();
			mi707exa.createCriteria().andCenteridEqualTo(centerid)
			.andItemidEqualTo(mi701.getClassification())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andStatusEqualTo(Constants.IS_OPEN);
			List<Mi707> mi707List = mi707Dao.selectByExample(mi707exa);
			if(!CommonUtil.isEmpty(mi707List)){
				mi701.setFreeuse6(mi707List.get(0).getItemval());
			}else{
				mi701.setFreeuse6("");
			}
			list.set(i, mi701);
		}
		queryResult.setNewsList(list);
		return queryResult;
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap> webapi701(String centerId, String pid, 
			String startDate, String endDate) throws Exception{
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("centerId", centerId);
		paraMap.put("pid", "%"+pid+"%");
		paraMap.put("startdate", startDate);
		paraMap.put("enddate", endDate);
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI701.webapi701", paraMap);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap> webapi701_01(String centerId, 
			String startDate, String endDate) throws Exception{
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("centerId", centerId);
		paraMap.put("startdate", startDate);
		paraMap.put("enddate", endDate);
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI701.webapi701_01", paraMap);
		return result;
	}
}
