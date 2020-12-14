package com.yondervision.mi.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.common.message.SimpleHttpMessageUtil;
import com.yondervision.mi.dto.CMi701;
import com.yondervision.mi.dto.Mi701;
import com.yondervision.mi.dto.Mi701WithBLOBs;

/**
 * 清除网站缓存数据
 * @author TWF
 *
 */
public class ClearWebCacheUtils {

	
	/**
	 * 当有效文章发布、修改、删除时清除网站缓存，重新生成数据
	 * @param centerId
	 */
	/**
	 * 新增文章时清除缓存
	 * @param form
	 */
	public static void clearInsert(CMi701 form) {
		
		if(checkValid(form))
			clear(form.getCenterid());
	}
	/**
	 * 删除文章时清除缓存
	 * 其中只要有一条是有效的则需要清除缓存
	 * @param deleteList
	 */
	public static void clearDelete(List<Mi701WithBLOBs> deleteList) {
		boolean flag =false;
		for(Mi701WithBLOBs m :deleteList){
			if(checkValid(m)){
				flag = true;
				break;
			}
		}
		if(flag)
			clear(deleteList.get(0).getCenterid());
		
	}
	/**
	 * 发布文章时清除缓存
	 * @param mi701List
	 */
	public static void clearPublish(List<Mi701> mi701List) {
		clear(mi701List.get(0).getCenterid());
	}
	
	private static boolean checkValid(Mi701WithBLOBs form){
		if(Constants.IS_NOT_VALIDFLAG.equals(form.getValidflag()))//无效
			return false;
		if(!Constants.PUBLISH_FLG_ONE.equals(form.getFreeuse3()))//不是已发布
			return false;
		if (!CommonUtil.isEmpty(form.getReleasetime()) && form.getReleasetime().compareTo(CommonUtil.getSystemDate()) >= 0)//发布时间大于今天
			return false;
		return true;
		
	}
	
	private static boolean checkValid(CMi701 form){
		if(Constants.IS_NOT_VALIDFLAG.equals(form.getValidflag()))//无效
			return false;
		if(!Constants.PUBLISH_FLG_ONE.equals(form.getFreeuse3()))//不是已发布
			return false;
		if (!CommonUtil.isEmpty(form.getReleasetime()) && form.getReleasetime().compareTo(CommonUtil.getSystemDate()) >= 0)//发布时间大于今天
			return false;
		return true;
		
	}
	private static void clear(String centerId){
		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "web_url_"+centerId);
		if(StringUtils.isNotBlank(url)){
			HashMap<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("centerId",centerId);
			paramMap.put("aplicationAlias", "YBMAPZH");//作为验证秘钥进行传输
			
			paramMap.put("secretConten", getMD5(new TreeMap<String, String>(paramMap)));//对参数进行加密
			
			SimpleHttpMessageUtil  httpUtil  = new SimpleHttpMessageUtil();
			try {
				httpUtil.sendPost(url, paramMap, "UTF-8");
			} catch (Exception e) {
				LoggerUtil.getLogger().error("清除网站缓存数据出错!"); 
			}
		}
	}
	
	/**
	 * MD5加密
	 * @param hashMap 使用TreeMap 保证遍历后获取的值相同
	 * @return
	 */
	public static String getMD5(TreeMap<String, String> hashMap ) {
		hashMap.put("secret", hashMap.get("aplicationAlias"));
		String values="";
		Iterator<Entry<String, String>> it = hashMap.entrySet().iterator();      
    	while (it.hasNext()) {       
            Entry<String, String> e = it.next();   
            values += e.getKey()+":";
			values += e.getValue()+",";
        }  
		
		String paraMD5;
		try {
			paraMD5 = EncryptionByMD5.getMD5(values.toString().getBytes("UTF-8"));
			paraMD5 = EncryptionByMD5.getMD5(paraMD5.toString().getBytes("UTF-8"));
		} catch (Exception e) {
			paraMD5 = "";
			e.printStackTrace();
		}
		return paraMD5;
	}
	
	/**
	 * 校验参数
	 * @param hashMap
	 * @param secretConten
	 * @return  true : 校验通过 
	 */
	public static boolean checkMD5Secret(HashMap<String, String> hashMap,String secretConten) {
		boolean flag = false;
		if(secretConten.equals(getMD5(new TreeMap<String, String>(hashMap))))
			flag = true;
		return flag;
		
	}
	
/*	public static void main(String[] args) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("centerId","00087500");
		paramMap.put("aplicationAlias", "YBMAPZH");//作为验证秘钥进行传输
		
		System.out.println( getMD5(new TreeMap<String, String>(paramMap)));//eb5b1fcef6ff3b2fa09252ee5a4747a4
	}
*/


	
	
	
}
