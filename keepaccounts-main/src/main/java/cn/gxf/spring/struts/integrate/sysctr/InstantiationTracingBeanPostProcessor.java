package cn.gxf.spring.struts.integrate.sysctr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;

import cn.gxf.spring.struts.integrate.sysctr.log.Log4j2Config;
import cn.gxf.spring.struts2.integrate.dao.RegisterUserDao;
import cn.gxf.spring.struts2.integrate.dao.RegisterUserDaoImpl;

@Service
public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private RegisterUserDao registerUserDao;
	
	@Autowired
	private SequenceFactory sequenceFactory;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		//�ж�spring�����Ƿ�������
        if (event.getApplicationContext().getParent() == null) {
        	// server������������Ҫ��ʽ�����������أ�ע�ᵽconsul
        	MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
    		System.out.println("InstantiationTracingBeanPostProcessor.....��ʽ�����������أ�ע�ᵽconsul.");
    		
    		Log4j2Config.config();
    		
    		// ��ʼ����������
    		long nextUserId = registerUserDao.generateUserId();
    		System.out.println("��ʼ��UserId����ֵ��" + nextUserId);
    		sequenceFactory.set(SequenceFactory.USER_ID_SEQ, nextUserId);
        }
		
	}

}
