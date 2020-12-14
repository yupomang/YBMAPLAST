package com.yondervision.mi.common;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.beans.factory.annotation.Autowired;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.dao.Mi040DAO;
import com.yondervision.mi.dao.Mi041DAO;
import com.yondervision.mi.dao.Mi042DAO;
import com.yondervision.mi.dao.impl.CMi008DAOImpl;
import com.yondervision.mi.dto.Mi040;
import com.yondervision.mi.dto.Mi041;
import com.yondervision.mi.dto.Mi041Example;
import com.yondervision.mi.dto.Mi042;
import com.yondervision.mi.dto.Mi042Example;
import com.yondervision.mi.form.AppApi50004Form;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JsonUtil;
import com.yondervision.mi.util.MessageSendUtil;
import com.yondervision.mi.util.SpringContextUtil;
import com.yondervision.mi.util.couchbase.CouchBase;

public class HeartBeatCheck {
	
	@Autowired
	private CommonUtil commonUtil;
	public CommonUtil getCommonUtil() {
		return commonUtil;
	}
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}

//	private static CommonUtil commonUtil = null;
//	static{
//		commonUtil = (CommonUtil)com.yondervision.mi.util.SpringContextUtil.getBean("commonUtil");
//	}
	@SuppressWarnings("rawtypes")
	public void heartCheack() throws Exception{
		Mi041DAO mi041DAO = (Mi041DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi041DAO");
		Mi041Example m041e=new Mi041Example();
		com.yondervision.mi.dto.Mi041Example.Criteria ca= m041e.createCriteria();
		//validflag 有效标志 0-无效 1有效
		//validflag设置为1有效
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi041> list = mi041DAO.selectByExample(m041e); 
		String resMsg = "";
		System.out.println("start heartCheack 心跳检查开始");
		for(int i=0;i<list.size();i++){
			Mi041 mi041 = list.get(i);
			Mi042 mi042 = new Mi042();
			
			//monitor监控开关1-打开0-关闭	
			if("1".equals(mi041.getMonitor().trim())){
				GetMethod myget = new GetMethod(mi041.getCheckurl().trim());
//				PostMethod mypost = new PostMethod(mi041.getCheckurl().trim());
//				mypost.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				myget.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				HttpClient httpClient = new HttpClient();		
//				String connectTimeout = PropertiesReader.getProperty(
//						Constants.PROPERTIES_FILE_NAME, "http_connection_time").trim();
//				String readTimeout = PropertiesReader.getProperty(
//						Constants.PROPERTIES_FILE_NAME, "http_read_time").trim();
				httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(20000);
				httpClient.getHttpConnectionManager().getParams().setSoTimeout(20000);
				int re_code=0;
				resMsg = "";
				String openFlag="open";
				try {
					re_code = httpClient.executeMethod(myget);
					System.out.println("心跳检查执行了访问心跳地址");
					if (re_code == 200) {
						System.out.println("心跳检查执行了访问心跳地址,返回200");
						resMsg = myget.getResponseBodyAsString();
//						resMsg = mypost.getResponseBodyAsString();
						//为了兼容12329渠道的[{"msg":"成功","recode":"000000"}] add by Carter King 20180408
						Map map = JsonUtil.getGson().fromJson(resMsg.replace("[", "").replace("]", ""), Map.class);
						if(!"000000".equals(map.get("recode"))){
							mi042.setOpen("0");
							mi042.setCause("通信异常"+map.get("recode")+","+map.get("message"));
							System.out.println("mi041.getFreeuse1()============="+mi041.getFreeuse1());
							System.out.println("mi041.getValidflag()============="+mi041.getValidflag());
							if (mi041.getFreeuse1()=="0"&&mi041.getValidflag()=="1") {
								System.out.println("prepare sending message 准备发短信");
								AppApi50004Form form = new AppApi50004Form();
								form.setTel("15161178395");
								form.setJgh("11");
								form.setMessage(mi041.getChannel()+"渠道心跳地址访问不通，请检查原因");
								MessageSendUtil.sendSmsCheckAndMessage00057400(form);
								
								AppApi50004Form form1 = new AppApi50004Form();
								form1.setTel("17606849858");
								form1.setJgh("11");
								form1.setMessage(mi041.getChannel()+"渠道心跳地址访问不通，请检查原因");
								MessageSendUtil.sendSmsCheckAndMessage00057400(form1);
								 
								AppApi50004Form form2 = new AppApi50004Form();
								form2.setTel("13039003683");
								form2.setJgh("11");
								form2.setMessage(mi041.getChannel()+ "渠道心跳地址访问不通，请检查原因");
								MessageSendUtil.sendSmsCheckAndMessage00057400(form2);
								
								
								System.out.println("短信发送完毕");
								CMi008DAOImpl cmi008Dao = (CMi008DAOImpl)SpringContextUtil.getBean("cmi008Dao");
								Connection conn=cmi008Dao.getDataSource().getConnection();
								String sql = "update mi041 set freeuse1=1 where channel="+mi041.getChannel();
								PreparedStatement pstm = conn.prepareStatement(sql) ;
								ResultSet rs = pstm.executeQuery() ;
								 rs.close();
								 pstm.close() ;
								 conn.close();
							}
						}else{
							mi042.setOpen("1");
							if(mi041.getFreeuse1()=="1"){
								CMi008DAOImpl cmi008Dao = (CMi008DAOImpl)SpringContextUtil.getBean("cmi008Dao");
								Connection conn=cmi008Dao.getDataSource().getConnection();
								String sql = "update mi041 set freeuse1=0 where channel="+mi041.getChannel();
								PreparedStatement pstm = conn.prepareStatement(sql) ;
								ResultSet rs = pstm.executeQuery() ;
								 rs.close();
								 pstm.close() ;
								 conn.close();
							}

						}
						System.out.println((LOG.REV_INFO.getLogText(resMsg)));
						
					} else {
						System.out.println("心跳检查执行了访问心跳地址,没有返回200");
						mi042.setOpen("0");
						mi042.setCause("通信异常"+re_code);	
						System.out.println("mi041.getFreeuse1()============="+mi041.getFreeuse1());
						System.out.println("mi041.getValidflag()============="+mi041.getValidflag());
						if (mi041.getFreeuse1()=="0"&&mi041.getValidflag()=="1") {
							System.out.println("prepare sending message 准备发短信");
							AppApi50004Form form = new AppApi50004Form();
							form.setTel("15161178395");
							form.setJgh("11");
							form.setMessage(mi041.getChannel()+"渠道心跳地址访问不通，请检查原因");
							MessageSendUtil.sendSmsCheckAndMessage00057400(form);
							
							AppApi50004Form form1 = new AppApi50004Form();
							form1.setTel("17606849858");
							form1.setJgh("11");
							form1.setMessage(mi041.getChannel()+"渠道心跳地址访问不通，请检查原因");
							MessageSendUtil.sendSmsCheckAndMessage00057400(form1);
							 
							AppApi50004Form form2 = new AppApi50004Form();
							form2.setTel("13039003683");
							form2.setJgh("11");
							form2.setMessage(mi041.getChannel()+ "渠道心跳地址访问不通，请检查原因");
							MessageSendUtil.sendSmsCheckAndMessage00057400(form2);
							
							
							System.out.println("短信发送完毕");
							CMi008DAOImpl cmi008Dao = (CMi008DAOImpl)SpringContextUtil.getBean("cmi008Dao");
							Connection conn=cmi008Dao.getDataSource().getConnection();
							String sql = "update mi041 set freeuse1=1 where channel="+mi041.getChannel();
							PreparedStatement pstm = conn.prepareStatement(sql) ;
							ResultSet rs = pstm.executeQuery() ;
							 rs.close();
							 pstm.close() ;
							 conn.close();
						}
						
						
//						throw new TransRuntimeErrorException(ERROR.CONNECT_SEND_ERROR.getValue(),"http错误码" + re_code);
					}
				} catch (HttpException e) {
					e.printStackTrace();
					System.out.println("心跳检查异常HttpException");
					openFlag = "close";
					mi042.setOpen("0");
					mi042.setCause("通信异常HttpException");
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("心跳检查异常IOException");
					openFlag = "close";
					mi042.setOpen("0");
					mi042.setCause("通信异常IOException");
				}catch (Exception e) {
					e.printStackTrace();
					System.out.println("未知异常，请联系管理员");
					openFlag = "close";
					mi042.setOpen("0");
					mi042.setCause("未知异常，请联系管理员");
				}finally{
					CouchBase cb=CouchBase.getInstance();
					HeartBeat heartbeat = new HeartBeat();
					heartbeat.setPid(mi041.getPid());
					heartbeat.setChannel(mi041.getChannel());
					heartbeat.setCenterid(mi041.getCenterid());
					heartbeat.setMonitor("1".equals(mi042.getOpen())?"open":"close");
					heartbeat.setCheckurl(mi041.getCheckurl());
					heartbeat.setOpen(openFlag);
					
					Mi040DAO mi040DAO = (Mi040DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi040DAO");
					Mi040 mi040 = mi040DAO.selectByPrimaryKey(mi041.getPid());
					if(!CommonUtil.isEmpty(mi040)){
						heartbeat.setPidname(mi040.getAppname());
					}
					String key = mi041.getCenterid()+"|"+mi041.getChannel()+"|"+mi041.getPid()+"|HeartBeat";
					cb.save(key, heartbeat , 20000);			
					
					Mi042DAO mi042DAO = (Mi042DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi042DAO");
					Mi042Example m042e=new Mi042Example();
					com.yondervision.mi.dto.Mi042Example.Criteria ca042= m042e.createCriteria();
					ca042.andCenteridEqualTo(mi041.getCenterid()).andChannelEqualTo(mi041.getChannel())
					.andPidEqualTo(mi041.getPid()).andOpenEqualTo("0").andRecoveryEqualTo("0");
					
//					open:心跳标志1-开通0-关闭
//					recovery:恢复状态1-已恢复0-未恢复
					List<Mi042> list042 = mi042DAO.selectByExample(m042e);
					if(CommonUtil.isEmpty(list042)){
						if("0".equals(mi042.getOpen())){
							mi042.setId(commonUtil.genKeyStatic("MI042", 20));
							mi042.setCenterid(mi041.getCenterid());
							mi042.setChannel(mi041.getChannel());
							mi042.setPid(mi041.getPid());
							mi042.setRecovery("0");						
							mi042.setValidflag("1");
							mi042.setDatecreated(CommonUtil.getSystemDate());
							mi042.setDatemodified(CommonUtil.getSystemDate());
							mi042DAO.insert(mi042);
						}
					}else{
						if("1".equals(mi042.getOpen())){
							mi042 = list042.get(0);
							mi042.setRecovery("1");
							mi042.setRecoverydate(CommonUtil.getSystemDate());
							mi042.setDatemodified(CommonUtil.getSystemDate());
							SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
							Date date1 = formatter.parse(CommonUtil.getSystemDate());
							Date date2 = formatter.parse(mi042.getDatecreated());
							long diff = (date1.getTime() - date2.getTime())/1000;
							mi042.setInterval(String.valueOf(diff));
							mi042DAO.updateByPrimaryKeySelective(mi042);
						}
					}				
				}
			}
		}
		
	}
}
