package com.mpush.mpns.web.common;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yxx on 2016/5/4.
 *
 * @author ohun@live.cn
 */
public class AccessLogHandler implements Handler<RoutingContext> {
    private final Logger logger;

    public AccessLogHandler(String logName) {
        this.logger = LoggerFactory.getLogger(logName);
    }

    public static AccessLogHandler create() {
        return new AccessLogHandler(AccessLogHandler.class.getName());
    }

    public static AccessLogHandler create(String logName) {
        return new AccessLogHandler(logName);
    }

    @Override
    public void handle(RoutingContext context) {
        long startTime = System.currentTimeMillis();
        context.addBodyEndHandler(event -> log(context, startTime));
        context.next();
    }

    public void log(RoutingContext context, long startTime) {
        long costTime = System.currentTimeMillis() - startTime;
        HttpMethod method = context.request().method();
        String uri = context.request().uri();
        HttpServerRequest request = context.request();
        long contentLength = request.response().bytesWritten();
        int status = request.response().getStatusCode();
        String referrer = request.headers().get("referrer");
        String userAgent = request.headers().get("user-agent");
        String remoteIp = Requests.getIp(request);

        StringBuilder sb = new StringBuilder(1024);
        sb.append('[').append(costTime).append(']');
        sb.append(' ').append('[').append(method).append(']');
        sb.append(' ').append('[').append(uri).append(']');
        sb.append(' ').append('[').append(status).append(']');
        sb.append(' ').append('[').append(contentLength).append(']');
        sb.append(' ').append('[').append(remoteIp).append(']');
        sb.append(' ').append('[').append(userAgent).append(']');
        sb.append(' ').append('[').append(referrer).append(']');
        logger.info(sb.toString());
    }

    private String getClientAddress(HttpServerRequest request) {
        SocketAddress address = request.remoteAddress();
        if (address == null) return null;
        return address.host();
    }
}
