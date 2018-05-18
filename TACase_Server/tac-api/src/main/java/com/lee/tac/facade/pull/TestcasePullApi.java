package com.lee.tac.facade.pull;

import com.lee.tac.requests.CommonRequest;
import com.lee.tac.responses.CommonResponse;
import com.lee.tac.dto.TestcaseDto;

import java.util.List;

/**
 * program: tac-root
 * description: Report 操作
 * author: zhao lee
 * create: 2018-04-11 14:52
 **/
public interface TestcasePullApi {
    CommonResponse<List<TestcaseDto>> testCaseListSearch(CommonRequest<TestcaseDto> request);
}
