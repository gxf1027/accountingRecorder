package cn.gxf.spring.struts.integrate.sysctr;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;

import cn.gxf.spring.struts.integrate.sysctr.log.Log4j2Config;

public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		//判断spring容器是否加载完成
        if (event.getApplicationContext().getParent() == null) {
        	// server程序启动后，需要显式调用心跳开关，注册到consul
        	MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
    		System.out.println("InstantiationTracingBeanPostProcessor.....显式调用心跳开关，注册到consul.");
    		
    		Log4j2Config.config();
        }
		
	}

}
