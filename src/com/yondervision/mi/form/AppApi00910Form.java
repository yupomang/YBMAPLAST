package com.yondervision.mi.form;

/** 
* @ClassName: AppApi00907Form 
* @Description: TODO
* @author Caozhongyan
* @date Oct 12, 2013 10:57:29 AM   
* 
*/ 
public class AppApi00910Form extends AppApiCommonForm{
//00073300中心上传数据  start
	/**
	 * 是否第一次公积金贷款(1：是；0：否)
	 */
	private String isfirstFundloans;
	/**
	 * 是否夫妻双方均在本地缴公积金(1：是；0：否)
	 */
	private String islocalPayFunc;
	/**
	 * 主借款人证件号码（aes加密）
	 */
	private String idCardNumber;
	/**
	 * 主借款人缴存基数
	 */
	private String payBaseNum;
	/**
	 * 主借款人月缴存额
	 */
	private String monthPayMoney;
	/**
	 * 配偶缴存基数
	 */
	private String matePayBaseNum;
	/**
	 * 配偶月缴存额
	 */
	private String mateMonthPayMoney;
	/**
	 * 房屋类型
	 */
	private String buildingType;
	/**
	 * 房屋总价
	 */
	private String totalPrice;
	/**
	 * 本套房提取总额
	 */
	private String extractTotal;
//00073300中心上传数据  end
	/**
	 * 贷款金额
	 */
	private String othplacflag;
	/**
	 * 贷款期限
	 */
	private String paycity;
	/**
	 * 还款方式
	 */
	private String birthday1;
	/**
	 * 贷款类型
	 */
	private String loansex1;
	/**
	 * 公积金贷款利率
	 */
	private String loanbal1;
	/**
	 * 商业贷款利率
	 */
	private String loanmonpay1;
	/**
	 * 贷款期限
	 */
	private String birthday2;
	/**
	 * 还款方式
	 */
	private String loansex2;
	/**
	 * 贷款类型
	 */
	private String loanbal2;
	/**
	 * 公积金贷款利率
	 */
	private String loanmonpay2;
	/**
	 * 商业贷款利率
	 */
	private String totalincome;
	/**
	 * 贷款期限
	 */
	private String otherpay;
	/**
	 * 还款方式
	 */
	private String housetype;
	/**
	 * 贷款类型
	 */
	private String buyhouseamt;
	/**
	 * 公积金贷款利率
	 */
	private String buyhousearea;
	/**
	 * 商业贷款利率
	 */
	private String loanhousenum;
	/**
	 * 贷款期限
	 */
	private String housingarea;
	/**
	 * 还款方式
	 */
	private String housestruct;
	/**
	 * 贷款类型
	 */
	private String secfinishdate;
	/**
	 * 公积金贷款利率
	 */
	private String loanterm;
	/**
	 * 商业贷款利率
	 */
	private String repaymode;
	public String getOthplacflag() {
		return othplacflag;
	}
	public void setOthplacflag(String othplacflag) {
		this.othplacflag = othplacflag;
	}
	public String getPaycity() {
		return paycity;
	}
	public void setPaycity(String paycity) {
		this.paycity = paycity;
	}
	public String getBirthday1() {
		return birthday1;
	}
	public void setBirthday1(String birthday1) {
		this.birthday1 = birthday1;
	}
	public String getLoansex1() {
		return loansex1;
	}
	public void setLoansex1(String loansex1) {
		this.loansex1 = loansex1;
	}
	public String getLoanbal1() {
		return loanbal1;
	}
	public void setLoanbal1(String loanbal1) {
		this.loanbal1 = loanbal1;
	}
	public String getLoanmonpay1() {
		return loanmonpay1;
	}
	public void setLoanmonpay1(String loanmonpay1) {
		this.loanmonpay1 = loanmonpay1;
	}
	public String getBirthday2() {
		return birthday2;
	}
	public void setBirthday2(String birthday2) {
		this.birthday2 = birthday2;
	}
	public String getLoansex2() {
		return loansex2;
	}
	public void setLoansex2(String loansex2) {
		this.loansex2 = loansex2;
	}
	public String getLoanbal2() {
		return loanbal2;
	}
	public void setLoanbal2(String loanbal2) {
		this.loanbal2 = loanbal2;
	}
	public String getLoanmonpay2() {
		return loanmonpay2;
	}
	public void setLoanmonpay2(String loanmonpay2) {
		this.loanmonpay2 = loanmonpay2;
	}
	public String getTotalincome() {
		return totalincome;
	}
	public void setTotalincome(String totalincome) {
		this.totalincome = totalincome;
	}
	public String getOtherpay() {
		return otherpay;
	}
	public void setOtherpay(String otherpay) {
		this.otherpay = otherpay;
	}
	public String getHousetype() {
		return housetype;
	}
	public void setHousetype(String housetype) {
		this.housetype = housetype;
	}
	public String getBuyhouseamt() {
		return buyhouseamt;
	}
	public void setBuyhouseamt(String buyhouseamt) {
		this.buyhouseamt = buyhouseamt;
	}
	public String getBuyhousearea() {
		return buyhousearea;
	}
	public void setBuyhousearea(String buyhousearea) {
		this.buyhousearea = buyhousearea;
	}
	public String getLoanhousenum() {
		return loanhousenum;
	}
	public void setLoanhousenum(String loanhousenum) {
		this.loanhousenum = loanhousenum;
	}
	public String getHousingarea() {
		return housingarea;
	}
	public void setHousingarea(String housingarea) {
		this.housingarea = housingarea;
	}
	public String getHousestruct() {
		return housestruct;
	}
	public void setHousestruct(String housestruct) {
		this.housestruct = housestruct;
	}
	public String getSecfinishdate() {
		return secfinishdate;
	}
	public void setSecfinishdate(String secfinishdate) {
		this.secfinishdate = secfinishdate;
	}
	public String getLoanterm() {
		return loanterm;
	}
	public void setLoanterm(String loanterm) {
		this.loanterm = loanterm;
	}
	public String getRepaymode() {
		return repaymode;
	}
	public void setRepaymode(String repaymode) {
		this.repaymode = repaymode;
	}
	public String getIsfirstFundloans() {
		return isfirstFundloans;
	}
	public void setIsfirstFundloans(String isfirstFundloans) {
		this.isfirstFundloans = isfirstFundloans;
	}
	public String getIslocalPayFunc() {
		return islocalPayFunc;
	}
	public void setIslocalPayFunc(String islocalPayFunc) {
		this.islocalPayFunc = islocalPayFunc;
	}
	public String getIdCardNumber() {
		return idCardNumber;
	}
	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}
	public String getPayBaseNum() {
		return payBaseNum;
	}
	public void setPayBaseNum(String payBaseNum) {
		this.payBaseNum = payBaseNum;
	}
	public String getMonthPayMoney() {
		return monthPayMoney;
	}
	public void setMonthPayMoney(String monthPayMoney) {
		this.monthPayMoney = monthPayMoney;
	}
	public String getMatePayBaseNum() {
		return matePayBaseNum;
	}
	public void setMatePayBaseNum(String matePayBaseNum) {
		this.matePayBaseNum = matePayBaseNum;
	}
	public String getMateMonthPayMoney() {
		return mateMonthPayMoney;
	}
	public void setMateMonthPayMoney(String mateMonthPayMoney) {
		this.mateMonthPayMoney = mateMonthPayMoney;
	}
	public String getBuildingType() {
		return buildingType;
	}
	public void setBuildingType(String buildingType) {
		this.buildingType = buildingType;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getExtractTotal() {
		return extractTotal;
	}
	public void setExtractTotal(String extractTotal) {
		this.extractTotal = extractTotal;
	}
	
}
