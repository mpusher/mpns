package com.shinemo.mpns.client.common;

public class EnvUtil {
	
	public static int ENV_DEV = 0;
    /**
     * 日常
     */
    public static int  ENV_DAILY   = 1;
    /**
     * 预发
     */
    public static int  ENV_PREF    = 2;
    /**
     * 线上
     */
    public static int  ENV_ONLINE  = 3;

    /**
     * 当前环境值,默认是daily
     */
    private static int current_env = 0;

    public void setEnv(int env) {
        current_env = env;
    }

    /**
     * 返回当前环境值
     * 
     * @return
     */
    public static int getCurrentEnv() {
    	if(ENV_DEV == current_env)
    		return ENV_DEV;
    	else if (ENV_DAILY == current_env)
            return ENV_DAILY;
        else if (ENV_PREF == current_env)
            return ENV_PREF;
        else
            return ENV_ONLINE;
    }
    
    /**
     * 当前环境是否为开发环境
     * 
     * @return
     */
    public static boolean isDev() {
        return ENV_DEV == current_env;
    }

    /**
     * 当前环境是否为日常环境
     * 
     * @return
     */
    public static boolean isDaily() {
        return ENV_DAILY == current_env;
    }

    /**
     * 当前环境是否为预发环境
     * 
     * @return
     */
    public static boolean isPre() {
        return ENV_PREF == current_env;
    }

    /**
     * 判断当前环境是否为线上环境
     * 
     * @return
     */
    public static boolean isOnline() {
        return ENV_ONLINE == current_env;
    }

}
