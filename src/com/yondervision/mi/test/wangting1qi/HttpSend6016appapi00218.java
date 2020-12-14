package com.yondervision.mi.test.wangting1qi;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//家庭住房信息查询
public class HttpSend6016appapi00218 {
	//白天只能连接生产环境，晚上才能连接测试环境，并且测试完，需要couchbase删除缓存
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	//public static final String POST_URL = "http://172.16.10.96:7001/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST6016(
			//String familyMembers
			String name,
			String idNo,
			String name1,
			String idNo1,
			String name2,
			String idNo2,
			String name3,
			String idNo3,
			String name4,
			String idNo4,
			String name5,
			String idNo5,
			String name6,
			String idNo6,
			String name7,
			String idNo7,
			String name8,
			String idNo8
	) {
		try {
			URL url = new URL(POST_URL + "appapi00218.json");
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

			String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6016&appid="
					+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
					+ "&name="+name
					+ "&idNo="+idNo
					+ "&name1="+name1
					+ "&idNo1="+idNo1
					+ "&name2="+name2
					+ "&idNo2="+idNo2
					+ "&name3="+name3
					+ "&idNo3="+idNo3
					+ "&name4="+name4
					+ "&idNo4="+idNo4
					+ "&name5="+name5
					+ "&idNo5="+idNo5
					+ "&name6="+name6
					+ "&idNo6="+idNo6
					+ "&name7="+name7
					+ "&idNo7="+idNo7
					+ "&name8="+name8
					+ "&idNo8="+idNo8;

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
			String result=aes.decrypt(sb.toString());
			System.out.println(result);
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			long starTime = System.currentTimeMillis();
			//familyMembers=[{"name": "史建", "idNo": "33021111316"},{ "name": "刘昌","idNo": "33021111316" }]
/*
王奕辉 330902198704120313 叶佳俞(叶佳艺)331004198811020048 王思颐 331004198811020048
龚宗耀 330205198207301819 张锦凤(张金凤) 150121198208297621 龚文傲 330205201107151838 龚文劭 330205201107151838
周浩 330225197903020018  蔡迎春  330226198202037045*/
			/*httpURLConnectionPOST6016("[{\"name\":\"王奕辉\",\"idNo\":\"330902198704120313\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"叶佳俞\",\"idNo\":\"331004198811020048\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"叶佳艺\",\"idNo\":\"331004198811020048\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"王思颐\",\"idNo\":\"330204201310113021\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"王奕辉\",\"idNo\":\"330902198704120313\"},{\"name\":\"龚宗耀\",\"idNo\":\"330205198207301819\"}]");

			httpURLConnectionPOST6016("[{\"name\":\"龚宗耀\",\"idNo\":\"330205198207301819\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"张锦凤\",\"idNo\":\"150121198208297621\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"张金凤\",\"idNo\":\"150121198208297621\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"龚文傲\",\"idNo\":\"330205201107151838\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"龚文劭\",\"idNo\":\"330205201612111853\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"龚宗耀\",\"idNo\":\"330205198207301819\"},{\"name\":\"张锦凤\",\"idNo\":\"150121198208297621\"}]");

			httpURLConnectionPOST6016("[{\"name\":\"周浩\",\"idNo\":\"330225197903020018\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"蔡迎春\",\"idNo\":\"330226198202037045\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"周若惜\",\"idNo\":\"330225200710070021\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"周洛晗\",\"idNo\":\"330225201510090049\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"周浩\",\"idNo\":\"330225197903020018\"},{\"name\":\"蔡迎春\",\"idNo\":\"330226198202037045\"}]");
*/
			/*httpURLConnectionPOST6016("[{\"name\":\"姜晓芬\",\"idNo\":\"332527198609052225\"},{\"name\":\"郑汉\",\"idNo\":\"330282201509229194\"},{\"name\":\"郑瀚\",\"idNo\":\"330282201509229194\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"王阿彤\",\"idNo\":\"230204196604202129\"},{\"name\":\"张雁亭\",\"idNo\":\"230202196504140319\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"姚银存\",\"idNo\":\"330225198210291813\"},{\"name\":\"史妲\",\"idNo\":\"330225198611190028\"},{\"name\":\"姚舒菡\",\"idNo\":\"330225201306200044\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"王奕辉\",\"idNo\":\"330902198704120313\"},{\"name\":\"叶佳瑜\",\"idNo\":\"331004198811020048\"},{\"name\":\"叶佳艺\",\"idNo\":\"331004198811020048\"},{\"name\":\"王思顾\",\"idNo\":\"330204201310113021\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"林鋆\",\"idNo\":\"330281199010042519\"},{\"name\":\"林均\",\"idNo\":\"330281199010042519\"},{\"name\":\"汪清儿\",\"idNo\":\"330281199008132523\"},{\"name\":\"林亦涵\",\"idNo\":\"330281201609122515\"}]");
*/
			/*httpURLConnectionPOST6016("[{\"name\":\"李广吉\",\"idNo\":\"230715197206220111\"},{\"name\":\"李广龙\",\"idNo\":\"230715197206220111\"},{\"name\":\"李丽红\",\"idNo\":\"230710197606230421\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"李明松\",\"idNo\":\"510921198306051036\"},{\"name\":\"韦学会\",\"idNo\":\"513425198302028120\"},{\"name\":\"李语菲\",\"idNo\":\"513425200810108127\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"薛继况\",\"idNo\":\"330327198904090213\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"薛继爽\",\"idNo\":\"330327198904090213\"},{\"name\":\"万琴\",\"idNo\":\"513124199508061761\"},{\"name\":\"薛育泽\",\"idNo\":\"33032720171030023X\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"朱江南\",\"idNo\":\"330621198802232338\"},{\"name\":\"朱樑勇\",\"idNo\":\"330621198802232338\"},{\"name\":\"李小庆\",\"idNo\":\"421123199007102107\"},{\"name\":\"朱俊宇\",\"idNo\":\"330621201608142317\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"邹泽林\",\"idNo\":\"330222198101080030\"},{\"name\":\"徐娜\",\"idNo\":\"330222198110270020\"},{\"name\":\"邹简宁\",\"idNo\":\"330282200709140069\"},{\"name\":\"邹运樱\",\"idNo\":\"330282200709140069\"},{\"name\":\"邹筠庭\",\"idNo\":\"330282201104140052\"},{\"name\":\"邹筠闳\",\"idNo\":\"330282201104140036\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"忤振江\",\"idNo\":\"411324198606191579\"},{\"name\":\"王有莲\",\"idNo\":\"530129198709112527\"},{\"name\":\"忤庆晨\",\"idNo\":\"410122201409200157\"},{\"name\":\"仵一诺\",\"idNo\":\"330282201902029180\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"叶婧妍\",\"idNo\":\"330211198810140041\"}]");
*/
			/*httpURLConnectionPOST6016("[{\"name\":\"杨慎强\",\"idNo\":\"360121198401163114\"},{\"name\":\"邓福娥\",\"idNo\":\"36068119860109222X\"},{\"name\":\"杨子航\",\"idNo\":\"360121200811173155\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"周益楠\",\"idNo\":\"330282198309079211\"},{\"name\":\"周益男\",\"idNo\":\"330282198309079211\"},{\"name\":\"邵利君\",\"idNo\":\"330281198401210044\"},{\"name\":\"周希姚\",\"idNo\":\"330281201102100667\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"姜冬辉\",\"idNo\":\"320981198907057741\"},{\"name\":\"倪荣\",\"idNo\":\"320924198707085718\"},{\"name\":\"倪嘉萱\",\"idNo\":\"320902201312150081\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"龙正祥\",\"idNo\":\"342422198801014290\"},{\"name\":\"龙正翔\",\"idNo\":\"342422198801014290\"},{\"name\":\"陈静\",\"idNo\":\"342422198912282626\"},{\"name\":\"龙萱\",\"idNo\":\"34152120150510258X\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"张超\",\"idNo\":\"372924198805272110\"},{\"name\":\"宋景丽\",\"idNo\":\"372924198502153341\"},{\"name\":\"张思雅\",\"idNo\":\"371723201411152120\"}]");
			httpURLConnectionPOST6016("[{\"name\":\"许锋\",\"idNo\":\"330222198011026639\"},{\"name\":\"许峰\",\"idNo\":\"330222198011026639\"},{\"name\":\"赵利冲\",\"idNo\":\"330282198210046620\"},{\"name\":\"许子杭\",\"idNo\":\"330282200709049177\"},{\"name\":\"许子伊\",\"idNo\":\"330282201607049189\"}]");
*/
			//330225201306200044	姚舒菡

			//httpURLConnectionPOST6016("姚银存","330225198210291813","","","","","","","","","","","","","","","","");
			//httpURLConnectionPOST6016("许锋","330222198011026639","","","","","","","","","","","","","","","","");
			//httpURLConnectionPOST6016("毛惠慧","330227198503057329","","","","","","","","","","","","","","","","");
			//httpURLConnectionPOST6016("李广吉","230715197206220111","李广龙","230715197206220111","李丽红","230710197606230421","","","","","","","","","","","","");
			////httpURLConnectionPOST6016("邵建宁","330204197508036114","","","","","","","","","","","","","","","","");
			////httpURLConnectionPOST6016("邵建宁","330204750803611","","","","","","","","","","","","","","","","");
			//httpURLConnectionPOST6016("胡竹利","330222197812080067","","","","","","","","","","","","","","","","");
			httpURLConnectionPOST6016("徐晶","330211198602080013","张勇","330211198901144021","","330211198602080013","","330211198901144021","徐宸棣","330211201805284010","","","","","","","","");



/*			httpURLConnectionPOST6016("张鹏程330726198810170330");
			httpURLConnectionPOST6016("330726198810170330张鹏程");
			httpURLConnectionPOST6016("张鹏程");
			httpURLConnectionPOST6016("330726198810170330");
			httpURLConnectionPOST6016("");*/

			long endTime = System.currentTimeMillis();
			long Time = endTime - starTime;
			System.out.println(Time);
		}
	}
}
