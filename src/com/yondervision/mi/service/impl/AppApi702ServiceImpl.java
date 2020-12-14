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
import com.yondervision.mi.dao.CMi703DAO;
import com.yondervision.mi.dao.Mi705DAO;
import com.yondervision.mi.dto.Mi701;
import com.yondervision.mi.dto.Mi701Example;
import com.yondervision.mi.dto.Mi701WithBLOBs;
import com.yondervision.mi.dto.Mi703;
import com.yondervision.mi.dto.Mi703Example;
import com.yondervision.mi.dto.Mi705;
import com.yondervision.mi.dto.Mi705Example;
import com.yondervision.mi.form.AppApi70203Form;
import com.yondervision.mi.form.AppApi70204Form;
import com.yondervision.mi.form.AppApi70205Form;
import com.yondervision.mi.form.AppApi70206Form;
import com.yondervision.mi.form.AppApi70208Form;
import com.yondervision.mi.form.AppApi702CommonForm;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.AppApi70201Result;
import com.yondervision.mi.result.AppApi70202Result;
import com.yondervision.mi.result.AppApi70203Result;
import com.yondervision.mi.result.AppApi70207Result;
import com.yondervision.mi.result.AppApi70208Result;
import com.yondervision.mi.result.NewspapersColumnsNewsBean;
import com.yondervision.mi.result.NewspapersNewsBean;
import com.yondervision.mi.result.NewspapersNewsCommentBean;
import com.yondervision.mi.result.NewspapersNewsDetailBean;
import com.yondervision.mi.result.NewspapersTitleInfoBean;
import com.yondervision.mi.service.AppApi702Service;
import com.yondervision.mi.service.CodeListApi001Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;

/** 
* @ClassName: AppApi702ServiceImpl
* @Description: 公积金报-移动客户端访问处理-无期次
* @author gongqi
* @date July 18, 2014 9:33:25 PM  
* 
*/ 
public class AppApi702ServiceImpl implements AppApi702Service {

	private CMi701DAO cmi701Dao = null;
	public void setCmi701Dao(CMi701DAO cmi701Dao) {
		this.cmi701Dao = cmi701Dao;
	}
	
