## Microservice Common 
* Spring Configuration setup
  * Jackson
  * Swagger
  * Feign
* Default filter and Controllers
  * Logging: Correlation Tracking
  * Exception Handler
  
### 分布式日志追踪 : Correlation-ID
在一个Request链路中, 增加一个唯一的ID并依次传递. 
最后依靠这个ID对日志进行查询, 就可以得到一次前端Request的所有链路请求.
- 由Request的起始端生成CorrelationId
- 系统接收到Id后, 在进行子系统调用时, 转发此Id
##### 接收CorrelationId
- Controller: CorrelationInterceptor, 从Request Header中获取链路Id(无则生成)
##### 转发CorrelationId
- Feign Client: FeignInterceptor, 在Feign Request中注入当前线程所保存的CorrelationId


