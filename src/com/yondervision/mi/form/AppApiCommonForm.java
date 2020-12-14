/**
 * 
 */
package com.yondervision.mi.form;

/**
 * APP接口公共Form
 * 
 * @author LinXiaolong
 * 
 */
public class AppApiCommonForm {
	/** 全加密标志 */
	private String AESFlag = "";
	/** 中心ID */			
	private String centerId = "";			
	/** 用户ID */			
	private String userId = "";			
	/** 用户类型 */			
	private String usertype = "";			
	/** 设备区分 1-iOS,2-Android,3-pc */			
	private String deviceType = "";			
	/** 设备识别码 移动设备专用，非移动设备变量名上传，对应值为空 */			
	private String deviceToken = "";			
	/** 当前版本 对应渠道当前版本号，如果没有默认上传“1.0” */			
	private String currenVersion = "";			
	/** 业务类型 */			
	private String buzType = "";			
	/** 10-APP,20-微信,30-门户网站,40-网上业务大厅,50-自助终端,60-服务热线,70-手机短信,80-官方微博 */			
	private String channel = "";			
	/** 应用标识yondervisionapp10,yondervisionweixin20,yondervisionwebsite30,yondervisionwebservice40,yondervisionselfservice50,yondervisiontelservice60,yondervisionsms70,yon */			
	private String appid = "";			
	/** 应用KEY */			
	private String appkey = "";			
	/** 应用TOKEN */			
	private String appToken = "";			
	/** 客户端IP地址 */			
	private String clientIp = "";			
	/** 网厅特殊参数:柜员号 */			
	private String tellCode = "";			
	/** 网厅特殊参数:机构号 */			
	private String brcCode = "";			
	/** 网厅特殊参数:实例号 */			
	private String channelSeq = "";			
	/** 网厅特殊参数:业务日期 */			
	private String tranDate = "";			
	/** 业务名称 */			
	private String businName = "";			
	/** 公积金账户 */			
	private String surplusAccount = "";			
	/** 身份证后加入 */			
	private String bodyCardNumber = "";			
	/** 联名卡卡号 */			
	private String cardnoNumber = "";			
	private String money = "";		
	/** 账户类型  1-身份证，2-军官证，3-护照，4-外国人永久居住证，5-港奥通行证*/	
	private String certinumType="";
	
	/**客户端MAC地址*/
	private String clientMAC = "";
	/**UK序列号*/	
	private String UKseq="";
	
	private String flag = "";
	private String coreflag = "";
	
	
	public String getCoreflag() {
		return coreflag;
	}

