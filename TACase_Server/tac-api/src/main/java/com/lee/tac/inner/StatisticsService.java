package com.lee.tac.inner;

import com.lee.tac.dto.StatisticsDto;

/**
 * program: tac-root
 * description: ${description}
 * author: zhaoli@leoao.com
 * create: 2018-04-21 14:22
 **/
public interface StatisticsService {
    /**
    * description: getSpecifiedData
    * @Param:
    * @return:
    * author: zhaoli@leoao.com
    * @Date:
    */
    StatisticsDto getSpecifiedData(String startTime, String endTime);
}
