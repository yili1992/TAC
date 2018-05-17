package com.lee.tac.inner;

/**
 * program: tac-root
 * description: ${description}
 * author: zhaoli@leoao.com
 * create: 2018-04-26 14:00
 **/
public interface AsyncService {
    void asyncMethod(String arg);

    void asyncExecuteCase(String content, Integer logId);

    void asyncUpdateXml(String filePath, String projectName, String Name);
}
