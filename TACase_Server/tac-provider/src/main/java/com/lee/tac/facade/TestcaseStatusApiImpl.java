package com.lee.tac.facade;

import com.lee.tac.facade.pull.TestcaseStatusPullApi;
import com.lee.tac.mapper.TestcaseStatusMapper;
import com.lee.tac.model.TestcaseStatus;
import com.lee.tac.responses.CommonResponse;
import com.lee.tac.dto.TestcaseStatusDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * program: tac-root
 * description:
 * author: zhao lee
 * create: 2018-04-09 10:29
 **/

@Service("caseStatusApi")
public class TestcaseStatusApiImpl implements TestcaseStatusPullApi {
    private final static Logger logger = LoggerFactory.getLogger(TestcaseStatusApiImpl.class);

    @Autowired
    private TestcaseStatusMapper testcaseStatusMapper;

    @Override
    public CommonResponse<List<TestcaseStatusDto>> getAllStatus() {
        CommonResponse<List<TestcaseStatusDto>> response = new CommonResponse<>();
        try {
            List<TestcaseStatusDto> statusDtoList = new ArrayList<>();
            List<TestcaseStatus> statusList = testcaseStatusMapper.selectAllStatus();
            for (TestcaseStatus x : statusList) {
                TestcaseStatusDto testcaseStatusDto = new TestcaseStatusDto();
                testcaseStatusDto.setCode(x.getCode());
                testcaseStatusDto.setName(x.getName());
                statusDtoList.add(testcaseStatusDto);
            }
            response.setData(statusDtoList);
        } catch (Exception e) {
            logger.error("getAllStatus error", e);
            response.fail(CommonResponse.ERROR, CommonResponse.ERROR_MSG);
        }
        return response;
    }
}
