package com.yondervision.mi.util;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.filter.PermissionEncodingFilter;
import com.yondervision.mi.common.message.SimpleHttpMessageUtil;
import com.yondervision.mi.dao.Mi007DAO;
import com.yondervision.mi.dao.Mi901DAO;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi007Example;
import com.yondervision.mi.dto.Mi901;
import com.yondervision.mi.dto.Mi901Example;
import com.yondervision.mi.dto.Mi007Example.Criteria;
import com.yondervision.mi.util.couchbase.CouchBase;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

public class WkfAccessTokenUtil
{
  public static void writeLog(String log)
  {
    try
    {
      FileWriter LogFileWrite = null;
      LogFileWrite = new FileWriter("/was/workflow/weikefu.log", true);
      LogFileWrite.write(new Timestamp(System.currentTimeMillis()).toString() + " " + log + "\n\r");
      LogFileWrite.close();
    } catch (Exception e) {
      System.out.println(log);
    }
  }
 
  public static void writeLog(Exception log) {
    try {
      FileWriter LogFileWrite = null;
      LogFileWrite = new FileWriter("/was/workflow/weikefu.log", true);
      LogFileWrite.write(new Timestamp(System.currentTimeMillis()).toString() + "\n\r");
      PrintWriter pw = new PrintWriter(LogFileWrite);
      log.printStackTrace(pw);
      LogFileWrite.write("\n\r");
      LogFileWrite.close();
    } catch (Exception e) {
      System.out.println(log);
    }
  } 
  /**
  * 取得微信客URL
  * @param centerId
  * @return
  */
  private static String getQzUrlWithCouchBase(String centerId){
	  writeLog("[+]getQzUrlWithCouchBase"); 
	  if(centerId==null){
			 HttpServletRequest request = 
				      (HttpServletRequest)PermissionEncodingFilter.threadRequestLocal
				      .get();
			 centerId=request.getParameter("centerId");		 
	  }
	  writeLog("[.]getQzUrlWithCouchBase centerId="+centerId); 
	  String url=null;
	  CouchBase cb=CouchBase.getInstance();
	  String key="WKF_url_"+centerId;
	  try {
	    if(cb.get(key)==null){
		  Map hm = getAppSecretAndAppKey(centerId);
		  if (hm != null){ 
			  String qzUrl = (String)hm.get("url");
			  writeLog("hm="+hm);
			  writeLog("qzUrl="+qzUrl);
			  if(qzUrl!=null)
			      cb.save(key, qzUrl, 360); 
			  url=qzUrl;
		  }else{ 
			  writeLog("[.]getQzUrlWithCouchBase 根据中心编码没有找到数据"); 
		  }		 
	    }else{
	    	url= (String)cb.get(key); 
	    }  
	    if("null".equals(url)){
	    	url=null;
	    }
	    if("".equals(url)){
	    	url=null;
	    }
	  } catch (Exception e) {
	      writeLog(e);
	      e.printStackTrace();	      
	  }	  
	  writeLog("[-]getQzUrlWithCouchBase url="+url); 	
	  return url;
  }
  private static String getTokenWithCouchBase(String centerId){
	  if(centerId==null){
		 HttpServletRequest request = 
			      (HttpServletRequest)PermissionEncodingFilter.threadRequestLocal
			      .get();
		 centerId=request.getParameter("centerId");		 
	  }
		  
	  writeLog("[+]getTokenWithCouchBase centerId="+centerId); 
	  CouchBase cb=CouchBase.getInstance();
	  String key="WKF_tooken_"+centerId;
	  String ret=null;
	  if(cb.get(key)==null){
		  Map hm = getAppSecretAndAppKey(centerId);
		  if (hm == null){
			  writeLog("中心编码"+centerId+" 没有在配置文件中配置。");
		      return null;
		  }
		  writeLog("getTokenWithCouchBase hm=" + hm);
		  String qzUrl = (String)hm.get("url");
		  String appKey = (String)hm.get("appKey");
		  String appSecret = (String)hm.get("appSecret");
		  try {
			  SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
			  HashMap hashMap = new HashMap();
			  hashMap.put("appKey", appKey);
			  hashMap.put("appSecret", appSecret);
			  hashMap.put("platform", "ybmapzh");
			  String tokenstr = msm.sendPost(qzUrl+"/accesstoken", hashMap, "UTF-8");
//			  HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
			  
//		      String tokenstr = null;
		      //新接口参数：{"appKey":"XXXX","appSecret":"xxxxxxx","platform":"ios"}
//		      tokenstr = getUrl(qzUrl + "/accesstoken?appKey=" + appKey + "&appSecret=" + appSecret+"&platform=ybmapzh");
//		      tokenstr = getUrl(qzUrl + "/app/accessToken?appKey=" + appKey + "&appSecret=" + appSecret);
		      writeLog(qzUrl + "/accesstoken?appKey=" + appKey + "&appSecret=" + appSecret+"&platform=ybmapzh");
		      JSONObject tokenjson = JSONObject.fromObject(tokenstr);
		      writeLog("获取TOKEN返回SJON数据 ："+tokenstr);
		      int exp = tokenjson.getInt("extdata") - 1000;
		      ret=tokenjson.getString("data");
		      writeLog("key="+key+",ret="+ret+",exp="+exp);
		      cb.save(key, ret, exp); 
		    } catch (Exception e) {
		      writeLog(e);
		      e.printStackTrace();
		      return ret;
		    }
		  
		  
	  }else{
		  ret=(String)cb.get(key);
	  }
	  System.out.println("WKF TOKEN:"+ret);  
	  writeLog("[-]getTokenWithCouchBase ret="+ret);
	  return ret;
  }
  
