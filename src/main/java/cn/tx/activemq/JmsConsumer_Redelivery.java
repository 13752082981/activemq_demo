package cn.tx.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;

import javax.jms.*;
import java.io.IOException;

/**
 * 生产者不变 消费者开启事物但是没有commit
 */
public class JmsConsumer_Redelivery {


    public static final String ACTIVEMQ_URL="tcp://192.168.160.137:61616";

    public static final String QUEUE_NAME="queue-redelivery";

    public static void main(String[] args) throws JMSException, IOException {
        //创建工厂
        ActiveMQConnectionFactory factory  =new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //设置重发次数
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setMaximumRedeliveries(3);
        factory.setRedeliveryPolicy(redeliveryPolicy);

        //获取连接
        Connection connection = factory.createConnection();
        connection.start();
        //获取session
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        //创建目的地
        Queue queue = session.createQueue(QUEUE_NAME);//
        //消费者
        MessageConsumer consumer = session.createConsumer(queue);
        while (true){
            TextMessage textMessage = (TextMessage) consumer.receive(1000l);//4秒后退出
            if(textMessage!=null){
                System.out.println("********消费者接收的消息:"+textMessage.getText());
            }else{
                break;
            }
        }

        consumer.close();
        session.close();
        connection.close();

        System.out.println("消息接收完成");

    }
}
