package com.yondervision.mi.util.security;

import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

public class Token {
	public static String newAccessToken(){
//		TimeBasedGenerator timeBasedGenerator=Generators.timeBasedGenerator(EthernetAddress.fromInterface());
//		return timeBasedGenerator.generate().toString();
		String value = UUID.randomUUID().toString().replaceAll("-", "");
		String prefix = RandomStringUtils.randomAlphanumeric(20);
		String suffix = RandomStringUtils.randomAlphanumeric(90 - 20 - value.length());
		return prefix + value + suffix;
	}
	
	public static void main(String[] args){
		System.out.println(newAccessToken());
		System.out.println(newAccessToken());
	}

}
