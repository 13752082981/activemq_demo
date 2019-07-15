package cn.tx.activemq.tx;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduce_TX {

    public static final String ACTIVEMQ_URL="tcp://10.199.202.71:61616";

    public static final String QUEUE_NAME="queue-tx";

    public static void main(String[] args) throws JMSException {

        //1.创建连接工厂,按照给定的url地址,采用默认用户名密码
        ActiveMQConnectionFactory factory  =new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过工厂 获取connection 并启动
        Connection connection = factory.createConnection();
        connection.start();
        //3.创建会话session
        //参数1  事务  参数2 签收
        //如果事物设置为true  需要在seseion 关闭之前commit 这时才把消息加入到队列中
        //事物签收
       Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);// 需要commit

        //非事物签收
      //  Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //4创建目的地（具体是队列queue还是主题topic）

        Queue queue = session.createQueue(QUEUE_NAME);//队列

        //5.创建消息的生产者
        MessageProducer producer = session.createProducer(queue);

        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        //6使用消息生产者 ,生产三条消息发送到mq的队列里
        for (int i = 1; i <= 3; i++) {
            //7 创建消息
            TextMessage textMessage = session.createTextMessage("message-tx" + i);//理解为一个字符串
            //通过生产者 发送给mq
            producer.send(textMessage);

        }
        //关闭资源
        producer.close();
        session.commit();
        session.close();
        connection.close();

        System.out.println("消息发送到queue-tx完成");

       /* try {

        }catch (Exception e){
            session.rollback();
        }finally {

        }*/
    }
}
