package com.lee.tac.facade.pull;

import com.lee.tac.dto.StatisticsYearDto;
import com.lee.tac.responses.CommonResponse;
import com.lee.tac.dto.StatisticsDto;

import java.util.List;


/**
 * program: tac-root
 * description: ${description}
 * author: zhao lee
 * create: 2018-04-20 21:03
 **/
public interface StatisticsApi {
    CommonResponse<StatisticsDto> getTodayData();
    CommonResponse<StatisticsDto> getYesterdayData();
    CommonResponse<StatisticsDto> getTotalData();
    CommonResponse<StatisticsDto> getSevenDayData();
    CommonResponse<List<StatisticsYearDto>> getRecentlyYearExecuteData();
}
