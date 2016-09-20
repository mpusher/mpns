package com.mpush.mpns.web.common;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Created by yxx on 2016/5/6.
 *
 * @author ohun@live.cn
 */
public class ApiErrorHandler implements Handler<RoutingContext> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public static ApiErrorHandler create() {
        return new ApiErrorHandler();
    }

    @Override
    public void handle(RoutingContext rc) {
        Throwable e = rc.failure();
        int code = 500;
        String message = null;
        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            code = apiException.getCode();
            message = apiException.getMessage();
        }

        rc.response().end(new ApiResult<>(code, message).toString());

        logger.error("api error, code={}, msg={}", code, message, e);
    }


}
