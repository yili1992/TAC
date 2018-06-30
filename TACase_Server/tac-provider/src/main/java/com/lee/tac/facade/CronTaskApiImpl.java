package com.lee.tac.facade;

import com.lee.tac.dto.CronTaskDto;
import com.lee.tac.dto.Page;
import com.lee.tac.facade.pull.CronTaskPullApi;
import com.lee.tac.inner.CronTaskService;
import com.lee.tac.mapper.CronTaskMapper;
import com.lee.tac.model.CronTask;
import com.lee.tac.requests.CommonRequest;
import com.lee.tac.responses.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * program: TACase_Server
 * description: ${description}
 * author: zhaoli
 * create: 2018-06-02 15:55
 **/
@Service("taskApi")
public class CronTaskApiImpl implements CronTaskPullApi {
    private final static Logger logger = LoggerFactory.getLogger(CronTaskApiImpl.class);

    @Autowired
    CronTaskMapper cronTaskMapper;

    @Override
    public CommonResponse<List<CronTaskDto>> getTasks(CommonRequest<CronTaskDto> request) {
        CommonResponse<List<CronTaskDto>> response = new CommonResponse<>();
        Page page = request.getPage(true);
        try {
            List<CronTaskDto> cronTaskDtoList = cronTaskMapper.queryCronTask();
            if (cronTaskDtoList.size() != 0) {
                page.setCount(cronTaskDtoList.size(), true);
                response.setPage(page);
                cronTaskDtoList = page.cutListByPage(cronTaskDtoList);
            }
            response.setData(cronTaskDtoList);
        } catch (Exception e) {
            logger.error("get tasks error", e);
            response.fail(CommonResponse.ERROR, CommonResponse.ERROR_MSG);
        }
        return  response;
    }
}
