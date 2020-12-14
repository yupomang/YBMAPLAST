package com.yondervision.mi.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi623DAO;
import com.yondervision.mi.dao.CMi701DAO;
import com.yondervision.mi.dao.CMi704DAO;
import com.yondervision.mi.dao.Mi001DAO;
import com.yondervision.mi.dao.Mi007DAO;
import com.yondervision.mi.dao.Mi106DAO;
import com.yondervision.mi.dao.Mi201DAO;
import com.yondervision.mi.dao.Mi202DAO;
import com.yondervision.mi.dao.Mi620DAO;
import com.yondervision.mi.dao.Mi621DAO;
import com.yondervision.mi.dao.Mi622DAO;
import com.yondervision.mi.dao.Mi707DAO;
import com.yondervision.mi.dto.Mi001;
import com.yondervision.mi.dto.Mi001Example;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi007Example;
import com.yondervision.mi.dto.Mi106;
import com.yondervision.mi.dto.Mi106Example;
import com.yondervision.mi.dto.Mi201;
import com.yondervision.mi.dto.Mi201Example;
import com.yondervision.mi.dto.Mi202;
import com.yondervision.mi.dto.Mi202Example;
import com.yondervision.mi.dto.Mi620;
import com.yondervision.mi.dto.Mi620Example;
import com.yondervision.mi.dto.Mi621;
import com.yondervision.mi.dto.Mi621Example;
import com.yondervision.mi.dto.Mi622;
import com.yondervision.mi.dto.Mi622Example;
import com.yondervision.mi.dto.Mi704;
import com.yondervision.mi.dto.Mi704Example;
import com.yondervision.mi.dto.Mi707;
import com.yondervision.mi.dto.Mi707Example;
import com.yondervision.mi.result.NewspapersTitleInfoBean;
import com.yondervision.mi.service.CodeListApi001Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: PtlApiServiceImpl
* @Description: 码表处理实现
* @author gongqi
* @date 2013-10-04
* 
*/ 
public class CodeListApi001ServiceImpl implements CodeListApi001Service {
	
	public static Map<String, HashMap<String, String>> codeValMap
			= new HashMap<String, HashMap<String, String>>();
	
	@SuppressWarnings("unchecked")
	public static Map<String, HashMap<String, List>> codeListJsonMap
			= new HashMap<String, HashMap<String, List>>();
	 
	public Mi001DAO mi001Dao = null;
	
	public Mi007DAO mi007Dao = null;

	public Mi106DAO mi106Dao = null;
	
	public Mi201DAO mi201Dao = null;
	
	public Mi202DAO mi202Dao = null;
	
	public Mi620DAO mi620Dao = null;
	
	public Mi621DAO mi621Dao = null;
	
	public Mi622DAO mi622Dao = null;
			
	public CMi623DAO cmi623Dao = null;
	
	public CMi704DAO cmi704Dao = null;
	
	public CMi701DAO cmi701Dao = null;
	
	public Mi707DAO mi707Dao = null;

	public void setMi001Dao(Mi001DAO mi001Dao) {
		this.mi001Dao = mi001Dao;
	}

	public void setMi007Dao(Mi007DAO mi007Dao) {
		this.mi007Dao = mi007Dao;
	}

	public void setMi106Dao(Mi106DAO mi106Dao) {
		this.mi106Dao = mi106Dao;
	}

	public void setMi202Dao(Mi202DAO mi202Dao) {
		this.mi202Dao = mi202Dao;
	}
	
	public void setMi620Dao(Mi620DAO mi620Dao) {
		this.mi620Dao = mi620Dao;
	}
	
	public void setMi621Dao(Mi621DAO mi621Dao) {
		this.mi621Dao = mi621Dao;
	}
	
	public void setMi622Dao(Mi622DAO mi622Dao) {
		this.mi622Dao = mi622Dao;
	}
	
	public void setCmi623Dao(CMi623DAO cmi623Dao) {
		this.cmi623Dao = cmi623Dao;
	}
	
	public void setCmi704Dao(CMi704DAO cmi704Dao) {
		this.cmi704Dao = cmi704Dao;
	}
	
	public void setCmi701Dao(CMi701DAO cmi701Dao) {
		this.cmi701Dao = cmi701Dao;
	}
	
	public void setMi707Dao(Mi707DAO mi707Dao) {
		this.mi707Dao = mi707Dao;
	}

