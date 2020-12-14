package com.yondervision.mi.shengchan.gongshangyinhangshoujiyinhang;
//一般提取计算可提取金额
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;

public class HttpSend5372appapi03808 {
	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/";
	private static final String appKey_V = "w6r8msk88243epqs3bezqiuek1z1il4t";
	private static final String appId_V = "yondervisionwebservice91";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5372(String	accnum,
			String	certitype,
			String	certinum,
			String	drawreason,
			String	drawreasoncode1,
			String	relation,
			String	buyhousedate,
			String	buyhouseamt,
			String	repaylntype,
			String	contrsigndate,
			String	loanterm,
			String	loansum,
			String	commloansum,
			String	monthrepayamt,
			String	commonthrepayamt,
			String	clearamt,
			String	cleardate,
			String	accloantype,
			String	ahdrepayamt,
			String	rentdate,
			String	rentenddate,
			String	rentamt,
			String	renttype,
			String	conamt,
			String	buyhousetype ) {
		try {
			URL url = new URL(POST_URL + "appapi03808.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "330225197902061571";
			AesTestGongShangYinHang aes = null;
			try {
				aes = new AesTestGongShangYinHang();
			} catch (Exception e) {
				e.printStackTrace();
			}
		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5372&appid="
					+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
					+"&accnum="+accnum
					+"&certitype="+certitype
					+"&certinum="+certinum
					+"&drawreason="+drawreason
					+"&drawreasoncode1="+drawreasoncode1
					+"&relation="+relation
					+"&buyhousedate="+buyhousedate
					+"&buyhouseamt="+buyhouseamt
					+"&repaylntype="+repaylntype
					+"&contrsigndate="+contrsigndate
					+"&loanterm="+loanterm
					+"&loansum="+loansum
					+"&commloansum="+commloansum
					+"&monthrepayamt="+monthrepayamt
					+"&commonthrepayamt="+commonthrepayamt
					+"&clearamt="+clearamt
					+"&cleardate="+cleardate
					+"&accloantype="+accloantype
					+"&ahdrepayamt="+ahdrepayamt
					+"&rentdate="+rentdate
					+"&rentenddate="+rentenddate
					+"&rentamt="+rentamt
					+"&renttype="+renttype
					+"&conamt="+conamt
					+"&buyhousetype="+buyhousetype;
			 String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=91";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=91";
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
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/*3. 租房提取
	信息随便写， 有最高房租限制 一般700，每次提取12个月的房租
	租房人： 0112469995 黄芳 330227197010265360 证件类型 1
	材料号： yyy111
	手机： 13615747900
	卡号： 7788004455
	提取原因： 004
	租房类型 ： 市场租赁 <renttype>23</>
	关系： 本人
	收款银行： 0101-市本级工行
	租房日期： 2016-01-01  结束日期： 2017-09-02
	房租： 100
	房屋地址： 北京市王府井大街8号*/
	
	/*2. 购房提取
	可以随便编造购房信息，比如下面
	主贷人： 0056861623 王伟柱 210602198702100016 证件类型 1
	材料号： yyy000
	手机： 13333333333
	卡号： 7788004455
	提取原因： 002
	购房类型 ： 商品房: <buyhousetype>02</> 二手房: <buyhousetype>03</>
	关系： 本人
	收款银行： 0101-市本级工行
	购房日期： 2017-01-01
	购房金额： 200000.00
	购房类型:buyhousetype  02-商品房；03-二手房
	房屋地址： 上海市南京路188号*/
	
/*	1. 偿还贷款本息提取
	主贷人： 0129073251 周成泽 320107198210182633 证件类型 1
	材料号： HT057400082016010626
	手机： 15258370006
	卡号： 3344556677889900
	提取原因： 001
	提取贷款类型accloantype： 11 - 系统内公积金贷款
	关系： 本人
	收款银行： 0101-市本级工行
	{"cleardate":"",
"monthrepayamt":"3512.66",
"recode":"000000",
"clearamt":"0.00",
"repaylntype":"0",
"buyhousedate":"",
"contrsigndate":"2016-10-27",
"msg":"成功",
"loansum":"550000.00",
"buyhousecerid":"",
"lasttrsdate":"",
"buyhouseamt":"1000000.00",
"commloansum":"0.00",
"buyhousename":"",
"commonthrepayamt":"0.00",
"houseaddr":"海曙区新典路68弄35号610，阁楼",
"ahdrepayamt":"0.00",
"loanterm":"204"}*/
	public static void main(String[] args) throws UnknownHostException {
		//httpURLConnectionPOST5372("0112469995","1","330227197010265360","004","yyy111","01","","","","","","","","","","","","","","2016-01-01","2017-09-02","100","23","100","");//租房提取		
//		httpURLConnectionPOST5372("0056861623","1","210602198702100016","002","yyy000","01","2017-01-01","200000.00","0","","","","","","","","","","","","","","","","02");//购房提取
		//httpURLConnectionPOST5372("0129073251","1","320107198210182633","001","HT057400082016010626","01","2015-10-17","1000000.00","0","2015-10-27","204","550000.00","0.00","3512.66","0.00","0.00","","11","0.00","","","","","","");//还贷提取
httpURLConnectionPOST5372("0118783856","1","330225197902061571","001","HT057400082016010626","01","2015-10-17","1000000.00","0","2015-10-27","204","550000.00","0.00","3512.66","0.00","0.00","","11","0.00","","","","","","");//还贷提取

/*		个人账号:accnum
		证件类型:certitype
		证件号码:certinum
		提取原因:drawreason
		提取材料号:drawreasoncode1
		与购房人关系:relation
		
		公贷还贷提取、商贷还款提取:
		购房日期:buyhousedate  2016-10-17
		购房金额:buyhouseamt  1000000.00
		还款类型:repaylntype  0
		合同签订日期:contrsigndate  2016-10-27
		贷款期限:loanterm  204
		公积金贷款金额:loansum  550000.00
		商贷金额:commloansum  0.00
		公贷月还款金额:monthrepayamt  3512.66
		商贷月还款金额:commonthrepayamt 0.00
		结清金额:clearamt 0.00
		结清日期:cleardate
		贷款类型:accloantype  11
		提前部分还款金额:ahdrepayamt

		租房提取:
		租房开始日期:rentdate  2016-01-01
		租房结束日期:rentenddate 2017-09-02
		月租金:rentamt   100
		租房类型:renttype  23
		政府补贴金额:conamt 100

		商品房提取（未领房产证）、二手房提取（已领房产证，二手房） :
		购房日期:buyhousedate
		购房金额:buyhouseamt
		购房类型:buyhousetype*/
	}
}
