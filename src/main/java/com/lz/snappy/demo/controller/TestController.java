package com.lz.snappy.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lz.snappy.demo.rocketmq.UserConsumer;
import com.lz.snappy.demo.rocketmq.UserProducer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api("这是一个控制器用来测试rocketmq")
public class TestController {
	
	@Autowired
	private UserProducer producer;
	
	@Autowired
	private UserConsumer consumer;
	
	@GetMapping("/demoProducer")
	@ApiOperation("此接口方法用来测试mq的生产者是否能够生产消息")
	public String testmq() {
		producer.procuder();
		return "生产成功";
	}
	
	@GetMapping("/demoConsumer")
	@ApiOperation("此接口谅零月租来测试mq的消费者是否能够消费消息")
	public String testConsumer() {
		consumer.consumer();
		return "消费成功";
	}

}
