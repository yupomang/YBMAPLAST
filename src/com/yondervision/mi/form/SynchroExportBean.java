package com.yondervision.mi.form;

import java.util.ArrayList;
import java.util.List;

public class SynchroExportBean {
	List<SynchroExportContentPubInfoBean> publishList = new ArrayList<SynchroExportContentPubInfoBean>();
	List<SynchroExportContentScInfoBean> deleteList = new ArrayList<SynchroExportContentScInfoBean>();
	public List<SynchroExportContentPubInfoBean> getPublishList() {
		return publishList;
	}
	public void setPublishList(List<SynchroExportContentPubInfoBean> publishList) {
		this.publishList = publishList;
	}
	public List<SynchroExportContentScInfoBean> getDeleteList() {
		return deleteList;
	}
	public void setDeleteList(List<SynchroExportContentScInfoBean> deleteList) {
		this.deleteList = deleteList;
	}
	
}
