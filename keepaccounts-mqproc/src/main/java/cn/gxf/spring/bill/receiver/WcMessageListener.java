package cn.gxf.spring.bill.receiver;

import java.util.concurrent.Callable;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.rabbitmq.client.Channel;

import cn.gxf.spring.bill.mailsender.EmailSender;


public class WcMessageListener implements ChannelAwareMessageListener{

	
	private ThreadPoolTaskExecutor taskExecutor;
	private SimpleMessageConverter simpleMsgConvt;
	private EmailSender sender; // 注入时根据业务不同注入不同的sender
	
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		
		// 反序列化
		Object objMsg = simpleMsgConvt.fromMessage(message);
		// 异步发送email
		//taskExecutor.execute(new SendingEmailProcess(sender, objMsg));
		
		ListenableFuture<String> future = taskExecutor.submitListenable(new Callable<String>() {

			@Override
			public String call() throws Exception {
				
				int res = sender.send(objMsg);
				return res==0 ? "RETURN_WITHOUT_SENDING" : "RETURN_WITH_SENDING";
			}
		});
		
		future.addCallback(new ListenableFutureCallback<String>() {

			@Override
			public void onSuccess(String result) {
				System.out.println("Callback onSuccess: " + result);
				
			}

			@Override
			public void onFailure(Throwable ex) {
				System.out.println("Callback onFailure: " + ex.getMessage());
			}
		});
	}
	
	
	public void setSimpleMsgConvt(SimpleMessageConverter simpleMsgConvt) {
		this.simpleMsgConvt = simpleMsgConvt;
	}
	
	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void setSender(EmailSender sender) {
		this.sender = sender;
	}

}
