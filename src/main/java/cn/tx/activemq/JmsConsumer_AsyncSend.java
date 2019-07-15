package cn.tx.activemq;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer_AsyncSend {

    public static final String ACTIVEMQ_URL="nio://10.199.202.71:61608";

    public static final String QUEUE_NAME="queue-Async";

    public static void main(String[] args) throws JMSException, IOException {

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

        //5.创建消息的消费者
        MessageConsumer consumer = session.createConsumer(queue);
        //6.第一种方法接收消息
        //同步阻塞方式（receive)
        //订阅者或接收者调用consumer的receive()方法来接收消息,receive方法在能够接收到消息之前（或超时之前）将一直阻塞
     /*   while (true){
            //TextMessage textMessage = (TextMessage) consumer.receive();//一直等待
            TextMessage textMessage = (TextMessage) consumer.receive(4000l);//4秒后退出
            if(textMessage!=null){
                System.out.println("********消费者接收的消息:"+textMessage.getText());
            }else{
                break;
            }
        }*/
        //6第二种方法 通过监听的方式来获取消息
        //异步非阻塞的方式（监听器onMessage）
        //订阅者或接收者,注册一个监听器,当消息到达之后,系统自动调用监听器MessageListener的onMessage
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if(null != message && message instanceof TextMessage){//
                    //生产者创建的消息是TextMessage 接收也要是这样的
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("********消费者接收的消息:"+textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }

               /* if(null != message && message instanceof MapMessage){//
                    //生产者创建的消息是TextMessage 接收也要是这样的
                    MapMessage mapMessage = (MapMessage) message;
                    try {
                        System.out.println("********消费者接收的消息:"+mapMessage.getString("v1"));
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }*/
            }
        });

        System.in.read();//保证控制台不停 如果没有 还没有获取到消息 就关闭了 没有监听到消息就关闭了
        //关闭资源
        consumer.close();
        session.close();
        connection.close();

        System.out.println("消息接收完成");

    }
}