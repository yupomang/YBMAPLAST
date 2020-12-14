package com.yondervision.mi.test;

import net.sf.json.*;
import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;
import sun.security.krb5.internal.crypto.Aes128;

public class WebDataApi
{
    public static String apiServer;
    
    public JSONObject getDataJSONObjectFromUrl(final String url, final String param) {
        final String json = this.getDataStringFromUrl(url, param);
        final JSONObject obj = this.getJsonObject(json);
        return obj;
    }

    public String getDataStringFromUrl( String url, final String param) {
        System.out.println(url);
    	String result = "";
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            final URL u = new URL(url);
             HttpURLConnection conn = (HttpURLConnection)u.openConnection();
   
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            
            if(param!=null){
            	conn.setDoOutput(true);
            	conn.setDoInput(true);
            	conn.setRequestMethod("POST");
            	out = new PrintWriter(conn.getOutputStream());
            	out.print(param);
            	out.flush();
            }else{
            	conn.setRequestMethod("GET");
            }
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GB2312"));
            String line;
            while ((line = in.readLine()) != null) {
                result = String.valueOf(result) + line;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            result = "{error:\"\u83b7\u53d6\u7f51\u7edc\u6570\u636e\u5f02\u5e38 \",url:\"" + url + "\",class:\"WebDataApi\"}";
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }
            catch (IOException e2) {
                result = "{error:\"\u5173\u95edinputRead\u6570\u636e\u5f02\u5e38 \",url:\"" + url + "\",class:\"WebDataApi\"}";
                e2.printStackTrace();
            }
            return result;
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }
            catch (IOException e2) {
                result = "{error:\"\u5173\u95edinputRead\u6570\u636e\u5f02\u5e38 \",url:\"" + url + "\",class:\"WebDataApi\"}";
                e2.printStackTrace();
            }
        }
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
        catch (IOException e2) {
            result = "{error:\"\u5173\u95edinputRead\u6570\u636e\u5f02\u5e38 \",url:\"" + url + "\",class:\"WebDataApi\"}";
            e2.printStackTrace();
        }
        return result;
    }
    
    
    
    //第2种访问接口方式 什么破加密方法脑子有病的接口
    public String getDataStringFromUrl2( String url,String headParam,String headParamMD5,  String postData) {
    	String result = "";
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            final URL u = new URL(url);
             HttpURLConnection conn = (HttpURLConnection)u.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            conn.setUseCaches(false); 
            conn.addRequestProperty("headpara", headParam);
            conn.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(headParamMD5.getBytes()), RSASignature.RSA_PRIVATE));
            
            
            System.out.println(url);
            System.out.println("headpara------------"+headParam);
            System.out.println("headparaMD5---------"+headParamMD5);
            System.out.println("签名数据-------------headParaMDA5-------"+RSASignature.sign(EncryptionByMD5.getMD5(headParamMD5.getBytes()), RSASignature.RSA_PRIVATE));
            System.out.println("post传递数据---------"+postData);
            
            
            if(postData!=null){
            	conn.setDoOutput(true);
            	conn.setDoInput(true);
            	conn.setRequestMethod("POST");
            	out = new PrintWriter(conn.getOutputStream());
            	out.print(postData);
            	out.flush();
            }else{
            	conn.setRequestMethod("GET");
            }
            
            
            conn.connect();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result = String.valueOf(result) + line;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            result = "{error:\"\u83b7\u53d6\u7f51\u7edc\u6570\u636e\u5f02\u5e38 \",url:\"" + url + "\",class:\"WebDataApi\"}";
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }
            catch (IOException e2) {
                result = "{error:\"\u5173\u95edinputRead\u6570\u636e\u5f02\u5e38 \",url:\"" + url + "\",class:\"WebDataApi\"}";
                e2.printStackTrace();
            }
            return result;
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }
            catch (IOException e2) {
                result = "{error:\"\u5173\u95edinputRead\u6570\u636e\u5f02\u5e38 \",url:\"" + url + "\",class:\"WebDataApi\"}";
                e2.printStackTrace();
            }
        }
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
        catch (IOException e2) {
            result = "{error:\"\u5173\u95edinputRead\u6570\u636e\u5f02\u5e38 \",url:\"" + url + "\",class:\"WebDataApi\"}";
            e2.printStackTrace();
        }
        return result;
    }
    
    public JSONObject getJsonObject(String jsonString) {
        if (jsonString.length() == 0) {
            jsonString = "{error:\"\u65e0\u6cd5\u83b7\u53d6\u6570\u636e \",url:\"\",class:\"WebDataApi\"}";
        }
        final JSONObject jsonObject = JSONObject.fromObject((Object)jsonString);
        return jsonObject;
    }
    
    public void setApiServer(final String address) {
        WebDataApi.apiServer = address;
    }
    
    
    
    public static void main(String[] args){
    	WebDataApi api=new WebDataApi();
    	api.setApiServer("http://61.153.144.77:7006/YBMAPZH");
    	//个人密码
    	JSONObject res=api.apiSingleSearch("330227199101094252", "", "111111");
    	//JSONObject res=api.apiSingleSearch("421102198912310460", "", "111111");
    	//JSONObject res=api.apiDaiKuanJingDu("421102198912310460","111111");
    	//JSONObject res=api.apiDaiKuanYuEr("330227199101094252","111111");
    	//JSONObject res=api.apiEditPassword("421102198912310460", "222222", "111111", "0236239239");
    	
    	System.out.println(res);
    	
    }
    
    
    
    public String getCommonHead(String buzType,String userid){
    	String result="";
     	//******************************公共参数
    	result+="centerId=00057400"; //'中心客户代码：00057400
    
    	System.out.println("-----"+userid);
    	result+="&userId="+aesEncode(userid); //各渠道客户标识
    	result+="&usertype=10"; //10-个人用户，20-缴存单位，21-开发商
    	result+="&deviceType=3";//1-iOS,2-Android,3-pc
    	result+="&deviceToken=";//移动设备专用，非移动设备变量名上传，对应值为空
    	result+="&currenVersion=1.0";//对应渠道当前版本号，如果没有默认上传“1.0”
    	result+="&buzType="+buzType;//详细信息参考以下各业务接口列表中的业务类型    	
    	result+="&devtoken=";//这是啥
    	result+="&channel=50";//50-自助终端
    	result+="&appid="+aesEncode("yondervisionselfservice50");//aes    yondervisionselfservice50
    	result+="&appkey="+aesEncode("f53de13df5bf0d043c64467187a23759");//aes   由综合服务管理系统维护，提供各服务渠道应用使用
    	result+="&appToken=";//服务器端同步最新APPTOKEN：使用“应用标识”与“应用KEY”信息到综合服务平台取得最新APPTOKEN，超时需要重新获取
    	String ip=getMyIp();
    	result+="&clientIp="+aesEncode(ip);//
    	//****************************公共参数结束
    	return result;
    }
    
 
    
    
    class SendObj{
    	String url;
    	String headparam;
    	String headParamMd5;
    	
    }
    
    
    
    //aes加密
    public  String aesEncode(String text){
    	String ret="";
		try {
			AesTest aes= new AesTest();
			ret = aes.encrypt(text.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return ret;
    }
    
    
    
    @SuppressWarnings("rawtypes")
    public static String getMyIp() {
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP
        try {
            Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            boolean finded = false;// 是否找到外网IP
            while (netInterfaces.hasMoreElements() && !finded) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                Enumeration address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = (InetAddress) address.nextElement();
                    if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                        netip = ip.getHostAddress();
                        finded = true;
                        break;
                    } else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                        localip = ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        if (netip != null && !"".equals(netip)) {
            return netip;
        } else {
            return localip;
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    //准备写公用的json转map拆解
    public Map<String, String> jsonKeyWordToMap(JSONObject valueObj){
    	Map<String,String> map=new HashMap<String, String>();
    	
    	//如果不存在result字段则直接返回
    	if(!valueObj.containsKey("result")){
    		return map;
    	}
    	
    	
    	JSONArray array=valueObj.getJSONArray("result");
    	Iterator<JSONObject> it=array.iterator();
        while(it.hasNext()){
        	JSONObject obj=(JSONObject)it.next();
        	map.put( obj.getString("name"),obj.getString("info"));
        }
    	return map;
    }
    
    
    /***************************************************各类新api接口***************************/
    //330203198212122417 111111
    //查询个人基本资料
    public JSONObject apiSingleSearch(String idcard,String accnum,String password){
    	String api=apiServer+"/appapi50006.json";
    	String ret="";
    	
    	if(idcard==null || idcard==""){
    		ret=this.getCommonHead("5432",accnum);
    	}else{
    		ret=this.getCommonHead("5432",idcard);
    	}
    	ret+="&bodyCardNumber="+aesEncode(idcard);//身份证
    	ret+="&accnum="+aesEncode(accnum);;//个人账号
    	ret+="&password="+aesEncode(password);//密码
    	
    	String headParam="centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,bodyCardNumber,accnum,password";
    	String headParamMD5=ret;
    	String postData=ret.replaceAll("\\+", "%2B");
    	
    	System.out.println(api);
    	System.out.println(headParamMD5);
    	System.out.println(postData);
    	
    	String s=getDataStringFromUrl2(api, headParam, headParamMD5, postData);
    	
    	JSONObject obj = this.getJsonObject(s);
    	System.out.println(obj.toString());
    	return obj;
    }
    
    
    //贷款进度api userid 系统查询基本信息之后返回的userid值
    public JSONObject apiDaiKuanJingDu(String idcard,String password,String userid){
    	String api=apiServer+"/appapi01101.json";
    	String ret=this.getCommonHead("5445",userid);
    	ret+="&bodyCardNumber="+idcard;//身份证
    	ret+="&pwd="+aesEncode(password);//密码
    	
    	String headParam="centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,bodyCardNumber,pwd";
    	String headParamMD5=ret;
    	String postData=ret.replaceAll("\\+", "%2B");
    	
    	System.out.println(api);
    	System.out.println("headParamMD5--------------------"+headParamMD5);
    	System.out.println("postData------------------"+postData);
    	
    	String s=getDataStringFromUrl2(api, headParam, headParamMD5, postData);
    	JSONObject obj = this.getJsonObject(s);
    	System.out.println(obj.toString());
    	return obj;
    }
    
    
    //贷款余额api
    public JSONObject apiDaiKuanYuEr(String idcard,String password,String userid){
    	String api=apiServer+"/appapi00702.json";
    	String ret=this.getCommonHead("5073",userid);
    	ret+="&bodyCardNumber="+idcard;//身份证
    	ret+="&pwd="+aesEncode(password);//密码
    	
    	String headParam="centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,bodyCardNumber,pwd";
    	String headParamMD5=ret;
    	String postData=ret.replaceAll("\\+", "%2B");
    	
    	System.out.println(api);
    	System.out.println(headParamMD5);
    	System.out.println("postData:="+postData);
    	
    	String s=getDataStringFromUrl2(api+"?"+postData, headParam, headParamMD5, null);
    	JSONObject obj = this.getJsonObject(s);
    	System.out.println(obj.toString());
    	return obj;
    }
    
    
    
  //修改密码api
    public JSONObject apiEditPassword(String idcard,String password,String newPassword,String accnum,String userid){
    	String api=apiServer+"/appapi50009.json";
    	String ret=this.getCommonHead("5448",userid);
    	ret+="&bodyCardNumber="+idcard;//身份证
    	ret+="&password="+aesEncode(password);//旧密码
    	ret+="&newpassword="+aesEncode(newPassword);//新密码
    	ret+="&confirmnewpassword="+aesEncode(newPassword);//新密码
    	ret+="&accnum="+accnum;//个人账号
    	
    	String headParam="centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,bodyCardNumber,password,newpassword,confirmnewpassword,accnum";
    	String headParamMD5=ret;
    	String postData=ret.replaceAll("\\+", "%2B");
    	
    	System.out.println(api);
    	System.out.println(headParamMD5);
    	System.out.println("postData:="+postData);
    	
    	String s=getDataStringFromUrl2(api, headParam, headParamMD5,postData);
    	JSONObject obj = this.getJsonObject(s);
    	return obj;
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
