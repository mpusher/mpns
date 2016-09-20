package com.mpush.mpns.web;

/**
 * Created by yxx on 2016/4/26.
 *
 * @author ohun@live.cn
 */
public class AppMain {
    public static void main(String[] args) {
        AppLauncher.main(new String[]{
                "run", AppServer.class.getName(),
                //"-cluster", "false",
                "-conf", AppMain.class.getResource("/cfg.json").getFile()
        });
    }
}
