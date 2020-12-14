package com.yondervision.mi.test.myutils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Lottery {
	  public static void main(String[] args) {
	        int nums = 6;	//红球个数
	        String[] users = new String[]{
	                "01", "02", "03", "04", "05", "06", "07", "08", "09","10",
	                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", 
	                "21", "22", "23", "24", "25", "26", "27", "28", "29",
	                "30", "31", "32", "33",
	        };
	        lottery(users, nums);
	        
	        int nums2 = 1;	//蓝球个数
	        String[] users2 = new String[]{
	                "01", "02", "03", "04", "05", "06", "07", "08", "09","10",
	                "11", "12", "13", "14", "15", "16", 
	        };
	        lottery(users2, nums2);
	    }

	    public static void lottery(String[] users, int nums){
	        List<String> userList = Arrays.asList(users);
	        //乱序
	        Collections.shuffle(userList);
	        Random random = new Random();
	        int i = 0;
	        Set<Integer> set = new HashSet<Integer>(nums);
	        do {
	            int nextInt = random.nextInt(userList.size());  //Random.nextInt（n）的使用Random.next（）不多于两次, 返回值范围为0到n - 1的分布
	            if(!set.contains(nextInt)){	//判断是否已经存在该值
	                set.add(nextInt);
	                i++;
	            }
	        } while (i < nums);
	        System.out.println("============双色球============");
	        for (Integer num : set) {
	            System.out.println(userList.get(num));
	        }
	    }
}
