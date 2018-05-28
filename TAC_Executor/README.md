# TAC自动化测试管理平台  TAC_Executor 执行器端简介
TAC_Executor 使用了xxl-MQ 消息队列来分发消息
- 目前采用的是 SERIAL_QUEUE(串行队列)模型 ， 想修改消费模型请阅读[XXL-MQ文档](http://www.xuxueli.com/xxl-mq/#/)

# TAC_Executor部署
## MQ接入Zookeeper配置
文件配置在项目所在硬盘绝对地址: “/data/webapps/xxl-conf.properties”
配置内容如下：
```
// zookeeper集群时，多个地址用逗号分隔
zkserver=127.0.0.1:2181
```

## 配置信息修改
对消息的操作都在TestCaseMqComsumer.java代码中：
![image](https://github.com/yili1992/TAC/raw/master/assets/executor.png)
- 先 对git 库进行更新，并进行maven 对项目进行打包， 然后通过 java -jar lee_TA.jar {testng xml配置文件} 命令来执行我们的测试用例
- 这里要注意的是  保存git账号密码到  tac_executor服务器上
- 红色方框的地方 是执行脚步的命令代码，如果想执行脚步非阻塞 可以用ProcessUtil.executeProcess 方法

## 部署TAC_Server
- 执行 mvn package ，会产生tac-executor.jar文件在/target下
- nohup java -Ddubbo.qos.port=33333 -jar tac-executor.jar > /dev/null  2>&1 &



