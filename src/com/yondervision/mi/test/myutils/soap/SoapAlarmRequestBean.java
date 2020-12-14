package com.yondervision.mi.test.myutils.soap;

public class SoapAlarmRequestBean {

	private String password;

	private String username;

	private String AlarmId;

	private String AlarmStatusCode;

	private String OpticalLength;

	private String MinOpticalLength;

	private String MaxOpticalLength;

	private String DestinationSiteId;

	private String GisRouteId;

	private String ExtendA;

	private String ExtendB;

	private String ExtendC;

	public SoapAlarmRequestBean() {

	}

	public SoapAlarmRequestBean(String password, String username,
			String AlarmId, String AlarmStatusCode,

			String OpticalLength, String MinOpticalLength,
			String MaxOpticalLength, String DestinationSiteId,

			String GisRouteId, String ExtendA, String ExtendB, String ExtendC) {

		this.password = password;

		this.username = username;

		this.AlarmId = AlarmId;

		this.AlarmStatusCode = AlarmStatusCode;

		this.OpticalLength = OpticalLength;

		this.MinOpticalLength = MinOpticalLength;

		this.MaxOpticalLength = MaxOpticalLength;

		this.DestinationSiteId = DestinationSiteId;

		this.GisRouteId = GisRouteId;

		this.ExtendA = ExtendA;

		this.ExtendB = ExtendB;

		this.ExtendC = ExtendC;

	}

	public String getPassword() {

		return password;

	}

	public void setPassword(String password) {

		this.password = password;

	}

	public String getUsername() {

		return username;

	}

	public void setUsername(String username) {

		this.username = username;

	}

	public String getAlarmId() {

		return AlarmId;

	}

	public void setAlarmId(String alarmId) {

		AlarmId = alarmId;

	}

	public String getAlarmStatusCode() {

		return AlarmStatusCode;

	}

	public void setAlarmStatusCode(String alarmStatusCode) {

		AlarmStatusCode = alarmStatusCode;

	}

	public String getOpticalLength() {

		return OpticalLength;

	}

	public void setOpticalLength(String opticalLength) {

		OpticalLength = opticalLength;

	}

	public String getMinOpticalLength() {

		return MinOpticalLength;

	}

	public void setMinOpticalLength(String minOpticalLength) {

		MinOpticalLength = minOpticalLength;

	}

	public String getMaxOpticalLength() {

		return MaxOpticalLength;

	}

	public void setMaxOpticalLength(String maxOpticalLength) {

		MaxOpticalLength = maxOpticalLength;

	}

	public String getDestinationSiteId() {

		return DestinationSiteId;

	}

	public void setDestinationSiteId(String destinationSiteId) {

		DestinationSiteId = destinationSiteId;

	}

	public String getGisRouteId() {

		return GisRouteId;

	}

	public void setGisRouteId(String gisRouteId) {

		GisRouteId = gisRouteId;

	}

	public String getExtendA() {

		return ExtendA;

	}

	public void setExtendA(String extendA) {

		ExtendA = extendA;

	}

	public String getExtendB() {

		return ExtendB;

	}

	public void setExtendB(String extendB) {

		ExtendB = extendB;

	}

	public String getExtendC() {

		return ExtendC;

	}

	public void setExtendC(String extendC) {

		ExtendC = extendC;

	}

}
