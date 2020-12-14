package com.yondervision.mi.test;

import java.io.BufferedReader;  
import java.io.DataOutputStream;  
import java.io.InputStreamReader;  
import java.net.HttpURLConnection;  
import java.net.URL;  
import java.net.UnknownHostException;  
import java.security.MessageDigest;

import org.apache.axis2.json.gson.rpc.JsonUtils;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class HttpSend100 {
	private static final Logger log = Logger.getRootLogger();

	//public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/appapi50006.json";
	private static final String clientIp_V="10.19.13.114";

	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/appapi50006.json";
	public static String encrypt(String value) {
		if(value==null||value.trim().length()==0){
			return null;
		}
		MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        byte[] byteArray = value.getBytes();
        byte[] md5Bytes = md5.digest(byteArray);        
        return String.valueOf(Hex.encodeHex(md5Bytes));
	}

	public static void httpURLConnectionPOST (String xm,String sfz,String ywid,String ryid) {  
	   log.info("xm===="+xm+"sfz==="+sfz+"ywid====="+ywid+"ryid"+ryid);
		String result = "";
		String sql = "";
		try {  
			URL url = new URL(POST_URL);  
			// ��url �� open�������ص�urlConnection  ����ǿתΪHttpURLConnection����  (��ʶһ��url�����õ�Զ�̶�������)  
			// ��ʱconnectionֻ��Ϊһ�����Ӷ���,��������  
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
			// �������������Ϊtrue,Ĭ��false (post �����������ķ�ʽ��ʽ�Ĵ��ݲ���)  
			connection.setDoOutput(true);  
			// ��������������Ϊtrue  
			connection.setDoInput(true);  
			// ��������ʽΪpost  
			connection.setRequestMethod("POST");  
			// post���󻺴���Ϊfalse  
			connection.setUseCaches(false);  
			// ���ø�HttpURLConnectionʵ���Ƿ��Զ�ִ���ض���  
			connection.setInstanceFollowRedirects(true);  
			// ��������ͷ����ĸ������� (����Ϊ�������ݵ�����,����Ϊ����urlEncoded������from����)  
			// application/x-javascript text/xml->xml��� application/x-javascript->json����
			//addRequestProperty�����ͬ��key���Ḳ�ǣ������ͬ�����ݻ���{name1,name2}  
			//--connection.addRequestProperty("from", "sfzh");  //��Դ�ĸ�ϵͳ  
			//�������֣�centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp
			//connection.addRequestProperty("headpara", "centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,bodyCardNumber,password,accnum");
			connection.addRequestProperty("headpara", "centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,password,accnum");	
			AesTest aes=null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String clientIp = aes.encrypt(clientIp_V.getBytes("UTF-8"));

			//userId������ܴ���
			String userId = aes.encrypt(sfz.getBytes("UTF-8"));
			String appKey = aes.encrypt("c7e45eaa6e7940abd6069400316021dd".getBytes("UTF-8"));
			String appId = aes.encrypt("yondervisionapp41".getBytes("UTF-8"));
			String certinum = aes.encrypt(sfz.getBytes("UTF-8"));			
			//String accnum = aes.encrypt("0237550054 ".getBytes("UTF-8"));	
			//String pwd = aes.encrypt("111111".getBytes("UTF-8"));
			String accnum = aes.encrypt("".getBytes("UTF-8"));	
			String pwd = aes.encrypt("".getBytes("UTF-8"));	
			
			//��������ǩ��			
			//String parm ="centerId=00057400&userId="+userId+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=41&appid="+appId+"&appkey="+appKey+"&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&bodyCardNumber="+certinum+"&password="+pwd+"&accnum="+accnum;
			String parm ="centerId=00057400&userId="+userId+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=41&appid="+appId+"&appkey="+appKey+"&appToken=&clientIp="+clientIp+"&password="+pwd+"&accnum="+accnum;

			//���ڷ���http����
			String parm1 ="centerId=00057400&userId="+userId.replace("+","%2B")+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=41&appid="+appId.replace("+", "%2B")+"&appkey="+appKey.replace("+", "%2B")+"&appToken=&clientIp="+clientIp+"&bodyCardNumber="+certinum.replace("+","%2B")+"&password="+pwd.replace("+","%2B")+"&accnum="+accnum.replace("+","%2B");
			//String parm1 ="centerId=00057400&userId="+userId.replace("+","%2B")+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=41&appid="+appId.replace("+", "%2B")+"&appkey="+appKey.replace("+", "%2B")+"&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&bodyCardNumber="+certinum.replace("+","%2B")+"&password="+pwd.replace("+","%2B")+"&accnum="+accnum.replace("+","%2B");

			 log.info("parm===="+parm);
			 log.info("parm=1==="+parm1);
			 
			//System.out.println(EncryptionByMD5.getMD5(parm.getBytes()));
			connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
			//application/x-www-form-urlencoded->�?��� ;charset=utf-8 ����Ҫ����Ȼ��Ǳ߻�������롾�����  
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");      
			// �������� (����δ��ʼ,ֱ��connection.getInputStream()��������ʱ�ŷ���,���ϸ��������������ڴ˷���֮ǰ����)  
			connection.connect();  
			// �������������,�����������������Я��Ĳ���,(�������Ϊ?���������)  
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());  
			System.out.println("���ݲ���"+parm1);  
			// ���������������  
			dataout.writeBytes(parm1);  
			// �����ɺ�ˢ�²��ر���  
			dataout.flush();   
			// ��Ҫ���׺��Բ��� (�ر���,�м�!)   
			dataout.close(); 
			//System.out.println(connection.getResponseCode());  
			// ���ӷ�������,�����������Ӧ  (�����ӻ�ȡ������������װΪbufferedReader)  
			BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));   
			String line;  
			 // �����洢��Ӧ���  
			StringBuilder sb = new StringBuilder();
			// ѭ����ȡ��,��������β��
			while ((line = bf.readLine()) != null) {  
				//sb.append(bf.readLine());  
				sb.append(line).append(System.getProperty("line.separator"));  
			}			// ��Ҫ���׺��Բ��� (�ر���,�м�!)   
			bf.close();    
			connection.disconnect(); // �������  
			System.out.println("result:"+sb.toString());
			System.out.println("flag 0000");
			result = sb.toString();
			
			
			log.info("���======="+result);
			
			/*JSONObject gjj_json = JsonUtils.str2JsonObject(result);
			
			System.out.println("recode:"+gjj_json.getString("recode"));	
			String  aa="�����֤����["+sfz+"]��ϵͳ���ж���˻�";
			System.out.println("sfz======"+aa);
			if("000000".equals(gjj_json.getString("recode"))){
		      result = gjj_json.getString("result");
		      System.out.println("000000:"+result);	
			  String accnum_r = "";
			  String accname = "";
			  String certinum_r = "";
			  String actmp100 = "";
			  String actmp1024 = "";
			  String actmp200 = "";
			  String amt = "";
			  String amt3 = "";
			  String amt7 = "";
			  String amt8 = "";
			  String bal = "";
			  String bankname = "";
			  String bankcode = "";
			  String basenum = "";
			  String begdate = "";
			  String cardstate = "";
			  String certitype = "";
			  String freeuse1 = "";
			  String frzamt = "";
			  String frzflag = "";
			  String handset = "";
			  String indiaccstate = "";
			  String lpaym = "";
			  String monintamt = "";
			  String monpaysum = "";
			  String subintamt = "";
			  String supintamt = "";
			  String unitaccname = "";
			  String accinstcode = "";
			  String accinstcodedes = "";
			  
			  JSONArray jsonArray = null;
			  jsonArray = gjj_json.getJSONArray("result");
			  
			  System.out.println("jsonArray:"+jsonArray.size());
			  
			  for (int i = 0; i < jsonArray.size(); i++) {
				  gjj_json = jsonArray.getJSONObject(i);
				  
				  if("accnum".equals(gjj_json.getString("name"))){
					  accnum_r = gjj_json.getString("info");
				  }else if("accname".equals(gjj_json.getString("name"))){
					  accname = gjj_json.getString("info");
				  }else if("certinum".equals(gjj_json.getString("name"))){
					  certinum_r = gjj_json.getString("info");
				  }else if("actmp100".equals(gjj_json.getString("name"))){
					  actmp100 = gjj_json.getString("info");
				  }else if("actmp1024".equals(gjj_json.getString("name"))){
					  actmp1024 = gjj_json.getString("info");
				  }else if("actmp200".equals(gjj_json.getString("name"))){
					  actmp200 = gjj_json.getString("info");
				  }else if("amt".equals(gjj_json.getString("name"))){
					  amt = gjj_json.getString("info");
				  }else if("amt3".equals(gjj_json.getString("name"))){
					  amt3 = gjj_json.getString("info");
				  }else if("amt7".equals(gjj_json.getString("name"))){
					  amt7 = gjj_json.getString("info");
				  }else if("amt8".equals(gjj_json.getString("name"))){
					  amt8 = gjj_json.getString("info");
				  }else if("bal".equals(gjj_json.getString("name"))){
					  bal = gjj_json.getString("info");
				  }else if("bankname".equals(gjj_json.getString("name"))){
					  bankname = gjj_json.getString("info");
				  }else if("bankcode".equals(gjj_json.getString("name"))){
					  bankcode = gjj_json.getString("info");
				  }else if("basenum".equals(gjj_json.getString("name"))){
					  basenum = gjj_json.getString("info");
				  }else if("begdate".equals(gjj_json.getString("name"))){
					  begdate = gjj_json.getString("info");
				  }else if("cardstate".equals(gjj_json.getString("name"))){
					  cardstate = gjj_json.getString("info");
				  }else if("certitype".equals(gjj_json.getString("name"))){
					  certitype = gjj_json.getString("info");
				  }else if("freeuse1".equals(gjj_json.getString("name"))){
					  freeuse1 = gjj_json.getString("info");
				  }else if("frzamt".equals(gjj_json.getString("name"))){
					  frzamt = gjj_json.getString("info");
				  }else if("frzflag".equals(gjj_json.getString("name"))){
					  frzflag = gjj_json.getString("info");
				  }else if("handset".equals(gjj_json.getString("name"))){
					  handset = gjj_json.getString("info");
				  }else if("indiaccstate".equals(gjj_json.getString("name"))){
					  indiaccstate = gjj_json.getString("info");
				  }else if("lpaym".equals(gjj_json.getString("name"))){
					  lpaym = gjj_json.getString("info");
				  }else if("monintamt".equals(gjj_json.getString("name"))){
					  monintamt = gjj_json.getString("info");
				  }else if("monpaysum".equals(gjj_json.getString("name"))){
					  monpaysum = gjj_json.getString("info");
				  }else if("subintamt".equals(gjj_json.getString("name"))){
					  subintamt = gjj_json.getString("info");
				  }else if("supintamt".equals(gjj_json.getString("name"))){
					  supintamt = gjj_json.getString("info");
				  }else if("unitaccname".equals(gjj_json.getString("name"))){
					  unitaccname = gjj_json.getString("info");
				  }else if("accinstcode".equals(gjj_json.getString("name"))){
					  accinstcode = gjj_json.getString("info");
				  }else if("accinstcodedes".equals(gjj_json.getString("name"))){
					  accinstcodedes = gjj_json.getString("info");
				  }
			  }
			  
			  log.info("sql===******************========"+sql);
			  //CodingUtils.getCoding("0502")
			  sql = " begin insert into dsr_hc_gjj_2017(gjid,gjhcid,gj_1,gj_2,gj_3,gj_4,gj_5,gj_6,gj_7,gj_8,gj_9,gj_10,gj_11,gj_12,gj_13," +
			  		"gj_14,gj_15,gj_16,gj_17,gj_18,gj_19,gj_20,gj_21,gj_22,gj_23,gj_24,gj_25,gj_26,gj_27,gj_28,gj_29,gj_30) values(" +
			  		"'"+CodingUtils.getCoding("0101")+"','"+ywid+"','"+accname+"','"+certinum_r+"','"+accnum_r+"','"+actmp100+"'," +
			  		"'"+actmp1024+"','"+actmp200+"','"+amt+"','"+amt3+"','"+amt7+"','"+amt8+"','"+bal+"','"+bankname+"','"+bankcode+"','"+basenum+"'," +
			  		"'"+begdate+"','"+cardstate+"','"+certitype+"','"+freeuse1+"','"+frzamt+"','"+frzflag+"','"+handset+"','"+indiaccstate+"'," +
			  		"'"+lpaym+"','"+monintamt+"','"+monpaysum+"','"+subintamt+"','"+supintamt+"','"+unitaccname+"','"+accinstcode+"','"+accinstcodedes+"') ; ";
			
			  sql = sql + " update dsr_hc_gjj_request set h55=2,hsjts=1 where ywid='"+ywid+"' and id='"+ryid+"'; end ";
			  log.info("sql==========="+sql);
			  
			}else if("999999".equals(gjj_json.getString("recode")) && "����������֤����δ��ҵ��ϵͳ�п�������ͽ���ѯ��������ҵ��ר���δ򹫻������12329��".equals(gjj_json.getString("msg"))){
				System.out.println("����999999�����֤δ������");
				sql = " begin update dsr_hc_gjj_request set h55=2,hsjts=0 where ywid='"+ywid+"' and id='"+ryid+"'; end ";
				log.info("sql2222222==========="+sql);
			}else  if("999999".equals(gjj_json.get("recode"))&&gjj_json.getString("msg").contains(aa)){
			System.out.println("�����֤����"+sfz+"��ϵͳ���ж���˻�");
			
			  sql = " begin insert into dsr_hc_gjj_2017(gjid,gjhcid,gj_1,gj_2,gj_31) values(" +
				  		"'"+CodingUtils.getCoding("0101")+"','"+ywid+"','"+xm+"','"+sfz+"','"+gjj_json.getString("msg")+"') ; ";
			
				  sql = sql + " update dsr_hc_gjj_request set h55=2,hsjts=1 where ywid='"+ywid+"' and id='"+ryid+"'; end";
				  log.info("sql33333333333==========="+sql);
			
			sql = " begin insert into dsr_hc_gjj_2017(gjid,gjhcid,gj_2,gj_31) values(" +
				  		"'"+CodingUtils.getCoding("0101")+"','"+ywid+"','"+sfz+"','"+gjj_json.getString("msg")+"') ; ";			
			sql = sql + " update dsr_hc_gjj_request set h55=2,hsjts=1 where ywid='"+ywid+"' and id='"+ryid+"'; end ";
		}else{
				//Log.error("��ѯ���ɹ�������˿ڣ�");
				System.out.println("flag:1111");
			}*/

			
		} catch (Exception e) {  
			e.printStackTrace();  
		}
	}
	
	public static void main(String[] args) throws UnknownHostException {  

		httpURLConnectionPOST("","33020419821220104X","","");  		
	}

}
