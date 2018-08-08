## 数据 - 抓取 分析 展示 平台
### 20180808
- 你有什么数据
- 你想知道什么
- 应该选取哪种可视化
- 看到了什么
- 数据的意义和不足
  - 你有什么数据...

### 20180807
* 使用线程池(手动/定时)抓取数据
* 使用MongoDB持久化数据
* 分割工程:
  * dashboard : 面向页面整理数据
  * analysis : 数据分析
    * 使用redis缓存需要重复访问的数据
  * crawler : 数据抓取和清洗
* docker-compose 引入数据容器


自顶向下开发
- 需要达到的目的: 例如, 想知道哪种视频更受欢迎
- 设计展示形式: 柱状图/饼状图; 类别/主题/博主粉丝 相对比
  - 这一步可以初步设计出 dashboard 中需要输出的接口
  - 分析建模输出数据的来源
- 设计分析逻辑: 
  - 类别 | 播放量-硬币 : 哪个分类的视频更吸引人
  - 类别 | 回复-弹幕 : 哪个分类的视频互动更多
  - 主题 | ...
- 挖掘清洗需要的数据 
  - 通过search接口 挖出各分类的视频数据 (需要划分时间点)
  - 对数据进行清洗 (去除不必要的字段再存储, 或是加上一些tag标记)
  - 数据变换, 对已有数据的更新/映射
- 对于底层的爬虫: 
  - 保留尽量多的数据, 保持尽可能固定的格式
  - 避免爬虫和底层数据的改动, 牵一发而动全身
  - 由上层应用对数据进行选择性取用

### 20180724
* 使用docker-compose

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