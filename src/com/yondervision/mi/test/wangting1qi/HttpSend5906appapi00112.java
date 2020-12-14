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
//有无住房查询
public class HttpSend5906appapi00112 {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";
	
	public static String httpURLConnectionPOST59061(
			String powerMatters,
			String subPowerMatters,
			String materialName,
			String clientusercid,
			String qlrzjh,
			String computername,
			String cxfw,
			String clientusername,
			String qlrmc
			) {
		try {
			long starTime=System.currentTimeMillis();
			URL url = new URL(POST_URL + "appapi00112.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "330219197910223297";

			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5906&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+ "&powerMatters="+powerMatters
						+ "&subPowerMatters="+subPowerMatters
						+ "&materialName="+materialName
						+"&clientusercid="+clientusercid
						+ "&qlrzjh="+qlrzjh
						+ "&computername="+computername
						+ "&cxfw="+cxfw
						+ "&clientusername="+ clientusername
						+ "&qlrmc="+ qlrmc;
//		 申请人证件号:clientusercid(提取公积金人的证件号)
//		 被查询人证件号:qlrzjh(以家庭单位的被查询人证件号)
//		 查询部门:computername(查询部门)
//		 查询范围:cxfw(被查询人需要查询的住房所在区域)
//		 申请人姓名:clientusername(提取公积金人的姓名)
//		 被查询人姓名:qlrmc(以家庭单位的被查询人姓名)
			 String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=40";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=40";
			//System.out.println("本地参数" + parm);
//			System.out.println("传递参数：" + parm1);
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
			long endTime=System.currentTimeMillis();
			long Time=endTime-starTime;
			System.out.println("请求大数据平台耗时"+Time+"毫秒");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	public static void ceshi(String certinum,String accname){
		httpURLConnectionPOST59061("","","",certinum,certinum,"GJJ","",accname,accname);
		/*
		httpURLConnectionPOST59061("","","",certinum,certinum,"GJJ","SBJ",accname,accname);
		httpURLConnectionPOST59061("","","",certinum,certinum,"GJJ","YY",accname,accname);
		httpURLConnectionPOST59061("","","",certinum,certinum,"GJJ","XS",accname,accname);
		httpURLConnectionPOST59061("","","",certinum,certinum,"GJJ","ZH",accname,accname);
		httpURLConnectionPOST59061("","","",certinum,certinum,"GJJ","BL",accname,accname);
		httpURLConnectionPOST59061("","","",certinum,certinum,"GJJ","FH",accname,accname);
		httpURLConnectionPOST59061("","","",certinum,certinum,"GJJ","CX",accname,accname);
		httpURLConnectionPOST59061("","","",certinum,certinum,"GJJ","NH",accname,accname);
		httpURLConnectionPOST59061("","","",certinum,certinum,"GJJ","HZW",accname,accname);
		*/
		//System.out.println("下一组");

	}

	public static void main(String[] args) throws UnknownHostException {
		for (int i = 1;i<=1;i++){
		//ceshi("211322199211142275","李春建");
		//ceshi("330206198901033128","陈晓未");

//		ceshi("33020419930722402X","林梦娇");
//		ceshi("330205198809133314","孙特超");
//		ceshi("330204199310164021","毕超静");
//		ceshi("330204199308065016","刘晓威");
		ceshi("330682199408031287","王琰慧");
		//{"msg":"成功","datas":[{"registrationOrg":"宁波市江北区民政局婚姻登记处","fCardId":"33020419930722402X","mCardId":"330205198809133314","fName":"林梦娇","registrationDate":"2015-01-15","id":null,"businessType":"补发结婚证","mName":"孙特超","tong_time":"2018-11-16 05:33:01.0"}],"requestId":"6ca1988be7a94d7783c4de5b96f3e2ae","totalDataCount":1,"totalPage":1,"recode":"000000"}
			//{"msg":"成功","datas":[{"registrationOrg":"宁波市鄞州区民政局婚姻登记处","fCardId":"330204199310164021","mCardId":"330204199308065016","fName":"毕超静","registrationDate":"2016-10-25","id":null,"mName":"刘晓威","businessType":"结婚登记","tong_time":"2019-02-03 16:00:54.0"}],"requestId":"0092f3d1cb9a46bd8dde43a3a138009b","totalDataCount":1,"totalPage":1,"recode":"000000"}
//		ceshi("331022198512192213","林送");
//		ceshi("420602197311050087","张环");
//		ceshi("330227199206043427","任碧芳");
//		ceshi("330225198703040035","林益");
//		ceshi("330903198411250911","徐益光");
//		ceshi("33022719710604823X","许展鸿");
//		ceshi("330226198901306259","储蒙帅");
//		ceshi("330283198408302729","李佳嫔");
//		ceshi("33022719831015136X","史芳萍");
//		ceshi("331022198706150214","陈欢杰");
//		ceshi("372922198811174418","郭庆力");
//		ceshi("330227198104260012","谢飞益");
//		ceshi("452224198812012028","黄秋凤");
//		ceshi("330224198010142729","王红烛台");
//		ceshi("330226197012165439","顾能川");
//		ceshi("330227197106056811","屠水波");
//		ceshi("330205197711090032","徐志荣");
//		ceshi("330224197908312718","樊超兵");
//		ceshi("330206196402220915","陶海明");
//		ceshi("330203197505011513","史仲豪");
//		ceshi("330225197409010017","周海敏");
//		ceshi("330227198805112549","陈燕南");
//		ceshi("330225196201170615","葛永祥");
//		ceshi("330283198210281811","张平");
//		ceshi("33021919731215667X","俞峰");
//		ceshi("330203197802050025","姜颖");
		/*
		ceshi("330227196308013719","徐建光");
		ceshi("330225199101291026","周琼琼");
		ceshi("330325198207012911","夏顺敏");
		ceshi("411303198807243724","杜桃婉");
		ceshi("330211198412234018","康海刚");
		ceshi("342421197102081418","徐忠国");
		ceshi("330226198508176574","张伟格");
		ceshi("330283199102037218","奉化市公安局");
		ceshi("330227198306250023","李百超");
		ceshi("330323197211257914","缪定增");
		ceshi("532124197608030747","张琴");
		ceshi("330227195510260022","张煜娟");
		ceshi("360428198901025827","徐礼华");
		ceshi("330621198501281523","王旭萍");
		ceshi("330204198210293016","潘力");
		ceshi("330206197306284315","胡良斌");
		ceshi("330203196802101537","孙亚峰");
		ceshi("330206197108065541","贵玉珠");
		ceshi("231084197906121726","刘宝昌");
		ceshi("330203196308170369","董亚君");
		ceshi("330227196304205756","付生良");
		ceshi("330205195102280616","陆瑞春");
		ceshi("330623197404245270","张立煜");
		ceshi("330227195211102016","张志才");
		ceshi("360123197601300011","张英波");
		ceshi("330227197905125641","王志蓓");
		ceshi("330227195503082010","陈明洋");
		ceshi("330222196106150035","陆燕");
		ceshi("330227197909012505","徐佩红");
		ceshi("330226197703206586","胡敏霞");
		ceshi("330227198103033715","肖起峰");
		ceshi("330282198911130059","徐颢恺");
		ceshi("330225197405154814","王建军");
		ceshi("330204198107092021","胡婵");
		ceshi("610330197006280016","李孟浩");
		ceshi("350424197210120065","叶庆玉");
		ceshi("331082198801273059","朱仁龙");
		ceshi("330204198506251077","周君");
		ceshi("372321198604080022","孙建凤");
		ceshi("332523198612281824","江美霞");
		ceshi("330622197403017640","毛晶");
		ceshi("33020419800513602X","殷珺");
		ceshi("340406198908031419","余龙辉");
		ceshi("331022199704071285","丁丽");
		ceshi("330282199309090052","陈松武");
		ceshi("330227198201042498","李军峰");
		ceshi("61240119830710741X","查大江");
		ceshi("330225199410253170","奚忠州");
		ceshi("330282198507118234","陈乐培");
		ceshi("321021197208310913","袁欣华");
		ceshi("330901196608120318","张建军");
		ceshi("330282199107218660","周颖");
		ceshi("362424198010011129","钟丽丹");
		ceshi("330227198209147323","张旭浓");
		ceshi("330227198701097321","汪科元");
		ceshi("330723198610170628","占慰慧");
		ceshi("33020519880613184X","罗海贝");
		ceshi("330226197004100790","胡伟能");
		ceshi("330203196308212119","周伟丰");
		ceshi("330203194405040014","忻东城");
		ceshi("420116198105170016","钟海东");
		ceshi("330227197003252318","颜福松");
		ceshi("330225198011093179","邱吕峰");
		ceshi("330227196505285422","吴智意");
		ceshi("330283198809250026","陈学敏");
		ceshi("330227196512275257","刘华震");
		ceshi("360722199312060029","施金姝");
		ceshi("330206197811124611","李卢镇");
		ceshi("330227197311025635","吴铁汉");
		ceshi("511381198803018178","唐文先");
		ceshi("320623197401264351","顾理明");
		ceshi("330225198510302916","顾得峰");
		ceshi("330204197508036114","邵建宁");
		ceshi("330226750803001","邵建宁");
		*/
		}
	}
}
