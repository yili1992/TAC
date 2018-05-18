package com.lee.tac.inner;

import com.lee.tac.dto.ProjectDto;

/**
 * program: tac-root
 * description: ${description}
 * author: zhao lee
 * create: 2018-04-11 16:09
 **/
public interface ProjectService {
    /**
    * description: save project
    * @Param:
    * @return:
    * author: zhao lee
    * @Date:
    */
    int saveProject(ProjectDto newProject);
}
