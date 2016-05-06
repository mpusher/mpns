package com.shinemo.mpns.dal.config;


import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.ConfigFactory;


/**
 * 针对每个配置项，建议各个对象自己持有，不建议每次都通过ConfigCenter获取，有性能损耗
 */
@Sources({
        "classpath:config.properties",
        "file:/${user.dir}/config.properties"
})
public interface ConfigCenter extends Config {

    ConfigCenter holder = ConfigFactory.create(ConfigCenter.class);
    
    @Key("jpush_app_key")
    @DefaultValue("d5effd7f9642f06c55fe29bf")
    String jpushAppKey();
    
    @Key("jpush_secret_key")
    @DefaultValue("4d5693aef9c59970403a70e0")
    String jpushSecretKey();
}
