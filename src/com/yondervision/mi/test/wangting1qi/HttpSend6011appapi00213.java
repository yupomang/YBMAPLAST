package com.yondervision.mi.test.wangting1qi;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//契税完税信息接口(省地税局)
public class HttpSend6011appapi00213 {
	//白天只能连接生产环境，晚上才能连接测试环境，并且测试完，需要couchbase删除缓存
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";
	
	public static String httpURLConnectionPOST6011(
		/*	"房屋坐落 syzlwz 不可为空
			产权面积 cqmj 不可为空
			承受方识别号(身份证) csfnsrsbh 不可为空"

		 */
		    String syzlwz,
			String cqmj,
			String csfnsrsbh

	) {
		try {
			URL url = new URL(POST_URL + "appapi00213.json");
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


		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6011&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+ "&syzlwz="+syzlwz + "&cqmj="+cqmj + "&csfnsrsbh="+csfnsrsbh
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

	public static void main(String[] args){
		for(int i=1;i<10;i++) {
			long starTime = System.currentTimeMillis();
		/*httpURLConnectionPOST6011("余隘人家19幢61号1201","139.14","330204197903075017");
		httpURLConnectionPOST6011("南雅街9弄40号304","52.14","33020419810525201X");*/
			httpURLConnectionPOST6011("卓蓝华庭2幢2单元1301室", "89.42", "330205198310260314");
/*		httpURLConnectionPOST6011("望春佳苑18幢36号1102","101.49","330283199008036024");
		httpURLConnectionPOST6011("春江花城2幢1703室","89.76","330683199605262814");*/

			//httpURLConnectionPOST6011("余隘人家19幢61号1201","139.14平米","330204197903075017");

	/*	{"msg":"成功","datas":{"code":"1","msg":"成功","datas":[{"BZ":"房源编号:F33020320180080105 房屋坐落位置:余隘人家19幢61号1201 房屋面积:139.14平米 合同签订时间:2013-05-12 楼层:19","DZSPHM":"80447868","JSJE":176709,"NSRMC":"徐海民","NSRQ":"2018-12-04","NSRSBH":"330204197903075017","SJJE":3534.18}],"dataCount":1},"dataCount":1,"requestId":null,"interfaces":null,"secondaryResults":null,"recode":"000000"}
		{"msg":"成功","datas":{"code":"1","msg":"成功","datas":[{"BZ":"房源编号:F33021220190008460 房屋坐落位置:宁波市鄞州区姜山镇雅旭花苑B3号地下车库020号车位 房屋面积:13.2平米 合同签订时间:2018-09-06","DZSPHM":"333021190100070675","JSJE":78636.36,"NSRMC":"郑凌飞","NSRQ":"2019-01-09","NSRSBH":"420606198311223515","SJJE":2359.09},{"BZ":"房源编号:F33021220180064450 房屋坐落位置:宁波市鄞州区姜山镇雅旭花苑2幢5号608室 房屋面积:106.07平米 合同签订时间:2017-02-11,备注：共有人比例{汪小娥50%郑凌飞50%}","DZSPHM":"80381617","JSJE":565037.28,"NSRMC":"郑凌飞","NSRQ":"2018-10-30","NSRSBH":"420606198311223515","SJJE":8475.56}],"dataCount":2},"dataCount":1,"requestId":null,"interfaces":null,"secondaryResults":null,"recode":"000000"}
		{"msg":"成功","datas":{"code":"1","msg":"成功","datas":[{"BZ":"  房源编号:F33020320180087797 房屋坐落地址:南雅街9弄40号304 权属转移面积:52.14平米 合同日期:2018-12-24,","DZSPHM":"80047994","JSJE":857142.86,"NSRMC":"陈鲁","NSRQ":"2018-12-24","NSRSBH":"33020419810525201X","SJJE":8571.43}],"dataCount":1},"dataCount":1,"requestId":null,"interfaces":null,"secondaryResults":null,"recode":"000000"}
*/

			long endTime = System.currentTimeMillis();
			long Time = endTime - starTime;
			System.out.println("请求大数据平台耗时" + Time + "毫秒");
		}
	}
}
