package com.yondervision.mi.util;

import com.yondervision.mi.common.ERRCODE.APP_ALERT;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.form.AppApiCommonForm;

public class ParamCheck {
	public void check(AppApiCommonForm form) throws NoRollRuntimeErrorException{
		System.out.println("公共参数检查,用户名:"+form.getUserId());
		System.out.println("公共参数检查,城市中心:"+form.getCenterId());
		System.out.println("公共参数检查,用户类型:"+form.getUsertype());
		System.out.println("公共参数检查,设备区分:"+form.getDeviceType());
		System.out.println("公共参数检查,设备识别码:"+form.getDeviceToken());
		System.out.println("公共参数检查,当前版本:"+form.getCurrenVersion());
		System.out.println("公共参数检查,服务类型:"+form.getBuzType());		
		System.out.println("公共参数检查,渠道类型:"+form.getChannel());
		System.out.println("公共参数检查,应用标识:"+form.getAppid());
		System.out.println("公共参数检查,应用KEY:"+form.getAppkey());
		System.out.println("公共参数检查,客户IP:"+form.getClientIp());
		
		if(CommonUtil.isEmpty(form.getUserId())){			
			throw new NoRollRuntimeErrorException(APP_ALERT.APP_NO_LOGIN
					.getValue(),"用户ID");
		}
		if(CommonUtil.isEmpty(form.getCenterId())){			
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getUsertype())){			
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"用户类型");
		}
		if(CommonUtil.isEmpty(form.getDeviceType())){			
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"设备区分");
		}
//		if(CommonUtil.isEmpty(form.getDeviceToken())){			
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"设备识别码");
//		}
		if(CommonUtil.isEmpty(form.getCurrenVersion())){			
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"当前版本");
		}
		if(CommonUtil.isEmpty(form.getBuzType())){			
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"服务类型");
		}
		if(CommonUtil.isEmpty(form.getChannel())){			
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"渠道类型");
		}
		if(CommonUtil.isEmpty(form.getAppid())){			
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"应用标识");
		}
		if(CommonUtil.isEmpty(form.getAppkey())){			
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"应用KEY");
		}
		if(CommonUtil.isEmpty(form.getClientIp())){			
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"客户IP");
		}
	}
}
