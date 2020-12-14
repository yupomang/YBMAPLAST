package com.yondervision.mi.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

 

public class Datelet {
	 /**
     * The const date field defination
     */
    public static final int YEAR = 0;
    public static final int MONTH = 1;
    public static final int DAY = 2;
    public static final int HOUR = 3;
    public static final int MINUTE = 4;
    public static final int SECOND = 5;

    /**
     * 天、小时的毫秒数
     */
    public static final long DAYS = 24 * 60 * 60 * 1000;
    public static final long HOURS = 60 * 60 * 1000;

    /**
     * The calendar to manuplate the date
     */
    GregorianCalendar cal = new GregorianCalendar();

    /**
     * The constructor.
     */
    protected Datelet() {}

    /**
     * Get the DateAide instance with current date.
     */
    public static Datelet getInstance() {
        return new Datelet();
    }

    /**
     * Get the DateAide instance with specified date.
     */
    public static Datelet getInstance(java.util.Date date) {
        Datelet datelet = new Datelet();
        datelet.setDate(date);

        return datelet;
    }
    
    /**
     * Get the DateAide instance with specified date.
     */
    public static Datelet getInstance(String dateStr) {
        Datelet datelet = new Datelet();
        String[] ds=dateStr.split("-");
        int year=Integer.parseInt(ds[0])-1900;
        int month=Integer.parseInt(ds[1])-1;
        int day=Integer.parseInt(ds[2]);        
        datelet.setDate(new java.util.Date(year,month,day));
        return datelet;
    }

    /**
     * 获取当前时间戳字符串,格式'yyyy-mm-dd tt:mm:ss.nnn'
     * @return java.sql.Timestamp 当前时间戳
     */
    public static String getCurrentTsString() {
        return (new java.sql.Timestamp(System.currentTimeMillis())).toString();
    }

    /**
     * 获取当前日期时间字符串,格式'yyyy-mm-dd tt:mm:ss'
     * @return String 当前日期时间字符串
     */
    public static String getCurrentDateTime() {
        return (new java.sql.Timestamp(System.currentTimeMillis())).toString().substring(0, 19);
    }

    /**
     * 获取当前日期字符串，格式'yyyy-mm-dd'
     * @return String 当前日期串
     */
    public static String getCurrentDateString() {
        return (new java.sql.Date(System.currentTimeMillis())).toString();
    }
    
 

    /**
     * 获取当前日期
     * @return java.sql.Date 当前日期
     */
    public static java.sql.Date getCurrentDate() {
        return new java.sql.Date(System.currentTimeMillis());
    }

    /**
     * 获取当前时间戳
     * @return java.sql.Timestamp 当前时间戳
     */
    public static java.sql.Timestamp getCurrentTimestamp() {
        return new java.sql.Timestamp(System.currentTimeMillis());
    }

    /**
     * 获取当前时间
     * @return java.sql.Time 当前时间
     */
    public static java.sql.Time getCurrentTime() {
        return new java.sql.Time(System.currentTimeMillis());
    }

    /**
     * 获取指定字符串代表的日期值
     * @param dateString String 当前日期字符串
     * @return java.sql.Time 当前时间
     */
    public static java.sql.Date dateValue(String dateString) {
        return java.sql.Date.valueOf(dateString);
    }

    /**
     * 获取指定字符串代表的日期戳值
     * @param tsString String 当前日期戳字符串
     * @return java.sql.Time 当前时间
     */
    public static java.sql.Timestamp timestampValue(String tsString) {
        return java.sql.Timestamp.valueOf(tsString);
    }

    /**
     * 获取时间段的天小时描述
     * @param time
     * @return String 天小时描述，格式"nn天xx小时"
     */
    public static String hourDays(long time) {
        long days = time / Datelet.DAYS;
        long hours = (time - days * Datelet.DAYS) / Datelet.HOURS;

        String desc = "";
        if (days > 0) {
            desc = days + "天";
        }

        if (hours > 0) {
            desc += hours + "小时";
        }

        if (days < 1 && hours < 1) {
            desc = "不足1小时";
        }

        return desc;
    }

    /**
     * Adjust the value of specified field.
     */
    public void adjust(int field, int amount) {
    	 
        switch (field) {
            case YEAR :
             
                cal.add(Calendar.YEAR, amount);
             
                break;
            case MONTH :
            	cal.add(Calendar.DATE, 1);
                cal.add(Calendar.MONTH, amount);
                cal.add(Calendar.DATE, -1);
                break;
            case DAY :
                cal.add(Calendar.DATE, amount);
                break;
            case HOUR :
                cal.add(Calendar.HOUR, amount);
                break;
            case MINUTE :
                cal.add(Calendar.MINUTE, amount);
                break;
            case SECOND :
                cal.add(Calendar.SECOND, amount);
                break;
            default :
                }
    
     
    }

    /**
     * Set the date to the speicfied date.
     *
     * @param date The specified date
     */
    public void setDate(java.util.Date date) {
        cal.setTime(date);
    }

    /**
     * Get the speicfied date as java.sql.Date object.
     *
     * @return The java.sql.Date object of the date
     */
    public java.sql.Date getDate() {
        return (new java.sql.Date(cal.getTime().getTime()));
    }

    /**
     * Convert the specified date to date string.
     */
    public String getDateAsString() {
        return (new java.sql.Date(cal.getTime().getTime())).toString();
    }

