package com.yondervision.mi.test.ningboshizizhuzhongduan;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;

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
 * @date 2020/9/10 10:17
 * 自助终端提前还贷测试
 */
public class NingBoZiZhuZhongDuan00262 {
    public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
    private static final String appKey_V = "78ce07bdcdf4877f56305895659defc6";
    private static final String appId_V = "yondervisionselfservice53";
    private static final String clientIp_V="172.10.0.1";

    public static String httpURLConnectionPOST5021(
            String spt_bfhkfs, String spt_gjjzhhkje,String spt_hkfs,String spt_jgh,String spt_jkhtbh,
            String spt_mlbm,String spt_mljbm,String spt_mlmc,String spt_pageno,String spt_pagesize,
            String spt_qdmc,String spt_qzlqfs,String spt_sfsygjjzh,String spt_sfzh,String spt_sjhm,
            String spt_sjrdz,String spt_sjrlxfs,String spt_sjrxm,String spt_sqrlx,String spt_sqsj,
            String spt_tqhkbj,String spt_tqhkje,String spt_tqhklx,String spt_xm,String spt_ywbh,
            String spt_ywbm,String spt_ywid,String spt_zwfwbb
    ) {

        try {
            URL url = new URL(POST_URL + "appapi00262.json");
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
                    + "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6262&devtoken=&channel=53&appid="
                    + appId
                    + "&appkey="
                    + appKey
                    + "&appToken=&clientIp=172.10.0.1&brcCode=05740008"
            ;

            // 用于发送http报文
            String parm1 = "centerId=00057400&userId="
                    + userId.replace("+", "%2B")
                    + "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6262&devtoken=&channel=53&appid="
                    + appId.replace("+", "%2B")
                    + "&appkey="
                    + appKey.replace("+", "%2B")
                    + "&appToken=&clientIp=172.10.0.1&brcCode=05740008"
                    + "&spt_bfhkfs=" + spt_bfhkfs + "&spt_gjjzhhkje=" + spt_gjjzhhkje + "&spt_hkfs="+ spt_hkfs + "&spt_jgh="+ spt_jgh
                    + "&spt_jkhtbh="+spt_jkhtbh + "&spt_mlbm=" + spt_mlbm + "&spt_mljbm=" + spt_mljbm + "&spt_mlmc="  + spt_mlmc
                    + "&spt_pageno="+spt_pageno +"&spt_pagesize="+spt_pagesize +"&spt_qdmc="+spt_qdmc + "&spt_qzlqfs=" + spt_qzlqfs + "&spt_sfsygjjzh=" + spt_sfsygjjzh
                    + "&spt_sfzh="+spt_sfzh +"&spt_sjhm="+spt_sjhm + "&spt_sjrdz=" + spt_sjrdz + "&spt_sjrlxfs=" + spt_sjrlxfs + "&spt_sjrxm=" + spt_sjrxm
                    + "&spt_sqrlx="+spt_sqrlx + "&spt_sqsj="+ spt_sqsj +"&spt_tqhkbj="+spt_tqhkbj + "&spt_tqhkje=" + spt_tqhkje + "&spt_tqhklx=" + spt_tqhklx
                    + "&spt_xm="+spt_xm +"&spt_ywbh="+spt_ywbh +"&spt_ywbm="+spt_ywbm +"&spt_ywid="+spt_ywid + "&spt_zwfwbb=" + spt_zwfwbb
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
		spt_bfhkfs      部分还款方式编码
		spt_gjjzhhkje   使用公积金账号还款金额
		spt_hkfs        还款方式编码
		spt_jgh         机构号
		spt_jkhtbh      借款合同编号
		spt_mlbm        目录编码
		spt_mljbm       目录基本码
		spt_mlmc        目录名称
		spt_pageno      分页页码
		spt_pagesize    分页大小
		spt_qdmc        渠道名称
		spt_qzlqfs      权证领取方式
		spt_sfsygjjzh   是否优先使用公积金账户还款
		spt_sfzh        身份证号码
		spt_sjhm        手机号码
		spt_sjrdz       收件人详细地址
		spt_sjrlxfs     收件人联系方式
		spt_sjrxm       收件人姓名
		spt_sqrlx       申请人类型
		spt_sqsj        申请时间
		spt_tqhkbj      申请提前还款本金
		spt_tqhkje      申请提前还款金额
		spt_tqhklx      申请提前还贷利息
		spt_xm          姓名
		spt_ywbh        业务申报号
		spt_ywbm        业务事项编码
		spt_ywid        业务申请唯一标识码
		spt_zwfwbb      政务服务版本
		*/


        httpURLConnectionPOST5021("01","2000", "02","05740008","2015001233","测试","测试",
                "测试","","","3","","0","330203197201280626","13645742938",
                "","", "","0","2020-09-15","10000.00","10000.00","0.00",URLEncoder.encode("励益平1","utf-8"),
                "","","NBZZ1912251000011720200915","2.0");
    }
}
