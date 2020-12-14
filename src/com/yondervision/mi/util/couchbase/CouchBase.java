package com.yondervision.mi.util.couchbase;
/**
 * 作者:韩占远
 * 日期:2014-12-16
 * 功能:CouchBase数据库联接
 * 说明:
 *    1.这次我还更新了couchbase-client-1.4.1.jar,spymemcached-2.11.2.jar,jettison-1.1.jar
 *        httpclient-cache-4.3.2.jar,httpcore-nio-4.3.1.jar,httpmime-4.3.2.jar
          netty-3.5.5.Final.jar,spymemcached-2.11.2.jar
 
 *    2.main 函数里面有使用说明
 * */
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

 



import net.spy.memcached.CASResponse;
import net.spy.memcached.CASValue;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactory;
import com.couchbase.client.CouchbaseConnectionFactoryBuilder;
import com.yondervision.mi.common.ChannelFlow;
import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.Flow;
import com.yondervision.mi.util.PropertiesReader;
 

 

public class CouchBase{	
//	private String[] urls = new String[]{"http://122.49.5.67:8091/pools","http://122.49.5.68:8091/pools"};
	//private String[] urls = new String[]{"http://172.16.0.165:8091/pools"};
	String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "couchbase").trim();
	private String[] urls = new String[]{url};
	private static CouchBase couchbase = null; 
	public static CouchBase getInstance() { 
		if (couchbase == null){
			couchbase = new CouchBase("YBMAP");
			couchbase.init();
		}		
		return couchbase;
	}
 
	
	final private ArrayList<URI> nodes = new ArrayList<URI>();
	private String bucket = null;
	final private String password = "111111";
	final private String lock = new String("lock");

	private boolean shutdowned = false;

	public void init() {
		shutdowned = false;
		for(String url : urls){
			nodes.add(URI.create(url));
		}
		getClient();
	}

	private CouchbaseClient client = null;

	public CouchbaseClient getClient() {
		if (client == null) {
			if (bucket == null){
				return null;
			}
			synchronized (lock) {
				while (true) {
					if (shutdowned || client != null) {
						break;
					}
					try {
						CouchbaseConnectionFactoryBuilder cfb = new CouchbaseConnectionFactoryBuilder(); 
						CouchbaseConnectionFactory cf = cfb.buildCouchbaseConnection(nodes, bucket, password);
						client = new CouchbaseClient(cf);						
						//client = new CouchbaseClient(nodes, bucket, password);
						break;
					} catch (IOException e) {
						e.printStackTrace();
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return client;
	}
	
	public void setClient(CouchbaseClient client) {
		this.client = client;
	}

	public void close(){
		shutdowned = true;
		if (client != null){
			client.shutdown();
		}
	}

	public CouchBase() {

	}
	
	public CouchBase(String bucket){
		this.bucket = bucket;
	}
	
	public String parse(Object obj){
		if(!String.class.isInstance(obj)){
		//if(!obj.getClass().getName().equals(String.class.getName())){
			obj = JsonUtil.getDisableHtmlGson().toJson(obj);
		}
		return obj.toString();
	}
	
	public Boolean save(String key, Object value){
		try{
			OperationFuture<Boolean> ret = getClient().set(key, parse(value));			
			if (ret.get().booleanValue()){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public Boolean cas(String key, Object value,long casId){
		try{
			CASResponse casr = getClient().cas(key, casId, parse(value));			
			if (casr.equals(CASResponse.OK)) {
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public Boolean save(String key, Object value, int exp){
		try{
			OperationFuture<Boolean> ret = getClient().set(key, exp, parse(value));
			if (ret.get().booleanValue()){
				return true;
			}else{
				System.err.println(ret.getStatus().getMessage());
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public <T> T get(String key, Class<T> t){
		Object obj = getClient().get(key);
		if(obj == null){
			return null;
		}
		return JsonUtil.getDisableHtmlGson().fromJson(obj.toString(), t);
	}

	public Object get(String key){
		return getClient().get(key);
	}
	
	public <T> T get(String key, Class<T> t, int exp){
		Object obj = getClient().getAndTouch(key, exp).getValue();
		if(obj == null){
			return null;
		}
		return JsonUtil.getDisableHtmlGson().fromJson(obj.toString(), t);
	}
	
	public Boolean touch(String key, int exp){
		try {
			return getClient().touch(key, exp).get();
		} catch (Exception e) {
			return null;
		}
	}

	public Boolean delete(String key){
		try {
			return getClient().delete(key).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	public Map<String, Object> query(Collection<String> keys){
		return getClient().getBulk(keys);
	}
	
	public static void main(String[] args) 
	{
		CouchBase cb=CouchBase.getInstance();
		 
	 
		System.out.println(cb.get("WKF_url_00075500"));
		cb.close();
		
		System.out.println("ok");
		
	}
	
	public CASValue getLock(Object key, int exp){
		CASValue result = getClient().getAndLock(parse(key), exp);
		System.out.println("CouchBase -- getClient.getAndLock= "+result.getValue());
		return result;
	}
	
	public Boolean unLock(String key,long casId){
		System.out.println("CouchBase -- Object get---key=   "+key+ "casId=   "+casId);
		System.out.println("CouchBase -- Object get---parse(key)=   "+parse(key));
		Boolean result = getClient().unlock(key, casId);
		System.out.println("CouchBase -- getClient.unlock= "+result);
		return result;
	}
	
}