package com.mpush.mpns.web;

import io.vertx.core.Vertx;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:spring-core.xml")
public class SpringConfig {
    private static AppServer SERVER;
    private static Vertx VERTX;
    private static AnnotationConfigApplicationContext CTX;

    static void init(io.vertx.core.Vertx vertx, AppServer server) {
        //VERTX = Vertx.newInstance(vertx);
        VERTX = vertx;
        SERVER = server;
        CTX = new AnnotationConfigApplicationContext(SpringConfig.class);
    }

    static void scanHandler() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.setParent(CTX);
        context.scan("com.mpush.mpns.web.handler");
        context.refresh();
    }


    @Bean(name = "VERTX", destroyMethod = "close")
    public Vertx vertx() {
        return VERTX;
    }

    @Bean(name = "SERVER", destroyMethod = "stop")
    public AppServer server() {
        return SERVER;
    }

}