package cn.tx.activemq.persist;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer_Topic_Persist {

    public static final String ACTIVEMQ_URL="tcp://10.199.202.71:61616";

    public static final String TOPIC_NAME="topic-persist";

    public static void main(String[] args) throws JMSException, IOException {

        //1.创建连接工厂,按照给定的url地址,采用默认用户名密码
        ActiveMQConnectionFactory factory  =new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过工厂 获取connection 并启动
        Connection connection = factory.createConnection();
        connection.setClientID("zs");
        //3.创建会话session
        //参数1  事务  参数2 签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4创建目的地（具体是队列queue还是主题topic）

        Topic topic = session.createTopic(TOPIC_NAME);//主题

        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic,"beizhu...");

        connection.start();

        Message message = topicSubscriber.receive();

        while (null!=message){
            TextMessage textMessage = (TextMessage) message;
            System.out.println("************持久化的topic-persist"+textMessage.getText());

            message = topicSubscriber.receive(1000l);
        }


        //关闭资源
        session.close();
        connection.close();

        System.out.println("消息接收topic-persist完成");

    }
}