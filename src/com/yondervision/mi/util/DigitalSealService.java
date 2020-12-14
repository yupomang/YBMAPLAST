package com.yondervision.mi.util;

import com.dingseal.result.ActionResult;
import com.lowagie.text.pdf.PdfReader;
import com.timevale.esign.result.account.LoginResult;
import com.timevale.esign.result.account.SealListResult;
import com.timevale.esign.result.account.SealResult;
import com.timevale.esign.result.account.SelfInfoResult;
import com.timevale.esign.result.file.SignPDFResult;
import com.timevale.esign.sdk.account.AccountService;
import com.timevale.esign.sdk.account.AccountServiceImpl;
import com.timevale.esign.sdk.file.LocalFileService;
import com.timevale.esign.sdk.file.LocalFileServiceImpl;
import esign.bean.PosBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Description 电子签章
 * @author lenovo
 * @date 2017年12月4日
 * @see DigitalSealService
 * @since
 */
public class DigitalSealService {

	//protected SysDictEntryCache dictEntryCache = SpringContext.getApplicationContext().getBean(SysDictEntryCache.class);
	
	/*//FTP参数
	private static String ip = Configurations.getConfig("ftp_ip");
	private static int ipPort = Integer.valueOf(Configurations.getConfig("ftp_ipport"));
	private static String userName = Configurations.getConfig("ftp_username");
	private static String passWord = Configurations.getConfig("ftp_password");*/
	
	/*
	 *对象定义 
	 */
	private static AccountService account=new AccountServiceImpl();//第三方应用登录实体类
	private static LoginResult
			login= new LoginResult();//实际用来验证登录信息的类，主要通过此方法获取用户id（在天谷系统中唯一识别码）
	private static SelfInfoResult selfinforesult;//登录成功后可获取账户所有信息的类
	private static SealListResult seallistresult;//获取的印章信息的实体类
	private static SealResult sealresult;//印章详细信息
	

	/*
	 * 常用的变量，实际代码中是需要的
	 */
	//private static String APPKEY = "33000000005895";
	//private static String EAPPKEY = Configurations.getConfig("Eappkey");//#异地缴存证明电子签章appkey
	//private static String VAPPKEY = Configurations.getConfig("Vappkey");//#单位年度验审电子签章appkey
	private static String appkey;//登录所必须的，在我方系统中唯一的参数
	private static int errcod;//定义返回后的代码  如果是0代表登录成功，其他为失败
	private static String errmassage;//定会返回的错误消息
	private static String accountid;//定义账户ID用于获取用户所有信息
	//private static Integer sealid = Integer.parseInt(Configurations.getConfig("sealid"));//印章id,印章唯一标识
	//private static Integer Esealid = Integer.parseInt(Configurations.getConfig("Esealid"));//#异地缴存证明电子签章sealid
	
