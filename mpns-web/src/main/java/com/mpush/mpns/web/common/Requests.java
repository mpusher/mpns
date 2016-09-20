package com.mpush.mpns.web.common;

import io.vertx.core.http.HttpServerRequest;

/**
 * Created by yxx on 2016/5/11.
 *
 * @author ohun@live.cn
 */
public final class Requests {

    public static String getIp(HttpServerRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.remoteAddress().host();
        }
        if (ip.startsWith("0:0:0")) ip = "127.0.0.1";
        else if (ip.lastIndexOf(',') != -1) {
            try {
                ip = ip.split(",")[0];
            } catch (Exception e) {
                ip = request.remoteAddress().host();
            }
        }
        return ip;
    }
}
