package com.yondervision.mi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi031DAO;
import com.yondervision.mi.dao.Mi001DAO;
import com.yondervision.mi.dao.Mi007DAO;
import com.yondervision.mi.dao.Mi029DAO;
import com.yondervision.mi.dao.Mi030DAO;
import com.yondervision.mi.dao.Mi034DAO;
import com.yondervision.mi.dao.Mi040DAO;
import com.yondervision.mi.dao.Mi048DAO;
import com.yondervision.mi.dao.Mi049DAO;
import com.yondervision.mi.dao.Mi625DAO;
import com.yondervision.mi.dto.CMi029;
import com.yondervision.mi.dto.CMi031;
import com.yondervision.mi.dto.Mi001;
import com.yondervision.mi.dto.Mi001Example;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi007Example;
import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.dto.Mi029Example;
import com.yondervision.mi.dto.Mi029Example.Criteria;
import com.yondervision.mi.dto.Mi030;
import com.yondervision.mi.dto.Mi031;
import com.yondervision.mi.dto.Mi031Example;
import com.yondervision.mi.dto.Mi034;
import com.yondervision.mi.dto.Mi034Example;
import com.yondervision.mi.dto.Mi040;
import com.yondervision.mi.dto.Mi040Example;
import com.yondervision.mi.dto.Mi048;
import com.yondervision.mi.dto.Mi048Example;
import com.yondervision.mi.dto.Mi049;
import com.yondervision.mi.dto.Mi049Example;
import com.yondervision.mi.dto.Mi625;
import com.yondervision.mi.dto.Mi625Example;
import com.yondervision.mi.service.WebApi500Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.SpringContextUtil;

import net.sf.json.JSONObject;
@SuppressWarnings("unchecked")
public class WebApi500ServiceImpl implements WebApi500Service {
	
	@Autowired
	private CommonUtil commonUtil;
	@Autowired
	private Mi029DAO mi029DAO;
	@Autowired
	private Mi030DAO mi030DAO;
	@Autowired
	private CMi031DAO cmi031DAO;
	@Autowired
	private Mi007DAO mi007Dao;
	@Autowired
	private Mi040DAO mi040DAO;
	@Autowired 
	private Mi034DAO mi034DAO;
	@Autowired
	private Mi048DAO mi048DAO;
	@Autowired
	private Mi049DAO mi049DAO;
	@Autowired
	public Mi625DAO mi625Dao;
	
	
	/**
	 * 获取中心列表，除去00000000
	 * @return
	 * @throws Exception
	 */
	public List<Mi001> webapi500centerList()throws Exception{
		Mi001DAO mi001Dao = (Mi001DAO)SpringContextUtil.getBean("mi001Dao");
		Mi001Example example = new Mi001Example();
		example.createCriteria().andCenteridNotEqualTo(Constants.YD_ADMIN).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		example.setOrderByClause("centerid");
		List<Mi001> list = mi001Dao.selectByExample(example);
		return list;
	}

