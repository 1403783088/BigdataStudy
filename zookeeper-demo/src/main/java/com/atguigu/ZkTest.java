package com.atguigu;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ZkTest {
    private static String connectString = "hadoop102:2181,hadoop103:2181,hadoop104:2181";
    private static int sessionTimeout = 2000;
    private ZooKeeper zkClient = null;

    @Before
    public void init() throws IOException {
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                //收到事件通知后的回调函数（用户的业务逻辑）
                System.out.println(watchedEvent.getType() + "--" + watchedEvent.getPath());

                //再次启动监听
                try {
                    zkClient.getChildren("/", true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    //创建子节点
    @Test
    public void create() throws KeeperException, InterruptedException {
        // 参数1：要创建的节点路径；
        // 参数2：节点数据；
        // 参数3：节点权限；
        // 参数4：节点的类型
        String nodeCreated = zkClient.create("/atguig", "jinlian".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(nodeCreated);
    }

    //删除子节点
    @Test
    public void delete() throws KeeperException, InterruptedException {
        zkClient.delete("/atguig", 0);
    }

    //获取子节点
    @Test
    public void getChildren() throws KeeperException, InterruptedException {
        List<String> children = zkClient.getChildren("/", true);
        for (String child : children) {
            System.out.println(child);
        }
        //延时阻塞
        Thread.sleep(Long.MAX_VALUE);
    }

    //判端znode是否存在
    @Test
    public void exist() throws KeeperException, InterruptedException {
        Stat stat = zkClient.exists("/atguigu", false);
        System.out.println(stat == null ? "not exist" : "exist");
    }
}
