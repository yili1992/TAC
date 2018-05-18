package com.lee.tac.service;

import com.lee.tac.dto.TestcaseLogDto;
import com.lee.tac.inner.AsyncService;
import com.lee.tac.inner.TestcaseLogService;
import com.lee.tac.utils.DateHelper;
import com.lee.tac.enums.LogStatusEnum;
import com.xxl.mq.client.XxlMqProducer;
import com.lee.tac.utils.DOM4JUtil;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import static com.lee.tac.utils.EncryptionTool.decryptBASE64;

/**
 * program: tac-root
 * description: ${description}
 * author: zhao lee
 * create: 2018-04-26 14:01
 **/
@Service("asyncService")
public class AsyncServiceImpl implements AsyncService {

    @Value(("${repoUrl}"))
    private String repoUrl;

    @Value(("${server.port}"))
    private String port;

    @Value(("${MQTopic}"))
    private String MQTopic;

    @Autowired
    TestcaseLogService testcaseLogService;

    @Async
    @Override
    public void asyncMethod(String arg) {
        System.out.println("arg:" + arg);
        System.out.println("=====" + Thread.currentThread().getName() + "=========");
    }

    @Async
    @Override
    public void asyncUpdateXml(String filePath, String projectName, String Name) {
        Document document = DOM4JUtil.xml2Dom4j(filePath);
        DOM4JUtil.setTestNGXmlSuiteName(document, projectName);
        DOM4JUtil.setTestNGXmlTestName(document, Name);
        DOM4JUtil.writeToNewXMLDocument(filePath,document);
    }

    @Async
    @Override
    public void asyncExecuteCase(String content, Integer logId) {
        System.out.println("=====" + Thread.currentThread().getName() + "=========");
        String host=null;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String filePath = decryptBASE64(content);
        File file = new File(filePath);
        // get name of file
        String fileName = file.getName().split("\\.")[0];
        String fileType = file.getName().split("\\.")[1];
        String testNGXmlUrl = String.format("http://%s:%s/download/%s&%s", host,port,fileName,fileType);
        TestcaseLogDto testcaseLogDto = testcaseLogService.selectTestcaseLogById(logId);
        try {
            // 消息数据
            Map<String, String> data = new HashMap<String, String>();
            data.put("testNGXmlUrl", testNGXmlUrl);
            data.put("logId", String.valueOf(logId));
            data.put("fileName", fileName);
            data.put("repoUrl", repoUrl);
            // 生产队列消息
            XxlMqProducer.produce(MQTopic, data);

        } catch (Exception e) {
            testcaseLogDto.setStatus(LogStatusEnum.TERMINATED.getName());
            testcaseLogDto.setEndTime(DateHelper.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
            testcaseLogService.updateTestcaseLog(testcaseLogDto);
            e.printStackTrace();
        }
    }

}
