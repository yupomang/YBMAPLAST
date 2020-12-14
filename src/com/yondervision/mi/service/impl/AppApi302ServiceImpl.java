/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.service.impl
 * 文件名：     AppApi302ServiceImpl.java
 * 创建日期：2013-10-24
 */
package com.yondervision.mi.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.APP_ALERT;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi402DAO;
import com.yondervision.mi.dao.Mi007DAO;
import com.yondervision.mi.dao.Mi122DAO;
import com.yondervision.mi.dao.Mi124DAO;
import com.yondervision.mi.dao.Mi404DAO;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi122;
import com.yondervision.mi.dto.Mi122Example;
import com.yondervision.mi.dto.Mi124;
import com.yondervision.mi.dto.Mi124Example;
import com.yondervision.mi.dto.Mi402;
import com.yondervision.mi.dto.Mi402Example;
import com.yondervision.mi.dto.Mi404;
import com.yondervision.mi.dto.Mi404Example;
import com.yondervision.mi.form.AppApi30201Form;
import com.yondervision.mi.form.AppApi30202Form;
import com.yondervision.mi.form.AppApi30203Form;
import com.yondervision.mi.form.AppApi30205Form;
import com.yondervision.mi.form.AppApi30207Form;
import com.yondervision.mi.result.AppApi30201Result;
import com.yondervision.mi.result.AppApi30202Result;
import com.yondervision.mi.service.AppApi302Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;

/**
 * @author LinXiaolong
 * 
 */
public class AppApi302ServiceImpl implements AppApi302Service {

	private CMi402DAO cMi402Dao;
	private Mi404DAO mi404Dao;
	private Mi122DAO mi122Dao;
	private Mi124DAO mi124Dao;
	private Mi007DAO mi007Dao;

	public Mi124DAO getMi124Dao() {
		return mi124Dao;
	}

	public void setMi124Dao(Mi124DAO mi124Dao) {
		this.mi124Dao = mi124Dao;
	}

	public Mi122DAO getMi122Dao() {
		return mi122Dao;
	}

	public void setMi122Dao(Mi122DAO mi122Dao) {
		this.mi122Dao = mi122Dao;
	}

	public Mi404DAO getMi404Dao() {
		return mi404Dao;
	}

	public void setMi404Dao(Mi404DAO mi404Dao) {
		this.mi404Dao = mi404Dao;
	}

	/**
	 * @return the cMi402Dao
	 */
	public CMi402DAO getcMi402Dao() {
		return cMi402Dao;
	}

	/**
	 * @param cMi402Dao
	 *            the cMi402Dao to set
	 */
	public void setcMi402Dao(CMi402DAO cMi402Dao) {
		this.cMi402Dao = cMi402Dao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervision.mi.service.AppApi302Service#appApi20101(com.yondervision
	 * .mi.form.AppApi30201Form)
	 */
	public List<AppApi30201Result> appApi30201(AppApi30201Form form)
			throws Exception {
		Logger log = LoggerUtil.getLogger();
		
		String path  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "serverPath");
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}
		/*
		 * 20141202 不登录用户可以查询公共信息
		 * */
