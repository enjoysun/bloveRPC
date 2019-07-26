# bloveRPC
be based on netty5.0   

test/java/blove/client/sendTest模拟客户端  
test/java/blove/server/launcher模拟服务端 
 

###### frameType  

|frameHeader|frameBody|frameTail|  
|------|------|------|  
|lengthField、frameType、acquireCode|request、response|crcCode、tail|  

> 采用消息定长机制处理粘包问题，定长的lengthField字段放于帧首位置引入LengthFieldBasedFrameDecoder、
LengthFieldPrepender分别用来添加长度字段和定长解析帧内容。  

###### 容器模拟  

> Simulate路径下存放了接口和其实现，利用定制好的配置路径反射获取其调用接口模拟spring IOC容器

###### 任务处理  

> 采用自定义线程池处理channelHandler中耗时任务，在task中进行任务处理和结果通知，通知利用channel的事件回调机制  

###### 多客户端设计  

> 使用可重入cas管程进行生产消费通知机制模拟，任务提交模拟多客户端引导器  

###### 待实现  

> 1.心跳检测机制(接入zookeeper)  
2.实现定时特殊功能帧发送检测效果   
3.实现可配置codec方式，更改帧的内容取消自动定长改为手动定长 

