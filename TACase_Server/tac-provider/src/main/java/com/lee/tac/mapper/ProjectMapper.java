package com.lee.tac.mapper;

import com.lee.tac.dto.ProjectDto;
import com.lee.tac.model.Project;

import java.util.List;

public interface ProjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(Integer id);

    Project selectByName(String name);

    List<Project> selectAllProject();

    List<Project> queryProjectListForSearch(ProjectDto projectDto);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);
}