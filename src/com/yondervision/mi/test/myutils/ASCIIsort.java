package com.yondervision.mi.test.myutils;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;  
import java.util.List;
import java.util.Map;  

import org.apache.commons.lang.StringUtils;
  
  
public class ASCIIsort {  
  
    public static void main(String[] args) {  
        //字典序列排序  
        Map<String,String> paraMap = new HashMap<String,String>();  
        paraMap.put("total_fee","200");  
        paraMap.put("appid", "wxd678efh567hg6787");  
        paraMap.put("body", "腾讯充值中心-QQ会员充值");  
        paraMap.put("out_trade_no","20150806125346");  
        String url = formatUrlMap(paraMap, true, true);  
        System.out.println(url);  
    }  
  
    /** 
     *  
     * 方法用途: 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序），并且生成url参数串<br> 
     * 实现步骤: <br> 
     * @param paraMap   要排序的Map对象 
     * @param urlEncode   是否需要URLENCODE 
     * @param keyToLower    是否需要将Key转换为全小写 
     *            true:key转化成小写，false:不转化 
     * @return 
     */  
    public static String formatUrlMap(Map<String, String> paraMap, boolean urlEncode, boolean keyToLower)  
    {  
        String buff = "";  
        Map<String, String> tmpMap = paraMap;  
        try  
        {  
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());  
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）  
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>()  
            {  
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2)  
                {  
                    return (o1.getKey()).toString().compareTo(o2.getKey());  
                }  
            });  
            // 构造URL 键值对的格式  
            StringBuilder buf = new StringBuilder();  
            for (Map.Entry<String, String> item : infoIds)  
            {  
                if (StringUtils.isNotBlank(item.getKey()))  
                {  
                    String key = item.getKey();  
                    String val = item.getValue();  
                    if (urlEncode)  
                    {  
                        val = URLEncoder.encode(val, "utf-8");  
                    }  
                    if (keyToLower)  
                    {  
                        buf.append(key.toLowerCase() + "=" + val);  
                    } else  
                    {  
                        buf.append(key + "=" + val);  
                    }  
                    buf.append("&");  
                }  
   
            }  
            buff = buf.toString();  
            if (buff.isEmpty() == false)  
            {  
                buff = buff.substring(0, buff.length() - 1);  
            }  
        } catch (Exception e)  
        {  
           return null;  
        }  
        return buff;  
    } 
}  