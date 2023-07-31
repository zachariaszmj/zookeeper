package com.example.zookeeperscheduler.lock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

public class DistributedLockFactory {
    private final ZooKeeper zk;
    private final String lockBasePath;
    private final String lockName;

    public DistributedLockFactory(ZooKeeper zk, String lockBasePath, String lockName) {
        this.zk = zk;
        this.lockBasePath = lockBasePath;
        this.lockName = lockName;
        try {
            if (zk.exists(lockBasePath, false) == null) {
                zk.create(lockBasePath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public DistributedLock get() {
        return new DistributedLock(
                zk,
                lockBasePath,
                lockName
        );
    }
}