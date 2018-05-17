package com.lee.tac.service;

import com.lee.tac.dto.*;
import com.lee.tac.inner.StatisticsService;
import com.lee.tac.mapper.TestcaseMapper;
import com.lee.tac.model.View;
import com.lee.tac.dto.*;
import com.lee.tac.mapper.ProjectMapper;
import com.lee.tac.mapper.TestcaseLogMapper;
import com.lee.tac.mapper.ViewMapper;
import com.lee.tac.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * program: tac-root
 * description: ${description}
 * author: zhaoli@leoao.com
 * create: 2018-04-11 16:17
 **/
@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {
    private final static Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);

    @Autowired
    private TestcaseMapper testcaseMapper;

    @Autowired
    private TestcaseLogMapper testcaseLogMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ViewMapper viewMapper;

    @Override
    public StatisticsDto getSpecifiedData(String startTime, String endTime) {
        StatisticsDto response = new StatisticsDto();
        TestcaseDto testcaseDto = new TestcaseDto();
        testcaseDto.setStartTime(startTime);
        testcaseDto.setEndTime(endTime);
        List<TestcaseDto> testcaseDtoList = testcaseMapper.queryTestCaseListForSearch(testcaseDto);

        LogPullDto logPullDto = new LogPullDto();
        logPullDto.setStartTime(startTime);
        logPullDto.setEndTime(endTime);
        List<TestcaseLogDto> testcaseLogDtoList = testcaseLogMapper.queryLogListForSearch(logPullDto);

        ProjectDto projectDto = new ProjectDto();
        projectDto.setStartTime(startTime);
        projectDto.setEndTime(endTime);
        List<Project> projectDtoList = projectMapper.queryProjectListForSearch(projectDto);

        ViewDto viewDto = new ViewDto();
        viewDto.setStartTime(startTime);
        viewDto.setEndTime(endTime);
        List<View> viewList = viewMapper.getViewCountList(viewDto);
        int viewCount = 0;
        for(View view: viewList){
            viewCount+=view.getPv();
        }
        response.setReportCount(testcaseDtoList.size());
        response.setReportLogCount(testcaseLogDtoList.size());
        response.setProjectCount(projectDtoList.size());
        response.setViewCount(viewCount);
        return  response;
    }
}
