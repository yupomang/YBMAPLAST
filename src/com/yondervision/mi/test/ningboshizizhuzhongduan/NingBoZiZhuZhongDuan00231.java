package com.yondervision.mi.test.ningboshizizhuzhongduan;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

public class NingBoZiZhuZhongDuan00231 {

	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "78ce07bdcdf4877f56305895659defc6";
	private static final String appId_V = "yondervisionselfservice53";
	private static final String clientIp_V="172.10.0.1";


	public static String httpURLConnectionPOST5021(
			String spt_ywid,String spt_ywbm,String apprnum, String spt_bglx,String spt_fjmx,String spt_hkyh,String spt_hkzh,String spt_jgh,
			String spt_jkbm,String spt_pageno, String spt_pagesize,String spt_qdmc,String spt_sfzh,String spt_sjhm,String spt_sqrlx,
			String spt_sqsj,String spt_userid, String spt_xm,String spt_ywbh
	) {

		try {
			URL url = new URL(POST_URL + "appapi00231.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,brcCode");
			AesTestZiZhu aes = null;
			try {
				aes = new AesTestZiZhu();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String userId = aes.encrypt("0000".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			String parm = "";
			// 用于数字签名
			parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6024&devtoken=&channel=53&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp=172.10.0.1&brcCode=05740008"
			;

			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6024&devtoken=&channel=53&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=172.10.0.1&brcCode=05740008"
					+ "&qdapprnum="+ spt_ywid + "&spt_ywbm="+ spt_ywbm +"&apprnum="+apprnum +"&spt_bglx="+spt_bglx +"&spt_fjmx="+spt_fjmx +"&spt_hkyh="+spt_hkyh +"&spt_hkzh="+spt_hkzh +"&spt_jgh="+spt_jgh +"&spt_jkbm="+spt_jkbm
					+ "&spt_pageno="+ spt_pageno +"&spt_pagesize="+spt_pagesize +"&spt_qdmc="+spt_qdmc +"&spt_sfzh="+spt_sfzh +"&spt_sjhm="+spt_sjhm +"&spt_sqrlx="+spt_sqrlx +"&spt_sqsj="+spt_sqsj
					+ "&spt_userid="+ spt_userid +"&spt_xm="+spt_xm +"&spt_ywbh="+spt_ywbh
					;

			System.out.println("parm" + parm);
			System.out.println("parm1" + parm1);
			connection.addRequestProperty("headparaMD5", RSASignature.sign(
					EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
			connection.connect();
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
			System.out.println("传递参数：" + parm1);
			dataout.writeBytes(parm1);
			dataout.flush();
			dataout.close();
			BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = bf.readLine()) != null) {
				sb.append(line).append(System.getProperty("line.separator"));
			}
			// 重要且易忽略步骤 (关闭流,切记!)
			bf.close();
			connection.disconnect(); // 销毁连接
			System.out.println(sb.toString());
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static void main(String[] args) throws UnknownHostException, UnsupportedEncodingException {
		/*查询
		spt_jkbm     借款编码
		spt_bglx  公积金贷款合同变更类型          固定值：DKYW04
		spt_sfzh     身份证号码
		申请
		spt_ywid  业务申请唯一标识码
		apprnum      贷款受理编号
		spt_bglx  公积金贷款合同变更类型          固定值：DKYW05
		spt_fjmx     申请附件
		spt_hkyh     还款银行代码
		spt_hkzh     还款账号
		spt_jgh      机构号
		spt_jkbm     借款编码
		spt_pageno   分页页码
		spt_pagesize 分页大小
		spt_qdmc     渠道名称编码
		spt_sfzh     身份证号码
		spt_sjhm     手机号码
		spt_sqrlx    申请人类型
		spt_sqsj     申请时间
		spt_userid   政务网用户ID
		spt_xm       姓名
		spt_ywbh     业务申报号
		*/


		httpURLConnectionPOST5021("NBZZ19122510000123","02492-004", "","02492-004-03","","0001","6215593901000526793",
				"05740008","DKYW05","","","","330726198810170330","","1",
				"2020-07-21 18:11:13","",URLEncoder.encode("张鹏程","utf-8"),"");
	}
}
