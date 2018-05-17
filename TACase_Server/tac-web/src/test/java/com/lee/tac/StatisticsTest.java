package com.lee.tac;

import com.lee.tac.dto.StatisticsYearDto;
import com.lee.tac.facade.StatisticsApiImpl;
import com.lee.tac.responses.CommonResponse;
import com.lee.tac.dto.StatisticsDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

// 在当前宿主工程里使用spring-test进行集成测试
// https://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/testing.html
// https://docs.spring.io/spring/docs/current/spring-framework-reference/html/integration-testing.html
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
// 可按需重写application.properties的配置
// @SpringBootTest("tac.sample=hello test")
// 按需选择
@ActiveProfiles("development")
public class StatisticsTest {
    @Resource
    StatisticsApiImpl statisticsApi;

    @Test
    public void getYesterdayData() {
        CommonResponse<StatisticsDto> result = statisticsApi.getYesterdayData();
        System.out.println(result.getData().getProjectCount());
        System.out.println(result.getData().getReportCount());
        System.out.println(result.getData().getReportLogCount());
        System.out.println(result.getData().getViewCount());
    }

    @Test
    public void getToDayData() {
        CommonResponse<StatisticsDto> result = statisticsApi.getTodayData();
        System.out.println(result.getData().getProjectCount());
        System.out.println(result.getData().getReportCount());
        System.out.println(result.getData().getReportLogCount());
        System.out.println(result.getData().getViewCount());
    }

    @Test
    public void getTotalData() {
        CommonResponse<StatisticsDto> result = statisticsApi.getTotalData();
        System.out.println(result.getData().getProjectCount());
        System.out.println(result.getData().getReportCount());
        System.out.println(result.getData().getReportLogCount());
        System.out.println(result.getData().getViewCount());
    }

    @Test
    public void getRecentlyYearExecuteData() {
        CommonResponse<List<StatisticsYearDto>> result = statisticsApi.getRecentlyYearExecuteData();
        for(StatisticsYearDto statisticsYearDto : result.getData()){
            System.out.println(statisticsYearDto.getMonth()+":"+statisticsYearDto.getCount());
        }
    }

}
