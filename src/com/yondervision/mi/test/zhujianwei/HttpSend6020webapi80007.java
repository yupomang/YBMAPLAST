package com.yondervision.mi.test.zhujianwei;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//住建委--中台--收件服务
public class HttpSend6020webapi80007 {
	//白天只能连接生产环境，晚上才能连接测试环境，并且测试完，需要couchbase删除缓存
	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/";
	//public static final String POST_URL = "http://61.153.144.77:11080/YBMAPZH/";
	//public static final String POST_URL = "http://172.16.10.96:11080/YBMAPZH/";
	private static final String appKey_V = "ba7f3a3dd146c4613102d0a16be5107b";
	private static final String appId_V = "yondervisionwebsite30";
	private static final String clientIp_V="172.10.0.1";
	public static String httpURLConnectionPOST6020(
		/*	银行卡的所属银行:payeebankcode
卡号:payeebankaccnum
姓名:accname
证件类型:certitype
证件号码:certinum
冻结状态:frzflag
提取原因:drawreason
渠道流水号：qdapprnum
单位账号:unitaccnum
手机号码:handset
可提取金额:inputamt
		 */
		    String param
			//String applicantVO

	) {
		try {
			URL url = new URL(POST_URL + "webapi80007.json");
			//URL url = new URL(POST_URL + "webapi80010.json");
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


		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6020&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+ "&param="+param;


			System.out.println("para:"+Para);
			String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=30";
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=30";
			System.out.println("fullPara:"+Para);
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
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args){
		for(int i=0;i<1;i++) {
			long starTime = System.currentTimeMillis();
			/*	银行卡的所属银行:payeebankcode
				卡号:payeebankaccnum
				姓名:accname
				证件类型:certitype
				证件号码:certinum
				冻结状态:frzflag
				提取原因:drawreason
				渠道流水号：qdapprnum
				单位账号:unitaccnum
				手机号码:handset
				可提取金额:inputamt
			 */
			//httpURLConnectionPOST6020("123456789","asdzxc");
			httpURLConnectionPOST6020("{\"matterCode\":\"f26acca2-be0d-11e7-8c42-008cfae57900\",\"approveType\":\"04\",\"projId\":\"330201200211817696356\",\"recvUserName\":\"宁波市蔬菜有限公司\",\"bizType\":\"1\",\"affairType\":\"00\",\"memo\":\"\",\"extInfo\":{\"PROJPWD\":\"115784\"},\"relBizId\":\"330201200211517694572\",\"affFormInfo\":\"{\\\"企业职工退休一件事\\\":[{\\\"name\\\":\\\"uniteServiceID\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"f26acca2-be0d-11e7-8c42-008cfae57900,141c5cdc-8cfa-4059-9195-209a5d31a38a\\\"},{\\\"name\\\":\\\"aac003\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"栗江勇\\\"},{\\\"name\\\":\\\"aae135\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"33020419600211001X\\\"},{\\\"name\\\":\\\"hangming\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"0001\\\"},{\\\"name\\\":\\\"aae008\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"6217213901001033972\\\"},{\\\"name\\\":\\\"khyhmc\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"0001\\\"},{\\\"name\\\":\\\"yhkh\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"\\\"},{\\\"name\\\":\\\"txny\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"202002\\\"},{\\\"name\\\":\\\"cbxqbm\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"330299\\\"},{\\\"name\\\":\\\"sbjgmc\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"宁波市社会保险管理服务中心\\\"},{\\\"name\\\":\\\"sbjgdz\\\",\\\"name_cn\\\":null,\\\"value\\\":\\\"宁波市海曙区解放南路257号\\\"}]}\",\"recvDeptCode\":\"001008002016015\",\"appId\":\"2088000579\",\"applicantVO\":{\"applyType\":\"01\",\"legalMan\":\"杨茂印\",\"address\":\"浙江省宁波市江北区庄桥马径丰安路5号\",\"applyCardType\":\"53\",\"contactCardNo\":\"330205196902053328\",\"contactName\":\"洪虹\",\"contactUid\":\"\",\"legalCardType\":\"\",\"applyCardNo\":\"91330200144063019U\",\"isAgent\":\"\",\"contactTelNo\":\"13805897411\",\"applyUid\":\"\",\"contactCardType\":\"31\",\"legalCardNo\":\"330226196911010073\",\"applyName\":\"宁波市蔬菜有限公司\"},\"gmtApply\":{\"date\":11,\"hours\":15,\"seconds\":1,\"month\":1,\"timezoneOffset\":-480,\"year\":120,\"minutes\":33,\"time\":1581406381000,\"day\":2},\"suffInfoList\":[{\"attachPath\":\"\",\"stuffName\":\"经办人身份证\",\"stuffUniId\":\"437F2EFE17CED22FFAB00AB392762A0B\",\"fetchMode\":\"02\",\"attachName\":\"\",\"memo\":\"\",\"stuffType\":\"02\",\"extInfo\":{\"ISTAKE\":\"0\"},\"stuffNum\":1},{\"attachPath\":\"\",\"stuffName\":\"个人住房公积金账户封存申请表\",\"stuffUniId\":\"2B5CFA5E82BBD97DE2320501E807AE05\",\"fetchMode\":\"02\",\"attachName\":\"\",\"memo\":\"\",\"stuffType\":\"02\",\"extInfo\":{\"ISTAKE\":\"0\"},\"stuffNum\":1},{\"attachPath\":\"\",\"stuffName\":\"职工身份证\",\"stuffUniId\":\"8670B842CA987A9CFE52732A8935DC53\",\"fetchMode\":\"02\",\"attachName\":\"\",\"memo\":\"\",\"stuffType\":\"02\",\"extInfo\":{\"ISTAKE\":\"0\"},\"stuffNum\":1}],\"applyOrigin\":\"9\",\"execDeptOrgCode\":\"002939927\",\"recvDeptName\":\"市住建局\",\"recvUserType\":\"3\",\"projectNature\":\"99\",\"areaCode\":\"330201\",\"matterDir\":null,\"recvUserId\":\"2339988\",\"projectName\":\"关于宁波市蔬菜有限公司申请退休\",\"deptCode\":\"001008002016015\"}");

			long endTime = System.currentTimeMillis();
			long Time = endTime - starTime;
			System.out.println("请求大数据平台耗时" + Time + "毫秒");
		}
	}
}
