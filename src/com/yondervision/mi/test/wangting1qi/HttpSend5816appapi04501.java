package com.yondervision.mi.test.wangting1qi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;
//个人补缴
public class HttpSend5816appapi04501 {
	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5816(String	unitaccnum,
			String	payamt,
			String	paymode,
			String	spaytype,
			String	unitacctype,
			String	qdfilename,
			String	qdfilepath,
			String	qdapprnum,
			String	UKseq) {
		try {
			URL url = new URL(POST_URL + "appapi04501.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "011500001167";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5816&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+"&unitaccnum="+unitaccnum
						+"&payamt="+payamt
						+"&paymode="+paymode
						+"&spaytype="+spaytype
						+"&unitacctype="+unitacctype
						+"&qdfilename="+qdfilename
						+"&qdfilepath="+qdfilepath
						+"&qdapprnum="+qdapprnum 
						+"&UKseq="+UKseq;

			 String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=40";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=40";
			System.out.println("本地参数" + parm);
			System.out.println("传递参数：" + parm1);
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
			String result=aes.decrypt(sb.toString());
			System.out.println(result);
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) throws UnknownHostException {
		//httpURLConnectionPOST5816("011500001167","3270","6","2","1","grbj1234567890","/bsptest/00000000/wt_file/","WT1226322344118","ukey001");		
		httpURLConnectionPOST5816("011500001167","2000","1","2","1","grbj0574000820171016115708315","/bsptest/00000000/wt_file/","WT122632222234118","ukey001");
		//paymode=1, spaytype=2, UKseq=null, unitacctype=1, regnum=, unitaccnum=011500001167, qdapprnum=wt1710161100026523413, buzType=5816, qdfilename=grbj0574000820171016104840175, payamt=2000, qdfilepath=/bsptest/00000000/wt_file/
		}
		/*	
				单位公积金账号：unitaccnum          "011500001167"
				补缴总金额：payamt                       "1440"
				缴款方式：paymode                        "6"
				补缴类型：spaytype                         "2"
				单位账户类型：unitacctype              "1"
				上传批量文件名：qdfilename           "5555"
				文件路径：qdfilepath                      "/bsptest/00000000/wt_file/"
				渠道唯一标识：qdapprnum  
				UK序列号：UKseq                            "ukey001"
		*/
	
}
