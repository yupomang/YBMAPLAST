package com.yondervision.mi.common.filter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.spy.memcached.CASValue;
import org.jfree.util.Log;
import com.yondervision.mi.common.ChannelFlow;
import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.Flow;
import com.yondervision.mi.common.Quotai;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.dao.Mi040DAO;
import com.yondervision.mi.dao.Mi050DAO;
import com.yondervision.mi.dao.Mi051DAO;
import com.yondervision.mi.dao.Mi052DAO;
import com.yondervision.mi.dao.Mi053DAO;
import com.yondervision.mi.dao.Mi627DAO;
import com.yondervision.mi.dto.Mi040;
import com.yondervision.mi.dto.Mi040Example;
import com.yondervision.mi.dto.Mi050;
import com.yondervision.mi.dto.Mi050Example;
import com.yondervision.mi.dto.Mi051;
import com.yondervision.mi.dto.Mi051Example;
import com.yondervision.mi.dto.Mi052;
import com.yondervision.mi.dto.Mi052Example;
import com.yondervision.mi.dto.Mi053;
import com.yondervision.mi.dto.Mi053Example;
import com.yondervision.mi.dto.Mi627;
import com.yondervision.mi.dto.Mi627Example;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.couchbase.CouchBase;
import com.yondervision.mi.util.couchbase.JsonUtil;
import com.yondervision.mi.util.security.AES;

public class CheckAppService {
	private final String startupOK = "1";
	private final String authentication = "1";
	private final String checktoken="1";
	private final int lockTime = 10;
	private final int unLockTime = 15;
	private final String serviceStart="1";	
	
	public String pid = "";
	
	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public boolean appservice(AES aes ,HttpServletRequest req ,HttpServletResponse rep ,UserContext user) throws NoRollRuntimeErrorException{
		boolean moneyType = false;
		String appid = aes.decrypt(req.getParameter("appid").trim());
		String appkey = aes.decrypt(req.getParameter("appkey").trim());
		String centerId = req.getParameter("centerId").trim();
		String channel = req.getParameter("channel").trim();
		String buzType = req.getParameter("buzType").trim();
		String appToken = req.getParameter("appToken").trim();
		CouchBase cb=CouchBase.getInstance();
		//**app  start
		System.out.println("***** 应用信息检查开始："+CommonUtil.getSystemDate());
		Mi040DAO mi040DAO = (Mi040DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi040DAO");
		Mi040Example m040e=new Mi040Example();
		com.yondervision.mi.dto.Mi040Example.Criteria ca= m040e.createCriteria();
		ca.andAppidEqualTo(appid).andAppkeyEqualTo(appkey).andChannelEqualTo(channel).andCenteridEqualTo(centerId);
		List<Mi040> list040 = mi040DAO.selectByExample(m040e);
		Mi040 mi040 = null;
		if(CommonUtil.isEmpty(list040)){
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS
					.getValue(), "应用信息错误");
		}else{
			mi040 = list040.get(0);
			req.setAttribute("MI040Pid", mi040.getPid());
			req.setAttribute("MI040PidName", mi040.getAppname());
			req.setAttribute("MI040Appid", mi040.getAppid());
			this.setPid(mi040.getPid());
			if(!startupOK.equals(mi040.getStartup())){
				throw new NoRollRuntimeErrorException(WEB_ALERT.SYS
						.getValue(), "应用已经停用");
			}
			SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);
			String today = formatter.format(System.currentTimeMillis());
			if(!(today.compareTo(mi040.getEffectivedaytart())>0&&today.compareTo(mi040.getEffectivedayend())<0)){
				throw new NoRollRuntimeErrorException(WEB_ALERT.SYS
						.getValue(), "应用服务过期");
			}
			if(checktoken.equals(mi040.getChecktoken().trim())){				
				String key = mi040.getCenterid()+"|"+mi040.getChannel()+"|"+mi040.getAppid()+"|"+mi040.getAppkey();
				if(CommonUtil.isEmpty((String)cb.get(key))){
					throw new NoRollRuntimeErrorException(WEB_ALERT.SYS
							.getValue(), "应用TOKEN过期");
				}
				if(!appToken.equals((String)cb.get(key))){
					throw new NoRollRuntimeErrorException(WEB_ALERT.SYS
							.getValue(), "应用TOKEN异常");
				}
			}
		}
		System.out.println("***** 应用信息检查结束："+CommonUtil.getSystemDate());
		//**app  end
		System.out.println("***** 业务量统计开始："+CommonUtil.getSystemDate());
		//TODO
		req.setAttribute("addFlowIs", "false");
		if("/appapi90106.json,/appapi90415.json,/appapi90423.json,/appapi90206.json,/appapi40107.json,/appapi99901.json,/appapi30314.json,/appapi30315.json,/appapi30292.json,/appapi30291.json,/appapi00199.json,/appapi07030.json,/appapi00144.json,/appapi00145.json,/appapi00158.json".indexOf(req.getServletPath())<0){
			addFlow(centerId , channel,req);
			req.removeAttribute("addFlowIs");
			req.setAttribute("addFlowIs", "true");
		}		
		System.out.println("***** 业务量统计结束："+CommonUtil.getSystemDate());
		// SERVICE CHECK
		
