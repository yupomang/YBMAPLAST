package com.yondervision.mi.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi701DAO;
import com.yondervision.mi.dao.CMi702DAO;
import com.yondervision.mi.dao.CMi703DAO;
import com.yondervision.mi.dao.CMi704DAO;
import com.yondervision.mi.dao.Mi705DAO;
import com.yondervision.mi.dto.Mi701;
import com.yondervision.mi.dto.Mi701Example;
import com.yondervision.mi.dto.Mi701WithBLOBs;
import com.yondervision.mi.dto.Mi702Example;
import com.yondervision.mi.dto.Mi703;
import com.yondervision.mi.dto.Mi703Example;
import com.yondervision.mi.dto.Mi704;
import com.yondervision.mi.dto.Mi704Example;
import com.yondervision.mi.dto.Mi705;
import com.yondervision.mi.dto.Mi705Example;
import com.yondervision.mi.form.AppApi70104Form;
import com.yondervision.mi.form.AppApi70105Form;
import com.yondervision.mi.form.AppApi70106Form;
import com.yondervision.mi.form.AppApi70107Form;
import com.yondervision.mi.form.AppApi70109Form;
import com.yondervision.mi.form.AppApi70110Form;
import com.yondervision.mi.form.AppApi701CommonForm;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.AppApi70101Result;
import com.yondervision.mi.result.AppApi70102Result;
import com.yondervision.mi.result.AppApi70103Result;
import com.yondervision.mi.result.AppApi70104Result;
import com.yondervision.mi.result.AppApi70108Result;
import com.yondervision.mi.result.AppApi70109Result;
import com.yondervision.mi.result.AppApi70110Result;
import com.yondervision.mi.result.NewspapersColumnsNewsBean;
import com.yondervision.mi.result.NewspapersNewsBean;
import com.yondervision.mi.result.NewspapersNewsCommentBean;
import com.yondervision.mi.result.NewspapersNewsDetailBean;
import com.yondervision.mi.result.NewspapersTitleInfoBean;
import com.yondervision.mi.service.AppApi701Service;
import com.yondervision.mi.service.CodeListApi001Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;

/** 
* @ClassName: AppApi701ServiceImpl
* @Description: 公积金报-移动客户端访问处理
* @author gongqi
* @date July 18, 2014 9:33:25 PM  
* 
*/ 
public class AppApi701ServiceImpl implements AppApi701Service {

	private CMi701DAO cmi701Dao = null;
	public void setCmi701Dao(CMi701DAO cmi701Dao) {
		this.cmi701Dao = cmi701Dao;
	}
	
	private CMi702DAO cmi702Dao = null;
	public void setCmi702Dao(CMi702DAO cmi702Dao) {
		this.cmi702Dao = cmi702Dao;
	}
	
	private CMi703DAO cmi703Dao = null;
	public void setCmi703Dao(CMi703DAO cmi703Dao) {
		this.cmi703Dao = cmi703Dao;
	}
	
	private CMi704DAO cmi704Dao = null;
	public void setCmi704Dao(CMi704DAO cmi704Dao) {
		this.cmi704Dao = cmi704Dao;
	}
	
	private Mi705DAO mi705Dao = null;
	public void setMi705Dao(Mi705DAO mi705Dao) {
		this.mi705Dao = mi705Dao;
	}

	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	
	@Autowired
	private CodeListApi001Service codeListApi001Service = null;

	public void setCodeListApi001Service(
			CodeListApi001Service codeListApi001Service) {
		this.codeListApi001Service = codeListApi001Service;
	}
	
