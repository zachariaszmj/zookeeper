package com.example.zookeeperscheduler.config;

import com.example.zookeeperscheduler.lock.DistributedLock;
import com.example.zookeeperscheduler.lock.DistributedLockFactory;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Configuration
public class ApplicationConfiguration {

    private static final CountDownLatch connectionLatch = new CountDownLatch(1);

    @Bean
    public DistributedLockFactory distributedLock(ZooKeeper zooKeeper) {
        String lockPath = "/LOCK_PATH";
        String lockName = "DISTRIBUTED_LOCK";
        return new DistributedLockFactory(
                zooKeeper,
                lockPath,
                lockName
        );
    }

    @Bean
    public ZooKeeper connect()
            throws IOException,
            InterruptedException {
        ZooKeeper zoo = new ZooKeeper("localhost:2181", 20000, we -> {
            if (we.getState() == Watcher.Event.KeeperState.SyncConnected) {
                connectionLatch.countDown();
            }
        });

        connectionLatch.await();
        return zoo;
    }
}
