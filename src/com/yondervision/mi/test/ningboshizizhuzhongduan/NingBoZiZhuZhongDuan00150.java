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

public class NingBoZiZhuZhongDuan00150 {

	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "78ce07bdcdf4877f56305895659defc6";
	private static final String appId_V = "yondervisionselfservice53";
	private static final String clientIp_V="172.10.0.1";
/*spt_ywbm, 业务事项编码, 入口字段
spt_sqrlx, 申请人类型, 入口字段
spt_xm, 姓名, 入口字段
spt_userid, 政务网用户ID, 入口字段
spt_fwxx, 房屋信息, 入口字段
spt_sqxx, 申请信息, 入口字段
spt_sqsj, 申请时间, 入口字段
spt_sqrxx, 申请人信息, 入口字段
spt_gtgxrxx, 共同关系人信息, 入口字段
spt_jgh, 机构号, 入口字段
spt_jkbm, 借款编码, 入口字段
spt_jkxx, 借款信息, 入口字段
spt_pageno, 分页页码, 入口字段
spt_pagesize, 分页大小, 入口字段
spt_poxx, 配偶信息, 入口字段
spt_qdmc, 渠道名称编码, 入口字段
spt_sfzh, 身份证号码, 入口字段
spt_fjmx, 申请附件, 入口字段
spt_sjhm, 手机号码, 入口字段
spt_ywbh, 业务申报号, 入口字段*/

	public static String httpURLConnectionPOST5021(
			String AAC003,
			String AAC002,
			String AAB301,
			String pageSize	) {

		try {
			URL url = new URL(POST_URL + "appapi00150.json");
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
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5979&devtoken=&channel=53&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp=172.10.0.1&brcCode=05740008"
			;

			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5979&devtoken=&channel=53&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=172.10.0.1&brcCode=05740008"
					+"&AAC003="+AAC003
					+"&AAC002="+AAC002
					+"&AAB301="+AAB301
					+"&pageSize="+pageSize;

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
			} // 重要且易忽略步骤 (关闭流,切记!)
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

		httpURLConnectionPOST5021(
				URLEncoder.encode("倪翠霞","utf-8"),"421181198703085020","330200","12");
	}
}
