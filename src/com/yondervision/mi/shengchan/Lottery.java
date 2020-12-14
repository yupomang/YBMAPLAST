package com.yondervision.mi.shengchan;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Lottery {
	  public static void main(String[] args) {
	        int nums = 2;	//中奖人数
	        String[] users = new String[]{
	                "test0", "test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8", "test9",
	                "test10", "test11", "test12", "test13", "test14", "test15", "test16", "test17", "test18", "test19",
	                "test20", "test21", "test22", "test23", "test24", "test25", "test26", "test27", "test28", "test29",
	                "test30", "test31", "test32", "test33", "test34", "test35", "test36", "test37", "test38", "test39",
	                "test40", "test41", "test42", "test43", "test44", "test45", "test46", "test47", "test48", "test49",
	        };
	        lottery(users, nums);
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
	        System.out.println("============中奖名单============");
	        for (Integer num : set) {
	            System.out.println(userList.get(num));
	        }
	    }
}
