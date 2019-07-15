package cn.tx.activemq.spring;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;


import javax.jms.TextMessage;

@Service
public class SpringMQ_Produce {

    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        SpringMQ_Produce produce = (SpringMQ_Produce) ctx.getBean("springMQ_Produce");

      /*  produce.jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("***spring整合activemq**demo11**");
                return textMessage;
            }
        });
*/
        //lambda 表达式  接口里只要一个方法 如果方法里只有一个参数  数据类型也可以不用谢
        //使用xml 里面的默认目的地
        produce.jmsTemplate.send((session)->{
            TextMessage textMessage = session.createTextMessage("***spring整合activemq**demo11**");
            return textMessage;
        });


        System.out.println("**********spring send task over");

    }
}


