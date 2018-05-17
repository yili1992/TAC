package com.lee.tac.facade;

import com.lee.tac.dto.TestcaseDto;
import com.lee.tac.requests.CommonRequest;
import com.lee.tac.responses.CommonResponse;

/**
 * program: tac-root
 * description: ${description}
 * author: zhaoli@leoao.com
 * create: 2018-04-16 19:58
 **/
public interface TestcaseApi {
    CommonResponse<Integer> saveTestCase(CommonRequest<TestcaseDto> request);

    CommonResponse<Integer> updateTestCase(CommonRequest<TestcaseDto> request);

    CommonResponse<Integer> deleteTestCase(CommonRequest<Integer> request);
}
