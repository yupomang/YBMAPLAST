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
//宁波市存量房交易合同查询 "330282199210120047","柴梦芸","205"
public class HttpSend5989appapi00139 {
	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";
	
	public static String httpURLConnectionPOST5989(
			String accname,
			String powerMatters,
			String subPowerMatters,
			String materialName,
			String bodyCardNumber,
			String address) {
		try {
			URL url = new URL(POST_URL + "appapi00139.json");
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

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5989&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
								+ "&accname="+accname
								+ "&powerMatters="+powerMatters
								+ "&subPowerMatters="+subPowerMatters
								+ "&materialName="+materialName
								+ "&bodyCardNumber="+bodyCardNumber
								+ "&address="+address;
		 
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
//			String result=sb.toString();
			String result=aes.decrypt(sb.toString());
			System.out.println(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
    
	public static void main(String[] args) throws UnknownHostException {
		for(int i=0;i<10;i++){

			long starTime = System.currentTimeMillis();
/*		蔡思思	330327198811122001  	北仑区新碶星阳新村16幢606室、北仑区新碶星阳新村16幢储029
徐中杰	33020519870515361X  	江北区颐和名苑3幢12号203
张会会	142603198309268824  	海曙区翠柏路5号1702室
范士元	330205198211114813  	海曙区青林湾西区78幢1单元702
金绍月	330402198402110024  	北仑区霞浦东泰家园1幢储22，204室
俞景翔	330226199308110030  	宁波市鄞州区中河街道盛世天城9幢24单元410室
郑倩男	341226199103073523  	大榭开发区海城花园22号楼305室
王慧	330227198008243722  	鄞州区中河街道嵩江中路300号802室
朱聚开	362322198505027511  	海曙区石碶街道雅源北路128弄3号204室
梅启明	421022198412026017  	鄞州区朝晖路416弄144号408室
陈凯	33021119900114301X  	镇海区骆驼街道静远西路566号后世博园7号2102室
宋夏南	33021919610828002X  	余姚市城区杜义弄157号311室
徐志伟	330282198212068639  	鄞州区中河街道嵩江东路728弄99号302室
方长春	360281197001046012  	江北区慈城解放路103号602室
林晓波	330921198803292531  	镇海区招宝山街道清川路136号706室
*/
/*		柴梦芸	330282199210120000		宗汉街道曙光家园玉兰苑285号楼205室、〈D-13〉车库
		毛惠慧	330227198503057000		古塘街道吉祥新村4号楼302室
*/
//		httpURLConnectionPOST5989("330227197803016823","吴千红","宁波市鄞州区古林镇戴家新村29幢72号402室");  
//			httpURLConnectionPOST5989("柴梦芸","其他-02491-000","其他-02491-001","二手房转让合同","330282199210120047","205");  
//			httpURLConnectionPOST5989("毛惠慧","其他-02492-000","其他-02492-002","二手房转让合同","330227198503057329","302");  

			/*httpURLConnectionPOST5989("毛惠慧","其他-02492-000","其他-02492-004","购房合同","330227198503057329","302");
			httpURLConnectionPOST5989("毛惠慧","其他-02492-000","其他-02492-004","其他","330227198503057329","302");
			httpURLConnectionPOST5989("叶婧妍","其他-02492-000","其他-02492-004","其他","330211198810140041","永平西路");
			httpURLConnectionPOST5989("史明亮","其他-02492-000","其他-02492-004","其他","230502198603071377","27幢503室");
*/			httpURLConnectionPOST5989("王玲娜","其他-02492-000","其他-02492-004","其他","330225198711270025","38弄7号208");
			httpURLConnectionPOST5989("倪爱良","其他-02492-000","其他-02492-004","其他","330203196704060014","510");

/*			
 * 			httpURLConnectionPOST5989("330921198803292531","林晓波","706");  
			httpURLConnectionPOST5989("360281197001046012","方长春","602");  httpURLConnectionPOST5989("330282198212068639","徐志伟","728弄99号302");  
			httpURLConnectionPOST5989("33021919610828002X","宋夏南","311号");  
			httpURLConnectionPOST5989("33021119900114301X","陈凯","2101");  
			httpURLConnectionPOST5989("330227197803016823","吴千红","宁波市鄞州区古林镇戴家新村29幢72号402室");*/
			long endTime = System.currentTimeMillis();
			long Time = endTime - starTime;
			System.out.println("请求大数据平台耗时" + Time + "毫秒");
		}
	}
}
