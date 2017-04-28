package org.easyweb.server;

import org.apache.zookeeper.*;
import org.apache.zookeeper.KeeperException.ConnectionLossException;
import org.apache.zookeeper.KeeperException.SessionExpiredException;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.Callable;

//this is zookeeper register

// /service/[rest-component] -> [server-info]


public class ZookeeperRegistry implements Watcher {


    private final String addr;

    private volatile boolean connected;


    public ZookeeperRegistry() {
        this("localhost");
    }

    public ZookeeperRegistry(String addr) {
        this.addr = addr;
    }

    private ZooKeeper zkClient; //the zookeeper client

    private List<ACL> acl = ZooDefs.Ids.OPEN_ACL_UNSAFE;

    public void startZK() throws Exception {
        zkClient = new ZooKeeper(this.addr, 3000, this);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {

        System.out.println("Processing event: " + watchedEvent.toString());

        System.out.println(Thread.currentThread());

        if (watchedEvent.getType() == Watcher.Event.EventType.None) {
            switch (watchedEvent.getState()) {
                case Disconnected:
                    connected = false;
                    break;
                case SyncConnected:
                    connected = true;
                    break;
            }
        }
    }

    public void stopZK() throws Exception {
        if (null != zkClient)
            zkClient.close();
    }


    private synchronized <T> T retryOperation(Callable<T> callable) throws KeeperException, InterruptedException {

        KeeperException keeperException = null;

        for (int i = 0; i < 5; i++) {
            try {
                return callable.call();
            } catch (SessionExpiredException e) {

                System.out.println("Session expired for: so reconnecting due to: " + e);

                //we just re
                throw e;
            } catch (ConnectionLossException e) {
                if (null == keeperException) {
                    keeperException = e;
                }
                //delay it ..
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignore) {
                }
            } catch (Exception e) {
                System.out.println("other exception " + e);
                throw new RuntimeException(e); //wrap it ...
            }
        }
        throw keeperException;
    }


    //ensureExists it ...
    private void ensureExists(final String path, final byte[] data, final List<ACL> aclList, final CreateMode flag) {
        try {
            retryOperation(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    Stat stat = zkClient.exists(path, false);
                    if (null != stat) return true;
                    zkClient.create(path, data, aclList, flag);
                    return true;
                }
            });
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] queryHost(final String identifier) {

        try {
            return retryOperation(new Callable<byte[]>() {
                @Override
                public byte[] call() throws Exception {
                    //just get data
                    return zkClient.getData(identifier, null, null);
                }
            });
        } catch (Exception ignore) {
            //just ignore it ...
            ignore.printStackTrace();
        }

        return new byte[0]; //return a one
    }

    public boolean registerService(final String rest, final String data) {

        ensureExists("/service", null, acl, CreateMode.PERSISTENT);

        try {
            return retryOperation(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    System.out.println(rest);

                    Stat stat = zkClient.exists(rest, false);

                    if (null != stat) {
                        System.out.println("another node exists");
                        return false; //we find other node
                    } else {
                        zkClient.create(rest, data.getBytes(), acl, CreateMode.EPHEMERAL);
                        return true;
                    }
                }
            });
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }

        return false;
    }

    public boolean connected() {
        return this.connected;
    }

    public static void main(String... args) throws Exception {

        ZookeeperRegistry registry = new ZookeeperRegistry("localhost");

        registry.startZK();

        boolean r = registry.registerService("/service/userAPI", "localhost");

        System.out.println("registry result " + r);

        byte[] data1 = registry.queryHost("/service/userAPI");

        System.out.println(new String(data1));

        Thread.sleep(1 * 60 * 1000);

        registry.stopZK();
    }
}
