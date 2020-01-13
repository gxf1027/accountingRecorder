package cn.gxf.spring.struts.integrate.sysctr;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.parser.ParserConfig;
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

		//判断spring容器是否加载完成
        if (event.getApplicationContext().getParent() == null) {
        	// server程序启动后，需要显式调用心跳开关，注册到consul
        	MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
    		System.out.println("InstantiationTracingBeanPostProcessor.....显式调用心跳开关，注册到consul.");
    		
    		Log4j2Config.config();
    		
    		// 初始化序列序列
    		long nextUserId = registerUserDao.generateUserId();
    		System.out.println("初始化UserId序列值：" + nextUserId);
    		sequenceFactory.set(SequenceFactory.USER_ID_SEQ, nextUserId);
    		
    		// sentinel 初始化
    		initFlowRules();
    		
    		// fastjson开启autoType
    		ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        }
		
	}
	
	
	private void initFlowRules(){
    	List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("DetailAccountUnivServiceImpl");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        /*
         * CONTROL_BEHAVIOR_RATE_LIMITER means requests more than threshold will be queueing in the queue,
         * until the queueing time is more than {@link FlowRule#maxQueueingTimeMs}, the requests will be rejected.
         */
        rule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER); // 流控效果：匀速排队模式
        // Set limit QPS to 20.
        rule.setCount(5);
        rule.setMaxQueueingTimeMs(10*1000); // 最长排队等待时间
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

}
