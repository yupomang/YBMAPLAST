/**
 * 
 */
package com.yondervision.mi.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.form.AppApi00901Form;
import com.yondervision.mi.form.AppApi00902Form;
import com.yondervision.mi.form.AppApi00903Form;
import com.yondervision.mi.form.AppApi00905Form;
import com.yondervision.mi.form.AppApi00907Form;
import com.yondervision.mi.form.AppApi00910Form;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.AppApi00901Result;
import com.yondervision.mi.result.AppApi00902Result;
import com.yondervision.mi.result.AppApi00903Result;
import com.yondervision.mi.result.AppApi00905Result;
import com.yondervision.mi.result.TitleInfoBean;
import com.yondervision.mi.service.AppApi009Service;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JavaBeanUtil;

/** 
* @ClassName: AppApi009Contorller 
* @Description: 贷款试算
* @author Caozhongyan
* @date Oct 11, 2013 5:16:29 PM   
* 
*/ 
@Controller
public class AppApi009Contorller {
	@Autowired
	private MsgSendApi001Service msgSendApi001Service = null;
	public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
		this.msgSendApi001Service = msgSendApi001Service;
	}
	@Autowired
	private AppApi009Service appApi009ServiceImpl = null;
	
	public AppApi009Service getAppApi009ServiceImpl() {
		return appApi009ServiceImpl;
	}
	public void setAppApi009ServiceImpl(AppApi009Service appApi009ServiceImpl) {
		this.appApi009ServiceImpl = appApi009ServiceImpl;
	}

	/**
	 * 月还款额、利息总额计算查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00901.{ext}")
	public String appapi00901(AppApi00901Form form,  ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("月还款额、利息总额计算查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getSurplusLoanInterestRate())){
			log.error(ERROR.PARAMS_NULL.getLogText("surplusLoanInterestRate"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"公积金贷款利率");
		}
		if(CommonUtil.isEmpty(form.getSurplusLoanAmount())){
			log.error(ERROR.PARAMS_NULL.getLogText("surplusLoanAmount"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"公积金贷款金额");
		}
		if(CommonUtil.isEmpty(form.getLoanDuration())){
			log.error(ERROR.PARAMS_NULL.getLogText("loanDuration"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"贷款期限");
		}

		if(CommonUtil.isEmpty(form.getCommercialLoanAmount())){
			log.error(ERROR.PARAMS_NULL.getLogText("commercialLoanAmount"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"商业贷款金额");
		}
		if(CommonUtil.isEmpty(form.getCommercialLoanInterestRate())){
			log.error(ERROR.PARAMS_NULL.getLogText("commercialLoanInterestRate"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"商业贷款利率");
		}
		if(CommonUtil.isEmpty(form.getRepaymentType())){
			log.error(ERROR.PARAMS_NULL.getLogText("repaymentType"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"还款方式");
		}
		String srate = form.getSurplusLoanInterestRate();
		String crate = form.getCommercialLoanInterestRate();
		form.setSurplusLoanInterestRate(String.valueOf(Float.parseFloat(srate)/100));
		form.setCommercialLoanInterestRate(String.valueOf(Float.parseFloat(crate)/100));
		AppApi00901Result result = appApi009ServiceImpl.appapi00901(form);
		
		if(Double.valueOf(form.getCommercialLoanAmount())>0.00){
			form.setSurplusLoanAmount(form.getCommercialLoanAmount());
			form.setSurplusLoanInterestRate(form.getCommercialLoanInterestRate());			
			AppApi00901Result sdresult = appApi009ServiceImpl.appapi00901(form);
			result.setSdmonthRepaymentAmount(sdresult.getMonthRepaymentAmount());
			result.setSdrepaymentRateSum(sdresult.getRepaymentRateSum());
			result.setSdrepaymentSum(sdresult.getRepaymentSum());
			String gdBalance = result.getBalance();
			String sdBalance = sdresult.getBalance();
			result.setBalance(String.format("%,.2f",(Double.valueOf(gdBalance.replace(",", "")) + Double.valueOf(sdBalance.replace(",", "")))));
		}
		
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", result);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));	
		return "";
	}	
	
	
	/**
	 * 贷款试算与贷商贷比较查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00902.{ext}")
	public String appapi00902(AppApi00902Form form,  ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("贷款试算与贷商贷比较查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}		
		if(CommonUtil.isEmpty(form.getMonthRepaymentAmount())){
			log.error(ERROR.PARAMS_NULL.getLogText("monthRepaymentAmount"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"月还款额");
		}
		if(CommonUtil.isEmpty(form.getRepaymentType())){
			log.error(ERROR.PARAMS_NULL.getLogText("repaymentType"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"还款方式");
		}
		if(CommonUtil.isEmpty(form.getCommercialLoanAmount())){
			log.error(ERROR.PARAMS_NULL.getLogText("commercialLoanAmount"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"商业贷款金额");
		}
		if(CommonUtil.isEmpty(form.getCommercialLoanInterestRate())){
			log.error(ERROR.PARAMS_NULL.getLogText("commercialLoanInterestRate"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"商业贷款利率");
		}
		if(CommonUtil.isEmpty(form.getLoanDuration())){
			log.error(ERROR.PARAMS_NULL.getLogText("loanDuration"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"贷款期限");
		}
		if(CommonUtil.isEmpty(form.getRepaymentSum())){
			log.error(ERROR.PARAMS_NULL.getLogText("repaymentSum"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"还款总额");
		}		
		
		String crate = form.getCommercialLoanInterestRate();		
		form.setCommercialLoanInterestRate(String.valueOf(Float.parseFloat(crate)/100));
		AppApi00902Result result = appApi009ServiceImpl.appapi00902(form);		
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", result);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}	
	
	/**
	 * 还款计划
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00903.{ext}")
	public String appapi00903(AppApi00903Form form,  ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("还款计划");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getSurplusLoanInterestRate())){
			log.error(ERROR.PARAMS_NULL.getLogText("surplusLoanInterestRate"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"公积金贷款利率");
		}
		if(CommonUtil.isEmpty(form.getSurplusLoanAmount())){
			log.error(ERROR.PARAMS_NULL.getLogText("surplusLoanAmount"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"公积金贷款金额");
		}
		if(CommonUtil.isEmpty(form.getLoanDuration())){
			log.error(ERROR.PARAMS_NULL.getLogText("loanDuration"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"贷款期限");
		}
		if(CommonUtil.isEmpty(form.getRepaymentType())){
			log.error(ERROR.PARAMS_NULL.getLogText("repaymentType"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"还款方式");
		}
		String srate = form.getSurplusLoanInterestRate();		
		form.setSurplusLoanInterestRate(String.valueOf(Float.parseFloat(srate)/100));		
		List<AppApi00903Result> list = appApi009ServiceImpl.appapi00903(form);
		List<List<TitleInfoBean>> result = new ArrayList();
		List<TitleInfoBean> appapi00903Result = null;
		for(int i=0;i<list.size();i++){
			appapi00903Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00903.result", list.get(i));			
			result.add(appapi00903Result);
		}
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", result);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "/index";
	}
	
	/**
	 * 还款计划发送
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00904.{ext}")
	public String appapi00904(AppApi00903Form form,  ModelMap modelMap){
		Logger log = LoggerUtil.getLogger();
		try {
			
			form.setSurplusLoanAmount("250000.00");//公积金贷款金额
			form.setSurplusLoanInterestRate("0.045");//公积金贷款利率			
			form.setLoanDuration("20");//贷款期限
			form.setRepaymentType("10");//公积金贷款利率
			
			List<AppApi00903Result> list = appApi009ServiceImpl.appapi00903(form);
			StringBuffer result = new StringBuffer();
			result.append("期数\t应还本金\t应还利息\t月还款额\t贷款余额\n");
			for(int i=0;i<list.size();i++){
				AppApi00903Result res = new AppApi00903Result();
				res = list.get(i);
				result.append(res.getRepaymentNumber()+"\t"+res.getRepaymentPrincipal()+"\t");
				result.append(res.getRepaymentInterest()+"\t"+res.getMonthRepaymentAmount()+"\t");
				result.append(res.getLoanBalance()+"\n");
			}
//			List<List<TitleInfoBean>> result = new ArrayList();
//			List<TitleInfoBean> appapi00903Result = null;
//			for(int i=0;i<list.size();i++){
//				appapi00903Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00903.result", list.get(i));			
//				result.add(appapi00903Result);
//			}			
			System.out.println(result.toString());
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("result", result.toString());
			
		} catch (Exception e) {
			//TODO 写日志，取得异常类中的异常信息和异常码进行put(recode和msg)
			e.printStackTrace();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "系统异常");
		}		
		return "/index";
	}
	
	/**
	 * 还款试算取利率
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00905.{ext}")
	public String appapi00905(AppApi00905Form form,  ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("还款试算取利率");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getLoanDuration())){
			log.error(ERROR.PARAMS_NULL.getLogText("loanDuration"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"期限");
		}
		AppApi00905Result result = appApi009ServiceImpl.appapi00905(form);		
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", result);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	
	/**
	 * 取核心贷款码表参
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00906.{ext}")
	public String appapi00906(AppApi00907Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("取核心贷款试算参数:"+form.getCenterId());	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		request.setAttribute("centerId", form.getCenterId());
		msgSendApi001Service.send(request, response);	
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 取核心贷款试算
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00907.{ext}")
	public String appapi00907(AppApi00907Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("取核心贷款试算参数:"+form.getCenterId());	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		request.setAttribute("centerId", form.getCenterId());
		msgSendApi001Service.send(request, response);	
//		modelMap.clear();
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	/**
	 * 取核心贷款额度计算
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00910.{ext}")
	public String appapi00910(AppApi00910Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("取核心贷款试算参数:"+form.getCenterId());	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		request.setAttribute("centerId", form.getCenterId());
		msgSendApi001Service.send(request, response);	
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 房屋类型查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00911.{ext}")
	public String appapi00911(AppApi00907Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("房屋类型查询:"+form.getCenterId());	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		request.setAttribute("centerId", form.getCenterId());
		msgSendApi001Service.send(request, response);	
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
}
