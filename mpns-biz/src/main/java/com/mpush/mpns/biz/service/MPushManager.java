/*
 * (C) Copyright 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     ohun@live.cn (夜色)
 */

package com.mpush.mpns.biz.service;

import com.mpush.api.srd.ServiceDiscovery;
import com.mpush.api.srd.ServiceNames;
import com.mpush.api.srd.ServiceNode;
import com.mpush.client.MPushClient;
import com.mpush.common.router.RemoteRouterManager;
import com.mpush.common.user.UserManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;

/**
 * Created by ohun on 16/9/20.
 *
 * @author ohun@live.cn (夜色)
 */
@Service
public class MPushManager {

    @Resource
    private ServiceDiscovery serviceDiscovery;

    private UserManager userManager;

    @Resource
    private MPushClient mPushClient;

    @PostConstruct
    public void init() {
        userManager = new UserManager(mPushClient.getCachedRemoteRouterManager());
    }

    public Collection<ServiceNode> getConnectServerList() {
        return serviceDiscovery.lookup(ServiceNames.CONN_SERVER);
    }

    public long getOnlineUserNum(String serverIp) {
        return userManager.getOnlineUserNum(serverIp);
    }

    public void kickUser(String userId) {
        userManager.kickUser(userId);
    }
}
