package cn.tx.activemq;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduce {

    public static final String ACTIVEMQ_URL="tcp://10.199.202.71:61608";
    //故障迁移
    //public static final String ACTIVEMQ_URL="failover:(tcp://192.168.160.137:61616,tcp://192.168.160.137:61617,tcp://192.168.160.137:61618)?randomize=false";
    public static final String QUEUE_NAME="queue01";

    public static void main(String[] args) throws JMSException {

        //1.创建连接工厂,按照给定的url地址,采用默认用户名密码
        ActiveMQConnectionFactory factory  =new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过工厂 获取connection 并启动
        Connection connection = factory.createConnection();
        connection.start();
        //3.创建会话session
        //参数1  事务  参数2 签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4创建目的地（具体是队列queue还是主题topic）

        //Destination destination = session.createQueue(QUEUE_NAME);
        Queue queue = session.createQueue(QUEUE_NAME);//队列

        //5.创建消息的生产者
        MessageProducer producer = session.createProducer(queue);
        //producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);//非持久
        //producer.setDeliveryMode(DeliveryMode.PERSISTENT);//持久
        //6使用消息生产者 ,生产三条消息发送到mq的队列里
        for (int i = 1; i <= 3; i++) {
            //7 创建消息
            TextMessage textMessage = session.createTextMessage("testmessage" + i);//理解为一个字符串
           // textMessage.setStringProperty("vp", "vip");
            //通过生产者 发送给mq
            producer.send(textMessage);
/*
            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("v1", "testmessage---v1");
            producer.send(mapMessage);*/

        }

        //关闭资源
        producer.close();
        session.close();
        connection.close();

        System.out.println("消息发送到mq完成");

    }
}