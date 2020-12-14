/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.result
 * 文件名：     PtlApi70000ResultAttr.java
 * 创建日期：2013-10-7
 */
package com.yondervision.mi.result;

/**
 * 栏目管理查询树所包含的业务信息
 * 
 * @author gongqi
 * 
 */
public class Page70701QryResultAttr {

    private String centerid;

    private String centername;
    
    private Integer dicid;

    private String itemid;

    private String itemval;

    private Integer updicid;
    
    private String updicname;

	public String getCenterid() {
        return centerid;
    }

    public void setCenterid(String centerid) {
        this.centerid = centerid;
    }

    public String getCentername() {
		return centername;
	}

	public void setCentername(String centername) {
		this.centername = centername;
	}
	
    public Integer getDicid() {
        return dicid;
    }

    public void setDicid(Integer dicid) {
        this.dicid = dicid;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getItemval() {
        return itemval;
    }

    public void setItemval(String itemval) {
        this.itemval = itemval;
    }

    public Integer getUpdicid() {
        return updicid;
    }
    
    public void setUpdicid(Integer updicid) {
        this.updicid = updicid;
    }
    
	public String getUpdicname() {
		return updicname;
	}

	public void setUpdicname(String updicname) {
		this.updicname = updicname;
	}

}
