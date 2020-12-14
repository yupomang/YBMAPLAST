package com.yondervision.mi.test.wangting1qi;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//import static jdk.nashorn.internal.runtime.GlobalFunctions.encodeURI;


//单位停缴缓缴上传文件查询
public class HttpSendappapi00235 {
	//白天只能连接生产环境，晚上才能连接测试环境，并且测试完，需要couchbase删除缓存
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	//public static final String POST_URL = "http://172.16.10.96:7001/YBMAPZH/";
	//private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	//private static final String appId_V = "yondervisionwebservice40";
	//private static final String clientIp_V="172.10.0.1";
	
	public static String httpURLConnectionPOST6010(

		    String json	) {
		try {
			URL url = new URL(POST_URL + "appapi00235.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			/*connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId ="330726198810170330";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}
*/
		 	String Para = "json="+json ;
			 //String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			//String parm ="fullPara="+fullPara+"&centerId=00057400&channel=40";
			// 用于发送http报文
			//String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=40";
			//connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
			connection.connect();
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
			dataout.writeBytes(Para);
			dataout.flush();
			dataout.close();
			BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = bf.readLine()) != null) {
				sb.append(line).append(System.getProperty("line.separator"));
			} 
			bf.close();
			connection.disconnect();
			//zT66VxgteWdr3gkjpu+kdoEhHM25tK7OHKv7mSTu4Ay+/zqe8a5XF4D6I/IT37ZQwnLmKeWzmCIL
			//NPUZqu8Rn9tGfCCnHoAqUAaot45/v7VFc+ckLIFqB6ojMJuT+zr4
			//System.out.println(sb.toString());
			//String result=aes.decrypt(sb.toString());
			System.out.println(sb.toString());
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) throws UnknownHostException, ParseException {
for(int i=1;i<2;i++) {
	long starTime=System.currentTimeMillis();


	try {
		String name=java.net.URLEncoder.encode("联系人", "UTF-8");
		System.out.println(name);
		name=java.net.URLEncoder.encode(name,"UTF-8");
		System.out.println(name);
		name = "%22%3A%22%E8%81%94%E7%B3%BB%E4%BA%BA%22%2C%22";
		name=java.net.URLDecoder.decode(name, "UTF-8");
		System.out.println(name);
		name=java.net.URLDecoder.decode(name, "UTF-8");
		System.out.println(name);
		DateFormat formatter1 = new SimpleDateFormat("yyyyMM");
		String date2="1590940800000" ;
		long time = Long.valueOf(date2);
		String date1 = formatter1.format(time);
		System.out.println(date1);

		/*String json = "{\"unitlinkman\":\"" + URLEncoder.encode(URLEncoder.encode("王琰慧", "UTF-8"),"UTF-8") + "\",\"unioncode\":\"113300000024821119\",\"flag\":\"4\"," +
				"\"qdapprnum\":\"233580db-a6be-45d3-b372-af2e36ce7ad6\",\"unitaccnum\":\"\"," +
				"\"certinum\":\"330211197801231023\",\"unitaccname\":\""+URLEncoder.encode("测试单位", "UTF-8")+"\",\"transdate\":" +
				"\"\",\"instanceid\":\"\",\"servicecode\":\"\",\"unitaccaddr\":\""+URLEncoder.encode("浙江杭州市区省府大楼", "UTF-8")+"\"," +
				"\"phone\":\"\",\"unitlinkphone\":\"13211112222\",\"ispflag\":\"1\",\"basenum\":\"\",\"servicename\":\"\"," +
				"\"begpym\":\"\",\"certitype\":\"01\",\"accname\":\""+URLEncoder.encode("测试", "UTF-8")+"\",\"endpym\":\"1585670400000\"}";*/
		String json = "{\"unitlinkman\":\"王琰慧\",\"unioncode\":\"113300000024821119\",\"flag\":\"4\"," +
				"\"qdapprnum\":\"233580db-a6be-45d3-b372-af2e36ce7ad6\",\"unitaccnum\":\"\"," +
				"\"certinum\":\"330211197801231023\",\"unitaccname\":\"联系人\",\"transdate\":" +
				"\"\",\"instanceid\":\"\",\"servicecode\":\"\",\"unitaccaddr\":\"联系人\"," +
				"\"phone\":\"\",\"unitlinkphone\":\"13211112222\",\"ispflag\":\"1\",\"basenum\":\"\",\"servicename\":\"\"," +
				"\"begpym\":\"\",\"certitype\":\"01\",\"accname\":\"测试人\",\"endpym\":\"1585670400000\",\"xzareacode\":\"330200\"}";
		System.out.println(json);
		System.out.println(URLEncoder.encode(json,"UTF-8"));
		httpURLConnectionPOST6010(URLEncoder.encode(json,"UTF-8"));
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	}


	long endTime=System.currentTimeMillis();
	long Time=endTime-starTime;
	System.out.println("请求大数据平台耗时"+Time+"毫秒");

		}
	}
}
