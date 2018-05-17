package com.lee.tac.facade;

import com.lee.tac.dto.StatisticsYearDto;
import com.lee.tac.responses.CommonResponse;
import com.lee.tac.utils.DateHelper;
import com.lee.tac.dto.StatisticsDto;
import com.lee.tac.facade.pull.StatisticsApi;
import com.lee.tac.service.StatisticsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * program: tac-root
 * description: ${description}
 * author: zhaoli@leoao.com
 * create: 2018-04-21 11:55
 **/

@Service("statisticsApi")
public class StatisticsApiImpl implements StatisticsApi{
    private final static Logger logger = LoggerFactory.getLogger(StatisticsApiImpl.class);

    @Autowired
    StatisticsServiceImpl statisticsServiceImpl;

    @Override
    public CommonResponse<StatisticsDto> getTodayData() {
        CommonResponse<StatisticsDto> response = new CommonResponse<>();
        String startTime = DateHelper.getCurrentDate("yyyy-MM-dd");
        String endTime = DateHelper.getSpecifiedDayAfter(startTime,1,"yyyy-MM-dd");
        try{
            StatisticsDto statisticsDto = statisticsServiceImpl.getSpecifiedData(startTime, endTime);
            response.setData(statisticsDto);
        } catch (Exception e) {
            logger.error("getTodayData error", e);
            response.fail(CommonResponse.ERROR, CommonResponse.ERROR_MSG);
        }
        return response;
    }

    @Override
    public CommonResponse<StatisticsDto> getYesterdayData() {
        CommonResponse<StatisticsDto> response = new CommonResponse<>();
        String endTime = DateHelper.getCurrentDate("yyyy-MM-dd");
        String startTime = DateHelper.getSpecifiedDayAfter(endTime,-1,"yyyy-MM-dd");
        try{
            StatisticsDto statisticsDto = statisticsServiceImpl.getSpecifiedData(startTime, endTime);
            response.setData(statisticsDto);
        } catch (Exception e) {
            logger.error("getYesterdayData error", e);
            response.fail(CommonResponse.ERROR, CommonResponse.ERROR_MSG);
        }
        return response;
    }

    @Override
    public CommonResponse<StatisticsDto> getTotalData() {
        CommonResponse<StatisticsDto> response = new CommonResponse<>();
        try{
            StatisticsDto statisticsDto = statisticsServiceImpl.getSpecifiedData("", "");
            response.setData(statisticsDto);
        } catch (Exception e) {
            logger.error("getTotalData error", e);
            response.fail(CommonResponse.ERROR, CommonResponse.ERROR_MSG);
        }
        return response;
    }

    @Override
    public CommonResponse<StatisticsDto> getSevenDayData() {
        CommonResponse<StatisticsDto> response = new CommonResponse<>();
        String today = DateHelper.getCurrentDate("yyyy-MM-dd");
        String endTime; //因为endTime 那天不包括所以要+1来包括今天
        endTime = DateHelper.getSpecifiedDayAfter(today,1,"yyyy-MM-dd");
        String startTime = DateHelper.getSpecifiedDayAfter(today,-6,"yyyy-MM-dd");
        try{
            StatisticsDto statisticsDto = statisticsServiceImpl.getSpecifiedData(startTime, endTime);
            response.setData(statisticsDto);
        } catch (Exception e) {
            logger.error("getSevenDayData error", e);
            response.fail(CommonResponse.ERROR, CommonResponse.ERROR_MSG);
        }
        return response;
    }

    @Override
    public CommonResponse<List<StatisticsYearDto>> getRecentlyYearExecuteData() {
        CommonResponse<List<StatisticsYearDto>> response = new CommonResponse<>();
        List<StatisticsYearDto> statisticsYearDtoList = new ArrayList<>();
        try{
            for(int i=-11; i <=0; i++){
                StatisticsYearDto statisticsYearDto = new StatisticsYearDto();
                String startTime = DateHelper.getMonthFirstDay(i);
                String endTime = DateHelper.getMonthLastDay(i);
                endTime = DateHelper.getSpecifiedDayAfter(endTime,1,"yyyy-MM-dd");
                int month = DateHelper.getSpecifiedMonth(i);
                statisticsYearDto.setMonth(String.format("%d 月", month));
                StatisticsDto statisticsDto = statisticsServiceImpl.getSpecifiedData(startTime, endTime);
                statisticsYearDto.setCount(statisticsDto.getReportLogCount());
                statisticsYearDtoList.add(statisticsYearDto);
            }
            response.setData(statisticsYearDtoList);
        } catch (Exception e){
            logger.error("getRecentlyYearExecuteData error", e);
            response.fail(CommonResponse.ERROR, CommonResponse.ERROR_MSG);
        }
        return response;
    }
}
