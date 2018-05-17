package com.lee.tac.inner;

import com.lee.tac.dto.ProjectDto;

/**
 * program: tac-root
 * description: ${description}
 * author: zhaoli@leoao.com
 * create: 2018-04-11 16:09
 **/
public interface ProjectService {
    /**
    * description: save project
    * @Param:
    * @return:
    * author: zhaoli@leoao.com
    * @Date:
    */
    int saveProject(ProjectDto newProject);
}
