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
//省资源厅不动产权证新（测试环境接口申请未成功）
public class HttpSend5995appapi00134 {
	//白天只能连接生产环境，晚上才能连接测试环境，并且测试完，需要couchbase删除缓存
	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

/*	权利人	        qlr	权利人（必传）
	权利人证件号	zjh	权利人证件号（必传）
	不动产坐落	zl	    不动产坐落
	不动产权字号	bdcqzh	不动产权字号
	行政区编码	xzqbm	行政区编码（必传）*/
	public static String httpURLConnectionPOST5995(String accname,String certinum,String zl,String bdcqzh,String xzqbm) {
		try {
			URL url = new URL(POST_URL + "appapi00134.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "330726198810170330";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5995&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
								+ "&certinum="+certinum
		 						+ "&accname="+accname
		 						+ "&zl="+zl
		 						+ "&bdcqzh="+bdcqzh
		 						+ "&xzqbm="+xzqbm;

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
//			String result=change(aes.decrypt(sb.toString()));
//			String result=sb.toString();
//			String result=change(sb.toString());
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
//            output = new String(input.getBytes("gb18030"),"utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }
		return output; 
    }  
	public static void main(String[] args) throws UnknownHostException {
			//httpURLConnectionPOST5995("张鹏程","330726198810170330","胜丰路78弄11号501","20140065401","330203");
			httpURLConnectionPOST5995("屠诺宁","330283198810240028","胜丰路78弄11号501","20140065401","330203");
//			httpURLConnectionPOST5995("梁勇","230224198710153310","宁波市鄞州区中河街道长寿南路126弄172号3203室","浙(2018)宁波市鄞州不动产权第0007139号","330203");		
//			httpURLConnectionPOST5995("孔祖波","330205198111100318","滨湖晴园1幢2号404","浙(2018)宁波市江北不动产权第0023588号","330203");		
//			httpURLConnectionPOST5995("张鹏程","330726198810170330","胜丰路78弄11号501","20140065401","330203");		
		httpURLConnectionPOST5995("杭州云禾置业有限公司","913301100639778394","杭州市余杭区仁和街道金鼎华庭8幢2单元401室","浙(2017)余杭区不动产权第0116338号","330110");
		httpURLConnectionPOST5995("杭州云禾置业有限公司","913301100639778394","杭州市余杭区仁和街道金鼎华庭8幢2单元401室","浙(2017)余杭区不动产权第0116338号","330110");

			/*	权利人	        qlr	权利人（必传）
				权利人证件号	zjh	权利人证件号（必传）
				不动产坐落	zl	    不动产坐落
				不动产权字号	bdcqzh	不动产权字号
				行政区编码	xzqbm	行政区编码（必传）*/
/*			230224198710153310 浙(2018)宁波市鄞州不动产权第0007139号	梁勇 宁波市鄞州区中河街道长寿南路126弄172号3203室	05740008	不动产权第0007139号
			230622198703034662 浙(2018)宁波市鄞州不动产权第0007140号	田亚丽 宁波市鄞州区中河街道长寿南路126弄172号3203室	05740008	不动产权第0007140号
			330205198111100318 浙(2018)宁波市江北不动产权第0023588号	孔祖波 滨湖晴园1幢2号404	05740008	不动产权第0023588号
			330203198112100915 浙(2018)宁波市海曙不动产权第0010284号	王斌 新典路99弄49号202	05740008	不动产权第0010284号
			330227197806141822 浙(2017)宁波市鄞州不动产权第0578929号	林飞 宁波市鄞州区邱隘镇明湖花苑20幢46号1003室	05740008	莶欢ǖ?578929号
			330205197707074822 浙(2016)宁波市(高新)不动产权第0120631号	吴超 东海景花苑3幢199号2002	05740008	)不动产权第0120631号
			330227197806141822 浙(2017)宁波市鄞州不动产权第0578929号	林飞 宁波市鄞州区邱隘镇明湖花苑20幢46号1003室	05740008	莶欢ǖ?578929号
			330282198512114676 浙(2018)宁波市江北不动产权第0008774号	张热 姚景花园15幢33号301	05740008	辈欢ǖ?008774号
*/			
/*		    海曙区 地区编码：330203 邮编：315000 电话区号：0574
		    江东区 地区编码：330204 邮编：315000 电话区号：0574
		    江北区 地区编码：330205 邮编：315000 电话区号：0574
		    北仑区 地区编码：330206 邮编：315800 电话区号：0574
		    镇海区 地区编码：330211 邮编：315200 电话区号：0574
		    鄞州区 地区编码：330212 邮编：315100 电话区号：0574
		    象山县 地区编码：330225 邮编：315700 电话区号：0574
		    宁海县 地区编码：330226 邮编：315600 电话区号：0574
		    余姚市 地区编码：330281 邮编：315400 电话区号：0574
		    慈溪市 地区编码：330282 邮编：315300 电话区号：0574
		    奉化市 地区编码：330283 邮编：315500 电话区号：0574*/
	}
}
