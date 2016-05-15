package com.shinemo.mpns.core.mpush.domain;

import com.google.common.collect.Lists;
import com.mpush.push.ConnectClient;
import com.mpush.push.zk.manager.ConnectZKNodeManager;
import com.mpush.zk.ZKServerNode;

import java.util.List;

public class ConnectionClientMain extends ConnectClient {

    private ConnectZKNodeManager zkNodeManager = new ConnectZKNodeManager();

    public List<ZKServerNode> getApplicationList() {
        return Lists.newArrayList(zkNodeManager.getList());
    }

}
