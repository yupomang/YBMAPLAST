package com.yondervision.mi.shengchan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Timee {

    /*
    获取当前时间之前或之后几分钟 minute
    */

    public static String getTimeByMinute(int minute) {

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MINUTE, minute);

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());

    }
    
    public static void testContinue1() {
        System.out.println("--------测试continue-------");
        for (int i = 1; i <= 5; i++) {
            if (i == 3) continue;
            System.out.println("i=" + i);
        }
    } 
	public static void main(String[] args) {
		testContinue1();
		
//		System.out.println(getTimeByMinute(-18));
//		System.out.println(String.valueOf(getTimeByMinute(-18)));
//		Date date = new Date();
//		String requestTime = String.valueOf(date.getTime()-1080000);
//		System.out.println(date.getTime());
	    long timelnMillis = 1506337288161L;
	    String newTypeTime = getTimeString(timelnMillis);
//	    System.out.println(newTypeTime);
	    }
	    public static String getTimeString(long timelnMillis){
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTimeInMillis(timelnMillis);
	    Date date = calendar.getTime();
	    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
	    String newTypeDate = f.format(date);
	    return newTypeDate;   
	    }
	    
		long starTime=System.currentTimeMillis();
		long endTime=System.currentTimeMillis();
		long Time=endTime-starTime;
}
