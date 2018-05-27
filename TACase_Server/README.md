

# TAC自动化测试管理平台  Server端简介
TAC_Server 整个TAC平台是前后端分离项目，TAC_Server是后端项目，后端只提供接口
- JSONRPC接口提供数据
- Rest接口提供资源交互
- Dubbo接口提供Service给TAC_Server及TAC_Executor

# TAC_Server部署
## 数据库信息
根据下面建表信息创建表

```` sql

#测试用例日志 状态表
CREATE TABLE testcase_status(
    id int not null auto_increment primary key comment '唯一主键',
    name varchar(100) comment '状态名',
    code varchar(10) comment '状态码',
    description varchar(200) comment '状态描述'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO testcase_status (name, code, description) VALUES ('执行中', 'doing', '正在执行的状态');
INSERT INTO testcase_status (name, code, description) VALUES ('未开始', 'notstart', '还没开始执行的状态');
INSERT INTO testcase_status (name, code, description) VALUES ('执行完成', 'done', '执行完成的状态');
INSERT INTO testcase_status (name, code, description) VALUES ('已终止', 'terminated', '执行被终止');
#项目表
CREATE TABLE project(
    id int not null auto_increment primary key comment '唯一主键',
    name varchar(20) comment '项目名',
    create_time datetime not null default CURRENT_TIMESTAMP comment '创建时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

#测试集表
CREATE TABLE testcase(
    id int not null auto_increment primary key comment '唯一主键',
    name varchar(50) comment '测试集名',
    project_id int comment '项目id',
    create_time datetime not null default CURRENT_TIMESTAMP comment '创建时间',
    last_excute_time datetime comment '最新执行时间',
    content text comment '配置信息，配置文件所在地址转Base64编码保存',
    author varchar(15) comment '作者'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
#测试日志
CREATE TABLE testcase_log(
    id int not null auto_increment primary key comment '唯一主键',
    testcase_id int comment '测试集id',
    status varchar(10) comment '状态码',
    start_time datetime comment '开始时间',
    end_time datetime comment '结束时间',
    log_link varchar(200) comment '测试报告链接'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
#PV,UV统计表
CREATE TABLE view(
    id int not null auto_increment primary key comment '唯一主键',
    time datetime comment '时间',
    pv int default 0 comment 'page views',
    uv int default 0 comment 'user views'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

````

## 配置信息修改
![image](https://github.com/yili1992/TAC/raw/master/assets/spring_properties.png)

```java
spring.profiles.active[0]=production  来选择生产环境配置信息还是开发环境配置信息
```
![image](https://github.com/yili1992/TAC/raw/master/assets/production.png)
在配置信息中填写自己的相应信息
- dubbo.registry 注册中心地址，使用zookeeper地址，因为后面TAC_executor还需要zookeeper,如果需要安装zookeeper请移步[Zookeeper安装]https://www.jianshu.com/p/bf3be8420a9a
- spring.datasource.tac.url 数据库地址
- uploadPath 上传文件存放路径
- repoUrl 代码仓库地址
- reportBaseUrl TAC没有包含测试报告，用的是[ExtendReport](https://github.com/yili1992/ExtentReport)，所以这里要填写相应的url。**如果你自己的TestNG已经有报告服务器了，需求去 修改tac-web 中TestCaseController.class的 executeCallBack() 方法来拼装你的报告地址。** **如果没有自己的报告服务器 可以 移步[ExtendReport](https://github.com/yili1992/ExtentReport)安装**

## 启动TAC_Server
- tac-web 是Springboot 宿主工程， 启动Application ,正常启动后 访问 http://localhost:8088

## 部署TAC_Server
- 对lefit-tac-root 执行 mvn package ，会产生tac.jar文件在lefit-tac-web/target下
- nohup java -jar tac.jar &

#### 接口说明

- [X] 1. 获取所有状态: /services/com.lefit.tac.facade.pull.TestcaseStatusPullApi ,method: 'getAllStatus'
- [X] 2. 获取测试集执行日志： /services/com.lefit.tac.facade.pull.TestcaseLogPullApi, method: 'logListSearch'
- [X] 3. 获取项目: /services/com.lefit.tac.facade.pull.ProjectPullApi, method: 'getProject'
- [X] 4. 获取测试集: /services/com.lefit.tac.facade.pull.TestcasePullApi ,method: 'testCaseListSearch'
- [X] 5. 更新测试集：/services/com.lefit.tac.facade.TestcaseApi'， method: 'updateTestCase'
- [X] 6. 保存测试集：/services/com.lefit.tac.facade.TestcaseApi'， method: 'saveTestCase'
- [X] 7. 删除测试集：/services/com.lefit.tac.facade.TestcaseApi， method: 'deleteTestCase'
- [X] 8. 获取今日统计数据接口： /services/com.lefit.tac.facade.pull.StatisticsApi, method: 'getTodayData'
- [X] 9. 获取昨日统计数据接口： /services/com.lefit.tac.facade.pull.StatisticsApi, method: 'getYesterdayData'
- [X] 10. 获取总统计数据接口： /services/com.lefit.tac.facade.pull.StatisticsApi, method: 'getTotalData'
- [X] 11. 获取近七日统计数据接口： /services/com.lefit.tac.facade.pull.StatisticsApi, method: 'getSevenDayData'
- [X] 12. 获取近十二个月统计数据接口： /services/com.lefit.tac.facade.pull.StatisticsApi, method: 'getRecentlyYearExecuteData'