	/**
	 * 
	 * @Title: digitalSealByKey 
	 * @Description: 依靠关键字盖章  
	 * @return void 返回类型
	 * @param 前台传入的参数：srcPdfFile盖章前的文件路径   dstPdfFile盖章后的文件路径      signKey签章的关键字
	 * @throws IOException 
	 */
	public static String digitalSealByKey(String signKey,String srcPdfFile,String dstPdfFile) {
		//personAlter_010100000028_4
		//signKey = "期末";
		//srcPdfFile = "D:\\temp\\1646520_excel01.xls_20191230173921.pdf"; //盖章前的文件路径     ireportFile\temp\\unitYearValided_010100001788_4.pdf
		System.out.println("srcPdfFile:"+srcPdfFile+"!");
		//dstPdfFile = "D:\\temp\\1646520.pdf"; //盖章后的文件路径
		System.out.println("dstPdfFile:"+dstPdfFile+"!");
		//String filename = dstPdfFile; //盖章后的文件路径;
		//String signKey = (String) input.get("signKey"); //签章的关键字
//		srcPdfFile = SessionUtil.getSession().getServletContext().getRealPath(srcPdfFile);  //测试
//		srcPdfFile = File.separator+"home"+File.separator+"weblogic"+File.separator+"pdffiledir"+srcPdfFile;  //生产
//		dstPdfFile = SessionUtil.getSession().getServletContext().getRealPath(dstPdfFile);  //测试
//		dstPdfFile = File.separator+"home"+File.separator+"weblogic"+File.separator+"pdffiledir"+dstPdfFile;  //生产
		Integer sealid=null;
		/*LoginInfo loginInfo = SessionManage.getLoginInfoSession();
		String centerCode = loginInfo.getOrgNo();*/
		/*Map<String,String> map = branchToAppkeys();
		for(String key :map.keySet()){
			if(key.equals(centerCode)){
				appkey = map.get(key);
			}
		}*/
		appkey = "33020000026092";
		login = account.appLogin(appkey);//设置登录账号为"123456789"，正确的账户
		System.out.println("login:"+login);
		errcod = login.getErrCode();//获取登录后的代码（返回0代表成功，1代表失败）
		errmassage = login.getMsg();//获取登录账号后返回的错误信息，如果登录成功就不返回
		accountid = login.getAccountId();//获取登录成功后的accountid，获取印章列表也是需要这个参数
		// 判断用户是否登录成功
		if(errcod==0){
			System.out.println("success=="+accountid);//登录成功返回accountid(用户id);
		}
		else{
			System.out.println("fail==="+errmassage);//登录失败后返回错误信息
		}
		//第二步(实际签章中此步骤可以省略)  获取登录后的账户所有信息  说明：用selfinforesult接收用户信息
		selfinforesult = account.getSelfinfo(accountid);//通过登录成功后返回的accountid获取账户信息
		System.out.println(selfinforesult.toString());
		// 获取印章信息
		seallistresult = account.getSealList(accountid, null, null, 0);
		List<SealResult> seal = new ArrayList<SealResult>(); 
		seal = seallistresult.getSeals();
		for(int i=0;i<seal.size();i++){
			sealresult = (SealResult) seal.get(0);
			sealid = sealresult.getSealId();
			System.out.println(String.valueOf(sealid));
		}
		//*第四步  定义盖章位置信息，这里主要分为两种
		PosBean posbean = new PosBean();//定义盖章位置信息
		PdfReader reader = null;
		try {
			reader = new PdfReader(srcPdfFile, "PDF".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		int num = reader.getNumberOfPages();
		String posType = "1";//定义盖章类型，0.坐标盖章；1.关键字盖章        默认0，不可空。
		String posPage = num+"";     //对第一页盖章  传入空不影响
		//String posPage = "1-3";  //第1到第3页盖章
		//String posPage = "1,3,8";//盖章第1、3、8页
		String key = signKey;//盖章的关键字，如果设置了坐标盖章，不要这个值  例如：单位公章或业务专用章
		System.out.println("key=="+key);
		posbean.setPosType(posType);//1
		posbean.setPosPage(posPage);//1
		posbean.setKey(key);//单位公章
		//*第五步 盖章
		SignPDFResult signpdfresult = new SignPDFResult();
		LocalFileService localfileservice = new LocalFileServiceImpl();
		int signType = 4; //1.坐标  2.  3.4.关键字
		System.out.println("sealid=="+sealid);
		System.out.println("ID=="+accountid);
		
		signpdfresult = localfileservice.localSignPDF(accountid,"",srcPdfFile,dstPdfFile,sealid,signType,posbean);
		//生产功能
		errcod = signpdfresult.getErrCode();
		errmassage = signpdfresult.getMsg();
		System.out.println(errcod+"......"+errmassage);
		/*if("成功".equals(errmassage)){
			//将工程外的盖好章的文件拷贝到工程内
			String oldPath =  File.separator+"home"+File.separator+"weblogic"+File.separator+"pdffiledir"+filename;
			System.out.println("digitalSealByKey的oldPath是："+oldPath+"!!!!!!!!!!!!!");
			// /home/weblogic/software/gjj-wsyyt/ireportFile/temp
			String newPath="/home/weblogic/software/gjj-wsyyt/ireportFile/temp"+filename;
			System.out.println("digitalSealByKey的newPath是："+newPath+"!!!!!!!!!!!!!");
			QRCode.copyFile(oldPath, newPath);
		}	*/
		return errmassage;
	}

	public static void main(String[] args) throws Exception {

		digitalSealByKey("","","");
		digitalSealByLocation(90,740,"","");
	}
	
	
	/**
	 *
	 * @Title: digitalSealByLocation
	 * @Description: 依靠坐标位置盖章
	 * @return void 返回类型
	 * @param 前台传入的参数： srcPdfFile盖章前的文件路径   dstPdfFile盖章后的文件路径     posX为X坐标   posY为Y坐标
	 * @throws Exception
	 */
	public static String  digitalSealByLocation(int positionX,int positionY,String srcPdfFile,String dstPdfFile) throws Exception{

		System.out.println("-----------------------------------pdf加盖电子章开始--------------------------------------");
		//String srcPdfFile = "D:\\temp\\1646520_zgjczm.doc_20200108151918.pdf"; //盖章前的文件路径     ireportFile\temp\\unitYearValided_010100001788_4.pdf
		System.out.println("srcPdfFile========"+srcPdfFile);
		//String dstPdfFile = "D:\\temp\\1646520.pdf"; //盖章后的文件路径
		System.out.println("dstPdfFile========"+dstPdfFile);
		String filename = dstPdfFile;//盖章后的文件路径
		//String pathname= filename.substring(1, 12);
		//System.out.println("pathname为"+pathname);
		//int positionX = 90; //X坐标
		//int positionY = 740; //Y坐标
		//srcPdfFile = SessionUtil.getSession().getServletContext().getRealPath(srcPdfFile);  //测试
//		srcPdfFile = File.separator+"home"+File.separator+"weblogic"+File.separator+"pdffiledir"+srcPdfFile;  //生产
//		dstPdfFile = SessionUtil.getSession().getServletContext().getRealPath(dstPdfFile);  //测试
//		dstPdfFile = File.separator+"home"+File.separator+"weblogic"+File.separator+"pdffiledir"+dstPdfFile;  //生产
//		LoginInfo loginInfo = SessionManage.getLoginInfoSession();
		Integer sealid = null;
		/*String centerCode = loginInfo.getOrgNo();
		Map<String,String> map = branchToAppkeys();
		for(String key :map.keySet()){
			if(key.equals(centerCode)){
				appkey = map.get(key);
			}
		}*/
		/*第一步
		 * 用户登录
		 * 说明：用login（LoginResult）接收appkey，参考接口文档（3.7），返回值login（LoginResult）参考
		 * 接口文档（附录二 对象解释 2.1）
		 */
		appkey="33020000026092";
		/*if(pathname.equals("empPayProve")){
			appkey = EAPPKEY;
			login = account.appLogin(appkey);//设置登录账号为"123456789"，正确的账户
			System.out.println("appkey:"+appkey);
			System.out.println("登录账号为login:"+login);
		}else if(pathname.equals("unitYearVal")){
			appkey = VAPPKEY;
			login = account.appLogin(appkey);//设置登录账号为"123456789"，正确的账户
			System.out.println("appkey:"+appkey);
			System.out.println("登录账号为login:"+login);
		}else{*/
		login = account.appLogin(appkey);//设置登录账号为"123456789"，正确的账户
		System.out.println("登录账号为login:"+login);
		//}
		errcod = login.getErrCode();//获取登录后的代码（返回0代表成功，1代表失败）
		System.out.println("获取登录后的代码errcod:"+errcod);
		errmassage = login.getMsg();//获取登录账号后返回的错误信息，如果登录成功就不返回
		System.out.println("获取登录账号后返回的错误信息errmassage:"+errmassage);
		accountid = login.getAccountId();//获取登录成功后的accountid，获取印章列表也是需要这个参数
		System.out.println("获取登录成功后的accountidaccountid:"+accountid);
		/*1.1
		 * 判断用户是否登录成功
		 */
		if(errcod==0){
			System.out.println("登录成功"+accountid);//登录成功返回accountid(用户id);
		}
		else{
			System.out.println(errmassage);//登录失败后返回错误信息
		}
		/*第二步(实际签章中此步骤可以省略)
		 * 获取登录后的账户所有信息
		 * 说明：用selfinforesult接收用户信息，参考接口文档（3.9），
		 * 返回值selfinforesult（SelfInfoResult），参考接口文档（附录二 对象解释 2.11）
		 */
		System.out.println("-------------------------------pdf电子章获取登录后的账户所有信息------------------------------------");
		selfinforesult = account.getSelfinfo(accountid);//通过登录成功后返回的accountid获取账户信息
		System.out.println(selfinforesult.toString());

		/*第三步
		 * 获取印章信息
		 * 说明：此步是为了账户下已经绑定的所有印章信息(主要获取印章id)，每一个印章都有自己唯一编号sealid
		 * 参考接口文档6.1，返回值seallistresult（seallistresult），参考接口文档（附录二 对象解释 2.6）
		 * 返回值sealresult（SealResult），，参考接口文档（附录二 对象解释 2.7）
		 */
		System.out.println("-------------------------------pdf电子章获取印章信息------------------------------------");
		seallistresult = account.getSealList(accountid, null, null, 0);
		List<SealResult> seal = new ArrayList<SealResult>();
		seal = seallistresult.getSeals();
		for(int i=0;i<seal.size();i++){
			sealresult = (SealResult) seal.get(0);
			sealid = sealresult.getSealId();
			System.out.println(sealid);
		}

		/*第四步
		 * 定义盖章位置信息，这里主要分为两种
		 * a.坐标：需设置X和Y的坐标值，单位为px
		 * b.关键字：传入一个盖章pdf文件中的关键字（如果pdf为图片或者内容无法选中的，无法使用该功能）
		 */
		System.out.println("-------------------------------pdf电子章定义盖章位置信息------------------------------------");
		PosBean posbean = new PosBean();//定义盖章位置信息
		String posType = "0";//定义盖章类型，0.坐标盖章；1.关键字盖章        默认0，不可空。
		/*对象：posPage，可空
		 * 说明：签署页码，若为多页签章，支持页码格式“1-3,5,8“，若为坐标定位时，不可空
		 */
		PdfReader reader = new PdfReader(srcPdfFile, "PDF".getBytes());
		int num = reader.getNumberOfPages();
		System.out.println("num========="+num);
		String posPage ="1-"+num;     //对第一页盖章1-12
		//String posPage = "1-3";  //第1到第3页盖章
		//String posPage = "1,3,8";//盖章第1、3、8页
		/*坐标盖章
		 * 说明：坐标盖章需要定义X轴和Y轴
		 * 如果关键字盖章，这两个值可以不要   700 535
		 */
		int posX = positionX;
		int posY = positionY;
		posbean.setPosType(posType);
		posbean.setPosPage(posPage);
		posbean.setPosX(posX);
		posbean.setPosY(posY);
		/*第五步
		 *盖章
		 */
		long starTime=System.currentTimeMillis();
		System.out.println("-------------------------------pdf电子章盖章------------------------------------");
		SignPDFResult signpdfresult = new SignPDFResult();
		LocalFileService localfileservice = new LocalFileServiceImpl();

		int signType = 2; //1.坐标  2.多页 3.骑缝章 4.关键字
		System.out.println("sealid=="+sealid);
		System.out.println("ID=="+accountid);
		signpdfresult = localfileservice.localSignPDF(accountid,"",srcPdfFile,dstPdfFile,sealid,signType,posbean);

		System.out.println("errmassage是:"+errmassage+"!!!!!!!!!!!!!!!!");
		//生产功能
		errcod = signpdfresult.getErrCode();
		errmassage = signpdfresult.getMsg();
		System.out.println(errcod+"......"+errmassage);
		System.out.println("-----------------------------------pdf加盖电子章结束--------------------------------------");
		long endTime=System.currentTimeMillis();
		long Time=endTime-starTime;
		System.out.println("请求大数据平台耗时"+Time+"毫秒");
		/*if("成功".equals(errmassage)&&pathname.equals("empPayProve")){
			//将成功的文件上传到36的指定文件夹内
			upFileOperate(filename);
		}*/
		return errmassage;
	}
	
	/**
	 * 
	 * @Title: digitalSealByLocationFromOther 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param input
	 * @return
	 * @throws Exception 
	 * @return Object 返回类型
	 */
	/*public Object digitalSealByLocationFromOther(HashMap<String,Object> input) throws Exception{
		System.out.println("-----------------------------------pdf加盖电子章开始--------------------------------------");
		String srcPdfFile = (String) input.get("srcPdfFile"); //盖章前的文件路径     ireportFile\temp\empPayProve_330205196801270665_5.pdf
		System.out.println("srcPdfFile路径是："+srcPdfFile+"!!!!!!!!!!!");
		String dstPdfFile = (String) input.get("dstPdfFile"); //盖章后的文件路径
		System.out.println("dstPdfFile路径是："+dstPdfFile+"!!!!!!!!!!!");
		String centerCode = (String) input.get("centerCode");
		String channel = (String) input.get("channel");
		String filename = dstPdfFile;//盖章后的文件路径
		String pathname= filename.substring(1, 12);
		System.out.println("pathname为"+pathname);
		int positionX = Integer.parseInt((String) input.get("posX")); //X坐标
		int positionY = Integer.parseInt((String) input.get("posY")); //Y坐标
//		srcPdfFile = SessionUtil.getSession().getServletContext().getRealPath(srcPdfFile);  //测试
		srcPdfFile = File.separator+"home"+File.separator+"weblogic"+File.separator+"pdffiledir"+srcPdfFile;  //生产
//		dstPdfFile = SessionUtil.getSession().getServletContext().getRealPath(dstPdfFile);  //测试
		dstPdfFile = File.separator+"home"+File.separator+"weblogic"+File.separator+"pdffiledir"+dstPdfFile;  //生产
		Integer sealid = null;
		Map<String,String> map = branchToAppkeys();
		for(String key :map.keySet()){
			if(key.equals(centerCode)){
				appkey = map.get(key);
			}
		}
		*//*第一步
		 * 用户登录
		 * 说明：用login（LoginResult）接收appkey，参考接口文档（3.7），返回值login（LoginResult）参考
		 * 接口文档（附录二 对象解释 2.1）
		 *//*
		//appkey="18668212948";
		if(pathname.equals("empPayProve")){
			appkey = EAPPKEY;
			login = account.appLogin(appkey);//设置登录账号为"123456789"，正确的账户
			System.out.println("appkey:"+appkey);
			System.out.println("登录账号为login:"+login);
		}else if(pathname.equals("unitYearVal")){
			appkey = VAPPKEY;
			login = account.appLogin(appkey);//设置登录账号为"123456789"，正确的账户
			System.out.println("appkey:"+appkey);
			System.out.println("登录账号为login:"+login);
		}else{
			login = account.appLogin(appkey);//设置登录账号为"123456789"，正确的账户
			System.out.println("登录账号为login:"+login);
		}
		errcod = login.getErrCode();//获取登录后的代码（返回0代表成功，1代表失败）
		System.out.println("获取登录后的代码errcod:"+errcod);
		errmassage = login.getMsg();//获取登录账号后返回的错误信息，如果登录成功就不返回
		System.out.println("获取登录账号后返回的错误信息errmassage:"+errmassage);
		accountid = login.getAccountId();//获取登录成功后的accountid，获取印章列表也是需要这个参数
		System.out.println("获取登录成功后的accountidaccountid:"+accountid);
		*//*1.1
		 * 判断用户是否登录成功
		 *//*
		if(errcod==0){
			System.out.println("登录成功"+accountid);//登录成功返回accountid(用户id);
		}
		else{
			System.out.println(errmassage);//登录失败后返回错误信息
		}
		*//*第二步(实际签章中此步骤可以省略)
		 * 获取登录后的账户所有信息
		 * 说明：用selfinforesult接收用户信息，参考接口文档（3.9），
		 * 返回值selfinforesult（SelfInfoResult），参考接口文档（附录二 对象解释 2.11）
		 *//*
		System.out.println("-------------------------------pdf电子章获取登录后的账户所有信息------------------------------------");
		selfinforesult = account.getSelfinfo(accountid);//通过登录成功后返回的accountid获取账户信息
		System.out.println(selfinforesult.toString());

		*//*第三步
		 * 获取印章信息
		 * 说明：此步是为了账户下已经绑定的所有印章信息(主要获取印章id)，每一个印章都有自己唯一编号sealid
		 * 参考接口文档6.1，返回值seallistresult（seallistresult），参考接口文档（附录二 对象解释 2.6）
		 * 返回值sealresult（SealResult），，参考接口文档（附录二 对象解释 2.7）
		 *//*
		System.out.println("-------------------------------pdf电子章获取印章信息------------------------------------");
		seallistresult = account.getSealList(accountid, null, null, 0);
		List<SealResult> seal = new ArrayList<SealResult>();
		seal = seallistresult.getSeals();
		for(int i=0;i<seal.size();i++){
			sealresult = (SealResult) seal.get(0);
			sealid = sealresult.getSealId();
			System.out.println(sealid);
		}

		*//*第四步
		 * 定义盖章位置信息，这里主要分为两种
		 * a.坐标：需设置X和Y的坐标值，单位为px
		 * b.关键字：传入一个盖章pdf文件中的关键字（如果pdf为图片或者内容无法选中的，无法使用该功能）
		 *//*
		System.out.println("-------------------------------pdf电子章定义盖章位置信息------------------------------------");
		PosBean posbean = new PosBean();//定义盖章位置信息
		String posType = "0";//定义盖章类型，0.坐标盖章；1.关键字盖章        默认0，不可空。
		*//*对象：posPage，可空
		 * 说明：签署页码，若为多页签章，支持页码格式“1-3,5,8“，若为坐标定位时，不可空
		 *//*
		PdfReader reader = new PdfReader(srcPdfFile, "PDF".getBytes());
		int num = reader.getNumberOfPages();
		String posPage ="1-"+num;     //对第一页盖章1-12
		//String posPage = "1-3";  //第1到第3页盖章
		//String posPage = "1,3,8";//盖章第1、3、8页
		*//*坐标盖章
		 * 说明：坐标盖章需要定义X轴和Y轴
		 * 如果关键字盖章，这两个值可以不要   700 535
		 *//*
		int posX = positionX;
		int posY = positionY;
		posbean.setPosType(posType);
		posbean.setPosPage(posPage);
		posbean.setPosX(posX);
		posbean.setPosY(posY);
		*//*第五步
		 *盖章
		 *//*
		System.out.println("-------------------------------pdf电子章盖章------------------------------------");
		SignPDFResult signpdfresult = new SignPDFResult();
		LocalFileService localfileservice = new LocalFileServiceImpl();

		int signType = 2; //1.坐标  2.多页 3.骑缝章 4.关键字
		System.out.println("sealid=="+sealid);
		System.out.println("ID=="+accountid);
		System.out.println("accountid:"+accountid+",srcPdfFile:"+srcPdfFile+",dstPdfFile:"+dstPdfFile+",sealid:"+sealid+",signType:"+signType+",posbean:"+posbean);
		signpdfresult = localfileservice.localSignPDF(accountid,"",srcPdfFile,dstPdfFile,sealid,signType,posbean);

		System.out.println("signpdfresult是:"+signpdfresult+"!!!!!!!!!!!!!!!!");
		//生产功能
		errcod = signpdfresult.getErrCode();
		errmassage = signpdfresult.getMsg();
		System.out.println(errcod+"......"+errmassage);
		System.out.println("-----------------------------------pdf加盖电子章结束--------------------------------------");
		if("成功".equals(errmassage)&&pathname.equals("empPayProve")){
				//将成功的文件上传到36的指定文件夹内
			upFileOperateFromOther(filename,channel,centerCode);
		}
		return new ActionResult();
	}*/
	/**
	 * @Title: upFileOperate 
	 * @Description: 写文件并上传到FTP  
	 * @param fileName 文件名(只是文件名)
	 * @throws Exception 
	 * @return String 返回类型-文件全路径
	 * 文件名称=日期+机构session 
	 */
	/*public static String upFileOperate(String fileName)throws Exception{
		FileInputStream in = null;
		try{
			LoginInfo loginInfo = SessionManage.getLoginInfoSession();
			String today = CommonUtil.DateToStr(new Date(), "yyyyMMdd")+loginInfo.getOrgNo();//生产环境
			//String tempPath = CommonUtil.DateToStr(new Date(), "yyyyMMdd");//测试环境
			String filePath = "/pdffiledir/"+today;//服务器存放上传文件路径
			System.out.println("上传目的文件路径是" + filePath);
			//判断服务器路径是否存在
			String fullFilePath = File.separator+"home"+File.separator+"weblogic"+File.separator+"pdffiledir"+fileName;;//本地文件路劲名称
			System.out.println("上传前文件路径是" + fullFilePath);
			//将文件上传到指定FTP服务器
			in = new FileInputStream(new File(fullFilePath));
			fileName = fileName.substring(1, fileName.length());
			System.out.println("开始从本地往管理平台拷文件！！！！！！");
			FtpUtil.uploadPDFFile(ip, ipPort, userName, passWord, filePath, fileName, in); 
			//返回文件全路径
//			return filePath+File.separator;
			return filePath+"/";
		}catch(Exception e){
			System.out.println("处理文件或上传文件时出错！",e);
			e.printStackTrace();
			throw new Exception("处理文件或上传文件时出错！");
		}finally{
			//关闭文件流
			if(in!=null){
				in.close();
			}
			
		}
	}*/
	
	/**
	 * 
	 * @Title: upFileOperateFromOther 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param fileName
	 * @return
	 * @throws Exception 
	 * @return String 返回类型
	 */
	/*public static String upFileOperateFromOther(String fileName,String channel,String centerCode)throws Exception{
		FileInputStream in = null;
		try{
			String today = CommonUtil.DateToStr(new Date(), "yyyyMMdd")+centerCode+channel;//生产环境
			//String tempPath = CommonUtil.DateToStr(new Date(), "yyyyMMdd");//测试环境
			String filePath = "/pdffiledir/"+today;//服务器存放上传文件路径
			System.out.println("上传目的文件路径是" + filePath);
			//判断服务器路径是否存在
			String fullFilePath = File.separator+"home"+File.separator+"weblogic"+File.separator+"pdffiledir"+fileName;;//本地文件路劲名称
			System.out.println("上传前文件路径是" + fullFilePath);
			//将文件上传到指定FTP服务器
			in = new FileInputStream(new File(fullFilePath));
			fileName = fileName.substring(1, fileName.length());
			System.out.println("开始从本地往管理平台拷文件！！！！！！");
			FtpUtil.uploadPDFFile(ip, ipPort, userName, passWord, filePath, fileName, in); 
			//返回文件全路径
//			return filePath+File.separator;
			return filePath+"/";
		}catch(Exception e){
			System.out.println("处理文件或上传文件时出错！",e);
			e.printStackTrace();
			throw new Exception("处理文件或上传文件时出错！");
		}finally{
			//关闭文件流
			if(in!=null){
				in.close();
			}
			
		}
	}*/
	
	/**
	 * instcllhead|
	 * 05740008:宁波市住房公积金管理中心|
	 * 05740011:宁波市住房公积金管理中心北仑分中心|
	 * 05740012:宁波市住房公积金管理中心奉化分中心|
	 * 05740013:宁波市住房公积金管理中心慈溪分中心|
	 * 05740014:宁波市住房公积金管理中心鄞州分中心|
	 * 05740015:宁波市住房公积金管理中心象山分中心|
	 * 05740016:宁波市住房公积金管理中心宁海分中心|
	 * 05740017:宁波市住房公积金管理中心镇海分中心|
	 * 05740018:宁波市住房公积金管理中心余姚分中心
	 * @Title: branchToAppkeys 
	 * @Description: 将每一个分中心对应的appkey放入map中
	 * @param  key为机构号  value为appkey
	 * @return 
	 * @return Map<String,String> 返回类型
	 */
	/*public static Map<String,String> branchToAppkeys(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("05740008", APPKEY); //宁波市住房公积金管理中心
		map.put("05740011", APPKEY); //宁波市住房公积金管理中心北仑分中心
		map.put("05740012", APPKEY); //宁波市住房公积金管理中心奉化分中心
		map.put("05740013", APPKEY); //宁波市住房公积金管理中心慈溪分中心
		map.put("05740014", APPKEY); //宁波市住房公积金管理中心鄞州分中心
		map.put("05740015", APPKEY); //宁波市住房公积金管理中心象山分中心
		map.put("05740016", APPKEY); //宁波市住房公积金管理中心宁海分中心
		map.put("05740017", APPKEY); //宁波市住房公积金管理中心镇海分中心
		map.put("05740018", APPKEY); //宁波市住房公积金管理中心余姚分中心
		return map;
	}*/

}
