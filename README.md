## 数据 - 抓取 分析 展示 平台



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