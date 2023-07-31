package com.example.zookeeperscheduler.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class DistributedLock {
    private final ZooKeeper zk;
    private final String lockBasePath;
    private final String lockName;
    private String lockPath;

    public DistributedLock(ZooKeeper zk, String lockBasePath, String lockName) {
        this.zk = zk;
        this.lockBasePath = lockBasePath;
        this.lockName = lockName;
    }

    public void lock() throws IOException {
        try {
            lockPath = zk.create(lockBasePath + "/" + lockName,
                    null,
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL
            );

            final Object lock = new Object();
            synchronized (lock) {
                while (true) {
                    List<String> nodes = zk.getChildren(lockBasePath, event -> {
                       log.info("Received event " + event);
                        synchronized (lock) {
                            lock.notifyAll();
                        }
                    });
                    System.out.println(Arrays.toString(nodes.toArray()));
                    Collections.sort(nodes); // ZooKeeper node names can be sorted lexographically
                    if (lockPath.endsWith(nodes.get(0))) {
                        return;
                    } else {
                        log.info("It is not my turn... Going to wait");
                        lock.wait();
                    }
                }
            }
        } catch (KeeperException e) {
            throw new IOException(e);
        } catch (InterruptedException e) {
            throw new IOException(e);
        }
    }

    public void unlock() throws IOException {
        try {
            zk.delete(lockPath, -1);
            lockPath = null;
        } catch (KeeperException e) {
            throw new IOException(e);
        } catch (InterruptedException e) {
            throw new IOException(e);
        }
    }
}