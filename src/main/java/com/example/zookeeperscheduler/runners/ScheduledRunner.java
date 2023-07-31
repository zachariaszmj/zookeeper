package com.example.zookeeperscheduler.runners;


import com.example.zookeeperscheduler.lock.DistributedLock;
import com.example.zookeeperscheduler.lock.DistributedLockFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class ScheduledRunner {

    private final DistributedLockFactory distributedLockFactory;

    @Scheduled(initialDelay = 2000, fixedDelay = Long.MAX_VALUE)
    public void firstRunner() {
        log.info("firstRunner is triggered");
        Thread one = new Thread(() -> {
            try {
                DistributedLock local = distributedLockFactory.get();
                local.lock();

                for (int i = 0; i < 10; i++) {
                    log.info("instance firstRunner is processing");
                    Thread.sleep(1000);
                }
                local.unlock();
            } catch (InterruptedException | IOException v) {
                System.out.println(v);
            }
        });
        one.start();
    }

    @Scheduled(initialDelay = 2100, fixedDelay = Long.MAX_VALUE)
    public void secondRunner() {
        log.info("secondRunner is triggered");
        Thread one = new Thread(() -> {
            try {
                DistributedLock local = distributedLockFactory.get();
                local.lock();

                for (int i = 0; i < 10; i++) {
                    log.info("instance secondRunner is processing");
                    Thread.sleep(1000);
                }
                local.unlock();
            } catch (InterruptedException | IOException v) {
                System.out.println(v);
            }
        });
        one.start();
    }

    @Scheduled(initialDelay = 2100, fixedDelay = Long.MAX_VALUE)
    public void thirdRunner() {
        log.info("thirdRunner is triggered");
        Thread one = new Thread(() -> {
            try {
                DistributedLock local = distributedLockFactory.get();
                local.lock();

                for (int i = 0; i < 10; i++) {
                    log.info("instance thirdRunner is processing");
                    Thread.sleep(1000);
                }
                local.unlock();
            } catch (InterruptedException | IOException v) {
                System.out.println(v);
            }
        });
        one.start();
    }


}
