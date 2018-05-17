package com.lee.tac.facade;

import com.lee.tac.dto.LogPullDto;
import com.lee.tac.responses.CommonResponse;
import com.lee.tac.dto.Page;
import com.lee.tac.dto.TestcaseLogDto;
import com.lee.tac.facade.pull.TestcaseLogPullApi;
import com.lee.tac.mapper.TestcaseLogMapper;
import com.lee.tac.requests.CommonRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * program: tac-root
 * description: ${description}
 * author: zhaoli@leoao.com
 * create: 2018-04-18 13:56
 **/
@Service("reportLogApi")
public class TestcaseLogApiImpl implements TestcaseLogPullApi {
    private final static Logger logger = LoggerFactory.getLogger(TestcaseLogApiImpl.class);

    @Autowired
    TestcaseLogMapper testcaseLogMapper;

    @Override
    public CommonResponse<List<TestcaseLogDto>> logListSearch(CommonRequest<LogPullDto> request) {
        CommonResponse<List<TestcaseLogDto>> response = new CommonResponse<>();
        Page page = request.getPage(true);
        try{
            List<TestcaseLogDto> testcaseLogDtos = testcaseLogMapper.queryLogListForSearch(request.getRequestData());
            if(testcaseLogDtos.size()!=0) {
                page.setCount(testcaseLogDtos.size(), true);
                response.setPage(page);
                testcaseLogDtos = page.cutListByPage(testcaseLogDtos);
            }
            response.setData(testcaseLogDtos);

        } catch (Exception e) {
            logger.error("logListSearch error", e);
            response.fail(CommonResponse.ERROR, CommonResponse.ERROR_MSG);
        }
        return  response;
    }
}
