package com.shinemo.mpns.core.mpush.service.impl;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.mpush.api.RedisKey;
import com.mpush.tools.redis.manage.RedisManage;
import com.mpush.zk.ZKServerNode;
import com.shinemo.mpns.client.common.GsonUtil;
import com.shinemo.mpns.core.async.AsyncSupport;
import com.shinemo.mpns.core.async.event.ConnOfflineEvent;
import com.shinemo.mpns.core.mpush.domain.App;
import com.shinemo.mpns.core.mpush.service.AllotService;
import com.shinemo.mpns.core.mpush.service.ConnectionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service("allotService")
public class AllotServiceImpl extends AsyncSupport implements AllotService {

    private static final Logger log = LoggerFactory.getLogger(AllotServiceImpl.class);

    @Resource
    private ConnectionService connService;

    private static final Cache<String, String> cache = CacheBuilder
            .newBuilder().expireAfterWrite(30, TimeUnit.SECONDS).build();// 15秒后消失

    private static final String CACHE_KEY = "serverList";

    @Subscribe
    public void handlerEvent(ConnOfflineEvent event) {
        log.info("receive conn offline event");
        cache.invalidate(CACHE_KEY);
    }


    @Override
    public String serverList() {
        List<ZKServerNode> ret = connService.getConnectionClient().getApplicationList();
        List<App> appList = Lists.newArrayList();
        for (ZKServerNode app : ret) {
            String tmp = app.getExtranetIp() + ":" + app.getPort();
            String strCount = cache.asMap().get(app.getExtranetIp());
            Long count = 0L;
            if (StringUtils.isBlank(strCount)) {
                count = RedisManage.zCard(RedisKey.getUserOnlineKey(app.getExtranetIp()));
                if (count == null) {
                    count = 0L;
                }
                cache.put(app.getExtranetIp(), count + "");
            } else {
                count = Long.parseLong(strCount);
            }
            appList.add(new App(tmp, count));
        }
        Collections.sort(appList);

        List<String> serverList = Lists.transform(appList, new Function<App, String>() {
            @Override
            public String apply(App input) {
                return input.getIpAndPort();
            }
        });

        if (log.isInfoEnabled()) {
            log.info("serverList:" + GsonUtil.toJson(appList));
        }
        return Joiner.on(",").join(serverList);
    }

}