	/**
	 * 根据姓名，证件号，手机获取个人信息
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public Mi029 webapi50001(CMi029 form)throws Exception{
		Logger log = LoggerUtil.getLogger();
		String username = form.getUsername();
		String certinum = form.getCertinum();
		String tel = form.getTel();
		String personalid = form.getPersonalid();
		if(CommonUtil.isEmpty(username)&&CommonUtil.isEmpty(certinum)&&CommonUtil.isEmpty(tel)&&CommonUtil.isEmpty(personalid)){
			log.error(ERROR.PARAMS_NULL.getLogText("查询参数都为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "查询参数都为空");
		}
		Mi029Example example = new Mi029Example();
		Criteria ca = example.createCriteria();
		if(!CommonUtil.isEmpty(username)){
			String name = new String(username.getBytes("iso8859-1"),"UTF-8");
			ca.andUsernameEqualTo(name);
		}
		if(!CommonUtil.isEmpty(certinum)){
			ca.andCertinumEqualTo(certinum);
		}
		if(!CommonUtil.isEmpty(tel)){
			ca.andTelEqualTo(tel);
		}
		if(!CommonUtil.isEmpty(personalid)){
			ca.andPersonalidEqualTo(personalid);
		}
//		String centerid = UserContext.getInstance().getCenterid();
//		if(!Constants.YD_ADMIN.equals(centerid)){
//			ca.andCenteridEqualTo(centerid);
//		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		List<Mi029> select = mi029DAO.selectByExample(example);
		if(select !=null && !select.isEmpty()){
			return select.get(0);
		}
		
		return null;
		
	}
	
	/**
	 * 根据用户获取单位信息
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public Mi030 webapi50002(CMi029 form)throws Exception{
		Logger log = LoggerUtil.getLogger();
		String unitaccnum = form.getUnitaccnum();
		if(CommonUtil.isEmpty(unitaccnum)){
			log.error(ERROR.PARAMS_NULL.getLogText("查询参数都为空"+unitaccnum));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "查询参数都为空"+unitaccnum);
		}
		Mi049Example mi049Example = new Mi049Example();
		mi049Example.createCriteria().andUnitaccountcodeEqualTo(unitaccnum).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi049> select049 = mi049DAO.selectByExample(mi049Example);
		if(select049 !=null && !select049.isEmpty()){
			String unitid = select049.get(0).getUnitid();
			Mi030 mi030 = mi030DAO.selectByPrimaryKey(unitid);
			return mi030;
		}
		return null;
	}
	
	/**
	 * 根据用户，服务渠道信息
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public List<Mi031> webapi50005(CMi029 form)throws Exception{
		Logger log = LoggerUtil.getLogger();
		String personalid = form.getPersonalid();
		if(CommonUtil.isEmpty(personalid)){
			log.error(ERROR.PARAMS_NULL.getLogText("查询参数都为空"+personalid));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "查询参数都为空"+personalid);
		}
		Mi031Example mi031Example = new Mi031Example();
		mi031Example.createCriteria().andPersonalidEqualTo(personalid).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi031> select = cmi031DAO.selectByExample(mi031Example);
		if(select !=null && !select.isEmpty()){
			List<Mi031> newList = new ArrayList<Mi031>();
			for(Mi031 mi031:select){
				CMi031 cmi031 = new CMi031();
				BeanUtils.copyProperties(cmi031, mi031);
				
				Mi007Example mi007Example = new Mi007Example();
				mi007Example.createCriteria().andCenteridEqualTo(mi031.getCenterid()).andItemidEqualTo("channel")
				.andUpdicidEqualTo(0);
				Mi007 mi007 = (Mi007)mi007Dao.selectByExample(mi007Example).get(0);
				Integer dicid = mi007.getDicid();
				
				Mi007Example mi007Example2 = new Mi007Example();
				mi007Example2.createCriteria().andItemidEqualTo(mi031.getChannel()).andCenteridEqualTo(mi031.getCenterid())
				.andUpdicidEqualTo(dicid);
				String itemval = ((Mi007)mi007Dao.selectByExample(mi007Example2).get(0)).getItemval();
				
				cmi031.setChannelname(itemval);
				//应用
				Mi040Example mi040Example = new Mi040Example();
				mi040Example.createCriteria().andCenteridEqualTo(mi031.getCenterid()).andPidEqualTo(mi031.getPid().trim())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				List<Mi040> list2 = mi040DAO.selectByExample(mi040Example);
				if(list2 !=null && !list2.isEmpty()){
					String appname = list2.get(0).getAppname();
					cmi031.setAppname(appname);
				}else{
					log.error(ERROR.NO_DATA.getLogText("当前应用没有配置，请检查！"));
					throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "当前应用没有配置，请检查！");
				}
				
				newList.add(cmi031);
			}
			return newList;
		}
		return null;
	}
	
	/**
	 * 根据用户查询关联人
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public List<Mi034> webapi50006(CMi029 form)throws Exception{
		Logger log = LoggerUtil.getLogger();
		String personalid = form.getPersonalid();
		if(CommonUtil.isEmpty(personalid)){
			log.error(ERROR.PARAMS_NULL.getLogText("查询参数都为空"+personalid));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "查询参数都为空"+personalid);
		}
		
		Mi034Example mi034EXample = new Mi034Example();
		mi034EXample.createCriteria().andPersonalidEqualTo(personalid).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi034> select = mi034DAO.selectByExample(mi034EXample);
		return select;
	}
	
	/**
	 * 根据用户查询关联账户
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public List<Mi048> webapi50007(CMi029 form)throws Exception{
		Logger log = LoggerUtil.getLogger();
		String personalid = form.getPersonalid();
		if(CommonUtil.isEmpty(personalid)){
			log.error(ERROR.PARAMS_NULL.getLogText("查询参数都为空"+personalid));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "查询参数都为空"+personalid);
		}
		Mi048Example mi048Example = new Mi048Example();
		mi048Example.createCriteria().andPersonalidEqualTo(personalid).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi048> select = mi048DAO.selectByExample(mi048Example);
		return select;
	}
	
	/**
	 * 根据personalid查询个人的预约信息
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public JSONObject webapi50018(CMi029 form)throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getPersonalid())){
			log.error(ERROR.PARAMS_NULL.getLogText("个人信息主键为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "个人信息主键为空");
		}
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "中心为空");
		}
		if(CommonUtil.isEmpty(form.getPage())){
			log.error(ERROR.PARAMS_NULL.getLogText("page为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "page为空");
		}
		if(CommonUtil.isEmpty(form.getRows())){
			log.error(ERROR.PARAMS_NULL.getLogText("rows为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "rows为空");
		}
		String sql = "select * from mi625 where validflag='"+Constants.IS_VALIDFLAG+"' and centerid='"+form.getCenterid()
		+"' and personalid='"+form.getPersonalid()+"'";
		
		if(!CommonUtil.isEmpty(form.getAppostate())){
			sql+=" and appostate='"+form.getAppostate()+"'";
		}
		
		JSONObject obj = CommonUtil.selectByPage(sql, form.getPage(), form.getRows());
		
		return obj;
	}
	
	public CommonUtil getCommonUtil() {
		return commonUtil;
	}
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}

	public Mi029DAO getMi029DAO() {
		return mi029DAO;
	}

	public void setMi029DAO(Mi029DAO mi029dao) {
		mi029DAO = mi029dao;
	}

	public Mi030DAO getMi030DAO() {
		return mi030DAO;
	}

	public void setMi030DAO(Mi030DAO mi030dao) {
		mi030DAO = mi030dao;
	}

	public CMi031DAO getCmi031DAO() {
		return cmi031DAO;
	}

	public void setCmi031DAO(CMi031DAO cmi031dao) {
		cmi031DAO = cmi031dao;
	}

	public Mi034DAO getMi034DAO() {
		return mi034DAO;
	}

	public void setMi034DAO(Mi034DAO mi034dao) {
		mi034DAO = mi034dao;
	}

	public Mi048DAO getMi048DAO() {
		return mi048DAO;
	}

	public void setMi048DAO(Mi048DAO mi048dao) {
		mi048DAO = mi048dao;
	}

	public Mi049DAO getMi049DAO() {
		return mi049DAO;
	}

	public void setMi049DAO(Mi049DAO mi049dao) {
		mi049DAO = mi049dao;
	}

	public Mi007DAO getMi007Dao() {
		return mi007Dao;
	}

	public void setMi007Dao(Mi007DAO mi007Dao) {
		this.mi007Dao = mi007Dao;
	}

	public Mi040DAO getMi040DAO() {
		return mi040DAO;
	}

	public void setMi040DAO(Mi040DAO mi040dao) {
		mi040DAO = mi040dao;
	}

	public Mi625DAO getMi625Dao() {
		return mi625Dao;
	}

	public void setMi625Dao(Mi625DAO mi625Dao) {
		this.mi625Dao = mi625Dao;
	}
}
