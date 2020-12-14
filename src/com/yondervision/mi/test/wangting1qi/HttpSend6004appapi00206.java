package com.yondervision.mi.test.wangting1qi;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

//民政部_婚姻登记信息核验(个人)
public class HttpSend6004appapi00206 {
	//白天只能连接生产环境，晚上才能连接测试环境，并且测试完，需要couchbase删除缓存
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";
	
	public static String httpURLConnectionPOST6004(
		/*	身份证号 cert_num_man
			姓名 name_man"
 		*/
		    String cert_num_man,
			String name_man
			) {
		try {
			URL url = new URL(POST_URL + "appapi00206.json");
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


		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6004&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+ "&cert_num_man="+cert_num_man
				 		+ "&name_man="+name_man
				 ;

		 

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
	private static String change(String input) {
		String output = null;
		try {
			output = new String(input.getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return output;
	}
	public static void main(String[] args) throws UnknownHostException {
		//本地人，单身
		//沈晶晶   330226199112154322
		//徐日东   330226199504081273
		//周裕杰   33028319920624721X
		//
		//本地，离异
		//葛为洪   330226196708134476
		//
		//省内，单身
		//金燕     330523199009102142
		//省外，单身
		//韩威峰   342221199409056610
		//向志民   360428198410045313
		//李沛     142423199405170017
		/*ttpURLConnectionPOST6004("330226199112154322","沈晶晶");
		httpURLConnectionPOST6004("330226199504081273","徐日东");
		httpURLConnectionPOST6004("33028319920624721X","周裕杰");

		httpURLConnectionPOST6004("330226196708134476","葛为洪");

		httpURLConnectionPOST6004("330523199009102142","金燕");

		httpURLConnectionPOST6004("342221199409056610","韩威峰");
		httpURLConnectionPOST6004("360428198410045313","向志民");
		httpURLConnectionPOST6004("142423199405170017","李沛");*/

		/*httpURLConnectionPOST6004("340322199005306812","陈昐上");
		httpURLConnectionPOST6004("340603196910061011","肖长杰");
		httpURLConnectionPOST6004("362421198706030812","谢端林");
		httpURLConnectionPOST6004("340322198904206984","张倩");
		httpURLConnectionPOST6004("340603196904221023","张秀丽");
		httpURLConnectionPOST6004("362421198803026524","王倩");*/

		/*httpURLConnectionPOST6004("330204199103161012","吴益斌");
		httpURLConnectionPOST6004("330203199107300626","许婷");
		httpURLConnectionPOST6004("622621199511020023","付丹");*/

		/*httpURLConnectionPOST6004("410403199601225589","吴竞");
		httpURLConnectionPOST6004("232332199711205129","孔祥汀");
		httpURLConnectionPOST6004("342222199107246028","孙静萍");
		httpURLConnectionPOST6004("522422198511024822","曹静");
		httpURLConnectionPOST6004("340828197609080558","刘后銮");
		httpURLConnectionPOST6004("410322199406289855","王哲哲");
		httpURLConnectionPOST6004("362331198912150011","张钊");
		httpURLConnectionPOST6004("420821197202046249","王红");*/
		/*httpURLConnectionPOST6004("622621199511020023","付丹");
		httpURLConnectionPOST6004("512225196411255591","秦胜千");
		httpURLConnectionPOST6004("330224197309114314","赵盈龙");
		httpURLConnectionPOST6004("42081197202046249" ,"王红");*/
		/*httpURLConnectionPOST6004("230124199610092441","徐福鑫");
		httpURLConnectionPOST6004("51072219960308036X","胥鸣");
		httpURLConnectionPOST6004("431126199304210033","杨海涛");
		httpURLConnectionPOST6004("130531199207040416","张西蒙");*/
		/*httpURLConnectionPOST6004("430304197809152062","成红英");
		httpURLConnectionPOST6004("330204197802206014","马力");
		*//*httpURLConnectionPOST6004("33022719720123273X","郑辉");
		httpURLConnectionPOST6004("330205197701154215","杨华军");*//*
		httpURLConnectionPOST6004("360421199101102038","张觉慧");
		httpURLConnectionPOST6004("360421199107033248","吴晶星");*/
		//httpURLConnectionPOST6004("440681198509280623","张于勤");
		/*httpURLConnectionPOST6004("320582198810318524","王晓");
		httpURLConnectionPOST6004("330421198704020058","黄利斌");
		httpURLConnectionPOST6004("510228197011286278","宗政");
		httpURLConnectionPOST6004("510228197404066305","吕明淑");
		httpURLConnectionPOST6004("231003196806141639","张彦民");
		httpURLConnectionPOST6004("231005197301204021","韩丽颖");
		httpURLConnectionPOST6004("320923198309120014","高鹤权");
		httpURLConnectionPOST6004("310115198311010441","朱颖莹");
		httpURLConnectionPOST6004("320681199405213220","宋璐佳");
		httpURLConnectionPOST6004("320684199202224674","沈庆博");
		httpURLConnectionPOST6004("612322197611061817","夏小敏");
		httpURLConnectionPOST6004("612301197802152525","毛小娟");
		httpURLConnectionPOST6004("341222198808231843","邹艳");
		httpURLConnectionPOST6004("510723199204223278","陈义洪");
		httpURLConnectionPOST6004("510723199204223278","陈丽洪");
		httpURLConnectionPOST6004("239005199008202330","牛鹏革");
		httpURLConnectionPOST6004("23102519920408152X","高铭");
		httpURLConnectionPOST6004("433122197906161029","姚春芳");
		httpURLConnectionPOST6004("433122197906161029","姚玉芳");
		httpURLConnectionPOST6004("433122196810281013","肖贤希");
		httpURLConnectionPOST6004("433122196810281013","肖贤曦");
		httpURLConnectionPOST6004("512926197005156474","章友权");
		httpURLConnectionPOST6004("512926197511026461","吴晓英");
		httpURLConnectionPOST6004("610427199402121347","白娟");
		httpURLConnectionPOST6004("610427199006071616","刘江");
		httpURLConnectionPOST6004("23050219880115072X","闯姗姗");
		httpURLConnectionPOST6004("230303197712224310","庞劲松");
		httpURLConnectionPOST6004("341227198803231010","董京京");
		httpURLConnectionPOST6004("341227198506181029","刘迎迎");
		httpURLConnectionPOST6004("341227198506181029","刘秀秀");
		httpURLConnectionPOST6004("530322198202192472","范国林");
		httpURLConnectionPOST6004("530322198302012424","代克琼");
		httpURLConnectionPOST6004("51032119940203692X","吴春梅");
		httpURLConnectionPOST6004("510321199403055313","王侠");
		httpURLConnectionPOST6004("500224198710123336","汪长顺");
		httpURLConnectionPOST6004("362526199102202929","彭春梅");*/

		/*httpURLConnectionPOST6004("511521199608064864","虞婷");
		httpURLConnectionPOST6004("320382198106286839","刘宇轩");
		httpURLConnectionPOST6004("320382198106286839","刘飞龙");
		httpURLConnectionPOST6004("320382198106286839","耿华义");
		httpURLConnectionPOST6004("360502199207160435","邹睿");
		httpURLConnectionPOST6004("433123198410135477","许勋");
		httpURLConnectionPOST6004("34120419930803161X","王浪");
		httpURLConnectionPOST6004("340881199502184315","朱珍珍");
		httpURLConnectionPOST6004("350583198905076697","叶青峰");
		httpURLConnectionPOST6004("532531199512121833","李刚山");
		httpURLConnectionPOST6004("420325198802181914","解勇");
		httpURLConnectionPOST6004("150124199611217031","赵智伟");
		httpURLConnectionPOST6004("510724199010204032","曾小东");
		httpURLConnectionPOST6004("510724199010204032","曾东东");*/
		for (int i = 1;i<=10;i++) {
			long starTime = System.currentTimeMillis();
			httpURLConnectionPOST6004("511521199608064864", "虞婷");
			long endTime = System.currentTimeMillis();
			long Time = endTime - starTime;
			System.out.println("请求大数据平台耗时" + Time + "毫秒");

		}


	}
}
