package com.yondervision.mi.result;

/** 
* @ClassName: AppApi40301Result 
* @Description: 版本更新
* @author Caozhongyan
* @date Oct 15, 2013 2:17:10 PM   
* 
*/ 
public class AppApi40301Result {
	//版板号 
	private String version_number="";
	//发布时间 
	private String effective_date="";
	//下载地址 
	private String download_url="";
	//更新描述 
	private String details="";
	//更新大小 
	private String updatesize="";
	//是否强制更新 备注：强制更新"true"，否则为"false"
	private String updateforce="";
	//二维码地址
	private String twodimensional = "";
	
	public String getTwodimensional() {
		return twodimensional;
	}
	public void setTwodimensional(String twodimensional) {
		this.twodimensional = twodimensional;
	}
	public String getVersion_number() {
		return version_number;
	}
	public void setVersion_number(String version_number) {
		this.version_number = version_number;
	}
	public String getEffective_date() {
		return effective_date;
	}
	public void setEffective_date(String effective_date) {
		this.effective_date = effective_date;
	}
	public String getDownload_url() {
		return download_url;
	}
	public void setDownload_url(String download_url) {
		this.download_url = download_url;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getUpdatesize() {
		return updatesize;
	}
	public void setUpdatesize(String updatesize) {
		this.updatesize = updatesize;
	}
	public String getUpdateforce() {
		return updateforce;
	}
	public void setUpdateforce(String updateforce) {
		this.updateforce = updateforce;
	}
	
}
