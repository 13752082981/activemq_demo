package cn.tx.activemq.broker;

import org.apache.activemq.broker.BrokerService;

public class EmbedBroker {

    public static void main(String[] args) throws Exception {
        //启动一个嵌入式的MQ
        //Activemq也支持在vm中通信 基于嵌入式的broker
        BrokerService brokerService = new BrokerService();

        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
    }
}
