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
//省国土资源厅不动产权证（新）--市平台
public class HttpSend5959appapi00170 {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	//public static final String POST_URL = "http://172.16.10.96:7001/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5959(
			//定义常量
			String qlr,
			String zjh,
			String zl,
			String bdcqzh,
			String xzqbm
			) {
		try {
			//设置url连接参数
			URL url = new URL(POST_URL + "appapi00170.json");
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

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5959&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+ "&qlr="+qlr
				 		+ "&zjh="+ zjh
				 		+ "&zl="+ zl
						+ "&bdcqzh="+bdcqzh
						+ "&xzqbm="+xzqbm
						;

			 String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=40";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=40";
//			System.out.println("本地参数" + parm);
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
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) throws UnknownHostException {
		for (int i = 0; i < 2; i++) {
//			httpURLConnectionPOST5959("","","","320919197301077497","320919197301077497","GJJ","","臧宏民","臧宏民","45号305");

//			httpURLConnectionPOST5959("其他-02492-000","其他-02492-002","缴存和户籍所在地家庭住房情况证明","330203197706282416","330203197706282416","GJJ","","金斌","金斌","502室");
//			httpURLConnectionPOST5959("其他-02492-000","其他-02492-002","房屋土地性质","330203197706282416","330203197706282416","GJJ","","金斌","金斌","502室");
//			httpURLConnectionPOST5959("其他-02492-000","其他-02492-002","房屋类型","330203197706282416","330203197706282416","GJJ","","金斌","金斌","502室");
//			httpURLConnectionPOST5959("其他-02492-000","其他-02492-002","不动产权号","330203197706282416","330203197706282416","GJJ","","金斌","金斌","502室");
//			httpURLConnectionPOST5959("其他-02492-000","其他-02492-002","房屋地址","330203197706282416","330203197706282416","GJJ","","金斌","金斌","502室");
//			httpURLConnectionPOST5959("其他-02492-000","其他-02492-002","出让方或受让方的不动产证（房屋所有权证、土地使用权证及契证）","330203197706282416","330203197706282416","GJJ","","金斌","金斌","502室");
//			httpURLConnectionPOST5959("其他-02492-000","其他-02492-003","建筑面积","330203197706282416","330203197706282416","GJJ","","金斌","金斌","502室");
//			httpURLConnectionPOST5959("其他-02492-000","其他-02492-003","房屋地址","330203197706282416","330203197706282416","GJJ","","金斌","金斌","502室");
//			httpURLConnectionPOST5959("其他-02492-000","其他-02492-003","缴存和户籍所在地家庭住房情况证明","330203197706282416","330203197706282416","GJJ","","金斌","金斌","502室");
//			httpURLConnectionPOST5959("其他-02492-000","其他-02492-003","不动产证（房屋所有权证、土地使用权证及契证）","330203197706282416","330203197706282416","GJJ","","金斌","金斌","502室");
//			httpURLConnectionPOST5959("其他-02492-000","其他-02492-003","家庭住房套数","330203197706282416","330203197706282416","GJJ","","金斌","金斌","502室");
//			httpURLConnectionPOST5959("其他-02492-000","其他-02492-003","房屋类型","330203197706282416","330203197706282416","GJJ","","金斌","金斌","502室");
//			httpURLConnectionPOST5959("其他-02492-000","其他-02492-003","不动产权号","330203197706282416","330203197706282416","GJJ","","金斌","金斌","502室");
//			httpURLConnectionPOST5959("其他-02492-000","其他-02492-003","房屋土地性质","330203197706282416","330203197706282416","GJJ","","金斌","金斌","502室");
//			httpURLConnectionPOST5959("其他-02492-000","其他-02492-004","不动产证（房屋所有权证、土地使用权证及契证）、不动产权属登记证明","330203197706282416","330203197706282416","GJJ","","金斌","金斌","502室");
//			httpURLConnectionPOST5959("330726198810170330","330726198810170330","GJJ","330726198810170330","张鹏程","张鹏程","胜丰路78弄11号","","");
/*			httpURLConnectionPOST5959("330726198810170330","330726198810170330","GJJ","","张鹏程","张鹏程","胜丰路78弄");
			httpURLConnectionPOST5959("330282199210120047","330282199210120047","GJJ","","柴梦芸","柴梦芸","玉兰苑285");
			httpURLConnectionPOST5959("330225197812114019","330225197812114019","GJJ","","杨亨武","杨亨武","靖南大街");
			httpURLConnectionPOST5959("330206199109203431","330206199109203431","GJJ","","齐达","齐达","庐山西路88号(庐山花园)20幢801");*/
			//httpURLConnectionPOST5959("屠诺宁","330283198810240028","","","330200");
			//httpURLConnectionPOST5959("屠诺宁","330283198810240028","","","330203");
			//httpURLConnectionPOST5959("屠诺宁","330283198810240028","","","330205");
			//httpURLConnectionPOST5959("屠诺宁","330283198810240028","","","330206");
			//httpURLConnectionPOST5959("屠诺宁","330283198810240028","","","330211");
			//httpURLConnectionPOST5959("屠诺宁","330283198810240028","","","330212");
			//httpURLConnectionPOST5959("屠诺宁","330283198810240028","","","330213");
			//httpURLConnectionPOST5959("屠诺宁","330283198810240028","","","330225");
			//httpURLConnectionPOST5959("屠诺宁","330283198810240028","","","330226");
			//httpURLConnectionPOST5959("屠诺宁","330283198810240028","","","330281");
			//httpURLConnectionPOST5959("屠诺宁","330283198810240028","","","330282");
			long starTime=System.currentTimeMillis();
			/*httpURLConnectionPOST5959("袁伟","330902198608168210","镇海区庄市街道同心路150号万科城33-1号-1-382室","浙(2018)宁波市(镇海)不动产权第0007856号","330211");

			*//*httpURLConnectionPOST5959("袁伟","330902198608168210","镇海区庄市街道同心路150号万科城33-1号-1-382室","浙(2018)宁波市(镇海)不动产权第0007856号","330200");
			httpURLConnectionPOST5959("袁伟","330902198608168210","镇海区庄市街道同心路150号万科城33-1号-1-382室","浙(2018)宁波市(镇海)不动产权第0007856号","330203");
			httpURLConnectionPOST5959("袁伟","330902198608168210","镇海区庄市街道同心路150号万科城33-1号-1-382室","浙(2018)宁波市(镇海)不动产权第0007856号","330205");
			httpURLConnectionPOST5959("袁伟","330902198608168210","镇海区庄市街道同心路150号万科城33-1号-1-382室","浙(2018)宁波市(镇海)不动产权第0007856号","330206");
			httpURLConnectionPOST5959("袁伟","330902198608168210","镇海区庄市街道同心路150号万科城33-1号-1-382室","浙(2018)宁波市(镇海)不动产权第0007856号","330211");
			httpURLConnectionPOST5959("袁伟","330902198608168210","镇海区庄市街道同心路150号万科城33-1号-1-382室","浙(2018)宁波市(镇海)不动产权第0007856号","330212");
			httpURLConnectionPOST5959("袁伟","330902198608168210","镇海区庄市街道同心路150号万科城33-1号-1-382室","浙(2018)宁波市(镇海)不动产权第0007856号","330213");
			httpURLConnectionPOST5959("袁伟","330902198608168210","镇海区庄市街道同心路150号万科城33-1号-1-382室","浙(2018)宁波市(镇海)不动产权第0007856号","330225");
			httpURLConnectionPOST5959("袁伟","330902198608168210","镇海区庄市街道同心路150号万科城33-1号-1-382室","浙(2018)宁波市(镇海)不动产权第0007856号","330226");
			httpURLConnectionPOST5959("袁伟","330902198608168210","镇海区庄市街道同心路150号万科城33-1号-1-382室","浙(2018)宁波市(镇海)不动产权第0007856号","330281");
			httpURLConnectionPOST5959("袁伟","330902198608168210","镇海区庄市街道同心路150号万科城33-1号-1-382室","浙(2018)宁波市(镇海)不动产权第0007856号","330282");
*/

			//httpURLConnectionPOST5959("杭州云禾置业有限公司","913301100639778394","","","330110");
			//罗旭娇 330282198610173442 新境公寓 330282
			httpURLConnectionPOST5959("","330282198610173442","","","");
			httpURLConnectionPOST5959("","330726198810170330","","","");
			httpURLConnectionPOST5959("","330282199106238635","","","");
			httpURLConnectionPOST5959("","33022719881010536X","","","");

			/*httpURLConnectionPOST5959("夏尚俊","422823197004034452","","","330200");
			httpURLConnectionPOST5959("夏尚俊","422823197004034452","","","330203");
			httpURLConnectionPOST5959("夏尚俊","422823197004034452","","","330205");
			httpURLConnectionPOST5959("夏尚俊","422823197004034452","","","330206");
			httpURLConnectionPOST5959("夏尚俊","422823197004034452","","","330211");
			httpURLConnectionPOST5959("夏尚俊","422823197004034452","","","330212");
			httpURLConnectionPOST5959("夏尚俊","422823197004034452","","","330213");
			httpURLConnectionPOST5959("夏尚俊","422823197004034452","","","330225");
			httpURLConnectionPOST5959("夏尚俊","422823197004034452","","","330226");
			httpURLConnectionPOST5959("夏尚俊","422823197004034452","","","330281");
			httpURLConnectionPOST5959("夏尚俊","422823197004034452","","","330282");

			httpURLConnectionPOST5959("施旖旎","330702197009200447","","","330200");
			httpURLConnectionPOST5959("施旖旎","330702197009200447","","","330203");
			httpURLConnectionPOST5959("施旖旎","330702197009200447","","","330205");
			httpURLConnectionPOST5959("施旖旎","330702197009200447","","","330206");
			httpURLConnectionPOST5959("施旖旎","330702197009200447","","","330211");
			httpURLConnectionPOST5959("施旖旎","330702197009200447","","","330212");
			httpURLConnectionPOST5959("施旖旎","330702197009200447","","","330213");
			httpURLConnectionPOST5959("施旖旎","330702197009200447","","","330225");
			httpURLConnectionPOST5959("施旖旎","330702197009200447","","","330226");
			httpURLConnectionPOST5959("施旖旎","330702197009200447","","","330281");
			httpURLConnectionPOST5959("施旖旎","330702197009200447","","","330282");*/
			//httpURLConnectionPOST5959("曹开艳","330206197902091421","","","330282");
			/*			+"&clientusercid="+clientusercid
			+ "&qlrmc="+qlrmc
			+ "&bdcqzh="+bdcqzh
			+ "&xzqbm="+xzqbm

			+ "&qlrzjh="+ qlrzjh
			+ "&zl="+ zl*/
			//330283198810240028 屠诺宁
			/*
			330200 	宁波市
			330203 	海曙区
			330205 	江北区
			330206 	北仑区
			330211 	镇海区
			330212 	鄞州区
			330213 	奉化区
			330225 	象山县
			330226 	宁海县
			330281 	余姚市
			330282 	慈溪市*/
			long endTime=System.currentTimeMillis();
			long Time=endTime-starTime;
			System.out.println("请求大数据平台耗时"+Time+"毫秒");
		}
	}
}
