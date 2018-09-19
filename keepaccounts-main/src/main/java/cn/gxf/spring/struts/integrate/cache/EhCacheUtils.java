package cn.gxf.spring.struts.integrate.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

import net.sf.ehcache.Element;



@Component
public class EhCacheUtils{
	
	@Autowired
	private EhCacheCacheManager cacheManager;
	
	private Map<String, EhCacheCache> cacheMap = new HashMap<String, EhCacheCache>();
	
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

}
