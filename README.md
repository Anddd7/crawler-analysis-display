## 数据 - 抓取 分析 展示 平台

### 20180523 
* 使用gradle构建整个项目
* 分离项目块, 使用微服务的方式搭建各个工程服务
* 因为微服务互相独立, 因为独立工程; 使用父子结构会使构建/测试/检测变得很难
  * 继续开发, 初步完善后重构, 分离repo
  
```groovy
// 开启代码检测
apply plugin: 'findbugs'
apply from: "plugin/checkstyle.gradle"
apply from: "plugin/jacoco.gradle"
```