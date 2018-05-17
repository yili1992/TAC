package com.lee.tac.facade.pull;

import com.lee.tac.responses.CommonResponse;
import com.lee.tac.dto.TestcaseStatusDto;

import java.util.List;

/**
 * program: tac-root
 * description: ${description}
 * author: zhaoli@leoao.com
 * create: 2018-04-09 15:31
 **/
public interface TestcaseStatusPullApi {
    CommonResponse<List<TestcaseStatusDto>> getAllStatus();
}
