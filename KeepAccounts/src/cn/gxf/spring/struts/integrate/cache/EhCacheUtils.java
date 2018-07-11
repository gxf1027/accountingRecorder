package cn.gxf.spring.struts.integrate.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;


public class EhCacheUtils {
	
	@Autowired
	private EhCacheCacheManager cacheManager;
	
	public void remove(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null){
        	return;
        }
        cache.evict(key);
    }
}
