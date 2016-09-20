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

package com.mpush.mpns.biz.service.impl;

import com.mpush.api.Constants;
import com.mpush.api.push.*;
import com.mpush.api.router.ClientLocation;
import com.mpush.mpns.biz.service.PushService;
import com.mpush.mpns.biz.domain.NotifyDO;
import com.mpush.mpns.biz.domain.OfflineMsg;
import com.mpush.tools.Jsons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by ohun on 16/9/15.
 *
 * @author ohun@live.cn (夜色)
 */
@Service
public class PushServiceImpl implements PushService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PushSender mpusher;

    private final AtomicLong msgIdSeq = new AtomicLong(1);//TODO业务自己处理

    @Override
    public boolean notify(String userId, NotifyDO notifyDO) {
        PushMsg pushMsg = PushMsg.build(MsgType.NOTIFICATION_AND_MESSAGE, Jsons.toJson(notifyDO));
        pushMsg.setMsgId(Long.toString(msgIdSeq.incrementAndGet()));
        byte[] content = Jsons.toJson(pushMsg).getBytes(Constants.UTF_8);

        doSend(userId, content, new PushCallback() {
            int retryCount = 0;

            @Override
            public void onSuccess(String userId, ClientLocation location) {
                logger.warn("send msg success");
            }

            @Override
            public void onFailure(String userId, ClientLocation clientLocation) {
                saveOfflineMsg(new OfflineMsg(userId, content));
            }

            @Override
            public void onOffline(String userId, ClientLocation clientLocation) {
                if (clientLocation != null) {
                    String os = clientLocation.getOsName().toLowerCase();
                    if (os.contains("ios")) {
                        send2ANPs(userId, notifyDO, clientLocation.getDeviceId());
                    } else if (os.contains("android")) {
                        if (os.contains("xiaomi")) {
                            send2MiPush(userId, notifyDO);
                        } else if (os.contains("huawei")) {
                            send2HuaweiPush(userId, notifyDO);
                        } else {
                            send2JPush(userId, notifyDO);
                        }
                    } else {
                        saveOfflineMsg(new OfflineMsg(userId, content));
                    }
                } else {
                    saveOfflineMsg(new OfflineMsg(userId, content));
                }
            }

            @Override
            public void onTimeout(String userId, ClientLocation clientLocation) {
                if (retryCount++ > 1) {
                    saveOfflineMsg(new OfflineMsg(userId, content));
                } else {
                    doSend(userId, content, this);
                }
            }
        });

        return true;
    }

    @Override
    public boolean send(String userId, byte[] content) {
        doSend(userId, content, new PushCallback() {
            int retryCount = 0;

            @Override
            public void onSuccess(String userId, ClientLocation location) {
                logger.warn("send msg success");
            }

            @Override
            public void onFailure(String userId, ClientLocation clientLocation) {
                saveOfflineMsg(new OfflineMsg(userId, content));
            }

            @Override
            public void onOffline(String userId, ClientLocation clientLocation) {
                saveOfflineMsg(new OfflineMsg(userId, content));
            }

            @Override
            public void onTimeout(String userId, ClientLocation clientLocation) {
                if (retryCount++ > 1) {
                    saveOfflineMsg(new OfflineMsg(userId, content));
                } else {
                    doSend(userId, content, this);
                }
            }
        });
        return true;
    }


    private void doSend(String userId, byte[] content, PushCallback callback) {
        mpusher.send(new PushContext(content)
                .setUserId(userId)
                .setCallback(callback)
        );
    }

    private void send2ANPs(String userId, NotifyDO notifyDO, String deviceToken) {
        logger.info("send to ANPs");
    }

    private void send2MiPush(String userId, NotifyDO notifyDO) {
        logger.info("send to xiaomi push");
    }

    private void send2HuaweiPush(String userId, NotifyDO notifyDO) {
        logger.info("send to huawei push");
    }

    private void send2JPush(String userId, NotifyDO notifyDO) {
        logger.info("send to jpush");
    }

    private void saveOfflineMsg(OfflineMsg offlineMsg) {
        logger.info("save offline msg to db");
    }

}
