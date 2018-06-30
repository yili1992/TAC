package com.lee.tac.facade.pull;

import com.lee.tac.dto.CronTaskDto;
import com.lee.tac.requests.CommonRequest;
import com.lee.tac.responses.CommonResponse;

import java.util.List;

/**
 * program: TACase_Server
 * description: ${description}
 * author: zhaoli
 * create: 2018-06-02 15:36
 **/
public interface CronTaskPullApi {
    CommonResponse<List<CronTaskDto>> getTasks(CommonRequest<CronTaskDto> request);
}
