package com.lee.tac.mapper;

import com.lee.tac.dto.TestcaseDto;
import com.lee.tac.model.Testcase;

import java.util.List;

public interface TestcaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Testcase record);

    int insertSelective(Testcase record);

    Testcase selectByPrimaryKey(Integer id);

    List<TestcaseDto> queryTestCaseListForSearch(TestcaseDto testcaseDto);

    int updateByPrimaryKeySelective(Testcase record);

    int updateByPrimaryKeySelectiveRowLock(Testcase record);

    int updateByPrimaryKey(Testcase record);
}