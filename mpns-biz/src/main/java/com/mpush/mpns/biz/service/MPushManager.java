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

import com.mpush.common.user.UserManager;
import com.mpush.zk.cache.ZKServerNodeCache;
import com.mpush.zk.listener.ZKServerNodeWatcher;
import com.mpush.zk.node.ZKServerNode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * Created by ohun on 16/9/20.
 *
 * @author ohun@live.cn (夜色)
 */
@Service
public class MPushManager {

    @Resource
    private ZKServerNodeWatcher connectZKNodeWatcher;

    public Collection<ZKServerNode> getConnectServerList() {
        return connectZKNodeWatcher.getCache().values();
    }

    public long getOnlineUserNum(String serverIp) {
        return UserManager.I.getOnlineUserNum(serverIp);
    }
}
