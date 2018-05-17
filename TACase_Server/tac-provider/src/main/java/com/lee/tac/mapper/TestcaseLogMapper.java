package com.lee.tac.mapper;

import com.lee.tac.dto.LogPullDto;
import com.lee.tac.dto.TestcaseLogDto;
import com.lee.tac.model.TestcaseLog;

import java.util.List;

public interface TestcaseLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TestcaseLog record);

    int insertSelective(TestcaseLog record);

    TestcaseLogDto selectByPrimaryKey(Integer id);

    List<TestcaseLogDto> queryLogListForSearch(LogPullDto logPullDto);

    int updateByPrimaryKeySelective(TestcaseLog record);

    int updateByPrimaryKey(TestcaseLog record);
}