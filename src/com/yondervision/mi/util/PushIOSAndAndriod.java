package com.yondervision.mi.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;

import javapns.back.PushNotificationManager;
import javapns.back.SSLConnectionHelper;
import javapns.data.Device;
import javapns.data.PayLoad;
import net.sf.json.JSONObject;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.message.sendIos.SendParam;
import com.yondervision.mi.common.message.sendIos.SendMessage;
import com.yondervision.mi.util.baidupush.BaiduPush;

public class PushIOSAndAndriod {
	public String deviceType="";
	public String host="";
	public String port="";
	public String path="";
	public String password="";
	public String deviceToken="";	
	public String secritkey="";
	public String apikey="";
	public String userId="";
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSecritkey() {
		return secritkey;
	}

	public void setSecritkey(String secritkey) {
		this.secritkey = secritkey;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String pushMessage(PushIOSAndAndriod pia) throws TransRuntimeErrorException{
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		String message = "用户"+pia.getUserId()+"，您绑定的住房公积金信息于"+formatter.format(System.currentTimeMillis())+"被其他用户进行了绑定登录，相关个人私密信息可能存在泄漏的风险";
		if (Constants.MI105_DEVTYPE_IOS.equals(pia.getDeviceType())) {
			System.out.println("调用IOS推送:"+pia.getDeviceToken());
			PushNotificationManager pushManager = PushNotificationManager
					.getInstance();			
			try {
				/**
				 * 20151113 解决生产IOS推送密钥长度限制报错问题
				pushManager.initializeConnection(pia.getHost(), Integer.valueOf(pia.getPort()),
						pia.getPath(), pia.getPassword(),
						SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);
				System.out.println("IOS推送链接建立");
				PayLoad payLoad = new PayLoad();
				payLoad.addAlert(message);// push的内容
				//payLoad.addBadge(notReadCount);// 图标小红圈的数值
				payLoad.addSound("default");// 铃音
				payLoad.addCustomDictionary("type", "99");
				payLoad.addCustomDictionary("userId", pia.getUserId());
				System.out.println("pia.getDeviceToken() : "+pia.getDeviceToken());
				pushManager.addDevice("iPhone"+pia.getDeviceToken(), pia.getDeviceToken());
				Device client = pushManager.getDevice("iPhone"+pia.getDeviceToken());
				System.out.println("IOS推送开始");
				pushManager.sendNotification(client, payLoad);				
				System.out.println("IOS推送结束");
				pushManager.stopConnection();
				pushManager.removeDevice("iPhone"+pia.getDeviceToken());
				*/
				/**20160305 升级WAS 8.5后取消*/
				SendMessage iossend = new SendMessage();
				SendParam p = new SendParam();
				p.setTranCode("121045");
				p.setHost(pia.getHost());
				p.setPort(pia.getPort());
				p.setCertificate(pia.getPath());
				p.setPwd(pia.getPassword());
				p.setAlert(message);
				p.setBadge("0");
				p.setCustomdictionary("99");
				p.setDevice(pia.getDeviceToken());
				p.setUserid(pia.getUserId());
				p.setPushid(pia.getDeviceToken());
				iossend.send_IOS(p);
				
			}catch (Exception e) {
				e.printStackTrace();
				System.out.println("推送失败");
				//throw new TransRuntimeErrorException(ERROR.CONNECT_ERROR.getValue(),"IOS推送失败");
			}
					
		}
		// ANDROID系统
		if (Constants.MI105_DEVTYPE_ANDROID.equals(pia.getDeviceType())) {
			System.out.println("调用ANDROID推送:"+pia.getDeviceToken());
			JSONObject customDictionary = new JSONObject();
			customDictionary.put("type", "99");
			customDictionary.put("userId", pia.getUserId());
			customDictionary.put("msg", message);
			String detail = message.length() > 20 ? message.substring(0, 19) + "..." : message;
			BaiduPush baiduPush = new BaiduPush(BaiduPush.HTTP_METHOD_POST, pia.getSecritkey(), pia.getApikey());
			baiduPush.PushNotify("被其他用户绑定", detail,
					customDictionary.toString(), pia.getDeviceToken());			
		}
		return null;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	
}
