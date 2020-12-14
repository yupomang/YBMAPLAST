/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dto
 * 文件名：     CMi401.java
 * 创建日期：2013-10-8
 */
package com.yondervision.mi.dto;


/**
 * @author LinXiaolong
 *
 */
public class CMi401 extends Mi401 {
	
	private String jgh="" ;
	/** 中心ID **/
//	private String centerId;
	/** 中心名称 **/
	private String centerName;
	/** WEB用户ID **/
	private String userid;
	/** WEB用户名称 **/
	private String username;
	/** WEB用户IP **/
	private String longinip;
	/** WEB用户角色ID(多个角色用逗号分割) **/
	private String roleid;
	/** 身份证号 */			
	private String certinum = "";
	/** 电话号 */
	private String tel = "";
	/** 业务类型 */			
	private String buzType = "";
	/** 模板编号 */	
	private String channelTemplate = "";
	/** 参数1 */			
	private String apiData1 = "";			
	/** 参数2 */			
	private String apiData2 = "";			
	/** 参数3 */			
	private String apiData3 = "";			
	/** 参数4 */			
	private String apiData4 = "";			
	/** 参数5 */			
	private String apiData5 = "";			
	/** 参数6 */			
	private String apiData6 = "";			
	/** 参数7 */			
	private String apiData7 = "";			
	/** 参数8 */			
	private String apiData8 = "";			
	/** 参数9 */			
	private String apiData9 = "";			
	/** 参数10 */			
	private String apiData10 = "";			
	/** 参数11 */			
	private String apiData11 = "";			
	/** 参数12 */			
	private String apiData12 = "";			
	/** 参数13 */			
	private String apiData13 = "";			
	/** 参数14 */			
	private String apiData14 = "";			
	/** 参数15 */			
	private String apiData15 = "";			
	/** 参数16 */			
	private String apiData16 = "";			
	/** 参数17 */			
	private String apiData17 = "";			
	/** 参数18 */			
	private String apiData18 = "";			
	/** 参数19 */			
	private String apiData19 = "";			
	/** 参数20 */			
	private String apiData20 = "";	
	/** 渠道信息 */	
	private String channel = "";
	/** 应用标识 */
	private String appid = "";
	/** 应用KEY */
	private String appkey = "";
	/**单图文404表id*/
	private String msmscommsgid = "";
	/**单图文404表freeuse3 粉丝可评论或者全部可评论*/
	private String isAllComment = "";
	/**单图文404表freeuse4 是否打开评论*/
	private String canComment = "";
	
	public String getIsAllComment() {
		return isAllComment;
	}

	public void setIsAllComment(String isAllComment) {
		this.isAllComment = isAllComment;
	}

	public String getCanComment() {
		return canComment;
	}

	public void setCanComment(String canComment) {
		this.canComment = canComment;
	}

	public String getMsmscommsgid() {
		return msmscommsgid;
	}

	public void setMsmscommsgid(String msmscommsgid) {
		this.msmscommsgid = msmscommsgid;
	}

