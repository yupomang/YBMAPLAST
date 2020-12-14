package com.yondervision.mi.test.wangting1qi;
//一般提取
import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class HttpSend5370appapi03806 {
	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5370(String	payeebankcode,
			String	payeebankaccnum,
			String	accnum,
			String	accname,
			String	certitype,
			String	certinum ,
			String	frzflag,
			String	drawreason,
			String	qdapprnum,
			String	drawreasoncode1,
			String	bankcode,
			String	buyhousename,
			String	buyhousecerid,
			String	buyhousedate,
			String	houseaddr,
			String	buyhouseamt,
			String	repaylntype ,
			String	contrsigndate,
			String	loanterm,
			String	loansum,
			String	commloansum ,
			String	monthrepayamt,
			String	relation,
			String	commonthrepayamt,
			String	lasttrsdate,
			String	inputamt,
			String	rentname,
			String	rentcertinum,
			String	rentdate,
			String	rentenddate,
			String	rentamt,
			String	renttype,
			String	unitaccnum,
			String	handset,
			String	buyhousetype,
			String	accloantype, String   appramt , String apprdate) {
		try {
			URL url = new URL(POST_URL + "appapi03806.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "0112469995";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}
		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5370&appid="
					+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
					+"&payeebankcode="+payeebankcode
					+"&payeebankaccnum="+payeebankaccnum
					+"&accnum="+accnum
					+"&accname="+accname
					+"&certitype="+certitype
					+"&certinum="+certinum
					+"&frzflag="+frzflag
					+"&drawreason="+drawreason
					+"&qdapprnum="+qdapprnum
					+"&drawreasoncode1="+drawreasoncode1
					+"&bankcode="+bankcode
					+"&buyhousename="+buyhousename
					+"&buyhousecerid="+buyhousecerid
					+"&buyhousedate="+buyhousedate
					+"&houseaddr="+houseaddr
					+"&buyhouseamt="+buyhouseamt
					+"&repaylntype="+repaylntype
					+"&contrsigndate="+contrsigndate
					+"&loanterm="+loanterm
					+"&loansum="+loansum
					+"&commloansum="+commloansum
					+"&monthrepayamt="+monthrepayamt
					+"&relation="+relation
					+"&commonthrepayamt="+commonthrepayamt
					+"&lasttrsdate="+lasttrsdate
					+"&inputamt="+inputamt
					+"&rentname="+rentname
					+"&rentcertinum="+rentcertinum
					+"&rentdate="+rentdate
					+"&rentenddate="+rentenddate
					+"&rentamt="+rentamt
					+"&renttype="+renttype
					+"&unitaccnum="+unitaccnum
					+"&handset="+handset
					+"&buyhousetype="+buyhousetype
					+"&accloantype="+accloantype
				 +"&appramt="+appramt
				 +"&apprdate="+apprdate;

			 String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=40";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=40";
			//System.out.println("本地参数" + parm);
			//System.out.println("传递参数：" + parm1);
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

	/*	1. 偿还贷款本息提取  5800.00
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
	/*3. 租房提取 金额1200.00
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
	public static void main(String[] args) throws UnknownHostException {
		for (int i = 1;i<=1;i++) {
			long starTime = System.currentTimeMillis();
		/*
userId=330424199001241421&usertype=1&deviceType=3&deviceToken=&currenVersion=1.0&appid=yondervisionwebservice40&
appkey=b5b1c6938e5d0cef72457bd788ffdef0&appToken=&clientIp=172.16.0.186&clientMAC=5C-F3-FC-B7-B8-20&brcCode=05740008&channelSeq=&
tranDate=2019-08-14&paybankcode=0002&subbal=0.00&handset=15869511537&clearamt=&accname=孔爱娟&unitaccnum=010200000185&
drawreason=001&accnum=0236195400&supbal=0.00&bankcode=0002&realdpayamt=74654.28&certitype=1&buzType=5370&houseaddr=江北区悦士庭一期-2第8#楼1单元2403室
&certinum=330424199001241421&cleardate=&bal=27062.43&accloantype=13&frzflag=0&repaylntype=0&buyhousedate=&relation=02&
drawreasoncode1=331983736-2012-20170460736&contrsigndate=2017-09-15&qdapprnum=wt1908140977195640889177&monbal=0.00&payeebankcode=0201&
payeebankaccnum=6236681590003398146&lasttrsdate=&buyhousecerid=511902198908300038&buyhouseamt=1639967.00&inputamt=15700.00&begdate=2017-09-15&c
ommloansum=1140000.00&buyhousename=罗川&commonthrepayamt=6221.19&buyhousecertitype=1&enddate=2018-09-15&ahdrepayamt=&loanterm=360*/
			/*httpURLConnectionPOST5370("0201", "6236681590003398146", "0236195400", "孔爱娟",
					"1", "330424199001241421", "0", "001", "wt1908140977195640889177", "331983736-2012-20170460736",
					"0002", "罗川", "511902198908300038", "", "江北区悦士庭一期-2第8#楼1单元2403室",
					"1639967.00", "0", "2017-09-15", "360", "550000.00", "1140000.00", "3512.66",
					"01", "6221.19", "", "15700.00", "", "", "", "", "", "",
					"010200000185", "15869511537", "02", "13");*/

		/* 公共参数  :
银行卡的所属银行:payeebankcode
卡号:payeebankaccnum
个人账号:accnum
姓名:accname
证件类型:certitype
证件号码:certinum
冻结状态:frzflag
提取原因:drawreason
单位账号：unitaccnum
手机号：handset
渠道流水号：qdapprnum

根据提取原因的不同，显示以下不同的要素:

公贷还贷提取、商贷还款提取:
提取材料号:drawreasoncode1
贷款银行:bankcode
主贷人姓名:buyhousename
主贷人证件号码:buyhousecerid
购房日期:buyhousedate（YYYY-MM-DD）
房屋地址:houseaddr
购房金额:buyhouseamt
购房类型:buyhousetype
还款类型:repaylntype
合同签订日期:contrsigndate（YYYY-MM-DD）
贷款期限:loanterm
公积金贷款金额:loansum
商贷金额:commloansum
公贷月还款金额:monthrepayamt
与购房人关系:relation
商贷月还款金额:commonthrepayamt
上次提取时间:lasttrsdate（YYYY-MM-DD）
可提取金额:inputamt
贷款还款额:realdpayamt
结清日期：cleardate
结清金额：clearamt
提前部分还款金额：ahdrepayamt

租房提取:
市场租赁编号:drawreasoncode1
主租人:rentname
主租人证件号码:rentcertinum
租房开始日期:rentdate（YYYY-MM-DD）
租房结束日期:rentenddate（YYYY-MM-DD）
租房地址:houseaddr
租房类型:renttype
月租金:rentamt
与租房人关系:relation
可提取金额:inputamt

商品房提取（未领房产证）、二手房提取（已领房产证，二手房） :
提取材料号:drawreasoncode1
购房人姓名:buyhousename
购房人证件号码:buyhousecerid
购房日期:buyhousedate（YYYY-MM-DD）
房屋地址:houseaddr
购房金额:buyhouseamt
购房类型:buyhousetype
与购房人关系:relation
提取金额:inputamt*/

		//httpURLConnectionPOST5370("0101","1111","0112469995","黄芳","1","330227197010265360","0","004","WT9933323911","yyy111","","","","","北京市王府井大街8号","","","","","","","","01","","","1200.00","黄芳","330227197010265360","2016-01-01","2017-09-02","100","23","011500001166","13615747900","","");//租房提取
		/*租房提取:
			市场租赁编号:drawreasoncode1
			主租人:rentname
			主租人证件号码:rentcertinum
			租房开始日期:rentdate
			租房结束日期:rentenddate
			租房地址:houseaddr
			租房类型:renttype
			月租金:rentamt
			与租房人关系:relation
			可提取金额:inputamt*/
		/*003	建造/翻修/大修自有住房
		102 死亡
		103	出境定居*/
			httpURLConnectionPOST5370("0101","1111","0072745715","胡晓昕","1",
				"330281198305040022","0","103","WT93454444324391","yyy000","",
				"","","","asdas","",
				"","","","","","","01","","",
				"800","胡晓昕","330281198305040022","2019-01-01","2019-12-03","700","23",
					"010100002496", "","","","","");//出境定居
			/*httpURLConnectionPOST5370("0101", "1111", "0129073251", "周成泽",
					"1", "320107198210182633", "0", "001", "WT99423638", "HT057400082016010626",
					"0101", "周成泽", "320107198210182633", "2016-10-17", "海曙区新典路68弄35号610，阁楼",
					"1000000.00", "0", "2016-10-27", "204", "550000.00", "0.00", "3512.66",
					"01", "0.00", "", "5800.00", "", "", "", "", "", "",
					"010100005938", "15258370006", "02", "11");//还贷提取*/

		/*	  公共参数  :
银行卡的所属银行:payeebankcode
卡号:payeebankaccnum
个人账号:accnum
姓名:accname
证件类型:certitype
证件号码:certinum
冻结状态:frzflag
提取原因:drawreason
单位账号：unitaccnum
手机号：handset
渠道流水号：qdapprnum

根据提取原因的不同，显示以下不同的要素:
翻建大修提取:
appramt 审批金额（必填）
approvaldate 批准日期（必填）
houseaddr 房屋地址（必填）

公贷还贷提取、商贷还款提取:
提取材料号:drawreasoncode1
贷款银行:bankcode
主贷人姓名:buyhousename
主贷人证件号码:buyhousecerid
购房日期:buyhousedate（YYYY-MM-DD）
房屋地址:houseaddr
购房金额:buyhouseamt
购房类型:buyhousetype
还款类型:repaylntype
合同签订日期:contrsigndate（YYYY-MM-DD）
贷款期限:loanterm
公积金贷款金额:loansum
商贷金额:commloansum
公贷月还款金额:monthrepayamt
与购房人关系:relation
商贷月还款金额:commonthrepayamt
上次提取时间:lasttrsdate（YYYY-MM-DD）
可提取金额:inputamt
贷款还款额:realdpayamt
结清日期：cleardate
结清金额：clearamt
提前部分还款金额：ahdrepayamt

租房提取:
市场租赁编号:drawreasoncode1
主租人:rentname
主租人证件号码:rentcertinum
租房开始日期:rentdate（YYYY-MM-DD）
租房结束日期:rentenddate（YYYY-MM-DD）
租房地址:houseaddr
租房类型:renttype
月租金:rentamt
与租房人关系:relation
可提取金额:inputamt

商品房提取（未领房产证）、二手房提取（已领房产证，二手房） :
提取材料号:drawreasoncode1
购房人姓名:buyhousename
购房人证件号码:buyhousecerid
购房日期:buyhousedate（YYYY-MM-DD）
房屋地址:houseaddr
购房金额:buyhouseamt
购房类型:buyhousetype
与购房人关系:relation
提取金额:inputamt*/
			long endTime = System.currentTimeMillis();
			long Time = endTime - starTime;
			System.out.println("请求大数据平台耗时" + Time + "毫秒");
		}
	}
}
