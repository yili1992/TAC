package com.lee.tac;

import com.lee.tac.dto.LogPullDto;
import com.lee.tac.dto.Page;
import com.lee.tac.dto.TestcaseLogDto;
import com.lee.tac.facade.TestcaseLogApiImpl;
import com.lee.tac.requests.CommonRequest;
import com.lee.tac.responses.CommonResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class TestCaseLogTest {
    @Resource
    TestcaseLogApiImpl reportLogApi;

    @Test
    public void getLog() {
        CommonRequest<LogPullDto> request = new CommonRequest<>();
        LogPullDto logPullDto = new LogPullDto();
        Page page = new Page();
        page.setPageSize(1);
        page.setCurrentPage(1);
        request.setPage(page);
        logPullDto.setTestcaseId(1);
        logPullDto.setStatusCode("done");
        request.setRequestData(logPullDto);
        CommonResponse<List<TestcaseLogDto>> result = reportLogApi.logListSearch(request);
        System.out.println(result.toString());
        for(TestcaseLogDto testcaseLogDto : result.getData()){
            System.out.println(testcaseLogDto.getId());
            System.out.println(testcaseLogDto.getTestcaseName());
            System.out.println(testcaseLogDto.getStartTime());
            System.out.println(testcaseLogDto.getEndTime());
        }
    }
}