	public void setCoreflag(String coreflag) {
		this.coreflag = coreflag;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getAESFlag() {
		return AESFlag;
	}

	public void setAESFlag(String aESFlag) {
		AESFlag = aESFlag;
	}

	public String getCertinumType() {
		return certinumType;
	}

	public void setCertinumType(String certinumType) {
		this.certinumType = certinumType;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	/**			
	 *<pre> 执行获取中心ID处理 </pre>			
	 * @return centerId 中心ID			
	 */			
	public String getCenterId() {			
	    return centerId;			
	}			
				
	/**			
	 *<pre> 执行设定中心ID处理 </pre>			
	 * @param centerId 中心ID			
	 */			
	public void setCenterId(String centerId) {			
	    this.centerId = centerId;			
	}			
				
	/**			
	 *<pre> 执行获取用户ID处理 </pre>			
	 * @return userId 用户ID			
	 */			
	public String getUserId() {			
	    return userId;			
	}			
				
	/**			
	 *<pre> 执行设定用户ID处理 </pre>			
	 * @param userId 用户ID			
	 */			
	public void setUserId(String userId) {			
	    this.userId = userId;			
	}			
				
	/**			
	 *<pre> 执行获取用户类型处理 </pre>			
	 * @return usertype 用户类型			
	 */			
	public String getUsertype() {			
	    return usertype;			
	}			
				
	/**			
	 *<pre> 执行设定用户类型处理 </pre>			
	 * @param usertype 用户类型			
	 */			
	public void setUsertype(String usertype) {			
	    this.usertype = usertype;			
	}			
				
	/**			
	 *<pre> 执行获取设备区分 1-iOS,2-Android,3-pc处理 </pre>			
	 * @return deviceType 设备区分 1-iOS,2-Android,3-pc			
	 */			
	public String getDeviceType() {			
	    return deviceType;			
	}			
				
	/**			
	 *<pre> 执行设定设备区分 1-iOS,2-Android,3-pc处理 </pre>			
	 * @param deviceType 设备区分 1-iOS,2-Android,3-pc			
	 */			
	public void setDeviceType(String deviceType) {			
	    this.deviceType = deviceType;			
	}			
				
	/**			
	 *<pre> 执行获取设备识别码 移动设备专用，非移动设备变量名上传，对应值为空处理 </pre>			
	 * @return deviceToken 设备识别码 移动设备专用，非移动设备变量名上传，对应值为空			
	 */			
	public String getDeviceToken() {			
	    return deviceToken;			
	}			
				
	/**			
	 *<pre> 执行设定设备识别码 移动设备专用，非移动设备变量名上传，对应值为空处理 </pre>			
	 * @param deviceToken 设备识别码 移动设备专用，非移动设备变量名上传，对应值为空			
	 */			
	public void setDeviceToken(String deviceToken) {			
	    this.deviceToken = deviceToken;			
	}			
				
	/**			
	 *<pre> 执行获取当前版本 对应渠道当前版本号，如果没有默认上传“1.0”处理 </pre>			
	 * @return currenVersion 当前版本 对应渠道当前版本号，如果没有默认上传“1.0”			
	 */			
	public String getCurrenVersion() {			
	    return currenVersion;			
	}			
				
	/**			
	 *<pre> 执行设定当前版本 对应渠道当前版本号，如果没有默认上传“1.0”处理 </pre>			
	 * @param currenVersion 当前版本 对应渠道当前版本号，如果没有默认上传“1.0”			
	 */			
	public void setCurrenVersion(String currenVersion) {			
	    this.currenVersion = currenVersion;			
	}			
				
	/**			
	 *<pre> 执行获取业务类型处理 </pre>			
	 * @return buzType 业务类型			
	 */			
	public String getBuzType() {			
	    return buzType;			
	}			
				
	/**			
	 *<pre> 执行设定业务类型处理 </pre>			
	 * @param buzType 业务类型			
	 */			
	public void setBuzType(String buzType) {			
	    this.buzType = buzType;			
	}			
				
	/**			
	 *<pre> 执行获取10-APP,20-微信,30-门户网站,40-网上业务大厅,50-自助终端,60-服务热线,70-手机短信,80-官方微博处理 </pre>			
	 * @return channel 10-APP,20-微信,30-门户网站,40-网上业务大厅,50-自助终端,60-服务热线,70-手机短信,80-官方微博			
	 */			
	public String getChannel() {			
	    return channel;			
	}			
				
	/**			
	 *<pre> 执行设定10-APP,20-微信,30-门户网站,40-网上业务大厅,50-自助终端,60-服务热线,70-手机短信,80-官方微博处理 </pre>			
	 * @param channel 10-APP,20-微信,30-门户网站,40-网上业务大厅,50-自助终端,60-服务热线,70-手机短信,80-官方微博			
	 */			
	public void setChannel(String channel) {			
	    this.channel = channel;			
	}			
				
	/**			
	 *<pre> 执行获取应用标识yondervisionapp10,yondervisionweixin20,yondervisionwebsite30,yondervisionwebservice40,yondervisionselfservice50,yondervisiontelservice60,yondervisionsms70,yon处理 </pre>			
	 * @return appid 应用标识yondervisionapp10,yondervisionweixin20,yondervisionwebsite30,yondervisionwebservice40,yondervisionselfservice50,yondervisiontelservice60,yondervisionsms70,yon			
	 */			
	public String getAppid() {			
	    return appid;			
	}			
				
	/**			
	 *<pre> 执行设定应用标识yondervisionapp10,yondervisionweixin20,yondervisionwebsite30,yondervisionwebservice40,yondervisionselfservice50,yondervisiontelservice60,yondervisionsms70,yon处理 </pre>			
	 * @param appid 应用标识yondervisionapp10,yondervisionweixin20,yondervisionwebsite30,yondervisionwebservice40,yondervisionselfservice50,yondervisiontelservice60,yondervisionsms70,yon			
	 */			
	public void setAppid(String appid) {			
	    this.appid = appid;			
	}			
				
	/**			
	 *<pre> 执行获取应用KEY处理 </pre>			
	 * @return appkey 应用KEY			
	 */			
	public String getAppkey() {			
	    return appkey;			
	}			
				
	/**			
	 *<pre> 执行设定应用KEY处理 </pre>			
	 * @param appkey 应用KEY			
	 */			
	public void setAppkey(String appkey) {			
	    this.appkey = appkey;			
	}			
				
	/**			
	 *<pre> 执行获取应用TOKEN处理 </pre>			
	 * @return appToken 应用TOKEN			
	 */			
	public String getAppToken() {			
	    return appToken;			
	}			
				
	/**			
	 *<pre> 执行设定应用TOKEN处理 </pre>			
	 * @param appToken 应用TOKEN			
	 */			
	public void setAppToken(String appToken) {			
	    this.appToken = appToken;			
	}			
				
	/**			
	 *<pre> 执行获取客户端IP地址处理 </pre>			
	 * @return clientIp 客户端IP地址			
	 */			
	public String getClientIp() {			
	    return clientIp;			
	}			
				
	/**			
	 *<pre> 执行设定客户端IP地址处理 </pre>			
	 * @param clientIp 客户端IP地址			
	 */			
	public void setClientIp(String clientIp) {			
	    this.clientIp = clientIp;			
	}			
				
	/**			
	 *<pre> 执行获取网厅特殊参数:柜员号处理 </pre>			
	 * @return tellCode 网厅特殊参数:柜员号			
	 */			
	public String getTellCode() {			
	    return tellCode;			
	}			
				
	/**			
	 *<pre> 执行设定网厅特殊参数:柜员号处理 </pre>			
	 * @param tellCode 网厅特殊参数:柜员号			
	 */			
	public void setTellCode(String tellCode) {			
	    this.tellCode = tellCode;			
	}			
				
	/**			
	 *<pre> 执行获取网厅特殊参数:机构号处理 </pre>			
	 * @return brcCode 网厅特殊参数:机构号			
	 */			
	public String getBrcCode() {			
	    return brcCode;			
	}			
				
	/**			
	 *<pre> 执行设定网厅特殊参数:机构号处理 </pre>			
	 * @param brcCode 网厅特殊参数:机构号			
	 */			
	public void setBrcCode(String brcCode) {			
	    this.brcCode = brcCode;			
	}			
				
	/**			
	 *<pre> 执行获取网厅特殊参数:实例号处理 </pre>			
	 * @return channelSeq 网厅特殊参数:实例号			
	 */			
	public String getChannelSeq() {			
	    return channelSeq;			
	}			
				
	/**			
	 *<pre> 执行设定网厅特殊参数:实例号处理 </pre>			
	 * @param channelSeq 网厅特殊参数:实例号			
	 */			
	public void setChannelSeq(String channelSeq) {			
	    this.channelSeq = channelSeq;			
	}			
				
	/**			
	 *<pre> 执行获取网厅特殊参数:业务日期处理 </pre>			
	 * @return tranDate 网厅特殊参数:业务日期			
	 */			
	public String getTranDate() {			
	    return tranDate;			
	}			
				
	/**			
	 *<pre> 执行设定网厅特殊参数:业务日期处理 </pre>			
	 * @param tranDate 网厅特殊参数:业务日期			
	 */			
	public void setTranDate(String tranDate) {			
	    this.tranDate = tranDate;			
	}			
				
	/**			
	 *<pre> 执行获取业务名称处理 </pre>			
	 * @return businName 业务名称			
	 */			
	public String getBusinName() {			
	    return businName;			
	}			
				
	/**			
	 *<pre> 执行设定业务名称处理 </pre>			
	 * @param businName 业务名称			
	 */			
	public void setBusinName(String businName) {			
	    this.businName = businName;			
	}			
				
	/**			
	 *<pre> 执行获取公积金账户处理 </pre>			
	 * @return surplusAccount 公积金账户			
	 */			
	public String getSurplusAccount() {			
	    return surplusAccount;			
	}			
				
	/**			
	 *<pre> 执行设定公积金账户处理 </pre>			
	 * @param surplusAccount 公积金账户			
	 */			
	public void setSurplusAccount(String surplusAccount) {			
	    this.surplusAccount = surplusAccount;			
	}			
				
	/**			
	 *<pre> 执行获取身份证后加入处理 </pre>			
	 * @return bodyCardNumber 身份证后加入			
	 */			
	public String getBodyCardNumber() {			
	    return bodyCardNumber;			
	}			
				
	/**			
	 *<pre> 执行设定身份证后加入处理 </pre>			
	 * @param bodyCardNumber 身份证后加入			
	 */			
	public void setBodyCardNumber(String bodyCardNumber) {			
	    this.bodyCardNumber = bodyCardNumber;			
	}			
				
	/**			
	 *<pre> 执行获取联名卡卡号处理 </pre>			
	 * @return cardnoNumber 联名卡卡号			
	 */			
	public String getCardnoNumber() {			
	    return cardnoNumber;			
	}			
				
	/**			
	 *<pre> 执行设定联名卡卡号处理 </pre>			
	 * @param cardnoNumber 联名卡卡号			
	 */			
	public void setCardnoNumber(String cardnoNumber) {			
	    this.cardnoNumber = cardnoNumber;			
	}

	public String getClientMAC() {
		return clientMAC;
	}

	public void setClientMAC(String clientMAC) {
		this.clientMAC = clientMAC;
	}

	public String getUKseq() {
		return UKseq;
	}

	public void setUKseq(String uKseq) {
		UKseq = uKseq;
	}			
}
