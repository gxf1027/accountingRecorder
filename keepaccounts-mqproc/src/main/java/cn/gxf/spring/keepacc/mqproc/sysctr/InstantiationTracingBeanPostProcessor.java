package cn.gxf.spring.keepacc.mqproc.sysctr;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.parser.ParserConfig;



@Service
public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {
	
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		//判断spring容器是否加载完成
        if (event.getApplicationContext().getParent() == null) {
        	
    		// fastjson开启autoType
    		ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        }
		
	}

}
