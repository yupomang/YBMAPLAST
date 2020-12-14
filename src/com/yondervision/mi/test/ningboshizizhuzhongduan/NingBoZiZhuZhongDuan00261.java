package com.yondervision.mi.test.ningboshizizhuzhongduan;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

/**
 * @author 俞文杰
 * @version V1.0
 * @date 2020/9/9 16:12
 * 自助终端提前还贷利息查询测试
 */
public class NingBoZiZhuZhongDuan00261 {
    public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
    private static final String appKey_V = "78ce07bdcdf4877f56305895659defc6";
    private static final String appId_V = "yondervisionselfservice53";
    private static final String clientIp_V="172.10.0.1";

    public static String httpURLConnectionPOST5021(
            String spt_hkfs,String spt_jgh,String spt_jkhtbh, String spt_pageno,String spt_pagesize,String spt_qdmc,String spt_sfzh,String spt_sjhm,
            String spt_sqrlx,String spt_sqsj,String spt_tqhkbj,String spt_xm,String spt_ywbh,
            String spt_ywbm,String spt_ywid
    ) {

        try {
            URL url = new URL(POST_URL + "appapi00261.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.addRequestProperty("headpara","centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,brcCode");
            AesTestZiZhu aes = null;
            try {
                aes = new AesTestZiZhu();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String userId = aes.encrypt("0000".getBytes("UTF-8"));
            String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
            String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
            String parm = "";
            // 用于数字签名
            parm = "centerId=00057400&userId="
                    + userId
                    + "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6261&devtoken=&channel=53&appid="
                    + appId
                    + "&appkey="
                    + appKey
                    + "&appToken=&clientIp=172.10.0.1&brcCode=05740008"
            ;

            // 用于发送http报文
            String parm1 = "centerId=00057400&userId="
                    + userId.replace("+", "%2B")
                    + "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6261&devtoken=&channel=53&appid="
                    + appId.replace("+", "%2B")
                    + "&appkey="
                    + appKey.replace("+", "%2B")
                    + "&appToken=&clientIp=172.10.0.1&brcCode=05740008"
                    + "&spt_hkfs="+ spt_hkfs + "&spt_jgh="+ spt_jgh +"&spt_jkhtbh="+spt_jkhtbh +"&spt_pageno="+spt_pageno +"&spt_pagesize="+spt_pagesize +"&spt_qdmc="+spt_qdmc +"&spt_sfzh="+spt_sfzh +"&spt_sjhm="+spt_sjhm +"&spt_sqrlx="+spt_sqrlx
                    + "&spt_sqsj="+ spt_sqsj +"&spt_tqhkbj="+spt_tqhkbj +"&spt_xm="+spt_xm +"&spt_ywbh="+spt_ywbh +"&spt_ywbm="+spt_ywbm +"&spt_ywid="+spt_ywid
                    ;

            System.out.println("parm" + parm);
            System.out.println("parm1" + parm1);
            connection.addRequestProperty("headparaMD5", RSASignature.sign(
                    EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
            connection.connect();
            DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
            System.out.println("传递参数：" + parm1);
            dataout.writeBytes(parm1);
            dataout.flush();
            dataout.close();
            BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bf.readLine()) != null) {
                sb.append(line).append(System.getProperty("line.separator"));
            }
            // 重要且易忽略步骤 (关闭流,切记!)
            bf.close();
            connection.disconnect(); // 销毁连接
            System.out.println(sb.toString());
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) throws UnknownHostException, UnsupportedEncodingException {
		/*查询
		spt_hkfs        还款方式编码
		spt_jgh         机构号
		spt_jkhtbh      借款合同编号
		spt_pageno      分页页码
		spt_pagesize    分页大小
		spt_qdmc        渠道名称
		spt_sfzh        身份证号码
		spt_sjhm        手机号码
		spt_sqrlx       申请人类型
		spt_sqsj        申请时间
		spt_tqhkbj      申请提前还款本金
		spt_xm          姓名
		spt_ywbh        业务申报号
		spt_ywbm        业务事项编码
		spt_ywid        业务申请唯一标识码
		*/


        //httpURLConnectionPOST5021("02","05740008", "2014004900","","","3","330726198810170330",
               // "15867529638","0","2020-09-15","10000",URLEncoder.encode("张鹏程","utf-8"),"","","NBZZ1912251000011720200915");

        /*String result = "{\"code\":\"00\",\"msg\":\"成功\",\"data\":\"\",\"datas\":\"[{\\\"ZSXMMC\\\":\\\"契税\\\",\\\"JSJE\\\":942857.14,\\\"NSRMC\\\":\\\"张乐乐\\\",\\\"SKSSQQ\\\":\\\"2019-04-01\\\",\\\"NSRQ\\\":\\\"2019-04-22\\\",\\\"BZ\\\":\\\" 共有人：张乐乐，王家琴  房源编号:F33021120190010239 房屋坐落地址:镇海区九龙湖镇滕山路359号恒大山水城·明苑1号201室 权属转移面积:88.1平米 合同日期:2019-04-22,\\\",\\\"SWJGMC\\\":\\\"国家税务总局宁波市镇海区税务局骆驼税务所\\\",\\\"NSRSBH\\\":\\\"341224198912313075\\\",\\\"SJJE\\\":9428.58,\\\"SKSSQZ\\\":\\\"2019-04-30\\\",\\\"DZSPHM\\\":\\\"333021190400112108\\\",\\\"ZSPMMC\\\":\\\"存量房（商品住房买卖）\\\",\\\"KSSL\\\":88.1,\\\"SL_1\\\":0.03}]\",\"dataCount\":1,\"requestId\":null,\"interfaces\":null,\"secondaryResults\":null}";
        System.out.println("++++++++++++++++++appapiend!!!");
        JSONObject json = JSONObject.fromObject(result.replace("\\", "").replace(":\"{", ":{").replace("}\",", "},").replace("%},", "%}\",").replace(":\"[",":[").replace("}]\"","}]"));
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");
        System.out.println("++++++++++++++++++appapiend!!!");*/
    }
}
