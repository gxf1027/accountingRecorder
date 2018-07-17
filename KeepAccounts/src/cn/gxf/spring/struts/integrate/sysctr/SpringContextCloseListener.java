package cn.gxf.spring.struts.integrate.sysctr;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;

public class SpringContextCloseListener implements ApplicationListener<ContextClosedEvent>{

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		
        if (event.getApplicationContext().getParent() == null) {
        	// server程序启动后，需要显式调用心跳开关，注册到consul
        	MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, false);
    		System.out.println("SpringContextCloseListener.....容器关闭，反注册到consul.");
        }
	}
	
}
