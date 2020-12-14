package com.yondervision.mi.util;

import java.text.DecimalFormat;

/** 
* @ClassName: Distance 
* @Description: 距离计算
* @author Caozhongyan
* @date Oct 11, 2013 3:53:31 PM   
* 
*/ 
public class Distance {
	
	public final static double EARTH_RADIUS_KM = 6378.137;
	
	public static double getDistance(double lng1, double lat1, double lng2,
			double lat2) {
		double radLat1 = Math.toRadians(lat1);
		double radLat2 = Math.toRadians(lat2);
		double radLng1 = Math.toRadians(lng1);
		double radLng2 = Math.toRadians(lng2);
		double deltaLat = radLat1 - radLat2;
		double deltaLng = radLng1 - radLng2;
		double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math
				.sin(deltaLat / 2), 2)
				+ Math.cos(radLat1)
				* Math.cos(radLat2)
				* Math.pow(Math.sin(deltaLng / 2), 2)));
		distance = distance * EARTH_RADIUS_KM;
		DecimalFormat df = new DecimalFormat("######0.00");
		distance = Double.valueOf(df.format(distance));
		//distance = Math.round(distance * 10000) / 10000;
		return distance;
	}
}
