# mpns 业务消息推送系统

#### 说明由于该系统是一个偏向业务的系统，所以本工程只是一个样例工程，具体的业务逻辑还需要业务方自己实现。

>~~ps:由于本工程目前依赖的`mpush-client:0.0.4`还没有发布~~

>~~要先到mpush `git checkout dev`分支~~

>~~然后执行命令`mvn clean install -Ppub`~~

>~~把`mpush-client:0.0.4`安装到本地maven仓库~~


## 源码启动

1. 本工程使用`vert.x + spring` 实现，需要了解一些`vert.x`的知识
2. 修改配置文件`conf-xxx.properties`主要修改Zookeeper配置
3. Main方法启动`com.mpush.mpns.web.AppMain.java`

## 独立部署
1. 打包 `mvn clean package -Ponline`
2. 打好的包位置： `mpns-web/target/mpns-release.tar.gz`
3. 解压 `tar -zvxf mpns-release.tar.gz`
4. 后台启动 `java -jar mpns.jar start`
5. 停止服务 `java -jar mpns.jar stop appId` 

  > appId为start后控制台输出的那个字符串
  
  > 如过不知道appId可以先执行下`java -jar mpns.jar stop` 控制台会输出
6. 查看帮助 `java -jar mpns.jar -h`
7. 前台启动 `java -jar mpns.jar`

## 对外接口
#### 详细参加`com.mpush.mpns.web.handler.AdminHandler`
1. http://127.0.0.1:8080/api/admin/push.json?userId=user-0&content=test 模拟给指定用户发送push
2. http://127.0.0.1:8080/api/admin/list/servers.json 查询mpush server 列表
3. http://127.0.0.1:8080/api/admin/get/onlineUserNum.json?ip=120.23.43.1 查询mpushServer在线用户数

