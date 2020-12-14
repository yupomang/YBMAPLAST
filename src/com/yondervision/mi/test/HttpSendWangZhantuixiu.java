package com.yondervision.mi.test;

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
public class HttpSendWangZhantuixiu {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "ba7f3a3dd146c4613102d0a16be5107b";
	private static final String appId_V = "yondervisionwebsite30";
	/**
	 * 收件--住建委--退休
	 */
	
	public static String httpURLConnectionPOST5940(String param) {
		try {
			URL url = new URL(POST_URL + "webapi80007.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty(
							"headpara",
							"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp");
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String userId = aes.encrypt("web".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			// 用于数字签名
			String parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6020&devtoken=&channel=30&appid="
					+ appKey
					+ "&appkey="
					+ appId
					+ "&appToken="+"&clientIp=10.19.93.192"
					;
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ "web".replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6020&devtoken=&channel=30"
					+ "&appid="+ appId_V.replace("+", "%2B")
					+ "&appkey="+ appKey_V.replace("+", "%2B")
					+ "&appToken="+"&clientIp=10.19.93.192"
					+ "&param="
					+ param
					;
			System.out.println("parm0" + parm);
			System.out.println("parm1" + parm1);
			System.out.println("parm3" + EncryptionByMD5.getMD5(parm.getBytes()));
			System.out.println("parm2" + RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
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

		//URLEncoder.encode("澜悦花苑","utf-8");
		httpURLConnectionPOST5940( URLEncoder.encode("{\"matterCode\":\"f26acca2-be0d-11e7-8c42-008cfae57900\",\"approveType\":\"04\",\"projId\":\"330201200211817696356\",\"recvUserName\":\"宁波市蔬菜有限公司\",\"bizType\":\"1\",\"affairType\":\"00\",\"memo\":\"\",\"extInfo\":{\"PROJPWD\":\"115784\"},\"relBizId\":\"330201200211517694572\",\"affFormInfo\":\"{\\\"企业职工退休一件事\\\":[{\\\"name\\\":\\\"uniteServiceID\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"f26acca2-be0d-11e7-8c42-008cfae57900,141c5cdc-8cfa-4059-9195-209a5d31a38a\\\"},{\\\"name\\\":\\\"aac003\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"栗江勇\\\"},{\\\"name\\\":\\\"aae135\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"362422199510198710\\\"},{\\\"name\\\":\\\"hangming\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"0001\\\"},{\\\"name\\\":\\\"aae008\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"6217213901001033972\\\"},{\\\"name\\\":\\\"khyhmc\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"0001\\\"},{\\\"name\\\":\\\"yhkh\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"\\\"},{\\\"name\\\":\\\"txny\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"202002\\\"},{\\\"name\\\":\\\"cbxqbm\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"330299\\\"},{\\\"name\\\":\\\"sbjgmc\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"宁波市社会保险管理服务中心\\\"},{\\\"name\\\":\\\"sbjgdz\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"宁波市海曙区解放南路257号\\\"}]}\",\"recvDeptCode\":\"001008002016015\",\"appId\":\"2088000579\",\"applicantVO\":{\"applyType\":\"01\",\"legalMan\":\"杨茂印\",\"address\":\"浙江省宁波市江北区庄桥马径丰安路5号\",\"applyCardType\":\"53\",\"contactCardNo\":\"362422199510198711\",\"contactName\":\"洪虹\",\"contactUid\":\"\",\"legalCardType\":\"\",\"applyCardNo\":\"91330200144063019U\",\"isAgent\":\"\",\"contactTelNo\":\"13805897411\",\"applyUid\":\"\",\"contactCardType\":\"31\",\"legalCardNo\":\"330226196911010073\",\"applyName\":\"宁波市蔬菜有限公司\"},\"gmtApply\":{\"date\":11,\"hours\":15,\"seconds\":1,\"month\":1,\"timezoneOffset\":-480,\"year\":120,\"minutes\":33,\"time\":1581406381000,\"day\":2},\"suffInfoList\":[{\"attachPath\":\"\",\"stuffName\":\"经办人身份证\",\"stuffUniId\":\"437F2EFE17CED22FFAB00AB392762A0B\",\"fetchMode\":\"02\",\"attachName\":\"\",\"memo\":\"\",\"stuffType\":\"02\",\"extInfo\":{\"ISTAKE\":\"0\"},\"stuffNum\":1},{\"attachPath\":\"\",\"stuffName\":\"个人住房公积金账户封存申请表\",\"stuffUniId\":\"2B5CFA5E82BBD97DE2320501E807AE05\",\"fetchMode\":\"02\",\"attachName\":\"\",\"memo\":\"\",\"stuffType\":\"02\",\"extInfo\":{\"ISTAKE\":\"0\"},\"stuffNum\":1},{\"attachPath\":\"\",\"stuffName\":\"职工身份证\",\"stuffUniId\":\"8670B842CA987A9CFE52732A8935DC53\",\"fetchMode\":\"02\",\"attachName\":\"\",\"memo\":\"\",\"stuffType\":\"02\",\"extInfo\":{\"ISTAKE\":\"0\"},\"stuffNum\":1}],\"applyOrigin\":\"9\",\"execDeptOrgCode\":\"002939927\",\"recvDeptName\":\"市住建局\",\"recvUserType\":\"3\",\"projectNature\":\"99\",\"areaCode\":\"330201\",\"matterDir\":null,\"recvUserId\":\"2339988\",\"projectName\":\"关于宁波市蔬菜有限公司申请退休\",\"deptCode\":\"001008002016015\"}","utf-8"));
		System.out.println(URLEncoder.encode("{\"matterCode\":\"f26acca2-be0d-11e7-8c42-008cfae57900\",\"approveType\":\"04\",\"projId\":\"330201200211817696356\",\"recvUserName\":\"宁波市蔬菜有限公司\",\"bizType\":\"1\",\"affairType\":\"00\",\"memo\":\"\",\"extInfo\":{\"PROJPWD\":\"115784\"},\"relBizId\":\"330201200211517694572\",\"affFormInfo\":\"{\\\"企业职工退休一件事\\\":[{\\\"name\\\":\\\"uniteServiceID\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"f26acca2-be0d-11e7-8c42-008cfae57900,141c5cdc-8cfa-4059-9195-209a5d31a38a\\\"},{\\\"name\\\":\\\"aac003\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"栗江勇\\\"},{\\\"name\\\":\\\"aae135\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"362422199510198710\\\"},{\\\"name\\\":\\\"hangming\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"0001\\\"},{\\\"name\\\":\\\"aae008\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"6217213901001033972\\\"},{\\\"name\\\":\\\"khyhmc\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"0001\\\"},{\\\"name\\\":\\\"yhkh\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"\\\"},{\\\"name\\\":\\\"txny\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"202002\\\"},{\\\"name\\\":\\\"cbxqbm\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"330299\\\"},{\\\"name\\\":\\\"sbjgmc\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"宁波市社会保险管理服务中心\\\"},{\\\"name\\\":\\\"sbjgdz\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"宁波市海曙区解放南路257号\\\"}]}\",\"recvDeptCode\":\"001008002016015\",\"appId\":\"2088000579\",\"applicantVO\":{\"applyType\":\"01\",\"legalMan\":\"杨茂印\",\"address\":\"浙江省宁波市江北区庄桥马径丰安路5号\",\"applyCardType\":\"53\",\"contactCardNo\":\"362422199510198711\",\"contactName\":\"洪虹\",\"contactUid\":\"\",\"legalCardType\":\"\",\"applyCardNo\":\"91330200144063019U\",\"isAgent\":\"\",\"contactTelNo\":\"13805897411\",\"applyUid\":\"\",\"contactCardType\":\"31\",\"legalCardNo\":\"330226196911010073\",\"applyName\":\"宁波市蔬菜有限公司\"},\"gmtApply\":{\"date\":11,\"hours\":15,\"seconds\":1,\"month\":1,\"timezoneOffset\":-480,\"year\":120,\"minutes\":33,\"time\":1581406381000,\"day\":2},\"suffInfoList\":[{\"attachPath\":\"\",\"stuffName\":\"经办人身份证\",\"stuffUniId\":\"437F2EFE17CED22FFAB00AB392762A0B\",\"fetchMode\":\"02\",\"attachName\":\"\",\"memo\":\"\",\"stuffType\":\"02\",\"extInfo\":{\"ISTAKE\":\"0\"},\"stuffNum\":1},{\"attachPath\":\"\",\"stuffName\":\"个人住房公积金账户封存申请表\",\"stuffUniId\":\"2B5CFA5E82BBD97DE2320501E807AE05\",\"fetchMode\":\"02\",\"attachName\":\"\",\"memo\":\"\",\"stuffType\":\"02\",\"extInfo\":{\"ISTAKE\":\"0\"},\"stuffNum\":1},{\"attachPath\":\"\",\"stuffName\":\"职工身份证\",\"stuffUniId\":\"8670B842CA987A9CFE52732A8935DC53\",\"fetchMode\":\"02\",\"attachName\":\"\",\"memo\":\"\",\"stuffType\":\"02\",\"extInfo\":{\"ISTAKE\":\"0\"},\"stuffNum\":1}],\"applyOrigin\":\"9\",\"execDeptOrgCode\":\"002939927\",\"recvDeptName\":\"市住建局\",\"recvUserType\":\"3\",\"projectNature\":\"99\",\"areaCode\":\"330201\",\"matterDir\":null,\"recvUserId\":\"2339988\",\"projectName\":\"关于宁波市蔬菜有限公司申请退休\",\"deptCode\":\"001008002016015\"}","utf-8"));

	}

}