	/*
	 * 初始页面访问数据获取处理
	 */
	@SuppressWarnings("unchecked")
	public AppApi70101Result appapi70101(AppApi701CommonForm form,HttpServletRequest request)
			throws Exception {
		
		// 1. 根据公共参数centerid获取所包含的有效的已发布的期次的列表，按期次编号降序
		Mi702Example timesExample = new Mi702Example();
		timesExample.setOrderByClause("seqno desc");
		timesExample.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andPublishflagEqualTo(Constants.PUBLISH_FLG_ONE);
		List<NewspapersTitleInfoBean> timesList = cmi702Dao.selectTimesList(timesExample);
		
		List<NewspapersTitleInfoBean> forumList = new ArrayList<NewspapersTitleInfoBean>();
		List<NewspapersColumnsNewsBean> columnsNewsList = new ArrayList<NewspapersColumnsNewsBean>();
		NewspapersColumnsNewsBean columnsNewsBean = new NewspapersColumnsNewsBean();
		
		if (timesList.size() > 0){
			
			// 2.获取最大期次包含的版块列表
			Mi704Example forumExample = new Mi704Example();
			forumExample.setOrderByClause("abs(itemid) asc");
			forumExample.createCriteria().andCenteridEqualTo(form.getCenterId()).andUpdicidEqualTo(timesList.get(0).getItemid());
			List<Mi704>  mi704List = cmi704Dao.selectByExample(forumExample);
			NewspapersTitleInfoBean forumBean = new NewspapersTitleInfoBean();
			for (int i = 0; i< mi704List.size(); i++){
				forumBean = new NewspapersTitleInfoBean();
				forumBean.setItemid(mi704List.get(i).getItemid());
				forumBean.setItemval(mi704List.get(i).getItemval());
				forumList.add(forumBean);
			}
			if (forumList.size() > 0){
				
				// 4. 根据本期看点标记，进行本期看点栏目的封装
				List<Mi701> newsHighlightResultList = getHighlightNews(form.getCenterId(), timesList.get(0).getItemid(),request.getAttribute("MI040Pid").toString());
				if (newsHighlightResultList.size() > 0){
					NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
					columnsBean.setItemid("highlight");
					columnsBean.setItemval("本期看点");
					
					List<NewspapersNewsBean> newsNoColumnList = getNewsList(form.getCenterId(), newsHighlightResultList);
					columnsNewsBean = new NewspapersColumnsNewsBean();
					columnsNewsBean.setColumns(columnsBean);
					columnsNewsBean.setNewsList(newsNoColumnList);
					
					columnsNewsList.add(columnsNewsBean);
				}
				
				// 3. 获取新闻信息表中最小版块编号包含的栏目列表，到码表获取各栏目名称，Mi701获取各栏目下的新闻列表
				form.setNewspapertimes(timesList.get(0).getItemid());
				form.setNewspaperforum(forumList.get(0).getItemid());
				List<HashMap<String,String>> columnsList = cmi701Dao.selectColumns(form);
				for (int i = 0; i<columnsList.size(); i++ ){
					String columnsItemId = columnsList.get(i).get("newspapercolumns").toString();
					
					String columnsItemVal = codeListApi001Service.getCodeVal(form.getCenterId(), Constants.COLUMNS_CODE+"."+columnsItemId);
					
					NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
					columnsBean.setItemid(columnsItemId);
					columnsBean.setItemval(columnsItemVal);
					
					//获取有归属栏目的新闻列表
					Mi701Example newsExample = new Mi701Example();
					newsExample.setOrderByClause("seqno asc");
					newsExample.createCriteria().andCenteridEqualTo(form.getCenterId())
					.andValidflagEqualTo(Constants.IS_VALIDFLAG)
					.andFreeuse8Like("%"+request.getAttribute("MI040Pid").toString()+"%")
					.andClassificationEqualTo(form.getNewspapertimes())
					.andNewspaperforumEqualTo(form.getNewspaperforum())
					.andNewspapercolumnsEqualTo(columnsItemId);
					List<Mi701> newsResultList = cmi701Dao.selectByExampleWithoutBLOBs(newsExample);
					
					List<NewspapersNewsBean> newsList = getNewsList(form.getCenterId(), newsResultList);
					
					columnsNewsBean = new NewspapersColumnsNewsBean();
					columnsNewsBean.setColumns(columnsBean);
					columnsNewsBean.setNewsList(newsList);
					
					columnsNewsList.add(columnsNewsBean);
				}
				
				// 5. 获取新闻信息表中最小版块编号包含的没有栏目归属（栏目=‘’）的新闻列表
				Mi701Example newsExample = new Mi701Example();
				newsExample.setOrderByClause("seqno asc");
				
				Mi701Example.Criteria ca = newsExample.createCriteria();
				ca.andCenteridEqualTo(form.getCenterId())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG)
				.andFreeuse8Like("%"+request.getAttribute("MI040Pid").toString()+"%")
				.andClassificationEqualTo(form.getNewspapertimes())
				.andNewspaperforumEqualTo(form.getNewspaperforum())
				.andNewspapercolumnsEqualTo("");
				newsExample.or(ca);
				Mi701Example.Criteria ca1 = newsExample.createCriteria();
				ca1.andCenteridEqualTo(form.getCenterId())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG)
				.andFreeuse8Like("%"+request.getAttribute("MI040Pid").toString()+"%")
				.andClassificationEqualTo(form.getNewspapertimes())
				.andNewspaperforumEqualTo(form.getNewspaperforum())
				.andNewspapercolumnsIsNull();
				newsExample.or(ca1);
				
				List<Mi701> newsNoColumnResultList = cmi701Dao.selectByExampleWithoutBLOBs(newsExample);

				if (newsNoColumnResultList.size() > 0){
					NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
					columnsBean.setItemid("other");
					columnsBean.setItemval("其他");
					
					List<NewspapersNewsBean> newsNoColumnList = getNewsList(form.getCenterId(), newsNoColumnResultList);
					columnsNewsBean = new NewspapersColumnsNewsBean();
					columnsNewsBean.setColumns(columnsBean);
					columnsNewsBean.setNewsList(newsNoColumnList);
					
					columnsNewsList.add(columnsNewsBean);
				}
			}

		}

		AppApi70101Result result = new AppApi70101Result();
		result.setTimesList(timesList);
		result.setForumList(forumList);
		result.setColumnsNewsList(columnsNewsList);
		return result;
	}
	
	/*
	 * 根据报刊期次，进行信息展示数据获取处理
	 */
	@SuppressWarnings("unchecked")
	public AppApi70102Result appapi70102(AppApi701CommonForm form,HttpServletRequest request) throws Exception {
		
		List<NewspapersTitleInfoBean> forumList = new ArrayList<NewspapersTitleInfoBean>();
		List<NewspapersColumnsNewsBean> columnsNewsList = new ArrayList<NewspapersColumnsNewsBean>();
		NewspapersColumnsNewsBean columnsNewsBean = new NewspapersColumnsNewsBean();

		System.out.println("传入的期次ID为："+form.getNewspapertimes());
		
		// 1.获取传入期次包含的版块列表
		Mi704Example forumExample = new Mi704Example();
		forumExample.setOrderByClause("abs(itemid) asc");
		forumExample.createCriteria().andCenteridEqualTo(form.getCenterId()).andUpdicidEqualTo(form.getNewspapertimes());
		List<Mi704>  mi704List = cmi704Dao.selectByExample(forumExample);
		NewspapersTitleInfoBean forumBean = new NewspapersTitleInfoBean();
		for (int i = 0; i< mi704List.size(); i++){
			forumBean = new NewspapersTitleInfoBean();
			forumBean.setItemid(mi704List.get(i).getItemid());
			forumBean.setItemval(mi704List.get(i).getItemval());
			forumList.add(forumBean);
		}
		if (forumList.size() > 0){
			
			// 3. 根据本期看点标记，进行本期看点栏目的封装
			List<Mi701> newsHighlightResultList = getHighlightNews(form.getCenterId(), form.getNewspapertimes(),request.getAttribute("MI040Pid").toString());
			if (newsHighlightResultList.size() > 0){
				NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
				columnsBean.setItemid("highlight");
				columnsBean.setItemval("本期看点");
				
				List<NewspapersNewsBean> newsNoColumnList = getNewsList(form.getCenterId(), newsHighlightResultList);
				columnsNewsBean = new NewspapersColumnsNewsBean();
				columnsNewsBean.setColumns(columnsBean);
				columnsNewsBean.setNewsList(newsNoColumnList);
				
				columnsNewsList.add(columnsNewsBean);
			}
			
			// 2. 获取新闻信息表中最小版块编号包含的栏目列表，到版块栏目配置表Mi704获取各栏目名称，Mi701获取各栏目下的新闻列表
			form.setNewspaperforum(forumList.get(0).getItemid());
			form.setChannel(request.getAttribute("MI040Pid").toString());
			List<HashMap<String,String>> columnsList = cmi701Dao.selectColumns(form);
			for (int i = 0; i<columnsList.size(); i++ ){
				String columnsItemId = columnsList.get(i).get("newspapercolumns").toString();
				
				String columnsItemVal = codeListApi001Service.getCodeVal(form.getCenterId(), Constants.COLUMNS_CODE+"."+columnsItemId);
				
				NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
				columnsBean.setItemid(columnsItemId);
				columnsBean.setItemval(columnsItemVal);
				
				//获取有归属栏目的新闻列表
				Mi701Example newsExample = new Mi701Example();
				newsExample.setOrderByClause("seqno asc");
				newsExample.createCriteria().andCenteridEqualTo(form.getCenterId())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG)
				.andFreeuse8Like("%"+form.getChannel()+"%")
				.andClassificationEqualTo(form.getNewspapertimes())
				.andNewspaperforumEqualTo(form.getNewspaperforum())
				.andNewspapercolumnsEqualTo(columnsItemId);
				List<Mi701> newsResultList = cmi701Dao.selectByExampleWithoutBLOBs(newsExample);
				
				List<NewspapersNewsBean> newsList = getNewsList(form.getCenterId(), newsResultList);
				
				columnsNewsBean = new NewspapersColumnsNewsBean();
				columnsNewsBean.setColumns(columnsBean);
				columnsNewsBean.setNewsList(newsList);
				
				columnsNewsList.add(columnsNewsBean);
			}
			
			// 4. 获取新闻信息表中最小版块编号包含的没有栏目归属（栏目=‘’）的新闻列表
			Mi701Example newsExample = new Mi701Example();
			newsExample.setOrderByClause("seqno asc");
			
			Mi701Example.Criteria ca = newsExample.createCriteria();
			ca.andCenteridEqualTo(form.getCenterId())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andFreeuse8Like("%"+form.getChannel()+"%")
			.andClassificationEqualTo(form.getNewspapertimes())
			.andNewspaperforumEqualTo(form.getNewspaperforum())
			.andNewspapercolumnsEqualTo("");
			newsExample.or(ca);
			Mi701Example.Criteria ca1 = newsExample.createCriteria();
			ca1.andCenteridEqualTo(form.getCenterId())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andFreeuse8Like("%"+form.getChannel()+"%")
			.andClassificationEqualTo(form.getNewspapertimes())
			.andNewspaperforumEqualTo(form.getNewspaperforum())
			.andNewspapercolumnsIsNull();
			newsExample.or(ca1);
			
			List<Mi701> newsNoColumnResultList = cmi701Dao.selectByExampleWithoutBLOBs(newsExample);

			if (newsNoColumnResultList.size() > 0){
				NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
				columnsBean.setItemid("other");
				columnsBean.setItemval("其他");
				
				List<NewspapersNewsBean> newsNoColumnList = getNewsList(form.getCenterId(), newsNoColumnResultList);
				columnsNewsBean = new NewspapersColumnsNewsBean();
				columnsNewsBean.setColumns(columnsBean);
				columnsNewsBean.setNewsList(newsNoColumnList);
				
				columnsNewsList.add(columnsNewsBean);
			}
		}

		AppApi70102Result result = new AppApi70102Result();
		result.setForumList(forumList);
		result.setColumnsNewsList(columnsNewsList);
		return result;
	}
	
	/*
	 * 根据报刊期次，版块，进行信息展示数据获取处理
	 */
	@SuppressWarnings("unchecked")
	public AppApi70103Result appapi70103(AppApi701CommonForm form,HttpServletRequest request) throws Exception {

		List<NewspapersColumnsNewsBean> columnsNewsList = new ArrayList<NewspapersColumnsNewsBean>();
		NewspapersColumnsNewsBean columnsNewsBean = new NewspapersColumnsNewsBean();
		form.setChannel(request.getAttribute("MI040Pid").toString());
		
		// 1. 根据传入版块Id判断版块栏目管理mi704中有无对应的记录
		Mi704Example dicidExample = new Mi704Example();
		dicidExample.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andUpdicidEqualTo(form.getNewspapertimes())
		.andItemidEqualTo(form.getNewspaperforum());
		List<Mi704> dicidList = cmi704Dao.selectByExample(dicidExample);
		if (dicidList.size() >0){
			// 获取传入期次包含的版块列表
			Mi704Example forumExample = new Mi704Example();
			forumExample.setOrderByClause("abs(itemid) asc");
			forumExample.createCriteria().andCenteridEqualTo(form.getCenterId()).andUpdicidEqualTo(form.getNewspapertimes());
			List<Mi704>  mi704List = cmi704Dao.selectByExample(forumExample);
			// 3. 判定当前待查询版块是否是头版，是则根据本期看点标记，进行本期看点栏目的封装
			if (mi704List.get(0).getItemid().equals(form.getNewspaperforum())){
				List<Mi701> newsResultList = getHighlightNews(form.getCenterId(), form.getNewspapertimes(),form.getChannel());
				if (newsResultList.size() > 0){
					NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
					columnsBean.setItemid("highlight");
					columnsBean.setItemval("本期看点");
					
					List<NewspapersNewsBean> newsNoColumnList = getNewsList(form.getCenterId(), newsResultList);
					columnsNewsBean = new NewspapersColumnsNewsBean();
					columnsNewsBean.setColumns(columnsBean);
					columnsNewsBean.setNewsList(newsNoColumnList);
					
					columnsNewsList.add(columnsNewsBean);
				}
			}
			
			// 2. 获取新闻信息表中传入的参数包含的栏目列表，到版块栏目配置表Mi704获取各栏目名称，Mi701获取各栏目下的新闻列表
			List<HashMap<String,String>> columnsList = cmi701Dao.selectColumns(form);
			for (int i = 0; i<columnsList.size(); i++ ){
				String columnsItemId = columnsList.get(i).get("newspapercolumns").toString();
				
				String columnsItemVal = codeListApi001Service.getCodeVal(form.getCenterId(), Constants.COLUMNS_CODE+"."+columnsItemId);
				
				NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
				columnsBean.setItemid(columnsItemId);
				columnsBean.setItemval(columnsItemVal);
				
				//获取有归属栏目的新闻列表
				Mi701Example newsExample = new Mi701Example();
				newsExample.setOrderByClause("seqno asc");
				newsExample.createCriteria().andCenteridEqualTo(form.getCenterId())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG)
				.andFreeuse8Like("%"+form.getChannel()+"%")
				.andClassificationEqualTo(form.getNewspapertimes())
				.andNewspaperforumEqualTo(form.getNewspaperforum())
				.andNewspapercolumnsEqualTo(columnsItemId);
				List<Mi701> newsResultList = cmi701Dao.selectByExampleWithoutBLOBs(newsExample);
				
				List<NewspapersNewsBean> newsList = getNewsList(form.getCenterId(), newsResultList);
				
				columnsNewsBean = new NewspapersColumnsNewsBean();
				columnsNewsBean.setColumns(columnsBean);
				columnsNewsBean.setNewsList(newsList);
				
				columnsNewsList.add(columnsNewsBean);
			}
			
			// 4. 获取新闻信息表中最小版块编号包含的没有栏目归属（栏目=‘’）的新闻列表
			Mi701Example newsExample = new Mi701Example();
			newsExample.setOrderByClause("seqno asc");
			
			Mi701Example.Criteria ca = newsExample.createCriteria();
			ca.andCenteridEqualTo(form.getCenterId())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andFreeuse8Like("%"+form.getChannel()+"%")
			.andClassificationEqualTo(form.getNewspapertimes())
			.andNewspaperforumEqualTo(form.getNewspaperforum())
			.andNewspapercolumnsEqualTo("");
			newsExample.or(ca);
			Mi701Example.Criteria ca1 = newsExample.createCriteria();
			ca1.andCenteridEqualTo(form.getCenterId())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andFreeuse8Like("%"+form.getChannel()+"%")
			.andClassificationEqualTo(form.getNewspapertimes())
			.andNewspaperforumEqualTo(form.getNewspaperforum())
			.andNewspapercolumnsIsNull();
			newsExample.or(ca1);
			
			List<Mi701> newsNoColumnResultList = cmi701Dao.selectByExampleWithoutBLOBs(newsExample);

			if (newsNoColumnResultList.size() > 0){
				NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
				columnsBean.setItemid("other");
				columnsBean.setItemval("其他");
				
				List<NewspapersNewsBean> newsNoColumnList = getNewsList(form.getCenterId(), newsNoColumnResultList);
				columnsNewsBean = new NewspapersColumnsNewsBean();
				columnsNewsBean.setColumns(columnsBean);
				columnsNewsBean.setNewsList(newsNoColumnList);
				
				columnsNewsList.add(columnsNewsBean);
			}
		}
		
		AppApi70103Result result = new AppApi70103Result();
		result.setColumnsNewsList(columnsNewsList);
		return result;
	}
	
	/*
	 * 根据新闻ID,进行新闻详细页面的展示数据的获取
	 */
	@SuppressWarnings("unchecked")
	public AppApi70104Result appapi70104(AppApi70104Form form) throws Exception {
		AppApi70104Result result = new AppApi70104Result();
		NewspapersNewsDetailBean newsDeatilBean = new NewspapersNewsDetailBean();
		NewspapersNewsCommentBean newsCommentBean = new NewspapersNewsCommentBean();
		List<NewspapersNewsCommentBean> commentList = new ArrayList<NewspapersNewsCommentBean>();
		
		// 1. 根据传入新闻ID，进行新闻信息获取封装
		Mi701WithBLOBs mi701 = cmi701Dao.selectByPrimaryKey(Integer.parseInt(form.getTitleId()));
		// 1.1 获取该用户对此新闻的点赞标记
		String newsPraiseFlg = Constants.IS_PRAISEFLG;
		if (0 != mi701.getPraisecounts()){
			Mi705Example mi705Example = new Mi705Example();
			mi705Example.createCriteria().andCenteridEqualTo(form.getCenterId())
			.andUseridEqualTo(form.getUser_Id())
			.andInfoseqnoEqualTo("N"+form.getTitleId())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			List<Mi705> mi705List = mi705Dao.selectByExample(mi705Example);
			if (mi705List.size() != 0){
				String mi705PraiseFlg = mi705List.get(0).getPraiseflg();
				if (Constants.IS_NOT_PRAISEFLG.equals(mi705PraiseFlg)){
					newsPraiseFlg = Constants.IS_NOT_PRAISEFLG;
				}
			}
		}
		
		newsDeatilBean.setTitle(mi701.getTitle());
		newsDeatilBean.setCitedtitle(mi701.getCitedtitle());
		newsDeatilBean.setSubtopics(mi701.getSubtopics());
		newsDeatilBean.setSource(mi701.getSource());
		String turnImageUrl  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "turnImageUrl"+form.getCenterId());
		if(turnImageUrl.equals("1")){
			String pathYuan  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "serverPath"+form.getCenterId());
			String path  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appServerPath"+form.getCenterId());
			System.out.println("【新闻内容内网地址:"+pathYuan+"】");
			System.out.println("【新闻内容外网地址:"+path+"】");
			newsDeatilBean.setContent( mi701.getContent().replaceAll(pathYuan, path));
		}else{
			newsDeatilBean.setContent(mi701.getContent());
		}
