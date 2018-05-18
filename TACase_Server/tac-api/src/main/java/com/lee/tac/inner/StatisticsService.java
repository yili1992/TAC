package com.lee.tac.inner;

import com.lee.tac.dto.StatisticsDto;

/**
 * program: tac-root
 * description: ${description}
 * author: zhao lee
 * create: 2018-04-21 14:22
 **/
public interface StatisticsService {
    /**
    * description: getSpecifiedData
    * @Param:
    * @return:
    * author: zhao lee
    * @Date:
    */
    StatisticsDto getSpecifiedData(String startTime, String endTime);
}
