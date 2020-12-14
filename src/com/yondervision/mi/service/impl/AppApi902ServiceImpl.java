package com.yondervision.mi.service.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.Mi103DAO;
import com.yondervision.mi.dao.Mi902DAO;
import com.yondervision.mi.dto.Mi103;
import com.yondervision.mi.dto.Mi103Example;
import com.yondervision.mi.dto.Mi902;
import com.yondervision.mi.dto.Mi902Example;
import com.yondervision.mi.service.AppApi902Service;

public class AppApi902ServiceImpl implements AppApi902Service {

	private Mi902DAO mi902Dao = null;
	public Mi902DAO getMi902Dao() {
		return mi902Dao;
	}

	public void setMi902Dao(Mi902DAO mi902Dao) {
		this.mi902Dao = mi902Dao;
	}

	/**
	 * 随机生成6位随机验证码
	 * @return 验证码
	 */
    private String createRandomVcode(){
        //验证码
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int)(Math.random() * 9);
        }
        return vcode;
    }
 
    public String createSms( String centerid, String phone ,String channel){
    	
    	String vcode = "";	//验证码
    	int timeout = 1;	//验证码有效时间(分)	此处应取配置信息
//    	String centername = "";	//城市中心名称
    	//TODO 
    	/* 1.
    	 * 根据centerid 取得城市中心名称 
    	 *
    	 * 2.
    	 * select count(1) from from MI902 where phoneNum= phome ;
    	 * 如果count(1) > 0
    	 * select validCode,FailureTime from MI902 where phoneNum= phome order by FailureTime desc ;
    	 * 取第一条, 判断FailureTime是否 > 当前系统时间 （ System.currentTimeMillis() ）
    	 * 如果 > 当前系统时间 证明次条验证码没有超时, 取出validCode直接发送短信
    	 * 不让走重新生产验证码
    	 */
    	Mi902Example m902e=new Mi902Example();
    	m902e.setOrderByClause("failuretime desc");
		com.yondervision.mi.dto.Mi902Example.Criteria ca= m902e.createCriteria();
		ca.andPhonenumEqualTo(phone).andChannelEqualTo(channel);
		
		List<Mi902> list  = mi902Dao.selectByExample(m902e);
		if(list.size()==0){
    	//生成新验证码
			vcode = createRandomVcode();
			Mi902 mi902 = new Mi902();
			mi902.setFailuretime(System.currentTimeMillis()+(timeout*75*1000)+"");
			mi902.setPhonenum(phone);
			mi902.setValidcode(vcode);
			mi902.setChannel(channel);
			mi902Dao.insert(mi902);
		}else{
			Mi902 mi902 = list.get(0);
			String failuretime = mi902.getFailuretime();
			if(new Long(failuretime)>System.currentTimeMillis()){
//				vcode = mi902.getValidcode();
				vcode = createRandomVcode();
			}else{
				vcode = createRandomVcode();
			}
			mi902.setValidcode(vcode);
			mi902.setFailuretime(System.currentTimeMillis()+timeout*75*1000+"");
			mi902Dao.updateByExample(mi902, m902e);
		}
    	return vcode;
    }
    
    /**
     * 发送短信到短信API
     * @param phone 手机号码
     * @param vcode 验证码
     * @param timeout 验证码有效时间
     * @param centername 城市中心名称
     * @return
     */
    public String sendSms( String phone ,String vcode, int timeout, String centername){
    	
    	//TODO 
    	/*
    	 * http://172.16.8.5:8080/mfp/020000.action?flag=0&smssource=kmbat2&ispflag=1&phone=13888072520&elements=日期[2015-12-03]的批量主程序开始运行&sendlx=0&sendtime=1899-12-31 00:00:01
    	 * 
    	 * 需要在昆明短信平台新建短信验证模板
    	 * 模板:请填写验证码vcode完成验证(timeout分钟有效)。[CENTERNAME]
    	 */
    	String content = "";
    	
    	return "";
    }
    /**
     * 验证短信码
     * @param phone 手机号码
     * @param vcode 验证码
     * @param timeout 验证码有效时间
     * @param centername 城市中心名称
     * @return
     */
    public List<Mi902> validCode( String phone ,String vcode ,String channel){
    	
    	//TODO 
    	/*
    	 * http://172.16.8.5:8080/mfp/020000.action?flag=0&smssource=kmbat2&ispflag=1&phone=13888072520&elements=日期[2015-12-03]的批量主程序开始运行&sendlx=0&sendtime=1899-12-31 00:00:01
    	 * 
    	 * 需要在昆明短信平台新建短信验证模板
    	 * 模板:请填写验证码vcode完成验证(timeout分钟有效)。[CENTERNAME]
    	 */
    	Mi902Example m902e=new Mi902Example();
    	m902e.setOrderByClause("failuretime desc");
		com.yondervision.mi.dto.Mi902Example.Criteria ca= m902e.createCriteria();
		ca.andPhonenumEqualTo(phone);
		ca.andValidcodeEqualTo(vcode);
		ca.andChannelEqualTo(channel);
		System.out.println("【=====短信验证查询=====】，phone："+phone+",vcode:"+vcode+",channel:"+channel);
		List<Mi902> list  = mi902Dao.selectByExample(m902e);
		
    	return list;
    }
    public int deleteSms( String phone ,String vcode){
    	try{
	    	Mi902Example m902e=new Mi902Example();
			com.yondervision.mi.dto.Mi902Example.Criteria ca= m902e.createCriteria();
			ca.andPhonenumEqualTo(phone);
			ca.andValidcodeEqualTo(vcode);
			int code  = mi902Dao.deleteByExample(m902e);
			
	    	return code;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return 0;
    }
}
