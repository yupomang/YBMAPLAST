package com.yondervision.mi.result;

import net.sf.json.JSONArray;

import com.yondervision.mi.dto.Mi701;
import com.yondervision.mi.dto.Mi701WithBLOBs;

public class WebApi70205_queryResult extends Mi701WithBLOBs {
	private JSONArray forumJsonArray;
	private JSONArray columnsJsonArray;
	
	public JSONArray getForumJsonArray() {
		return forumJsonArray;
	}
	public void setForumJsonArray(JSONArray forumJsonArray) {
		this.forumJsonArray = forumJsonArray;
	}
	public JSONArray getColumnsJsonArray() {
		return columnsJsonArray;
	}
	public void setColumnsJsonArray(JSONArray columnsJsonArray) {
		this.columnsJsonArray = columnsJsonArray;
	}
}
