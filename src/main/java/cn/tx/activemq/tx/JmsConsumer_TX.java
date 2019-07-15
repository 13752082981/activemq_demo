package cn.tx.activemq.tx;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer_TX {

    public static final String ACTIVEMQ_URL="tcp://10.199.202.71:61616";

    public static final String QUEUS_NAME="queue-tx";

    public static void main(String[] args) throws JMSException, IOException {

        //1.创建连接工厂,按照给定的url地址,采用默认用户名密码
        ActiveMQConnectionFactory factory  =new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过工厂 获取connection 并启动
        Connection connection = factory.createConnection();
        connection.start();
        //3.创建会话session
        //参数1  事务  参数2 签收

        //事物签收
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);//需要commit
        //事物 手动签收 执行commit了不需要textMessage.acknowledge();  没有执行commit 使用textMessage.acknowledge(); 也没有作用
      //  Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);//需要commit




        //当签收=SESSION_TRANSACTED 时 就没有效果了，遵循事物
        //非事物签收 手动签收 需要 textMessage.acknowledge();
        //Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        //非事物签收 DUPS_OK_ACKNOWLEDGE带副本允许重复的签收
        //Session session = connection.createSession(false, Session.DUPS_OK_ACKNOWLEDGE);

        //4创建目的地（具体是队列queue还是主题topic）

        Queue queue = session.createQueue(QUEUS_NAME);//主题

        //5.创建消息的消费者
        MessageConsumer consumer = session.createConsumer(queue);

        while (true){
            //TextMessage textMessage = (TextMessage) consumer.receive();//一直等待
            TextMessage textMessage = (TextMessage) consumer.receive(4000l);//4秒后退出

            if(textMessage!=null){
                System.out.println("********消费者接收的消息:"+textMessage.getText());
                //非事物手动签收 开启事物了 commit 就不要这个了 没有commit 执行这个也没有作用
                //  textMessage.acknowledge();

            }else{
                break;
            }
        }
        //关闭资源
        consumer.close();
        session.commit();
        session.close();
        connection.close();

        System.out.println("消息queue-tx接收完成");

    }
}