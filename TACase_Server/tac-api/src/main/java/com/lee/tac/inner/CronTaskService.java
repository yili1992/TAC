package com.lee.tac.inner;

import com.lee.tac.dto.CronTaskDto;
import com.lee.tac.requests.CommonRequest;
import com.lee.tac.responses.CommonResponse;

/**
 * program: TACase_Server
 * description: ${description}
 * author: zhaoli
 * create: 2018-06-02 15:52
 **/
public interface CronTaskService {
    String saveCronTask(Integer testcaseId, String author, String cron);

    boolean updateCronTask(Integer id, String author, String cron, boolean enable, boolean isDelete);

    boolean deleteCronTask(Integer id);
}
