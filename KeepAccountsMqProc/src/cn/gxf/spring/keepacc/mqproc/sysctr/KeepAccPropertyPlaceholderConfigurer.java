package cn.gxf.spring.keepacc.mqproc.sysctr;

import java.util.Enumeration;
import java.util.Properties;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import cn.gxf.spring.keepacc.mqproc.util.AESUtil;


public class KeepAccPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private String postfix = ".encryption";
	
	@Override
	public void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) {
		Enumeration<?> keys = props.propertyNames();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			String value = props.getProperty(key);
			if (key.endsWith(this.postfix) && null != value) {
				props.remove(key);
				key = key.substring(0, key.length() - this.postfix.length());
				// Ω‚√‹
				value = AESUtil.decrypt(value.trim());
				props.setProperty(key, value);
			}
			System.setProperty(key, value);
		}

		super.processProperties(beanFactory, props);
	}
}