		Mi051DAO mi051DAO = (Mi051DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi051DAO");
		Mi051Example m051e=new Mi051Example();
		com.yondervision.mi.dto.Mi051Example.Criteria ca051= m051e.createCriteria();
		ca051.andBuztypeEqualTo(buzType);
		List<Mi051> list051 = mi051DAO.selectByExample(m051e);
		if(CommonUtil.isEmpty(list051)){
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS
					.getValue(), "服务信息错误");
		}
		System.out.println("***** 资金服务限额检查开始："+CommonUtil.getSystemDate());
		Mi051 mi051 = list051.get(0);
		req.setAttribute("moneyType", mi051.getMoneytype());
		if(mi051.getMoneytype().trim().equals("1")){//资金类业务计算单笔、当日是限额
			moneyType = true;
			req.setAttribute("aes"+centerId, aes);
			String money = aes.decrypt(req.getParameter("money"));
			quotaiCheck(centerId ,channel ,buzType ,money);
			Mi627DAO mi627Dao = (Mi627DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi627Dao");
			Mi627Example m627e=new Mi627Example();
			com.yondervision.mi.dto.Mi627Example.Criteria ca627= m627e.createCriteria();
			ca627.andFestivaldateEqualTo(CommonUtil.getDate());
			ca627.andFestivalflagEqualTo("1");
			ca627.andCenteridEqualTo(centerId);
			ca627.andValidflagEqualTo("1");
			
			List<Mi627> list627 = mi627Dao.selectByExample(m627e);
			if(!CommonUtil.isEmpty(list627)){
				throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR
						.getValue(), "非工作日期间不能办理业务，请在下一工作日进行业务办理!");
			}else{
				System.out.println("【资金类业务查询节假日时，当前日期为正常工作日！】:centerId:"+centerId+",date:"+CommonUtil.getDate());
			}
			
		}
		System.out.println("***** 资金服务限额检查结束："+CommonUtil.getSystemDate());
		
		System.out.println("***** 应用服务检查开始："+CommonUtil.getSystemDate());
		Mi053DAO mi053DAO = (Mi053DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi053DAO");
		Mi053Example m053e=new Mi053Example();
		com.yondervision.mi.dto.Mi053Example.Criteria ca053= m053e.createCriteria();
		ca053.andCenteridEqualTo(centerId).andChannelEqualTo(channel).andPidEqualTo(mi040.getPid()).andServiceidEqualTo(mi051.getServiceid());
		List<Mi053> list053 = mi053DAO.selectByExample(m053e);
		if(CommonUtil.isEmpty(list053)){
			throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR
					.getValue(), "应用下服务信息错误！");
		}
		Mi053 mi053 = list053.get(0);
		if(!serviceStart.equals(mi053.getStartserver())){
			throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR
					.getValue(), "该服务暂停！");
		}
		
		checkServiceDateTime(mi053);
