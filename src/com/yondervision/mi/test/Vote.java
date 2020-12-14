package com.yondervision.mi.test;
/*
 * 投票类：定义投票银行的基本信息，标题，选项，展示内容等
 */
public class Vote {
	
	public String bankname = ""; 
	public String bankid = "";
	public String p_tpl_ids = ""; //投票行id
	public String busitype = "";	   //业务类型 ：贷款，归集提取
	public String[] q_id = new String[3];           //小题目id
	public String[][] option_id = new String[3][3];//选项id
	public String[][] option_score = new String[3][3];//选项分数
	
	
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getBankid() {
		return bankid;
	}
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	public String getP_tpl_ids() {
		return p_tpl_ids;
	}
	public void setP_tpl_ids(String p_tpl_ids) {
		this.p_tpl_ids = p_tpl_ids;
	}
	public String getBusitype() {
		return busitype;
	}
	public void setBusitype(String busitype) {
		this.busitype = busitype;
	}
	
	public String[] getQ_id() {
		return q_id;
	}
	public void setQ_id(String[] q_id) {
		this.q_id = q_id;
	}
	
	public String[][] getOption_id() {
		return option_id;
	}
	public void setOption_id(String[][] option_id) {
		this.option_id = option_id;
	}
	public String[][] getOption_score() {
		return option_score;
	}
	public void setOption_score(String[][] option_score) {
		this.option_score = option_score;
	}
}