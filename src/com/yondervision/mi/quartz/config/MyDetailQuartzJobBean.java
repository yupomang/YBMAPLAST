package com.yondervision.mi.quartz.config;

import java.lang.reflect.Method;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class MyDetailQuartzJobBean extends QuartzJobBean {

	private String targetObject;
	private String targetMethod;
	private ApplicationContext ctx;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		try {
			Object otargetObject = ctx.getBean(targetObject);
			Method m = null;

			try {
				System.out.println("开始这一步了");
				m = otargetObject.getClass().getMethod(targetMethod, new Class[] {JobExecutionContext.class});
				m.invoke(otargetObject, new Object[] {context});
			} catch (SecurityException e) {
				//logger.error(e);
			} catch (NoSuchMethodException e) {
				//logger.error(e);
			}
		} catch (Exception e) {
			throw new JobExecutionException(e);
		}

	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.ctx = applicationContext;
	}

	public void setTargetObject(String targetObject) {
		this.targetObject = targetObject;
	}

	public void setTargetMethod(String targetMethod) {
		this.targetMethod = targetMethod;
	}
}
