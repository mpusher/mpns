package com.shinemo.mpns.core.mpush.service.impl;

import com.google.common.collect.Maps;
import com.mpush.common.security.CipherBox;
import com.mpush.conn.client.ClientChannelHandler;
import com.mpush.netty.client.NettyClientFactory;
import com.mpush.netty.client.SecurityNettyClient;
import com.mpush.zk.ZKServerNode;
import com.shinemo.mpns.client.common.GsonUtil;
import com.shinemo.mpns.core.mpush.domain.ConnectionClientMain;
import com.shinemo.mpns.core.mpush.service.ConnectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service("connService")
public class ConnectionServiceImpl implements ConnectionService {

    private static final ConnectionClientMain clientMain = new ConnectionClientMain();

    private static final Logger log = LoggerFactory.getLogger(ConnectionServiceImpl.class);

    private Map<String, SecurityNettyClient> userId2Conn = Maps.newHashMap();

    static {
        clientMain.start();
        log.info("connection client init success!");
    }

    @Override
    public void connect(Collection<String> userIds) throws Exception {
        List<ZKServerNode> serverList = clientMain.getApplicationList();
        if (serverList.size() <= 0) {
            log.error("connection application is null:" + GsonUtil.toJson(userIds));
            return;
        }
        Iterator<String> iterator = userIds.iterator();
        while (iterator.hasNext()) {
            String userId = iterator.next();
            int index = (int) ((Math.random() % serverList.size()) * serverList.size());
            ZKServerNode server = serverList.get(index);
            String clientVersion = "1.0." + userId;
            String osName = "android";
            String osVersion = "1.0.1";
            String deviceId = "test-device-id-" + userId;
            String cipher = "";
            byte[] clientKey = CipherBox.INSTANCE.randomAESKey();
            byte[] iv = CipherBox.INSTANCE.randomAESIV();

            SecurityNettyClient client = new SecurityNettyClient(server.getIp(), server.getPort());
            client.setClientKey(clientKey);
            client.setIv(iv);
            client.setClientVersion(clientVersion);
            client.setDeviceId(deviceId);
            client.setOsName(osName);
            client.setOsVersion(osVersion);
            client.setUserId(userId);
            client.setCipher(cipher);

            ClientChannelHandler handler = new ClientChannelHandler(client);
            NettyClientFactory.INSTANCE.create(handler);
            userId2Conn.put(userId, client);
            Thread.sleep(10);
        }

    }

    @Override
    public ConnectionClientMain getConnectionClient() {
        return clientMain;
    }

    @Override
    public void close(String userId) {
        SecurityNettyClient client = userId2Conn.get(userId);
        if (client != null) {
            client.close("close");
        }
    }

}
