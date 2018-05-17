
## 模块及功能

### 自动化测试管理平台简介
用于管理自动化测试集 增/删/查/改/执行


#### 数据库信息

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

#### 接口

- [X] 1. 获取所有状态: /services/TestcaseStatusPullApi ,method: 'getAllStatus'
- [X] 2. 获取测试集执行日志： /services/TestcaseLogPullApi, method: 'logListSearch'
- [X] 3. 获取项目: /services/ProjectPullApi, method: 'getProject'
- [X] 4. 获取测试集: /services/TestcasePullApi ,method: 'testCaseListSearch'
- [X] 5. 更新测试集：/services/TestcaseApi'， method: 'updateTestCase'
- [X] 6. 保存测试集：/services/TestcaseApi'， method: 'saveTestCase'
- [X] 7. 删除测试集：/services/TestcaseApi， method: 'deleteTestCase'
- [X] 8. 获取今日统计数据接口： /services/StatisticsApi, method: 'getTodayData'
- [X] 9. 获取昨日统计数据接口： /services/StatisticsApi, method: 'getYesterdayData'
- [X] 10. 获取总统计数据接口： /services/StatisticsApi, method: 'getTotalData'
- [X] 11. 获取近七日统计数据接口： /services/StatisticsApi, method: 'getSevenDayData'
- [X] 12. 获取近十二个月统计数据接口： /services/StatisticsApi, method: 'getRecentlyYearExecuteData'

