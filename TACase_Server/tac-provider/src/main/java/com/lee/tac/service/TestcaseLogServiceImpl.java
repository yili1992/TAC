package com.lee.tac.service;

import com.lee.tac.dto.TestcaseLogDto;
import com.lee.tac.inner.TestcaseLogService;
import com.lee.tac.model.TestcaseLog;
import com.lee.tac.mapper.TestcaseLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * program: tac-root
 * description: ${description}
 * author: zhao lee
 * create: 2018-04-27 13:53
 **/
@Service("testcaseLogService")
public class TestcaseLogServiceImpl implements TestcaseLogService {

    @Autowired
    TestcaseLogMapper testcaseLogMapper;

    @Override
    public void updateTestcaseLog(TestcaseLogDto testcaseLogDto) {
        TestcaseLog testcaseLog = new TestcaseLog();
        testcaseLog.setId(testcaseLogDto.getId());
        testcaseLog.setStartTime(testcaseLogDto.getStartTime());
        testcaseLog.setEndTime(testcaseLogDto.getEndTime());
        testcaseLog.setStatus(testcaseLogDto.getStatus());
        testcaseLog.setLogLink(testcaseLogDto.getLogLink());
        testcaseLogMapper.updateByPrimaryKeySelective(testcaseLog);
    }

    @Override
    public TestcaseLogDto selectTestcaseLogById(Integer id) {
        TestcaseLog testcaseLog = new TestcaseLog();
        testcaseLog.setId(id);
        return testcaseLogMapper.selectByPrimaryKey(id);
    }
}
