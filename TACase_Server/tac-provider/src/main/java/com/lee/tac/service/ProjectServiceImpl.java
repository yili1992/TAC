package com.lee.tac.service;

import com.lee.tac.dto.ProjectDto;
import com.lee.tac.inner.ProjectService;
import com.lee.tac.mapper.ProjectMapper;
import com.lee.tac.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * program: tac-root
 * description: ${description}
 * author: zhao lee
 * create: 2018-04-11 16:17
 **/
@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
    private final static Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public int saveProject(ProjectDto newProject) {
        Project project = new Project();
        project.setName(newProject.getName());
        projectMapper.insert(project);
        return projectMapper.selectByName(project.getName()).getId();
    }
}
