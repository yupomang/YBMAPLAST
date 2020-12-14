package com.yondervision.mi.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi411DAO;
import com.yondervision.mi.dao.Mi412DAO;
import com.yondervision.mi.dto.CMi411;
import com.yondervision.mi.dto.Mi411;
import com.yondervision.mi.dto.Mi411Example;
import com.yondervision.mi.dto.Mi412;
import com.yondervision.mi.dto.Mi412Example;
import com.yondervision.mi.result.WebApi41104Query_queryResult;
import com.yondervision.mi.service.WebApi412Service;
import com.yondervision.mi.util.CommonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WebApi412ServiceImpl implements WebApi412Service {
	
	@Autowired
	private CMi411DAO cmi411DAO;
	@Autowired
	private Mi412DAO mi412DAO;
	@Autowired
	private CommonUtil commonUtil;

	//增加
	public String webapi411Add(CMi411 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		checkCMi411(form);
		Mi411Example example = new Mi411Example();
		example.createCriteria().andCenteridEqualTo(form.getCenterid()).andThemeEqualTo(form.getTheme())
		.andChannelEqualTo(form.getChannel()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		int count = cmi411DAO.countByExample(example);
		if(count !=0){
			log.error(ERROR.ADD_CHECK.getLogText("消息模板MI411违反唯一约束"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.ADD_CHECK.getLogText("消息模板MI411违反唯一约束"));
		}
		// 采号
		String templateId = commonUtil.genKey("MI411", 6).toString();// 采号生成，前补0，总长度20
		if (CommonUtil.isEmpty(templateId)) {
			log.error(ERROR.NULL_KEY.getLogText("消息模板MI411"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.NULL_KEY.getLogText("消息模板MI411"));
		}
		
		form.setTemplateid(templateId);
		form.setValidflag(Constants.IS_VALIDFLAG);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		form.setFreeuse2("1");
		cmi411DAO.insert(form);
		saveTemplateDetail(form.getTemplatecontent(),templateId);
		return templateId;
	}
	//删除
	@Transactional
	public void webapi411Remove(CMi411 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getTemplateid())){
			log.error(ERROR.NULL_KEY.getLogText("消息模板MI411删除主键为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.NULL_KEY.getLogText("消息模板MI411删除主键为空"));
		}
		String[] ids = form.getTemplateid().trim().split(",");
		for(String id:ids){
			Mi411 mi411 = cmi411DAO.selectByPrimaryKey(id);
			if(CommonUtil.isEmpty(mi411)){
				log.error(ERROR.DEL_CHECK.getLogText("消息模板MI411记录不存在"+id));
				throw new TransRuntimeErrorException(WEB_ALERT.DEL_CHECK.getValue(), ERROR.DEL_CHECK.getLogText("消息模板MI411记录不存在"+id));
			}
			//首先删模板下的元素
			Mi412Example example = new Mi412Example(); 
			example.createCriteria().andTemplateidEqualTo(id).andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi412DAO.deleteByExample(example);
			//删除模板自己
			cmi411DAO.deleteByPrimaryKey(id);
		}
	}
	//修改（保存）
	@SuppressWarnings("unchecked")
	@Transactional
	public void webapi411Update(CMi411 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		checkCMi411(form);
		if(CommonUtil.isEmpty(form.getTemplateid())){
			log.error(ERROR.PARAMS_NULL.getLogText("消息模板Mi411主键为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "消息模板Mi411主键为空");
		}
		Mi411Example example = new Mi411Example();
		example.createCriteria().andCenteridEqualTo(form.getCenterid())
		.andThemeEqualTo(form.getTheme()).andChannelEqualTo(form.getChannel()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi411> select411 = cmi411DAO.selectByExample(example);
		if(select411 !=null && !select411.isEmpty()){
			String templateid = select411.get(0).getTemplateid();
			if(!templateid.equals(form.getTemplateid())){
				log.error(ERROR.ADD_CHECK.getLogText("消息模板MI411违反唯一约束"));
				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.ADD_CHECK.getLogText("消息模板MI411违反唯一约束"));
			}
		}
		
		Mi411 mi411 = cmi411DAO.selectByPrimaryKey(form.getTemplateid());
		if(CommonUtil.isEmpty(mi411)){
			log.error(ERROR.NO_DATA.getLogText("消息模板Mi411不存在该记录："+form.getTemplateid()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "消息模板Mi411不存在该记录："+form.getTemplateid());
		}
		mi411.setCenterid(form.getCenterid());
		mi411.setChannel(form.getChannel());
		mi411.setChanneltemplate(form.getChanneltemplate());
		mi411.setDatemodified(CommonUtil.getSystemDate());
		mi411.setPid(form.getPid());
		mi411.setTemplatecontent(form.getTemplatecontent());
		mi411.setTheme(form.getTheme());
		mi411.setFreeuse1(form.getFreeuse1());
		cmi411DAO.updateByPrimaryKeySelective(mi411);
		//要素表
		Mi412Example mi412Example = new Mi412Example();
		mi412Example.createCriteria().andTemplateidEqualTo(form.getTemplateid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		mi412DAO.deleteByExample(mi412Example);
		saveTemplateDetail(form.getTemplatecontent(),form.getTemplateid());
		
	}
	
	//修改（保存）
		@SuppressWarnings("unchecked")
		@Transactional
		public void webapi411UpdateSend(CMi411 form) throws Exception{
			Logger log = LoggerUtil.getLogger();
			
			if(CommonUtil.isEmpty(form.getTemplateid())){
				log.error(ERROR.PARAMS_NULL.getLogText("消息模板Mi411主键为空"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "消息模板Mi411主键为空");
			}
			Mi411Example example = new Mi411Example();
			example.createCriteria().andTemplateidEqualTo(form.getTemplateid());
			Mi411 mi411 = cmi411DAO.selectByPrimaryKey(form.getTemplateid());
			if(!CommonUtil.isEmpty(mi411)){
				Mi411 upmi411 = new Mi411();
				upmi411.setTemplateid(mi411.getTemplateid());
				if(!CommonUtil.isEmpty(form.getFreeuse2())){
					upmi411.setFreeuse2(form.getFreeuse2());
				}
				if(!CommonUtil.isEmpty(form.getTemplatedemo())){
					upmi411.setTemplatedemo(form.getTemplatedemo());
				}
				
				cmi411DAO.updateByPrimaryKeySelective(upmi411);
			}
		}
	
	//查询
	public WebApi41104Query_queryResult webapi411Query(CMi411 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form)){
			log.error(ERROR.NO_DATA.getLogText("消息模板Mi411", "form：" + form));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "消息模板Mi411");
		}
		if(CommonUtil.isEmpty(form.getPage())){
			log.error(ERROR.PARAMS_NULL.getLogText("消息模板Mi411", "page：" + form.getPage()));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "消息模板Mi411");
		}
		if(CommonUtil.isEmpty(form.getRows())){
			log.error(ERROR.PARAMS_NULL.getLogText("消息模板Mi411", "rows：" + form.getRows()));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "消息模板Mi411");
		}
		WebApi41104Query_queryResult result = cmi411DAO.select411Page(form);
		return result;
		
		
	}
	
	//根据模板查询要素
	@SuppressWarnings("unchecked")
	public List<Mi412> webapi411Detail(CMi411 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getTemplateid())){
			log.error(ERROR.PARAMS_NULL.getLogText("消息模板Mi411主键为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "消息模板Mi411主键为空");
		}
		Mi412Example example = new Mi412Example();
		example.setOrderByClause("orderid");
		example.createCriteria().andTemplateidEqualTo(form.getTemplateid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi412> list = mi412DAO.selectByExample(example);
		return list;
		
	}
	
	//模板要素修改
	public void webapi41203(JSONArray arr) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(arr)){
			log.error(ERROR.PARAMS_NULL.getLogText("要修改的数组为空:"+arr));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"要修改的数组为空:"+arr);
		}
		for(int i=0;i<arr.size();i++){
			JSONObject obj = arr.getJSONObject(i);
			String id = obj.getString("templatedetailid");
			String apidata = obj.getString("apidata");
			String templatekey = obj.getString("templatekey");
			Mi412 mi412 = new Mi412();
			mi412.setTemplatedetailid(id);
			mi412.setApidata(apidata);
			mi412.setTemplatekey(templatekey);
			mi412.setDatemodified(CommonUtil.getSystemDate());
			mi412DAO.updateByPrimaryKeySelective(mi412);
		}
	}

	
	private void saveTemplateDetail(String templateDetail,String templateId)throws Exception{
		//先根据｝截取
		String[] templateDetailName = templateDetail.split("}");//如:xx{name1,xx{name2,xx{name3,xxxx
		int count=0;
		//如:xx{name1,xx{name2,xx{name3,xxxx
		for(int i=0;i<templateDetailName.length;i++){
			// 再根据｛截取，后半部分则为关键词
			String[] t = templateDetailName[i].split("\\{");
			if (t.length > 1 && (!"".equals(t[1].trim()))) {
				Mi412 mtd = new Mi412();
				mtd.setTemplateid(templateId);
				mtd.setDatecreated(CommonUtil.getSystemDate());
				mtd.setDatemodified(CommonUtil.getSystemDate());
				mtd.setTemplatedetailid(commonUtil.genKey("MI412", 6).toString());
				mtd.setValidflag(Constants.IS_VALIDFLAG);
				mtd.setTemplatedata(t[1]);
				mtd.setTemplatekey("0");
				mtd.setOrderid(count);
				mi412DAO.insert(mtd);
				count++;
			}
		}
	}
	
	private void checkCMi411(CMi411 form)throws Exception{
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(form.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心编码"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "中心编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getChannel())) {
			log.error(ERROR.PARAMS_NULL.getLogText("渠道编码"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "渠道编码为空");
		}
		if (CommonUtil.isEmpty(form.getTheme())) {
			log.error(ERROR.PARAMS_NULL.getLogText("主题"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "主题为空");
		}
		if (CommonUtil.isEmpty(form.getTemplatecontent())) {
			log.error(ERROR.PARAMS_NULL.getLogText("模板内容"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "模板内容为空");
		}
		if (CommonUtil.isEmpty(form.getChanneltemplate())) {
			log.error(ERROR.PARAMS_NULL.getLogText("模板编码"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "模板编码为空");
		}
	}
	
	public CMi411DAO getCmi411DAO() {
		return cmi411DAO;
	}
	public void setCmi411DAO(CMi411DAO cmi411dao) {
		cmi411DAO = cmi411dao;
	}
	public Mi412DAO getMi412DAO() {
		return mi412DAO;
	}
	public void setMi412DAO(Mi412DAO mi412dao) {
		mi412DAO = mi412dao;
	}
	public CommonUtil getCommonUtil() {
		return commonUtil;
	}
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
}

