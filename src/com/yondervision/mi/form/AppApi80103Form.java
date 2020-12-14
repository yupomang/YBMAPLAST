package com.yondervision.mi.form;

public class AppApi80103Form extends AppApiCommonForm {
	/** 账户类型 */				
	private String zhlx = "";
	/** 结转年度 */				
	private String jznd = "";
					
	private String dwzh = "";
	public String getDwzh() {
		return dwzh;
	}

	public void setDwzh(String dwzh) {
		this.dwzh = dwzh;
	}

	/**
	 * <pre> 执行获取账户类型处理 </pre>	
	 * @return zhlx 账户类型
	 */
	public String getZhlx() {
		return zhlx;
	}

	/**
	 * <pre> 执行设定账户类型处理 </pre>
	 * @param zhlx 账户类型
	 */
	public void setZhlx(String zhlx) {
		this.zhlx = zhlx;
	}

	/**
	 * <pre> 执行获取结转年度处理 </pre>
	 * @return  jznd 结转年度
	 */
	public String getJznd() {
		return jznd;
	}

	/**
	 * <pre> 执行设定结转年度处理 </pre>
	 * @param jznd 结转年度
	 */
	public void setJznd(String jznd) {
		this.jznd = jznd;
	}
}
