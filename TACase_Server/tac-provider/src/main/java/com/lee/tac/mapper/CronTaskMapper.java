package com.lee.tac.mapper;

import com.lee.tac.dto.CronTaskDto;
import com.lee.tac.model.CronTask;

import java.util.List;

public interface CronTaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CronTask record);

    int insertSelective(CronTask record);

    CronTask selectByPrimaryKey(Integer id);

    List<CronTask> selectExistTaskByTestcaseId(Integer id);

    List<CronTaskDto> queryCronTask();

    int updateByPrimaryKeySelective(CronTask record);

    int updateByPrimaryKey(CronTask record);
}