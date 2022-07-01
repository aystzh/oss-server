# oss-server

提供文件存储服务， 支持Local FastDFS MinIo MongoDB文件存储下载功能；集成了Nacos和OpenFeign，可以作为Springcloud一个服务独立部署。

## 项目结构

```java_holder_method_tree
├── oss-common      通用工具类封装
├── oss-sdk         对外提供第三方SDK接口
├── oss-service     存储服务
```

## 相关配置

### Nacos配置文件相关

```yaml
spring:
  application:
    name: base-oss-server
  cloud:
    nacos:
      discovery:
        server-addr: 10.0.xx.xx:8848
        group: DEVELOP_XX_GROUP
        namespace: 424a08e2-xxxx-xxxx-xxxx-ac56d36ee8f4
      config:
        server-addr: 10.0.xx.xx:8848
        group: DEVELOP_XX_GROUP
        namespace: 424a08e2-xxxx-xxxx-xxxx-ac56d36ee8f4
        prefix: base-oss-server
        file-extension: yaml
```

PS：这里需要使用base-oss-server.yml配置相关存储服务配置信息 其中涉及到一下几个开关都是配合使用的。如：

```yaml
#各个配置启动开关是否开启
oss:
  platform:
    local:
      enabled: true
    fdfs:
      enabled: true
    minio:
      enabled: true
    mongodb:
      enabled: true

#######存储配置设置#########
local:
  root: /Users/xxx/Desktop/upload
  invokingRoot: 192.xxx.0.7

fdfs:
  connect-timeout: 600
  so-timeout: 1500
  thumb-image:
    height: 150
    width: 150
  tracker-list:
    - 10.xx.xx.xxx:44144
  http: 10.xx.xx.xxx:40002
  pool:
    jmx-enabled: false

minio:
  # minio服务地址
  endpoint: http://10.xx.xx.xxx
  #端口
  port: 9000
  # 账号
  accessKey: xxx
  # 密码
  secretKey: xxxxxxx
  # 桶名称(根目录文件夹名称)
  bucketName: xxx

mongodb:
  uri: 10.xx.xx.xxx:27017
  username: xxx
  password: xxxxxx
  schema: xxx

#######存储配置设置#########
```

### 第三方引入方式

本地maven install oss-sdk到自己本地仓库或者 maven deploy到公司私服仓库，相关消费方pom文件导入依赖即可
