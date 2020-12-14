package com.yondervision.mi.result;

import com.yondervision.mi.dto.Mi701WithBLOBs;

public class AppApi70002Result extends Mi701WithBLOBs{
	
	private String centername;

	/**
	 * @return the centername
	 */
	public String getCentername() {
		return centername;
	}

	/**
	 * @param centername the centername to set
	 */
	public void setCentername(String centername) {
		this.centername = centername;
	}
	
}
