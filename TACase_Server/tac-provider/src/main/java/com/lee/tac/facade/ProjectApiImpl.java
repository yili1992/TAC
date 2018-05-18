package com.lee.tac.facade;

import com.lee.tac.dto.Page;
import com.lee.tac.dto.ProjectDto;
import com.lee.tac.facade.pull.ProjectPullApi;
import com.lee.tac.responses.CommonResponse;
import com.lee.tac.mapper.ProjectMapper;
import com.lee.tac.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * program: tac-root
 * description: ${description}
 * author: zhao lee
 * create: 2018-04-11 16:17
 **/
@Service("projectApi")
public class ProjectApiImpl implements ProjectPullApi {
    private final static Logger logger = LoggerFactory.getLogger(ProjectApiImpl.class);

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public CommonResponse<List<ProjectDto>> getProject() {
        CommonResponse<List<ProjectDto>> response = new CommonResponse<>();
        try {
            List<ProjectDto> projectDtoList = new ArrayList<>();
            List<Project> projects = projectMapper.selectAllProject();
            for (Project x : projects) {
                ProjectDto projectDto = new ProjectDto();
                projectDto.setId(x.getId());
                projectDto.setName(x.getName());
                projectDtoList.add(projectDto);
            }
            response.setData(projectDtoList);
            Page page = new Page();
            page.setCount(projectDtoList.size());
            response.setPage(page);
        } catch (Exception e) {
            logger.error("getProject error", e);
            response.fail(CommonResponse.ERROR, CommonResponse.ERROR_MSG);
        }
        return response;
    }

}
