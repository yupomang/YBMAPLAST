/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.util
 * 文件名：     CommonUtil.java
 * 创建日期：2013-9-29
 */
package com.yondervision.mi.util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ibatis.sqlmap.client.SqlMapSession;
import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.dao.CMi008DAO;
import com.yondervision.mi.dao.impl.CMi008DAOImpl;
import com.yondervision.mi.dao.impl.Mi008DAOImpl;
import com.yondervision.mi.dto.Mi008;
import com.yondervision.mi.dto.Mi008Example;
import com.yondervision.mi.dto.Mi008Example.Criteria;
import com.yondervision.mi.service.impl.SqlApi001ServiceImpl;

/**
 * 公共方法类
 * 
 * @author LinXiaolong
 * 
 */
public class CommonUtil {
	
	@Autowired
	private CMi008DAO cmi008Dao;
	
	/**
	 * @param cmi008Dao the cmi008Dao to set
	 */
	public void setCmi008Dao(CMi008DAO cmi008Dao) {
		this.cmi008Dao = cmi008Dao;
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param obj
	 *            要判断的对象
	 * @return true:对象为空；false:对象不为空
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(Object obj) {
		if (obj instanceof String) {
			String temp = (String) obj;
			return temp == null || temp.equals("");
		}
		if (obj instanceof Object[]) {
			Object[] temp = (Object[]) obj;
			return temp == null || temp.length == 0;
		}
		if (obj instanceof List) {
			List temp = (List) obj;
			return temp == null || temp.size() == 0;
		}
		if (obj instanceof Map) {
			Map temp = (Map) obj;
			return temp == null || temp.size() == 0;
		}
		return obj == null;
	}

	/**
	 * 根据在工程中的相对路径取得绝对路径
	 * 
	 * @param pURL
	 *            在工程中的相对路径
	 * @return 系统中的绝对路径
	 * @throws IOException 
	 */
	public static String getFullURL(String pURL) throws IOException {
		pURL = pURL.replace("/", File.separator).replace("\\\\", File.separator);
		pURL = pURL.startsWith(File.separator) ? pURL : File.separator + pURL;
		String fullURL = SpringContextUtil.getApplicationContext().getResource(pURL).getFile().getAbsolutePath();
		
		return fullURL.endsWith(File.separator) ? fullURL : fullURL+File.separator;
	}
	
	/**
	 * 取得当前系统时间，格式为yyyy-MM-dd HH:mm:ss.SSS
	 * @return 当前系统时间，格式为yyyy-MM-dd HH:mm:ss.SSS
	 */
	public static String getSystemDate() {
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		return formatter.format(new Date());
	}
	
	/**
	 * 取得当前系统时间，格式为yyyyMMddHHmmssSSS
	 * @return 当前系统时间，格式为yyyyMMddHHmmssSSS
	 */
	public static String getSystemDateNumOnly() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return formatter.format(new Date());
	}
	
	/**
	 * 将格式为yyyy-MM-dd HH:mm:ss.SSS的字符串转换为格式为yyyy-MM-dd HH:mm:ss字符串
	 * @param systemDate 系统时间，格式为yyyy-MM-dd HH:mm:ss.SSS
	 * @return APP时间，格式为yyyy-MM-dd HH:mm:ss
	 * @throws ParseException
	 */
	public static String getAppDate(String systemDate) throws ParseException{
		SimpleDateFormat sysFormatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		SimpleDateFormat appFormatter = new SimpleDateFormat(Constants.APP_DATE_TIME_FORMAT);
		return appFormatter.format(sysFormatter.parse(systemDate));
	}
	
