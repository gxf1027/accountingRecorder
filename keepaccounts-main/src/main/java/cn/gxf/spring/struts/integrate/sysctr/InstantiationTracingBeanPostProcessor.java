package cn.gxf.spring.struts.integrate.sysctr;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;

import cn.gxf.spring.struts.integrate.sysctr.log.Log4j2Config;

public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		//�ж�spring�����Ƿ�������
        if (event.getApplicationContext().getParent() == null) {
        	// server������������Ҫ��ʽ�����������أ�ע�ᵽconsul
        	MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
    		System.out.println("InstantiationTracingBeanPostProcessor.....��ʽ�����������أ�ע�ᵽconsul.");
    		
    		Log4j2Config.config();
        }
		
	}

}