    /**
     * Convert the specified date to timestamp string.
     * 返回19位字符串标识
     */
    public String getDateAsTsString() {
        return (new java.sql.Timestamp(cal.getTime().getTime())).toString().substring(0, 19);
    }

    /**
     * Get the year of the date.
     */
    public int getYear() {
        return cal.get(Calendar.YEAR);
    }

    /**
     * Get the month of the date.
     */
    public int getMonth() {
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * Get the month day of the date.
     */
    public int getDay() {
        return cal.get(Calendar.DATE);
    }
    
    /**
     * Get the quarter of the date
     * @return
     */
    public int getQuarter(){
        int quarter = 1;
        int month = getMonth();
        switch(month){
        	case 1:
        	case 2:
        	case 3:
        	    quarter = 1;
        	    break;
        	case 4:
        	case 5:
        	case 6:
        	    quarter = 2;
        	    break;
        	case 7:
        	case 8:
        	case 9:
        	    quarter = 3;
        	    break;
        	
        	case 10:
        	case 11:
        	case 12:
        	    quarter = 4;
        	    break;
        }
        
        return quarter;
    }

    /**
     * Format the month or day field to 2 chars.
     */
    public String formatMonthDay(int value) {
        String result = null;

        result = value >= 10 ? value + "" : "0" + value;

        return result;
    }
    
    /**
     * 获得当前系统时间的周数
     * @return
     */
    public  int getSysWeek() {
		int iValue = 1;
        Calendar calNew = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try{
            String strdate = String.valueOf(getYear()) +"-01-01";
	        java.util.Date dd = formatter.parse(strdate);
	        calNew.setTime(dd);
	        int week = calNew.get(Calendar.DAY_OF_WEEK)-1;
	        
	        switch(week){
	        	case 0:
	        	    calNew.add(Calendar.DATE,1);
	        	    break;
	        	case 1:
	        	    calNew.add(Calendar.DATE,7);
	        	    break;
	        	case 2:
	        	    calNew.add(Calendar.DATE,6);
	        	    break;
	        	case 3:
	        	    calNew.add(Calendar.DATE,5);
	        	    break;
	        	case 4:
	        	    calNew.add(Calendar.DATE,4);
	        	    break;
	        	case 5:
	        	    calNew.add(Calendar.DATE,3);
	        	    break;
	        	case 6:
	        	    calNew.add(Calendar.DATE,2);
	        	    break;
	
	        }
	        
	        int currentDay = cal.get(Calendar.DAY_OF_YEAR);
	        int newDay     = calNew.get(Calendar.DAY_OF_YEAR);
	        
	        if(currentDay<newDay){
	            iValue =1;
	        }
	        else{
	            iValue = (currentDay-newDay)/7 + 2;
	        }
	        
        }
        catch(Exception e){
            
        }
		return iValue;
	}
    
    /**
     * 根据指定的年、月、日计算周数
     * @param year
     * @param month
     * @param day
     * @return
     */
    public  int getWeek(String year,String month,String day) {
		int iValue = 1;
        Calendar calNew = Calendar.getInstance();
        Calendar calbegin = Calendar.getInstance();
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try{
            String strbegin = String.valueOf(year +"-01-01");
            String strdate  = String.valueOf(year + "-" + month + "-" + day);
            
            java.util.Date begin = formatter.parse(strbegin);
	        java.util.Date dd = formatter.parse(strdate);
	        
	        calbegin.setTime(begin);
	        calNew.setTime(dd);
	        
	        int week = calbegin.get(Calendar.DAY_OF_WEEK)-1;
	        
	        switch(week){
	        	case 0:
	        	    calbegin.add(Calendar.DATE,1);
	        	    break;
	        	case 1:
	        	    calbegin.add(Calendar.DATE,7);
	        	    break;
	        	case 2:
	        	    calbegin.add(Calendar.DATE,6);
	        	    break;
	        	case 3:
	        	    calbegin.add(Calendar.DATE,5);
	        	    break;
	        	case 4:
	        	    calbegin.add(Calendar.DATE,4);
	        	    break;
	        	case 5:
	        	    calbegin.add(Calendar.DATE,3);
	        	    break;
	        	case 6:
	        	    calbegin.add(Calendar.DATE,2);
	        	    break;
	
	        }
	        
	        int currentDay = calNew.get(Calendar.DAY_OF_YEAR);
	        int beginDay     = calbegin.get(Calendar.DAY_OF_YEAR);
	        
	        if(currentDay<beginDay){
	            iValue =1;
	        }
	        else{
	            iValue = (currentDay-beginDay)/7 + 2;
	        }
	        
        }
        catch(Exception e){
            
        }
		return iValue;
	}
    
    /**
     * 将传入参数转换为格式yyyy-mm-dd的字符串
     * @param cal Calendar类型
     * @return yyyy-mm-dd 字符串
     */
    public String cal2String(Calendar cal){
    	int iY;
		int iM;
		int iD;
    	iY = cal.get(Calendar.YEAR);
		iM = cal.get(Calendar.MONTH) + 1;
		iD = cal.get(Calendar.DAY_OF_MONTH);
		String strYear = "" + iY;
		String strMon = "";
		String strDay = "";
		if (iM < 10) {
			strMon = "0" + iM;
		} else {
			strMon = "" + iM;
		}
		if (iD < 10) {
			strDay = "0" + iD;
		} else {
			strDay = "" + iD;
		}
		String Date = strYear + "-" + strMon + "-" + strDay;
    	return Date;
    }
}