//		String path  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "serverPath");
//		newsDeatilBean.setContent(mi701.getContent().replaceAll("src=\"/YBMAP/", "src=\""+path+"/YBMAP/"));
		newsDeatilBean.setPraisecounts(mi701.getPraisecounts());
		newsDeatilBean.setPraiseFlg(newsPraiseFlg);
		
		// 2. 获取对应新闻ID的评论列表
		List<Mi705> mi705List = new ArrayList<Mi705>();
		Mi705Example mi705ComExample = new Mi705Example();
		Mi703Example mi703Example = new Mi703Example();
		mi703Example.setOrderByClause("datecreated desc");
		mi703Example.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andNewsseqnoEqualTo(Integer.parseInt(form.getTitleId()));
		List<Mi703> mi703List = cmi703Dao.selectByExample(mi703Example);
		for(int i = 0; i < mi703List.size(); i++){
			Mi703 mi703 = mi703List.get(i);
			// 2.1 获取该用户对此评论的点赞标记
			String commentPraiseFlg = Constants.IS_PRAISEFLG;
			if (0 != mi703.getPraisecounts()){
				mi705ComExample.clear();
				mi705ComExample.createCriteria().andCenteridEqualTo(form.getCenterId())
				.andUseridEqualTo(form.getUser_Id())
				.andInfoseqnoEqualTo("P"+(mi703.getSeqno().toString()))
				.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi705List = mi705Dao.selectByExample(mi705ComExample);
				if (mi705List.size() != 0){
					String mi705PraiseFlg = mi705List.get(0).getPraiseflg();
					if (Constants.IS_NOT_PRAISEFLG.equals(mi705PraiseFlg)){
						commentPraiseFlg = Constants.IS_NOT_PRAISEFLG;
					}
				}
			}

			newsCommentBean = new NewspapersNewsCommentBean();
			newsCommentBean.setCommentSeqno(mi703.getSeqno().toString());
			newsCommentBean.setCommentUser(mi703.getUserid());
			newsCommentBean.setCommentTime(mi703.getDatecreated().substring(0, 16));
			newsCommentBean.setCommentCotent(mi703.getContent());
			newsCommentBean.setPraisecounts(mi703.getPraisecounts());
			newsCommentBean.setCommentUserId(mi703.getFreeuse1());
			newsCommentBean.setPraiseFlg(commentPraiseFlg);
			commentList.add(newsCommentBean);
		}
		
		result.setNewsDetal(newsDeatilBean);
		result.setCommentList(commentList);
		return result;
	}
	
	/*
	 * 对新闻进行评论--增加评论
	 */
	public void appapi70105(AppApi70105Form form) throws Exception {
		
		Logger log = LoggerUtil.getLogger();
		
		Integer seqno = commonUtil.genKey("MI703").intValue();
		if (CommonUtil.isEmpty(seqno)) {
			log.error(ERROR.NULL_KEY.getLogText("新闻评论MI703"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.NULL_KEY.getLogText("新闻评论MI703"));
		}
		
		String commentContent = null;
		if(!"20".equals(form.getChannel())){
			commentContent = form.getCommentContent();
		}else{
			try {
				commentContent = new String(form.getCommentContent().getBytes("iso8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		Mi703 record = new Mi703();
		record.setSeqno(seqno);
		record.setCenterid(form.getCenterId());
		record.setNewsseqno(Integer.parseInt(form.getNewsSeqno()));
		// 评论来源处理
		if(Constants.CHANNELTYPE_APP.equals(form.getChannel())){
			if(Constants.MI105_DEVTYPE_ANDROID.equals(form.getDeviceType())){
				record.setDevtype(Constants.ANDROID_MOBILE_CLIENT);
			}else{
				record.setDevtype(Constants.IOS_MOBILE_CLIENT);
			}
		}else{
			record.setDevtype(Constants.WEIXIN_MOBILE_CLIENT);
		}
		
		record.setDevid(form.getDeviceToken());
		record.setUserid(form.getCommentUser());
		record.setContent(commentContent);
		record.setDatecreated(CommonUtil.getSystemDate());
		record.setPraisecounts(0);
		record.setValidflag(Constants.IS_VALIDFLAG);
		record.setFreeuse1(form.getCommentUserId());
		
		cmi703Dao.insert(record);
	}
	
	/*
	 * 对新闻进行点赞/取消点赞
	 */
	@SuppressWarnings("unchecked")
	public void appapi70106(AppApi70106Form form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		
		Mi705Example mi705Example = new Mi705Example();
		mi705Example.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andUseridEqualTo(form.getUser_Id())
		.andInfoseqnoEqualTo("N" + form.getNewsSeqno());
		List<Mi705> resultList = mi705Dao.selectByExample(mi705Example);
		if (resultList.size() != 0){
			String praiseflg = "";
			if (Constants.IS_PRAISEFLG.equals(form.getPraiseFlg())){
				praiseflg = Constants.IS_NOT_PRAISEFLG;
			}else {
				praiseflg = Constants.IS_PRAISEFLG;
			}
			Mi705 updataRecord = new Mi705();
			updataRecord.setPraiseflg(praiseflg);
			updataRecord.setDatemodified(CommonUtil.getSystemDate());
			mi705Dao.updateByExampleSelective(updataRecord, mi705Example);
			
		}else{
			Mi705 record = new Mi705();
			
			Integer seqno = commonUtil.genKey("MI705").intValue();
			if (CommonUtil.isEmpty(seqno)) {
				log.error(ERROR.NULL_KEY.getLogText("用户点赞标记管理MI705"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
						ERROR.NULL_KEY.getLogText("用户点赞标记管理MI705"));
			}
			record.setSeqno(seqno);
			record.setCenterid(form.getCenterId());
			record.setUserid(form.getUser_Id());
			record.setUsername(form.getUserName());
			record.setInfoseqno("N" + form.getNewsSeqno());
			record.setPraiseflg(Constants.IS_NOT_PRAISEFLG);
			record.setDatecreated(CommonUtil.getSystemDate());
			record.setValidflag(Constants.IS_VALIDFLAG);
			mi705Dao.insert(record);
		}
		
		Mi701 record = new Mi701();
		record.setSeqno(Integer.parseInt(form.getNewsSeqno()));
		if (Constants.IS_PRAISEFLG.equals(form.getPraiseFlg())){
			cmi701Dao.updatePraisecountsByPrimaryKey(record);
		}else{
			cmi701Dao.updatePraisecountsSubByPrimaryKey(record);
		}
	}
	
	/*
	 * 对评论进行点赞
	 */
	@SuppressWarnings("unchecked")
	public void appapi70107(AppApi70107Form form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		
		Mi705Example mi705Example = new Mi705Example();
		mi705Example.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andUseridEqualTo(form.getUser_Id())
		.andInfoseqnoEqualTo("P" + form.getCommentSeqno());
		List<Mi705> resultList = mi705Dao.selectByExample(mi705Example);
		if (resultList.size() != 0){
			String praiseflg = "";
			if (Constants.IS_PRAISEFLG.equals(form.getPraiseFlg())){
				praiseflg = Constants.IS_NOT_PRAISEFLG;
			}else {
				praiseflg = Constants.IS_PRAISEFLG;
			}
			Mi705 updataRecord = new Mi705();
			updataRecord.setPraiseflg(praiseflg);
			updataRecord.setDatemodified(CommonUtil.getSystemDate());
			mi705Dao.updateByExampleSelective(updataRecord, mi705Example);
			
		}else{
			Mi705 record = new Mi705();
			
			Integer seqno = commonUtil.genKey("MI705").intValue();
			if (CommonUtil.isEmpty(seqno)) {
				log.error(ERROR.NULL_KEY.getLogText("用户点赞标记管理MI705"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
						ERROR.NULL_KEY.getLogText("用户点赞标记管理MI705"));
			}
			record.setSeqno(seqno);
			record.setCenterid(form.getCenterId());
			record.setUserid(form.getUser_Id());
			record.setUsername(form.getUserName());
			record.setInfoseqno("P" + form.getCommentSeqno());
			record.setPraiseflg(Constants.IS_NOT_PRAISEFLG);
			record.setDatecreated(CommonUtil.getSystemDate());
			record.setValidflag(Constants.IS_VALIDFLAG);
			mi705Dao.insert(record);
		}
		
		Mi703 record = new Mi703();
		record.setSeqno(Integer.parseInt(form.getCommentSeqno()));
		if (Constants.IS_PRAISEFLG.equals(form.getPraiseFlg())){
			cmi703Dao.updatePraisecountsByPrimaryKey(record);
		}else{
			cmi703Dao.updatePraisecountsSubByPrimaryKey(record);
		}
	}
	
	/*
	 * 订阅设置-获取待订阅的栏目范围
	 */
	public List<AppApi70108Result> appapi70108(AppApiCommonForm form,HttpServletRequest request) throws Exception{
		List<AppApi70108Result> resultList = new ArrayList<AppApi70108Result>();
		form.setChannel(request.getAttribute("MI040Pid").toString());
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("centerId", form.getCenterId());
		paraMap.put("channel", form.getChannel());
		
		AppApi70108Result result = new AppApi70108Result();
		
		List<HashMap<String, String>> resultMapList = cmi701Dao.selfSelectColumnsByCenterid(paraMap);
		String itemid = null;
		String itemval = null;
		for(int i = 0;i < resultMapList.size(); i ++){
			result = new AppApi70108Result();
			itemid = resultMapList.get(i).get("itemid");
			itemval = resultMapList.get(i).get("itemval");
			System.out.println("获取到的栏目Id："+itemid);
			System.out.println("获取到的栏目名称："+itemval);
			result.setItemid(itemid);
			result.setItemval(itemval);
			
			resultList.add(result);
		}
		
		return resultList;
	}
	/*
	 * 我的订阅-初始页面访问数据获取处理
	 */
	@SuppressWarnings("unchecked")
	public AppApi70109Result appapi70109(AppApi70109Form form,HttpServletRequest request) throws Exception{
		AppApi70109Result result = new AppApi70109Result();
		form.setChannel(request.getAttribute("MI040Pid").toString());
		List<NewspapersColumnsNewsBean> columnsNewsList = new ArrayList<NewspapersColumnsNewsBean>();
		NewspapersColumnsNewsBean columnsNewsBean = new NewspapersColumnsNewsBean();
		NewspapersTitleInfoBean columns = new NewspapersTitleInfoBean();
		List<NewspapersNewsBean> newsList = new ArrayList<NewspapersNewsBean>();

		// 1. 根据公共参数centerid获取所包含的有效的已发布的期次的列表，按期次编号降序
		Mi702Example timesExample = new Mi702Example();
		timesExample.setOrderByClause("seqno desc");
		timesExample.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andPublishflagEqualTo(Constants.PUBLISH_FLG_ONE);
		List<NewspapersTitleInfoBean> timesList = cmi702Dao.selectTimesList(timesExample);
		if(timesList.size() > 0){

			// 2. 获取最大期次下包含的订阅栏目范围中的栏目新闻列表
			System.out.println("获取到的最大的期次ID为："+timesList.get(0).getItemid());
			System.out.println("获取到的最大的期次名称为："+timesList.get(0).getItemval());
			
			String[] columnsStrs = form.getNewspapercolumns().split(",");
			
			for (int i = 0; i < columnsStrs.length; i++){
				Mi701Example mi701Example = new Mi701Example();
				mi701Example.setOrderByClause("seqno asc");
				mi701Example.createCriteria().andCenteridEqualTo(form.getCenterId())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG)
				.andFreeuse8Like("%"+form.getChannel()+"%")
				.andClassificationEqualTo(timesList.get(0).getItemid())
				.andNewspapercolumnsEqualTo(columnsStrs[i]);
				List<Mi701> mi701List = cmi701Dao.selectByExampleWithoutBLOBs(mi701Example);
				
				if (mi701List.size() > 0){
					// 获取到订阅栏目的新闻列表，才给应答结果中的栏目对象封装值
					String columnName = codeListApi001Service.getCodeVal(form.getCenterId(), Constants.COLUMNS_CODE+"."+columnsStrs[i]);
					System.out.println("获取到的栏目名称："+columnName);
					
					columns = new NewspapersTitleInfoBean();
					columns.setItemid(columnsStrs[i]);
					columns.setItemval(columnName);
					
					newsList = getNewsList(form.getCenterId(), mi701List);
					
					columnsNewsBean = new NewspapersColumnsNewsBean();
					columnsNewsBean.setColumns(columns);
					columnsNewsBean.setNewsList(newsList);
					columnsNewsList.add(columnsNewsBean);
				}
			}
		}
		
		result.setTimesList(timesList);
		result.setColumnsNewsList(columnsNewsList);
		return result;
	}
	
	/*
	 * 我的订阅-根据报刊期次，进行信息展示数据获取处理
	 */
	@SuppressWarnings("unchecked")
	public AppApi70110Result appapi70110(AppApi70110Form form,HttpServletRequest request) throws Exception{
		AppApi70110Result result = new AppApi70110Result();
		form.setChannel(request.getAttribute("MI040Pid").toString());
		
		List<NewspapersColumnsNewsBean> columnsNewsList = new ArrayList<NewspapersColumnsNewsBean>();
		NewspapersColumnsNewsBean columnsNewsBean = new NewspapersColumnsNewsBean();
		NewspapersTitleInfoBean columns = new NewspapersTitleInfoBean();
		List<NewspapersNewsBean> newsList = new ArrayList<NewspapersNewsBean>();

		// 1. 获取订阅栏目范围中的栏目新闻列表
		String[] columnsStrs = form.getNewspapercolumns().split(",");
		
		for (int i = 0; i < columnsStrs.length; i++){
			Mi701Example mi701Example = new Mi701Example();
			mi701Example.setOrderByClause("seqno asc");
			mi701Example.createCriteria().andCenteridEqualTo(form.getCenterId())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andFreeuse8Like("%"+form.getChannel()+"%")
			.andClassificationEqualTo(form.getNewspapertimes())
			.andNewspapercolumnsEqualTo(columnsStrs[i]);
			List<Mi701> mi701List = cmi701Dao.selectByExampleWithoutBLOBs(mi701Example);
			
			if (mi701List.size() > 0){
				// 获取到订阅栏目的新闻列表，才给应答结果中的栏目对象封装值
				String columnName = codeListApi001Service.getCodeVal(form.getCenterId(), Constants.COLUMNS_CODE+"."+columnsStrs[i]);
				
				columns = new NewspapersTitleInfoBean();
				columns.setItemid(columnsStrs[i]);
				columns.setItemval(columnName);
				
				newsList = getNewsList(form.getCenterId(), mi701List);
				
				columnsNewsBean = new NewspapersColumnsNewsBean();
				columnsNewsBean.setColumns(columns);
				columnsNewsBean.setNewsList(newsList);
				columnsNewsList.add(columnsNewsBean);
			}
		}
		
		result.setColumnsNewsList(columnsNewsList);
		return result;
	}
	
	/*
	 * 订阅设置-获取待订阅的版块范围
	 */
	public List<AppApi70108Result> appapi70111(AppApiCommonForm form,HttpServletRequest request) throws Exception{
		List<AppApi70108Result> resultList = new ArrayList<AppApi70108Result>();
		form.setChannel(request.getAttribute("MI040Pid").toString());
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("centerId", form.getCenterId());
		paraMap.put("channel", form.getChannel());
		
		AppApi70108Result result = new AppApi70108Result();
		
		List<HashMap<String, String>> resultMapList = cmi701Dao.selfSelectForumByCenterid(paraMap);
		String itemid = null;
		String itemval = null;
		for(int i = 0;i < resultMapList.size(); i ++){
			result = new AppApi70108Result();
			itemid = resultMapList.get(i).get("itemid");
			itemval = resultMapList.get(i).get("itemval");
			System.out.println("获取到的版块Id："+itemid);
			System.out.println("获取到的版块名称："+itemval);
			result.setItemid(itemid);
			result.setItemval(itemval);
			
			resultList.add(result);
		}
		
		return resultList;
	}
	
	/*
	 * 我的订阅-初始页面访问数据获取处理（根据订阅版块范围）
	 */
	@SuppressWarnings("unchecked")
	public AppApi70109Result appapi70112(AppApi70109Form form,HttpServletRequest request) throws Exception{
		AppApi70109Result result = new AppApi70109Result();
		form.setChannel(request.getAttribute("MI040Pid").toString());
		
		List<NewspapersColumnsNewsBean> columnsNewsList = new ArrayList<NewspapersColumnsNewsBean>();
		NewspapersColumnsNewsBean columnsNewsBean = new NewspapersColumnsNewsBean();
		NewspapersTitleInfoBean forum = new NewspapersTitleInfoBean();
		List<NewspapersNewsBean> newsList = new ArrayList<NewspapersNewsBean>();

		// 1. 根据公共参数centerid获取所包含的有效的已发布的期次的列表，按期次编号降序
		Mi702Example timesExample = new Mi702Example();
		timesExample.setOrderByClause("seqno desc");
		timesExample.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andPublishflagEqualTo(Constants.PUBLISH_FLG_ONE);
		List<NewspapersTitleInfoBean> timesList = cmi702Dao.selectTimesList(timesExample);
		if(timesList.size() > 0){

			// 2. 获取最大期次下包含的订阅版块范围中的版块新闻列表
			System.out.println("获取到的最大的期次ID为："+timesList.get(0).getItemid());
			System.out.println("获取到的最大的期次名称为："+timesList.get(0).getItemval());
			// forumStrs中存放的为订阅的版块
			String[] forumStrs = form.getNewspapercolumns().split(",");
			
			for (int i = 0; i < forumStrs.length; i++){
				Mi701Example mi701Example = new Mi701Example();
				mi701Example.setOrderByClause("seqno asc");
				mi701Example.createCriteria().andCenteridEqualTo(form.getCenterId())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG)
				.andFreeuse8Like("%"+form.getChannel()+"%")
				.andClassificationEqualTo(timesList.get(0).getItemid())
				.andNewspaperforumEqualTo(forumStrs[i]);
				List<Mi701> mi701List = cmi701Dao.selectByExampleWithoutBLOBs(mi701Example);
				
				if (mi701List.size() > 0){
					// 获取到订阅版块的新闻列表，才给应答结果中的版块对象封装值
					String forumName = codeListApi001Service.getCodeVal(form.getCenterId(), Constants.FORUM_CODE+"."+forumStrs[i]);
					System.out.println("获取到的版块名称："+forumName);
					
					forum = new NewspapersTitleInfoBean();
					forum.setItemid(forumStrs[i]);
					forum.setItemval(forumName);
					
					newsList = getNewsList(form.getCenterId(), mi701List);
					
					columnsNewsBean = new NewspapersColumnsNewsBean();
					columnsNewsBean.setColumns(forum);
					columnsNewsBean.setNewsList(newsList);
					columnsNewsList.add(columnsNewsBean);
				}
			}
		}
		
		result.setTimesList(timesList);
		result.setColumnsNewsList(columnsNewsList);
		return result;
	}
	
	/*
	 * 我的订阅-根据报刊期次，进行信息展示数据获取处理（及订阅版块范围）
	 */
	@SuppressWarnings("unchecked")
	public AppApi70110Result appapi70113(AppApi70110Form form,HttpServletRequest request) throws Exception{
		AppApi70110Result result = new AppApi70110Result();
		form.setChannel(request.getAttribute("MI040Pid").toString());
		
		List<NewspapersColumnsNewsBean> columnsNewsList = new ArrayList<NewspapersColumnsNewsBean>();
		NewspapersColumnsNewsBean columnsNewsBean = new NewspapersColumnsNewsBean();
		NewspapersTitleInfoBean forums = new NewspapersTitleInfoBean();
		List<NewspapersNewsBean> newsList = new ArrayList<NewspapersNewsBean>();

		// 1. 获取订阅版块范围中的版块新闻列表
		String[] forumStrs = form.getNewspapercolumns().split(",");
		
		for (int i = 0; i < forumStrs.length; i++){
			Mi701Example mi701Example = new Mi701Example();
			mi701Example.setOrderByClause("seqno asc");
			mi701Example.createCriteria().andCenteridEqualTo(form.getCenterId())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andFreeuse8Like("%"+form.getChannel()+"%")
			.andClassificationEqualTo(form.getNewspapertimes())
			.andNewspaperforumEqualTo(forumStrs[i]);
			List<Mi701> mi701List = cmi701Dao.selectByExampleWithoutBLOBs(mi701Example);
			
			if (mi701List.size() > 0){
				// 获取到订阅版块的新闻列表，才给应答结果中的版块对象封装值
				String forumName = codeListApi001Service.getCodeVal(form.getCenterId(), Constants.FORUM_CODE+"."+forumStrs[i]);
				
				forums = new NewspapersTitleInfoBean();
				forums.setItemid(forumStrs[i]);
				forums.setItemval(forumName);
				
				newsList = getNewsList(form.getCenterId(), mi701List);
				
				columnsNewsBean = new NewspapersColumnsNewsBean();
				columnsNewsBean.setColumns(forums);
				columnsNewsBean.setNewsList(newsList);
				columnsNewsList.add(columnsNewsBean);
			}
		}
		
		result.setColumnsNewsList(columnsNewsList);
		return result;
	}
	
	/*
	 * 今日新闻初始页面获取处理
	 */
	@SuppressWarnings("unchecked")
	public AppApi70101Result appapi70114(AppApi701CommonForm form,HttpServletRequest request)
			throws Exception {
		form.setChannel(request.getAttribute("MI040Pid").toString());
		// 1. 根据公共参数centerid获取所今日已发布的期次（默认只有一天只发布一个期次），按期次编号降序
		Mi702Example timesExample = new Mi702Example();
		timesExample.setOrderByClause("seqno desc");
		timesExample.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andPublishdateEqualTo(CommonUtil.getSystemDate().substring(0, 10))
		.andPublishflagEqualTo(Constants.PUBLISH_FLG_ONE);
		List<NewspapersTitleInfoBean> timesList = cmi702Dao.selectTimesList(timesExample);
		
		List<NewspapersTitleInfoBean> forumList = new ArrayList<NewspapersTitleInfoBean>();
		List<NewspapersColumnsNewsBean> columnsNewsList = new ArrayList<NewspapersColumnsNewsBean>();
		NewspapersColumnsNewsBean columnsNewsBean = new NewspapersColumnsNewsBean();
		
		if (timesList.size() > 0){
			
			// 2.获取今日发布的期次包含的版块列表
			Mi704Example forumExample = new Mi704Example();
			forumExample.setOrderByClause("abs(itemid) asc");
			forumExample.createCriteria().andCenteridEqualTo(form.getCenterId()).andUpdicidEqualTo(timesList.get(0).getItemid());
			List<Mi704>  mi704List = cmi704Dao.selectByExample(forumExample);
			NewspapersTitleInfoBean forumBean = new NewspapersTitleInfoBean();
			for (int i = 0; i< mi704List.size(); i++){
				forumBean = new NewspapersTitleInfoBean();
				forumBean.setItemid(mi704List.get(i).getItemid());
				forumBean.setItemval(mi704List.get(i).getItemval());
				forumList.add(forumBean);
			}
			if (forumList.size() > 0){
				
				// 4. 根据本期看点标记，进行本期看点栏目的封装
				List<Mi701> newsHighlightResultList = getHighlightNews(form.getCenterId(), timesList.get(0).getItemid(),form.getChannel());
				if (newsHighlightResultList.size() > 0){
					NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
					columnsBean.setItemid("highlight");
					columnsBean.setItemval("本期看点");
					
					List<NewspapersNewsBean> newsNoColumnList = getNewsList(form.getCenterId(), newsHighlightResultList);
					columnsNewsBean = new NewspapersColumnsNewsBean();
					columnsNewsBean.setColumns(columnsBean);
					columnsNewsBean.setNewsList(newsNoColumnList);
					
					columnsNewsList.add(columnsNewsBean);
				}
				
				// 3. 获取新闻信息表中最小版块编号包含的栏目列表，到码表获取各栏目名称，Mi701获取各栏目下的新闻列表
				form.setNewspapertimes(timesList.get(0).getItemid());
				form.setNewspaperforum(forumList.get(0).getItemid());
				List<HashMap<String,String>> columnsList = cmi701Dao.selectColumns(form);
				for (int i = 0; i<columnsList.size(); i++ ){
					String columnsItemId = columnsList.get(i).get("newspapercolumns").toString();
					
					String columnsItemVal = codeListApi001Service.getCodeVal(form.getCenterId(), Constants.COLUMNS_CODE+"."+columnsItemId);
					
					NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
					columnsBean.setItemid(columnsItemId);
					columnsBean.setItemval(columnsItemVal);
					
					//获取有归属栏目的新闻列表
					Mi701Example newsExample = new Mi701Example();
					newsExample.setOrderByClause("seqno asc");
					newsExample.createCriteria().andCenteridEqualTo(form.getCenterId())
					.andValidflagEqualTo(Constants.IS_VALIDFLAG)
					.andFreeuse8Like("%"+form.getChannel()+"%")
					.andClassificationEqualTo(form.getNewspapertimes())
					.andNewspaperforumEqualTo(form.getNewspaperforum())
					.andNewspapercolumnsEqualTo(columnsItemId);
					List<Mi701> newsResultList = cmi701Dao.selectByExampleWithoutBLOBs(newsExample);
					
					List<NewspapersNewsBean> newsList = getNewsList(form.getCenterId(), newsResultList);
					
					columnsNewsBean = new NewspapersColumnsNewsBean();
					columnsNewsBean.setColumns(columnsBean);
					columnsNewsBean.setNewsList(newsList);
					
					columnsNewsList.add(columnsNewsBean);
				}
				
				// 5. 获取新闻信息表中最小版块编号包含的没有栏目归属（栏目=‘’）的新闻列表
				Mi701Example newsExample = new Mi701Example();
				newsExample.setOrderByClause("seqno asc");
				
				Mi701Example.Criteria ca = newsExample.createCriteria();
				ca.andCenteridEqualTo(form.getCenterId())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG)
				.andFreeuse8Like("%"+form.getChannel()+"%")
				.andClassificationEqualTo(form.getNewspapertimes())
				.andNewspaperforumEqualTo(form.getNewspaperforum())
				.andNewspapercolumnsEqualTo("");
				newsExample.or(ca);
				Mi701Example.Criteria ca1 = newsExample.createCriteria();
				ca1.andCenteridEqualTo(form.getCenterId())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG)
				.andFreeuse8Like("%"+form.getChannel()+"%")
				.andClassificationEqualTo(form.getNewspapertimes())
				.andNewspaperforumEqualTo(form.getNewspaperforum())
				.andNewspapercolumnsIsNull();
				newsExample.or(ca1);
				
				List<Mi701> newsNoColumnResultList = cmi701Dao.selectByExampleWithoutBLOBs(newsExample);

				if (newsNoColumnResultList.size() > 0){
					NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
					columnsBean.setItemid("other");
					columnsBean.setItemval("其他");
					
					List<NewspapersNewsBean> newsNoColumnList = getNewsList(form.getCenterId(), newsNoColumnResultList);
					columnsNewsBean = new NewspapersColumnsNewsBean();
					columnsNewsBean.setColumns(columnsBean);
					columnsNewsBean.setNewsList(newsNoColumnList);
					
					columnsNewsList.add(columnsNewsBean);
				}
			}

		}

		AppApi70101Result result = new AppApi70101Result();
		result.setTimesList(timesList);// 把期次列表也返回，等页面版块切换时，再传回来，直接利用之前的接口appapi70103.json,不再新增接口
		result.setForumList(forumList);
		result.setColumnsNewsList(columnsNewsList);
		return result;
	}
	
	/*
	 * 往日新闻，初始页面访问数据获取处理
	 */
	@SuppressWarnings("unchecked")
	public AppApi70101Result appapi70115(AppApi701CommonForm form,HttpServletRequest request)
			throws Exception {
		form.setChannel(request.getAttribute("MI040Pid").toString());
		// 1. 根据公共参数centerid获取所包含的往日的有效的已发布的期次的列表，按期次编号降序
		Mi702Example timesExample = new Mi702Example();
		timesExample.setOrderByClause("seqno desc");
		timesExample.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andPublishdateLessThan(CommonUtil.getSystemDate().substring(0, 10))
		.andPublishflagEqualTo(Constants.PUBLISH_FLG_ONE);
		List<NewspapersTitleInfoBean> timesList = cmi702Dao.selectTimesList(timesExample);
		
		List<NewspapersTitleInfoBean> forumList = new ArrayList<NewspapersTitleInfoBean>();
		List<NewspapersColumnsNewsBean> columnsNewsList = new ArrayList<NewspapersColumnsNewsBean>();
		NewspapersColumnsNewsBean columnsNewsBean = new NewspapersColumnsNewsBean();
		
		if (timesList.size() > 0){
			
			// 2.获取最大期次包含的版块列表
			Mi704Example forumExample = new Mi704Example();
			forumExample.setOrderByClause("abs(itemid) asc");
			forumExample.createCriteria().andCenteridEqualTo(form.getCenterId()).andUpdicidEqualTo(timesList.get(0).getItemid());
			List<Mi704>  mi704List = cmi704Dao.selectByExample(forumExample);
			NewspapersTitleInfoBean forumBean = new NewspapersTitleInfoBean();
			for (int i = 0; i< mi704List.size(); i++){
				forumBean = new NewspapersTitleInfoBean();
				forumBean.setItemid(mi704List.get(i).getItemid());
				forumBean.setItemval(mi704List.get(i).getItemval());
				forumList.add(forumBean);
			}
			if (forumList.size() > 0){
				
				// 4. 根据本期看点标记，进行本期看点栏目的封装
				List<Mi701> newsHighlightResultList = getHighlightNews(form.getCenterId(), timesList.get(0).getItemid(),form.getChannel());
				if (newsHighlightResultList.size() > 0){
					NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
					columnsBean.setItemid("highlight");
					columnsBean.setItemval("本期看点");
					
					List<NewspapersNewsBean> newsNoColumnList = getNewsList(form.getCenterId(), newsHighlightResultList);
					columnsNewsBean = new NewspapersColumnsNewsBean();
					columnsNewsBean.setColumns(columnsBean);
					columnsNewsBean.setNewsList(newsNoColumnList);
					
					columnsNewsList.add(columnsNewsBean);
				}
				
				// 3. 获取新闻信息表中最小版块编号包含的栏目列表，到码表获取各栏目名称，Mi701获取各栏目下的新闻列表
				form.setNewspapertimes(timesList.get(0).getItemid());
				form.setNewspaperforum(forumList.get(0).getItemid());
				List<HashMap<String,String>> columnsList = cmi701Dao.selectColumns(form);
				for (int i = 0; i<columnsList.size(); i++ ){
					String columnsItemId = columnsList.get(i).get("newspapercolumns").toString();
					
					String columnsItemVal = codeListApi001Service.getCodeVal(form.getCenterId(), Constants.COLUMNS_CODE+"."+columnsItemId);
					
					NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
					columnsBean.setItemid(columnsItemId);
					columnsBean.setItemval(columnsItemVal);
					
					//获取有归属栏目的新闻列表
					Mi701Example newsExample = new Mi701Example();
					newsExample.setOrderByClause("seqno asc");
					newsExample.createCriteria().andCenteridEqualTo(form.getCenterId())
					.andValidflagEqualTo(Constants.IS_VALIDFLAG)
					.andFreeuse8Like("%"+form.getChannel()+"%")
					.andClassificationEqualTo(form.getNewspapertimes())
					.andNewspaperforumEqualTo(form.getNewspaperforum())
					.andNewspapercolumnsEqualTo(columnsItemId);
					List<Mi701> newsResultList = cmi701Dao.selectByExampleWithoutBLOBs(newsExample);
					
					List<NewspapersNewsBean> newsList = getNewsList(form.getCenterId(), newsResultList);
					
					columnsNewsBean = new NewspapersColumnsNewsBean();
					columnsNewsBean.setColumns(columnsBean);
					columnsNewsBean.setNewsList(newsList);
					
					columnsNewsList.add(columnsNewsBean);
				}
				
				// 5. 获取新闻信息表中最小版块编号包含的没有栏目归属（栏目=‘’）的新闻列表
				Mi701Example newsExample = new Mi701Example();
				newsExample.setOrderByClause("seqno asc");
				
				Mi701Example.Criteria ca = newsExample.createCriteria();
				ca.andCenteridEqualTo(form.getCenterId())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG)
				.andFreeuse8Like("%"+form.getChannel()+"%")
				.andClassificationEqualTo(form.getNewspapertimes())
				.andNewspaperforumEqualTo(form.getNewspaperforum())
				.andNewspapercolumnsEqualTo("");
				newsExample.or(ca);
				Mi701Example.Criteria ca1 = newsExample.createCriteria();
				ca1.andCenteridEqualTo(form.getCenterId())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG)
				.andFreeuse8Like("%"+form.getChannel()+"%")
				.andClassificationEqualTo(form.getNewspapertimes())
				.andNewspaperforumEqualTo(form.getNewspaperforum())
				.andNewspapercolumnsIsNull();
				newsExample.or(ca1);
				
				List<Mi701> newsNoColumnResultList = cmi701Dao.selectByExampleWithoutBLOBs(newsExample);

				if (newsNoColumnResultList.size() > 0){
					NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
					columnsBean.setItemid("other");
					columnsBean.setItemval("其他");
					
					List<NewspapersNewsBean> newsNoColumnList = getNewsList(form.getCenterId(), newsNoColumnResultList);
					columnsNewsBean = new NewspapersColumnsNewsBean();
					columnsNewsBean.setColumns(columnsBean);
					columnsNewsBean.setNewsList(newsNoColumnList);
					
					columnsNewsList.add(columnsNewsBean);
				}
			}

		}

		AppApi70101Result result = new AppApi70101Result();
		result.setTimesList(timesList);
		result.setForumList(forumList);
		result.setColumnsNewsList(columnsNewsList);
		return result;
	}
	
	/*
	 * 根据查询结果list重新封装要返回的结果bean
	 */
	private List<NewspapersNewsBean> getNewsList(String centerid, List<Mi701> newsResultList){
		List<NewspapersNewsBean> newsList = new ArrayList<NewspapersNewsBean>();
		NewspapersNewsBean newsBean = new NewspapersNewsBean();;
		Mi701 record = new Mi701();
		for(int j = 0; j < newsResultList.size(); j++){
			record = newsResultList.get(j);
			
			// 获取此条新闻的评论数
			Mi703Example mi703Example = new Mi703Example();
			mi703Example.createCriteria().andCenteridEqualTo(centerid)
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andNewsseqnoEqualTo(record.getSeqno());
			int commentCount = cmi703Dao.countByExample(mi703Example);
			
			newsBean = new NewspapersNewsBean();
			newsBean.setTitleId(record.getSeqno().toString());
			newsBean.setTitle(record.getTitle());
			newsBean.setSource(record.getSource());
			newsBean.setIntroduction(record.getIntroduction());
			if (!CommonUtil.isEmpty(record.getImage())){
				String turnImageUrl  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "turnImageUrl"+centerid);
				if(turnImageUrl.equals("1")){
					String pathYuan  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "serverPath"+centerid);
					String path  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appServerPath"+centerid);
					newsBean.setImgUrl(record.getImage().replaceAll(pathYuan, path));
				}else{
					newsBean.setImgUrl(record.getImage());
				}
			}else{
				newsBean.setImgUrl("");
			}
			newsBean.setPraisecounts(record.getPraisecounts());
			newsBean.setCommentcounts(commentCount);
			
			newsList.add(newsBean);
		}
		return newsList;
	}
	
	/*
	 * 获取对应中心ID，对应期次，有效数据中含有本期看点的新闻
	 */
	@SuppressWarnings("unchecked")
	private List<Mi701> getHighlightNews(String centerid, String timesid,String channel){
		List<Mi701> newsResultList = new ArrayList<Mi701>();
		Mi701Example newsExample = new Mi701Example();
		newsExample.setOrderByClause("abs(newspaperforum) asc, seqno asc");
		newsExample.createCriteria().andCenteridEqualTo(centerid)
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andFreeuse8Like("%"+channel+"%")
		.andClassificationEqualTo(timesid)
		.andFreeuse2EqualTo(Constants.ATTENTION_FLG_ONE);
		newsResultList = cmi701Dao.selectByExampleWithoutBLOBs(newsExample);
		return newsResultList;
	}

}
