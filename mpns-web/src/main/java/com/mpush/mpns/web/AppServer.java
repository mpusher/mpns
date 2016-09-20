package com.mpush.mpns.web;

import com.mpush.mpns.web.common.AccessLogHandler;
import com.mpush.mpns.web.common.ApiErrorHandler;
import com.sun.management.OperatingSystemMXBean;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.ResponseTimeHandler;
import io.vertx.ext.web.handler.TimeoutHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.handler.sockjs.SockJSHandlerOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * Created by yxx on 2016/4/22.
 *
 * @author ohun@live.cn
 */
public class AppServer extends AbstractVerticle {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private OperatingSystemMXBean osMxBean;

    private JsonObject config;

    private HttpServer server;

    private Router mainRouter;

    private Router apiRouter;

    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);
        config = config();
        mainRouter = Router.router(vertx);
        apiRouter = Router.router(vertx);
        SpringConfig.init(vertx, this);
    }

    public void initHandler() {
        /*mainRouter.route("/static").handler(StaticHandler
                .create()
                .setCachingEnabled(true)
        );
        mainRouter.route("/favicon.ico").handler(FaviconHandler.create("/webroot/favicon.ico"));
        mainRouter.route().failureHandler(ErrorHandler.create());*/
        mainRouter.mountSubRouter("/api", apiRouter);
        apiRouter.route().handler(TimeoutHandler.create(config.getInteger("max.request.timeout", 10 * 1024)));
        apiRouter.route().handler(AccessLogHandler.create(config.getString("api.access.log", "App-Api")));
        apiRouter.route().handler(BodyHandler.create());
        apiRouter.route().handler(ResponseTimeHandler.create());
        apiRouter.route().failureHandler(ApiErrorHandler.create());
    }

    @Override
    public void start() {
        initHandler();
        //initWebSocket();
        //initMxBean();
        startServer();
        SpringConfig.scanHandler();
    }

    private void startServer() {
        int port = config.getInteger("http.server.port", 8080);
        server = vertx.createHttpServer();
        server.requestHandler(mainRouter::accept).listen(port);
        logger.error("app server start success listen " + port);
    }

    private void initWebSocket() {
        Router router = Router.router(vertx);
        SockJSHandlerOptions options = new SockJSHandlerOptions()
                .setHeartbeatInterval(1000 * 60);
        SockJSHandler sockJSHandler = SockJSHandler.create(vertx, options);

        PermittedOptions inboundPermitted = new PermittedOptions().setAddressRegex("server/.*");
        PermittedOptions outboundPermitted = new PermittedOptions().setAddressRegex("client/.*");

        BridgeOptions ops = new BridgeOptions()
                .addInboundPermitted(inboundPermitted)
                .addOutboundPermitted(outboundPermitted);

        sockJSHandler.bridge(ops);

        router.route("/eb/*").handler(sockJSHandler);
        mainRouter.mountSubRouter("/ws", router);
    }

    private void initMxBean() {
        try {
            osMxBean = ManagementFactory.newPlatformMXBeanProxy(ManagementFactory.getPlatformMBeanServer(),
                    ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        vertx.setPeriodic(MINUTES.toMillis(5), t -> logger.info("avgLoad:{}\n freeMem:{}",
                osMxBean.getSystemLoadAverage(),
                osMxBean.getFreePhysicalMemorySize()
                )
        );
    }

    public HttpServer getServer() {
        return server;
    }

    public Router getMainRouter() {
        return mainRouter;
    }

    public Router getApiRouter() {
        return apiRouter;
    }

    public JsonObject getConfig() {
        return config;
    }

}
