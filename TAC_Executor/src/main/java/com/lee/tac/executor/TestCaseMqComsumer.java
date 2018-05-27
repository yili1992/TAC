package com.lee.tac.executor;

import com.lee.tac.enums.LogStatusEnum;
import com.lee.tac.inner.TestcaseLogService;
import com.xxl.mq.client.consumer.IMqConsumer;
import com.xxl.mq.client.consumer.annotation.MqConsumer;
import com.xxl.mq.client.rpc.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lee.tac.utils.DOM4JUtil;
import com.lee.tac.utils.ProcessUtil;
import com.lee.tac.dto.TestcaseLogDto;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.lee.tac.utils.DateHelper.getCurrentDate;
import static com.lee.tac.utils.HttpUtil.downloadFile;
import static com.xxl.mq.client.consumer.annotation.MqConsumerType.SERIAL_QUEUE;

/**
 * 消息模型 2/3 : SERIAL_QUEUE = 串行队列 : 点对点模式, 消息进去队列之后, 只会被消费一次。同一Topic下的多个Consumer并行消费消息, 吞吐量较大
 * Created by xuxueli on 16/8/28.
 */
@MqConsumer(value = "mqtestcase", type = SERIAL_QUEUE)
@Service
public class TestCaseMqComsumer implements IMqConsumer {
    private Logger logger = LoggerFactory.getLogger(TestCaseMqComsumer.class);

    @Value(("${MQTopic}"))
    private String MQTopic;

    @Value(("${executorPath}"))
    private String executorPath;

    @Autowired
    TestcaseLogService testcaseLogService;

    @Override
    public void consume(Map<String, String> data) throws Exception {
        logger.info("SERIAL_QUEUE(串行队列): {}消费一条消息:{}", MQTopic, JacksonUtil.writeValueAsString(data));
        String fileName = data.get("fileName");
        String repoUrl = data.get("repoUrl");
        String testNGXmlUrl = data.get("testNGXmlUrl");
        String logId = data.get("logId");
        String fileType = "xml";
        String testNGXmlPath = executorPath + fileName + "." + fileType;
        TestcaseLogDto testcaseLogDto = testcaseLogService.selectTestcaseLogById(Integer.valueOf(logId));
        try {
            downloadFile(testNGXmlPath, testNGXmlUrl);
            System.out.println("下载执行文件完毕！ ");
            Document document = DOM4JUtil.xml2Dom4j(testNGXmlPath);
            DOM4JUtil.addLogIdParameter(document, String.valueOf(logId));
            DOM4JUtil.writeToNewXMLDocument(testNGXmlPath, document);
            testcaseLogDto.setStatus(LogStatusEnum.DOING.getName());
            testcaseLogDto.setStartTime(getCurrentDate("yyyy-MM-dd HH:mm:ss"));
            testcaseLogService.updateTestcaseLog(testcaseLogDto);
            ProcessUtil.executeProcessByWait(String.format("git clone %s repo", repoUrl), executorPath);
            ProcessUtil.executeProcessByWait("git reset --hard origin/master", executorPath+"repo");
            ProcessUtil.executeProcessByWait("git pull", executorPath+"repo");
            ProcessUtil.executeProcessByWait("mvn clean compile", executorPath+"repo");
            ProcessUtil.executeProcessByWait("mvn assembly:single", executorPath+"repo");
            String testNGJarPath = executorPath+"repo/target/lee_TA-0.0.1-SNAPSHOT-assembly.jar";
            String command = String.format("java -jar %s %s", testNGJarPath, testNGXmlPath);
            System.out.println("开始执行测试！ "+command);
            ProcessUtil.executeProcessByWait(command, executorPath);
            System.out.println("执行测试完毕！ ");
            testcaseLogDto.setStatus(LogStatusEnum.DONE.getName());
            testcaseLogDto.setEndTime(getCurrentDate("yyyy-MM-dd HH:mm:ss"));
            testcaseLogService.updateTestcaseLog(testcaseLogDto);
        } catch (Exception e) {
            testcaseLogDto.setStatus(LogStatusEnum.TERMINATED.getName());
            testcaseLogDto.setEndTime(getCurrentDate("yyyy-MM-dd HH:mm:ss"));
            testcaseLogService.updateTestcaseLog(testcaseLogDto);
            e.printStackTrace();
        }
    }

}
