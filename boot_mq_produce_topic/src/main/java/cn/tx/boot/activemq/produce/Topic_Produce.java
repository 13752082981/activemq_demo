package cn.tx.boot.activemq.produce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Topic;
import java.util.UUID;

@Component
public class Topic_Produce {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Topic topic;


    public void produceMessage(){
        jmsMessagingTemplate.convertAndSend(topic,"********:boot-queue"+ UUID.randomUUID().toString().substring(0,6));
    }

    //定时执行 3秒执行一次
    @Scheduled(fixedDelay = 3000)
    public void prodeceMessageScheduled(){
        jmsMessagingTemplate.convertAndSend(topic,"********:boot-queue-Scheduled"+ UUID.randomUUID().toString().substring(0,6));
        System.out.println("****定时发送Scheduled****");
    }
}
