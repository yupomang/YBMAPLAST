package com.yondervision.mi.test;

public class Alphal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean  flag =false;
		int i=0;
		do{
			System.out.print(i++);
			flag=i<10;
			continue;
		}while(flag);
	}

}