//		if (CommonUtil.isEmpty(form.getUserId())) {
//			log.error(ERROR.PARAMS_NULL.getLogText("userId"));
//			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
//					.getValue(), "用户名");
//		}
		if (CommonUtil.isEmpty(form.getRowsum())) {
			log.error(ERROR.PARAMS_NULL.getLogText("rowsum"));
			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
					.getValue(), "要取得的总记录数");
		}

		List<AppApi30201Result> result = new ArrayList<AppApi30201Result>();

		/*
		 * 查询推送信息数据
		 */
		Mi402Example mi402Example = new Mi402Example();
		mi402Example.setOrderByClause("PUSH_ORDER_NO desc");
		Mi402Example.Criteria mi402Criteria = mi402Example.createCriteria();
		mi402Criteria.andCenteridEqualTo(form.getCenterId());
		mi402Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		mi402Criteria.andStatusEqualTo(Constants.PUSH_MSG_ARD_PUSH);
		List list = new ArrayList();
		if(!"20".equals(form.getChannel())){//APP
			
			list.add("00");
			if(!CommonUtil.isEmpty(form.getUserId())){//登录用户				
//				Mi402Example.Criteria mi402Criteria1 = mi402Example.createCriteria();
//				mi402Criteria1.andCenteridEqualTo(form.getCenterId());
//				mi402Criteria1.andUseridEqualTo(form.getUserId());
//				if (!CommonUtil.isEmpty(form.getMessageid())) {
//					mi402Criteria1.andPushOrderNoGreaterThan(form.getMessageid());
//				}
//				mi402Criteria1.andValidflagEqualTo(Constants.IS_VALIDFLAG);
//				mi402Criteria1.andStatusEqualTo(Constants.PUSH_MSG_ARD_PUSH);
//				mi402Criteria1.andPusMessageTypeEqualTo("01");
//				mi402Example.or(mi402Criteria1);
				list.add("01");
//				mi402Criteria.andUseridEqualTo(form.getUserId());
			}		
		}else if("20".equals(form.getChannel())){			
						
			if("wx0303099233".equals(form.getUserId())){
//				Mi402Example.Criteria mi402Criteria1 = mi402Example.createCriteria();
//				mi402Criteria1.andCenteridEqualTo(form.getCenterId());
//				mi402Criteria1.andUseridEqualTo(form.getUserId());
//				if (!CommonUtil.isEmpty(form.getMessageid())) {
//					mi402Criteria1.andPushOrderNoGreaterThan(form.getMessageid());
//				}
//				mi402Criteria1.andValidflagEqualTo(Constants.IS_VALIDFLAG);
//				mi402Criteria1.andStatusEqualTo(Constants.PUSH_MSG_ARD_PUSH);
//				mi402Criteria1.andPusMessageTypeEqualTo("02");
//				mi402Example.or(mi402Criteria1);
				list.add("02");
			}else{
				list.add("02");
				//list.add("03");				
				
				Mi402Example.Criteria mi402Criteria1 = mi402Example.createCriteria();
				mi402Criteria1.andCenteridEqualTo(form.getCenterId());
//				mi402Criteria1.andUseridEqualTo(form.getUserId());
//				if (!CommonUtil.isEmpty(form.getMessageid())) {
//					mi402Criteria1.andPushOrderNoGreaterThan(form.getMessageid());
//				}
				mi402Criteria1.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi402Criteria1.andStatusEqualTo(Constants.PUSH_MSG_ARD_PUSH);
				mi402Criteria1.andPusMessageTypeEqualTo("03");
				mi402Example.or(mi402Criteria1);
				
				
				
				
			}

		}
		mi402Criteria.andPusMessageTypeIn(list);
		if (!CommonUtil.isEmpty(form.getMessageid())) {
//			mi402Criteria.andPushOrderNoGreaterThan(form.getMessageid());
		}
		List<Mi402> listMi402 = this.getcMi402Dao().selectByExamplePagination(
				mi402Example, 1, form.getRowsum().intValue()).getRows();
		// 查询结果验证
		if (CommonUtil.isEmpty(listMi402)) {
			log.debug(ERROR.NO_DATA.getLogText("MI402", mi402Criteria
					.getCriteriaWithSingleValue().toString()));
			throw new NoRollRuntimeErrorException(APP_ALERT.NO_DATA.getValue(),
					"推送信息");
		}

		/*
		 * 遍历推送信息数据
		 */
		if(form.getChannel()!=null&&"20".equals(form.getChannel())){
			
			for (Iterator<Mi402> iterator = listMi402.iterator(); iterator
			.hasNext();) {
				Mi402 mi402 = (Mi402) iterator.next();				
				
				if("03".equals(mi402.getTsmsgtype())){
					
					Mi404Example mi404Example = new Mi404Example();
					mi404Example.setOrderByClause("abs(msnum) asc,datecreated desc");
					Mi404Example.Criteria mi404Criteria = mi404Example.createCriteria();
					mi404Criteria.andCenteridEqualTo(form.getCenterId());
					mi404Criteria.andCommsgidEqualTo(mi402.getCommsgid());
					mi404Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
					mi404Criteria.andStatusEqualTo(Constants.PUSH_MSG_ARD_PUSH);
					List<Mi404> list404 = mi404Dao.selectByExampleWithBLOBs(mi404Example);
					for(int i=0;i<list404.size();i++){
						Mi404 mi404 = list404.get(i);
						
						AppApi30201Result rs = new AppApi30201Result();
//						rs.setMessageid(mi402.getPushOrderNo());
						rs.setMessagetitle(mi404.getTitle());
						rs.setMessagebody(mi404.getDetail());
						rs.setMessagetime(CommonUtil.getAppDate(mi404.getDatemodified()));
						rs.setPhone(mi404.getParam1());
						if (!CommonUtil.isEmpty(mi404.getParam2())) {
							List<String> listImgUrl = new ArrayList<String>();
							String[] pImgs = mi404.getParam2().split(",");
							for (int j = 0; j < pImgs.length; j++) {
								String img = pImgs[j];
								String imgUrl = CommonUtil.getDownloadFileUrl(
										"push_msg_img", form.getCenterId() + "/"
												+ img, true);
								listImgUrl.add(imgUrl);
							}
							rs.setImage(listImgUrl);
							
						}
						rs.setPusMessageType(mi404.getPusMessageType());
//						rs.setIsread(mi402.getIsread());
						rs.setTsmsg(mi404.getTsmsg().replaceAll("src=\"/YBMAPZH/", "src=\""+path+"/YBMAPZH/"));
						rs.setTsmsgtype(mi404.getTsmsgtype());
						rs.setMsgid(String.valueOf(mi404.getMsmscommsgid()));
						rs.setTemplateCode(mi402.getFreeuse1());
						result.add(rs);					
						
					}
					
				}else if("02".equals(mi402.getTsmsgtype())){
					
					
					AppApi30201Result rs = new AppApi30201Result();
//					rs.setMessageid(mi402.getPushOrderNo());
					rs.setMessagetitle(mi402.getTitle());
					rs.setMessagebody(mi402.getDetail());
					rs.setMessagetime(CommonUtil.getAppDate(mi402.getDatemodified()));
					rs.setPhone(mi402.getParam1());
					if (!CommonUtil.isEmpty(mi402.getParam2())) {
						List<String> listImgUrl = new ArrayList<String>();
						String[] pImgs = mi402.getParam2().split(",");
						for (int i = 0; i < pImgs.length; i++) {
							String img = pImgs[i];
							String imgUrl = "<img src=\""+path+"/YBMAPZH/"+CommonUtil.getDownloadFileUrl(
									"push_msg_img", form.getCenterId() + "/"
											+ img, true)+"\"/>";//http://10.22.21.107:8080
							listImgUrl.add(imgUrl);
						}
						
						rs.setImage(listImgUrl);
						
					}
					rs.setPusMessageType(mi402.getPusMessageType());
//					rs.setIsread(mi402.getIsread());
					rs.setTsmsg(rs.getImage().get(0));
					rs.setTsmsgtype(mi402.getTsmsgtype());
					rs.setMsgid(String.valueOf(mi402.getMsgid()));
					rs.setTemplateCode(mi402.getFreeuse1());
					result.add(rs);
					
					
					
				}else{
					AppApi30201Result rs = new AppApi30201Result();
//					rs.setMessageid(mi402.getPushOrderNo());
					rs.setMessagetitle(mi402.getTitle());
					rs.setMessagebody(mi402.getDetail());
					rs.setMessagetime(CommonUtil.getAppDate(mi402.getDatemodified()));
					rs.setPhone(mi402.getParam1());
					if (!CommonUtil.isEmpty(mi402.getParam2())) {
						List<String> listImgUrl = new ArrayList<String>();
						String[] pImgs = mi402.getParam2().split(",");
						for (int i = 0; i < pImgs.length; i++) {
							String img = pImgs[i];
							String imgUrl = CommonUtil.getDownloadFileUrl(
									"push_msg_img", form.getCenterId() + "/"
											+ img, true);
							listImgUrl.add(imgUrl);
						}
						rs.setImage(listImgUrl);
						
					}
					rs.setPusMessageType(mi402.getPusMessageType());
//					rs.setIsread(mi402.getIsread());
					rs.setTsmsg(mi402.getTsmsg().replaceAll("src=\"/YBMAPZH/", "src=\""+path+"/YBMAPZH/"));
					rs.setTsmsgtype(mi402.getTsmsgtype());
					rs.setMsgid(String.valueOf(mi402.getMsgid()));
					rs.setTemplateCode(mi402.getFreeuse1());
					result.add(rs);
				}
				
				
			}
			
			
		}else{
			for (Iterator<Mi402> iterator = listMi402.iterator(); iterator
			.hasNext();) {
				Mi402 mi402 = (Mi402) iterator.next();
				AppApi30201Result rs = new AppApi30201Result();
//				rs.setMessageid(mi402.getPushOrderNo());
				rs.setMessagetitle(mi402.getTitle());
				rs.setMessagebody(mi402.getDetail());
				rs.setMessagetime(CommonUtil.getAppDate(mi402.getDatemodified()));
				rs.setPhone(mi402.getParam1());
				if (!CommonUtil.isEmpty(mi402.getParam2())) {
					List<String> listImgUrl = new ArrayList<String>();
					String[] pImgs = mi402.getParam2().split(",");
					for (int i = 0; i < pImgs.length; i++) {
						String img = pImgs[i];
						String imgUrl = CommonUtil.getDownloadFileUrl(
								"push_msg_img", form.getCenterId() + "/"
										+ img, true);
						listImgUrl.add(imgUrl);
					}
					rs.setImage(listImgUrl);
					
				}
				rs.setPusMessageType(mi402.getPusMessageType());
//				rs.setIsread(mi402.getIsread());
				rs.setTsmsg(mi402.getTsmsg());
				rs.setTsmsgtype(mi402.getTsmsgtype());
				rs.setMsgid(String.valueOf(mi402.getMsgid()));
				rs.setTemplateCode(mi402.getFreeuse1());
				result.add(rs);
			}
		}		

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.yondervision.mi.service.AppApi302Service#appApi30202(com.yondervision.mi.form.AppApi30202Form)
	 */
	public void appApi30202(AppApi30202Form form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form.getMessageid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("messageid"));
			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
					.getValue(), "短信息ID");
		}
		
		/*
		 * 将信息状态更新为已读
		 */
		Mi402 mi402 = new Mi402();
