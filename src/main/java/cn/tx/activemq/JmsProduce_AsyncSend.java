package cn.tx.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

import javax.jms.*;
import java.util.UUID;

/**
 * 异步投递
 */
public class JmsProduce_AsyncSend {

    public static final String ACTIVEMQ_URL="failover:(tcp://192.168.160.137:61618,tcp://192.168.160.137:61617,tcp://192.168.160.137:61616)?randomize=false";

    public static final String QUEUE_NAME="queue-Async";

    public static void main(String[] args) throws JMSException {

        ActiveMQConnectionFactory factory  =new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);

        ActiveMQMessageProducer activeMQMessageProducer = (ActiveMQMessageProducer) session.createProducer(queue);

        activeMQMessageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        TextMessage textMessage=null;
        for (int i = 1; i <= 3; i++) {
            textMessage = session.createTextMessage("testmessage" + i);
            textMessage.setJMSMessageID(UUID.randomUUID().toString()+"---标识");
            String jmsMessageID = textMessage.getJMSMessageID();

            activeMQMessageProducer.send(textMessage, new AsyncCallback() {
                @Override
                public void onSuccess() {
                    System.out.println(jmsMessageID+" is ok");
                }

                @Override
                public void onException(JMSException e) {
                    //失败之后做处理
                    System.out.println(jmsMessageID+" is error");
                }
            });
        }

        activeMQMessageProducer.close();
        session.close();
        connection.close();
        System.out.println("消息发送到mq完成");

    }
}