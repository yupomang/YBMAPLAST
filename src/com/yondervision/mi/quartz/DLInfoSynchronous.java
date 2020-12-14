package com.yondervision.mi.quartz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.common.message.SimpleHttpMessageI;
import com.yondervision.mi.dao.CMi701DAO;
import com.yondervision.mi.dao.Mi126DAO;
import com.yondervision.mi.dto.Mi126;
import com.yondervision.mi.dto.Mi126Example;
import com.yondervision.mi.dto.Mi701;
import com.yondervision.mi.dto.Mi701Example;
import com.yondervision.mi.dto.Mi701WithBLOBs;
import com.yondervision.mi.quartz.bean.ScInfoBean;
import com.yondervision.mi.quartz.bean.XgInfoBean;
import com.yondervision.mi.quartz.bean.XzInfoBean;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;


public class DLInfoSynchronous {
	
	private String centerid = "00041100";
	private SimpleDateFormat timestampformatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);

	public CMi701DAO cmi701Dao = null;
	public void setCmi701Dao(CMi701DAO cmi701Dao) {
		this.cmi701Dao = cmi701Dao;
	}
	
	public Mi126DAO mi126Dao = null;
	public void setMi126Dao(Mi126DAO mi126Dao) {
		this.mi126Dao = mi126Dao;
	}

	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	
	public String synHttp() throws Exception{
		String logText = "内容同步通信";
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText(logText));
		log.info("开始--当前系统时间："+timestampformatter.format(new Date()));
		
		// 1.http通信
		String resMsg= "";
		String syntime = getSyntime(centerid, "newsQuarzSyn", "syntime", "内容定时同步");
		String classname = PropertiesReader.getProperty("properties.properties", "simplehttp_classname");
		String url = PropertiesReader.getProperty("properties.properties", "simplehttp_dalian_content_url")+syntime;
		SimpleHttpMessageI messageObj = (SimpleHttpMessageI)com.yondervision.mi.util.SpringContextUtil.getBean(classname);
		resMsg = messageObj.sendGet(url, "UTF-8");
		//resMsg = "{\"code\":\"000000\",\"msg\":\"同步成功\",\"syntime\":\"20150801 18:20:20\",\"xzlist\":[{\"id\":\"1\",\"title\":\"测试新增新闻记录1\",\"fbtime\":\"2015-05-12\",\"content\":\"<p style=\\\"TEXT-JUSTIFY: inter-ideograph; FONT-FAMILY: 'Times New Roman'; FONT-SIZE: 10.5pt\\\">新增记录1</p>\"},{\"id\":\"2\",\"title\":\"测试新增新闻记录2\",\"fbtime\":\"2015-05-13\",\"content\":\"<p style=\\\"TEXT-JUSTIFY: inter-ideograph; FONT-FAMILY: 'Times New Roman'; FONT-SIZE: 10.5pt\\\">新增记录2</p>\"},{\"id\":\"3\",\"title\":\"测试新增新闻记录3\",\"fbtime\":\"2015-05-14\",\"content\":\"<p style=\\\"TEXT-JUSTIFY: inter-ideograph; FONT-FAMILY: 'Times New Roman'; FONT-SIZE: 10.5pt\\\">新增记录3</p>\"}]}";
		//resMsg = "{\"code\":\"000000\",\"msg\":\"同步成功\",\"syntime\":\"20150901 10:30:20\",\"xzlist\":[{\"id\":\"4\",\"title\":\"测试新增新闻记录4\",\"fbtime\":\"2015-05-15\",\"content\":\"<p style=\\\"TEXT-JUSTIFY: inter-ideograph; FONT-FAMILY: 'Times New Roman'; FONT-SIZE: 10.5pt\\\">新增记录4</p>\"},{\"id\":\"5\",\"title\":\"测试新增新闻记录5\",\"fbtime\":\"2015-05-16\",\"content\":\"<p style=\\\"TEXT-JUSTIFY: inter-ideograph; FONT-FAMILY: 'Times New Roman'; FONT-SIZE: 10.5pt\\\">新增记录5</p>\"}],\"xglist\":[{\"id\":\"1\",\"title\":\"修改新闻记录1\",\"xgtime\":\"2015-05-17\",\"content\":\"<p style=\\\"TEXT-JUSTIFY: inter-ideograph; FONT-FAMILY: 'Times New Roman'; FONT-SIZE: 10.5pt\\\">修改记录1</p>\"},{\"id\":\"2\",\"title\":\"修改新闻记录2\",\"xgtime\":\"2015-05-18\",\"content\":\"<p style=\\\"TEXT-JUSTIFY: inter-ideograph; FONT-FAMILY: 'Times New Roman'; FONT-SIZE: 10.5pt\\\">修改记录2</p>\"}],\"sclist\":[{\"id\":\"3\",\"sctime\":\"2015-05-19\"}]}";
		log.info("结束--当前系统时间："+timestampformatter.format(new Date()));
		log.info(LOG.END_BUSIN.getLogText(logText));
		return resMsg;
	}
	
	@Transactional(noRollbackFor=NoRollRuntimeErrorException.class)
	public void synDataDeal(String resMsg) throws JSONException, Exception{
		String logText = "内容同步通信数据处理";
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText(logText));
		
		// 2.处理http通信结果
		System.out.println(resMsg);
		String recode = null, msg = null, syntimeReturn = "";
		List<XzInfoBean> xzList = new ArrayList<XzInfoBean>();
		List<XgInfoBean> xgList = new ArrayList<XgInfoBean>();
		List<ScInfoBean> scList = new ArrayList<ScInfoBean>();
		JSONObject response = new JSONObject(resMsg);
		if (response.has("code")) {
			recode = response.getString("code");
			if (response.has("msg")) {
				msg = response.getString("msg");
			}
			if (response.has("syntime")) {
				syntimeReturn = response.getString("syntime");
			}
			if ("000000".equals(recode)) {
				if (response.has("xzlist")) {
					// 创建一个JsonParser
					JsonParser parser = new JsonParser();
					// 通过JsonParser对象可以把json格式的字符串解析成一个JsonElement对象
					JsonElement el = parser.parse(response.getString("xzlist"));
					JsonArray ja = el.getAsJsonArray();
					Gson gson = new GsonBuilder().create();
					Iterator<JsonElement> it = ja.iterator();
					while (it.hasNext()) {
						JsonElement je = (JsonElement) it.next();
						XzInfoBean bean = gson.fromJson(je, XzInfoBean.class);
						xzList.add(bean);
					}
				}
				if (response.has("xglist")) {
					// 创建一个JsonParser
					JsonParser parser = new JsonParser();
					// 通过JsonParser对象可以把json格式的字符串解析成一个JsonElement对象
					JsonElement el = parser.parse(response.getString("xglist"));
					JsonArray ja = el.getAsJsonArray();
					Gson gson = new GsonBuilder().create();
					Iterator<JsonElement> it = ja.iterator();
					while (it.hasNext()) {
						JsonElement je = (JsonElement) it.next();
						XgInfoBean bean = gson.fromJson(je, XgInfoBean.class);
						xgList.add(bean);
					}
				}
				if (response.has("sclist")) {
					// 创建一个JsonParser
					JsonParser parser = new JsonParser();
					// 通过JsonParser对象可以把json格式的字符串解析成一个JsonElement对象
					JsonElement el = parser.parse(response.getString("sclist"));
					JsonArray ja = el.getAsJsonArray();
					Gson gson = new GsonBuilder().create();
					Iterator<JsonElement> it = ja.iterator();
					while (it.hasNext()) {
						JsonElement je = (JsonElement) it.next();
						ScInfoBean bean = gson.fromJson(je, ScInfoBean.class);
						scList.add(bean);
					}
				}
				// 操作数据库
				if(!CommonUtil.isEmpty(xzList)){
					insertInfo(xzList, log);
				}
				if(!CommonUtil.isEmpty(xgList)){
					modInfo(xgList, log);
				}
				if(!CommonUtil.isEmpty(scList)){
					delInfo(scList, log);
				}
				updSyntime(centerid, "newsQuarzSyn", "syntime", syntimeReturn);
			} else {
				log.info(LOG.SELF_LOG.getLogText("内容同步处理失败"));
				log.info(LOG.REV_INFO.getLogText(resMsg));
				log.info(LOG.SELF_LOG.getLogText("返回状态码："+recode));
				log.info(LOG.SELF_LOG.getLogText("返回状态描述:"+msg));
				TransRuntimeErrorException tre = new TransRuntimeErrorException(
						WEB_ALERT.SELF_ERR.getValue(), msg);
				throw tre;
			}
		} else {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SELF_ERR.getValue(), "返回无code，失败");
			throw tre;
		}
		log.info(LOG.END_BUSIN.getLogText(logText));
	}
	    
	@SuppressWarnings("unchecked")
	private void insertInfo(List<XzInfoBean> xzList, Logger log) throws Exception{
		int seqnoLength = xzList.size();
		// 校验seqnos的采号
		Integer minSeqno = commonUtil.genKeyAndIncrease("MI701", seqnoLength).intValue();
		
		if (CommonUtil.isEmpty(minSeqno)) {
			log.error(ERROR.NULL_KEY.getLogText("新闻动态MI701"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.NULL_KEY.getLogText("新闻动态MI701"));
		}
		
		for (int i = 0; i < seqnoLength; i++){
			// 先进行对应同步的id的查找，如果有，则更新
			Mi701Example qryExample = new Mi701Example();
			qryExample.createCriteria()
			.andFreeuse6EqualTo(xzList.get(i).getId())
			//.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andCenteridEqualTo(centerid);
			List<Mi701> qryList = cmi701Dao.selectByExampleWithoutBLOBs(qryExample);
			if(!CommonUtil.isEmpty(qryList)){
				Mi701WithBLOBs record = new Mi701WithBLOBs();
				record.setTitle(xzList.get(i).getTitle());
				// TODO  关于摘要，要过滤content中的html标签，然后截取
				String contextHtml = xzList.get(i).getContent();
				String context = removeHtmlTag(contextHtml);
				if (context.length() <= 30) {
					record.setIntroduction(context);
				}else{
					record.setIntroduction(context.substring(0, 30)+"...");
				}
				// TODO 注意content中是否需要对一些特殊字符做转译
				record.setContent(contextHtml);
				record.setReleasetime(xzList.get(i).getFbtime());
				record.setDatemodified(xzList.get(i).getFbtime());
				record.setLoginid("auto");

				record.setSeqno(qryList.get(0).getSeqno());
				cmi701Dao.updateByPrimaryKeySelective(record);
			}else{
				Mi701WithBLOBs record = new Mi701WithBLOBs();
				record.setSeqno(minSeqno);
				record.setCenterid(centerid);
				// TODO 从properties中配置“新闻动态”，到mi705中获取码值,下方185行也需要对应
				record.setClassification("1");
				record.setTitle(xzList.get(i).getTitle());
				// TODO  关于摘要，要过滤content中的html标签，然后截取
				String contextHtml = xzList.get(i).getContent();
				String context = removeHtmlTag(contextHtml);
				if (context.length() <= 30) {
					record.setIntroduction(context);
				}else{
					record.setIntroduction(context.substring(0, 30)+"...");
				}
				// TODO 注意content中是否需要对一些特殊字符做转译
				record.setContent(contextHtml);
				// TODO 注意同步发布日期格式，看是否需要进行格式化
				//record.setReleasetime(form.getReleasetime() +" " + dateFormatter.format(new Date()).substring(11));
				record.setReleasetime(xzList.get(i).getFbtime());
				record.setImage("");
				record.setValidflag(Constants.IS_VALIDFLAG);
				record.setDatecreated(xzList.get(i).getFbtime());
				record.setLoginid("auto");
				String defaultImgUrl = PropertiesReader.getProperty("properties.properties", "infoType_"+centerid+"_1");
				record.setFreeuse1(defaultImgUrl);
				record.setPraisecounts(0);
				record.setFreeuse3(Constants.PUBLISH_FLG_ONE);// 发布标记
				record.setFreeuse5(Constants.CONTENT_SOURCE_PLAT_AUTO);// 内容管理的数据来源
				record.setFreeuse6(xzList.get(i).getId());//同步的数据的内容的id
				cmi701Dao.insert(record);
			}
			minSeqno = minSeqno + 1;
		}

	}
	
	@SuppressWarnings("unchecked")
	private void modInfo(List<XgInfoBean> xgList, Logger log){
		List<Mi701> qryList = new ArrayList<Mi701>();
		Mi701Example qryExample = new Mi701Example();
		
		for(int i = 0; i < xgList.size(); i++){
			qryExample = new Mi701Example();
			qryExample.createCriteria()
			.andFreeuse6EqualTo(xgList.get(i).getId())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andCenteridEqualTo(centerid);
			qryList = cmi701Dao.selectByExampleWithoutBLOBs(qryExample);
			if(!CommonUtil.isEmpty(qryList)){
				Mi701WithBLOBs record = new Mi701WithBLOBs();
				record.setTitle(xgList.get(i).getTitle());
				// TODO  关于摘要，要过滤content中的html标签，然后截取
				String contextHtml = xgList.get(i).getContent();
				String context = removeHtmlTag(contextHtml);
				if (context.length() <= 30) {
					record.setIntroduction(context);
				}else{
					record.setIntroduction(context.substring(0, 30)+"...");
				}
				// TODO 注意content中是否需要对一些特殊字符做转译
				record.setContent(contextHtml);
				// 修改时间
				record.setDatemodified(xgList.get(i).getXgtime());
				record.setLoginid("auto");

				record.setSeqno(qryList.get(0).getSeqno());
				cmi701Dao.updateByPrimaryKeySelective(record);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void delInfo(List<ScInfoBean> scList, Logger log){
		List<Mi701> qryList = new ArrayList<Mi701>();
		Mi701Example qryExample = new Mi701Example();
		
		for(int i = 0; i < scList.size(); i++){
			qryExample.createCriteria()
			.andFreeuse6EqualTo(scList.get(i).getId())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andCenteridEqualTo(centerid);
			qryList = cmi701Dao.selectByExampleWithoutBLOBs(qryExample);
			if(!CommonUtil.isEmpty(qryList)){
				Mi701WithBLOBs record = new Mi701WithBLOBs();
				// 修改时间
				record.setDatemodified(scList.get(i).getSctime());
				// 删除标记
				record.setValidflag(Constants.IS_NOT_VALIDFLAG);
				// 删除者
				record.setLoginid("auto");
				
				record.setSeqno(qryList.get(0).getSeqno());
				cmi701Dao.updateByPrimaryKeySelective(record);
			}
		}
	}
	
	/**
	* 通过递归删除html标签     
	* @param content - 包含HTML标签的内容  
	* @return 不带HTML标签的文本内容     
	*/
	private String removeHtmlTag(String content) {
		Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>");
		Matcher m = p.matcher(content);
		if (m.find()) { 
			content = content.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>", "$2");
			content = removeHtmlTag(content);
		} 
		return content; 
	}
	
	/**
	 * 根据城市中心ID,功能名称,以及参数，获取对应的值
	 * @param centerid
	 * @param func
	 * @param params
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private String getSyntime(String centerid, String func, String paramsKey,
			String funcDescrip) throws Exception{
		String paraValue = "20120101 01:01:01";
		List<Mi126> qryList = new ArrayList<Mi126>();
		Mi126Example qryExample = new Mi126Example();
		
		qryExample.createCriteria()
		.andCenteridEqualTo(centerid)
		.andFuncEqualTo(func)
		.andParamkeyEqualTo(paramsKey)
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);

		qryList = mi126Dao.selectByExample(qryExample);
		if(!CommonUtil.isEmpty(qryList)){
			paraValue = qryList.get(0).getParamvalue();
		}else{
			String seqno = commonUtil.genKey("MI126").toString();
			
			Mi126 mi126 = new Mi126();
			mi126.setSeqno(Integer.parseInt(seqno));
			mi126.setCenterid(centerid);
			mi126.setFunc(func);
			mi126.setParamkey(paramsKey);
			mi126.setParamvalue(paraValue);
			mi126.setValidflag(Constants.IS_VALIDFLAG);
			mi126.setDatecreated(CommonUtil.getSystemDate());
			mi126.setFreeuse1(funcDescrip);
			mi126Dao.insert(mi126);
		}

		return paraValue;
	}
	
	/**
	 * 根据城市中心ID,功能名称,以及参数，更新对应的值
	 * @param centerid
	 * @param func
	 * @param params
	 * @throws Exception 
	 */
	private void updSyntime(String centerid, String func, String paramsKey, String paramVal) throws Exception{
		Mi126Example example = new Mi126Example();
		
		example.createCriteria()
		.andCenteridEqualTo(centerid)
		.andFuncEqualTo(func)
		.andParamkeyEqualTo(paramsKey)
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);

		Mi126 record = new Mi126();
		record.setParamvalue(paramVal);
		record.setDatemodified(CommonUtil.getSystemDate());
		
		mi126Dao.updateByExampleSelective(record, example);
	}
}
