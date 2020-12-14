package com.yondervision.mi.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MsgSendApi001Service {
	
	public String send(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public void mapHttpSend(HttpServletRequest request, HttpServletResponse response, String...params) throws Exception;
	
	public String synchroExportSend(String centerid, String param) throws Exception;
}

