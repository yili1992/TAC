package com.lee.tac.facade.pull;

import com.lee.tac.requests.CommonRequest;
import com.lee.tac.responses.CommonResponse;
import com.lee.tac.dto.LogPullDto;
import com.lee.tac.dto.TestcaseLogDto;

import java.util.List;

/**
 * program: tac-root
 * description: ${description}
 * author: zhaoli@leoao.com
 * create: 2018-04-18 13:56
 **/
public interface TestcaseLogPullApi {
    CommonResponse<List<TestcaseLogDto>> logListSearch(CommonRequest<LogPullDto> request);
}