//		mi402.setIsread(Constants.PUSH_MSG_IS_READ);
		Mi402Example mi402Example = new Mi402Example();
//		mi402Example.createCriteria().andPushOrderNoEqualTo(
//		Long.parseLong(form.getMessageid()));
		this.getcMi402Dao().updateByExampleSelective(mi402, mi402Example);
	}

	public AppApi30201Result appApi30203(AppApi30203Form form) throws Exception {
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
					.getValue(), "用户名");
		}
		/*
		 * 查询推送信息数据
		 */
		Mi402Example mi402Example = new Mi402Example();
		mi402Example.setOrderByClause("PUSH_ORDER_NO desc");
		Mi402Example.Criteria mi402Criteria = mi402Example.createCriteria();
		mi402Criteria.andCenteridEqualTo(form.getCenterId());
		if(form.getChannel()==null||!"20".equals(form.getChannel())){
//			mi402Criteria.andUseridEqualTo(form.getUserId());
		}		
		mi402Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		mi402Criteria.andStatusEqualTo(Constants.PUSH_MSG_ARD_PUSH);
		if(form.getChannel()!=null&&"20".equals(form.getChannel())){
			mi402Criteria.andPusMessageTypeEqualTo("02");
		}
		if (!CommonUtil.isEmpty(form.getMessageid())) {
//			mi402Criteria.andPushOrderNoGreaterThan(form.getMessageid());
		}
