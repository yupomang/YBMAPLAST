package com.yondervision.mi.test.myutils;

import java.io.UnsupportedEncodingException;



public class DESforFSP {
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		byte[] c = DESForJava.encryption("<?xml version=\"1.0\" encoding = \"GBK\"?>	"+
				"<root>"+
				"<head>"+
				"<reqflag>0</reqflag>"+
				"<msgtype>03</msgtype>"+
				"<tr_code>A00070</tr_code> "+
				"<corp_no>0574</corp_no> "+
				"<user_no>2</user_no> "+
				"<req_no>0</req_no> "+
				"<oreq_no>0</oreq_no> "+
				"<tr_acdt>2018-01-17</tr_acdt> "+
				"<tr_time>140000</tr_time> "+
				"<channel>1</channel> "+
				"<sign>03</sign> "+
				"<key>3</key> "+
				"<mac></mac> "+
				"<reserved>909</reserved> "+
				"</head>"+
				"<body>"+
				"<opertype>99</opertype> "+
				"<bankcode>03</bankcode>"+
				"<certitype>1</certitype>"+
				"<certinum>320225196802028614</certinum>"+
				"<free_use1></free_use1>"+
				"<free_use2></free_use2> "+
				"<free_use3></free_use3> "+
				"<free_use4></free_use4> "+
				"<free_use5></free_use5> "+
				"<free_use6></free_use6> "+
				"</body>"+
				"</root>","795085");
		c = DESForJava.bcd_to_asc(c);
		String a=new String(c);
		System.out.println(a);
	}
}
