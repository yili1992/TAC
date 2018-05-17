package com.lee.tac;

import com.lee.tac.dto.TestcaseDto;
import com.lee.tac.facade.TestcaseApiImpl;
import com.lee.tac.mapper.TestcaseMapper;
import com.lee.tac.model.Testcase;
import com.lee.tac.requests.CommonRequest;
import com.lee.tac.responses.CommonResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

// 在当前宿主工程里使用spring-test进行集成测试
// https://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/testing.html
// https://docs.spring.io/spring/docs/current/spring-framework-reference/html/integration-testing.html
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
// 可按需重写application.properties的配置
// @SpringBootTest("tac.sample=hello test")
// 按需选择
@ActiveProfiles("development")
public class TestCaseTest {
    @Resource
    TestcaseApiImpl reportApi;

    @Autowired
    private TestcaseMapper testcaseMapper;

    @Test
    public void getReport() {
        CommonRequest<TestcaseDto> request = new CommonRequest<>();
        TestcaseDto reqTestcaseDto = new TestcaseDto();
//        Page page = new Page();
//        page.setPageSize(1);
//        page.setCurrentPage(1);
//        request.setPage(page);
        //reqTestcaseDto.setName("正式");
        request.setRequestData(reqTestcaseDto);
        CommonResponse<List<TestcaseDto>> result = reportApi.testCaseListSearch(request);
        System.out.println(result.getPage());
        for(TestcaseDto testcaseDto : result.getData()){
            System.out.println(testcaseDto.getId());
            System.out.println(testcaseDto.getName());
            System.out.println(testcaseDto.getProjectName());
            System.out.println(testcaseDto.getContent());
            System.out.println(testcaseDto.getNum());
        }
    }

    @Test
    public void saveReport() {
        CommonRequest<TestcaseDto> request = new CommonRequest<>();
        TestcaseDto reqTestcaseDto = new TestcaseDto();
        reqTestcaseDto.setAuthor("zhaoli");
        reqTestcaseDto.setContent("aaaa");
        reqTestcaseDto.setName("testreport");
        reqTestcaseDto.setProjectName("PHP2测试");
        request.setRequestData(reqTestcaseDto);
        System.out.println(request.toString());
        CommonResponse<Integer> result = reportApi.saveTestCase(request);
        System.out.println(result.getData());
    }

    @Test
    public void updateReport() {
        CommonRequest<TestcaseDto> request = new CommonRequest<>();
        TestcaseDto reqTestcaseDto = new TestcaseDto();
        reqTestcaseDto.setId(12);
        reqTestcaseDto.setAuthor("zhaoli");
        reqTestcaseDto.setContent("aaaa");
        reqTestcaseDto.setName("testreport");
        reqTestcaseDto.setProjectName("PHP2测试");
        request.setRequestData(reqTestcaseDto);
        System.out.println(request.toString());
        CommonResponse<Integer> result = reportApi.updateTestCase(request);
        System.out.println(result.getData());
        System.out.println(result.getMsg());
    }
    @Test
    public void updateTestcaseRowLock() {
        Testcase testcase = new Testcase();
        testcase.setId(30);
        testcase.setProjectId(4);
        int testcaseId = testcaseMapper.updateByPrimaryKeySelectiveRowLock(testcase);
        System.out.println(testcaseId);
    }

    @Test
    public void deleteReport() {
        CommonRequest<Integer> request = new CommonRequest<>();
        request.setRequestData(12);
        System.out.println(request.toString());
        CommonResponse<Integer> result = reportApi.deleteTestCase(request);
        System.out.println(result.getData());

    }
}
