package com.mpush.mpns.web.handler;

import com.mpush.mpns.biz.domain.NotifyDO;
import com.mpush.mpns.biz.service.MPushManager;
import com.mpush.mpns.biz.service.PushService;
import com.mpush.mpns.web.common.ApiResult;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by yxx on 2016/4/26.
 *
 * @author ohun@live.cn
 */
@Controller
public class AdminHandler extends BaseHandler {

    @Resource
    private PushService pushService;

    @Resource
    private MPushManager mPushManager;

    @Override
    public String getRootPath() {
        return "/admin";
    }

    @Override
    protected void initRouter(Router router) {
        router("/push", this::sendPush);
        router("/list/servers", this::listMPushServers);
        router("/get/onlineUserNum", this::getOnlineUserNum);

        initConsumer(eventBus);
    }

    protected void initConsumer(EventBus eventBus) {
        consumer("/getUser", this::onTestEvent);
    }

    public void listMPushServers(RoutingContext rc) {
        rc.response().end(new ApiResult<>(mPushManager.getConnectServerList()).toString());
    }

    public void getOnlineUserNum(RoutingContext rc) {
        String ip = rc.request().getParam("ip");
        rc.response().end(new ApiResult<>(mPushManager.getOnlineUserNum(ip)).toString());
    }

    public void sendPush(RoutingContext rc) {
        String userId = rc.request().getParam("userId");
        String content = rc.request().getParam("content");
        boolean success = pushService.notify(userId, new NotifyDO(content));
        rc.response().end(new ApiResult<>(success).toString());
    }

    public void onTestEvent(Message<JsonObject> event) {
        JsonObject object = event.body();
        object.put("data", "server replay msg");
        event.reply(object);
    }
}
