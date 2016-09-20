package com.mpush.mpns.web.common;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;
import java.util.Locale;

/**
 * Created by yxx on 2016/5/12.
 *
 * @author ohun@live.cn
 */
public class LocaleResolveHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext rc) {
        LocaleContextHolder.setLocale(getLocale(rc));
        rc.next();
    }

    private Locale getLocale(RoutingContext rc) {
        List<io.vertx.ext.web.Locale> list = rc.acceptableLocales();
        if (list == null || list.isEmpty()) return Locale.getDefault();
        io.vertx.ext.web.Locale l = list.get(0);
        return new Locale(l.language(), l.country(), l.variant());
    }
}
