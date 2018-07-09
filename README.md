## 数据 - 抓取 分析 展示 平台
### 20180709
* 修复了一些脚本和日志配置

### 20180706
* docker compose进行多容器管理
  * 使用脚本进行镜像和容器的管理
  * 各容器互连
* zuul进行对外接口的暴露
* 补测试

基本实现了一键部署开发环境

### 20180622
* gradle的docker插件有不可控性, 使用部分功能和命令行进行定制
  * gradle 编译
  * 下线已有container/删除已有container和images
  * build新的images/上线新的container

* 统一配置:
  * 所有应用在内部使用8080端口
  * 建立内部网络进行容器间互联
  * 仅对外开放 registry 和 gateway
  
对开发/测试环境来说, 可以在编写完代码后在本地一键启动一整套服务, 并已经相互连接

### 20180614
* 微服务基础组件
  * conditional + feature toggle + auto configuration
  * 配置/组件 重构
  * controller日志
  * correlation id + feign, 微服务日志记录
  * profile
   
* DevOps 
  * 本机 Jenkins CI
  * Docker集成

### 20180601
* 使用`SpringCloud`构建了整个项目
* beehive - 注册中心/网关
  - service-registry : eureka server 注册中心/负载均衡
* bees - 各模块的微服务
  - search-api : 基础服务, eureka client
  - analysis : 中间服务, eureka/feign(ribbon) client
* flowers - 公共组件


即所有服务开启后到`eureka server`进行注册,
 在使用服务时通过`eureka server`查询可用的服务, 并获取具体的路径.
 然后通过`feign/ribbon`进行远程调用.


### 20180523 
* 使用gradle构建整个项目
* 分离项目块, 使用微服务的方式搭建各个工程服务
* 因为微服务互相独立, 因为独立工程; 使用父子结构会使构建/测试/检测变得很难
  * 继续开发, 初步完善后重构, 分离repo
  
```groovy
// 开启代码检测
apply plugin: 'findbugs'
apply from: "plugin/checkstyle.gradle"
apply from: jacoco-task.gradle
```