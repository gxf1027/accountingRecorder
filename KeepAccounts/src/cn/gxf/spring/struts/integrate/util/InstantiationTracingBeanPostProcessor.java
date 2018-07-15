package cn.gxf.spring.struts.integrate.util;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;

public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		// server������������Ҫ��ʽ�����������أ�ע�ᵽconsul
		MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
		System.out.println("InstantiationTracingBeanPostProcessor.....��ʽ�����������أ�ע�ᵽconsul.");
	}

}
