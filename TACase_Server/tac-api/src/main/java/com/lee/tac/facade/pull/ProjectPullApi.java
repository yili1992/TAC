package com.lee.tac.facade.pull;

import com.lee.tac.dto.ProjectDto;
import com.lee.tac.responses.CommonResponse;

import java.util.List;

/**
 * program: tac-root
 * description: ${description}
 * author: zhaoli@leoao.com
 * create: 2018-04-11 16:08
 **/
public interface ProjectPullApi {
    CommonResponse<List<ProjectDto>> getProject();
}