	/** 批量文件名*/
	private String filename = "";
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getAppid() {
		return appid;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getChannelTemplate() {
		return channelTemplate;
	}

	public void setChannelTemplate(String channelTemplate) {
		this.channelTemplate = channelTemplate;
	}

	/**			
	 *<pre> 执行获取参数1处理 </pre>			
	 * @return apiData1 参数1			
	 */			
	public String getApiData1() {			
	    return apiData1;			
	}			
				
	/**			
	 *<pre> 执行设定参数1处理 </pre>			
	 * @param apiData1 参数1			
	 */			
	public void setApiData1(String apiData1) {			
	    this.apiData1 = apiData1;			
	}			
				
	/**			
	 *<pre> 执行获取参数2处理 </pre>			
	 * @return apiData2 参数2			
	 */			
	public String getApiData2() {			
	    return apiData2;			
	}			
				
	/**			
	 *<pre> 执行设定参数2处理 </pre>			
	 * @param apiData2 参数2			
	 */			
	public void setApiData2(String apiData2) {			
	    this.apiData2 = apiData2;			
	}			
				
	/**			
	 *<pre> 执行获取参数3处理 </pre>			
	 * @return apiData3 参数3			
	 */			
	public String getApiData3() {			
	    return apiData3;			
	}			
				
	/**			
	 *<pre> 执行设定参数3处理 </pre>			
	 * @param apiData3 参数3			
	 */			
	public void setApiData3(String apiData3) {			
	    this.apiData3 = apiData3;			
	}			
				
	/**			
	 *<pre> 执行获取参数4处理 </pre>			
	 * @return apiData4 参数4			
	 */			
	public String getApiData4() {			
	    return apiData4;			
	}			
				
	/**			
	 *<pre> 执行设定参数4处理 </pre>			
	 * @param apiData4 参数4			
	 */			
	public void setApiData4(String apiData4) {			
	    this.apiData4 = apiData4;			
	}			
				
	/**			
	 *<pre> 执行获取参数5处理 </pre>			
	 * @return apiData5 参数5			
	 */			
	public String getApiData5() {			
	    return apiData5;			
	}			
				
	/**			
	 *<pre> 执行设定参数5处理 </pre>			
	 * @param apiData5 参数5			
	 */			
	public void setApiData5(String apiData5) {			
	    this.apiData5 = apiData5;			
	}			
				
	/**			
	 *<pre> 执行获取参数6处理 </pre>			
	 * @return apiData6 参数6			
	 */			
	public String getApiData6() {			
	    return apiData6;			
	}			
				
	/**			
	 *<pre> 执行设定参数6处理 </pre>			
	 * @param apiData6 参数6			
	 */			
	public void setApiData6(String apiData6) {			
	    this.apiData6 = apiData6;			
	}			
				
	/**			
	 *<pre> 执行获取参数7处理 </pre>			
	 * @return apiData7 参数7			
	 */			
	public String getApiData7() {			
	    return apiData7;			
	}			
				
	/**			
	 *<pre> 执行设定参数7处理 </pre>			
	 * @param apiData7 参数7			
	 */			
	public void setApiData7(String apiData7) {			
	    this.apiData7 = apiData7;			
	}			
				
	/**			
	 *<pre> 执行获取参数8处理 </pre>			
	 * @return apiData8 参数8			
	 */			
	public String getApiData8() {			
	    return apiData8;			
	}			
				
	/**			
	 *<pre> 执行设定参数8处理 </pre>			
	 * @param apiData8 参数8			
	 */			
	public void setApiData8(String apiData8) {			
	    this.apiData8 = apiData8;			
	}			
				
	/**			
	 *<pre> 执行获取参数9处理 </pre>			
	 * @return apiData9 参数9			
	 */			
	public String getApiData9() {			
	    return apiData9;			
	}			
				
	/**			
	 *<pre> 执行设定参数9处理 </pre>			
	 * @param apiData9 参数9			
	 */			
	public void setApiData9(String apiData9) {			
	    this.apiData9 = apiData9;			
	}			
				
	/**			
	 *<pre> 执行获取参数10处理 </pre>			
	 * @return apiData10 参数10			
	 */			
	public String getApiData10() {			
	    return apiData10;			
	}			
				
	/**			
	 *<pre> 执行设定参数10处理 </pre>			
	 * @param apiData10 参数10			
	 */			
	public void setApiData10(String apiData10) {			
	    this.apiData10 = apiData10;			
	}			
				
	/**			
	 *<pre> 执行获取参数11处理 </pre>			
	 * @return apiData11 参数11			
	 */			
	public String getApiData11() {			
	    return apiData11;			
	}			
				
	/**			
	 *<pre> 执行设定参数11处理 </pre>			
	 * @param apiData11 参数11			
	 */			
	public void setApiData11(String apiData11) {			
	    this.apiData11 = apiData11;			
	}			
				
	/**			
	 *<pre> 执行获取参数12处理 </pre>			
	 * @return apiData12 参数12			
	 */			
	public String getApiData12() {			
	    return apiData12;			
	}			
				
	/**			
	 *<pre> 执行设定参数12处理 </pre>			
	 * @param apiData12 参数12			
	 */			
	public void setApiData12(String apiData12) {			
	    this.apiData12 = apiData12;			
	}			
				
	/**			
	 *<pre> 执行获取参数13处理 </pre>			
	 * @return apiData13 参数13			
	 */			
	public String getApiData13() {			
	    return apiData13;			
	}			
				
	/**			
	 *<pre> 执行设定参数13处理 </pre>			
	 * @param apiData13 参数13			
	 */			
	public void setApiData13(String apiData13) {			
	    this.apiData13 = apiData13;			
	}			
				
	/**			
	 *<pre> 执行获取参数14处理 </pre>			
	 * @return apiData14 参数14			
	 */			
	public String getApiData14() {			
	    return apiData14;			
	}			
				
	/**			
	 *<pre> 执行设定参数14处理 </pre>			
	 * @param apiData14 参数14			
	 */			
	public void setApiData14(String apiData14) {			
	    this.apiData14 = apiData14;			
	}			
				
	/**			
	 *<pre> 执行获取参数15处理 </pre>			
	 * @return apiData15 参数15			
	 */			
	public String getApiData15() {			
	    return apiData15;			
	}			
				
	/**			
	 *<pre> 执行设定参数15处理 </pre>			
	 * @param apiData15 参数15			
	 */			
	public void setApiData15(String apiData15) {			
	    this.apiData15 = apiData15;			
	}			
				
	/**			
	 *<pre> 执行获取参数16处理 </pre>			
	 * @return apiData16 参数16			
	 */			
	public String getApiData16() {			
	    return apiData16;			
	}			
				
	/**			
	 *<pre> 执行设定参数16处理 </pre>			
	 * @param apiData16 参数16			
	 */			
	public void setApiData16(String apiData16) {			
	    this.apiData16 = apiData16;			
	}			
				
	/**			
	 *<pre> 执行获取参数17处理 </pre>			
	 * @return apiData17 参数17			
	 */			
	public String getApiData17() {			
	    return apiData17;			
	}			
				
	/**			
	 *<pre> 执行设定参数17处理 </pre>			
	 * @param apiData17 参数17			
	 */			
	public void setApiData17(String apiData17) {			
	    this.apiData17 = apiData17;			
	}			
				
	/**			
	 *<pre> 执行获取参数18处理 </pre>			
	 * @return apiData18 参数18			
	 */			
	public String getApiData18() {			
	    return apiData18;			
	}			
				
	/**			
	 *<pre> 执行设定参数18处理 </pre>			
	 * @param apiData18 参数18			
	 */			
	public void setApiData18(String apiData18) {			
	    this.apiData18 = apiData18;			
	}			
				
	/**			
	 *<pre> 执行获取参数19处理 </pre>			
	 * @return apiData19 参数19			
	 */			
	public String getApiData19() {			
	    return apiData19;			
	}			
				
	/**			
	 *<pre> 执行设定参数19处理 </pre>			
	 * @param apiData19 参数19			
	 */			
	public void setApiData19(String apiData19) {			
	    this.apiData19 = apiData19;			
	}			
				
	/**			
	 *<pre> 执行获取参数20处理 </pre>			
	 * @return apiData20 参数20			
	 */			
	public String getApiData20() {			
	    return apiData20;			
	}			
				
	/**			
	 *<pre> 执行设定参数20处理 </pre>			
	 * @param apiData20 参数20			
	 */			
	public void setApiData20(String apiData20) {			
	    this.apiData20 = apiData20;			
	}			

	
	public String getCertinum() {
		return certinum;
	}

	public void setCertinum(String certinum) {
		this.certinum = certinum;
	}

	public String getBuzType() {
		return buzType;
	}

	public void setBuzType(String buzType) {
		this.buzType = buzType;
	}

	/**
	 * @return the centerId
	 */
//	public String getCenterId() {
//		return centerId;
//	}

	/**
	 * @param centerId
	 *            the centerId to set
	 */
//	public void setCenterId(String centerId) {
//		this.centerId = centerId;
//	}

	/**
	 * @return the centerName
	 */
	public String getCenterName() {
		return centerName;
	}

	/**
	 * @param centerName
	 *            the centerName to set
	 */
	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid
	 *            the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the longinip
	 */
	public String getLonginip() {
		return longinip;
	}

	/**
	 * @param longinip
	 *            the longinip to set
	 */
	public void setLonginip(String longinip) {
		this.longinip = longinip;
	}

	/**
	 * @return the roleid
	 */
	public String getRoleid() {
		return roleid;
	}

	/**
	 * @param roleid
	 *            the roleid to set
	 */
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getJgh() {
		return jgh;
	}

	public void setJgh(String jgh) {
		this.jgh = jgh;
	}
	
}
