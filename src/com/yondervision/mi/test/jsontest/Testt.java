package com.yondervision.mi.test.jsontest;

import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;
public class Testt {
		public static void main(String[] args) {
			String DashujuJson ="{'code':'00','msg':'成功','datas':[{'mCardId':'330204197508036114','registrationOrg':'海曙区民政局','fName':'毛蓓华','registrationDate':'2001-11-09','id':'14644543','businessType':'结婚登记','mName':'邵建宁','fCardId':'330203750113032','tong_time':'2017-04-26 02:03:09.0'}],'dataCount':1,'recode':'000000'}";		
	        Student stu = new Student();
			JSONObject result1 =  JSONObject.fromObject(DashujuJson);
	        stu = (Student)JSONObject.toBean(result1, Student.class);
	        System.out.println(stu);
	    }

}