  //浙江公共数据工作平台
  public static String getPublicDataSecretWithCouchBase(String centerId){
	  if(centerId==null){
		 HttpServletRequest request = 
			      (HttpServletRequest)PermissionEncodingFilter.threadRequestLocal
			      .get();
		 centerId=request.getParameter("centerId");		 
	  }
	  
	  String ret=null;
	  Map hm = getAppSecretAndAppKey(centerId,"configPublicData");
	  if (hm == null){
		  writeLog("中心编码"+centerId+" 没有在配置文件中配置。");
	      return null;
	  }
	  String refreshSecret="pd|refreshSecret|"+centerId;
	  String requestSecret="pd|requestSecret|"+centerId;
	  writeLog("getPublicDataSecretWithCouchBase hm=" + hm);
	  String refreshUrl = String.valueOf(hm.get("url")) + (String)hm.get("refreshUrl");
	  String requestUrl = String.valueOf(hm.get("url")) + (String)hm.get("requestUrl");
	  String appKey = (String)hm.get("appKey");
	  String appSecret = (String)hm.get("appSecret");
	  try {
		  CouchBase cb=CouchBase.getInstance();
		  //判断请求秘钥是否存在
		  if(cb.get(requestSecret)==null){
			  String result = "";
			  SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
			  Date date = new Date();
			  String requestTime = String.valueOf(date.getTime());
			  System.out.println("requestTime:" + requestTime);
			  String url = "";
			  //判断刷新秘钥是否存在
			  if(cb.get(refreshSecret)==null){
				 String sign = EncryptionByMD5.getMD5((appKey.toLowerCase() + appSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
			     url = refreshUrl + "?appKey=" + appKey + "&sign=" + sign + "&requestTime=" + requestTime;
			  }else{
				 String sign = EncryptionByMD5.getMD5((appKey.toLowerCase() + cb.get(refreshSecret).toString().toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
			     url = requestUrl + "?appKey=" + appKey + "&sign=" + sign + "&requestTime=" + requestTime;
			  }
			  result = msm.sendGet(url, "UTF-8");
			  writeLog("获取TOKEN："+url);
			  System.out.println("Refresh Secret："+result);
			  JSONObject tokenjson = JSONObject.fromObject(result);
			  writeLog("[+]getPublicDataSecretWithCouchBase centerId="+centerId);  
			  if("00".equals(tokenjson.getString("code"))){
				 JSONObject datas = tokenjson.getJSONObject("datas");
				 //将刷新秘钥保存到缓存
			     int expRefresh = 3600*47;//48小时过期，提前一小时更新
			     writeLog("key="+refreshSecret+",ret="
			    		 +datas.getString("refreshSecret")+",exp="+expRefresh);
			     cb.save(refreshSecret, datas.getString("refreshSecret"), expRefresh); 
			     //将请求秘钥保存到缓存
			     ret=datas.getString("requestSecret");
			     int expRequest = 600;//15分钟过期，提前5分钟更新
			     writeLog("key="+requestSecret+",ret="
			    		 +ret+",exp="+expRequest);
			     cb.save(requestSecret, ret, expRequest);
			  }
		  }else{
			  ret=(String)cb.get(requestSecret);
		  }
	    } catch (Exception e) {
	      writeLog(e);
	      e.printStackTrace();
	      return ret;
	    }
	  
	  System.out.println("Request Secret:"+ret);  
	  writeLog("[-]getPublicDataSecretWithCouchBase ret="+ret);
	  return ret;
  }
  
  public static Map<String, String> getAppSecretAndAppKey(String centerId,String configname)
  {
    writeLog("[+]getAppSecretAndAppKey centerId=" + centerId);
    Map hm = new HashMap();
    Mi007DAO mi007Dao = (Mi007DAO)SpringContextUtil.getBean("mi007Dao");
    Mi007Example m7e = new Mi007Example();
    m7e.createCriteria()
      .andCenteridEqualTo(centerId).andItemidEqualTo(configname);
    List list = mi007Dao.selectByExample(m7e);
    if (list.size() == 0)
      return hm;
    Integer updicid = ((Mi007)list.get(0)).getDicid();

    m7e = new Mi007Example();
    m7e.createCriteria().andUpdicidEqualTo(updicid);
    List keylist = mi007Dao.selectByExample(m7e);
    for (int i = 0; i < keylist.size(); ++i) {
      hm.put(((Mi007)keylist.get(i)).getItemid(), ((Mi007)keylist.get(i)).getItemval());
    }
    writeLog("[-]getAppSecretAndAppKey hm=" + hm);
    return hm;
  }
  
  //工单取token
  public static String getGDTokenWithCouchBase(String centerId,String from_plat){
	  if(centerId==null){
		 HttpServletRequest request = 
			      (HttpServletRequest)PermissionEncodingFilter.threadRequestLocal
			      .get();
		 centerId=request.getParameter("centerId");		 
	  }
	  
	  String ret=null;
	  Map hm = getAppSecretAndAppKey(centerId,"configgd");
	  if (hm == null){
		  writeLog("中心编码"+centerId+" 没有在配置文件中配置。");
	      return null;
	  }
	  String key="gd|token|"+centerId;
	  writeLog("getGDTokenWithCouchBase hm=" + hm);
	  String qzUrl = (String)hm.get("url");
	  String appKey = (String)hm.get("appKey");
	  String appSecret = (String)hm.get("appSecret");
	  try {
	      String tokenstr = null;
	      //新接口参数：{"appKey":"XXXX","appSecret":"xxxxxxx","platform":"ios"}
	      //tokenstr = getUrl(qzUrl + "/service/tokens?key=" + appKey + "&secret=" + appSecret + "&from_plat=" + from_plat);
		 
		 
		  CouchBase cb=CouchBase.getInstance();
		  
		  
		  if(cb.get(key)==null){
			 SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		     String url = qzUrl + "/service/tokens?key=" + appKey + "&secret=" + appSecret + "&from_plat=" + from_plat;
			 tokenstr = msm.sendGet(url, "UTF-8");
		     writeLog("获取TOKEN："+url);
		     System.out.println("工单获取TOKEN："+tokenstr);
		     JSONObject tokenjson = JSONObject.fromObject(tokenstr);
		      
		     writeLog("[+]getGDTokenWithCouchBase centerId="+centerId);  
			  
			 ret=tokenjson.getString("data");
			 int exp = 3600;
		     writeLog("key="+key+",ret="+ret+",exp="+exp);
		     cb.save(key, ret, exp); 
		  }else{
			  ret=(String)cb.get(key);
		  }
		  //tokenjson.getInt("extdata") - 1000;
	      
	    } catch (Exception e) {
	      writeLog(e);
	      e.printStackTrace();
	      return ret;
	    }
	  
	  System.out.println("WKF TOKEN:"+ret);  
	  writeLog("[-]getGDTokenWithCouchBase ret="+ret);
	  return ret;
  }
  
//微博获取token
public static String getWBTokenWithCouchBase(String centerId){
	if(centerId==null){
		HttpServletRequest request = 
			(HttpServletRequest)PermissionEncodingFilter.threadRequestLocal.get();
		centerId=request.getParameter("centerId");
	}

	String ret=null;
	Map hm = getAppSecretAndAppKey(centerId,"configwb");
	if (hm == null){
		writeLog("中心编码"+centerId+" 没有在配置文件中配置。");
		return null;
	}
	
	String key="wb|token|"+centerId;
	writeLog("getWBTokenWithCouchBase hm=" + hm);
	String qzUrl = (String)hm.get("url");
	String appKey = (String)hm.get("appKey");
	String appSecret = (String)hm.get("appSecret");
	try {
		String tokenstr = null;
		CouchBase cb=CouchBase.getInstance();
		if(cb.get(key)==null){
			SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
			String url = qzUrl + "/accesstoken";
			HashMap propsMap = new HashMap();
			propsMap.put("key", appKey);
			propsMap.put("secret", appSecret);
			tokenstr = msm.sendPost(url, propsMap, "UTF-8");
			writeLog("获取TOKEN："+url);
			System.out.println("微博获取TOKEN："+tokenstr);
			JSONObject tokenjson = JSONObject.fromObject(tokenstr);

			writeLog("[+]getWBTokenWithCouchBase centerId="+centerId);  

			ret=tokenjson.getString("data");
			int exp = 3600;
			if(CommonUtil.isEmpty(tokenjson.getString("extdata"))){
				exp = Integer.valueOf(tokenjson.getString("extdata"));
			}
			writeLog("key="+key+",ret="+ret+",exp="+exp);
			cb.save(key, ret, exp); 
		}else{
			ret=(String)cb.get(key);
		}
	} catch (Exception e) {
		writeLog(e);
		e.printStackTrace();
		return ret;
	}

	System.out.println("WKF TOKEN:"+ret);  
	writeLog("[-]getWBTokenWithCouchBase ret="+ret);
	return ret;
}
  
 /**
 * 取007表中存的对应中心信息
 * @param centerId
 * @return
 */
public static Map<String, String> getAppSecretAndAppKey(String centerId)
  {
    writeLog("[+]getAppSecretAndAppKey centerId=" + centerId);
    Map hm = new HashMap();
    Mi007DAO mi007Dao = (Mi007DAO)SpringContextUtil.getBean("mi007Dao");
    Mi007Example m7e = new Mi007Example();
    m7e.createCriteria()
      .andCenteridEqualTo(centerId).andItemidEqualTo("config");
    List list = mi007Dao.selectByExample(m7e);
    if (list.size() == 0)
      return hm;
    Integer updicid = ((Mi007)list.get(0)).getDicid();

    m7e = new Mi007Example();
    m7e.createCriteria().andUpdicidEqualTo(updicid);
    List keylist = mi007Dao.selectByExample(m7e);
    for (int i = 0; i < keylist.size(); ++i) {
      hm.put(((Mi007)keylist.get(i)).getItemid(), ((Mi007)keylist.get(i)).getItemval());
    }
    writeLog("[-]getAppSecretAndAppKey hm=" + hm);
    return hm;
  }

  private static String getUrl(String strurl) throws Exception {
	System.out.println("strurl:"+strurl);
    URL url = new URL(strurl);
    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
    conn.setConnectTimeout(10000);
    conn.setRequestMethod("GET");
    conn.addRequestProperty("Content-Type", 
      "text/json");
    conn.setRequestProperty("Connection", "Keep-Alive");
    conn.setDoOutput(true);
    conn.setUseCaches(false);
    int len = conn.getContentLength();

    if (len > -1) {
      InputStream inStream = conn.getInputStream();
      byte[] bw = new byte[len];
      inStream.read(bw);
      inStream.close();
      return new String(bw);
    }

    InputStreamReader isr = new InputStreamReader(conn.getInputStream());
    BufferedReader dr = new BufferedReader(isr);
    String line = dr.readLine();
    dr.close();
    return line;
  }

  private static String postUrlByToken(String strurl, String info) throws Exception {
    String accessToken = getTokenWithCouchBase(null);
    if(accessToken==null){
    	throw new Exception("获取token失败，请查看日志"); 
    }
    if (strurl.indexOf("?") == -1)
      strurl = strurl + "?accessToken=" + accessToken;
    else {
      strurl = strurl + "&accessToken=" + accessToken;
    }
    writeLog("-------------------------");
    writeLog("post地址:" + strurl);
    writeLog("上传报文:" + info);

    PostMethod mypost = new PostMethod(strurl);
    mypost.addRequestHeader("Content-Type", "text/json");
    InputStream in = new ByteArrayInputStream(info.getBytes("utf-8"));
    mypost.setRequestBody(in);
    HttpClient httpClient = new HttpClient();
    httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
	httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
    int re_code = httpClient.executeMethod(mypost);
    writeLog("postUrlByToken re_code=" + re_code);
    String repMsg = null;
    if (re_code == 200) {
      repMsg = mypost.getResponseBodyAsString();
    }    	
    if(repMsg!=null){
    	 repMsg = new String(repMsg.getBytes("iso8859-1"), "utf-8");
         writeLog("返回信息:" + repMsg);
        
    }else{
    	 writeLog("返回信息:null" );
    }
    writeLog("-------------------------");
    return repMsg;
  }

  public static JSONObject WKF_getMqttToken()
  {
    
    HttpServletRequest request = 
      (HttpServletRequest)PermissionEncodingFilter.threadRequestLocal
      .get();
    String urlqz = getQzUrlWithCouchBase(null);
    JSONObject retObj = null;
    try {
      String appPlat = "default";
      if (request.getParameter("deviceType") != null) {
        if (request.getParameter("deviceType").equals("1"))
          appPlat = "ios";
        else if (request.getParameter("deviceType").equals("2")) {
          appPlat = "android";
        }
      }
      String userid = "YK" + request.getParameter("deviceToken");
      request.getSession().setAttribute("_WKFTOKEN_USER", userid);
      String info = "{\"name\":\"" + userid + "\",\"nickname\":\"" + getNickName(userid) + "\",\"passwd\":\"123456\"}";
      String ret=postUrlByToken(urlqz + "/user/register", info);
      retObj=JSONObject.fromObject(ret);
	   if(retObj.getInt("errcode")==3018){//当用户存在
		   postUrlByToken( urlqz+"/user/update", info);	
	   }
      ret = postUrlByToken(urlqz + "/user/mqtttoken?platform=" + appPlat, "{appUser:\"" + userid + "\"}");
      retObj = JSONObject.fromObject(ret);
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new TransRuntimeErrorException("299992", new String[] { "调用微客服接口失败1" });
    }
    if (retObj.get("errcode") != null) {
      int errcode = retObj.getInt("errcode");
      if (errcode != 0) {
        throw new TransRuntimeErrorException("299992", new String[] { "调用微客服接口失败 errcode=" + errcode });
      }
    }
    return retObj;
  }

  public static JSONObject WKF_Login(String userid) {
    writeLog("[+]WKF_Login userid=" + userid);
   

    HttpServletRequest request = 
      (HttpServletRequest)PermissionEncodingFilter.threadRequestLocal
      .get();
    String urlqz =getQzUrlWithCouchBase(null);
    if (urlqz == null)
      return null;
    JSONObject retObj = null;
    try {
      String appPlat = "default";
      if (request.getParameter("deviceType") != null) {
        if (request.getParameter("deviceType").equals("1"))
          appPlat = "ios";
        else if (request.getParameter("deviceType").equals("2")) {
          appPlat = "android";
        }
      }
      request.getSession().setAttribute("_WKFTOKEN_USER", userid);
      String info = "{\"name\":\"" + userid + "\",\"nickname\":\"" + userid + "\",\"passwd\":\"123456\"}";
      postUrlByToken(urlqz + "/user/register", info);
      String ret = postUrlByToken(urlqz + "/user/mqtttoken?platform=" + appPlat, "{appUser:\"" + userid + "\"}");
      retObj = JSONObject.fromObject(ret);
    } catch (Exception e) {
      e.printStackTrace();
      throw new TransRuntimeErrorException("299992", new String[] { "调用微客服接口失败2" });
    }
    if (retObj.get("errcode") != null) {
      int errcode = retObj.getInt("errcode");
      if (errcode != 0) {
        throw new TransRuntimeErrorException("299992", new String[] { "调用微客服接口失败 errcode=" + errcode });
      }
    }
    writeLog("[-]WKF_Login");
    return retObj;
  }
 
  public static JSONObject WKF_SendMsg(String data,String cmd)
  {
    writeLog("[+]WKF_SendMsg data=" + data);
    HttpServletRequest request = 
      (HttpServletRequest)PermissionEncodingFilter.threadRequestLocal
      .get();
    String token = getTokenWithCouchBase(null);
    if (token == null)
      throw new TransRuntimeErrorException("299998", new String[] { "获取token失败，请查看日志" });
    String urlqz = getQzUrlWithCouchBase(null);
    String appUser = (String)request.getSession().getAttribute("_WKFTOKEN_USER");
    if(appUser==null){
    	throw new TransRuntimeErrorException("299998", new String[] { "会话超时" });
    }

    String appPlat = "default";
    if (request.getParameter("deviceType") != null) {
      if (request.getParameter("deviceType").equals("1"))
        appPlat = "ios";
      else if (request.getParameter("deviceType").equals("2")) {
        appPlat = "android";
      }
    }
    JSONObject messageBody = new JSONObject();
    messageBody.put("appUser", appUser);
    messageBody.put("appPlat", appPlat);
    if(cmd!=null){ 
    	  messageBody.put("cmd", cmd);
    }
    messageBody.put("data", data);
 

     writeLog("messageBody=" + messageBody.toString());

    String ret = null;
    JSONObject retObj = null;
    try {
      ret = postUrlByToken(urlqz + "/user/sendmsg", messageBody.toString());
      writeLog("ref=" + ret);
      retObj = JSONObject.fromObject(ret);
    } catch (Exception e) {
      e.printStackTrace();
      throw new TransRuntimeErrorException("299992", new String[] { "调用微客服接口失败3" });
    }
    if ((retObj != null) && 
      (retObj.get("errcode") != null)) {
      int errcode = retObj.getInt("errcode");
      if (errcode != 0) {
        throw new TransRuntimeErrorException("299992", new String[] { "调用微客服接口失败 errcode=" + errcode });
      }
    }
    if (retObj != null)
      writeLog("[-]WKF_SendMsg retObj=" + retObj.toString());
    else
      writeLog("[-]WKF_SendMsg retObj");
    return retObj; }

  public static JSONObject WKF_reGetMqtt() {
    writeLog("[+]WKF_reGetMqtt ");
    HttpServletRequest request = 
      (HttpServletRequest)PermissionEncodingFilter.threadRequestLocal
      .get();
    String token = getTokenWithCouchBase(null);
    if (token == null)
      throw new TransRuntimeErrorException("299998", new String[] { "调用微客服接口失败 超时..." });
    String appPlat = "default";
    if (request.getParameter("deviceType") != null) {
      if (request.getParameter("deviceType").equals("1"))
        appPlat = "ios";
      else if (request.getParameter("deviceType").equals("2")) {
        appPlat = "android";
      }
    }
    String urlqz =getQzUrlWithCouchBase(null);
    String appUser = (String)request.getSession().getAttribute("_WKFTOKEN_USER");
    if(appUser==null){
    	throw new TransRuntimeErrorException("299998", new String[] { "会话超时" });
    }
    JSONObject messageBody = new JSONObject();
    String ret = null;
    JSONObject retObj = null;
    try {
      ret = postUrlByToken(urlqz + "/user/mqtttoken?platform=" + appPlat, "{appUser:\"" + appUser + "\"}");
      retObj = JSONObject.fromObject(ret);
    } catch (Exception e) {
      writeLog(e);
    }
    writeLog("[-]WKF_reGetMqtt retObj=" + retObj);
    return retObj;
  }

  public static JSONObject WKF_chatlog(String chatId, int startId, int startPage, int size, int current) {
    writeLog("[+]WKF_chatlog chatId=" + chatId + ",startId=" + startId);
    HttpServletRequest request = 
      (HttpServletRequest)PermissionEncodingFilter.threadRequestLocal
      .get();
    String token = getTokenWithCouchBase(null);
    if (token == null)
      throw new TransRuntimeErrorException("299998", new String[] { "调用微客服接口失败 超时" });
    String urlqz = getQzUrlWithCouchBase(null);
    String appUser = (String)request.getSession().getAttribute("_WKFTOKEN_USER");
    if(appUser==null){
    	throw new TransRuntimeErrorException("299998", new String[] { "会话超时" });
    }

    String appPlat = "default";
    if (request.getParameter("deviceType") != null) {
      if (request.getParameter("deviceType").equals("1"))
        appPlat = "ios";
      else if (request.getParameter("deviceType").equals("2")) {
        appPlat = "android";
      }
    }
    JSONObject messageBody = new JSONObject();
    messageBody.put("startId", Integer.valueOf(startId));
    messageBody.put("startPage", Integer.valueOf(startPage));
    messageBody.put("size", Integer.valueOf(size));
    messageBody.put("current", Integer.valueOf(current));
    messageBody.put("appPlat", appPlat);
    String ret = null;
    JSONObject retObj = null;
    
    try {
      if(chatId==null ){    	  
    	  chatId="";	
      }
      String turl=urlqz + "/user/chatlog?appUser="+appUser+"&chatId=" + chatId; 
      ret = postUrlByToken( turl, messageBody.toString());
      writeLog("ref=" + ret);
      retObj = JSONObject.fromObject(ret);
    } catch (Exception e) {
      e.printStackTrace();
      writeLog(e);
      throw new TransRuntimeErrorException("299992", new String[] { "调用微客服接口失败" });
    }
    if ((retObj != null) && 
      (retObj.get("errcode") != null)) {
      int errcode = retObj.getInt("errcode");
      if (errcode != 0) {
        throw new TransRuntimeErrorException("299992", new String[] { "调用微客服接口失败 errcode=" + errcode });
      }
    }
    if (retObj != null)
      writeLog("[-]WKF_SendMsg retObj=" + retObj.toString());
    else
      writeLog("[-]WKF_SendMsg retObj");
    writeLog("[-]WKF_chatlog");
    return retObj;
  }
  /**
   * 评分    
   * */
  public static JSONObject WKF_closechat(String chatId, int score) {
    HttpServletRequest request = 
      (HttpServletRequest)PermissionEncodingFilter.threadRequestLocal
      .get();
    String token = getTokenWithCouchBase(null);
    if (token == null)
      throw new TransRuntimeErrorException("299998", new String[] { "调用微客服接口失败 超时..." });
    String urlqz = getQzUrlWithCouchBase(null);//
    String appUser = (String)request.getSession().getAttribute("_WKFTOKEN_USER");
    if(appUser==null){
    	throw new TransRuntimeErrorException("299998", new String[] { "会话超时" });
    }
    
    JSONObject messageBody = new JSONObject();
    messageBody.put("score", Integer.valueOf(score));
    messageBody.put("appUser", appUser);
    String ret = null;
    JSONObject retObj = null;
    try {
      ret = postUrlByToken(urlqz + "/user/score?chatId=" + chatId, messageBody.toString());
      retObj = JSONObject.fromObject(ret);
    } catch (Exception e) {
      e.printStackTrace();
      throw new TransRuntimeErrorException("299992", new String[] { "调用微客服接口失败4" });
    }
    if (retObj.get("errcode") != null) {
      int errcode = retObj.getInt("errcode");
      if (errcode != 0) {
        throw new TransRuntimeErrorException("299992", new String[] { "调用微客服接口失败 errcode=" + errcode });
      }
    }
    return retObj;
  }

  public static JSONObject WKF_WeixinPost(String centerId, String strurl, String messageBody) {
    writeLog("[+]WKF_WeixinPost strurl=" + strurl + ",messageBody=" + messageBody);
    
    String qzUrl =getQzUrlWithCouchBase(null);
    
    strurl = qzUrl + strurl;
 
    String accessToken =getTokenWithCouchBase(null);
    if(accessToken==null){
    	throw new TransRuntimeErrorException("299992", new String[] { "获取token失败，请查看日志"  });
    }
    if (strurl.indexOf("?") == -1)
      strurl = strurl + "?accessToken=" + accessToken;
    else {
      strurl = strurl + "&accessToken=" + accessToken;
    }
    PostMethod mypost = new PostMethod(strurl);
    mypost.addRequestHeader("Content-Type", "text/json");
    String repMsg = null;
    try {
      InputStream in = new ByteArrayInputStream(messageBody.getBytes("utf-8"));
      mypost.setRequestBody(in);
      HttpClient httpClient = new HttpClient();
      int re_code = httpClient.executeMethod(mypost);
      writeLog("postUrlByToken re_code=" + re_code);
      if (re_code == 200)
        repMsg = mypost.getResponseBodyAsString();
    }
    catch (Exception e) {
      e.printStackTrace();
      writeLog(e);
      writeLog(e.getMessage());
    }
    try {
      if(repMsg==null) 
    	  repMsg="";
      else
          repMsg = new String(repMsg.getBytes("iso8859-1"), "utf-8");
    } catch (Exception e) {
      writeLog(e);
    }

    writeLog("[-]WKF_WeixinPost repMsg=" + repMsg);

    JSONObject obj= JSONObject.fromObject(repMsg);
    if(obj.getInt("errcode")==3015){
    	writeLog("WKF_WeixinPost token 过期 重新获取");
    	CouchBase cb=CouchBase.getInstance();
    	String key="WKF_url_"+centerId;
    	cb.delete(key);
    	obj=WKF_WeixinPost(  centerId,   strurl,   messageBody);
    }
    return obj;
  }
  private static String getNickName(String deviceToken){
	   writeLog("[+]getNickName deviceToken="+deviceToken);
	   String nickName=null;
	   Mi901DAO mi901Dao = (Mi901DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi901Dao");
	   {
		   Mi901Example m9e=new Mi901Example();	 
		   m9e.createCriteria().andDevicetokenEqualTo(deviceToken);
		   List<Mi901> list=mi901Dao.selectByExample(m9e);
		   if(list.size()>0){ 
			   return list.get(0).getNickname();
		   }
	   }
	   Mi901Example m9e=new Mi901Example();	 
	   m9e.createCriteria().andTrandateEqualTo(Datelet.getCurrentDateString());
	   m9e.setOrderByClause("xh");
	   List<Mi901> list=mi901Dao.selectByExample(m9e);
	   int xh=0;
	   for(int i=0;i<list.size();i++){
		   Mi901 obj=list.get(i); 
		   xh=obj.getXh();
	   }
	   xh++;
	   nickName="手机用户"+String.format("%04d", xh);
	   
	   Mi901 record=new Mi901();
	   record.setXh(xh);
	   record.setNickname(nickName);
	   record.setTrandate(Datelet.getCurrentDateString());
	   record.setDevicetoken(deviceToken);
	   record.setCreatetime(Datelet.getCurrentDateTime());
	   mi901Dao.insert(record);
	   writeLog("[+] getNickName "+nickName);
	   return nickName;
  }
  
  public static JSONObject WKF_upload(String centerId ,String filepath) {
	  writeLog("[+]WKF_upload headMap=" + filepath);
	  String qzUrl =getQzUrlWithCouchBase(null); 
	  String accessToken =getTokenWithCouchBase(centerId);
	  String  strurl = qzUrl + "/upload?accessToken="+accessToken; 
	  writeLog("----------->>>>>>>>>>>>>>>>>strurl="+strurl);
	  Map<String, String> textMap = new HashMap<String, String>();
	  textMap.put("name", "testname");
	  Map<String, String> fileMap = new HashMap<String, String>();
	  fileMap.put("userfile", filepath);
	  String ret =null;
	  try{ 
	     ret=HttpPostUploadUtil.formUpload(strurl, textMap, fileMap);
	  }catch(Exception e){
		  writeLog(e); 
	  }catch(java.lang.Throwable e1){
		  writeLog(e1.getMessage()); 
	  }
	  writeLog("[-]WKF_upload" + ret); 	  
      return JSONObject.fromObject(ret); 
	  
  }
  
  public static String WKF_upload_ZH(String centerId ,String filepath ,String accessToken) {
//	  writeLog("[+]WKF_upload headMap=" + filepath);
	  System.out.println("[+]WKF_upload headMap=" + filepath);
	  String qzUrl =getQzUrlWithCouchBase(centerId);
	  if(CommonUtil.isEmpty(accessToken)){
		  accessToken =getTokenWithCouchBase(centerId);
	  }
	 
//	  String  strurl = qzUrl + "/upload?accessToken="+accessToken; 
	  String  strurl = qzUrl + "/gridfs/chat?accessToken="+accessToken;
//	  writeLog("----------->>>>>>>>>>>>>>>>>strurl="+strurl);
	  System.out.println("微信服图片上传URL----------->>>>>>>>>>>>>>>>>strurl="+strurl);
	  Map<String, String> textMap = new HashMap<String, String>();
	  textMap.put("name", "testname");
	  Map<String, String> fileMap = new HashMap<String, String>();
	  fileMap.put("userfile", filepath);
	  String ret =null;
	  try{ 
	     ret=HttpPostUploadUtil.formUpload(strurl, textMap, fileMap);
	  }catch(Exception e){
		  writeLog(e); 
	  }catch(java.lang.Throwable e1){
		  writeLog(e1.getMessage()); 
	  }
//	  writeLog("[-]WKF_upload" + ret); 	
	  System.out.println("[-]WKF_upload" + ret);
      return ret; 
	  
  }
  
  public static String WKF_upload_Imgs(String urkKey ,String centerId ,String[] filepath ,String accessToken) {
	  writeLog("[+]WKF_upload_Imgs headMap=" + JsonUtil.getGson().toJson(filepath));
	  System.out.println("[+]WKF_upload headMap=" + filepath);
	  String qzUrl =getQzUrlWithCouchBase(centerId);
	  if(CommonUtil.isEmpty(accessToken)){
		  accessToken =getTokenWithCouchBase(centerId);
	  }
	 
	  String  strurl = qzUrl + urkKey +"?accessToken="+accessToken;
	  writeLog("[-]WKF_upload_Imgs >>>>>>>>>>>>>>>>>url="+strurl);
	  System.out.println("微信服"+urkKey+"图片上传URL----------->>>>>>>>>>>>>>>>>strurl="+strurl);
	  Map<String, String> textMap = new HashMap<String, String>();
	  textMap.put("name", "testname");
	  Map<String, String> fileMap = new HashMap<String, String>();
	  for(int i=0;i<filepath.length;i++){
		  System.out.println("微信服"+urkKey+"图片上传----------->>>>>>>>>>>>>>>>>filepath="+filepath[i].toString());
		  fileMap.put("userfile"+i, filepath[i]);
	  }
	  String ret =null;
	  try{ 
	     ret=HttpPostUploadUtil.formUpload(strurl, textMap, fileMap);
	  }catch(Exception e){
		  writeLog(e); 
	  }catch(java.lang.Throwable e1){
		  writeLog(e1.getMessage()); 
	  }
	  writeLog("[-]WKF_upload_Imgs" + ret); 	
	  System.out.println("[-]WKF_upload_Imgs" + ret);
      return ret; 
	  
  }
  
  public static String WKF_GET_TOKEN(String centerId) {
    writeLog("[+]WKF_chatlog centerId=" + centerId);

    String token = getTokenWithCouchBase(centerId);
    if (token == null)
    	throw new TransRuntimeErrorException("299992", new String[] { "调用微客服接口失败 超时" });
   
    return token;
  }
  
  public static String GD_GET_TOKEN(String centerId) {
	    writeLog("[+]WKF_chatlog centerId=" + centerId);

	    String token = getTokenWithCouchBase(centerId);
	    if (token == null)
	    	throw new TransRuntimeErrorException("299992", new String[] { "调用微客服接口失败 超时" });
	   
	    return token;
	  }
  
  public static void deleteTokenWithCouchBase(String centerId){
	  CouchBase cb=CouchBase.getInstance();
	  String key="WKF_tooken_"+centerId;
	  cb.delete(key);
  }
  
  
}