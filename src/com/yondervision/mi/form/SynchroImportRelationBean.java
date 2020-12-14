package com.yondervision.mi.form;

import java.util.ArrayList;
import java.util.List;

public class SynchroImportRelationBean {
	private String docid = "";
	private List<SynchroImportFileInfoBean> fileinfolist = new ArrayList<SynchroImportFileInfoBean>();
	public String getDocid() {
		return docid;
	}
	public void setDocid(String docid) {
		this.docid = docid;
	}
	public List<SynchroImportFileInfoBean> getFileinfolist() {
		return fileinfolist;
	}
	public void setFileinfolist(List<SynchroImportFileInfoBean> fileinfolist) {
		this.fileinfolist = fileinfolist;
	}
}
