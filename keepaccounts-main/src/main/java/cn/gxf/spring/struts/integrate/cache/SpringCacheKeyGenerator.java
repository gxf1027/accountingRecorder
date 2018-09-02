package cn.gxf.spring.struts.integrate.cache;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.ClassUtils;

/*
 * 
 * http://blog.csdn.net/syani/article/details/52239967
 * 
 */
public class SpringCacheKeyGenerator implements KeyGenerator{
	
	private Logger logger = LogManager.getLogger();
	private final String NO_PARAM_KEY = "key001";
	private final String NULL_PARAM_KEY = "keynull";

	@Override
	public Object generate(Object target, Method method, Object... params) {
		
		StringBuilder key = new StringBuilder();
        key.append(target.getClass().getName()).append(".").append(method.getName()).append(":");
        if (params.length == 0) {
        	System.out.println("generated key: " + key.toString());
            return key.append(NO_PARAM_KEY).toString();
        }
        for (Object param : params) {
            if (param == null) {
            	logger.warn("input null param for Spring cache, use default key={}", NULL_PARAM_KEY);
                key.append(NULL_PARAM_KEY);
            } else if (ClassUtils.isPrimitiveArray(param.getClass())) {
                int length = Array.getLength(param);
                for (int i = 0; i < length; i++) {
                    key.append(Array.get(param, i));
                    key.append(',');
                }
            } else if (ClassUtils.isPrimitiveOrWrapper(param.getClass()) || param instanceof String) {
                key.append(param);
            } else {
//            	logger.warn("Using an object as a cache key may lead to unexpected results. " +
//                        "Either use @Cacheable(key=..) or implement CacheKey. Method is " + target.getClass() + "#" + method.getName());
                key.append(param.hashCode());
            }
            key.append('-');
        }

        String finalKey = key.toString();
        
        long cacheKeyHash = 0L;//Hashing.murmur3_128().hashString(finalKey, Charset.defaultCharset()).asLong();
        //logger.debug("using cache key={} hashCode={}", finalKey, cacheKeyHash);
        //logger.debug("Class {} method {} using cache key={}", target.getClass().getName(), method.getName(), finalKey);
        System.out.println("generated key: " + key.toString());
        return finalKey;
	}
	
}
