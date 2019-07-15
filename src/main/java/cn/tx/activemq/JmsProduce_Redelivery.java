package cn.tx.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 生产者不变 消费者开启事物但是没有commit
 */
public class JmsProduce_Redelivery {

    public static final String ACTIVEMQ_URL="tcp://192.168.160.137:61616";

    public static final String QUEUE_NAME="queue-redelivery";

    public static void main(String[] args) throws JMSException {

        ActiveMQConnectionFactory factory  =new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);

        MessageProducer producer = session.createProducer(queue);

        long delay=3*1000;//延时3秒
        long period=4*1000;//4秒执行一次
        int repeat =5;//执行5次


        for (int i = 1; i <= 3; i++) {
            TextMessage textMessage = session.createTextMessage("testmessage" + i);//理解为一个字符串
            producer.send(textMessage);
        }

        producer.close();
        session.close();
        connection.close();
        System.out.println("消息发送到mq完成");

    }
}