//		mi402Criteria.andMsgidEqualTo(Long.valueOf(form.getMsgid()));
//		Mi402 mi402 = cMi402Dao.selectByPrimaryKey(Long.valueOf(form.getMsgid()));
		// 查询结果验证
//		if (CommonUtil.isEmpty(mi402)) {
//			log.debug(ERROR.NO_DATA.getLogText("MI402", mi402Criteria
//					.getCriteriaWithSingleValue().toString()));
//			throw new NoRollRuntimeErrorException(APP_ALERT.NO_DATA.getValue(),
//					"推送信息");
//		}
		AppApi30201Result rs = new AppApi30201Result();
		/*
		 * 遍历推送信息数据
		 */
//		for (Iterator<Mi402> iterator = listMi402.iterator(); iterator
//				.hasNext();) {
//			Mi402 mi402 = (Mi402) iterator.next();
			
//			rs.setMessageid(mi402.getPushOrderNo());
//			rs.setMessagetitle(mi402.getTitle());
//			rs.setMessagebody(mi402.getDetail());
//			rs.setMessagetime(CommonUtil.getAppDate(mi402.getDatemodified()));
//			rs.setPhone(mi402.getParam1());
//			if (!CommonUtil.isEmpty(mi402.getParam2())) {
//				List<String> listImgUrl = new ArrayList<String>();
//				String[] pImgs = mi402.getParam2().split(",");
//				for (int i = 0; i < pImgs.length; i++) {
//					String img = pImgs[i];
//					String imgUrl = CommonUtil.getDownloadFileUrl(
//							"push_msg_img", form.getCenterId() + "/"
//									+ img, true);
//					listImgUrl.add(imgUrl);
//				}
//				rs.setImage(listImgUrl);
//				
//			}
//			rs.setPusMessageType(mi402.getPusMessageType());
//			rs.setIsread(mi402.getIsread());
//			rs.setTsmsg(mi402.getTsmsg());
//			rs.setTsmsgtype(mi402.getTsmsgtype());
//			rs.setMsgid(String.valueOf(mi402.getMsgid()));
//			break;
//		}
		return rs;
		
	}

	public List<AppApi30202Result> appApi30204(AppApi30202Form form,List<Mi007> list) throws Exception {
		Mi122Example mi122Example = new Mi122Example();
		mi122Example.setOrderByClause("num asc");
		Mi122Example.Criteria mi122Criteria = mi122Example.createCriteria();
		mi122Criteria.andCenteridEqualTo(form.getCenterId());
		mi122Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi122> list122 = mi122Dao.selectByExample(mi122Example);
		if(list122.size()==0){
			throw new NoRollRuntimeErrorException(APP_ALERT.NO_DATA.getValue(),
			"主题信息");
		}
		Mi124Example mi124Example = new Mi124Example();
		Mi124Example.Criteria mi124Criteria = mi124Example.createCriteria();
		mi124Criteria.andCenteridEqualTo(form.getCenterId());
		mi124Criteria.andUseridEqualTo(form.getUserId());
		List<Mi124> list124 = mi124Dao.selectByExample(mi124Example);
		List<AppApi30202Result> listAppApi30202Result = new ArrayList<AppApi30202Result>();
		if(list124.size()>0){
			Mi124 mi124 = list124.get(0);	
			if("1".equals(mi124.getFirstzt())){
				for(int i=0;i<list122.size();i++){
					AppApi30202Result appapi30202result = new AppApi30202Result();
					if(CommonUtil.isEmpty(mi124.getTopictype())){
						appapi30202result.setDisable("0");
					}else{
						appapi30202result.setDisable((mi124.getTopictype()).indexOf((list122.get(i).getMessageTopicType()))!=-1?"1":"0");
					}
					appapi30202result.setTopictypeid(list122.get(i).getMessageTopicType());
					for(int j=0;j<list.size();j++){
						if(list.get(j).getItemid().equals(list122.get(i).getMessageTopicType())){
							appapi30202result.setTopictypename(list.get(j).getItemval());
							break;
						}
					}					
					listAppApi30202Result.add(appapi30202result);
				}
			}else{
				for(int i=0;i<list122.size();i++){
					AppApi30202Result appapi30202result = new AppApi30202Result();
					appapi30202result.setDisable("1");
					appapi30202result.setTopictypeid(list122.get(i).getMessageTopicType());
					for(int j=0;j<list.size();j++){
						if(list.get(j).getItemid().equals(list122.get(i).getMessageTopicType())){
							appapi30202result.setTopictypename(list.get(j).getItemval());
							break;
						}
					}
					listAppApi30202Result.add(appapi30202result);
				}
			}
			
		}else{			
			for(int i=0;i<list122.size();i++){
				AppApi30202Result appapi30202result = new AppApi30202Result();
				appapi30202result.setDisable("1");
				appapi30202result.setTopictypeid(list122.get(i).getMessageTopicType());
				for(int j=0;j<list.size();j++){
					if(list.get(j).getItemid().equals(list122.get(i).getMessageTopicType())){
						appapi30202result.setTopictypename(list.get(j).getItemval());
						break;
					}
				}
				listAppApi30202Result.add(appapi30202result);
			}			
		}
		return listAppApi30202Result;
	}

	public void appApi30205(AppApi30205Form form) throws Exception {
		Mi124Example mi124Example = new Mi124Example();
		Mi124Example.Criteria mi124Criteria = mi124Example.createCriteria();
//		mi124Criteria.andCenteridEqualTo(form.getCenterId());
		mi124Criteria.andUseridEqualTo(form.getUserId());
		mi124Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi124> list124 = mi124Dao.selectByExample(mi124Example);
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi124 mi124 = new Mi124();
		if(list124.size()==0){
			mi124.setUserid(form.getUserId());
			mi124.setCenterid(form.getCenterId());
			mi124.setChannel(form.getChannel());
			mi124.setFirsttime("0");
			mi124.setFirstzt("1");
			mi124.setTopictype(form.getTopictypeids());			
			
			String starttime  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "nodisturbstarttime"+form.getCenterId());
			String endtime = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "nodisturbendtime"+form.getCenterId());
			mi124.setNodisturb("0");
			mi124.setStarttime(starttime);
			mi124.setEndtime(endtime);			
			
			mi124.setValidflag(Constants.IS_VALIDFLAG);
			mi124.setDatecreated(formatter.format(date));
			mi124.setDatemodified(formatter.format(date));
			mi124Dao.insert(mi124);
		}else{
			Mi124 mi124Date = new Mi124(); 
			mi124Date.setUserid(list124.get(0).getUserid());
			if("0".equals(list124.get(0).getFirstzt())){
				mi124Date.setFirstzt("1");
			}
			mi124Date.setTopictype(form.getTopictypeids());
			mi124Date.setDatemodified(formatter.format(date));
			mi124Dao.updateByPrimaryKeySelective(mi124Date);
		}		
	}
	
	public Mi124 appApi30206(AppApi30207Form form) throws Exception {
		Mi124Example mi124Example = new Mi124Example();
		Mi124Example.Criteria mi124Criteria = mi124Example.createCriteria();
//		mi124Criteria.andCenteridEqualTo(form.getCenterId());
		mi124Criteria.andUseridEqualTo(form.getUserId());
		mi124Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi124> list124 = mi124Dao.selectByExample(mi124Example);
		Mi124 mi124 = new Mi124();
		if(list124.size()==0){
			String starttime  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "nodisturbstarttime"+form.getCenterId());
			String endtime = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "nodisturbendtime"+form.getCenterId());
			mi124.setNodisturb("0");
			mi124.setStarttime(starttime);
			mi124.setEndtime(endtime);
		}else{
			mi124.setNodisturb(list124.get(0).getNodisturb());
			mi124.setStarttime(list124.get(0).getStarttime());
			mi124.setEndtime(list124.get(0).getEndtime());
		}
		return mi124;
	}
	
	public void appApi30207(AppApi30207Form form) throws Exception {
		Mi124Example mi124Example = new Mi124Example();
		Mi124Example.Criteria mi124Criteria = mi124Example.createCriteria();
//		mi124Criteria.andCenteridEqualTo(form.getCenterId());
		mi124Criteria.andUseridEqualTo(form.getUserId());
		mi124Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi124> list124 = mi124Dao.selectByExample(mi124Example);
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi124 mi124 = new Mi124();
		if(list124.size()==0){
			mi124.setUserid(form.getUserId());
			mi124.setCenterid(form.getCenterId());
			mi124.setChannel(form.getChannel());
			mi124.setFirsttime("1");
			mi124.setFirstzt("0");
			mi124.setNodisturb(form.getNodisturb());
			if("1".equals(form.getNodisturb())){
				mi124.setStarttime(form.getStarttime());
				mi124.setEndtime(form.getEndtime());
			}else{
				String starttime  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "nodisturbstarttime"+form.getCenterId());
				String endtime = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "nodisturbendtime"+form.getCenterId());
				mi124.setStarttime(starttime);
				mi124.setEndtime(endtime);
			}
			mi124.setTopictype("");
			mi124.setCtimeseqid("");
			mi124.setValidflag(Constants.IS_VALIDFLAG);
			mi124.setDatecreated(formatter.format(date));
			mi124.setDatemodified(formatter.format(date));
			mi124Dao.insert(mi124);
		}else{
			mi124.setUserid(list124.get(0).getUserid());
			if("0".equals(list124.get(0).getFirsttime())){
				mi124.setFirsttime("1");
			}
			mi124.setNodisturb(form.getNodisturb());
			if("1".equals(form.getNodisturb())){
				mi124.setStarttime(form.getStarttime());
				mi124.setEndtime(form.getEndtime());
			}
			mi124.setDatemodified(formatter.format(date));
			mi124Dao.updateByPrimaryKeySelective(mi124);
		}
	}

	

}
