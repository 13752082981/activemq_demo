package cn.tx.boot.activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BootMqProduceTopicApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootMqProduceTopicApplication.class, args);
    }

}
