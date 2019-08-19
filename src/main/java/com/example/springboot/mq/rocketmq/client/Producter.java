package com.example.springboot.mq.rocketmq.client;


import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;



/**
 * 
* @author 作者 zhangsiming: 
* @version 创建时间：2018年11月1日 上午11:09:51 
* 类说明 mq 生产者
 */
public class Producter {
	public static void main(String[] args) throws MQClientException,  InterruptedException {
		//指定groupNanme具有唯一性
		DefaultMQProducer producer = new DefaultMQProducer("PushConsumer");
		//设置nameServer
		producer.setNamesrvAddr("127.0.0.1:9876");
		producer.start();
		for (int i = 0; i < 100; i++) {
			try {
				Message message = new Message();
				//设置Topic
				message.setTags("TopicTest");
				//设置Tag
				message.setTags("TagA");
				message.setBody(("Hello RocketMQ" +i).getBytes("UTF-8"));
				SendResult sendResult = producer.send(message);
				System.out.println(sendResult);
			} catch (Exception e) {
				e.printStackTrace();
				Thread.sleep(1000);
			}
		}
		//断开连接释放资源
		producer.shutdown();
	}

}
