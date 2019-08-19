package com.example.springboot.mq;

import java.io.UnsupportedEncodingException;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.util.StopWatch;

public class MqService {
	public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, MQBrokerException, InterruptedException {
		// 生产者的组名
		DefaultMQProducer producer = new DefaultMQProducer("PushConsumer");
		// 指定NameServer地址，多个地址以 ; 隔开
		producer.setNamesrvAddr("127.0.0.1:9876");

		/**
		 * Producer对象在使用之前必须要调用start初始化，初始化一次即可 注意：切记不可以在每次发送消息时，都调用start方法
		 */
		producer.start();

		// 创建一个消息实例，包含 topic、tag 和 消息体
		// 如下：topic 为 "TopicTest"，tag 为 "push"
		Message message = new Message("TopicTest", "push","发送消息----zhisheng-----".getBytes(RemotingHelper.DEFAULT_CHARSET));

		// StopWatch stop = new StopWatch();
		// stop.start();

		SendResult result = producer.send(message);
		System.out.println("发送响应：MsgId:" + result.getMsgId() + "，发送状态:" + result.getSendStatus());

	}
}
