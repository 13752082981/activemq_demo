package cn.tx.activemq.spring;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Component
public class MyMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        if(null!=message && message instanceof TextMessage){
            TextMessage textMestesage = (TextMessage) message;
            try {
                System.out.println("监听器--------"+textMestesage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
