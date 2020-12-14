package com.yondervision.mi.test.gongshangyinhangshoujiyinhang;
//一般提取
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;

public class HttpSend5370appapi03806 {
	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/";
	private static final String appKey_V = "w6r8msk88243epqs3bezqiuek1z1il4t";
	private static final String appId_V = "yondervisionwebservice91";
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
			String	drawreasoncode1 ,
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
			String	accloantype) {
		try {
			URL url = new URL(POST_URL + "appapi03806.json");
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
					+"&accloantype="+accloantype;

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
		
		//httpURLConnectionPOST5370("0101","1111","0112469995","黄芳","1","330227197010265360","0","004","WT99333239","yyy111","","","","","北京市王府井大街8号","","","","","","","","01","","","1200.00","黄芳","330227197010265360","2016-01-01","2017-09-02","100","23","011500001166","13615747900","","");//租房提取
//		httpURLConnectionPOST5370("0101","1111","0237724982","王琰慧","1","330682199408031287","0","002","WT9345444432439","yyy000","","王琰慧","330682199408031287","2017-01-01","上海市南京路188号","200000.00","","","","","","","01","","","2000.00","","","","","","","011500001166","13333333333","02","");//购房提取
//		httpURLConnectionPOST5370("0101","1111","0129073251","周成泽","1","320107198210182633","0","001","WT99423639","HT057400082016010626","0101","周成泽","320107198210182633","2016-10-17","海曙区新典路68弄35号610，阁楼","1000000.00","0","2016-10-27","204","550000.00","0.00","3512.66","01","0.00","","5800.00","","","","","","","010100005938","15258370006","02","11");//还贷提取
		httpURLConnectionPOST5370("0101","1111","9876543210","CarterKing","1","332528197008144425","0","001","WT9944423639","HT05741234016010626","0101","樊勇","320107198210182633","2016-10-17","海曙区新典路68弄35号610，阁楼","10.00","0","2016-10-27","204","55.00","0.00","35.66","01","0.00","","17600.00","","","","","","","0101470682","15161178395","02","11");//还贷提取

		/*	 公共参数  : 
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
			购房日期:buyhousedate  
			房屋地址:houseaddr  
			购房金额:buyhouseamt  
			购房类型:buyhousetype 
			还款类型:repaylntype 
			合同签订日期:contrsigndate  
			贷款期限:loanterm  
			公积金贷款金额:loansum  
			商贷金额:commloansum 
			公贷月还款金额:monthrepayamt  
			与购房人关系:relation
			商贷月还款金额:commonthrepayamt
			上次提取时间:lasttrsdate
			可提取金额:inputamt
			
			租房提取:
			市场租赁编号:drawreasoncode1
			主租人:rentname
			主租人证件号码:rentcertinum 
			租房开始日期:rentdate
			租房结束日期:rentenddate  
			租房地址:houseaddr
			租房类型:renttype
			月租金:rentamt
			与租房人关系:relation  
			可提取金额:inputamt
			
			商品房提取（未领房产证）、二手房提取（已领房产证，二手房） :
			提取材料号:drawreasoncode1 
			购房人姓名:buyhousename  
			购房人证件号码:buyhousecerid  
			购房日期:buyhousedate
			房屋地址:houseaddr
			购房金额:buyhouseamt 
			购房类型:buyhousetype 
			与购房人关系:relation 
			提取金额:inputamt*/
	}
}
