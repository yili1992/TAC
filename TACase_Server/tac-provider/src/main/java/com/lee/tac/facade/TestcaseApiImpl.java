package com.lee.tac.facade;

import com.lee.tac.dto.Page;
import com.lee.tac.dto.ProjectDto;
import com.lee.tac.dto.TestcaseDto;
import com.lee.tac.facade.pull.TestcasePullApi;
import com.lee.tac.inner.AsyncService;
import com.lee.tac.mapper.TestcaseMapper;
import com.lee.tac.model.Testcase;
import com.lee.tac.requests.CommonRequest;
import com.lee.tac.responses.CommonResponse;
import com.lee.tac.service.ProjectServiceImpl;
import com.lee.tac.utils.DateHelper;
import com.lee.tac.mapper.ProjectMapper;
import com.lee.tac.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import static com.lee.tac.utils.EncryptionTool.decryptBASE64;

/**
 * program: tac-root
 * description: ${description}
 * author: zhao lee
 * create: 2018-04-12 14:09
 **/
@Service("reportApi")
public class TestcaseApiImpl implements TestcasePullApi, TestcaseApi {
    private final static Logger logger = LoggerFactory.getLogger(TestcaseApiImpl.class);

    @Autowired
    private TestcaseMapper testcaseMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    AsyncService asyncService;

    @Autowired
    private ProjectServiceImpl projectServiceImpl;

    @Override
    public CommonResponse<List<TestcaseDto>> testCaseListSearch(CommonRequest<TestcaseDto> request) {
        CommonResponse<List<TestcaseDto>> response = new CommonResponse<>();
        Page page = request.getPage(true);
        try{
            List<TestcaseDto> testcaseDtoList = testcaseMapper.queryTestCaseListForSearch(request.getRequestData());
            if(testcaseDtoList.size()!=0){
                page.setCount(testcaseDtoList.size(),true);
                response.setPage(page);
                testcaseDtoList = page.cutListByPage(testcaseDtoList);
            }
            response.setData(testcaseDtoList);
        } catch (Exception e) {
            logger.error("reportListSearch error", e);
            response.fail(CommonResponse.ERROR, CommonResponse.ERROR_MSG);
        }
        return  response;
    }

    @Override
    public CommonResponse<Integer> saveTestCase(CommonRequest<TestcaseDto> request) {
        CommonResponse<Integer> response = new CommonResponse<>();
        try{
            int projectId;
            TestcaseDto testcaseDto = request.getRequestData();
            Project checkProject = projectMapper.selectByName(testcaseDto.getProjectName());
            if(checkProject ==null){
                ProjectDto newProject = new ProjectDto();
                newProject.setName(testcaseDto.getProjectName());
                projectId = projectServiceImpl.saveProject(newProject);
            } else{
                projectId = checkProject.getId();
            }
            Testcase testcase = new Testcase();
            testcase.setAuthor(testcaseDto.getAuthor());
            testcase.setContent(testcaseDto.getContent());
            testcase.setName(testcaseDto.getName());
            testcase.setProjectId(projectId);
            testcase.setCreateTime(DateHelper.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
            int testcaseId = testcaseMapper.insert(testcase);
            String filePath = decryptBASE64(testcaseDto.getContent());
            asyncService.asyncUpdateXml(filePath, testcaseDto.getProjectName(), testcaseDto.getName());
            response.setData(testcaseId);
            response.setMsg("测试集增加成功");
        } catch (Exception e) {
            logger.error("saveTestCase error", e);
            response.fail(CommonResponse.ERROR, CommonResponse.ERROR_MSG);
        }
        return  response;
    }

    @Override
    public CommonResponse<Integer> updateTestCase(CommonRequest<TestcaseDto> request) {
        CommonResponse<Integer> response = new CommonResponse<>();
        int testcaseId = 0;
        try{
            Testcase testcase = new Testcase();
            testcase.setId(request.getRequestData().getId());
            testcase.setName(request.getRequestData().getName());
            testcase.setAuthor(request.getRequestData().getAuthor());
            Project checkProject = projectMapper.selectByName(request.getRequestData().getProjectName());
            testcase.setProjectId(checkProject.getId());
            Testcase orgTestcase = testcaseMapper.selectByPrimaryKey(request.getRequestData().getId());
            if (orgTestcase.getName().equals(testcase.getName())&&
                    orgTestcase.getProjectId().equals(testcase.getProjectId())&&
                    orgTestcase.getAuthor().equals(testcase.getAuthor())){
                testcaseId =1;
                response.setMsg("测试集没有更新");
            } else if (orgTestcase.getName().equals(testcase.getName())&&
                    orgTestcase.getProjectId().equals(testcase.getProjectId())){
                testcaseId = testcaseMapper.updateByPrimaryKeySelective(testcase);
                response.setMsg("测试集更新成功");
            } else {
                String filePath = decryptBASE64(orgTestcase.getContent());
                asyncService.asyncUpdateXml(filePath, request.getRequestData().getProjectName(), testcase.getName());
                testcaseId = testcaseMapper.updateByPrimaryKeySelective(testcase);
                response.setMsg("测试集更新成功");
            }

            response.setData(testcaseId);
            if(testcaseId!=1){
                response.setCode("10001");
                response.setMsg("测试集更新失败");
            }
        } catch (Exception e) {
            logger.error("updateReport error", e);
            response.fail(CommonResponse.ERROR, CommonResponse.ERROR_MSG);
        }
        return  response;
    }

    @Override
    public CommonResponse<Integer> deleteTestCase(CommonRequest<Integer> request) {
        CommonResponse<Integer> response = new CommonResponse<>();
        try{
            int testcaseId = testcaseMapper.deleteByPrimaryKey(request.getRequestData());
            response.setData(testcaseId);
            if(testcaseId==1){
                response.setMsg("测试集删除成功");
            } else{
                response.setCode("10001");
                response.setMsg("测试集删除失败");
            }
        } catch (Exception e) {
            logger.error("deleteReport error", e);
            response.fail(CommonResponse.ERROR, CommonResponse.ERROR_MSG);
        }
        return  response;
    }
}
