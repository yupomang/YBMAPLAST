package com.yondervision.mi.test.myutils.soap;

public class SoapAlarmRespondBean {

private String Latitude;
private String Longitude;
private String url_image;
private String Message;
private String CodeExp;
private String ExtendA;
private String ExtendB;
private String ExtendC;

public SoapAlarmRespondBean() {

}

public SoapAlarmRespondBean(String Latitude, String Longitude, String url_image, String Message,

String CodeExp, String ExtendA, String ExtendB, String ExtendC) {

this.Latitude = Latitude;

this.Longitude = Longitude;

this.url_image = url_image;

this.Message = Message;

this.CodeExp = CodeExp;

this.ExtendA = ExtendA;

this.ExtendB = ExtendB;

this.ExtendC = ExtendC;

}

public String getLatitude() {

return Latitude;

}

public void setLatitude(String latitude) {

Latitude = latitude;

}

public String getLongitude() {

return Longitude;

}

public void setLongitude(String longitude) {

Longitude = longitude;

}

public String getUrl_image() {

return url_image;

}

public void setUrl_image(String url_image) {

this.url_image = url_image;

}

public String getMessage() {

return Message;

}

public void setMessage(String message) {

Message = message;

}

public String getCodeExp() {

return CodeExp;

}

public void setCodeExp(String codeExp) {

CodeExp = codeExp;

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