//		SimpleDateFormat formatter = new SimpleDateFormat(Constants.TIME_FORMAT);
//		String time = formatter.format(System.currentTimeMillis());
//		if(!CommonUtil.isEmpty(mi053.getStarttime())&&!CommonUtil.isEmpty(mi053.getEndtime())){
//			if(!(time.compareTo(mi053.getStarttime())>0&&time.compareTo(mi053.getEndtime())<0)){
//				throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR
//						.getValue(), "请在"+mi053.getStarttime()+"到"+mi053.getEndtime()+"时间办理业务");
//			}
//		}
		
		// URL CHECK
		String url = req.getServletPath().substring(req.getServletPath().lastIndexOf("/")+1);
		Mi050DAO mi050DAO = (Mi050DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi050DAO");
		Mi050Example m050e=new Mi050Example();
		com.yondervision.mi.dto.Mi050Example.Criteria ca050= m050e.createCriteria();
		ca050.andUrlEqualTo(url).andValidflagEqualTo("1");
		List<Mi050> list050 = mi050DAO.selectByExample(m050e);
		if(CommonUtil.isEmpty(list050)){
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS
					.getValue(), "应用下服务接口错误");
		}
		Mi050 mi050 = list050.get(0);
		Mi052DAO mi052DAO = (Mi052DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi052DAO");
		Mi052Example m052e=new Mi052Example();
		com.yondervision.mi.dto.Mi052Example.Criteria ca052= m052e.createCriteria();
		ca052.andServiceidEqualTo(mi051.getServiceid()).andApiidEqualTo(mi050.getApiid()).andValidflagEqualTo("1");
		List<Mi052> list052 = mi052DAO.selectByExample(m052e);
		System.out.println("***** 应用服务检查结束："+CommonUtil.getSystemDate());
		if(CommonUtil.isEmpty(list052)){
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS
					.getValue(), "应用下服务接口错误");
		}
		
		return moneyType;
	}
	
	/**
	 * 校验服务是否在可服务时间
	 * @param mi053
	 * @throws ParseException 
	 */
	private void checkServiceDateTime(Mi053 mi053){
		String day = mi053.getFreeuse2();
		if(!CommonUtil.isEmpty(day))
		{
			try {
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH) + 1;
				int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);  
				String startDay = day.split("-")[0];
				String endDay = day.split("-")[1];
				//判断设置结束日期是否大于当前月最后一天
				if(Integer.parseInt(endDay)>maxDay)endDay = maxDay + "";
				startDay = startDay.length()==1?"0"+startDay:startDay;
				endDay = endDay.length()==1?"0"+endDay:endDay;
				String monthString = month+"";
				monthString = monthString.length()==1?"0"+monthString:monthString;
				String startDate = year + "-" + monthString + "-" + startDay;
				Log.info("校验服务开始日期："+startDate);
				String endDate = "";
				//如果开始时间大于等于结束时间，表示跨月
				if(Integer.parseInt(startDay) > Integer.parseInt(endDay))
				{
					endDate = year + "-" + monthString + "-" + maxDay;
					Log.info("跨月：校验服务结束日期："+endDate);
					boolean temp = CommonUtil.belongCalendar(cal.getTime(),startDate, endDate, Constants.DATE_FORMAT_YYYY_MM_DD);
					if(!temp)
					{
						startDate = year + "-" + monthString + "-01" ;
						endDate = year + "-" + monthString + "-" + endDay;
						temp = CommonUtil.belongCalendar(cal.getTime(),startDate, endDate, Constants.DATE_FORMAT_YYYY_MM_DD);
						if(!temp)
						{
							throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR
									.getValue(), "请在每月"+startDay+"号到次月"+endDay+"号办理业务");
						}
					}
				}else
				{
					endDate = year + "-" + monthString + "-" + endDay;
					Log.info("不跨月：校验服务结束日期："+endDate);
					boolean temp = CommonUtil.belongCalendar(cal.getTime(),startDate, endDate, Constants.DATE_FORMAT_YYYY_MM_DD);
					if(!temp)
					{
						throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR
								.getValue(), "请在每月"+startDay+"号到"+endDay+"号办理业务");
					}
				}
			} catch (ParseException e) {
				throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR
						.getValue(), "当前业务不可用");
			}
		}
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.TIME_FORMAT);
		String time = formatter.format(System.currentTimeMillis());
		if(!CommonUtil.isEmpty(mi053.getStarttime())&&!CommonUtil.isEmpty(mi053.getEndtime())){
				if(!(time.compareTo(mi053.getStarttime())>0&&time.compareTo(mi053.getEndtime())<0)){
				throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR
						.getValue(), "请在"+mi053.getStarttime()+"到"+mi053.getEndtime()+"时间办理业务");
			}
		}
	}

	public void addFlow(final String centerId ,final String channel,final HttpServletRequest req){
//		Thread t = new Thread(new Runnable(){  
//            public void run(){  
//            	addFlowCount(centerId,channel,req);
//            }});  
//        t.start();
        addFlowCount(centerId,channel,req);
	}
	
	public void addFlowCount(String centerId ,String channel,HttpServletRequest req){
		CouchBase cb=CouchBase.getInstance();
		String key = centerId+"|Flow";
		long beginTime = (new Date()).getTime();
		while(true)
		{
			long endTime = (new Date()).getTime();
			CASValue cas = cb.getLock(key, lockTime);
			if((endTime - beginTime) > (lockTime * 1000))break;
			if ( cas.getCas() != -1 ){
//				long beginTime = System.currentTimeMillis();
				Flow flow = JsonUtil.getGson().fromJson((String)cb.get(key).toString(), Flow.class);
				System.out.println("业务量统计缓存信息 ："+JsonUtil.getGson().toJson(cb.get(key)));
				System.out.println("#$#$#$#$[ "+flow.getBussinessflow()+" ]");
				System.out.println("#$#$#$#$[ "+flow.getBussinessflowTOP()+" ]");
				if(flow.getBussinessflowTOP()<(flow.getBussinessflow()+1)){
					Monitor monitor = new Monitor();
					monitor.monitorNotice("02", centerId, "业务量超限额：当前业务量("+(flow.getBussinessflow()+1)+"),业务量限额("+flow.getBussinessflowTOP()+")");//type 02 flow ctrl
					System.out.println("业务量访问超限额"+(flow.getBussinessflow()+1));
					throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR
							.getValue(), "业务量超限额,请稍候访问！");
					//return;
				}
				List<ChannelFlow> list = flow.getChannelbusinesses();
				
				for(int i=0;i<list.size();i++){
					System.out.println("业务访问量channel[ "+list.get(i).getChannel()+" ]");
					if(channel.equals(list.get(i).getChannel())){
						ChannelFlow cf = list.get(i);
						cf.setCountBusinesses(list.get(i).getCountBusinesses()+1);
						list.set(i, cf);
						flow.setBussinessflow(flow.getBussinessflow()+1);
						break;
					}
				}
				System.out.println("######  业务量统计加保存前数据 ："+JsonUtil.getGson().toJson(flow));
				flow.setChannelbusinesses(list);
			
				boolean s = cb.cas(key, flow,cas.getCas());	
//				System.out.println("minflow:"+minflow++);
//				long endTime = System.currentTimeMillis();
				break;
			
//			CASValue cas = cb.getLock(key, lockTime);
//			if ( cas.getCas() != -1 ){
//				boolean s = cb.cas(key, flow,cas.getCas());	
//				SecurityCheckLog.writeLog("addflowIn:"+addflowIn++);
			}
		}
	}
	
	
	public void minusFlow(final String centerId ,final String channel){
//		Thread t = new Thread(new Runnable(){  
//            public void run(){  
//            	minusFlowCount(centerId,channel);
//            }});  
//        t.start();
        minusFlowCount(centerId,channel);
	}
	
	public void minusFlowCount(String centerId,String channel){
		try{
			CouchBase cb=CouchBase.getInstance();
			String key = centerId+"|Flow";
			long beginTime = (new Date()).getTime();
			while(true)
			{
				long endTime = (new Date()).getTime();
				if((endTime - beginTime) > (unLockTime * 1000))break;
				CASValue cas = cb.getLock(key, unLockTime);
				if ( cas.getCas() != -1 ){
//					long beginTime = System.currentTimeMillis();
					Object objFlow = (Object)cb.get(key);
					Flow flow = JsonUtil.getGson().fromJson(objFlow.toString(), Flow.class);
					List<ChannelFlow> list = flow.getChannelbusinesses();
					for(int i=0;i<list.size();i++){
						if(channel.equals(list.get(i).getChannel())){
							ChannelFlow cf = list.get(i);
							if(list.get(i).getCountBusinesses()>0){
								cf.setCountBusinesses(list.get(i).getCountBusinesses()-1);
								list.set(i, cf);
								if(flow.getBussinessflow()>0){
									flow.setBussinessflow(flow.getBussinessflow()-1);
								}
							}
							break;
						}
					}
					flow.setChannelbusinesses(list);
					boolean s = cb.cas(key, flow,cas.getCas());	
//					long endTime = System.currentTimeMillis();
					break;
//					cb.unLock(key, cas.getCas());
//					cb.save(key, flow);
				}else
				{
					Thread.sleep(1000);
				}
			}
		}catch (Exception e){
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS
					.getValue(), "业务量统计减异常");
		}
		//** flow ctrl end
	}
	
	public void quotaiCheck(String centerId ,String channel ,String buzType ,String money){
		CouchBase cb=CouchBase.getInstance();
		String key = centerId+"|"+channel+"|"+buzType+"|"+"quotai";
		String quotaiOBJ = (String)cb.get(key);
		Quotai quotai = JsonUtil.getGson().fromJson(quotaiOBJ, Quotai.class);
		
		System.out.println("资金类业务："+quotaiOBJ);
		
		double m = Double.parseDouble(money);
		
		System.out.println("渠道："+channel+",单笔限额："+quotai.getOnequotaiTop()+"，当日限额："+quotai.getDayquotaiTop()+",当日实际累计："+quotai.getDayquotai());
		
		if(m>quotai.getOnequotaiTop()){//单笔限额
			Monitor monitor = new Monitor();
			monitor.monitorNotice("03", centerId, "单笔业务资金超限额：发生金额("+m+"),资金限额("+quotai.getOnequotaiTop()+")");//type 03 one money ctrl
			return;
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);
		String date = formatter.format(System.currentTimeMillis());
		System.out.println("资金缓存中日期："+quotai.getToday()+" , 系统当前日期："+date);
		if(date.compareTo(quotai.getToday())>0){//隔日初始化当日限额
			quotai.setToday(date);
			quotai.setDayquotai(0.0);
		}
		
		quotai.setDayquotai(quotai.getDayquotai()+m);
		if(quotai.getDayquotai()>quotai.getDayquotaiTop()){//当日限额
			Monitor monitor = new Monitor();
			monitor.monitorNotice("03", centerId, "当日累计业务资金超限额：发生金额("+m+"),当日累计资金限额("+quotai.getDayquotaiTop()+")");//type 03 one money ctrl
			return;
		}
		CASValue cas = cb.getLock(key, lockTime);
		if ( cas.getCas() != -1 ){
			cb.unLock(key, cas.getCas());
			cb.save(key, quotai);
		}		
	}
	
	
	/**
	 * 失败时减去当日累计
	 * @param centerId
	 * @param channel
	 * @param buzType
	 * @param money
	 */
	public void quotaiErrer(String centerId ,String channel ,String buzType ,String money){
		
		//待处理。。。。。。。。。。。。。。。。。。。。。。。。。
		
		
		CouchBase cb=CouchBase.getInstance();
		String key = centerId+"|"+channel+"|"+buzType+"|"+"quotai";
		String quotaiOBJ = (String)cb.get(key);
		Quotai quotai = JsonUtil.getGson().fromJson(quotaiOBJ, Quotai.class);
		
		System.out.println("资金类业务："+quotaiOBJ);
		
		double m = Double.parseDouble(money);
		
		System.out.println("渠道："+channel+",单笔限额："+quotai.getOnequotaiTop()+"，当日限额："+quotai.getDayquotaiTop()+",当日实际累计："+quotai.getDayquotai());
		
		if(m>quotai.getOnequotaiTop()){//单笔限额
			Monitor monitor = new Monitor();
			monitor.monitorNotice("03", centerId, "单笔业务资金超限额：发生金额("+m+"),资金限额("+quotai.getOnequotaiTop()+")");//type 03 one money ctrl
			return;
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);
		String date = formatter.format(System.currentTimeMillis());
		System.out.println("资金缓存中日期："+quotai.getToday()+" , 系统当前日期："+date);
		if(date.compareTo(quotai.getToday())>0){//隔日初始化当日限额
			quotai.setToday(date);
			quotai.setDayquotai(0.0);
		}
		
		quotai.setDayquotai(quotai.getDayquotai()+m);
		if(quotai.getDayquotai()>quotai.getDayquotaiTop()){//当日限额
			Monitor monitor = new Monitor();
			monitor.monitorNotice("03", centerId, "当日累计业务资金超限额：发生金额("+m+"),当日累计资金限额("+quotai.getDayquotaiTop()+")");//type 03 one money ctrl
			return;
		}
		CASValue cas = cb.getLock(key, lockTime);
		if ( cas.getCas() != -1 ){
			cb.unLock(key, cas.getCas());
			cb.save(key, quotai);
		}		
	}
	
	public static void main(String[] args){
//		Flow flow = new Flow();
//		flow.setCenterid(20);
//		ChannelFlow channel = new ChannelFlow();
//		channel.setChannel("10");
//		channel.setCount(10);
//		channel.setType("1");
//		
//		ChannelFlow channel1 = new ChannelFlow();
//		channel1.setChannel("20");
//		channel1.setCount(10);
//		channel1.setType("2");
//		
//		List<ChannelFlow> list = new ArrayList();
//		list.add(channel);
//		list.add(channel1);
//		flow.setChannelbusinesses(list);
//		
//		System.out.println(JsonUtil.getGson().toJson(flow));
		String v = "./ajsdlfjas.json".substring("./ajsdlfjas.json".lastIndexOf("/")+1);
		System.out.println(v);
		
	}
	
}
