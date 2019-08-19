package com.example.springboot.mq.rocketmq.server;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.util.Date;
import java.util.List;

/**
 * 
* @author 作者 zhangsiming: 
* @version 创建时间：2018年11月5日 上午9:29:51 
* 类说明 mq 消费者
 */
public class Consumer {
	public static void main(String[] args) throws MQClientException {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("PushConsumer");
		consumer.setNamesrvAddr("127.0.0.1:9876");
		//指定历史记录开始消费
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		consumer.subscribe("TopicTest","*");
		//默认消费mqgs一条消息，通过此方法设置批量
//		consumer.setConsumeMessageBatchMaxSize(10);
		consumer.registerMessageListener(new MessageListenerConcurrently() {

			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				for (MessageExt ext: msgs) {
					try {
						System.out.println(new Date()+new String(ext.getBody(),"UTF-8"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});
		//Consumer对象只是用前必须要调用start初始化，初始化一次即可
		consumer.start();
		System.err.println("Consumer Started");
	}

}
