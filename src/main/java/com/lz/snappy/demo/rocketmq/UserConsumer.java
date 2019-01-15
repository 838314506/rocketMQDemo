package com.lz.snappy.demo.rocketmq;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

@Service
public class UserConsumer {
	
	@Value("${apache.rocketmq.consumer.PushConsumer}")
	private String consumerGroup;
	
	@Value("${apache.rocketmq.namesrvaddr}")
	private String namesrvaddr;
	
	
	@PostConstruct
	public void consumer() {
		System.out.println("init defaultMQConsumer");
		
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
		
		consumer.setNamesrvAddr(namesrvaddr);
		
		try {
			consumer.subscribe("user-topic", "user-tag");
			consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
			consumer.registerMessageListener((MessageListenerConcurrently)(list,context)->{
				try {
					for (MessageExt messageExt : list) {
						System.out.println("消费消息："+new String(messageExt.getBody()));
					}
				} catch (Exception e) {
					e.printStackTrace();
					return ConsumeConcurrentlyStatus.RECONSUME_LATER;//稍后再试
				}
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;//消费成功
			});
			consumer.start();
		} catch (MQClientException e) {
			e.printStackTrace();
		}
		
	}

}
