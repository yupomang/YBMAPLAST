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
//自然人、法人信用信息核查(核心不用 不再调用)
public class HttpSend5997appapi00132 {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";
	
	public static String httpURLConnectionPOST5997(
			String	bmdm,
			String	bmmc,
			String	czrsfzhm,
			String	czrxm,
			String	hcrq,
			String	hcsy,
			String	mc,
			String	sxdm,
			String	sxmc,
			String	timestamp,
			String	tyshxydm,
			String	tysqsbm,
			String	xtjrm) {
		try {
			URL url = new URL(POST_URL + "appapi00132.json");
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

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5997&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+"&xtjrm="+xtjrm
						+"&tysqsbm="+tysqsbm
						+"&mc="+mc
						+"&tyshxydm="+tyshxydm
						+"&czrxm="+czrxm
						+"&czrsfzhm="+czrsfzhm
						+"&sxmc="+sxmc
						+"&sxdm="+sxdm
						+"&hcsy="+hcsy
						+"&bmmc="+bmmc
						+"&bmdm="+bmdm
						+"&hcrq="+hcrq
						+"&timestamp="+timestamp;
		 
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
//			System.out.println(result);
			System.out.println(change(result));
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
//            output = new String(input.getBytes("gb18030"),"utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }
		return output;  
    }  
    
	public static void main(String[] args) throws UnknownHostException {
			httpURLConnectionPOST5997("33020140","宁波市住房公积金管理中心","330205197501011235","李四","2018-06-05 12:00:00",
					"对企业主体对象进行信用核查","慈溪市振飞塑粉有限公司","09987","开业登记","2018-06-05 12:00:00",
					"91330282796027937C","345242398708457","1336668402247121");
			httpURLConnectionPOST5997("33020140","宁波市住房公积金管理中心","330205197501011235","李四","2018-06-05 12:00:00",
					"对企业主体对象进行信用核查","张仙云","09987","开业登记","2018-06-05 12:00:00",
					"332601196309114140","345242398708457","1336668402247121");  
			
		httpURLConnectionPOST5997("33020140","宁波市住房公积金管理中心","330205197401233018","裘昕亮","2018-12-05 09:13:06",
					"贷款审批失信核查","裘昕亮","128201","贷款审批","2018-12-05 09:13:06",
					"330205197401233018","008201812050009","1336668402247121");
/*			httpURLConnectionPOST5997("33021230","区地税局","330205197501011235","李四","2018-06-05 12:00:00",
					"对企业主体对象进行信用核查","慈溪市振飞塑粉有限公司","09987","开业登记","2018-06-05 12:00:00",
					"91330282796027937C","345242398708457","1336668402247121");   
			httpURLConnectionPOST5997("33021230","区地税局","330205197501011235","李四","2018-06-05 12:00:00",2
					"对企业主体对象进行信用核查","张三","09987","开业登记","2018-06-05 12:00:00",
					"330205197501011234","345242398708457","1336668402247121");    */
/*			<bmdm>33021230</bmdm>
			<bmmc>区地税局</bmmc>
			<czrsfzhm>330205197501011235</czrsfzhm>
			<czrxm>李四</czrxm>
			<hcrq>2018-06-05 12:00:00</hcrq>
			<hcsy>对企业主体对象进行信用核查</hcsy>
			<mc>慈溪市振飞塑粉有限公司</mc>
			<sxdm>09987</sxdm>
			<sxmc>开业登记</sxmc>
			<timestamp>2018-06-05 12:00:00</timestamp>
			<tyshxydm>91330282796027937C</tyshxydm>
			<tysqsbm>345242398708457</tysqsbm>
			<xtjrm>1234567891023456</xtjrm>*/
	//1336668402247121
			//自然人 330205197501011234 张三
			//宁波香叶纺织有限公司
			/*	<bmdm>33020140</bmdm>
				<bmmc>宁波市住房公积金管理中心</bmmc>
				<czrsfzhm>330205197501011235</czrsfzhm>
				<czrxm>李四</czrxm>
				<hcrq>2018-06-05 12:00:00</hcrq>
				<hcsy>对企业主体对象进行信用核查</hcsy>
				<mc>慈溪市振飞塑粉有限公司</mc>
				<sxdm>09987</sxdm>
				<sxmc>开业登记</sxmc>
				<timestamp>2018-06-05 12:00:00</timestamp>
				<tyshxydm>91330282796027937C</tyshxydm>
				<tysqsbm>345242398708457</tysqsbm>
				<xtjrm>1234567891023456</xtjrm>*/

			/*	<bmdm>33021230</bmdm>
				<bmmc>区地税局</bmmc>
				<czrsfzhm>330205197501011235</czrsfzhm>
				<czrxm>李四</czrxm>
				<hcly></hcly>
				<hcrq>2018-06-05 12:00:00</hcrq>
				<hcsy>对企业主体对象进行信用核查</hcsy>
				<mc>慈溪市振飞塑粉有限公司</mc>
				<sxdm>09987</sxdm>
				<sxmc>开业登记</sxmc>
				<timestamp>2018-06-05 12:00:00</timestamp>
				<tyshxydm>91330282796027937C</tyshxydm>
				<tysqsbm>345242398708457</tysqsbm>
				<xtjrm>1234567891023456</xtjrm>*/


	}
}
