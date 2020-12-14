package com.yondervision.mi.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.dao.Mi109DAO;
import com.yondervision.mi.dto.Mi109;
import com.yondervision.mi.dto.Mi109Example;
import com.yondervision.mi.form.AppApi00901Form;
import com.yondervision.mi.form.AppApi00902Form;
import com.yondervision.mi.form.AppApi00903Form;
import com.yondervision.mi.form.AppApi00905Form;
import com.yondervision.mi.result.AppApi00901Result;
import com.yondervision.mi.result.AppApi00902Result;
import com.yondervision.mi.result.AppApi00903Result;
import com.yondervision.mi.result.AppApi00905Result;
import com.yondervision.mi.service.AppApi009Service;

/** 
* @ClassName: AppApi009ServiceImpl 
* @Description: 贷款试算处理类
* @author Caozhongyan
* @date Oct 12, 2013 8:40:09 AM   
* 
*/ 
public class AppApi009ServiceImpl implements AppApi009Service {

	public Mi109DAO mi109Dao = null;
	
	public Mi109DAO getMi109Dao() {
		return mi109Dao;
	}

	public void setMi109Dao(Mi109DAO mi109Dao) {
		this.mi109Dao = mi109Dao;
	}
	
	public AppApi00901Result appapi00901(AppApi00901Form form) throws TransRuntimeErrorException{
		// TODO Auto-generated method stub
		AppApi00901Result result = new AppApi00901Result();
		try {
			// 业务处理
			double monthRepaymentAmount = 0.00; // 月还款额
			double interestSum = 0.00; // 应还利息总额
			double repaymentSum = 0.00; // 还款总额
			
			double otherRepaymentSum = 0.00; // 另一种还款方式还款总额
			double balance = 0.00; // 等额本金与等额本息差额

			double dLoanAmount = Double.parseDouble(form.getSurplusLoanAmount().replaceAll(",", "")); // 公积金贷款金额
			double dLoanInterestRate = Double.parseDouble(form.getSurplusLoanInterestRate().replaceAll(",", "")) / 12; // 公积金贷款月利率
			int iLoanDuration = Integer.parseInt(form.getLoanDuration()) * 12; // 还款期数

			/*
			 * 等额本金还款
			 */
			if (Constants.REPAY_TYPE_A.equals(form.getRepaymentType())) {
				monthRepaymentAmount = this.getMonthRepaymentAmountA(
						dLoanAmount, dLoanInterestRate, iLoanDuration, 1, true);
				interestSum = this.getSumRepaymentInterestA(dLoanAmount,
						dLoanInterestRate, iLoanDuration, true);
				repaymentSum = this.getSumRepaymentAmountA(dLoanAmount,
						dLoanInterestRate, iLoanDuration, true);
				
				//等额本息，还贷总额
				otherRepaymentSum = this.getSumRepaymentAmountB(dLoanAmount,
						dLoanInterestRate, iLoanDuration, true);
				balance = repaymentSum - otherRepaymentSum;
			}
			/*
			 * 等额本息还款
			 */
			else if (Constants.REPAY_TYPE_B.equals(form.getRepaymentType())) {
				monthRepaymentAmount = this.getMonthRepaymentAmountB(
						dLoanAmount, dLoanInterestRate, iLoanDuration, 1, true);
				interestSum = this.getSumRepaymentInterestB(dLoanAmount,
						dLoanInterestRate, iLoanDuration, true);
				repaymentSum = this.getSumRepaymentAmountB(dLoanAmount,
						dLoanInterestRate, iLoanDuration, true);
				
				//等额本金，还贷总额
				otherRepaymentSum = this.getSumRepaymentAmountA(dLoanAmount,
						dLoanInterestRate, iLoanDuration, true);
				balance = otherRepaymentSum - repaymentSum;
			} else {
				//异常处理
				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"计算月还款额、利息总额异常。");
			}			
			result.setRepaymentSum(String.format("%,.2f",interestSum));
			result.setMonthRepaymentAmount(String.format("%,.2f",monthRepaymentAmount));
			result.setRepaymentRateSum(String.format("%,.2f",repaymentSum));
			result.setBalance(String.format("%,.2f",balance));
		} catch (TransRuntimeErrorException e) {
			// 系统异常			
			throw e;
		}
		return result;
	}
	
	public AppApi00902Result appapi00902(AppApi00902Form form) throws TransRuntimeErrorException{
		// TODO Auto-generated method stub
		AppApi00902Result result = new AppApi00902Result();
		try {
			// TODO 业务处理
			double commercialMonthRepaymentAmount = 0.00; // 商贷月还款额
			double commercialRepaymentSum = 0.00; // 商贷还款总额

			double dLoanAmount = Double.parseDouble(form.getCommercialLoanAmount().replaceAll(",", "")); // 商贷金额
			double dLoanInterestRate = Double.parseDouble(form.getCommercialLoanInterestRate().replaceAll(",", "")) / 12; // 商贷月利率
			int iLoanDuration = Integer.parseInt(form.getLoanDuration()) * 12; // 还款期数

			/*
			 * 等额本金还款
			 */
			if (Constants.REPAY_TYPE_A.equals(form.getRepaymentType())) {
				commercialMonthRepaymentAmount = this.getMonthRepaymentAmountA(
						dLoanAmount, dLoanInterestRate, iLoanDuration, 1, true);
				commercialRepaymentSum = this.getSumRepaymentAmountA(
						dLoanAmount, dLoanInterestRate, iLoanDuration, true);
			}
			/*
			 * 等额本息还款
			 */
			else if (Constants.REPAY_TYPE_B.equals(form.getRepaymentType())) {
				commercialMonthRepaymentAmount = this.getMonthRepaymentAmountB(
						dLoanAmount, dLoanInterestRate, iLoanDuration, 1, true);
				commercialRepaymentSum = this.getSumRepaymentAmountB(
						dLoanAmount, dLoanInterestRate, iLoanDuration, true);
			} else {
				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"贷款试算与贷商贷比较异常。");
			}

			result.setBalanceMonthRepaymentAmount(String.format(
					"%,.2f", commercialMonthRepaymentAmount
							- Double.parseDouble(form.getMonthRepaymentAmount().replaceAll(",", ""))));
			result.setBalanceRepaymentSum(String.format("%,.2f",
					commercialRepaymentSum - Double.parseDouble(form.getRepaymentSum().replaceAll(",", ""))));
			result.setCommercialMonthRepaymentAmount(String
					.format("%,.2f", commercialMonthRepaymentAmount));
			result.setCommercialRepaymentSum(String.format(
					"%,.2f", commercialRepaymentSum));
			result.setSurplusMonthRepaymentAmount(String.format(
					"%,.2f", Double.parseDouble(form.getMonthRepaymentAmount().replaceAll(",", ""))));
			result.setSurplusRepaymentSum(String.format("%,.2f",
					Double.parseDouble(form.getRepaymentSum().replaceAll(",", ""))));
		} catch (TransRuntimeErrorException e) {
			// 系统异常			
			throw e;
		}
		return result;
	}

	public List<AppApi00903Result> appapi00903(AppApi00903Form form) throws TransRuntimeErrorException{
		// TODO Auto-generated method stub
		List<AppApi00903Result> result = new ArrayList<AppApi00903Result>();
		try {
			// 业务处理
			double dLoanAmount = Double.valueOf(form.getSurplusLoanAmount().replaceAll(",", "")); // 公积金贷款金额
			double dLoanInterestRate = Double.parseDouble(form.getSurplusLoanInterestRate().replaceAll(",", "")) / 12; // 公积金贷款月利率
			int iLoanDuration = Integer.parseInt(form.getLoanDuration()) * 12; // 还款期数

			double repaymentPrincipal = 0.0; // 应还本金
			double repaymentInterest = 0.0; // 应还利息
			double monthRepaymentAmount = 0.0; // 月还款额
			double loanBalance = dLoanAmount; // 贷款余额

			/*
			 * 等额本息
			 */
			if (Constants.REPAY_TYPE_A.equals(form.getRepaymentType())) {
				for (int i = 0; i < iLoanDuration; i++) {
					AppApi00903Result item = new AppApi00903Result();
					/*
					 * 末期
					 */
					if (i == iLoanDuration - 1) {
						// TODO 等额本息末期还款计划
						repaymentPrincipal = Double.valueOf(String.format("%.2f",loanBalance));
						repaymentInterest = Double.valueOf(String.format("%.2f",loanBalance * dLoanInterestRate));
						loanBalance -= Double.valueOf(String.format("%.2f",repaymentPrincipal));
						monthRepaymentAmount = Double.valueOf(String.format("%.2f",repaymentPrincipal
								+ repaymentInterest));

						item.setLoanBalance(String.format(
								"%,.2f", Math.abs(loanBalance)));
						item.setMonthRepaymentAmount(String
								.format("%,.2f", monthRepaymentAmount));
						item.setRepaymentInterest(String.format(
								"%,.2f", repaymentInterest));
						item.setRepaymentNumber(String.valueOf(i + 1));
						item.setRepaymentPrincipal(String
								.format("%,.2f", repaymentPrincipal));
						result.add(item);
						break;
					}

					/*
					 * 非末期
					 */
					monthRepaymentAmount = Double.valueOf(String.format("%.2f",(dLoanAmount * dLoanInterestRate * Math
							.pow((1 + dLoanInterestRate), iLoanDuration))
							/ (Math.pow((1 + dLoanInterestRate), iLoanDuration) - 1)));
					repaymentInterest = Double.valueOf(String.format("%.2f",loanBalance * dLoanInterestRate));
					repaymentPrincipal = Double.valueOf(String.format("%.2f",monthRepaymentAmount
							- repaymentInterest));
					loanBalance -= Double.valueOf(String.format("%.2f",repaymentPrincipal));

					
					item.setLoanBalance(String.valueOf(String.format("%,.2f",loanBalance)));
					item.setMonthRepaymentAmount(String.valueOf(String.format("%,.2f",monthRepaymentAmount)));
					item.setRepaymentInterest(String.valueOf(String.format("%,.2f",repaymentInterest)));
					item.setRepaymentNumber(String.valueOf(i + 1));
					item.setRepaymentPrincipal(String.valueOf(String.format("%,.2f",repaymentPrincipal)));					
					result.add(item);
				}// end for
			}// end if
			/*
			 * 等额本金
			 */
			else if (Constants.REPAY_TYPE_B.equals(form.getRepaymentType())) {
				for (int i = 0; i < iLoanDuration; i++) {
					AppApi00903Result item = new AppApi00903Result();
					/*
					 * 末期
					 */
					if (i == iLoanDuration - 1) {
						// TODO 等额本息末期还款计划
						repaymentPrincipal = Double.valueOf(String.format("%.2f",loanBalance));
						repaymentInterest = Double.valueOf(String.format("%.2f",loanBalance * dLoanInterestRate));
						loanBalance -= Double.valueOf(String.format("%.2f",repaymentPrincipal));
						monthRepaymentAmount = Double.valueOf(String.format("%.2f",repaymentPrincipal
								+ repaymentInterest));

						item.setLoanBalance(String.valueOf(String.format("%,.2f",Math.abs(loanBalance))));
						item.setMonthRepaymentAmount(String.valueOf(String.format("%,.2f",monthRepaymentAmount)));
						item.setRepaymentInterest(String.valueOf(String.format("%,.2f",repaymentInterest)));
						item.setRepaymentNumber(String.valueOf(i + 1));
						item.setRepaymentPrincipal(String.valueOf(String.format("%,.2f",repaymentPrincipal)));
						result.add(item);
						break;
					}

					/*
					 * 非末期
					 */
					repaymentPrincipal = Double.valueOf(String.format("%.2f",dLoanAmount / iLoanDuration));
					repaymentInterest = Double.valueOf(String.format("%.2f",loanBalance * dLoanInterestRate));
					monthRepaymentAmount = Double.valueOf(String.format("%.2f",repaymentPrincipal
							+ repaymentInterest));
					loanBalance -= Double.valueOf(String.format("%.2f",repaymentPrincipal));
					
					item.setLoanBalance(String.valueOf(String.format("%,.2f",loanBalance)));
					item.setMonthRepaymentAmount(String.valueOf(String.format("%,.2f",monthRepaymentAmount)));
					item.setRepaymentInterest(String.valueOf(String.format("%,.2f",repaymentInterest)));
					item.setRepaymentNumber(String.valueOf(i + 1));
					item.setRepaymentPrincipal(String.valueOf(String.format("%,.2f",repaymentPrincipal)));
					
					result.add(item);
				}// end for
			} else {
				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"还款计划计算异常。");
			}
		} catch (TransRuntimeErrorException e) {
			// 系统异常
			throw e;
		}
		return result;
	}

	public void appapi00904() throws Exception{
		// TODO Auto-generated method stub

	}

	public AppApi00905Result appapi00905(AppApi00905Form form) throws TransRuntimeErrorException{
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);
		Date date = new Date();
		String cdate = formatter.format(date);
		Mi109Example m109e=new Mi109Example();
		com.yondervision.mi.dto.Mi109Example.Criteria ca10= m109e.createCriteria();	
		m109e.setOrderByClause("terms asc,effective_date desc,rate asc");
		ca10.andTermsGreaterThanOrEqualTo(Integer.valueOf(form.getLoanDuration())*12);
		ca10.andEffectiveDateLessThanOrEqualTo(cdate);
		ca10.andCenteridEqualTo(form.getCenterId());
		ca10.andRatetypeEqualTo(Constants.RETA_TYPE_10);
		ca10.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi109> list1 = new ArrayList<Mi109>(); 
		list1 = mi109Dao.selectByExample(m109e);
		if(list1.isEmpty()||list1.size()==0){
			//异常处理
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"公积金利率信息");
		}
		
		Mi109Example m109e1=new Mi109Example();
		com.yondervision.mi.dto.Mi109Example.Criteria ca20= m109e1.createCriteria();
		m109e1.setOrderByClause("terms asc,effective_date desc,rate asc");
		ca20.andTermsGreaterThanOrEqualTo(Integer.valueOf(form.getLoanDuration())*12);
		ca20.andEffectiveDateLessThanOrEqualTo(cdate);
		ca20.andCenteridEqualTo(form.getCenterId());
		ca20.andRatetypeEqualTo(Constants.RETA_TYPE_20);
		ca20.andValidflagEqualTo(Constants.IS_VALIDFLAG);	
		List<Mi109> list2 = new ArrayList<Mi109>(); 
		list2 = mi109Dao.selectByExample(m109e1);
		
		if(list2.isEmpty()||list2.size()==0){
			//异常处理
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"商贷利率信息");
		}		
		AppApi00905Result result = new AppApi00905Result();		
		result.setCommercialLoanInterestRate(list2.get(0).getRate());
		result.setSurplusLoanInterestRate(list1.get(0).getRate());
		return result;
	}

	
	
	
	
	/**
	 * 等额本息还款方式，还款总额计算<BR>
	 * 
	 * @param dLoanAmount
	 *            贷款本金
	 * @param dLoanInterestRate
	 *            贷款月利率
	 * @param iLoanDuration
	 *            贷款期限（月）
	 * @param iRepaymentNumber
	 *            要计算的期数
	 * @param flag
	 *            是否保留两位小数
	 * @return double 还款总额
	 */
	private double getSumRepaymentAmountB(double dLoanAmount,
			double dLoanInterestRate, int iLoanDuration, boolean flag) {
		double dSumRepaymentPrincipal = 0.00;

		if (dLoanInterestRate <= 0.00 || iLoanDuration < 1) {
//			throw new ExceptionYdErrorData();
		}

		dSumRepaymentPrincipal = dLoanAmount
				+ getSumRepaymentInterestB(dLoanAmount, dLoanInterestRate,
						iLoanDuration, flag);

		if (flag) {
			return Double
					.valueOf(String.format("%.2f", dSumRepaymentPrincipal));
		}
		return dSumRepaymentPrincipal;
	}
	
	/**
	 * 等额本息还款方式，还款利息总额计算<BR>
	 * 
	 * @param dLoanAmount
	 *            贷款本金
	 * @param dLoanInterestRate
	 *            贷款月利率
	 * @param iLoanDuration
	 *            贷款期限（月）
	 * @param iRepaymentNumber
	 *            要计算的期数
	 * @param flag
	 *            是否保留两位小数
	 * @return double 还款利息总额
	 */
	private double getSumRepaymentInterestB(double dLoanAmount,
			double dLoanInterestRate, int iLoanDuration, boolean flag) {
		
		if (dLoanInterestRate <= 0.00 || iLoanDuration < 1) {
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"贷款月利率："+dLoanInterestRate+" 贷款期限(月)："+iLoanDuration);
		}

		double dTempAmt = 0.00; // 累计还款本金
		for (int i = 0; i < iLoanDuration; i++) {
			dTempAmt += getMonthRepaymentInterestB(dLoanAmount,
					dLoanInterestRate, iLoanDuration, i + 1, false);
		}

		if (flag) {
			return Double.valueOf(String.format("%.2f", dTempAmt));
		}
		return dTempAmt;
	}
	
	/**
	 * 等额本金还款方式，月还款额计算<BR>
	 * 
	 * @param dLoanAmount
	 *            贷款本金
	 * @param dLoanInterestRate
	 *            贷款月利率
	 * @param iLoanDuration
	 *            贷款期限（月）
	 * @param iRepaymentNumber
	 *            要计算的期数
	 * @param flag
	 *            是否保留两位小数
	 * @return double 月还款额
	 */
	private double getMonthRepaymentAmountB(double dLoanAmount,
			double dLoanInterestRate, int iLoanDuration, int iRepaymentNumber,
			boolean flag) {
		double dRepaymentPrincipal = 0.00;

		if (dLoanInterestRate <= 0.00 || iLoanDuration < 1
				|| iRepaymentNumber < 1 || iRepaymentNumber > iLoanDuration) {
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"贷款月利率："+dLoanInterestRate+" 贷款期限(月)："+iLoanDuration+" 要计算的期数："+iRepaymentNumber);
		}

		dRepaymentPrincipal = getMonthRepaymentPrincipalB(dLoanAmount,
				dLoanInterestRate, iLoanDuration, iRepaymentNumber, false)
				+ getMonthRepaymentInterestB(dLoanAmount, dLoanInterestRate,
						iLoanDuration, iRepaymentNumber, false);

		if (flag) {
			return Double.valueOf(String.format("%.2f", dRepaymentPrincipal));
		}
		return dRepaymentPrincipal;
	}
	
	/**
	 * 等额本金还款方式，月还款利息计算<BR>
	 * 
	 * @param dLoanAmount
	 *            贷款本金
	 * @param dLoanInterestRate
	 *            贷款月利率
	 * @param iLoanDuration
	 *            贷款期限（月）
	 * @param iRepaymentNumber
	 *            要计算的期数
	 * @param flag
	 *            是保留两个小数
	 * @return double 月还款利息
	 */
	private double getMonthRepaymentInterestB(double dLoanAmount,
			double dLoanInterestRate, int iLoanDuration, int iRepaymentNumber,
			boolean flag) {
		double dMonthRepaymentInterest = 0.00;

		if (dLoanInterestRate <= 0.00 || iLoanDuration < 1
				|| iRepaymentNumber < 1 || iRepaymentNumber > iLoanDuration) {
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"贷款月利率："+dLoanInterestRate+" 贷款期限(月)："+iLoanDuration+" 要计算的期数："+iRepaymentNumber);
		}

		double dTempAmt = (dLoanAmount / iLoanDuration)
				* (iRepaymentNumber - 1); // 累计还款本金
		dMonthRepaymentInterest = (dLoanAmount - dTempAmt) * dLoanInterestRate;

		if (flag) {
			return Double.valueOf(String
					.format("%.2f", dMonthRepaymentInterest));
		}
		return dMonthRepaymentInterest;
	}
	
	/**
	 * 等额本金还款方式，月还款本金计算<BR>
	 * 
	 * @param dLoanAmount
	 *            贷款本金
	 * @param dLoanInterestRate
	 *            贷款月利率
	 * @param iLoanDuration
	 *            贷款期限（月）
	 * @param iRepaymentNumber
	 *            要计算的期数
	 * @param flag
	 *            是保留两位小数
	 * @return double 月还款利息
	 */
	private double getMonthRepaymentPrincipalB(double dLoanAmount,
			double dLoanInterestRate, int iLoanDuration, int iRepaymentNumber,
			boolean flag) {
		double dMonthRepaymentPrincipal = 0.00;

		if (dLoanInterestRate <= 0.00 || iLoanDuration < 1
				|| iRepaymentNumber < 1 || iRepaymentNumber > iLoanDuration) {
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"贷款月利率："+dLoanInterestRate+" 贷款期限(月)："+iLoanDuration+" 要计算的期数："+iRepaymentNumber);
		}

		/*
		 * 非末期
		 */
		if (iRepaymentNumber != iLoanDuration) {
			dMonthRepaymentPrincipal = dLoanAmount / iLoanDuration;
		}
		/*
		 * 末期
		 */
		else {
			dMonthRepaymentPrincipal = dLoanAmount
					- (dLoanAmount / iLoanDuration) * (iLoanDuration - 1);
		}

		if (flag) {
			return Double.valueOf(String.format("%.2f",
					dMonthRepaymentPrincipal));
		}
		return dMonthRepaymentPrincipal;
	}
	
	/**
	 * 等额本息还款方式，还款总额计算<BR>
	 * 
	 * @param dLoanAmount
	 *            贷款本金
	 * @param dLoanInterestRate
	 *            贷款月利率
	 * @param iLoanDuration
	 *            贷款期限（月）
	 * @param iRepaymentNumber
	 *            要计算的期数
	 * @param flag
	 *            是否保留两位小数
	 * @return double 还款总额
	 */
	private double getSumRepaymentAmountA(double dLoanAmount,
			double dLoanInterestRate, int iLoanDuration, boolean flag) {
		double dSumRepaymentPrincipal = 0.00;

		if (dLoanInterestRate <= 0.00 || iLoanDuration < 1) {
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"贷款月利率："+dLoanInterestRate+" 贷款期限(月)："+iLoanDuration);
		}

		dSumRepaymentPrincipal = dLoanAmount
				+ getSumRepaymentInterestA(dLoanAmount, dLoanInterestRate,
						iLoanDuration, flag);

		if (flag) {
			return Double
					.valueOf(String.format("%.2f", dSumRepaymentPrincipal));
		}
		return dSumRepaymentPrincipal;
	}
	
	
	/**
	 * 等额本息还款方式，还款利息总额计算<BR>
	 * 
	 * @param dLoanAmount
	 *            贷款本金
	 * @param dLoanInterestRate
	 *            贷款月利率
	 * @param iLoanDuration
	 *            贷款期限（月）
	 * @param iRepaymentNumber
	 *            要计算的期数
	 * @param flag
	 *            是否保留两位小数
	 * @return double 还款利息总额
	 */
	private double getSumRepaymentInterestA(double dLoanAmount,
			double dLoanInterestRate, int iLoanDuration, boolean flag) {
		double dSumRepaymentInterest = 0.00;

		if (dLoanInterestRate <= 0.00 || iLoanDuration < 1) {
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"贷款月利率："+dLoanInterestRate+" 贷款期限(月)："+iLoanDuration);
		}

		double dTempAmt = 0.00; // 累计还款本金
		for (int i = 0; i < iLoanDuration; i++) {
			double dMonthRepaymentInterest = (dLoanAmount - dTempAmt)
					* dLoanInterestRate;
			dTempAmt += getMonthRepaymentAmountA(dLoanAmount,
					dLoanInterestRate, iLoanDuration, i + 1, false)
					- dMonthRepaymentInterest;
			dSumRepaymentInterest += dMonthRepaymentInterest;
		}

		if (flag) {
			return Double.valueOf(String.format("%.2f", dSumRepaymentInterest));
		}
		return dSumRepaymentInterest;
	}
	
	
	
	/**
	 * 等额本息还款方式，月还款额计算<BR>
	 * 
	 * @param dLoanAmount
	 *            贷款本金
	 * @param dLoanInterestRate
	 *            贷款月利率
	 * @param iLoanDuration
	 *            贷款期限（月）
	 * @param iRepaymentNumber
	 *            要计算的期数
	 * @param flag
	 *            是否保留两位小数
	 * @return double 月还款额
	 */
	private double getMonthRepaymentAmountA(double dLoanAmount,
			double dLoanInterestRate, int iLoanDuration, int iRepaymentNumber,
			boolean flag) {
		double dRepaymentPrincipal = 0.00;

		if (dLoanInterestRate <= 0.00 || iLoanDuration < 1
				|| iRepaymentNumber < 1) {
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"贷款月利率："+dLoanInterestRate+" 贷款期限(月)："+iLoanDuration);
		}

		/*
		 * 非末期
		 */
		if (iRepaymentNumber < iLoanDuration) {
			dRepaymentPrincipal = (dLoanAmount * dLoanInterestRate * Math.pow(
					(1 + dLoanInterestRate), iLoanDuration))
					/ (Math.pow((1 + dLoanInterestRate), iLoanDuration) - 1);
		}
		/*
		 * 末期
		 */
		else if (iRepaymentNumber == iLoanDuration) {
			double dTempAmt = 0.00;
			for (int i = 1; i < iLoanDuration; i++) {
				dTempAmt += getMonthRepaymentPrincipalA(dLoanAmount,
						dLoanInterestRate, iLoanDuration, i, false);
			}
			dRepaymentPrincipal = (dLoanAmount - dTempAmt)
					* (1 + dLoanInterestRate);
		} else {
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"要计算的期数异常");
		}
		if (flag) {
			return Double.valueOf(String.format("%.2f", dRepaymentPrincipal));
		}
		return dRepaymentPrincipal;
	}
	
	/**
	 * 等额本息还款方式，月还款本金计算<BR>
	 * 
	 * @param dLoanAmount
	 *            贷款本金
	 * @param dLoanInterestRate
	 *            贷款月利率
	 * @param iLoanDuration
	 *            贷款期限（月）
	 * @param iRepaymentNumber
	 *            要计算的期数
	 * @param flag
	 *            是保留两位小数
	 * @return double 月还款利息
	 */
	private double getMonthRepaymentPrincipalA(double dLoanAmount,
			double dLoanInterestRate, int iLoanDuration, int iRepaymentNumber,
			boolean flag) {
		double dMonthRepaymentPrincipal = 0.00;

		if (dLoanInterestRate <= 0.00 || iLoanDuration < 1
				|| iRepaymentNumber < 1 || iRepaymentNumber > iLoanDuration) {
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"贷款月利率："+dLoanInterestRate+" 贷款期限(月)："+iLoanDuration+" 要计算的期数："+iRepaymentNumber);
		}

		dMonthRepaymentPrincipal = getMonthRepaymentAmountA(dLoanAmount,
				dLoanInterestRate, iLoanDuration, iRepaymentNumber, false)
				- getMonthRepaymentInterestA(dLoanAmount, dLoanInterestRate,
						iLoanDuration, iRepaymentNumber, false);

		if (flag) {
			return Double.valueOf(String.format("%.2f",
					dMonthRepaymentPrincipal));
		}
		return dMonthRepaymentPrincipal;
	}
	
	/**
	 * 等额本息还款方式，月还款利息计算<BR>
	 * 
	 * @param dLoanAmount
	 *            贷款本金
	 * @param dLoanInterestRate
	 *            贷款月利率
	 * @param iLoanDuration
	 *            贷款期限（月）
	 * @param iRepaymentNumber
	 *            要计算的期数
	 * @param flag
	 *            是保留两个小数
	 * @return double 月还款利息
	 */
	private double getMonthRepaymentInterestA(double dLoanAmount,
			double dLoanInterestRate, int iLoanDuration, int iRepaymentNumber,
			boolean flag) {
		double dMonthRepaymentInterest = 0.00;

		if (dLoanInterestRate <= 0.00 || iLoanDuration < 1
				|| iRepaymentNumber < 1 || iRepaymentNumber > iLoanDuration) {
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"贷款月利率："+dLoanInterestRate+" 贷款期限(月)："+iLoanDuration+" 要计算的期数："+iRepaymentNumber);
		}

		double dTempAmt = 0.00; // 累计还款本金
		for (int i = 0; i < iRepaymentNumber; i++) {
			dMonthRepaymentInterest = (dLoanAmount - dTempAmt)
					* dLoanInterestRate;
			dTempAmt += getMonthRepaymentAmountA(dLoanAmount,
					dLoanInterestRate, iLoanDuration, iRepaymentNumber, false)
					- dMonthRepaymentInterest;
		}

		if (flag) {
			return Double.valueOf(String
					.format("%.2f", dMonthRepaymentInterest));
		}
		return dMonthRepaymentInterest;
	}
	
	
	

}