	private CMi703DAO cmi703Dao = null;
	public void setCmi703Dao(CMi703DAO cmi703Dao) {
		this.cmi703Dao = cmi703Dao;
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
	@SuppressWarnings({ "unchecked"})
	public AppApi70201Result appapi70201(AppApi702CommonForm form,HttpServletRequest request)
			throws Exception {
		
		List<NewspapersTitleInfoBean> forumList = new ArrayList<NewspapersTitleInfoBean>();
		List<NewspapersColumnsNewsBean> columnsNewsList = new ArrayList<NewspapersColumnsNewsBean>();
		NewspapersColumnsNewsBean columnsNewsBean = new NewspapersColumnsNewsBean();
		form.setChannel(request.getAttribute("MI040Pid").toString());
		
		//1. 根据公共参数centerid获取当前新闻中实际已发布过新闻的版块的列表(按编号从小到大升序)
		forumList = codeListApi001Service.getClassificFromMi707AndMi701("0", form.getCenterId(),request.getAttribute("MI040Pid").toString());
		//2. 获取对应的最小版块编号包含的栏目的列表，到码表获取各栏目名称，Mi701获取各栏目下的新闻列表
		if (forumList.size() > 0){
			System.out.println("获取到的默认的首先显示的版块编号为："+forumList.get(0).getItemid()+",名称为："+forumList.get(0).getItemval());
			
			//3. 根据本期看点标记，进行本期看点栏目的封装
			List<Mi701> newsHighlightResultList = getHighlightNewsNoTimes(form.getCenterId(),form.getChannel());
			if (newsHighlightResultList.size() > 0){
				NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
				columnsBean.setItemid("highlight");
				columnsBean.setItemval("新闻看点");
				
				List<NewspapersNewsBean> newsNoColumnList = getNewsList(form.getCenterId(), newsHighlightResultList);
				columnsNewsBean = new NewspapersColumnsNewsBean();
				columnsNewsBean.setColumns(columnsBean);
				columnsNewsBean.setNewsList(newsNoColumnList);
				
				columnsNewsList.add(columnsNewsBean);
			}
			
			form.setNewspaperforum(forumList.get(0).getItemid());
			//版块下信息过期日期
			String forumInfoInvalidDate = "";
			forumInfoInvalidDate = forumList.get(0).getInValidDate();
			List<NewspapersTitleInfoBean> columnsList = codeListApi001Service.getClassificFromMi707AndMi701(
					forumList.get(0).getItemid(), form.getCenterId(),request.getAttribute("MI040Pid").toString());
			for (int i = 0; i<columnsList.size(); i++ ){
				String columnsItemId = columnsList.get(i).getItemid();
				String columnsItemVal = columnsList.get(i).getItemval();
				String colInfoInvalidDate = columnsList.get(i).getInValidDate();
				
				System.out.println("获取到的栏目编号："+columnsItemId);
				System.out.println("获取到的栏目名称为："+columnsItemVal);
				System.out.println("获取到的栏目下信息过期时间为："+colInfoInvalidDate);
				NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
				columnsBean.setItemid(columnsItemId);
				columnsBean.setItemval(columnsItemVal);
				columnsBean.setInValidDate(colInfoInvalidDate);
				
				//获取有归属栏目的新闻列表
				Mi701Example newsExample = new Mi701Example();
				newsExample.setOrderByClause("seqno desc");
				newsExample.createCriteria().andCenteridEqualTo(form.getCenterId())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG)
				.andFreeuse8Like("%"+form.getChannel()+"%")
				.andClassificationEqualTo(columnsItemId)
				//.andNewspaperforumEqualTo(form.getNewspaperforum())
				.andDatecreatedGreaterThanOrEqualTo(colInfoInvalidDate);
				List<Mi701> newsResultList = cmi701Dao.selectByExampleWithoutBLOBs(newsExample);
				
				List<NewspapersNewsBean> newsList = getNewsList(form.getCenterId(), newsResultList);
				
				columnsNewsBean = new NewspapersColumnsNewsBean();
				columnsNewsBean.setColumns(columnsBean);
				columnsNewsBean.setNewsList(newsList);
				
				columnsNewsList.add(columnsNewsBean);
			}
			
			//4. 获取新闻信息表中最小版块编号包含的没有栏目归属（栏目=‘’）的新闻列表
			Mi701Example newsExample = new Mi701Example();
			newsExample.setOrderByClause("seqno desc");
			
			Mi701Example.Criteria ca = newsExample.createCriteria();
			ca.andCenteridEqualTo(form.getCenterId())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andFreeuse8Like("%"+form.getChannel()+"%")
			.andClassificationEqualTo(form.getNewspaperforum())
			.andDatecreatedGreaterThanOrEqualTo(forumInfoInvalidDate);
			
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
		
		AppApi70201Result result = new AppApi70201Result();
		result.setForumList(forumList);
		result.setColumnsNewsList(columnsNewsList);
		return result;
	}
	
	/*
	 * 根据版块，进行信息展示数据获取处理
	 */
	@SuppressWarnings({ "unchecked"})
	public AppApi70202Result appapi70202(AppApi702CommonForm form,HttpServletRequest request) throws Exception {

		List<NewspapersColumnsNewsBean> columnsNewsList = new ArrayList<NewspapersColumnsNewsBean>();
		NewspapersColumnsNewsBean columnsNewsBean = new NewspapersColumnsNewsBean();
		form.setChannel(request.getAttribute("MI040Pid").toString());
		// 获取对应版块下的信息的过期日期
		String validateDate = codeListApi001Service.getClassificationInvalidDate(form.getNewspaperforum(), form.getCenterId());
		
		// 根据公共参数centerid获取当前新闻中实际已发布过新闻的版块的列表(按编号从小到大升序)
		List<NewspapersTitleInfoBean> forumList = codeListApi001Service.getClassificFromMi707AndMi701("0", form.getCenterId(),request.getAttribute("MI040Pid").toString());
		//2. 判定当前待查询版块是否是头版，是则根据本期看点标记，进行本期看点栏目的封装
		if(forumList.get(0).getItemid().equals(form.getIsFirstForum())){

			List<Mi701> newsResultList = getHighlightNewsNoTimes(form.getCenterId(),form.getChannel());
			if (newsResultList.size() > 0){
				NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
				columnsBean.setItemid("highlight");
				columnsBean.setItemval("新闻看点");
				
				List<NewspapersNewsBean> newsNoColumnList = getNewsList(form.getCenterId(), newsResultList);
				columnsNewsBean = new NewspapersColumnsNewsBean();
				columnsNewsBean.setColumns(columnsBean);
				columnsNewsBean.setNewsList(newsNoColumnList);
				
				columnsNewsList.add(columnsNewsBean);
			}
		}
		
		// 1. 获取新闻信息表中传入的参数包含的栏目列表，到码表Mi007获取各栏目名称，Mi701获取各栏目下的新闻列表
		List<NewspapersTitleInfoBean> columnsList = codeListApi001Service.getClassificFromMi707AndMi701(
				form.getNewspaperforum(), form.getCenterId(),request.getAttribute("MI040Pid").toString());
		for (int i = 0; i<columnsList.size(); i++ ){
			String columnsItemId = columnsList.get(i).getItemid();
			String columnsItemVal = columnsList.get(i).getItemval();
			String colInfoInvalidDate = columnsList.get(i).getInValidDate();
			
			NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
			columnsBean.setItemid(columnsItemId);
			columnsBean.setItemval(columnsItemVal);
			columnsBean.setInValidDate(colInfoInvalidDate);
			
			//获取有归属栏目的新闻列表
			Mi701Example newsExample = new Mi701Example();
			newsExample.setOrderByClause("seqno desc");
			newsExample.createCriteria().andCenteridEqualTo(form.getCenterId())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andFreeuse8Like("%"+form.getChannel()+"%")
			.andClassificationEqualTo(columnsItemId)
			.andNewspaperforumEqualTo(form.getNewspaperforum())
			.andDatecreatedGreaterThanOrEqualTo(colInfoInvalidDate);
			List<Mi701> newsResultList = cmi701Dao.selectByExampleWithoutBLOBs(newsExample);
			
			List<NewspapersNewsBean> newsList = getNewsList(form.getCenterId(), newsResultList);
			columnsNewsBean = new NewspapersColumnsNewsBean();
			columnsNewsBean.setColumns(columnsBean);
			columnsNewsBean.setNewsList(newsList);
			columnsNewsList.add(columnsNewsBean);
		}
		
		//3. 获取新闻信息表中最小版块编号包含的没有栏目归属（栏目=‘’）的新闻列表
		Mi701Example newsExample = new Mi701Example();
		newsExample.setOrderByClause("seqno desc");
		
		Mi701Example.Criteria ca = newsExample.createCriteria();
		ca.andCenteridEqualTo(form.getCenterId())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andFreeuse8Like("%"+form.getChannel()+"%")
		.andClassificationEqualTo(form.getNewspaperforum())
		.andDatecreatedGreaterThanOrEqualTo(validateDate);
		
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
		
		AppApi70202Result result = new AppApi70202Result();
		result.setColumnsNewsList(columnsNewsList);
		return result;
	}
	
	/*
	 * 根据新闻ID,进行新闻详细页面的展示数据的获取
	 */
	@SuppressWarnings("unchecked")
	public AppApi70203Result appapi70203(AppApi70203Form form) throws Exception {

		AppApi70203Result result = new AppApi70203Result();
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
	public void appapi70204(AppApi70204Form form) throws Exception {
		
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
	public void appapi70205(AppApi70205Form form) throws Exception {
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
	public void appapi70206(AppApi70206Form form) throws Exception {
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
	 * 订阅设置-获取待订阅的栏目范围(Mi707中父级不为0，开放状态、有效的 且在mi701中有记录的)
	 */
	public List<AppApi70207Result> appapi70207(AppApiCommonForm form,HttpServletRequest request) throws Exception{
		List<AppApi70207Result> resultList = new ArrayList<AppApi70207Result>();
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("centerId", form.getCenterId());
		
		AppApi70207Result result = new AppApi70207Result();
		
		List<NewspapersTitleInfoBean> resultListTmp = codeListApi001Service.getClassificNotEqualValFromMi707AndMi701("0", form.getCenterId(),request.getAttribute("MI040Pid").toString());
		NewspapersTitleInfoBean bean = null;
		String itemid = null;
		String itemval = null;
		for(int i = 0;i < resultListTmp.size(); i ++){
			result = new AppApi70207Result();
			bean = resultListTmp.get(i);
			itemid = bean.getItemid();
			itemval = bean.getItemval();
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
	@SuppressWarnings({ "unchecked"})
	public AppApi70208Result appapi70208(AppApi70208Form form,HttpServletRequest request) throws Exception{
		AppApi70208Result result = new AppApi70208Result();
		
		List<NewspapersColumnsNewsBean> columnsNewsList = new ArrayList<NewspapersColumnsNewsBean>();
		NewspapersColumnsNewsBean columnsNewsBean = new NewspapersColumnsNewsBean();
		NewspapersTitleInfoBean columns = new NewspapersTitleInfoBean();
		List<NewspapersNewsBean> newsList = new ArrayList<NewspapersNewsBean>();
		form.setChannel(request.getAttribute("MI040Pid").toString());

		// 获取对应版块下的信息的过期日期
		String validateDate = codeListApi001Service.getClassificationInvalidDate(form.getNewspapercolumns(), form.getCenterId());
		
		String[] columnsStrs = form.getNewspapercolumns().split(",");
		//包含的订阅栏目范围中的栏目新闻列表
		for (int i = 0; i < columnsStrs.length; i++){
			Mi701Example mi701Example = new Mi701Example();
			mi701Example.setOrderByClause("seqno desc");
			mi701Example.createCriteria().andCenteridEqualTo(form.getCenterId())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andFreeuse8Like("%"+form.getChannel()+"%")
			.andClassificationEqualTo(columnsStrs[i])
			.andDatecreatedGreaterThanOrEqualTo(validateDate);
			List<Mi701> mi701List = cmi701Dao.selectByExampleWithoutBLOBs(mi701Example);
			
			if (mi701List.size() > 0){
				// 获取到订阅栏目的新闻列表，才给应答结果中的栏目对象封装值
				//String columnName = codeListApi001Service.getCodeValWithDicid(form.getCenterId(), Integer.parseInt(columnsStrs[i]));
				String columnName = codeListApi001Service.getCodeValWithDicidFromMi707(form.getCenterId(), Integer.parseInt(columnsStrs[i]));
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
	 * 获取对应中心ID，有效数据中含有本期看点的新闻
	 * TODO（去掉期次后，看点的过期日期要根据实际的需求特殊对待，暂不进行过期时间的过滤）
	 */
	@SuppressWarnings({ "unchecked"})
	private List<Mi701> getHighlightNewsNoTimes(String centerid,String channel){
		List<Mi701> newsResultList = new ArrayList<Mi701>();
		//String validateDate = commonUtil.getFifteendayDate();
		Mi701Example newsExample = new Mi701Example();
		newsExample.setOrderByClause("seqno desc");
		newsExample.createCriteria().andCenteridEqualTo(centerid)
		.andFreeuse8Like("%"+channel+"%")
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andFreeuse2EqualTo(Constants.ATTENTION_FLG_ONE);
		//.andDatecreatedGreaterThanOrEqualTo(infoInvalidDate);
		newsResultList = cmi701Dao.selectByExampleWithoutBLOBs(newsExample);
		return newsResultList;
	}

}
