package com.yondervision.mi.result;

import java.util.List;


/** 
* @ClassName: AppApi10108Result 
* @Description: 
* @author gongqi
* 
*/ 
public class AppApi10108Result {
	private List<MyHisQueueBean> queuelist = null;
	private String tips = "";
	
	public List<MyHisQueueBean> getQueuelist() {
		return queuelist;
	}
	public void setQueuelist(List<MyHisQueueBean> queuelist) {
		this.queuelist = queuelist;
	}
	public String getTips() {
		return tips;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}
}