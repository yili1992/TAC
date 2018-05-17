package com.lee.tac.controller;



import com.lee.tac.dto.TestcaseDto;
import com.lee.tac.model.Testcase;
import com.lee.tac.dto.TestcaseLogDto;
import com.lee.tac.facade.TestcaseApiImpl;
import com.lee.tac.inner.AsyncService;
import com.lee.tac.inner.TestcaseLogService;
import com.lee.tac.mapper.TestcaseLogMapper;
import com.lee.tac.mapper.TestcaseMapper;
import com.lee.tac.model.TestcaseLog;
import com.lee.tac.enums.LogStatusEnum;
import com.lee.tac.requests.CommonRequest;
import com.lee.tac.responses.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lee.tac.utils.DateHelper.getCurrentDate;


/**
 * Created by zhaoli on 2018/4/8
 * author zhaoli
 */

@RestController
public class TestCaseController {
    private final static Logger logger = LoggerFactory.getLogger(TestCaseController.class);

    @Autowired
    TestcaseApiImpl testcaseApi;

    @Autowired
    TestcaseLogMapper testcaseLogMapper;

    @Autowired
    TestcaseMapper testcaseMapper;

    @Autowired
    TestcaseLogService testcaseLogService;

    @Autowired
    AsyncService asyncService;

    @Value(("${reportBaseUrl}"))
    private String reportBaseUrl;
    /**
     * TestCase 执行接口
     * @param testcaseId
     * @return
     */
    @CrossOrigin
    @GetMapping("/testcase/{id}")
    public ResponseEntity<Map<String,Object>> executeCase(@PathVariable("id") String testcaseId){
        CommonRequest<TestcaseDto> testCaseRequest = new CommonRequest<>();
        Map<String,Object> map = new HashMap<String,Object>();
        TestcaseDto testcaseDto = new TestcaseDto();
        testcaseDto.setId(Integer.valueOf(testcaseId));
        testCaseRequest.setRequestData(testcaseDto);
        CommonResponse<List<TestcaseDto>> result = testcaseApi.testCaseListSearch(testCaseRequest);
        testcaseDto = result.getData().get(0);
        try{
            if (testcaseDto.getNum() == 0) {
                String lastExecuteTime = getCurrentDate("yyyy-MM-dd HH:mm:ss");
                Testcase testcase = new Testcase();
                testcase.setLastExcuteTime(lastExecuteTime);
                testcase.setId(Integer.valueOf(testcaseId));
                int updateResult = testcaseMapper.updateByPrimaryKeySelectiveRowLock(testcase);
                if (updateResult == 0 ){
                    map.put("code", "10001");
                    map.put("msg", "测试集正在执行,请刷新页面");
                    return new ResponseEntity<>(map, HttpStatus.OK);
                }
                TestcaseLog testcaseLog = new TestcaseLog();
                testcaseLog.setStatus(LogStatusEnum.NOTSTART.getName());
                testcaseLog.setTestcaseId(testcaseDto.getId());
                testcaseLog.setStartTime(lastExecuteTime);
                testcaseLogMapper.insert(testcaseLog);
                asyncService.asyncExecuteCase(testcaseDto.getContent(),testcaseLog.getId());
                map.put("code", "00000");
                map.put("lastExcuteTime", lastExecuteTime);
                map.put("msg", "成功执行: " + testcaseDto.getName());
            } else {
                map.put("code", "10001");
                map.put("msg", "测试集正在执行,请刷新页面");
            }
        } catch(Exception e) {
            map.put("code", "10002");
            map.put("msg", "测试集执行失败："+e.toString());
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * TestCase 执行结束接口
     * @param logId
     * * @param reportId
     * @return
     */
    @CrossOrigin
    @GetMapping("/executor/{logId}&{reportId}")
    public void executeCallBack(@PathVariable("logId") String logId, @PathVariable("reportId") String reportId) {
        String logLink = reportBaseUrl+"?id="+reportId;
        TestcaseLogDto testcaseLogDto = testcaseLogService.selectTestcaseLogById(Integer.valueOf(logId));
        testcaseLogDto.setLogLink(logLink);
        testcaseLogService.updateTestcaseLog(testcaseLogDto);
        logger.info("回调报告地址："+logLink);
    }
}