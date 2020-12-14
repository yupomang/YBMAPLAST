package com.yondervision.mi.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yondervision.mi.common.message.SimpleHttpMessageUtil;
import com.yondervision.mi.form.AppApi00225Form;
import com.yondervision.mi.form.AppApi50004Form;

public class MessageSendUtil {
	
	public static HashMap sendSmsCheckAndMessage00057400(AppApi50004Form form)
			throws Exception
	{
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		String encoding = "UTF-8";
		String rep = "";
		String seqno = CommonUtil.getSystemDateNumOnly();
		System.out.println("【渠道主动发送短信息00057400 流水号：【" + seqno + "】 , 手机号：【" + form.getTel() + "】");
		String url = PropertiesReader.getProperty("properties.properties", "smsurl").trim();
		String mess = form.getMessage();
		HashMap hashMap = new HashMap();
		System.out.println("【渠道主动发送短信验证码信息】---要素转码前信息---【" + mess + "】:");
		System.out.println("【渠道主动发送短信验证码信息】---主题编码---【" + form.getSendTheme() + "】:");
		hashMap.put("SJHM", form.getTel());//必填，接收短信的手机号码
		hashMap.put("JGH", form.getJgh());//必填，短信发送机构号
		hashMap.put("YWLBDM", "ZH");//必填，业务类别代码
		hashMap.put("YWMCDM", "30");//必填，业务名称代码
		hashMap.put("MSG", mess);//必填，要发送的短信内容
		hashMap.put("YXJ", "1");//必填，短信优先级1-4（1最高，4最低）
//		    hashMap.put("FSSJ", "");//选填，指定发送时间发送时间(格式：YYYY-MM-DD HH24:MI:SS)，可为空。不为空是为指定时间发送，为空时为即时发送。
//		    hashMap.put("EXTNO", "");//选填，是否发送扩展号，Y=发送，一般用户发送短信后的回复使用。
//		    hashMap.put("CALLBACKURL", "");//选填，短信回复的回调地址
//		    hashMap.put("DYID", "");//选填，如果是上行短信的回复短信，需要返回DYID参数。
//		    说明：如果只是短信发送，后面的EXTNO、CALLBACKURL、DYID可不传递

		System.out.println("【渠道主动发送短信验证码信息】---等推送短信息流水号---【" + seqno + "】 URL:" + url);
		rep = msm.sendPost(url, hashMap, encoding);

		System.out.println("【渠道主动发送短信息】---等推送短信息流水号---【" + seqno + "】，返回信息为【" + rep + "】");
		System.out.println("【渠道主动发送短信息】---等推送短信息流水号---【" + seqno + "】，返回信息为【" + change(rep) + "】");

		String repString=rep.toString();
		String repflag=repString.substring(7, 8);
		System.out.println("repflag=============="+repflag);
		String miSeqno=repString.replace("】", "").substring(repString.length()-21);
		System.out.println("miSeqno=============="+miSeqno);
		HashMap remap =new HashMap();
		if (repflag.equals("1")) {
			remap.put("recode", "000000");
			remap.put("msg", "处理成功");
			remap.put("miSeqno", miSeqno);
			System.out.println("recode==============="+remap.get("recode"));
			System.out.println("msg================="+remap.get("msg"));
			System.out.println("miSeqno=============="+remap.get("miSeqno"));
			return remap;
		}
		return null;
	}

	public static HashMap ydjczm(AppApi00225Form form)
			throws Exception
	{
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		String encoding = "UTF-8";
		String rep = "";

		String url = "http://172.16.10.156:7001/gjj-wsyyt/servlet/EmpViewServlet";
		HashMap hashMap = new HashMap();

		hashMap.put("accnum", form.getAccnum());
		hashMap.put("certinum", form.getCertinum());
		hashMap.put("centername", form.getCentername());
		hashMap.put("userid", form.getUserid());
		hashMap.put("brccode", form.getBrccode());
		hashMap.put("channel", "");
		System.out.println("url======" + url);
		rep = msm.sendPost(url, hashMap, encoding);

		System.out.println("form.getAccnum()======" + form.getAccnum());
		System.out.println("form.getCertinum()======" + form.getCertinum());
		System.out.println("form.getCentername()======" + form.getCentername());
		System.out.println("form.getUserid()======" + form.getUserid());
		System.out.println("form.getBrccode()======" + form.getBrccode());
		System.out.println("form.getChannel()======" + form.getChannel());
		System.out.println("form.getProjectname()======" + form.getProjectname());
		System.out.println("form.getCenterId()======" + form.getCenterId());
		System.out.println("form.getBuzType()======" + form.getBuzType());

		System.out.println("rep=============="+rep);

		return null;
	}

	public static String change(String input) {  
    	String output = null;
        try {  
            output = new String(input.getBytes("ISO-8859-1"),"utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }
		return output;  
    } 

}
