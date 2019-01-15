package com.lz.snappy.demo.rocketmq;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.lz.snappy.demo.model.UserContent;

@Service
public class UserProducer {

	/**
	 * 生产者的组名
	 */
	@Value("${apache.rocketmq.producer.producerGroup}")
	private String producerGroup;

	/**
	 * nameServer地址
	 */
	@Value("${apache.rocketmq.namesrvaddr}")
	private String namesrvaddr;

	/**
	 * @PostConstruct
	 * 此注解相当于servlet的init方法，构造器执行完后，调用此方法
	 * @throws RemotingException
	 * @throws MQBrokerException
	 * @throws InterruptedException
	 */
	@PostConstruct
	public void procuder() {
		DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
		producer.setNamesrvAddr(namesrvaddr);
		System.out.println(namesrvaddr);
		System.out.println(producerGroup);
		try {
			producer.start();
			for (int i = 0; i < 5; i++) {
				UserContent model = UserContent.builder().userName(String.valueOf(i)).pwd("abc" + i)
						.build();
				String jsonStr = JSON.toJSONString(model);
				System.out.println("发送消息：" + jsonStr);
				Message message = new Message("user-topic", "user-tag", jsonStr.getBytes());
				SendResult result = producer.send(message,new MessageQueueSelector() {
					
					@Override
					public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
						 Integer id = (Integer) arg;
					        int index = id % mqs.size();
					        return mqs.get(index);
					}
				},1);
				System.out.println(
						"发送响应，msgId为" + result.getMsgId() + "  发送状态为" + result.getSendStatus());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.shutdown();
		}

	}

}
