package com.example.zookeeperscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ZookeeperSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZookeeperSchedulerApplication.class, args);
    }

}
