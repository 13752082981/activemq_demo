package cn.tx.boot.activemq;

import cn.tx.boot.activemq.produce.Queue_Produce;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootMqProduceApplicationTests {

    @Resource
    private Queue_Produce queue_produce;

    @Test
    public void testSend() {

        queue_produce.produceMessage();
    }

}
