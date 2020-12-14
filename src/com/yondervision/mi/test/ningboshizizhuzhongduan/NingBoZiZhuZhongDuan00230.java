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

public class NingBoZiZhuZhongDuan00230 {

	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "78ce07bdcdf4877f56305895659defc6";
	private static final String appId_V = "yondervisionselfservice53";
	private static final String clientIp_V="172.10.0.1";


	public static String httpURLConnectionPOST5021(
			String spt_ywbm,String email, String spt_xm,String spt_fkrxm,String spt_fkyh,String spt_fkyhbh,String spt_fkzh,
			String spt_gtlx,String spt_hjszd, String spt_jgh,String spt_jtdz,String spt_qdmc,String spt_sfzh,String spt_yzbm,
			String spt_sjhm,String spt_sqrlx, String spt_sqsj,String spt_sqyjce,String spt_ywbh,String spt_ywid
	) {

		try {
			URL url = new URL(POST_URL + "appapi00230.json");
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
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6023&devtoken=&channel=53&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp=172.10.0.1&brcCode=05740008"
			;

			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6023&devtoken=&channel=53&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=172.10.0.1&brcCode=05740008"
					+ "&spt_ywbm="+ spt_ywbm +"&email="+email +"&spt_xm="+spt_xm +"&spt_fkrxm="+spt_fkrxm +"&spt_fkyh="+spt_fkyh +"&spt_fkyhbh="+spt_fkyhbh +"&spt_fkzh="+spt_fkzh
					+ "&spt_gtlx="+ spt_gtlx +"&spt_hjszd="+spt_hjszd +"&spt_jgh="+spt_jgh +"&spt_jtdz="+spt_jtdz +"&spt_qdmc="+spt_qdmc +"&spt_sfzh="+spt_sfzh +"&spt_yzbm="+spt_yzbm
					+ "&spt_sjhm="+ spt_sjhm +"&spt_sqrlx="+spt_sqrlx +"&spt_sqsj="+spt_sqsj +"&spt_sqyjce="+spt_sqyjce +"&spt_ywbh="+spt_ywbh +"&qdapprnum="+spt_ywid
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
		/*自由职业者开户
16	spt_ywbm, 业务事项编码, 入口字段
80	email, 电子邮箱, 入口字段						758678067@qq.com
20	spt_xm, 姓名, 入口字段	(必填)					罗东波
120	spt_fkrxm, 付款人姓名（缴存划扣户名）, 入口字段(必填)罗东波
255 spt_fkyh, 付款行（缴存划扣银行）, 入口字段(必填)	工商银行
30	spt_fkyhbh, 合作银行编号, 入口字段(必填)			0001
30	spt_fkzh, 付款账号（缴存划扣帐号）, 入口字段(必填)	1231231232
4	spt_gtlx, 个体类型, 入口字段（02---(自由职业者)）(必填)	02
255	spt_hjszd, 户籍所在地, 入口字段(必填)				吉林省长春市
14	spt_jgh, 机构号, 入口字段(必填)						05740008
255	spt_jtdz, 家庭地址, 入口字段(必填)					吉林省长春市
4	spt_qdmc, 渠道名称编码, 入口字段
18	spt_sfzh, 身份证号码, 入口字段 (必填)				420101199706200110
255	spt_yzbm, 邮政编码, 入口字段(必填)					130200
11	spt_sjhm, 手机号码, 入口字段(必填)					15948258527
4	spt_sqrlx, 申请人类型, 入口字段
19	spt_sqsj, 申请时间, 入口字段
10	spt_sqyjce, 申请公积金月缴存额, 入口字段(必填)		400
21	spt_ywbh, 业务申报号, 入口字段
64	spt_ywid, 业务申请唯一标识码, 双向					sdafsafwrqwft*/
		httpURLConnectionPOST5021("","758678067@qq.com", URLEncoder.encode("罗东波","utf-8"),URLEncoder.encode("罗东波","utf-8"),URLEncoder.encode("工商银行","utf-8"),"0001","1231231232",
				"02",URLEncoder.encode("吉林省长春市","utf-8"),"05740008",URLEncoder.encode("吉林省长春市","utf-8"),"","420101199706200110","130200","15948258527",
				"","","400","","121223561");
	}

}
