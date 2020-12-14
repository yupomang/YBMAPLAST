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

//民政部_婚姻登记信息核验(双方)
public class HttpSend6005appapi00207 {
	//白天只能连接生产环境，晚上才能连接测试环境，并且测试完，需要couchbase删除缓存
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";
	
	public static String httpURLConnectionPOST6005(
		/*	男方身份证号 cert_num_man
			男方姓名 name_man
			女方身份证号 cert_num_woman
			女方姓名 name_woman
 		*/

		    String cert_num_man,
			String name_man,
			String cert_num_woman,
			String name_woman
	) {
		try {
			URL url = new URL(POST_URL + "appapi00207.json");
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


		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6005&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+ "&cert_num_man="+cert_num_man
				 		+ "&name_man="+name_man
				 		+ "&cert_num_woman="+cert_num_woman
				 		+ "&name_woman="+name_woman
				 ;
			//System.out.println(Para);
		 
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

	public static void main(String[] args) throws UnknownHostException {
		//本地，夫妻
		//吴益斌   330204199103161012
		//许婷     330203199107300626
		//
		//曹玉婷   330205199201121221
		//葛崇渊   330226198710011271    （未交公积金）
		//省外，夫妻
		//陈昐上   340322199005306812
		//张倩     340322198904206984
		//
		//张秀丽   340603196904221023
		//肖长杰   340603196910061011
		//
		//赵美荣   341226198611262326
		//张峰     341225198602282038
		//
		//谢端林   362421198706030812
		//王倩     362421198803026524      （未交公积金）
		/*httpURLConnectionPOST6005("330204199103161012","吴益斌","330203199107300626","许婷");
		httpURLConnectionPOST6005("330226198710011271","葛崇渊","330205199201121221","曹玉婷");
		httpURLConnectionPOST6005("340322199005306812","陈昐上","340322198904206984","张倩");
		httpURLConnectionPOST6005("340603196910061011","肖长杰","340603196904221023","张秀丽");
		httpURLConnectionPOST6005("341225198602282038","张峰","341226198611262326","赵美荣");
		httpURLConnectionPOST6005("362421198706030812","谢端林","362421198803026524","王倩");*/
		//httpURLConnectionPOST6005("330224197309114314","赵盈龙","420821197202046249","王红");
		/*httpURLConnectionPOST6005("330302197903011614","池策","310230197901061489","顾建丽");
		httpURLConnectionPOST6005("341125197608192930","陶永民","34112519850120238X","陆媛媛");
		httpURLConnectionPOST6005("341203197906254439","武其光","342101198012217628","闫萍");
		httpURLConnectionPOST6005("341223198210271310","董建志","341621198302063526","龚晴");*/

		/*httpURLConnectionPOST6005("341203198811234050","华磊磊","341203198912103228","李优");
		httpURLConnectionPOST6005("321085197912265214","韩小军","321283198002245225","季春红");
		httpURLConnectionPOST6005("612401197405281476","荆纪锋","612401197802042583","向茂雪");
		httpURLConnectionPOST6005("612401198309221312","邹恩伟","612401198608013281","王宗艳");
		httpURLConnectionPOST6005("610328198006262419","邢亚军","610322198405304526","马玲芳");*/

		/*httpURLConnectionPOST6005("320102197810311252","劳有凯","452824197508184480","黄梅英");
		httpURLConnectionPOST6005("432503198606155117","肖毅斌","432503198804247020","梁礼均");
		httpURLConnectionPOST6005("432501198310106014","龚鸿督","432524198509221241","肖金霞");
		httpURLConnectionPOST6005("431224198707072892","张良容","433024198806053620","杨艳");

		httpURLConnectionPOST6005("420683198603272119","杜伟","42068319851121216X","高海丽");
		httpURLConnectionPOST6005("420624198904302617","张显成","420624198810131861","张萍");
		httpURLConnectionPOST6005("511027198209195695","苏波","510724198310201226","蔡欢");
		httpURLConnectionPOST6005("510824197803015879","张继平","510824198203145762","谯兴芳");*/

		//httpURLConnectionPOST6005("360421199101102038","张觉慧","360421199107033248","吴晶星");
		/*httpURLConnectionPOST6005("360421199107033248","吴晶星","360421199101102038","张觉慧");
		httpURLConnectionPOST6005("21122319910701141X","王志强","211223198907251062","国新");
		httpURLConnectionPOST6005("421081198311070676","王广海","360429198609222328","夏欣");
		httpURLConnectionPOST6005("330204197011271012","傅红卫","320325198001309224","汪带弟");
		*///httpURLConnectionPOST6005("330204197802206014","马力","430304197809152062","成红英");
		/*httpURLConnectionPOST6005("330421198704020058","黄利斌","320582198810318524","王晓");
		httpURLConnectionPOST6005("510228197011286278","宗政","510228197404066305","吕明淑");
		httpURLConnectionPOST6005("231003196806141639","张彦民","231005197301204021","韩丽颖");
		httpURLConnectionPOST6005("320923198309120014","高鹤权","310115198311010441","朱颖莹");
		httpURLConnectionPOST6005("320684199202224674","沈庆博","320681199405213220","宋璐佳");
		httpURLConnectionPOST6005("612322197611061817","夏小敏","612301197802152525","毛小娟");
		httpURLConnectionPOST6005("510723199204223278","陈义洪","341222198808231843","邹艳");*/
		/*httpURLConnectionPOST6005("510723199204223278","陈丽洪","341222198808231843","邹艳");
		httpURLConnectionPOST6005("239005199008202330","牛鹏革","23102519920408152X","高铭");
		httpURLConnectionPOST6005("433122196810281013","肖贤希","433122197906161029","姚春芳");*/
		/*httpURLConnectionPOST6005("433122196810281013","肖贤曦","433122197906161029","姚玉芳");
		httpURLConnectionPOST6005("512926197005156474","章友权","512926197511026461","吴晓英");
		httpURLConnectionPOST6005("610427199006071616","刘江","610427199402121347","白娟");
		httpURLConnectionPOST6005("230303197712224310","庞劲松","23050219880115072X","闯姗姗");
		httpURLConnectionPOST6005("341227198803231010","董京京","341227198506181029","刘迎迎");
		httpURLConnectionPOST6005("530322198202192472","范国林","530322198302012424","代克琼");
		httpURLConnectionPOST6005("510321199403055313","王侠","51032119940203692X","吴春梅");
*/
		for (int i = 1;i<=10;i++) {
			long starTime = System.currentTimeMillis();
			httpURLConnectionPOST6005("500224198710123336", "汪长顺", "362526199102202929", "彭春梅");
			long endTime = System.currentTimeMillis();
			long Time = endTime - starTime;
			System.out.println("请求大数据平台耗时" + Time + "毫秒");

		}
	}
}