	/**
	 * 获取码表中对应编码的名称
	 * @param centerid
	 * @param param
	 * @return String
	 */
	public String getCodeVal(String centerid, String param){
		
		String returnVal = null;
		
		// 判断缓存中是否有满足需要的值
		if (codeValMap.get(centerid) != null) {
			HashMap<String, String> valMap = new HashMap<String, String>();
			valMap = codeValMap.get(centerid);
			if (valMap.get(param) != null) {
				returnVal = valMap.get(param);	
				return returnVal; 
			}else{
				if (codeValMap.get("00000000") != null) {
					HashMap<String, String> valSuperMap = new HashMap<String, String>();
					valSuperMap = codeValMap.get("00000000");
					if (valSuperMap.get(param) != null) {
						returnVal = valSuperMap.get(param);	
						return returnVal;
					}
				}
			}
		}else{
			if (codeValMap.get("00000000") != null) {
				HashMap<String, String> valSuperMap = new HashMap<String, String>();
				valSuperMap = codeValMap.get("00000000");
				if (valSuperMap.get(param) != null) {
					returnVal = valSuperMap.get(param);	
					return returnVal;
				}
			}
		}
		// 参数合法性校验
		if (centerid == null || centerid.length() == 0) {
			//抛异常
		}else if (centerid.length() < 8) {
			// 右补空格
			StringBuffer buf = new StringBuffer();
			for (int i = 0 ;i < (8 - centerid.length()); i++ ) {
				buf.append(" ");
			}
			centerid = centerid.concat(buf.toString());
		}
		
		returnVal = queryCodeValInfo(centerid, 0, param);
		if (CommonUtil.isEmpty(returnVal)) {
			returnVal = queryCodeValInfo("00000000", 0, param);
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(param, returnVal);
		codeValMap.put(centerid, map);
		
		return returnVal;
	}
	
	/**
	 * 获取码表中对应param的子级编码列表
	 * @param centerid
	 * @param param
	 * @return list
	 */
	public List<Mi007> getCodeList(String centerid, String param){
		// 参数合法性校验
		if (centerid == null || centerid.length() == 0) {
			//抛异常
		}else if (centerid.length() < 8) {
			// 右补空格
			StringBuffer buf = new StringBuffer();
			for (int i = 0 ;i < (8 - centerid.length()); i++ ) {
				buf.append(" ");
			}
			centerid = centerid.concat(buf.toString());
		}
		
		List<Mi007> list = new ArrayList<Mi007>();
		// 循环要查询参数列表，分隔符最后一个参数的数据信息，提供给下一步查询获取使用
		list = queryChildList(centerid, 0, param);
		// 对应当前城市中心ID的码表信息存在
		if (CommonUtil.isEmpty(list)) {
			// 到中心ID为00000000（公共基础数据中）
			list = queryChildList("00000000", 0, param);
		}

		return list;
	}
	
	public List<Mi007> getSubCodeList(String centerid, int dicid){
		List<Mi007> list = new ArrayList<Mi007>();
		list = getChildList(centerid, dicid);
		// 对应当前城市中心ID的码表信息存在
		if (CommonUtil.isEmpty(list)) {
			// 到中心ID为00000000（公共基础数据中）
			list = getChildList("00000000", dicid);
		}

		return list;
	}
	
	@SuppressWarnings("unchecked")
	private List<Mi007> getChildList(String centerid, int dicid) {
		List<Mi007> list = new ArrayList<Mi007>();
		Mi007Example example = new Mi007Example();
		example.createCriteria()
		.andCenteridEqualTo(centerid)
		.andUpdicidEqualTo(dicid);
		example.setOrderByClause("DICID ASC");
		list = this.mi007Dao.selectByExample(example);
		return list;
	}
	
	/**
	 * 获取码表中对应编码的名称
	 * @param centerid
	 * @param param
	 * @return JSONObject
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getCodeValJson(String centerid, String param){
		ObjectMapper mapper = new  ObjectMapper();
		String returnVal = null;
		String[] params = param.split("[.]");
		
		// 判断缓存中是否有满足需要的值
		if (codeValMap.get(centerid) != null) {
			HashMap<String, String> valMap = new HashMap<String, String>();
			valMap = codeValMap.get(centerid);
			if (valMap.get(param) != null) {
				returnVal = valMap.get(param);	
				HashMap<String,String> returnMap = new HashMap<String,String>();
				returnMap.put(params[params.length-1], returnVal);
				return mapper.convertValue(returnMap, JSONObject.class); 
			}else{
				if (codeValMap.get("00000000") != null) {
					HashMap<String, String> valSuperMap = new HashMap<String, String>();
					valSuperMap = codeValMap.get("00000000");
					if (valSuperMap.get(param) != null) {
						returnVal = valSuperMap.get(param);	
						HashMap<String,String> returnMap = new HashMap<String,String>();
						returnMap.put(params[params.length-1], returnVal);
						return mapper.convertValue(returnMap, JSONObject.class); 
					}
				}
			}
		}else{
			if (codeValMap.get("00000000") != null) {
				HashMap<String, String> valSuperMap = new HashMap<String, String>();
				valSuperMap = codeValMap.get("00000000");
				if (valSuperMap.get(param) != null) {
					returnVal = valSuperMap.get(param);	
					HashMap<String,String> returnMap = new HashMap<String,String>();
					returnMap.put(params[params.length-1], returnVal);
					return mapper.convertValue(returnMap, JSONObject.class); 
				}
			}
		}
		
		// 参数合法性校验
		if (centerid == null || centerid.length() == 0) {
			//抛异常
		}else if (centerid.length() < 8) {
			// 右补空格
			StringBuffer buf = new StringBuffer();
			for (int i = 0 ;i < (8 - centerid.length()); i++ ) {
				buf.append(" ");
			}
			centerid = centerid.concat(buf.toString());
		}
		
		returnVal = queryCodeValInfo(centerid, 0, param);
		if (CommonUtil.isEmpty(returnVal)) {
			returnVal = queryCodeValInfo("00000000", 0, param);
		}
		
		HashMap map=new HashMap();
		map.put(params[params.length-1], returnVal);
		
		HashMap<String, String> mapTmp = new HashMap<String, String>();
		mapTmp.put(param, returnVal);
		codeValMap.put(centerid, mapTmp);
		return mapper.convertValue(map, JSONObject.class);
	}
	
	/**
	 * 获取码表中对应param的子级编码列表
	 * 例如：ratetype、ratetype.type1下的所有内容列表
	 * @param centerid
	 * @param param
	 * @return JSONArray【格式：{{检索结果记录中第一条的JSONObject对象},{第二条的JSONObject对象},{..}...}】
	 * 以利率类型为例：[{"centerid":"00000000","dicid":2,"itemval":"公积金贷款","itemid":"10","updicid":1},
	 * {"centerid":"00000000","dicid":3,"itemval":"商业贷款5","itemid":"20","updicid":1},
	 * {"centerid":"00000000","dicid":4,"itemval":"定期存款","itemid":"30","updicid":1},
	 * {"centerid":"00000000","dicid":5,"itemval":"活期存款","itemid":"40","updicid":1}]
	 */
	@SuppressWarnings("unchecked")
	public JSONArray getCodeListJson(String centerid, String param) {
		
		ObjectMapper mapper = new  ObjectMapper();
		List<Mi007> list = new ArrayList<Mi007>();
		
		// 判断缓存中是否有满足需要的值
		if (codeListJsonMap.get(centerid) != null) {
			HashMap<String, List> valMap = new HashMap<String, List>();
			valMap = codeListJsonMap.get(centerid);
			if (valMap.get(param) != null) {
				list = valMap.get(param);
				return mapper.convertValue(list, JSONArray.class); 
			}else{
				if (codeListJsonMap.get("00000000") != null) {
					HashMap<String, List> valSuperMap = new HashMap<String, List>();
					valSuperMap = codeListJsonMap.get("00000000");
					if (valSuperMap.get(param) != null) {
						list = valSuperMap.get(param);
						return mapper.convertValue(list, JSONArray.class); 
					}
				}
			}
		}else{
			if (codeListJsonMap.get("00000000") != null) {
				HashMap<String, List> valSuperMap = new HashMap<String, List>();
				valSuperMap = codeListJsonMap.get("00000000");
				if (valSuperMap.get(param) != null) {
					list = valSuperMap.get(param);
					return mapper.convertValue(list, JSONArray.class); 
				}
			}
		}
		
		// 参数合法性校验
		if (centerid == null || centerid.length() == 0) {
			//抛异常
		}else if (centerid.length() < 8) {
			// 右补空格
			StringBuffer buf = new StringBuffer();
			for (int i = 0 ;i < (8 - centerid.length()); i++ ) {
				buf.append(" ");
			}
			centerid = centerid.concat(buf.toString());
		}
		
		// 循环要查询参数列表，获取分隔符最后一个参数的数据信息，提供给下一步查询获取使用
		list = queryChildList(centerid, 0, param);
		// 对应当前城市中心ID的码表信息存在
		if (CommonUtil.isEmpty(list)) {
			// 到中心ID为00000000（公共基础数据中）
			list = queryChildList("00000000", 0, param);
		}
		HashMap<String, List> valMap = new HashMap<String, List>();
		valMap.put(param, list);
		codeListJsonMap.put(centerid, valMap);
		
		return mapper.convertValue(list, JSONArray.class); 
	}
	
	/**
	 * 循环要查询参数列表中的最后一个参数对应的value值
	 * @param centerid
	 * @param dicid
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String queryCodeValInfo(String centerid, int dicid, String param) {
		String returnVal = null;
		Mi007 mi007 = new Mi007();
		mi007.setCenterid(centerid);
		mi007.setDicid(dicid);

		String[] params = param.split("[.]");
		//循环要查询参数列表，获取分隔符最后一个参数的数据信息，提供给下一步查询获取使用
		for (int i = 0; i < params.length; i++) {
			Mi007Example example = new Mi007Example();
			example.createCriteria()
			.andCenteridEqualTo(mi007.getCenterid())
			.andUpdicidEqualTo(mi007.getDicid())
			.andItemidEqualTo(params[i]);
			List<Mi007> list = this.mi007Dao.selectByExample(example);
			if (list.size() != 0) {
				mi007 = list.get(0);
			}else {
				mi007 = new Mi007();
				break;
			}
		}
		
		if (!CommonUtil.isEmpty(mi007)) {
			returnVal = mi007.getItemval();
		}
		return returnVal;
	}
	
	/**
	 * 循环要查询参数列表，获取分隔符最后一个参数的子级列表
	 * @param centerid
	 * @param dicid
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Mi007> queryChildList(String centerid, int dicid, String param) {
		
		List<Mi007> list = new ArrayList<Mi007>();
		
		Mi007 mi007 = new Mi007();
		mi007.setCenterid(centerid);
		mi007.setDicid(dicid);

		String[] params = param.split("[.]");
		//循环要查询参数列表，获取分隔符最后一个参数的数据信息，提供给下一步查询获取使用
		for (int i = 0; i < params.length; i++) {
			Mi007Example example = new Mi007Example();
			example.createCriteria()
			.andCenteridEqualTo(mi007.getCenterid())
			.andUpdicidEqualTo(mi007.getDicid())
			.andItemidEqualTo(params[i]);
			list = this.mi007Dao.selectByExample(example);
			if (list.size() != 0) {
				mi007 = list.get(0);
			}else {
				mi007 = new Mi007();
				list = new ArrayList<Mi007>();
				break;
			}
		}
		
		if (list.size() != 0 ){
			mi007 = list.get(0);
			// 获取当前层级下包含内容
			Mi007Example example = new Mi007Example();
			example.createCriteria()
			.andCenteridEqualTo(mi007.getCenterid())
			.andUpdicidEqualTo(mi007.getDicid());
			example.setOrderByClause("DICID ASC");
			list = this.mi007Dao.selectByExample(example);
		}
		
		return list;
	}
	
	/**
	 * 当码表有维护操作时，需要重载
	 */
	public static void clearCodeMap() {
		codeValMap.clear();
		codeListJsonMap.clear();
	}

	@SuppressWarnings("unchecked")
	public List<Mi001> getCityMessage() {
		Mi001Example example = new Mi001Example();
		example.createCriteria().andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi001> list = mi001Dao.selectByExample(example);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Mi202> getAreaMessage(String centerid) {
		Mi202Example example = new Mi202Example();
		example.createCriteria().andCenteridEqualTo(centerid).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi202> list = mi202Dao.selectByExample(example);
		return list;		
	}
	@SuppressWarnings("unchecked")
	public List<Mi620> getBussType(String centerid) {
		Mi620Example example = new Mi620Example();
		example.createCriteria().andCenteridEqualTo(centerid).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi620> list = mi620Dao.selectByExample(example);
		return list;		
	}
	
	public List<Mi621> getBussTempla(String centerid){
		Mi621Example example = new Mi621Example();
		example.createCriteria().andCenteridEqualTo(centerid).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi621> list = mi621Dao.selectByExample(example);
		return list;
	}
	
	/**
	 * 查询有效的软件版本编号
	 */
	@SuppressWarnings("unchecked")
	public List<Mi106> getVersionno(String centerid) {
		List<Mi106> list = new ArrayList<Mi106>();
		Mi106Example example = new Mi106Example();
		example.createCriteria().andCenteridEqualTo(centerid)
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		list = mi106Dao.selectByExample(example);
		return list;
	}
	
	/**
	 * 查询有效的软件版本编号
	 */
	public JSONObject getVersionnoJson(){
		ObjectMapper mapper = new  ObjectMapper();
		// 获取城市list
		List<Mi001> mi001list = getCityMessage();
		HashMap<String, List<HashMap<String, String>>> centerVernoMap = new HashMap<String, List<HashMap<String, String>>>();
		for (int i = 0 ; i < mi001list.size(); i++) {
			// 利用获取的城市信息，获取对应该城市的有效的软件版本编号
			List<Mi106> mi106list = getVersionno(mi001list.get(i).getCenterid());
			List<HashMap<String, String>> vernoList = new ArrayList<HashMap<String, String>>();
			for(int j = 0 ; j < mi106list.size(); j++){
				HashMap<String, String> vernoMap = new HashMap<String, String>();
				vernoMap.put(mi106list.get(j).getVersionno(), mi106list.get(j).getVersionno());
				vernoList.add(vernoMap);
			}
			centerVernoMap.put(mi001list.get(i).getCenterid(), vernoList);
		}
		
		return mapper.convertValue(centerVernoMap, JSONObject.class);
	}

	public List<Mi201> getMi201(String centerid) {
		List<Mi201> list = new ArrayList<Mi201>();
		Mi201Example example = new Mi201Example();
		example.createCriteria().andCenteridEqualTo(centerid)
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		list = mi201Dao.selectByExample(example);
		return list;
	}

	public void setMi201Dao(Mi201DAO mi201Dao) {
		this.mi201Dao = mi201Dao;
	}
	
	public List<Mi622> getBussTemplaDetail(String template){
		Mi622Example example = new Mi622Example();
		example.createCriteria().andValidflagEqualTo(Constants.IS_VALIDFLAG).andAppotemplateidEqualTo(template);
		List<Mi622> list = mi622Dao.selectByExample(example);
		return list;
	}

	public List<HashMap> getWebSiteInfo(String centerid) {
		// TODO Auto-generated method stub
		List<HashMap> list=cmi623Dao.selectWebSiteInfo4AppoQuery(centerid);
		return list;
	}

	/**
	 * 根据报刊期次，Mi701获取页面版块下拉选择框的级联数据
	 */
	public List<NewspapersTitleInfoBean> getForumByTimesFromMi701(String centerid, String newspapertimes)throws Exception{
		List<NewspapersTitleInfoBean> forumList = new ArrayList<NewspapersTitleInfoBean>();
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("centerId", centerid);
		paraMap.put("newspapertimes", newspapertimes);

		List<HashMap<String, String>> forumMapList = cmi701Dao.selectForumByClassification(paraMap);

		for (int i = 0;i <forumMapList.size(); i++){
			NewspapersTitleInfoBean forumBean = new NewspapersTitleInfoBean();
			forumBean.setItemid(forumMapList.get(i).get("newspaperforum"));
			forumBean.setItemval(getCodeVal(centerid,
					Constants.FORUM_CODE+"."+forumMapList.get(i).get("newspaperforum")));
			forumList.add(forumBean);
		}
		return forumList;
	}
	
	/**
	 * 根据报刊期次，Mi701获取页面栏目下拉选择框的级联数据
	 */
	public List<NewspapersTitleInfoBean> getColumnsByTimesFromMi701(String centerid, String newspapertimes)throws Exception{
		List<NewspapersTitleInfoBean> columnsList = new ArrayList<NewspapersTitleInfoBean>();
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("centerId", centerid);
		paraMap.put("newspapertimes", newspapertimes);
		
		List<HashMap<String, String>> columnsMapList = cmi701Dao.selectColumnsByClassification(paraMap);
		
		for (int i = 0;i <columnsMapList.size(); i++){
			NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
			columnsBean.setItemid(columnsMapList.get(i).get("newspapercolumns"));
			columnsBean.setItemval(getCodeVal(centerid,
					Constants.COLUMNS_CODE+"."+columnsMapList.get(i).get("newspapercolumns")));
			columnsList.add(columnsBean);
		}
		// 如果新闻信息表已发布最新期次包含栏目为空的记录，则将返回前台的栏目列表追加{"other":"其他"}
		int columnsNullCounts = cmi701Dao.selectColumnsNullCountByClassification(paraMap);
		if (0 != columnsNullCounts){
			NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
			columnsBean.setItemid("other");
			columnsBean.setItemval("其他");
			columnsList.add(columnsBean);
		}
		return columnsList;
	}
	
	/**
	 * 根据报刊期次、版块，Mi701获取页面栏目下拉选择框的级联数据
	 */
	public List<NewspapersTitleInfoBean> getColumnsByTimesForumFromMi701(String centerid,
			String newspapertimes, String newspaperforum)throws Exception{
		List<NewspapersTitleInfoBean> columnsList = new ArrayList<NewspapersTitleInfoBean>();
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("centerId", centerid);
		paraMap.put("newspapertimes", newspapertimes);
		paraMap.put("newspaperforum", newspaperforum);
		
		List<HashMap<String, String>> columnsMapList = cmi701Dao.selectColumnsByTimesForum(paraMap);
		
		for (int i = 0;i <columnsMapList.size(); i++){
			NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
			columnsBean.setItemid(columnsMapList.get(i).get("newspapercolumns"));
			columnsBean.setItemval(getCodeVal(centerid,
					Constants.COLUMNS_CODE+"."+columnsMapList.get(i).get("newspapercolumns")));
			columnsList.add(columnsBean);
		}
		// 如果新闻信息表已发布最新期次包含栏目为空的记录，则将返回前台的栏目列表追加{"other":"其他"}
		int columnsNullCounts = cmi701Dao.selectColumnsNullCountByTimesForum(paraMap);
		if (0 != columnsNullCounts){
			NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
			columnsBean.setItemid("other");
			columnsBean.setItemval("其他");
			columnsList.add(columnsBean);
		}
		
		return columnsList;
	}
	
	
	/**
	 * 根据报刊期次，Mi704获取页面版块下拉选择框的级联数据
	 */
	@SuppressWarnings("unchecked")
	public List<Mi704> getForumByTimesFromMi704(String centerid,
			String newspapertimes)throws Exception{
		List<Mi704> forumList = new ArrayList<Mi704>();
		Mi704Example forumExample = new Mi704Example();
		forumExample.setOrderByClause("abs(itemid) asc");
		forumExample.createCriteria().andCenteridEqualTo(centerid).andUpdicidEqualTo(newspapertimes);
		forumList = cmi704Dao.selectByExample(forumExample);
		return forumList;
		
	}
	
	/**
	 * 根据报刊期次，Mi704获取页面栏目下拉选择框的级联数据
	 */
	public List<NewspapersTitleInfoBean> getColumnsByTimesFromMi704(String centerid,
			String newspapertimes)throws Exception{
		List<NewspapersTitleInfoBean> columnsList = new ArrayList<NewspapersTitleInfoBean>();
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("centerId", centerid);
		paraMap.put("newspapertimes", newspapertimes);
		
		List<HashMap<String, String>> columnsMapList = cmi704Dao.getColumnsByTimes(paraMap);
		for (int i = 0;i <columnsMapList.size(); i++){
			NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
			columnsBean.setItemid(columnsMapList.get(i).get("itemid"));
			columnsBean.setItemval(getCodeVal(centerid,
					Constants.COLUMNS_CODE+"."+columnsMapList.get(i).get("itemid")));
			columnsList.add(columnsBean);
		}
	
		return columnsList;
	}
	
	/**
	 * 根据报刊期次、版块，Mi704获取页面栏目下拉选择框的级联数据
	 */
	public List<NewspapersTitleInfoBean> getColumnsByTimesForumFromMi704(String centerid,
			String newspapertimes, String newspaperforum)throws Exception{
		List<NewspapersTitleInfoBean> columnsList = new ArrayList<NewspapersTitleInfoBean>();
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("centerId", centerid);
		paraMap.put("newspapertimes", newspapertimes);
		paraMap.put("newspaperforum", newspaperforum);
		
		List<HashMap<String, String>> columnsMapList = cmi704Dao.getColumnsByTimesForum(paraMap);
		for (int i = 0;i <columnsMapList.size(); i++){
			NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
			columnsBean.setItemid(columnsMapList.get(i).get("itemid"));
			columnsBean.setItemval(columnsMapList.get(i).get("itemval"));
			columnsList.add(columnsBean);
		}
		return columnsList;
	}
	
	/**
	 * [版块栏目升级合并后废弃]
	 * Mi701获取页面版块下拉选择框数据（Mi007配合mi701共同实现），主要web用
	 * 根据公共参数centerid获取当前新闻中实际已发布过新闻的版块的列表(按编号从小到大升序)
	 */
	public List<NewspapersTitleInfoBean> getForumFromMi007AndMi701(String centerid)throws Exception{
		List<NewspapersTitleInfoBean> forumList = new ArrayList<NewspapersTitleInfoBean>();
		
		List<Mi007> forumAllList = getCodeList(centerid, Constants.FORUM_CODE);
		HashMap<String, String> paraMap;
		for (int i = 0; i < forumAllList.size(); i++){
			paraMap = new HashMap<String, String>();
			paraMap.put("centerId", centerid);
			paraMap.put("newspaperforum", forumAllList.get(i).getDicid().toString());
			int forumCount = cmi701Dao.selectForumInMi701Count(paraMap);
			if (0 != forumCount){
				NewspapersTitleInfoBean forumBean = new NewspapersTitleInfoBean();
				forumBean.setItemid(forumAllList.get(i).getDicid().toString());
				forumBean.setItemval(forumAllList.get(i).getItemval());
				forumList.add(forumBean);
			}
		}
		
		return forumList;
	}
	
	/**
	 * Mi701获取页面栏目下拉选择框数据，主要web用
	 */
	public List<NewspapersTitleInfoBean> getColumnsFromMi007AndMi701(String centerid)throws Exception{
		List<NewspapersTitleInfoBean> columnsList = new ArrayList<NewspapersTitleInfoBean>();
		
		List<Mi007> columnsAllList = getCodeList(centerid, Constants.COLUMNS_CODE);
		HashMap<String, String> paraMap;
		for(int i = 0; i < columnsAllList.size(); i++){
			paraMap = new HashMap<String, String>();
			paraMap.put("centerId", centerid);
			paraMap.put("newspapercolumns", columnsAllList.get(i).getDicid().toString());
			int columnsCount = cmi701Dao.selectColumnsInMi701Count(paraMap);
			if (0 != columnsCount){
				NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
				columnsBean.setItemid(columnsAllList.get(i).getDicid().toString());
				columnsBean.setItemval(columnsAllList.get(i).getItemval());
				columnsList.add(columnsBean);
			}
		}
		
		// 如果新闻信息表包含父级为0的记录，表示存在只有版块归属的记录，没有栏目归属，所以将返回前台的栏目列表追加{"other":"其他"}
		HashMap<String, String> paraMapTmp = new HashMap<String, String>();
		paraMapTmp.put("centerId", centerid);
		int updicidZeroCounts = cmi701Dao.selectUpdicidZeroCountFromMi701(paraMapTmp);//即mi701中newspaperforum=0的记录是否存在
		if (0 != updicidZeroCounts){
			NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
			columnsBean.setItemid("other");
			columnsBean.setItemval("其他");
			columnsList.add(columnsBean);
		}
		return columnsList;
	}
	
	/**
	 * 获取对应城市中心的dicid的itemval
	 * @param centerid
	 * @param dicid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String queryCodeValInfoWithDicid(String centerid, int dicid) {
		String returnVal = null;
		Mi007Example example = new Mi007Example();
		example.createCriteria()
		.andCenteridEqualTo(centerid).andDicidEqualTo(dicid);
		List<Mi007> list = this.mi007Dao.selectByExample(example);
		if (list.size() != 0) {
			returnVal = list.get(0).getItemval();
		}
		return returnVal;
	}
	
	/**
	 * 获取码表中对应编码的名称
	 * @param centerid
	 * @param param
	 * @return String
	 */
	public String getCodeValWithDicid(String centerid, int dicid){
		String returnVal = null;
		// 判断缓存中是否有满足需要的值
		if (codeValMap.get(centerid) != null) {
			HashMap<String, String> valMap = new HashMap<String, String>();
			valMap = codeValMap.get(centerid);
			if (valMap.get(String.valueOf(dicid)) != null) {
				returnVal = valMap.get(String.valueOf(dicid));	
				return returnVal; 
			}else{
				if (codeValMap.get("00000000") != null) {
					HashMap<String, String> valSuperMap = new HashMap<String, String>();
					valSuperMap = codeValMap.get("00000000");
					if (valSuperMap.get(String.valueOf(dicid)) != null) {
						returnVal = valSuperMap.get(String.valueOf(dicid));	
						return returnVal;
					}
				}
			}
		}else{
			if (codeValMap.get("00000000") != null) {
				HashMap<String, String> valSuperMap = new HashMap<String, String>();
				valSuperMap = codeValMap.get("00000000");
				if (valSuperMap.get(String.valueOf(dicid)) != null) {
					returnVal = valSuperMap.get(String.valueOf(dicid));	
					return returnVal;
				}
			}
		}
		
		returnVal = queryCodeValInfoWithDicid(centerid, dicid);
		if (CommonUtil.isEmpty(returnVal)) {
			returnVal = queryCodeValInfoWithDicid("00000000", dicid);
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(String.valueOf(dicid), returnVal);
		codeValMap.put(centerid, map);
		
		return returnVal;
	}
	
	/**
	 * 根据报刊版块，Mi701获取页面栏目下拉选择框的级联数据
	 */
	public List<NewspapersTitleInfoBean> getColumnsByForumFromMi701(String centerid,
			String newspaperforum)throws Exception{
		List<NewspapersTitleInfoBean> columnsList = new ArrayList<NewspapersTitleInfoBean>();
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("centerId", centerid);
		paraMap.put("newspaperforum", newspaperforum);
		List<HashMap<String, String>> columnsMapList = cmi701Dao.selectColumnsByForum(paraMap);
		
		for (int i = 0;i <columnsMapList.size(); i++){
			NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
			columnsBean.setItemid(columnsMapList.get(i).get("classification"));
			columnsBean.setItemval(getCodeValWithDicid(centerid, Integer.parseInt(columnsMapList.get(i).get("classification"))));
			columnsList.add(columnsBean);
		}

		// 如果新闻信息表中，对应版块包含栏目为空的记录，则将返回前台的栏目列表追加{"other":"其他"}
		int columnsNullCounts = cmi701Dao.selectColumnsNullCountByForum(paraMap);
		if (0 != columnsNullCounts){
			NewspapersTitleInfoBean columnsBean = new NewspapersTitleInfoBean();
			columnsBean.setItemid("other");
			columnsBean.setItemval("其他");
			columnsList.add(columnsBean);
		}
		
		return columnsList;
	}
	
	// TODO 版块栏目升级
	/**
	 * 查询对应 上级编码和城市中心代码的记录列表,不考虑状态的开放和关闭【主要用于web】
	 * @param pid
	 * @param centerid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List<Mi707> getClassificationData(String pid, String centerid) throws Exception{
		Logger log = LoggerUtil.getLogger();
		// 上传参数空值校验
		if (CommonUtil.isEmpty(pid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("上级编码："+pid));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"上级编码获取失败");
		}
		if (CommonUtil.isEmpty(centerid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码："+centerid));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		
		List<Mi707> mi707List = new ArrayList<Mi707>();
		//查询符合当前中心id的所有上级编码为0的记录
		Mi707Example example = new Mi707Example();
		
		example.createCriteria()
		.andCenteridEqualTo(centerid)
		.andUpdicidEqualTo(Integer.parseInt(pid)).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		example.setOrderByClause("DICID ASC");
		mi707List = mi707Dao.selectByExample(example);
		if (CommonUtil.isEmpty(mi707List)){
			Mi707Example exampleTmp = new Mi707Example();
			exampleTmp.createCriteria()
			.andCenteridEqualTo("00000000")
			.andUpdicidEqualTo(Integer.parseInt(pid)).andValidflagEqualTo(Constants.IS_VALIDFLAG);
			exampleTmp.setOrderByClause("DICID ASC");
			mi707List = mi707Dao.selectByExample(exampleTmp);
		}
		return mi707List;
	}
	
	/**
	 * 查询对应 上级编码和城市中心代码，状态为开放的记录列表【主要用于app】
	 * @param pid
	 * @param centerid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List<Mi707> getClassificationOpenData(String pid, String centerid,String channel) throws Exception{
		Logger log = LoggerUtil.getLogger();
		// 上传参数空值校验
		if (CommonUtil.isEmpty(pid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("上级编码："+pid));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"上级编码获取失败");
		}
		if (CommonUtil.isEmpty(centerid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码："+centerid));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		
		//查询符合当前中心id的所有对应上级编码的记录
		Mi707Example example = new Mi707Example();
		
		example.createCriteria()
		.andCenteridEqualTo(centerid)
		.andUpdicidEqualTo(Integer.parseInt(pid))
		.andFreeuse3Like("%"+channel+"%")
		.andStatusEqualTo(Constants.IS_OPEN)
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		example.setOrderByClause("DICID ASC");
		return mi707Dao.selectByExample(example);
	}
	
	/**
	 * 查询对应 上级编码和城市中心代码，状态为开放的记录的 信息失效日期【主要用于app】
	 * @param pid
	 * @param centerid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getClassificationInvalidDate(String pid, String centerid) throws Exception{
		Logger log = LoggerUtil.getLogger();
		// 上传参数空值校验
		if (CommonUtil.isEmpty(pid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("对应编码："+pid));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"对应编码获取失败");
		}
		if (CommonUtil.isEmpty(centerid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码："+centerid));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		
		if ("0".equals(pid)){
			return "";
		}
		
		//查询符合当前中心id的所有上级编码为0的记录
		Mi707Example example = new Mi707Example();
		
		example.createCriteria()
		.andCenteridEqualTo(centerid)
		.andDicidEqualTo(Integer.parseInt(pid))
		.andStatusEqualTo(Constants.IS_OPEN)
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		example.setOrderByClause("DICID ASC");
		List<Mi707> record = mi707Dao.selectByExample(example);
		if (record.size() != 0){
			if (CommonUtil.isEmpty(record.get(0).getFreeuse1())){
				return getClassificationInvalidDate(record.get(0).getUpdicid().toString(), centerid);
			}else{
				String invalidDateTmp = record.get(0).getFreeuse1();
				// 信息过期日期大于当前系统日期，返回空
				SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);
				if (dateFormatter.parse(invalidDateTmp).after(dateFormatter.parse(CommonUtil.getSystemDate()))){
					invalidDateTmp = "";
				}
				return invalidDateTmp;
			}
		}else{
			return "";
		}
	}
	
	/**
	 * 查询对应 上级编码和城市中心代码的树
	 * @param pid
	 * @param centerid
	 * @return
	 * @throws Exception
	 */
	public JSONArray getClassificationTreeJsonArray(String pid, String centerid) throws Exception{
		JSONArray ary = new JSONArray();
		List<Mi707> list = getClassificationData(pid, centerid) ;
		 Mi707 mi707 = new Mi707();
		 for(int i = 0; i < list.size(); i++){
			 mi707 = list.get(i);
		     JSONObject obj=new JSONObject();
		     obj.put("id", mi707.getItemid());
		     obj.put("text", mi707.getItemval());
		     JSONArray childAry = getClassificationTreeJsonArray(mi707.getDicid().toString(), centerid);
		     if (childAry != null && childAry.size() != 0){
		    	 obj.put("children", childAry);
		     }
		     ary.add(obj);
		 }
		return ary;
	}
	
	/**
	 * Mi707获取版块数据（Mi707配合mi701共同实现）
	 * 根据公共参数centerid和所属上级编码获取当前新闻中实际已发布过新闻的版块/栏目的列表(按编号从小到大升序)
	 */
	public List<NewspapersTitleInfoBean> getClassificFromMi707AndMi701(String updicid, String centerid,String channel)throws Exception{
		List<NewspapersTitleInfoBean> classificList = new ArrayList<NewspapersTitleInfoBean>();
		
		// 获取父级为0的栏目列表(所谓版块)
		List<Mi707> classificAllList = getClassificationOpenData(updicid, centerid,channel);
		HashMap<String, String> paraMap;
		for (int i = 0; i < classificAllList.size(); i++){
			// 获取其对应的信息过期时间（先找本级，本级没有找上级，直到最上级）
			String inValidDate = null;
			Mi707 mi707 = classificAllList.get(i);
			if(CommonUtil.isEmpty(mi707.getFreeuse1())){
				inValidDate = getClassificationInvalidDate(mi707.getUpdicid().toString(), centerid);
			}else {
				inValidDate = mi707.getFreeuse1();
			}
			
			paraMap = new HashMap<String, String>();
			paraMap.put("centerId", centerid);
			paraMap.put("classification", mi707.getItemid());
			paraMap.put("inValidDate", inValidDate);
			paraMap.put("channel", channel);
			int forumCount = cmi701Dao.selectClassificInMi701Count(paraMap);
			if (0 != forumCount){
				NewspapersTitleInfoBean forumBean = new NewspapersTitleInfoBean();
				forumBean.setItemid(mi707.getItemid());
				forumBean.setItemval(mi707.getItemval());
				forumBean.setInValidDate(inValidDate);
				classificList.add(forumBean);
			}
		}
		
		return classificList;
	}
	
	/**
	 * 获取栏目管理中对应编码的名称
	 * @param centerid
	 * @param param
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String getCodeValWithDicidFromMi707(String centerid, int dicid){
		String returnVal = "";
		
		Mi707Example example = new Mi707Example();
		example.createCriteria()
		.andCenteridEqualTo(centerid).andDicidEqualTo(dicid).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi707> list = this.mi707Dao.selectByExample(example);
		if (list.size() != 0) {
			returnVal = list.get(0).getItemval();
		}
		
		return returnVal;
	}
	
	/**
	 * 查询对应城市中心代码下 上级编码不为某值，状态为开放的记录列表【主要用于app】
	 * @param pid
	 * @param centerid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List<Mi707> getClassificNotEqualVal(String pid, String centerid,String channel) throws Exception{
		Logger log = LoggerUtil.getLogger();
		// 上传参数空值校验
		if (CommonUtil.isEmpty(pid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("上级编码："+pid));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"上级编码获取失败");
		}
		if (CommonUtil.isEmpty(centerid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码："+centerid));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		
		//查询符合当前中心id的所有对应上级编码的记录
		Mi707Example example = new Mi707Example();
		
		example.createCriteria()
		.andCenteridEqualTo(centerid)
		.andFreeuse3Like("%"+channel+"%")
		.andUpdicidNotEqualTo(Integer.parseInt(pid))
		.andStatusEqualTo(Constants.IS_OPEN)
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		example.setOrderByClause("DICID ASC");
		return mi707Dao.selectByExample(example);
	}
	
	/**
	 * Mi707获取版块数据（Mi707配合mi701共同实现,Mi707中，父级不等于某值，且在mi701中有对应记录存在的栏目列表）
	 * 根据公共参数centerid和所属上级编码获取当前新闻中实际已发布过新闻的版块/栏目的列表(按编号从小到大升序)
	 */
	public List<NewspapersTitleInfoBean> getClassificNotEqualValFromMi707AndMi701(String updicid, String centerid,String channel)throws Exception{
		List<NewspapersTitleInfoBean> classificList = new ArrayList<NewspapersTitleInfoBean>();
		
		// 获取父级不等于某值的栏目列表
		List<Mi707> classificAllList = getClassificNotEqualVal(updicid, centerid,channel);
		HashMap<String, String> paraMap;
		for (int i = 0; i < classificAllList.size(); i++){
			// 获取其对应的信息过期时间（先找本级，本级没有找上级，直到最上级）
			String inValidDate = null;
			Mi707 mi707 = classificAllList.get(i);
			if(CommonUtil.isEmpty(mi707.getFreeuse1())){
				inValidDate = getClassificationInvalidDate(mi707.getUpdicid().toString(), centerid);
			}else {
				inValidDate = mi707.getFreeuse1();
			}
			
			paraMap = new HashMap<String, String>();
			paraMap.put("centerId", centerid);
			paraMap.put("classification", mi707.getDicid().toString());
			paraMap.put("inValidDate", inValidDate);
			int forumCount = cmi701Dao.selectClassificInMi701Count(paraMap);
			if (0 != forumCount){
				NewspapersTitleInfoBean forumBean = new NewspapersTitleInfoBean();
				forumBean.setItemid(mi707.getDicid().toString());
				forumBean.setItemval(mi707.getItemval());
				forumBean.setInValidDate(inValidDate);
				classificList.add(forumBean);
			}
		}
		
		return classificList;
	}

	//根据中心和设备获取版本号
	public List<Mi106> ptl40013Verno(String centerid,String devtype)throws Exception{
		Logger log = LoggerUtil.getLogger();
		// 传入参数空值校验
		// 中心ID
		if (CommonUtil.isEmpty(centerid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心名称"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"中心名称");
		}
		if (CommonUtil.isEmpty(devtype)) {
			log.error(ERROR.PARAMS_NULL.getLogText("设备为空"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"设备为空");
		}
		Mi106Example example = new Mi106Example();
		example.createCriteria().andCenteridEqualTo(centerid).andDevtypeEqualTo(devtype)
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		List<Mi106> list = mi106Dao.selectByExample(example);
		
		return list;
		
	}
}
