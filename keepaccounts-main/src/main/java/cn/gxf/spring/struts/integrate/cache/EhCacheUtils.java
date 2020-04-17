package cn.gxf.spring.struts.integrate.cache;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

import cn.gxf.spring.struts.integrate.util.DateFomatTransfer;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;



@Component
public class EhCacheUtils{
	
	@Autowired
	private EhCacheCacheManager cacheManager;
	
	private Map<String, EhCacheCache> cacheMap = new ConcurrentHashMap<String, EhCacheCache>();
	
	public void remove(String cacheName){
		
		EhCacheCache cache = getCache(cacheName);
		if (null == cache){
			throw new IllegalArgumentException("cache: " + cacheName + " is null.");
		}
		
		cache.clear();
	}
	
	public void remove(String cacheName, String key) {
		
		EhCacheCache cache = getCache(cacheName);
		
		if (null == cache){
			throw new IllegalArgumentException("cache: " + cacheName + " is null.");
		}
		
		cache.evict(key);
    }
	
	public void put(String cacheName, String key, Object value){
		EhCacheCache cache = getCache(cacheName);
		
		if (null == cache){
			throw new IllegalArgumentException("cache: " + cacheName + " is null.");
		}
		
		Element element = new Element(key, value);
		cache.getNativeCache().put(element);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getAllKeys(String cacheName){
		EhCacheCache cache = getCache(cacheName);
		if (cache == null){
			return null;
		}
		
		return cache.getNativeCache().getKeys();
	}
	
	private EhCacheCache getCache(String cacheName){
		EhCacheCache cache =  cacheMap.get(cacheName);
		
		if (cache != null){
			return cache;
		}
		
		cache =  (EhCacheCache) cacheManager.getCache(cacheName);
		if (cache != null){
			this.cacheMap.put(cacheName, cache);
			return cache;
		}
		
		return null;
		
	}
	
	// ---------------------------------------------
		public List<EhCacheInfo> getCacheInfo(String cacheName){
			List<EhCacheInfo> rvList = new ArrayList<EhCacheInfo>();
			List<Object> keyList= this.getAllKeys(cacheName);
			
			for(Object key:keyList){
				
				Ehcache ehcache = cacheManager.getCacheManager().getEhcache(cacheName);
	            Element element = ehcache.getQuiet(key);
	            
				EhCacheInfo info = new EhCacheInfo();
				info.setKeyName(key.toString());
				info.setHitCount(element.getHitCount());
				info.setCreationTime(DateFomatTransfer.pareTime(element.getCreationTime()));
				info.setLastAccessTime(DateFomatTransfer.pareTime(element.getLastAccessTime()));
				info.setTimeToLive(element.getTimeToLive());
				info.setTimeToIdle(element.getTimeToIdle());
	        }
			
			return rvList;
		}
		
		public void printCacheInfo(String cacheName) {
	        
	        List<Object> list= this.getAllKeys(cacheName);
	        System.out.print(StringUtils.rightPad("Key", 15));
	        System.out.print(" | ");
	        System.out.print(StringUtils.rightPad("HintCount", 10));
	        System.out.print(" | ");
	        System.out.print(StringUtils.rightPad("CreationTime", 25));
	        System.out.print(" | ");
	        System.out.print(StringUtils.rightPad("LastAccessTime", 25));
	        System.out.print(" | ");
	        System.out.print(StringUtils.rightPad("TimeToLive(ms)", 15));
	        System.out.print(" | ");
	        System.out.print(StringUtils.rightPad("TimeToIdle(ms)", 15));
	        System.out.print(" | ");
	        System.out.println();
	        for(Object cache:list){
	            Ehcache ehcache = cacheManager.getCacheManager().getEhcache(cacheName);
	            Element element = ehcache.getQuiet(cache);
	            System.out.print(StringUtils.rightPad(cache.toString(), 15));//key name
	            System.out.print(" | ");
	            System.out.print(StringUtils.rightPad(""+element.getHitCount(), 10));//命中次数
	            System.out.print(" | ");
	            System.out.print(StringUtils.rightPad(DateFomatTransfer.pareTime(element.getCreationTime()), 25));//创建时间
	            System.out.print(" | ");
	            System.out.print(StringUtils.rightPad(DateFomatTransfer.pareTime(element.getLastAccessTime()), 25));//最后访问时间
	            System.out.print(" | ");
	            System.out.print(StringUtils.rightPad(""+element.getTimeToLive(), 15));   //存活时间
	            System.out.print(" | ");
	            System.out.print(StringUtils.rightPad(""+element.getTimeToIdle(), 15));   //空闲时间
	            System.out.print(" | ");
	            System.out.println();
	        }
	    }
		

	    public static void main(String[] args) {
	    	URL url = EhCacheUtils.class.getClassLoader().getResource("ehcache.xml");
	    	CacheManager manager = new CacheManager(url); 
	    	String cacheName = "dmCache";
	        final net.sf.ehcache.Cache cache = manager.getCache(cacheName);
	        
	        cache.put(new Element("key1", "111111111111111111"));
	        cache.get("key1");
	        cache.put(new Element("key2", "123123123"));
	        
	        List<Object> list= cache.getKeys();
	        System.out.print(StringUtils.rightPad("Key", 15));
	        System.out.print(" | ");
	        System.out.print(StringUtils.rightPad("HitCount", 10));
	        System.out.print(" | ");
	        System.out.print(StringUtils.rightPad("CreationTime", 25));
	        System.out.print(" | ");
	        System.out.print(StringUtils.rightPad("LastAccessTime", 25));
	        System.out.print(" | ");
	        System.out.print(StringUtils.rightPad("TimeToLive(s)", 15));
	        System.out.print(" | ");
	        System.out.print(StringUtils.rightPad("TimeToIdle(s)", 15));
	        System.out.print(" | ");
	        System.out.println();
	        for(Object key:list){
	            Ehcache ehcache = manager.getEhcache(cacheName);
	            Element element = ehcache.getQuiet(key);
	            System.out.print(StringUtils.rightPad(key.toString(), 15));//key name
	            System.out.print(" | ");
	            System.out.print(StringUtils.rightPad(""+element.getHitCount(), 10));//命中次数
	            System.out.print(" | ");
	            System.out.print(StringUtils.rightPad(DateFomatTransfer.pareTime(element.getCreationTime()), 25));//创建时间
	            System.out.print(" | ");
	            System.out.print(StringUtils.rightPad(DateFomatTransfer.pareTime(element.getLastAccessTime()), 25));//最后访问时间
	            System.out.print(" | ");
	            System.out.print(StringUtils.rightPad(""+element.getTimeToLive(), 15));   //存活时间
	            System.out.print(" | ");
	            System.out.print(StringUtils.rightPad(""+element.getTimeToIdle(), 15));   //空闲时间
	            System.out.print(" | ");
	            System.out.println();
	        }
	    }

}
