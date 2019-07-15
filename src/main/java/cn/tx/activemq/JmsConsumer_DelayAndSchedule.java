package cn.tx.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer_DelayAndSchedule {


    public static final String ACTIVEMQ_URL="tcp://192.168.160.137:61616";

    public static final String QUEUE_NAME="queue-delay";

    public static void main(String[] args) throws JMSException, IOException {

        ActiveMQConnectionFactory factory  =new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);//

        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if(null != message && message instanceof TextMessage){//
                    //生产者创建的消息是TextMessage 接收也要是这样的
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("********消费者接收的topic消息:"+textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        System.in.read();

        consumer.close();
        session.close();
        connection.close();

        System.out.println("消息接收完成");

    }
}