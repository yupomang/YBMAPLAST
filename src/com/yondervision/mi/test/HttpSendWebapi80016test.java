package com.yondervision.mi.test;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class HttpSendWebapi80016test {

    public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
    //public static final String POST_URL = "http://172.16.10.96:7001/YBMAPZH/";
    private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
    private static final String appId_V = "yondervisionwebservice40";
    private static final String clientIp_V="172.10.0.1";

    public static String httpURLConnectionPOST6028(String	accnum,
                                                   String	spt_indiaccstatename,
                                                   String	centerId,
                                                   String   certinum,
                                                   String   basenum,
                                                   String   userId,
                                                   String   brccode,
                                                   String   channel,
                                                   String   transdate,
                                                    String   bal) {

        try {
            URL url = new URL(POST_URL + "webapi80016.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.addRequestProperty("headpara","fullPara,centerId,channel");
            //String userId = "330203195908132417";
            AesTest aes = null;
            try {
                aes = new AesTest();
            } catch (Exception e) {
                e.printStackTrace();
            }

            String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5914&appid="
                    + appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
                    +"&accnum="+accnum
                    +"&spt_indiaccstatename="+spt_indiaccstatename
                    +"&certinum="+certinum
                    +"&brccode="+brccode
                    +"&basenum="+basenum
                    +"&transdate="+transdate
                    +"&bal="+ bal
                    +"&channel="+ channel;


            String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
            // 用于数字签名
            String parm ="fullPara="+fullPara+"&centerId=00057400&channel=40";
            // 用于发送http报文
            String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=40";
            System.out.println("本地参数" + parm);
            System.out.println("传递参数：" + parm1);
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
        String rep = "{\"msg\":\"错误信息：公共报文字段 BrcCode 未赋值\",\"recode\":\"999999\"}";
        JSONObject json1 = JSONObject.fromObject(rep);
        System.out.println(json1.get("recode").toString());
        if(json1.get("recode").equals("999999")){
            System.out.println(rep);
            //response.getOutputStream().write(json1.toString().getBytes("UTF-8"));
        }
        httpURLConnectionPOST6028("0238557595", "正常", "00057400","330283198802270016","6000.00","stgy","05740008","10","2021-03-03","8300.00");
        //for (int i = 0; i < args.length; i++) {
            //String qdapprnum = "YTJ"+ UUID.randomUUID();
            //httpURLConnectionPOST6028("0238557595", "测试住房公积金中心", "00057400","330283198802270016","宁波市住房公积金管理中心","stgy","05740008","10","2021-03-03",qdapprnum);
            //注意不能重复，证明未过期，无法出具新证明。
            /*<accnum>0237550054</><projectname>测试住房公积金中心</><qdapprnum>WT194</><*/
       // }
    }
}
