package com.mpush.mpns.web.handler;

import com.mpush.mpns.web.AppServer;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created by yxx on 2016/4/26.
 *
 * @author ohun@live.cn
 */
public abstract class BaseHandler {

    @Resource
    protected Vertx vertx;

    @Resource
    protected AppServer server;

    protected EventBus eventBus;

    protected Router router;

    protected abstract void initRouter(Router router);

    @PostConstruct
    public void initRouter() {
        router = Router.router(vertx);
        server.getApiRouter().mountSubRouter(getRootPath(), router);
        initRouter(router);
    }

    protected Route router(String path, Handler<RoutingContext> h) {
        return router.route(path + ".json").handler(h);
    }

    protected <T> void consumer(String path, Handler<Message<T>> handler) {
        vertx.eventBus().localConsumer("server" + getRootPath() + path, handler);
    }

    public String getRootPath() {
        return "/";
    }
}
