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

public class NingBoZiZhuZhongDuan00232 {

	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "78ce07bdcdf4877f56305895659defc6";
	private static final String appId_V = "yondervisionselfservice53";
	private static final String clientIp_V="172.10.0.1";
/*公共参数：
qdapprnum   //业务申请唯一标识码
spt_fjmx;   // 申请附件
spt_jgh;      // 机构号   （必填）
spt_jkbm;   // 借款编码   （必填）
spt_pageno;   // 分页页码
spt_pagesize;  // 分页大小
spt_qdmc;   // 渠道名称编码
spt_sfzh;   // 身份证号码  （必填）
spt_sjhm;   // 手机号码
spt_sqrlx;   // 申请人类型  （必填）
spt_sqsj;   // 申请时间   （必填）
spt_userid;   // 政务网用户ID
spt_xm;    // 姓名    （必填）
spt_ywbh;   // 业务申报号  （必填）
spt_ywbm;   // 业务事项编码  （必填)
贷款申请前查询 (第一步)
spt_jkbm == DKYW01
入参
spt_sqxx;   // 申请信息（必填)

贷款预审核(第二步)
spt_jkbm == DKYW02
入参
spt_sqxx;   // 申请信息（必填)
spt_sqrxx;   // 申请人信息（必填)
spt_poxx;   // 配偶信息（必填)
spt_gtgxrxx;  // 共同关系人信息（必填)
spt_fwxx;      // 房屋信息（必填)

贷款提交(第三步)
spt_jkbm == DKYW03
入参
spt_jkxx;   // 借款信息（必填)*/

	public static String httpURLConnectionPOST5021(
			String spt_ywid,String spt_fjmx,String spt_jgh, String spt_jkbm,String spt_pageno,String spt_pagesize,String spt_qdmc,String spt_sfzh,
			String spt_sjhm,String spt_sqrlx, String spt_sqsj,String spt_userid,String spt_xm,String spt_ywbh,String spt_ywbm,
			String spt_sqxx, String spt_sqrxx,String spt_poxx,String spt_gtgxrxx,String spt_fwxx,String spt_jkxx
	) {

		try {
			URL url = new URL(POST_URL + "appapi00232.json");
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
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6025&devtoken=&channel=53&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp=172.10.0.1&brcCode=05740008"
			;

			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6025&devtoken=&channel=53&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=172.10.0.1&brcCode=05740008"
					+ "&qdapprnum="+ spt_ywid + "&spt_fjmx="+ spt_fjmx +"&spt_jgh="+spt_jgh +"&spt_jkbm="+spt_jkbm +"&spt_pageno="+spt_pageno +"&spt_pagesize="+spt_pagesize +"&spt_qdmc="+spt_qdmc +"&spt_sfzh="+spt_sfzh
					+"&spt_sjhm="+spt_sjhm + "&spt_sqrlx="+ spt_sqrlx +"&spt_sqsj="+spt_sqsj +"&spt_userid="+spt_userid +"&spt_xm="+spt_xm +"&spt_ywbh="+spt_ywbh +"&spt_ywbm="+spt_ywbm
					+"&spt_sqxx="+spt_sqxx + "&spt_sqrxx="+ spt_sqrxx +"&spt_poxx="+spt_poxx +"&spt_gtgxrxx="+spt_gtgxrxx +  "&spt_fwxx="+ spt_fwxx +"&spt_jkxx="+spt_jkxx
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
spt_ywbh, 业务申报号, 入口字段
spt_success, 成功标志, 出口字段
spt_showmsg, 返回结果描述, 出口字段
spt_errormsg, 失败错误日志, 出口字段
spt_datacount, 总结果记录数, 出口字段
spt_data, 返回参数信息, 出口字段
paramdes, 参数描述, 出口字段
stdata, 省厅下传数据, 出口字段
data, 省厅综服批量数据, 出口字段
memo, 备注信息, 出口字段
spt_ywid, 业务申请唯一标识码, 双向                      */

	/*<spt_ywbm>02492-001</>
<spt_sqrlx>1</>
<spt_xm>罗霖</>
<spt_userid></>
<spt_fwxx></>
<spt_sqxx>[{"dkcs":"宁波市","dkfs":"组合贷款","hjd":"浙江省内","htbh":"浙江省内","hyzt":"已婚","jccs":"宁波市","jkyt":"购买新建住房","dkjgh":"11","jcjgh":"11"}]</>
<spt_sqsj>2019-12-27 16:30:06</>
<spt_sqrxx></>
<spt_gtgxrxx></>
<spt_jgh>11</>
<spt_jkbm>DKYW01</>
<spt_jkxx></>
<spt_pageno></>
<spt_pagesize></>
<spt_poxx></>
<spt_qdmc></>
<spt_sfzh>362422199510198710</>
<spt_fjmx></>
<spt_sjhm></>
<spt_ywbh></>
<spt_ywid>NBZZ19122710000156</>> */
		httpURLConnectionPOST5021("NBZZ19122710000126","", "11","DKYW01","","","",
				"330203197809092412","","1","2019-12-27 16:30:06","", URLEncoder.encode("罗霖"),"","02492-001",
				"[{\"dkcs\":\"宁波市\",\"dkfs\":\"组合贷款\",\"hjd\":\"浙江省内\",\"htbh\":\"浙江省内\",\"hyzt\":\"已婚\",\"jccs\":\"宁波市\",\"jkyt\":\"购买新建住房\",\"dkjgh\":\"11\",\"jcjgh\":\"11\"}]","","","","","");
	}
}