	/**
	 * 取得当前系统日期，格式为yyyy-MM-dd
	 * @return 当前系统日期，格式为yyyy-MM-dd
	 */
	public static String getDate() {
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);
		return formatter.format(new Date());
	}
	
	/**
	 * 取得当前系统时间，格式为HH:mm:ss
	 * @return 当前系统时间，格式为HH:mm:ss
	 */
	public static String getTime() {
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.TIME_FORMAT);
		return formatter.format(new Date());
	}
	
	/**
	 * 取得当前系统时间，格式为HH
	 * @return 当前系统时间，格式为HH:mm:ss
	 */
	public static String getHour() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH");
		return formatter.format(new Date());
	}
	 
	/**
	 * 生成一个递增序号--静态方法
	 * 
	 * @param key
	 *            种子	 *            
	 * @return 递增的序号
	 * @throws Exception 
	 */	
	public static Long genKeyStatic(String key) throws Exception{
		return genKeyAndIncreaseStatic(key, 1);
	    /*Mi008DAO   mi008Dao=	(Mi008DAO)SpringContextUtil.getBean("mi008Dao");	 
		Mi008Example m8e=new Mi008Example();
		Criteria ca= m8e.createCriteria();
		ca.andSyskeyEqualTo(key); 
		List<Mi008> list=mi008Dao.selectByExample(m8e);
		 
		if(list.size()==0){	
			Mi008 record =new Mi008();
			record.setSyskey(key);
			record.setSysseq(new Long(1));
			record.setModitime(Datelet.getCurrentDateTime());
			mi008Dao.insert(record);
			return new Long(1);
		}else{ 
			Mi008 record=list.get(0);
			Mi008 recordTmp = new Mi008();
			recordTmp.setSyskey(record.getSyskey());
			recordTmp.setSysseq(record.getSysseq()+1);
			recordTmp.setModitime(Datelet.getCurrentDateTime());
			mi008Dao.updateByExample(recordTmp, m8e);
			return new Long(record.getSysseq()+1);
		}*/
	}
	
	public static String genKeyStatic(String key , int num) throws Exception{
		int n = genKeyAndIncreaseStatic(key, 1).intValue();
		
		StringBuffer value = new StringBuffer();		
		for(int i=String.valueOf(n).length();i<num;i++){
			value.append("0");
		}
		value.append(n);
		return value.toString();
		
	}
	/**
	 * 采号
	 * @param key 采号操作的表
	 * 此方法比原来新增了 事物管理的操作，原来静态采号方法，压力测试环境锁不住表
	 * @param basic 采号步长
	 * @return 
	 * @throws Exception 
	 * @throws Exception
	 */
	public synchronized static Long genKeyAndIncreaseStatic(String key, int basic) throws Exception{
		System.out.println("采号步长："+basic);
//		Long ret=null;
//		CMi008DAOImpl cmi008Dao = (CMi008DAOImpl)SpringContextUtil.getBean("cmi008Dao");
//		SqlMapSession session = null;
//	    try
//	    {
//	      session = cmi008Dao.getSqlMapClient().getSession();
//	      session.startTransaction();
//	      session.getCurrentConnection().setAutoCommit(false);
//
//	      Mi008Example m8e = new Mi008Example();
//	      Mi008Example.Criteria ca = m8e.createCriteria();
//	      ca.andSyskeyEqualTo(key);
//	      Mi008 list = (Mi008)session.queryForObject("CMI008.abatorgenerated_selectByExample", m8e);
//	      if (list == null) {
//	        Mi008 record = new Mi008();
//	        record.setSyskey(key);
//	        record.setSysseq(new Long(basic));
//	        record.setModitime(Datelet.getCurrentDateTime());
//
//	        ret = Long.valueOf(basic);
//	        session.insert("MI008.abatorgenerated_insert", record);
//	        session.getCurrentConnection().setAutoCommit(true);
//	      } else {
//	        Mi008 record = list;
//	        Mi008 recordTmp = new Mi008();
//	        recordTmp.setSyskey(record.getSyskey());
//	        recordTmp.setSysseq(Long.valueOf(record.getSysseq().longValue() + basic));
//	        recordTmp.setModitime(Datelet.getCurrentDateTime());
//
//	        session.update("CMI008.abatorgenerated_updateBySyskey", recordTmp);
//	        session.getCurrentConnection().setAutoCommit(true);
//	        ret = Long.valueOf(recordTmp.getSysseq().longValue());
//	      }
//	    } catch (Exception e) {
//	      e.printStackTrace();
//	      
//	    } finally {
//	      if (session != null) {
//	        session.commitTransaction();
//	        session.endTransaction();
//	        session.close();
//	      }
//
//	    }
		
		Long ret=null;
		CMi008DAOImpl cmi008Dao = (CMi008DAOImpl)SpringContextUtil.getBean("cmi008Dao");
		Connection conn = null;
		PreparedStatement prepdStatement = null;
		ResultSet rs = null;
		String syskey = "";
		Long sysseq = null;
		String moditime = "";
	    try
	    {
			  conn=cmi008Dao.getDataSource().getConnection();
			  conn.setAutoCommit(false);
			  //DB2 : for update with RS
			  //ORACLE : for update
			  String sql = "select SYSKEY, SYSSEQ, MODITIME from MI008 where SYSKEY=? for update ";
			  
			  prepdStatement = conn.prepareStatement(sql);
			  prepdStatement.setString(1, key);
			  rs = prepdStatement.executeQuery();
			  int count = 0;
			  while (rs.next()) {
					count++;
					syskey = rs.getString("SYSKEY");
					sysseq = rs.getLong("SYSSEQ");
					moditime = rs.getString("MODITIME");
				}
			  
			  if (count == 0) {
				  String insertMi008 = "insert into MI008 (SYSKEY, SYSSEQ, MODITIME) values (?, ?, ?)";
				  prepdStatement = conn.prepareStatement(insertMi008);
				  prepdStatement.setString(1, key);
				  prepdStatement.setLong(2, new Long(basic));
				  prepdStatement.setString(3, Datelet.getCurrentDateTime());
				  prepdStatement.executeUpdate();
			     
			      ret = Long.valueOf(basic);
			     
			  } else {
				  String updateMi008 = " update MI008 set SYSSEQ = ?,MODITIME = ? where SYSKEY = ?";
				  prepdStatement = conn.prepareStatement(updateMi008);				  
				  prepdStatement.setLong(1, Long.valueOf(sysseq + basic));
				  prepdStatement.setString(2, Datelet.getCurrentDateTime());
				  prepdStatement.setString(3, key);
				  
				  prepdStatement.executeUpdate();
			   
			    ret = Long.valueOf(sysseq + basic);
			  }
	    } catch (Exception e) {
	      e.printStackTrace();
	      
	    } finally {
	    	try {
				if(conn!=null)
				{
					conn.commit();
					conn.setAutoCommit(true);
				}
				if(prepdStatement!=null){
					prepdStatement.close();
				}
				if(conn!=null)
					conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}

	    }
		
		return ret-basic+1;
	}
	
	/**
	 * 生成一个递增序号
	 * 
	 * @param key
	 *            种子	 *            
	 * @return 递增的序号
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor=NoRollRuntimeErrorException.class)
	public Long genKey(String key) throws Exception{
		return genKeyAndIncrease(key, 1);
	    /*Mi008DAO   mi008Dao=	(Mi008DAO)SpringContextUtil.getBean("mi008Dao");	 
		Mi008Example m8e=new Mi008Example();
		Criteria ca= m8e.createCriteria();
		ca.andSyskeyEqualTo(key); 
		List<Mi008> list=mi008Dao.selectByExample(m8e);
		 
		if(list.size()==0){	
			Mi008 record =new Mi008();
			record.setSyskey(key);
			record.setSysseq(new Long(1));
			record.setModitime(Datelet.getCurrentDateTime());
			mi008Dao.insert(record);
			return new Long(1);
		}else{ 
			Mi008 record=list.get(0);
			Mi008 recordTmp = new Mi008();
			recordTmp.setSyskey(record.getSyskey());
			recordTmp.setSysseq(record.getSysseq()+1);
			recordTmp.setModitime(Datelet.getCurrentDateTime());
			mi008Dao.updateByExample(recordTmp, m8e);
			return new Long(record.getSysseq()+1);
		}*/
	}
	
	public static Long genKeyAndCommit(String key) throws Exception{
		Mi008DAOImpl   mi008Dao=	(Mi008DAOImpl)SpringContextUtil.getBean("mi008Dao");	 
		mi008Dao.getSqlMapClient().startTransaction();
		Mi008Example m8e=new Mi008Example();
		Criteria ca= m8e.createCriteria();
		ca.andSyskeyEqualTo(key); 
		List<Mi008> list=mi008Dao.selectByExample(m8e);
		long ret=0;
		if(list.size()==0){	
			Mi008 record =new Mi008();
			record.setSyskey(key);
			record.setSysseq(new Long(1));
			record.setModitime(Datelet.getCurrentDateTime());
			mi008Dao.insert(record);
			ret= Long.valueOf("1");
		}else{ 
			Mi008 record=list.get(0);
			Mi008 recordTmp = new Mi008();
			recordTmp.setSyskey(record.getSyskey());
			recordTmp.setSysseq(record.getSysseq()+1);
			recordTmp.setModitime(Datelet.getCurrentDateTime());
			mi008Dao.updateByExample(recordTmp, m8e);
			ret= Long.valueOf(record.getSysseq()+1);
		} 		
		mi008Dao.getSqlMapClient().commitTransaction();
		mi008Dao.getSqlMapClient().endTransaction();
		return ret;
	}
	
	/**
	 * 采号
	 * @param key 采号操作的表
	 * @param basic 采号步长
	 * @return 
	 * @throws Exception 
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor=NoRollRuntimeErrorException.class)
	public synchronized Long genKeyAndIncrease(String key, int basic) throws Exception{
		System.out.println("采号步长："+basic);
		Long ret=null;
		Mi008Example m8e = new Mi008Example();
		Criteria ca = m8e.createCriteria();
		ca.andSyskeyEqualTo(key);
		List<Mi008> list = cmi008Dao.selectByExampleForUpdate(m8e);
		if(list.size()==0){
			Mi008 record = new Mi008();
			record.setSyskey(key);
			record.setSysseq(new Long(basic));
			record.setModitime(Datelet.getCurrentDateTime());
			cmi008Dao.insert(record);
			ret = Long.valueOf(basic);
		}else{ 
			Mi008 record = list.get(0);
			Mi008 recordTmp = new Mi008();
			recordTmp.setSyskey(record.getSyskey());
			recordTmp.setSysseq(record.getSysseq()+basic);
			recordTmp.setModitime(Datelet.getCurrentDateTime());
			cmi008Dao.updateByExample(recordTmp, m8e);
			ret = Long.valueOf(recordTmp.getSysseq());
		}

		return ret-basic+1;
	}
	
	/**
	 * 生成一个递增序号，按照要求在前面补0
	 * 
	 * @param key
	 *            种子	 
	 * @param ws
	 *            要求返回的位数要求          
	 * @return 递增的序号
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor=NoRollRuntimeErrorException.class)
	public String genKey(String key,int ws) throws Exception{
		Long seq=genKey(key);
		int tmplen=(seq+"").length();
		StringBuffer zero=new StringBuffer();
		for(int i=0;i<ws-tmplen;i++){
			zero.append("0");
		}
		return zero.append(seq).toString();
	}
	
	public static String genKeyAndCommit(String key,int ws) throws Exception{
		Long seq=genKeyAndCommit(key);
		int tmplen=(seq+"").length();
		StringBuffer zero=new StringBuffer();
		for(int i=0;i<ws-tmplen;i++){
			zero.append("0");
		}
		return zero.toString()+seq;
	}
	
	/**
	 * 记录参数日志时使用，将JAVABEAN转为String。
	 * @param javaBean 参数的form对象
	 * @return 可输出的参数日志
	 */
	public static String getStringParams(Object javaBean) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(javaBean, Map.class).toString();
	}
	
	/**
	 * 将JAVABEAN转为JSON格式的String。
	 * @param javaBean 参数的form对象
	 * @return JSON格式的String
	 */
	@SuppressWarnings("unchecked")
	public static String getJSONStringParams(Object javaBean) {
		ObjectMapper mapper = new ObjectMapper();
		if (javaBean instanceof List || javaBean instanceof Object[]) {
			return mapper.convertValue(javaBean, JSONArray.class).toString();
		}
		return mapper.convertValue(javaBean, JSONObject.class).toString();
	}
	
	/**
	 * 取得文件的下载路径
	 * @param filePathParam 文件路径在配置文件中的名称
	 * @param fileName 文件名
	 * @param isFullUrl 是否是全路径（true=全路径；false=工程的相对路径）
	 * @return 文件的下载地址
	 * @throws IOException 
	 */
	public static String getDownloadFileUrl(String filePathParam, String fileName, boolean isFullUrl) throws IOException{
		String action = "downloadimg.file";
		StringBuffer sbUrl = new StringBuffer();
		sbUrl.append(action);
		sbUrl.append("?filePathParam=");
		sbUrl.append(filePathParam);
		sbUrl.append("&fileName=");
		sbUrl.append(fileName);
		sbUrl.append("&isFullUrl=");
		sbUrl.append(isFullUrl);
		return sbUrl.toString();
	}
	
	/**
	 * 取得文件的下载路径
	 * @param filePathParam 文件路径在配置文件中的名称
	 * @param fileName 文件名
	 * @param isFullUrl 是否是全路径（true=全路径；false=工程的相对路径）
	 * @return 文件的下载地址
	 * @throws IOException 
	 */
	public static String getDownloadFileUrlNew(String action, String filePathParam, String fileName, boolean isFullUrl) throws IOException{
		StringBuffer sbUrl = new StringBuffer();
		sbUrl.append(action);
		sbUrl.append("?filePathParam=");
		sbUrl.append(filePathParam);
		sbUrl.append("&fileName=");
		sbUrl.append(fileName);
		sbUrl.append("&isFullUrl=");
		sbUrl.append(isFullUrl);
		return sbUrl.toString();
	}
	
	/**
	 * 取得文件的绝对路径
	 * @param filePathParam 文件路径在配置文件中的名称
	 * @param fileName 文件名
	 * @param isFullUrl 是否是全路径（true=全路径；false=工程的相对路径）
	 * @return 文件的绝对路径
	 * @throws IOException 
	 */
	public static String getFileFullPath(String filePathParam, String fileName, boolean isFullUrl) throws IOException{
		String filePath = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, filePathParam);
		if (!isFullUrl) {
			filePath = getFullURL(filePath);
		} else {
			filePath = filePath.replace("/", File.separator).replace("\\\\", File.separator);
			filePath = filePath.endsWith(File.separator) ? filePath : filePath+File.separator;
		}
		return filePath+fileName;
	}
	
	/**
	 * 根据sql和翻页信息得到符合jqery-easyui datagrid格式的json
	 * @param sql
	 * @param rows 每页的记录数
	 * @param page 当前页号
	 * @return json对象
	 * @throws Exception 
	 */
	public static JSONObject selectByPage(String sql,Integer page,Integer rows){	 
		if(rows==null)
			rows=new Integer(10);
		if(page==null)
			page=new Integer(0);
		try{
		    SqlApi001ServiceImpl   sqlapi=	(SqlApi001ServiceImpl)SpringContextUtil.getBean("SqlApiService");
		    JSONObject obj=sqlapi.selectByPage(sql,page ,rows);	 
		    System.out.println("hhhhobj.toString()========"+obj.toString());
		    return obj;
		}catch(Exception e){
			e.printStackTrace();	
			throw new com.yondervision.mi.common.exp.TransRuntimeErrorException("999999","请查看日志");
		}
	}
	

	/**
	 * 传入日期和当天日期进行比较
	 * 
	 * @param inputDate
	 * @return
	 */
	public static boolean compareCurentDate(String inputDate) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(
				Constants.DATE_FORMAT_YYYY_MM_DD);
		String currentDate = dateFormatter.format(new Date());
		String currentBegin = currentDate + " 00:00:00.000";
		String currentEnd = currentDate + " 23:59:59.999";
		if (inputDate.compareTo(currentBegin) >= 0
				&& inputDate.compareTo(currentEnd) <= 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 传入日期和昨日日期进行比较
	 * 
	 * @param inputDate
	 * @return
	 */
	public static boolean compareYesterDayDate(String inputDate) {
		String yesterdayDate = getYesterdayDate();
		String inputTmp = inputDate.substring(0, 10);
		if (inputTmp.compareTo(yesterdayDate) == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 传入日期与其是否满足当前日期七天内的比较
	 * 
	 * @param inputDate
	 * @return
	 */
	public static boolean compareCurentSevenDayDate(String inputDate) {
		String inputDateTmp = inputDate.substring(0, 10);
		String currentDate = getCurrentDate();
		String sevenDate = getSevendayDate();
		if (inputDateTmp.compareTo(sevenDate) >= 0
				&& inputDateTmp.compareTo(currentDate) <= 0) {
			return true;
		}
		return false;
	}

	// 获取昨天的日期
	private static String getYesterdayDate(){
		Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date d=cal.getTime();

        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        return sp.format(d);

	}
	
	// 获取当前的日期
	private static String getCurrentDate(){
		Date date = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		return dateformat.format(date);

	}
	
	// 获取七天前的日期
	private static String getSevendayDate(){
		Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-7);
        Date d=cal.getTime();

        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        return sp.format(d);

	}
	
	//  实现给定某日期，判断是星期几 
    public static String getWeekday(String date) throws Exception{
    	SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd"); 
    	SimpleDateFormat sdw = new SimpleDateFormat("E", Locale.CHINA); 
    	Date d = null; 
    	d = sd.parse(date); 
    	return sdw.format(d); 
    } 

	// 获取15天前的日期
    public static String getFifteendayDate(){
		Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-15);
        Date d=cal.getTime();

        SimpleDateFormat sp = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        return sp.format(d);

	}
    
    /** 
    * 获得指定日期的前n天 
    * @param specifiedDay 
    * @return 
    * @throws Exception 
    */ 
    public static String getSpecifiedDayBefore(String specifiedDay, int n){ 
	    //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	    Calendar c = Calendar.getInstance(); 
	    Date date=null; 
	    try { 
	    	date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay); 
	    } catch (ParseException e) { 
	    	e.printStackTrace(); 
	    } 
	    c.setTime(date); 
	    int day=c.get(Calendar.DATE); 
	    c.set(Calendar.DATE,day-n); 
	
	    String dayBefore=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
	    return dayBefore; 
    } 
    
    /**
	 * 手机号脱敏处理
	 * @param tel
	 * @return
	 */
	public static String getDesensitizationTel(String tel){
		StringBuffer value = new StringBuffer();
		int size = tel.length();
		value.append(tel.substring(0, 3));
		for(int i=0;i<(size-7);i++){
			value.append("*");
		}
		value.append(tel.substring(size-4));
		return value.toString();
	}
	
	/**
	 * 银行卡号脱敏处理
	 * @param bank
	 * @return
	 */
	public static String getDesensitizationBank(String bank){
		StringBuffer value = new StringBuffer();
		int size = bank.length();
		value.append(bank.substring(0, 4));
		for(int i=0;i<(size-8);i++){
			value.append("*");
		}
		value.append(bank.substring(size-4));
		return value.toString();
	}
	
	/**
	 * 证件号码脱敏处理
	 * @param certinum
	 * @return
	 */
	public static String getDesensitizationCertinum(String certinum){
		StringBuffer value = new StringBuffer();
		int size = certinum.length();
		value.append(certinum.substring(0, 3));
		for(int i=0;i<(size-7);i++){
			value.append("*");
		}
		value.append(certinum.substring(size-4));
		return value.toString();
	}
	
	/**
	 * 个人公积金账号脱敏处理
	 * @param accnum
	 * @return
	 */
	public static String getDesensitizationAccnum(String accnum){
		StringBuffer value = new StringBuffer();
		int size = accnum.length();
		value.append(accnum.substring(0, 3));
		for(int i=0;i<(size-7);i++){
			value.append("*");
		}
		value.append(accnum.substring(size-4));
		return value.toString();
	}
	
	/**
	 * 个人客户号脱敏处理
	 * @param custid
	 * @return
	 */
	public static String getDesensitizationCustid(String custid){
		StringBuffer value = new StringBuffer();
		int size = custid.length();
		value.append(custid.substring(0, 3));
		for(int i=0;i<(size-6);i++){
			value.append("*");
		}
		value.append(custid.substring(size-3));
		return value.toString();
	}
	
	/**
	 * 单位公积金账号脱敏处理
	 * @param unitaccnum
	 * @return
	 */
	public static String getDesensitizationUnitaccnum(String unitaccnum){
		StringBuffer value = new StringBuffer();
		int size = unitaccnum.length();
		value.append(unitaccnum.substring(0, 3));
		for(int i=0;i<(size-7);i++){
			value.append("*");
		}
		value.append(unitaccnum.substring(size-4));
		return value.toString();
	}
	
	/**
	 * 单位客户号脱敏处理
	 * @param unitcustid
	 * @return
	 */
	public static String getDesensitizationUnitcustid(String unitcustid){
		StringBuffer value = new StringBuffer();
		int size = unitcustid.length();
		value.append(unitcustid.substring(0, 3));
		for(int i=0;i<(size-6);i++){
			value.append("*");
		}
		value.append(unitcustid.substring(size-3));
		return value.toString();
	}
	
	/**
	 * 借款合同号脱敏处理
	 * @param unitcustid
	 * @return
	 */
	public static String getDesensitizationLoancontrnum(String loancontrnum){
		StringBuffer value = new StringBuffer();
		int size = loancontrnum.length();
		value.append(loancontrnum.substring(0, 2));
		for(int i=0;i<(size-6);i++){
			value.append("*");
		}
		value.append(loancontrnum.substring(size-4));
		return value.toString();
	}
	
	/**
	 * 贷款账号脱敏处理
	 * @param lnaccnum
	 * @return
	 */
	public static String getDesensitizationLnaccnum(String lnaccnum){
		StringBuffer value = new StringBuffer();
		int size = lnaccnum.length();
		value.append(lnaccnum.substring(0, 6));
		for(int i=0;i<(size-10);i++){
			value.append("*");
		}
		value.append(lnaccnum.substring(size-4));
		return value.toString();
	}
	
	public static int getSplitDian(double num){
		String[] values = (String.valueOf(num)).split("\\.");
		return Integer.parseInt(values[0]);
	}
	
	public static String getSplitDianString(double num){
		String[] values = (String.valueOf(num)).split("\\.");
		return String.valueOf(values[0]);
	}
    
	/**
	 * 根据脱敏规则进行脱敏处理
	 */
	public static String getDesensitization(String sourceStr, String desensiChar, 
			int firstNum, int tailNum){
		if(sourceStr.length() > firstNum+tailNum){
			String result = sourceStr.substring(0, firstNum);
			int len = sourceStr.length()-firstNum-tailNum;
			for(int i=0; i<len; i++){
				result = result + desensiChar;
			}
			return result + sourceStr.substring(sourceStr.length()-tailNum);
		}else{
			return sourceStr;
		}
	}
	
	public static String bSubstring(String s, int length) throws Exception
    {

        byte[] bytes = s.getBytes("Unicode");
        int n = 0; // 表示当前的字节数
        int i = 2; // 要截取的字节数，从第3个字节开始
        for (; i < bytes.length && n < length; i++)
        {
            // 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
            if (i % 2 == 1)
            {
                n++; // 在UCS2第二个字节时n加1
            }
            else
            {
                // 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
                if (bytes[i] != 0)
                {
                    n++;
                }
            }
        }
        // 如果i为奇数时，处理成偶数
        if (i % 2 == 1)

        {
            // 该UCS2字符是汉字时，去掉这个截一半的汉字
            if (bytes[i - 1] != 0)
                i = i - 1;
            // 该UCS2字符是字母或数字，则保留该字符
            else
                i = i + 1;
        }

        return new String(bytes, 0, i, "Unicode");
    }
	
	/**
	 * HashMap合并，key相同，值相加
	 * @param hm1
	 * @param hm2
	 * @return
	 */
	public static HashMap mergeHashMap(HashMap hm1,HashMap hm2)
	{
		Iterator iter = hm1.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			if (hm2.containsKey(key)) {
				int map099 = Integer.parseInt((String)hm2.get(key));
				int map107 = Integer.parseInt((String)hm1.get(key));
				hm2.put(key, (map099+map107)+"");
			}else {
				hm2.put(key,val);
			}
		}
		return hm2;
	}
	
	/**
	 * 判断是否在日期内
	 * @param time
	 * @param from
	 * @param to
	 * @return
	 * @throws ParseException 
	 */
	 public static boolean belongCalendar(Date time, String from, String to,String format) throws ParseException {
		 SimpleDateFormat formatter = new SimpleDateFormat(format);
	        Calendar date = Calendar.getInstance();
	        date.setTime(time);

	        Calendar after = Calendar.getInstance();
	        after.setTime(formatter.parse(to));
	        after.add(Calendar.DAY_OF_MONTH, 1);

	        Calendar before = Calendar.getInstance();
	        before.setTime(formatter.parse(from));
	        
	        if (date.getTimeInMillis()>=before.getTimeInMillis() && date.getTimeInMillis()<=after.getTimeInMillis()) {
	            return true;
	        } else {
	            return false;
	        }
    }
	 
	 /**
		 * @deprecated 获取请求字符集
		 * @param request
		 * @param response
		 * @return
		 */
		public static String getEncoding(HttpServletRequest request, HttpServletResponse response){
			String encoding = "";
			if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
				encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}
			return encoding;
		}
		
		
		/**
		* 判断是否进行base64加密
		* @param str
		* @return
		*/
		public static boolean checkBase64(String str){
		if(str.length()%4!=0){
		return false;
		}
		char[] charArray = str.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
		if(charArray[i]>='A' && charArray[i]<='Z'){
		continue;
		}
		if(charArray[i]>='a' && charArray[i]<='z'){
		continue;
		}
		if(charArray[i]>='0' && charArray[i]<='9'){
		continue;
		}
		if(charArray[i]=='+' || charArray[i]=='\\' || charArray[i]=='='){
		continue;
		}
		return false;
		}
		return true;
		}
		
		public static List<Date> findDates(Date dBegin, Date dEnd)
		 {
		  List<Date> lDate = new ArrayList<Date>();
		  lDate.add(dBegin);
		  Calendar calBegin = Calendar.getInstance();
		  // 使用给定的 Date 设置此 Calendar 的时间
		  calBegin.setTime(dBegin);
		  Calendar calEnd = Calendar.getInstance();
		  // 使用给定的 Date 设置此 Calendar 的时间
		  calEnd.setTime(dEnd);
		  // 测试此日期是否在指定日期之后
		  while (dEnd.after(calBegin.getTime()))
		  {
		   // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
		   calBegin.add(Calendar.DAY_OF_MONTH, 1);
		   lDate.add(calBegin.getTime());
		  }
		  return lDate;
		 }
}
