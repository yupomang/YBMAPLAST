package com.yondervision.mi.test.wangting1qi;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;


//单位停缴缓缴上传文件查询
public class HttpSend6026appapi00234 {
	//白天只能连接生产环境，晚上才能连接测试环境，并且测试完，需要couchbase删除缓存
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	//public static final String POST_URL = "http://172.16.10.96:7001/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";
	
	public static String httpURLConnectionPOST6010(
		/*
			unitaccnum   单位账号
unitaccname   单位名称
begdate  缓/停缴开始时间 202001
enddate  缓/停缴终止时间
postpayflag 缓/停缴标志   1-缓缴 5-缓缴解除 2-停缴 6-停缴解除
postpayren  缓/停缴原因
unitlinkman1  单位经办人
unitlinkphone2 经办人联系电话
filename  图片文件名称
		 */
		    String unitaccnum, String unitaccname, String begdate, String enddate,String postpayflag, String postpayren, String unitlinkman1, String unitlinkphone2 , String filename
	) {
		try {
			URL url = new URL(POST_URL + "appapi00234.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId ="330726198810170330";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 	String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6026&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+ "&unitaccnum="+unitaccnum + "&unitaccname="+unitaccname + "&begdate="+begdate + "&enddate="+enddate
					+ "&postpayflag="+postpayflag + "&postpayren="+postpayren + "&unitlinkman1="+unitlinkman1 + "&unitlinkphone2="+unitlinkphone2 + "&filename="+filename;
			//System.out.println("Para----------"+Para);
			 String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=40";
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=40";
			connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
			connection.connect();
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
			dataout.writeBytes(parm1);
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
			String result=aes.decrypt(sb.toString());
			System.out.println(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) throws UnknownHostException {
for(int i=1;i<2;i++) {
	long starTime=System.currentTimeMillis();
		/*httpURLConnectionPOST6010("","362422199510198710");
		httpURLConnectionPOST6010("","330204196906123024");
		httpURLConnectionPOST6010("","330682199408031287");
		httpURLConnectionPOST6010("","330203196105211212");
		httpURLConnectionPOST6010("","330203196810020614");
		httpURLConnectionPOST6010("","33020319970406182X");*/

		//httpURLConnectionPOST6010("陈珩","330203196411299910");

		httpURLConnectionPOST6010("010100000207", "宁波莱诗日化用品有限公司","202001","202006","1","罗霖","罗霖","17681808295","5213452020022701");
		/*httpURLConnectionPOST6010("周春峰", "330227197902016474");
		httpURLConnectionPOST6010("罗霖", "362422199510198710");*/

	long endTime=System.currentTimeMillis();
	long Time=endTime-starTime;
	System.out.println("请求大数据平台耗时"+Time+"毫秒");

		}
	}
}
