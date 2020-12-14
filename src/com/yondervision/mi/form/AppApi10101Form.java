package com.yondervision.mi.form;

/** 
* @ClassName: AppApi10101Form 
* @Description: 提取明细查询请求Form
* @author Caozhongyan
* @date Sep 27, 2013 9:15:58 AM 
* 
*/ 
public class AppApi10101Form extends AppApiCommonForm {
	private String selectType;						
	/** 查询信息条件值 */						
	private String selectValue;						
	private String positionX;
	
	private String positionY;
	
	private String websiteType="";
	private String areaId="";
	private String websiteName="";
	private String address="";
	private String businessName="";
	private String ispaging="";//是否分页
	private String pagenum="";
	private String pagerows="";
	
	public String getIspaging() {
		return ispaging;
	}
	public void setIspaging(String ispaging) {
		this.ispaging = ispaging;
	}
	public String getPositionX() {
		return positionX;
	}
	public void setPositionX(String positionX) {
		this.positionX = positionX;
	}
	public String getPositionY() {
		return positionY;
	}
	public void setPositionY(String positionY) {
		this.positionY = positionY;
	}						
	/**						
	 *<pre> 执行获取查询类型(1.模糊查询信息;2.查询全部信息;3.查询区域信息)处理 </pre>						
	 * @return selectType 查询类型(1.模糊查询信息;2.查询全部信息;3.查询区域信息)						
	 */						
	public String getSelectType() {						
	    return selectType;						
	}						
							
	/**						
	 *<pre> 执行设定查询类型(1.模糊查询信息;2.查询全部信息;3.查询区域信息)处理 </pre>						
	 * @param selectType 查询类型(1.模糊查询信息;2.查询全部信息;3.查询区域信息)						
	 */						
	public void setSelectType(String selectType) {						
	    this.selectType = selectType;						
	}						
							
	/**						
	 *<pre> 执行获取查询信息条件值处理 </pre>						
	 * @return selectValue 查询信息条件值						
	 */						
	public String getSelectValue() {						
	    return selectValue;						
	}						
							
	/**						
	 *<pre> 执行设定查询信息条件值处理 </pre>						
	 * @param selectValue 查询信息条件值						
	 */						
	public void setSelectValue(String selectValue) {						
	    this.selectValue = selectValue;						
	}
	public String getWebsiteType() {
		return websiteType;
	}
	public void setWebsiteType(String websiteType) {
		this.websiteType = websiteType;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getWebsiteName() {
		return websiteName;
	}
	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getPagenum() {
		return pagenum;
	}
	public void setPagenum(String pagenum) {
		this.pagenum = pagenum;
	}
	public String getPagerows() {
		return pagerows;
	}
	public void setPagerows(String pagerows) {
		this.pagerows = pagerows;
	}						

}
