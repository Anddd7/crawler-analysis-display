## Bilibili 数据分析
最近准备学习大数据方向的东西 ,大数据必须得先有大量的测试数据 ,在经过好几天的选题过后 ,决定以Bilibili作为数据分析的入口.

* 本人很喜欢B站 ,是一个老粉了
* 内容很有吸引力 ,用户群以年轻人居多 ,不论是视频内容还是弹幕评论 ,都有较高的分析价值
* 数据量也很大
* B站提供了数据接口 ,在获取数据上方便很多

## Python初体验
作为一个Java猿 ,最开始使用Java写了一些爬虫 ,主要是网页识别和抓取 ,也使用了一些大神的爬虫框架 `code4craft/webmagic` .但是总的来说对页面元素分析的'体力劳动'占了更多的时间 ,对于当前的学习来说意义不大 .在了解了一下爬虫和框架的原理后 ,借此良机学习成名已久的Python爬虫.

试着阅读了一些Python代码 ,感觉和之前学习的Scala有相似之处 ,所以就直接上手操作了
* ftp-utils : 上传文件到ftp服务器
* linuxconf-pdf-crawler : 爬取Linux之家的资源服务资源 (是一个好网站 ,大家支持 ,这里仅作练习)

实践出真知 ,Python还是很好理解和使用的 ,后续会找时间重新撸一编Python的基础教程 ,补充基础.

## 后续开发
* 继续使用Python ,连接获取B站的开发接口数据 ( 会设置步长 ,避免拖累服务器) : 参考[Vespa314/bilibili-api](https://github.com/Vespa314/bilibili-api)
* 抓取数据放到mongodb
* 数据建模 ,确定一些分析模式
* 学习hadoop ,搭建hadoop ,使用hadoop (从入门到放弃- -)
* 数据清洗/分析/建模/展示
* Tensorflow ... 

## 20170829
* 使用Java+Mysql重写了数据获取和存储 ,后期处理主要在Java上 ,Python+mongodb在数据获取上没有问题 ,但后面总归还是要各种转型到Java .
* 启用了beeltSQL作为持久化框架 ,之前使用过他核心的模板引擎 ,国产精品 ,支持学习一下 .
* -- 如何使用beeltSQL分表 ; beeltSQL其他操作 ...

## 20170906
* beeltSQL支持动态sql来进行分表 ,但没有一个总体的解决方案 (作者推荐当当网的shared-jdbc)
* 使用mysql直接存放爬取的数据并不是很好的考虑 (json数据会经过多次转换)
* python 爬取 - mongodb 存储 - hadoop 抽取分析 - hbase 存储中间数据 - mysql 结果数据 - web 展示

## 20170907
### Hadoop 开发环境安装和搭建
> [教你Windows平台安装配置Hadoop](http://blog.csdn.net/antgan/article/details/52067441)选用Hadoop 2.7.4 + [hadooponwindows](https://github.com/sardetushar/hadooponwindows) 就可以在Windows上*单机运行*Hadoop程序了 .

* 单机运行 : maven 添加相应依赖 ,运行时会寻找Path的 HADOOP_HOME ,配置好环境变量即可
  * hadoop-core 是 1.x 时代的包 ,2.x已经拆成各个子模块
* 伪集群运行 : 直接start-all.cmd ,但nodemanager启动失败 ,内存设置太小 ,待解决

### Hadoop Job
* 使用Hadoop分析tag使用率/播放量与视频类别的关系(哪个类别更受关注)
* 分析数据均存放到mongodb ,灵活性更高

## 20170910
* 开启mongodb连接池 ,开启线程池爬取数据
* 修改架构(bilibili)
  * 爬取数据 : 爬取上一月(上一周)的视频数据 ,存放到数据源表(search_source)
      * 播放/收藏/硬币是动态的 (爬虫做同步监测比较难),只针对部分视频进行实时分析 (某up主视频热度曲线/某视频热度曲线)
  * 分析数据 : 设置若干分析任务 ,通过管理员入口启动分析任务
  * 查看结果数据 : 各种图形展示
  * 所有数据属于平台公用 :
    * 爬取数据不可重爬 (播放/收藏/硬币只能做横向分析 ,各类别的视频关注度对比) ,未爬取的可由管理员触发
    * 分析数据可重启(意义不大- -) ,通过规则引擎自定义分析任务 (页面设置)
* douyu弹幕分析启动
  * 通过页面随机爬取主播聊天室;预设大主播房间 ,监测上下播
  * 弹幕数量/观看人数分析 ;弹幕类别分析 ; 礼物分析 (最新支持) ; 内容分析 ...
  
  