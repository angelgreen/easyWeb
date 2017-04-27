package org.easyweb

import org.apache.zookeeper.AsyncCallback
import org.apache.zookeeper.AsyncCallback.DataCallback
import org.apache.zookeeper.AsyncCallback.StringCallback
import org.apache.zookeeper.CreateMode
import org.apache.zookeeper.KeeperException
import org.apache.zookeeper.KeeperException.SessionExpiredException
import org.apache.zookeeper.WatchedEvent
import org.apache.zookeeper.Watcher
import org.apache.zookeeper.ZooDefs
import org.apache.zookeeper.ZooKeeper
import org.apache.zookeeper.data.Stat;

//this is zookeeper register

// /service/[rest-component] -> [server-info]


public class ZookeeperRegister implements Watcher {


    private final String addr;

    private volatile boolean connected;

    public ZookeeperRegister(String addr) {
        this.addr = addr;
    }

    private ZooKeeper zkClient; //the zookeeper client

    public void startZK() {
        zkClient = new ZooKeeper(this.addr, 3000, this);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {

        System.out.println("Processing event: " + watchedEvent.toString());

        if (watchedEvent.type == Watcher.Event.EventType.None) {
            switch (watchedEvent.getState()) {
                case Watcher.Event.KeeperState.Disconnected:
                    connected = false;
                    break;
                case Watcher.Event.KeeperState.SyncConnected:
                    connected = true;
                    break;
            }
        }
    }

    public void stopZK() throws Exception {
        if (null != zkClient)
            zkClient.close()
    }


    private class RegisterServiceStringCallback implements StringCallback {

        private final String path;
        private final byte[] data;

        public RegisterServiceStringCallback(String path, byte[] data) {
            this.path = path;
            this.data = data;
        }

        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (rc) {
                case KeeperException.Code.ConnectionLoss:
                    //checkService
                    checkService(this.path, this.data);
                    break;

                case KeeperException.Code.NodeExists:
                    //existsService
                    existsService(this.path, this.data);
                    break;

                case KeeperException.Code.Ok:

                    System.out.println("register ok ");
                    //ok
                    break;

                default:
                    System.out.println("the error log");
                    System.out.println(path);
                    System.out.println(rc == KeeperException.Code.BadArguments)
                    System.out.println("error checkService ");
                    break;
            }
        }
    }

    private class CheckServiceDataCallback implements DataCallback {
        private final String path;
        private final byte[] data;

        public CheckServiceDataCallback(String path, byte[] data) {
            this.path = path;
            this.data = data;
        }

        @Override
        public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {

            switch (rc) {

                case KeeperException.Code.Ok:
                    //we find it ...
                    if (Arrays.equals(this.data, data)) {
                        System.out.println("we find it");
                    } else {
                        existsService(this.path, this.data);
                    }
                    break;
                case KeeperException.Code.ConnectionLoss:
                    //try to check it..
                    checkService(this.path, this.data);
                    break;

                case KeeperException.Code.NoNode:
                    registerService(this.path, this.data);
                    break;

                default:
                    System.out.println(path);
                    System.out.println(rc == KeeperException.Code.BadArguments)
                    System.out.println("error checkService ");
                    break;
            }
        }
    }

    private class ExistsServiceStatCallback implements AsyncCallback.StatCallback {
        private final String path;
        private final byte[] data;

        public ExistsServiceStatCallback(String path, byte[] data) {
            this.path = path;
            this.data = data;
        }

        @Override
        public void processResult(int rc, String path, Object ctx, Stat stat) {
            switch (rc) {
                case KeeperException.Code.ConnectionLoss:
                    existsService(this.path, this.data);
                    break;
                case KeeperException.Code.Ok:
                    //another exists
                    break;
                case KeeperException.Code.NoNode:
                    //
                    registerService(this.path, this.data);
                    break;
                default:
                    checkService(this.path, this.data);
                    break;
            }
        }
    }

    private class ExistsServiceWatcher implements Watcher {
        private final String path;
        private final byte[] data;

        public ExistsServiceWatcher(String path, byte[] data) {
            this.path = path;
            this.data = data;
        }

        @Override
        public void process(WatchedEvent event) {
            if (event.type == Watcher.Event.EventType.NodeDeleted) {
                //delete it .. notify
                registerService(this.path, this.data);
            }
        }
    }


    private void existsService(final String path, final byte[] data) {
        zkClient.exists(path,
                new ExistsServiceWatcher(path, data),
                new ExistsServiceStatCallback(path, data),
                null);
    }

    private void checkService(final String path, final byte[] data) {
        zkClient.getData(path,
                null,
                new CheckServiceDataCallback(path, data),
                null);
    }

    //register it ..
    public void registerService(final String path, final byte[] data) {

        zkClient.create(path, data,
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL,
                new RegisterServiceStringCallback(path, data),
                null);
    }


    public byte[] queryAPI(String path) throws KeeperException, InterruptedException{

        KeeperException ex = null;

        for(int i = 0; i < 5; i++) {
            try {
                return zkClient.getData(path, null, null);
            }catch (SessionExpiredException e1) {
                System.out.println("Session expired for: " + zkClient + " so reconnecting due to: " + e1);
                throw e1;
            } catch (KeeperException.ConnectionLossException e2) {
                if(ex == null) {
                    ex = e2;
                }
                try{
                    Thread.sleep(2*1000);
                }catch (InterruptedException ignore) {
                    //ignore it ...
                }
            }
        }

        throw ex;
    }

    public boolean connected() {
        return this.connected;
    }

    public static void main(String... args) throws Exception {
        ZookeeperRegister registry = new ZookeeperRegister("localhost");

        registry.startZK();

        while (!registry.connected()) {
            Thread.sleep(100);
        }

        registry.registerService("/userAPI", "localhost".getBytes());


        byte[] data1 = registry.queryAPI("/userAPI");

        println "the data1 is " + new String(data1)

        Thread.sleep(1 * 60 * 1000);

        registry.stopZK();
    }
}
