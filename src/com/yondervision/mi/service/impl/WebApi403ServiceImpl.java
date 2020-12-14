package com.yondervision.mi.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.dao.CMi106DAO;
import com.yondervision.mi.dao.Mi106DAO;
import com.yondervision.mi.dto.CMi106;
import com.yondervision.mi.dto.Mi106;
import com.yondervision.mi.dto.Mi106Example;
import com.yondervision.mi.dto.Mi201;
import com.yondervision.mi.result.WebApi40304_queryResult;
import com.yondervision.mi.service.WebApi403Service;
import com.yondervision.mi.service.WebApiUploadFile;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi403ServiceImpl 
* @Description: TODO
* @author Caozhongyan
* @date Oct 9, 2013 9:13:18 AM   
* 
*/ 
public class WebApi403ServiceImpl implements WebApi403Service {
	
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	
	private WebApiUploadFile webApiUploadFile=null;
	
	Mi106DAO mi106Dao = null;
	
	CMi106DAO cmi106Dao = null;
	
	public WebApiUploadFile getWebApiUploadFile() {
		return webApiUploadFile;
	}

	public void setWebApiUploadFile(WebApiUploadFile webApiUploadFile) {
		this.webApiUploadFile = webApiUploadFile;
	}
	
	public CMi106DAO getCmi106Dao() {
		return cmi106Dao;
	}

	public void setCmi106Dao(CMi106DAO cmi106Dao) {
		this.cmi106Dao = cmi106Dao;
	}

	public Mi106DAO getMi106Dao() {
		return mi106Dao;
	}

	public void setMi106Dao(Mi106DAO mi106Dao) {
		this.mi106Dao = mi106Dao;
	}

	public void webapi40301(CMi106 form) throws Exception{
		// TODO Auto-generated method stub
		
		Mi106Example m106e=new Mi106Example();
		com.yondervision.mi.dto.Mi106Example.Criteria ca= m106e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterid());
		ca.andDevtypeEqualTo(form.getDevtype());
		ca.andVersionnoEqualTo(form.getVersionno());
		List<Mi106> mi106List = mi106Dao.selectByExample(m106e);
		if(mi106List.size()>0){
			throw new TransRuntimeErrorException(WEB_ALERT.DATA_CHECK_INSERT.getValue(),"中心该款手机型号版本己存在");
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi106 mi106 = new Mi106();
		mi106.setCenterid(form.getCenterid());
		mi106.setDatecreated(formatter.format(date));		
		mi106.setDatemodified(formatter.format(date));
		mi106.setDevtype(form.getDevtype());
		mi106.setDownloadurl(form.getDownloadurl());
		mi106.setFreeuse1(form.getFreeuse1());
		mi106.setFreeuse2(form.getFreeuse2());
		mi106.setFreeuse3(form.getFreeuse3());
		mi106.setFreeuse4(form.getFreeuse4());
		mi106.setReleasecontent(form.getReleasecontent());
		mi106.setReleasedate(form.getReleasedate());
		mi106.setUsableflag(form.getUsableflag());
		mi106.setValidflag(Constants.IS_VALIDFLAG);
		mi106.setVersionno(form.getVersionno());
		mi106.setVersionid(commonUtil.genKey("MI106", 20));
		mi106.setLoginid(form.getUserid());
		mi106.setPublishflag("0");
		mi106Dao.insert(mi106);
	}

	public void webapi40302(CMi106 form) throws Exception{
		// TODO Auto-generated method stub
		String[] deletes=form.getListVersionId().split(",");
		for(int i=0;i<deletes.length;i++){
			SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
			Date date = new Date();
			Mi106 mi106 = new Mi106();
			mi106.setVersionid(deletes[i]);
			mi106.setDatemodified(formatter.format(date));
			mi106.setValidflag(Constants.IS_NOT_VALIDFLAG);
			mi106Dao.updateByPrimaryKeySelective(mi106);
		}		
	}

	public WebApi40304_queryResult webapi40304(CMi106 form) throws Exception{
		// TODO Auto-generated method stub
		//变更为分页查询
//		Mi106Example m106e=new Mi106Example();
//		com.yondervision.mi.dto.Mi106Example.Criteria ca= m106e.createCriteria();
//		if(!(form.getCenterid().isEmpty()||"".equals(form.getCenterid()))){
//			ca.andCenteridEqualTo(form.getCenterid());
//		}		
//		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
//		m106e.setOrderByClause("devtype asc,versionno desc");
//		List<Mi106> list=mi106Dao.selectByExample(m106e);
		//变更为分页查询
		
		return cmi106Dao.select106Page(form);
	}	

	public int webapi40303(CMi106 form) throws Exception{
		// TODO Auto-generated method stub
		
		Mi106Example m106e=new Mi106Example();
		com.yondervision.mi.dto.Mi106Example.Criteria ca= m106e.createCriteria();		
		if(form.getFbxg().equals("")){
			ca.andCenteridEqualTo(form.getCenterid());
			ca.andDevtypeEqualTo(form.getDevtype());
			ca.andVersionnoEqualTo(form.getVersionno());
			ca.andVersionidNotEqualTo(form.getVersionid());
			List<Mi106> mi106List = mi106Dao.selectByExample(m106e);
			if(mi106List.size()>0){
				throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(),"中心该款手机型号版本己存在");
			}
		}
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi106 mi106 = new Mi106();
		mi106.setCenterid(form.getCenterid());
		mi106.setDatemodified(formatter.format(date));
		mi106.setDevtype(form.getDevtype());
		mi106.setDownloadurl(form.getDownloadurl());
		mi106.setFreeuse1(form.getFreeuse1());
		mi106.setFreeuse2(form.getFreeuse2());
		mi106.setFreeuse3(form.getFreeuse3());
		mi106.setFreeuse4(form.getFreeuse4());
		mi106.setReleasecontent(form.getReleasecontent());
		mi106.setReleasedate(form.getReleasedate());
		mi106.setUsableflag(form.getUsableflag());
		mi106.setVersionno(form.getVersionno());
		mi106.setVersionid(form.getVersionid());
		return mi106Dao.updateByPrimaryKeySelective(mi106);
	}

	public int webapi40305(CMi106 form) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi106 mi106 = new Mi106();		
		mi106.setDatemodified(formatter.format(date));
		mi106.setPublishdate(formatter.format(date));
		mi106.setPublisher(form.getUserid());
		mi106.setPublishflag("1");	
		mi106.setVersionid(form.getVersionid());
		return mi106Dao.updateByPrimaryKeySelective(mi106);
	}

	public String webapi40306(String magecenterId, String versionid_pic,
			MultipartFile file) throws Exception {
		if (CommonUtil.isEmpty(magecenterId)) {
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}
		String filePath = CommonUtil.getFileFullPath("push_twodimensional", magecenterId, true);
		String fileName = ""; 
		try{
			fileName = webApiUploadFile.uploadFile(filePath, file, 1024);
		}catch (NoRollRuntimeErrorException e){
			throw e;
		}		
		return fileName;
	}

	public int webapi40307(CMi106 form) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi106 mi106 = new Mi106();		
		mi106.setDatemodified(formatter.format(date));		
		mi106.setVersionid(form.getVersionid());
		mi106.setFreeuse1(form.getFreeuse1());
		return mi106Dao.updateByPrimaryKeySelective(mi106);
	}

}
